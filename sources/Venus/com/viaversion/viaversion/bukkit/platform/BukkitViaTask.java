/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.scheduler.BukkitTask
 */
package com.viaversion.viaversion.bukkit.platform;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.platform.PlatformTask;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BukkitViaTask
implements PlatformTask<BukkitTask> {
    private final BukkitTask task;

    public BukkitViaTask(@Nullable BukkitTask bukkitTask) {
        this.task = bukkitTask;
    }

    @Override
    public @Nullable BukkitTask getObject() {
        return this.task;
    }

    @Override
    public void cancel() {
        Preconditions.checkArgument(this.task != null, "Task cannot be cancelled");
        this.task.cancel();
    }

    @Override
    public @Nullable Object getObject() {
        return this.getObject();
    }
}

