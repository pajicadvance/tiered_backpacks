package me.pajic.tiered_backpacks.tooltip;

import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BackpackTooltipCompat implements ShulkerBoxTooltipApi {

    @Override
    public void registerProviders(@NotNull PreviewProviderRegistry registry) {
        Set<Item> backpacks = new HashSet<>();
        BuiltInRegistries.ITEM.getTagOrEmpty(BackpackUtil.BACKPACKS).forEach(item -> backpacks.add(item.value()));
        registry.register(TieredBackpacks.id("backpack_tooltip"), new BackpackPreviewProvider(), backpacks);
    }
}
