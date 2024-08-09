/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.ibm.icu.text.CharsetRecognizer;

abstract class CharsetRecog_2022
extends CharsetRecognizer {
    CharsetRecog_2022() {
    }

    int match(byte[] byArray, int n, byte[][] byArray2) {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        block0: for (int i = 0; i < n; ++i) {
            if (byArray[i] == 27) {
                block1: for (int j = 0; j < byArray2.length; ++j) {
                    byte[] byArray3 = byArray2[j];
                    if (n - i < byArray3.length) continue;
                    for (int k = 1; k < byArray3.length; ++k) {
                        if (byArray3[k] != byArray[i + k]) continue block1;
                    }
                    ++n2;
                    i += byArray3.length - 1;
                    continue block0;
                }
                ++n3;
            }
            if (byArray[i] != 14 && byArray[i] != 15) continue;
            ++n4;
        }
        if (n2 == 0) {
            return 1;
        }
        int n5 = (100 * n2 - 100 * n3) / (n2 + n3);
        if (n2 + n4 < 5) {
            n5 -= (5 - (n2 + n4)) * 10;
        }
        if (n5 < 0) {
            n5 = 0;
        }
        return n5;
    }

    static class CharsetRecog_2022CN
    extends CharsetRecog_2022 {
        private byte[][] escapeSequences = new byte[][]{{27, 36, 41, 65}, {27, 36, 41, 71}, {27, 36, 42, 72}, {27, 36, 41, 69}, {27, 36, 43, 73}, {27, 36, 43, 74}, {27, 36, 43, 75}, {27, 36, 43, 76}, {27, 36, 43, 77}, {27, 78}, {27, 79}};

        CharsetRecog_2022CN() {
        }

        @Override
        String getName() {
            return "ISO-2022-CN";
        }

        @Override
        CharsetMatch match(CharsetDetector charsetDetector) {
            int n = this.match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            return n == 0 ? null : new CharsetMatch(charsetDetector, this, n);
        }
    }

    static class CharsetRecog_2022KR
    extends CharsetRecog_2022 {
        private byte[][] escapeSequences = new byte[][]{{27, 36, 41, 67}};

        CharsetRecog_2022KR() {
        }

        @Override
        String getName() {
            return "ISO-2022-KR";
        }

        @Override
        CharsetMatch match(CharsetDetector charsetDetector) {
            int n = this.match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            return n == 0 ? null : new CharsetMatch(charsetDetector, this, n);
        }
    }

    static class CharsetRecog_2022JP
    extends CharsetRecog_2022 {
        private byte[][] escapeSequences = new byte[][]{{27, 36, 40, 67}, {27, 36, 40, 68}, {27, 36, 64}, {27, 36, 65}, {27, 36, 66}, {27, 38, 64}, {27, 40, 66}, {27, 40, 72}, {27, 40, 73}, {27, 40, 74}, {27, 46, 65}, {27, 46, 70}};

        CharsetRecog_2022JP() {
        }

        @Override
        String getName() {
            return "ISO-2022-JP";
        }

        @Override
        CharsetMatch match(CharsetDetector charsetDetector) {
            int n = this.match(charsetDetector.fInputBytes, charsetDetector.fInputLen, this.escapeSequences);
            return n == 0 ? null : new CharsetMatch(charsetDetector, this, n);
        }
    }
}

