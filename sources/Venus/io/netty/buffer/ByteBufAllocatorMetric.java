/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

public interface ByteBufAllocatorMetric {
    public long usedHeapMemory();

    public long usedDirectMemory();
}

