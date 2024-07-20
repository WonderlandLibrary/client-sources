/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class DoubleByReference
extends ByReference {
    public DoubleByReference() {
        this(0.0);
    }

    public DoubleByReference(double value) {
        super(8);
        this.setValue(value);
    }

    public void setValue(double value) {
        this.getPointer().setDouble(0L, value);
    }

    public double getValue() {
        return this.getPointer().getDouble(0L);
    }
}

