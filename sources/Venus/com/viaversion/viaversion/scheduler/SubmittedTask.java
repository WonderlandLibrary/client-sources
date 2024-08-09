/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.scheduler;

import com.viaversion.viaversion.api.scheduler.Task;
import com.viaversion.viaversion.api.scheduler.TaskStatus;
import java.util.concurrent.Future;

public final class SubmittedTask
implements Task {
    private final Future<?> future;

    public SubmittedTask(Future<?> future) {
        this.future = future;
    }

    @Override
    public TaskStatus status() {
        return this.future.isDone() ? TaskStatus.STOPPED : TaskStatus.RUNNING;
    }

    @Override
    public void cancel() {
        this.future.cancel(false);
    }
}

