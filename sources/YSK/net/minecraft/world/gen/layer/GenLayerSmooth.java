package net.minecraft.world.gen.layer;

public class GenLayerSmooth extends GenLayer
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
        if (3 <= 0) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (j < n3) {
                final int n8 = ints[j + "".length() + (i + " ".length()) * n7];
                final int n9 = ints[j + "  ".length() + (i + " ".length()) * n7];
                final int n10 = ints[j + " ".length() + (i + "".length()) * n7];
                final int n11 = ints[j + " ".length() + (i + "  ".length()) * n7];
                int n12 = ints[j + " ".length() + (i + " ".length()) * n7];
                if (n8 == n9 && n10 == n11) {
                    this.initChunkSeed(j + n, i + n2);
                    if (this.nextInt("  ".length()) == 0) {
                        n12 = n8;
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        n12 = n10;
                        "".length();
                        if (2 < -1) {
                            throw null;
                        }
                    }
                }
                else {
                    if (n8 == n9) {
                        n12 = n8;
                    }
                    if (n10 == n11) {
                        n12 = n10;
                    }
                }
                intCache[j + i * n3] = n12;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GenLayerSmooth(final long n, final GenLayer parent) {
        super(n);
        super.parent = parent;
    }
}
