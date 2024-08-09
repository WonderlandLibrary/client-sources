/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.CheckReturnValue;

@CanIgnoreReturnValue
@GwtIncompatible
public final class ThreadFactoryBuilder {
    private String nameFormat = null;
    private Boolean daemon = null;
    private Integer priority = null;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory backingThreadFactory = null;

    public ThreadFactoryBuilder setNameFormat(String string) {
        String string2 = ThreadFactoryBuilder.format(string, 0);
        this.nameFormat = string;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean bl) {
        this.daemon = bl;
        return this;
    }

    public ThreadFactoryBuilder setPriority(int n) {
        Preconditions.checkArgument(n >= 1, "Thread priority (%s) must be >= %s", n, 1);
        Preconditions.checkArgument(n <= 10, "Thread priority (%s) must be <= %s", n, 10);
        this.priority = n;
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.backingThreadFactory = Preconditions.checkNotNull(threadFactory);
        return this;
    }

    @CheckReturnValue
    public ThreadFactory build() {
        return ThreadFactoryBuilder.build(this);
    }

    private static ThreadFactory build(ThreadFactoryBuilder threadFactoryBuilder) {
        String string = threadFactoryBuilder.nameFormat;
        Boolean bl = threadFactoryBuilder.daemon;
        Integer n = threadFactoryBuilder.priority;
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = threadFactoryBuilder.uncaughtExceptionHandler;
        ThreadFactory threadFactory = threadFactoryBuilder.backingThreadFactory != null ? threadFactoryBuilder.backingThreadFactory : Executors.defaultThreadFactory();
        AtomicLong atomicLong = string != null ? new AtomicLong(0L) : null;
        return new ThreadFactory(threadFactory, string, atomicLong, bl, n, uncaughtExceptionHandler){
            final ThreadFactory val$backingThreadFactory;
            final String val$nameFormat;
            final AtomicLong val$count;
            final Boolean val$daemon;
            final Integer val$priority;
            final Thread.UncaughtExceptionHandler val$uncaughtExceptionHandler;
            {
                this.val$backingThreadFactory = threadFactory;
                this.val$nameFormat = string;
                this.val$count = atomicLong;
                this.val$daemon = bl;
                this.val$priority = n;
                this.val$uncaughtExceptionHandler = uncaughtExceptionHandler;
            }

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread2 = this.val$backingThreadFactory.newThread(runnable);
                if (this.val$nameFormat != null) {
                    thread2.setName(ThreadFactoryBuilder.access$000(this.val$nameFormat, new Object[]{this.val$count.getAndIncrement()}));
                }
                if (this.val$daemon != null) {
                    thread2.setDaemon(this.val$daemon);
                }
                if (this.val$priority != null) {
                    thread2.setPriority(this.val$priority);
                }
                if (this.val$uncaughtExceptionHandler != null) {
                    thread2.setUncaughtExceptionHandler(this.val$uncaughtExceptionHandler);
                }
                return thread2;
            }
        };
    }

    private static String format(String string, Object ... objectArray) {
        return String.format(Locale.ROOT, string, objectArray);
    }

    static String access$000(String string, Object[] objectArray) {
        return ThreadFactoryBuilder.format(string, objectArray);
    }
}

