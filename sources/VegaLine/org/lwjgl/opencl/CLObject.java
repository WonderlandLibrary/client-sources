/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opencl;

import org.lwjgl.PointerWrapperAbstract;

abstract class CLObject
extends PointerWrapperAbstract {
    protected CLObject(long pointer) {
        super(pointer);
    }

    final long getPointerUnsafe() {
        return this.pointer;
    }
}

