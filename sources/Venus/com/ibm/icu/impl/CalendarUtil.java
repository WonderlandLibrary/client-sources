/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.TreeMap;

public final class CalendarUtil {
    private static final String CALKEY = "calendar";
    private static final String DEFCAL = "gregorian";

    public static String getCalendarType(ULocale uLocale) {
        String string = uLocale.getKeywordValue(CALKEY);
        if (string != null) {
            return string.toLowerCase(Locale.ROOT);
        }
        ULocale uLocale2 = ULocale.createCanonical(uLocale.toString());
        string = uLocale2.getKeywordValue(CALKEY);
        if (string != null) {
            return string;
        }
        String string2 = ULocale.getRegionForSupplementalData(uLocale2, true);
        return CalendarPreferences.access$000().getCalendarTypeForRegion(string2);
    }

    private static final class CalendarPreferences
    extends UResource.Sink {
        private static final CalendarPreferences INSTANCE = new CalendarPreferences();
        Map<String, String> prefs = new TreeMap<String, String>();

        CalendarPreferences() {
            try {
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData");
                iCUResourceBundle.getAllItemsWithFallback("calendarPreferenceData", this);
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }

        String getCalendarTypeForRegion(String string) {
            String string2 = this.prefs.get(string);
            return string2 == null ? CalendarUtil.DEFCAL : string2;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string;
                UResource.Array array = value.getArray();
                if (array.getValue(0, value) && !(string = value.getString()).equals(CalendarUtil.DEFCAL)) {
                    this.prefs.put(key.toString(), string);
                }
                ++n;
            }
        }

        static CalendarPreferences access$000() {
            return INSTANCE;
        }
    }
}

