/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

public interface LongCounter {
    public void add(long var1);

    public void increment();

    public void decrement();

    public long value();
}

