package cookie.rollinghills.mixin;

import cookie.rollinghills.TwoClouds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cookie.rollinghills.RollingHillsConfig.cfg;

@Mixin(value = WorldRenderer.class, remap = false)
public abstract class WorldRendererMixin {

	@Shadow
	private Minecraft mc;

	@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderGlobal;renderClouds(F)V", shift = At.Shift.AFTER))
	private void rollingHills_renderSecondClouds(float partialTick, long updateRenderersUntil, CallbackInfo ci) {
		RenderGlobal renderGlobal = mc.renderGlobal;
		if (cfg.getBoolean("Rolling Hills.SecondCloudLayer")) {
			if (renderGlobal instanceof TwoClouds)
				((TwoClouds) renderGlobal).bta_rolling_hills$renderSecondClouds(partialTick);
		}
	}
}
