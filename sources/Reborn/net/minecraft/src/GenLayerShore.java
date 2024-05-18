package net.minecraft.src;

public class GenLayerShore extends GenLayer
{
    public GenLayerShore(final long par1, final GenLayer par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
    }
    
    @Override
    public int[] getInts(final int par1, final int par2, final int par3, final int par4) {
        final int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        final int[] var6 = IntCache.getIntCache(par3 * par4);
        for (int var7 = 0; var7 < par4; ++var7) {
            for (int var8 = 0; var8 < par3; ++var8) {
                this.initChunkSeed(var8 + par1, var7 + par2);
                final int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];
                if (var9 == BiomeGenBase.mushroomIsland.biomeID) {
                    final int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    final int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    final int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    final int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                    if (var10 != BiomeGenBase.ocean.biomeID && var11 != BiomeGenBase.ocean.biomeID && var12 != BiomeGenBase.ocean.biomeID && var13 != BiomeGenBase.ocean.biomeID) {
                        var6[var8 + var7 * par3] = var9;
                    }
                    else {
                        var6[var8 + var7 * par3] = BiomeGenBase.mushroomIslandShore.biomeID;
                    }
                }
                else if (var9 != BiomeGenBase.ocean.biomeID && var9 != BiomeGenBase.river.biomeID && var9 != BiomeGenBase.swampland.biomeID && var9 != BiomeGenBase.extremeHills.biomeID) {
                    final int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    final int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    final int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    final int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                    if (var10 != BiomeGenBase.ocean.biomeID && var11 != BiomeGenBase.ocean.biomeID && var12 != BiomeGenBase.ocean.biomeID && var13 != BiomeGenBase.ocean.biomeID) {
                        var6[var8 + var7 * par3] = var9;
                    }
                    else {
                        var6[var8 + var7 * par3] = BiomeGenBase.beach.biomeID;
                    }
                }
                else if (var9 == BiomeGenBase.extremeHills.biomeID) {
                    final int var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    final int var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    final int var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    final int var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                    if (var10 == BiomeGenBase.extremeHills.biomeID && var11 == BiomeGenBase.extremeHills.biomeID && var12 == BiomeGenBase.extremeHills.biomeID && var13 == BiomeGenBase.extremeHills.biomeID) {
                        var6[var8 + var7 * par3] = var9;
                    }
                    else {
                        var6[var8 + var7 * par3] = BiomeGenBase.extremeHillsEdge.biomeID;
                    }
                }
                else {
                    var6[var8 + var7 * par3] = var9;
                }
            }
        }
        return var6;
    }
}
