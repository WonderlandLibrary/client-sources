/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.locale.LSR;
import com.ibm.icu.util.BytesTrie;
import com.ibm.icu.util.ULocale;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.TreeMap;

public final class XLikelySubtags {
    private static final String PSEUDO_ACCENTS_PREFIX = "'";
    private static final String PSEUDO_BIDI_PREFIX = "+";
    private static final String PSEUDO_CRACKED_PREFIX = ",";
    public static final int SKIP_SCRIPT = 1;
    private static final boolean DEBUG_OUTPUT = false;
    public static final XLikelySubtags INSTANCE;
    private final Map<String, String> languageAliases;
    private final Map<String, String> regionAliases;
    private final BytesTrie trie;
    private final long trieUndState;
    private final long trieUndZzzzState;
    private final int defaultLsrIndex;
    private final long[] trieFirstLetterStates = new long[26];
    private final LSR[] lsrs;
    static final boolean $assertionsDisabled;

    private XLikelySubtags(Data data) {
        this.languageAliases = data.languageAliases;
        this.regionAliases = data.regionAliases;
        this.trie = new BytesTrie(data.trie, 0);
        this.lsrs = data.lsrs;
        BytesTrie.Result result = this.trie.next(42);
        if (!$assertionsDisabled && !result.hasNext()) {
            throw new AssertionError();
        }
        this.trieUndState = this.trie.getState64();
        result = this.trie.next(42);
        if (!$assertionsDisabled && !result.hasNext()) {
            throw new AssertionError();
        }
        this.trieUndZzzzState = this.trie.getState64();
        result = this.trie.next(42);
        if (!$assertionsDisabled && !result.hasValue()) {
            throw new AssertionError();
        }
        this.defaultLsrIndex = this.trie.getValue();
        this.trie.reset();
        for (int n = 97; n <= 122; n = (int)((char)(n + 1))) {
            result = this.trie.next(n);
            if (result == BytesTrie.Result.NO_VALUE) {
                this.trieFirstLetterStates[n - 97] = this.trie.getState64();
            }
            this.trie.reset();
        }
    }

    public ULocale canonicalize(ULocale uLocale) {
        String string = uLocale.getLanguage();
        String string2 = this.languageAliases.get(string);
        String string3 = uLocale.getCountry();
        String string4 = this.regionAliases.get(string3);
        if (string2 != null || string4 != null) {
            return new ULocale(string2 == null ? string : string2, uLocale.getScript(), string4 == null ? string3 : string4);
        }
        return uLocale;
    }

    private static String getCanonical(Map<String, String> map, String string) {
        String string2 = map.get(string);
        return string2 == null ? string : string2;
    }

    public LSR makeMaximizedLsrFrom(ULocale uLocale) {
        String string = uLocale.getName();
        if (string.startsWith("@x=")) {
            String string2 = uLocale.toLanguageTag();
            if (!$assertionsDisabled && !string2.startsWith("x-")) {
                throw new AssertionError();
            }
            return new LSR(string2, "", "");
        }
        return this.makeMaximizedLsr(uLocale.getLanguage(), uLocale.getScript(), uLocale.getCountry(), uLocale.getVariant());
    }

    public LSR makeMaximizedLsrFrom(Locale locale) {
        String string = locale.toLanguageTag();
        if (string.startsWith("x-")) {
            return new LSR(string, "", "");
        }
        return this.makeMaximizedLsr(locale.getLanguage(), locale.getScript(), locale.getCountry(), locale.getVariant());
    }

    private LSR makeMaximizedLsr(String string, String string2, String string3, String string4) {
        if (string3.length() == 2 && string3.charAt(0) == 'X') {
            switch (string3.charAt(1)) {
                case 'A': {
                    return new LSR(PSEUDO_ACCENTS_PREFIX + string, PSEUDO_ACCENTS_PREFIX + string2, string3);
                }
                case 'B': {
                    return new LSR(PSEUDO_BIDI_PREFIX + string, PSEUDO_BIDI_PREFIX + string2, string3);
                }
                case 'C': {
                    return new LSR(PSEUDO_CRACKED_PREFIX + string, PSEUDO_CRACKED_PREFIX + string2, string3);
                }
            }
        }
        if (string4.startsWith("PS")) {
            switch (string4) {
                case "PSACCENT": {
                    return new LSR(PSEUDO_ACCENTS_PREFIX + string, PSEUDO_ACCENTS_PREFIX + string2, string3.isEmpty() ? "XA" : string3);
                }
                case "PSBIDI": {
                    return new LSR(PSEUDO_BIDI_PREFIX + string, PSEUDO_BIDI_PREFIX + string2, string3.isEmpty() ? "XB" : string3);
                }
                case "PSCRACK": {
                    return new LSR(PSEUDO_CRACKED_PREFIX + string, PSEUDO_CRACKED_PREFIX + string2, string3.isEmpty() ? "XC" : string3);
                }
            }
        }
        string = XLikelySubtags.getCanonical(this.languageAliases, string);
        string3 = XLikelySubtags.getCanonical(this.regionAliases, string3);
        return this.maximize(string, string2, string3);
    }

    private LSR maximize(String string, String string2, String string3) {
        long l;
        int n;
        if (string.equals("und")) {
            string = "";
        }
        if (string2.equals("Zzzz")) {
            string2 = "";
        }
        if (string3.equals("ZZ")) {
            string3 = "";
        }
        if (!(string2.isEmpty() || string3.isEmpty() || string.isEmpty())) {
            return new LSR(string, string2, string3);
        }
        int n2 = 0;
        BytesTrie bytesTrie = new BytesTrie(this.trie);
        int n3 = string.length() >= 2 && 0 <= (n = string.charAt(0) - 97) && n <= 25 && (l = this.trieFirstLetterStates[n]) != 0L ? XLikelySubtags.trieNext(bytesTrie.resetToState64(l), string, 1) : XLikelySubtags.trieNext(bytesTrie, string, 0);
        if (n3 >= 0) {
            if (!string.isEmpty()) {
                n2 |= 4;
            }
            l = bytesTrie.getState64();
        } else {
            n2 |= 4;
            bytesTrie.resetToState64(this.trieUndState);
            l = 0L;
        }
        if (n3 > 0) {
            if (n3 == 1) {
                n3 = 0;
            }
            if (!string2.isEmpty()) {
                n2 |= 2;
            }
        } else {
            n3 = XLikelySubtags.trieNext(bytesTrie, string2, 0);
            if (n3 >= 0) {
                if (!string2.isEmpty()) {
                    n2 |= 2;
                }
                l = bytesTrie.getState64();
            } else {
                n2 |= 2;
                if (l == 0L) {
                    bytesTrie.resetToState64(this.trieUndZzzzState);
                } else {
                    bytesTrie.resetToState64(l);
                    n3 = XLikelySubtags.trieNext(bytesTrie, "", 0);
                    if (!$assertionsDisabled && n3 < 0) {
                        throw new AssertionError();
                    }
                    l = bytesTrie.getState64();
                }
            }
        }
        if (n3 > 0) {
            if (!string3.isEmpty()) {
                n2 |= 1;
            }
        } else {
            n3 = XLikelySubtags.trieNext(bytesTrie, string3, 0);
            if (n3 >= 0) {
                if (!string3.isEmpty()) {
                    n2 |= 1;
                }
            } else {
                n2 |= 1;
                if (l == 0L) {
                    n3 = this.defaultLsrIndex;
                } else {
                    bytesTrie.resetToState64(l);
                    n3 = XLikelySubtags.trieNext(bytesTrie, "", 0);
                    if (!$assertionsDisabled && n3 <= 0) {
                        throw new AssertionError();
                    }
                }
            }
        }
        LSR lSR = this.lsrs[n3];
        if (string.isEmpty()) {
            string = "und";
        }
        if (n2 == 0) {
            return lSR;
        }
        if ((n2 & 4) == 0) {
            string = lSR.language;
        }
        if ((n2 & 2) == 0) {
            string2 = lSR.script;
        }
        if ((n2 & 1) == 0) {
            string3 = lSR.region;
        }
        return new LSR(string, string2, string3);
    }

    private static final int trieNext(BytesTrie bytesTrie, String string, int n) {
        BytesTrie.Result result;
        if (string.isEmpty()) {
            result = bytesTrie.next(42);
        } else {
            int n2 = string.length() - 1;
            while (true) {
                char c = string.charAt(n);
                if (n >= n2) {
                    result = bytesTrie.next(c | 0x80);
                    break;
                }
                if (!bytesTrie.next(c).hasNext()) {
                    return 1;
                }
                ++n;
            }
        }
        switch (1.$SwitchMap$com$ibm$icu$util$BytesTrie$Result[result.ordinal()]) {
            case 1: {
                return 1;
            }
            case 2: {
                return 1;
            }
            case 3: {
                if (!$assertionsDisabled && bytesTrie.getValue() != 1) {
                    throw new AssertionError();
                }
                return 0;
            }
            case 4: {
                return bytesTrie.getValue();
            }
        }
        return 1;
    }

    LSR minimizeSubtags(String string, String string2, String string3, ULocale.Minimize minimize) {
        LSR lSR;
        LSR lSR2 = this.maximize(string, string2, string3);
        BytesTrie bytesTrie = new BytesTrie(this.trie);
        int n = XLikelySubtags.trieNext(bytesTrie, lSR2.language, 0);
        if (!$assertionsDisabled && n < 0) {
            throw new AssertionError();
        }
        if (n == 0) {
            n = XLikelySubtags.trieNext(bytesTrie, "", 0);
            if (!$assertionsDisabled && n < 0) {
                throw new AssertionError();
            }
            if (n == 0) {
                n = XLikelySubtags.trieNext(bytesTrie, "", 0);
            }
        }
        if (!$assertionsDisabled && n <= 0) {
            throw new AssertionError();
        }
        LSR lSR3 = this.lsrs[n];
        boolean bl = false;
        if (lSR2.script.equals(lSR3.script)) {
            if (lSR2.region.equals(lSR3.region)) {
                return new LSR(lSR2.language, "", "");
            }
            if (minimize == ULocale.Minimize.FAVOR_REGION) {
                return new LSR(lSR2.language, "", lSR2.region);
            }
            bl = true;
        }
        if ((lSR = this.maximize(string, string2, "")).equals(lSR2)) {
            return new LSR(lSR2.language, lSR2.script, "");
        }
        if (bl) {
            return new LSR(lSR2.language, "", lSR2.region);
        }
        return lSR2;
    }

    private Map<String, LSR> getTable() {
        TreeMap<String, LSR> treeMap = new TreeMap<String, LSR>();
        StringBuilder stringBuilder = new StringBuilder();
        for (BytesTrie.Entry entry : this.trie) {
            stringBuilder.setLength(0);
            int n = entry.bytesLength();
            int n2 = 0;
            while (n2 < n) {
                byte by;
                if ((by = entry.byteAt(n2++)) == 42) {
                    stringBuilder.append("*-");
                    continue;
                }
                if (by >= 0) {
                    stringBuilder.append((char)by);
                    continue;
                }
                stringBuilder.append((char)(by & 0x7F)).append('-');
            }
            if (!($assertionsDisabled || stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '-')) {
                throw new AssertionError();
            }
            stringBuilder.setLength(stringBuilder.length() - 1);
            treeMap.put(stringBuilder.toString(), this.lsrs[entry.value]);
        }
        return treeMap;
    }

    public String toString() {
        return this.getTable().toString();
    }

    static {
        $assertionsDisabled = !XLikelySubtags.class.desiredAssertionStatus();
        INSTANCE = new XLikelySubtags(Data.load());
    }

    public static final class Data {
        public final Map<String, String> languageAliases;
        public final Map<String, String> regionAliases;
        public final byte[] trie;
        public final LSR[] lsrs;

        public Data(Map<String, String> map, Map<String, String> map2, byte[] byArray, LSR[] lSRArray) {
            this.languageAliases = map;
            this.regionAliases = map2;
            this.trie = byArray;
            this.lsrs = lSRArray;
        }

        private static UResource.Value getValue(UResource.Table table, String string, UResource.Value value) {
            if (!table.findValue(string, value)) {
                throw new MissingResourceException("langInfo.res missing data", "", "likely/" + string);
            }
            return value;
        }

        public static Data load() throws MissingResourceException {
            Map<String, String> map;
            Object object;
            ICUResourceBundle iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "langInfo", ICUResourceBundle.ICU_DATA_CLASS_LOADER, ICUResourceBundle.OpenType.DIRECT);
            UResource.Value value = iCUResourceBundle.getValueWithFallback("likely");
            UResource.Table table = value.getTable();
            if (table.findValue("languageAliases", value)) {
                object = value.getStringArray();
                map = new HashMap(((String[])object).length / 2);
                for (int i = 0; i < ((String[])object).length; i += 2) {
                    map.put(object[i], object[i + 1]);
                }
            } else {
                map = Collections.emptyMap();
            }
            if (table.findValue("regionAliases", value)) {
                String[] stringArray = value.getStringArray();
                object = new HashMap(stringArray.length / 2);
                for (int i = 0; i < stringArray.length; i += 2) {
                    object.put(stringArray[i], stringArray[i + 1]);
                }
            } else {
                object = Collections.emptyMap();
            }
            ByteBuffer byteBuffer = Data.getValue(table, "trie", value).getBinary();
            byte[] byArray = new byte[byteBuffer.remaining()];
            byteBuffer.get(byArray);
            String[] stringArray = Data.getValue(table, "lsrs", value).getStringArray();
            LSR[] lSRArray = new LSR[stringArray.length / 3];
            int n = 0;
            int n2 = 0;
            while (n < stringArray.length) {
                lSRArray[n2] = new LSR(stringArray[n], stringArray[n + 1], stringArray[n + 2]);
                n += 3;
                ++n2;
            }
            return new Data(map, (Map<String, String>)object, byArray, lSRArray);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!this.getClass().equals(object.getClass())) {
                return true;
            }
            Data data = (Data)object;
            return this.languageAliases.equals(data.languageAliases) && this.regionAliases.equals(data.regionAliases) && Arrays.equals(this.trie, data.trie) && Arrays.equals(this.lsrs, data.lsrs);
        }
    }
}

