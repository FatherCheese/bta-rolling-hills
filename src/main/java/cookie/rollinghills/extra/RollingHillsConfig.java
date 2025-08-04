package cookie.rollinghills.extra;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import java.io.File;
import java.io.IOException;

import static cookie.rollinghills.core.RollingHills.MOD_ID;

public class RollingHillsConfig {
	private static final Toml PROPERTIES = new Toml("Rolling Hill's TOML Config");
	public static TomlConfigHandler cfg;

	static {
		PROPERTIES.addCategory("Rolling Hills")
			.addEntry("BetterGrass", true)
			.addEntry("SecondCloudLayer", true);

		cfg = new TomlConfigHandler(MOD_ID, PROPERTIES);

		File configFile = cfg.getConfigFile();
		if (cfg.getConfigFile().exists()) {
			cfg.loadConfig();
			cfg.setDefaults(cfg.getRawParsed());
		} else {
			cfg.setDefaults(PROPERTIES);
			try {
				//noinspection ResultOfMethodCallIgnored
				configFile.getParentFile().mkdirs();

				//noinspection ResultOfMethodCallIgnored
				configFile.createNewFile();
				cfg.writeConfig();
				cfg.loadConfig();
			} catch (IOException e) {
				throw new RuntimeException("Failed to generate config!", e);
			}
		}
	}
}
