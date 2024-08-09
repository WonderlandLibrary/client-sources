/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.util.UUID;

public class ProtocolInfoImpl
implements ProtocolInfo {
    private final UserConnection connection;
    private State state = State.HANDSHAKE;
    private int protocolVersion = -1;
    private int serverProtocolVersion = -1;
    private String username;
    private UUID uuid;
    private ProtocolPipeline pipeline;

    public ProtocolInfoImpl(UserConnection userConnection) {
        this.connection = userConnection;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    @Override
    public void setProtocolVersion(int n) {
        ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(n);
        this.protocolVersion = protocolVersion.getVersion();
    }

    @Override
    public int getServerProtocolVersion() {
        return this.serverProtocolVersion;
    }

    @Override
    public void setServerProtocolVersion(int n) {
        ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(n);
        this.serverProtocolVersion = protocolVersion.getVersion();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(String string) {
        this.username = string;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public void setUuid(UUID uUID) {
        this.uuid = uUID;
    }

    @Override
    public ProtocolPipeline getPipeline() {
        return this.pipeline;
    }

    @Override
    public void setPipeline(ProtocolPipeline protocolPipeline) {
        this.pipeline = protocolPipeline;
    }

    @Override
    public UserConnection getUser() {
        return this.connection;
    }

    public String toString() {
        return "ProtocolInfo{state=" + (Object)((Object)this.state) + ", protocolVersion=" + this.protocolVersion + ", serverProtocolVersion=" + this.serverProtocolVersion + ", username='" + this.username + '\'' + ", uuid=" + this.uuid + '}';
    }
}

