/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.bungee.platform;

import us.myles.ViaVersion.api.platform.TaskId;

public class BungeeTaskId
implements TaskId {
    private final Integer object;

    public BungeeTaskId(Integer object) {
        this.object = object;
    }

    @Override
    public Integer getObject() {
        return this.object;
    }
}

