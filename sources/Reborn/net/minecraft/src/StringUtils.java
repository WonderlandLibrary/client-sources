package net.minecraft.src;

import java.util.regex.*;

public class StringUtils
{
    private static final Pattern patternControlCode;
    
    static {
        patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    }
    
    public static String ticksToElapsedTime(final int par0) {
        int var1 = par0 / 20;
        final int var2 = var1 / 60;
        var1 %= 60;
        return (var1 < 10) ? (String.valueOf(var2) + ":0" + var1) : (String.valueOf(var2) + ":" + var1);
    }
    
    public static String stripControlCodes(final String par0Str) {
        return StringUtils.patternControlCode.matcher(par0Str).replaceAll("");
    }
}
