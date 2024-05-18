package net.minecraft.src;

public class GenLayerAddIsland extends GenLayer
{
    public GenLayerAddIsland(final long par1, final GenLayer par3GenLayer) {
        super(par1);
        this.parent = par3GenLayer;
    }
    
    @Override
    public int[] getInts(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = par1 - 1;
        final int var6 = par2 - 1;
        final int var7 = par3 + 2;
        final int var8 = par4 + 2;
        final int[] var9 = this.parent.getInts(var5, var6, var7, var8);
        final int[] var10 = IntCache.getIntCache(par3 * par4);
        for (int var11 = 0; var11 < par4; ++var11) {
            for (int var12 = 0; var12 < par3; ++var12) {
                final int var13 = var9[var12 + 0 + (var11 + 0) * var7];
                final int var14 = var9[var12 + 2 + (var11 + 0) * var7];
                final int var15 = var9[var12 + 0 + (var11 + 2) * var7];
                final int var16 = var9[var12 + 2 + (var11 + 2) * var7];
                final int var17 = var9[var12 + 1 + (var11 + 1) * var7];
                this.initChunkSeed(var12 + par1, var11 + par2);
                if (var17 == 0 && (var13 != 0 || var14 != 0 || var15 != 0 || var16 != 0)) {
                    int var18 = 1;
                    int var19 = 1;
                    if (var13 != 0 && this.nextInt(var18++) == 0) {
                        var19 = var13;
                    }
                    if (var14 != 0 && this.nextInt(var18++) == 0) {
                        var19 = var14;
                    }
                    if (var15 != 0 && this.nextInt(var18++) == 0) {
                        var19 = var15;
                    }
                    if (var16 != 0 && this.nextInt(var18++) == 0) {
                        var19 = var16;
                    }
                    if (this.nextInt(3) == 0) {
                        var10[var12 + var11 * par3] = var19;
                    }
                    else if (var19 == BiomeGenBase.icePlains.biomeID) {
                        var10[var12 + var11 * par3] = BiomeGenBase.frozenOcean.biomeID;
                    }
                    else {
                        var10[var12 + var11 * par3] = 0;
                    }
                }
                else if (var17 > 0 && (var13 == 0 || var14 == 0 || var15 == 0 || var16 == 0)) {
                    if (this.nextInt(5) == 0) {
                        if (var17 == BiomeGenBase.icePlains.biomeID) {
                            var10[var12 + var11 * par3] = BiomeGenBase.frozenOcean.biomeID;
                        }
                        else {
                            var10[var12 + var11 * par3] = 0;
                        }
                    }
                    else {
                        var10[var12 + var11 * par3] = var17;
                    }
                }
                else {
                    var10[var12 + var11 * par3] = var17;
                }
            }
        }
        return var10;
    }
}
