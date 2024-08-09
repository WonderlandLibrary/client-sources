/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal;

import io.netty.util.concurrent.FastThreadLocalThread;
import io.netty.util.internal.ConcurrentSet;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SystemPropertyUtil;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ObjectCleaner {
    private static final int REFERENCE_QUEUE_POLL_TIMEOUT_MS = Math.max(500, SystemPropertyUtil.getInt("io.netty.util.internal.ObjectCleaner.refQueuePollTimeout", 10000));
    static final String CLEANER_THREAD_NAME = ObjectCleaner.class.getSimpleName() + "Thread";
    private static final Set<AutomaticCleanerReference> LIVE_SET = new ConcurrentSet<AutomaticCleanerReference>();
    private static final ReferenceQueue<Object> REFERENCE_QUEUE = new ReferenceQueue();
    private static final AtomicBoolean CLEANER_RUNNING = new AtomicBoolean(false);
    private static final Runnable CLEANER_TASK = new Runnable(){

        @Override
        public void run() {
            boolean bl = false;
            while (true) {
                if (!ObjectCleaner.access$000().isEmpty()) {
                    AutomaticCleanerReference automaticCleanerReference;
                    try {
                        automaticCleanerReference = (AutomaticCleanerReference)ObjectCleaner.access$200().remove(ObjectCleaner.access$100());
                    } catch (InterruptedException interruptedException) {
                        bl = true;
                        continue;
                    }
                    if (automaticCleanerReference == null) continue;
                    try {
                        automaticCleanerReference.cleanup();
                    } catch (Throwable throwable) {
                        // empty catch block
                    }
                    ObjectCleaner.access$000().remove(automaticCleanerReference);
                    continue;
                }
                ObjectCleaner.access$300().set(true);
                if (ObjectCleaner.access$000().isEmpty() || !ObjectCleaner.access$300().compareAndSet(false, false)) break;
            }
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    };

    public static void register(Object object, Runnable runnable) {
        AutomaticCleanerReference automaticCleanerReference = new AutomaticCleanerReference(object, ObjectUtil.checkNotNull(runnable, "cleanupTask"));
        LIVE_SET.add(automaticCleanerReference);
        if (CLEANER_RUNNING.compareAndSet(false, false)) {
            FastThreadLocalThread fastThreadLocalThread = new FastThreadLocalThread(CLEANER_TASK);
            fastThreadLocalThread.setPriority(1);
            AccessController.doPrivileged(new PrivilegedAction<Void>((Thread)fastThreadLocalThread){
                final Thread val$cleanupThread;
                {
                    this.val$cleanupThread = thread2;
                }

                @Override
                public Void run() {
                    this.val$cleanupThread.setContextClassLoader(null);
                    return null;
                }

                @Override
                public Object run() {
                    return this.run();
                }
            });
            fastThreadLocalThread.setName(CLEANER_THREAD_NAME);
            fastThreadLocalThread.setDaemon(false);
            fastThreadLocalThread.start();
        }
    }

    public static int getLiveSetCount() {
        return LIVE_SET.size();
    }

    private ObjectCleaner() {
    }

    static Set access$000() {
        return LIVE_SET;
    }

    static int access$100() {
        return REFERENCE_QUEUE_POLL_TIMEOUT_MS;
    }

    static ReferenceQueue access$200() {
        return REFERENCE_QUEUE;
    }

    static AtomicBoolean access$300() {
        return CLEANER_RUNNING;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class AutomaticCleanerReference
    extends WeakReference<Object> {
        private final Runnable cleanupTask;

        AutomaticCleanerReference(Object object, Runnable runnable) {
            super(object, ObjectCleaner.access$200());
            this.cleanupTask = runnable;
        }

        void cleanup() {
            this.cleanupTask.run();
        }

        @Override
        public Thread get() {
            return null;
        }

        @Override
        public void clear() {
            ObjectCleaner.access$000().remove(this);
            super.clear();
        }

        @Override
        public Object get() {
            return this.get();
        }
    }
}

