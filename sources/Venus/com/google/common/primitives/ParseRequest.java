/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.primitives;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class ParseRequest {
    final String rawValue;
    final int radix;

    private ParseRequest(String string, int n) {
        this.rawValue = string;
        this.radix = n;
    }

    static ParseRequest fromString(String string) {
        int n;
        String string2;
        if (string.length() == 0) {
            throw new NumberFormatException("empty string");
        }
        char c = string.charAt(0);
        if (string.startsWith("0x") || string.startsWith("0X")) {
            string2 = string.substring(2);
            n = 16;
        } else if (c == '#') {
            string2 = string.substring(1);
            n = 16;
        } else if (c == '0' && string.length() > 1) {
            string2 = string.substring(1);
            n = 8;
        } else {
            string2 = string;
            n = 10;
        }
        return new ParseRequest(string2, n);
    }
}

