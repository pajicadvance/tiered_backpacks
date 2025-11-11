package me.pajic.tiered_backpacks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.tiered_backpacks.item.ModItems;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CauldronInteraction.class)
public interface CauldronInteractionMixin {

    @Inject(
            method = "bootStrap",
            at = @At("TAIL")
    )
    private static void addBackpack(CallbackInfo ci, @Local(ordinal = 1) Map<Item, CauldronInteraction> map) {
        map.put(ModItems.LEATHER_BACKPACK, CauldronInteraction::dyedItemIteration);
        map.put(ModItems.COPPER_BACKPACK, CauldronInteraction::dyedItemIteration);
        map.put(ModItems.IRON_BACKPACK, CauldronInteraction::dyedItemIteration);
        map.put(ModItems.GOLDEN_BACKPACK, CauldronInteraction::dyedItemIteration);
        map.put(ModItems.DIAMOND_BACKPACK, CauldronInteraction::dyedItemIteration);
    }
}
