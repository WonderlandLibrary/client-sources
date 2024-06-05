package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

public class NumberingSystem
{
    private static final String[] OTHER_NS_KEYWORDS;
    public static final NumberingSystem LATIN;
    private String desc;
    private int radix;
    private boolean algorithmic;
    private String name;
    private static CacheBase<String, NumberingSystem, LocaleLookupData> cachedLocaleData;
    private static CacheBase<String, NumberingSystem, Void> cachedStringData;
    
    public NumberingSystem() {
        this.radix = 10;
        this.algorithmic = false;
        this.desc = "0123456789";
        this.name = "latn";
    }
    
    public static NumberingSystem getInstance(final int radix_in, final boolean isAlgorithmic_in, final String desc_in) {
        return getInstance(null, radix_in, isAlgorithmic_in, desc_in);
    }
    
    private static NumberingSystem getInstance(final String name_in, final int radix_in, final boolean isAlgorithmic_in, final String desc_in) {
        if (radix_in < 2) {
            throw new IllegalArgumentException("Invalid radix for numbering system");
        }
        if (!isAlgorithmic_in && (desc_in.codePointCount(0, desc_in.length()) != radix_in || !isValidDigitString(desc_in))) {
            throw new IllegalArgumentException("Invalid digit string for numbering system");
        }
        final NumberingSystem ns = new NumberingSystem();
        ns.radix = radix_in;
        ns.algorithmic = isAlgorithmic_in;
        ns.desc = desc_in;
        ns.name = name_in;
        return ns;
    }
    
    public static NumberingSystem getInstance(final Locale inLocale) {
        return getInstance(ULocale.forLocale(inLocale));
    }
    
    public static NumberingSystem getInstance(final ULocale locale) {
        boolean nsResolved = true;
        String numbersKeyword = locale.getKeywordValue("numbers");
        if (numbersKeyword != null) {
            for (final String keyword : NumberingSystem.OTHER_NS_KEYWORDS) {
                if (numbersKeyword.equals(keyword)) {
                    nsResolved = false;
                    break;
                }
            }
        }
        else {
            numbersKeyword = "default";
            nsResolved = false;
        }
        if (nsResolved) {
            final NumberingSystem ns = getInstanceByName(numbersKeyword);
            if (ns != null) {
                return ns;
            }
            numbersKeyword = "default";
        }
        final String baseName = locale.getBaseName();
        final String key = baseName + "@numbers=" + numbersKeyword;
        final LocaleLookupData localeLookupData = new LocaleLookupData(locale, numbersKeyword);
        return NumberingSystem.cachedLocaleData.getInstance(key, localeLookupData);
    }
    
    static NumberingSystem lookupInstanceByLocale(final LocaleLookupData localeLookupData) {
        final ULocale locale = localeLookupData.locale;
        ICUResourceBundle rb;
        try {
            rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", locale);
            rb = rb.getWithFallback("NumberElements");
        }
        catch (MissingResourceException ex) {
            return new NumberingSystem();
        }
        String numbersKeyword = localeLookupData.numbersKeyword;
        String resolvedNumberingSystem = null;
    Label_0099:
        while (true) {
            try {
                resolvedNumberingSystem = rb.getStringWithFallback(numbersKeyword);
            }
            catch (MissingResourceException ex2) {
                if (numbersKeyword.equals("native") || numbersKeyword.equals("finance")) {
                    numbersKeyword = "default";
                }
                else {
                    if (!numbersKeyword.equals("traditional")) {
                        break Label_0099;
                    }
                    numbersKeyword = "native";
                }
                continue;
            }
            break;
        }
        NumberingSystem ns = null;
        if (resolvedNumberingSystem != null) {
            ns = getInstanceByName(resolvedNumberingSystem);
        }
        if (ns == null) {
            ns = new NumberingSystem();
        }
        return ns;
    }
    
    public static NumberingSystem getInstance() {
        return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }
    
    public static NumberingSystem getInstanceByName(final String name) {
        return NumberingSystem.cachedStringData.getInstance(name, null);
    }
    
    private static NumberingSystem lookupInstanceByName(final String name) {
        String description;
        int radix;
        boolean isAlgorithmic;
        try {
            final UResourceBundle numberingSystemsInfo = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "numberingSystems");
            final UResourceBundle nsCurrent = numberingSystemsInfo.get("numberingSystems");
            final UResourceBundle nsTop = nsCurrent.get(name);
            description = nsTop.getString("desc");
            final UResourceBundle nsRadixBundle = nsTop.get("radix");
            final UResourceBundle nsAlgBundle = nsTop.get("algorithmic");
            radix = nsRadixBundle.getInt();
            final int algorithmic = nsAlgBundle.getInt();
            isAlgorithmic = (algorithmic == 1);
        }
        catch (MissingResourceException ex) {
            return null;
        }
        return getInstance(name, radix, isAlgorithmic, description);
    }
    
    public static String[] getAvailableNames() {
        final UResourceBundle numberingSystemsInfo = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "numberingSystems");
        final UResourceBundle nsCurrent = numberingSystemsInfo.get("numberingSystems");
        final ArrayList<String> output = new ArrayList<String>();
        final UResourceBundleIterator it = nsCurrent.getIterator();
        while (it.hasNext()) {
            final UResourceBundle temp = it.next();
            final String nsName = temp.getKey();
            output.add(nsName);
        }
        return output.toArray(new String[output.size()]);
    }
    
    public static boolean isValidDigitString(final String str) {
        final int numCodepoints = str.codePointCount(0, str.length());
        return numCodepoints == 10;
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
    
    static {
        OTHER_NS_KEYWORDS = new String[] { "native", "traditional", "finance" };
        LATIN = lookupInstanceByName("latn");
        NumberingSystem.cachedLocaleData = new SoftCache<String, NumberingSystem, LocaleLookupData>() {
            @Override
            protected NumberingSystem createInstance(final String key, final LocaleLookupData localeLookupData) {
                return NumberingSystem.lookupInstanceByLocale(localeLookupData);
            }
        };
        NumberingSystem.cachedStringData = new SoftCache<String, NumberingSystem, Void>() {
            @Override
            protected NumberingSystem createInstance(final String key, final Void unused) {
                return lookupInstanceByName(key);
            }
        };
    }
    
    private static class LocaleLookupData
    {
        public final ULocale locale;
        public final String numbersKeyword;
        
        LocaleLookupData(final ULocale locale, final String numbersKeyword) {
            this.locale = locale;
            this.numbersKeyword = numbersKeyword;
        }
    }
}
