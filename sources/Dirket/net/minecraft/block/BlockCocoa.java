package net.minecraft.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCocoa extends BlockHorizontal implements IGrowable {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
	protected static final AxisAlignedBB[] COCOA_EAST_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.6875D, 0.4375D, 0.375D, 0.9375D, 0.75D, 0.625D),
			new AxisAlignedBB(0.5625D, 0.3125D, 0.3125D, 0.9375D, 0.75D, 0.6875D), new AxisAlignedBB(0.5625D, 0.3125D, 0.3125D, 0.9375D, 0.75D, 0.6875D) };
	protected static final AxisAlignedBB[] COCOA_WEST_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0625D, 0.4375D, 0.375D, 0.3125D, 0.75D, 0.625D),
			new AxisAlignedBB(0.0625D, 0.3125D, 0.3125D, 0.4375D, 0.75D, 0.6875D), new AxisAlignedBB(0.0625D, 0.3125D, 0.3125D, 0.4375D, 0.75D, 0.6875D) };
	protected static final AxisAlignedBB[] COCOA_NORTH_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.375D, 0.4375D, 0.0625D, 0.625D, 0.75D, 0.3125D),
			new AxisAlignedBB(0.3125D, 0.3125D, 0.0625D, 0.6875D, 0.75D, 0.4375D), new AxisAlignedBB(0.3125D, 0.3125D, 0.0625D, 0.6875D, 0.75D, 0.4375D) };
	protected static final AxisAlignedBB[] COCOA_SOUTH_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.375D, 0.4375D, 0.6875D, 0.625D, 0.75D, 0.9375D),
			new AxisAlignedBB(0.3125D, 0.3125D, 0.5625D, 0.6875D, 0.75D, 0.9375D), new AxisAlignedBB(0.3125D, 0.3125D, 0.5625D, 0.6875D, 0.75D, 0.9375D) };

	public BlockCocoa() {
		super(Material.PLANTS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(AGE, Integer.valueOf(0)));
		this.setTickRandomly(true);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!this.canBlockStay(worldIn, pos, state)) {
			this.dropBlock(worldIn, pos, state);
		} else if (worldIn.rand.nextInt(5) == 0) {
			int i = state.getValue(AGE).intValue();

			if (i < 2) {
				worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i + 1)), 2);
			}
		}
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		pos = pos.offset(state.getValue(FACING));
		IBlockState iblockstate = worldIn.getBlockState(pos);
		return (iblockstate.getBlock() == Blocks.LOG) && (iblockstate.getValue(BlockOldLog.VARIANT) == BlockPlanks.EnumType.JUNGLE);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		int i = state.getValue(AGE).intValue();

		switch (state.getValue(FACING)) {
		case SOUTH:
			return COCOA_SOUTH_AABB[i];

		case NORTH:
		default:
			return COCOA_NORTH_AABB[i];

		case WEST:
			return COCOA_WEST_AABB[i];

		case EAST:
			return COCOA_EAST_AABB[i];
		}
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

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing enumfacing = EnumFacing.fromAngle(placer.rotationYaw);
		worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (!facing.getAxis().isHorizontal()) {
			facing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, facing.getOpposite()).withProperty(AGE, Integer.valueOf(0));
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		if (!this.canBlockStay(p_189540_2_, p_189540_3_, p_189540_1_)) {
			this.dropBlock(p_189540_2_, p_189540_3_, p_189540_1_);
		}
	}

	private void dropBlock(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
		this.dropBlockAsItem(worldIn, pos, state, 0);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		int i = state.getValue(AGE).intValue();
		int j = 1;

		if (i >= 2) {
			j = 3;
		}

		for (int k = 0; k < j; ++k) {
			spawnAsEntity(worldIn, pos, new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()));
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage());
	}

	/**
	 * Whether this IGrowable can grow
	 */
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return state.getValue(AGE).intValue() < 2;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(state.getValue(AGE).intValue() + 1)), 2);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(AGE, Integer.valueOf((meta & 15) >> 2));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();
		i = i | (state.getValue(AGE).intValue() << 2);
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, AGE });
	}
}
