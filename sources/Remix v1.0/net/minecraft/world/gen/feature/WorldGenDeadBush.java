package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WorldGenDeadBush extends WorldGenerator
{
    @Override
    public boolean generate(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        Block var4;
        while (((var4 = worldIn.getBlockState(p_180709_3_).getBlock()).getMaterial() == Material.air || var4.getMaterial() == Material.leaves) && p_180709_3_.getY() > 0) {
            p_180709_3_ = p_180709_3_.offsetDown();
        }
        for (int var5 = 0; var5 < 4; ++var5) {
            final BlockPos var6 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.isAirBlock(var6) && Blocks.deadbush.canBlockStay(worldIn, var6, Blocks.deadbush.getDefaultState())) {
                worldIn.setBlockState(var6, Blocks.deadbush.getDefaultState(), 2);
            }
        }
        return true;
    }
}
