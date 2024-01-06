package cookie.rollinghills;

import cookie.rollinghills.metastates.GlassMetaState;
import cookie.rollinghills.metastates.GrassMetaState;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import useless.dragonfly.helper.ModelHelper;
import useless.dragonfly.model.block.BlockModelDragonFly;

import static cookie.rollinghills.RollingHills.MOD_ID;
import static cookie.rollinghills.RollingHillsConfig.cfg;

public class RollingHillsBlockModels {

	public static void initializeModels() {
		if (cfg.getBoolean("Rolling Hills.BetterGrass")) {
			BlockModelDispatcher.getInstance().addDispatch(
				Block.grass,
				new BlockModelDragonFly(ModelHelper.getOrCreateBlockModel(MOD_ID, "block/grass_item.json"),
					ModelHelper.getOrCreateBlockState(MOD_ID, "grass.json"),
					new GrassMetaState(), true, 0.25f)
			);
		}

		if (cfg.getBoolean("Rolling Hills.ConnectedGlass")) {
			BlockModelDispatcher.getInstance().addDispatch(
				Block.glass,
				new BlockModelDragonFly(ModelHelper.getOrCreateBlockModel(MOD_ID, "block/glass_item.json"),
					ModelHelper.getOrCreateBlockState(MOD_ID, "glass.json"),
					new GlassMetaState(), true, 0.25f)
			);
		}
	}
}
