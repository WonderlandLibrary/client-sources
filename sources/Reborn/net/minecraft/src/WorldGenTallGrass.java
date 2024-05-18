package net.minecraft.src;

import java.util.*;

public class WorldGenTallGrass extends WorldGenerator
{
    private int tallGrassID;
    private int tallGrassMetadata;
    
    public WorldGenTallGrass(final int par1, final int par2) {
        this.tallGrassID = par1;
        this.tallGrassMetadata = par2;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, int par4, final int par5) {
        final boolean var6 = false;
        int var7;
        while (((var7 = par1World.getBlockId(par3, par4, par5)) == 0 || var7 == Block.leaves.blockID) && par4 > 0) {
            --par4;
        }
        for (int var8 = 0; var8 < 128; ++var8) {
            final int var9 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            final int var10 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            final int var11 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
            if (par1World.isAirBlock(var9, var10, var11) && Block.blocksList[this.tallGrassID].canBlockStay(par1World, var9, var10, var11)) {
                par1World.setBlock(var9, var10, var11, this.tallGrassID, this.tallGrassMetadata, 2);
            }
        }
        return true;
    }
}
