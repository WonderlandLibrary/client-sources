/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ViaListener {
    private final Class<? extends Protocol> requiredPipeline;
    private boolean registered;

    protected ViaListener(Class<? extends Protocol> clazz) {
        this.requiredPipeline = clazz;
    }

    protected @Nullable UserConnection getUserConnection(UUID uUID) {
        return Via.getManager().getConnectionManager().getConnectedClient(uUID);
    }

    protected boolean isOnPipe(UUID uUID) {
        UserConnection userConnection = this.getUserConnection(uUID);
        return userConnection != null && (this.requiredPipeline == null || userConnection.getProtocolInfo().getPipeline().contains(this.requiredPipeline));
    }

    public abstract void register();

    protected Class<? extends Protocol> getRequiredPipeline() {
        return this.requiredPipeline;
    }

    protected boolean isRegistered() {
        return this.registered;
    }

    protected void setRegistered(boolean bl) {
        this.registered = bl;
    }
}

