package me.pajic.unsophisticated_backpacks.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.unsophisticated_backpacks.Main;
import me.pajic.unsophisticated_backpacks.item.ModItems;
import me.pajic.unsophisticated_backpacks.network.ModNetworking;
import me.pajic.unsophisticated_backpacks.item.BackpackAccessory;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EquipmentSlot;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static final KeyMapping.Category MOD_KEYS = KeyMapping.Category.register(Main.withModNamespace("keys"));

    public static final KeyMapping OPEN_BACKPACK = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.unsophisticated_backpacks.open_backpack",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            MOD_KEYS
    ));

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (OPEN_BACKPACK.consumeClick() && client.player != null && client.level != null) {
                if (Main.ACCESSORIES_LOADED && BackpackAccessory.tryOpenBackpack(client.player)) return;
                if (client.player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.BACKPACKS)) {
                    ClientPlayNetworking.send(new ModNetworking.C2SOpenBackpackPayload(1));
                }
                else if (Main.CONFIG.canOpenFromInventory.get()) {
                    if (client.player.getInventory().getNonEquipmentItems().stream().anyMatch(stack -> stack.is(ModItems.BACKPACKS))) {
                        ClientPlayNetworking.send(new ModNetworking.C2SOpenBackpackPayload(0));
                    }
                }
            }
        });
    }
}
