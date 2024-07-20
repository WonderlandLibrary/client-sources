/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna.ptr;

import com.sun.jna.Memory;
import com.sun.jna.PointerType;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public abstract class ByReference
extends PointerType {
    protected ByReference(int dataSize) {
        this.setPointer(new Memory(dataSize));
    }
}

