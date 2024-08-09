/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.IntTrieBuilder;
import com.ibm.icu.impl.PropsVectors;

public class PVecToTrieCompactHandler
implements PropsVectors.CompactHandler {
    public IntTrieBuilder builder;
    public int initialValue;

    @Override
    public void setRowIndexForErrorValue(int n) {
    }

    @Override
    public void setRowIndexForInitialValue(int n) {
        this.initialValue = n;
    }

    @Override
    public void setRowIndexForRange(int n, int n2, int n3) {
        this.builder.setRange(n, n2 + 1, n3, false);
    }

    @Override
    public void startRealValues(int n) {
        if (n > 65535) {
            throw new IndexOutOfBoundsException();
        }
        this.builder = new IntTrieBuilder(null, 100000, this.initialValue, this.initialValue, false);
    }
}

