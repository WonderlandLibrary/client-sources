/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import java.util.BitSet;

@Deprecated
final class CookieUtil {
    private static final BitSet VALID_COOKIE_VALUE_OCTETS = CookieUtil.validCookieValueOctets();
    private static final BitSet VALID_COOKIE_NAME_OCTETS = CookieUtil.validCookieNameOctets(VALID_COOKIE_VALUE_OCTETS);

    private static BitSet validCookieValueOctets() {
        BitSet bitSet = new BitSet(8);
        for (int i = 35; i < 127; ++i) {
            bitSet.set(i);
        }
        bitSet.set(34, true);
        bitSet.set(44, true);
        bitSet.set(59, true);
        bitSet.set(92, true);
        return bitSet;
    }

    private static BitSet validCookieNameOctets(BitSet bitSet) {
        BitSet bitSet2 = new BitSet(8);
        bitSet2.or(bitSet);
        bitSet2.set(40, true);
        bitSet2.set(41, true);
        bitSet2.set(60, true);
        bitSet2.set(62, true);
        bitSet2.set(64, true);
        bitSet2.set(58, true);
        bitSet2.set(47, true);
        bitSet2.set(91, true);
        bitSet2.set(93, true);
        bitSet2.set(63, true);
        bitSet2.set(61, true);
        bitSet2.set(123, true);
        bitSet2.set(125, true);
        bitSet2.set(32, true);
        bitSet2.set(9, true);
        return bitSet2;
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

    private CookieUtil() {
    }
}

