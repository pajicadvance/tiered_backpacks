package me.pajic.tiered_backpacks.platform;

import me.pajic.tiered_backpacks.ui.BackpackMenu;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public interface Platform {
	boolean isModLoaded(String modId);

	boolean isDebug();

	void sendToServer(CustomPacketPayload payload);

	void sendToClient(ServerPlayer player, CustomPacketPayload payload);

	MenuType<BackpackMenu> constructMenu();

	InteractionResult openBackpackScreen(Player player, ItemStack backpack);
}
