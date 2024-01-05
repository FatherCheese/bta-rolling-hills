package cookie.rollinghills;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.world.WorldSource;
import useless.dragonfly.model.blockstates.processed.MetaStateInterpreter;

import java.util.HashMap;

public class GrassMetaState extends MetaStateInterpreter {

	private boolean isBlockGrass(WorldSource worldSource, int x, int y, int z) {
		Block block = worldSource.getBlock(x, y, z);
		return block != null && block.blockMaterial == Material.grass;
	}

	private boolean isBlockSnow(WorldSource worldSource, int x, int y, int z) {
		Block block = worldSource.getBlock(x, y + 1, z);
		return block != null && block == Block.layerSnow;
	}

	@Override
	public HashMap<String, String> getStateMap(WorldSource worldSource, int x, int y, int z, Block block, int meta) {
		boolean snowy = isBlockSnow(worldSource, x, y, z);

		boolean north = isBlockGrass(worldSource, x, y - 1, z - 1);
		boolean south = isBlockGrass(worldSource, x, y - 1, z + 1);
		boolean east = isBlockGrass(worldSource, x + 1, y - 1, z);
		boolean west = isBlockGrass(worldSource, x - 1, y - 1, z);

		HashMap<String, String> result = new HashMap<>();
		result.put("snowy", snowy ? "true" : "false");
		result.put("north", north ? "true" : "false");
		result.put("east", east ? "true" : "false");
		result.put("south", south ? "true" : "false");
		result.put("west", west ? "true" : "false");
		return result;
	}
}
