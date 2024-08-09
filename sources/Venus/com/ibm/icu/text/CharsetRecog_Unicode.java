/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.ibm.icu.text.CharsetRecognizer;

abstract class CharsetRecog_Unicode
extends CharsetRecognizer {
    CharsetRecog_Unicode() {
    }

    @Override
    abstract String getName();

    @Override
    abstract CharsetMatch match(CharsetDetector var1);

    static int codeUnit16FromBytes(byte by, byte by2) {
        return (by & 0xFF) << 8 | by2 & 0xFF;
    }

    static int adjustConfidence(int n, int n2) {
        if (n == 0) {
            n2 -= 10;
        } else if (n >= 32 && n <= 255 || n == 10) {
            n2 += 10;
        }
        if (n2 < 0) {
            n2 = 0;
        } else if (n2 > 100) {
            n2 = 100;
        }
        return n2;
    }

    static class CharsetRecog_UTF_32_LE
    extends CharsetRecog_UTF_32 {
        CharsetRecog_UTF_32_LE() {
        }

        @Override
        int getChar(byte[] byArray, int n) {
            return (byArray[n + 3] & 0xFF) << 24 | (byArray[n + 2] & 0xFF) << 16 | (byArray[n + 1] & 0xFF) << 8 | byArray[n + 0] & 0xFF;
        }

        @Override
        String getName() {
            return "UTF-32LE";
        }
    }

    static class CharsetRecog_UTF_32_BE
    extends CharsetRecog_UTF_32 {
        CharsetRecog_UTF_32_BE() {
        }

        @Override
        int getChar(byte[] byArray, int n) {
            return (byArray[n + 0] & 0xFF) << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
        }

        @Override
        String getName() {
            return "UTF-32BE";
        }
    }

    static abstract class CharsetRecog_UTF_32
    extends CharsetRecog_Unicode {
        CharsetRecog_UTF_32() {
        }

        abstract int getChar(byte[] var1, int var2);

        @Override
        abstract String getName();

        @Override
        CharsetMatch match(CharsetDetector charsetDetector) {
            byte[] byArray = charsetDetector.fRawInput;
            int n = charsetDetector.fRawLength / 4 * 4;
            int n2 = 0;
            int n3 = 0;
            boolean bl = false;
            int n4 = 0;
            if (n == 0) {
                return null;
            }
            if (this.getChar(byArray, 0) == 65279) {
                bl = true;
            }
            for (int i = 0; i < n; i += 4) {
                int n5 = this.getChar(byArray, i);
                if (n5 < 0 || n5 >= 0x10FFFF || n5 >= 55296 && n5 <= 57343) {
                    ++n3;
                    continue;
                }
                ++n2;
            }
            if (bl && n3 == 0) {
                n4 = 100;
            } else if (bl && n2 > n3 * 10) {
                n4 = 80;
            } else if (n2 > 3 && n3 == 0) {
                n4 = 100;
            } else if (n2 > 0 && n3 == 0) {
                n4 = 80;
            } else if (n2 > n3 * 10) {
                n4 = 25;
            }
            return n4 == 0 ? null : new CharsetMatch(charsetDetector, this, n4);
        }
    }

    static class CharsetRecog_UTF_16_LE
    extends CharsetRecog_Unicode {
        CharsetRecog_UTF_16_LE() {
        }

        @Override
        String getName() {
            return "UTF-16LE";
        }

        @Override
        CharsetMatch match(CharsetDetector charsetDetector) {
            byte[] byArray = charsetDetector.fRawInput;
            int n = 10;
            int n2 = Math.min(byArray.length, 30);
            for (int i = 0; i < n2 - 1; i += 2) {
                int n3 = CharsetRecog_UTF_16_LE.codeUnit16FromBytes(byArray[i + 1], byArray[i]);
                if (i == 0 && n3 == 65279) {
                    n = 100;
                    break;
                }
                if ((n = CharsetRecog_UTF_16_LE.adjustConfidence(n3, n)) == 0 || n == 100) break;
            }
            if (n2 < 4 && n < 100) {
                n = 0;
            }
            if (n > 0) {
                return new CharsetMatch(charsetDetector, this, n);
            }
            return null;
        }
    }

    static class CharsetRecog_UTF_16_BE
    extends CharsetRecog_Unicode {
        CharsetRecog_UTF_16_BE() {
        }

        @Override
        String getName() {
            return "UTF-16BE";
        }

        @Override
        CharsetMatch match(CharsetDetector charsetDetector) {
            byte[] byArray = charsetDetector.fRawInput;
            int n = 10;
            int n2 = Math.min(byArray.length, 30);
            for (int i = 0; i < n2 - 1; i += 2) {
                int n3 = CharsetRecog_UTF_16_BE.codeUnit16FromBytes(byArray[i], byArray[i + 1]);
                if (i == 0 && n3 == 65279) {
                    n = 100;
                    break;
                }
                if ((n = CharsetRecog_UTF_16_BE.adjustConfidence(n3, n)) == 0 || n == 100) break;
            }
            if (n2 < 4 && n < 100) {
                n = 0;
            }
            if (n > 0) {
                return new CharsetMatch(charsetDetector, this, n);
            }
            return null;
        }
    }
}

