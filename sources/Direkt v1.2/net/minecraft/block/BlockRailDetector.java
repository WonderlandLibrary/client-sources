package net.minecraft.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRailDetector extends BlockRailBase {
	public static final PropertyEnum<BlockRailBase.EnumRailDirection> SHAPE = PropertyEnum.<BlockRailBase.EnumRailDirection> create("shape", BlockRailBase.EnumRailDirection.class,
			new Predicate<BlockRailBase.EnumRailDirection>() {
				@Override
				public boolean apply(@Nullable BlockRailBase.EnumRailDirection p_apply_1_) {
					return (p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_EAST) && (p_apply_1_ != BlockRailBase.EnumRailDirection.NORTH_WEST)
							&& (p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_EAST) && (p_apply_1_ != BlockRailBase.EnumRailDirection.SOUTH_WEST);
				}
			});
	public static final PropertyBool POWERED = PropertyBool.create("powered");

	public BlockRailDetector() {
		super(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.valueOf(false)).withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
		this.setTickRandomly(true);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World worldIn) {
		return 20;
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this change based on its state.
	 */
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if (!worldIn.isRemote) {
			if (!state.getValue(POWERED).booleanValue()) {
				this.updatePoweredState(worldIn, pos, state);
			}
		}
	}

	/**
	 * Called randomly when setTickRandomly is set to true (used by e.g. crops to grow, etc.)
	 */
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote && state.getValue(POWERED).booleanValue()) {
			this.updatePoweredState(worldIn, pos, state);
		}
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED).booleanValue() ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return !blockState.getValue(POWERED).booleanValue() ? 0 : (side == EnumFacing.UP ? 15 : 0);
	}

	private void updatePoweredState(World worldIn, BlockPos pos, IBlockState state) {
		boolean flag = state.getValue(POWERED).booleanValue();
		boolean flag1 = false;
		List<EntityMinecart> list = this.<EntityMinecart> findMinecarts(worldIn, pos, EntityMinecart.class, new Predicate[0]);

		if (!list.isEmpty()) {
			flag1 = true;
		}

		if (flag1 && !flag) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(true)), 3);
			this.updateConnectedRails(worldIn, pos, state, true);
			worldIn.notifyNeighborsOfStateChange(pos, this);
			worldIn.notifyNeighborsOfStateChange(pos.down(), this);
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
		}

		if (!flag1 && flag) {
			worldIn.setBlockState(pos, state.withProperty(POWERED, Boolean.valueOf(false)), 3);
			this.updateConnectedRails(worldIn, pos, state, false);
			worldIn.notifyNeighborsOfStateChange(pos, this);
			worldIn.notifyNeighborsOfStateChange(pos.down(), this);
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
		}

		if (flag1) {
			worldIn.scheduleUpdate(new BlockPos(pos), this, this.tickRate(worldIn));
		}

		worldIn.updateComparatorOutputLevel(pos, this);
	}

	protected void updateConnectedRails(World worldIn, BlockPos pos, IBlockState state, boolean p_185592_4_) {
		BlockRailBase.Rail blockrailbase$rail = new BlockRailBase.Rail(worldIn, pos, state);

		for (BlockPos blockpos : blockrailbase$rail.getConnectedRails()) {
			IBlockState iblockstate = worldIn.getBlockState(blockpos);

			if (iblockstate != null) {
				iblockstate.func_189546_a(worldIn, blockpos, iblockstate.getBlock());
			}
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.updatePoweredState(worldIn, pos, state);
	}

	@Override
	public IProperty<BlockRailBase.EnumRailDirection> getShapeProperty() {
		return SHAPE;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		if (blockState.getValue(POWERED).booleanValue()) {
			List<EntityMinecartCommandBlock> list = this.<EntityMinecartCommandBlock> findMinecarts(worldIn, pos, EntityMinecartCommandBlock.class, new Predicate[0]);

			if (!list.isEmpty()) { return list.get(0).getCommandBlockLogic().getSuccessCount(); }

			List<EntityMinecart> list1 = this.<EntityMinecart> findMinecarts(worldIn, pos, EntityMinecart.class, new Predicate[] { EntitySelectors.HAS_INVENTORY });

			if (!list1.isEmpty()) { return Container.calcRedstoneFromInventory((IInventory) list1.get(0)); }
		}

		return 0;
	}

	protected <T extends EntityMinecart> List<T> findMinecarts(World worldIn, BlockPos pos, Class<T> clazz, Predicate<Entity>... filter) {
		AxisAlignedBB axisalignedbb = this.getDectectionBox(pos);
		return filter.length != 1 ? worldIn.getEntitiesWithinAABB(clazz, axisalignedbb) : worldIn.getEntitiesWithinAABB(clazz, axisalignedbb, filter[0]);
	}

	private AxisAlignedBB getDectectionBox(BlockPos pos) {
		float f = 0.2F;
		return new AxisAlignedBB(pos.getX() + 0.2F, pos.getY(), pos.getZ() + 0.2F, pos.getX() + 1 - 0.2F, pos.getY() + 1 - 0.2F, pos.getZ() + 1 - 0.2F);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta & 7)).withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(SHAPE).getMetadata();

		if (state.getValue(POWERED).booleanValue()) {
			i |= 8;
		}

		return i;
	}

	@Override
	@SuppressWarnings("incomplete-switch")

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case CLOCKWISE_180:
			switch (state.getValue(SHAPE)) {
			case ASCENDING_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);

			case ASCENDING_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);

			case ASCENDING_NORTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);

			case ASCENDING_SOUTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);

			case SOUTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);

			case SOUTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);

			case NORTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);

			case NORTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);
			}

		case COUNTERCLOCKWISE_90:
			switch (state.getValue(SHAPE)) {
			case ASCENDING_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);

			case ASCENDING_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);

			case ASCENDING_NORTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);

			case ASCENDING_SOUTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);

			case SOUTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);

			case SOUTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);

			case NORTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);

			case NORTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);

			case NORTH_SOUTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);

			case EAST_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
			}

		case CLOCKWISE_90:
			switch (state.getValue(SHAPE)) {
			case ASCENDING_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);

			case ASCENDING_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);

			case ASCENDING_NORTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);

			case ASCENDING_SOUTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);

			case SOUTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);

			case SOUTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);

			case NORTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);

			case NORTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);

			case NORTH_SOUTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);

			case EAST_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
			}

		default:
			return state;
		}
	}

	@Override
	@SuppressWarnings("incomplete-switch")

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate.
	 */
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = state.getValue(SHAPE);

		switch (mirrorIn) {
		case LEFT_RIGHT:
			switch (blockrailbase$enumraildirection) {
			case ASCENDING_NORTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);

			case ASCENDING_SOUTH:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);

			case SOUTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);

			case SOUTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);

			case NORTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);

			case NORTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);

			default:
				return super.withMirror(state, mirrorIn);
			}

		case FRONT_BACK:
			switch (blockrailbase$enumraildirection) {
			case ASCENDING_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);

			case ASCENDING_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);

			case ASCENDING_NORTH:
			case ASCENDING_SOUTH:
			default:
				break;

			case SOUTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_WEST);

			case SOUTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.SOUTH_EAST);

			case NORTH_WEST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_EAST);

			case NORTH_EAST:
				return state.withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_WEST);
			}
		}

		return super.withMirror(state, mirrorIn);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { SHAPE, POWERED });
	}
}
