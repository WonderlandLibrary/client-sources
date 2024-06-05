package net.minecraft.src;

public class GenLayerBiome extends GenLayer
{
    private BiomeGenBase[] allowedBiomes;
    
    public GenLayerBiome(final long par1, final GenLayer par3GenLayer, final WorldType par4WorldType) {
        super(par1);
        this.allowedBiomes = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga, BiomeGenBase.jungle };
        this.parent = par3GenLayer;
        if (par4WorldType == WorldType.DEFAULT_1_1) {
            this.allowedBiomes = new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.forest, BiomeGenBase.extremeHills, BiomeGenBase.swampland, BiomeGenBase.plains, BiomeGenBase.taiga };
        }
    }
    
    @Override
    public int[] getInts(final int par1, final int par2, final int par3, final int par4) {
        final int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        final int[] var6 = IntCache.getIntCache(par3 * par4);
        for (int var7 = 0; var7 < par4; ++var7) {
            for (int var8 = 0; var8 < par3; ++var8) {
                this.initChunkSeed(var8 + par1, var7 + par2);
                final int var9 = var5[var8 + var7 * par3];
                if (var9 == 0) {
                    var6[var8 + var7 * par3] = 0;
                }
                else if (var9 == BiomeGenBase.mushroomIsland.biomeID) {
                    var6[var8 + var7 * par3] = var9;
                }
                else if (var9 == 1) {
                    var6[var8 + var7 * par3] = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;
                }
                else {
                    final int var10 = this.allowedBiomes[this.nextInt(this.allowedBiomes.length)].biomeID;
                    if (var10 == BiomeGenBase.taiga.biomeID) {
                        var6[var8 + var7 * par3] = var10;
                    }
                    else {
                        var6[var8 + var7 * par3] = BiomeGenBase.icePlains.biomeID;
                    }
                }
            }
        }
        return var6;
    }
}
