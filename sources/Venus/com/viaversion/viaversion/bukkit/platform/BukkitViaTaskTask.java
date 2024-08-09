/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.scheduler.Task;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BukkitViaTaskTask
implements PlatformTask<Task> {
    private final Task task;

    public BukkitViaTaskTask(Task task) {
        this.task = task;
    }

    @Override
    public @Nullable Task getObject() {
        return this.task;
    }

    @Override
    public void cancel() {
        this.task.cancel();
    }

    @Override
    public @Nullable Object getObject() {
        return this.getObject();
    }
}

