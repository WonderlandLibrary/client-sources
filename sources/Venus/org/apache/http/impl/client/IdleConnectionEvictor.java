/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.util.Args;

public final class IdleConnectionEvictor {
    private final HttpClientConnectionManager connectionManager;
    private final ThreadFactory threadFactory;
    private final Thread thread;
    private final long sleepTimeMs;
    private final long maxIdleTimeMs;
    private volatile Exception exception;

    public IdleConnectionEvictor(HttpClientConnectionManager httpClientConnectionManager, ThreadFactory threadFactory, long l, TimeUnit timeUnit, long l2, TimeUnit timeUnit2) {
        this.connectionManager = Args.notNull(httpClientConnectionManager, "Connection manager");
        this.threadFactory = threadFactory != null ? threadFactory : new DefaultThreadFactory();
        this.sleepTimeMs = timeUnit != null ? timeUnit.toMillis(l) : l;
        this.maxIdleTimeMs = timeUnit2 != null ? timeUnit2.toMillis(l2) : l2;
        this.thread = this.threadFactory.newThread(new Runnable(this, httpClientConnectionManager){
            final HttpClientConnectionManager val$connectionManager;
            final IdleConnectionEvictor this$0;
            {
                this.this$0 = idleConnectionEvictor;
                this.val$connectionManager = httpClientConnectionManager;
            }

            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(IdleConnectionEvictor.access$000(this.this$0));
                        this.val$connectionManager.closeExpiredConnections();
                        if (IdleConnectionEvictor.access$100(this.this$0) <= 0L) continue;
                        this.val$connectionManager.closeIdleConnections(IdleConnectionEvictor.access$100(this.this$0), TimeUnit.MILLISECONDS);
                    }
                } catch (Exception exception) {
                    IdleConnectionEvictor.access$202(this.this$0, exception);
                }
            }
        });
    }

    public IdleConnectionEvictor(HttpClientConnectionManager httpClientConnectionManager, long l, TimeUnit timeUnit, long l2, TimeUnit timeUnit2) {
        this(httpClientConnectionManager, null, l, timeUnit, l2, timeUnit2);
    }

    public IdleConnectionEvictor(HttpClientConnectionManager httpClientConnectionManager, long l, TimeUnit timeUnit) {
        this(httpClientConnectionManager, null, l > 0L ? l : 5L, timeUnit != null ? timeUnit : TimeUnit.SECONDS, l, timeUnit);
    }

    public void start() {
        this.thread.start();
    }

    public void shutdown() {
        this.thread.interrupt();
    }

    public boolean isRunning() {
        return this.thread.isAlive();
    }

    public void awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        this.thread.join((timeUnit != null ? timeUnit : TimeUnit.MILLISECONDS).toMillis(l));
    }

    static long access$000(IdleConnectionEvictor idleConnectionEvictor) {
        return idleConnectionEvictor.sleepTimeMs;
    }

    static long access$100(IdleConnectionEvictor idleConnectionEvictor) {
        return idleConnectionEvictor.maxIdleTimeMs;
    }

    static Exception access$202(IdleConnectionEvictor idleConnectionEvictor, Exception exception) {
        idleConnectionEvictor.exception = exception;
        return idleConnectionEvictor.exception;
    }

    static class DefaultThreadFactory
    implements ThreadFactory {
        DefaultThreadFactory() {
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread2 = new Thread(runnable, "Connection evictor");
            thread2.setDaemon(false);
            return thread2;
        }
    }
}

