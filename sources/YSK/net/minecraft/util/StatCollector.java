package net.minecraft.util;

public class StatCollector
{
    private static StringTranslate localizedName;
    private static StringTranslate fallbackTranslator;
    
    public static boolean canTranslate(final String s) {
        return StatCollector.localizedName.isKeyTranslated(s);
    }
    
    public static String translateToLocal(final String s) {
        return StatCollector.localizedName.translateKey(s);
    }
    
    public static String translateToLocalFormatted(final String s, final Object... array) {
        return StatCollector.localizedName.translateKeyFormat(s, array);
    }
    
    static {
        StatCollector.localizedName = StringTranslate.getInstance();
        StatCollector.fallbackTranslator = new StringTranslate();
    }
    
    public static long getLastTranslationUpdateTimeInMilliseconds() {
        return StatCollector.localizedName.getLastUpdateTimeInMilliseconds();
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
    
    public static String translateToFallback(final String s) {
        return StatCollector.fallbackTranslator.translateKey(s);
    }
}
