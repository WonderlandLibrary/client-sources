package net.minecraft.src;

import java.util.*;

public class MapGenBase
{
    protected int range;
    protected Random rand;
    protected World worldObj;
    
    public MapGenBase() {
        this.range = 8;
        this.rand = new Random();
    }
    
    public void generate(final IChunkProvider par1IChunkProvider, final World par2World, final int par3, final int par4, final byte[] par5ArrayOfByte) {
        final int var6 = this.range;
        this.worldObj = par2World;
        this.rand.setSeed(par2World.getSeed());
        final long var7 = this.rand.nextLong();
        final long var8 = this.rand.nextLong();
        for (int var9 = par3 - var6; var9 <= par3 + var6; ++var9) {
            for (int var10 = par4 - var6; var10 <= par4 + var6; ++var10) {
                final long var11 = var9 * var7;
                final long var12 = var10 * var8;
                this.rand.setSeed(var11 ^ var12 ^ par2World.getSeed());
                this.recursiveGenerate(par2World, var9, var10, par3, par4, par5ArrayOfByte);
            }
        }
    }
    
    protected void recursiveGenerate(final World par1World, final int par2, final int par3, final int par4, final int par5, final byte[] par6ArrayOfByte) {
    }
}
