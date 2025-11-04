package me.pajic.unsophisticated_backpacks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.unsophisticated_backpacks.item.BackpackAccessory;
import me.pajic.unsophisticated_backpacks.item.ModItems;
import me.pajic.unsophisticated_backpacks.network.ModNetworking;
import me.pajic.unsophisticated_backpacks.ui.BackpackMenu;
import me.pajic.unsophisticated_backpacks.util.BackpackDimensions;
import me.pajic.unsophisticated_backpacks.util.BackpackTier;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Main implements ModInitializer {
    public static final String MOD_ID = "unsophisticated_backpacks";
    public static final ResourceLocation CONFIG_RL = withModNamespace("config");
    public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);
    public static final boolean ACCESSORIES_LOADED = FabricLoader.getInstance().isModLoaded("accessories");

    public static final DataComponentType<BackpackTier> BACKPACK_TIER = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            withModNamespace("tier"),
            DataComponentType.<BackpackTier>builder().persistent(StringRepresentable.fromEnum(BackpackTier::values)).build()
    );
    public static final MenuType<BackpackMenu> BACKPACK_MENU = new ExtendedScreenHandlerType<>(
            BackpackMenu::new, ModNetworking.S2CBackpackScreenPayload.CODEC
    );

    @Override
    public void onInitialize() {
        ModItems.init();
        ModNetworking.init();
        BackpackAccessory.init();
        Registry.register(BuiltInRegistries.MENU, withModNamespace("backpack_menu"), BACKPACK_MENU);
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
            String pack = switch (CONFIG.accessorySlot.get()) {
                case BACK -> "default";
                case BACKPACK -> "unique";
            };
            ResourceManagerHelper.registerBuiltinResourcePack(
                    withModNamespace(pack),
                    modContainer,
                    ResourcePackActivationType.ALWAYS_ENABLED
            );
        });
    }

    @SuppressWarnings("resource")
    public static InteractionResult openBackpackScreen(Player player, ItemStack backpack) {
        if (!player.level().isClientSide()) {
            player.openMenu(new ExtendedScreenHandlerFactory<ModNetworking.S2CBackpackScreenPayload>() {
                @Override
                public @NotNull AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
                    return new BackpackMenu(i, inventory, backpack);
                }

                @Override
                public @NotNull Component getDisplayName() {
                    return backpack.getDisplayName();
                }

                @Override
                public ModNetworking.S2CBackpackScreenPayload getScreenOpeningData(ServerPlayer serverPlayer) {
                    return new ModNetworking.S2CBackpackScreenPayload(backpack);
                }
            });
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static BackpackDimensions getBackpackDimensions(ItemStack backpack) {
        return switch (backpack.getOrDefault(Main.BACKPACK_TIER, BackpackTier.LEATHER)) {
            case LEATHER -> Main.CONFIG.leatherSize.get();
            case COPPER -> Main.CONFIG.copperSize.get();
            case IRON -> Main.CONFIG.ironSize.get();
            case GOLDEN -> Main.CONFIG.goldenSize.get();
            case DIAMOND -> Main.CONFIG.diamondSize.get();
            case NETHERITE -> Main.CONFIG.netheriteSize.get();
        };
    }

    public static ResourceLocation withModNamespace(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
