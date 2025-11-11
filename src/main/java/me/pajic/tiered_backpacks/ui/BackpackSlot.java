package me.pajic.tiered_backpacks.ui;

import me.pajic.tiered_backpacks.Main;
import me.pajic.tiered_backpacks.item.ModItems;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BackpackSlot extends Slot {
    public BackpackSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPickup(@NotNull Player player) {
        return check(getItem());
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return check(stack);
    }

    private boolean check(ItemStack stack) {
        if (stack.is(ModItems.BACKPACKS)) return false;
        return Main.CONFIG.canStoreShulkers.get() || !stack.is(ItemTags.SHULKER_BOXES);
    }
}
