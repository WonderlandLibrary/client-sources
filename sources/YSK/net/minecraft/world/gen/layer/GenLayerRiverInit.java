package net.minecraft.world.gen.layer;

public class GenLayerRiverInit extends GenLayer
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int[] ints = this.parent.getInts(n, n2, n3, n4);
        final int[] intCache = IntCache.getIntCache(n3 * n4);
        int i = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (j < n3) {
                this.initChunkSeed(j + n, i + n2);
                final int[] array = intCache;
                final int n5 = j + i * n3;
                int length;
                if (ints[j + i * n3] > 0) {
                    length = this.nextInt(52317 + 230575 - 200514 + 217621) + "  ".length();
                    "".length();
                    if (0 == 3) {
                        throw null;
                    }
                }
                else {
                    length = "".length();
                }
                array[n5] = length;
                ++j;
            }
            ++i;
        }
        return intCache;
    }
    
    public GenLayerRiverInit(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
}
