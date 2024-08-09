/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class ShortByReference
extends ByReference {
    public ShortByReference() {
        this(0);
    }

    public ShortByReference(short s) {
        super(2);
        this.setValue(s);
    }

    public void setValue(short s) {
        this.getPointer().setShort(0L, s);
    }

    public short getValue() {
        return this.getPointer().getShort(0L);
    }
}

