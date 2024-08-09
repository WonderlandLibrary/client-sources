/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

public interface NativeResource
extends AutoCloseable {
    public void free();

    @Override
    default public void close() {
        this.free();
    }
}

