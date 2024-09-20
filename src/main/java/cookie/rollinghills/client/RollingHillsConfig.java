package cookie.rollinghills.client;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static cookie.rollinghills.RollingHills.MOD_ID;

public class RollingHillsConfig {
	private static final Toml properties = new Toml("Rolling Hill's TOML Config");
	public static TomlConfigHandler cfg;

	static {
		properties.addCategory("Rolling Hills")
			.addEntry("BetterGrass", true)
			.addEntry("SecondCloudLayer", true);

		cfg = new TomlConfigHandler(MOD_ID, properties);
	}
}
