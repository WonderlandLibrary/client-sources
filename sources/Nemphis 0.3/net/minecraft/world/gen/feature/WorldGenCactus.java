/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCactus
extends WorldGenerator {
    private static final String __OBFID = "CL_00000404";

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        int var4 = 0;
        while (var4 < 10) {
            BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (worldIn.isAirBlock(var5)) {
                int var6 = 1 + p_180709_2_.nextInt(p_180709_2_.nextInt(3) + 1);
                int var7 = 0;
                while (var7 < var6) {
                    if (Blocks.cactus.canBlockStay(worldIn, var5)) {
                        worldIn.setBlockState(var5.offsetUp(var7), Blocks.cactus.getDefaultState(), 2);
                    }
                    ++var7;
                }
            }
            ++var4;
        }
        return true;
    }
}

