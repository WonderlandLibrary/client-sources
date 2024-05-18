package net.minecraft.world.gen.layer;

public class GenLayerAddSnow extends GenLayer
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
        if (3 != 3) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
            while (j < n3) {
                final int n8 = ints[j + " ".length() + (i + " ".length()) * n7];
                this.initChunkSeed(j + n, i + n2);
                if (n8 == 0) {
                    intCache[j + i * n3] = "".length();
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else {
                    final int nextInt = this.nextInt(0x45 ^ 0x43);
                    int n9;
                    if (nextInt == 0) {
                        n9 = (0x9D ^ 0x99);
                        "".length();
                        if (-1 == 1) {
                            throw null;
                        }
                    }
                    else if (nextInt <= " ".length()) {
                        n9 = "   ".length();
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else {
                        n9 = " ".length();
                    }
                    intCache[j + i * n3] = n9;
                }
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    public GenLayerAddSnow(final long n, final GenLayer parent) {
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
}
