/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class DoubleByReference
extends ByReference {
    public DoubleByReference() {
        this(0.0);
    }

    public DoubleByReference(double d) {
        super(8);
        this.setValue(d);
    }

    public void setValue(double d) {
        this.getPointer().setDouble(0L, d);
    }

    public double getValue() {
        return this.getPointer().getDouble(0L);
    }
}

