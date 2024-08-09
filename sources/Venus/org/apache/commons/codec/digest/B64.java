/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.digest;

import java.util.Random;

class B64 {
    static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    B64() {
    }

    static void b64from24bit(byte by, byte by2, byte by3, int n, StringBuilder stringBuilder) {
        int n2 = by << 16 & 0xFFFFFF | by2 << 8 & 0xFFFF | by3 & 0xFF;
        int n3 = n;
        while (n3-- > 0) {
            stringBuilder.append(B64T.charAt(n2 & 0x3F));
            n2 >>= 6;
        }
    }

    static String getRandomSalt(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= n; ++i) {
            stringBuilder.append(B64T.charAt(new Random().nextInt(64)));
        }
        return stringBuilder.toString();
    }
}

