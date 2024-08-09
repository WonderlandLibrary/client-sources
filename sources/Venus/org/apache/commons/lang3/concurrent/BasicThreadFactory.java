/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class BasicThreadFactory
implements ThreadFactory {
    private final AtomicLong threadCounter;
    private final ThreadFactory wrappedFactory;
    private final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private final String namingPattern;
    private final Integer priority;
    private final Boolean daemonFlag;

    private BasicThreadFactory(Builder builder) {
        this.wrappedFactory = Builder.access$000(builder) == null ? Executors.defaultThreadFactory() : Builder.access$000(builder);
        this.namingPattern = Builder.access$100(builder);
        this.priority = Builder.access$200(builder);
        this.daemonFlag = Builder.access$300(builder);
        this.uncaughtExceptionHandler = Builder.access$400(builder);
        this.threadCounter = new AtomicLong();
    }

    public final ThreadFactory getWrappedFactory() {
        return this.wrappedFactory;
    }

    public final String getNamingPattern() {
        return this.namingPattern;
    }

    public final Boolean getDaemonFlag() {
        return this.daemonFlag;
    }

    public final Integer getPriority() {
        return this.priority;
    }

    public final Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.uncaughtExceptionHandler;
    }

    public long getThreadCount() {
        return this.threadCounter.get();
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread2 = this.getWrappedFactory().newThread(runnable);
        this.initializeThread(thread2);
        return thread2;
    }

    private void initializeThread(Thread thread2) {
        if (this.getNamingPattern() != null) {
            Long l = this.threadCounter.incrementAndGet();
            thread2.setName(String.format(this.getNamingPattern(), l));
        }
        if (this.getUncaughtExceptionHandler() != null) {
            thread2.setUncaughtExceptionHandler(this.getUncaughtExceptionHandler());
        }
        if (this.getPriority() != null) {
            thread2.setPriority(this.getPriority());
        }
        if (this.getDaemonFlag() != null) {
            thread2.setDaemon(this.getDaemonFlag());
        }
    }

    BasicThreadFactory(Builder builder, 1 var2_2) {
        this(builder);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.commons.lang3.builder.Builder<BasicThreadFactory> {
        private ThreadFactory wrappedFactory;
        private Thread.UncaughtExceptionHandler exceptionHandler;
        private String namingPattern;
        private Integer priority;
        private Boolean daemonFlag;

        public Builder wrappedFactory(ThreadFactory threadFactory) {
            if (threadFactory == null) {
                throw new NullPointerException("Wrapped ThreadFactory must not be null!");
            }
            this.wrappedFactory = threadFactory;
            return this;
        }

        public Builder namingPattern(String string) {
            if (string == null) {
                throw new NullPointerException("Naming pattern must not be null!");
            }
            this.namingPattern = string;
            return this;
        }

        public Builder daemon(boolean bl) {
            this.daemonFlag = bl;
            return this;
        }

        public Builder priority(int n) {
            this.priority = n;
            return this;
        }

        public Builder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            if (uncaughtExceptionHandler == null) {
                throw new NullPointerException("Uncaught exception handler must not be null!");
            }
            this.exceptionHandler = uncaughtExceptionHandler;
            return this;
        }

        public void reset() {
            this.wrappedFactory = null;
            this.exceptionHandler = null;
            this.namingPattern = null;
            this.priority = null;
            this.daemonFlag = null;
        }

        @Override
        public BasicThreadFactory build() {
            BasicThreadFactory basicThreadFactory = new BasicThreadFactory(this, null);
            this.reset();
            return basicThreadFactory;
        }

        @Override
        public Object build() {
            return this.build();
        }

        static ThreadFactory access$000(Builder builder) {
            return builder.wrappedFactory;
        }

        static String access$100(Builder builder) {
            return builder.namingPattern;
        }

        static Integer access$200(Builder builder) {
            return builder.priority;
        }

        static Boolean access$300(Builder builder) {
            return builder.daemonFlag;
        }

        static Thread.UncaughtExceptionHandler access$400(Builder builder) {
            return builder.exceptionHandler;
        }
    }
}

