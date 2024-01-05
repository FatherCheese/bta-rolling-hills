package cookie.rollinghills;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import useless.dragonfly.helper.ModelHelper;
import useless.dragonfly.model.block.BlockModelDragonFly;


public class RollingHills implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "rollinghills";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
		BlockModelDispatcher.getInstance().addDispatch(
			Block.grass,
			new BlockModelDragonFly(ModelHelper.getOrCreateBlockModel(MOD_ID, "block/grass_item.json"),
				ModelHelper.getOrCreateBlockState(MOD_ID, "grass.json"),
				new GrassMetaState(), true, 0.25f)
		);

		LOGGER.info("Rolling Hills has been initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}
}
