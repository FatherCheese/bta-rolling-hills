package cookie.rollinghills.mixin;

import cookie.rollinghills.RenderTwoClouds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FogManager;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldRenderer.class, remap = false)
public abstract class WorldRendererMixin {

	@Shadow
	private Minecraft mc;

	@Shadow
	private FogManager fogManager;

	@Shadow
	private float farPlaneDistance;

	@Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderGlobal;renderClouds(F)V", shift = At.Shift.AFTER))
	private void rollingHills_renderSecondClouds(float partialTick, long updateRenderersUntil, CallbackInfo ci) {
			RenderTwoClouds.renderClouds(partialTick);
	}
}
