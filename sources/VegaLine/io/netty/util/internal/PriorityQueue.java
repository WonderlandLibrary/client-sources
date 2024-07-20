/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import java.util.Queue;

public interface PriorityQueue<T>
extends Queue<T> {
    public boolean removeTyped(T var1);

    public boolean containsTyped(T var1);

    public void priorityChanged(T var1);
}

