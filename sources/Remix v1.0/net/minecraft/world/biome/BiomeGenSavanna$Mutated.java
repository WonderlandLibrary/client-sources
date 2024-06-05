package net.minecraft.world.biome;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;

public static class Mutated extends BiomeGenMutated
{
    public Mutated(final int p_i45382_1_, final BiomeGenBase p_i45382_2_) {
        super(p_i45382_1_, p_i45382_2_);
        this.theBiomeDecorator.treesPerChunk = 2;
        this.theBiomeDecorator.flowersPerChunk = 2;
        this.theBiomeDecorator.grassPerChunk = 5;
    }
    
    @Override
    public void genTerrainBlocks(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_, final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
        this.topBlock = Blocks.grass.getDefaultState();
        this.fillerBlock = Blocks.dirt.getDefaultState();
        if (p_180622_6_ > 1.75) {
            this.topBlock = Blocks.stone.getDefaultState();
            this.fillerBlock = Blocks.stone.getDefaultState();
        }
        else if (p_180622_6_ > -0.5) {
            this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
        }
        this.func_180628_b(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
    
    @Override
    public void func_180624_a(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        this.theBiomeDecorator.func_180292_a(worldIn, p_180624_2_, this, p_180624_3_);
    }
}
