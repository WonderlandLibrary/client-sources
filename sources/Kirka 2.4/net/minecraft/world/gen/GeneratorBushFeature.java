/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GeneratorBushFeature
extends WorldGenerator {
    private BlockBush field_175908_a;
    private static final String __OBFID = "CL_00002000";

    public GeneratorBushFeature(BlockBush p_i45633_1_) {
        this.field_175908_a = p_i45633_1_;
    }

    @Override
    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_) {
        for (int var4 = 0; var4 < 64; ++var4) {
            BlockPos var5 = p_180709_3_.add(p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(8) - p_180709_2_.nextInt(8));
            if (!worldIn.isAirBlock(var5) || worldIn.provider.getHasNoSky() && var5.getY() >= 255 || !this.field_175908_a.canBlockStay(worldIn, var5, this.field_175908_a.getDefaultState())) continue;
            worldIn.setBlockState(var5, this.field_175908_a.getDefaultState(), 2);
        }
        return true;
    }
}

