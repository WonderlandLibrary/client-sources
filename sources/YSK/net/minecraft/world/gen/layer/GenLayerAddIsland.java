package net.minecraft.world.gen.layer;

public class GenLayerAddIsland extends GenLayer
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GenLayerAddIsland(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n - " ".length();
        final int n6 = n2 - " ".length();
        final int n7 = n3 + "  ".length();
        final int[] ints = this.parent.getInts(n5, n6, n7, n4 + "  ".length());
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (j < n3) {
                final int n8 = ints[j + "".length() + (i + "".length()) * n7];
                final int n9 = ints[j + "  ".length() + (i + "".length()) * n7];
                final int n10 = ints[j + "".length() + (i + "  ".length()) * n7];
                final int n11 = ints[j + "  ".length() + (i + "  ".length()) * n7];
                final int n12 = ints[j + " ".length() + (i + " ".length()) * n7];
                this.initChunkSeed(j + n, i + n2);
                if (n12 != 0 || (n8 == 0 && n9 == 0 && n10 == 0 && n11 == 0)) {
                    if (n12 > 0 && (n8 == 0 || n9 == 0 || n10 == 0 || n11 == 0)) {
                        if (this.nextInt(0xB3 ^ 0xB6) == 0) {
                            if (n12 == (0x56 ^ 0x52)) {
                                intCache[j + i * n3] = (0xBE ^ 0xBA);
                                "".length();
                                if (0 >= 3) {
                                    throw null;
                                }
                            }
                            else {
                                intCache[j + i * n3] = "".length();
                                "".length();
                                if (4 == 1) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            intCache[j + i * n3] = n12;
                            "".length();
                            if (0 >= 4) {
                                throw null;
                            }
                        }
                    }
                    else {
                        intCache[j + i * n3] = n12;
                        "".length();
                        if (4 == 0) {
                            throw null;
                        }
                    }
                }
                else {
                    int length = " ".length();
                    int length2 = " ".length();
                    if (n8 != 0 && this.nextInt(length++) == 0) {
                        length2 = n8;
                    }
                    if (n9 != 0 && this.nextInt(length++) == 0) {
                        length2 = n9;
                    }
                    if (n10 != 0 && this.nextInt(length++) == 0) {
                        length2 = n10;
                    }
                    if (n11 != 0 && this.nextInt(length++) == 0) {
                        length2 = n11;
                    }
                    if (this.nextInt("   ".length()) == 0) {
                        intCache[j + i * n3] = length2;
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                    else if (length2 == (0xBA ^ 0xBE)) {
                        intCache[j + i * n3] = (0x65 ^ 0x61);
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                    else {
                        intCache[j + i * n3] = "".length();
                    }
                }
                ++j;
            }
            ++i;
        }
        return intCache;
    }
}
