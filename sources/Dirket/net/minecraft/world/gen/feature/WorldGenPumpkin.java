package net.minecraft.world.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenPumpkin extends WorldGenerator {
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		for (int i = 0; i < 64; ++i) {
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

			if (worldIn.isAirBlock(blockpos) && (worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS) && Blocks.PUMPKIN.canPlaceBlockAt(worldIn, blockpos)) {
				worldIn.setBlockState(blockpos, Blocks.PUMPKIN.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.Plane.HORIZONTAL.random(rand)), 2);
			}
		}

		return true;
	}
}
