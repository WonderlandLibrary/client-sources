package net.minecraft.src;

import java.util.*;

public class WorldGenLiquids extends WorldGenerator
{
    private int liquidBlockId;
    
    public WorldGenLiquids(final int par1) {
        this.liquidBlockId = par1;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        if (par1World.getBlockId(par3, par4 + 1, par5) != Block.stone.blockID) {
            return false;
        }
        if (par1World.getBlockId(par3, par4 - 1, par5) != Block.stone.blockID) {
            return false;
        }
        if (par1World.getBlockId(par3, par4, par5) != 0 && par1World.getBlockId(par3, par4, par5) != Block.stone.blockID) {
            return false;
        }
        int var6 = 0;
        if (par1World.getBlockId(par3 - 1, par4, par5) == Block.stone.blockID) {
            ++var6;
        }
        if (par1World.getBlockId(par3 + 1, par4, par5) == Block.stone.blockID) {
            ++var6;
        }
        if (par1World.getBlockId(par3, par4, par5 - 1) == Block.stone.blockID) {
            ++var6;
        }
        if (par1World.getBlockId(par3, par4, par5 + 1) == Block.stone.blockID) {
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
        if (var6 == 3 && var7 == 1) {
            par1World.setBlock(par3, par4, par5, this.liquidBlockId, 0, 2);
            par1World.scheduledUpdatesAreImmediate = true;
            Block.blocksList[this.liquidBlockId].updateTick(par1World, par3, par4, par5, par2Random);
            par1World.scheduledUpdatesAreImmediate = false;
        }
        return true;
    }
}
