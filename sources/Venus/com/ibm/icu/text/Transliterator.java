/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.AnyTransliterator;
import com.ibm.icu.text.BreakTransliterator;
import com.ibm.icu.text.CaseFoldTransliterator;
import com.ibm.icu.text.CompoundTransliterator;
import com.ibm.icu.text.EscapeTransliterator;
import com.ibm.icu.text.LowercaseTransliterator;
import com.ibm.icu.text.NameUnicodeTransliterator;
import com.ibm.icu.text.NormalizationTransliterator;
import com.ibm.icu.text.NullTransliterator;
import com.ibm.icu.text.RemoveTransliterator;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.ReplaceableString;
import com.ibm.icu.text.RuleBasedTransliterator;
import com.ibm.icu.text.StringTransform;
import com.ibm.icu.text.TitlecaseTransliterator;
import com.ibm.icu.text.TransliteratorIDParser;
import com.ibm.icu.text.TransliteratorParser;
import com.ibm.icu.text.TransliteratorRegistry;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnescapeTransliterator;
import com.ibm.icu.text.UnicodeFilter;
import com.ibm.icu.text.UnicodeNameTransliterator;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.text.UppercaseTransliterator;
import com.ibm.icu.util.CaseInsensitiveString;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;

public abstract class Transliterator
implements StringTransform {
    public static final int FORWARD = 0;
    public static final int REVERSE = 1;
    private String ID;
    private UnicodeSet filter;
    private int maximumContextLength = 0;
    private static TransliteratorRegistry registry = new TransliteratorRegistry();
    private static Map<CaseInsensitiveString, String> displayNameCache = Collections.synchronizedMap(new HashMap());
    private static final String RB_DISPLAY_NAME_PREFIX = "%Translit%%";
    private static final String RB_SCRIPT_DISPLAY_NAME_PREFIX = "%Translit%";
    private static final String RB_DISPLAY_NAME_PATTERN = "TransliteratorNamePattern";
    static final char ID_DELIM = ';';
    static final char ID_SEP = '-';
    static final char VARIANT_SEP = '/';
    static final boolean DEBUG = false;
    private static final String ROOT = "root";
    private static final String RB_RULE_BASED_IDS = "RuleBasedTransliteratorIDs";

    protected Transliterator(String string, UnicodeFilter unicodeFilter) {
        if (string == null) {
            throw new NullPointerException();
        }
        this.ID = string;
        this.setFilter(unicodeFilter);
    }

    public final int transliterate(Replaceable replaceable, int n, int n2) {
        if (n < 0 || n2 < n || replaceable.length() < n2) {
            return 1;
        }
        Position position = new Position(n, n2, n);
        this.filteredTransliterate(replaceable, position, false, true);
        return position.limit;
    }

    public final void transliterate(Replaceable replaceable) {
        this.transliterate(replaceable, 0, replaceable.length());
    }

    public final String transliterate(String string) {
        ReplaceableString replaceableString = new ReplaceableString(string);
        this.transliterate(replaceableString);
        return replaceableString.toString();
    }

    public final void transliterate(Replaceable replaceable, Position position, String string) {
        position.validate(replaceable.length());
        if (string != null) {
            replaceable.replace(position.limit, position.limit, string);
            position.limit += string.length();
            position.contextLimit += string.length();
        }
        if (position.limit > 0 && UTF16.isLeadSurrogate(replaceable.charAt(position.limit - 1))) {
            return;
        }
        this.filteredTransliterate(replaceable, position, true, true);
    }

    public final void transliterate(Replaceable replaceable, Position position, int n) {
        this.transliterate(replaceable, position, UTF16.valueOf(n));
    }

    public final void transliterate(Replaceable replaceable, Position position) {
        this.transliterate(replaceable, position, null);
    }

    public final void finishTransliteration(Replaceable replaceable, Position position) {
        position.validate(replaceable.length());
        this.filteredTransliterate(replaceable, position, false, true);
    }

    protected abstract void handleTransliterate(Replaceable var1, Position var2, boolean var3);

    private void filteredTransliterate(Replaceable replaceable, Position position, boolean bl, boolean bl2) {
        int n;
        if (this.filter == null && !bl2) {
            this.handleTransliterate(replaceable, position, bl);
            return;
        }
        int n2 = position.limit;
        Object var6_6 = null;
        do {
            int n3;
            int n4;
            if (this.filter != null) {
                while (position.start < n2 && !this.filter.contains(n = replaceable.char32At(position.start))) {
                    position.start += UTF16.getCharCount(n);
                }
                position.limit = position.start;
                while (position.limit < n2 && this.filter.contains(n = replaceable.char32At(position.limit))) {
                    position.limit += UTF16.getCharCount(n);
                }
            }
            if (position.start == position.limit) break;
            int n5 = n = position.limit < n2 ? 0 : bl;
            if (bl2 && n != 0) {
                int n6;
                n4 = position.start;
                int n7 = position.limit;
                int n8 = n7 - n4;
                int n9 = replaceable.length();
                replaceable.copy(n4, n7, n9);
                int n10 = n4;
                int n11 = n9;
                int n12 = position.start;
                int n13 = 0;
                int n14 = 0;
                while ((n12 += (n6 = UTF16.getCharCount(replaceable.char32At(n12)))) <= n7) {
                    n13 += n6;
                    position.limit = n12;
                    this.handleTransliterate(replaceable, position, false);
                    n3 = position.limit - n12;
                    if (position.start != position.limit) {
                        int n15 = n11 + n3 - (position.limit - n10);
                        replaceable.replace(n10, position.limit, "");
                        replaceable.copy(n15, n15 + n13, n10);
                        position.start = n10;
                        position.limit = n12;
                        position.contextLimit -= n3;
                        continue;
                    }
                    n10 = n12 = position.start;
                    n11 += n3 + n13;
                    n13 = 0;
                    n7 += n3;
                    n14 += n3;
                }
                n2 += n14;
                replaceable.replace(n9 += n14, n9 + n8, "");
                position.start = n10;
                continue;
            }
            n4 = position.limit;
            this.handleTransliterate(replaceable, position, n != 0);
            n3 = position.limit - n4;
            if (n == 0 && position.start != position.limit) {
                throw new RuntimeException("ERROR: Incomplete non-incremental transliteration by " + this.getID());
            }
            n2 += n3;
        } while (this.filter != null && n == 0);
        position.limit = n2;
    }

    public void filteredTransliterate(Replaceable replaceable, Position position, boolean bl) {
        this.filteredTransliterate(replaceable, position, bl, false);
    }

    public final int getMaximumContextLength() {
        return this.maximumContextLength;
    }

    protected void setMaximumContextLength(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Invalid context length " + n);
        }
        this.maximumContextLength = n;
    }

    public final String getID() {
        return this.ID;
    }

    protected final void setID(String string) {
        this.ID = string;
    }

    public static final String getDisplayName(String string) {
        return Transliterator.getDisplayName(string, ULocale.getDefault(ULocale.Category.DISPLAY));
    }

    public static String getDisplayName(String string, Locale locale) {
        return Transliterator.getDisplayName(string, ULocale.forLocale(locale));
    }

    public static String getDisplayName(String string, ULocale uLocale) {
        String string2;
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/translit", uLocale);
        String[] stringArray = TransliteratorIDParser.IDtoSTV(string);
        if (stringArray == null) {
            return "";
        }
        String string3 = stringArray[0] + '-' + stringArray[5];
        if (stringArray[5] != null && stringArray[5].length() > 0) {
            string3 = string3 + '/' + stringArray[5];
        }
        if ((string2 = displayNameCache.get(new CaseInsensitiveString(string3))) != null) {
            return string2;
        }
        try {
            return iCUResourceBundle.getString(RB_DISPLAY_NAME_PREFIX + string3);
        } catch (MissingResourceException missingResourceException) {
            try {
                MessageFormat messageFormat = new MessageFormat(iCUResourceBundle.getString(RB_DISPLAY_NAME_PATTERN));
                Object[] objectArray = new Object[]{2, stringArray[0], stringArray[5]};
                for (int i = 1; i <= 2; ++i) {
                    try {
                        objectArray[i] = iCUResourceBundle.getString(RB_SCRIPT_DISPLAY_NAME_PREFIX + (String)objectArray[i]);
                        continue;
                    } catch (MissingResourceException missingResourceException2) {
                        // empty catch block
                    }
                }
                return stringArray[5].length() > 0 ? messageFormat.format(objectArray) + '/' + stringArray[5] : messageFormat.format(objectArray);
            } catch (MissingResourceException missingResourceException3) {
                throw new RuntimeException();
            }
        }
    }

    public final UnicodeFilter getFilter() {
        return this.filter;
    }

    public void setFilter(UnicodeFilter unicodeFilter) {
        if (unicodeFilter == null) {
            this.filter = null;
        } else {
            try {
                this.filter = new UnicodeSet((UnicodeSet)unicodeFilter).freeze();
            } catch (Exception exception) {
                this.filter = new UnicodeSet();
                unicodeFilter.addMatchSetTo(this.filter);
                this.filter.freeze();
            }
        }
    }

    public static final Transliterator getInstance(String string) {
        return Transliterator.getInstance(string, 0);
    }

    public static Transliterator getInstance(String string, int n) {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<TransliteratorIDParser.SingleID> arrayList = new ArrayList<TransliteratorIDParser.SingleID>();
        UnicodeSet[] unicodeSetArray = new UnicodeSet[1];
        if (!TransliteratorIDParser.parseCompoundID(string, n, stringBuffer, arrayList, unicodeSetArray)) {
            throw new IllegalArgumentException("Invalid ID " + string);
        }
        List<Transliterator> list = TransliteratorIDParser.instantiateList(arrayList);
        Transliterator transliterator = null;
        transliterator = arrayList.size() > 1 || stringBuffer.indexOf(";") >= 0 ? new CompoundTransliterator(list) : list.get(0);
        transliterator.setID(stringBuffer.toString());
        if (unicodeSetArray[0] != null) {
            transliterator.setFilter(unicodeSetArray[0]);
        }
        return transliterator;
    }

    static Transliterator getBasicInstance(String string, String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        Transliterator transliterator = registry.get(string, stringBuffer);
        if (stringBuffer.length() != 0) {
            transliterator = Transliterator.getInstance(stringBuffer.toString(), 0);
        }
        if (transliterator != null && string2 != null) {
            transliterator.setID(string2);
        }
        return transliterator;
    }

    public static final Transliterator createFromRules(String string, String string2, int n) {
        Transliterator transliterator = null;
        TransliteratorParser transliteratorParser = new TransliteratorParser();
        transliteratorParser.parse(string2, n);
        if (transliteratorParser.idBlockVector.size() == 0 && transliteratorParser.dataVector.size() == 0) {
            transliterator = new NullTransliterator();
        } else if (transliteratorParser.idBlockVector.size() == 0 && transliteratorParser.dataVector.size() == 1) {
            transliterator = new RuleBasedTransliterator(string, transliteratorParser.dataVector.get(0), transliteratorParser.compoundFilter);
        } else if (transliteratorParser.idBlockVector.size() == 1 && transliteratorParser.dataVector.size() == 0) {
            transliterator = transliteratorParser.compoundFilter != null ? Transliterator.getInstance(transliteratorParser.compoundFilter.toPattern(true) + ";" + transliteratorParser.idBlockVector.get(0)) : Transliterator.getInstance(transliteratorParser.idBlockVector.get(0));
            if (transliterator != null) {
                transliterator.setID(string);
            }
        } else {
            ArrayList<Transliterator> arrayList = new ArrayList<Transliterator>();
            int n2 = 1;
            int n3 = Math.max(transliteratorParser.idBlockVector.size(), transliteratorParser.dataVector.size());
            for (int i = 0; i < n3; ++i) {
                Transliterator transliterator2;
                Object object;
                if (i < transliteratorParser.idBlockVector.size() && ((String)(object = transliteratorParser.idBlockVector.get(i))).length() > 0 && !((transliterator2 = Transliterator.getInstance((String)object)) instanceof NullTransliterator)) {
                    arrayList.add(Transliterator.getInstance((String)object));
                }
                if (i >= transliteratorParser.dataVector.size()) continue;
                object = transliteratorParser.dataVector.get(i);
                arrayList.add(new RuleBasedTransliterator("%Pass" + n2++, (RuleBasedTransliterator.Data)object, null));
            }
            transliterator = new CompoundTransliterator(arrayList, n2 - 1);
            transliterator.setID(string);
            if (transliteratorParser.compoundFilter != null) {
                transliterator.setFilter(transliteratorParser.compoundFilter);
            }
        }
        return transliterator;
    }

    public String toRules(boolean bl) {
        return this.baseToRules(bl);
    }

    protected final String baseToRules(boolean bl) {
        if (bl) {
            int n;
            StringBuffer stringBuffer = new StringBuffer();
            String string = this.getID();
            for (int i = 0; i < string.length(); i += UTF16.getCharCount(n)) {
                n = UTF16.charAt(string, i);
                if (Utility.escapeUnprintable(stringBuffer, n)) continue;
                UTF16.append(stringBuffer, n);
            }
            stringBuffer.insert(0, "::");
            stringBuffer.append(';');
            return stringBuffer.toString();
        }
        return "::" + this.getID() + ';';
    }

    public Transliterator[] getElements() {
        Transliterator[] transliteratorArray;
        if (this instanceof CompoundTransliterator) {
            CompoundTransliterator compoundTransliterator = (CompoundTransliterator)this;
            transliteratorArray = new Transliterator[compoundTransliterator.getCount()];
            for (int i = 0; i < transliteratorArray.length; ++i) {
                transliteratorArray[i] = compoundTransliterator.getTransliterator(i);
            }
        } else {
            transliteratorArray = new Transliterator[]{this};
        }
        return transliteratorArray;
    }

    public final UnicodeSet getSourceSet() {
        UnicodeSet unicodeSet = new UnicodeSet();
        this.addSourceTargetSet(this.getFilterAsUnicodeSet(UnicodeSet.ALL_CODE_POINTS), unicodeSet, new UnicodeSet());
        return unicodeSet;
    }

    protected UnicodeSet handleGetSourceSet() {
        return new UnicodeSet();
    }

    public UnicodeSet getTargetSet() {
        UnicodeSet unicodeSet = new UnicodeSet();
        this.addSourceTargetSet(this.getFilterAsUnicodeSet(UnicodeSet.ALL_CODE_POINTS), new UnicodeSet(), unicodeSet);
        return unicodeSet;
    }

    @Deprecated
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        UnicodeSet unicodeSet4 = this.getFilterAsUnicodeSet(unicodeSet);
        UnicodeSet unicodeSet5 = new UnicodeSet(this.handleGetSourceSet()).retainAll(unicodeSet4);
        unicodeSet2.addAll(unicodeSet5);
        for (String string : unicodeSet5) {
            String string2;
            if (string.equals(string2 = this.transliterate(string))) continue;
            unicodeSet3.addAll(string2);
        }
    }

    @Deprecated
    public UnicodeSet getFilterAsUnicodeSet(UnicodeSet unicodeSet) {
        UnicodeSet unicodeSet2;
        if (this.filter == null) {
            return unicodeSet;
        }
        UnicodeSet unicodeSet3 = new UnicodeSet(unicodeSet);
        try {
            unicodeSet2 = this.filter;
        } catch (ClassCastException classCastException) {
            unicodeSet2 = new UnicodeSet();
            this.filter.addMatchSetTo(unicodeSet2);
        }
        return unicodeSet3.retainAll(unicodeSet2).freeze();
    }

    public final Transliterator getInverse() {
        return Transliterator.getInstance(this.ID, 1);
    }

    public static void registerClass(String string, Class<? extends Transliterator> clazz, String string2) {
        registry.put(string, clazz, false);
        if (string2 != null) {
            displayNameCache.put(new CaseInsensitiveString(string), string2);
        }
    }

    public static void registerFactory(String string, Factory factory) {
        registry.put(string, factory, false);
    }

    public static void registerInstance(Transliterator transliterator) {
        registry.put(transliterator.getID(), transliterator, false);
    }

    static void registerInstance(Transliterator transliterator, boolean bl) {
        registry.put(transliterator.getID(), transliterator, bl);
    }

    public static void registerAlias(String string, String string2) {
        registry.put(string, string2, false);
    }

    static void registerSpecialInverse(String string, String string2, boolean bl) {
        TransliteratorIDParser.registerSpecialInverse(string, string2, bl);
    }

    public static void unregister(String string) {
        displayNameCache.remove(new CaseInsensitiveString(string));
        registry.remove(string);
    }

    public static final Enumeration<String> getAvailableIDs() {
        return registry.getAvailableIDs();
    }

    public static final Enumeration<String> getAvailableSources() {
        return registry.getAvailableSources();
    }

    public static final Enumeration<String> getAvailableTargets(String string) {
        return registry.getAvailableTargets(string);
    }

    public static final Enumeration<String> getAvailableVariants(String string, String string2) {
        return registry.getAvailableVariants(string, string2);
    }

    @Deprecated
    public static void registerAny() {
        AnyTransliterator.register();
    }

    @Override
    public String transform(String string) {
        return this.transliterate(string);
    }

    @Override
    public Object transform(Object object) {
        return this.transform((String)object);
    }

    static {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/translit", ROOT);
        UResourceBundle uResourceBundle2 = uResourceBundle.get(RB_RULE_BASED_IDS);
        int n = uResourceBundle2.getSize();
        for (int i = 0; i < n; ++i) {
            String string;
            UResourceBundle uResourceBundle3 = uResourceBundle2.get(i);
            String string2 = uResourceBundle3.getKey();
            if (string2.indexOf("-t-") >= 0) continue;
            UResourceBundle uResourceBundle4 = uResourceBundle3.get(0);
            String string3 = uResourceBundle4.getKey();
            if (string3.equals("file") || string3.equals("internal")) {
                int n2;
                string = uResourceBundle4.getString("resource");
                String string4 = uResourceBundle4.getString("direction");
                switch (string4.charAt(0)) {
                    case 'F': {
                        n2 = 0;
                        break;
                    }
                    case 'R': {
                        n2 = 1;
                        break;
                    }
                    default: {
                        throw new RuntimeException("Can't parse direction: " + string4);
                    }
                }
                registry.put(string2, string, n2, !string3.equals("internal"));
                continue;
            }
            if (string3.equals("alias")) {
                string = uResourceBundle4.getString();
                registry.put(string2, string, false);
                continue;
            }
            throw new RuntimeException("Unknow type: " + string3);
        }
        Transliterator.registerSpecialInverse("Null", "Null", false);
        Transliterator.registerClass("Any-Null", NullTransliterator.class, null);
        RemoveTransliterator.register();
        EscapeTransliterator.register();
        UnescapeTransliterator.register();
        LowercaseTransliterator.register();
        UppercaseTransliterator.register();
        TitlecaseTransliterator.register();
        CaseFoldTransliterator.register();
        UnicodeNameTransliterator.register();
        NameUnicodeTransliterator.register();
        NormalizationTransliterator.register();
        BreakTransliterator.register();
        AnyTransliterator.register();
    }

    public static interface Factory {
        public Transliterator getInstance(String var1);
    }

    public static class Position {
        public int contextStart;
        public int contextLimit;
        public int start;
        public int limit;

        public Position() {
            this(0, 0, 0, 0);
        }

        public Position(int n, int n2, int n3) {
            this(n, n2, n3, n2);
        }

        public Position(int n, int n2, int n3, int n4) {
            this.contextStart = n;
            this.contextLimit = n2;
            this.start = n3;
            this.limit = n4;
        }

        public Position(Position position) {
            this.set(position);
        }

        public void set(Position position) {
            this.contextStart = position.contextStart;
            this.contextLimit = position.contextLimit;
            this.start = position.start;
            this.limit = position.limit;
        }

        public boolean equals(Object object) {
            if (object instanceof Position) {
                Position position = (Position)object;
                return this.contextStart == position.contextStart && this.contextLimit == position.contextLimit && this.start == position.start && this.limit == position.limit;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hash(this.contextStart, this.contextLimit, this.start, this.limit);
        }

        public String toString() {
            return "[cs=" + this.contextStart + ", s=" + this.start + ", l=" + this.limit + ", cl=" + this.contextLimit + "]";
        }

        public final void validate(int n) {
            if (this.contextStart < 0 || this.start < this.contextStart || this.limit < this.start || this.contextLimit < this.limit || n < this.contextLimit) {
                throw new IllegalArgumentException("Invalid Position {cs=" + this.contextStart + ", s=" + this.start + ", l=" + this.limit + ", cl=" + this.contextLimit + "}, len=" + n);
            }
        }
    }
}

