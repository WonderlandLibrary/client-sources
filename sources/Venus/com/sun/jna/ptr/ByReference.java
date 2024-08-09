/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.Memory;
import com.sun.jna.PointerType;

public abstract class ByReference
extends PointerType {
    protected ByReference(int n) {
        this.setPointer(new Memory(n));
    }
}

