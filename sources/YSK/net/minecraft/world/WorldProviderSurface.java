package net.minecraft.world;

public class WorldProviderSurface extends WorldProvider
{
    private static final String[] I;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(" !\u000b36\u0000%\u0002%", "oWnAA");
        WorldProviderSurface.I[" ".length()] = I("", "wnIMI");
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
    
    static {
        I();
    }
    
    @Override
    public String getInternalNameSuffix() {
        return WorldProviderSurface.I[" ".length()];
    }
    
    @Override
    public String getDimensionName() {
        return WorldProviderSurface.I["".length()];
    }
}
