/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.OlsonTimeZone;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.SimpleTimeZone;
import com.ibm.icu.util.TimeZone;
import com.ibm.icu.util.UResourceBundle;
import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.util.Collections;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeSet;

public final class ZoneMeta {
    private static final boolean ASSERT = false;
    private static final String ZONEINFORESNAME = "zoneinfo64";
    private static final String kREGIONS = "Regions";
    private static final String kZONES = "Zones";
    private static final String kNAMES = "Names";
    private static final String kGMT_ID = "GMT";
    private static final String kCUSTOM_TZ_PREFIX = "GMT";
    private static final String kWorld = "001";
    private static SoftReference<Set<String>> REF_SYSTEM_ZONES;
    private static SoftReference<Set<String>> REF_CANONICAL_SYSTEM_ZONES;
    private static SoftReference<Set<String>> REF_CANONICAL_SYSTEM_LOCATION_ZONES;
    private static String[] ZONEIDS;
    private static ICUCache<String, String> CANONICAL_ID_CACHE;
    private static ICUCache<String, String> REGION_CACHE;
    private static ICUCache<String, Boolean> SINGLE_COUNTRY_CACHE;
    private static final SystemTimeZoneCache SYSTEM_ZONE_CACHE;
    private static final int kMAX_CUSTOM_HOUR = 23;
    private static final int kMAX_CUSTOM_MIN = 59;
    private static final int kMAX_CUSTOM_SEC = 59;
    private static final CustomTimeZoneCache CUSTOM_ZONE_CACHE;
    static final boolean $assertionsDisabled;

    private static synchronized Set<String> getSystemZIDs() {
        Set<String> set = null;
        if (REF_SYSTEM_ZONES != null) {
            set = REF_SYSTEM_ZONES.get();
        }
        if (set == null) {
            String[] stringArray;
            TreeSet<String> treeSet = new TreeSet<String>();
            for (String string : stringArray = ZoneMeta.getZoneIDs()) {
                if (string.equals("Etc/Unknown")) continue;
                treeSet.add(string);
            }
            set = Collections.unmodifiableSet(treeSet);
            REF_SYSTEM_ZONES = new SoftReference<Set<String>>(set);
        }
        return set;
    }

    private static synchronized Set<String> getCanonicalSystemZIDs() {
        Set<String> set = null;
        if (REF_CANONICAL_SYSTEM_ZONES != null) {
            set = REF_CANONICAL_SYSTEM_ZONES.get();
        }
        if (set == null) {
            String[] stringArray;
            TreeSet<String> treeSet = new TreeSet<String>();
            for (String string : stringArray = ZoneMeta.getZoneIDs()) {
                String string2;
                if (string.equals("Etc/Unknown") || !string.equals(string2 = ZoneMeta.getCanonicalCLDRID(string))) continue;
                treeSet.add(string);
            }
            set = Collections.unmodifiableSet(treeSet);
            REF_CANONICAL_SYSTEM_ZONES = new SoftReference<Set<String>>(set);
        }
        return set;
    }

    private static synchronized Set<String> getCanonicalSystemLocationZIDs() {
        Set<String> set = null;
        if (REF_CANONICAL_SYSTEM_LOCATION_ZONES != null) {
            set = REF_CANONICAL_SYSTEM_LOCATION_ZONES.get();
        }
        if (set == null) {
            String[] stringArray;
            TreeSet<String> treeSet = new TreeSet<String>();
            for (String string : stringArray = ZoneMeta.getZoneIDs()) {
                String string2;
                String string3;
                if (string.equals("Etc/Unknown") || !string.equals(string3 = ZoneMeta.getCanonicalCLDRID(string)) || (string2 = ZoneMeta.getRegion(string)) == null || string2.equals(kWorld)) continue;
                treeSet.add(string);
            }
            set = Collections.unmodifiableSet(treeSet);
            REF_CANONICAL_SYSTEM_LOCATION_ZONES = new SoftReference<Set<String>>(set);
        }
        return set;
    }

    public static Set<String> getAvailableIDs(TimeZone.SystemTimeZoneType systemTimeZoneType, String string, Integer n) {
        Set<String> set = null;
        switch (1.$SwitchMap$com$ibm$icu$util$TimeZone$SystemTimeZoneType[systemTimeZoneType.ordinal()]) {
            case 1: {
                set = ZoneMeta.getSystemZIDs();
                break;
            }
            case 2: {
                set = ZoneMeta.getCanonicalSystemZIDs();
                break;
            }
            case 3: {
                set = ZoneMeta.getCanonicalSystemLocationZIDs();
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown SystemTimeZoneType");
            }
        }
        if (string == null && n == null) {
            return set;
        }
        if (string != null) {
            string = string.toUpperCase(Locale.ENGLISH);
        }
        TreeSet<String> treeSet = new TreeSet<String>();
        for (String string2 : set) {
            Object object;
            if (string != null && !string.equals(object = ZoneMeta.getRegion(string2)) || n != null && ((object = ZoneMeta.getSystemTimeZone(string2)) == null || !n.equals(((TimeZone)object).getRawOffset()))) continue;
            treeSet.add(string2);
        }
        if (treeSet.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public static synchronized int countEquivalentIDs(String string) {
        int n = 0;
        UResourceBundle uResourceBundle = ZoneMeta.openOlsonResource(null, string);
        if (uResourceBundle != null) {
            try {
                UResourceBundle uResourceBundle2 = uResourceBundle.get("links");
                int[] nArray = uResourceBundle2.getIntVector();
                n = nArray.length;
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return n;
    }

    public static synchronized String getEquivalentID(String string, int n) {
        UResourceBundle uResourceBundle;
        Object object = "";
        if (n >= 0 && (uResourceBundle = ZoneMeta.openOlsonResource(null, string)) != null) {
            Object object2;
            int n2 = -1;
            try {
                object2 = uResourceBundle.get("links");
                int[] nArray = ((UResourceBundle)object2).getIntVector();
                if (n < nArray.length) {
                    n2 = nArray[n];
                }
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            if (n2 >= 0 && (object2 = ZoneMeta.getZoneID(n2)) != null) {
                object = object2;
            }
        }
        return object;
    }

    private static synchronized String[] getZoneIDs() {
        if (ZONEIDS == null) {
            try {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                ZONEIDS = uResourceBundle.getStringArray(kNAMES);
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        if (ZONEIDS == null) {
            ZONEIDS = new String[0];
        }
        return ZONEIDS;
    }

    private static String getZoneID(int n) {
        String[] stringArray;
        if (n >= 0 && n < (stringArray = ZoneMeta.getZoneIDs()).length) {
            return stringArray[n];
        }
        return null;
    }

    private static int getZoneIndex(String string) {
        int n = -1;
        String[] stringArray = ZoneMeta.getZoneIDs();
        if (stringArray.length > 0) {
            int n2;
            int n3 = 0;
            int n4 = stringArray.length;
            int n5 = Integer.MAX_VALUE;
            while (n5 != (n2 = (n3 + n4) / 2)) {
                n5 = n2;
                int n6 = string.compareTo(stringArray[n2]);
                if (n6 == 0) {
                    n = n2;
                    break;
                }
                if (n6 < 0) {
                    n4 = n2;
                    continue;
                }
                n3 = n2;
            }
        }
        return n;
    }

    public static String getCanonicalCLDRID(TimeZone timeZone) {
        if (timeZone instanceof OlsonTimeZone) {
            return ((OlsonTimeZone)timeZone).getCanonicalID();
        }
        return ZoneMeta.getCanonicalCLDRID(timeZone.getID());
    }

    public static String getCanonicalCLDRID(String string) {
        String string2 = CANONICAL_ID_CACHE.get(string);
        if (string2 == null) {
            string2 = ZoneMeta.findCLDRCanonicalID(string);
            if (string2 == null) {
                try {
                    int n = ZoneMeta.getZoneIndex(string);
                    if (n >= 0) {
                        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                        UResourceBundle uResourceBundle2 = uResourceBundle.get(kZONES);
                        UResourceBundle uResourceBundle3 = uResourceBundle2.get(n);
                        if (uResourceBundle3.getType() == 7) {
                            string = ZoneMeta.getZoneID(uResourceBundle3.getInt());
                            string2 = ZoneMeta.findCLDRCanonicalID(string);
                        }
                        if (string2 == null) {
                            string2 = string;
                        }
                    }
                } catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
            }
            if (string2 != null) {
                CANONICAL_ID_CACHE.put(string, string2);
            }
        }
        return string2;
    }

    private static String findCLDRCanonicalID(String string) {
        String string2 = null;
        String string3 = string.replace('/', ':');
        try {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            UResourceBundle uResourceBundle2 = uResourceBundle.get("typeMap");
            UResourceBundle uResourceBundle3 = uResourceBundle2.get("timezone");
            try {
                uResourceBundle3.get(string3);
                string2 = string;
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            if (string2 == null) {
                UResourceBundle uResourceBundle4 = uResourceBundle.get("typeAlias");
                UResourceBundle uResourceBundle5 = uResourceBundle4.get("timezone");
                string2 = uResourceBundle5.getString(string3);
            }
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return string2;
    }

    public static String getRegion(String string) {
        int n;
        String string2 = REGION_CACHE.get(string);
        if (string2 == null && (n = ZoneMeta.getZoneIndex(string)) >= 0) {
            try {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                UResourceBundle uResourceBundle2 = uResourceBundle.get(kREGIONS);
                if (n < uResourceBundle2.getSize()) {
                    string2 = uResourceBundle2.getString(n);
                }
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            if (string2 != null) {
                REGION_CACHE.put(string, string2);
            }
        }
        return string2;
    }

    public static String getCanonicalCountry(String string) {
        String string2 = ZoneMeta.getRegion(string);
        if (string2 != null && string2.equals(kWorld)) {
            string2 = null;
        }
        return string2;
    }

    public static String getCanonicalCountry(String string, Output<Boolean> output) {
        Object object;
        output.value = Boolean.FALSE;
        String string2 = ZoneMeta.getRegion(string);
        if (string2 != null && string2.equals(kWorld)) {
            return null;
        }
        Boolean bl = SINGLE_COUNTRY_CACHE.get(string);
        if (bl == null) {
            object = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL_LOCATION, string2, null);
            if (!$assertionsDisabled && object.size() < 1) {
                throw new AssertionError();
            }
            bl = object.size() <= 1;
            SINGLE_COUNTRY_CACHE.put(string, bl);
        }
        if (bl.booleanValue()) {
            output.value = Boolean.TRUE;
        } else {
            try {
                object = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "metaZones");
                UResourceBundle uResourceBundle = ((UResourceBundle)object).get("primaryZones");
                String string3 = uResourceBundle.getString(string2);
                if (string.equals(string3)) {
                    output.value = Boolean.TRUE;
                } else {
                    String string4 = ZoneMeta.getCanonicalCLDRID(string);
                    if (string4 != null && string4.equals(string3)) {
                        output.value = Boolean.TRUE;
                    }
                }
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return string2;
    }

    public static UResourceBundle openOlsonResource(UResourceBundle uResourceBundle, String string) {
        UResourceBundle uResourceBundle2 = null;
        int n = ZoneMeta.getZoneIndex(string);
        if (n >= 0) {
            try {
                UResourceBundle uResourceBundle3;
                UResourceBundle uResourceBundle4;
                if (uResourceBundle == null) {
                    uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                }
                if ((uResourceBundle4 = (uResourceBundle3 = uResourceBundle.get(kZONES)).get(n)).getType() == 7) {
                    uResourceBundle4 = uResourceBundle3.get(uResourceBundle4.getInt());
                }
                uResourceBundle2 = uResourceBundle4;
            } catch (MissingResourceException missingResourceException) {
                uResourceBundle2 = null;
            }
        }
        return uResourceBundle2;
    }

    public static OlsonTimeZone getSystemTimeZone(String string) {
        return (OlsonTimeZone)SYSTEM_ZONE_CACHE.getInstance(string, string);
    }

    public static SimpleTimeZone getCustomTimeZone(String string) {
        int[] nArray = new int[4];
        if (ZoneMeta.parseCustomID(string, nArray)) {
            Integer n = nArray[0] * (nArray[1] | nArray[2] << 5 | nArray[3] << 11);
            return (SimpleTimeZone)CUSTOM_ZONE_CACHE.getInstance(n, nArray);
        }
        return null;
    }

    public static String getCustomID(String string) {
        int[] nArray = new int[4];
        if (ZoneMeta.parseCustomID(string, nArray)) {
            return ZoneMeta.formatCustomID(nArray[1], nArray[2], nArray[3], nArray[0] < 0);
        }
        return null;
    }

    static boolean parseCustomID(String string, int[] nArray) {
        NumberFormat numberFormat = null;
        if (string != null && string.length() > 3 && string.toUpperCase(Locale.ENGLISH).startsWith("GMT")) {
            ParsePosition parsePosition = new ParsePosition(3);
            int n = 1;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            if (string.charAt(parsePosition.getIndex()) == '-') {
                n = -1;
            } else if (string.charAt(parsePosition.getIndex()) != '+') {
                return true;
            }
            parsePosition.setIndex(parsePosition.getIndex() + 1);
            numberFormat = NumberFormat.getInstance();
            numberFormat.setParseIntegerOnly(false);
            int n5 = parsePosition.getIndex();
            Number number = numberFormat.parse(string, parsePosition);
            if (parsePosition.getIndex() == n5) {
                return true;
            }
            n2 = number.intValue();
            if (parsePosition.getIndex() < string.length()) {
                if (parsePosition.getIndex() - n5 > 2 || string.charAt(parsePosition.getIndex()) != ':') {
                    return true;
                }
                parsePosition.setIndex(parsePosition.getIndex() + 1);
                int n6 = parsePosition.getIndex();
                number = numberFormat.parse(string, parsePosition);
                if (parsePosition.getIndex() - n6 != 2) {
                    return true;
                }
                n3 = number.intValue();
                if (parsePosition.getIndex() < string.length()) {
                    if (string.charAt(parsePosition.getIndex()) != ':') {
                        return true;
                    }
                    parsePosition.setIndex(parsePosition.getIndex() + 1);
                    n6 = parsePosition.getIndex();
                    number = numberFormat.parse(string, parsePosition);
                    if (parsePosition.getIndex() != string.length() || parsePosition.getIndex() - n6 != 2) {
                        return true;
                    }
                    n4 = number.intValue();
                }
            } else {
                int n7 = parsePosition.getIndex() - n5;
                if (n7 <= 0 || 6 < n7) {
                    return true;
                }
                switch (n7) {
                    case 1: 
                    case 2: {
                        break;
                    }
                    case 3: 
                    case 4: {
                        n3 = n2 % 100;
                        n2 /= 100;
                        break;
                    }
                    case 5: 
                    case 6: {
                        n4 = n2 % 100;
                        n3 = n2 / 100 % 100;
                        n2 /= 10000;
                    }
                }
            }
            if (n2 <= 23 && n3 <= 59 && n4 <= 59) {
                if (nArray != null) {
                    if (nArray.length >= 1) {
                        nArray[0] = n;
                    }
                    if (nArray.length >= 2) {
                        nArray[1] = n2;
                    }
                    if (nArray.length >= 3) {
                        nArray[2] = n3;
                    }
                    if (nArray.length >= 4) {
                        nArray[3] = n4;
                    }
                }
                return false;
            }
        }
        return true;
    }

    public static SimpleTimeZone getCustomTimeZone(int n) {
        boolean bl = false;
        int n2 = n;
        if (n < 0) {
            bl = true;
            n2 = -n;
        }
        int n3 = (n2 /= 1000) % 60;
        int n4 = (n2 /= 60) % 60;
        int n5 = n2 / 60;
        String string = ZoneMeta.formatCustomID(n5, n4, n3, bl);
        return new SimpleTimeZone(n, string);
    }

    static String formatCustomID(int n, int n2, int n3, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("GMT");
        if (n != 0 || n2 != 0) {
            if (bl) {
                stringBuilder.append('-');
            } else {
                stringBuilder.append('+');
            }
            if (n < 10) {
                stringBuilder.append('0');
            }
            stringBuilder.append(n);
            stringBuilder.append(':');
            if (n2 < 10) {
                stringBuilder.append('0');
            }
            stringBuilder.append(n2);
            if (n3 != 0) {
                stringBuilder.append(':');
                if (n3 < 10) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(n3);
            }
        }
        return stringBuilder.toString();
    }

    public static String getShortID(TimeZone timeZone) {
        String string = null;
        string = timeZone instanceof OlsonTimeZone ? ((OlsonTimeZone)timeZone).getCanonicalID() : ZoneMeta.getCanonicalCLDRID(timeZone.getID());
        if (string == null) {
            return null;
        }
        return ZoneMeta.getShortIDFromCanonical(string);
    }

    public static String getShortID(String string) {
        String string2 = ZoneMeta.getCanonicalCLDRID(string);
        if (string2 == null) {
            return null;
        }
        return ZoneMeta.getShortIDFromCanonical(string2);
    }

    private static String getShortIDFromCanonical(String string) {
        String string2 = null;
        String string3 = string.replace('/', ':');
        try {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            UResourceBundle uResourceBundle2 = uResourceBundle.get("typeMap");
            UResourceBundle uResourceBundle3 = uResourceBundle2.get("timezone");
            string2 = uResourceBundle3.getString(string3);
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return string2;
    }

    static {
        $assertionsDisabled = !ZoneMeta.class.desiredAssertionStatus();
        ZONEIDS = null;
        CANONICAL_ID_CACHE = new SimpleCache<String, String>();
        REGION_CACHE = new SimpleCache<String, String>();
        SINGLE_COUNTRY_CACHE = new SimpleCache<String, Boolean>();
        SYSTEM_ZONE_CACHE = new SystemTimeZoneCache(null);
        CUSTOM_ZONE_CACHE = new CustomTimeZoneCache(null);
    }

    private static class CustomTimeZoneCache
    extends SoftCache<Integer, SimpleTimeZone, int[]> {
        static final boolean $assertionsDisabled = !ZoneMeta.class.desiredAssertionStatus();

        private CustomTimeZoneCache() {
        }

        @Override
        protected SimpleTimeZone createInstance(Integer n, int[] nArray) {
            if (!$assertionsDisabled && nArray.length != 4) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && nArray[0] != 1 && nArray[0] != -1) {
                throw new AssertionError();
            }
            if (!($assertionsDisabled || nArray[1] >= 0 && nArray[1] <= 23)) {
                throw new AssertionError();
            }
            if (!($assertionsDisabled || nArray[2] >= 0 && nArray[2] <= 59)) {
                throw new AssertionError();
            }
            if (!($assertionsDisabled || nArray[3] >= 0 && nArray[3] <= 59)) {
                throw new AssertionError();
            }
            String string = ZoneMeta.formatCustomID(nArray[1], nArray[2], nArray[3], nArray[0] < 0);
            int n2 = nArray[0] * ((nArray[1] * 60 + nArray[2]) * 60 + nArray[3]) * 1000;
            SimpleTimeZone simpleTimeZone = new SimpleTimeZone(n2, string);
            simpleTimeZone.freeze();
            return simpleTimeZone;
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((Integer)object, (int[])object2);
        }

        CustomTimeZoneCache(1 var1_1) {
            this();
        }
    }

    private static class SystemTimeZoneCache
    extends SoftCache<String, OlsonTimeZone, String> {
        private SystemTimeZoneCache() {
        }

        @Override
        protected OlsonTimeZone createInstance(String string, String string2) {
            OlsonTimeZone olsonTimeZone = null;
            try {
                UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", ZoneMeta.ZONEINFORESNAME, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
                UResourceBundle uResourceBundle2 = ZoneMeta.openOlsonResource(uResourceBundle, string2);
                if (uResourceBundle2 != null) {
                    olsonTimeZone = new OlsonTimeZone(uResourceBundle, uResourceBundle2, string2);
                    olsonTimeZone.freeze();
                }
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            return olsonTimeZone;
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (String)object2);
        }

        SystemTimeZoneCache(1 var1_1) {
            this();
        }
    }
}

