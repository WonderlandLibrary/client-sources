/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ProxyServer
 *  net.md_5.bungee.api.ServerPing
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
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;

public final class ProtocolDetectorService
extends AbstractProtocolDetectorService {
    public void probeServer(ServerInfo serverInfo) {
        String string = serverInfo.getName();
        serverInfo.ping((arg_0, arg_1) -> this.lambda$probeServer$0(string, arg_0, arg_1));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void probeAllServers() {
        Collection collection = ProxyServer.getInstance().getServers().values();
        HashSet<String> hashSet = new HashSet<String>(collection.size());
        for (ServerInfo serverInfo : collection) {
            this.probeServer(serverInfo);
            hashSet.add(serverInfo.getName());
        }
        this.lock.writeLock().lock();
        try {
            this.detectedProtocolIds.keySet().retainAll(hashSet);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lambda$probeServer$0(String string, ServerPing serverPing, Throwable throwable) {
        if (throwable != null || serverPing == null || serverPing.getVersion() == null || serverPing.getVersion().getProtocol() <= 0) {
            return;
        }
        int n = this.serverProtocolVersion(string);
        if (n == serverPing.getVersion().getProtocol()) {
            return;
        }
        this.setProtocolVersion(string, serverPing.getVersion().getProtocol());
        if (((BungeeViaConfig)Via.getConfig()).isBungeePingSave()) {
            Map<String, Integer> map = ((BungeeViaConfig)Via.getConfig()).getBungeeServerProtocols();
            Integer n2 = map.get(string);
            if (n2 != null && n2.intValue() == serverPing.getVersion().getProtocol()) {
                return;
            }
            ConfigurationProvider configurationProvider = Via.getPlatform().getConfigurationProvider();
            synchronized (configurationProvider) {
                map.put(string, serverPing.getVersion().getProtocol());
            }
            Via.getPlatform().getConfigurationProvider().saveConfig();
        }
    }
}

