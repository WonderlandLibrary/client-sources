/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUConfig;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.impl.TZDBTimeZoneNames;
import com.ibm.icu.impl.TimeZoneNamesImpl;
import com.ibm.icu.util.ULocale;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

public abstract class TimeZoneNames
implements Serializable {
    private static final long serialVersionUID = -9180227029248969153L;
    private static Cache TZNAMES_CACHE = new Cache(null);
    private static final Factory TZNAMES_FACTORY;
    private static final String FACTORY_NAME_PROP = "com.ibm.icu.text.TimeZoneNames.Factory.impl";
    private static final String DEFAULT_FACTORY_CLASS = "com.ibm.icu.impl.TimeZoneNamesFactoryImpl";

    public static TimeZoneNames getInstance(ULocale uLocale) {
        String string = uLocale.getBaseName();
        return (TimeZoneNames)TZNAMES_CACHE.getInstance(string, uLocale);
    }

    public static TimeZoneNames getInstance(Locale locale) {
        return TimeZoneNames.getInstance(ULocale.forLocale(locale));
    }

    public static TimeZoneNames getTZDBInstance(ULocale uLocale) {
        return new TZDBTimeZoneNames(uLocale);
    }

    public abstract Set<String> getAvailableMetaZoneIDs();

    public abstract Set<String> getAvailableMetaZoneIDs(String var1);

    public abstract String getMetaZoneID(String var1, long var2);

    public abstract String getReferenceZoneID(String var1, String var2);

    public abstract String getMetaZoneDisplayName(String var1, NameType var2);

    public final String getDisplayName(String string, NameType nameType, long l) {
        String string2 = this.getTimeZoneDisplayName(string, nameType);
        if (string2 == null) {
            String string3 = this.getMetaZoneID(string, l);
            string2 = this.getMetaZoneDisplayName(string3, nameType);
        }
        return string2;
    }

    public abstract String getTimeZoneDisplayName(String var1, NameType var2);

    public String getExemplarLocationName(String string) {
        return TimeZoneNamesImpl.getDefaultExemplarLocationName(string);
    }

    public Collection<MatchInfo> find(CharSequence charSequence, int n, EnumSet<NameType> enumSet) {
        throw new UnsupportedOperationException("The method is not implemented in TimeZoneNames base class.");
    }

    @Deprecated
    public void loadAllDisplayNames() {
    }

    @Deprecated
    public void getDisplayNames(String string, NameType[] nameTypeArray, long l, String[] stringArray, int n) {
        if (string == null || string.length() == 0) {
            return;
        }
        String string2 = null;
        for (int i = 0; i < nameTypeArray.length; ++i) {
            NameType nameType = nameTypeArray[i];
            String string3 = this.getTimeZoneDisplayName(string, nameType);
            if (string3 == null) {
                if (string2 == null) {
                    string2 = this.getMetaZoneID(string, l);
                }
                string3 = this.getMetaZoneDisplayName(string2, nameType);
            }
            stringArray[n + i] = string3;
        }
    }

    protected TimeZoneNames() {
    }

    static Factory access$100() {
        return TZNAMES_FACTORY;
    }

    static {
        Factory factory = null;
        String string = ICUConfig.get(FACTORY_NAME_PROP, DEFAULT_FACTORY_CLASS);
        while (true) {
            try {
                factory = (Factory)Class.forName(string).newInstance();
                break;
            } catch (ClassNotFoundException classNotFoundException) {
            } catch (IllegalAccessException illegalAccessException) {
            } catch (InstantiationException instantiationException) {
                // empty catch block
            }
            if (string.equals(DEFAULT_FACTORY_CLASS)) break;
            string = DEFAULT_FACTORY_CLASS;
        }
        if (factory == null) {
            factory = new DefaultTimeZoneNames.FactoryImpl();
        }
        TZNAMES_FACTORY = factory;
    }

    private static class DefaultTimeZoneNames
    extends TimeZoneNames {
        private static final long serialVersionUID = -995672072494349071L;
        public static final DefaultTimeZoneNames INSTANCE = new DefaultTimeZoneNames();

        private DefaultTimeZoneNames() {
        }

        @Override
        public Set<String> getAvailableMetaZoneIDs() {
            return Collections.emptySet();
        }

        @Override
        public Set<String> getAvailableMetaZoneIDs(String string) {
            return Collections.emptySet();
        }

        @Override
        public String getMetaZoneID(String string, long l) {
            return null;
        }

        @Override
        public String getReferenceZoneID(String string, String string2) {
            return null;
        }

        @Override
        public String getMetaZoneDisplayName(String string, NameType nameType) {
            return null;
        }

        @Override
        public String getTimeZoneDisplayName(String string, NameType nameType) {
            return null;
        }

        @Override
        public Collection<MatchInfo> find(CharSequence charSequence, int n, EnumSet<NameType> enumSet) {
            return Collections.emptyList();
        }

        public static class FactoryImpl
        extends Factory {
            @Override
            public TimeZoneNames getTimeZoneNames(ULocale uLocale) {
                return INSTANCE;
            }
        }
    }

    private static class Cache
    extends SoftCache<String, TimeZoneNames, ULocale> {
        private Cache() {
        }

        @Override
        protected TimeZoneNames createInstance(String string, ULocale uLocale) {
            return TimeZoneNames.access$100().getTimeZoneNames(uLocale);
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (ULocale)object2);
        }

        Cache(1 var1_1) {
            this();
        }
    }

    @Deprecated
    public static abstract class Factory {
        @Deprecated
        public abstract TimeZoneNames getTimeZoneNames(ULocale var1);

        @Deprecated
        protected Factory() {
        }
    }

    public static class MatchInfo {
        private NameType _nameType;
        private String _tzID;
        private String _mzID;
        private int _matchLength;

        public MatchInfo(NameType nameType, String string, String string2, int n) {
            if (nameType == null) {
                throw new IllegalArgumentException("nameType is null");
            }
            if (string == null && string2 == null) {
                throw new IllegalArgumentException("Either tzID or mzID must be available");
            }
            if (n <= 0) {
                throw new IllegalArgumentException("matchLength must be positive value");
            }
            this._nameType = nameType;
            this._tzID = string;
            this._mzID = string2;
            this._matchLength = n;
        }

        public String tzID() {
            return this._tzID;
        }

        public String mzID() {
            return this._mzID;
        }

        public NameType nameType() {
            return this._nameType;
        }

        public int matchLength() {
            return this._matchLength;
        }
    }

    public static enum NameType {
        LONG_GENERIC,
        LONG_STANDARD,
        LONG_DAYLIGHT,
        SHORT_GENERIC,
        SHORT_STANDARD,
        SHORT_DAYLIGHT,
        EXEMPLAR_LOCATION;

    }
}

