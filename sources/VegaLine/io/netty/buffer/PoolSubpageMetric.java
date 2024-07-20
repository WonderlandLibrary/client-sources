/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

public interface PoolSubpageMetric {
    public int maxNumElements();

    public int numAvailable();

    public int elementSize();

    public int pageSize();
}

