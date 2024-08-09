/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

public class LongByReference
extends ByReference {
    public LongByReference() {
        this(0L);
    }

    public LongByReference(long l) {
        super(8);
        this.setValue(l);
    }

    public void setValue(long l) {
        this.getPointer().setLong(0L, l);
    }

    public long getValue() {
        return this.getPointer().getLong(0L);
    }
}

