package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import java.util.*;
import com.ibm.icu.util.*;
import java.text.*;

public class PluralRulesLoader extends PluralRules.Factory
{
    private final Map<String, PluralRules> rulesIdToRules;
    private Map<String, String> localeIdToCardinalRulesId;
    private Map<String, String> localeIdToOrdinalRulesId;
    private Map<String, ULocale> rulesIdToEquivalentULocale;
    private static Map<String, PluralRanges> localeIdToPluralRanges;
    public static final PluralRulesLoader loader;
    private static final PluralRanges UNKNOWN_RANGE;
    
    private PluralRulesLoader() {
        this.rulesIdToRules = new HashMap<String, PluralRules>();
    }
    
    @Override
    public ULocale[] getAvailableULocales() {
        final Set<String> keys = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).keySet();
        final ULocale[] locales = new ULocale[keys.size()];
        int n = 0;
        final Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
            locales[n++] = ULocale.createCanonical(iter.next());
        }
        return locales;
    }
    
    @Override
    public ULocale getFunctionalEquivalent(final ULocale locale, final boolean[] isAvailable) {
        if (isAvailable != null && isAvailable.length > 0) {
            final String localeId = ULocale.canonicalize(locale.getBaseName());
            final Map<String, String> idMap = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL);
            isAvailable[0] = idMap.containsKey(localeId);
        }
        final String rulesId = this.getRulesIdForLocale(locale, PluralRules.PluralType.CARDINAL);
        if (rulesId == null || rulesId.trim().length() == 0) {
            return ULocale.ROOT;
        }
        final ULocale result = this.getRulesIdToEquivalentULocaleMap().get(rulesId);
        if (result == null) {
            return ULocale.ROOT;
        }
        return result;
    }
    
    private Map<String, String> getLocaleIdToRulesIdMap(final PluralRules.PluralType type) {
        this.checkBuildRulesIdMaps();
        return (type == PluralRules.PluralType.CARDINAL) ? this.localeIdToCardinalRulesId : this.localeIdToOrdinalRulesId;
    }
    
    private Map<String, ULocale> getRulesIdToEquivalentULocaleMap() {
        this.checkBuildRulesIdMaps();
        return this.rulesIdToEquivalentULocale;
    }
    
    private void checkBuildRulesIdMaps() {
        final boolean haveMap;
        synchronized (this) {
            haveMap = (this.localeIdToCardinalRulesId != null);
        }
        if (!haveMap) {
            Map<String, String> tempLocaleIdToCardinalRulesId;
            Map<String, ULocale> tempRulesIdToEquivalentULocale;
            Map<String, String> tempLocaleIdToOrdinalRulesId;
            try {
                final UResourceBundle pluralb = this.getPluralBundle();
                UResourceBundle localeb = pluralb.get("locales");
                tempLocaleIdToCardinalRulesId = new TreeMap<String, String>();
                tempRulesIdToEquivalentULocale = new HashMap<String, ULocale>();
                for (int i = 0; i < localeb.getSize(); ++i) {
                    final UResourceBundle b = localeb.get(i);
                    final String id = b.getKey();
                    final String value = b.getString().intern();
                    tempLocaleIdToCardinalRulesId.put(id, value);
                    if (!tempRulesIdToEquivalentULocale.containsKey(value)) {
                        tempRulesIdToEquivalentULocale.put(value, new ULocale(id));
                    }
                }
                localeb = pluralb.get("locales_ordinals");
                tempLocaleIdToOrdinalRulesId = new TreeMap<String, String>();
                for (int i = 0; i < localeb.getSize(); ++i) {
                    final UResourceBundle b = localeb.get(i);
                    final String id = b.getKey();
                    final String value = b.getString().intern();
                    tempLocaleIdToOrdinalRulesId.put(id, value);
                }
            }
            catch (MissingResourceException e) {
                tempLocaleIdToCardinalRulesId = Collections.emptyMap();
                tempLocaleIdToOrdinalRulesId = Collections.emptyMap();
                tempRulesIdToEquivalentULocale = Collections.emptyMap();
            }
            synchronized (this) {
                if (this.localeIdToCardinalRulesId == null) {
                    this.localeIdToCardinalRulesId = tempLocaleIdToCardinalRulesId;
                    this.localeIdToOrdinalRulesId = tempLocaleIdToOrdinalRulesId;
                    this.rulesIdToEquivalentULocale = tempRulesIdToEquivalentULocale;
                }
            }
        }
    }
    
    public String getRulesIdForLocale(final ULocale locale, final PluralRules.PluralType type) {
        final Map<String, String> idMap = this.getLocaleIdToRulesIdMap(type);
        String localeId = ULocale.canonicalize(locale.getBaseName());
        String rulesId = null;
        while (null == (rulesId = idMap.get(localeId))) {
            final int ix = localeId.lastIndexOf("_");
            if (ix == -1) {
                break;
            }
            localeId = localeId.substring(0, ix);
        }
        return rulesId;
    }
    
    public PluralRules getRulesForRulesId(final String rulesId) {
        PluralRules rules = null;
        final boolean hasRules;
        synchronized (this.rulesIdToRules) {
            hasRules = this.rulesIdToRules.containsKey(rulesId);
            if (hasRules) {
                rules = this.rulesIdToRules.get(rulesId);
            }
        }
        if (!hasRules) {
            try {
                final UResourceBundle pluralb = this.getPluralBundle();
                final UResourceBundle rulesb = pluralb.get("rules");
                final UResourceBundle setb = rulesb.get(rulesId);
                final StringBuilder sb = new StringBuilder();
                for (int i = 0; i < setb.getSize(); ++i) {
                    final UResourceBundle b = setb.get(i);
                    if (i > 0) {
                        sb.append("; ");
                    }
                    sb.append(b.getKey());
                    sb.append(": ");
                    sb.append(b.getString());
                }
                rules = PluralRules.parseDescription(sb.toString());
            }
            catch (ParseException ex) {}
            catch (MissingResourceException ex2) {}
            synchronized (this.rulesIdToRules) {
                if (this.rulesIdToRules.containsKey(rulesId)) {
                    rules = this.rulesIdToRules.get(rulesId);
                }
                else {
                    this.rulesIdToRules.put(rulesId, rules);
                }
            }
        }
        return rules;
    }
    
    public UResourceBundle getPluralBundle() throws MissingResourceException {
        return ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "plurals", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
    }
    
    @Override
    public PluralRules forLocale(final ULocale locale, final PluralRules.PluralType type) {
        final String rulesId = this.getRulesIdForLocale(locale, type);
        if (rulesId == null || rulesId.trim().length() == 0) {
            return PluralRules.DEFAULT;
        }
        PluralRules rules = this.getRulesForRulesId(rulesId);
        if (rules == null) {
            rules = PluralRules.DEFAULT;
        }
        return rules;
    }
    
    @Override
    public boolean hasOverride(final ULocale locale) {
        return false;
    }
    
    public PluralRanges getPluralRanges(final ULocale locale) {
        PluralRanges result;
        int ix;
        for (String localeId = ULocale.canonicalize(locale.getBaseName()); null == (result = PluralRulesLoader.localeIdToPluralRanges.get(localeId)); localeId = localeId.substring(0, ix)) {
            ix = localeId.lastIndexOf("_");
            if (ix == -1) {
                result = PluralRulesLoader.UNKNOWN_RANGE;
                break;
            }
        }
        return result;
    }
    
    public boolean isPluralRangesAvailable(final ULocale locale) {
        return this.getPluralRanges(locale) == PluralRulesLoader.UNKNOWN_RANGE;
    }
    
    static {
        loader = new PluralRulesLoader();
        UNKNOWN_RANGE = new PluralRanges().freeze();
        final String[][] pluralRangeData = { { "locales", "id ja km ko lo ms my th vi zh" }, { "other", "other", "other" }, { "locales", "am bn fr gu hi hy kn mr pa zu" }, { "one", "one", "one" }, { "one", "other", "other" }, { "other", "other", "other" }, { "locales", "fa" }, { "one", "one", "other" }, { "one", "other", "other" }, { "other", "other", "other" }, { "locales", "ka" }, { "one", "other", "one" }, { "other", "one", "other" }, { "other", "other", "other" }, { "locales", "az de el gl hu it kk ky ml mn ne nl pt sq sw ta te tr ug uz" }, { "one", "other", "other" }, { "other", "one", "one" }, { "other", "other", "other" }, { "locales", "af bg ca en es et eu fi nb sv ur" }, { "one", "other", "other" }, { "other", "one", "other" }, { "other", "other", "other" }, { "locales", "da fil is" }, { "one", "one", "one" }, { "one", "other", "other" }, { "other", "one", "one" }, { "other", "other", "other" }, { "locales", "si" }, { "one", "one", "one" }, { "one", "other", "other" }, { "other", "one", "other" }, { "other", "other", "other" }, { "locales", "mk" }, { "one", "one", "other" }, { "one", "other", "other" }, { "other", "one", "other" }, { "other", "other", "other" }, { "locales", "lv" }, { "zero", "zero", "other" }, { "zero", "one", "one" }, { "zero", "other", "other" }, { "one", "zero", "other" }, { "one", "one", "one" }, { "one", "other", "other" }, { "other", "zero", "other" }, { "other", "one", "one" }, { "other", "other", "other" }, { "locales", "ro" }, { "one", "few", "few" }, { "one", "other", "other" }, { "few", "one", "few" }, { "few", "few", "few" }, { "few", "other", "other" }, { "other", "few", "few" }, { "other", "other", "other" }, { "locales", "hr sr bs" }, { "one", "one", "one" }, { "one", "few", "few" }, { "one", "other", "other" }, { "few", "one", "one" }, { "few", "few", "few" }, { "few", "other", "other" }, { "other", "one", "one" }, { "other", "few", "few" }, { "other", "other", "other" }, { "locales", "sl" }, { "one", "one", "few" }, { "one", "two", "two" }, { "one", "few", "few" }, { "one", "other", "other" }, { "two", "one", "few" }, { "two", "two", "two" }, { "two", "few", "few" }, { "two", "other", "other" }, { "few", "one", "few" }, { "few", "two", "two" }, { "few", "few", "few" }, { "few", "other", "other" }, { "other", "one", "few" }, { "other", "two", "two" }, { "other", "few", "few" }, { "other", "other", "other" }, { "locales", "he" }, { "one", "two", "other" }, { "one", "many", "many" }, { "one", "other", "other" }, { "two", "many", "other" }, { "two", "other", "other" }, { "many", "many", "many" }, { "many", "other", "many" }, { "other", "one", "other" }, { "other", "two", "other" }, { "other", "many", "many" }, { "other", "other", "other" }, { "locales", "cs pl sk" }, { "one", "few", "few" }, { "one", "many", "many" }, { "one", "other", "other" }, { "few", "few", "few" }, { "few", "many", "many" }, { "few", "other", "other" }, { "many", "one", "one" }, { "many", "few", "few" }, { "many", "many", "many" }, { "many", "other", "other" }, { "other", "one", "one" }, { "other", "few", "few" }, { "other", "many", "many" }, { "other", "other", "other" }, { "locales", "lt ru uk" }, { "one", "one", "one" }, { "one", "few", "few" }, { "one", "many", "many" }, { "one", "other", "other" }, { "few", "one", "one" }, { "few", "few", "few" }, { "few", "many", "many" }, { "few", "other", "other" }, { "many", "one", "one" }, { "many", "few", "few" }, { "many", "many", "many" }, { "many", "other", "other" }, { "other", "one", "one" }, { "other", "few", "few" }, { "other", "many", "many" }, { "other", "other", "other" }, { "locales", "cy" }, { "zero", "one", "one" }, { "zero", "two", "two" }, { "zero", "few", "few" }, { "zero", "many", "many" }, { "zero", "other", "other" }, { "one", "two", "two" }, { "one", "few", "few" }, { "one", "many", "many" }, { "one", "other", "other" }, { "two", "few", "few" }, { "two", "many", "many" }, { "two", "other", "other" }, { "few", "many", "many" }, { "few", "other", "other" }, { "many", "other", "other" }, { "other", "one", "one" }, { "other", "two", "two" }, { "other", "few", "few" }, { "other", "many", "many" }, { "other", "other", "other" }, { "locales", "ar" }, { "zero", "one", "zero" }, { "zero", "two", "zero" }, { "zero", "few", "few" }, { "zero", "many", "many" }, { "zero", "other", "other" }, { "one", "two", "other" }, { "one", "few", "few" }, { "one", "many", "many" }, { "one", "other", "other" }, { "two", "few", "few" }, { "two", "many", "many" }, { "two", "other", "other" }, { "few", "few", "few" }, { "few", "many", "many" }, { "few", "other", "other" }, { "many", "few", "few" }, { "many", "many", "many" }, { "many", "other", "other" }, { "other", "one", "other" }, { "other", "two", "other" }, { "other", "few", "few" }, { "other", "many", "many" }, { "other", "other", "other" } };
        PluralRanges pr = null;
        String[] locales = null;
        final HashMap<String, PluralRanges> tempLocaleIdToPluralRanges = new HashMap<String, PluralRanges>();
        for (final String[] row : pluralRangeData) {
            if (row[0].equals("locales")) {
                if (pr != null) {
                    pr.freeze();
                    for (final String locale : locales) {
                        tempLocaleIdToPluralRanges.put(locale, pr);
                    }
                }
                locales = row[1].split(" ");
                pr = new PluralRanges();
            }
            else {
                pr.add(StandardPlural.fromString(row[0]), StandardPlural.fromString(row[1]), StandardPlural.fromString(row[2]));
            }
        }
        for (final String locale2 : locales) {
            tempLocaleIdToPluralRanges.put(locale2, pr);
        }
        PluralRulesLoader.localeIdToPluralRanges = Collections.unmodifiableMap((Map<? extends String, ? extends PluralRanges>)tempLocaleIdToPluralRanges);
    }
}
