/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

public class DefaultEventExecutorGroup
extends MultithreadEventExecutorGroup {
    public DefaultEventExecutorGroup(int n) {
        this(n, null);
    }

    public DefaultEventExecutorGroup(int n, ThreadFactory threadFactory) {
        this(n, threadFactory, SingleThreadEventExecutor.DEFAULT_MAX_PENDING_EXECUTOR_TASKS, RejectedExecutionHandlers.reject());
    }

    public DefaultEventExecutorGroup(int n, ThreadFactory threadFactory, int n2, RejectedExecutionHandler rejectedExecutionHandler) {
        super(n, threadFactory, n2, rejectedExecutionHandler);
    }

    @Override
    protected EventExecutor newChild(Executor executor, Object ... objectArray) throws Exception {
        return new DefaultEventExecutor((EventExecutorGroup)this, executor, (int)((Integer)objectArray[0]), (RejectedExecutionHandler)objectArray[5]);
    }
}

