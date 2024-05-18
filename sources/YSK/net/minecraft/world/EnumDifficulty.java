package net.minecraft.world;

public enum EnumDifficulty
{
    private final int difficultyId;
    private static final EnumDifficulty[] difficultyEnums;
    
    HARD(EnumDifficulty.I[0x64 ^ 0x62], "   ".length(), "   ".length(), EnumDifficulty.I[0x76 ^ 0x71]);
    
    private static final EnumDifficulty[] ENUM$VALUES;
    
    NORMAL(EnumDifficulty.I[0x2C ^ 0x28], "  ".length(), "  ".length(), EnumDifficulty.I[0x11 ^ 0x14]);
    
    private final String difficultyResourceKey;
    
    PEACEFUL(EnumDifficulty.I["".length()], "".length(), "".length(), EnumDifficulty.I[" ".length()]);
    
    private static final String[] I;
    
    EASY(EnumDifficulty.I["  ".length()], " ".length(), " ".length(), EnumDifficulty.I["   ".length()]);
    
    public int getDifficultyId() {
        return this.difficultyId;
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        final EnumDifficulty[] enum$VALUES = new EnumDifficulty[0x25 ^ 0x21];
        enum$VALUES["".length()] = EnumDifficulty.PEACEFUL;
        enum$VALUES[" ".length()] = EnumDifficulty.EASY;
        enum$VALUES["  ".length()] = EnumDifficulty.NORMAL;
        enum$VALUES["   ".length()] = EnumDifficulty.HARD;
        ENUM$VALUES = enum$VALUES;
        difficultyEnums = new EnumDifficulty[values().length];
        final EnumDifficulty[] values;
        final int length = (values = values()).length;
        int i = "".length();
        "".length();
        if (4 == 3) {
            throw null;
        }
        while (i < length) {
            final EnumDifficulty enumDifficulty = values[i];
            EnumDifficulty.difficultyEnums[enumDifficulty.difficultyId] = enumDifficulty;
            ++i;
        }
    }
    
    public String getDifficultyResourceKey() {
        return this.difficultyResourceKey;
    }
    
    private EnumDifficulty(final String s, final int n, final int difficultyId, final String difficultyResourceKey) {
        this.difficultyId = difficultyId;
        this.difficultyResourceKey = difficultyResourceKey;
    }
    
    public static EnumDifficulty getDifficultyEnum(final int n) {
        return EnumDifficulty.difficultyEnums[n % EnumDifficulty.difficultyEnums.length];
    }
    
    private static void I() {
        (I = new String[0x65 ^ 0x6D])["".length()] = I("6\u001d\u0000\u00002 \r\r", "fXACw");
        EnumDifficulty.I[" ".length()] = I("\u00013-%\u001a\u00000w(\u001c\b%0/\u0000\u00027 b\u0005\u000b\":)\u0013\u001b/", "nCYLu");
        EnumDifficulty.I["  ".length()] = I("\u00165\u0006\u0017", "StUNT");
        EnumDifficulty.I["   ".length()] = I("\n3\r(\u0003\u000b0W%\u0005\u0003%\u0010\"\u0019\t7\u0000o\t\u00040\u0000", "eCyAl");
        EnumDifficulty.I[0x7C ^ 0x78] = I("\t\"&.\u0015\u000b", "GmtcT");
        EnumDifficulty.I[0x73 ^ 0x76] = I(";'\u0015\u0001):$O\f/21\b\u000b38#\u0018F(;%\f\t*", "TWahF");
        EnumDifficulty.I[0x10 ^ 0x16] = I("%\u00056\u0007", "mDdCw");
        EnumDifficulty.I[0x4C ^ 0x4B] = I("\t;-8\r\b8w5\u000b\u0000-02\u0017\n? \u007f\n\u00079=", "fKYQb");
    }
}
