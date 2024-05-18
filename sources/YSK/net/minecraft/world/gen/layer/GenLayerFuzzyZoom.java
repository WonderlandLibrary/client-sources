package net.minecraft.world.gen.layer;

public class GenLayerFuzzyZoom extends GenLayerZoom
{
    @Override
    protected int selectModeOrRandom(final int n, final int n2, final int n3, final int n4) {
        final int[] array = new int[0x2 ^ 0x6];
        array["".length()] = n;
        array[" ".length()] = n2;
        array["  ".length()] = n3;
        array["   ".length()] = n4;
        return this.selectRandom(array);
    }
    
    public GenLayerFuzzyZoom(final long n, final GenLayer genLayer) {
        super(n, genLayer);
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
            if (4 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
