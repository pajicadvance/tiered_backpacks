package me.pajic.unsophisticated_backpacks.item;

import me.pajic.unsophisticated_backpacks.Main;
import me.pajic.unsophisticated_backpacks.util.BackpackTier;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItems {
    public static final Item LEATHER_BACKPACK = registerBackpack(BackpackTier.LEATHER);
    public static final Item COPPER_BACKPACK = registerBackpack(BackpackTier.COPPER);
    public static final Item IRON_BACKPACK = registerBackpack(BackpackTier.IRON);
    public static final Item GOLDEN_BACKPACK = registerBackpack(BackpackTier.GOLDEN);
    public static final Item DIAMOND_BACKPACK = registerBackpack(BackpackTier.DIAMOND);
    public static final Item NETHERITE_BACKPACK = registerBackpack(BackpackTier.NETHERITE);

    public static final TagKey<Item> BACKPACKS = TagKey.create(Registries.ITEM, Main.withModNamespace("backpacks"));

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries ->
                entries.addBefore(Items.COMPASS, LEATHER_BACKPACK, COPPER_BACKPACK, IRON_BACKPACK, GOLDEN_BACKPACK, DIAMOND_BACKPACK, NETHERITE_BACKPACK)
        );
    }

    private static Item registerBackpack(BackpackTier tier) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                ResourceKey.create(Registries.ITEM, Main.withModNamespace(tier.getSerializedName() + "_backpack")),
                new BackpackItem(tier)
        );
    }
}
