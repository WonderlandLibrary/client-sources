package net.minecraft.src;

import java.util.*;

public class WorldChunkManager
{
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;
    private BiomeCache biomeCache;
    private List biomesToSpawnIn;
    
    protected WorldChunkManager() {
        this.biomeCache = new BiomeCache(this);
        (this.biomesToSpawnIn = new ArrayList()).add(BiomeGenBase.forest);
        this.biomesToSpawnIn.add(BiomeGenBase.plains);
        this.biomesToSpawnIn.add(BiomeGenBase.taiga);
        this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
        this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
        this.biomesToSpawnIn.add(BiomeGenBase.jungle);
        this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
    }
    
    public WorldChunkManager(final long par1, final WorldType par3WorldType) {
        this();
        final GenLayer[] var4 = GenLayer.initializeAllBiomeGenerators(par1, par3WorldType);
        this.genBiomes = var4[0];
        this.biomeIndexLayer = var4[1];
    }
    
    public WorldChunkManager(final World par1World) {
        this(par1World.getSeed(), par1World.getWorldInfo().getTerrainType());
    }
    
    public List getBiomesToSpawnIn() {
        return this.biomesToSpawnIn;
    }
    
    public BiomeGenBase getBiomeGenAt(final int par1, final int par2) {
        return this.biomeCache.getBiomeGenAt(par1, par2);
    }
    
    public float[] getRainfall(float[] par1ArrayOfFloat, final int par2, final int par3, final int par4, final int par5) {
        IntCache.resetIntCache();
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }
        final int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
        for (int var7 = 0; var7 < par4 * par5; ++var7) {
            float var8 = BiomeGenBase.biomeList[var6[var7]].getIntRainfall() / 65536.0f;
            if (var8 > 1.0f) {
                var8 = 1.0f;
            }
            par1ArrayOfFloat[var7] = var8;
        }
        return par1ArrayOfFloat;
    }
    
    public float getTemperatureAtHeight(final float par1, final int par2) {
        return par1;
    }
    
    public float[] getTemperatures(float[] par1ArrayOfFloat, final int par2, final int par3, final int par4, final int par5) {
        IntCache.resetIntCache();
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }
        final int[] var6 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
        for (int var7 = 0; var7 < par4 * par5; ++var7) {
            float var8 = BiomeGenBase.biomeList[var6[var7]].getIntTemperature() / 65536.0f;
            if (var8 > 1.0f) {
                var8 = 1.0f;
            }
            par1ArrayOfFloat[var7] = var8;
        }
        return par1ArrayOfFloat;
    }
    
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, final int par2, final int par3, final int par4, final int par5) {
        IntCache.resetIntCache();
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }
        final int[] var6 = this.genBiomes.getInts(par2, par3, par4, par5);
        for (int var7 = 0; var7 < par4 * par5; ++var7) {
            par1ArrayOfBiomeGenBase[var7] = BiomeGenBase.biomeList[var6[var7]];
        }
        return par1ArrayOfBiomeGenBase;
    }
    
    public BiomeGenBase[] loadBlockGeneratorData(final BiomeGenBase[] par1ArrayOfBiomeGenBase, final int par2, final int par3, final int par4, final int par5) {
        return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
    }
    
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, final int par2, final int par3, final int par4, final int par5, final boolean par6) {
        IntCache.resetIntCache();
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }
        if (par6 && par4 == 16 && par5 == 16 && (par2 & 0xF) == 0x0 && (par3 & 0xF) == 0x0) {
            final BiomeGenBase[] var9 = this.biomeCache.getCachedBiomes(par2, par3);
            System.arraycopy(var9, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
            return par1ArrayOfBiomeGenBase;
        }
        final int[] var10 = this.biomeIndexLayer.getInts(par2, par3, par4, par5);
        for (int var11 = 0; var11 < par4 * par5; ++var11) {
            par1ArrayOfBiomeGenBase[var11] = BiomeGenBase.biomeList[var10[var11]];
        }
        return par1ArrayOfBiomeGenBase;
    }
    
    public boolean areBiomesViable(final int par1, final int par2, final int par3, final List par4List) {
        IntCache.resetIntCache();
        final int var5 = par1 - par3 >> 2;
        final int var6 = par2 - par3 >> 2;
        final int var7 = par1 + par3 >> 2;
        final int var8 = par2 + par3 >> 2;
        final int var9 = var7 - var5 + 1;
        final int var10 = var8 - var6 + 1;
        final int[] var11 = this.genBiomes.getInts(var5, var6, var9, var10);
        for (int var12 = 0; var12 < var9 * var10; ++var12) {
            final BiomeGenBase var13 = BiomeGenBase.biomeList[var11[var12]];
            if (!par4List.contains(var13)) {
                return false;
            }
        }
        return true;
    }
    
    public ChunkPosition findBiomePosition(final int par1, final int par2, final int par3, final List par4List, final Random par5Random) {
        IntCache.resetIntCache();
        final int var6 = par1 - par3 >> 2;
        final int var7 = par2 - par3 >> 2;
        final int var8 = par1 + par3 >> 2;
        final int var9 = par2 + par3 >> 2;
        final int var10 = var8 - var6 + 1;
        final int var11 = var9 - var7 + 1;
        final int[] var12 = this.genBiomes.getInts(var6, var7, var10, var11);
        ChunkPosition var13 = null;
        int var14 = 0;
        for (int var15 = 0; var15 < var10 * var11; ++var15) {
            final int var16 = var6 + var15 % var10 << 2;
            final int var17 = var7 + var15 / var10 << 2;
            final BiomeGenBase var18 = BiomeGenBase.biomeList[var12[var15]];
            if (par4List.contains(var18) && (var13 == null || par5Random.nextInt(var14 + 1) == 0)) {
                var13 = new ChunkPosition(var16, 0, var17);
                ++var14;
            }
        }
        return var13;
    }
    
    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }
}
