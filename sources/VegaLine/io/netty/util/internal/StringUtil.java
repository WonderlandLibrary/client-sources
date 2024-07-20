/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
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
    private static final String[] BYTE2HEX_PAD;
    private static final String[] BYTE2HEX_NOPAD;
    private static final int CSV_NUMBER_ESCAPE_CHARACTERS = 7;
    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    private StringUtil() {
    }

    public static String substringAfter(String value, char delim) {
        int pos = value.indexOf(delim);
        if (pos >= 0) {
            return value.substring(pos + 1);
        }
        return null;
    }

    public static boolean commonSuffixOfLength(String s, String p, int len) {
        return s != null && p != null && len >= 0 && s.regionMatches(s.length() - len, p, p.length() - len, len);
    }

    public static String byteToHexStringPadded(int value) {
        return BYTE2HEX_PAD[value & 0xFF];
    }

    public static <T extends Appendable> T byteToHexStringPadded(T buf, int value) {
        try {
            buf.append(StringUtil.byteToHexStringPadded(value));
        } catch (IOException e) {
            PlatformDependent.throwException(e);
        }
        return buf;
    }

    public static String toHexStringPadded(byte[] src) {
        return StringUtil.toHexStringPadded(src, 0, src.length);
    }

    public static String toHexStringPadded(byte[] src, int offset, int length) {
        return StringUtil.toHexStringPadded(new StringBuilder(length << 1), src, offset, length).toString();
    }

    public static <T extends Appendable> T toHexStringPadded(T dst, byte[] src) {
        return StringUtil.toHexStringPadded(dst, src, 0, src.length);
    }

    public static <T extends Appendable> T toHexStringPadded(T dst, byte[] src, int offset, int length) {
        int end = offset + length;
        for (int i = offset; i < end; ++i) {
            StringUtil.byteToHexStringPadded(dst, src[i]);
        }
        return dst;
    }

    public static String byteToHexString(int value) {
        return BYTE2HEX_NOPAD[value & 0xFF];
    }

    public static <T extends Appendable> T byteToHexString(T buf, int value) {
        try {
            buf.append(StringUtil.byteToHexString(value));
        } catch (IOException e) {
            PlatformDependent.throwException(e);
        }
        return buf;
    }

    public static String toHexString(byte[] src) {
        return StringUtil.toHexString(src, 0, src.length);
    }

    public static String toHexString(byte[] src, int offset, int length) {
        return StringUtil.toHexString(new StringBuilder(length << 1), src, offset, length).toString();
    }

    public static <T extends Appendable> T toHexString(T dst, byte[] src) {
        return StringUtil.toHexString(dst, src, 0, src.length);
    }

    public static <T extends Appendable> T toHexString(T dst, byte[] src, int offset, int length) {
        int i;
        assert (length >= 0);
        if (length == 0) {
            return dst;
        }
        int end = offset + length;
        int endMinusOne = end - 1;
        for (i = offset; i < endMinusOne && src[i] == 0; ++i) {
        }
        StringUtil.byteToHexString(dst, src[i++]);
        int remaining = end - i;
        StringUtil.toHexStringPadded(dst, src, i, remaining);
        return dst;
    }

    public static String simpleClassName(Object o) {
        if (o == null) {
            return "null_object";
        }
        return StringUtil.simpleClassName(o.getClass());
    }

    public static String simpleClassName(Class<?> clazz) {
        String className = ObjectUtil.checkNotNull(clazz, "clazz").getName();
        int lastDotIdx = className.lastIndexOf(46);
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static CharSequence escapeCsv(CharSequence value) {
        CharSequence charSequence;
        int length = ObjectUtil.checkNotNull(value, "value").length();
        if (length == 0) {
            return value;
        }
        int last = length - 1;
        boolean quoted = StringUtil.isDoubleQuote(value.charAt(0)) && StringUtil.isDoubleQuote(value.charAt(last)) && length != 1;
        boolean foundSpecialCharacter = false;
        boolean escapedDoubleQuote = false;
        StringBuilder escaped = new StringBuilder(length + 7).append('\"');
        block4: for (int i = 0; i < length; ++i) {
            char current = value.charAt(i);
            switch (current) {
                case '\"': {
                    if (i == 0 || i == last) {
                        if (quoted) continue block4;
                        escaped.append('\"');
                    } else {
                        boolean isNextCharDoubleQuote = StringUtil.isDoubleQuote(value.charAt(i + 1));
                        if (StringUtil.isDoubleQuote(value.charAt(i - 1)) || isNextCharDoubleQuote && i + 1 != last) break;
                        escaped.append('\"');
                        escapedDoubleQuote = true;
                        break;
                    }
                }
                case '\n': 
                case '\r': 
                case ',': {
                    foundSpecialCharacter = true;
                }
            }
            escaped.append(current);
        }
        if (escapedDoubleQuote || foundSpecialCharacter && !quoted) {
            charSequence = escaped.append('\"');
            return charSequence;
        }
        charSequence = value;
        return charSequence;
    }

    public static CharSequence unescapeCsv(CharSequence value) {
        boolean quoted;
        int length = ObjectUtil.checkNotNull(value, "value").length();
        if (length == 0) {
            return value;
        }
        int last = length - 1;
        boolean bl = quoted = StringUtil.isDoubleQuote(value.charAt(0)) && StringUtil.isDoubleQuote(value.charAt(last)) && length != 1;
        if (!quoted) {
            StringUtil.validateCsvFormat(value);
            return value;
        }
        StringBuilder unescaped = InternalThreadLocalMap.get().stringBuilder();
        for (int i = 1; i < last; ++i) {
            char current = value.charAt(i);
            if (current == '\"') {
                if (StringUtil.isDoubleQuote(value.charAt(i + 1)) && i + 1 != last) {
                    ++i;
                } else {
                    throw StringUtil.newInvalidEscapedCsvFieldException(value, i);
                }
            }
            unescaped.append(current);
        }
        return unescaped.toString();
    }

    public static List<CharSequence> unescapeCsvFields(CharSequence value) {
        ArrayList<CharSequence> unescaped = new ArrayList<CharSequence>(2);
        StringBuilder current = InternalThreadLocalMap.get().stringBuilder();
        boolean quoted = false;
        int last = value.length() - 1;
        block8: for (int i = 0; i <= last; ++i) {
            char c = value.charAt(i);
            if (quoted) {
                switch (c) {
                    case '\"': {
                        char next;
                        if (i == last) {
                            unescaped.add(current.toString());
                            return unescaped;
                        }
                        if ((next = value.charAt(++i)) == '\"') {
                            current.append('\"');
                            break;
                        }
                        if (next == ',') {
                            quoted = false;
                            unescaped.add(current.toString());
                            current.setLength(0);
                            break;
                        }
                        throw StringUtil.newInvalidEscapedCsvFieldException(value, i - 1);
                    }
                    default: {
                        current.append(c);
                        break;
                    }
                }
                continue;
            }
            switch (c) {
                case ',': {
                    unescaped.add(current.toString());
                    current.setLength(0);
                    continue block8;
                }
                case '\"': {
                    if (current.length() == 0) {
                        quoted = true;
                        continue block8;
                    }
                }
                case '\n': 
                case '\r': {
                    throw StringUtil.newInvalidEscapedCsvFieldException(value, i);
                }
                default: {
                    current.append(c);
                }
            }
        }
        if (quoted) {
            throw StringUtil.newInvalidEscapedCsvFieldException(value, last);
        }
        unescaped.add(current.toString());
        return unescaped;
    }

    private static void validateCsvFormat(CharSequence value) {
        int length = value.length();
        for (int i = 0; i < length; ++i) {
            switch (value.charAt(i)) {
                case '\n': 
                case '\r': 
                case '\"': 
                case ',': {
                    throw StringUtil.newInvalidEscapedCsvFieldException(value, i);
                }
            }
        }
    }

    private static IllegalArgumentException newInvalidEscapedCsvFieldException(CharSequence value, int index) {
        return new IllegalArgumentException("invalid escaped CSV field: " + value + " index: " + index);
    }

    public static int length(String s) {
        return s == null ? 0 : s.length();
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static int indexOfNonWhiteSpace(CharSequence seq, int offset) {
        while (offset < seq.length()) {
            if (!Character.isWhitespace(seq.charAt(offset))) {
                return offset;
            }
            ++offset;
        }
        return -1;
    }

    public static boolean isSurrogate(char c) {
        return c >= '\ud800' && c <= '\udfff';
    }

    private static boolean isDoubleQuote(char c) {
        return c == '\"';
    }

    public static boolean endsWith(CharSequence s, char c) {
        int len = s.length();
        return len > 0 && s.charAt(len - 1) == c;
    }

    static {
        int i;
        NEWLINE = System.getProperty("line.separator");
        BYTE2HEX_PAD = new String[256];
        BYTE2HEX_NOPAD = new String[256];
        for (i = 0; i < 10; ++i) {
            StringUtil.BYTE2HEX_PAD[i] = "0" + i;
            StringUtil.BYTE2HEX_NOPAD[i] = String.valueOf(i);
        }
        while (i < 16) {
            char c = (char)(97 + i - 10);
            StringUtil.BYTE2HEX_PAD[i] = "0" + c;
            StringUtil.BYTE2HEX_NOPAD[i] = String.valueOf(c);
            ++i;
        }
        while (i < BYTE2HEX_PAD.length) {
            String str;
            StringUtil.BYTE2HEX_PAD[i] = str = Integer.toHexString(i);
            StringUtil.BYTE2HEX_NOPAD[i] = str;
            ++i;
        }
    }
}

