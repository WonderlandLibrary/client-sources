package com.ibm.icu.text;

import com.ibm.icu.lang.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.coll.*;
import com.ibm.icu.impl.*;
import java.util.*;

public abstract class Collator implements Comparator<Object>, Freezable<Collator>, Cloneable
{
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
    private static final String BASE = "com/ibm/icu/impl/data/icudt62b/coll";
    private static final boolean DEBUG;
    
    @Override
    public boolean equals(final Object obj) {
        return this == obj || (obj != null && this.getClass() == obj.getClass());
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    private void checkNotFrozen() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen Collator");
        }
    }
    
    public void setStrength(final int newStrength) {
        this.checkNotFrozen();
    }
    
    @Deprecated
    public Collator setStrength2(final int newStrength) {
        this.setStrength(newStrength);
        return this;
    }
    
    public void setDecomposition(final int decomposition) {
        this.checkNotFrozen();
    }
    
    public void setReorderCodes(final int... order) {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }
    
    public static final Collator getInstance() {
        return getInstance(ULocale.getDefault());
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    private static ServiceShim getShim() {
        if (Collator.shim == null) {
            try {
                final Class<?> cls = Class.forName("com.ibm.icu.text.CollatorServiceShim");
                Collator.shim = (ServiceShim)cls.newInstance();
            }
            catch (MissingResourceException e) {
                throw e;
            }
            catch (Exception e2) {
                if (Collator.DEBUG) {
                    e2.printStackTrace();
                }
                throw new ICUException(e2);
            }
        }
        return Collator.shim;
    }
    
    private static final boolean getYesOrNo(final String keyword, final String s) {
        if (ASCII.equalIgnoreCase(s, "yes")) {
            return true;
        }
        if (ASCII.equalIgnoreCase(s, "no")) {
            return false;
        }
        throw new IllegalArgumentException("illegal locale keyword=value: " + keyword + "=" + s);
    }
    
    private static final int getIntValue(final String keyword, final String s, final String... values) {
        for (int i = 0; i < values.length; ++i) {
            if (ASCII.equalIgnoreCase(s, values[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("illegal locale keyword=value: " + keyword + "=" + s);
    }
    
    private static final int getReorderCode(final String keyword, final String s) {
        return 4096 + getIntValue(keyword, s, "space", "punct", "symbol", "currency", "digit");
    }
    
    private static void setAttributesFromKeywords(final ULocale loc, final Collator coll, final RuleBasedCollator rbc) {
        String value = loc.getKeywordValue("colHiraganaQuaternary");
        if (value != null) {
            throw new UnsupportedOperationException("locale keyword kh/colHiraganaQuaternary");
        }
        value = loc.getKeywordValue("variableTop");
        if (value != null) {
            throw new UnsupportedOperationException("locale keyword vt/variableTop");
        }
        value = loc.getKeywordValue("colStrength");
        if (value != null) {
            final int strength = getIntValue("colStrength", value, "primary", "secondary", "tertiary", "quaternary", "identical");
            coll.setStrength((strength <= 3) ? strength : 15);
        }
        value = loc.getKeywordValue("colBackwards");
        if (value != null) {
            if (rbc == null) {
                throw new UnsupportedOperationException("locale keyword kb/colBackwards only settable for RuleBasedCollator");
            }
            rbc.setFrenchCollation(getYesOrNo("colBackwards", value));
        }
        value = loc.getKeywordValue("colCaseLevel");
        if (value != null) {
            if (rbc == null) {
                throw new UnsupportedOperationException("locale keyword kb/colBackwards only settable for RuleBasedCollator");
            }
            rbc.setCaseLevel(getYesOrNo("colCaseLevel", value));
        }
        value = loc.getKeywordValue("colCaseFirst");
        if (value != null) {
            if (rbc == null) {
                throw new UnsupportedOperationException("locale keyword kf/colCaseFirst only settable for RuleBasedCollator");
            }
            final int cf = getIntValue("colCaseFirst", value, "no", "lower", "upper");
            if (cf == 0) {
                rbc.setLowerCaseFirst(false);
                rbc.setUpperCaseFirst(false);
            }
            else if (cf == 1) {
                rbc.setLowerCaseFirst(true);
            }
            else {
                rbc.setUpperCaseFirst(true);
            }
        }
        value = loc.getKeywordValue("colAlternate");
        if (value != null) {
            if (rbc == null) {
                throw new UnsupportedOperationException("locale keyword ka/colAlternate only settable for RuleBasedCollator");
            }
            rbc.setAlternateHandlingShifted(getIntValue("colAlternate", value, "non-ignorable", "shifted") != 0);
        }
        value = loc.getKeywordValue("colNormalization");
        if (value != null) {
            coll.setDecomposition(getYesOrNo("colNormalization", value) ? 17 : 16);
        }
        value = loc.getKeywordValue("colNumeric");
        if (value != null) {
            if (rbc == null) {
                throw new UnsupportedOperationException("locale keyword kn/colNumeric only settable for RuleBasedCollator");
            }
            rbc.setNumericCollation(getYesOrNo("colNumeric", value));
        }
        value = loc.getKeywordValue("colReorder");
        Label_0603: {
            if (value != null) {
                final int[] codes = new int[190];
                int codesLength = 0;
                int scriptNameStart = 0;
                while (codesLength != codes.length) {
                    int limit;
                    for (limit = scriptNameStart; limit < value.length() && value.charAt(limit) != '-'; ++limit) {}
                    final String scriptName = value.substring(scriptNameStart, limit);
                    int code;
                    if (scriptName.length() == 4) {
                        code = UCharacter.getPropertyValueEnum(4106, scriptName);
                    }
                    else {
                        code = getReorderCode("colReorder", scriptName);
                    }
                    codes[codesLength++] = code;
                    if (limit == value.length()) {
                        if (codesLength == 0) {
                            throw new IllegalArgumentException("no script codes for colReorder locale keyword");
                        }
                        final int[] args = new int[codesLength];
                        System.arraycopy(codes, 0, args, 0, codesLength);
                        coll.setReorderCodes(args);
                        break Label_0603;
                    }
                    else {
                        scriptNameStart = limit + 1;
                    }
                }
                throw new IllegalArgumentException("too many script codes for colReorder locale keyword: " + value);
            }
        }
        value = loc.getKeywordValue("kv");
        if (value != null) {
            coll.setMaxVariable(getReorderCode("kv", value));
        }
    }
    
    public static final Collator getInstance(ULocale locale) {
        if (locale == null) {
            locale = ULocale.getDefault();
        }
        final Collator coll = getShim().getInstance(locale);
        if (!locale.getName().equals(locale.getBaseName())) {
            setAttributesFromKeywords(locale, coll, (coll instanceof RuleBasedCollator) ? ((RuleBasedCollator)coll) : null);
        }
        return coll;
    }
    
    public static final Collator getInstance(final Locale locale) {
        return getInstance(ULocale.forLocale(locale));
    }
    
    public static final Object registerInstance(final Collator collator, final ULocale locale) {
        return getShim().registerInstance(collator, locale);
    }
    
    public static final Object registerFactory(final CollatorFactory factory) {
        return getShim().registerFactory(factory);
    }
    
    public static final boolean unregister(final Object registryKey) {
        return Collator.shim != null && Collator.shim.unregister(registryKey);
    }
    
    public static Locale[] getAvailableLocales() {
        if (Collator.shim == null) {
            return ICUResourceBundle.getAvailableLocales("com/ibm/icu/impl/data/icudt62b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        return Collator.shim.getAvailableLocales();
    }
    
    public static final ULocale[] getAvailableULocales() {
        if (Collator.shim == null) {
            return ICUResourceBundle.getAvailableULocales("com/ibm/icu/impl/data/icudt62b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        return Collator.shim.getAvailableULocales();
    }
    
    public static final String[] getKeywords() {
        return Collator.KEYWORDS;
    }
    
    public static final String[] getKeywordValues(final String keyword) {
        if (!keyword.equals(Collator.KEYWORDS[0])) {
            throw new IllegalArgumentException("Invalid keyword: " + keyword);
        }
        return ICUResourceBundle.getKeywordValues("com/ibm/icu/impl/data/icudt62b/coll", "collations");
    }
    
    public static final String[] getKeywordValuesForLocale(final String key, final ULocale locale, final boolean commonlyUsed) {
        final ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/coll", locale);
        final KeywordsSink sink = new KeywordsSink();
        bundle.getAllItemsWithFallback("collations", sink);
        return sink.values.toArray(new String[sink.values.size()]);
    }
    
    public static final ULocale getFunctionalEquivalent(final String keyword, final ULocale locID, final boolean[] isAvailable) {
        return ICUResourceBundle.getFunctionalEquivalent("com/ibm/icu/impl/data/icudt62b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER, "collations", keyword, locID, isAvailable, true);
    }
    
    public static final ULocale getFunctionalEquivalent(final String keyword, final ULocale locID) {
        return getFunctionalEquivalent(keyword, locID, null);
    }
    
    public static String getDisplayName(final Locale objectLocale, final Locale displayLocale) {
        return getShim().getDisplayName(ULocale.forLocale(objectLocale), ULocale.forLocale(displayLocale));
    }
    
    public static String getDisplayName(final ULocale objectLocale, final ULocale displayLocale) {
        return getShim().getDisplayName(objectLocale, displayLocale);
    }
    
    public static String getDisplayName(final Locale objectLocale) {
        return getShim().getDisplayName(ULocale.forLocale(objectLocale), ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public static String getDisplayName(final ULocale objectLocale) {
        return getShim().getDisplayName(objectLocale, ULocale.getDefault(ULocale.Category.DISPLAY));
    }
    
    public int getStrength() {
        return 2;
    }
    
    public int getDecomposition() {
        return 16;
    }
    
    public boolean equals(final String source, final String target) {
        return this.compare(source, target) == 0;
    }
    
    public UnicodeSet getTailoredSet() {
        return new UnicodeSet(0, 1114111);
    }
    
    public abstract int compare(final String p0, final String p1);
    
    @Override
    public int compare(final Object source, final Object target) {
        return this.doCompare((CharSequence)source, (CharSequence)target);
    }
    
    @Deprecated
    protected int doCompare(final CharSequence left, final CharSequence right) {
        return this.compare(left.toString(), right.toString());
    }
    
    public abstract CollationKey getCollationKey(final String p0);
    
    public abstract RawCollationKey getRawCollationKey(final String p0, final RawCollationKey p1);
    
    public Collator setMaxVariable(final int group) {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }
    
    public int getMaxVariable() {
        return 4097;
    }
    
    @Deprecated
    public abstract int setVariableTop(final String p0);
    
    public abstract int getVariableTop();
    
    @Deprecated
    public abstract void setVariableTop(final int p0);
    
    public abstract VersionInfo getVersion();
    
    public abstract VersionInfo getUCAVersion();
    
    public int[] getReorderCodes() {
        throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
    }
    
    public static int[] getEquivalentReorderCodes(final int reorderCode) {
        final CollationData baseData = CollationRoot.getData();
        return baseData.getEquivalentScripts(reorderCode);
    }
    
    @Override
    public boolean isFrozen() {
        return false;
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
    
    public ULocale getLocale(final ULocale.Type type) {
        return ULocale.ROOT;
    }
    
    void setLocale(final ULocale valid, final ULocale actual) {
    }
    
    static {
        KEYWORDS = new String[] { "collation" };
        DEBUG = ICUDebug.enabled("collator");
    }
    
    public abstract static class CollatorFactory
    {
        public boolean visible() {
            return true;
        }
        
        public Collator createCollator(final ULocale loc) {
            return this.createCollator(loc.toLocale());
        }
        
        public Collator createCollator(final Locale loc) {
            return this.createCollator(ULocale.forLocale(loc));
        }
        
        public String getDisplayName(final Locale objectLocale, final Locale displayLocale) {
            return this.getDisplayName(ULocale.forLocale(objectLocale), ULocale.forLocale(displayLocale));
        }
        
        public String getDisplayName(final ULocale objectLocale, final ULocale displayLocale) {
            if (this.visible()) {
                final Set<String> supported = this.getSupportedLocaleIDs();
                final String name = objectLocale.getBaseName();
                if (supported.contains(name)) {
                    return objectLocale.getDisplayName(displayLocale);
                }
            }
            return null;
        }
        
        public abstract Set<String> getSupportedLocaleIDs();
        
        protected CollatorFactory() {
        }
    }
    
    abstract static class ServiceShim
    {
        abstract Collator getInstance(final ULocale p0);
        
        abstract Object registerInstance(final Collator p0, final ULocale p1);
        
        abstract Object registerFactory(final CollatorFactory p0);
        
        abstract boolean unregister(final Object p0);
        
        abstract Locale[] getAvailableLocales();
        
        abstract ULocale[] getAvailableULocales();
        
        abstract String getDisplayName(final ULocale p0, final ULocale p1);
    }
    
    private static final class ASCII
    {
        static boolean equalIgnoreCase(final CharSequence left, final CharSequence right) {
            final int length = left.length();
            if (length != right.length()) {
                return false;
            }
            for (int i = 0; i < length; ++i) {
                final char lc = left.charAt(i);
                final char rc = right.charAt(i);
                if (lc != rc) {
                    if ('A' <= lc && lc <= 'Z') {
                        if (lc + ' ' == rc) {
                            continue;
                        }
                    }
                    else if ('A' <= rc && rc <= 'Z' && rc + ' ' == lc) {
                        continue;
                    }
                    return false;
                }
            }
            return true;
        }
    }
    
    private static final class KeywordsSink extends UResource.Sink
    {
        LinkedList<String> values;
        boolean hasDefault;
        
        private KeywordsSink() {
            this.values = new LinkedList<String>();
            this.hasDefault = false;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table collations = value.getTable();
            for (int i = 0; collations.getKeyAndValue(i, key, value); ++i) {
                final int type = value.getType();
                if (type == 0) {
                    if (!this.hasDefault && key.contentEquals("default")) {
                        final String defcoll = value.getString();
                        if (!defcoll.isEmpty()) {
                            this.values.remove(defcoll);
                            this.values.addFirst(defcoll);
                            this.hasDefault = true;
                        }
                    }
                }
                else if (type == 2 && !key.startsWith("private-")) {
                    final String collkey = key.toString();
                    if (!this.values.contains(collkey)) {
                        this.values.add(collkey);
                    }
                }
            }
        }
    }
    
    public interface ReorderCodes
    {
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
