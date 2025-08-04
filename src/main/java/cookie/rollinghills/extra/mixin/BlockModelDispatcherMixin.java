package cookie.rollinghills.extra.mixin;

import cookie.rollinghills.extra.IRemoveDispatch;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.util.dispatch.Dispatcher;
import net.minecraft.core.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(value = BlockModelDispatcher.class, remap = false)
public abstract class BlockModelDispatcherMixin extends Dispatcher<Block<?>, BlockModel<?>> implements IRemoveDispatch<Block<?>> {
	@Override
	public void bta_rolling_hills$removeDispatch(Block<?> key) {
		dispatches.remove(key);
	}
}
