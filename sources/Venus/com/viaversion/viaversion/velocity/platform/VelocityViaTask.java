/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.scheduler.ScheduledTask
 */
package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.viaversion.viaversion.api.platform.PlatformTask;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VelocityViaTask
implements PlatformTask<ScheduledTask> {
    private final ScheduledTask task;

    public VelocityViaTask(ScheduledTask scheduledTask) {
        this.task = scheduledTask;
    }

    @Override
    public ScheduledTask getObject() {
        return this.task;
    }

    @Override
    public void cancel() {
        this.task.cancel();
    }

    @Override
    public Object getObject() {
        return this.getObject();
    }
}

