package me.pajic.tiered_backpacks;

import me.pajic.tiered_backpacks.item.BackpackAccessory;
import me.pajic.tiered_backpacks.keybind.ModKeybinds;
import me.pajic.tiered_backpacks.ui.BackpackScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class ClientMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(Main.BACKPACK_MENU, BackpackScreen::new);
        if (Main.ACCESSORIES_LOADED) BackpackAccessory.clientInit();
        ModKeybinds.init();
    }
}
