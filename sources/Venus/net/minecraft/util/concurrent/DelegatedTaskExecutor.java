/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.concurrent;

import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.concurrent.ITaskQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DelegatedTaskExecutor<T>
implements ITaskExecutor<T>,
AutoCloseable,
Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AtomicInteger flags = new AtomicInteger(0);
    public final ITaskQueue<? super T, ? extends Runnable> queue;
    private final Executor delegate;
    private final String name;

    public static DelegatedTaskExecutor<Runnable> create(Executor executor, String string) {
        return new DelegatedTaskExecutor<Runnable>(new ITaskQueue.Single(new ConcurrentLinkedQueue()), executor, string);
    }

    public DelegatedTaskExecutor(ITaskQueue<? super T, ? extends Runnable> iTaskQueue, Executor executor, String string) {
        this.delegate = executor;
        this.queue = iTaskQueue;
        this.name = string;
    }

    private boolean setActive() {
        int n;
        do {
            if (((n = this.flags.get()) & 3) == 0) continue;
            return true;
        } while (!this.flags.compareAndSet(n, n | 2));
        return false;
    }

    private void clearActive() {
        int n;
        while (!this.flags.compareAndSet(n = this.flags.get(), n & 0xFFFFFFFD)) {
        }
    }

    private boolean shouldSchedule() {
        if ((this.flags.get() & 1) != 0) {
            return true;
        }
        return !this.queue.isEmpty();
    }

    @Override
    public void close() {
        int n;
        while (!this.flags.compareAndSet(n = this.flags.get(), n | 1)) {
        }
    }

    private boolean isActive() {
        return (this.flags.get() & 2) != 0;
    }

    private boolean driveOne() {
        String string;
        Thread thread2;
        if (!this.isActive()) {
            return true;
        }
        Runnable runnable = this.queue.poll();
        if (runnable == null) {
            return true;
        }
        if (SharedConstants.developmentMode) {
            thread2 = Thread.currentThread();
            string = thread2.getName();
            thread2.setName(this.name);
        } else {
            thread2 = null;
            string = null;
        }
        runnable.run();
        if (thread2 != null) {
            thread2.setName(string);
        }
        return false;
    }

    @Override
    public void run() {
        try {
            this.driveWhile(DelegatedTaskExecutor::lambda$run$0);
        } finally {
            this.clearActive();
            this.reschedule();
        }
    }

    @Override
    public void enqueue(T t) {
        this.queue.enqueue(t);
        this.reschedule();
    }

    private void reschedule() {
        if (this.shouldSchedule() && this.setActive()) {
            try {
                this.delegate.execute(this);
            } catch (RejectedExecutionException rejectedExecutionException) {
                try {
                    this.delegate.execute(this);
                } catch (RejectedExecutionException rejectedExecutionException2) {
                    LOGGER.error("Cound not schedule mailbox", (Throwable)rejectedExecutionException2);
                }
            }
        }
    }

    private int driveWhile(Int2BooleanFunction int2BooleanFunction) {
        int n = 0;
        while (int2BooleanFunction.get(n) && this.driveOne()) {
            ++n;
        }
        return n;
    }

    public String toString() {
        return this.name + " " + this.flags.get() + " " + this.queue.isEmpty();
    }

    @Override
    public String getName() {
        return this.name;
    }

    private static boolean lambda$run$0(int n) {
        return n == 0;
    }
}

