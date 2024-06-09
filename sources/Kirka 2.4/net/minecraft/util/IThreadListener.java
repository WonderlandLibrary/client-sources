/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener {
    public ListenableFuture addScheduledTask(Runnable var1);

    public boolean isCallingFromMinecraftThread();
}

