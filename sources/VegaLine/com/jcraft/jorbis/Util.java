/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jcraft.jorbis;

class Util {
    Util() {
    }

    static int ilog(int v) {
        int ret = 0;
        while (v != 0) {
            ++ret;
            v >>>= 1;
        }
        return ret;
    }

    static int ilog2(int v) {
        int ret = 0;
        while (v > 1) {
            ++ret;
            v >>>= 1;
        }
        return ret;
    }

    static int icount(int v) {
        int ret = 0;
        while (v != 0) {
            ret += v & 1;
            v >>>= 1;
        }
        return ret;
    }
}

