package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerShore extends GenLayer
{
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_151632_a(final int[] array, final int[] array2, final int n, final int n2, final int n3, final int n4, final int n5) {
        if (GenLayer.isBiomeOceanic(n4)) {
            array2[n + n2 * n3] = n4;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            final int n6 = array[n + " ".length() + (n2 + " ".length() - " ".length()) * (n3 + "  ".length())];
            final int n7 = array[n + " ".length() + " ".length() + (n2 + " ".length()) * (n3 + "  ".length())];
            final int n8 = array[n + " ".length() - " ".length() + (n2 + " ".length()) * (n3 + "  ".length())];
            final int n9 = array[n + " ".length() + (n2 + " ".length() + " ".length()) * (n3 + "  ".length())];
            if (!GenLayer.isBiomeOceanic(n6) && !GenLayer.isBiomeOceanic(n7) && !GenLayer.isBiomeOceanic(n8) && !GenLayer.isBiomeOceanic(n9)) {
                array2[n + n2 * n3] = n4;
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else {
                array2[n + n2 * n3] = n5;
            }
        }
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n - " ".length(), n2 - " ".length(), n3 + "  ".length(), n4 + "  ".length());
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(j + n, i + n2);
                final int n5 = ints[j + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                final BiomeGenBase biome = BiomeGenBase.getBiome(n5);
                if (n5 == BiomeGenBase.mushroomIsland.biomeID) {
                    final int n6 = ints[j + " ".length() + (i + " ".length() - " ".length()) * (n3 + "  ".length())];
                    final int n7 = ints[j + " ".length() + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                    final int n8 = ints[j + " ".length() - " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                    final int n9 = ints[j + " ".length() + (i + " ".length() + " ".length()) * (n3 + "  ".length())];
                    if (n6 != BiomeGenBase.ocean.biomeID && n7 != BiomeGenBase.ocean.biomeID && n8 != BiomeGenBase.ocean.biomeID && n9 != BiomeGenBase.ocean.biomeID) {
                        intCache[j + i * n3] = n5;
                        "".length();
                        if (-1 >= 1) {
                            throw null;
                        }
                    }
                    else {
                        intCache[j + i * n3] = BiomeGenBase.mushroomIslandShore.biomeID;
                        "".length();
                        if (3 < 1) {
                            throw null;
                        }
                    }
                }
                else if (biome != null && biome.getBiomeClass() == BiomeGenJungle.class) {
                    final int n10 = ints[j + " ".length() + (i + " ".length() - " ".length()) * (n3 + "  ".length())];
                    final int n11 = ints[j + " ".length() + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                    final int n12 = ints[j + " ".length() - " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                    final int n13 = ints[j + " ".length() + (i + " ".length() + " ".length()) * (n3 + "  ".length())];
                    if (this.func_151631_c(n10) && this.func_151631_c(n11) && this.func_151631_c(n12) && this.func_151631_c(n13)) {
                        if (!GenLayer.isBiomeOceanic(n10) && !GenLayer.isBiomeOceanic(n11) && !GenLayer.isBiomeOceanic(n12) && !GenLayer.isBiomeOceanic(n13)) {
                            intCache[j + i * n3] = n5;
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                        }
                        else {
                            intCache[j + i * n3] = BiomeGenBase.beach.biomeID;
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                    }
                    else {
                        intCache[j + i * n3] = BiomeGenBase.jungleEdge.biomeID;
                        "".length();
                        if (4 < 0) {
                            throw null;
                        }
                    }
                }
                else if (n5 != BiomeGenBase.extremeHills.biomeID && n5 != BiomeGenBase.extremeHillsPlus.biomeID && n5 != BiomeGenBase.extremeHillsEdge.biomeID) {
                    if (biome != null && biome.isSnowyBiome()) {
                        this.func_151632_a(ints, intCache, j, i, n3, n5, BiomeGenBase.coldBeach.biomeID);
                        "".length();
                        if (4 == -1) {
                            throw null;
                        }
                    }
                    else if (n5 != BiomeGenBase.mesa.biomeID && n5 != BiomeGenBase.mesaPlateau_F.biomeID) {
                        if (n5 != BiomeGenBase.ocean.biomeID && n5 != BiomeGenBase.deepOcean.biomeID && n5 != BiomeGenBase.river.biomeID && n5 != BiomeGenBase.swampland.biomeID) {
                            final int n14 = ints[j + " ".length() + (i + " ".length() - " ".length()) * (n3 + "  ".length())];
                            final int n15 = ints[j + " ".length() + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                            final int n16 = ints[j + " ".length() - " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                            final int n17 = ints[j + " ".length() + (i + " ".length() + " ".length()) * (n3 + "  ".length())];
                            if (!GenLayer.isBiomeOceanic(n14) && !GenLayer.isBiomeOceanic(n15) && !GenLayer.isBiomeOceanic(n16) && !GenLayer.isBiomeOceanic(n17)) {
                                intCache[j + i * n3] = n5;
                                "".length();
                                if (0 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                intCache[j + i * n3] = BiomeGenBase.beach.biomeID;
                                "".length();
                                if (4 <= 1) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            intCache[j + i * n3] = n5;
                            "".length();
                            if (1 >= 3) {
                                throw null;
                            }
                        }
                    }
                    else {
                        final int n18 = ints[j + " ".length() + (i + " ".length() - " ".length()) * (n3 + "  ".length())];
                        final int n19 = ints[j + " ".length() + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                        final int n20 = ints[j + " ".length() - " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                        final int n21 = ints[j + " ".length() + (i + " ".length() + " ".length()) * (n3 + "  ".length())];
                        if (!GenLayer.isBiomeOceanic(n18) && !GenLayer.isBiomeOceanic(n19) && !GenLayer.isBiomeOceanic(n20) && !GenLayer.isBiomeOceanic(n21)) {
                            if (this.func_151633_d(n18) && this.func_151633_d(n19) && this.func_151633_d(n20) && this.func_151633_d(n21)) {
                                intCache[j + i * n3] = n5;
                                "".length();
                                if (-1 >= 4) {
                                    throw null;
                                }
                            }
                            else {
                                intCache[j + i * n3] = BiomeGenBase.desert.biomeID;
                                "".length();
                                if (true != true) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            intCache[j + i * n3] = n5;
                            "".length();
                            if (-1 == 4) {
                                throw null;
                            }
                        }
                    }
                }
                else {
                    this.func_151632_a(ints, intCache, j, i, n3, n5, BiomeGenBase.stoneBeach.biomeID);
                }
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    public GenLayerShore(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
    
    private boolean func_151633_d(final int n) {
        return BiomeGenBase.getBiome(n) instanceof BiomeGenMesa;
    }
    
    private boolean func_151631_c(final int n) {
        int n2;
        if (BiomeGenBase.getBiome(n) != null && BiomeGenBase.getBiome(n).getBiomeClass() == BiomeGenJungle.class) {
            n2 = " ".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else if (n != BiomeGenBase.jungleEdge.biomeID && n != BiomeGenBase.jungle.biomeID && n != BiomeGenBase.jungleHills.biomeID && n != BiomeGenBase.forest.biomeID && n != BiomeGenBase.taiga.biomeID && !GenLayer.isBiomeOceanic(n)) {
            n2 = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        return n2 != 0;
    }
}
