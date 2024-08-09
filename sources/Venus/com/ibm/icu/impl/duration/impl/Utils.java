/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration.impl;

import java.util.Locale;

public class Utils {
    public static final Locale localeFromString(String string) {
        String string2 = string;
        String string3 = "";
        String string4 = "";
        int n = string2.indexOf("_");
        if (n != -1) {
            string3 = string2.substring(n + 1);
            string2 = string2.substring(0, n);
        }
        if ((n = string3.indexOf("_")) != -1) {
            string4 = string3.substring(n + 1);
            string3 = string3.substring(0, n);
        }
        return new Locale(string2, string3, string4);
    }

    public static String chineseNumber(long l, ChineseDigits chineseDigits) {
        int n;
        if (l < 0L) {
            l = -l;
        }
        if (l <= 10L) {
            if (l == 2L) {
                return String.valueOf(chineseDigits.liang);
            }
            return String.valueOf(chineseDigits.digits[(int)l]);
        }
        char[] cArray = new char[40];
        char[] cArray2 = String.valueOf(l).toCharArray();
        boolean bl = true;
        boolean bl2 = false;
        int n2 = cArray.length;
        int n3 = cArray2.length;
        int n4 = -1;
        int n5 = -1;
        while (--n3 >= 0) {
            if (n4 == -1) {
                if (n5 != -1) {
                    cArray[--n2] = chineseDigits.levels[n5];
                    bl = true;
                    bl2 = false;
                }
                ++n4;
            } else {
                cArray[--n2] = chineseDigits.units[n4++];
                if (n4 == 3) {
                    n4 = -1;
                    ++n5;
                }
            }
            n = cArray2[n3] - 48;
            if (n == 0) {
                if (n2 < cArray.length - 1 && n4 != 0) {
                    cArray[n2] = 42;
                }
                if (bl || bl2) {
                    cArray[--n2] = 42;
                    continue;
                }
                cArray[--n2] = chineseDigits.digits[0];
                bl = true;
                bl2 = n4 == 1;
                continue;
            }
            bl = false;
            cArray[--n2] = chineseDigits.digits[n];
        }
        if (l > 1000000L) {
            n3 = 1;
            n4 = cArray.length - 3;
            while (cArray[n4] != '0') {
                int n6 = n3 = n3 == 0 ? 1 : 0;
                if ((n4 -= 8) > n2) continue;
            }
            n4 = cArray.length - 7;
            do {
                if (cArray[n4] == chineseDigits.digits[0] && n3 == 0) {
                    cArray[n4] = 42;
                }
                int n7 = n3 = n3 == 0 ? 1 : 0;
            } while ((n4 -= 8) > n2);
            if (l >= 100000000L) {
                n4 = cArray.length - 8;
                do {
                    n5 = 1;
                    int n8 = Math.max(n2 - 1, n4 - 8);
                    for (n = n4 - 1; n > n8; --n) {
                        if (cArray[n] == '*') continue;
                        n5 = 0;
                        break;
                    }
                    if (n5 == 0) continue;
                    cArray[n4] = cArray[n4 + 1] != '*' && cArray[n4 + 1] != chineseDigits.digits[0] ? chineseDigits.digits[0] : 42;
                } while ((n4 -= 8) > n2);
            }
        }
        for (n3 = n2; n3 < cArray.length; ++n3) {
            if (cArray[n3] != chineseDigits.digits[2] || n3 < cArray.length - 1 && cArray[n3 + 1] == chineseDigits.units[0] || n3 > n2 && (cArray[n3 - 1] == chineseDigits.units[0] || cArray[n3 - 1] == chineseDigits.digits[0] || cArray[n3 - 1] == '*')) continue;
            cArray[n3] = chineseDigits.liang;
        }
        if (cArray[n2] == chineseDigits.digits[1] && (chineseDigits.ko || cArray[n2 + 1] == chineseDigits.units[0])) {
            ++n2;
        }
        n3 = n2;
        for (n4 = n2; n4 < cArray.length; ++n4) {
            if (cArray[n4] == '*') continue;
            cArray[n3++] = cArray[n4];
        }
        return new String(cArray, n2, n3 - n2);
    }

    public static class ChineseDigits {
        final char[] digits;
        final char[] units;
        final char[] levels;
        final char liang;
        final boolean ko;
        public static final ChineseDigits DEBUG = new ChineseDigits("0123456789s", "sbq", "WYZ", 'L', false);
        public static final ChineseDigits TRADITIONAL = new ChineseDigits("\u96f6\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341", "\u5341\u767e\u5343", "\u842c\u5104\u5146", '\u5169', false);
        public static final ChineseDigits SIMPLIFIED = new ChineseDigits("\u96f6\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341", "\u5341\u767e\u5343", "\u4e07\u4ebf\u5146", '\u4e24', false);
        public static final ChineseDigits KOREAN = new ChineseDigits("\uc601\uc77c\uc774\uc0bc\uc0ac\uc624\uc721\uce60\ud314\uad6c\uc2ed", "\uc2ed\ubc31\ucc9c", "\ub9cc\uc5b5?", '\uc774', true);

        ChineseDigits(String string, String string2, String string3, char c, boolean bl) {
            this.digits = string.toCharArray();
            this.units = string2.toCharArray();
            this.levels = string3.toCharArray();
            this.liang = c;
            this.ko = bl;
        }
    }
}

