/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;

public interface IFutureReloadListener {
    public CompletableFuture<Void> reload(IStage var1, IResourceManager var2, IProfiler var3, IProfiler var4, Executor var5, Executor var6);

    default public String getSimpleName() {
        return this.getClass().getSimpleName();
    }

    public static interface IStage {
        public <T> CompletableFuture<T> markCompleteAwaitingOthers(T var1);
    }
}

