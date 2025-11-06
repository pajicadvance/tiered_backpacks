package me.pajic.unsophisticated_backpacks;

import me.pajic.unsophisticated_backpacks.item.BackpackAccessory;
import me.pajic.unsophisticated_backpacks.keybind.ModKeybinds;
import me.pajic.unsophisticated_backpacks.ui.BackpackScreen;
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
