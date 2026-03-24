package me.pajic.tiered_backpacks.platform.fabric;

//? fabric {

import me.pajic.tiered_backpacks.TieredBackpacks;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.pajic.tiered_backpacks.keybind.ModKeybinds;
import me.pajic.tiered_backpacks.menu.ModMenuTypes;
import me.pajic.tiered_backpacks.ui.BackpackScreen;
import me.pajic.tiered_backpacks.util.BackpackClientUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModKeybinds.init();
		initKeybinds();
		initBackpackMenuScreen();
	}

	private void initKeybinds() {
		KeyMapping.Category.register(TieredBackpacks.id("keys"));
		KeyMappingHelper.registerKeyMapping(ModKeybinds.OPEN_BACKPACK);
		ClientTickEvents.END_CLIENT_TICK.register(BackpackClientUtil::onClientTick);
	}

	private void initBackpackMenuScreen() {
		MenuScreens.register(ModMenuTypes.BACKPACK_MENU, BackpackScreen::new);
	}
}
//?}
