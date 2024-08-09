/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.connection;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ConnectionManager;
import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ConnectionManagerImpl
implements ConnectionManager {
    protected final Map<UUID, UserConnection> clients = new ConcurrentHashMap<UUID, UserConnection>();
    protected final Set<UserConnection> connections = Collections.newSetFromMap(new ConcurrentHashMap());

    @Override
    public void onLoginSuccess(UserConnection userConnection) {
        UUID uUID;
        UserConnection userConnection2;
        Objects.requireNonNull(userConnection, "connection is null!");
        Channel channel = userConnection.getChannel();
        if (channel != null && !channel.isOpen()) {
            return;
        }
        boolean bl = this.connections.add(userConnection);
        if (this.isFrontEnd(userConnection) && (userConnection2 = this.clients.put(uUID = userConnection.getProtocolInfo().getUuid(), userConnection)) != null && userConnection2 != userConnection) {
            Via.getPlatform().getLogger().warning("Duplicate UUID on frontend connection! (" + uUID + ")");
        }
        if (channel != null) {
            if (!channel.isOpen()) {
                this.onDisconnect(userConnection);
            } else if (bl) {
                channel.closeFuture().addListener(arg_0 -> this.lambda$onLoginSuccess$0(userConnection, arg_0));
            }
        }
    }

    @Override
    public void onDisconnect(UserConnection userConnection) {
        Objects.requireNonNull(userConnection, "connection is null!");
        this.connections.remove(userConnection);
        if (this.isFrontEnd(userConnection)) {
            UUID uUID = userConnection.getProtocolInfo().getUuid();
            this.clients.remove(uUID);
        }
    }

    @Override
    public Map<UUID, UserConnection> getConnectedClients() {
        return Collections.unmodifiableMap(this.clients);
    }

    @Override
    public @Nullable UserConnection getConnectedClient(UUID uUID) {
        return this.clients.get(uUID);
    }

    @Override
    public @Nullable UUID getConnectedClientId(UserConnection userConnection) {
        if (userConnection.getProtocolInfo() == null) {
            return null;
        }
        UUID uUID = userConnection.getProtocolInfo().getUuid();
        UserConnection userConnection2 = this.clients.get(uUID);
        if (userConnection.equals(userConnection2)) {
            return uUID;
        }
        return null;
    }

    @Override
    public Set<UserConnection> getConnections() {
        return Collections.unmodifiableSet(this.connections);
    }

    @Override
    public boolean isClientConnected(UUID uUID) {
        return this.clients.containsKey(uUID);
    }

    private void lambda$onLoginSuccess$0(UserConnection userConnection, ChannelFuture channelFuture) throws Exception {
        this.onDisconnect(userConnection);
    }
}

