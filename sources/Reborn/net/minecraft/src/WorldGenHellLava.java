package net.minecraft.src;

import java.util.*;

public class WorldGenHellLava extends WorldGenerator
{
    private int hellLavaID;
    private boolean field_94524_b;
    
    public WorldGenHellLava(final int par1, final boolean par2) {
        this.field_94524_b = false;
        this.hellLavaID = par1;
        this.field_94524_b = par2;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        if (par1World.getBlockId(par3, par4 + 1, par5) != Block.netherrack.blockID) {
            return false;
        }
        if (par1World.getBlockId(par3, par4, par5) != 0 && par1World.getBlockId(par3, par4, par5) != Block.netherrack.blockID) {
            return false;
        }
        int var6 = 0;
        if (par1World.getBlockId(par3 - 1, par4, par5) == Block.netherrack.blockID) {
            ++var6;
        }
        if (par1World.getBlockId(par3 + 1, par4, par5) == Block.netherrack.blockID) {
            ++var6;
        }
        if (par1World.getBlockId(par3, par4, par5 - 1) == Block.netherrack.blockID) {
            ++var6;
        }
        if (par1World.getBlockId(par3, par4, par5 + 1) == Block.netherrack.blockID) {
            ++var6;
        }
        if (par1World.getBlockId(par3, par4 - 1, par5) == Block.netherrack.blockID) {
            ++var6;
        }
        int var7 = 0;
        if (par1World.isAirBlock(par3 - 1, par4, par5)) {
            ++var7;
        }
        if (par1World.isAirBlock(par3 + 1, par4, par5)) {
            ++var7;
        }
        if (par1World.isAirBlock(par3, par4, par5 - 1)) {
            ++var7;
        }
        if (par1World.isAirBlock(par3, par4, par5 + 1)) {
            ++var7;
        }
        if (par1World.isAirBlock(par3, par4 - 1, par5)) {
            ++var7;
        }
        if ((!this.field_94524_b && var6 == 4 && var7 == 1) || var6 == 5) {
            par1World.setBlock(par3, par4, par5, this.hellLavaID, 0, 2);
            par1World.scheduledUpdatesAreImmediate = true;
            Block.blocksList[this.hellLavaID].updateTick(par1World, par3, par4, par5, par2Random);
            par1World.scheduledUpdatesAreImmediate = false;
        }
        return true;
    }
}
