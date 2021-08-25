package funskydev.milkcrate.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MilkCrateBlock extends TransparentBlock {
	
	public MilkCrateBlock(Settings settings) {
		super(settings);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.9375f, 0.5f, 0.9375f);
	}
	 
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0F;
	}

	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
}
