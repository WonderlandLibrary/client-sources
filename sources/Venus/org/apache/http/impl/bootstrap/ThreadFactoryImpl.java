/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.bootstrap;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

class ThreadFactoryImpl
implements ThreadFactory {
    private final String namePrefix;
    private final ThreadGroup group;
    private final AtomicLong count;

    ThreadFactoryImpl(String string, ThreadGroup threadGroup) {
        this.namePrefix = string;
        this.group = threadGroup;
        this.count = new AtomicLong();
    }

    ThreadFactoryImpl(String string) {
        this(string, null);
    }

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(this.group, runnable, this.namePrefix + "-" + this.count.incrementAndGet());
    }
}

