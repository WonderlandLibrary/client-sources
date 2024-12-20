package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGrassPath extends Block {
	protected static final AxisAlignedBB GRASS_PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	protected BlockGrassPath() {
		super(Material.GROUND);
		this.setLightOpacity(255);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		switch (side) {
		case UP:
			return true;

		case NORTH:
		case SOUTH:
		case WEST:
		case EAST:
			IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
			Block block = iblockstate.getBlock();
			return !iblockstate.isOpaqueCube() && (block != Blocks.FARMLAND) && (block != Blocks.GRASS_PATH);

		default:
			return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return GRASS_PATH_AABB;
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

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(this);
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_);

		if (p_189540_2_.getBlockState(p_189540_3_.up()).getMaterial().isSolid()) {
			p_189540_2_.setBlockState(p_189540_3_, Blocks.DIRT.getDefaultState());
		}
	}
}
