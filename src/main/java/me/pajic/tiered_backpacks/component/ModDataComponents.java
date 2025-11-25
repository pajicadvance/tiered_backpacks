package me.pajic.tiered_backpacks.component;

import me.pajic.tiered_backpacks.util.BackpackTier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.util.StringRepresentable;

public class ModDataComponents {
	public static final DataComponentType<BackpackTier> BACKPACK_TIER = DataComponentType.<BackpackTier>builder()
			.persistent(StringRepresentable.fromEnum(BackpackTier::values)).build();

	public static void init() {}
}
