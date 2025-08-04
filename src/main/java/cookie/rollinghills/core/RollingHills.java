package cookie.rollinghills.core;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RollingHills implements ModInitializer {
    public static final String MOD_ID = "rollinghills";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
		LOGGER.info("Rolling Hills has been initialized.");
    }
}
