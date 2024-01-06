package cookie.rollinghills.metastates;

import net.minecraft.core.block.Block;
import net.minecraft.core.world.WorldSource;
import useless.dragonfly.model.blockstates.processed.MetaStateInterpreter;

import java.util.HashMap;

public class GlassMetaState extends MetaStateInterpreter {

	private boolean nextToGlass(WorldSource worldSource, int x, int y, int z) {
		Block block = worldSource.getBlock(x, y, z);
		return block != null && block == Block.glass;
	}

	@Override
	public HashMap<String, String> getStateMap(WorldSource worldSource, int x, int y, int z, Block block, int meta) {
		boolean up = nextToGlass(worldSource, x, y + 1, z);
		boolean down = nextToGlass(worldSource, x, y - 1, z);
		boolean north = nextToGlass(worldSource, x, y, z + 1);
		boolean south = nextToGlass(worldSource, x, y, z - 1);
		boolean east = nextToGlass(worldSource, x - 1, y, z);
		boolean west = nextToGlass(worldSource, x + 1, y, z);

		HashMap<String, String> result = new HashMap<>();
		result.put("up", up ? "true" : "false");
		result.put("down", down ? "true" : "false");
		result.put("north", north ? "true" : "false");
		result.put("south", south ? "true" : "false");
		result.put("east", east ? "true" : "false");
		result.put("west", west ? "true" : "false");
		return result;
	}
}
