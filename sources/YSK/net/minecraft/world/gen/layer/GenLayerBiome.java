package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.*;

public class GenLayerBiome extends GenLayer
{
    private BiomeGenBase[] field_151623_c;
    private final ChunkProviderSettings field_175973_g;
    private BiomeGenBase[] field_151621_d;
    private BiomeGenBase[] field_151620_f;
    private BiomeGenBase[] field_151622_e;
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n, n2, n3, n4);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(j + n, i + n2);
                final int n5 = ints[j + i * n3];
                final int n6 = (n5 & 3024 + 275 - 2510 + 3051) >> (0x54 ^ 0x5C);
                final int n7 = n5 & -(2276 + 1482 - 1964 + 2047);
                if (this.field_175973_g != null && this.field_175973_g.fixedBiome >= 0) {
                    intCache[j + i * n3] = this.field_175973_g.fixedBiome;
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else if (GenLayer.isBiomeOceanic(n7)) {
                    intCache[j + i * n3] = n7;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else if (n7 == BiomeGenBase.mushroomIsland.biomeID) {
                    intCache[j + i * n3] = n7;
                    "".length();
                    if (1 == 2) {
                        throw null;
                    }
                }
                else if (n7 == " ".length()) {
                    if (n6 > 0) {
                        if (this.nextInt("   ".length()) == 0) {
                            intCache[j + i * n3] = BiomeGenBase.mesaPlateau.biomeID;
                            "".length();
                            if (0 >= 4) {
                                throw null;
                            }
                        }
                        else {
                            intCache[j + i * n3] = BiomeGenBase.mesaPlateau_F.biomeID;
                            "".length();
                            if (3 < 3) {
                                throw null;
                            }
                        }
                    }
                    else {
                        intCache[j + i * n3] = this.field_151623_c[this.nextInt(this.field_151623_c.length)].biomeID;
                        "".length();
                        if (4 < 4) {
                            throw null;
                        }
                    }
                }
                else if (n7 == "  ".length()) {
                    if (n6 > 0) {
                        intCache[j + i * n3] = BiomeGenBase.jungle.biomeID;
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                    else {
                        intCache[j + i * n3] = this.field_151621_d[this.nextInt(this.field_151621_d.length)].biomeID;
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                }
                else if (n7 == "   ".length()) {
                    if (n6 > 0) {
                        intCache[j + i * n3] = BiomeGenBase.megaTaiga.biomeID;
                        "".length();
                        if (4 <= 2) {
                            throw null;
                        }
                    }
                    else {
                        intCache[j + i * n3] = this.field_151622_e[this.nextInt(this.field_151622_e.length)].biomeID;
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                }
                else if (n7 == (0xB ^ 0xF)) {
                    intCache[j + i * n3] = this.field_151620_f[this.nextInt(this.field_151620_f.length)].biomeID;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    intCache[j + i * n3] = BiomeGenBase.mushroomIsland.biomeID;
                }
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    public GenLayerBiome(final long n, final GenLayer parent, final WorldType worldType, final String s) {
        super(n);
        final BiomeGenBase[] field_151623_c = new BiomeGenBase[0x8D ^ 0x8B];
        field_151623_c["".length()] = BiomeGenBase.desert;
        field_151623_c[" ".length()] = BiomeGenBase.desert;
        field_151623_c["  ".length()] = BiomeGenBase.desert;
        field_151623_c["   ".length()] = BiomeGenBase.savanna;
        field_151623_c[0xC3 ^ 0xC7] = BiomeGenBase.savanna;
        field_151623_c[0xA1 ^ 0xA4] = BiomeGenBase.plains;
        this.field_151623_c = field_151623_c;
        final BiomeGenBase[] field_151621_d = new BiomeGenBase[0x5C ^ 0x5A];
        field_151621_d["".length()] = BiomeGenBase.forest;
        field_151621_d[" ".length()] = BiomeGenBase.roofedForest;
        field_151621_d["  ".length()] = BiomeGenBase.extremeHills;
        field_151621_d["   ".length()] = BiomeGenBase.plains;
        field_151621_d[0xC0 ^ 0xC4] = BiomeGenBase.birchForest;
        field_151621_d[0xB9 ^ 0xBC] = BiomeGenBase.swampland;
        this.field_151621_d = field_151621_d;
        final BiomeGenBase[] field_151622_e = new BiomeGenBase[0x92 ^ 0x96];
        field_151622_e["".length()] = BiomeGenBase.forest;
        field_151622_e[" ".length()] = BiomeGenBase.extremeHills;
        field_151622_e["  ".length()] = BiomeGenBase.taiga;
        field_151622_e["   ".length()] = BiomeGenBase.plains;
        this.field_151622_e = field_151622_e;
        final BiomeGenBase[] field_151620_f = new BiomeGenBase[0xA7 ^ 0xA3];
        field_151620_f["".length()] = BiomeGenBase.icePlains;
        field_151620_f[" ".length()] = BiomeGenBase.icePlains;
        field_151620_f["  ".length()] = BiomeGenBase.icePlains;
        field_151620_f["   ".length()] = BiomeGenBase.coldTaiga;
        this.field_151620_f = field_151620_f;
        this.parent = parent;
        if (worldType == WorldType.DEFAULT_1_1) {
            final BiomeGenBase[] field_151623_c2 = new BiomeGenBase[0x9F ^ 0x99];
            field_151623_c2["".length()] = BiomeGenBase.desert;
            field_151623_c2[" ".length()] = BiomeGenBase.forest;
            field_151623_c2["  ".length()] = BiomeGenBase.extremeHills;
            field_151623_c2["   ".length()] = BiomeGenBase.swampland;
            field_151623_c2[0xAE ^ 0xAA] = BiomeGenBase.plains;
            field_151623_c2[0x50 ^ 0x55] = BiomeGenBase.taiga;
            this.field_151623_c = field_151623_c2;
            this.field_175973_g = null;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else if (worldType == WorldType.CUSTOMIZED) {
            this.field_175973_g = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            this.field_175973_g = null;
        }
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
