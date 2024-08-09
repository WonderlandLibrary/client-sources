/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultEventLoopGroup
extends MultithreadEventLoopGroup {
    public DefaultEventLoopGroup() {
        this(0);
    }

    public DefaultEventLoopGroup(int n) {
        this(n, (ThreadFactory)null);
    }

    public DefaultEventLoopGroup(int n, ThreadFactory threadFactory) {
        super(n, threadFactory, new Object[0]);
    }

    public DefaultEventLoopGroup(int n, Executor executor) {
        super(n, executor, new Object[0]);
    }

    @Override
    protected EventLoop newChild(Executor executor, Object ... objectArray) throws Exception {
        return new DefaultEventLoop((EventLoopGroup)this, executor);
    }

    @Override
    protected EventExecutor newChild(Executor executor, Object[] objectArray) throws Exception {
        return this.newChild(executor, objectArray);
    }
}

