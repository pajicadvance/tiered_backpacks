package me.pajic.tiered_backpacks;

import me.pajic.tiered_backpacks.config.ModConfig;
import net.minecraft.resources.Identifier;

// Headless fixture: avoid Fabric's config-file registration; use the real config defaults.
public final class TieredBackpacks {
    public static final String MOD_ID = "tiered_backpacks";
    public static ModConfig CONFIG = new ModConfig();
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
