/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.graph.Graph;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CompatibleWith;

@Beta
public interface MutableGraph<N>
extends Graph<N> {
    @CanIgnoreReturnValue
    public boolean addNode(N var1);

    @CanIgnoreReturnValue
    public boolean putEdge(N var1, N var2);

    @CanIgnoreReturnValue
    public boolean removeNode(@CompatibleWith(value="N") Object var1);

    @CanIgnoreReturnValue
    public boolean removeEdge(@CompatibleWith(value="N") Object var1, @CompatibleWith(value="N") Object var2);
}

