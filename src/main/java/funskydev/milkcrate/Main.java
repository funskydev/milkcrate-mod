package funskydev.milkcrate;

import funskydev.milkcrate.init.BlocksManager;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {

	public static final String MODID = "milkcrate";
	
	@Override
	public void onInitialize() {
		
		BlocksManager.init();
		
	}

}
