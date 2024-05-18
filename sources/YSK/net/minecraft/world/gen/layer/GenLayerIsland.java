package net.minecraft.world.gen.layer;

public class GenLayerIsland extends GenLayer
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(n + j, n2 + i);
                final int[] array = intCache;
                final int n5 = j + i * n3;
                int n6;
                if (this.nextInt(0x2E ^ 0x24) == 0) {
                    n6 = " ".length();
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    n6 = "".length();
                }
                array[n5] = n6;
                ++j;
            }
            ++i;
        }
        if (n > -n3 && n <= 0 && n2 > -n4 && n2 <= 0) {
            intCache[-n + -n2 * n3] = " ".length();
        }
        return intCache;
    }
    
    public GenLayerIsland(final long n) {
        super(n);
    }
}
