/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.impl.locale.Extension;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class UnicodeLocaleExtension
extends Extension {
    public static final char SINGLETON = 'u';
    private static final SortedSet<String> EMPTY_SORTED_SET = new TreeSet<String>();
    private static final SortedMap<String, String> EMPTY_SORTED_MAP = new TreeMap<String, String>();
    private SortedSet<String> _attributes = EMPTY_SORTED_SET;
    private SortedMap<String, String> _keywords = EMPTY_SORTED_MAP;
    public static final UnicodeLocaleExtension CA_JAPANESE = new UnicodeLocaleExtension();
    public static final UnicodeLocaleExtension NU_THAI;

    private UnicodeLocaleExtension() {
        super('u');
    }

    UnicodeLocaleExtension(SortedSet<String> sortedSet, SortedMap<String, String> sortedMap) {
        this();
        if (sortedSet != null && sortedSet.size() > 0) {
            this._attributes = sortedSet;
        }
        if (sortedMap != null && sortedMap.size() > 0) {
            this._keywords = sortedMap;
        }
        if (this._attributes.size() > 0 || this._keywords.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String object : this._attributes) {
                stringBuilder.append("-").append(object);
            }
            for (Map.Entry entry : this._keywords.entrySet()) {
                String string = (String)entry.getKey();
                String string2 = (String)entry.getValue();
                stringBuilder.append("-").append(string);
                if (string2.length() <= 0) continue;
                stringBuilder.append("-").append(string2);
            }
            this._value = stringBuilder.substring(1);
        }
    }

    public Set<String> getUnicodeLocaleAttributes() {
        return Collections.unmodifiableSet(this._attributes);
    }

    public Set<String> getUnicodeLocaleKeys() {
        return Collections.unmodifiableSet(this._keywords.keySet());
    }

    public String getUnicodeLocaleType(String string) {
        return (String)this._keywords.get(string);
    }

    public static boolean isSingletonChar(char c) {
        return 'u' == AsciiUtil.toLower(c);
    }

    public static boolean isAttribute(String string) {
        return string.length() >= 3 && string.length() <= 8 && AsciiUtil.isAlphaNumericString(string);
    }

    public static boolean isKey(String string) {
        return string.length() == 2 && AsciiUtil.isAlphaNumeric(string.charAt(0)) && AsciiUtil.isAlpha(string.charAt(1));
    }

    public static boolean isTypeSubtag(String string) {
        return string.length() >= 3 && string.length() <= 8 && AsciiUtil.isAlphaNumericString(string);
    }

    public static boolean isType(String string) {
        int n = 0;
        boolean bl = false;
        while (true) {
            int n2;
            String string2;
            String string3 = string2 = (n2 = string.indexOf("-", n)) < 0 ? string.substring(n) : string.substring(n, n2);
            if (!UnicodeLocaleExtension.isTypeSubtag(string2)) {
                return true;
            }
            bl = true;
            if (n2 < 0) break;
            n = n2 + 1;
        }
        return bl && n < string.length();
    }

    static {
        UnicodeLocaleExtension.CA_JAPANESE._keywords = new TreeMap<String, String>();
        UnicodeLocaleExtension.CA_JAPANESE._keywords.put("ca", "japanese");
        UnicodeLocaleExtension.CA_JAPANESE._value = "ca-japanese";
        NU_THAI = new UnicodeLocaleExtension();
        UnicodeLocaleExtension.NU_THAI._keywords = new TreeMap<String, String>();
        UnicodeLocaleExtension.NU_THAI._keywords.put("nu", "thai");
        UnicodeLocaleExtension.NU_THAI._value = "nu-thai";
    }
}

