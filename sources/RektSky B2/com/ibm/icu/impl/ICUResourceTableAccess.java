package com.ibm.icu.impl;

import com.ibm.icu.util.*;

public class ICUResourceTableAccess
{
    public static String getTableString(final String path, final ULocale locale, final String tableName, final String itemName, final String defaultValue) {
        final ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(path, locale.getBaseName());
        return getTableString(bundle, tableName, null, itemName, defaultValue);
    }
    
    public static String getTableString(ICUResourceBundle bundle, final String tableName, final String subtableName, final String item, final String defaultValue) {
        String result = null;
        try {
            while (true) {
                final ICUResourceBundle table = bundle.findWithFallback(tableName);
                if (table == null) {
                    return defaultValue;
                }
                ICUResourceBundle stable = table;
                if (subtableName != null) {
                    stable = table.findWithFallback(subtableName);
                }
                if (stable != null) {
                    result = stable.findStringWithFallback(item);
                    if (result != null) {
                        break;
                    }
                }
                if (subtableName == null) {
                    String currentName = null;
                    if (tableName.equals("Countries")) {
                        currentName = LocaleIDs.getCurrentCountryID(item);
                    }
                    else if (tableName.equals("Languages")) {
                        currentName = LocaleIDs.getCurrentLanguageID(item);
                    }
                    if (currentName != null) {
                        result = table.findStringWithFallback(currentName);
                        if (result != null) {
                            break;
                        }
                    }
                }
                String fallbackLocale = table.findStringWithFallback("Fallback");
                if (fallbackLocale == null) {
                    return defaultValue;
                }
                if (fallbackLocale.length() == 0) {
                    fallbackLocale = "root";
                }
                if (fallbackLocale.equals(table.getULocale().getName())) {
                    return defaultValue;
                }
                bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(bundle.getBaseName(), fallbackLocale);
            }
        }
        catch (Exception ex) {}
        return (result != null && result.length() > 0) ? result : defaultValue;
    }
}
