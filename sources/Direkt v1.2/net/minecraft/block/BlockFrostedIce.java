package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockFrostedIce extends BlockIce {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);

	public BlockFrostedIce() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AGE).intValue();
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, Integer.valueOf(MathHelper.clamp_int(meta, 0, 3)));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (((rand.nextInt(3) == 0) || (this.countNeighbors(worldIn, pos) < 4)) && (worldIn.getLightFromNeighbors(pos) > (11 - state.getValue(AGE).intValue() - state.getLightOpacity()))) {
			this.slightlyMelt(worldIn, pos, state, rand, true);
		} else {
			worldIn.scheduleUpdate(pos, this, MathHelper.getRandomIntegerInRange(rand, 20, 40));
		}
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		if (p_189540_4_ == this) {
			int i = this.countNeighbors(p_189540_2_, p_189540_3_);

			if (i < 2) {
				this.turnIntoWater(p_189540_2_, p_189540_3_);
			}
		}
	}

	private int countNeighbors(World p_185680_1_, BlockPos p_185680_2_) {
		int i = 0;

		for (EnumFacing enumfacing : EnumFacing.values()) {
			if (p_185680_1_.getBlockState(p_185680_2_.offset(enumfacing)).getBlock() == this) {
				++i;

				if (i >= 4) { return i; }
			}
		}

		return i;
	}

	protected void slightlyMelt(World p_185681_1_, BlockPos p_185681_2_, IBlockState p_185681_3_, Random p_185681_4_, boolean p_185681_5_) {
		int i = p_185681_3_.getValue(AGE).intValue();

		if (i < 3) {
			p_185681_1_.setBlockState(p_185681_2_, p_185681_3_.withProperty(AGE, Integer.valueOf(i + 1)), 2);
			p_185681_1_.scheduleUpdate(p_185681_2_, this, MathHelper.getRandomIntegerInRange(p_185681_4_, 20, 40));
		} else {
			this.turnIntoWater(p_185681_1_, p_185681_2_);

			if (p_185681_5_) {
				for (EnumFacing enumfacing : EnumFacing.values()) {
					BlockPos blockpos = p_185681_2_.offset(enumfacing);
					IBlockState iblockstate = p_185681_1_.getBlockState(blockpos);

					if (iblockstate.getBlock() == this) {
						this.slightlyMelt(p_185681_1_, blockpos, iblockstate, p_185681_4_, false);
					}
				}
			}
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE });
	}

	@Override
	@Nullable
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return null;
	}
}
