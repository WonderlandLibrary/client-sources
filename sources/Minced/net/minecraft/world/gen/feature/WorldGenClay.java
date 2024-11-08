// 
// Decompiled by Procyon v0.5.36
// 

package net.minecraft.world.gen.feature;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;

public class WorldGenClay extends WorldGenerator
{
    private final Block block;
    private final int numberOfBlocks;
    
    public WorldGenClay(final int p_i2011_1_) {
        this.block = Blocks.CLAY;
        this.numberOfBlocks = p_i2011_1_;
    }
    
    @Override
    public boolean generate(final World worldIn, final Random rand, final BlockPos position) {
        if (worldIn.getBlockState(position).getMaterial() != Material.WATER) {
            return false;
        }
        final int i = rand.nextInt(this.numberOfBlocks - 2) + 2;
        final int j = 1;
        for (int k = position.getX() - i; k <= position.getX() + i; ++k) {
            for (int l = position.getZ() - i; l <= position.getZ() + i; ++l) {
                final int i2 = k - position.getX();
                final int j2 = l - position.getZ();
                if (i2 * i2 + j2 * j2 <= i * i) {
                    for (int k2 = position.getY() - 1; k2 <= position.getY() + 1; ++k2) {
                        final BlockPos blockpos = new BlockPos(k, k2, l);
                        final Block block = worldIn.getBlockState(blockpos).getBlock();
                        if (block == Blocks.DIRT || block == Blocks.CLAY) {
                            worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
