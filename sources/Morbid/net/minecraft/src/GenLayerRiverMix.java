package net.minecraft.src;

public class GenLayerRiverMix extends GenLayer
{
    private GenLayer biomePatternGeneratorChain;
    private GenLayer riverPatternGeneratorChain;
    
    public GenLayerRiverMix(final long par1, final GenLayer par3GenLayer, final GenLayer par4GenLayer) {
        super(par1);
        this.biomePatternGeneratorChain = par3GenLayer;
        this.riverPatternGeneratorChain = par4GenLayer;
    }
    
    @Override
    public void initWorldGenSeed(final long par1) {
        this.biomePatternGeneratorChain.initWorldGenSeed(par1);
        this.riverPatternGeneratorChain.initWorldGenSeed(par1);
        super.initWorldGenSeed(par1);
    }
    
    @Override
    public int[] getInts(final int par1, final int par2, final int par3, final int par4) {
        final int[] var5 = this.biomePatternGeneratorChain.getInts(par1, par2, par3, par4);
        final int[] var6 = this.riverPatternGeneratorChain.getInts(par1, par2, par3, par4);
        final int[] var7 = IntCache.getIntCache(par3 * par4);
        for (int var8 = 0; var8 < par3 * par4; ++var8) {
            if (var5[var8] == BiomeGenBase.ocean.biomeID) {
                var7[var8] = var5[var8];
            }
            else if (var6[var8] >= 0) {
                if (var5[var8] == BiomeGenBase.icePlains.biomeID) {
                    var7[var8] = BiomeGenBase.frozenRiver.biomeID;
                }
                else if (var5[var8] != BiomeGenBase.mushroomIsland.biomeID && var5[var8] != BiomeGenBase.mushroomIslandShore.biomeID) {
                    var7[var8] = var6[var8];
                }
                else {
                    var7[var8] = BiomeGenBase.mushroomIslandShore.biomeID;
                }
            }
            else {
                var7[var8] = var5[var8];
            }
        }
        return var7;
    }
}
