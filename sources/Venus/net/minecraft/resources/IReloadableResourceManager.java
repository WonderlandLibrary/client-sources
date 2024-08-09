/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.resources.IAsyncReloader;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.util.Unit;

public interface IReloadableResourceManager
extends IResourceManager,
AutoCloseable {
    default public CompletableFuture<Unit> reloadResourcesAndThen(Executor executor, Executor executor2, List<IResourcePack> list, CompletableFuture<Unit> completableFuture) {
        return this.reloadResources(executor, executor2, completableFuture, list).onceDone();
    }

    public IAsyncReloader reloadResources(Executor var1, Executor var2, CompletableFuture<Unit> var3, List<IResourcePack> var4);

    public void addReloadListener(IFutureReloadListener var1);

    @Override
    public void close();
}

