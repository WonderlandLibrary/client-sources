package com.ibm.icu.impl;

import com.ibm.icu.text.*;
import com.ibm.icu.impl.number.parse.*;
import java.util.*;
import com.ibm.icu.util.*;

public class StaticUnicodeSets
{
    private static final Map<Key, UnicodeSet> unicodeSets;
    
    public static UnicodeSet get(final Key key) {
        final UnicodeSet candidate = StaticUnicodeSets.unicodeSets.get(key);
        if (candidate == null) {
            return UnicodeSet.EMPTY;
        }
        return candidate;
    }
    
    public static Key chooseFrom(final String str, final Key key1) {
        return ParsingUtils.safeContains(get(key1), str) ? key1 : null;
    }
    
    public static Key chooseFrom(final String str, final Key key1, final Key key2) {
        return ParsingUtils.safeContains(get(key1), str) ? key1 : chooseFrom(str, key2);
    }
    
    public static Key chooseCurrency(final String str) {
        if (get(Key.DOLLAR_SIGN).contains(str)) {
            return Key.DOLLAR_SIGN;
        }
        if (get(Key.POUND_SIGN).contains(str)) {
            return Key.POUND_SIGN;
        }
        if (get(Key.RUPEE_SIGN).contains(str)) {
            return Key.RUPEE_SIGN;
        }
        if (get(Key.YEN_SIGN).contains(str)) {
            return Key.YEN_SIGN;
        }
        return null;
    }
    
    private static UnicodeSet computeUnion(final Key k1, final Key k2) {
        return new UnicodeSet().addAll(get(k1)).addAll(get(k2)).freeze();
    }
    
    private static UnicodeSet computeUnion(final Key k1, final Key k2, final Key k3) {
        return new UnicodeSet().addAll(get(k1)).addAll(get(k2)).addAll(get(k3)).freeze();
    }
    
    private static void saveSet(final Key key, final String unicodeSetPattern) {
        assert StaticUnicodeSets.unicodeSets.get(key) == null;
        StaticUnicodeSets.unicodeSets.put(key, new UnicodeSet(unicodeSetPattern).freeze());
    }
    
    static {
        (unicodeSets = new EnumMap<Key, UnicodeSet>(Key.class)).put(Key.DEFAULT_IGNORABLES, new UnicodeSet("[[:Zs:][\\u0009][:Bidi_Control:][:Variation_Selector:]]").freeze());
        StaticUnicodeSets.unicodeSets.put(Key.STRICT_IGNORABLES, new UnicodeSet("[[:Bidi_Control:]]").freeze());
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", ULocale.ROOT);
        rb.getAllItemsWithFallback("parse", new ParseDataSink());
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.COMMA);
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.STRICT_COMMA);
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.PERIOD);
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.STRICT_PERIOD);
        StaticUnicodeSets.unicodeSets.put(Key.OTHER_GROUPING_SEPARATORS, new UnicodeSet("['\u066c\u2018\u2019\uff07\\u0020\\u00A0\\u2000-\\u200A\\u202F\\u205F\\u3000]").freeze());
        StaticUnicodeSets.unicodeSets.put(Key.ALL_SEPARATORS, computeUnion(Key.COMMA, Key.PERIOD, Key.OTHER_GROUPING_SEPARATORS));
        StaticUnicodeSets.unicodeSets.put(Key.STRICT_ALL_SEPARATORS, computeUnion(Key.STRICT_COMMA, Key.STRICT_PERIOD, Key.OTHER_GROUPING_SEPARATORS));
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.MINUS_SIGN);
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.PLUS_SIGN);
        StaticUnicodeSets.unicodeSets.put(Key.PERCENT_SIGN, new UnicodeSet("[%\u066a]").freeze());
        StaticUnicodeSets.unicodeSets.put(Key.PERMILLE_SIGN, new UnicodeSet("[\u2030\u0609]").freeze());
        StaticUnicodeSets.unicodeSets.put(Key.INFINITY, new UnicodeSet("[\u221e]").freeze());
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.DOLLAR_SIGN);
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.POUND_SIGN);
        assert StaticUnicodeSets.unicodeSets.containsKey(Key.RUPEE_SIGN);
        StaticUnicodeSets.unicodeSets.put(Key.YEN_SIGN, new UnicodeSet("[¥\\uffe5]").freeze());
        StaticUnicodeSets.unicodeSets.put(Key.DIGITS, new UnicodeSet("[:digit:]").freeze());
        StaticUnicodeSets.unicodeSets.put(Key.DIGITS_OR_ALL_SEPARATORS, computeUnion(Key.DIGITS, Key.ALL_SEPARATORS));
        StaticUnicodeSets.unicodeSets.put(Key.DIGITS_OR_STRICT_ALL_SEPARATORS, computeUnion(Key.DIGITS, Key.STRICT_ALL_SEPARATORS));
    }
    
    public enum Key
    {
        DEFAULT_IGNORABLES, 
        STRICT_IGNORABLES, 
        COMMA, 
        PERIOD, 
        STRICT_COMMA, 
        STRICT_PERIOD, 
        OTHER_GROUPING_SEPARATORS, 
        ALL_SEPARATORS, 
        STRICT_ALL_SEPARATORS, 
        MINUS_SIGN, 
        PLUS_SIGN, 
        PERCENT_SIGN, 
        PERMILLE_SIGN, 
        INFINITY, 
        DOLLAR_SIGN, 
        POUND_SIGN, 
        RUPEE_SIGN, 
        YEN_SIGN, 
        DIGITS, 
        DIGITS_OR_ALL_SEPARATORS, 
        DIGITS_OR_STRICT_ALL_SEPARATORS;
    }
    
    static class ParseDataSink extends UResource.Sink
    {
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table contextsTable = value.getTable();
            for (int i = 0; contextsTable.getKeyAndValue(i, key, value); ++i) {
                if (!key.contentEquals("date")) {
                    assert key.contentEquals("general") || key.contentEquals("number");
                    final UResource.Table strictnessTable = value.getTable();
                    for (int j = 0; strictnessTable.getKeyAndValue(j, key, value); ++j) {
                        final boolean isLenient = key.contentEquals("lenient");
                        final UResource.Array array = value.getArray();
                        for (int k = 0; k < array.getSize(); ++k) {
                            array.getValue(k, value);
                            final String str = value.toString();
                            if (str.indexOf(46) != -1) {
                                saveSet(isLenient ? StaticUnicodeSets.Key.PERIOD : StaticUnicodeSets.Key.STRICT_PERIOD, str);
                            }
                            else if (str.indexOf(44) != -1) {
                                saveSet(isLenient ? StaticUnicodeSets.Key.COMMA : StaticUnicodeSets.Key.STRICT_COMMA, str);
                            }
                            else if (str.indexOf(43) != -1) {
                                saveSet(StaticUnicodeSets.Key.PLUS_SIGN, str);
                            }
                            else if (str.indexOf(8210) != -1) {
                                saveSet(StaticUnicodeSets.Key.MINUS_SIGN, str);
                            }
                            else if (str.indexOf(36) != -1) {
                                saveSet(StaticUnicodeSets.Key.DOLLAR_SIGN, str);
                            }
                            else if (str.indexOf(163) != -1) {
                                saveSet(StaticUnicodeSets.Key.POUND_SIGN, str);
                            }
                            else if (str.indexOf(8360) != -1) {
                                saveSet(StaticUnicodeSets.Key.RUPEE_SIGN, str);
                            }
                        }
                    }
                }
            }
        }
    }
}
