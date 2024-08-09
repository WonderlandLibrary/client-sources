/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Unit;

public interface IResourceManagerReloadListener
extends IFutureReloadListener {
    @Override
    default public CompletableFuture<Void> reload(IFutureReloadListener.IStage iStage, IResourceManager iResourceManager, IProfiler iProfiler, IProfiler iProfiler2, Executor executor, Executor executor2) {
        return iStage.markCompleteAwaitingOthers(Unit.INSTANCE).thenRunAsync(() -> this.lambda$reload$0(iProfiler2, iResourceManager), executor2);
    }

    public void onResourceManagerReload(IResourceManager var1);

    private void lambda$reload$0(IProfiler iProfiler, IResourceManager iResourceManager) {
        iProfiler.startTick();
        iProfiler.startSection("listener");
        this.onResourceManagerReload(iResourceManager);
        iProfiler.endSection();
        iProfiler.endTick();
    }
}

