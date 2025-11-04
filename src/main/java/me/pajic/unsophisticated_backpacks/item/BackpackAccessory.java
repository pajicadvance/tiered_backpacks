package me.pajic.unsophisticated_backpacks.item;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.unsophisticated_backpacks.Main;
import me.pajic.unsophisticated_backpacks.network.ModNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.Optional;

public class BackpackAccessory implements Accessory {

    public static void init() {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) ->
                registries.lookupOrThrow(Registries.ITEM).getOrThrow(ModItems.BACKPACKS).forEach(itemHolder ->
                        AccessoryRegistry.register(itemHolder.value(), new BackpackAccessory())
                )
        );
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        CommonLifecycleEvents.TAGS_LOADED.register((registries, client) ->
                registries.lookupOrThrow(Registries.ITEM).getOrThrow(ModItems.BACKPACKS).forEach(itemHolder ->
                        AccessoriesRendererRegistry.bindItemToEmptyRenderer(itemHolder.value())
                )
        );
    }

    public static boolean tryOpenBackpack(Player player) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
        if (ac.isPresent() && ac.get().isEquipped(stack -> stack.is(ModItems.BACKPACKS))) {
            ClientPlayNetworking.send(new ModNetworking.C2SOpenBackpackPayload(2));
            return true;
        }
        return false;
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        return Main.CONFIG.preventUnequipWhenNotEmpty.get() && stack.is(ModItems.BACKPACKS) ?
                stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY) == ItemContainerContents.EMPTY
                : Accessory.super.canUnequip(stack, reference);
    }
}
