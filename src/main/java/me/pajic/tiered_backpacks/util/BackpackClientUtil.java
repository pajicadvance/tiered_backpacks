package me.pajic.tiered_backpacks.util;

import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.keybind.ModKeybinds;
import me.pajic.tiered_backpacks.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class BackpackClientUtil {

	public static void onClientTick(Minecraft client) {
		if (ModKeybinds.OPEN_BACKPACK.consumeClick() && client.player != null && client.level != null) {
			ItemStack chestItem = ItemStack.EMPTY;
			if (CompatFlags.TRINKETS_LOADED) chestItem = TrinketsCompat.tryGetTrinketBackpack(client.player);
			if (!chestItem.isEmpty()) TieredBackpacks.xplat().sendToServer(new ModNetworking.C2SOpenBackpackPayload(2));
			else {
				chestItem = client.player.getItemBySlot(EquipmentSlot.CHEST);
				if (BackpackUtil.isValidContainerHolder(chestItem)) {
					TieredBackpacks.xplat().sendToServer(new ModNetworking.C2SOpenBackpackPayload(1));
				} else if (TieredBackpacks.CONFIG.canOpenFromInventory.get()) {
					if (client.player.getInventory().getNonEquipmentItems().stream().anyMatch(stack -> stack.is(BackpackUtil.BACKPACKS))) {
						TieredBackpacks.xplat().sendToServer(new ModNetworking.C2SOpenBackpackPayload(0));
					}
				}
			}
		}
	}
}
