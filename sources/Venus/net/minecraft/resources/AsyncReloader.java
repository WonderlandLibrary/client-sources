/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.profiler.EmptyProfiler;
import net.minecraft.resources.IAsyncReloader;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;

public class AsyncReloader<S>
implements IAsyncReloader {
    protected final IResourceManager resourceManager;
    protected final CompletableFuture<Unit> allAsyncCompleted = new CompletableFuture();
    protected final CompletableFuture<List<S>> resultListFuture;
    private final Set<IFutureReloadListener> taskSet;
    private final int taskCount;
    private int syncScheduled;
    private int syncCompleted;
    private final AtomicInteger asyncScheduled = new AtomicInteger();
    private final AtomicInteger asyncCompleted = new AtomicInteger();

    public static AsyncReloader<Void> create(IResourceManager iResourceManager, List<IFutureReloadListener> list, Executor executor, Executor executor2, CompletableFuture<Unit> completableFuture) {
        return new AsyncReloader<Void>(executor, executor2, iResourceManager, list, (arg_0, arg_1, arg_2, arg_3, arg_4) -> AsyncReloader.lambda$create$0(executor, arg_0, arg_1, arg_2, arg_3, arg_4), completableFuture);
    }

    protected AsyncReloader(Executor executor, Executor executor2, IResourceManager iResourceManager, List<IFutureReloadListener> list, IStateFactory<S> iStateFactory, CompletableFuture<Unit> completableFuture) {
        this.resourceManager = iResourceManager;
        this.taskCount = list.size();
        this.asyncScheduled.incrementAndGet();
        completableFuture.thenRun(this.asyncCompleted::incrementAndGet);
        ArrayList<CompletableFuture<S>> arrayList = Lists.newArrayList();
        CompletableFuture<Unit> completableFuture2 = completableFuture;
        this.taskSet = Sets.newHashSet(list);
        for (IFutureReloadListener iFutureReloadListener : list) {
            CompletableFuture<Unit> completableFuture3 = completableFuture2;
            CompletableFuture<S> completableFuture4 = iStateFactory.create(new IFutureReloadListener.IStage(){
                final Executor val$gameExecutor;
                final IFutureReloadListener val$ifuturereloadlistener;
                final CompletableFuture val$completablefuture1;
                final AsyncReloader this$0;
                {
                    this.this$0 = asyncReloader;
                    this.val$gameExecutor = executor;
                    this.val$ifuturereloadlistener = iFutureReloadListener;
                    this.val$completablefuture1 = completableFuture;
                }

                @Override
                public <T> CompletableFuture<T> markCompleteAwaitingOthers(T t) {
                    this.val$gameExecutor.execute(() -> this.lambda$markCompleteAwaitingOthers$0(this.val$ifuturereloadlistener));
                    return this.this$0.allAsyncCompleted.thenCombine((CompletionStage)this.val$completablefuture1, (arg_0, arg_1) -> 1.lambda$markCompleteAwaitingOthers$1(t, arg_0, arg_1));
                }

                private static Object lambda$markCompleteAwaitingOthers$1(Object object, Unit unit, Object object2) {
                    return object;
                }

                private void lambda$markCompleteAwaitingOthers$0(IFutureReloadListener iFutureReloadListener) {
                    this.this$0.taskSet.remove(iFutureReloadListener);
                    if (this.this$0.taskSet.isEmpty()) {
                        this.this$0.allAsyncCompleted.complete(Unit.INSTANCE);
                    }
                }
            }, iResourceManager, iFutureReloadListener, arg_0 -> this.lambda$new$2(executor, arg_0), arg_0 -> this.lambda$new$4(executor2, arg_0));
            arrayList.add(completableFuture4);
            completableFuture2 = completableFuture4;
        }
        this.resultListFuture = Util.gather(arrayList);
    }

    @Override
    public CompletableFuture<Unit> onceDone() {
        return this.resultListFuture.thenApply(AsyncReloader::lambda$onceDone$5);
    }

    @Override
    public float estimateExecutionSpeed() {
        int n = this.taskCount - this.taskSet.size();
        float f = this.asyncCompleted.get() * 2 + this.syncCompleted * 2 + n * 1;
        float f2 = this.asyncScheduled.get() * 2 + this.syncScheduled * 2 + this.taskCount * 1;
        return f / f2;
    }

    @Override
    public boolean asyncPartDone() {
        return this.allAsyncCompleted.isDone();
    }

    @Override
    public boolean fullyDone() {
        return this.resultListFuture.isDone();
    }

    @Override
    public void join() {
        if (this.resultListFuture.isCompletedExceptionally()) {
            this.resultListFuture.join();
        }
    }

    private static Unit lambda$onceDone$5(List list) {
        return Unit.INSTANCE;
    }

    private void lambda$new$4(Executor executor, Runnable runnable) {
        ++this.syncScheduled;
        executor.execute(() -> this.lambda$new$3(runnable));
    }

    private void lambda$new$3(Runnable runnable) {
        runnable.run();
        ++this.syncCompleted;
    }

    private void lambda$new$2(Executor executor, Runnable runnable) {
        this.asyncScheduled.incrementAndGet();
        executor.execute(() -> this.lambda$new$1(runnable));
    }

    private void lambda$new$1(Runnable runnable) {
        runnable.run();
        this.asyncCompleted.incrementAndGet();
    }

    private static CompletableFuture lambda$create$0(Executor executor, IFutureReloadListener.IStage iStage, IResourceManager iResourceManager, IFutureReloadListener iFutureReloadListener, Executor executor2, Executor executor3) {
        return iFutureReloadListener.reload(iStage, iResourceManager, EmptyProfiler.INSTANCE, EmptyProfiler.INSTANCE, executor, executor3);
    }

    public static interface IStateFactory<S> {
        public CompletableFuture<S> create(IFutureReloadListener.IStage var1, IResourceManager var2, IFutureReloadListener var3, Executor var4, Executor var5);
    }
}

