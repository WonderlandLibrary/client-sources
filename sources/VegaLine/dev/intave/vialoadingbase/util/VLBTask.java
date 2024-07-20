/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.vialoadingbase.util;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.scheduler.Task;
import com.viaversion.viaversion.api.scheduler.TaskStatus;

public class VLBTask
implements PlatformTask<Task> {
    private final Task object;

    public VLBTask(Task object) {
        this.object = object;
    }

    @Override
    public Task getObject() {
        return this.object;
    }

    @Override
    public void cancel() {
        this.object.cancel();
    }

    public TaskStatus getStatus() {
        return this.getObject().status();
    }
}

