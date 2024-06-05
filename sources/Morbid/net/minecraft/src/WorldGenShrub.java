package net.minecraft.src;

import java.util.*;

public class WorldGenShrub extends WorldGenerator
{
    private int field_76527_a;
    private int field_76526_b;
    
    public WorldGenShrub(final int par1, final int par2) {
        this.field_76526_b = par1;
        this.field_76527_a = par2;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, int par4, final int par5) {
        final boolean var6 = false;
        int var7;
        while (((var7 = par1World.getBlockId(par3, par4, par5)) == 0 || var7 == Block.leaves.blockID) && par4 > 0) {
            --par4;
        }
        final int var8 = par1World.getBlockId(par3, par4, par5);
        if (var8 == Block.dirt.blockID || var8 == Block.grass.blockID) {
            ++par4;
            this.setBlockAndMetadata(par1World, par3, par4, par5, Block.wood.blockID, this.field_76526_b);
            for (int var9 = par4; var9 <= par4 + 2; ++var9) {
                final int var10 = var9 - par4;
                for (int var11 = 2 - var10, var12 = par3 - var11; var12 <= par3 + var11; ++var12) {
                    final int var13 = var12 - par3;
                    for (int var14 = par5 - var11; var14 <= par5 + var11; ++var14) {
                        final int var15 = var14 - par5;
                        if ((Math.abs(var13) != var11 || Math.abs(var15) != var11 || par2Random.nextInt(2) != 0) && !Block.opaqueCubeLookup[par1World.getBlockId(var12, var9, var14)]) {
                            this.setBlockAndMetadata(par1World, var12, var9, var14, Block.leaves.blockID, this.field_76527_a);
                        }
                    }
                }
            }
        }
        return true;
    }
}
