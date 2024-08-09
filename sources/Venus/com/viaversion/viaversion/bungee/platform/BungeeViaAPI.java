/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.config.ServerInfo
 *  net.md_5.bungee.api.connection.ProxiedPlayer
 */
package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.ViaAPIBase;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bungee.service.ProtocolDetectorService;
import io.netty.buffer.ByteBuf;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeViaAPI
extends ViaAPIBase<ProxiedPlayer> {
    @Override
    public int getPlayerVersion(ProxiedPlayer proxiedPlayer) {
        return this.getPlayerVersion(proxiedPlayer.getUniqueId());
    }

    @Override
    public void sendRawPacket(ProxiedPlayer proxiedPlayer, ByteBuf byteBuf) throws IllegalArgumentException {
        this.sendRawPacket(proxiedPlayer.getUniqueId(), byteBuf);
    }

    public void probeServer(ServerInfo serverInfo) {
        ((ProtocolDetectorService)Via.proxyPlatform().protocolDetectorService()).probeServer(serverInfo);
    }

    @Override
    public void sendRawPacket(Object object, ByteBuf byteBuf) {
        this.sendRawPacket((ProxiedPlayer)object, byteBuf);
    }

    @Override
    public int getPlayerVersion(Object object) {
        return this.getPlayerVersion((ProxiedPlayer)object);
    }
}

