package me.pajic.unsophisticated_backpacks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.unsophisticated_backpacks.Main;
import me.pajic.unsophisticated_backpacks.item.ModItems;
import net.minecraft.core.component.DataComponents;
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
    private boolean modifyMayPickup(boolean original, @Local ItemStack stack) {
        return Main.CONFIG.preventUnequipWhenNotEmpty.get() && stack.is(ModItems.BACKPACKS) ?
                stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY) == ItemContainerContents.EMPTY
                : original;
    }
}
