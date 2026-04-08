package me.pajic.tiered_backpacks.util;

import eu.pb4.trinkets.api.TrinketsApi;
import me.pajic.tiered_backpacks.item.BackpackItem;
import me.pajic.tiered_backpacks.item.BackpackTrinketItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TrinketsCompat {

	public static ItemStack tryGetTrinketBackpack(LivingEntity entity) {
		var list = TrinketsApi.getAttachment(entity).getEquipped(stack -> stack.is(BackpackUtil.BACKPACKS));
		if (!list.isEmpty()) return list.getFirst().getB();
		return ItemStack.EMPTY;
	}

	public static BackpackItem makeTrinketBackpack(BackpackTier tier) {
		return new BackpackTrinketItem(tier);
	}
}
