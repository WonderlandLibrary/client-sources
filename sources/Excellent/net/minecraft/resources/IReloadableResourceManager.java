package net.minecraft.resources;

import net.minecraft.util.Unit;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface IReloadableResourceManager extends IResourceManager, AutoCloseable
{
default CompletableFuture<Unit> reloadResourcesAndThen(Executor backgroundExecutor, Executor gameExecutor, List<IResourcePack> resourcePacks, CompletableFuture<Unit> waitingFor)
    {
        return this.reloadResources(backgroundExecutor, gameExecutor, waitingFor, resourcePacks).onceDone();
    }

    IAsyncReloader reloadResources(Executor backgroundExecutor, Executor gameExecutor, CompletableFuture<Unit> waitingFor, List<IResourcePack> resourcePacks);

    void addReloadListener(IFutureReloadListener listener);

    void close();
}
