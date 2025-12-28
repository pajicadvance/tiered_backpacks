package me.pajic.tiered_backpacks.platform.fabric;

//? fabric {

import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.component.ModDataComponents;
import me.pajic.tiered_backpacks.item.BackpackAccessory;
import me.pajic.tiered_backpacks.item.ModItems;
import me.pajic.tiered_backpacks.menu.ModMenuTypes;
import me.pajic.tiered_backpacks.network.ModNetworking;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import me.pajic.tiered_backpacks.util.CompatFlags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

import java.util.Map;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		ModDataComponents.init();
		ModItems.init();
		ModMenuTypes.init();
		ModNetworking.init();
		initRegistry();
		CauldronInteraction.WATER.map().putAll(Map.of(
				ModItems.LEATHER_BACKPACK, CauldronInteraction::dyedItemIteration,
				ModItems.COPPER_BACKPACK, CauldronInteraction::dyedItemIteration,
				ModItems.IRON_BACKPACK, CauldronInteraction::dyedItemIteration,
				ModItems.GOLDEN_BACKPACK, CauldronInteraction::dyedItemIteration,
				ModItems.DIAMOND_BACKPACK, CauldronInteraction::dyedItemIteration)
		);
		initConditionalCommonResources();
		if (CompatFlags.ACCESSORIES_LOADED) initBackpackAccessory();
		initCreativeTabs();
		initNetworking();
	}

	private void initConditionalCommonResources() {
		FabricLoader.getInstance().getModContainer(TieredBackpacks.MOD_ID).ifPresent(modContainer -> {
			String pack = switch (TieredBackpacks.CONFIG.accessorySlot.get()) {
				case BACK -> "default";
				case BACKPACK -> "unique";
			};
			ResourceManagerHelper.registerBuiltinResourcePack(
					TieredBackpacks.id(pack),
					modContainer,
					ResourcePackActivationType.ALWAYS_ENABLED
			);
		});
	}

	private void initBackpackAccessory() {
		if (CompatFlags.ACCESSORIES_LOADED) CommonLifecycleEvents.TAGS_LOADED.register((registries, client) ->
				BackpackAccessory.initBackpackAccessory(registries.lookupOrThrow(Registries.ITEM))
		);
	}

	private void initNetworking() {
		PayloadTypeRegistry.playC2S().register(ModNetworking.C2SOpenBackpackPayload.TYPE, ModNetworking.C2SOpenBackpackPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(
				ModNetworking.C2SOpenBackpackPayload.TYPE,
				(payload, context) ->
						BackpackUtil.tryOpenBackpack(payload.openMethod(), context.player())
		);
	}

	private void initRegistry() {
		Registry.register(
				BuiltInRegistries.DATA_COMPONENT_TYPE,
				TieredBackpacks.id("tier"),
				ModDataComponents.BACKPACK_TIER
		);
		Registry.register(
				BuiltInRegistries.MENU,
				TieredBackpacks.id("backpack_menu"),
				ModMenuTypes.BACKPACK_MENU
		);
		Registry.register(
				BuiltInRegistries.ITEM,
				ResourceKey.create(Registries.ITEM, TieredBackpacks.id("leather_backpack")),
				ModItems.LEATHER_BACKPACK
		);
		Registry.register(
				BuiltInRegistries.ITEM,
				ResourceKey.create(Registries.ITEM, TieredBackpacks.id("copper_backpack")),
				ModItems.COPPER_BACKPACK
		);
		Registry.register(
				BuiltInRegistries.ITEM,
				ResourceKey.create(Registries.ITEM, TieredBackpacks.id("iron_backpack")),
				ModItems.IRON_BACKPACK
		);
		Registry.register(
				BuiltInRegistries.ITEM,
				ResourceKey.create(Registries.ITEM, TieredBackpacks.id("golden_backpack")),
				ModItems.GOLDEN_BACKPACK
		);
		Registry.register(
				BuiltInRegistries.ITEM,
				ResourceKey.create(Registries.ITEM, TieredBackpacks.id("diamond_backpack")),
				ModItems.DIAMOND_BACKPACK
		);
		Registry.register(
				BuiltInRegistries.ITEM,
				ResourceKey.create(Registries.ITEM, TieredBackpacks.id("netherite_backpack")),
				ModItems.NETHERITE_BACKPACK
		);
	}

	private void initCreativeTabs() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries ->
				entries.addBefore(Items.COMPASS,
						ModItems.LEATHER_BACKPACK,
						ModItems.COPPER_BACKPACK,
						ModItems.IRON_BACKPACK,
						ModItems.GOLDEN_BACKPACK,
						ModItems.DIAMOND_BACKPACK,
						ModItems.NETHERITE_BACKPACK
				)
		);
	}
}
//?}
