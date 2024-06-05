package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class WorldGenGlowStone2 extends WorldGenerator
{
    @Override
    public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        if (!worldIn.isAirBlock(p_180709_3_)) {
            return false;
        }
        if (worldIn.getBlockState(p_180709_3_.offsetUp()).getBlock() != Blocks.netherrack) {
            return false;
        }
        worldIn.setBlockState(p_180709_3_, Blocks.glowstone.getDefaultState(), 2);
        for (int var4 = 0; var4 < 1500; ++var4) {
            final BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(12), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.getBlockState(var5).getBlock().getMaterial() == Material.air) {
                int var6 = 0;
                for (final EnumFacing var10 : EnumFacing.values()) {
                    if (worldIn.getBlockState(var5.offset(var10)).getBlock() == Blocks.glowstone) {
                        ++var6;
                    }
                    if (var6 > 1) {
                        break;
                    }
                }
                if (var6 == 1) {
                    worldIn.setBlockState(var5, Blocks.glowstone.getDefaultState(), 2);
                }
            }
        }
        return true;
    }
}
