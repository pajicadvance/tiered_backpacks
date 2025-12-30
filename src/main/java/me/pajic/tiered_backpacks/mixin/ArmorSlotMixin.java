package me.pajic.tiered_backpacks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ArmorSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorSlot.class)
public class ArmorSlotMixin {

    @ModifyReturnValue(
            method = "mayPickup",
            at = @At("RETURN")
    )
    private boolean modifyMayPickup(boolean original, @Local ItemStack stack, @Local(argsOnly = true) Player player) {
        return !player.isCreative() && TieredBackpacks.CONFIG.preventUnequipWhenNotEmpty.get() && BackpackUtil.isValidContainerHolder(stack) ?
                stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY) == ItemContainerContents.EMPTY : original;
    }
}
