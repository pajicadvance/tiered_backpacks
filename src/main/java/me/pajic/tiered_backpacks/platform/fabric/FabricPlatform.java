package me.pajic.tiered_backpacks.platform.fabric;

//? fabric {

import me.pajic.tiered_backpacks.network.ModNetworking;
import me.pajic.tiered_backpacks.platform.Platform;
import me.pajic.tiered_backpacks.ui.BackpackMenu;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FabricPlatform implements Platform {

	@Override
	public boolean isModLoaded(String modId) {
		return FabricLoader.getInstance().isModLoaded(modId);
	}

	@Override
	public ModLoader loader() {
		return ModLoader.FABRIC;
	}

	@Override
	public String mcVersion() {
		return FabricLoader.getInstance().getRawGameVersion();
	}

	@Override
	public boolean isDebug() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	@Override
	public void sendToClient(ServerPlayer player, CustomPacketPayload payload) {
		ServerPlayNetworking.send(player, payload);
	}

	@Override
	public void sendToServer(CustomPacketPayload payload) {
		ClientPlayNetworking.send(payload);
	}

	@Override
	public MenuType<BackpackMenu> constructMenu() {
		return new ExtendedScreenHandlerType<>(BackpackMenu::new, ModNetworking.S2CBackpackScreenPayload.CODEC);
	}

	@SuppressWarnings("resource")
	@Override
	public InteractionResult openBackpackScreen(Player player, ItemStack backpack) {
		if (!player.level().isClientSide()) {
			player.openMenu(new ExtendedScreenHandlerFactory<ModNetworking.S2CBackpackScreenPayload>() {
				@Override
				public @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
					return new BackpackMenu(i, inventory, backpack);
				}

				@Override
				public @NotNull Component getDisplayName() {
					return backpack.getDisplayName();
				}

				@Override
				public ModNetworking.S2CBackpackScreenPayload getScreenOpeningData(ServerPlayer serverPlayer) {
					return new ModNetworking.S2CBackpackScreenPayload(backpack);
				}
			});
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
//?}
