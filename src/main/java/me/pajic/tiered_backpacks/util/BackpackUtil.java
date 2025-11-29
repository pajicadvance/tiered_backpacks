package me.pajic.tiered_backpacks.util;

import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.component.ModDataComponents;
import me.pajic.tiered_backpacks.item.BackpackAccessory;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BackpackUtil {

	public static final TagKey<Item> BACKPACKS = TagKey.create(Registries.ITEM, TieredBackpacks.id("backpacks"));

	public static void tryOpenBackpack(int openMethod, ServerPlayer player) {
		ItemStack backpack = ItemStack.EMPTY;
		switch (openMethod) {
			case 0 -> backpack = player.getInventory().getNonEquipmentItems().stream()
					.filter(stack -> stack.is(BACKPACKS))
					.findFirst().orElse(ItemStack.EMPTY);
			case 1 -> backpack = player.getItemBySlot(EquipmentSlot.CHEST);
			case 2 -> backpack = BackpackAccessory.openBackpack(player);
		}
		if (!backpack.isEmpty()) TieredBackpacks.xplat().openBackpackScreen(player, backpack);
	}

	public static BackpackDimensions getBackpackDimensions(ItemStack backpack) {
		return switch (backpack.getOrDefault(ModDataComponents.BACKPACK_TIER, BackpackTier.LEATHER)) {
			case LEATHER -> TieredBackpacks.CONFIG.leatherSize.get();
			case COPPER -> TieredBackpacks.CONFIG.copperSize.get();
			case IRON -> TieredBackpacks.CONFIG.ironSize.get();
			case GOLDEN -> TieredBackpacks.CONFIG.goldenSize.get();
			case DIAMOND -> TieredBackpacks.CONFIG.diamondSize.get();
			case NETHERITE -> TieredBackpacks.CONFIG.netheriteSize.get();
		};
	}
}
