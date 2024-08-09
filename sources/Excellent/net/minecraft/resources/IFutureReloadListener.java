package net.minecraft.resources;

import net.minecraft.profiler.IProfiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface IFutureReloadListener
{
    CompletableFuture<Void> reload(IFutureReloadListener.IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor);

default String getSimpleName()
    {
        return this.getClass().getSimpleName();
    }

    public interface IStage
    {
        <T> CompletableFuture<T> markCompleteAwaitingOthers(T backgroundResult);
    }
}
