package net.minecraft.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChorusPlant extends Block {
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool DOWN = PropertyBool.create("down");

	protected BlockChorusPlant() {
		super(Material.PLANTS);
		this.setCreativeTab(CreativeTabs.DECORATIONS);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false))
				.withProperty(WEST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(DOWN, Boolean.valueOf(false)));
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		Block block1 = worldIn.getBlockState(pos.up()).getBlock();
		Block block2 = worldIn.getBlockState(pos.north()).getBlock();
		Block block3 = worldIn.getBlockState(pos.east()).getBlock();
		Block block4 = worldIn.getBlockState(pos.south()).getBlock();
		Block block5 = worldIn.getBlockState(pos.west()).getBlock();
		return state.withProperty(DOWN, Boolean.valueOf((block == this) || (block == Blocks.CHORUS_FLOWER) || (block == Blocks.END_STONE)))
				.withProperty(UP, Boolean.valueOf((block1 == this) || (block1 == Blocks.CHORUS_FLOWER))).withProperty(NORTH, Boolean.valueOf((block2 == this) || (block2 == Blocks.CHORUS_FLOWER)))
				.withProperty(EAST, Boolean.valueOf((block3 == this) || (block3 == Blocks.CHORUS_FLOWER))).withProperty(SOUTH, Boolean.valueOf((block4 == this) || (block4 == Blocks.CHORUS_FLOWER)))
				.withProperty(WEST, Boolean.valueOf((block5 == this) || (block5 == Blocks.CHORUS_FLOWER)));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = state.getActualState(source, pos);
		float f = 0.1875F;
		float f1 = state.getValue(WEST).booleanValue() ? 0.0F : 0.1875F;
		float f2 = state.getValue(DOWN).booleanValue() ? 0.0F : 0.1875F;
		float f3 = state.getValue(NORTH).booleanValue() ? 0.0F : 0.1875F;
		float f4 = state.getValue(EAST).booleanValue() ? 1.0F : 0.8125F;
		float f5 = state.getValue(UP).booleanValue() ? 1.0F : 0.8125F;
		float f6 = state.getValue(SOUTH).booleanValue() ? 1.0F : 0.8125F;
		return new AxisAlignedBB(f1, f2, f3, f4, f5, f6);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
		state = state.getActualState(worldIn, pos);
		float f = 0.1875F;
		float f1 = 0.8125F;
		addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));

		if (state.getValue(WEST).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D));
		}

		if (state.getValue(EAST).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.8125D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D));
		}

		if (state.getValue(UP).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.8125D, 0.1875D, 0.8125D, 1.0D, 0.8125D));
		}

		if (state.getValue(DOWN).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D));
		}

		if (state.getValue(NORTH).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D));
		}

		if (state.getValue(SOUTH).booleanValue()) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D));
		}
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!this.canSurviveAt(worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
		}
	}

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.CHORUS_FRUIT;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random) {
		return random.nextInt(2);
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
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) ? this.canSurviveAt(worldIn, pos) : false;
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		if (!this.canSurviveAt(p_189540_2_, p_189540_3_)) {
			p_189540_2_.scheduleUpdate(p_189540_3_, this, 1);
		}
	}

	public boolean canSurviveAt(World wordIn, BlockPos pos) {
		boolean flag = wordIn.isAirBlock(pos.up());
		boolean flag1 = wordIn.isAirBlock(pos.down());

		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			BlockPos blockpos = pos.offset(enumfacing);
			Block block = wordIn.getBlockState(blockpos).getBlock();

			if (block == this) {
				if (!flag && !flag1) { return false; }

				Block block1 = wordIn.getBlockState(blockpos.down()).getBlock();

				if ((block1 == this) || (block1 == Blocks.END_STONE)) { return true; }
			}
		}

		Block block2 = wordIn.getBlockState(pos.down()).getBlock();
		return (block2 == this) || (block2 == Blocks.END_STONE);
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
		return (block != this) && (block != Blocks.CHORUS_FLOWER) && ((side != EnumFacing.DOWN) || (block != Blocks.END_STONE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST, UP, DOWN });
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}
}
