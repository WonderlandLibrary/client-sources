/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.util.ByteArrayWrapper;

public final class RawCollationKey
extends ByteArrayWrapper {
    public RawCollationKey() {
    }

    public RawCollationKey(int n) {
        this.bytes = new byte[n];
    }

    public RawCollationKey(byte[] byArray) {
        this.bytes = byArray;
    }

    public RawCollationKey(byte[] byArray, int n) {
        super(byArray, n);
    }

    @Override
    public int compareTo(RawCollationKey rawCollationKey) {
        int n = super.compareTo(rawCollationKey);
        return n < 0 ? -1 : (n == 0 ? 0 : 1);
    }
}

