package cookie.rollinghills;

import cookie.rollinghills.client.metastates.GrassMetaState;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import org.useless.dragonfly.helper.ModelHelper;
import org.useless.dragonfly.model.block.BlockModelDragonFly;

import static cookie.rollinghills.RollingHills.MOD_ID;
import static cookie.rollinghills.client.RollingHillsConfig.cfg;

public class RollingHillsBlockModels {

	public static void initializeModels() {
		if (cfg.getBoolean("Rolling Hills.BetterGrass")) {
			BlockModelDragonFly model = new BlockModelDragonFly(Block.grass,
				ModelHelper.getOrCreateBlockModel(MOD_ID, "block/grass_item.json"),
				ModelHelper.getOrCreateBlockState(MOD_ID, "grass.json"),
				new GrassMetaState(),
				true,
				0.25f
				);

			BlockModelDispatcher.getInstance().addDispatch(
				Block.grass,
				model
			);
		}
	}
}
