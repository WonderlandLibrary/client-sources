package net.minecraft.src;

import java.util.*;

public class WorldGenReed extends WorldGenerator
{
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        for (int var6 = 0; var6 < 20; ++var6) {
            final int var7 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
            final int var8 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);
            if (par1World.isAirBlock(var7, par4, var8) && (par1World.getBlockMaterial(var7 - 1, par4 - 1, var8) == Material.water || par1World.getBlockMaterial(var7 + 1, par4 - 1, var8) == Material.water || par1World.getBlockMaterial(var7, par4 - 1, var8 - 1) == Material.water || par1World.getBlockMaterial(var7, par4 - 1, var8 + 1) == Material.water)) {
                for (int var9 = 2 + par2Random.nextInt(par2Random.nextInt(3) + 1), var10 = 0; var10 < var9; ++var10) {
                    if (Block.reed.canBlockStay(par1World, var7, par4 + var10, var8)) {
                        par1World.setBlock(var7, par4 + var10, var8, Block.reed.blockID, 0, 2);
                    }
                }
            }
        }
        return true;
    }
}
