package net.minecraft.world.gen.layer;

import org.apache.logging.log4j.*;
import net.minecraft.world.biome.*;

public class GenLayerHills extends GenLayer
{
    private GenLayer field_151628_d;
    private static final String[] I;
    private static final Logger logger;
    
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("9\"\u0017TO", "VNsuo");
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n - " ".length(), n2 - " ".length(), n3 + "  ".length(), n4 + "  ".length());
        final int[] ints2 = this.field_151628_d.getInts(n - " ".length(), n2 - " ".length(), n3 + "  ".length(), n4 + "  ".length());
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(j + n, i + n2);
                final int n5 = ints[j + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                final int n6 = ints2[j + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                int n7;
                if ((n6 - "  ".length()) % (0x9C ^ 0x81) == 0) {
                    n7 = " ".length();
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
                else {
                    n7 = "".length();
                }
                final int n8 = n7;
                if (n5 > 185 + 161 - 285 + 194) {
                    GenLayerHills.logger.debug(GenLayerHills.I["".length()] + n5);
                }
                if (n5 != 0 && n6 >= "  ".length() && (n6 - "  ".length()) % (0x26 ^ 0x3B) == " ".length() && n5 < 32 + 117 - 134 + 113) {
                    if (BiomeGenBase.getBiome(n5 + (105 + 7 - 35 + 51)) != null) {
                        intCache[j + i * n3] = n5 + (23 + 98 - 20 + 27);
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        intCache[j + i * n3] = n5;
                        "".length();
                        if (3 == 2) {
                            throw null;
                        }
                    }
                }
                else if (this.nextInt("   ".length()) != 0 && n8 == 0) {
                    intCache[j + i * n3] = n5;
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else {
                    int n9;
                    if ((n9 = n5) == BiomeGenBase.desert.biomeID) {
                        n9 = BiomeGenBase.desertHills.biomeID;
                        "".length();
                        if (3 == 2) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.forest.biomeID) {
                        n9 = BiomeGenBase.forestHills.biomeID;
                        "".length();
                        if (!true) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.birchForest.biomeID) {
                        n9 = BiomeGenBase.birchForestHills.biomeID;
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.roofedForest.biomeID) {
                        n9 = BiomeGenBase.plains.biomeID;
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.taiga.biomeID) {
                        n9 = BiomeGenBase.taigaHills.biomeID;
                        "".length();
                        if (3 <= 0) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.megaTaiga.biomeID) {
                        n9 = BiomeGenBase.megaTaigaHills.biomeID;
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.coldTaiga.biomeID) {
                        n9 = BiomeGenBase.coldTaigaHills.biomeID;
                        "".length();
                        if (1 >= 3) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.plains.biomeID) {
                        if (this.nextInt("   ".length()) == 0) {
                            n9 = BiomeGenBase.forestHills.biomeID;
                            "".length();
                            if (4 < -1) {
                                throw null;
                            }
                        }
                        else {
                            n9 = BiomeGenBase.forest.biomeID;
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                    }
                    else if (n5 == BiomeGenBase.icePlains.biomeID) {
                        n9 = BiomeGenBase.iceMountains.biomeID;
                        "".length();
                        if (2 < 2) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.jungle.biomeID) {
                        n9 = BiomeGenBase.jungleHills.biomeID;
                        "".length();
                        if (2 >= 4) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.ocean.biomeID) {
                        n9 = BiomeGenBase.deepOcean.biomeID;
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.extremeHills.biomeID) {
                        n9 = BiomeGenBase.extremeHillsPlus.biomeID;
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.savanna.biomeID) {
                        n9 = BiomeGenBase.savannaPlateau.biomeID;
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                    else if (GenLayer.biomesEqualOrMesaPlateau(n5, BiomeGenBase.mesaPlateau_F.biomeID)) {
                        n9 = BiomeGenBase.mesa.biomeID;
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                    }
                    else if (n5 == BiomeGenBase.deepOcean.biomeID && this.nextInt("   ".length()) == 0) {
                        if (this.nextInt("  ".length()) == 0) {
                            n9 = BiomeGenBase.plains.biomeID;
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else {
                            n9 = BiomeGenBase.forest.biomeID;
                        }
                    }
                    if (n8 != 0 && n9 != n5) {
                        if (BiomeGenBase.getBiome(n9 + (47 + 82 - 15 + 14)) != null) {
                            n9 += 128;
                            "".length();
                            if (3 < 2) {
                                throw null;
                            }
                        }
                        else {
                            n9 = n5;
                        }
                    }
                    if (n9 == n5) {
                        intCache[j + i * n3] = n5;
                        "".length();
                        if (4 < 2) {
                            throw null;
                        }
                    }
                    else {
                        final int n10 = ints[j + " ".length() + (i + " ".length() - " ".length()) * (n3 + "  ".length())];
                        final int n11 = ints[j + " ".length() + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                        final int n12 = ints[j + " ".length() - " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                        final int n13 = ints[j + " ".length() + (i + " ".length() + " ".length()) * (n3 + "  ".length())];
                        int length = "".length();
                        if (GenLayer.biomesEqualOrMesaPlateau(n10, n5)) {
                            ++length;
                        }
                        if (GenLayer.biomesEqualOrMesaPlateau(n11, n5)) {
                            ++length;
                        }
                        if (GenLayer.biomesEqualOrMesaPlateau(n12, n5)) {
                            ++length;
                        }
                        if (GenLayer.biomesEqualOrMesaPlateau(n13, n5)) {
                            ++length;
                        }
                        if (length >= "   ".length()) {
                            intCache[j + i * n3] = n9;
                            "".length();
                            if (1 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            intCache[j + i * n3] = n5;
                        }
                    }
                }
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    public GenLayerHills(final long n, final GenLayer parent, final GenLayer field_151628_d) {
        super(n);
        this.parent = parent;
        this.field_151628_d = field_151628_d;
    }
}
