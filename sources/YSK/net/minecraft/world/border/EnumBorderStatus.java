package net.minecraft.world.border;

public enum EnumBorderStatus
{
    STATIONARY(EnumBorderStatus.I["  ".length()], "  ".length(), 1636826 + 1690490 - 2891580 + 1702631);
    
    private static final EnumBorderStatus[] ENUM$VALUES;
    private static final String[] I;
    
    SHRINKING(EnumBorderStatus.I[" ".length()], " ".length(), 10565820 + 15013215 - 19552909 + 10697890);
    
    private final int id;
    
    GROWING(EnumBorderStatus.I["".length()], "".length(), 3472495 + 1888369 - 4568398 + 3467246);
    
    public int getID() {
        return this.id;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u00034\"\u00169\n!", "DfmAp");
        EnumBorderStatus.I[" ".length()] = I("\u001b&\u0018\u000e%\u0003'\u0004\u0000", "HnJGk");
        EnumBorderStatus.I["  ".length()] = I("#:7\";? 7$+", "pnvvr");
    }
    
    private EnumBorderStatus(final String s, final int n, final int id) {
        this.id = id;
    }
    
    static {
        I();
        final EnumBorderStatus[] enum$VALUES = new EnumBorderStatus["   ".length()];
        enum$VALUES["".length()] = EnumBorderStatus.GROWING;
        enum$VALUES[" ".length()] = EnumBorderStatus.SHRINKING;
        enum$VALUES["  ".length()] = EnumBorderStatus.STATIONARY;
        ENUM$VALUES = enum$VALUES;
    }
}
