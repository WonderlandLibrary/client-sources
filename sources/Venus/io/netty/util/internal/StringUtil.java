/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class StringUtil {
    public static final String EMPTY_STRING = "";
    public static final String NEWLINE;
    public static final char DOUBLE_QUOTE = '\"';
    public static final char COMMA = ',';
    public static final char LINE_FEED = '\n';
    public static final char CARRIAGE_RETURN = '\r';
    public static final char TAB = '\t';
    public static final char SPACE = ' ';
    private static final String[] BYTE2HEX_PAD;
    private static final String[] BYTE2HEX_NOPAD;
    private static final int CSV_NUMBER_ESCAPE_CHARACTERS = 7;
    private static final char PACKAGE_SEPARATOR_CHAR = '.';
    static final boolean $assertionsDisabled;

    private StringUtil() {
    }

    public static String substringAfter(String string, char c) {
        int n = string.indexOf(c);
        if (n >= 0) {
            return string.substring(n + 1);
        }
        return null;
    }

    public static boolean commonSuffixOfLength(String string, String string2, int n) {
        return string != null && string2 != null && n >= 0 && string.regionMatches(string.length() - n, string2, string2.length() - n, n);
    }

    public static String byteToHexStringPadded(int n) {
        return BYTE2HEX_PAD[n & 0xFF];
    }

    public static <T extends Appendable> T byteToHexStringPadded(T t, int n) {
        try {
            t.append(StringUtil.byteToHexStringPadded(n));
        } catch (IOException iOException) {
            PlatformDependent.throwException(iOException);
        }
        return t;
    }

    public static String toHexStringPadded(byte[] byArray) {
        return StringUtil.toHexStringPadded(byArray, 0, byArray.length);
    }

    public static String toHexStringPadded(byte[] byArray, int n, int n2) {
        return StringUtil.toHexStringPadded(new StringBuilder(n2 << 1), byArray, n, n2).toString();
    }

    public static <T extends Appendable> T toHexStringPadded(T t, byte[] byArray) {
        return StringUtil.toHexStringPadded(t, byArray, 0, byArray.length);
    }

    public static <T extends Appendable> T toHexStringPadded(T t, byte[] byArray, int n, int n2) {
        int n3 = n + n2;
        for (int i = n; i < n3; ++i) {
            StringUtil.byteToHexStringPadded(t, byArray[i]);
        }
        return t;
    }

    public static String byteToHexString(int n) {
        return BYTE2HEX_NOPAD[n & 0xFF];
    }

    public static <T extends Appendable> T byteToHexString(T t, int n) {
        try {
            t.append(StringUtil.byteToHexString(n));
        } catch (IOException iOException) {
            PlatformDependent.throwException(iOException);
        }
        return t;
    }

    public static String toHexString(byte[] byArray) {
        return StringUtil.toHexString(byArray, 0, byArray.length);
    }

    public static String toHexString(byte[] byArray, int n, int n2) {
        return StringUtil.toHexString(new StringBuilder(n2 << 1), byArray, n, n2).toString();
    }

    public static <T extends Appendable> T toHexString(T t, byte[] byArray) {
        return StringUtil.toHexString(t, byArray, 0, byArray.length);
    }

    public static <T extends Appendable> T toHexString(T t, byte[] byArray, int n, int n2) {
        int n3;
        if (!$assertionsDisabled && n2 < 0) {
            throw new AssertionError();
        }
        if (n2 == 0) {
            return t;
        }
        int n4 = n + n2;
        int n5 = n4 - 1;
        for (n3 = n; n3 < n5 && byArray[n3] == 0; ++n3) {
        }
        StringUtil.byteToHexString(t, byArray[n3++]);
        int n6 = n4 - n3;
        StringUtil.toHexStringPadded(t, byArray, n3, n6);
        return t;
    }

    public static int decodeHexNibble(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 55;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 87;
        }
        return 1;
    }

    public static byte decodeHexByte(CharSequence charSequence, int n) {
        int n2 = StringUtil.decodeHexNibble(charSequence.charAt(n));
        int n3 = StringUtil.decodeHexNibble(charSequence.charAt(n + 1));
        if (n2 == -1 || n3 == -1) {
            throw new IllegalArgumentException(String.format("invalid hex byte '%s' at index %d of '%s'", charSequence.subSequence(n, n + 2), n, charSequence));
        }
        return (byte)((n2 << 4) + n3);
    }

    public static byte[] decodeHexDump(CharSequence charSequence, int n, int n2) {
        if (n2 < 0 || (n2 & 1) != 0) {
            throw new IllegalArgumentException("length: " + n2);
        }
        if (n2 == 0) {
            return EmptyArrays.EMPTY_BYTES;
        }
        byte[] byArray = new byte[n2 >>> 1];
        for (int i = 0; i < n2; i += 2) {
            byArray[i >>> 1] = StringUtil.decodeHexByte(charSequence, n + i);
        }
        return byArray;
    }

    public static byte[] decodeHexDump(CharSequence charSequence) {
        return StringUtil.decodeHexDump(charSequence, 0, charSequence.length());
    }

    public static String simpleClassName(Object object) {
        if (object == null) {
            return "null_object";
        }
        return StringUtil.simpleClassName(object.getClass());
    }

    public static String simpleClassName(Class<?> clazz) {
        String string = ObjectUtil.checkNotNull(clazz, "clazz").getName();
        int n = string.lastIndexOf(46);
        if (n > -1) {
            return string.substring(n + 1);
        }
        return string;
    }

    public static CharSequence escapeCsv(CharSequence charSequence) {
        return StringUtil.escapeCsv(charSequence, false);
    }

    public static CharSequence escapeCsv(CharSequence charSequence, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4 = ObjectUtil.checkNotNull(charSequence, "value").length();
        if (bl) {
            n3 = StringUtil.indexOfFirstNonOwsChar(charSequence, n4);
            n2 = StringUtil.indexOfLastNonOwsChar(charSequence, n3, n4);
        } else {
            n3 = 0;
            n2 = n4 - 1;
        }
        if (n3 > n2) {
            return EMPTY_STRING;
        }
        int n5 = -1;
        boolean bl2 = false;
        if (StringUtil.isDoubleQuote(charSequence.charAt(n3))) {
            boolean bl3 = bl2 = StringUtil.isDoubleQuote(charSequence.charAt(n2)) && n2 > n3;
            if (bl2) {
                ++n3;
                --n2;
            } else {
                n5 = n3;
            }
        }
        if (n5 < 0) {
            int n6;
            if (bl2) {
                for (n6 = n3; n6 <= n2; ++n6) {
                    if (!StringUtil.isDoubleQuote(charSequence.charAt(n6))) continue;
                    if (n6 == n2 || !StringUtil.isDoubleQuote(charSequence.charAt(n6 + 1))) {
                        n5 = n6;
                        break;
                    }
                    ++n6;
                }
            } else {
                for (n6 = n3; n6 <= n2; ++n6) {
                    n = charSequence.charAt(n6);
                    if (n == 10 || n == 13 || n == 44) {
                        n5 = n6;
                        break;
                    }
                    if (!StringUtil.isDoubleQuote((char)n)) continue;
                    if (n6 == n2 || !StringUtil.isDoubleQuote(charSequence.charAt(n6 + 1))) {
                        n5 = n6;
                        break;
                    }
                    ++n6;
                }
            }
            if (n5 < 0) {
                return bl2 ? charSequence.subSequence(n3 - 1, n2 + 2) : charSequence.subSequence(n3, n2 + 1);
            }
        }
        StringBuilder stringBuilder = new StringBuilder(n2 - n3 + 1 + 7);
        stringBuilder.append('\"').append(charSequence, n3, n5);
        for (n = n5; n <= n2; ++n) {
            char c = charSequence.charAt(n);
            if (StringUtil.isDoubleQuote(c)) {
                stringBuilder.append('\"');
                if (n < n2 && StringUtil.isDoubleQuote(charSequence.charAt(n + 1))) {
                    ++n;
                }
            }
            stringBuilder.append(c);
        }
        return stringBuilder.append('\"');
    }

    public static CharSequence unescapeCsv(CharSequence charSequence) {
        boolean bl;
        int n = ObjectUtil.checkNotNull(charSequence, "value").length();
        if (n == 0) {
            return charSequence;
        }
        int n2 = n - 1;
        boolean bl2 = bl = StringUtil.isDoubleQuote(charSequence.charAt(0)) && StringUtil.isDoubleQuote(charSequence.charAt(n2)) && n != 1;
        if (!bl) {
            StringUtil.validateCsvFormat(charSequence);
            return charSequence;
        }
        StringBuilder stringBuilder = InternalThreadLocalMap.get().stringBuilder();
        for (int i = 1; i < n2; ++i) {
            char c = charSequence.charAt(i);
            if (c == '\"') {
                if (StringUtil.isDoubleQuote(charSequence.charAt(i + 1)) && i + 1 != n2) {
                    ++i;
                } else {
                    throw StringUtil.newInvalidEscapedCsvFieldException(charSequence, i);
                }
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static List<CharSequence> unescapeCsvFields(CharSequence charSequence) {
        ArrayList<CharSequence> arrayList = new ArrayList<CharSequence>(2);
        StringBuilder stringBuilder = InternalThreadLocalMap.get().stringBuilder();
        boolean bl = false;
        int n = charSequence.length() - 1;
        block8: for (int i = 0; i <= n; ++i) {
            char c = charSequence.charAt(i);
            if (bl) {
                switch (c) {
                    case '\"': {
                        char c2;
                        if (i == n) {
                            arrayList.add(stringBuilder.toString());
                            return arrayList;
                        }
                        if ((c2 = charSequence.charAt(++i)) == '\"') {
                            stringBuilder.append('\"');
                            break;
                        }
                        if (c2 == ',') {
                            bl = false;
                            arrayList.add(stringBuilder.toString());
                            stringBuilder.setLength(0);
                            break;
                        }
                        throw StringUtil.newInvalidEscapedCsvFieldException(charSequence, i - 1);
                    }
                    default: {
                        stringBuilder.append(c);
                        break;
                    }
                }
                continue;
            }
            switch (c) {
                case ',': {
                    arrayList.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    continue block8;
                }
                case '\"': {
                    if (stringBuilder.length() == 0) {
                        bl = true;
                        continue block8;
                    }
                }
                case '\n': 
                case '\r': {
                    throw StringUtil.newInvalidEscapedCsvFieldException(charSequence, i);
                }
                default: {
                    stringBuilder.append(c);
                }
            }
        }
        if (bl) {
            throw StringUtil.newInvalidEscapedCsvFieldException(charSequence, n);
        }
        arrayList.add(stringBuilder.toString());
        return arrayList;
    }

    private static void validateCsvFormat(CharSequence charSequence) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            switch (charSequence.charAt(i)) {
                case '\n': 
                case '\r': 
                case '\"': 
                case ',': {
                    throw StringUtil.newInvalidEscapedCsvFieldException(charSequence, i);
                }
            }
        }
    }

    private static IllegalArgumentException newInvalidEscapedCsvFieldException(CharSequence charSequence, int n) {
        return new IllegalArgumentException("invalid escaped CSV field: " + charSequence + " index: " + n);
    }

    public static int length(String string) {
        return string == null ? 0 : string.length();
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static int indexOfNonWhiteSpace(CharSequence charSequence, int n) {
        while (n < charSequence.length()) {
            if (!Character.isWhitespace(charSequence.charAt(n))) {
                return n;
            }
            ++n;
        }
        return 1;
    }

    public static boolean isSurrogate(char c) {
        return c >= '\ud800' && c <= '\udfff';
    }

    private static boolean isDoubleQuote(char c) {
        return c == '\"';
    }

    public static boolean endsWith(CharSequence charSequence, char c) {
        int n = charSequence.length();
        return n > 0 && charSequence.charAt(n - 1) == c;
    }

    public static CharSequence trimOws(CharSequence charSequence) {
        int n = charSequence.length();
        if (n == 0) {
            return charSequence;
        }
        int n2 = StringUtil.indexOfFirstNonOwsChar(charSequence, n);
        int n3 = StringUtil.indexOfLastNonOwsChar(charSequence, n2, n);
        return n2 == 0 && n3 == n - 1 ? charSequence : charSequence.subSequence(n2, n3 + 1);
    }

    private static int indexOfFirstNonOwsChar(CharSequence charSequence, int n) {
        int n2;
        for (n2 = 0; n2 < n && StringUtil.isOws(charSequence.charAt(n2)); ++n2) {
        }
        return n2;
    }

    private static int indexOfLastNonOwsChar(CharSequence charSequence, int n, int n2) {
        int n3;
        for (n3 = n2 - 1; n3 > n && StringUtil.isOws(charSequence.charAt(n3)); --n3) {
        }
        return n3;
    }

    private static boolean isOws(char c) {
        return c == ' ' || c == '\t';
    }

    static {
        $assertionsDisabled = !StringUtil.class.desiredAssertionStatus();
        NEWLINE = SystemPropertyUtil.get("line.separator", "\n");
        BYTE2HEX_PAD = new String[256];
        BYTE2HEX_NOPAD = new String[256];
        for (int i = 0; i < BYTE2HEX_PAD.length; ++i) {
            String string = Integer.toHexString(i);
            StringUtil.BYTE2HEX_PAD[i] = i > 15 ? string : '0' + string;
            StringUtil.BYTE2HEX_NOPAD[i] = string;
        }
    }
}

