/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.LegacyViaAPI;
import com.viaversion.viaversion.api.protocol.version.BlockedProtocolVersions;
import com.viaversion.viaversion.api.protocol.version.ServerProtocolVersion;
import com.viaversion.viaversion.legacy.LegacyAPI;
import io.netty.buffer.ByteBuf;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ViaAPIBase<T>
implements ViaAPI<T> {
    private final LegacyAPI<T> legacy = new LegacyAPI();

    @Override
    public ServerProtocolVersion getServerVersion() {
        return Via.getManager().getProtocolManager().getServerProtocolVersion();
    }

    @Override
    public int getPlayerVersion(UUID uUID) {
        UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(uUID);
        return userConnection != null ? userConnection.getProtocolInfo().getProtocolVersion() : -1;
    }

    @Override
    public String getVersion() {
        return Via.getPlatform().getPluginVersion();
    }

    @Override
    public boolean isInjected(UUID uUID) {
        return Via.getManager().getConnectionManager().isClientConnected(uUID);
    }

    @Override
    public @Nullable UserConnection getConnection(UUID uUID) {
        return Via.getManager().getConnectionManager().getConnectedClient(uUID);
    }

    @Override
    public void sendRawPacket(UUID uUID, ByteBuf byteBuf) throws IllegalArgumentException {
        if (!this.isInjected(uUID)) {
            throw new IllegalArgumentException("This player is not controlled by ViaVersion!");
        }
        UserConnection userConnection = Via.getManager().getConnectionManager().getConnectedClient(uUID);
        userConnection.scheduleSendRawPacket(byteBuf);
    }

    @Override
    public SortedSet<Integer> getSupportedVersions() {
        TreeSet<Integer> treeSet = new TreeSet<Integer>(Via.getManager().getProtocolManager().getSupportedVersions());
        BlockedProtocolVersions blockedProtocolVersions = Via.getPlatform().getConf().blockedProtocolVersions();
        treeSet.removeIf(blockedProtocolVersions::contains);
        return treeSet;
    }

    @Override
    public SortedSet<Integer> getFullSupportedVersions() {
        return Via.getManager().getProtocolManager().getSupportedVersions();
    }

    @Override
    public LegacyViaAPI<T> legacyAPI() {
        return this.legacy;
    }
}

