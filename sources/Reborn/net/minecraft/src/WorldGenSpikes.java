package net.minecraft.src;

import java.util.*;

public class WorldGenSpikes extends WorldGenerator
{
    private int replaceID;
    
    public WorldGenSpikes(final int par1) {
        this.replaceID = par1;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        if (par1World.isAirBlock(par3, par4, par5) && par1World.getBlockId(par3, par4 - 1, par5) == this.replaceID) {
            final int var6 = par2Random.nextInt(32) + 6;
            final int var7 = par2Random.nextInt(4) + 1;
            for (int var8 = par3 - var7; var8 <= par3 + var7; ++var8) {
                for (int var9 = par5 - var7; var9 <= par5 + var7; ++var9) {
                    final int var10 = var8 - par3;
                    final int var11 = var9 - par5;
                    if (var10 * var10 + var11 * var11 <= var7 * var7 + 1 && par1World.getBlockId(var8, par4 - 1, var9) != this.replaceID) {
                        return false;
                    }
                }
            }
            for (int var8 = par4; var8 < par4 + var6 && var8 < 128; ++var8) {
                for (int var9 = par3 - var7; var9 <= par3 + var7; ++var9) {
                    for (int var10 = par5 - var7; var10 <= par5 + var7; ++var10) {
                        final int var11 = var9 - par3;
                        final int var12 = var10 - par5;
                        if (var11 * var11 + var12 * var12 <= var7 * var7 + 1) {
                            par1World.setBlock(var9, var8, var10, Block.obsidian.blockID, 0, 2);
                        }
                    }
                }
            }
            final EntityEnderCrystal var13 = new EntityEnderCrystal(par1World);
            var13.setLocationAndAngles(par3 + 0.5f, par4 + var6, par5 + 0.5f, par2Random.nextFloat() * 360.0f, 0.0f);
            par1World.spawnEntityInWorld(var13);
            par1World.setBlock(par3, par4 + var6, par5, Block.bedrock.blockID, 0, 2);
            return true;
        }
        return false;
    }
}
