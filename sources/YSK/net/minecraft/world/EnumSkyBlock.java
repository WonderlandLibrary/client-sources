package net.minecraft.world;

public enum EnumSkyBlock
{
    private static final EnumSkyBlock[] ENUM$VALUES;
    
    BLOCK(EnumSkyBlock.I[" ".length()], " ".length(), "".length());
    
    private static final String[] I;
    
    SKY(EnumSkyBlock.I["".length()], "".length(), 0x31 ^ 0x3E);
    
    public final int defaultLightValue;
    
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0006/\f", "UdUHZ");
        EnumSkyBlock.I[" ".length()] = I(";<\u001d\u0014:", "ypRWq");
    }
    
    static {
        I();
        final EnumSkyBlock[] enum$VALUES = new EnumSkyBlock["  ".length()];
        enum$VALUES["".length()] = EnumSkyBlock.SKY;
        enum$VALUES[" ".length()] = EnumSkyBlock.BLOCK;
        ENUM$VALUES = enum$VALUES;
    }
    
    private EnumSkyBlock(final String s, final int n, final int defaultLightValue) {
        this.defaultLightValue = defaultLightValue;
    }
}
