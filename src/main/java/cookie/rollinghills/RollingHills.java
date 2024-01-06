package cookie.rollinghills;

import cookie.rollinghills.metastates.GlassMetaState;
import cookie.rollinghills.metastates.GrassMetaState;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import useless.dragonfly.helper.ModelHelper;
import useless.dragonfly.model.block.BlockModelDragonFly;

import static cookie.rollinghills.RollingHillsConfig.cfg;


public class RollingHills implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "rollinghills";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

		new RollingHillsConfig();
		RollingHillsBlockModels.initializeModels();
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
