package me.pajic.unsophisticated_backpacks.tooltip;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.color.ColorKey;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import me.pajic.unsophisticated_backpacks.Main;
import me.pajic.unsophisticated_backpacks.util.BackpackDimensions;
import me.pajic.unsophisticated_backpacks.util.BackpackTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BackpackPreviewProvider implements PreviewProvider {

    @Override
    public boolean shouldDisplay(@NotNull PreviewContext context) {
        return !getInventory(context).stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public List<ItemStack> getInventory(@NotNull PreviewContext context) {
        ItemContainerContents contents = context.stack().getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        return contents.stream().toList();
    }

    @Override
    public int getInventoryMaxSize(@NotNull PreviewContext context) {
        BackpackDimensions dimensions = Main.getBackpackDimensions(context.stack());
        return dimensions.rows.get() * dimensions.columns.get();
    }

    @Override
    public int getMaxRowSize(@NotNull PreviewContext context) {
        BackpackDimensions dimensions = Main.getBackpackDimensions(context.stack());
        return dimensions.columns.get();
    }

    @Override
    public ColorKey getWindowColorKey(@NotNull PreviewContext context) {
        return switch (context.stack().getOrDefault(Main.BACKPACK_TIER, BackpackTier.LEATHER)) {
            case LEATHER -> ColorKey.ofRgb(-6265536);
            case COPPER -> ColorKey.ofRgb(-2790072);
            case IRON -> ColorKey.ofRgb(-3947581);
            case GOLDEN -> ColorKey.ofRgb(-1659115);
            case DIAMOND -> ColorKey.ofRgb(-11932970);
            case NETHERITE -> ColorKey.ofRgb(-11777972);
        };
    }
}
