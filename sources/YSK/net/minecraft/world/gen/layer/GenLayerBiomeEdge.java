package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerBiomeEdge extends GenLayer
{
    private boolean replaceBiomeEdgeIfNecessary(final int[] array, final int[] array2, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (!GenLayer.biomesEqualOrMesaPlateau(n4, n5)) {
            return "".length() != 0;
        }
        final int n7 = array[n + " ".length() + (n2 + " ".length() - " ".length()) * (n3 + "  ".length())];
        final int n8 = array[n + " ".length() + " ".length() + (n2 + " ".length()) * (n3 + "  ".length())];
        final int n9 = array[n + " ".length() - " ".length() + (n2 + " ".length()) * (n3 + "  ".length())];
        final int n10 = array[n + " ".length() + (n2 + " ".length() + " ".length()) * (n3 + "  ".length())];
        if (this.canBiomesBeNeighbors(n7, n5) && this.canBiomesBeNeighbors(n8, n5) && this.canBiomesBeNeighbors(n9, n5) && this.canBiomesBeNeighbors(n10, n5)) {
            array2[n + n2 * n3] = n4;
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            array2[n + n2 * n3] = n6;
        }
        return " ".length() != 0;
    }
    
    public GenLayerBiomeEdge(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private boolean canBiomesBeNeighbors(final int n, final int n2) {
        if (GenLayer.biomesEqualOrMesaPlateau(n, n2)) {
            return " ".length() != 0;
        }
        final BiomeGenBase biome = BiomeGenBase.getBiome(n);
        final BiomeGenBase biome2 = BiomeGenBase.getBiome(n2);
        if (biome == null || biome2 == null) {
            return "".length() != 0;
        }
        final BiomeGenBase.TempCategory tempCategory = biome.getTempCategory();
        final BiomeGenBase.TempCategory tempCategory2 = biome2.getTempCategory();
        if (tempCategory != tempCategory2 && tempCategory != BiomeGenBase.TempCategory.MEDIUM && tempCategory2 != BiomeGenBase.TempCategory.MEDIUM) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n - " ".length(), n2 - " ".length(), n3 + "  ".length(), n4 + "  ".length());
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(j + n, i + n2);
                final int n5 = ints[j + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                if (!this.replaceBiomeEdgeIfNecessary(ints, intCache, j, i, n3, n5, BiomeGenBase.extremeHills.biomeID, BiomeGenBase.extremeHillsEdge.biomeID) && !this.replaceBiomeEdge(ints, intCache, j, i, n3, n5, BiomeGenBase.mesaPlateau_F.biomeID, BiomeGenBase.mesa.biomeID) && !this.replaceBiomeEdge(ints, intCache, j, i, n3, n5, BiomeGenBase.mesaPlateau.biomeID, BiomeGenBase.mesa.biomeID) && !this.replaceBiomeEdge(ints, intCache, j, i, n3, n5, BiomeGenBase.megaTaiga.biomeID, BiomeGenBase.taiga.biomeID)) {
                    if (n5 == BiomeGenBase.desert.biomeID) {
                        final int n6 = ints[j + " ".length() + (i + " ".length() - " ".length()) * (n3 + "  ".length())];
                        final int n7 = ints[j + " ".length() + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                        final int n8 = ints[j + " ".length() - " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                        final int n9 = ints[j + " ".length() + (i + " ".length() + " ".length()) * (n3 + "  ".length())];
                        if (n6 != BiomeGenBase.icePlains.biomeID && n7 != BiomeGenBase.icePlains.biomeID && n8 != BiomeGenBase.icePlains.biomeID && n9 != BiomeGenBase.icePlains.biomeID) {
                            intCache[j + i * n3] = n5;
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                        else {
                            intCache[j + i * n3] = BiomeGenBase.extremeHillsPlus.biomeID;
                            "".length();
                            if (1 == -1) {
                                throw null;
                            }
                        }
                    }
                    else if (n5 == BiomeGenBase.swampland.biomeID) {
                        final int n10 = ints[j + " ".length() + (i + " ".length() - " ".length()) * (n3 + "  ".length())];
                        final int n11 = ints[j + " ".length() + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                        final int n12 = ints[j + " ".length() - " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                        final int n13 = ints[j + " ".length() + (i + " ".length() + " ".length()) * (n3 + "  ".length())];
                        if (n10 != BiomeGenBase.desert.biomeID && n11 != BiomeGenBase.desert.biomeID && n12 != BiomeGenBase.desert.biomeID && n13 != BiomeGenBase.desert.biomeID && n10 != BiomeGenBase.coldTaiga.biomeID && n11 != BiomeGenBase.coldTaiga.biomeID && n12 != BiomeGenBase.coldTaiga.biomeID && n13 != BiomeGenBase.coldTaiga.biomeID && n10 != BiomeGenBase.icePlains.biomeID && n11 != BiomeGenBase.icePlains.biomeID && n12 != BiomeGenBase.icePlains.biomeID && n13 != BiomeGenBase.icePlains.biomeID) {
                            if (n10 != BiomeGenBase.jungle.biomeID && n13 != BiomeGenBase.jungle.biomeID && n11 != BiomeGenBase.jungle.biomeID && n12 != BiomeGenBase.jungle.biomeID) {
                                intCache[j + i * n3] = n5;
                                "".length();
                                if (1 >= 2) {
                                    throw null;
                                }
                            }
                            else {
                                intCache[j + i * n3] = BiomeGenBase.jungleEdge.biomeID;
                                "".length();
                                if (4 == 3) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            intCache[j + i * n3] = BiomeGenBase.plains.biomeID;
                            "".length();
                            if (3 == 0) {
                                throw null;
                            }
                        }
                    }
                    else {
                        intCache[j + i * n3] = n5;
                    }
                }
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    private boolean replaceBiomeEdge(final int[] array, final int[] array2, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n4 != n5) {
            return "".length() != 0;
        }
        final int n7 = array[n + " ".length() + (n2 + " ".length() - " ".length()) * (n3 + "  ".length())];
        final int n8 = array[n + " ".length() + " ".length() + (n2 + " ".length()) * (n3 + "  ".length())];
        final int n9 = array[n + " ".length() - " ".length() + (n2 + " ".length()) * (n3 + "  ".length())];
        final int n10 = array[n + " ".length() + (n2 + " ".length() + " ".length()) * (n3 + "  ".length())];
        if (GenLayer.biomesEqualOrMesaPlateau(n7, n5) && GenLayer.biomesEqualOrMesaPlateau(n8, n5) && GenLayer.biomesEqualOrMesaPlateau(n9, n5) && GenLayer.biomesEqualOrMesaPlateau(n10, n5)) {
            array2[n + n2 * n3] = n4;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            array2[n + n2 * n3] = n6;
        }
        return " ".length() != 0;
    }
}
