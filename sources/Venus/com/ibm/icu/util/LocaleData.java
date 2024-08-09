/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.VersionInfo;
import java.util.MissingResourceException;

public final class LocaleData {
    private static final String MEASUREMENT_SYSTEM = "MeasurementSystem";
    private static final String PAPER_SIZE = "PaperSize";
    private static final String LOCALE_DISPLAY_PATTERN = "localeDisplayPattern";
    private static final String PATTERN = "pattern";
    private static final String SEPARATOR = "separator";
    private boolean noSubstitute;
    private ICUResourceBundle bundle;
    private ICUResourceBundle langBundle;
    public static final int ES_STANDARD = 0;
    public static final int ES_AUXILIARY = 1;
    public static final int ES_INDEX = 2;
    @Deprecated
    public static final int ES_CURRENCY = 3;
    public static final int ES_PUNCTUATION = 4;
    @Deprecated
    public static final int ES_COUNT = 5;
    public static final int QUOTATION_START = 0;
    public static final int QUOTATION_END = 1;
    public static final int ALT_QUOTATION_START = 2;
    public static final int ALT_QUOTATION_END = 3;
    @Deprecated
    public static final int DELIMITER_COUNT = 4;
    private static final String[] DELIMITER_TYPES = new String[]{"quotationStart", "quotationEnd", "alternateQuotationStart", "alternateQuotationEnd"};
    private static VersionInfo gCLDRVersion = null;

    private LocaleData() {
    }

    public static UnicodeSet getExemplarSet(ULocale uLocale, int n) {
        return LocaleData.getInstance(uLocale).getExemplarSet(n, 0);
    }

    public static UnicodeSet getExemplarSet(ULocale uLocale, int n, int n2) {
        return LocaleData.getInstance(uLocale).getExemplarSet(n, n2);
    }

    public UnicodeSet getExemplarSet(int n, int n2) {
        String[] stringArray = new String[]{"ExemplarCharacters", "AuxExemplarCharacters", "ExemplarCharactersIndex", "ExemplarCharactersCurrency", "ExemplarCharactersPunctuation"};
        if (n2 == 3) {
            return this.noSubstitute ? null : UnicodeSet.EMPTY;
        }
        try {
            String string = stringArray[n2];
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)this.bundle.get(string);
            if (this.noSubstitute && !this.bundle.isRoot() && iCUResourceBundle.isRoot()) {
                return null;
            }
            String string2 = iCUResourceBundle.getString();
            return new UnicodeSet(string2, 1 | n);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new IllegalArgumentException(arrayIndexOutOfBoundsException);
        } catch (Exception exception) {
            return this.noSubstitute ? null : UnicodeSet.EMPTY;
        }
    }

    public static final LocaleData getInstance(ULocale uLocale) {
        LocaleData localeData = new LocaleData();
        localeData.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
        localeData.langBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/lang", uLocale);
        localeData.noSubstitute = false;
        return localeData;
    }

    public static final LocaleData getInstance() {
        return LocaleData.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public void setNoSubstitute(boolean bl) {
        this.noSubstitute = bl;
    }

    public boolean getNoSubstitute() {
        return this.noSubstitute;
    }

    public String getDelimiter(int n) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)this.bundle.get("delimiters");
        ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.getWithFallback(DELIMITER_TYPES[n]);
        if (this.noSubstitute && !this.bundle.isRoot() && iCUResourceBundle2.isRoot()) {
            return null;
        }
        return iCUResourceBundle2.getString();
    }

    private static UResourceBundle measurementTypeBundleForLocale(ULocale uLocale, String string) {
        UResourceBundle uResourceBundle = null;
        String string2 = ULocale.getRegionForSupplementalData(uLocale, true);
        try {
            UResourceBundle uResourceBundle2 = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            UResourceBundle uResourceBundle3 = uResourceBundle2.get("measurementData");
            UResourceBundle uResourceBundle4 = null;
            try {
                uResourceBundle4 = uResourceBundle3.get(string2);
                uResourceBundle = uResourceBundle4.get(string);
            } catch (MissingResourceException missingResourceException) {
                uResourceBundle4 = uResourceBundle3.get("001");
                uResourceBundle = uResourceBundle4.get(string);
            }
        } catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        return uResourceBundle;
    }

    public static final MeasurementSystem getMeasurementSystem(ULocale uLocale) {
        UResourceBundle uResourceBundle = LocaleData.measurementTypeBundleForLocale(uLocale, MEASUREMENT_SYSTEM);
        switch (uResourceBundle.getInt()) {
            case 0: {
                return MeasurementSystem.SI;
            }
            case 1: {
                return MeasurementSystem.US;
            }
            case 2: {
                return MeasurementSystem.UK;
            }
        }
        return null;
    }

    public static final PaperSize getPaperSize(ULocale uLocale) {
        UResourceBundle uResourceBundle = LocaleData.measurementTypeBundleForLocale(uLocale, PAPER_SIZE);
        int[] nArray = uResourceBundle.getIntVector();
        return new PaperSize(nArray[0], nArray[1], null);
    }

    public String getLocaleDisplayPattern() {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)this.langBundle.get(LOCALE_DISPLAY_PATTERN);
        String string = iCUResourceBundle.getStringWithFallback(PATTERN);
        return string;
    }

    public String getLocaleSeparator() {
        String string = "{0}";
        String string2 = "{1}";
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)this.langBundle.get(LOCALE_DISPLAY_PATTERN);
        String string3 = iCUResourceBundle.getStringWithFallback(SEPARATOR);
        int n = string3.indexOf(string);
        int n2 = string3.indexOf(string2);
        if (n >= 0 && n2 >= 0 && n <= n2) {
            return string3.substring(n + string.length(), n2);
        }
        return string3;
    }

    public static VersionInfo getCLDRVersion() {
        if (gCLDRVersion == null) {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            UResourceBundle uResourceBundle2 = uResourceBundle.get("cldrVersion");
            gCLDRVersion = VersionInfo.getInstance(uResourceBundle2.getString());
        }
        return gCLDRVersion;
    }

    public static final class PaperSize {
        private int height;
        private int width;

        private PaperSize(int n, int n2) {
            this.height = n;
            this.width = n2;
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        PaperSize(int n, int n2, 1 var3_3) {
            this(n, n2);
        }
    }

    public static final class MeasurementSystem {
        public static final MeasurementSystem SI = new MeasurementSystem();
        public static final MeasurementSystem US = new MeasurementSystem();
        public static final MeasurementSystem UK = new MeasurementSystem();

        private MeasurementSystem() {
        }
    }
}

