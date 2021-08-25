package funskydev.milkcrate.client;

import funskydev.milkcrate.init.BlocksManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		
		BlockRenderLayerMapImpl.INSTANCE.putBlocks(RenderLayer.getCutout(), BlocksManager.MILKCRATE, BlocksManager.UD_MILKCRATE);
		
	}

}
