package net.minecraft.src;

import java.util.*;

public class WorldGenClay extends WorldGenerator
{
    private int clayBlockId;
    private int numberOfBlocks;
    
    public WorldGenClay(final int par1) {
        this.clayBlockId = Block.blockClay.blockID;
        this.numberOfBlocks = par1;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        if (par1World.getBlockMaterial(par3, par4, par5) != Material.water) {
            return false;
        }
        final int var6 = par2Random.nextInt(this.numberOfBlocks - 2) + 2;
        final byte var7 = 1;
        for (int var8 = par3 - var6; var8 <= par3 + var6; ++var8) {
            for (int var9 = par5 - var6; var9 <= par5 + var6; ++var9) {
                final int var10 = var8 - par3;
                final int var11 = var9 - par5;
                if (var10 * var10 + var11 * var11 <= var6 * var6) {
                    for (int var12 = par4 - var7; var12 <= par4 + var7; ++var12) {
                        final int var13 = par1World.getBlockId(var8, var12, var9);
                        if (var13 == Block.dirt.blockID || var13 == Block.blockClay.blockID) {
                            par1World.setBlock(var8, var12, var9, this.clayBlockId, 0, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
