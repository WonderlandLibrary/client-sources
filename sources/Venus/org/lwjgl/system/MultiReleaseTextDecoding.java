/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.nio.charset.StandardCharsets;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;

final class MultiReleaseTextDecoding {
    private MultiReleaseTextDecoding() {
    }

    static String decodeUTF8(long l, int n) {
        if (n <= 0) {
            return "";
        }
        if (Checks.DEBUG) {
            byte[] byArray = n <= MemoryUtil.ARRAY_TLC_SIZE ? MemoryUtil.ARRAY_TLC_BYTE.get() : new byte[n];
            MemoryUtil.memByteBuffer(l, n).get(byArray, 0, n);
            return new String(byArray, 0, n, StandardCharsets.UTF_8);
        }
        char[] cArray = n <= MemoryUtil.ARRAY_TLC_SIZE ? MemoryUtil.ARRAY_TLC_CHAR.get() : new char[n];
        int n2 = 0;
        int n3 = 0;
        while (n3 < n) {
            char c;
            int n4;
            if ((n4 = MemoryUtil.UNSAFE.getByte(null, l + (long)n3++) & 0xFF) < 128) {
                c = (char)n4;
            } else {
                int n5 = MemoryUtil.UNSAFE.getByte(null, l + (long)n3++) & 0x3F;
                if ((n4 & 0xE0) == 192) {
                    c = (char)((n4 & 0x1F) << 6 | n5);
                } else {
                    int n6 = MemoryUtil.UNSAFE.getByte(null, l + (long)n3++) & 0x3F;
                    if ((n4 & 0xF0) == 224) {
                        c = (char)((n4 & 0xF) << 12 | n5 << 6 | n6);
                    } else {
                        int n7 = MemoryUtil.UNSAFE.getByte(null, l + (long)n3++) & 0x3F;
                        int n8 = (n4 & 7) << 18 | n5 << 12 | n6 << 6 | n7;
                        if (n2 < n) {
                            cArray[n2++] = (char)((n8 >>> 10) + 55232);
                        }
                        c = (char)((n8 & 0x3FF) + 56320);
                    }
                }
            }
            if (n2 >= n) continue;
            cArray[n2++] = c;
        }
        return new String(cArray, 0, Math.min(n2, n));
    }
}

