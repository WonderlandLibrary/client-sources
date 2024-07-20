/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.proxy.server.RegisteredServer
 */
package com.viaversion.viaversion.velocity.service;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.platform.AbstractProtocolDetectorService;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public final class ProtocolDetectorService
extends AbstractProtocolDetectorService {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void probeAllServers() {
        Collection servers = VelocityPlugin.PROXY.getAllServers();
        HashSet<String> serverNames = new HashSet<String>(servers.size());
        for (RegisteredServer server : servers) {
            this.probeServer(server);
            serverNames.add(server.getServerInfo().getName());
        }
        this.lock.writeLock().lock();
        try {
            this.detectedProtocolIds.keySet().retainAll(serverNames);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void probeServer(RegisteredServer server) {
        String serverName = server.getServerInfo().getName();
        server.ping().thenAccept(serverPing -> {
            if (serverPing == null || serverPing.getVersion() == null) {
                return;
            }
            int oldProtocolVersion = this.serverProtocolVersion(serverName);
            if (oldProtocolVersion != -1 && oldProtocolVersion == serverPing.getVersion().getProtocol()) {
                return;
            }
            this.setProtocolVersion(serverName, serverPing.getVersion().getProtocol());
            if (((VelocityViaConfig)Via.getConfig()).isVelocityPingSave()) {
                Map<String, Integer> servers = this.configuredServers();
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

    @Override
    protected Map<String, Integer> configuredServers() {
        return ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
    }

    @Override
    protected int lowestSupportedProtocolVersion() {
        try {
            return ProtocolVersion.getProtocol(Via.getManager().getInjector().getServerProtocolVersion()).getVersion();
        } catch (Exception e) {
            e.printStackTrace();
            return ProtocolVersion.v1_8.getVersion();
        }
    }
}

