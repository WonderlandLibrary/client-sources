package net.minecraft.world.gen.layer;

public class GenLayerZoom extends GenLayer
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GenLayerZoom(final long n, final GenLayer parent) {
        super(n);
        super.parent = parent;
    }
    
    public static GenLayer magnify(final long n, final GenLayer genLayer, final int n2) {
        GenLayer genLayer2 = genLayer;
        int i = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (i < n2) {
            genLayer2 = new GenLayerZoom(n + i, genLayer2);
            ++i;
        }
        return genLayer2;
    }
    
    @Override
    public int[] getInts(final int n, final int n2, final int n3, final int n4) {
        final int n5 = n >> " ".length();
        final int n6 = n2 >> " ".length();
        final int n7 = (n3 >> " ".length()) + "  ".length();
        final int n8 = (n4 >> " ".length()) + "  ".length();
        final int[] ints = this.parent.getInts(n5, n6, n7, n8);
        final int n9 = n7 - " ".length() << " ".length();
        final int[] intCache = IntCache.getIntCache(n9 * (n8 - " ".length() << " ".length()));
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < n8 - " ".length()) {
            int n10 = (i << " ".length()) * n9;
            int j = "".length();
            int n11 = ints[j + "".length() + (i + "".length()) * n7];
            int n12 = ints[j + "".length() + (i + " ".length()) * n7];
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j < n7 - " ".length()) {
                this.initChunkSeed(j + n5 << " ".length(), i + n6 << " ".length());
                final int n13 = ints[j + " ".length() + (i + "".length()) * n7];
                final int n14 = ints[j + " ".length() + (i + " ".length()) * n7];
                intCache[n10] = n11;
                final int[] array = intCache;
                final int n15 = n10++ + n9;
                final int[] array2 = new int["  ".length()];
                array2["".length()] = n11;
                array2[" ".length()] = n12;
                array[n15] = this.selectRandom(array2);
                final int[] array3 = intCache;
                final int n16 = n10;
                final int[] array4 = new int["  ".length()];
                array4["".length()] = n11;
                array4[" ".length()] = n13;
                array3[n16] = this.selectRandom(array4);
                intCache[n10++ + n9] = this.selectModeOrRandom(n11, n13, n12, n14);
                n11 = n13;
                n12 = n14;
                ++j;
            }
            ++i;
        }
        final int[] intCache2 = IntCache.getIntCache(n3 * n4);
        int k = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (k < n4) {
            System.arraycopy(intCache, (k + (n2 & " ".length())) * n9 + (n & " ".length()), intCache2, k * n3, n3);
            ++k;
        }
        return intCache2;
    }
}
