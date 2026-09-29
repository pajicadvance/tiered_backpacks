import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import me.pajic.tiered_backpacks.TieredBackpacks;
import me.pajic.tiered_backpacks.component.ModDataComponents;
import me.pajic.tiered_backpacks.item.BackpackItem;
import me.pajic.tiered_backpacks.tooltip.BackpackTooltipCompat;
import me.pajic.tiered_backpacks.util.BackpackTier;
import me.pajic.tiered_backpacks.util.BackpackUtil;
import net.minecraft.SharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.ItemContainerContents;
import java.lang.reflect.Proxy;
import java.util.*;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TooltipRegression {
    private static int checks;
    private static void check(boolean condition, String name) {
        if (!condition) throw new AssertionError(name);
        checks++;
    }
    private static ItemStack filled(Item item) {
        ItemStack stack = new ItemStack(item);
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(List.of(new ItemStack(Items.DIAMOND))));
        return stack;
    }
    private static PreviewContext context(ItemStack stack) {
        return PreviewContext.builder(stack).build();
    }
    public static void main(String[] args) throws Exception {
        SharedConstants.tryDetectVersion();
        // Headless bootstrap: insert mod registrations before the vanilla registry freeze.
        var bootstrapped = Bootstrap.class.getDeclaredField("isBootstrapped");
        bootstrapped.setAccessible(true);
        bootstrapped.setBoolean(null, true);
        Item ignored = Items.AIR;
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, TieredBackpacks.id("tier"), ModDataComponents.BACKPACK_TIER);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, TieredBackpacks.id("stored_dye"), ModDataComponents.STORED_BACKPACK_DYE);
        check(!TieredBackpacks.CONFIG.canEquipInChestSlot.get(), "backpacks need not be equippable");
        List<Item> backpacks = new ArrayList<>();
        for (BackpackTier tier : BackpackTier.values()) {
            Item item = new BackpackItem(tier);
            Registry.register(BuiltInRegistries.ITEM, TieredBackpacks.id(tier.getSerializedName() + "_backpack"), item);
            backpacks.add(item);
        }
        var armorId = TieredBackpacks.id("regression_chestplate");
        Item moddedArmor = new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, armorId)).equippable(EquipmentSlot.CHEST));
        Registry.register(BuiltInRegistries.ITEM, armorId, moddedArmor);
        BuiltInRegistries.bootStrap();
        var lookup = net.minecraft.data.registries.VanillaRegistries.createWorldLookup();
        BuiltInRegistries.DATA_COMPONENT_INITIALIZERS.build(lookup).forEach(net.minecraft.core.component.DataComponentInitializers.PendingComponents::apply);
        check(!BuiltInRegistries.ITEM.getTagOrEmpty(BackpackUtil.BACKPACKS).iterator().hasNext(), "backpack tags not loaded");
        check(!BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.CHEST_ARMOR).iterator().hasNext(), "armor tags not loaded");
        Map<Item, PreviewProvider> providers = new IdentityHashMap<>();
        PreviewProviderRegistry registry = (PreviewProviderRegistry) Proxy.newProxyInstance(
            PreviewProviderRegistry.class.getClassLoader(), new Class<?>[]{PreviewProviderRegistry.class},
            (proxy, method, arguments) -> {
                if (!method.getName().equals("register")) throw new AssertionError(method);
                PreviewProvider provider = (PreviewProvider) arguments[1];
                Iterable<Item> items = arguments[2] instanceof Item[] array ? Arrays.asList(array) : (Iterable<Item>) arguments[2];
                for (Item item : items) providers.put(item, provider);
                return null;
            });
        new BackpackTooltipCompat().registerProviders(registry);
        for (Item backpack : backpacks) {
            check(!backpack.components().has(DataComponents.EQUIPPABLE), "backpack has chest equipment disabled");
            check(providers.containsKey(backpack), "backpack registered before tags: " + backpack);
        }
        check(providers.containsKey(Items.DIAMOND_CHESTPLATE), "vanilla chest armor registered before tags");
        check(providers.containsKey(moddedArmor), "modded chest armor registered before tags");
        check(!providers.containsKey(Items.STONE), "unrelated item not claimed");
        check(!providers.containsKey(Items.SHULKER_BOX), "shulker box provider not claimed");
        Map<TagKey<Item>, List<Holder<Item>>> tags = new HashMap<>();
        tags.put(BackpackUtil.BACKPACKS, backpacks.stream().<Holder<Item>>map(Item::builtInRegistryHolder).toList());
        tags.put(ItemTags.CHEST_ARMOR, List.of(Items.DIAMOND_CHESTPLATE.builtInRegistryHolder(), moddedArmor.builtInRegistryHolder()));
        BuiltInRegistries.ITEM.prepareTagReload(new net.minecraft.tags.TagLoader.LoadResult<>(Registries.ITEM, tags)).apply();
        for (Item backpack : backpacks) {
            PreviewProvider provider = providers.get(backpack);
            check(provider.shouldDisplay(context(filled(backpack))), "filled backpack preview");
            check(!provider.shouldDisplay(context(new ItemStack(backpack))), "empty backpack hidden");
        }
        for (Item armor : List.of(Items.DIAMOND_CHESTPLATE, moddedArmor)) {
            PreviewProvider provider = providers.get(armor);
            check(!provider.shouldDisplay(context(filled(armor))), "container without attached backpack hidden");
            ItemStack attached = filled(armor);
            attached.set(ModDataComponents.BACKPACK_TIER, BackpackTier.DIAMOND);
            check(provider.shouldDisplay(context(attached)), "attached armor preview");
            check(provider.getInventoryMaxSize(context(attached)) == 66, "attached backpack dimensions");
            check(provider.getMaxRowSize(context(attached)) == 11, "attached backpack row size");
            attached.set(ModDataComponents.STORED_BACKPACK_DYE, new DyedItemColor(0x123456));
            check(provider.getWindowColorKey(context(attached)).rgb() == 0x123456, "attached backpack stored dye");
            attached.set(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            check(!provider.shouldDisplay(context(attached)), "empty attached backpack hidden");
        }
        System.out.println("PASS: " + checks + " tooltip registration and preview assertions");
    }
}
