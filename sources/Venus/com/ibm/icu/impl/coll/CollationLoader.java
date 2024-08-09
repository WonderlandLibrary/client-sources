/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.coll.CollationDataReader;
import com.ibm.icu.impl.coll.CollationRoot;
import com.ibm.icu.impl.coll.CollationTailoring;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class CollationLoader {
    private static volatile String rootRules = null;

    private CollationLoader() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void loadRootRules() {
        if (rootRules != null) {
            return;
        }
        Class<CollationLoader> clazz = CollationLoader.class;
        synchronized (CollationLoader.class) {
            if (rootRules == null) {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/coll", ULocale.ROOT);
                rootRules = uResourceBundle.getString("UCARules");
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return;
        }
    }

    public static String getRootRules() {
        CollationLoader.loadRootRules();
        return rootRules;
    }

    static String loadRules(ULocale uLocale, String string) {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/coll", uLocale);
        ICUResourceBundle iCUResourceBundle = ((ICUResourceBundle)uResourceBundle).getWithFallback("collations/" + ASCII.toLowerCase(string));
        String string2 = iCUResourceBundle.getString("Sequence");
        return string2;
    }

    private static final UResourceBundle findWithFallback(UResourceBundle uResourceBundle, String string) {
        return ((ICUResourceBundle)uResourceBundle).findWithFallback(string);
    }

    public static CollationTailoring loadTailoring(ULocale uLocale, Output<ULocale> output) {
        UResourceBundle uResourceBundle;
        UResourceBundle uResourceBundle2;
        UResourceBundle uResourceBundle3;
        CollationTailoring collationTailoring = CollationRoot.getRoot();
        String string = uLocale.getName();
        if (string.length() == 0 || string.equals("root")) {
            output.value = ULocale.ROOT;
            return collationTailoring;
        }
        ICUResourceBundle iCUResourceBundle = null;
        try {
            iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/coll", uLocale, ICUResourceBundle.OpenType.LOCALE_ROOT);
        } catch (MissingResourceException missingResourceException) {
            output.value = ULocale.ROOT;
            return collationTailoring;
        }
        ULocale uLocale2 = ((UResourceBundle)iCUResourceBundle).getULocale();
        String string2 = uLocale2.getName();
        if (string2.length() == 0 || string2.equals("root")) {
            uLocale2 = ULocale.ROOT;
        }
        output.value = uLocale2;
        try {
            uResourceBundle3 = iCUResourceBundle.get("collations");
            if (uResourceBundle3 == null) {
                return collationTailoring;
            }
        } catch (MissingResourceException missingResourceException) {
            return collationTailoring;
        }
        String string3 = uLocale.getKeywordValue("collation");
        String string4 = "standard";
        String string5 = ((ICUResourceBundle)uResourceBundle3).findStringWithFallback("default");
        if (string5 != null) {
            string4 = string5;
        }
        if ((uResourceBundle2 = CollationLoader.findWithFallback(uResourceBundle3, string3 = string3 == null || string3.equals("default") ? string4 : ASCII.toLowerCase(string3))) == null && string3.length() > 6 && string3.startsWith("search")) {
            string3 = "search";
            uResourceBundle2 = CollationLoader.findWithFallback(uResourceBundle3, string3);
        }
        if (uResourceBundle2 == null && !string3.equals(string4)) {
            string3 = string4;
            uResourceBundle2 = CollationLoader.findWithFallback(uResourceBundle3, string3);
        }
        if (uResourceBundle2 == null && !string3.equals("standard")) {
            string3 = "standard";
            uResourceBundle2 = CollationLoader.findWithFallback(uResourceBundle3, string3);
        }
        if (uResourceBundle2 == null) {
            return collationTailoring;
        }
        ULocale uLocale3 = uResourceBundle2.getULocale();
        String string6 = uLocale3.getName();
        if (string6.length() == 0 || string6.equals("root")) {
            uLocale3 = ULocale.ROOT;
            if (string3.equals("standard")) {
                return collationTailoring;
            }
        }
        CollationTailoring collationTailoring2 = new CollationTailoring(collationTailoring.settings);
        collationTailoring2.actualLocale = uLocale3;
        UResourceBundle uResourceBundle4 = uResourceBundle2.get("%%CollationBin");
        ByteBuffer byteBuffer = uResourceBundle4.getBinary();
        try {
            CollationDataReader.read(collationTailoring, byteBuffer, collationTailoring2);
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException("Failed to load collation tailoring data for locale:" + uLocale3 + " type:" + string3, iOException);
        }
        try {
            collationTailoring2.setRulesResource(uResourceBundle2.get("Sequence"));
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        if (!string3.equals(string4)) {
            output.value = uLocale2.setKeywordValue("collation", string3);
        }
        if (!uLocale3.equals(uLocale2) && (string5 = ((ICUResourceBundle)(uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/coll", uLocale3))).findStringWithFallback("collations/default")) != null) {
            string4 = string5;
        }
        if (!string3.equals(string4)) {
            collationTailoring2.actualLocale = collationTailoring2.actualLocale.setKeywordValue("collation", string3);
        }
        return collationTailoring2;
    }

    private static final class ASCII {
        private ASCII() {
        }

        static String toLowerCase(String string) {
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                if ('A' > c || c > 'Z') continue;
                StringBuilder stringBuilder = new StringBuilder(string.length());
                stringBuilder.append(string, 0, i).append((char)(c + 32));
                while (++i < string.length()) {
                    c = string.charAt(i);
                    if ('A' <= c && c <= 'Z') {
                        c = (char)(c + 32);
                    }
                    stringBuilder.append(c);
                }
                return stringBuilder.toString();
            }
            return string;
        }
    }
}

