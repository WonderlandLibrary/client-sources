package net.minecraft.tileentity;

public class TileEntityDropper extends TileEntityDispenser
{
    private static final String[] I;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001a67$\b\u00107<\"G\u001d+6 \u0019\u001c+", "yYYPi");
        TileEntityDropper.I[" ".length()] = I("8\u0003\u0004\u000f4'\u000b\f\u001em1\u0018\u0005\u001a'0\u0018", "UjjjW");
    }
    
    @Override
    public String getGuiID() {
        return TileEntityDropper.I[" ".length()];
    }
    
    @Override
    public String getName() {
        String customName;
        if (this.hasCustomName()) {
            customName = this.customName;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            customName = TileEntityDropper.I["".length()];
        }
        return customName;
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
    }
}
