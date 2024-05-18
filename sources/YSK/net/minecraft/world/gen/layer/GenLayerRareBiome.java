package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerRareBiome extends GenLayer
{
    public GenLayerRareBiome(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n - " ".length(), n2 - " ".length(), n3 + "  ".length(), n4 + "  ".length());
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (4 == 1) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(j + n, i + n2);
                final int n5 = ints[j + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                if (this.nextInt(0x7 ^ 0x3E) == 0) {
                    if (n5 == BiomeGenBase.plains.biomeID) {
                        intCache[j + i * n3] = BiomeGenBase.plains.biomeID + (33 + 96 - 85 + 84);
                        "".length();
                        if (1 >= 2) {
                            throw null;
                        }
                    }
                    else {
                        intCache[j + i * n3] = n5;
                        "".length();
                        if (2 < -1) {
                            throw null;
                        }
                    }
                }
                else {
                    intCache[j + i * n3] = n5;
                }
                ++j;
            }
            ++i;
        }
        return intCache;
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
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
