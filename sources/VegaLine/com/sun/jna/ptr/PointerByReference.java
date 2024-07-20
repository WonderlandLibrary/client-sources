/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByReference;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class PointerByReference
extends ByReference {
    public PointerByReference() {
        this(null);
    }

    public PointerByReference(Pointer value) {
        super(Pointer.SIZE);
        this.setValue(value);
    }

    public void setValue(Pointer value) {
        this.getPointer().setPointer(0L, value);
    }

    public Pointer getValue() {
        return this.getPointer().getPointer(0L);
    }
}

