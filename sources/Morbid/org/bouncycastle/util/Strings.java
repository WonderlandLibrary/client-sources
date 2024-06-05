package org.bouncycastle.util;

public final class Strings
{
    public static String toLowerCase(final String par0Str) {
        boolean var1 = false;
        final char[] var2 = par0Str.toCharArray();
        for (int var3 = 0; var3 != var2.length; ++var3) {
            final char var4 = var2[var3];
            if ('A' <= var4 && 'Z' >= var4) {
                var1 = true;
                var2[var3] = (char)(var4 - 'A' + 'a');
            }
        }
        if (var1) {
            return new String(var2);
        }
        return par0Str;
    }
}
