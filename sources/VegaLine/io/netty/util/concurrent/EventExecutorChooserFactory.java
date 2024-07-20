/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.concurrent;

import io.netty.util.concurrent.EventExecutor;

public interface EventExecutorChooserFactory {
    public EventExecutorChooser newChooser(EventExecutor[] var1);

    public static interface EventExecutorChooser {
        public EventExecutor next();
    }
}

