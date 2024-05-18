package net.minecraft.src;

import java.util.*;

public class WorldGenDesertWells extends WorldGenerator
{
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, int par4, final int par5) {
        while (par1World.isAirBlock(par3, par4, par5) && par4 > 2) {
            --par4;
        }
        final int var6 = par1World.getBlockId(par3, par4, par5);
        if (var6 != Block.sand.blockID) {
            return false;
        }
        for (int var7 = -2; var7 <= 2; ++var7) {
            for (int var8 = -2; var8 <= 2; ++var8) {
                if (par1World.isAirBlock(par3 + var7, par4 - 1, par5 + var8) && par1World.isAirBlock(par3 + var7, par4 - 2, par5 + var8)) {
                    return false;
                }
            }
        }
        for (int var7 = -1; var7 <= 0; ++var7) {
            for (int var8 = -2; var8 <= 2; ++var8) {
                for (int var9 = -2; var9 <= 2; ++var9) {
                    par1World.setBlock(par3 + var8, par4 + var7, par5 + var9, Block.sandStone.blockID, 0, 2);
                }
            }
        }
        par1World.setBlock(par3, par4, par5, Block.waterMoving.blockID, 0, 2);
        par1World.setBlock(par3 - 1, par4, par5, Block.waterMoving.blockID, 0, 2);
        par1World.setBlock(par3 + 1, par4, par5, Block.waterMoving.blockID, 0, 2);
        par1World.setBlock(par3, par4, par5 - 1, Block.waterMoving.blockID, 0, 2);
        par1World.setBlock(par3, par4, par5 + 1, Block.waterMoving.blockID, 0, 2);
        for (int var7 = -2; var7 <= 2; ++var7) {
            for (int var8 = -2; var8 <= 2; ++var8) {
                if (var7 == -2 || var7 == 2 || var8 == -2 || var8 == 2) {
                    par1World.setBlock(par3 + var7, par4 + 1, par5 + var8, Block.sandStone.blockID, 0, 2);
                }
            }
        }
        par1World.setBlock(par3 + 2, par4 + 1, par5, Block.stoneSingleSlab.blockID, 1, 2);
        par1World.setBlock(par3 - 2, par4 + 1, par5, Block.stoneSingleSlab.blockID, 1, 2);
        par1World.setBlock(par3, par4 + 1, par5 + 2, Block.stoneSingleSlab.blockID, 1, 2);
        par1World.setBlock(par3, par4 + 1, par5 - 2, Block.stoneSingleSlab.blockID, 1, 2);
        for (int var7 = -1; var7 <= 1; ++var7) {
            for (int var8 = -1; var8 <= 1; ++var8) {
                if (var7 == 0 && var8 == 0) {
                    par1World.setBlock(par3 + var7, par4 + 4, par5 + var8, Block.sandStone.blockID, 0, 2);
                }
                else {
                    par1World.setBlock(par3 + var7, par4 + 4, par5 + var8, Block.stoneSingleSlab.blockID, 1, 2);
                }
            }
        }
        for (int var7 = 1; var7 <= 3; ++var7) {
            par1World.setBlock(par3 - 1, par4 + var7, par5 - 1, Block.sandStone.blockID, 0, 2);
            par1World.setBlock(par3 - 1, par4 + var7, par5 + 1, Block.sandStone.blockID, 0, 2);
            par1World.setBlock(par3 + 1, par4 + var7, par5 - 1, Block.sandStone.blockID, 0, 2);
            par1World.setBlock(par3 + 1, par4 + var7, par5 + 1, Block.sandStone.blockID, 0, 2);
        }
        return true;
    }
}
