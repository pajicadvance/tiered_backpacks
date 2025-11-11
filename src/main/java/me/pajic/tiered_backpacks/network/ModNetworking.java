package me.pajic.tiered_backpacks.network;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import me.pajic.tiered_backpacks.Main;
import me.pajic.tiered_backpacks.item.ModItems;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ModNetworking {
    public static final ResourceLocation OPEN_BACKPACK = Main.withModNamespace("open_backpack");
    public static final ResourceLocation BACKPACK_SCREEN = Main.withModNamespace("backpack_screen");

    public record C2SOpenBackpackPayload(int openMethod) implements CustomPacketPayload {
        public static final Type<C2SOpenBackpackPayload> TYPE = new Type<>(OPEN_BACKPACK);
        public static final StreamCodec<RegistryFriendlyByteBuf, C2SOpenBackpackPayload> CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, C2SOpenBackpackPayload::openMethod,
                C2SOpenBackpackPayload::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record S2CBackpackScreenPayload(ItemStack backpack) implements CustomPacketPayload {
        public static final Type<S2CBackpackScreenPayload> TYPE = new Type<>(BACKPACK_SCREEN);
        public static final StreamCodec<RegistryFriendlyByteBuf, S2CBackpackScreenPayload> CODEC = StreamCodec.composite(
                ItemStack.STREAM_CODEC, S2CBackpackScreenPayload::backpack,
                S2CBackpackScreenPayload::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void init() {
        PayloadTypeRegistry.playC2S().register(C2SOpenBackpackPayload.TYPE, C2SOpenBackpackPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(C2SOpenBackpackPayload.TYPE, (payload, context) -> {
            ItemStack backpack = ItemStack.EMPTY;
            switch (payload.openMethod) {
                case 0 -> backpack = context.player().getInventory().getNonEquipmentItems().stream()
                        .filter(stack -> stack.is(ModItems.BACKPACKS))
                        .findFirst().orElse(ItemStack.EMPTY);
                case 1 -> backpack = context.player().getItemBySlot(EquipmentSlot.CHEST);
                case 2 -> {
                    Optional<AccessoriesCapability> ac = AccessoriesCapability.getOptionally(context.player());
                    if (ac.isPresent() && ac.get().isEquipped(stack -> stack.is(ModItems.BACKPACKS))) {
                        SlotEntryReference itemRef = ac.get().getFirstEquipped(stack -> stack.is(ModItems.BACKPACKS));
                        if (itemRef != null) {
                            backpack = itemRef.stack();
                        }
                    }
                }
            }
            if (!backpack.isEmpty()) Main.openBackpackScreen(context.player(), backpack);
        });
    }
}
