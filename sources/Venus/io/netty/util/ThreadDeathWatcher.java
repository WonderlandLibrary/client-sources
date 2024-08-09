/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
public final class ThreadDeathWatcher {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadDeathWatcher.class);
    static final ThreadFactory threadFactory;
    private static final Queue<Entry> pendingEntries;
    private static final Watcher watcher;
    private static final AtomicBoolean started;
    private static volatile Thread watcherThread;

    public static void watch(Thread thread2, Runnable runnable) {
        if (thread2 == null) {
            throw new NullPointerException("thread");
        }
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        if (!thread2.isAlive()) {
            throw new IllegalArgumentException("thread must be alive.");
        }
        ThreadDeathWatcher.schedule(thread2, runnable, true);
    }

    public static void unwatch(Thread thread2, Runnable runnable) {
        if (thread2 == null) {
            throw new NullPointerException("thread");
        }
        if (runnable == null) {
            throw new NullPointerException("task");
        }
        ThreadDeathWatcher.schedule(thread2, runnable, false);
    }

    private static void schedule(Thread thread2, Runnable runnable, boolean bl) {
        pendingEntries.add(new Entry(thread2, runnable, bl));
        if (started.compareAndSet(false, false)) {
            Thread thread3 = threadFactory.newThread(watcher);
            AccessController.doPrivileged(new PrivilegedAction<Void>(thread3){
                final Thread val$watcherThread;
                {
                    this.val$watcherThread = thread2;
                }

                @Override
                public Void run() {
                    this.val$watcherThread.setContextClassLoader(null);
                    return null;
                }

                @Override
                public Object run() {
                    return this.run();
                }
            });
            thread3.start();
            watcherThread = thread3;
        }
    }

    public static boolean awaitInactivity(long l, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        Thread thread2 = watcherThread;
        if (thread2 != null) {
            thread2.join(timeUnit.toMillis(l));
            return !thread2.isAlive();
        }
        return false;
    }

    private ThreadDeathWatcher() {
    }

    static Queue access$100() {
        return pendingEntries;
    }

    static AtomicBoolean access$200() {
        return started;
    }

    static InternalLogger access$300() {
        return logger;
    }

    static {
        pendingEntries = new ConcurrentLinkedQueue<Entry>();
        watcher = new Watcher(null);
        started = new AtomicBoolean();
        String string = "threadDeathWatcher";
        String string2 = SystemPropertyUtil.get("io.netty.serviceThreadPrefix");
        if (!StringUtil.isNullOrEmpty(string2)) {
            string = string2 + string;
        }
        threadFactory = new DefaultThreadFactory(string, true, 1, null);
    }

    private static final class Entry {
        final Thread thread;
        final Runnable task;
        final boolean isWatch;

        Entry(Thread thread2, Runnable runnable, boolean bl) {
            this.thread = thread2;
            this.task = runnable;
            this.isWatch = bl;
        }

        public int hashCode() {
            return this.thread.hashCode() ^ this.task.hashCode();
        }

        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (!(object instanceof Entry)) {
                return true;
            }
            Entry entry = (Entry)object;
            return this.thread == entry.thread && this.task == entry.task;
        }
    }

    private static final class Watcher
    implements Runnable {
        private final List<Entry> watchees = new ArrayList<Entry>();
        static final boolean $assertionsDisabled = !ThreadDeathWatcher.class.desiredAssertionStatus();

        private Watcher() {
        }

        @Override
        public void run() {
            while (true) {
                this.fetchWatchees();
                this.notifyWatchees();
                this.fetchWatchees();
                this.notifyWatchees();
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (!this.watchees.isEmpty() || !ThreadDeathWatcher.access$100().isEmpty()) continue;
                boolean bl = ThreadDeathWatcher.access$200().compareAndSet(true, true);
                if (!$assertionsDisabled && !bl) {
                    throw new AssertionError();
                }
                if (ThreadDeathWatcher.access$100().isEmpty() || !ThreadDeathWatcher.access$200().compareAndSet(false, false)) break;
            }
        }

        private void fetchWatchees() {
            Entry entry;
            while ((entry = (Entry)ThreadDeathWatcher.access$100().poll()) != null) {
                if (entry.isWatch) {
                    this.watchees.add(entry);
                    continue;
                }
                this.watchees.remove(entry);
            }
        }

        private void notifyWatchees() {
            List<Entry> list = this.watchees;
            int n = 0;
            while (n < list.size()) {
                Entry entry = list.get(n);
                if (!entry.thread.isAlive()) {
                    list.remove(n);
                    try {
                        entry.task.run();
                    } catch (Throwable throwable) {
                        ThreadDeathWatcher.access$300().warn("Thread death watcher task raised an exception:", throwable);
                    }
                    continue;
                }
                ++n;
            }
        }

        Watcher(1 var1_1) {
            this();
        }
    }
}

