/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenMelon
extends WorldGenerator {
    private static final String __OBFID = "CL_00000424";

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        int var4 = 0;
        while (var4 < 64) {
            BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (Blocks.melon_block.canPlaceBlockAt(worldIn, var5) && worldIn.getBlockState(var5.offsetDown()).getBlock() == Blocks.grass) {
                worldIn.setBlockState(var5, Blocks.melon_block.getDefaultState(), 2);
            }
            ++var4;
        }
        return true;
    }
}

