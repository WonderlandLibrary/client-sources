/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.scheduler;

import com.viaversion.viaversion.api.scheduler.TaskStatus;

public interface Task {
    public TaskStatus status();

    public void cancel();
}

