package net.minecraft.entity;

public enum EnumCreatureAttribute
{
    private static final String[] I;
    private static final EnumCreatureAttribute[] ENUM$VALUES;
    
    ARTHROPOD(EnumCreatureAttribute.I["  ".length()], "  ".length()), 
    UNDEAD(EnumCreatureAttribute.I[" ".length()], " ".length()), 
    UNDEFINED(EnumCreatureAttribute.I["".length()], "".length());
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\r\u0018\u00120\u0017\u0011\u0018\u00131", "XVVuQ");
        EnumCreatureAttribute.I[" ".length()] = I(">\u0002<\u00072/", "kLxBs");
        EnumCreatureAttribute.I["  ".length()] = I("\u0014\u001e\">\u001e\u001a\u001c92", "ULvvL");
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private EnumCreatureAttribute(final String s, final int n) {
    }
    
    static {
        I();
        final EnumCreatureAttribute[] enum$VALUES = new EnumCreatureAttribute["   ".length()];
        enum$VALUES["".length()] = EnumCreatureAttribute.UNDEFINED;
        enum$VALUES[" ".length()] = EnumCreatureAttribute.UNDEAD;
        enum$VALUES["  ".length()] = EnumCreatureAttribute.ARTHROPOD;
        ENUM$VALUES = enum$VALUES;
    }
}
