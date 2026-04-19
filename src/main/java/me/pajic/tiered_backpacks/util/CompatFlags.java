package me.pajic.tiered_backpacks.util;

import me.pajic.tiered_backpacks.TieredBackpacks;

public class CompatFlags {
	public static final boolean TRINKETS_LOADED = TieredBackpacks.xplat().isModLoaded("trinkets_updated");
	public static final boolean OHMEGA_LOADED = TieredBackpacks.xplat().isModLoaded("ohmega");
	public static final boolean SHULKER_BOX_TOOLTIP_LOADED = TieredBackpacks.xplat().isModLoaded("shulkerboxtooltip");
}
