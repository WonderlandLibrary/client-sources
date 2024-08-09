/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.scheduler.ScheduledTask
 */
package com.viaversion.viaversion.sponge.platform;

import com.viaversion.viaversion.api.platform.PlatformTask;
import org.spongepowered.api.scheduler.ScheduledTask;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SpongeViaTask
implements PlatformTask<ScheduledTask> {
    private final ScheduledTask task;

    public SpongeViaTask(ScheduledTask scheduledTask) {
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

