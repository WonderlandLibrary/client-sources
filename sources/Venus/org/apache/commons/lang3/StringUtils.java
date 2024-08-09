/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharSequenceUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.ObjectUtils;

public class StringUtils {
    public static final String SPACE = " ";
    public static final String EMPTY = "";
    public static final String LF = "\n";
    public static final String CR = "\r";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !StringUtils.isEmpty(charSequence);
    }

    public static boolean isAnyEmpty(CharSequence ... charSequenceArray) {
        if (ArrayUtils.isEmpty(charSequenceArray)) {
            return false;
        }
        for (CharSequence charSequence : charSequenceArray) {
            if (!StringUtils.isEmpty(charSequence)) continue;
            return false;
        }
        return true;
    }

    public static boolean isNoneEmpty(CharSequence ... charSequenceArray) {
        return !StringUtils.isAnyEmpty(charSequenceArray);
    }

    public static boolean isBlank(CharSequence charSequence) {
        int n;
        if (charSequence == null || (n = charSequence.length()) == 0) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (Character.isWhitespace(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !StringUtils.isBlank(charSequence);
    }

    public static boolean isAnyBlank(CharSequence ... charSequenceArray) {
        if (ArrayUtils.isEmpty(charSequenceArray)) {
            return false;
        }
        for (CharSequence charSequence : charSequenceArray) {
            if (!StringUtils.isBlank(charSequence)) continue;
            return false;
        }
        return true;
    }

    public static boolean isNoneBlank(CharSequence ... charSequenceArray) {
        return !StringUtils.isAnyBlank(charSequenceArray);
    }

    public static String trim(String string) {
        return string == null ? null : string.trim();
    }

    public static String trimToNull(String string) {
        String string2 = StringUtils.trim(string);
        return StringUtils.isEmpty(string2) ? null : string2;
    }

    public static String trimToEmpty(String string) {
        return string == null ? EMPTY : string.trim();
    }

    public static String truncate(String string, int n) {
        return StringUtils.truncate(string, 0, n);
    }

    public static String truncate(String string, int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("offset cannot be negative");
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("maxWith cannot be negative");
        }
        if (string == null) {
            return null;
        }
        if (n > string.length()) {
            return EMPTY;
        }
        if (string.length() > n2) {
            int n3 = n + n2 > string.length() ? string.length() : n + n2;
            return string.substring(n, n3);
        }
        return string.substring(n);
    }

    public static String strip(String string) {
        return StringUtils.strip(string, null);
    }

    public static String stripToNull(String string) {
        if (string == null) {
            return null;
        }
        return (string = StringUtils.strip(string, null)).isEmpty() ? null : string;
    }

    public static String stripToEmpty(String string) {
        return string == null ? EMPTY : StringUtils.strip(string, null);
    }

    public static String strip(String string, String string2) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }
        string = StringUtils.stripStart(string, string2);
        return StringUtils.stripEnd(string, string2);
    }

    public static String stripStart(String string, String string2) {
        int n;
        int n2;
        if (string == null || (n2 = string.length()) == 0) {
            return string;
        }
        if (string2 == null) {
            for (n = 0; n != n2 && Character.isWhitespace(string.charAt(n)); ++n) {
            }
        } else {
            if (string2.isEmpty()) {
                return string;
            }
            while (n != n2 && string2.indexOf(string.charAt(n)) != -1) {
                ++n;
            }
        }
        return string.substring(n);
    }

    public static String stripEnd(String string, String string2) {
        int n;
        if (string == null || (n = string.length()) == 0) {
            return string;
        }
        if (string2 == null) {
            while (n != 0 && Character.isWhitespace(string.charAt(n - 1))) {
                --n;
            }
        } else {
            if (string2.isEmpty()) {
                return string;
            }
            while (n != 0 && string2.indexOf(string.charAt(n - 1)) != -1) {
                --n;
            }
        }
        return string.substring(0, n);
    }

    public static String[] stripAll(String ... stringArray) {
        return StringUtils.stripAll(stringArray, null);
    }

    public static String[] stripAll(String[] stringArray, String string) {
        int n;
        if (stringArray == null || (n = stringArray.length) == 0) {
            return stringArray;
        }
        String[] stringArray2 = new String[n];
        for (int i = 0; i < n; ++i) {
            stringArray2[i] = StringUtils.strip(stringArray[i], string);
        }
        return stringArray2;
    }

    public static String stripAccents(String string) {
        if (string == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        StringBuilder stringBuilder = new StringBuilder(Normalizer.normalize(string, Normalizer.Form.NFD));
        StringUtils.convertRemainingAccentCharacters(stringBuilder);
        return pattern.matcher(stringBuilder).replaceAll(EMPTY);
    }

    private static void convertRemainingAccentCharacters(StringBuilder stringBuilder) {
        for (int i = 0; i < stringBuilder.length(); ++i) {
            if (stringBuilder.charAt(i) == '\u0141') {
                stringBuilder.deleteCharAt(i);
                stringBuilder.insert(i, 'L');
                continue;
            }
            if (stringBuilder.charAt(i) != '\u0142') continue;
            stringBuilder.deleteCharAt(i);
            stringBuilder.insert(i, 'l');
        }
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return false;
        }
        if (charSequence == null || charSequence2 == null) {
            return true;
        }
        if (charSequence.length() != charSequence2.length()) {
            return true;
        }
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return charSequence.equals(charSequence2);
        }
        return CharSequenceUtils.regionMatches(charSequence, false, 0, charSequence2, 0, charSequence.length());
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == charSequence2;
        }
        if (charSequence == charSequence2) {
            return false;
        }
        if (charSequence.length() != charSequence2.length()) {
            return true;
        }
        return CharSequenceUtils.regionMatches(charSequence, true, 0, charSequence2, 0, charSequence.length());
    }

    public static int compare(String string, String string2) {
        return StringUtils.compare(string, string2, true);
    }

    public static int compare(String string, String string2, boolean bl) {
        if (string == string2) {
            return 1;
        }
        if (string == null) {
            return bl ? -1 : 1;
        }
        if (string2 == null) {
            return bl ? 1 : -1;
        }
        return string.compareTo(string2);
    }

    public static int compareIgnoreCase(String string, String string2) {
        return StringUtils.compareIgnoreCase(string, string2, true);
    }

    public static int compareIgnoreCase(String string, String string2, boolean bl) {
        if (string == string2) {
            return 1;
        }
        if (string == null) {
            return bl ? -1 : 1;
        }
        if (string2 == null) {
            return bl ? 1 : -1;
        }
        return string.compareToIgnoreCase(string2);
    }

    public static boolean equalsAny(CharSequence charSequence, CharSequence ... charSequenceArray) {
        if (ArrayUtils.isNotEmpty(charSequenceArray)) {
            for (CharSequence charSequence2 : charSequenceArray) {
                if (!StringUtils.equals(charSequence, charSequence2)) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean equalsAnyIgnoreCase(CharSequence charSequence, CharSequence ... charSequenceArray) {
        if (ArrayUtils.isNotEmpty(charSequenceArray)) {
            for (CharSequence charSequence2 : charSequenceArray) {
                if (!StringUtils.equalsIgnoreCase(charSequence, charSequence2)) continue;
                return false;
            }
        }
        return true;
    }

    public static int indexOf(CharSequence charSequence, int n) {
        if (StringUtils.isEmpty(charSequence)) {
            return 1;
        }
        return CharSequenceUtils.indexOf(charSequence, n, 0);
    }

    public static int indexOf(CharSequence charSequence, int n, int n2) {
        if (StringUtils.isEmpty(charSequence)) {
            return 1;
        }
        return CharSequenceUtils.indexOf(charSequence, n, n2);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        return CharSequenceUtils.indexOf(charSequence, charSequence2, 0);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int n) {
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        return CharSequenceUtils.indexOf(charSequence, charSequence2, n);
    }

    public static int ordinalIndexOf(CharSequence charSequence, CharSequence charSequence2, int n) {
        return StringUtils.ordinalIndexOf(charSequence, charSequence2, n, false);
    }

    private static int ordinalIndexOf(CharSequence charSequence, CharSequence charSequence2, int n, boolean bl) {
        if (charSequence == null || charSequence2 == null || n <= 0) {
            return 1;
        }
        if (charSequence2.length() == 0) {
            return bl ? charSequence.length() : 0;
        }
        int n2 = 0;
        int n3 = bl ? charSequence.length() : -1;
        do {
            if ((n3 = bl ? CharSequenceUtils.lastIndexOf(charSequence, charSequence2, n3 - 1) : CharSequenceUtils.indexOf(charSequence, charSequence2, n3 + 1)) >= 0) continue;
            return n3;
        } while (++n2 < n);
        return n3;
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtils.indexOfIgnoreCase(charSequence, charSequence2, 0);
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int n) {
        int n2;
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        if (n < 0) {
            n = 0;
        }
        if (n > (n2 = charSequence.length() - charSequence2.length() + 1)) {
            return 1;
        }
        if (charSequence2.length() == 0) {
            return n;
        }
        for (int i = n; i < n2; ++i) {
            if (!CharSequenceUtils.regionMatches(charSequence, true, i, charSequence2, 0, charSequence2.length())) continue;
            return i;
        }
        return 1;
    }

    public static int lastIndexOf(CharSequence charSequence, int n) {
        if (StringUtils.isEmpty(charSequence)) {
            return 1;
        }
        return CharSequenceUtils.lastIndexOf(charSequence, n, charSequence.length());
    }

    public static int lastIndexOf(CharSequence charSequence, int n, int n2) {
        if (StringUtils.isEmpty(charSequence)) {
            return 1;
        }
        return CharSequenceUtils.lastIndexOf(charSequence, n, n2);
    }

    public static int lastIndexOf(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        return CharSequenceUtils.lastIndexOf(charSequence, charSequence2, charSequence.length());
    }

    public static int lastOrdinalIndexOf(CharSequence charSequence, CharSequence charSequence2, int n) {
        return StringUtils.ordinalIndexOf(charSequence, charSequence2, n, true);
    }

    public static int lastIndexOf(CharSequence charSequence, CharSequence charSequence2, int n) {
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        return CharSequenceUtils.lastIndexOf(charSequence, charSequence2, n);
    }

    public static int lastIndexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        return StringUtils.lastIndexOfIgnoreCase(charSequence, charSequence2, charSequence.length());
    }

    public static int lastIndexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int n) {
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        if (n > charSequence.length() - charSequence2.length()) {
            n = charSequence.length() - charSequence2.length();
        }
        if (n < 0) {
            return 1;
        }
        if (charSequence2.length() == 0) {
            return n;
        }
        for (int i = n; i >= 0; --i) {
            if (!CharSequenceUtils.regionMatches(charSequence, true, i, charSequence2, 0, charSequence2.length())) continue;
            return i;
        }
        return 1;
    }

    public static boolean contains(CharSequence charSequence, int n) {
        if (StringUtils.isEmpty(charSequence)) {
            return true;
        }
        return CharSequenceUtils.indexOf(charSequence, n, 0) >= 0;
    }

    public static boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return true;
        }
        return CharSequenceUtils.indexOf(charSequence, charSequence2, 0) >= 0;
    }

    public static boolean containsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return true;
        }
        int n = charSequence2.length();
        int n2 = charSequence.length() - n;
        for (int i = 0; i <= n2; ++i) {
            if (!CharSequenceUtils.regionMatches(charSequence, true, i, charSequence2, 0, n)) continue;
            return false;
        }
        return true;
    }

    public static boolean containsWhitespace(CharSequence charSequence) {
        if (StringUtils.isEmpty(charSequence)) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (!Character.isWhitespace(charSequence.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static int indexOfAny(CharSequence charSequence, char ... cArray) {
        if (StringUtils.isEmpty(charSequence) || ArrayUtils.isEmpty(cArray)) {
            return 1;
        }
        int n = charSequence.length();
        int n2 = n - 1;
        int n3 = cArray.length;
        int n4 = n3 - 1;
        for (int i = 0; i < n; ++i) {
            char c = charSequence.charAt(i);
            for (int j = 0; j < n3; ++j) {
                if (cArray[j] != c) continue;
                if (i < n2 && j < n4 && Character.isHighSurrogate(c)) {
                    if (cArray[j + 1] != charSequence.charAt(i + 1)) continue;
                    return i;
                }
                return i;
            }
        }
        return 1;
    }

    public static int indexOfAny(CharSequence charSequence, String string) {
        if (StringUtils.isEmpty(charSequence) || StringUtils.isEmpty(string)) {
            return 1;
        }
        return StringUtils.indexOfAny(charSequence, string.toCharArray());
    }

    public static boolean containsAny(CharSequence charSequence, char ... cArray) {
        if (StringUtils.isEmpty(charSequence) || ArrayUtils.isEmpty(cArray)) {
            return true;
        }
        int n = charSequence.length();
        int n2 = cArray.length;
        int n3 = n - 1;
        int n4 = n2 - 1;
        for (int i = 0; i < n; ++i) {
            char c = charSequence.charAt(i);
            for (int j = 0; j < n2; ++j) {
                if (cArray[j] != c) continue;
                if (Character.isHighSurrogate(c)) {
                    if (j == n4) {
                        return false;
                    }
                    if (i >= n3 || cArray[j + 1] != charSequence.charAt(i + 1)) continue;
                    return false;
                }
                return false;
            }
        }
        return true;
    }

    public static boolean containsAny(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2 == null) {
            return true;
        }
        return StringUtils.containsAny(charSequence, CharSequenceUtils.toCharArray(charSequence2));
    }

    public static boolean containsAny(CharSequence charSequence, CharSequence ... charSequenceArray) {
        if (StringUtils.isEmpty(charSequence) || ArrayUtils.isEmpty(charSequenceArray)) {
            return true;
        }
        for (CharSequence charSequence2 : charSequenceArray) {
            if (!StringUtils.contains(charSequence, charSequence2)) continue;
            return false;
        }
        return true;
    }

    public static int indexOfAnyBut(CharSequence charSequence, char ... cArray) {
        if (StringUtils.isEmpty(charSequence) || ArrayUtils.isEmpty(cArray)) {
            return 1;
        }
        int n = charSequence.length();
        int n2 = n - 1;
        int n3 = cArray.length;
        int n4 = n3 - 1;
        block0: for (int i = 0; i < n; ++i) {
            char c = charSequence.charAt(i);
            for (int j = 0; j < n3; ++j) {
                if (cArray[j] == c && (i >= n2 || j >= n4 || !Character.isHighSurrogate(c) || cArray[j + 1] == charSequence.charAt(i + 1))) continue block0;
            }
            return i;
        }
        return 1;
    }

    public static int indexOfAnyBut(CharSequence charSequence, CharSequence charSequence2) {
        if (StringUtils.isEmpty(charSequence) || StringUtils.isEmpty(charSequence2)) {
            return 1;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            boolean bl;
            char c = charSequence.charAt(i);
            boolean bl2 = bl = CharSequenceUtils.indexOf(charSequence2, c, 0) >= 0;
            if (i + 1 < n && Character.isHighSurrogate(c)) {
                char c2 = charSequence.charAt(i + 1);
                if (!bl || CharSequenceUtils.indexOf(charSequence2, c2, 0) >= 0) continue;
                return i;
            }
            if (bl) continue;
            return i;
        }
        return 1;
    }

    public static boolean containsOnly(CharSequence charSequence, char ... cArray) {
        if (cArray == null || charSequence == null) {
            return true;
        }
        if (charSequence.length() == 0) {
            return false;
        }
        if (cArray.length == 0) {
            return true;
        }
        return StringUtils.indexOfAnyBut(charSequence, cArray) == -1;
    }

    public static boolean containsOnly(CharSequence charSequence, String string) {
        if (charSequence == null || string == null) {
            return true;
        }
        return StringUtils.containsOnly(charSequence, string.toCharArray());
    }

    public static boolean containsNone(CharSequence charSequence, char ... cArray) {
        if (charSequence == null || cArray == null) {
            return false;
        }
        int n = charSequence.length();
        int n2 = n - 1;
        int n3 = cArray.length;
        int n4 = n3 - 1;
        for (int i = 0; i < n; ++i) {
            char c = charSequence.charAt(i);
            for (int j = 0; j < n3; ++j) {
                if (cArray[j] != c) continue;
                if (Character.isHighSurrogate(c)) {
                    if (j == n4) {
                        return true;
                    }
                    if (i >= n2 || cArray[j + 1] != charSequence.charAt(i + 1)) continue;
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean containsNone(CharSequence charSequence, String string) {
        if (charSequence == null || string == null) {
            return false;
        }
        return StringUtils.containsNone(charSequence, string.toCharArray());
    }

    public static int indexOfAny(CharSequence charSequence, CharSequence ... charSequenceArray) {
        if (charSequence == null || charSequenceArray == null) {
            return 1;
        }
        int n = charSequenceArray.length;
        int n2 = Integer.MAX_VALUE;
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            CharSequence charSequence2 = charSequenceArray[i];
            if (charSequence2 == null || (n3 = CharSequenceUtils.indexOf(charSequence, charSequence2, 0)) == -1 || n3 >= n2) continue;
            n2 = n3;
        }
        return n2 == Integer.MAX_VALUE ? -1 : n2;
    }

    public static int lastIndexOfAny(CharSequence charSequence, CharSequence ... charSequenceArray) {
        if (charSequence == null || charSequenceArray == null) {
            return 1;
        }
        int n = charSequenceArray.length;
        int n2 = -1;
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            CharSequence charSequence2 = charSequenceArray[i];
            if (charSequence2 == null || (n3 = CharSequenceUtils.lastIndexOf(charSequence, charSequence2, charSequence.length())) <= n2) continue;
            n2 = n3;
        }
        return n2;
    }

    public static String substring(String string, int n) {
        if (string == null) {
            return null;
        }
        if (n < 0) {
            n = string.length() + n;
        }
        if (n < 0) {
            n = 0;
        }
        if (n > string.length()) {
            return EMPTY;
        }
        return string.substring(n);
    }

    public static String substring(String string, int n, int n2) {
        if (string == null) {
            return null;
        }
        if (n2 < 0) {
            n2 = string.length() + n2;
        }
        if (n < 0) {
            n = string.length() + n;
        }
        if (n2 > string.length()) {
            n2 = string.length();
        }
        if (n > n2) {
            return EMPTY;
        }
        if (n < 0) {
            n = 0;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        return string.substring(n, n2);
    }

    public static String left(String string, int n) {
        if (string == null) {
            return null;
        }
        if (n < 0) {
            return EMPTY;
        }
        if (string.length() <= n) {
            return string;
        }
        return string.substring(0, n);
    }

    public static String right(String string, int n) {
        if (string == null) {
            return null;
        }
        if (n < 0) {
            return EMPTY;
        }
        if (string.length() <= n) {
            return string;
        }
        return string.substring(string.length() - n);
    }

    public static String mid(String string, int n, int n2) {
        if (string == null) {
            return null;
        }
        if (n2 < 0 || n > string.length()) {
            return EMPTY;
        }
        if (n < 0) {
            n = 0;
        }
        if (string.length() <= n + n2) {
            return string.substring(n);
        }
        return string.substring(n, n + n2);
    }

    public static String substringBefore(String string, String string2) {
        if (StringUtils.isEmpty(string) || string2 == null) {
            return string;
        }
        if (string2.isEmpty()) {
            return EMPTY;
        }
        int n = string.indexOf(string2);
        if (n == -1) {
            return string;
        }
        return string.substring(0, n);
    }

    public static String substringAfter(String string, String string2) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }
        if (string2 == null) {
            return EMPTY;
        }
        int n = string.indexOf(string2);
        if (n == -1) {
            return EMPTY;
        }
        return string.substring(n + string2.length());
    }

    public static String substringBeforeLast(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        int n = string.lastIndexOf(string2);
        if (n == -1) {
            return string;
        }
        return string.substring(0, n);
    }

    public static String substringAfterLast(String string, String string2) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }
        if (StringUtils.isEmpty(string2)) {
            return EMPTY;
        }
        int n = string.lastIndexOf(string2);
        if (n == -1 || n == string.length() - string2.length()) {
            return EMPTY;
        }
        return string.substring(n + string2.length());
    }

    public static String substringBetween(String string, String string2) {
        return StringUtils.substringBetween(string, string2, string2);
    }

    public static String substringBetween(String string, String string2, String string3) {
        int n;
        if (string == null || string2 == null || string3 == null) {
            return null;
        }
        int n2 = string.indexOf(string2);
        if (n2 != -1 && (n = string.indexOf(string3, n2 + string2.length())) != -1) {
            return string.substring(n2 + string2.length(), n);
        }
        return null;
    }

    public static String[] substringsBetween(String string, String string2, String string3) {
        int n;
        int n2;
        if (string == null || StringUtils.isEmpty(string2) || StringUtils.isEmpty(string3)) {
            return null;
        }
        int n3 = string.length();
        if (n3 == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        int n4 = string3.length();
        int n5 = string2.length();
        ArrayList<String> arrayList = new ArrayList<String>();
        int n6 = 0;
        while (n6 < n3 - n4 && (n2 = string.indexOf(string2, n6)) >= 0 && (n = string.indexOf(string3, n2 += n5)) >= 0) {
            arrayList.add(string.substring(n2, n));
            n6 = n + n4;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] split(String string) {
        return StringUtils.split(string, null, -1);
    }

    public static String[] split(String string, char c) {
        return StringUtils.splitWorker(string, c, false);
    }

    public static String[] split(String string, String string2) {
        return StringUtils.splitWorker(string, string2, -1, false);
    }

    public static String[] split(String string, String string2, int n) {
        return StringUtils.splitWorker(string, string2, n, false);
    }

    public static String[] splitByWholeSeparator(String string, String string2) {
        return StringUtils.splitByWholeSeparatorWorker(string, string2, -1, false);
    }

    public static String[] splitByWholeSeparator(String string, String string2, int n) {
        return StringUtils.splitByWholeSeparatorWorker(string, string2, n, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String string, String string2) {
        return StringUtils.splitByWholeSeparatorWorker(string, string2, -1, true);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String string, String string2, int n) {
        return StringUtils.splitByWholeSeparatorWorker(string, string2, n, true);
    }

    private static String[] splitByWholeSeparatorWorker(String string, String string2, int n, boolean bl) {
        if (string == null) {
            return null;
        }
        int n2 = string.length();
        if (n2 == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (string2 == null || EMPTY.equals(string2)) {
            return StringUtils.splitWorker(string, null, n, bl);
        }
        int n3 = string2.length();
        ArrayList<String> arrayList = new ArrayList<String>();
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        while (n6 < n2) {
            n6 = string.indexOf(string2, n5);
            if (n6 > -1) {
                if (n6 > n5) {
                    if (++n4 == n) {
                        n6 = n2;
                        arrayList.add(string.substring(n5));
                        continue;
                    }
                    arrayList.add(string.substring(n5, n6));
                    n5 = n6 + n3;
                    continue;
                }
                if (bl) {
                    if (++n4 == n) {
                        n6 = n2;
                        arrayList.add(string.substring(n5));
                    } else {
                        arrayList.add(EMPTY);
                    }
                }
                n5 = n6 + n3;
                continue;
            }
            arrayList.add(string.substring(n5));
            n6 = n2;
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitPreserveAllTokens(String string) {
        return StringUtils.splitWorker(string, null, -1, true);
    }

    public static String[] splitPreserveAllTokens(String string, char c) {
        return StringUtils.splitWorker(string, c, true);
    }

    private static String[] splitWorker(String string, char c, boolean bl) {
        if (string == null) {
            return null;
        }
        int n = string.length();
        if (n == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        int n3 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        while (n2 < n) {
            if (string.charAt(n2) == c) {
                if (bl2 || bl) {
                    arrayList.add(string.substring(n3, n2));
                    bl2 = false;
                    bl3 = true;
                }
                n3 = ++n2;
                continue;
            }
            bl3 = false;
            bl2 = true;
            ++n2;
        }
        if (bl2 || bl && bl3) {
            arrayList.add(string.substring(n3, n2));
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitPreserveAllTokens(String string, String string2) {
        return StringUtils.splitWorker(string, string2, -1, true);
    }

    public static String[] splitPreserveAllTokens(String string, String string2, int n) {
        return StringUtils.splitWorker(string, string2, n, true);
    }

    private static String[] splitWorker(String string, String string2, int n, boolean bl) {
        if (string == null) {
            return null;
        }
        int n2 = string.length();
        if (n2 == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        int n3 = 1;
        int n4 = 0;
        int n5 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        if (string2 == null) {
            while (n4 < n2) {
                if (Character.isWhitespace(string.charAt(n4))) {
                    if (bl2 || bl) {
                        bl3 = true;
                        if (n3++ == n) {
                            n4 = n2;
                            bl3 = false;
                        }
                        arrayList.add(string.substring(n5, n4));
                        bl2 = false;
                    }
                    n5 = ++n4;
                    continue;
                }
                bl3 = false;
                bl2 = true;
                ++n4;
            }
        } else if (string2.length() == 1) {
            char c = string2.charAt(0);
            while (n4 < n2) {
                if (string.charAt(n4) == c) {
                    if (bl2 || bl) {
                        bl3 = true;
                        if (n3++ == n) {
                            n4 = n2;
                            bl3 = false;
                        }
                        arrayList.add(string.substring(n5, n4));
                        bl2 = false;
                    }
                    n5 = ++n4;
                    continue;
                }
                bl3 = false;
                bl2 = true;
                ++n4;
            }
        } else {
            while (n4 < n2) {
                if (string2.indexOf(string.charAt(n4)) >= 0) {
                    if (bl2 || bl) {
                        bl3 = true;
                        if (n3++ == n) {
                            n4 = n2;
                            bl3 = false;
                        }
                        arrayList.add(string.substring(n5, n4));
                        bl2 = false;
                    }
                    n5 = ++n4;
                    continue;
                }
                bl3 = false;
                bl2 = true;
                ++n4;
            }
        }
        if (bl2 || bl && bl3) {
            arrayList.add(string.substring(n5, n4));
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static String[] splitByCharacterType(String string) {
        return StringUtils.splitByCharacterType(string, false);
    }

    public static String[] splitByCharacterTypeCamelCase(String string) {
        return StringUtils.splitByCharacterType(string, true);
    }

    private static String[] splitByCharacterType(String string, boolean bl) {
        if (string == null) {
            return null;
        }
        if (string.isEmpty()) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        char[] cArray = string.toCharArray();
        ArrayList<String> arrayList = new ArrayList<String>();
        int n = 0;
        int n2 = Character.getType(cArray[n]);
        for (int i = n + 1; i < cArray.length; ++i) {
            int n3 = Character.getType(cArray[i]);
            if (n3 == n2) continue;
            if (bl && n3 == 2 && n2 == 1) {
                int n4 = i - 1;
                if (n4 != n) {
                    arrayList.add(new String(cArray, n, n4 - n));
                    n = n4;
                }
            } else {
                arrayList.add(new String(cArray, n, i - n));
                n = i;
            }
            n2 = n3;
        }
        arrayList.add(new String(cArray, n, cArray.length - n));
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static <T> String join(T ... TArray) {
        return StringUtils.join((Object[])TArray, null);
    }

    public static String join(Object[] objectArray, char c) {
        if (objectArray == null) {
            return null;
        }
        return StringUtils.join(objectArray, c, 0, objectArray.length);
    }

    public static String join(long[] lArray, char c) {
        if (lArray == null) {
            return null;
        }
        return StringUtils.join(lArray, c, 0, lArray.length);
    }

    public static String join(int[] nArray, char c) {
        if (nArray == null) {
            return null;
        }
        return StringUtils.join(nArray, c, 0, nArray.length);
    }

    public static String join(short[] sArray, char c) {
        if (sArray == null) {
            return null;
        }
        return StringUtils.join(sArray, c, 0, sArray.length);
    }

    public static String join(byte[] byArray, char c) {
        if (byArray == null) {
            return null;
        }
        return StringUtils.join(byArray, c, 0, byArray.length);
    }

    public static String join(char[] cArray, char c) {
        if (cArray == null) {
            return null;
        }
        return StringUtils.join(cArray, c, 0, cArray.length);
    }

    public static String join(float[] fArray, char c) {
        if (fArray == null) {
            return null;
        }
        return StringUtils.join(fArray, c, 0, fArray.length);
    }

    public static String join(double[] dArray, char c) {
        if (dArray == null) {
            return null;
        }
        return StringUtils.join(dArray, c, 0, dArray.length);
    }

    public static String join(Object[] objectArray, char c, int n, int n2) {
        if (objectArray == null) {
            return null;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(c);
            }
            if (objectArray[i] == null) continue;
            stringBuilder.append(objectArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(long[] lArray, char c, int n, int n2) {
        if (lArray == null) {
            return null;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(c);
            }
            stringBuilder.append(lArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(int[] nArray, char c, int n, int n2) {
        if (nArray == null) {
            return null;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(c);
            }
            stringBuilder.append(nArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(byte[] byArray, char c, int n, int n2) {
        if (byArray == null) {
            return null;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(c);
            }
            stringBuilder.append(byArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(short[] sArray, char c, int n, int n2) {
        if (sArray == null) {
            return null;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(c);
            }
            stringBuilder.append(sArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(char[] cArray, char c, int n, int n2) {
        if (cArray == null) {
            return null;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(c);
            }
            stringBuilder.append(cArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(double[] dArray, char c, int n, int n2) {
        if (dArray == null) {
            return null;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(c);
            }
            stringBuilder.append(dArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(float[] fArray, char c, int n, int n2) {
        if (fArray == null) {
            return null;
        }
        int n3 = n2 - n;
        if (n3 <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(c);
            }
            stringBuilder.append(fArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(Object[] objectArray, String string) {
        if (objectArray == null) {
            return null;
        }
        return StringUtils.join(objectArray, string, 0, objectArray.length);
    }

    public static String join(Object[] objectArray, String string, int n, int n2) {
        int n3;
        if (objectArray == null) {
            return null;
        }
        if (string == null) {
            string = EMPTY;
        }
        if ((n3 = n2 - n) <= 0) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(n3 * 16);
        for (int i = n; i < n2; ++i) {
            if (i > n) {
                stringBuilder.append(string);
            }
            if (objectArray[i] == null) continue;
            stringBuilder.append(objectArray[i]);
        }
        return stringBuilder.toString();
    }

    public static String join(Iterator<?> iterator2, char c) {
        if (iterator2 == null) {
            return null;
        }
        if (!iterator2.hasNext()) {
            return EMPTY;
        }
        Object obj = iterator2.next();
        if (!iterator2.hasNext()) {
            String string = ObjectUtils.toString(obj);
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(256);
        if (obj != null) {
            stringBuilder.append(obj);
        }
        while (iterator2.hasNext()) {
            stringBuilder.append(c);
            Object obj2 = iterator2.next();
            if (obj2 == null) continue;
            stringBuilder.append(obj2);
        }
        return stringBuilder.toString();
    }

    public static String join(Iterator<?> iterator2, String string) {
        if (iterator2 == null) {
            return null;
        }
        if (!iterator2.hasNext()) {
            return EMPTY;
        }
        Object obj = iterator2.next();
        if (!iterator2.hasNext()) {
            String string2 = ObjectUtils.toString(obj);
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder(256);
        if (obj != null) {
            stringBuilder.append(obj);
        }
        while (iterator2.hasNext()) {
            Object obj2;
            if (string != null) {
                stringBuilder.append(string);
            }
            if ((obj2 = iterator2.next()) == null) continue;
            stringBuilder.append(obj2);
        }
        return stringBuilder.toString();
    }

    public static String join(Iterable<?> iterable, char c) {
        if (iterable == null) {
            return null;
        }
        return StringUtils.join(iterable.iterator(), c);
    }

    public static String join(Iterable<?> iterable, String string) {
        if (iterable == null) {
            return null;
        }
        return StringUtils.join(iterable.iterator(), string);
    }

    public static String joinWith(String string, Object ... objectArray) {
        if (objectArray == null) {
            throw new IllegalArgumentException("Object varargs must not be null");
        }
        String string2 = StringUtils.defaultString(string, EMPTY);
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Object> iterator2 = Arrays.asList(objectArray).iterator();
        while (iterator2.hasNext()) {
            String string3 = ObjectUtils.toString(iterator2.next());
            stringBuilder.append(string3);
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }

    public static String deleteWhitespace(String string) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }
        int n = string.length();
        char[] cArray = new char[n];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            if (Character.isWhitespace(string.charAt(i))) continue;
            cArray[n2++] = string.charAt(i);
        }
        if (n2 == n) {
            return string;
        }
        return new String(cArray, 0, n2);
    }

    public static String removeStart(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        if (string.startsWith(string2)) {
            return string.substring(string2.length());
        }
        return string;
    }

    public static String removeStartIgnoreCase(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        if (StringUtils.startsWithIgnoreCase(string, string2)) {
            return string.substring(string2.length());
        }
        return string;
    }

    public static String removeEnd(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        if (string.endsWith(string2)) {
            return string.substring(0, string.length() - string2.length());
        }
        return string;
    }

    public static String removeEndIgnoreCase(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        if (StringUtils.endsWithIgnoreCase(string, string2)) {
            return string.substring(0, string.length() - string2.length());
        }
        return string;
    }

    public static String remove(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        return StringUtils.replace(string, string2, EMPTY, -1);
    }

    public static String removeIgnoreCase(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        return StringUtils.replaceIgnoreCase(string, string2, EMPTY, -1);
    }

    public static String remove(String string, char c) {
        if (StringUtils.isEmpty(string) || string.indexOf(c) == -1) {
            return string;
        }
        char[] cArray = string.toCharArray();
        int n = 0;
        for (int i = 0; i < cArray.length; ++i) {
            if (cArray[i] == c) continue;
            cArray[n++] = cArray[i];
        }
        return new String(cArray, 0, n);
    }

    public static String removeAll(String string, String string2) {
        return StringUtils.replaceAll(string, string2, EMPTY);
    }

    public static String removeFirst(String string, String string2) {
        return StringUtils.replaceFirst(string, string2, EMPTY);
    }

    public static String replaceOnce(String string, String string2, String string3) {
        return StringUtils.replace(string, string2, string3, 1);
    }

    public static String replaceOnceIgnoreCase(String string, String string2, String string3) {
        return StringUtils.replaceIgnoreCase(string, string2, string3, 1);
    }

    public static String replacePattern(String string, String string2, String string3) {
        if (string == null || string2 == null || string3 == null) {
            return string;
        }
        return Pattern.compile(string2, 32).matcher(string).replaceAll(string3);
    }

    public static String removePattern(String string, String string2) {
        return StringUtils.replacePattern(string, string2, EMPTY);
    }

    public static String replaceAll(String string, String string2, String string3) {
        if (string == null || string2 == null || string3 == null) {
            return string;
        }
        return string.replaceAll(string2, string3);
    }

    public static String replaceFirst(String string, String string2, String string3) {
        if (string == null || string2 == null || string3 == null) {
            return string;
        }
        return string.replaceFirst(string2, string3);
    }

    public static String replace(String string, String string2, String string3) {
        return StringUtils.replace(string, string2, string3, -1);
    }

    public static String replaceIgnoreCase(String string, String string2, String string3) {
        return StringUtils.replaceIgnoreCase(string, string2, string3, -1);
    }

    public static String replace(String string, String string2, String string3, int n) {
        return StringUtils.replace(string, string2, string3, n, false);
    }

    private static String replace(String string, String string2, String string3, int n, boolean bl) {
        int n2;
        int n3;
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2) || string3 == null || n == 0) {
            return string;
        }
        String string4 = string;
        if (bl) {
            string4 = string.toLowerCase();
            string2 = string2.toLowerCase();
        }
        if ((n3 = string4.indexOf(string2, n2 = 0)) == -1) {
            return string;
        }
        int n4 = string2.length();
        int n5 = string3.length() - n4;
        int n6 = n5 = n5 < 0 ? 0 : n5;
        StringBuilder stringBuilder = new StringBuilder(string.length() + (n5 *= n < 0 ? 16 : (n > 64 ? 64 : n)));
        while (n3 != -1) {
            stringBuilder.append(string.substring(n2, n3)).append(string3);
            n2 = n3 + n4;
            if (--n == 0) break;
            n3 = string4.indexOf(string2, n2);
        }
        stringBuilder.append(string.substring(n2));
        return stringBuilder.toString();
    }

    public static String replaceIgnoreCase(String string, String string2, String string3, int n) {
        return StringUtils.replace(string, string2, string3, n, true);
    }

    public static String replaceEach(String string, String[] stringArray, String[] stringArray2) {
        return StringUtils.replaceEach(string, stringArray, stringArray2, false, 0);
    }

    public static String replaceEachRepeatedly(String string, String[] stringArray, String[] stringArray2) {
        int n = stringArray == null ? 0 : stringArray.length;
        return StringUtils.replaceEach(string, stringArray, stringArray2, true, n);
    }

    private static String replaceEach(String string, String[] stringArray, String[] stringArray2, boolean bl, int n) {
        int n2;
        int n3;
        if (string == null || string.isEmpty() || stringArray == null || stringArray.length == 0 || stringArray2 == null || stringArray2.length == 0) {
            return string;
        }
        if (n < 0) {
            throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
        }
        int n4 = stringArray.length;
        int n5 = stringArray2.length;
        if (n4 != n5) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: " + n4 + " vs " + n5);
        }
        boolean[] blArray = new boolean[n4];
        int n6 = -1;
        int n7 = -1;
        int n8 = -1;
        for (n3 = 0; n3 < n4; ++n3) {
            if (blArray[n3] || stringArray[n3] == null || stringArray[n3].isEmpty() || stringArray2[n3] == null) continue;
            n8 = string.indexOf(stringArray[n3]);
            if (n8 == -1) {
                blArray[n3] = true;
                continue;
            }
            if (n6 != -1 && n8 >= n6) continue;
            n6 = n8;
            n7 = n3;
        }
        if (n6 == -1) {
            return string;
        }
        n3 = 0;
        int n9 = 0;
        for (int i = 0; i < stringArray.length; ++i) {
            if (stringArray[i] == null || stringArray2[i] == null || (n2 = stringArray2[i].length() - stringArray[i].length()) <= 0) continue;
            n9 += 3 * n2;
        }
        n9 = Math.min(n9, string.length() / 5);
        StringBuilder stringBuilder = new StringBuilder(string.length() + n9);
        while (n6 != -1) {
            for (n2 = n3; n2 < n6; ++n2) {
                stringBuilder.append(string.charAt(n2));
            }
            stringBuilder.append(stringArray2[n7]);
            n3 = n6 + stringArray[n7].length();
            n6 = -1;
            n7 = -1;
            n8 = -1;
            for (n2 = 0; n2 < n4; ++n2) {
                if (blArray[n2] || stringArray[n2] == null || stringArray[n2].isEmpty() || stringArray2[n2] == null) continue;
                n8 = string.indexOf(stringArray[n2], n3);
                if (n8 == -1) {
                    blArray[n2] = true;
                    continue;
                }
                if (n6 != -1 && n8 >= n6) continue;
                n6 = n8;
                n7 = n2;
            }
        }
        n2 = string.length();
        for (int i = n3; i < n2; ++i) {
            stringBuilder.append(string.charAt(i));
        }
        String string2 = stringBuilder.toString();
        if (!bl) {
            return string2;
        }
        return StringUtils.replaceEach(string2, stringArray, stringArray2, bl, n - 1);
    }

    public static String replaceChars(String string, char c, char c2) {
        if (string == null) {
            return null;
        }
        return string.replace(c, c2);
    }

    public static String replaceChars(String string, String string2, String string3) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        if (string3 == null) {
            string3 = EMPTY;
        }
        boolean bl = false;
        int n = string3.length();
        int n2 = string.length();
        StringBuilder stringBuilder = new StringBuilder(n2);
        for (int i = 0; i < n2; ++i) {
            char c = string.charAt(i);
            int n3 = string2.indexOf(c);
            if (n3 >= 0) {
                bl = true;
                if (n3 >= n) continue;
                stringBuilder.append(string3.charAt(n3));
                continue;
            }
            stringBuilder.append(c);
        }
        if (bl) {
            return stringBuilder.toString();
        }
        return string;
    }

    public static String overlay(String string, String string2, int n, int n2) {
        if (string == null) {
            return null;
        }
        if (string2 == null) {
            string2 = EMPTY;
        }
        int n3 = string.length();
        if (n < 0) {
            n = 0;
        }
        if (n > n3) {
            n = n3;
        }
        if (n2 < 0) {
            n2 = 0;
        }
        if (n2 > n3) {
            n2 = n3;
        }
        if (n > n2) {
            int n4 = n;
            n = n2;
            n2 = n4;
        }
        return new StringBuilder(n3 + n - n2 + string2.length() + 1).append(string.substring(0, n)).append(string2).append(string.substring(n2)).toString();
    }

    public static String chomp(String string) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }
        if (string.length() == 1) {
            char c = string.charAt(0);
            if (c == '\r' || c == '\n') {
                return EMPTY;
            }
            return string;
        }
        int n = string.length() - 1;
        char c = string.charAt(n);
        if (c == '\n') {
            if (string.charAt(n - 1) == '\r') {
                --n;
            }
        } else if (c != '\r') {
            ++n;
        }
        return string.substring(0, n);
    }

    @Deprecated
    public static String chomp(String string, String string2) {
        return StringUtils.removeEnd(string, string2);
    }

    public static String chop(String string) {
        if (string == null) {
            return null;
        }
        int n = string.length();
        if (n < 2) {
            return EMPTY;
        }
        int n2 = n - 1;
        String string2 = string.substring(0, n2);
        char c = string.charAt(n2);
        if (c == '\n' && string2.charAt(n2 - 1) == '\r') {
            return string2.substring(0, n2 - 1);
        }
        return string2;
    }

    public static String repeat(String string, int n) {
        if (string == null) {
            return null;
        }
        if (n <= 0) {
            return EMPTY;
        }
        int n2 = string.length();
        if (n == 1 || n2 == 0) {
            return string;
        }
        if (n2 == 1 && n <= 8192) {
            return StringUtils.repeat(string.charAt(0), n);
        }
        int n3 = n2 * n;
        switch (n2) {
            case 1: {
                return StringUtils.repeat(string.charAt(0), n);
            }
            case 2: {
                char c = string.charAt(0);
                char c2 = string.charAt(1);
                char[] cArray = new char[n3];
                for (int i = n * 2 - 2; i >= 0; --i) {
                    cArray[i] = c;
                    cArray[i + 1] = c2;
                    --i;
                }
                return new String(cArray);
            }
        }
        StringBuilder stringBuilder = new StringBuilder(n3);
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static String repeat(String string, String string2, int n) {
        if (string == null || string2 == null) {
            return StringUtils.repeat(string, n);
        }
        String string3 = StringUtils.repeat(string + string2, n);
        return StringUtils.removeEnd(string3, string2);
    }

    public static String repeat(char c, int n) {
        if (n <= 0) {
            return EMPTY;
        }
        char[] cArray = new char[n];
        for (int i = n - 1; i >= 0; --i) {
            cArray[i] = c;
        }
        return new String(cArray);
    }

    public static String rightPad(String string, int n) {
        return StringUtils.rightPad(string, n, ' ');
    }

    public static String rightPad(String string, int n, char c) {
        if (string == null) {
            return null;
        }
        int n2 = n - string.length();
        if (n2 <= 0) {
            return string;
        }
        if (n2 > 8192) {
            return StringUtils.rightPad(string, n, String.valueOf(c));
        }
        return string.concat(StringUtils.repeat(c, n2));
    }

    public static String rightPad(String string, int n, String string2) {
        if (string == null) {
            return null;
        }
        if (StringUtils.isEmpty(string2)) {
            string2 = SPACE;
        }
        int n2 = string2.length();
        int n3 = string.length();
        int n4 = n - n3;
        if (n4 <= 0) {
            return string;
        }
        if (n2 == 1 && n4 <= 8192) {
            return StringUtils.rightPad(string, n, string2.charAt(0));
        }
        if (n4 == n2) {
            return string.concat(string2);
        }
        if (n4 < n2) {
            return string.concat(string2.substring(0, n4));
        }
        char[] cArray = new char[n4];
        char[] cArray2 = string2.toCharArray();
        for (int i = 0; i < n4; ++i) {
            cArray[i] = cArray2[i % n2];
        }
        return string.concat(new String(cArray));
    }

    public static String leftPad(String string, int n) {
        return StringUtils.leftPad(string, n, ' ');
    }

    public static String leftPad(String string, int n, char c) {
        if (string == null) {
            return null;
        }
        int n2 = n - string.length();
        if (n2 <= 0) {
            return string;
        }
        if (n2 > 8192) {
            return StringUtils.leftPad(string, n, String.valueOf(c));
        }
        return StringUtils.repeat(c, n2).concat(string);
    }

    public static String leftPad(String string, int n, String string2) {
        if (string == null) {
            return null;
        }
        if (StringUtils.isEmpty(string2)) {
            string2 = SPACE;
        }
        int n2 = string2.length();
        int n3 = string.length();
        int n4 = n - n3;
        if (n4 <= 0) {
            return string;
        }
        if (n2 == 1 && n4 <= 8192) {
            return StringUtils.leftPad(string, n, string2.charAt(0));
        }
        if (n4 == n2) {
            return string2.concat(string);
        }
        if (n4 < n2) {
            return string2.substring(0, n4).concat(string);
        }
        char[] cArray = new char[n4];
        char[] cArray2 = string2.toCharArray();
        for (int i = 0; i < n4; ++i) {
            cArray[i] = cArray2[i % n2];
        }
        return new String(cArray).concat(string);
    }

    public static int length(CharSequence charSequence) {
        return charSequence == null ? 0 : charSequence.length();
    }

    public static String center(String string, int n) {
        return StringUtils.center(string, n, ' ');
    }

    public static String center(String string, int n, char c) {
        if (string == null || n <= 0) {
            return string;
        }
        int n2 = string.length();
        int n3 = n - n2;
        if (n3 <= 0) {
            return string;
        }
        string = StringUtils.leftPad(string, n2 + n3 / 2, c);
        string = StringUtils.rightPad(string, n, c);
        return string;
    }

    public static String center(String string, int n, String string2) {
        int n2;
        int n3;
        if (string == null || n <= 0) {
            return string;
        }
        if (StringUtils.isEmpty(string2)) {
            string2 = SPACE;
        }
        if ((n3 = n - (n2 = string.length())) <= 0) {
            return string;
        }
        string = StringUtils.leftPad(string, n2 + n3 / 2, string2);
        string = StringUtils.rightPad(string, n, string2);
        return string;
    }

    public static String upperCase(String string) {
        if (string == null) {
            return null;
        }
        return string.toUpperCase();
    }

    public static String upperCase(String string, Locale locale) {
        if (string == null) {
            return null;
        }
        return string.toUpperCase(locale);
    }

    public static String lowerCase(String string) {
        if (string == null) {
            return null;
        }
        return string.toLowerCase();
    }

    public static String lowerCase(String string, Locale locale) {
        if (string == null) {
            return null;
        }
        return string.toLowerCase(locale);
    }

    public static String capitalize(String string) {
        char c;
        int n;
        if (string == null || (n = string.length()) == 0) {
            return string;
        }
        char c2 = string.charAt(0);
        if (c2 == (c = Character.toTitleCase(c2))) {
            return string;
        }
        char[] cArray = new char[n];
        cArray[0] = c;
        string.getChars(1, n, cArray, 1);
        return String.valueOf(cArray);
    }

    public static String uncapitalize(String string) {
        char c;
        int n;
        if (string == null || (n = string.length()) == 0) {
            return string;
        }
        char c2 = string.charAt(0);
        if (c2 == (c = Character.toLowerCase(c2))) {
            return string;
        }
        char[] cArray = new char[n];
        cArray[0] = c;
        string.getChars(1, n, cArray, 1);
        return String.valueOf(cArray);
    }

    public static String swapCase(String string) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }
        char[] cArray = string.toCharArray();
        for (int i = 0; i < cArray.length; ++i) {
            char c = cArray[i];
            if (Character.isUpperCase(c)) {
                cArray[i] = Character.toLowerCase(c);
                continue;
            }
            if (Character.isTitleCase(c)) {
                cArray[i] = Character.toLowerCase(c);
                continue;
            }
            if (!Character.isLowerCase(c)) continue;
            cArray[i] = Character.toUpperCase(c);
        }
        return new String(cArray);
    }

    public static int countMatches(CharSequence charSequence, CharSequence charSequence2) {
        if (StringUtils.isEmpty(charSequence) || StringUtils.isEmpty(charSequence2)) {
            return 1;
        }
        int n = 0;
        int n2 = 0;
        while ((n2 = CharSequenceUtils.indexOf(charSequence, charSequence2, n2)) != -1) {
            ++n;
            n2 += charSequence2.length();
        }
        return n;
    }

    public static int countMatches(CharSequence charSequence, char c) {
        if (StringUtils.isEmpty(charSequence)) {
            return 1;
        }
        int n = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            if (c != charSequence.charAt(i)) continue;
            ++n;
        }
        return n;
    }

    public static boolean isAlpha(CharSequence charSequence) {
        if (StringUtils.isEmpty(charSequence)) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isLetter(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isAlphaSpace(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isLetter(charSequence.charAt(i)) || charSequence.charAt(i) == ' ') continue;
            return true;
        }
        return false;
    }

    public static boolean isAlphanumeric(CharSequence charSequence) {
        if (StringUtils.isEmpty(charSequence)) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isLetterOrDigit(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isAlphanumericSpace(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isLetterOrDigit(charSequence.charAt(i)) || charSequence.charAt(i) == ' ') continue;
            return true;
        }
        return false;
    }

    public static boolean isAsciiPrintable(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (CharUtils.isAsciiPrintable(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isNumeric(CharSequence charSequence) {
        if (StringUtils.isEmpty(charSequence)) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isDigit(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isNumericSpace(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isDigit(charSequence.charAt(i)) || charSequence.charAt(i) == ' ') continue;
            return true;
        }
        return false;
    }

    public static boolean isWhitespace(CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isWhitespace(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isAllLowerCase(CharSequence charSequence) {
        if (charSequence == null || StringUtils.isEmpty(charSequence)) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isLowerCase(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean isAllUpperCase(CharSequence charSequence) {
        if (charSequence == null || StringUtils.isEmpty(charSequence)) {
            return true;
        }
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (Character.isUpperCase(charSequence.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static String defaultString(String string) {
        return string == null ? EMPTY : string;
    }

    public static String defaultString(String string, String string2) {
        return string == null ? string2 : string;
    }

    public static <T extends CharSequence> T defaultIfBlank(T t, T t2) {
        return StringUtils.isBlank(t) ? t2 : t;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T t, T t2) {
        return StringUtils.isEmpty(t) ? t2 : t;
    }

    public static String rotate(String string, int n) {
        if (string == null) {
            return null;
        }
        int n2 = string.length();
        if (n == 0 || n2 == 0 || n % n2 == 0) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(n2);
        int n3 = -(n % n2);
        stringBuilder.append(StringUtils.substring(string, n3));
        stringBuilder.append(StringUtils.substring(string, 0, n3));
        return stringBuilder.toString();
    }

    public static String reverse(String string) {
        if (string == null) {
            return null;
        }
        return new StringBuilder(string).reverse().toString();
    }

    public static String reverseDelimited(String string, char c) {
        if (string == null) {
            return null;
        }
        Object[] objectArray = StringUtils.split(string, c);
        ArrayUtils.reverse(objectArray);
        return StringUtils.join(objectArray, c);
    }

    public static String abbreviate(String string, int n) {
        return StringUtils.abbreviate(string, 0, n);
    }

    public static String abbreviate(String string, int n, int n2) {
        if (string == null) {
            return null;
        }
        if (n2 < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        }
        if (string.length() <= n2) {
            return string;
        }
        if (n > string.length()) {
            n = string.length();
        }
        if (string.length() - n < n2 - 3) {
            n = string.length() - (n2 - 3);
        }
        String string2 = "...";
        if (n <= 4) {
            return string.substring(0, n2 - 3) + "...";
        }
        if (n2 < 7) {
            throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
        }
        if (n + n2 - 3 < string.length()) {
            return "..." + StringUtils.abbreviate(string.substring(n), n2 - 3);
        }
        return "..." + string.substring(string.length() - (n2 - 3));
    }

    public static String abbreviateMiddle(String string, String string2, int n) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        if (n >= string.length() || n < string2.length() + 2) {
            return string;
        }
        int n2 = n - string2.length();
        int n3 = n2 / 2 + n2 % 2;
        int n4 = string.length() - n2 / 2;
        StringBuilder stringBuilder = new StringBuilder(n);
        stringBuilder.append(string.substring(0, n3));
        stringBuilder.append(string2);
        stringBuilder.append(string.substring(n4));
        return stringBuilder.toString();
    }

    public static String difference(String string, String string2) {
        if (string == null) {
            return string2;
        }
        if (string2 == null) {
            return string;
        }
        int n = StringUtils.indexOfDifference((CharSequence)string, (CharSequence)string2);
        if (n == -1) {
            return EMPTY;
        }
        return string2.substring(n);
    }

    public static int indexOfDifference(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        if (charSequence == charSequence2) {
            return 1;
        }
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        for (n = 0; n < charSequence.length() && n < charSequence2.length() && charSequence.charAt(n) == charSequence2.charAt(n); ++n) {
        }
        if (n < charSequence2.length() || n < charSequence.length()) {
            return n;
        }
        return 1;
    }

    public static int indexOfDifference(CharSequence ... charSequenceArray) {
        int n;
        if (charSequenceArray == null || charSequenceArray.length <= 1) {
            return 1;
        }
        boolean bl = false;
        boolean bl2 = true;
        int n2 = charSequenceArray.length;
        int n3 = Integer.MAX_VALUE;
        int n4 = 0;
        for (n = 0; n < n2; ++n) {
            if (charSequenceArray[n] == null) {
                bl = true;
                n3 = 0;
                continue;
            }
            bl2 = false;
            n3 = Math.min(charSequenceArray[n].length(), n3);
            n4 = Math.max(charSequenceArray[n].length(), n4);
        }
        if (bl2 || n4 == 0 && !bl) {
            return 1;
        }
        if (n3 == 0) {
            return 1;
        }
        n = -1;
        for (int i = 0; i < n3; ++i) {
            char c = charSequenceArray[0].charAt(i);
            for (int j = 1; j < n2; ++j) {
                if (charSequenceArray[j].charAt(i) == c) continue;
                n = i;
                break;
            }
            if (n != -1) break;
        }
        if (n == -1 && n3 != n4) {
            return n3;
        }
        return n;
    }

    public static String getCommonPrefix(String ... stringArray) {
        if (stringArray == null || stringArray.length == 0) {
            return EMPTY;
        }
        int n = StringUtils.indexOfDifference(stringArray);
        if (n == -1) {
            if (stringArray[0] == null) {
                return EMPTY;
            }
            return stringArray[0];
        }
        if (n == 0) {
            return EMPTY;
        }
        return stringArray[0].substring(0, n);
    }

    public static int getLevenshteinDistance(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        Object object;
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int n2 = charSequence.length();
        int n3 = charSequence2.length();
        if (n2 == 0) {
            return n3;
        }
        if (n3 == 0) {
            return n2;
        }
        if (n2 > n3) {
            object = charSequence;
            charSequence = charSequence2;
            charSequence2 = object;
            n2 = n3;
            n3 = charSequence2.length();
        }
        object = new int[n2 + 1];
        Object object2 = new int[n2 + 1];
        for (n = 0; n <= n2; ++n) {
            object[n] = n;
        }
        for (int i = 1; i <= n3; ++i) {
            char c = charSequence2.charAt(i - 1);
            object2[0] = i;
            for (n = 1; n <= n2; ++n) {
                boolean bl = charSequence.charAt(n - 1) != c;
                object2[n] = Math.min(Math.min(object2[n - 1] + 1, (int)(object[n] + true)), (int)(object[n - 1] + bl));
            }
            Object object3 = object;
            object = object2;
            object2 = object3;
        }
        return (int)object[n2];
    }

    public static int getLevenshteinDistance(CharSequence charSequence, CharSequence charSequence2, int n) {
        int n2;
        Object object;
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }
        int n3 = charSequence.length();
        int n4 = charSequence2.length();
        if (n3 == 0) {
            return n4 <= n ? n4 : -1;
        }
        if (n4 == 0) {
            return n3 <= n ? n3 : -1;
        }
        if (Math.abs(n3 - n4) > n) {
            return 1;
        }
        if (n3 > n4) {
            object = charSequence;
            charSequence = charSequence2;
            charSequence2 = object;
            n3 = n4;
            n4 = charSequence2.length();
        }
        object = new int[n3 + 1];
        Object object2 = new int[n3 + 1];
        int n5 = Math.min(n3, n) + 1;
        for (n2 = 0; n2 < n5; ++n2) {
            object[n2] = n2;
        }
        Arrays.fill((int[])object, n5, ((Object)object).length, Integer.MAX_VALUE);
        Arrays.fill(object2, Integer.MAX_VALUE);
        for (n2 = 1; n2 <= n4; ++n2) {
            int n6;
            char c = charSequence2.charAt(n2 - 1);
            object2[0] = n2;
            int n7 = Math.max(1, n2 - n);
            int n8 = n6 = n2 > Integer.MAX_VALUE - n ? n3 : Math.min(n3, n2 + n);
            if (n7 > n6) {
                return 1;
            }
            if (n7 > 1) {
                object2[n7 - 1] = Integer.MAX_VALUE;
            }
            for (int i = n7; i <= n6; ++i) {
                object2[i] = charSequence.charAt(i - 1) == c ? (int)object[i - 1] : 1 + Math.min(Math.min(object2[i - 1], (int)object[i]), (int)object[i - 1]);
            }
            Object object3 = object;
            object = object2;
            object2 = object3;
        }
        if (object[n3] <= n) {
            return (int)object[n3];
        }
        return 1;
    }

    public static double getJaroWinklerDistance(CharSequence charSequence, CharSequence charSequence2) {
        double d = 0.1;
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int[] nArray = StringUtils.matches(charSequence, charSequence2);
        double d2 = nArray[0];
        if (d2 == 0.0) {
            return 0.0;
        }
        double d3 = (d2 / (double)charSequence.length() + d2 / (double)charSequence2.length() + (d2 - (double)nArray[1]) / d2) / 3.0;
        double d4 = d3 < 0.7 ? d3 : d3 + Math.min(0.1, 1.0 / (double)nArray[3]) * (double)nArray[2] * (1.0 - d3);
        return (double)Math.round(d4 * 100.0) / 100.0;
    }

    private static int[] matches(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        int n2;
        CharSequence charSequence3;
        CharSequence charSequence4;
        if (charSequence.length() > charSequence2.length()) {
            charSequence4 = charSequence;
            charSequence3 = charSequence2;
        } else {
            charSequence4 = charSequence2;
            charSequence3 = charSequence;
        }
        int n3 = Math.max(charSequence4.length() / 2 - 1, 0);
        int[] nArray = new int[charSequence3.length()];
        Arrays.fill(nArray, -1);
        boolean[] blArray = new boolean[charSequence4.length()];
        int n4 = 0;
        block0: for (int i = 0; i < charSequence3.length(); ++i) {
            char c = charSequence3.charAt(i);
            n2 = Math.min(i + n3 + 1, charSequence4.length());
            for (n = Math.max(i - n3, 0); n < n2; ++n) {
                if (blArray[n] || c != charSequence4.charAt(n)) continue;
                nArray[i] = n;
                blArray[n] = true;
                ++n4;
                continue block0;
            }
        }
        char[] cArray = new char[n4];
        char[] cArray2 = new char[n4];
        n2 = 0;
        for (n = 0; n < charSequence3.length(); ++n) {
            if (nArray[n] == -1) continue;
            cArray[n2] = charSequence3.charAt(n);
            ++n2;
        }
        n2 = 0;
        for (n = 0; n < charSequence4.length(); ++n) {
            if (!blArray[n]) continue;
            cArray2[n2] = charSequence4.charAt(n);
            ++n2;
        }
        n = 0;
        for (n2 = 0; n2 < cArray.length; ++n2) {
            if (cArray[n2] == cArray2[n2]) continue;
            ++n;
        }
        n2 = 0;
        for (int i = 0; i < charSequence3.length() && charSequence.charAt(i) == charSequence2.charAt(i); ++i) {
            ++n2;
        }
        return new int[]{n4, n / 2, n2, charSequence4.length()};
    }

    public static int getFuzzyDistance(CharSequence charSequence, CharSequence charSequence2, Locale locale) {
        if (charSequence == null || charSequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (locale == null) {
            throw new IllegalArgumentException("Locale must not be null");
        }
        String string = charSequence.toString().toLowerCase(locale);
        String string2 = charSequence2.toString().toLowerCase(locale);
        int n = 0;
        int n2 = 0;
        int n3 = Integer.MIN_VALUE;
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            boolean bl = false;
            while (n2 < string.length() && !bl) {
                char c2 = string.charAt(n2);
                if (c == c2) {
                    ++n;
                    if (n3 + 1 == n2) {
                        n += 2;
                    }
                    n3 = n2;
                    bl = true;
                }
                ++n2;
            }
        }
        return n;
    }

    public static boolean startsWith(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtils.startsWith(charSequence, charSequence2, false);
    }

    public static boolean startsWithIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtils.startsWith(charSequence, charSequence2, true);
    }

    private static boolean startsWith(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == null && charSequence2 == null;
        }
        if (charSequence2.length() > charSequence.length()) {
            return true;
        }
        return CharSequenceUtils.regionMatches(charSequence, bl, 0, charSequence2, 0, charSequence2.length());
    }

    public static boolean startsWithAny(CharSequence charSequence, CharSequence ... charSequenceArray) {
        if (StringUtils.isEmpty(charSequence) || ArrayUtils.isEmpty(charSequenceArray)) {
            return true;
        }
        for (CharSequence charSequence2 : charSequenceArray) {
            if (!StringUtils.startsWith(charSequence, charSequence2)) continue;
            return false;
        }
        return true;
    }

    public static boolean endsWith(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtils.endsWith(charSequence, charSequence2, false);
    }

    public static boolean endsWithIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return StringUtils.endsWith(charSequence, charSequence2, true);
    }

    private static boolean endsWith(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == null && charSequence2 == null;
        }
        if (charSequence2.length() > charSequence.length()) {
            return true;
        }
        int n = charSequence.length() - charSequence2.length();
        return CharSequenceUtils.regionMatches(charSequence, bl, n, charSequence2, 0, charSequence2.length());
    }

    public static String normalizeSpace(String string) {
        if (StringUtils.isEmpty(string)) {
            return string;
        }
        int n = string.length();
        char[] cArray = new char[n];
        int n2 = 0;
        int n3 = 0;
        boolean bl = true;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            boolean bl2 = Character.isWhitespace(c);
            if (!bl2) {
                bl = false;
                cArray[n2++] = c == '\u00a0' ? 32 : (int)c;
                n3 = 0;
                continue;
            }
            if (n3 == 0 && !bl) {
                cArray[n2++] = SPACE.charAt(0);
            }
            ++n3;
        }
        if (bl) {
            return EMPTY;
        }
        return new String(cArray, 0, n2 - (n3 > 0 ? 1 : 0)).trim();
    }

    public static boolean endsWithAny(CharSequence charSequence, CharSequence ... charSequenceArray) {
        if (StringUtils.isEmpty(charSequence) || ArrayUtils.isEmpty(charSequenceArray)) {
            return true;
        }
        for (CharSequence charSequence2 : charSequenceArray) {
            if (!StringUtils.endsWith(charSequence, charSequence2)) continue;
            return false;
        }
        return true;
    }

    private static String appendIfMissing(String string, CharSequence charSequence, boolean bl, CharSequence ... charSequenceArray) {
        if (string == null || StringUtils.isEmpty(charSequence) || StringUtils.endsWith(string, charSequence, bl)) {
            return string;
        }
        if (charSequenceArray != null && charSequenceArray.length > 0) {
            for (CharSequence charSequence2 : charSequenceArray) {
                if (!StringUtils.endsWith(string, charSequence2, bl)) continue;
                return string;
            }
        }
        return string + charSequence.toString();
    }

    public static String appendIfMissing(String string, CharSequence charSequence, CharSequence ... charSequenceArray) {
        return StringUtils.appendIfMissing(string, charSequence, false, charSequenceArray);
    }

    public static String appendIfMissingIgnoreCase(String string, CharSequence charSequence, CharSequence ... charSequenceArray) {
        return StringUtils.appendIfMissing(string, charSequence, true, charSequenceArray);
    }

    private static String prependIfMissing(String string, CharSequence charSequence, boolean bl, CharSequence ... charSequenceArray) {
        if (string == null || StringUtils.isEmpty(charSequence) || StringUtils.startsWith(string, charSequence, bl)) {
            return string;
        }
        if (charSequenceArray != null && charSequenceArray.length > 0) {
            for (CharSequence charSequence2 : charSequenceArray) {
                if (!StringUtils.startsWith(string, charSequence2, bl)) continue;
                return string;
            }
        }
        return charSequence.toString() + string;
    }

    public static String prependIfMissing(String string, CharSequence charSequence, CharSequence ... charSequenceArray) {
        return StringUtils.prependIfMissing(string, charSequence, false, charSequenceArray);
    }

    public static String prependIfMissingIgnoreCase(String string, CharSequence charSequence, CharSequence ... charSequenceArray) {
        return StringUtils.prependIfMissing(string, charSequence, true, charSequenceArray);
    }

    @Deprecated
    public static String toString(byte[] byArray, String string) throws UnsupportedEncodingException {
        return string != null ? new String(byArray, string) : new String(byArray, Charset.defaultCharset());
    }

    public static String toEncodedString(byte[] byArray, Charset charset) {
        return new String(byArray, charset != null ? charset : Charset.defaultCharset());
    }

    public static String wrap(String string, char c) {
        if (StringUtils.isEmpty(string) || c == '\u0000') {
            return string;
        }
        return c + string + c;
    }

    public static String wrap(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        return string2.concat(string).concat(string2);
    }

    public static String wrapIfMissing(String string, char c) {
        if (StringUtils.isEmpty(string) || c == '\u0000') {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + 2);
        if (string.charAt(0) != c) {
            stringBuilder.append(c);
        }
        stringBuilder.append(string);
        if (string.charAt(string.length() - 1) != c) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String wrapIfMissing(String string, String string2) {
        if (StringUtils.isEmpty(string) || StringUtils.isEmpty(string2)) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + string2.length() + string2.length());
        if (!string.startsWith(string2)) {
            stringBuilder.append(string2);
        }
        stringBuilder.append(string);
        if (!string.endsWith(string2)) {
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }
}

