/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener {
    public ListenableFuture<Object> addScheduledTask(Runnable var1);

    public boolean isCallingFromMinecraftThread();
}

