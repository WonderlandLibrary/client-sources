package net.minecraft.src;

import java.util.*;

public class MapGenScatteredFeature extends MapGenStructure
{
    private static List biomelist;
    private List scatteredFeatureSpawnList;
    private int maxDistanceBetweenScatteredFeatures;
    private int minDistanceBetweenScatteredFeatures;
    
    static {
        MapGenScatteredFeature.biomelist = Arrays.asList(BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland);
    }
    
    public MapGenScatteredFeature() {
        this.scatteredFeatureSpawnList = new ArrayList();
        this.maxDistanceBetweenScatteredFeatures = 32;
        this.minDistanceBetweenScatteredFeatures = 8;
        this.scatteredFeatureSpawnList.add(new SpawnListEntry(EntityWitch.class, 1, 1, 1));
    }
    
    public MapGenScatteredFeature(final Map par1Map) {
        this();
        for (final Map.Entry var3 : par1Map.entrySet()) {
            if (var3.getKey().equals("distance")) {
                this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax(var3.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
            }
        }
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int par1, int par2) {
        final int var3 = par1;
        final int var4 = par2;
        if (par1 < 0) {
            par1 -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        if (par2 < 0) {
            par2 -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        int var5 = par1 / this.maxDistanceBetweenScatteredFeatures;
        int var6 = par2 / this.maxDistanceBetweenScatteredFeatures;
        final Random var7 = this.worldObj.setRandomSeed(var5, var6, 14357617);
        var5 *= this.maxDistanceBetweenScatteredFeatures;
        var6 *= this.maxDistanceBetweenScatteredFeatures;
        var5 += var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        var6 += var7.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        if (var3 == var5 && var4 == var6) {
            final BiomeGenBase var8 = this.worldObj.getWorldChunkManager().getBiomeGenAt(var3 * 16 + 8, var4 * 16 + 8);
            for (final BiomeGenBase var10 : MapGenScatteredFeature.biomelist) {
                if (var8 == var10) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    protected StructureStart getStructureStart(final int par1, final int par2) {
        return new StructureScatteredFeatureStart(this.worldObj, this.rand, par1, par2);
    }
    
    public List getScatteredFeatureSpawnList() {
        return this.scatteredFeatureSpawnList;
    }
}
