package me.pajic.tiered_backpacks.platform.fabric;

//? fabric {

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.keybind.ModKeybinds;
import me.pajic.tiered_backpacks.menu.ModMenuTypes;
import me.pajic.tiered_backpacks.ui.BackpackScreen;
import me.pajic.tiered_backpacks.util.BackpackClientUtil;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import me.pajic.tiered_backpacks.util.CompatFlags;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.Registries;

@SuppressWarnings("unused")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		TieredBackpacks.onInitializeClient();
		ModKeybinds.init();
		if (CompatFlags.ACCESSORIES_LOADED) initBackpackAccessoryRenderer();
		initKeybinds();
		initBackpackMenuScreen();
	}

	private void initBackpackAccessoryRenderer() {
		CommonLifecycleEvents.TAGS_LOADED.register((registries, client) ->
				registries.lookupOrThrow(Registries.ITEM).getOrThrow(BackpackUtil.BACKPACKS).forEach(itemHolder ->
						AccessoriesRendererRegistry.bindItemToEmptyRenderer(itemHolder.value())
				)
		);
	}

	private void initKeybinds() {
		KeyMapping.Category.register(TieredBackpacks.id("keys"));
		KeyBindingHelper.registerKeyBinding(ModKeybinds.OPEN_BACKPACK);
		ClientTickEvents.END_CLIENT_TICK.register(BackpackClientUtil::onClientTick);
	}

	private void initBackpackMenuScreen() {
		MenuScreens.register(ModMenuTypes.BACKPACK_MENU, BackpackScreen::new);
	}
}
//?}
