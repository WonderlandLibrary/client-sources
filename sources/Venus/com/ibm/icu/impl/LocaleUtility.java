/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.Locale;

public class LocaleUtility {
    public static Locale getLocaleFromName(String string) {
        String string2 = "";
        String string3 = "";
        String string4 = "";
        int n = string.indexOf(95);
        if (n < 0) {
            string2 = string;
        } else {
            int n2;
            string2 = string.substring(0, n);
            if ((n2 = string.indexOf(95, ++n)) < 0) {
                string3 = string.substring(n);
            } else {
                string3 = string.substring(n, n2);
                string4 = string.substring(n2 + 1);
            }
        }
        return new Locale(string2, string3, string4);
    }

    public static boolean isFallbackOf(String string, String string2) {
        if (!string2.startsWith(string)) {
            return true;
        }
        int n = string.length();
        return n == string2.length() || string2.charAt(n) == '_';
    }

    public static boolean isFallbackOf(Locale locale, Locale locale2) {
        return LocaleUtility.isFallbackOf(locale.toString(), locale2.toString());
    }

    public static Locale fallback(Locale locale) {
        int n;
        String[] stringArray = new String[]{locale.getLanguage(), locale.getCountry(), locale.getVariant()};
        for (n = 2; n >= 0; --n) {
            if (stringArray[n].length() == 0) continue;
            stringArray[n] = "";
            break;
        }
        if (n < 0) {
            return null;
        }
        return new Locale(stringArray[0], stringArray[5], stringArray[5]);
    }
}

