package cookie.rollinghills.client;

import cookie.rollinghills.client.block.model.BlockModelHillGrass;
import cookie.rollinghills.extra.IRemoveDispatch;
import cookie.rollinghills.extra.RollingHillsConfig;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.helper.Side;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

public class RollingHillsModels implements ModelEntrypoint {
	private static final Side[] S_TB;
	private static final Side[] S_SIDES;

	@Override
	public void initBlockModels(BlockModelDispatcher blockModelDispatcher) {
		if (RollingHillsConfig.cfg.getBoolean("Rolling Hills.BetterGrass")) {
			((IRemoveDispatch) blockModelDispatcher).bta_rolling_hills$removeDispatch(Blocks.GRASS);
			((IRemoveDispatch) blockModelDispatcher).bta_rolling_hills$removeDispatch(Blocks.GRASS_RETRO);

			ModelHelper.setBlockModel(Blocks.GRASS, () -> {
				BlockModelStandard<?> model = new BlockModelHillGrass<>(Blocks.GRASS);
				model.setTex(BlockModelStandard.BLOCK_TEXTURES, "minecraft:block/grass/side", S_SIDES);
				model.setTex(BlockModelStandard.BLOCK_TEXTURES, "minecraft:block/grass/top", Side.TOP);
				model.setTex(BlockModelStandard.BLOCK_TEXTURES, "minecraft:block/grass/bottom", Side.BOTTOM);

				return model;
			});

			ModelHelper.setBlockModel(Blocks.GRASS_RETRO, () -> {
				BlockModelStandard<?> model = new BlockModelHillGrass<>(Blocks.GRASS_RETRO);
				model.setTex(BlockModelStandard.BLOCK_TEXTURES, "minecraft:block/grass_retro/side", S_SIDES);
				model.setTex(BlockModelStandard.BLOCK_TEXTURES, "minecraft:block/grass_retro/top", Side.TOP);
				model.setTex(BlockModelStandard.BLOCK_TEXTURES, "minecraft:block/grass_retro/bottom", Side.BOTTOM);

				return model;
			});
		}
	}

	@Override
	public void initItemModels(ItemModelDispatcher itemModelDispatcher) {

	}

	@Override
	public void initEntityModels(EntityRenderDispatcher entityRenderDispatcher) {

	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher tileEntityRenderDispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher blockColorDispatcher) {

	}

	static {
		S_TB = new Side[]{Side.TOP, Side.BOTTOM};
		S_SIDES = new Side[]{Side.NORTH, Side.EAST, Side.SOUTH, Side.WEST};
	}
}
