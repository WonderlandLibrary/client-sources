package net.minecraft.world;

public class ColorizerGrass
{
    private static int[] grassBuffer;
    
    public static int getGrassColor(final double n, double n2) {
        n2 *= n;
        final int n3 = (int)((1.0 - n2) * 255.0) << (0x5B ^ 0x53) | (int)((1.0 - n) * 255.0);
        int n4;
        if (n3 > ColorizerGrass.grassBuffer.length) {
            n4 = -(3686 + 61775 - 52944 + 52764);
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            n4 = ColorizerGrass.grassBuffer[n3];
        }
        return n4;
    }
    
    public static void setGrassBiomeColorizer(final int[] grassBuffer) {
        ColorizerGrass.grassBuffer = grassBuffer;
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        ColorizerGrass.grassBuffer = new int[46171 + 51820 - 59983 + 27528];
    }
}
