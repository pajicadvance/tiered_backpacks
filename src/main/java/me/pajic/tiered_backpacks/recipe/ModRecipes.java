package me.pajic.tiered_backpacks.recipe;

import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipes {

	public static RecipeSerializer<AttachBackpackRecipe> ATTACH_BACKPACK = new CustomRecipe.Serializer<>(AttachBackpackRecipe::new);

	public static RecipeSerializer<DetachBackpackRecipe> DETACH_BACKPACK = new CustomRecipe.Serializer<>(DetachBackpackRecipe::new);
}
