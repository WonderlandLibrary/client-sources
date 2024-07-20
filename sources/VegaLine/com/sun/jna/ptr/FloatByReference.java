/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class FloatByReference
extends ByReference {
    public FloatByReference() {
        this(0.0f);
    }

    public FloatByReference(float value) {
        super(4);
        this.setValue(value);
    }

    public void setValue(float value) {
        this.getPointer().setFloat(0L, value);
    }

    public float getValue() {
        return this.getPointer().getFloat(0L);
    }
}

