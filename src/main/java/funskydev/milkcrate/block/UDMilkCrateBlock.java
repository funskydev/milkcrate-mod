package funskydev.milkcrate.block;

import java.util.Random;

import funskydev.milkcrate.init.BlocksManager;
import funskydev.milkcrate.util.CustomDamageSrc;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class UDMilkCrateBlock extends TransparentBlock {
	
	public static final BooleanProperty STACKED = BooleanProperty.of("stacked");
	
	public static final VoxelShape shape1 = VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.9375f, 0.5f, 0.9375f);
	public static final VoxelShape shape2 = VoxelShapes.cuboid(0.0625f, 0f, 0.0625f, 0.9375f, 1f, 0.9375f);
	
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
				
				if(!player.isCreative()) is.decrement(1);
				
				world.setBlockState(pos, state.with(STACKED, true));
				player.playSound(SoundEvents.BLOCK_WOOD_PLACE, 1, 1);
		        
				 
		        return ActionResult.SUCCESS;
				
			}
			
		}
		
		return ActionResult.PASS;
		
    }
	
	@Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        
		if(world.isClient) return;
		
		if(!(entity instanceof LivingEntity)) return;
		
		if(entity.isSprinting() || new Random().nextInt(15) == 1) {
			
			BlockState state = world.getBlockState(pos);
			
			if(state.get(STACKED).booleanValue()) {
				world.setBlockState(pos, state.with(STACKED, false));
				ItemEntity item = (ItemEntity) EntityType.ITEM.create(world);
				item.setStack(new ItemStack(Item.fromBlock(BlocksManager.UD_MILKCRATE)));
				item.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
				world.spawnEntity(item);
			} else {
				world.breakBlock(pos, true);
			}
			
			if(entity.isSprinting()) entity.damage(new CustomDamageSrc("milkcratesprint"), new Random().nextInt(18));
			else entity.damage(new CustomDamageSrc("milkcrate"), new Random().nextInt(10));
			
		}
		
        super.onSteppedOn(world, pos, entity);
        
    }
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
		if (state.get(STACKED).booleanValue()) return shape2;
		else return shape1;
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
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return sideCoversSmallSquare(world, pos.down(), Direction.UP);
	}
	
}
