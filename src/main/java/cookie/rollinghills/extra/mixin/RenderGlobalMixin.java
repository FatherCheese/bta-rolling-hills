package cookie.rollinghills.extra.mixin;

import cookie.rollinghills.extra.TwoClouds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenPhotoMode;
import net.minecraft.client.render.RenderGlobal;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.worldtype.WorldTypeFX;
import net.minecraft.client.render.worldtype.WorldTypeFXDispatcher;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(value = RenderGlobal.class, remap = false)
public abstract class RenderGlobalMixin implements TwoClouds {

	@Shadow
	private Minecraft mc;

	@Shadow
	@Final
	private TextureManager textureManager;
	@Shadow
	private WorldClient worldObj;
	@Unique
	private float lastCloudOffset2X;

	@Unique
	private float lastCloudOffset2Z;

	@Unique
	private float cloudVelocity2X;

	@Unique
	private float cloudVelocity2Z;

	@Unique
	private float cloudOffset2X;

	@Unique
	private float cloudOffset2Z;

	@Override
	public void bta_rolling_hills$renderSecondClouds(float partialTick) {
		WorldTypeFX worldTypeFX = WorldTypeFXDispatcher.getInstance().getDispatch(mc.currentWorld.getWorldType());

		if (worldTypeFX.hasClouds() && !(mc.currentScreen instanceof ScreenPhotoMode)) {
			if (mc.gameSettings.fancyGraphics.value == 1) bta_rolling_hills$renderSecondCloudsFancy(partialTick);
			else {
				GL11.glDisable(2884);
				float cameraY = (float) mc.activeCamera.getY(partialTick);
				byte cloudRadius = 32;
				int i = 256 / cloudRadius;
				net.minecraft.client.render.tessellator.Tessellator tessellator = net.minecraft.client.render.tessellator.Tessellator.instance;
				textureManager.bindTexture(textureManager.loadTexture("/assets/rollinghills/textures/environment/clouds2.png"));
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				Vec3 dimensionColor = worldObj.getDimensionColor(mc.activeCamera, partialTick);
				float r = (float) dimensionColor.x;
				float g = (float) dimensionColor.y;
				float b = (float) dimensionColor.z;
				float f6 = 4.8828125E-4F;
				double posX = mc.activeCamera.getX(partialTick)
					+ (double) ((lastCloudOffset2X + (cloudOffset2X - lastCloudOffset2X) * partialTick) * 0.03F);
				double posZ = mc.activeCamera.getZ(partialTick)
					+ (double) ((lastCloudOffset2Z + (cloudOffset2Z - lastCloudOffset2Z) * partialTick) * 0.03F);
				int j = MathHelper.floor(posX / 2048.0);
				int k = MathHelper.floor(posZ / 2048.0);
				posX -= j * 2048;
				posZ -= k * 2048;
				float cloudHeight = worldTypeFX.getCloudHeight() - cameraY + 0.33F + 4.0F;
				float f10 = (float) (posX * (double) f6);
				float f11 = (float) (posZ * (double) f6);
				tessellator.startDrawingQuads();
				tessellator.setColorRGBA_F(r, g, b, 0.6F);

				for (int cloudX = -cloudRadius * i; cloudX < cloudRadius * i; cloudX += cloudRadius) {
					for (int cloudZ = -cloudRadius * i; cloudZ < cloudRadius * i; cloudZ += cloudRadius) {
						tessellator.addVertexWithUV(
							cloudX,
							cloudHeight,
							cloudZ + cloudRadius,
							(float) (cloudX) * f6 + f10,
							(float) (cloudZ + cloudRadius) * f6 + f11
						);
						tessellator.addVertexWithUV(
							cloudX + cloudRadius,
							cloudHeight,
							cloudZ + cloudRadius,
							(float) (cloudX + cloudRadius) * f6 + f10,
							(float) (cloudZ + cloudRadius) * f6 + f11
						);
						tessellator.addVertexWithUV(
							cloudX + cloudRadius,
							cloudHeight,
							cloudZ,
							(float) (cloudX + cloudRadius) * f6 + f10,
							(float) (cloudZ) * f6 + f11
						);
						tessellator.addVertexWithUV(
							cloudX,
							cloudHeight,
							cloudZ,
							(float) (cloudX) * f6 + f10,
							(float) (cloudZ) * f6 + f11
						);
					}
				}

				tessellator.draw();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(3042);
				GL11.glEnable(2884);
			}
		}
	}

	@Override
	public void bta_rolling_hills$renderSecondCloudsFancy(float partialTick) {
		WorldTypeFX worldTypeFX = WorldTypeFXDispatcher.getInstance().getDispatch(mc.currentWorld.getWorldType());

		GL11.glDisable(2884);
		float cameraY = (float) mc.activeCamera.getY(partialTick);
		net.minecraft.client.render.tessellator.Tessellator tessellator = net.minecraft.client.render.tessellator.Tessellator.instance;
		float cloudWidth = 12.0F;
		float cloudThickness = 4.0F;
		double dx = (
			mc.activeCamera.getX(partialTick) + (double)((lastCloudOffset2X + (cloudOffset2X - lastCloudOffset2X) * partialTick) * 0.03F)
		)
			/ (double)cloudWidth;
		double dz = (
			mc.activeCamera.getZ(partialTick)
				+ (double)((lastCloudOffset2Z + (cloudOffset2Z - lastCloudOffset2Z) * partialTick) * 0.03F)
		)
			/ (double)cloudWidth
			+ 0.33;
		float dy = worldTypeFX.getCloudHeight() - cameraY + 0.33F + 4.0F;
		int i = MathHelper.floor(dx / 2048.0);
		int j = MathHelper.floor(dz / 2048.0);
		dx -= i * 2048;
		dz -= j * 2048;
		textureManager.bindTexture(textureManager.loadTexture("/assets/rollinghills/textures/environment/clouds2.png"));
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		Vec3 color = worldObj.getDimensionColor(mc.activeCamera, partialTick);
		float red = (float)color.x;
		float green = (float)color.y;
		float blue = (float)color.z;
		float f9;
		float f11;
		float f13 = 0.00390625F;
		f9 = (float)MathHelper.floor(dx) * f13;
		f11 = (float)MathHelper.floor(dz) * f13;
		float f14 = (float)(dx - (double)MathHelper.floor(dx));
		float f15 = (float)(dz - (double)MathHelper.floor(dz));
		int cloudWidthScale = 8;
		byte radius = 3;
		float f16 = 9.765625E-4F;

		for(int l = 0; l < 2; ++l) {
			if (l == 0) {
				GL11.glColorMask(false, false, false, false);
			} else {
				GL11.glColorMask(true, true, true, true);
			}

			tessellator.startDrawingQuads();

			for(int ix = -radius + 1; ix <= radius; ++ix) {
				for(int iz = -radius + 1; iz <= radius; ++iz) {
					float f17 = (float)(ix * cloudWidthScale);
					float f18 = (float)(iz * cloudWidthScale);
					float cloudX = f17 - f14;
					float cloudZ = f18 - f15;
					if (dy > -cloudThickness - 1.0F) {
						tessellator.setColorRGBA_F(red * 0.7F, green * 0.7F, blue * 0.7F, 0.6F);
						tessellator.setNormal(0.0F, -1.0F, 0.0F);
						tessellator.addVertexWithUV(
							cloudX + 0.0F,
							dy + 0.0F,
							cloudZ + (float)cloudWidthScale,
							(f17 + 0.0F) * f13 + f9,
							(f18 + (float)cloudWidthScale) * f13 + f11
						);
						tessellator.addVertexWithUV(
							cloudX + (float)cloudWidthScale,
							dy + 0.0F,
							cloudZ + (float)cloudWidthScale,
							(f17 + (float)cloudWidthScale) * f13 + f9,
							(f18 + (float)cloudWidthScale) * f13 + f11
						);
						tessellator.addVertexWithUV(
							cloudX + (float)cloudWidthScale,
							dy + 0.0F,
							cloudZ + 0.0F,
							(f17 + (float)cloudWidthScale) * f13 + f9,
							(f18 + 0.0F) * f13 + f11
						);
						tessellator.addVertexWithUV(
							cloudX + 0.0F,
							dy + 0.0F,
							cloudZ + 0.0F,
							(f17 + 0.0F) * f13 + f9,
							(f18 + 0.0F) * f13 + f11
						);
					}

					if (dy <= cloudThickness + 1.0F) {
						tessellator.setColorRGBA_F(red, green, blue, 0.6F);
						tessellator.setNormal(0.0F, 1.0F, 0.0F);
						tessellator.addVertexWithUV(
							cloudX + 0.0F,
							dy + cloudThickness - f16,
							cloudZ + (float)cloudWidthScale,
							(f17 + 0.0F) * f13 + f9,
							(f18 + (float)cloudWidthScale) * f13 + f11
						);
						tessellator.addVertexWithUV(
							cloudX + (float)cloudWidthScale,
							dy + cloudThickness - f16,
							cloudZ + (float)cloudWidthScale,
							(f17 + (float)cloudWidthScale) * f13 + f9,
							(f18 + (float)cloudWidthScale) * f13 + f11
						);
						tessellator.addVertexWithUV(
							cloudX + (float)cloudWidthScale,
							dy + cloudThickness - f16,
							cloudZ + 0.0F,
							(f17 + (float)cloudWidthScale) * f13 + f9,
							(f18 + 0.0F) * f13 + f11
						);
						tessellator.addVertexWithUV(
							cloudX + 0.0F,
							dy + cloudThickness - f16,
							cloudZ + 0.0F,
							(f17 + 0.0F) * f13 + f9,
							(f18 + 0.0F) * f13 + f11
						);
					}

					tessellator.setColorRGBA_F(red * 0.9F, green * 0.9F, blue * 0.9F, 0.6F);
					if (ix > -1) {
						tessellator.setNormal(-1.0F, 0.0F, 0.0F);

						for(int k1 = 0; k1 < cloudWidthScale; ++k1) {
							tessellator.addVertexWithUV(
								cloudX + (float)k1 + 0.0F,
								dy + 0.0F,
								cloudZ + (float)cloudWidthScale,
								(f17 + (float)k1 + 0.5F) * f13 + f9,
								(f18 + (float)cloudWidthScale) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)k1 + 0.0F,
								dy + cloudThickness,
								cloudZ + (float)cloudWidthScale,
								(f17 + (float)k1 + 0.5F) * f13 + f9,
								(f18 + (float)cloudWidthScale) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)k1 + 0.0F,
								dy + cloudThickness,
								cloudZ + 0.0F,
								(f17 + (float)k1 + 0.5F) * f13 + f9,
								(f18 + 0.0F) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)k1 + 0.0F,
								dy + 0.0F,
								cloudZ + 0.0F,
								(f17 + (float)k1 + 0.5F) * f13 + f9,
								(f18 + 0.0F) * f13 + f11
							);
						}
					}

					if (ix <= 1) {
						tessellator.setNormal(1.0F, 0.0F, 0.0F);

						for(int l1 = 0; l1 < cloudWidthScale; ++l1) {
							tessellator.addVertexWithUV(
								cloudX + (float)l1 + 1.0F - f16,
								dy + 0.0F,
								cloudZ + (float)cloudWidthScale,
								(f17 + (float)l1 + 0.5F) * f13 + f9,
								(f18 + (float)cloudWidthScale) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)l1 + 1.0F - f16,
								dy + cloudThickness,
								cloudZ + (float)cloudWidthScale,
								(f17 + (float)l1 + 0.5F) * f13 + f9,
								(f18 + (float)cloudWidthScale) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)l1 + 1.0F - f16,
								dy + cloudThickness,
								cloudZ + 0.0F,
								(f17 + (float)l1 + 0.5F) * f13 + f9,
								(f18 + 0.0F) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)l1 + 1.0F - f16,
								dy + 0.0F,
								cloudZ + 0.0F,
								(f17 + (float)l1 + 0.5F) * f13 + f9,
								(f18 + 0.0F) * f13 + f11
							);
						}
					}

					tessellator.setColorRGBA_F(red * 0.8F, green * 0.8F, blue * 0.8F, 0.6F);
					if (iz > -1) {
						tessellator.setNormal(0.0F, 0.0F, -1.0F);

						for(int i2 = 0; i2 < cloudWidthScale; ++i2) {
							tessellator.addVertexWithUV(
								cloudX + 0.0F,
								dy + cloudThickness,
								cloudZ + (float)i2 + 0.0F,
								(f17 + 0.0F) * f13 + f9,
								(f18 + (float)i2 + 0.5F) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)cloudWidthScale,
								dy + cloudThickness,
								cloudZ + (float)i2 + 0.0F,
								(f17 + (float)cloudWidthScale) * f13 + f9,
								(f18 + (float)i2 + 0.5F) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)cloudWidthScale,
								dy + 0.0F,
								cloudZ + (float)i2 + 0.0F,
								(f17 + (float)cloudWidthScale) * f13 + f9,
								(f18 + (float)i2 + 0.5F) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + 0.0F,
								dy + 0.0F,
								cloudZ + (float)i2 + 0.0F,
								(f17 + 0.0F) * f13 + f9,
								(f18 + (float)i2 + 0.5F) * f13 + f11
							);
						}
					}

					if (iz <= 1) {
						tessellator.setNormal(0.0F, 0.0F, 1.0F);

						for(int j2 = 0; j2 < cloudWidthScale; ++j2) {
							tessellator.addVertexWithUV(
								cloudX + 0.0F,
								dy + cloudThickness,
								cloudZ + (float)j2 + 1.0F - f16,
								(f17 + 0.0F) * f13 + f9,
								(f18 + (float)j2 + 0.5F) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)cloudWidthScale,
								dy + cloudThickness,
								cloudZ + (float)j2 + 1.0F - f16,
								(f17 + (float)cloudWidthScale) * f13 + f9,
								(f18 + (float)j2 + 0.5F) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + (float)cloudWidthScale,
								dy + 0.0F,
								cloudZ + (float)j2 + 1.0F - f16,
								(f17 + (float)cloudWidthScale) * f13 + f9,
								(f18 + (float)j2 + 0.5F) * f13 + f11
							);
							tessellator.addVertexWithUV(
								cloudX + 0.0F,
								dy + 0.0F,
								cloudZ + (float)j2 + 1.0F - f16,
								(f17 + 0.0F) * f13 + f9,
								(f18 + (float)j2 + 0.5F) * f13 + f11
							);
						}
					}
				}
			}

			tessellator.draw();
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
		GL11.glDisable(3042);
		GL11.glEnable(2884);
	}

	@Override
	public void bta_rolling_hills$updateSecondClouds() {
		float windDirection = worldObj.worldType.getWindManager().getWindDirection(worldObj, 0.0F, 500.0F, 0.0F);
		float windIntensity = worldObj.worldType.getWindManager().getWindIntensity(worldObj, 0.0F, 500.0F, 0.0F);
		float dx = -((float)(Math.cos((double)windDirection * Math.PI * 2.0) * (double)windIntensity));
		float dz = -((float)(Math.sin((double)windDirection * Math.PI * 2.0) * (double)windIntensity));
		cloudVelocity2X += dx;
		cloudVelocity2Z += dz;
		float maxVel = 0.75F;
		if (cloudVelocity2X > maxVel) {
			cloudVelocity2X = maxVel;
		}

		if (cloudVelocity2X < -maxVel) {
			cloudVelocity2X = -maxVel;
		}

		if (cloudVelocity2Z > maxVel) {
			cloudVelocity2Z = maxVel;
		}

		if (cloudVelocity2Z < -maxVel) {
			cloudVelocity2Z = -maxVel;
		}

		lastCloudOffset2X = cloudOffset2X;
		lastCloudOffset2Z = cloudOffset2Z;
		cloudOffset2X += cloudVelocity2X;
		cloudOffset2Z += cloudVelocity2Z;
	}
}
