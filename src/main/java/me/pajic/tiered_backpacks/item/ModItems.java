package me.pajic.tiered_backpacks.item;

import me.pajic.tiered_backpacks.util.BackpackTier;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final Item LEATHER_BACKPACK = new BackpackItem(BackpackTier.LEATHER);
    public static final Item COPPER_BACKPACK = new BackpackItem(BackpackTier.COPPER);
    public static final Item IRON_BACKPACK = new BackpackItem(BackpackTier.IRON);
    public static final Item GOLDEN_BACKPACK = new BackpackItem(BackpackTier.GOLDEN);
    public static final Item DIAMOND_BACKPACK = new BackpackItem(BackpackTier.DIAMOND);
    public static final Item NETHERITE_BACKPACK = new BackpackItem(BackpackTier.NETHERITE);

	public static void init() {}
}
