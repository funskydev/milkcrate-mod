package funskydev.milkcrate.init;

import funskydev.milkcrate.Main;
import funskydev.milkcrate.block.MilkCrateBlock;
import funskydev.milkcrate.block.UDMilkCrateBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlocksManager {
	
	public static final Block MILKCRATE = new MilkCrateBlock(FabricBlockSettings.of(Material.WOOD).strength(0.06f));
	
	public static final Block UD_MILKCRATE = new UDMilkCrateBlock(FabricBlockSettings.of(Material.WOOD).strength(0.06f));
	
	public static void init() {
		
		register(MILKCRATE, "milkcrate", ItemGroup.DECORATIONS);
		register(UD_MILKCRATE, "upsidedown_milkcrate", ItemGroup.DECORATIONS);
		
	}
	
	private static void register(Block block, String name, ItemGroup group) {
		
		Registry.register(Registry.BLOCK, new Identifier(Main.MODID, name), block);
		Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), new BlockItem(block, new FabricItemSettings().group(group)));
			
	}
	
}
