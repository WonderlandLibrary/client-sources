/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.concurrent;

import com.google.common.collect.Queues;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.network.play.server.SUnloadChunkPacket;
import net.minecraft.network.play.server.SUpdateLightPacket;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.optifine.Config;
import net.optifine.util.PacketRunnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ThreadTaskExecutor<R extends Runnable>
implements ITaskExecutor<R>,
Executor {
    private final String name;
    private static final Logger LOGGER = LogManager.getLogger();
    private final Queue<R> queue = Queues.newConcurrentLinkedQueue();
    private int drivers;

    protected ThreadTaskExecutor(String string) {
        this.name = string;
    }

    protected abstract R wrapTask(Runnable var1);

    protected abstract boolean canRun(R var1);

    public boolean isOnExecutionThread() {
        return Thread.currentThread() == this.getExecutionThread();
    }

    protected abstract Thread getExecutionThread();

    protected boolean shouldDeferTasks() {
        return !this.isOnExecutionThread();
    }

    public int getQueueSize() {
        return this.queue.size();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public <V> CompletableFuture<V> supplyAsync(Supplier<V> supplier) {
        return this.shouldDeferTasks() ? CompletableFuture.supplyAsync(supplier, this) : CompletableFuture.completedFuture(supplier.get());
    }

    private CompletableFuture<Void> deferTask(Runnable runnable) {
        return CompletableFuture.supplyAsync(() -> ThreadTaskExecutor.lambda$deferTask$0(runnable), this);
    }

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        if (this.shouldDeferTasks()) {
            return this.deferTask(runnable);
        }
        runnable.run();
        return CompletableFuture.completedFuture(null);
    }

    public void runImmediately(Runnable runnable) {
        if (!this.isOnExecutionThread()) {
            this.deferTask(runnable).join();
        } else {
            runnable.run();
        }
    }

    @Override
    public void enqueue(R r) {
        this.queue.add(r);
        LockSupport.unpark(this.getExecutionThread());
    }

    @Override
    public void execute(Runnable runnable) {
        if (this.shouldDeferTasks()) {
            this.enqueue(this.wrapTask(runnable));
        } else {
            runnable.run();
        }
    }

    protected void dropTasks() {
        this.queue.clear();
    }

    protected void drainTasks() {
        int n = Integer.MAX_VALUE;
        if (Config.isLazyChunkLoading() && this == Minecraft.getInstance()) {
            n = this.getTaskCount();
        }
        while (this.driveOne() && --n > 0) {
        }
    }

    protected boolean driveOne() {
        Runnable runnable = (Runnable)this.queue.peek();
        if (runnable == null) {
            return true;
        }
        if (this.drivers == 0 && !this.canRun(runnable)) {
            return true;
        }
        this.run((Runnable)this.queue.remove());
        return false;
    }

    public void driveUntil(BooleanSupplier booleanSupplier) {
        ++this.drivers;
        try {
            while (!booleanSupplier.getAsBoolean()) {
                if (this.driveOne()) continue;
                this.threadYieldPark();
            }
        } finally {
            --this.drivers;
        }
    }

    protected void threadYieldPark() {
        Thread.yield();
        LockSupport.parkNanos("waiting for tasks", 100000L);
    }

    protected void run(R r) {
        block2: {
            try {
                r.run();
            } catch (Exception exception) {
                LOGGER.fatal("Error executing task on {}", (Object)this.getName(), (Object)exception);
                if (!(exception.getCause() instanceof OutOfMemoryError)) break block2;
                OutOfMemoryError outOfMemoryError = (OutOfMemoryError)exception.getCause();
                throw outOfMemoryError;
            }
        }
    }

    private int getTaskCount() {
        if (this.queue.isEmpty()) {
            return 1;
        }
        Runnable[] runnableArray = this.queue.toArray(new Runnable[this.queue.size()]);
        double d = this.getChunkUpdateWeight(runnableArray);
        if (d < 5.0) {
            return 0;
        }
        int n = runnableArray.length;
        int n2 = Math.max(Config.getFpsAverage(), 1);
        double d2 = n * 10 / n2;
        return this.getCount(runnableArray, d2);
    }

    private int getCount(R[] RArray, double d) {
        double d2 = 0.0;
        for (int i = 0; i < RArray.length; ++i) {
            R r = RArray[i];
            if (!((d2 += this.getChunkUpdateWeight((Runnable)r)) > d)) continue;
            return i + 1;
        }
        return RArray.length;
    }

    private double getChunkUpdateWeight(R[] RArray) {
        double d = 0.0;
        for (int i = 0; i < RArray.length; ++i) {
            R r = RArray[i];
            d += this.getChunkUpdateWeight((Runnable)r);
        }
        return d;
    }

    private double getChunkUpdateWeight(Runnable runnable) {
        if (runnable instanceof PacketRunnable) {
            PacketRunnable packetRunnable = (PacketRunnable)runnable;
            IPacket iPacket = packetRunnable.getPacket();
            if (iPacket instanceof SChunkDataPacket) {
                return 1.0;
            }
            if (iPacket instanceof SUpdateLightPacket) {
                return 0.2;
            }
            if (iPacket instanceof SUnloadChunkPacket) {
                return 2.6;
            }
        }
        return 0.0;
    }

    @Override
    public void enqueue(Object object) {
        this.enqueue((R)((Runnable)object));
    }

    private static Void lambda$deferTask$0(Runnable runnable) {
        runnable.run();
        return null;
    }
}

