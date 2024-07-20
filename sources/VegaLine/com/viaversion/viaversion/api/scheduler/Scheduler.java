/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.scheduler;

import com.viaversion.viaversion.api.scheduler.Task;
import java.util.concurrent.TimeUnit;

public interface Scheduler {
    public Task execute(Runnable var1);

    public Task schedule(Runnable var1, long var2, TimeUnit var4);

    public Task scheduleRepeating(Runnable var1, long var2, long var4, TimeUnit var6);

    public void shutdown();
}

