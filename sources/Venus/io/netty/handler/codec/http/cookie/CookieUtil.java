/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.cookie;

import io.netty.util.internal.InternalThreadLocalMap;
import java.util.BitSet;

final class CookieUtil {
    private static final BitSet VALID_COOKIE_NAME_OCTETS = CookieUtil.validCookieNameOctets();
    private static final BitSet VALID_COOKIE_VALUE_OCTETS = CookieUtil.validCookieValueOctets();
    private static final BitSet VALID_COOKIE_ATTRIBUTE_VALUE_OCTETS = CookieUtil.validCookieAttributeValueOctets();

    private static BitSet validCookieNameOctets() {
        int[] nArray;
        BitSet bitSet = new BitSet();
        for (int i = 32; i < 127; ++i) {
            bitSet.set(i);
        }
        for (int n : nArray = new int[]{40, 41, 60, 62, 64, 44, 59, 58, 92, 34, 47, 91, 93, 63, 61, 123, 125, 32, 9}) {
            bitSet.set(n, true);
        }
        return bitSet;
    }

    private static BitSet validCookieValueOctets() {
        int n;
        BitSet bitSet = new BitSet();
        bitSet.set(33);
        for (n = 35; n <= 43; ++n) {
            bitSet.set(n);
        }
        for (n = 45; n <= 58; ++n) {
            bitSet.set(n);
        }
        for (n = 60; n <= 91; ++n) {
            bitSet.set(n);
        }
        for (n = 93; n <= 126; ++n) {
            bitSet.set(n);
        }
        return bitSet;
    }

    private static BitSet validCookieAttributeValueOctets() {
        BitSet bitSet = new BitSet();
        for (int i = 32; i < 127; ++i) {
            bitSet.set(i);
        }
        bitSet.set(59, true);
        return bitSet;
    }

    static StringBuilder stringBuilder() {
        return InternalThreadLocalMap.get().stringBuilder();
    }

    static String stripTrailingSeparatorOrNull(StringBuilder stringBuilder) {
        return stringBuilder.length() == 0 ? null : CookieUtil.stripTrailingSeparator(stringBuilder);
    }

    static String stripTrailingSeparator(StringBuilder stringBuilder) {
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }

    static void add(StringBuilder stringBuilder, String string, long l) {
        stringBuilder.append(string);
        stringBuilder.append('=');
        stringBuilder.append(l);
        stringBuilder.append(';');
        stringBuilder.append(' ');
    }

    static void add(StringBuilder stringBuilder, String string, String string2) {
        stringBuilder.append(string);
        stringBuilder.append('=');
        stringBuilder.append(string2);
        stringBuilder.append(';');
        stringBuilder.append(' ');
    }

    static void add(StringBuilder stringBuilder, String string) {
        stringBuilder.append(string);
        stringBuilder.append(';');
        stringBuilder.append(' ');
    }

    static void addQuoted(StringBuilder stringBuilder, String string, String string2) {
        if (string2 == null) {
            string2 = "";
        }
        stringBuilder.append(string);
        stringBuilder.append('=');
        stringBuilder.append('\"');
        stringBuilder.append(string2);
        stringBuilder.append('\"');
        stringBuilder.append(';');
        stringBuilder.append(' ');
    }

    static int firstInvalidCookieNameOctet(CharSequence charSequence) {
        return CookieUtil.firstInvalidOctet(charSequence, VALID_COOKIE_NAME_OCTETS);
    }

    static int firstInvalidCookieValueOctet(CharSequence charSequence) {
        return CookieUtil.firstInvalidOctet(charSequence, VALID_COOKIE_VALUE_OCTETS);
    }

    static int firstInvalidOctet(CharSequence charSequence, BitSet bitSet) {
        for (int i = 0; i < charSequence.length(); ++i) {
            char c = charSequence.charAt(i);
            if (bitSet.get(c)) continue;
            return i;
        }
        return 1;
    }

    static CharSequence unwrapValue(CharSequence charSequence) {
        int n = charSequence.length();
        if (n > 0 && charSequence.charAt(0) == '\"') {
            if (n >= 2 && charSequence.charAt(n - 1) == '\"') {
                return n == 2 ? "" : charSequence.subSequence(1, n - 1);
            }
            return null;
        }
        return charSequence;
    }

    static String validateAttributeValue(String string, String string2) {
        if (string2 == null) {
            return null;
        }
        if ((string2 = string2.trim()).isEmpty()) {
            return null;
        }
        int n = CookieUtil.firstInvalidOctet(string2, VALID_COOKIE_ATTRIBUTE_VALUE_OCTETS);
        if (n != -1) {
            throw new IllegalArgumentException(string + " contains the prohibited characters: " + string2.charAt(n));
        }
        return string2;
    }

    private CookieUtil() {
    }
}

