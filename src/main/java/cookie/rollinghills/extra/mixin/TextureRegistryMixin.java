package cookie.rollinghills.extra.mixin;

import cookie.rollinghills.RollingHills;
import net.minecraft.client.render.stitcher.AtlasStitcher;
import net.minecraft.client.render.stitcher.TextureRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

import static net.minecraft.client.render.stitcher.TextureRegistry.artAtlas;
import static net.minecraft.client.render.stitcher.TextureRegistry.initializeAllFiles;

@Mixin(value = TextureRegistry.class, remap = false)
public abstract class TextureRegistryMixin {

	@Shadow
	public static HashMap<String, AtlasStitcher> stitcherMap;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void rollinghills_registerTextures(CallbackInfo ci) {
		try {
			for (AtlasStitcher stitcher : stitcherMap.values()) {
				initializeAllFiles(RollingHills.MOD_ID, stitcher, stitcher != artAtlas);
			}
		} catch (Exception e) {
			RollingHills.LOGGER.error(e.getMessage(), e);
		}
	}
}
