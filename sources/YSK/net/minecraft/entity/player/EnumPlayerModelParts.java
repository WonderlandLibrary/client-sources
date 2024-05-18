package net.minecraft.entity.player;

import net.minecraft.util.*;

public enum EnumPlayerModelParts
{
    JACKET(EnumPlayerModelParts.I["  ".length()], " ".length(), " ".length(), EnumPlayerModelParts.I["   ".length()]);
    
    private final IChatComponent field_179339_k;
    private static final String[] I;
    
    HAT(EnumPlayerModelParts.I[0xA6 ^ 0xAA], 0x74 ^ 0x72, 0x66 ^ 0x60, EnumPlayerModelParts.I[0xBB ^ 0xB6]), 
    LEFT_SLEEVE(EnumPlayerModelParts.I[0xC4 ^ 0xC0], "  ".length(), "  ".length(), EnumPlayerModelParts.I[0x6F ^ 0x6A]);
    
    private final int partId;
    
    RIGHT_SLEEVE(EnumPlayerModelParts.I[0xAC ^ 0xAA], "   ".length(), "   ".length(), EnumPlayerModelParts.I[0x88 ^ 0x8F]), 
    CAPE(EnumPlayerModelParts.I["".length()], "".length(), "".length(), EnumPlayerModelParts.I[" ".length()]), 
    RIGHT_PANTS_LEG(EnumPlayerModelParts.I[0x43 ^ 0x49], 0x39 ^ 0x3C, 0x48 ^ 0x4D, EnumPlayerModelParts.I[0x1D ^ 0x16]);
    
    private static final EnumPlayerModelParts[] ENUM$VALUES;
    private final int partMask;
    
    LEFT_PANTS_LEG(EnumPlayerModelParts.I[0x3F ^ 0x37], 0x53 ^ 0x57, 0x3A ^ 0x3E, EnumPlayerModelParts.I[0xBA ^ 0xB3]);
    
    private final String partName;
    
    public int getPartMask() {
        return this.partMask;
    }
    
    static {
        I();
        final EnumPlayerModelParts[] enum$VALUES = new EnumPlayerModelParts[0x1F ^ 0x18];
        enum$VALUES["".length()] = EnumPlayerModelParts.CAPE;
        enum$VALUES[" ".length()] = EnumPlayerModelParts.JACKET;
        enum$VALUES["  ".length()] = EnumPlayerModelParts.LEFT_SLEEVE;
        enum$VALUES["   ".length()] = EnumPlayerModelParts.RIGHT_SLEEVE;
        enum$VALUES[0x4 ^ 0x0] = EnumPlayerModelParts.LEFT_PANTS_LEG;
        enum$VALUES[0x68 ^ 0x6D] = EnumPlayerModelParts.RIGHT_PANTS_LEG;
        enum$VALUES[0x6B ^ 0x6D] = EnumPlayerModelParts.HAT;
        ENUM$VALUES = enum$VALUES;
    }
    
    private static void I() {
        (I = new String[0x72 ^ 0x7D])["".length()] = I(")4 \r", "jupHS");
        EnumPlayerModelParts.I[" ".length()] = I("&;\n)", "EZzLK");
        EnumPlayerModelParts.I["  ".length()] = I(".\u0012\u0012\u001b40", "dSQPq");
        EnumPlayerModelParts.I["   ".length()] = I("/\u0013\u0005\u000f,1", "ErfdI");
        EnumPlayerModelParts.I[0x7C ^ 0x78] = I("\u001e5\b,\u0011\u0001<\u000b=\u0018\u0017", "RpNxN");
        EnumPlayerModelParts.I[0xC5 ^ 0xC0] = I("\u0016\u0011\u000e,\f\t\u0018\r=%\u001f", "zthXS");
        EnumPlayerModelParts.I[0x1B ^ 0x1D] = I("1\u0001&,\u0016<\u001b-!\u00075\r", "cHadB");
        EnumPlayerModelParts.I[0xA4 ^ 0xA3] = I(">\b-$5\u0013\u0012&)$:\u0004", "LaJLA");
        EnumPlayerModelParts.I[0x23 ^ 0x2B] = I("\r3+\f\u001e\u00117#\f\u0012\u001e:(\u001f", "AvmXA");
        EnumPlayerModelParts.I[0x64 ^ 0x6D] = I("\u0015?*9\u000f\t;\"9#&6)*", "yZLMP");
        EnumPlayerModelParts.I[0xA3 ^ 0xA9] = I("\u0016:\u001f\u0018\u001d\u001b#\u0019\u001e\u001d\u0017,\u0014\u0015\u000e", "DsXPI");
        EnumPlayerModelParts.I[0x14 ^ 0x1F] = I("+\u0019\u0010\u001c2\u0006\u0000\u0016\u001a2*/\u001b\u0011!", "YpwtF");
        EnumPlayerModelParts.I[0x46 ^ 0x4A] = I("\"\u00133", "jRgeT");
        EnumPlayerModelParts.I[0x54 ^ 0x59] = I("%\u0007:", "MfNpt");
        EnumPlayerModelParts.I[0x70 ^ 0x7E] = I("\u0018;>\r8\u00198d\t8\u0013.&46\u0005?d", "wKJdW");
    }
    
    public IChatComponent func_179326_d() {
        return this.field_179339_k;
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String getPartName() {
        return this.partName;
    }
    
    private EnumPlayerModelParts(final String s, final int n, final int partId, final String partName) {
        this.partId = partId;
        this.partMask = " ".length() << partId;
        this.partName = partName;
        this.field_179339_k = new ChatComponentTranslation(EnumPlayerModelParts.I[0x88 ^ 0x86] + partName, new Object["".length()]);
    }
    
    public int getPartId() {
        return this.partId;
    }
}
