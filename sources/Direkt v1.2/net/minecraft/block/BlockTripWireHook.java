package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Objects;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWireHook extends Block {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool POWERED = PropertyBool.create("powered");
	public static final PropertyBool ATTACHED = PropertyBool.create("attached");
	protected static final AxisAlignedBB HOOK_NORTH_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.625D, 0.6875D, 0.625D, 1.0D);
	protected static final AxisAlignedBB HOOK_SOUTH_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.625D, 0.375D);
	protected static final AxisAlignedBB HOOK_WEST_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.3125D, 1.0D, 0.625D, 0.6875D);
	protected static final AxisAlignedBB HOOK_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 0.375D, 0.625D, 0.6875D);

	public BlockTripWireHook() {
		super(Material.CIRCUITS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.valueOf(false)).withProperty(ATTACHED, Boolean.valueOf(false)));
		this.setCreativeTab(CreativeTabs.REDSTONE);
		this.setTickRandomly(true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		switch (state.getValue(FACING)) {
		case EAST:
		default:
			return HOOK_EAST_AABB;

		case WEST:
			return HOOK_WEST_AABB;

		case SOUTH:
			return HOOK_SOUTH_AABB;

		case NORTH:
			return HOOK_NORTH_AABB;
		}
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Check whether this Block can be placed on the given side
	 */
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		return side.getAxis().isHorizontal() && worldIn.getBlockState(pos.offset(side.getOpposite())).isNormalCube();
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			if (worldIn.getBlockState(pos.offset(enumfacing)).isNormalCube()) { return true; }
		}

		return false;
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState iblockstate = this.getDefaultState().withProperty(POWERED, Boolean.valueOf(false)).withProperty(ATTACHED, Boolean.valueOf(false));

		if (facing.getAxis().isHorizontal()) {
			iblockstate = iblockstate.withProperty(FACING, facing);
		}

		return iblockstate;
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		this.calculateState(worldIn, pos, state, false, false, -1, (IBlockState) null);
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		if (p_189540_4_ != this) {
			if (this.checkForDrop(p_189540_2_, p_189540_3_, p_189540_1_)) {
				EnumFacing enumfacing = p_189540_1_.getValue(FACING);

				if (!p_189540_2_.getBlockState(p_189540_3_.offset(enumfacing.getOpposite())).isNormalCube()) {
					this.dropBlockAsItem(p_189540_2_, p_189540_3_, p_189540_1_, 0);
					p_189540_2_.setBlockToAir(p_189540_3_);
				}
			}
		}
	}

	public void calculateState(World worldIn, BlockPos pos, IBlockState hookState, boolean p_176260_4_, boolean p_176260_5_, int p_176260_6_, @Nullable IBlockState p_176260_7_) {
		EnumFacing enumfacing = hookState.getValue(FACING);
		boolean flag = hookState.getValue(ATTACHED).booleanValue();
		boolean flag1 = hookState.getValue(POWERED).booleanValue();
		boolean flag2 = !p_176260_4_;
		boolean flag3 = false;
		int i = 0;
		IBlockState[] aiblockstate = new IBlockState[42];

		for (int j = 1; j < 42; ++j) {
			BlockPos blockpos = pos.offset(enumfacing, j);
			IBlockState iblockstate = worldIn.getBlockState(blockpos);

			if (iblockstate.getBlock() == Blocks.TRIPWIRE_HOOK) {
				if (iblockstate.getValue(FACING) == enumfacing.getOpposite()) {
					i = j;
				}

				break;
			}

			if ((iblockstate.getBlock() != Blocks.TRIPWIRE) && (j != p_176260_6_)) {
				aiblockstate[j] = null;
				flag2 = false;
			} else {
				if (j == p_176260_6_) {
					iblockstate = Objects.firstNonNull(p_176260_7_, iblockstate);
				}

				boolean flag4 = !iblockstate.getValue(BlockTripWire.DISARMED).booleanValue();
				boolean flag5 = iblockstate.getValue(BlockTripWire.POWERED).booleanValue();
				flag3 |= flag4 && flag5;
				aiblockstate[j] = iblockstate;

				if (j == p_176260_6_) {
					worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
					flag2 &= flag4;
				}
			}
		}

		flag2 = flag2 & (i > 1);
		flag3 = flag3 & flag2;
		IBlockState iblockstate1 = this.getDefaultState().withProperty(ATTACHED, Boolean.valueOf(flag2)).withProperty(POWERED, Boolean.valueOf(flag3));

		if (i > 0) {
			BlockPos blockpos1 = pos.offset(enumfacing, i);
			EnumFacing enumfacing1 = enumfacing.getOpposite();
			worldIn.setBlockState(blockpos1, iblockstate1.withProperty(FACING, enumfacing1), 3);
			this.notifyNeighbors(worldIn, blockpos1, enumfacing1);
			this.playSound(worldIn, blockpos1, flag2, flag3, flag, flag1);
		}

		this.playSound(worldIn, pos, flag2, flag3, flag, flag1);

		if (!p_176260_4_) {
			worldIn.setBlockState(pos, iblockstate1.withProperty(FACING, enumfacing), 3);

			if (p_176260_5_) {
				this.notifyNeighbors(worldIn, pos, enumfacing);
			}
		}

		if (flag != flag2) {
			for (int k = 1; k < i; ++k) {
				BlockPos blockpos2 = pos.offset(enumfacing, k);
				IBlockState iblockstate2 = aiblockstate[k];

				if ((iblockstate2 != null) && (worldIn.getBlockState(blockpos2).getMaterial() != Material.AIR)) {
					worldIn.setBlockState(blockpos2, iblockstate2.withProperty(ATTACHED, Boolean.valueOf(flag2)), 3);
				}
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
		this.calculateState(worldIn, pos, state, false, true, -1, (IBlockState) null);
	}

	private void playSound(World worldIn, BlockPos pos, boolean p_180694_3_, boolean p_180694_4_, boolean p_180694_5_, boolean p_180694_6_) {
		if (p_180694_4_ && !p_180694_6_) {
			worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 0.4F, 0.6F);
		} else if (!p_180694_4_ && p_180694_6_) {
			worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_OFF, SoundCategory.BLOCKS, 0.4F, 0.5F);
		} else if (p_180694_3_ && !p_180694_5_) {
			worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_TRIPWIRE_ATTACH, SoundCategory.BLOCKS, 0.4F, 0.7F);
		} else if (!p_180694_3_ && p_180694_5_) {
			worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_TRIPWIRE_DETACH, SoundCategory.BLOCKS, 0.4F, 1.2F / ((worldIn.rand.nextFloat() * 0.2F) + 0.9F));
		}
	}

	private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing side) {
		worldIn.notifyNeighborsOfStateChange(pos, this);
		worldIn.notifyNeighborsOfStateChange(pos.offset(side.getOpposite()), this);
	}

	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			this.dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		boolean flag = state.getValue(ATTACHED).booleanValue();
		boolean flag1 = state.getValue(POWERED).booleanValue();

		if (flag || flag1) {
			this.calculateState(worldIn, pos, state, true, false, -1, (IBlockState) null);
		}

		if (flag1) {
			worldIn.notifyNeighborsOfStateChange(pos, this);
			worldIn.notifyNeighborsOfStateChange(pos.offset(state.getValue(FACING).getOpposite()), this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(POWERED).booleanValue() ? 15 : 0;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return !blockState.getValue(POWERED).booleanValue() ? 0 : (blockState.getValue(FACING) == side ? 15 : 0);
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this change based on its state.
	 */
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(POWERED, Boolean.valueOf((meta & 8) > 0)).withProperty(ATTACHED,
				Boolean.valueOf((meta & 4) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();

		if (state.getValue(POWERED).booleanValue()) {
			i |= 8;
		}

		if (state.getValue(ATTACHED).booleanValue()) {
			i |= 4;
		}

		return i;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED, ATTACHED });
	}
}
