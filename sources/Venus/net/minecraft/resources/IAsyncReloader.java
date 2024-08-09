/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.util.concurrent.CompletableFuture;
import net.minecraft.util.Unit;

public interface IAsyncReloader {
    public CompletableFuture<Unit> onceDone();

    public float estimateExecutionSpeed();

    public boolean asyncPartDone();

    public boolean fullyDone();

    public void join();
}

