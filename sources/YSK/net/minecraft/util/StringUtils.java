package net.minecraft.util;

import java.util.regex.*;

public class StringUtils
{
    private static final String[] I;
    private static final Pattern patternControlCode;
    
    static {
        I();
        patternControlCode = Pattern.compile(StringUtils.I["".length()]);
    }
    
    private static void I() {
        (I = new String[0xD ^ 0x9])["".length()] = I("~l;E5#cb-^\rc\u007fU({\u0015\u0019A&\u0004\u000e", "VSRli");
        StringUtils.I[" ".length()] = I("_h", "eXsvX");
        StringUtils.I["  ".length()] = I("i", "SRQlU");
        StringUtils.I["   ".length()] = I("", "JdZQa");
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
    
    public static boolean isNullOrEmpty(final String s) {
        return org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)s);
    }
    
    public static String ticksToElapsedTime(final int n) {
        final int n2 = n / (0x84 ^ 0x90);
        final int n3 = n2 / (0x16 ^ 0x2A);
        final int n4 = n2 % (0x70 ^ 0x4C);
        String s;
        if (n4 < (0x97 ^ 0x9D)) {
            s = String.valueOf(n3) + StringUtils.I[" ".length()] + n4;
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            s = String.valueOf(n3) + StringUtils.I["  ".length()] + n4;
        }
        return s;
    }
    
    public static String stripControlCodes(final String s) {
        return StringUtils.patternControlCode.matcher(s).replaceAll(StringUtils.I["   ".length()]);
    }
}
