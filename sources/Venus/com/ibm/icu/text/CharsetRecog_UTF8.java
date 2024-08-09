/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import com.ibm.icu.text.CharsetRecognizer;

class CharsetRecog_UTF8
extends CharsetRecognizer {
    CharsetRecog_UTF8() {
    }

    @Override
    String getName() {
        return "UTF-8";
    }

    @Override
    CharsetMatch match(CharsetDetector charsetDetector) {
        boolean bl = false;
        int n = 0;
        int n2 = 0;
        byte[] byArray = charsetDetector.fRawInput;
        int n3 = 0;
        if (charsetDetector.fRawLength >= 3 && (byArray[0] & 0xFF) == 239 && (byArray[1] & 0xFF) == 187 && (byArray[2] & 0xFF) == 191) {
            bl = true;
        }
        block0: for (int i = 0; i < charsetDetector.fRawLength; ++i) {
            byte by = byArray[i];
            if ((by & 0x80) == 0) continue;
            if ((by & 0xE0) == 192) {
                n3 = 1;
            } else if ((by & 0xF0) == 224) {
                n3 = 2;
            } else if ((by & 0xF8) == 240) {
                n3 = 3;
            } else {
                ++n2;
                continue;
            }
            while (++i < charsetDetector.fRawLength) {
                by = byArray[i];
                if ((by & 0xC0) != 128) {
                    ++n2;
                    continue block0;
                }
                if (--n3 != 0) continue;
                ++n;
                continue block0;
            }
        }
        int n4 = 0;
        if (bl && n2 == 0) {
            n4 = 100;
        } else if (bl && n > n2 * 10) {
            n4 = 80;
        } else if (n > 3 && n2 == 0) {
            n4 = 100;
        } else if (n > 0 && n2 == 0) {
            n4 = 80;
        } else if (n == 0 && n2 == 0) {
            n4 = 15;
        } else if (n > n2 * 10) {
            n4 = 25;
        }
        return n4 == 0 ? null : new CharsetMatch(charsetDetector, this, n4);
    }
}

