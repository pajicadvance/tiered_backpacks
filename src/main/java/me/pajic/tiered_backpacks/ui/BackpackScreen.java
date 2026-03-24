package me.pajic.tiered_backpacks.ui;

import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.util.BackpackDimensions;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class BackpackScreen extends AbstractContainerScreen<BackpackMenu> {

    private static final Identifier GUI_TEXTURE = TieredBackpacks.id("backpack");
    private static final Identifier SLOT_TEXTURE = Identifier.withDefaultNamespace("container/slot");
    private final int rows;
    private final int columns;

    public BackpackScreen(BackpackMenu menu, Inventory playerInventory, Component ignored) {
		BackpackDimensions dimensions = BackpackUtil.getBackpackDimensions(menu.getBackpack());
        super(menu, playerInventory, menu.getBackpack().getHoverName(), menu.getWidth(dimensions.columns.get()), menu.getHeight(dimensions.rows.get()));
        rows = dimensions.rows.get();
        columns = dimensions.columns.get();
        titleLabelY = 6;
        inventoryLabelY = imageHeight - 95;
    }

	@Override
	public void extractBackground(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		graphics.blitSprite(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, columns <= 9 ? 176 : 176 + (columns - 9) * 18, 115 + rows * 18);
		for (Slot slot : getMenu().slots) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, x + slot.x - 1, y + slot.y - 1, 18, 18);
		}
	}
}
