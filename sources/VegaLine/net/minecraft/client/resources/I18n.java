/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import java.util.Map;
import net.minecraft.client.resources.Locale;

public class I18n {
    private static Locale i18nLocale;

    static void setLocale(Locale i18nLocaleIn) {
        i18nLocale = i18nLocaleIn;
    }

    public static String format(String translateKey, Object ... parameters) {
        return i18nLocale.formatMessage(translateKey, parameters);
    }

    public static boolean hasKey(String key) {
        return i18nLocale.hasKey(key);
    }

    public static Map getLocaleProperties() {
        return I18n.i18nLocale.properties;
    }
}

