package funskydev.milkcrate.block;

import funskydev.milkcrate.init.BlocksManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class UDMilkCrateBlock extends TransparentBlock {
	
	public static final BooleanProperty STACKED = BooleanProperty.of("stacked");
	
	public UDMilkCrateBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(STACKED, false));
	}
	
	@Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(STACKED);
    }
	
	@Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        
		if(hit.getSide() == Direction.UP && state.get(STACKED).booleanValue() == false) {
			
			ItemStack is = player.getMainHandStack();
			
			if(is.getItem() == Item.fromBlock(BlocksManager.UD_MILKCRATE)) {
				
				if(!player.isCreative()) {
					if(is.getCount() < 2) {
						is.decrement(1);
					} else {
						is.decrement(1);
					}
				}
				
				world.setBlockState(pos, state.with(STACKED, true));
				player.playSound(SoundEvents.BLOCK_WOOD_PLACE, 1, 1);
		        
				 
		        return ActionResult.SUCCESS;
				
			}
			
		}
		
		return ActionResult.PASS;
		
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		if (state.get(STACKED).booleanValue() == false) return VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.9375f, 0.5f, 0.9375f);
		else return VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.9375f, 1f, 0.9375f);
	}
	 
	@Environment(EnvType.CLIENT)
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0F;
	}

	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
	
}
