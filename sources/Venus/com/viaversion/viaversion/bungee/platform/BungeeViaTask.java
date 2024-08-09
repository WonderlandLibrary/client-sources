/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.scheduler.ScheduledTask
 */
package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.platform.PlatformTask;
import net.md_5.bungee.api.scheduler.ScheduledTask;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BungeeViaTask
implements PlatformTask<ScheduledTask> {
    private final ScheduledTask task;

    public BungeeViaTask(ScheduledTask scheduledTask) {
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

