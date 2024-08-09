/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

final class ShadyPines {
    private ShadyPines() {
    }

    static int floor(double d) {
        int n = (int)d;
        return d < (double)n ? n - 1 : n;
    }

    static int floor(float f) {
        int n = (int)f;
        return f < (float)n ? n - 1 : n;
    }
}

