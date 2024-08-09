/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.external.com.google.gdata.util.common.base;

import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.UnicodeEscaper;

public class PercentEscaper
extends UnicodeEscaper {
    public static final String SAFECHARS_URLENCODER = "-_.*";
    public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*'()@:$&,;=";
    public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*'()@:$,;/?:";
    private static final char[] URI_ESCAPED_SPACE = new char[]{'+'};
    private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    public PercentEscaper(String string, boolean bl) {
        if (string.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        if (bl && string.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        if (string.contains("%")) {
            throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
        }
        this.plusForSpace = bl;
        this.safeOctets = PercentEscaper.createSafeOctets(string);
    }

    /*
     * WARNING - void declaration
     */
    private static boolean[] createSafeOctets(String string) {
        void var6_10;
        int n;
        char[] cArray;
        int n2 = 122;
        char[] objectArray = cArray = string.toCharArray();
        int n3 = objectArray.length;
        for (n = 0; n < n3; ++n) {
            char c = objectArray[n];
            n2 = Math.max(c, n2);
        }
        boolean[] blArray = new boolean[n2 + 1];
        for (n3 = 48; n3 <= 57; ++n3) {
            blArray[n3] = true;
        }
        for (n3 = 65; n3 <= 90; ++n3) {
            blArray[n3] = true;
        }
        for (n3 = 97; n3 <= 122; ++n3) {
            blArray[n3] = true;
        }
        char[] cArray2 = cArray;
        n = cArray2.length;
        boolean bl = false;
        while (var6_10 < n) {
            char c = cArray2[var6_10];
            blArray[c] = true;
            ++var6_10;
        }
        return blArray;
    }

    @Override
    protected int nextEscapeIndex(CharSequence charSequence, int n, int n2) {
        char c;
        while (n < n2 && (c = charSequence.charAt(n)) < this.safeOctets.length && this.safeOctets[c]) {
            ++n;
        }
        return n;
    }

    @Override
    public String escape(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c < this.safeOctets.length && this.safeOctets[c]) continue;
            return this.escapeSlow(string, i);
        }
        return string;
    }

    @Override
    protected char[] escape(int n) {
        if (n < this.safeOctets.length && this.safeOctets[n]) {
            return null;
        }
        if (n == 32 && this.plusForSpace) {
            return URI_ESCAPED_SPACE;
        }
        if (n <= 127) {
            char[] cArray = new char[3];
            cArray[0] = 37;
            cArray[2] = UPPER_HEX_DIGITS[n & 0xF];
            cArray[1] = UPPER_HEX_DIGITS[n >>> 4];
            return cArray;
        }
        if (n <= 2047) {
            char[] cArray = new char[6];
            cArray[0] = 37;
            cArray[3] = 37;
            cArray[5] = UPPER_HEX_DIGITS[n & 0xF];
            cArray[4] = UPPER_HEX_DIGITS[8 | (n >>>= 4) & 3];
            cArray[2] = UPPER_HEX_DIGITS[(n >>>= 2) & 0xF];
            cArray[1] = UPPER_HEX_DIGITS[0xC | (n >>>= 4)];
            return cArray;
        }
        if (n <= 65535) {
            char[] cArray = new char[9];
            cArray[0] = 37;
            cArray[1] = 69;
            cArray[3] = 37;
            cArray[6] = 37;
            cArray[8] = UPPER_HEX_DIGITS[n & 0xF];
            cArray[7] = UPPER_HEX_DIGITS[8 | (n >>>= 4) & 3];
            cArray[5] = UPPER_HEX_DIGITS[(n >>>= 2) & 0xF];
            cArray[4] = UPPER_HEX_DIGITS[8 | (n >>>= 4) & 3];
            cArray[2] = UPPER_HEX_DIGITS[n >>>= 2];
            return cArray;
        }
        if (n <= 0x10FFFF) {
            char[] cArray = new char[12];
            cArray[0] = 37;
            cArray[1] = 70;
            cArray[3] = 37;
            cArray[6] = 37;
            cArray[9] = 37;
            cArray[11] = UPPER_HEX_DIGITS[n & 0xF];
            cArray[10] = UPPER_HEX_DIGITS[8 | (n >>>= 4) & 3];
            cArray[8] = UPPER_HEX_DIGITS[(n >>>= 2) & 0xF];
            cArray[7] = UPPER_HEX_DIGITS[8 | (n >>>= 4) & 3];
            cArray[5] = UPPER_HEX_DIGITS[(n >>>= 2) & 0xF];
            cArray[4] = UPPER_HEX_DIGITS[8 | (n >>>= 4) & 3];
            cArray[2] = UPPER_HEX_DIGITS[(n >>>= 2) & 7];
            return cArray;
        }
        throw new IllegalArgumentException("Invalid unicode character value " + n);
    }
}

