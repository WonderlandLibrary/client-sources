package net.minecraft.src;

import java.util.*;

public class WorldChunkManagerHell extends WorldChunkManager
{
    private BiomeGenBase biomeGenerator;
    private float hellTemperature;
    private float rainfall;
    
    public WorldChunkManagerHell(final BiomeGenBase par1BiomeGenBase, final float par2, final float par3) {
        this.biomeGenerator = par1BiomeGenBase;
        this.hellTemperature = par2;
        this.rainfall = par3;
    }
    
    @Override
    public BiomeGenBase getBiomeGenAt(final int par1, final int par2) {
        return this.biomeGenerator;
    }
    
    @Override
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, final int par2, final int par3, final int par4, final int par5) {
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }
        Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeGenerator);
        return par1ArrayOfBiomeGenBase;
    }
    
    @Override
    public float[] getTemperatures(float[] par1ArrayOfFloat, final int par2, final int par3, final int par4, final int par5) {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }
        Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.hellTemperature);
        return par1ArrayOfFloat;
    }
    
    @Override
    public float[] getRainfall(float[] par1ArrayOfFloat, final int par2, final int par3, final int par4, final int par5) {
        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5) {
            par1ArrayOfFloat = new float[par4 * par5];
        }
        Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, this.rainfall);
        return par1ArrayOfFloat;
    }
    
    @Override
    public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, final int par2, final int par3, final int par4, final int par5) {
        if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5) {
            par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
        }
        Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, this.biomeGenerator);
        return par1ArrayOfBiomeGenBase;
    }
    
    @Override
    public BiomeGenBase[] getBiomeGenAt(final BiomeGenBase[] par1ArrayOfBiomeGenBase, final int par2, final int par3, final int par4, final int par5, final boolean par6) {
        return this.loadBlockGeneratorData(par1ArrayOfBiomeGenBase, par2, par3, par4, par5);
    }
    
    @Override
    public ChunkPosition findBiomePosition(final int par1, final int par2, final int par3, final List par4List, final Random par5Random) {
        return par4List.contains(this.biomeGenerator) ? new ChunkPosition(par1 - par3 + par5Random.nextInt(par3 * 2 + 1), 0, par2 - par3 + par5Random.nextInt(par3 * 2 + 1)) : null;
    }
    
    @Override
    public boolean areBiomesViable(final int par1, final int par2, final int par3, final List par4List) {
        return par4List.contains(this.biomeGenerator);
    }
}
