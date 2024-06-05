package com.sun.jna.ptr;

import com.sun.jna.*;

public abstract class ByReference extends PointerType
{
    protected ByReference(final int dataSize) {
        this.setPointer(new Memory(dataSize));
    }
}
