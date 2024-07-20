/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class IntByReference
extends ByReference {
    public IntByReference() {
        this(0);
    }

    public IntByReference(int value) {
        super(4);
        this.setValue(value);
    }

    public void setValue(int value) {
        this.getPointer().setInt(0L, value);
    }

    public int getValue() {
        return this.getPointer().getInt(0L);
    }
}

