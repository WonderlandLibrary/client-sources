/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByReference;

public class PointerByReference
extends ByReference {
    public PointerByReference() {
        this(null);
    }

    public PointerByReference(Pointer pointer) {
        super(Pointer.SIZE);
        this.setValue(pointer);
    }

    public void setValue(Pointer pointer) {
        this.getPointer().setPointer(0L, pointer);
    }

    public Pointer getValue() {
        return this.getPointer().getPointer(0L);
    }
}

