/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

public abstract class ReloadListener<T>
implements IFutureReloadListener {
    @Override
    public final CompletableFuture<Void> reload(IFutureReloadListener.IStage iStage, IResourceManager iResourceManager, IProfiler iProfiler, IProfiler iProfiler2, Executor executor, Executor executor2) {
        return ((CompletableFuture)CompletableFuture.supplyAsync(() -> this.lambda$reload$0(iResourceManager, iProfiler), executor).thenCompose(iStage::markCompleteAwaitingOthers)).thenAcceptAsync(arg_0 -> this.lambda$reload$1(iResourceManager, iProfiler2, arg_0), executor2);
    }

    protected abstract T prepare(IResourceManager var1, IProfiler var2);

    protected abstract void apply(T var1, IResourceManager var2, IProfiler var3);

    private void lambda$reload$1(IResourceManager iResourceManager, IProfiler iProfiler, Object object) {
        this.apply(object, iResourceManager, iProfiler);
    }

    private Object lambda$reload$0(IResourceManager iResourceManager, IProfiler iProfiler) {
        return this.prepare(iResourceManager, iProfiler);
    }
}

