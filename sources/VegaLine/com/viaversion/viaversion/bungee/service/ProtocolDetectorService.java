/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ProxyServer
 *  net.md_5.bungee.api.config.ServerInfo
 */
package com.viaversion.viaversion.bungee.service;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.bungee.platform.BungeeViaConfig;
import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
import com.viaversion.viaversion.platform.AbstractProtocolDetectorService;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

public final class ProtocolDetectorService
extends AbstractProtocolDetectorService {
    public void probeServer(ServerInfo serverInfo) {
        String serverName = serverInfo.getName();
        serverInfo.ping((serverPing, throwable) -> {
            if (throwable != null || serverPing == null || serverPing.getVersion() == null || serverPing.getVersion().getProtocol() <= 0) {
                return;
            }
            int oldProtocolVersion = this.serverProtocolVersion(serverName);
            if (oldProtocolVersion == serverPing.getVersion().getProtocol()) {
                return;
            }
            this.setProtocolVersion(serverName, serverPing.getVersion().getProtocol());
            if (((BungeeViaConfig)Via.getConfig()).isBungeePingSave()) {
                Map<String, Integer> servers = ((BungeeViaConfig)Via.getConfig()).getBungeeServerProtocols();
                Integer protocol = servers.get(serverName);
                if (protocol != null && protocol.intValue() == serverPing.getVersion().getProtocol()) {
                    return;
                }
                ConfigurationProvider configurationProvider = Via.getPlatform().getConfigurationProvider();
                synchronized (configurationProvider) {
                    servers.put(serverName, serverPing.getVersion().getProtocol());
                }
                Via.getPlatform().getConfigurationProvider().saveConfig();
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void probeAllServers() {
        Collection servers = ProxyServer.getInstance().getServers().values();
        HashSet<String> serverNames = new HashSet<String>(servers.size());
        for (ServerInfo serverInfo : servers) {
            this.probeServer(serverInfo);
            serverNames.add(serverInfo.getName());
        }
        this.lock.writeLock().lock();
        try {
            this.detectedProtocolIds.keySet().retainAll(serverNames);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    protected Map<String, Integer> configuredServers() {
        return ((BungeeViaConfig)Via.getConfig()).getBungeeServerProtocols();
    }

    @Override
    protected int lowestSupportedProtocolVersion() {
        return BungeeVersionProvider.getLowestSupportedVersion();
    }
}

