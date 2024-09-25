/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.base;

import java.util.UUID;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import us.myles.ViaVersion.packets.State;

public class ProtocolInfo
extends StoredObject {
    private State state = State.HANDSHAKE;
    private int protocolVersion = -1;
    private int serverProtocolVersion = -1;
    private String username;
    private UUID uuid;
    private ProtocolPipeline pipeline;

    public ProtocolInfo(UserConnection user) {
        super(user);
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(int protocolVersion) {
        ProtocolVersion protocol = ProtocolVersion.getProtocol(protocolVersion);
        this.protocolVersion = protocol.getVersion();
    }

    public int getServerProtocolVersion() {
        return this.serverProtocolVersion;
    }

    public void setServerProtocolVersion(int serverProtocolVersion) {
        ProtocolVersion protocol = ProtocolVersion.getProtocol(serverProtocolVersion);
        this.serverProtocolVersion = protocol.getVersion();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ProtocolPipeline getPipeline() {
        return this.pipeline;
    }

    public void setPipeline(ProtocolPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String toString() {
        return "ProtocolInfo{state=" + (Object)((Object)this.state) + ", protocolVersion=" + this.protocolVersion + ", serverProtocolVersion=" + this.serverProtocolVersion + ", username='" + this.username + '\'' + ", uuid=" + this.uuid + '}';
    }
}

