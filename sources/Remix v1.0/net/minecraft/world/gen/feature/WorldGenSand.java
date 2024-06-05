package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenSand extends WorldGenerator
{
    private Block field_150517_a;
    private int radius;
    
    public WorldGenSand(final Block p_i45462_1_, final int p_i45462_2_) {
        this.field_150517_a = p_i45462_1_;
        this.radius = p_i45462_2_;
    }
    
    @Override
    public boolean generate(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        if (worldIn.getBlockState(p_180709_3_).getBlock().getMaterial() != Material.water) {
            return false;
        }
        final int var4 = p_180709_2_.nextInt(this.radius - 2) + 2;
        final byte var5 = 2;
        for (int var6 = p_180709_3_.getX() - var4; var6 <= p_180709_3_.getX() + var4; ++var6) {
            for (int var7 = p_180709_3_.getZ() - var4; var7 <= p_180709_3_.getZ() + var4; ++var7) {
                final int var8 = var6 - p_180709_3_.getX();
                final int var9 = var7 - p_180709_3_.getZ();
                if (var8 * var8 + var9 * var9 <= var4 * var4) {
                    for (int var10 = p_180709_3_.getY() - var5; var10 <= p_180709_3_.getY() + var5; ++var10) {
                        final BlockPos var11 = new BlockPos(var6, var10, var7);
                        final Block var12 = worldIn.getBlockState(var11).getBlock();
                        if (var12 == Blocks.dirt || var12 == Blocks.grass) {
                            worldIn.setBlockState(var11, this.field_150517_a.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
