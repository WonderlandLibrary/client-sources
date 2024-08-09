/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.velocitypowered.api.proxy.server.RegisteredServer
 *  com.velocitypowered.api.proxy.server.ServerPing
 */
package com.viaversion.viaversion.velocity.service;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerPing;
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
        Collection collection = VelocityPlugin.PROXY.getAllServers();
        HashSet<String> hashSet = new HashSet<String>(collection.size());
        for (RegisteredServer registeredServer : collection) {
            this.probeServer(registeredServer);
            hashSet.add(registeredServer.getServerInfo().getName());
        }
        this.lock.writeLock().lock();
        try {
            this.detectedProtocolIds.keySet().retainAll(hashSet);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void probeServer(RegisteredServer registeredServer) {
        String string = registeredServer.getServerInfo().getName();
        registeredServer.ping().thenAccept(arg_0 -> this.lambda$probeServer$0(string, arg_0));
    }

    @Override
    protected Map<String, Integer> configuredServers() {
        return ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
    }

    @Override
    protected int lowestSupportedProtocolVersion() {
        try {
            return ProtocolVersion.getProtocol(Via.getManager().getInjector().getServerProtocolVersion()).getVersion();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ProtocolVersion.v1_8.getVersion();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lambda$probeServer$0(String string, ServerPing serverPing) {
        if (serverPing == null || serverPing.getVersion() == null) {
            return;
        }
        int n = this.serverProtocolVersion(string);
        if (n != -1 && n == serverPing.getVersion().getProtocol()) {
            return;
        }
        this.setProtocolVersion(string, serverPing.getVersion().getProtocol());
        if (((VelocityViaConfig)Via.getConfig()).isVelocityPingSave()) {
            Map<String, Integer> map = this.configuredServers();
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

