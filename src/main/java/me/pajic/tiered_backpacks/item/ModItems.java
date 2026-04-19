package me.pajic.tiered_backpacks.item;

import me.pajic.tiered_backpacks.compat.OhmegaCompat;
import me.pajic.tiered_backpacks.util.BackpackTier;
import me.pajic.tiered_backpacks.util.CompatFlags;
import me.pajic.tiered_backpacks.compat.TrinketsCompat;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final Item LEATHER_BACKPACK = makeBackpack(BackpackTier.LEATHER);
    public static final Item COPPER_BACKPACK = makeBackpack(BackpackTier.COPPER);
    public static final Item IRON_BACKPACK = makeBackpack(BackpackTier.IRON);
    public static final Item GOLDEN_BACKPACK = makeBackpack(BackpackTier.GOLDEN);
    public static final Item DIAMOND_BACKPACK = makeBackpack(BackpackTier.DIAMOND);
    public static final Item NETHERITE_BACKPACK = makeBackpack(BackpackTier.NETHERITE);

	private static BackpackItem makeBackpack(BackpackTier tier) {
		if (CompatFlags.TRINKETS_LOADED) return TrinketsCompat.makeTrinketBackpack(tier);
		if (CompatFlags.OHMEGA_LOADED) return OhmegaCompat.makeOhmegaBackpack(tier);
		return new BackpackItem(tier);
	}

	public static void init() {}
}
