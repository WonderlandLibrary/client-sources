package net.minecraft.client.resources;

public class I18n
{
    private static Locale i18nLocale;
    
    static void setLocale(final Locale i18nLocaleIn) {
        I18n.i18nLocale = i18nLocaleIn;
    }
    
    public static String format(final String translateKey, final Object... parameters) {
        return I18n.i18nLocale.formatMessage(translateKey, parameters);
    }
}
