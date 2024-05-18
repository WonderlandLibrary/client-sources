package net.minecraft.world.gen.layer;

public class GenLayerRemoveTooMuchOcean extends GenLayer
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
        if (2 == 0) {
            throw null;
        }
        while (i < n4) {
            int j = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (j < n3) {
                final int n8 = ints[j + " ".length() + (i + " ".length() - " ".length()) * (n3 + "  ".length())];
                final int n9 = ints[j + " ".length() + " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                final int n10 = ints[j + " ".length() - " ".length() + (i + " ".length()) * (n3 + "  ".length())];
                final int n11 = ints[j + " ".length() + (i + " ".length() + " ".length()) * (n3 + "  ".length())];
                final int n12 = ints[j + " ".length() + (i + " ".length()) * n7];
                intCache[j + i * n3] = n12;
                this.initChunkSeed(j + n, i + n2);
                if (n12 == 0 && n8 == 0 && n9 == 0 && n10 == 0 && n11 == 0 && this.nextInt("  ".length()) == 0) {
                    intCache[j + i * n3] = " ".length();
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GenLayerRemoveTooMuchOcean(final long n, final GenLayer parent) {
        super(n);
        this.parent = parent;
    }
}
