/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class IntByReference
extends ByReference {
    public IntByReference() {
        this(0);
    }

    public IntByReference(int n) {
        super(4);
        this.setValue(n);
    }

    public void setValue(int n) {
        this.getPointer().setInt(0L, n);
    }

    public int getValue() {
        return this.getPointer().getInt(0L);
    }
}

