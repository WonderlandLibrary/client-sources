package com.ibm.icu.util;

import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;
import java.util.*;

public final class LocaleData
{
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
    private static final String[] DELIMITER_TYPES;
    private static VersionInfo gCLDRVersion;
    
    private LocaleData() {
    }
    
    public static UnicodeSet getExemplarSet(final ULocale locale, final int options) {
        return getInstance(locale).getExemplarSet(options, 0);
    }
    
    public static UnicodeSet getExemplarSet(final ULocale locale, final int options, final int extype) {
        return getInstance(locale).getExemplarSet(options, extype);
    }
    
    public UnicodeSet getExemplarSet(final int options, final int extype) {
        final String[] exemplarSetTypes = { "ExemplarCharacters", "AuxExemplarCharacters", "ExemplarCharactersIndex", "ExemplarCharactersCurrency", "ExemplarCharactersPunctuation" };
        if (extype == 3) {
            return this.noSubstitute ? null : UnicodeSet.EMPTY;
        }
        try {
            final String aKey = exemplarSetTypes[extype];
            final ICUResourceBundle stringBundle = (ICUResourceBundle)this.bundle.get(aKey);
            if (this.noSubstitute && !this.bundle.isRoot() && stringBundle.isRoot()) {
                return null;
            }
            final String unicodeSetPattern = stringBundle.getString();
            return new UnicodeSet(unicodeSetPattern, 0x1 | options);
        }
        catch (ArrayIndexOutOfBoundsException aiooe) {
            throw new IllegalArgumentException(aiooe);
        }
        catch (Exception ex) {
            return this.noSubstitute ? null : UnicodeSet.EMPTY;
        }
    }
    
    public static final LocaleData getInstance(final ULocale locale) {
        final LocaleData ld = new LocaleData();
        ld.bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", locale);
        ld.langBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/lang", locale);
        ld.noSubstitute = false;
        return ld;
    }
    
    public static final LocaleData getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public void setNoSubstitute(final boolean setting) {
        this.noSubstitute = setting;
    }
    
    public boolean getNoSubstitute() {
        return this.noSubstitute;
    }
    
    public String getDelimiter(final int type) {
        final ICUResourceBundle delimitersBundle = (ICUResourceBundle)this.bundle.get("delimiters");
        final ICUResourceBundle stringBundle = delimitersBundle.getWithFallback(LocaleData.DELIMITER_TYPES[type]);
        if (this.noSubstitute && !this.bundle.isRoot() && stringBundle.isRoot()) {
            return null;
        }
        return stringBundle.getString();
    }
    
    private static UResourceBundle measurementTypeBundleForLocale(final ULocale locale, final String measurementType) {
        UResourceBundle measTypeBundle = null;
        final String region = ULocale.getRegionForSupplementalData(locale, true);
        try {
            final UResourceBundle rb = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            final UResourceBundle measurementData = rb.get("measurementData");
            UResourceBundle measDataBundle = null;
            try {
                measDataBundle = measurementData.get(region);
                measTypeBundle = measDataBundle.get(measurementType);
            }
            catch (MissingResourceException mre) {
                measDataBundle = measurementData.get("001");
                measTypeBundle = measDataBundle.get(measurementType);
            }
        }
        catch (MissingResourceException ex) {}
        return measTypeBundle;
    }
    
    public static final MeasurementSystem getMeasurementSystem(final ULocale locale) {
        final UResourceBundle sysBundle = measurementTypeBundleForLocale(locale, "MeasurementSystem");
        switch (sysBundle.getInt()) {
            case 0: {
                return MeasurementSystem.SI;
            }
            case 1: {
                return MeasurementSystem.US;
            }
            case 2: {
                return MeasurementSystem.UK;
            }
            default: {
                return null;
            }
        }
    }
    
    public static final PaperSize getPaperSize(final ULocale locale) {
        final UResourceBundle obj = measurementTypeBundleForLocale(locale, "PaperSize");
        final int[] size = obj.getIntVector();
        return new PaperSize(size[0], size[1]);
    }
    
    public String getLocaleDisplayPattern() {
        final ICUResourceBundle locDispBundle = (ICUResourceBundle)this.langBundle.get("localeDisplayPattern");
        final String localeDisplayPattern = locDispBundle.getStringWithFallback("pattern");
        return localeDisplayPattern;
    }
    
    public String getLocaleSeparator() {
        final String sub0 = "{0}";
        final String sub2 = "{1}";
        final ICUResourceBundle locDispBundle = (ICUResourceBundle)this.langBundle.get("localeDisplayPattern");
        final String localeSeparator = locDispBundle.getStringWithFallback("separator");
        final int index0 = localeSeparator.indexOf(sub0);
        final int index2 = localeSeparator.indexOf(sub2);
        if (index0 >= 0 && index2 >= 0 && index0 <= index2) {
            return localeSeparator.substring(index0 + sub0.length(), index2);
        }
        return localeSeparator;
    }
    
    public static VersionInfo getCLDRVersion() {
        if (LocaleData.gCLDRVersion == null) {
            final UResourceBundle supplementalDataBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            final UResourceBundle cldrVersionBundle = supplementalDataBundle.get("cldrVersion");
            LocaleData.gCLDRVersion = VersionInfo.getInstance(cldrVersionBundle.getString());
        }
        return LocaleData.gCLDRVersion;
    }
    
    static {
        DELIMITER_TYPES = new String[] { "quotationStart", "quotationEnd", "alternateQuotationStart", "alternateQuotationEnd" };
        LocaleData.gCLDRVersion = null;
    }
    
    public static final class MeasurementSystem
    {
        public static final MeasurementSystem SI;
        public static final MeasurementSystem US;
        public static final MeasurementSystem UK;
        
        private MeasurementSystem() {
        }
        
        static {
            SI = new MeasurementSystem();
            US = new MeasurementSystem();
            UK = new MeasurementSystem();
        }
    }
    
    public static final class PaperSize
    {
        private int height;
        private int width;
        
        private PaperSize(final int h, final int w) {
            this.height = h;
            this.width = w;
        }
        
        public int getHeight() {
            return this.height;
        }
        
        public int getWidth() {
            return this.width;
        }
    }
}
