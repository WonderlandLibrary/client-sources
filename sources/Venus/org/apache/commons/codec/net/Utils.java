/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;

class Utils {
    private static final int RADIX = 16;

    Utils() {
    }

    static int digit16(byte by) throws DecoderException {
        int n = Character.digit((char)by, 16);
        if (n == -1) {
            throw new DecoderException("Invalid URL encoding: not a valid digit (radix 16): " + by);
        }
        return n;
    }

    static char hexDigit(int n) {
        return Character.toUpperCase(Character.forDigit(n & 0xF, 16));
    }
}

