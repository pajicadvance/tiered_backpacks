package me.pajic.tiered_backpacks.item;

import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.util.BackpackTier;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BackpackItem extends Item {

    public BackpackItem(BackpackTier tier) {
        super(BackpackUtil.createProperties(tier));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return TieredBackpacks.CONFIG.canOpenWithRightClick.get() ?
				TieredBackpacks.xplat().openBackpackScreen(player, player.getItemInHand(hand)) :
                super.use(level, player, hand);
    }
}
