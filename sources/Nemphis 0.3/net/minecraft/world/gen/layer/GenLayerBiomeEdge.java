/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeEdge
extends GenLayer {
    private static final String __OBFID = "CL_00000554";

    public GenLayerBiomeEdge(long p_i45475_1_, GenLayer p_i45475_3_) {
        super(p_i45475_1_);
        this.parent = p_i45475_3_;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] var5 = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
        int[] var6 = IntCache.getIntCache(areaWidth * areaHeight);
        int var7 = 0;
        while (var7 < areaHeight) {
            int var8 = 0;
            while (var8 < areaWidth) {
                this.initChunkSeed(var8 + areaX, var7 + areaY);
                int var9 = var5[var8 + 1 + (var7 + 1) * (areaWidth + 2)];
                if (!(this.replaceBiomeEdgeIfNecessary(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID) || this.replaceBiomeEdge(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.mesaPlateau_F.biomeID, BiomeGenBase.mesa.biomeID) || this.replaceBiomeEdge(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.mesaPlateau.biomeID, BiomeGenBase.mesa.biomeID) || this.replaceBiomeEdge(var5, var6, var8, var7, areaWidth, var9, BiomeGenBase.megaTaiga.biomeID, BiomeGenBase.taiga.biomeID))) {
                    int var11;
                    int var13;
                    int var12;
                    int var10;
                    if (var9 == BiomeGenBase.desert.biomeID) {
                        var10 = var5[var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2)];
                        var11 = var5[var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2)];
                        var12 = var5[var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2)];
                        var13 = var5[var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2)];
                        var6[var8 + var7 * areaWidth] = var10 != BiomeGenBase.icePlains.biomeID && var11 != BiomeGenBase.icePlains.biomeID && var12 != BiomeGenBase.icePlains.biomeID && var13 != BiomeGenBase.icePlains.biomeID ? var9 : BiomeGenBase.extremeHillsPlus.biomeID;
                    } else if (var9 == BiomeGenBase.swampland.biomeID) {
                        var10 = var5[var8 + 1 + (var7 + 1 - 1) * (areaWidth + 2)];
                        var11 = var5[var8 + 1 + 1 + (var7 + 1) * (areaWidth + 2)];
                        var12 = var5[var8 + 1 - 1 + (var7 + 1) * (areaWidth + 2)];
                        var13 = var5[var8 + 1 + (var7 + 1 + 1) * (areaWidth + 2)];
                        var6[var8 + var7 * areaWidth] = var10 != BiomeGenBase.desert.biomeID && var11 != BiomeGenBase.desert.biomeID && var12 != BiomeGenBase.desert.biomeID && var13 != BiomeGenBase.desert.biomeID && var10 != BiomeGenBase.coldTaiga.biomeID && var11 != BiomeGenBase.coldTaiga.biomeID && var12 != BiomeGenBase.coldTaiga.biomeID && var13 != BiomeGenBase.coldTaiga.biomeID && var10 != BiomeGenBase.icePlains.biomeID && var11 != BiomeGenBase.icePlains.biomeID && var12 != BiomeGenBase.icePlains.biomeID && var13 != BiomeGenBase.icePlains.biomeID ? (var10 != BiomeGenBase.jungle.biomeID && var13 != BiomeGenBase.jungle.biomeID && var11 != BiomeGenBase.jungle.biomeID && var12 != BiomeGenBase.jungle.biomeID ? var9 : BiomeGenBase.jungleEdge.biomeID) : BiomeGenBase.plains.biomeID;
                    } else {
                        var6[var8 + var7 * areaWidth] = var9;
                    }
                }
                ++var8;
            }
            ++var7;
        }
        return var6;
    }

    private boolean replaceBiomeEdgeIfNecessary(int[] p_151636_1_, int[] p_151636_2_, int p_151636_3_, int p_151636_4_, int p_151636_5_, int p_151636_6_, int p_151636_7_, int p_151636_8_) {
        if (!GenLayerBiomeEdge.biomesEqualOrMesaPlateau(p_151636_6_, p_151636_7_)) {
            return false;
        }
        int var9 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2)];
        int var10 = p_151636_1_[p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
        int var11 = p_151636_1_[p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
        int var12 = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2)];
        p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = this.canBiomesBeNeighbors(var9, p_151636_7_) && this.canBiomesBeNeighbors(var10, p_151636_7_) && this.canBiomesBeNeighbors(var11, p_151636_7_) && this.canBiomesBeNeighbors(var12, p_151636_7_) ? p_151636_6_ : p_151636_8_;
        return true;
    }

    private boolean replaceBiomeEdge(int[] p_151635_1_, int[] p_151635_2_, int p_151635_3_, int p_151635_4_, int p_151635_5_, int p_151635_6_, int p_151635_7_, int p_151635_8_) {
        if (p_151635_6_ != p_151635_7_) {
            return false;
        }
        int var9 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2)];
        int var10 = p_151635_1_[p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
        int var11 = p_151635_1_[p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
        int var12 = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2)];
        p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = GenLayerBiomeEdge.biomesEqualOrMesaPlateau(var9, p_151635_7_) && GenLayerBiomeEdge.biomesEqualOrMesaPlateau(var10, p_151635_7_) && GenLayerBiomeEdge.biomesEqualOrMesaPlateau(var11, p_151635_7_) && GenLayerBiomeEdge.biomesEqualOrMesaPlateau(var12, p_151635_7_) ? p_151635_6_ : p_151635_8_;
        return true;
    }

    private boolean canBiomesBeNeighbors(int p_151634_1_, int p_151634_2_) {
        if (GenLayerBiomeEdge.biomesEqualOrMesaPlateau(p_151634_1_, p_151634_2_)) {
            return true;
        }
        BiomeGenBase var3 = BiomeGenBase.getBiome(p_151634_1_);
        BiomeGenBase var4 = BiomeGenBase.getBiome(p_151634_2_);
        if (var3 != null && var4 != null) {
            BiomeGenBase.TempCategory var6;
            BiomeGenBase.TempCategory var5 = var3.getTempCategory();
            if (var5 != (var6 = var4.getTempCategory()) && var5 != BiomeGenBase.TempCategory.MEDIUM && var6 != BiomeGenBase.TempCategory.MEDIUM) {
                return false;
            }
            return true;
        }
        return false;
    }
}

