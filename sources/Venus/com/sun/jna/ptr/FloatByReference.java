/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class FloatByReference
extends ByReference {
    public FloatByReference() {
        this(0.0f);
    }

    public FloatByReference(float f) {
        super(4);
        this.setValue(f);
    }

    public void setValue(float f) {
        this.getPointer().setFloat(0L, f);
    }

    public float getValue() {
        return this.getPointer().getFloat(0L);
    }
}

