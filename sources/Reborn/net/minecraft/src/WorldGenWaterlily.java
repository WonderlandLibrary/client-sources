package net.minecraft.src;

import java.util.*;

public class WorldGenWaterlily extends WorldGenerator
{
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        for (int var6 = 0; var6 < 10; ++var6) {
            final int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            final int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            final int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
            if (par1World.isAirBlock(var7, var8, var9) && Block.waterlily.canPlaceBlockAt(par1World, var7, var8, var9)) {
                par1World.setBlock(var7, var8, var9, Block.waterlily.blockID, 0, 2);
            }
        }
        return true;
    }
}
