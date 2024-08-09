/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible
final class SerializingExecutor
implements Executor {
    private static final Logger log = Logger.getLogger(SerializingExecutor.class.getName());
    private final Executor executor;
    @GuardedBy(value="internalLock")
    private final Deque<Runnable> queue = new ArrayDeque<Runnable>();
    @GuardedBy(value="internalLock")
    private boolean isWorkerRunning = false;
    @GuardedBy(value="internalLock")
    private int suspensions = 0;
    private final Object internalLock = new Object();

    public SerializingExecutor(Executor executor) {
        this.executor = Preconditions.checkNotNull(executor);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void execute(Runnable runnable) {
        Object object = this.internalLock;
        synchronized (object) {
            this.queue.add(runnable);
        }
        this.startQueueWorker();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeFirst(Runnable runnable) {
        Object object = this.internalLock;
        synchronized (object) {
            this.queue.addFirst(runnable);
        }
        this.startQueueWorker();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void suspend() {
        Object object = this.internalLock;
        synchronized (object) {
            ++this.suspensions;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resume() {
        Object object = this.internalLock;
        synchronized (object) {
            Preconditions.checkState(this.suspensions > 0);
            --this.suspensions;
        }
        this.startQueueWorker();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void startQueueWorker() {
        Object object = this.internalLock;
        synchronized (object) {
            if (this.queue.peek() == null) {
                return;
            }
            if (this.suspensions > 0) {
                return;
            }
            if (this.isWorkerRunning) {
                return;
            }
            this.isWorkerRunning = true;
        }
        boolean bl = true;
        try {
            this.executor.execute(new QueueWorker(this, null));
            bl = false;
        } finally {
            if (bl) {
                Object object2 = this.internalLock;
                synchronized (object2) {
                    this.isWorkerRunning = false;
                }
            }
        }
    }

    static Object access$100(SerializingExecutor serializingExecutor) {
        return serializingExecutor.internalLock;
    }

    static boolean access$202(SerializingExecutor serializingExecutor, boolean bl) {
        serializingExecutor.isWorkerRunning = bl;
        return serializingExecutor.isWorkerRunning;
    }

    static int access$300(SerializingExecutor serializingExecutor) {
        return serializingExecutor.suspensions;
    }

    static Deque access$400(SerializingExecutor serializingExecutor) {
        return serializingExecutor.queue;
    }

    static Logger access$500() {
        return log;
    }

    private final class QueueWorker
    implements Runnable {
        final SerializingExecutor this$0;

        private QueueWorker(SerializingExecutor serializingExecutor) {
            this.this$0 = serializingExecutor;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                this.workOnQueue();
            } catch (Error error2) {
                Object object = SerializingExecutor.access$100(this.this$0);
                synchronized (object) {
                    SerializingExecutor.access$202(this.this$0, false);
                }
                throw error2;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void workOnQueue() {
            while (true) {
                Runnable runnable = null;
                Object object = SerializingExecutor.access$100(this.this$0);
                synchronized (object) {
                    if (SerializingExecutor.access$300(this.this$0) == 0) {
                        runnable = (Runnable)SerializingExecutor.access$400(this.this$0).poll();
                    }
                    if (runnable == null) {
                        SerializingExecutor.access$202(this.this$0, false);
                        return;
                    }
                }
                try {
                    runnable.run();
                    continue;
                } catch (RuntimeException runtimeException) {
                    SerializingExecutor.access$500().log(Level.SEVERE, "Exception while executing runnable " + runnable, runtimeException);
                    continue;
                }
                break;
            }
        }

        QueueWorker(SerializingExecutor serializingExecutor, 1 var2_2) {
            this(serializingExecutor);
        }
    }
}

