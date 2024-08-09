/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.text.PluralRanges;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;

public class PluralRulesLoader
extends PluralRules.Factory {
    private final Map<String, PluralRules> rulesIdToRules = new HashMap<String, PluralRules>();
    private Map<String, String> localeIdToCardinalRulesId;
    private Map<String, String> localeIdToOrdinalRulesId;
    private Map<String, ULocale> rulesIdToEquivalentULocale;
    private static Map<String, PluralRanges> localeIdToPluralRanges;
    public static final PluralRulesLoader loader;
    private static final PluralRanges UNKNOWN_RANGE;

    private PluralRulesLoader() {
    }

    @Override
    public ULocale[] getAvailableULocales() {
        Set<String> set = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL).keySet();
        ULocale[] uLocaleArray = new ULocale[set.size()];
        int n = 0;
        Iterator<String> iterator2 = set.iterator();
        while (iterator2.hasNext()) {
            uLocaleArray[n++] = ULocale.createCanonical(iterator2.next());
        }
        return uLocaleArray;
    }

    @Override
    public ULocale getFunctionalEquivalent(ULocale uLocale, boolean[] blArray) {
        Object object;
        String string;
        if (blArray != null && blArray.length > 0) {
            string = ULocale.canonicalize(uLocale.getBaseName());
            object = this.getLocaleIdToRulesIdMap(PluralRules.PluralType.CARDINAL);
            blArray[0] = object.containsKey(string);
        }
        if ((string = this.getRulesIdForLocale(uLocale, PluralRules.PluralType.CARDINAL)) == null || string.trim().length() == 0) {
            return ULocale.ROOT;
        }
        object = this.getRulesIdToEquivalentULocaleMap().get(string);
        if (object == null) {
            return ULocale.ROOT;
        }
        return object;
    }

    private Map<String, String> getLocaleIdToRulesIdMap(PluralRules.PluralType pluralType) {
        this.checkBuildRulesIdMaps();
        return pluralType == PluralRules.PluralType.CARDINAL ? this.localeIdToCardinalRulesId : this.localeIdToOrdinalRulesId;
    }

    private Map<String, ULocale> getRulesIdToEquivalentULocaleMap() {
        this.checkBuildRulesIdMaps();
        return this.rulesIdToEquivalentULocale;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkBuildRulesIdMaps() {
        boolean bl;
        TreeMap<String, String> treeMap = this;
        synchronized (treeMap) {
            bl = this.localeIdToCardinalRulesId != null;
        }
        if (!bl) {
            Map<String, String> map;
            Map<String, ULocale> map2;
            Object object;
            try {
                String string;
                String string2;
                UResourceBundle uResourceBundle;
                int n;
                object = this.getPluralBundle();
                UResourceBundle uResourceBundle2 = ((UResourceBundle)object).get("locales");
                treeMap = new TreeMap<String, String>();
                map2 = new HashMap();
                for (n = 0; n < uResourceBundle2.getSize(); ++n) {
                    uResourceBundle = uResourceBundle2.get(n);
                    string2 = uResourceBundle.getKey();
                    string = uResourceBundle.getString().intern();
                    treeMap.put(string2, string);
                    if (map2.containsKey(string)) continue;
                    map2.put(string, new ULocale(string2));
                }
                uResourceBundle2 = ((UResourceBundle)object).get("locales_ordinals");
                map = new TreeMap();
                for (n = 0; n < uResourceBundle2.getSize(); ++n) {
                    uResourceBundle = uResourceBundle2.get(n);
                    string2 = uResourceBundle.getKey();
                    string = uResourceBundle.getString().intern();
                    map.put(string2, string);
                }
            } catch (MissingResourceException missingResourceException) {
                treeMap = Collections.emptyMap();
                map = Collections.emptyMap();
                map2 = Collections.emptyMap();
            }
            object = this;
            synchronized (object) {
                if (this.localeIdToCardinalRulesId == null) {
                    this.localeIdToCardinalRulesId = treeMap;
                    this.localeIdToOrdinalRulesId = map;
                    this.rulesIdToEquivalentULocale = map2;
                }
            }
        }
    }

    public String getRulesIdForLocale(ULocale uLocale, PluralRules.PluralType pluralType) {
        int n;
        Map<String, String> map = this.getLocaleIdToRulesIdMap(pluralType);
        String string = ULocale.canonicalize(uLocale.getBaseName());
        String string2 = null;
        while (null == (string2 = map.get(string)) && (n = string.lastIndexOf("_")) != -1) {
            string = string.substring(0, n);
        }
        return string2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PluralRules getRulesForRulesId(String string) {
        boolean bl;
        PluralRules pluralRules = null;
        Map<String, PluralRules> map = this.rulesIdToRules;
        synchronized (map) {
            bl = this.rulesIdToRules.containsKey(string);
            if (bl) {
                pluralRules = this.rulesIdToRules.get(string);
            }
        }
        if (!bl) {
            try {
                map = this.getPluralBundle();
                UResourceBundle uResourceBundle = ((UResourceBundle)((Object)map)).get("rules");
                UResourceBundle uResourceBundle2 = uResourceBundle.get(string);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < uResourceBundle2.getSize(); ++i) {
                    UResourceBundle uResourceBundle3 = uResourceBundle2.get(i);
                    if (i > 0) {
                        stringBuilder.append("; ");
                    }
                    stringBuilder.append(uResourceBundle3.getKey());
                    stringBuilder.append(": ");
                    stringBuilder.append(uResourceBundle3.getString());
                }
                pluralRules = PluralRules.parseDescription(stringBuilder.toString());
            } catch (ParseException parseException) {
            } catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            map = this.rulesIdToRules;
            synchronized (map) {
                if (this.rulesIdToRules.containsKey(string)) {
                    pluralRules = this.rulesIdToRules.get(string);
                } else {
                    this.rulesIdToRules.put(string, pluralRules);
                }
            }
        }
        return pluralRules;
    }

    public UResourceBundle getPluralBundle() throws MissingResourceException {
        return ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "plurals", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
    }

    @Override
    public PluralRules forLocale(ULocale uLocale, PluralRules.PluralType pluralType) {
        String string = this.getRulesIdForLocale(uLocale, pluralType);
        if (string == null || string.trim().length() == 0) {
            return PluralRules.DEFAULT;
        }
        PluralRules pluralRules = this.getRulesForRulesId(string);
        if (pluralRules == null) {
            pluralRules = PluralRules.DEFAULT;
        }
        return pluralRules;
    }

    @Override
    public boolean hasOverride(ULocale uLocale) {
        return true;
    }

    public PluralRanges getPluralRanges(ULocale uLocale) {
        PluralRanges pluralRanges;
        String string = ULocale.canonicalize(uLocale.getBaseName());
        while (null == (pluralRanges = localeIdToPluralRanges.get(string))) {
            int n = string.lastIndexOf("_");
            if (n == -1) {
                pluralRanges = UNKNOWN_RANGE;
                break;
            }
            string = string.substring(0, n);
        }
        return pluralRanges;
    }

    public boolean isPluralRangesAvailable(ULocale uLocale) {
        return this.getPluralRanges(uLocale) == UNKNOWN_RANGE;
    }

    static {
        loader = new PluralRulesLoader();
        UNKNOWN_RANGE = new PluralRanges().freeze();
        String[][] stringArrayArray = new String[][]{{"locales", "id ja km ko lo ms my th vi zh"}, {"other", "other", "other"}, {"locales", "am bn fr gu hi hy kn mr pa zu"}, {"one", "one", "one"}, {"one", "other", "other"}, {"other", "other", "other"}, {"locales", "fa"}, {"one", "one", "other"}, {"one", "other", "other"}, {"other", "other", "other"}, {"locales", "ka"}, {"one", "other", "one"}, {"other", "one", "other"}, {"other", "other", "other"}, {"locales", "az de el gl hu it kk ky ml mn ne nl pt sq sw ta te tr ug uz"}, {"one", "other", "other"}, {"other", "one", "one"}, {"other", "other", "other"}, {"locales", "af bg ca en es et eu fi nb sv ur"}, {"one", "other", "other"}, {"other", "one", "other"}, {"other", "other", "other"}, {"locales", "da fil is"}, {"one", "one", "one"}, {"one", "other", "other"}, {"other", "one", "one"}, {"other", "other", "other"}, {"locales", "si"}, {"one", "one", "one"}, {"one", "other", "other"}, {"other", "one", "other"}, {"other", "other", "other"}, {"locales", "mk"}, {"one", "one", "other"}, {"one", "other", "other"}, {"other", "one", "other"}, {"other", "other", "other"}, {"locales", "lv"}, {"zero", "zero", "other"}, {"zero", "one", "one"}, {"zero", "other", "other"}, {"one", "zero", "other"}, {"one", "one", "one"}, {"one", "other", "other"}, {"other", "zero", "other"}, {"other", "one", "one"}, {"other", "other", "other"}, {"locales", "ro"}, {"one", "few", "few"}, {"one", "other", "other"}, {"few", "one", "few"}, {"few", "few", "few"}, {"few", "other", "other"}, {"other", "few", "few"}, {"other", "other", "other"}, {"locales", "hr sr bs"}, {"one", "one", "one"}, {"one", "few", "few"}, {"one", "other", "other"}, {"few", "one", "one"}, {"few", "few", "few"}, {"few", "other", "other"}, {"other", "one", "one"}, {"other", "few", "few"}, {"other", "other", "other"}, {"locales", "sl"}, {"one", "one", "few"}, {"one", "two", "two"}, {"one", "few", "few"}, {"one", "other", "other"}, {"two", "one", "few"}, {"two", "two", "two"}, {"two", "few", "few"}, {"two", "other", "other"}, {"few", "one", "few"}, {"few", "two", "two"}, {"few", "few", "few"}, {"few", "other", "other"}, {"other", "one", "few"}, {"other", "two", "two"}, {"other", "few", "few"}, {"other", "other", "other"}, {"locales", "he"}, {"one", "two", "other"}, {"one", "many", "many"}, {"one", "other", "other"}, {"two", "many", "other"}, {"two", "other", "other"}, {"many", "many", "many"}, {"many", "other", "many"}, {"other", "one", "other"}, {"other", "two", "other"}, {"other", "many", "many"}, {"other", "other", "other"}, {"locales", "cs pl sk"}, {"one", "few", "few"}, {"one", "many", "many"}, {"one", "other", "other"}, {"few", "few", "few"}, {"few", "many", "many"}, {"few", "other", "other"}, {"many", "one", "one"}, {"many", "few", "few"}, {"many", "many", "many"}, {"many", "other", "other"}, {"other", "one", "one"}, {"other", "few", "few"}, {"other", "many", "many"}, {"other", "other", "other"}, {"locales", "lt ru uk"}, {"one", "one", "one"}, {"one", "few", "few"}, {"one", "many", "many"}, {"one", "other", "other"}, {"few", "one", "one"}, {"few", "few", "few"}, {"few", "many", "many"}, {"few", "other", "other"}, {"many", "one", "one"}, {"many", "few", "few"}, {"many", "many", "many"}, {"many", "other", "other"}, {"other", "one", "one"}, {"other", "few", "few"}, {"other", "many", "many"}, {"other", "other", "other"}, {"locales", "cy"}, {"zero", "one", "one"}, {"zero", "two", "two"}, {"zero", "few", "few"}, {"zero", "many", "many"}, {"zero", "other", "other"}, {"one", "two", "two"}, {"one", "few", "few"}, {"one", "many", "many"}, {"one", "other", "other"}, {"two", "few", "few"}, {"two", "many", "many"}, {"two", "other", "other"}, {"few", "many", "many"}, {"few", "other", "other"}, {"many", "other", "other"}, {"other", "one", "one"}, {"other", "two", "two"}, {"other", "few", "few"}, {"other", "many", "many"}, {"other", "other", "other"}, {"locales", "ar"}, {"zero", "one", "zero"}, {"zero", "two", "zero"}, {"zero", "few", "few"}, {"zero", "many", "many"}, {"zero", "other", "other"}, {"one", "two", "other"}, {"one", "few", "few"}, {"one", "many", "many"}, {"one", "other", "other"}, {"two", "few", "few"}, {"two", "many", "many"}, {"two", "other", "other"}, {"few", "few", "few"}, {"few", "many", "many"}, {"few", "other", "other"}, {"many", "few", "few"}, {"many", "many", "many"}, {"many", "other", "other"}, {"other", "one", "other"}, {"other", "two", "other"}, {"other", "few", "few"}, {"other", "many", "many"}, {"other", "other", "other"}};
        PluralRanges pluralRanges = null;
        String[] stringArray = null;
        HashMap<Object, PluralRanges> hashMap = new HashMap<Object, PluralRanges>();
        for (String[] stringArray2 : stringArrayArray) {
            if (stringArray2[0].equals("locales")) {
                if (pluralRanges != null) {
                    pluralRanges.freeze();
                    for (String string : stringArray) {
                        hashMap.put(string, pluralRanges);
                    }
                }
                stringArray = stringArray2[5].split(" ");
                pluralRanges = new PluralRanges();
                continue;
            }
            pluralRanges.add(StandardPlural.fromString(stringArray2[0]), StandardPlural.fromString(stringArray2[5]), StandardPlural.fromString(stringArray2[5]));
        }
        for (String[] stringArray2 : stringArray) {
            hashMap.put(stringArray2, pluralRanges);
        }
        localeIdToPluralRanges = Collections.unmodifiableMap(hashMap);
    }
}

