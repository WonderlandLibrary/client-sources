/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;

@Deprecated
public final class ComposedCharIter {
    @Deprecated
    public static final char DONE = '\uffff';
    private final Normalizer2Impl n2impl;
    private String decompBuf;
    private int curChar = 0;
    private int nextChar = -1;

    @Deprecated
    public ComposedCharIter() {
        this(false, 0);
    }

    @Deprecated
    public ComposedCharIter(boolean bl, int n) {
        this.n2impl = bl ? Norm2AllModes.getNFKCInstance().impl : Norm2AllModes.getNFCInstance().impl;
    }

    @Deprecated
    public boolean hasNext() {
        if (this.nextChar == -1) {
            this.findNextChar();
        }
        return this.nextChar != -1;
    }

    @Deprecated
    public char next() {
        if (this.nextChar == -1) {
            this.findNextChar();
        }
        this.curChar = this.nextChar;
        this.nextChar = -1;
        return (char)this.curChar;
    }

    @Deprecated
    public String decomposition() {
        if (this.decompBuf != null) {
            return this.decompBuf;
        }
        return "";
    }

    private void findNextChar() {
        int n;
        block2: {
            this.decompBuf = null;
            for (n = this.curChar + 1; n < 65535; ++n) {
                this.decompBuf = this.n2impl.getDecomposition(n);
                if (this.decompBuf == null) {
                    continue;
                }
                break block2;
            }
            n = -1;
        }
        this.nextChar = n;
    }
}

