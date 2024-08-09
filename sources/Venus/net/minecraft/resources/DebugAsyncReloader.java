/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.Profiler;
import net.minecraft.resources.AsyncReloader;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugAsyncReloader
extends AsyncReloader<DataPoint> {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Stopwatch timer = Stopwatch.createUnstarted();

    public DebugAsyncReloader(IResourceManager iResourceManager, List<IFutureReloadListener> list, Executor executor, Executor executor2, CompletableFuture<Unit> completableFuture) {
        super(executor, executor2, iResourceManager, list, (arg_0, arg_1, arg_2, arg_3, arg_4) -> DebugAsyncReloader.lambda$new$7(executor2, arg_0, arg_1, arg_2, arg_3, arg_4), completableFuture);
        this.timer.start();
        this.resultListFuture.thenAcceptAsync(this::logStatistics, executor2);
    }

    private void logStatistics(List<DataPoint> list) {
        this.timer.stop();
        int n = 0;
        LOGGER.info("Resource reload finished after " + this.timer.elapsed(TimeUnit.MILLISECONDS) + " ms");
        for (DataPoint dataPoint : list) {
            IProfileResult iProfileResult = dataPoint.prepareProfilerResult;
            IProfileResult iProfileResult2 = dataPoint.applyProfilerResult;
            int n2 = (int)((double)dataPoint.prepareDuration.get() / 1000000.0);
            int n3 = (int)((double)dataPoint.applyDuration.get() / 1000000.0);
            int n4 = n2 + n3;
            String string = dataPoint.className;
            LOGGER.info(string + " took approximately " + n4 + " ms (" + n2 + " ms preparing, " + n3 + " ms applying)");
            n += n3;
        }
        LOGGER.info("Total blocking time: " + n + " ms");
    }

    private static CompletableFuture lambda$new$7(Executor executor, IFutureReloadListener.IStage iStage, IResourceManager iResourceManager, IFutureReloadListener iFutureReloadListener, Executor executor2, Executor executor3) {
        AtomicLong atomicLong = new AtomicLong();
        AtomicLong atomicLong2 = new AtomicLong();
        Profiler profiler = new Profiler(Util.nanoTimeSupplier, DebugAsyncReloader::lambda$new$0, false);
        Profiler profiler2 = new Profiler(Util.nanoTimeSupplier, DebugAsyncReloader::lambda$new$1, false);
        CompletableFuture<Void> completableFuture = iFutureReloadListener.reload(iStage, iResourceManager, profiler, profiler2, arg_0 -> DebugAsyncReloader.lambda$new$3(executor2, atomicLong, arg_0), arg_0 -> DebugAsyncReloader.lambda$new$5(executor3, atomicLong2, arg_0));
        return completableFuture.thenApplyAsync(arg_0 -> DebugAsyncReloader.lambda$new$6(iFutureReloadListener, profiler, profiler2, atomicLong, atomicLong2, arg_0), executor);
    }

    private static DataPoint lambda$new$6(IFutureReloadListener iFutureReloadListener, Profiler profiler, Profiler profiler2, AtomicLong atomicLong, AtomicLong atomicLong2, Void void_) {
        return new DataPoint(iFutureReloadListener.getSimpleName(), profiler.getResults(), profiler2.getResults(), atomicLong, atomicLong2);
    }

    private static void lambda$new$5(Executor executor, AtomicLong atomicLong, Runnable runnable) {
        executor.execute(() -> DebugAsyncReloader.lambda$new$4(runnable, atomicLong));
    }

    private static void lambda$new$4(Runnable runnable, AtomicLong atomicLong) {
        long l = Util.nanoTime();
        runnable.run();
        atomicLong.addAndGet(Util.nanoTime() - l);
    }

    private static void lambda$new$3(Executor executor, AtomicLong atomicLong, Runnable runnable) {
        executor.execute(() -> DebugAsyncReloader.lambda$new$2(runnable, atomicLong));
    }

    private static void lambda$new$2(Runnable runnable, AtomicLong atomicLong) {
        long l = Util.nanoTime();
        runnable.run();
        atomicLong.addAndGet(Util.nanoTime() - l);
    }

    private static int lambda$new$1() {
        return 1;
    }

    private static int lambda$new$0() {
        return 1;
    }

    public static class DataPoint {
        private final String className;
        private final IProfileResult prepareProfilerResult;
        private final IProfileResult applyProfilerResult;
        private final AtomicLong prepareDuration;
        private final AtomicLong applyDuration;

        private DataPoint(String string, IProfileResult iProfileResult, IProfileResult iProfileResult2, AtomicLong atomicLong, AtomicLong atomicLong2) {
            this.className = string;
            this.prepareProfilerResult = iProfileResult;
            this.applyProfilerResult = iProfileResult2;
            this.prepareDuration = atomicLong;
            this.applyDuration = atomicLong2;
        }
    }
}

