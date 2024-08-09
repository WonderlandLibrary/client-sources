/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.bootstrap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.impl.bootstrap.Worker;

class WorkerPoolExecutor
extends ThreadPoolExecutor {
    private final Map<Worker, Boolean> workerSet = new ConcurrentHashMap<Worker, Boolean>();

    public WorkerPoolExecutor(int n, int n2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory) {
        super(n, n2, l, timeUnit, blockingQueue, threadFactory);
    }

    @Override
    protected void beforeExecute(Thread thread2, Runnable runnable) {
        if (runnable instanceof Worker) {
            this.workerSet.put((Worker)runnable, Boolean.TRUE);
        }
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        if (runnable instanceof Worker) {
            this.workerSet.remove(runnable);
        }
    }

    public Set<Worker> getWorkers() {
        return new HashSet<Worker>(this.workerSet.keySet());
    }
}

