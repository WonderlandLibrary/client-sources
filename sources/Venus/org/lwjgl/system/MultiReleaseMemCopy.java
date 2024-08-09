/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.libc.LibCString;

final class MultiReleaseMemCopy {
    private MultiReleaseMemCopy() {
    }

    static void copy(long l, long l2, long l3) {
        if (l3 < 384L) {
            if (((int)l & 7) == 0 && ((int)l2 & 7) == 0) {
                MemoryUtil.memCopyAligned(l, l2, (int)l3 & 0x1FF);
            } else {
                MemoryUtil.UNSAFE.copyMemory(l, l2, l3);
            }
        } else {
            LibCString.nmemcpy(l2, l, l3);
        }
    }
}

