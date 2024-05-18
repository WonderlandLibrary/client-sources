package net.minecraft.item;

public enum EnumAction
{
    NONE(EnumAction.I["".length()], "".length());
    
    private static final String[] I;
    
    DRINK(EnumAction.I["  ".length()], "  ".length()), 
    BOW(EnumAction.I[0x9D ^ 0x99], 0x3 ^ 0x7), 
    EAT(EnumAction.I[" ".length()], " ".length());
    
    private static final EnumAction[] ENUM$VALUES;
    
    BLOCK(EnumAction.I["   ".length()], "   ".length());
    
    private static void I() {
        (I = new String[0xB9 ^ 0xBC])["".length()] = I("#.\u00006", "maNsd");
        EnumAction.I[" ".length()] = I(")\u0016\u0013", "lWGGp");
        EnumAction.I["  ".length()] = I("2\u0006\u0013\u0003\u0011", "vTZMZ");
        EnumAction.I["   ".length()] = I("*)'\u0000\u0007", "hehCL");
        EnumAction.I[0x94 ^ 0x90] = I("/\u0018\u0016", "mWAAG");
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
        final EnumAction[] enum$VALUES = new EnumAction[0x18 ^ 0x1D];
        enum$VALUES["".length()] = EnumAction.NONE;
        enum$VALUES[" ".length()] = EnumAction.EAT;
        enum$VALUES["  ".length()] = EnumAction.DRINK;
        enum$VALUES["   ".length()] = EnumAction.BLOCK;
        enum$VALUES[0x9F ^ 0x9B] = EnumAction.BOW;
        ENUM$VALUES = enum$VALUES;
    }
    
    private EnumAction(final String s, final int n) {
    }
}
