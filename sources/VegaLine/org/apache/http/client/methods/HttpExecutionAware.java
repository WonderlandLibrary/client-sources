/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import org.apache.http.concurrent.Cancellable;

public interface HttpExecutionAware {
    public boolean isAborted();

    public void setCancellable(Cancellable var1);
}

