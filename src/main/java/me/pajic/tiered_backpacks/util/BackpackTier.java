package me.pajic.tiered_backpacks.util;

import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum BackpackTier implements StringRepresentable, EnumTranslatable {
    LEATHER("leather"),
    COPPER("copper"),
    IRON("iron"),
    GOLDEN("golden"),
    DIAMOND("diamond"),
    NETHERITE("netherite");

    private final String name;

    BackpackTier(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    @Override
    public @NotNull String prefix() {
        return "tiered_backpacks.backpackTier";
    }
}
