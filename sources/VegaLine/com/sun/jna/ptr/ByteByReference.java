/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.ptr.ByReference;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class ByteByReference
extends ByReference {
    public ByteByReference() {
        this(0);
    }

    public ByteByReference(byte value) {
        super(1);
        this.setValue(value);
    }

    public void setValue(byte value) {
        this.getPointer().setByte(0L, value);
    }

    public byte getValue() {
        return this.getPointer().getByte(0L);
    }
}

