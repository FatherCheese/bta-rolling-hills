package cookie.rollinghills;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class RollingHills implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "rollinghills";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
		LOGGER.info("Rolling Hills has been initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		RollingHillsBlockModels.initializeModels();
	}
}
