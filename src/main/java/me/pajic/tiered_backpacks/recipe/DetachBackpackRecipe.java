package me.pajic.tiered_backpacks.recipe;

import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.component.ModDataComponents;
import me.pajic.tiered_backpacks.item.ModItems;
import me.pajic.tiered_backpacks.util.BackpackTier;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DetachBackpackRecipe extends CustomRecipe {

	public DetachBackpackRecipe(CraftingBookCategory category) {
		super(category);
	}

	@Override
	public boolean matches(CraftingInput input, @NotNull Level level) {
		if (!TieredBackpacks.CONFIG.canAttachToChestplate.get() || input.size() != 1) return false;
		ItemStack itemStack = input.getItem(0);
		return !itemStack.isEmpty() && BackpackUtil.isChestplateWithBackpackAttached(itemStack);
	}

	@Override
	public @NotNull ItemStack assemble(CraftingInput inputStacks, @NotNull HolderLookup.Provider registries) {
		ItemStack backpack = ItemStack.EMPTY;
		ItemStack input = inputStacks.getItem(0);
		if (BackpackUtil.isChestplateWithBackpackAttached(input)) {
			backpack = switch (input.getOrDefault(ModDataComponents.BACKPACK_TIER, BackpackTier.LEATHER)) {
				case LEATHER -> new ItemStack(ModItems.LEATHER_BACKPACK);
				case COPPER -> new ItemStack(ModItems.COPPER_BACKPACK);
				case IRON -> new ItemStack(ModItems.IRON_BACKPACK);
				case GOLDEN -> new ItemStack(ModItems.GOLDEN_BACKPACK);
				case DIAMOND -> new ItemStack(ModItems.DIAMOND_BACKPACK);
				case NETHERITE -> new ItemStack(ModItems.NETHERITE_BACKPACK);
			};
			backpack.set(DataComponents.CONTAINER, input.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
			DyedItemColor dye = input.get(ModDataComponents.STORED_BACKPACK_DYE);
			if (dye != null) backpack.set(DataComponents.DYED_COLOR, dye);
		}
		return backpack;
	}

	@Override
	public @NotNull NonNullList<ItemStack> getRemainingItems(@NotNull CraftingInput inputStacks) {
		NonNullList<ItemStack> nonNullList = NonNullList.withSize(inputStacks.size(), ItemStack.EMPTY);
		for (int i = 0; i < nonNullList.size(); i++) {
			ItemStack input = inputStacks.getItem(i);
			if (BackpackUtil.isChestplateWithBackpackAttached(input)) {
				ItemStack chestplate = input.copy();
				chestplate.remove(ModDataComponents.BACKPACK_TIER);
				chestplate.remove(DataComponents.CONTAINER);
				chestplate.remove(ModDataComponents.STORED_BACKPACK_DYE);
				nonNullList.set(i, chestplate);
			}
		}
		return nonNullList;
	}

	@Override
	public @NotNull RecipeSerializer<? extends CustomRecipe> getSerializer() {
		return ModRecipes.DETACH_BACKPACK;
	}
}
