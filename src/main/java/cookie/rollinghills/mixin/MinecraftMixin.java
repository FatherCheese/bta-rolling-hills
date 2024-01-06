package cookie.rollinghills.mixin;

import cookie.rollinghills.TwoClouds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, remap = false)
public abstract class MinecraftMixin {

	@Shadow
	public volatile boolean isGamePaused;

	@Shadow
	public RenderGlobal renderGlobal;

	@Inject(method = "runTick", at = @At("TAIL"))
	private void rollingHills_cloudSpeed(CallbackInfo ci) {
		if (!isGamePaused && renderGlobal instanceof TwoClouds)
			((TwoClouds) renderGlobal).bta_rolling_hills$updateSecondClouds();
	}
}
