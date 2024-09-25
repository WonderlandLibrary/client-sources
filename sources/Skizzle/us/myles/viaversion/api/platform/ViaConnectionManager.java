/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.ChannelFutureListener
 *  io.netty.util.concurrent.GenericFutureListener
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.platform;

import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;

public class ViaConnectionManager {
    protected final Map<UUID, UserConnection> clients = new ConcurrentHashMap<UUID, UserConnection>();
    protected final Set<UserConnection> connections = Collections.newSetFromMap(new ConcurrentHashMap());

    public void onLoginSuccess(UserConnection connection) {
        UUID id;
        Objects.requireNonNull(connection, "connection is null!");
        this.connections.add(connection);
        if (this.isFrontEnd(connection) && this.clients.put(id = connection.getProtocolInfo().getUuid(), connection) != null) {
            Via.getPlatform().getLogger().warning("Duplicate UUID on frontend connection! (" + id + ")");
        }
        if (connection.getChannel() != null) {
            connection.getChannel().closeFuture().addListener((GenericFutureListener)((ChannelFutureListener)future -> this.onDisconnect(connection)));
        }
    }

    public void onDisconnect(UserConnection connection) {
        Objects.requireNonNull(connection, "connection is null!");
        this.connections.remove(connection);
        if (this.isFrontEnd(connection)) {
            UUID id = connection.getProtocolInfo().getUuid();
            this.clients.remove(id);
        }
    }

    public boolean isFrontEnd(UserConnection conn) {
        return !conn.isClientSide();
    }

    public Map<UUID, UserConnection> getConnectedClients() {
        return Collections.unmodifiableMap(this.clients);
    }

    @Nullable
    public UserConnection getConnectedClient(UUID clientIdentifier) {
        return this.clients.get(clientIdentifier);
    }

    @Nullable
    public UUID getConnectedClientId(UserConnection conn) {
        if (conn.getProtocolInfo() == null) {
            return null;
        }
        UUID uuid = conn.getProtocolInfo().getUuid();
        UserConnection client = this.clients.get(uuid);
        if (client != null && client.equals(conn)) {
            return uuid;
        }
        return null;
    }

    public Set<UserConnection> getConnections() {
        return Collections.unmodifiableSet(this.connections);
    }

    public boolean isClientConnected(UUID playerId) {
        return this.clients.containsKey(playerId);
    }
}

