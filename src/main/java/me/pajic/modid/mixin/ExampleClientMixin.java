package me.pajic.modid.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.modid.ModTemplate;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public class ExampleClientMixin {

	@Inject(
			method = "onGameLoadFinished",
			at = @At("TAIL")
	)
	private void onGameLoadFinished(CallbackInfo ci) {
		ModTemplate.debugLog("Client finished loading!");
	}
}
