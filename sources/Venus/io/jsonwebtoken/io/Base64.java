/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.DecodingException;
import java.util.Arrays;

final class Base64 {
    private static final char[] BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    private static final char[] BASE64URL_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();
    private static final int[] BASE64_IALPHABET = new int[256];
    private static final int[] BASE64URL_IALPHABET = new int[256];
    private static final int IALPHABET_MAX_INDEX = BASE64_IALPHABET.length - 1;
    static final Base64 DEFAULT;
    static final Base64 URL_SAFE;
    private final boolean urlsafe;
    private final char[] ALPHABET;
    private final int[] IALPHABET;

    private Base64(boolean bl) {
        this.urlsafe = bl;
        this.ALPHABET = bl ? BASE64URL_ALPHABET : BASE64_ALPHABET;
        this.IALPHABET = bl ? BASE64URL_IALPHABET : BASE64_IALPHABET;
    }

    private String getName() {
        return this.urlsafe ? "base64url" : "base64";
    }

    private char[] encodeToChar(byte[] byArray, boolean bl) {
        int n;
        int n2 = n = byArray != null ? byArray.length : 0;
        if (n == 0) {
            return new char[0];
        }
        int n3 = n / 3 * 3;
        int n4 = n - n3;
        int n5 = (n - 1) / 3 + 1 << 2;
        int n6 = n5 + (bl ? (n5 - 1) / 76 << 1 : 0);
        int n7 = 0;
        if (n4 == 2) {
            n7 = 1;
        } else if (n4 == 1) {
            n7 = 2;
        }
        char[] cArray = new char[this.urlsafe ? n6 - n7 : n6];
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        while (n8 < n3) {
            int n11 = (byArray[n8++] & 0xFF) << 16 | (byArray[n8++] & 0xFF) << 8 | byArray[n8++] & 0xFF;
            cArray[n9++] = this.ALPHABET[n11 >>> 18 & 0x3F];
            cArray[n9++] = this.ALPHABET[n11 >>> 12 & 0x3F];
            cArray[n9++] = this.ALPHABET[n11 >>> 6 & 0x3F];
            cArray[n9++] = this.ALPHABET[n11 & 0x3F];
            if (!bl || ++n10 != 19 || n9 >= n6 - 2) continue;
            cArray[n9++] = 13;
            cArray[n9++] = 10;
            n10 = 0;
        }
        if (n4 > 0) {
            n8 = (byArray[n3] & 0xFF) << 10 | (n4 == 2 ? (byArray[n - 1] & 0xFF) << 2 : 0);
            cArray[n6 - 4] = this.ALPHABET[n8 >> 12];
            cArray[n6 - 3] = this.ALPHABET[n8 >>> 6 & 0x3F];
            if (n4 == 2) {
                cArray[n6 - 2] = this.ALPHABET[n8 & 0x3F];
            } else if (!this.urlsafe) {
                cArray[n6 - 2] = 61;
            }
            if (!this.urlsafe) {
                cArray[n6 - 1] = 61;
            }
        }
        return cArray;
    }

    private int ctoi(char c) {
        int n;
        int n2 = n = c > IALPHABET_MAX_INDEX ? -1 : this.IALPHABET[c];
        if (n < 0) {
            String string = "Illegal " + this.getName() + " character: '" + c + "'";
            throw new DecodingException(string);
        }
        return n;
    }

    byte[] decodeFast(CharSequence charSequence) throws DecodingException {
        int n;
        int n2;
        int n3 = n2 = charSequence != null ? charSequence.length() : 0;
        if (n2 == 0) {
            return new byte[0];
        }
        int n4 = n2 - 1;
        for (n = 0; n < n4 && this.IALPHABET[charSequence.charAt(n)] < 0; ++n) {
        }
        while (n4 > 0 && this.IALPHABET[charSequence.charAt(n4)] < 0) {
            --n4;
        }
        int n5 = charSequence.charAt(n4) == '=' ? (charSequence.charAt(n4 - 1) == '=' ? 2 : 1) : 0;
        int n6 = n4 - n + 1;
        int n7 = n2 > 76 ? (charSequence.charAt(76) == '\r' ? n6 / 78 : 0) << 1 : 0;
        int n8 = ((n6 - n7) * 6 >> 3) - n5;
        byte[] byArray = new byte[n8];
        int n9 = 0;
        int n10 = 0;
        int n11 = n8 / 3 * 3;
        while (n9 < n11) {
            int n12 = this.ctoi(charSequence.charAt(n++)) << 18 | this.ctoi(charSequence.charAt(n++)) << 12 | this.ctoi(charSequence.charAt(n++)) << 6 | this.ctoi(charSequence.charAt(n++));
            byArray[n9++] = (byte)(n12 >> 16);
            byArray[n9++] = (byte)(n12 >> 8);
            byArray[n9++] = (byte)n12;
            if (n7 <= 0 || ++n10 != 19) continue;
            n += 2;
            n10 = 0;
        }
        if (n9 < n8) {
            n10 = 0;
            n11 = 0;
            while (n <= n4 - n5) {
                n10 |= this.ctoi(charSequence.charAt(n++)) << 18 - n11 * 6;
                ++n11;
            }
            n11 = 16;
            while (n9 < n8) {
                byArray[n9++] = (byte)(n10 >> n11);
                n11 -= 8;
            }
        }
        return byArray;
    }

    String encodeToString(byte[] byArray, boolean bl) {
        return new String(this.encodeToChar(byArray, bl));
    }

    static {
        Arrays.fill(BASE64_IALPHABET, -1);
        System.arraycopy(BASE64_IALPHABET, 0, BASE64URL_IALPHABET, 0, BASE64_IALPHABET.length);
        int n = BASE64_ALPHABET.length;
        for (int i = 0; i < n; ++i) {
            Base64.BASE64_IALPHABET[Base64.BASE64_ALPHABET[i]] = i;
            Base64.BASE64URL_IALPHABET[Base64.BASE64URL_ALPHABET[i]] = i;
        }
        Base64.BASE64_IALPHABET[61] = 0;
        Base64.BASE64URL_IALPHABET[61] = 0;
        DEFAULT = new Base64(false);
        URL_SAFE = new Base64(true);
    }
}

