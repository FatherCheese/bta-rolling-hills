package cookie.rollinghills.client.block.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class BlockModelHillGrass<T extends BlockLogic> extends BlockModelStandard<T> {
	public static boolean useOverlay = false;
	private final IconCoordinate[] overlayTextures = new IconCoordinate[4];
	private final IconCoordinate[] sideTextures = new IconCoordinate[4];
	private final IconCoordinate[] snowyTextures = new IconCoordinate[4];
	private final IconCoordinate[] topTextures = new IconCoordinate[3];
	private final IconCoordinate[] bottomTextures = new IconCoordinate[2];

	public BlockModelHillGrass(Block<T> block) {
		super(block);

		useOverlay = RenderBlocks.fancyGrass;

		sideTextures[0] = TextureRegistry.getTexture("minecraft:block/grass/side");
		sideTextures[1] = TextureRegistry.getTexture("rollinghills:block/grass/side/side_left");
		sideTextures[2] = TextureRegistry.getTexture("rollinghills:block/grass/side/side_left_right");
		sideTextures[3] = TextureRegistry.getTexture("rollinghills:block/grass/side/side_right");

		overlayTextures[0] = TextureRegistry.getTexture("minecraft:block/grass/side_overlay");
		overlayTextures[1] = TextureRegistry.getTexture("rollinghills:block/grass/overlay/overlay_left");
		overlayTextures[2] = TextureRegistry.getTexture("rollinghills:block/grass/overlay/overlay_left_right");
		overlayTextures[3] = TextureRegistry.getTexture("rollinghills:block/grass/overlay/overlay_right");

		snowyTextures[0] = TextureRegistry.getTexture("minecraft:block/grass/snowy_side");
		snowyTextures[1] = TextureRegistry.getTexture("rollinghills:block/grass/snowy/side_left");
		snowyTextures[2] = TextureRegistry.getTexture("rollinghills:block/grass/snowy/side_left_right");
		snowyTextures[3] = TextureRegistry.getTexture("rollinghills:block/grass/snowy/side_right");

		topTextures[0] = TextureRegistry.getTexture("minecraft:block/grass/top");
		topTextures[1] = TextureRegistry.getTexture("minecraft:block/block_snow");
		topTextures[2] = TextureRegistry.getTexture("minecraft:block/grass_retro/top");

		bottomTextures[0] = TextureRegistry.getTexture("minecraft:block/grass/bottom");
		bottomTextures[1] = TextureRegistry.getTexture("minecraft:block/grass_retro/bottom");
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {
		AABB bounds = block.getBounds();
		boolean didRender = isRetro() ? renderStandardBlock(tessellator, bounds, x, y, z, 1.0F, 1.0F, 1.0F) : renderStandardBlock(tessellator, bounds, x, y, z);

		if (RenderBlocks.fancyGrass && (retroBlockTextures.hasTexture() || !isRetro())) {
			useOverlay = true;
			didRender |= renderStandardBlock(tessellator, bounds, x, y, z);
			useOverlay = false;
		}

		return didRender;
	}

	@Override
	public IconCoordinate getBlockTexture(WorldSource blockAccess, int x, int y, int z, Side side) {
		boolean thisSnowy = isBlockSnow(blockAccess, x, y, z);
		boolean northSnowy = isBlockSnow(blockAccess, x, y - 1, z - 1);
		boolean southSnowy = isBlockSnow(blockAccess, x, y - 1, z + 1);
		boolean eastSnowy = isBlockSnow(blockAccess, x + 1, y - 1, z);
		boolean westSnowy = isBlockSnow(blockAccess, x - 1, y - 1, z);

		boolean northGrass = isBlockGrass(blockAccess, x, y - 1, z - 1);
		boolean southGrass = isBlockGrass(blockAccess, x, y - 1, z + 1);
		boolean eastGrass = isBlockGrass(blockAccess, x + 1, y - 1, z);
		boolean westGrass = isBlockGrass(blockAccess, x - 1, y - 1, z);

		boolean adjacentNorthEastGrass = isBlockGrass(blockAccess, x, y, z - 1) && isBlockGrass(blockAccess, x + 1, y - 1, z - 1);
		boolean adjacentSouthEastGrass = isBlockGrass(blockAccess, x, y, z + 1) && isBlockGrass(blockAccess, x + 1, y - 1, z + 1);
		boolean adjacentNorthWestGrass = isBlockGrass(blockAccess, x, y, z - 1) && isBlockGrass(blockAccess, x - 1, y - 1, z - 1);
		boolean adjacentSouthWestGrass = isBlockGrass(blockAccess, x, y, z + 1) && isBlockGrass(blockAccess, x - 1, y - 1, z + 1);

		boolean northIsSolid = sideIsSolid(blockAccess, x, y, z, Side.NORTH);
		boolean southIsSolid = sideIsSolid(blockAccess, x, y, z, Side.SOUTH);
		boolean eastIsSolid = sideIsSolid(blockAccess, x, y, z, Side.EAST);
		boolean westIsSolid = sideIsSolid(blockAccess, x, y, z, Side.WEST);

		if (useOverlay) {
			switch (side) {
				case TOP:
					return topTextures[thisSnowy ? 1 : 0];
				case BOTTOM:
					return bottomTextures[0];
				case NORTH:
					if (northGrass) return topTextures[thisSnowy && northSnowy ? 1 : 0];
					if (eastIsSolid || westIsSolid) {
						if (eastIsSolid && westIsSolid) {
							if (thisSnowy) return snowyTextures[2];
							return overlayTextures[2];
						} else if (eastIsSolid) {
							if (thisSnowy) return snowyTextures[1];
							return overlayTextures[1];
						} else {
							if (thisSnowy) return snowyTextures[3];
							return overlayTextures[3];
						}
					}

					return (thisSnowy) ? snowyTextures[0] : overlayTextures[0];
				case EAST:
					if (eastGrass) return thisSnowy && eastSnowy ? topTextures[1] : topTextures[0];
					if (northIsSolid || southIsSolid) {
						if (northIsSolid && southIsSolid) {
							if (thisSnowy) return snowyTextures[2];
							return overlayTextures[2];
						} else if (northIsSolid) {
							if (thisSnowy) return snowyTextures[3];
							return overlayTextures[3];
						} else {
							if (thisSnowy) return snowyTextures[1];
							return overlayTextures[1];
						}
					}

					return (thisSnowy) ? snowyTextures[0] : overlayTextures[0];
				case SOUTH:
					if (southGrass) return thisSnowy && southSnowy ? topTextures[1] : topTextures[0];
					if (eastIsSolid || westIsSolid) {
						if (eastIsSolid && westIsSolid) {
							if (thisSnowy) return snowyTextures[2];
							return overlayTextures[2];
						} else if (eastIsSolid) {
							if (thisSnowy) return snowyTextures[3];
							return overlayTextures[3];
						} else {
							if (thisSnowy) return snowyTextures[1];
							return overlayTextures[1];
						}
					}

					return (thisSnowy) ? snowyTextures[0] : overlayTextures[0];
				case WEST:
					if (westGrass) return thisSnowy && westSnowy ? topTextures[1] : topTextures[0];
					if (northIsSolid || southIsSolid) {
						if (northIsSolid && southIsSolid) {
							if (thisSnowy) return snowyTextures[2];
							return overlayTextures[2];
						} else if (northIsSolid) {
							if (thisSnowy) return snowyTextures[1];
							return overlayTextures[1];
						} else {
							if (thisSnowy) return snowyTextures[3];
							return overlayTextures[3];
						}
					}

					return (thisSnowy) ? snowyTextures[0] : overlayTextures[0];
			}
		} else {
			switch (side) {
				case TOP:
					if (thisSnowy) return topTextures[1];
					return topTextures[isRetro() ? 2 : 0];
				case BOTTOM:
					return bottomTextures[isRetro() ? 1 : 0];
				case NORTH:
					if (northGrass) {
						if (thisSnowy) return topTextures[1];
						return topTextures[isRetro() ? 2 : 0];
					}

					if (eastIsSolid || westIsSolid) {
						if (eastIsSolid && westIsSolid) {
							if (thisSnowy) return snowyTextures[2];
							return sideTextures[2];
						} else if (eastIsSolid) {
							if (thisSnowy) return snowyTextures[1];
							return sideTextures[1];
						} else {
							if (thisSnowy) return snowyTextures[3];
							return sideTextures[3];
						}
					}

					return thisSnowy ? snowyTextures[0] : sideTextures[0];
				case EAST:
					if (eastGrass) {
						if (thisSnowy) return topTextures[1];
						return topTextures[isRetro() ? 2 : 0];
					}

					if (northIsSolid || southIsSolid) {
						if (northIsSolid && southIsSolid) {
							if (thisSnowy) return snowyTextures[2];
							return sideTextures[2];
						} else if (northIsSolid) {
							if (thisSnowy) return snowyTextures[3];
							return sideTextures[3];
						} else {
							if (thisSnowy) return snowyTextures[1];
							return sideTextures[1];
						}
					}

					return thisSnowy ? snowyTextures[0] : sideTextures[0];
				case SOUTH:
					if (southGrass) {
						if (thisSnowy) return topTextures[1];
						return topTextures[isRetro() ? 2 : 0];
					}

					if (eastIsSolid || westIsSolid) {
						if (eastIsSolid && westIsSolid) {
							if (thisSnowy) return snowyTextures[2];
							return sideTextures[2];
						} else if (eastIsSolid) {
							if (thisSnowy) return snowyTextures[3];
							return sideTextures[3];
						} else {
							if (thisSnowy) return snowyTextures[1];
							return sideTextures[1];
						}
					}

					return thisSnowy ? snowyTextures[0] : sideTextures[0];
				case WEST:
					if (westGrass) {
						if (thisSnowy) return topTextures[1];
						return topTextures[isRetro() ? 2 : 0];
					}
					if (northIsSolid || southIsSolid) {
						if (northIsSolid && southIsSolid) {
							if (thisSnowy) return snowyTextures[2];
							return sideTextures[2];
						} else if (northIsSolid) {
							if (thisSnowy) return snowyTextures[1];
							return sideTextures[1];
						} else {
							if (thisSnowy) return snowyTextures[3];
							return sideTextures[3];
						}
					}

					return thisSnowy ? snowyTextures[0] : sideTextures[0];
			}
		}

		return null;
	}

	private boolean isBlockGrass(@NotNull WorldSource worldSource, int x, int y, int z) {
		Block<?> blockAbove = worldSource.getBlock(x, y + 1, z);
		if (blockAbove != null && blockAbove.isSolidRender()) return false;

		Block<?> block = worldSource.getBlock(x, y, z);
		return block != null && block.getMaterial() == Material.grass;
	}

	private boolean isBlockSnow(@NotNull WorldSource worldSource, int x, int y, int z) {
		Block<?> block = worldSource.getBlock(x, y + 1, z);
		return block != null && block == Blocks.LAYER_SNOW || block == Blocks.BLOCK_SNOW;
	}

	private boolean sideIsSolid(WorldSource worldSource, int x, int y, int z, @NotNull Side side) {
		int adjX = x;
		int adjZ = z;

		switch(side) {
			case NORTH: adjZ--; break;
			case SOUTH: adjZ++; break;
			case EAST: adjX++; break;
			case WEST: adjX--; break;
		}

		return isBlockGrass(worldSource, adjX, y - 1, adjZ) && !isBlockSnow(worldSource, adjX, y, adjZ);
	}

	@Override
	public void renderBlockOnInventory(@NotNull Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
		GL11.glColor4f(brightness, brightness, brightness, alpha);
		float yOffset = 0.5F;
		AABB bounds = this.getBlockBoundsForItemRender();
		GL11.glTranslatef(-0.5F, 0.0F - yOffset, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		this.renderBottomFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.BOTTOM, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		this.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.NORTH, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		this.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.SOUTH, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		this.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.WEST, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		this.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.EAST, metadata));
		tessellator.draw();
		if (renderBlocks.useInventoryTint && !this.isRetro()) {
			int l = BlockColorDispatcher.getInstance().getDispatch(this.block).getFallbackColor(metadata);
			float f4 = (float)(l >> 16 & 255) / 255.0F;
			float f8 = (float)(l >> 8 & 255) / 255.0F;
			float f9 = (float)(l & 255) / 255.0F;
			GL11.glColor4f(f4 * brightness, f8 * brightness, f9 * brightness, alpha);
		}

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		this.renderTopFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.TOP, metadata));
		tessellator.draw();
		if (RenderBlocks.fancyGrass && !this.isRetro()) {
			useOverlay = true;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			this.renderNorthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.NORTH, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			this.renderSouthFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.SOUTH, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			this.renderWestFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.WEST, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			this.renderEastFace(tessellator, bounds, 0.0F, 0.0F, 0.0F, this.getBlockTextureFromSideAndMetadata(Side.EAST, metadata));
			tessellator.draw();
			useOverlay = false;
		}

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	public IconCoordinate getBlockTextureFromSideAndMetadata(Side side, int data) {
		return useOverlay ? overlayTextures[0] : super.getBlockTextureFromSideAndMetadata(side, data);
	}

	@Override
	public boolean shouldSideBeColored(@NotNull WorldSource blockAccess, int x, int y, int z, int side, int meta) {
		if (!useOverlay) return false;
		if (isRetro()) return false;

		boolean thisSnowy = isBlockSnow(blockAccess, x, y, z);
		if (!thisSnowy) return useOverlay || side == Side.TOP.getId();

		boolean north = isBlockSnow(blockAccess, x, y - 1, z - 1);
		boolean south = isBlockSnow(blockAccess, x, y - 1, z + 1);
		boolean east = isBlockSnow(blockAccess, x + 1, y - 1, z);
		boolean west = isBlockSnow(blockAccess, x - 1, y - 1, z);

		boolean northGrass = isBlockGrass(blockAccess, x, y - 1, z - 1);
		boolean southGrass = isBlockGrass(blockAccess, x, y - 1, z + 1);
		boolean eastGrass = isBlockGrass(blockAccess, x + 1, y - 1, z);
		boolean westGrass = isBlockGrass(blockAccess, x - 1, y - 1, z);

		if (side == Side.NORTH.getId() && !northGrass) return false;
		if (side == Side.SOUTH.getId() && !southGrass) return false;
		if (side == Side.EAST.getId() && !eastGrass) return false;
		if (side == Side.WEST.getId() && !westGrass) return false;

		if (side == Side.NORTH.getId() && !north) return true;
		if (side == Side.SOUTH.getId() && !south) return true;
		if (side == Side.EAST.getId() && !east) return true;
		return side == Side.WEST.getId() && !west;
	}

	@Override
	public boolean isRetro() {
		return super.isRetro() || block == Blocks.GRASS_RETRO;
	}
}
