package net.minecraft.src;

import java.util.*;

public class MapGenVillage extends MapGenStructure
{
    public static final List villageSpawnBiomes;
    private int terrainType;
    private int field_82665_g;
    private int field_82666_h;
    
    static {
        villageSpawnBiomes = Arrays.asList(BiomeGenBase.plains, BiomeGenBase.desert);
    }
    
    public MapGenVillage() {
        this.terrainType = 0;
        this.field_82665_g = 32;
        this.field_82666_h = 8;
    }
    
    public MapGenVillage(final Map par1Map) {
        this();
        for (final Map.Entry var3 : par1Map.entrySet()) {
            if (var3.getKey().equals("size")) {
                this.terrainType = MathHelper.parseIntWithDefaultAndMax(var3.getValue(), this.terrainType, 0);
            }
            else {
                if (!var3.getKey().equals("distance")) {
                    continue;
                }
                this.field_82665_g = MathHelper.parseIntWithDefaultAndMax(var3.getValue(), this.field_82665_g, this.field_82666_h + 1);
            }
        }
    }
    
    @Override
    protected boolean canSpawnStructureAtCoords(int par1, int par2) {
        final int var3 = par1;
        final int var4 = par2;
        if (par1 < 0) {
            par1 -= this.field_82665_g - 1;
        }
        if (par2 < 0) {
            par2 -= this.field_82665_g - 1;
        }
        int var5 = par1 / this.field_82665_g;
        int var6 = par2 / this.field_82665_g;
        final Random var7 = this.worldObj.setRandomSeed(var5, var6, 10387312);
        var5 *= this.field_82665_g;
        var6 *= this.field_82665_g;
        var5 += var7.nextInt(this.field_82665_g - this.field_82666_h);
        var6 += var7.nextInt(this.field_82665_g - this.field_82666_h);
        if (var3 == var5 && var4 == var6) {
            final boolean var8 = this.worldObj.getWorldChunkManager().areBiomesViable(var3 * 16 + 8, var4 * 16 + 8, 0, MapGenVillage.villageSpawnBiomes);
            if (var8) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected StructureStart getStructureStart(final int par1, final int par2) {
        return new StructureVillageStart(this.worldObj, this.rand, par1, par2, this.terrainType);
    }
}
