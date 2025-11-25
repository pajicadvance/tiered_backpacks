package me.pajic.tiered_backpacks.item;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.core.Accessory;
import io.wispforest.accessories.api.core.AccessoryRegistry;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.network.ModNetworking;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.Optional;

public class BackpackAccessory implements Accessory {

	public static void register(Item item) {
		AccessoryRegistry.register(item, new BackpackAccessory());
	}

	public static void assignRenderer(Item item) {
		AccessoriesRendererRegistry.bindItemToEmptyRenderer(item);
	}

	public static ItemStack openBackpack(Player player) {
		Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
		if (ac.isPresent() && ac.get().isEquipped(stack -> stack.is(BackpackUtil.BACKPACKS))) {
			SlotEntryReference itemRef = ac.get().getFirstEquipped(stack -> stack.is(BackpackUtil.BACKPACKS));
			if (itemRef != null) return itemRef.stack();
		}
		return ItemStack.EMPTY;
	}

    public static boolean tryOpenBackpack(Player player) {
        Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(player);
        if (ac.isPresent() && ac.get().isEquipped(stack -> stack.is(BackpackUtil.BACKPACKS))) {
            TieredBackpacks.xplat().sendToServer(new ModNetworking.C2SOpenBackpackPayload(2));
            return true;
        }
        return false;
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        return TieredBackpacks.CONFIG.preventUnequipWhenNotEmpty.get() && stack.is(BackpackUtil.BACKPACKS) ?
                stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY) == ItemContainerContents.EMPTY
                : Accessory.super.canUnequip(stack, reference);
    }
}
