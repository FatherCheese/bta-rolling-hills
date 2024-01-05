package cookie.rollinghills;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPhotoMode;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.Tessellator;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;

public class RenderTwoClouds {
	private static final Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
	private static final RenderEngine renderEngine = new RenderEngine(mc, mc.texturePackList, mc.gameSettings);
	private static final World worldObj = mc.theWorld;

	private static final float lastCloudOffsetX = 0.0F;
	private static final float lastCloudOffsetZ = 0.0F;
	private static final float cloudOffsetX = 0.0F;
	private static float cloudOffsetZ;
	private static float cloudVelocityX;
	private static float cloudVelocityZ;

	public static void renderCloudsFancy(float partialTick) {
		GL11.glDisable(2884);
		float cameraY = (float) mc.activeCamera.getY(partialTick);
		Tessellator tessellator = Tessellator.instance;
		float cloudWidth = 12.0F;
		float cloudThickness = 4.0F;
		double dx = (
			mc.activeCamera.getX(partialTick) + (double)((lastCloudOffsetX + (cloudOffsetX - lastCloudOffsetX) * partialTick) * 0.03F)
		)
			/ (double)cloudWidth;
		double dz = (
			mc.activeCamera.getZ(partialTick)
				+ (double)((lastCloudOffsetZ + (cloudOffsetZ - lastCloudOffsetZ) * partialTick) * 0.03F)
		)
			/ (double)cloudWidth
			+ 0.33;
		float dy = worldObj.worldType.getCloudHeight() - cameraY + 0.33F + 4.0F;
		int i = MathHelper.floor_double(dx / 2048.0);
		int j = MathHelper.floor_double(dz / 2048.0);
		dx -= i * 2048;
		dz -= j * 2048;
		GL11.glBindTexture(3553, renderEngine.getTexture("/assets/rollinghills/environment/clouds2.png"));
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		Vec3d color = worldObj.getDimensionColor(partialTick);
		float red = (float)color.xCoord;
		float green = (float)color.yCoord;
		float blue = (float)color.zCoord;
		float f9 = (float)(dx * 0.0);
		float f11 = (float)(dz * 0.0);
		float f13 = 0.00390625F;
		f9 = (float)MathHelper.floor_double(dx) * f13;
		f11 = (float)MathHelper.floor_double(dz) * f13;
		float f14 = (float)(dx - (double)MathHelper.floor_double(dx));
		float f15 = (float)(dz - (double)MathHelper.floor_double(dz));
		int cloudWidthScale = 8;
		byte radius = 3;
		float f16 = 9.765625E-4F;
		GL11.glScalef(cloudWidth, 1.0F, cloudWidth);

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
						tessellator.setColorRGBA_F(red * 0.7F, green * 0.7F, blue * 0.7F, 0.8F);
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
						tessellator.setColorRGBA_F(red, green, blue, 0.8F);
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

					tessellator.setColorRGBA_F(red * 0.9F, green * 0.9F, blue * 0.9F, 0.8F);
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

					tessellator.setColorRGBA_F(red * 0.8F, green * 0.8F, blue * 0.8F, 0.8F);
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

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(3042);
		GL11.glEnable(2884);
	}

	public static void renderClouds(float partialTick) {
		if (mc.theWorld.dimension != Dimension.nether) {
			if (!(mc.currentScreen instanceof GuiPhotoMode)) {
				if (mc.gameSettings.fancyGraphics.value == 1) {
					renderCloudsFancy(partialTick);
				} else {
					GL11.glDisable(2884);
					float cameraY = (float) mc.activeCamera.getY(partialTick);
					byte cloudRadius = 32;
					int i = 256 / cloudRadius;
					Tessellator tessellator = Tessellator.instance;
					GL11.glBindTexture(3553, renderEngine.getTexture("/assets/rollinghills/environment/clouds2.png"));					GL11.glEnable(3042);
					GL11.glBlendFunc(770, 771);
					Vec3d dimensionColor = worldObj.getDimensionColor(partialTick);
					float r = (float)dimensionColor.xCoord;
					float g = (float)dimensionColor.yCoord;
					float b = (float)dimensionColor.zCoord;
					float f6 = 4.8828125E-4F;
					double posX = mc.activeCamera.getX(partialTick)
						+ (double)((lastCloudOffsetX + (cloudOffsetX - lastCloudOffsetX) * partialTick) * 0.03F);
					double posZ = mc.activeCamera.getZ(partialTick)
						+ (double)((lastCloudOffsetZ + (cloudOffsetZ - lastCloudOffsetZ) * partialTick) * 0.03F);
					int j = MathHelper.floor_double(posX / 2048.0);
					int k = MathHelper.floor_double(posZ / 2048.0);
					posX -= j * 2048;
					posZ -= k * 2048;
					float cloudHeight = worldObj.worldType.getCloudHeight() - cameraY + 0.33F + 4.0F;
					float f10 = (float)(posX * (double)f6);
					float f11 = (float)(posZ * (double)f6);
					tessellator.startDrawingQuads();
					tessellator.setColorRGBA_F(r, g, b, 0.8F);

					for(int cloudX = -cloudRadius * i; cloudX < cloudRadius * i; cloudX += cloudRadius) {
						for(int cloudZ = -cloudRadius * i; cloudZ < cloudRadius * i; cloudZ += cloudRadius) {
							tessellator.addVertexWithUV(
                                    cloudX,
                                    cloudHeight,
                                    cloudZ + cloudRadius,
                                    (float)(cloudX) * f6 + f10,
                                    (float)(cloudZ + cloudRadius) * f6 + f11
							);
							tessellator.addVertexWithUV(
                                    cloudX + cloudRadius,
                                    cloudHeight,
                                    cloudZ + cloudRadius,
                                    (float)(cloudX + cloudRadius) * f6 + f10,
                                    (float)(cloudZ + cloudRadius) * f6 + f11
							);
							tessellator.addVertexWithUV(
                                    cloudX + cloudRadius,
                                    cloudHeight,
                                    cloudZ,
                                    (float)(cloudX + cloudRadius) * f6 + f10,
                                    (float)(cloudZ) * f6 + f11
							);
							tessellator.addVertexWithUV(
                                    cloudX,
                                    cloudHeight,
                                    cloudZ,
                                    (float)(cloudX) * f6 + f10,
                                    (float)(cloudZ) * f6 + f11
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
	}
}
