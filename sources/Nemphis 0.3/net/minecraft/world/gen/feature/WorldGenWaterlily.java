/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenWaterlily
extends WorldGenerator {
    private static final String __OBFID = "CL_00000189";

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        int var4 = 0;
        while (var4 < 10) {
            int var6;
            int var7;
            int var5 = p_180709_3_.getX() + p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8);
            if (worldIn.isAirBlock(new BlockPos(var5, var6 = p_180709_3_.getY() + p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), var7 = p_180709_3_.getZ() + p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8))) && Blocks.waterlily.canPlaceBlockAt(worldIn, new BlockPos(var5, var6, var7))) {
                worldIn.setBlockState(new BlockPos(var5, var6, var7), Blocks.waterlily.getDefaultState(), 2);
            }
            ++var4;
        }
        return true;
    }
}

