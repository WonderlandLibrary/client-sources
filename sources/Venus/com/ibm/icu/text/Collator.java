/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationRoot;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.CollationKey;
import com.ibm.icu.text.RawCollationKey;
import com.ibm.icu.text.RuleBasedCollator;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.VersionInfo;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class Collator
implements Comparator<Object>,
Freezable<Collator>,
Cloneable {
    public static final int PRIMARY = 0;
    public static final int SECONDARY = 1;
    public static final int TERTIARY = 2;
    public static final int QUATERNARY = 3;
    public static final int IDENTICAL = 15;
    public static final int FULL_DECOMPOSITION = 15;
    public static final int NO_DECOMPOSITION = 16;
    public static final int CANONICAL_DECOMPOSITION = 17;
    private static ServiceShim shim;
    private static final String[] KEYWORDS;
    private static final String RESOURCE = "collations";
    private static final String BASE = "com/ibm/icu/impl/data/icudt66b/coll";
    private static final boolean DEBUG;

    @Override
    public boolean equals(Object object) {
        return this == object || object != null && this.getClass() == object.getClass();
    }

    public int hashCode() {
        return 1;
    }

    private void checkNotFrozen() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen Collator");
        }
    }

    public void setStrength(int n) {
        this.checkNotFrozen();
    }

    @Deprecated
    public Collator setStrength2(int n) {
        this.setStrength(n);
        return this;
    }

    public void setDecomposition(int n) {
        this.checkNotFrozen();
    }

    public void setReorderCodes(int ... nArray) {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public static final Collator getInstance() {
        return Collator.getInstance(ULocale.getDefault());
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private static ServiceShim getShim() {
        if (shim == null) {
            try {
                Class<?> clazz = Class.forName("com.ibm.icu.text.CollatorServiceShim");
                shim = (ServiceShim)clazz.newInstance();
            } catch (MissingResourceException missingResourceException) {
                throw missingResourceException;
            } catch (Exception exception) {
                if (DEBUG) {
                    exception.printStackTrace();
                }
                throw new ICUException(exception);
            }
        }
        return shim;
    }

    private static final boolean getYesOrNo(String string, String string2) {
        if (ASCII.equalIgnoreCase(string2, "yes")) {
            return false;
        }
        if (ASCII.equalIgnoreCase(string2, "no")) {
            return true;
        }
        throw new IllegalArgumentException("illegal locale keyword=value: " + string + "=" + string2);
    }

    private static final int getIntValue(String string, String string2, String ... stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            if (!ASCII.equalIgnoreCase(string2, stringArray[i])) continue;
            return i;
        }
        throw new IllegalArgumentException("illegal locale keyword=value: " + string + "=" + string2);
    }

    private static final int getReorderCode(String string, String string2) {
        return 4096 + Collator.getIntValue(string, string2, "space", "punct", "symbol", "currency", "digit");
    }

    private static void setAttributesFromKeywords(ULocale uLocale, Collator collator, RuleBasedCollator ruleBasedCollator) {
        int n;
        String string = uLocale.getKeywordValue("colHiraganaQuaternary");
        if (string != null) {
            throw new UnsupportedOperationException("locale keyword kh/colHiraganaQuaternary");
        }
        string = uLocale.getKeywordValue("variableTop");
        if (string != null) {
            throw new UnsupportedOperationException("locale keyword vt/variableTop");
        }
        string = uLocale.getKeywordValue("colStrength");
        if (string != null) {
            n = Collator.getIntValue("colStrength", string, "primary", "secondary", "tertiary", "quaternary", "identical");
            collator.setStrength(n <= 3 ? n : 15);
        }
        if ((string = uLocale.getKeywordValue("colBackwards")) != null) {
            if (ruleBasedCollator != null) {
                ruleBasedCollator.setFrenchCollation(Collator.getYesOrNo("colBackwards", string));
            } else {
                throw new UnsupportedOperationException("locale keyword kb/colBackwards only settable for RuleBasedCollator");
            }
        }
        if ((string = uLocale.getKeywordValue("colCaseLevel")) != null) {
            if (ruleBasedCollator != null) {
                ruleBasedCollator.setCaseLevel(Collator.getYesOrNo("colCaseLevel", string));
            } else {
                throw new UnsupportedOperationException("locale keyword kb/colBackwards only settable for RuleBasedCollator");
            }
        }
        if ((string = uLocale.getKeywordValue("colCaseFirst")) != null) {
            if (ruleBasedCollator != null) {
                n = Collator.getIntValue("colCaseFirst", string, "no", "lower", "upper");
                if (n == 0) {
                    ruleBasedCollator.setLowerCaseFirst(true);
                    ruleBasedCollator.setUpperCaseFirst(true);
                } else if (n == 1) {
                    ruleBasedCollator.setLowerCaseFirst(false);
                } else {
                    ruleBasedCollator.setUpperCaseFirst(false);
                }
            } else {
                throw new UnsupportedOperationException("locale keyword kf/colCaseFirst only settable for RuleBasedCollator");
            }
        }
        if ((string = uLocale.getKeywordValue("colAlternate")) != null) {
            if (ruleBasedCollator != null) {
                ruleBasedCollator.setAlternateHandlingShifted(Collator.getIntValue("colAlternate", string, "non-ignorable", "shifted") != 0);
            } else {
                throw new UnsupportedOperationException("locale keyword ka/colAlternate only settable for RuleBasedCollator");
            }
        }
        if ((string = uLocale.getKeywordValue("colNormalization")) != null) {
            collator.setDecomposition(Collator.getYesOrNo("colNormalization", string) ? 17 : 16);
        }
        if ((string = uLocale.getKeywordValue("colNumeric")) != null) {
            if (ruleBasedCollator != null) {
                ruleBasedCollator.setNumericCollation(Collator.getYesOrNo("colNumeric", string));
            } else {
                throw new UnsupportedOperationException("locale keyword kn/colNumeric only settable for RuleBasedCollator");
            }
        }
        if ((string = uLocale.getKeywordValue("colReorder")) != null) {
            int[] nArray = new int[198];
            int n2 = 0;
            int n3 = 0;
            while (true) {
                int n4;
                if (n2 == nArray.length) {
                    throw new IllegalArgumentException("too many script codes for colReorder locale keyword: " + string);
                }
                for (n4 = n3; n4 < string.length() && string.charAt(n4) != '-'; ++n4) {
                }
                String string2 = string.substring(n3, n4);
                int n5 = string2.length() == 4 ? UCharacter.getPropertyValueEnum(4106, string2) : Collator.getReorderCode("colReorder", string2);
                nArray[n2++] = n5;
                if (n4 == string.length()) break;
                n3 = n4 + 1;
            }
            if (n2 == 0) {
                throw new IllegalArgumentException("no script codes for colReorder locale keyword");
            }
            int[] nArray2 = new int[n2];
            System.arraycopy(nArray, 0, nArray2, 0, n2);
            collator.setReorderCodes(nArray2);
        }
        if ((string = uLocale.getKeywordValue("kv")) != null) {
            collator.setMaxVariable(Collator.getReorderCode("kv", string));
        }
    }

    public static final Collator getInstance(ULocale uLocale) {
        if (uLocale == null) {
            uLocale = ULocale.getDefault();
        }
        Collator collator = Collator.getShim().getInstance(uLocale);
        if (!uLocale.getName().equals(uLocale.getBaseName())) {
            Collator.setAttributesFromKeywords(uLocale, collator, collator instanceof RuleBasedCollator ? (RuleBasedCollator)collator : null);
        }
        return collator;
    }

    public static final Collator getInstance(Locale locale) {
        return Collator.getInstance(ULocale.forLocale(locale));
    }

    public static final Object registerInstance(Collator collator, ULocale uLocale) {
        return Collator.getShim().registerInstance(collator, uLocale);
    }

    public static final Object registerFactory(CollatorFactory collatorFactory) {
        return Collator.getShim().registerFactory(collatorFactory);
    }

    public static final boolean unregister(Object object) {
        if (shim == null) {
            return true;
        }
        return shim.unregister(object);
    }

    public static Locale[] getAvailableLocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableLocales(BASE, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        return shim.getAvailableLocales();
    }

    public static final ULocale[] getAvailableULocales() {
        if (shim == null) {
            return ICUResourceBundle.getAvailableULocales(BASE, ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        return shim.getAvailableULocales();
    }

    public static final String[] getKeywords() {
        return KEYWORDS;
    }

    public static final String[] getKeywordValues(String string) {
        if (!string.equals(KEYWORDS[0])) {
            throw new IllegalArgumentException("Invalid keyword: " + string);
        }
        return ICUResourceBundle.getKeywordValues(BASE, RESOURCE);
    }

    public static final String[] getKeywordValuesForLocale(String string, ULocale uLocale, boolean bl) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(BASE, uLocale);
        KeywordsSink keywordsSink = new KeywordsSink(null);
        iCUResourceBundle.getAllItemsWithFallback(RESOURCE, keywordsSink);
        return keywordsSink.values.toArray(new String[keywordsSink.values.size()]);
    }

    public static final ULocale getFunctionalEquivalent(String string, ULocale uLocale, boolean[] blArray) {
        return ICUResourceBundle.getFunctionalEquivalent(BASE, ICUResourceBundle.ICU_DATA_CLASS_LOADER, RESOURCE, string, uLocale, blArray, true);
    }

    public static final ULocale getFunctionalEquivalent(String string, ULocale uLocale) {
        return Collator.getFunctionalEquivalent(string, uLocale, null);
    }

    public static String getDisplayName(Locale locale, Locale locale2) {
        return Collator.getShim().getDisplayName(ULocale.forLocale(locale), ULocale.forLocale(locale2));
    }

    public static String getDisplayName(ULocale uLocale, ULocale uLocale2) {
        return Collator.getShim().getDisplayName(uLocale, uLocale2);
    }

    public static String getDisplayName(Locale locale) {
        return Collator.getShim().getDisplayName(ULocale.forLocale(locale), ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public static String getDisplayName(ULocale uLocale) {
        return Collator.getShim().getDisplayName(uLocale, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public int getStrength() {
        return 1;
    }

    public int getDecomposition() {
        return 1;
    }

    public boolean equals(String string, String string2) {
        return this.compare(string, string2) == 0;
    }

    public UnicodeSet getTailoredSet() {
        return new UnicodeSet(0, 0x10FFFF);
    }

    @Override
    public abstract int compare(String var1, String var2);

    @Override
    public int compare(Object object, Object object2) {
        return this.doCompare((CharSequence)object, (CharSequence)object2);
    }

    @Deprecated
    protected int doCompare(CharSequence charSequence, CharSequence charSequence2) {
        return this.compare(charSequence.toString(), charSequence2.toString());
    }

    public abstract CollationKey getCollationKey(String var1);

    public abstract RawCollationKey getRawCollationKey(String var1, RawCollationKey var2);

    public Collator setMaxVariable(int n) {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public int getMaxVariable() {
        return 0;
    }

    @Deprecated
    public abstract int setVariableTop(String var1);

    public abstract int getVariableTop();

    @Deprecated
    public abstract void setVariableTop(int var1);

    public abstract VersionInfo getVersion();

    public abstract VersionInfo getUCAVersion();

    public int[] getReorderCodes() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    public static int[] getEquivalentReorderCodes(int n) {
        CollationData collationData = CollationRoot.getData();
        return collationData.getEquivalentScripts(n);
    }

    @Override
    public boolean isFrozen() {
        return true;
    }

    @Override
    public Collator freeze() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    @Override
    public Collator cloneAsThawed() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }

    protected Collator() {
    }

    public ULocale getLocale(ULocale.Type type) {
        return ULocale.ROOT;
    }

    void setLocale(ULocale uLocale, ULocale uLocale2) {
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
        KEYWORDS = new String[]{"collation"};
        DEBUG = ICUDebug.enabled("collator");
    }

    private static final class KeywordsSink
    extends UResource.Sink {
        LinkedList<String> values = new LinkedList();
        boolean hasDefault = false;

        private KeywordsSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string;
                int n2 = value.getType();
                if (n2 == 0) {
                    if (!this.hasDefault && key.contentEquals("default") && !(string = value.getString()).isEmpty()) {
                        this.values.remove(string);
                        this.values.addFirst(string);
                        this.hasDefault = true;
                    }
                } else if (n2 == 2 && !key.startsWith("private-") && !this.values.contains(string = key.toString())) {
                    this.values.add(string);
                }
                ++n;
            }
        }

        KeywordsSink(1 var1_1) {
            this();
        }
    }

    private static final class ASCII {
        private ASCII() {
        }

        static boolean equalIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
            int n = charSequence.length();
            if (n != charSequence2.length()) {
                return true;
            }
            for (int i = 0; i < n; ++i) {
                char c;
                char c2 = charSequence.charAt(i);
                if (c2 == (c = charSequence2.charAt(i)) || ('A' <= c2 && c2 <= 'Z' ? c2 + 32 == c : 'A' <= c && c <= 'Z' && c + 32 == c2)) continue;
                return true;
            }
            return false;
        }
    }

    static abstract class ServiceShim {
        ServiceShim() {
        }

        abstract Collator getInstance(ULocale var1);

        abstract Object registerInstance(Collator var1, ULocale var2);

        abstract Object registerFactory(CollatorFactory var1);

        abstract boolean unregister(Object var1);

        abstract Locale[] getAvailableLocales();

        abstract ULocale[] getAvailableULocales();

        abstract String getDisplayName(ULocale var1, ULocale var2);
    }

    public static abstract class CollatorFactory {
        public boolean visible() {
            return false;
        }

        public Collator createCollator(ULocale uLocale) {
            return this.createCollator(uLocale.toLocale());
        }

        public Collator createCollator(Locale locale) {
            return this.createCollator(ULocale.forLocale(locale));
        }

        public String getDisplayName(Locale locale, Locale locale2) {
            return this.getDisplayName(ULocale.forLocale(locale), ULocale.forLocale(locale2));
        }

        public String getDisplayName(ULocale uLocale, ULocale uLocale2) {
            String string;
            Set<String> set;
            if (this.visible() && (set = this.getSupportedLocaleIDs()).contains(string = uLocale.getBaseName())) {
                return uLocale.getDisplayName(uLocale2);
            }
            return null;
        }

        public abstract Set<String> getSupportedLocaleIDs();

        protected CollatorFactory() {
        }
    }

    public static interface ReorderCodes {
        public static final int DEFAULT = -1;
        public static final int NONE = 103;
        public static final int OTHERS = 103;
        public static final int SPACE = 4096;
        public static final int FIRST = 4096;
        public static final int PUNCTUATION = 4097;
        public static final int SYMBOL = 4098;
        public static final int CURRENCY = 4099;
        public static final int DIGIT = 4100;
        @Deprecated
        public static final int LIMIT = 4101;
    }
}

