package net.minecraft.src;

public class GenLayerHills extends GenLayer
{
    public GenLayerHills(final long par1, final GenLayer par3GenLayer) {
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
                if (this.nextInt(3) == 0) {
                    int var10;
                    if ((var10 = var9) == BiomeGenBase.desert.biomeID) {
                        var10 = BiomeGenBase.desertHills.biomeID;
                    }
                    else if (var9 == BiomeGenBase.forest.biomeID) {
                        var10 = BiomeGenBase.forestHills.biomeID;
                    }
                    else if (var9 == BiomeGenBase.taiga.biomeID) {
                        var10 = BiomeGenBase.taigaHills.biomeID;
                    }
                    else if (var9 == BiomeGenBase.plains.biomeID) {
                        var10 = BiomeGenBase.forest.biomeID;
                    }
                    else if (var9 == BiomeGenBase.icePlains.biomeID) {
                        var10 = BiomeGenBase.iceMountains.biomeID;
                    }
                    else if (var9 == BiomeGenBase.jungle.biomeID) {
                        var10 = BiomeGenBase.jungleHills.biomeID;
                    }
                    if (var10 == var9) {
                        var6[var8 + var7 * par3] = var9;
                    }
                    else {
                        final int var11 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                        final int var12 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                        final int var13 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                        final int var14 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];
                        if (var11 == var9 && var12 == var9 && var13 == var9 && var14 == var9) {
                            var6[var8 + var7 * par3] = var10;
                        }
                        else {
                            var6[var8 + var7 * par3] = var9;
                        }
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
