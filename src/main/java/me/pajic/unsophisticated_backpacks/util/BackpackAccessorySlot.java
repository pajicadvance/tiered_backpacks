package me.pajic.unsophisticated_backpacks.util;

import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import org.jetbrains.annotations.NotNull;

public enum BackpackAccessorySlot implements EnumTranslatable {
    BACK, BACKPACK;

    @Override
    public @NotNull String prefix() {
        return "unsophisticated_backpacks.backpackAccessorySlot";
    }
}
