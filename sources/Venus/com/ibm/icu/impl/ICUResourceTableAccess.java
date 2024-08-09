/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.LocaleIDs;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;

public class ICUResourceTableAccess {
    public static String getTableString(String string, ULocale uLocale, String string2, String string3, String string4) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(string, uLocale.getBaseName());
        return ICUResourceTableAccess.getTableString(iCUResourceBundle, string2, null, string3, string4);
    }

    public static String getTableString(ICUResourceBundle iCUResourceBundle, String string, String string2, String string3, String string4) {
        String string5 = null;
        try {
            while (true) {
                String string6;
                ICUResourceBundle iCUResourceBundle2;
                if ((iCUResourceBundle2 = iCUResourceBundle.findWithFallback(string)) == null) {
                    return string4;
                }
                ICUResourceBundle iCUResourceBundle3 = iCUResourceBundle2;
                if (string2 != null) {
                    iCUResourceBundle3 = iCUResourceBundle2.findWithFallback(string2);
                }
                if (iCUResourceBundle3 != null && (string5 = iCUResourceBundle3.findStringWithFallback(string3)) != null) break;
                if (string2 == null) {
                    string6 = null;
                    if (string.equals("Countries")) {
                        string6 = LocaleIDs.getCurrentCountryID(string3);
                    } else if (string.equals("Languages")) {
                        string6 = LocaleIDs.getCurrentLanguageID(string3);
                    }
                    if (string6 != null && (string5 = iCUResourceBundle2.findStringWithFallback(string6)) != null) break;
                }
                if ((string6 = iCUResourceBundle2.findStringWithFallback("Fallback")) == null) {
                    return string4;
                }
                if (string6.length() == 0) {
                    string6 = "root";
                }
                if (string6.equals(iCUResourceBundle2.getULocale().getName())) {
                    return string4;
                }
                iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(iCUResourceBundle.getBaseName(), string6);
            }
        } catch (Exception exception) {
            // empty catch block
        }
        return string5 != null && string5.length() > 0 ? string5 : string4;
    }
}

