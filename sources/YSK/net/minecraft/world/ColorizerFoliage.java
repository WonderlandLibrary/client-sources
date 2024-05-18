package net.minecraft.world;

public class ColorizerFoliage
{
    private static int[] foliageBuffer;
    
    static {
        ColorizerFoliage.foliageBuffer = new int[50819 + 2287 - 35000 + 47430];
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static int getFoliageColor(final double n, double n2) {
        n2 *= n;
        return ColorizerFoliage.foliageBuffer[(int)((1.0 - n2) * 255.0) << (0xAA ^ 0xA2) | (int)((1.0 - n) * 255.0)];
    }
    
    public static int getFoliageColorPine() {
        return 4949220 + 2443659 - 7199589 + 6202967;
    }
    
    public static int getFoliageColorBirch() {
        return 4859833 + 1392832 + 1139526 + 1039254;
    }
    
    public static int getFoliageColorBasic() {
        return 2938297 + 2789147 - 3276146 + 2313654;
    }
    
    public static void setFoliageBiomeColorizer(final int[] foliageBuffer) {
        ColorizerFoliage.foliageBuffer = foliageBuffer;
    }
}
