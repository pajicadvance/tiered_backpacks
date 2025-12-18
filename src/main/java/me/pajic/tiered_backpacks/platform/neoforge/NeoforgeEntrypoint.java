package me.pajic.tiered_backpacks.platform.neoforge;

//? neoforge {

/*import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.component.ModDataComponents;
import me.pajic.tiered_backpacks.item.BackpackAccessory;
import me.pajic.tiered_backpacks.item.ModItems;
import me.pajic.tiered_backpacks.menu.ModMenuTypes;
import me.pajic.tiered_backpacks.network.ModNetworking;
import me.pajic.tiered_backpacks.tooltip.PreviewExtensionPoint;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import me.pajic.tiered_backpacks.util.CompatFlags;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;

@Mod(TieredBackpacks.MOD_ID)
@EventBusSubscriber(modid = TieredBackpacks.MOD_ID)
public class NeoforgeEntrypoint {

	@SubscribeEvent
	private static void onCommonSetup(FMLCommonSetupEvent event) {
		TieredBackpacks.onInitialize();
		if (CompatFlags.SHULKER_BOX_TOOLTIP_LOADED) PreviewExtensionPoint.register();
	}

	@SubscribeEvent
	private static void initConditionalCommonResources(AddPackFindersEvent event) {
		if (CompatFlags.ACCESSORIES_LOADED) {
			String pack = switch (TieredBackpacks.CONFIG.accessorySlot.get()) {
				case BACK -> "default";
				case BACKPACK -> "unique";
			};
			event.addPackFinders(
					TieredBackpacks.id("resourcepacks/" + pack),
					PackType.SERVER_DATA,
					Component.literal("Tiered Backpacks Accessory Slot"),
					PackSource.BUILT_IN,
					true,
					Pack.Position.TOP
			);
		}
	}

	@SubscribeEvent
	private static void initRegistry(RegisterEvent event) {
		ModDataComponents.init();
		ModItems.init();
		ModMenuTypes.init();
		ModNetworking.init();
		event.register(
				Registries.DATA_COMPONENT_TYPE,
				registry -> registry.register(TieredBackpacks.id("tier"), ModDataComponents.BACKPACK_TIER)
		);
		event.register(
				Registries.MENU,
				registry -> registry.register(TieredBackpacks.id("backpack_menu"), ModMenuTypes.BACKPACK_MENU)
		);
		event.register(
				Registries.ITEM,
				registry -> {
					registry.register(TieredBackpacks.id("leather_backpack"), ModItems.LEATHER_BACKPACK);
					registry.register(TieredBackpacks.id("copper_backpack"), ModItems.COPPER_BACKPACK);
					registry.register(TieredBackpacks.id("iron_backpack"), ModItems.IRON_BACKPACK);
					registry.register(TieredBackpacks.id("golden_backpack"), ModItems.GOLDEN_BACKPACK);
					registry.register(TieredBackpacks.id("diamond_backpack"), ModItems.DIAMOND_BACKPACK);
					registry.register(TieredBackpacks.id("netherite_backpack"), ModItems.NETHERITE_BACKPACK);
				}
		);
		CauldronInteraction.WATER.map().putAll(Map.of(
				ModItems.LEATHER_BACKPACK, CauldronInteraction::dyedItemIteration,
				ModItems.COPPER_BACKPACK, CauldronInteraction::dyedItemIteration,
				ModItems.IRON_BACKPACK, CauldronInteraction::dyedItemIteration,
				ModItems.GOLDEN_BACKPACK, CauldronInteraction::dyedItemIteration,
				ModItems.DIAMOND_BACKPACK, CauldronInteraction::dyedItemIteration)
		);
	}

	@SubscribeEvent
	private static void initCreativeTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.insertBefore(
					Items.COMPASS.getDefaultInstance(),
					ModItems.NETHERITE_BACKPACK.getDefaultInstance(),
					CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
			);
			event.insertBefore(
					ModItems.NETHERITE_BACKPACK.getDefaultInstance(),
					ModItems.DIAMOND_BACKPACK.getDefaultInstance(),
					CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
			);
			event.insertBefore(
					ModItems.DIAMOND_BACKPACK.getDefaultInstance(),
					ModItems.GOLDEN_BACKPACK.getDefaultInstance(),
					CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
			);
			event.insertBefore(
					ModItems.GOLDEN_BACKPACK.getDefaultInstance(),
					ModItems.IRON_BACKPACK.getDefaultInstance(),
					CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
			);
			event.insertBefore(
					ModItems.IRON_BACKPACK.getDefaultInstance(),
					ModItems.COPPER_BACKPACK.getDefaultInstance(),
					CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
			);
			event.insertBefore(
					ModItems.COPPER_BACKPACK.getDefaultInstance(),
					ModItems.LEATHER_BACKPACK.getDefaultInstance(),
					CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
			);
		}
	}

	@SubscribeEvent
	private static void initBackpackAccessory(TagsUpdatedEvent event) {
		if (CompatFlags.ACCESSORIES_LOADED) BackpackAccessory.initBackpackAccessory(
				event.getLookupProvider().lookupOrThrow(Registries.ITEM)
		);
	}

	@SubscribeEvent
	private static void initNetworking(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar("1");
		registrar.playToServer(
				ModNetworking.C2SOpenBackpackPayload.TYPE,
				ModNetworking.C2SOpenBackpackPayload.CODEC,
				(payload, context) ->
						BackpackUtil.tryOpenBackpack(payload.openMethod(), (ServerPlayer) context.player())
		);
		registrar.playToClient(
				ModNetworking.S2CBackpackScreenPayload.TYPE,
				ModNetworking.S2CBackpackScreenPayload.CODEC,
				(payload, context) -> {}
		);
	}
}
*///?}
