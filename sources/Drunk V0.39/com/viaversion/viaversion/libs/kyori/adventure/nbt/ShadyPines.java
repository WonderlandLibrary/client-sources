/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

final class ShadyPines {
    private ShadyPines() {
    }

    static int floor(double dv) {
        int n;
        int iv = (int)dv;
        if (dv < (double)iv) {
            n = iv - 1;
            return n;
        }
        n = iv;
        return n;
    }

    static int floor(float fv) {
        int n;
        int iv = (int)fv;
        if (fv < (float)iv) {
            n = iv - 1;
            return n;
        }
        n = iv;
        return n;
    }
}

