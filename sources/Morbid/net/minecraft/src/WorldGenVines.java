package net.minecraft.src;

import java.util.*;

public class WorldGenVines extends WorldGenerator
{
    @Override
    public boolean generate(final World par1World, final Random par2Random, int par3, int par4, int par5) {
        final int var6 = par3;
        final int var7 = par5;
        while (par4 < 128) {
            if (par1World.isAirBlock(par3, par4, par5)) {
                for (int var8 = 2; var8 <= 5; ++var8) {
                    if (Block.vine.canPlaceBlockOnSide(par1World, par3, par4, par5, var8)) {
                        par1World.setBlock(par3, par4, par5, Block.vine.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[var8]], 2);
                        break;
                    }
                }
            }
            else {
                par3 = var6 + par2Random.nextInt(4) - par2Random.nextInt(4);
                par5 = var7 + par2Random.nextInt(4) - par2Random.nextInt(4);
            }
            ++par4;
        }
        return true;
    }
}
