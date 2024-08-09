/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.Grego;
import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.JavaTimeZone;
import com.ibm.icu.impl.OlsonTimeZone;
import com.ibm.icu.impl.TimeZoneAdapter;
import com.ibm.icu.impl.ZoneMeta;
import com.ibm.icu.text.TimeZoneFormat;
import com.ibm.icu.text.TimeZoneNames;
import com.ibm.icu.util.BasicTimeZone;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import com.ibm.icu.util.VersionInfo;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.logging.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class TimeZone
implements Serializable,
Cloneable,
Freezable<TimeZone> {
    private static final Logger LOGGER;
    private static final long serialVersionUID = -744942128318337471L;
    public static final int TIMEZONE_ICU = 0;
    public static final int TIMEZONE_JDK = 1;
    public static final int SHORT = 0;
    public static final int LONG = 1;
    public static final int SHORT_GENERIC = 2;
    public static final int LONG_GENERIC = 3;
    public static final int SHORT_GMT = 4;
    public static final int LONG_GMT = 5;
    public static final int SHORT_COMMONLY_USED = 6;
    public static final int GENERIC_LOCATION = 7;
    public static final String UNKNOWN_ZONE_ID = "Etc/Unknown";
    static final String GMT_ZONE_ID = "Etc/GMT";
    public static final TimeZone UNKNOWN_ZONE;
    public static final TimeZone GMT_ZONE;
    private String ID;
    private static volatile TimeZone defaultZone;
    private static int TZ_IMPL;
    private static final String TZIMPL_CONFIG_KEY = "com.ibm.icu.util.TimeZone.DefaultTimeZoneType";
    private static final String TZIMPL_CONFIG_ICU = "ICU";
    private static final String TZIMPL_CONFIG_JDK = "JDK";
    static final boolean $assertionsDisabled;

    public TimeZone() {
    }

    @Deprecated
    protected TimeZone(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.ID = string;
    }

    public abstract int getOffset(int var1, int var2, int var3, int var4, int var5, int var6);

    public int getOffset(long l) {
        int[] nArray = new int[2];
        this.getOffset(l, false, nArray);
        return nArray[0] + nArray[1];
    }

    public void getOffset(long l, boolean bl, int[] nArray) {
        nArray[0] = this.getRawOffset();
        if (!bl) {
            l += (long)nArray[0];
        }
        int[] nArray2 = new int[6];
        int n = 0;
        while (true) {
            Grego.timeToFields(l, nArray2);
            nArray[1] = this.getOffset(1, nArray2[0], nArray2[1], nArray2[2], nArray2[3], nArray2[5]) - nArray[0];
            if (n != 0 || !bl || nArray[1] == 0) break;
            l -= (long)nArray[1];
            ++n;
        }
    }

    public abstract void setRawOffset(int var1);

    public abstract int getRawOffset();

    public String getID() {
        return this.ID;
    }

    public void setID(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
        }
        this.ID = string;
    }

    public final String getDisplayName() {
        return this._getDisplayName(3, false, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public final String getDisplayName(Locale locale) {
        return this._getDisplayName(3, false, ULocale.forLocale(locale));
    }

    public final String getDisplayName(ULocale uLocale) {
        return this._getDisplayName(3, false, uLocale);
    }

    public final String getDisplayName(boolean bl, int n) {
        return this.getDisplayName(bl, n, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public String getDisplayName(boolean bl, int n, Locale locale) {
        return this.getDisplayName(bl, n, ULocale.forLocale(locale));
    }

    public String getDisplayName(boolean bl, int n, ULocale uLocale) {
        if (n < 0 || n > 7) {
            throw new IllegalArgumentException("Illegal style: " + n);
        }
        return this._getDisplayName(n, bl, uLocale);
    }

    private String _getDisplayName(int n, boolean bl, ULocale uLocale) {
        if (uLocale == null) {
            throw new NullPointerException("locale is null");
        }
        String string = null;
        if (n == 7 || n == 3 || n == 2) {
            TimeZoneFormat timeZoneFormat = TimeZoneFormat.getInstance(uLocale);
            long l = System.currentTimeMillis();
            Output<TimeZoneFormat.TimeType> output = new Output<TimeZoneFormat.TimeType>(TimeZoneFormat.TimeType.UNKNOWN);
            switch (n) {
                case 7: {
                    string = timeZoneFormat.format(TimeZoneFormat.Style.GENERIC_LOCATION, this, l, output);
                    break;
                }
                case 3: {
                    string = timeZoneFormat.format(TimeZoneFormat.Style.GENERIC_LONG, this, l, output);
                    break;
                }
                case 2: {
                    string = timeZoneFormat.format(TimeZoneFormat.Style.GENERIC_SHORT, this, l, output);
                }
            }
            if (bl && output.value == TimeZoneFormat.TimeType.STANDARD || !bl && output.value == TimeZoneFormat.TimeType.DAYLIGHT) {
                int n2 = bl ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
                string = n == 2 ? timeZoneFormat.formatOffsetShortLocalizedGMT(n2) : timeZoneFormat.formatOffsetLocalizedGMT(n2);
            }
        } else if (n == 5 || n == 4) {
            TimeZoneFormat timeZoneFormat = TimeZoneFormat.getInstance(uLocale);
            int n3 = bl && this.useDaylightTime() ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
            switch (n) {
                case 5: {
                    string = timeZoneFormat.formatOffsetLocalizedGMT(n3);
                    break;
                }
                case 4: {
                    string = timeZoneFormat.formatOffsetISO8601Basic(n3, false, false, true);
                }
            }
        } else {
            if (!$assertionsDisabled && n != 1 && n != 0 && n != 6) {
                throw new AssertionError();
            }
            long l = System.currentTimeMillis();
            TimeZoneNames timeZoneNames = TimeZoneNames.getInstance(uLocale);
            TimeZoneNames.NameType nameType = null;
            switch (n) {
                case 1: {
                    nameType = bl ? TimeZoneNames.NameType.LONG_DAYLIGHT : TimeZoneNames.NameType.LONG_STANDARD;
                    break;
                }
                case 0: 
                case 6: {
                    nameType = bl ? TimeZoneNames.NameType.SHORT_DAYLIGHT : TimeZoneNames.NameType.SHORT_STANDARD;
                }
            }
            string = timeZoneNames.getDisplayName(ZoneMeta.getCanonicalCLDRID(this), nameType, l);
            if (string == null) {
                TimeZoneFormat timeZoneFormat = TimeZoneFormat.getInstance(uLocale);
                int n4 = bl && this.useDaylightTime() ? this.getRawOffset() + this.getDSTSavings() : this.getRawOffset();
                String string2 = string = n == 1 ? timeZoneFormat.formatOffsetLocalizedGMT(n4) : timeZoneFormat.formatOffsetShortLocalizedGMT(n4);
            }
        }
        if (!$assertionsDisabled && string == null) {
            throw new AssertionError();
        }
        return string;
    }

    public int getDSTSavings() {
        if (this.useDaylightTime()) {
            return 1;
        }
        return 1;
    }

    public abstract boolean useDaylightTime();

    public boolean observesDaylightTime() {
        return this.useDaylightTime() || this.inDaylightTime(new Date());
    }

    public abstract boolean inDaylightTime(Date var1);

    public static TimeZone getTimeZone(String string) {
        return TimeZone.getTimeZone(string, TZ_IMPL, false);
    }

    public static TimeZone getFrozenTimeZone(String string) {
        return TimeZone.getTimeZone(string, TZ_IMPL, true);
    }

    public static TimeZone getTimeZone(String string, int n) {
        return TimeZone.getTimeZone(string, n, false);
    }

    private static TimeZone getTimeZone(String string, int n, boolean bl) {
        TimeZone timeZone;
        if (n == 1) {
            timeZone = JavaTimeZone.createTimeZone(string);
            if (timeZone != null) {
                return bl ? timeZone.freeze() : timeZone;
            }
            timeZone = TimeZone.getFrozenICUTimeZone(string, false);
        } else {
            timeZone = TimeZone.getFrozenICUTimeZone(string, true);
        }
        if (timeZone == null) {
            LOGGER.fine("\"" + string + "\" is a bogus id so timezone is falling back to Etc/Unknown(GMT).");
            timeZone = UNKNOWN_ZONE;
        }
        return bl ? timeZone : timeZone.cloneAsThawed();
    }

    static BasicTimeZone getFrozenICUTimeZone(String string, boolean bl) {
        BasicTimeZone basicTimeZone = null;
        if (bl) {
            basicTimeZone = ZoneMeta.getSystemTimeZone(string);
        }
        if (basicTimeZone == null) {
            basicTimeZone = ZoneMeta.getCustomTimeZone(string);
        }
        return basicTimeZone;
    }

    public static synchronized void setDefaultTimeZoneType(int n) {
        if (n != 0 && n != 1) {
            throw new IllegalArgumentException("Invalid timezone type");
        }
        TZ_IMPL = n;
    }

    public static int getDefaultTimeZoneType() {
        return TZ_IMPL;
    }

    public static Set<String> getAvailableIDs(SystemTimeZoneType systemTimeZoneType, String string, Integer n) {
        return ZoneMeta.getAvailableIDs(systemTimeZoneType, string, n);
    }

    public static String[] getAvailableIDs(int n) {
        Set<String> set = TimeZone.getAvailableIDs(SystemTimeZoneType.ANY, null, n);
        return set.toArray(new String[0]);
    }

    public static String[] getAvailableIDs(String string) {
        Set<String> set = TimeZone.getAvailableIDs(SystemTimeZoneType.ANY, string, null);
        return set.toArray(new String[0]);
    }

    public static String[] getAvailableIDs() {
        Set<String> set = TimeZone.getAvailableIDs(SystemTimeZoneType.ANY, null, null);
        return set.toArray(new String[0]);
    }

    public static int countEquivalentIDs(String string) {
        return ZoneMeta.countEquivalentIDs(string);
    }

    public static String getEquivalentID(String string, int n) {
        return ZoneMeta.getEquivalentID(string, n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TimeZone getDefault() {
        TimeZone timeZone = defaultZone;
        if (timeZone != null) return timeZone.cloneAsThawed();
        Class<java.util.TimeZone> clazz = java.util.TimeZone.class;
        synchronized (java.util.TimeZone.class) {
            Class<TimeZone> clazz2 = TimeZone.class;
            synchronized (TimeZone.class) {
                timeZone = defaultZone;
                if (timeZone != null) return timeZone.cloneAsThawed();
                if (TZ_IMPL == 1) {
                    timeZone = new JavaTimeZone();
                } else {
                    java.util.TimeZone timeZone2 = java.util.TimeZone.getDefault();
                    timeZone = TimeZone.getFrozenTimeZone(timeZone2.getID());
                }
                defaultZone = timeZone;
                // ** MonitorExit[var2_2] (shouldn't be in output)
                return timeZone.cloneAsThawed();
            }
        }
    }

    public static synchronized void setDefault(TimeZone timeZone) {
        TimeZone.setICUDefault(timeZone);
        if (timeZone != null) {
            String string;
            java.util.TimeZone timeZone2 = null;
            if (timeZone instanceof JavaTimeZone) {
                timeZone2 = ((JavaTimeZone)timeZone).unwrap();
            } else if (timeZone instanceof OlsonTimeZone && !(string = timeZone.getID()).equals((timeZone2 = java.util.TimeZone.getTimeZone(string)).getID()) && !(string = TimeZone.getCanonicalID(string)).equals((timeZone2 = java.util.TimeZone.getTimeZone(string)).getID())) {
                timeZone2 = null;
            }
            if (timeZone2 == null) {
                timeZone2 = TimeZoneAdapter.wrap(timeZone);
            }
            java.util.TimeZone.setDefault(timeZone2);
        }
    }

    @Deprecated
    public static synchronized void setICUDefault(TimeZone timeZone) {
        defaultZone = timeZone == null ? null : (timeZone.isFrozen() ? timeZone : ((TimeZone)timeZone.clone()).freeze());
    }

    public boolean hasSameRules(TimeZone timeZone) {
        return timeZone != null && this.getRawOffset() == timeZone.getRawOffset() && this.useDaylightTime() == timeZone.useDaylightTime();
    }

    public Object clone() {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        return this.ID.equals(((TimeZone)object).ID);
    }

    public int hashCode() {
        return this.ID.hashCode();
    }

    public static String getTZDataVersion() {
        return VersionInfo.getTZDataVersion();
    }

    public static String getCanonicalID(String string) {
        return TimeZone.getCanonicalID(string, null);
    }

    public static String getCanonicalID(String string, boolean[] blArray) {
        String string2 = null;
        boolean bl = false;
        if (string != null && string.length() != 0) {
            if (string.equals(UNKNOWN_ZONE_ID)) {
                string2 = UNKNOWN_ZONE_ID;
                bl = false;
            } else {
                string2 = ZoneMeta.getCanonicalCLDRID(string);
                if (string2 != null) {
                    bl = true;
                } else {
                    string2 = ZoneMeta.getCustomID(string);
                }
            }
        }
        if (blArray != null) {
            blArray[0] = bl;
        }
        return string2;
    }

    public static String getRegion(String string) {
        String string2 = null;
        if (!string.equals(UNKNOWN_ZONE_ID)) {
            string2 = ZoneMeta.getRegion(string);
        }
        if (string2 == null) {
            throw new IllegalArgumentException("Unknown system zone id: " + string);
        }
        return string2;
    }

    public static String getWindowsID(String string) {
        boolean[] blArray = new boolean[]{false};
        string = TimeZone.getCanonicalID(string, blArray);
        if (!blArray[0]) {
            return null;
        }
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        UResourceBundle uResourceBundle2 = uResourceBundle.get("mapTimezones");
        UResourceBundleIterator uResourceBundleIterator = uResourceBundle2.getIterator();
        while (uResourceBundleIterator.hasNext()) {
            UResourceBundle uResourceBundle3 = uResourceBundleIterator.next();
            if (uResourceBundle3.getType() != 2) continue;
            UResourceBundleIterator uResourceBundleIterator2 = uResourceBundle3.getIterator();
            while (uResourceBundleIterator2.hasNext()) {
                String[] stringArray;
                UResourceBundle uResourceBundle4 = uResourceBundleIterator2.next();
                if (uResourceBundle4.getType() != 0) continue;
                for (String string2 : stringArray = uResourceBundle4.getString().split(" ")) {
                    if (!string2.equals(string)) continue;
                    return uResourceBundle3.getKey();
                }
            }
        }
        return null;
    }

    public static String getIDForWindowsID(String string, String string2) {
        String string3 = null;
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "windowsZones", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        UResourceBundle uResourceBundle2 = uResourceBundle.get("mapTimezones");
        try {
            UResourceBundle uResourceBundle3 = uResourceBundle2.get(string);
            if (string2 != null) {
                try {
                    int n;
                    string3 = uResourceBundle3.getString(string2);
                    if (string3 != null && (n = string3.indexOf(32)) > 0) {
                        string3 = string3.substring(0, n);
                    }
                } catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
            }
            if (string3 == null) {
                string3 = uResourceBundle3.getString("001");
            }
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return string3;
    }

    @Override
    public boolean isFrozen() {
        return true;
    }

    @Override
    public TimeZone freeze() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    @Override
    public TimeZone cloneAsThawed() {
        try {
            TimeZone timeZone = (TimeZone)super.clone();
            return timeZone;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    static {
        $assertionsDisabled = !TimeZone.class.desiredAssertionStatus();
        LOGGER = Logger.getLogger("com.ibm.icu.util.TimeZone");
        UNKNOWN_ZONE = new ConstantZone(0, UNKNOWN_ZONE_ID, null).freeze();
        GMT_ZONE = new ConstantZone(0, GMT_ZONE_ID, null).freeze();
        defaultZone = null;
        TZ_IMPL = 0;
        String string = ICUConfig.get(TZIMPL_CONFIG_KEY, TZIMPL_CONFIG_ICU);
        if (string.equalsIgnoreCase(TZIMPL_CONFIG_JDK)) {
            TZ_IMPL = 1;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class ConstantZone
    extends TimeZone {
        private static final long serialVersionUID = 1L;
        private int rawOffset;
        private volatile transient boolean isFrozen = false;

        private ConstantZone(int n, String string) {
            super(string);
            this.rawOffset = n;
        }

        @Override
        public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
            return this.rawOffset;
        }

        @Override
        public void setRawOffset(int n) {
            if (this.isFrozen()) {
                throw new UnsupportedOperationException("Attempt to modify a frozen TimeZone instance.");
            }
            this.rawOffset = n;
        }

        @Override
        public int getRawOffset() {
            return this.rawOffset;
        }

        @Override
        public boolean useDaylightTime() {
            return true;
        }

        @Override
        public boolean inDaylightTime(Date date) {
            return true;
        }

        @Override
        public boolean isFrozen() {
            return this.isFrozen;
        }

        @Override
        public TimeZone freeze() {
            this.isFrozen = true;
            return this;
        }

        @Override
        public TimeZone cloneAsThawed() {
            ConstantZone constantZone = (ConstantZone)super.cloneAsThawed();
            constantZone.isFrozen = false;
            return constantZone;
        }

        @Override
        public Object cloneAsThawed() {
            return this.cloneAsThawed();
        }

        @Override
        public Object freeze() {
            return this.freeze();
        }

        ConstantZone(int n, String string, 1 var3_3) {
            this(n, string);
        }
    }

    public static enum SystemTimeZoneType {
        ANY,
        CANONICAL,
        CANONICAL_LOCATION;

    }
}

