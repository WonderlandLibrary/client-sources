package net.minecraft.util;

public enum EnumWorldBlockLayer
{
    SOLID(EnumWorldBlockLayer.I["".length()], "".length(), EnumWorldBlockLayer.I[" ".length()]), 
    CUTOUT_MIPPED(EnumWorldBlockLayer.I["  ".length()], " ".length(), EnumWorldBlockLayer.I["   ".length()]), 
    TRANSLUCENT(EnumWorldBlockLayer.I[0xB9 ^ 0xBF], "   ".length(), EnumWorldBlockLayer.I[0x76 ^ 0x71]);
    
    private final String layerName;
    
    CUTOUT(EnumWorldBlockLayer.I[0xC2 ^ 0xC6], "  ".length(), EnumWorldBlockLayer.I[0x4B ^ 0x4E]);
    
    private static final String[] I;
    private static final EnumWorldBlockLayer[] ENUM$VALUES;
    
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
            if (4 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x65 ^ 0x6D])["".length()] = I("4\r!\b>", "gBmAz");
        EnumWorldBlockLayer.I[" ".length()] = I("7=.,>", "dRBEZ");
        EnumWorldBlockLayer.I["  ".length()] = I("\u00173\u0010'\u0000\u00009\t!\u0005\u0004#\u0000", "TfDhU");
        EnumWorldBlockLayer.I["   ".length()] = I(")\f*\u0019#\u0000E\u0019\u001c2\u000b\u0010.", "deZiF");
        EnumWorldBlockLayer.I[0xB3 ^ 0xB7] = I("\r\"\u0000\"\u0005\u001a", "NwTmP");
        EnumWorldBlockLayer.I[0x89 ^ 0x8C] = I("\u0016\u0010\u0012\u0007\u000f!", "Uefhz");
        EnumWorldBlockLayer.I[0x1F ^ 0x19] = I("\f\u0015'\u0017#\u0014\u0012%\u001c>\f", "XGfYp");
        EnumWorldBlockLayer.I[0x2D ^ 0x2A] = I("\u0000\u001f(<\u001c8\u0018*7\u0001 ", "TmIRo");
    }
    
    @Override
    public String toString() {
        return this.layerName;
    }
    
    static {
        I();
        final EnumWorldBlockLayer[] enum$VALUES = new EnumWorldBlockLayer[0x56 ^ 0x52];
        enum$VALUES["".length()] = EnumWorldBlockLayer.SOLID;
        enum$VALUES[" ".length()] = EnumWorldBlockLayer.CUTOUT_MIPPED;
        enum$VALUES["  ".length()] = EnumWorldBlockLayer.CUTOUT;
        enum$VALUES["   ".length()] = EnumWorldBlockLayer.TRANSLUCENT;
        ENUM$VALUES = enum$VALUES;
    }
    
    private EnumWorldBlockLayer(final String s, final int n, final String layerName) {
        this.layerName = layerName;
    }
}
