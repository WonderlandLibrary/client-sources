package net.minecraft.src;

public abstract class GenLayer
{
    private long worldGenSeed;
    protected GenLayer parent;
    private long chunkSeed;
    private long baseSeed;
    
    public static GenLayer[] initializeAllBiomeGenerators(final long par0, final WorldType par2WorldType) {
        final GenLayerIsland var3 = new GenLayerIsland(1L);
        final GenLayerFuzzyZoom var4 = new GenLayerFuzzyZoom(2000L, var3);
        GenLayerAddIsland var5 = new GenLayerAddIsland(1L, var4);
        GenLayerZoom var6 = new GenLayerZoom(2001L, var5);
        var5 = new GenLayerAddIsland(2L, var6);
        final GenLayerAddSnow var7 = new GenLayerAddSnow(2L, var5);
        var6 = new GenLayerZoom(2002L, var7);
        var5 = new GenLayerAddIsland(3L, var6);
        var6 = new GenLayerZoom(2003L, var5);
        var5 = new GenLayerAddIsland(4L, var6);
        final GenLayerAddMushroomIsland var8 = new GenLayerAddMushroomIsland(5L, var5);
        byte var9 = 4;
        if (par2WorldType == WorldType.LARGE_BIOMES) {
            var9 = 6;
        }
        GenLayer var10 = GenLayerZoom.magnify(1000L, var8, 0);
        final GenLayerRiverInit var11 = new GenLayerRiverInit(100L, var10);
        var10 = GenLayerZoom.magnify(1000L, var11, var9 + 2);
        final GenLayerRiver var12 = new GenLayerRiver(1L, var10);
        final GenLayerSmooth var13 = new GenLayerSmooth(1000L, var12);
        GenLayer var14 = GenLayerZoom.magnify(1000L, var8, 0);
        final GenLayerBiome var15 = new GenLayerBiome(200L, var14, par2WorldType);
        var14 = GenLayerZoom.magnify(1000L, var15, 2);
        Object var16 = new GenLayerHills(1000L, var14);
        for (int var17 = 0; var17 < var9; ++var17) {
            var16 = new GenLayerZoom(1000 + var17, (GenLayer)var16);
            if (var17 == 0) {
                var16 = new GenLayerAddIsland(3L, (GenLayer)var16);
            }
            if (var17 == 1) {
                var16 = new GenLayerShore(1000L, (GenLayer)var16);
            }
            if (var17 == 1) {
                var16 = new GenLayerSwampRivers(1000L, (GenLayer)var16);
            }
        }
        final GenLayerSmooth var18 = new GenLayerSmooth(1000L, (GenLayer)var16);
        final GenLayerRiverMix var19 = new GenLayerRiverMix(100L, var18, var13);
        final GenLayerVoronoiZoom var20 = new GenLayerVoronoiZoom(10L, var19);
        var19.initWorldGenSeed(par0);
        var20.initWorldGenSeed(par0);
        return new GenLayer[] { var19, var20, var19 };
    }
    
    public GenLayer(final long par1) {
        this.baseSeed = par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += par1;
    }
    
    public void initWorldGenSeed(final long par1) {
        this.worldGenSeed = par1;
        if (this.parent != null) {
            this.parent.initWorldGenSeed(par1);
        }
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }
    
    public void initChunkSeed(final long par1, final long par3) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par3;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par1;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += par3;
    }
    
    protected int nextInt(final int par1) {
        int var2 = (int)((this.chunkSeed >> 24) % par1);
        if (var2 < 0) {
            var2 += par1;
        }
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return var2;
    }
    
    public abstract int[] getInts(final int p0, final int p1, final int p2, final int p3);
}
