package net.minecraft.world.gen.layer;

import net.minecraft.world.biome.*;

public class GenLayerRiver extends GenLayer
{
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n - " ".length();
        final int n6 = n2 - " ".length();
        final int n7 = n3 + "  ".length();
        final int[] ints = this.parent.getInts(n5, n6, n7, n4 + "  ".length());
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (j < n3) {
                final int func_151630_c = this.func_151630_c(ints[j + "".length() + (i + " ".length()) * n7]);
                final int func_151630_c2 = this.func_151630_c(ints[j + "  ".length() + (i + " ".length()) * n7]);
                final int func_151630_c3 = this.func_151630_c(ints[j + " ".length() + (i + "".length()) * n7]);
                final int func_151630_c4 = this.func_151630_c(ints[j + " ".length() + (i + "  ".length()) * n7]);
                final int func_151630_c5 = this.func_151630_c(ints[j + " ".length() + (i + " ".length()) * n7]);
                if (func_151630_c5 == func_151630_c && func_151630_c5 == func_151630_c3 && func_151630_c5 == func_151630_c2 && func_151630_c5 == func_151630_c4) {
                    intCache[j + i * n3] = -" ".length();
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                }
                else {
                    intCache[j + i * n3] = BiomeGenBase.river.biomeID;
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private int func_151630_c(final int n) {
        int n2;
        if (n >= "  ".length()) {
            n2 = "  ".length() + (n & " ".length());
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n2 = n;
        }
        return n2;
    }
    
    public GenLayerRiver(final long n, final GenLayer parent) {
        super(n);
        super.parent = parent;
    }
}
