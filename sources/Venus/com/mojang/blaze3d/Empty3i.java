/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.IRenderCall;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Empty3i {
    private final List<ConcurrentLinkedQueue<IRenderCall>> linkedRenderCalls = ImmutableList.of(new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue());
    private volatile int x = this.y = this.z + 1;
    private volatile int y;
    private volatile int z;
}

