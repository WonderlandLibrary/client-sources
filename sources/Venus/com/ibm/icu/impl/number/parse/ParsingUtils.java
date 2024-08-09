/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number.parse;

import com.ibm.icu.text.UnicodeSet;

public class ParsingUtils {
    public static final int PARSE_FLAG_IGNORE_CASE = 1;
    public static final int PARSE_FLAG_MONETARY_SEPARATORS = 2;
    public static final int PARSE_FLAG_STRICT_SEPARATORS = 4;
    public static final int PARSE_FLAG_STRICT_GROUPING_SIZE = 8;
    public static final int PARSE_FLAG_INTEGER_ONLY = 16;
    public static final int PARSE_FLAG_GROUPING_DISABLED = 32;
    public static final int PARSE_FLAG_INCLUDE_UNPAIRED_AFFIXES = 128;
    public static final int PARSE_FLAG_USE_FULL_AFFIXES = 256;
    public static final int PARSE_FLAG_EXACT_AFFIX = 512;
    public static final int PARSE_FLAG_PLUS_SIGN_ALLOWED = 1024;
    public static final int PARSE_FLAG_FORCE_BIG_DECIMAL = 4096;
    public static final int PARSE_FLAG_NO_FOREIGN_CURRENCIES = 8192;
    public static final int PARSE_FLAG_ALLOW_INFINITE_RECURSION = 16384;
    public static final int PARSE_FLAG_STRICT_IGNORABLES = 32768;
    public static final int PARSE_FLAG_JAVA_COMPATIBILITY_IGNORABLES = 65536;

    public static void putLeadCodePoints(UnicodeSet unicodeSet, UnicodeSet unicodeSet2) {
        for (UnicodeSet.EntryRange object : unicodeSet.ranges()) {
            unicodeSet2.add(object.codepoint, object.codepointEnd);
        }
        for (String string : unicodeSet.strings()) {
            unicodeSet2.add(string.codePointAt(0));
        }
    }

    public static void putLeadCodePoint(String string, UnicodeSet unicodeSet) {
        if (!string.isEmpty()) {
            unicodeSet.add(string.codePointAt(0));
        }
    }

    public static boolean safeContains(UnicodeSet unicodeSet, CharSequence charSequence) {
        return charSequence.length() != 0 && unicodeSet.contains(charSequence);
    }
}

