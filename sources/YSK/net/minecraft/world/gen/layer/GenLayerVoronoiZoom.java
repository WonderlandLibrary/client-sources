package net.minecraft.world.gen.layer;

public class GenLayerVoronoiZoom extends GenLayer
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GenLayerVoronoiZoom(final long n, final GenLayer parent) {
        super(n);
        super.parent = parent;
    }
    
    @Override
    public int[] getInts(int n, int n2, final int n3, final int n4) {
        n -= 2;
        n2 -= 2;
        final int n5 = n >> "  ".length();
        final int n6 = n2 >> "  ".length();
        final int n7 = (n3 >> "  ".length()) + "  ".length();
        final int n8 = (n4 >> "  ".length()) + "  ".length();
        final int[] ints = this.parent.getInts(n5, n6, n7, n8);
        final int n9 = n7 - " ".length() << "  ".length();
        final int[] intCache = IntCache.getIntCache(n9 * (n8 - " ".length() << "  ".length()));
        int i = "".length();
        "".length();
        if (1 < 1) {
            throw null;
        }
        while (i < n8 - " ".length()) {
            int j = "".length();
            int n10 = ints[j + "".length() + (i + "".length()) * n7];
            int n11 = ints[j + "".length() + (i + " ".length()) * n7];
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (j < n7 - " ".length()) {
                this.initChunkSeed(j + n5 << "  ".length(), i + n6 << "  ".length());
                final double n12 = (this.nextInt(365 + 563 + 6 + 90) / 1024.0 - 0.5) * 3.6;
                final double n13 = (this.nextInt(435 + 257 - 654 + 986) / 1024.0 - 0.5) * 3.6;
                this.initChunkSeed(j + n5 + " ".length() << "  ".length(), i + n6 << "  ".length());
                final double n14 = (this.nextInt(479 + 562 - 463 + 446) / 1024.0 - 0.5) * 3.6 + 4.0;
                final double n15 = (this.nextInt(929 + 42 - 413 + 466) / 1024.0 - 0.5) * 3.6;
                this.initChunkSeed(j + n5 << "  ".length(), i + n6 + " ".length() << "  ".length());
                final double n16 = (this.nextInt(901 + 291 - 554 + 386) / 1024.0 - 0.5) * 3.6;
                final double n17 = (this.nextInt(159 + 128 + 219 + 518) / 1024.0 - 0.5) * 3.6 + 4.0;
                this.initChunkSeed(j + n5 + " ".length() << "  ".length(), i + n6 + " ".length() << "  ".length());
                final double n18 = (this.nextInt(146 + 1021 - 757 + 614) / 1024.0 - 0.5) * 3.6 + 4.0;
                final double n19 = (this.nextInt(198 + 757 - 200 + 269) / 1024.0 - 0.5) * 3.6 + 4.0;
                final int n20 = ints[j + " ".length() + (i + "".length()) * n7] & 176 + 102 - 143 + 120;
                final int n21 = ints[j + " ".length() + (i + " ".length()) * n7] & 40 + 166 - 74 + 123;
                int k = "".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                while (k < (0xA2 ^ 0xA6)) {
                    int n22 = ((i << "  ".length()) + k) * n9 + (j << "  ".length());
                    int l = "".length();
                    "".length();
                    if (1 == 2) {
                        throw null;
                    }
                    while (l < (0xA ^ 0xE)) {
                        final double n23 = (k - n13) * (k - n13) + (l - n12) * (l - n12);
                        final double n24 = (k - n15) * (k - n15) + (l - n14) * (l - n14);
                        final double n25 = (k - n17) * (k - n17) + (l - n16) * (l - n16);
                        final double n26 = (k - n19) * (k - n19) + (l - n18) * (l - n18);
                        if (n23 < n24 && n23 < n25 && n23 < n26) {
                            intCache[n22++] = n10;
                            "".length();
                            if (1 == -1) {
                                throw null;
                            }
                        }
                        else if (n24 < n23 && n24 < n25 && n24 < n26) {
                            intCache[n22++] = n20;
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else if (n25 < n23 && n25 < n24 && n25 < n26) {
                            intCache[n22++] = n11;
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else {
                            intCache[n22++] = n21;
                        }
                        ++l;
                    }
                    ++k;
                }
                n10 = n20;
                n11 = n21;
                ++j;
            }
            ++i;
        }
        final int[] intCache2 = IntCache.getIntCache(n3 * n4);
        int length = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (length < n4) {
            System.arraycopy(intCache, (length + (n2 & "   ".length())) * n9 + (n & "   ".length()), intCache2, length * n3, n3);
            ++length;
        }
        return intCache2;
    }
}
