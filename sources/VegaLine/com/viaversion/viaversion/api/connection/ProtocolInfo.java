/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.connection;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface ProtocolInfo {
    @Deprecated
    default public State getState() {
        return this.getServerState();
    }

    public State getClientState();

    public State getServerState();

    default public State getState(Direction direction) {
        return direction == Direction.CLIENTBOUND ? this.getServerState() : this.getClientState();
    }

    default public void setState(State state) {
        this.setClientState(state);
        this.setServerState(state);
    }

    public void setClientState(State var1);

    public void setServerState(State var1);

    public int getProtocolVersion();

    public void setProtocolVersion(int var1);

    public int getServerProtocolVersion();

    public void setServerProtocolVersion(int var1);

    public @Nullable String getUsername();

    public void setUsername(String var1);

    public @Nullable UUID getUuid();

    public void setUuid(UUID var1);

    public ProtocolPipeline getPipeline();

    public void setPipeline(ProtocolPipeline var1);

    @Deprecated
    public UserConnection getUser();
}

