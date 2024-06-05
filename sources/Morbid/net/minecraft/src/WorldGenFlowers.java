package net.minecraft.src;

import java.util.*;

public class WorldGenFlowers extends WorldGenerator
{
    private int plantBlockId;
    
    public WorldGenFlowers(final int par1) {
        this.plantBlockId = par1;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        for (int var6 = 0; var6 < 64; ++var6) {
            final int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            final int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            final int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
            if (par1World.isAirBlock(var7, var8, var9) && (!par1World.provider.hasNoSky || var8 < 127) && Block.blocksList[this.plantBlockId].canBlockStay(par1World, var7, var8, var9)) {
                par1World.setBlock(var7, var8, var9, this.plantBlockId, 0, 2);
            }
        }
        return true;
    }
}
