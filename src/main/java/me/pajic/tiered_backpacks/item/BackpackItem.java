package me.pajic.tiered_backpacks.item;

import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.component.ModDataComponents;
import me.pajic.tiered_backpacks.util.BackpackTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BackpackItem extends Item {

    public BackpackItem(BackpackTier tier) {
        super(tier == BackpackTier.NETHERITE ?
				TieredBackpacks.CONFIG.canEquipInChestSlot.get() ?
                        new Properties()
                                .stacksTo(1)
                                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                                .component(ModDataComponents.BACKPACK_TIER, tier)
                                .equippable(EquipmentSlot.CHEST)
                                .fireResistant()
                                .setId(ResourceKey.create(Registries.ITEM, TieredBackpacks.id(tier.getSerializedName() + "_backpack"))) :
                        new Properties()
                                .stacksTo(1)
                                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                                .component(ModDataComponents.BACKPACK_TIER, tier)
                                .fireResistant()
                                .setId(ResourceKey.create(Registries.ITEM, TieredBackpacks.id(tier.getSerializedName() + "_backpack")))
                : TieredBackpacks.CONFIG.canEquipInChestSlot.get() ?
                        new Properties()
                                .stacksTo(1)
                                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                                .component(ModDataComponents.BACKPACK_TIER, tier)
                                .equippable(EquipmentSlot.CHEST)
                                .setId(ResourceKey.create(Registries.ITEM, TieredBackpacks.id(tier.getSerializedName() + "_backpack"))) :
                        new Properties()
                                .stacksTo(1)
                                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                                .component(ModDataComponents.BACKPACK_TIER, tier)
                                .setId(ResourceKey.create(Registries.ITEM, TieredBackpacks.id(tier.getSerializedName() + "_backpack")))
        );
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return TieredBackpacks.CONFIG.canOpenWithRightClick.get() ?
				TieredBackpacks.xplat().openBackpackScreen(player, player.getItemInHand(hand)) :
                super.use(level, player, hand);
    }
}
