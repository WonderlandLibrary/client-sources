/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class ByteByReference
extends ByReference {
    public ByteByReference() {
        this(0);
    }

    public ByteByReference(byte by) {
        super(1);
        this.setValue(by);
    }

    public void setValue(byte by) {
        this.getPointer().setByte(0L, by);
    }

    public byte getValue() {
        return this.getPointer().getByte(0L);
    }
}

