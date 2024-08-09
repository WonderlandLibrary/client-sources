/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.number.parse.ParsingUtils;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.EnumMap;
import java.util.Map;

public class StaticUnicodeSets {
    private static final Map<Key, UnicodeSet> unicodeSets;
    static final boolean $assertionsDisabled;

    public static UnicodeSet get(Key key) {
        UnicodeSet unicodeSet = unicodeSets.get((Object)key);
        if (unicodeSet == null) {
            return UnicodeSet.EMPTY;
        }
        return unicodeSet;
    }

    public static Key chooseFrom(String string, Key key) {
        return ParsingUtils.safeContains(StaticUnicodeSets.get(key), string) ? key : null;
    }

    public static Key chooseFrom(String string, Key key, Key key2) {
        return ParsingUtils.safeContains(StaticUnicodeSets.get(key), string) ? key : StaticUnicodeSets.chooseFrom(string, key2);
    }

    public static Key chooseCurrency(String string) {
        if (StaticUnicodeSets.get(Key.DOLLAR_SIGN).contains(string)) {
            return Key.DOLLAR_SIGN;
        }
        if (StaticUnicodeSets.get(Key.POUND_SIGN).contains(string)) {
            return Key.POUND_SIGN;
        }
        if (StaticUnicodeSets.get(Key.RUPEE_SIGN).contains(string)) {
            return Key.RUPEE_SIGN;
        }
        if (StaticUnicodeSets.get(Key.YEN_SIGN).contains(string)) {
            return Key.YEN_SIGN;
        }
        if (StaticUnicodeSets.get(Key.WON_SIGN).contains(string)) {
            return Key.WON_SIGN;
        }
        return null;
    }

    private static UnicodeSet computeUnion(Key key, Key key2) {
        return new UnicodeSet().addAll(StaticUnicodeSets.get(key)).addAll(StaticUnicodeSets.get(key2)).freeze();
    }

    private static UnicodeSet computeUnion(Key key, Key key2, Key key3) {
        return new UnicodeSet().addAll(StaticUnicodeSets.get(key)).addAll(StaticUnicodeSets.get(key2)).addAll(StaticUnicodeSets.get(key3)).freeze();
    }

    private static void saveSet(Key key, String string) {
        if (!$assertionsDisabled && unicodeSets.get((Object)key) != null) {
            throw new AssertionError();
        }
        unicodeSets.put(key, new UnicodeSet(string).freeze());
    }

    static void access$000(Key key, String string) {
        StaticUnicodeSets.saveSet(key, string);
    }

    static {
        $assertionsDisabled = !StaticUnicodeSets.class.desiredAssertionStatus();
        unicodeSets = new EnumMap<Key, UnicodeSet>(Key.class);
        unicodeSets.put(Key.EMPTY, new UnicodeSet("[]").freeze());
        unicodeSets.put(Key.DEFAULT_IGNORABLES, new UnicodeSet("[[:Zs:][\\u0009][:Bidi_Control:][:Variation_Selector:]]").freeze());
        unicodeSets.put(Key.STRICT_IGNORABLES, new UnicodeSet("[[:Bidi_Control:]]").freeze());
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", ULocale.ROOT);
        iCUResourceBundle.getAllItemsWithFallback("parse", new ParseDataSink());
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.COMMA)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.STRICT_COMMA)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.PERIOD)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.STRICT_PERIOD)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.APOSTROPHE_SIGN)) {
            throw new AssertionError();
        }
        UnicodeSet unicodeSet = new UnicodeSet("[\u066c\u2018\\u0020\\u00A0\\u2000-\\u200A\\u202F\\u205F\\u3000]");
        unicodeSet.addAll(unicodeSets.get((Object)Key.APOSTROPHE_SIGN));
        unicodeSets.put(Key.OTHER_GROUPING_SEPARATORS, unicodeSet.freeze());
        unicodeSets.put(Key.ALL_SEPARATORS, StaticUnicodeSets.computeUnion(Key.COMMA, Key.PERIOD, Key.OTHER_GROUPING_SEPARATORS));
        unicodeSets.put(Key.STRICT_ALL_SEPARATORS, StaticUnicodeSets.computeUnion(Key.STRICT_COMMA, Key.STRICT_PERIOD, Key.OTHER_GROUPING_SEPARATORS));
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.MINUS_SIGN)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.PLUS_SIGN)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.PERCENT_SIGN)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.PERMILLE_SIGN)) {
            throw new AssertionError();
        }
        unicodeSets.put(Key.INFINITY_SIGN, new UnicodeSet("[\u221e]").freeze());
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.DOLLAR_SIGN)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.POUND_SIGN)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.RUPEE_SIGN)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.YEN_SIGN)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !unicodeSets.containsKey((Object)Key.WON_SIGN)) {
            throw new AssertionError();
        }
        unicodeSets.put(Key.DIGITS, new UnicodeSet("[:digit:]").freeze());
        unicodeSets.put(Key.DIGITS_OR_ALL_SEPARATORS, StaticUnicodeSets.computeUnion(Key.DIGITS, Key.ALL_SEPARATORS));
        unicodeSets.put(Key.DIGITS_OR_STRICT_ALL_SEPARATORS, StaticUnicodeSets.computeUnion(Key.DIGITS, Key.STRICT_ALL_SEPARATORS));
    }

    static class ParseDataSink
    extends UResource.Sink {
        static final boolean $assertionsDisabled = !StaticUnicodeSets.class.desiredAssertionStatus();

        ParseDataSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (!key.contentEquals("date")) {
                    if (!($assertionsDisabled || key.contentEquals("general") || key.contentEquals("number"))) {
                        throw new AssertionError();
                    }
                    UResource.Table table2 = value.getTable();
                    int n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        boolean bl2 = key.contentEquals("lenient");
                        UResource.Array array = value.getArray();
                        for (int i = 0; i < array.getSize(); ++i) {
                            array.getValue(i, value);
                            String string = value.toString();
                            if (string.indexOf(46) != -1) {
                                StaticUnicodeSets.access$000(bl2 ? Key.PERIOD : Key.STRICT_PERIOD, string);
                                continue;
                            }
                            if (string.indexOf(44) != -1) {
                                StaticUnicodeSets.access$000(bl2 ? Key.COMMA : Key.STRICT_COMMA, string);
                                continue;
                            }
                            if (string.indexOf(43) != -1) {
                                StaticUnicodeSets.access$000(Key.PLUS_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(45) != -1) {
                                StaticUnicodeSets.access$000(Key.MINUS_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(36) != -1) {
                                StaticUnicodeSets.access$000(Key.DOLLAR_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(163) != -1) {
                                StaticUnicodeSets.access$000(Key.POUND_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(8377) != -1) {
                                StaticUnicodeSets.access$000(Key.RUPEE_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(165) != -1) {
                                StaticUnicodeSets.access$000(Key.YEN_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(8361) != -1) {
                                StaticUnicodeSets.access$000(Key.WON_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(37) != -1) {
                                StaticUnicodeSets.access$000(Key.PERCENT_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(8240) != -1) {
                                StaticUnicodeSets.access$000(Key.PERMILLE_SIGN, string);
                                continue;
                            }
                            if (string.indexOf(8217) != -1) {
                                StaticUnicodeSets.access$000(Key.APOSTROPHE_SIGN, string);
                                continue;
                            }
                            throw new AssertionError((Object)("Unknown class of parse lenients: " + string));
                        }
                        ++n2;
                    }
                }
                ++n;
            }
        }
    }

    public static enum Key {
        EMPTY,
        DEFAULT_IGNORABLES,
        STRICT_IGNORABLES,
        COMMA,
        PERIOD,
        STRICT_COMMA,
        STRICT_PERIOD,
        APOSTROPHE_SIGN,
        OTHER_GROUPING_SEPARATORS,
        ALL_SEPARATORS,
        STRICT_ALL_SEPARATORS,
        MINUS_SIGN,
        PLUS_SIGN,
        PERCENT_SIGN,
        PERMILLE_SIGN,
        INFINITY_SIGN,
        DOLLAR_SIGN,
        POUND_SIGN,
        RUPEE_SIGN,
        YEN_SIGN,
        WON_SIGN,
        DIGITS,
        DIGITS_OR_ALL_SEPARATORS,
        DIGITS_OR_STRICT_ALL_SEPARATORS;

    }
}

