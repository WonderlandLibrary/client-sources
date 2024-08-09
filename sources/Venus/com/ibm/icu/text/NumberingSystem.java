/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CacheBase;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SoftCache;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceBundleIterator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;

public class NumberingSystem {
    private static final String[] OTHER_NS_KEYWORDS = new String[]{"native", "traditional", "finance"};
    public static final NumberingSystem LATIN = NumberingSystem.lookupInstanceByName("latn");
    private String desc = "0123456789";
    private int radix = 10;
    private boolean algorithmic = false;
    private String name = "latn";
    private static CacheBase<String, NumberingSystem, LocaleLookupData> cachedLocaleData = new SoftCache<String, NumberingSystem, LocaleLookupData>(){

        @Override
        protected NumberingSystem createInstance(String string, LocaleLookupData localeLookupData) {
            return NumberingSystem.lookupInstanceByLocale(localeLookupData);
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (LocaleLookupData)object2);
        }
    };
    private static CacheBase<String, NumberingSystem, Void> cachedStringData = new SoftCache<String, NumberingSystem, Void>(){

        @Override
        protected NumberingSystem createInstance(String string, Void void_) {
            return NumberingSystem.access$000(string);
        }

        @Override
        protected Object createInstance(Object object, Object object2) {
            return this.createInstance((String)object, (Void)object2);
        }
    };

    public static NumberingSystem getInstance(int n, boolean bl, String string) {
        return NumberingSystem.getInstance(null, n, bl, string);
    }

    private static NumberingSystem getInstance(String string, int n, boolean bl, String string2) {
        if (n < 2) {
            throw new IllegalArgumentException("Invalid radix for numbering system");
        }
        if (!(bl || string2.codePointCount(0, string2.length()) == n && NumberingSystem.isValidDigitString(string2))) {
            throw new IllegalArgumentException("Invalid digit string for numbering system");
        }
        NumberingSystem numberingSystem = new NumberingSystem();
        numberingSystem.radix = n;
        numberingSystem.algorithmic = bl;
        numberingSystem.desc = string2;
        numberingSystem.name = string;
        return numberingSystem;
    }

    public static NumberingSystem getInstance(Locale locale) {
        return NumberingSystem.getInstance(ULocale.forLocale(locale));
    }

    public static NumberingSystem getInstance(ULocale uLocale) {
        boolean bl = true;
        String string = uLocale.getKeywordValue("numbers");
        if (string != null) {
            for (String string2 : OTHER_NS_KEYWORDS) {
                if (!string.equals(string2)) continue;
                bl = false;
                break;
            }
        } else {
            string = "default";
            bl = false;
        }
        if (bl) {
            NumberingSystem numberingSystem = NumberingSystem.getInstanceByName(string);
            if (numberingSystem != null) {
                return numberingSystem;
            }
            string = "default";
        }
        String string3 = uLocale.getBaseName();
        String string4 = string3 + "@numbers=" + string;
        LocaleLookupData localeLookupData = new LocaleLookupData(uLocale, string);
        return cachedLocaleData.getInstance(string4, localeLookupData);
    }

    static NumberingSystem lookupInstanceByLocale(LocaleLookupData localeLookupData) {
        ICUResourceBundle iCUResourceBundle;
        ULocale uLocale = localeLookupData.locale;
        try {
            iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", uLocale);
            iCUResourceBundle = iCUResourceBundle.getWithFallback("NumberElements");
        } catch (MissingResourceException missingResourceException) {
            return new NumberingSystem();
        }
        String string = localeLookupData.numbersKeyword;
        String string2 = null;
        while (true) {
            try {
                string2 = iCUResourceBundle.getStringWithFallback(string);
            } catch (MissingResourceException missingResourceException) {
                if (string.equals("native") || string.equals("finance")) {
                    string = "default";
                    continue;
                }
                if (!string.equals("traditional")) break;
                string = "native";
                continue;
            }
            break;
        }
        NumberingSystem numberingSystem = null;
        if (string2 != null) {
            numberingSystem = NumberingSystem.getInstanceByName(string2);
        }
        if (numberingSystem == null) {
            numberingSystem = new NumberingSystem();
        }
        return numberingSystem;
    }

    public static NumberingSystem getInstance() {
        return NumberingSystem.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static NumberingSystem getInstanceByName(String string) {
        return cachedStringData.getInstance(string, null);
    }

    private static NumberingSystem lookupInstanceByName(String string) {
        boolean bl;
        int n;
        String string2;
        try {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "numberingSystems");
            UResourceBundle uResourceBundle2 = uResourceBundle.get("numberingSystems");
            UResourceBundle uResourceBundle3 = uResourceBundle2.get(string);
            string2 = uResourceBundle3.getString("desc");
            UResourceBundle uResourceBundle4 = uResourceBundle3.get("radix");
            UResourceBundle uResourceBundle5 = uResourceBundle3.get("algorithmic");
            n = uResourceBundle4.getInt();
            int n2 = uResourceBundle5.getInt();
            bl = n2 == 1;
        } catch (MissingResourceException missingResourceException) {
            return null;
        }
        return NumberingSystem.getInstance(string, n, bl, string2);
    }

    public static String[] getAvailableNames() {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "numberingSystems");
        UResourceBundle uResourceBundle2 = uResourceBundle.get("numberingSystems");
        ArrayList<String> arrayList = new ArrayList<String>();
        UResourceBundleIterator uResourceBundleIterator = uResourceBundle2.getIterator();
        while (uResourceBundleIterator.hasNext()) {
            UResourceBundle uResourceBundle3 = uResourceBundleIterator.next();
            String string = uResourceBundle3.getKey();
            arrayList.add(string);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static boolean isValidDigitString(String string) {
        int n = string.codePointCount(0, string.length());
        return n == 10;
    }

    public int getRadix() {
        return this.radix;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getName() {
        return this.name;
    }

    public boolean isAlgorithmic() {
        return this.algorithmic;
    }

    static NumberingSystem access$000(String string) {
        return NumberingSystem.lookupInstanceByName(string);
    }

    private static class LocaleLookupData {
        public final ULocale locale;
        public final String numbersKeyword;

        LocaleLookupData(ULocale uLocale, String string) {
            this.locale = uLocale;
            this.numbersKeyword = string;
        }
    }
}

