package cookie.rollinghills.client;

import cookie.rollinghills.core.RollingHills;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class RollingHillsClient implements ClientModInitializer {
	public static final String MOD_ID = "rollinghills|client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		try {
			TextureRegistry.initializeAllFiles(RollingHills.MOD_ID, TextureRegistry.blockAtlas, true);
		} catch (URISyntaxException | IOException e) {
			LOGGER.error("Failed to initialize Rolling Hills textures!", e);
			throw new RuntimeException(e);
		}

		LOGGER.info("Rolling Hills Client has been initialized.");
	}
}
