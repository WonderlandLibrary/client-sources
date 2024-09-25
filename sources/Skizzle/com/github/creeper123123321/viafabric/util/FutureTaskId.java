/*
 * Decompiled with CFR 0.150.
 */
package com.github.creeper123123321.viafabric.util;

import java.util.concurrent.Future;
import us.myles.ViaVersion.api.platform.TaskId;

public class FutureTaskId
implements TaskId {
    private final Future<?> object;

    public FutureTaskId(Future<?> object) {
        this.object = object;
    }

    @Override
    public Future<?> getObject() {
        return this.object;
    }
}

