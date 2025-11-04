package me.pajic.unsophisticated_backpacks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.unsophisticated_backpacks.Main;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmithingRecipe;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmithingMenu.class)
public abstract class SmithingMenuMixin extends ItemCombinerMenu {
    public SmithingMenuMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition slotDefinition) {
        super(menuType, containerId, inventory, access, slotDefinition);
    }

    @Inject(
            method = "method_64653",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/inventory/SmithingMenu;resultSlots:Lnet/minecraft/world/inventory/ResultContainer;",
                    ordinal = 0
            )
    )
    private void handleDiamondBackpackUpgrade(
            CallbackInfo ci,
            @Local(argsOnly = true) RecipeHolder<SmithingRecipe> recipeHolder,
            @Local ItemStack result
    ) {
        if (recipeHolder.id().location().equals(Main.withModNamespace("netherite_backpack_smithing"))) {
            result.set(DataComponents.CONTAINER, slots.get(1).getItem().getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
        }
    }
}
