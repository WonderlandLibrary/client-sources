/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.StringPrepParseException;
import com.ibm.icu.text.UTF16;

public final class Punycode {
    private static final int BASE = 36;
    private static final int TMIN = 1;
    private static final int TMAX = 26;
    private static final int SKEW = 38;
    private static final int DAMP = 700;
    private static final int INITIAL_BIAS = 72;
    private static final int INITIAL_N = 128;
    private static final char HYPHEN = '-';
    private static final char DELIMITER = '-';
    private static final int ZERO = 48;
    private static final int SMALL_A = 97;
    private static final int SMALL_Z = 122;
    private static final int CAPITAL_A = 65;
    private static final int CAPITAL_Z = 90;
    static final int[] basicToDigit = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    private static int adaptBias(int n, int n2, boolean bl) {
        n = bl ? (n /= 700) : (n /= 2);
        n += n / n2;
        int n3 = 0;
        while (n > 455) {
            n /= 35;
            n3 += 36;
        }
        return n3 + 36 * n / (n + 38);
    }

    private static char asciiCaseMap(char c, boolean bl) {
        if (bl) {
            if ('a' <= c && c <= 'z') {
                c = (char)(c - 32);
            }
        } else if ('A' <= c && c <= 'Z') {
            c = (char)(c + 32);
        }
        return c;
    }

    private static char digitToBasic(int n, boolean bl) {
        if (n < 26) {
            if (bl) {
                return (char)(65 + n);
            }
            return (char)(97 + n);
        }
        return (char)(22 + n);
    }

    public static StringBuilder encode(CharSequence charSequence, boolean[] blArray) throws StringPrepParseException {
        int n;
        int n2;
        int n3 = charSequence.length();
        int[] nArray = new int[n3];
        StringBuilder stringBuilder = new StringBuilder(n3);
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            char c;
            char c2 = charSequence.charAt(n2);
            if (Punycode.isBasic(c2)) {
                nArray[n4++] = 0;
                stringBuilder.append(blArray != null ? Punycode.asciiCaseMap(c2, blArray[n2]) : c2);
                continue;
            }
            n = (blArray != null && blArray[n2] ? 1 : 0) << 31;
            if (!UTF16.isSurrogate(c2)) {
                n |= c2;
            } else if (UTF16.isLeadSurrogate(c2) && n2 + 1 < n3 && UTF16.isTrailSurrogate(c = charSequence.charAt(n2 + 1))) {
                ++n2;
                n |= UCharacter.getCodePoint(c2, c);
            } else {
                throw new StringPrepParseException("Illegal char found", 1);
            }
            nArray[n4++] = n;
        }
        int n5 = stringBuilder.length();
        if (n5 > 0) {
            stringBuilder.append('-');
        }
        n = 128;
        int n6 = 0;
        int n7 = 72;
        int n8 = n5;
        while (n8 < n4) {
            int n9;
            int n10 = Integer.MAX_VALUE;
            for (n2 = 0; n2 < n4; ++n2) {
                n9 = nArray[n2] & Integer.MAX_VALUE;
                if (n > n9 || n9 >= n10) continue;
                n10 = n9;
            }
            if (n10 - n > (Integer.MAX_VALUE - n6) / (n8 + 1)) {
                throw new IllegalStateException("Internal program error");
            }
            n6 += (n10 - n) * (n8 + 1);
            n = n10;
            for (n2 = 0; n2 < n4; ++n2) {
                n9 = nArray[n2] & Integer.MAX_VALUE;
                if (n9 < n) {
                    ++n6;
                    continue;
                }
                if (n9 != n) continue;
                n9 = n6;
                int n11 = 36;
                while (true) {
                    int n12;
                    if ((n12 = n11 - n7) < 1) {
                        n12 = 1;
                    } else if (n11 >= n7 + 26) {
                        n12 = 26;
                    }
                    if (n9 < n12) break;
                    stringBuilder.append(Punycode.digitToBasic(n12 + (n9 - n12) % (36 - n12), false));
                    n9 = (n9 - n12) / (36 - n12);
                    n11 += 36;
                }
                stringBuilder.append(Punycode.digitToBasic(n9, nArray[n2] < 0));
                n7 = Punycode.adaptBias(n6, n8 + 1, n8 == n5);
                n6 = 0;
                ++n8;
            }
            ++n6;
            ++n;
        }
        return stringBuilder;
    }

    private static boolean isBasic(int n) {
        return n < 128;
    }

    private static boolean isBasicUpperCase(int n) {
        return 65 <= n && n >= 90;
    }

    private static boolean isSurrogate(int n) {
        return (n & 0xFFFFF800) == 55296;
    }

    public static StringBuilder decode(CharSequence charSequence, boolean[] blArray) throws StringPrepParseException {
        int n;
        int n2;
        int n3 = charSequence.length();
        StringBuilder stringBuilder = new StringBuilder(charSequence.length());
        int n4 = n3;
        while (n4 > 0 && charSequence.charAt(--n4) != '-') {
        }
        int n5 = n2 = n4;
        for (n4 = 0; n4 < n5; ++n4) {
            char c = charSequence.charAt(n4);
            if (!Punycode.isBasic(c)) {
                throw new StringPrepParseException("Illegal char found", 0);
            }
            stringBuilder.append(c);
            if (blArray == null || n4 >= blArray.length) continue;
            blArray[n4] = Punycode.isBasicUpperCase(c);
        }
        int n6 = 128;
        int n7 = 0;
        int n8 = 72;
        int n9 = 1000000000;
        int n10 = n = n5 > 0 ? n5 + 1 : 0;
        while (n < n3) {
            int n11;
            int n12 = n7;
            int n13 = 1;
            int n14 = 36;
            while (true) {
                int n15;
                if (n >= n3) {
                    throw new StringPrepParseException("Illegal char found", 1);
                }
                if ((n15 = basicToDigit[charSequence.charAt(n++) & 0xFF]) < 0) {
                    throw new StringPrepParseException("Invalid char found", 0);
                }
                if (n15 > (Integer.MAX_VALUE - n7) / n13) {
                    throw new StringPrepParseException("Illegal char found", 1);
                }
                n7 += n15 * n13;
                int n16 = n14 - n8;
                if (n16 < 1) {
                    n16 = 1;
                } else if (n14 >= n8 + 26) {
                    n16 = 26;
                }
                if (n15 < n16) break;
                if (n13 > Integer.MAX_VALUE / (36 - n16)) {
                    throw new StringPrepParseException("Illegal char found", 1);
                }
                n13 *= 36 - n16;
                n14 += 36;
            }
            n8 = Punycode.adaptBias(n7 - n12, ++n2, n12 == 0);
            if (n7 / n2 > Integer.MAX_VALUE - n6) {
                throw new StringPrepParseException("Illegal char found", 1);
            }
            n6 += n7 / n2;
            n7 %= n2;
            if (n6 > 0x10FFFF || Punycode.isSurrogate(n6)) {
                throw new StringPrepParseException("Illegal char found", 1);
            }
            int n17 = Character.charCount(n6);
            if (n7 <= n9) {
                n11 = n7;
                n9 = n17 > 1 ? n11 : ++n9;
            } else {
                n11 = stringBuilder.offsetByCodePoints(n9, n7 - n9);
            }
            if (blArray != null && stringBuilder.length() + n17 <= blArray.length) {
                if (n11 < stringBuilder.length()) {
                    System.arraycopy(blArray, n11, blArray, n11 + n17, stringBuilder.length() - n11);
                }
                blArray[n11] = Punycode.isBasicUpperCase(charSequence.charAt(n - 1));
                if (n17 == 2) {
                    blArray[n11 + 1] = false;
                }
            }
            if (n17 == 1) {
                stringBuilder.insert(n11, (char)n6);
            } else {
                stringBuilder.insert(n11, UTF16.getLeadSurrogate(n6));
                stringBuilder.insert(n11 + 1, UTF16.getTrailSurrogate(n6));
            }
            ++n7;
        }
        return stringBuilder;
    }
}

