package net.minecraft.resources;

import net.minecraft.profiler.IProfiler;
import net.minecraft.util.Unit;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface IResourceManagerReloadListener extends IFutureReloadListener
{
default CompletableFuture<Void> reload(IFutureReloadListener.IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor)
    {
        return stage.markCompleteAwaitingOthers(Unit.INSTANCE).thenRunAsync(() ->
        {
            reloadProfiler.startTick();
            reloadProfiler.startSection("listener");
            this.onResourceManagerReload(resourceManager);
            reloadProfiler.endSection();
            reloadProfiler.endTick();
        }, gameExecutor);
    }

    void onResourceManagerReload(IResourceManager resourceManager);
}
