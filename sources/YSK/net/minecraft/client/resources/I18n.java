package net.minecraft.client.resources;

public class I18n
{
    private static Locale i18nLocale;
    
    public static String format(final String s, final Object... array) {
        return I18n.i18nLocale.formatMessage(s, array);
    }
    
    static void setLocale(final Locale i18nLocale) {
        I18n.i18nLocale = i18nLocale;
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
