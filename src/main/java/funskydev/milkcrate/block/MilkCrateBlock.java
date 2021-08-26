package funskydev.milkcrate.block;

import java.util.stream.Stream;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MilkCrateBlock extends TransparentBlock {
	
	public static final IntProperty MILK = IntProperty.of("milk", 0, 4);
	
	public static final VoxelShape shape = Stream.of(
			Block.createCuboidShape(1, 0, 1, 15, 1, 15),
			Block.createCuboidShape(1, 1, 1, 3, 8, 15),
			Block.createCuboidShape(13, 1, 1, 15, 8, 15),
			Block.createCuboidShape(3, 1, 1, 13, 8, 3),
			Block.createCuboidShape(3, 1, 13, 13, 8, 15)
			).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
	
	public MilkCrateBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(MILK, 0));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(MILK);
    }
	
	@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        
		int milks = state.get(MILK).intValue();
		ItemStack is = player.getMainHandStack();
		
		if(is.getItem() == Items.MILK_BUCKET && milks < 4) {
			
			if(!player.isCreative()) is.decrement(1);
			world.setBlockState(pos, state.with(MILK, milks + 1));
			player.playSound(SoundEvents.BLOCK_WOOD_PLACE, 1, 1);
			
			return ActionResult.SUCCESS;
			
		} else if(is.getItem() == Items.AIR  && milks > 0) {
			
			player.giveItemStack(new ItemStack(Items.MILK_BUCKET));
			world.setBlockState(pos, state.with(MILK, milks - 1));
			
			return ActionResult.SUCCESS;
			
		}
		
		return ActionResult.PASS;
		
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		return shape;
	}
	
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0F;
	}

	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
	@Override
	public BlockSoundGroup getSoundGroup(BlockState state) {
		return BlockSoundGroup.WOOD;
	}
	
}
