package com.ibm.icu.impl;

import com.ibm.icu.util.*;
import java.util.*;

public final class CalendarUtil
{
    private static final String CALKEY = "calendar";
    private static final String DEFCAL = "gregorian";
    
    public static String getCalendarType(final ULocale loc) {
        String calType = loc.getKeywordValue("calendar");
        if (calType != null) {
            return calType.toLowerCase(Locale.ROOT);
        }
        final ULocale canonical = ULocale.createCanonical(loc.toString());
        calType = canonical.getKeywordValue("calendar");
        if (calType != null) {
            return calType;
        }
        final String region = ULocale.getRegionForSupplementalData(canonical, true);
        return CalendarPreferences.INSTANCE.getCalendarTypeForRegion(region);
    }
    
    private static final class CalendarPreferences extends UResource.Sink
    {
        private static final CalendarPreferences INSTANCE;
        Map<String, String> prefs;
        
        CalendarPreferences() {
            this.prefs = new TreeMap<String, String>();
            try {
                final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "supplementalData");
                rb.getAllItemsWithFallback("calendarPreferenceData", this);
            }
            catch (MissingResourceException ex) {}
        }
        
        String getCalendarTypeForRegion(final String region) {
            final String type = this.prefs.get(region);
            return (type == null) ? "gregorian" : type;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table calendarPreferenceData = value.getTable();
            for (int i = 0; calendarPreferenceData.getKeyAndValue(i, key, value); ++i) {
                final UResource.Array types = value.getArray();
                if (types.getValue(0, value)) {
                    final String type = value.getString();
                    if (!type.equals("gregorian")) {
                        this.prefs.put(key.toString(), type);
                    }
                }
            }
        }
        
        static {
            INSTANCE = new CalendarPreferences();
        }
    }
}
