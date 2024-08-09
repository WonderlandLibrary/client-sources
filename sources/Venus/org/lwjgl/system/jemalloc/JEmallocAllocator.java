/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.jemalloc.JEmalloc;

public class JEmallocAllocator
implements MemoryUtil.MemoryAllocator {
    @Override
    public long getMalloc() {
        return JEmalloc.Functions.malloc;
    }

    @Override
    public long getCalloc() {
        return JEmalloc.Functions.calloc;
    }

    @Override
    public long getRealloc() {
        return JEmalloc.Functions.realloc;
    }

    @Override
    public long getFree() {
        return JEmalloc.Functions.free;
    }

    @Override
    public long getAlignedAlloc() {
        return JEmalloc.Functions.aligned_alloc;
    }

    @Override
    public long getAlignedFree() {
        return JEmalloc.Functions.free;
    }

    @Override
    public long malloc(long l) {
        return JEmalloc.nje_malloc(l);
    }

    @Override
    public long calloc(long l, long l2) {
        return JEmalloc.nje_calloc(l, l2);
    }

    @Override
    public long realloc(long l, long l2) {
        return JEmalloc.nje_realloc(l, l2);
    }

    @Override
    public void free(long l) {
        JEmalloc.nje_free(l);
    }

    @Override
    public long aligned_alloc(long l, long l2) {
        return JEmalloc.nje_aligned_alloc(l, l2);
    }

    @Override
    public void aligned_free(long l) {
        JEmalloc.nje_free(l);
    }

    static {
        JEmalloc.getLibrary();
    }
}

