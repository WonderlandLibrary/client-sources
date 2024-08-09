package dev.excellent.impl.util.other;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class Multithreading {

    private final ScheduledExecutorService RUNNABLE_EXECUTOR = Executors.newScheduledThreadPool(3, new ThreadFactory() {
        private final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "Multithreading Thread " + COUNTER.incrementAndGet());
        }
    });

    public ExecutorService EXECUTOR = Executors.newCachedThreadPool(new ThreadFactory() {
        private final AtomicInteger COUNTER = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "Multithreading Thread " + COUNTER.incrementAndGet());
        }
    });

    public static void awaitMillis(long millis) {
        try {
            //noinspection ResultOfMethodCallIgnored
            EXECUTOR.awaitTermination(millis, TimeUnit.MILLISECONDS);
        } catch(Exception ignored) {
        }
    }

    public void schedule(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        RUNNABLE_EXECUTOR.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable r, long delay, TimeUnit unit) {
        return Multithreading.RUNNABLE_EXECUTOR.schedule(r, delay, unit);
    }

    public int getTotal() {
        return ((ThreadPoolExecutor) Multithreading.EXECUTOR).getActiveCount();
    }

    public void runAsync(Runnable runnable) {
        EXECUTOR.execute(runnable);
    }

}