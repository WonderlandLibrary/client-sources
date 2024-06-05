package net.minecraft.src;

import java.util.*;

public abstract class WorldGenerator
{
    private final boolean doBlockNotify;
    
    public WorldGenerator() {
        this.doBlockNotify = false;
    }
    
    public WorldGenerator(final boolean par1) {
        this.doBlockNotify = par1;
    }
    
    public abstract boolean generate(final World p0, final Random p1, final int p2, final int p3, final int p4);
    
    public void setScale(final double par1, final double par3, final double par5) {
    }
    
    protected void setBlock(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.setBlockAndMetadata(par1World, par2, par3, par4, par5, 0);
    }
    
    protected void setBlockAndMetadata(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if (this.doBlockNotify) {
            par1World.setBlock(par2, par3, par4, par5, par6, 3);
        }
        else {
            par1World.setBlock(par2, par3, par4, par5, par6, 2);
        }
    }
}
