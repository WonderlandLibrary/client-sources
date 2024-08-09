/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.oio;

import io.netty.channel.ThreadPerChannelEventLoopGroup;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class OioEventLoopGroup
extends ThreadPerChannelEventLoopGroup {
    public OioEventLoopGroup() {
        this(0);
    }

    public OioEventLoopGroup(int n) {
        this(n, Executors.defaultThreadFactory());
    }

    public OioEventLoopGroup(int n, Executor executor) {
        super(n, executor, new Object[0]);
    }

    public OioEventLoopGroup(int n, ThreadFactory threadFactory) {
        super(n, threadFactory, new Object[0]);
    }
}

