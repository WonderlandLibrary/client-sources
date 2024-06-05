/*
 * Decompiled with CFR 0.152.
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
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProtocolDetectorService
implements Runnable {
    private static final Map<String, Integer> detectedProtocolIds = new ConcurrentHashMap<String, Integer>();
    private static ProtocolDetectorService instance;

    public ProtocolDetectorService() {
        instance = this;
    }

    public static Integer getProtocolId(String serverName) {
        Map<String, Integer> servers = ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
        Integer protocol = servers.get(serverName);
        if (protocol != null) {
            return protocol;
        }
        Integer detectedProtocol = detectedProtocolIds.get(serverName);
        if (detectedProtocol != null) {
            return detectedProtocol;
        }
        Integer defaultProtocol = servers.get("default");
        if (defaultProtocol != null) {
            return defaultProtocol;
        }
        try {
            return ProtocolVersion.getProtocol(Via.getManager().getInjector().getServerProtocolVersion()).getVersion();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ProtocolVersion.v1_8.getVersion();
        }
    }

    @Override
    public void run() {
        Iterator iterator = VelocityPlugin.PROXY.getAllServers().iterator();
        while (iterator.hasNext()) {
            RegisteredServer serv = (RegisteredServer)iterator.next();
            ProtocolDetectorService.probeServer(serv);
        }
    }

    public static void probeServer(RegisteredServer serverInfo) {
        String key = serverInfo.getServerInfo().getName();
        serverInfo.ping().thenAccept(serverPing -> {
            if (serverPing == null) return;
            if (serverPing.getVersion() == null) return;
            detectedProtocolIds.put(key, serverPing.getVersion().getProtocol());
            if (!((VelocityViaConfig)Via.getConfig()).isVelocityPingSave()) return;
            Map<String, Integer> servers = ((VelocityViaConfig)Via.getConfig()).getVelocityServerProtocols();
            Integer protocol = servers.get(key);
            if (protocol != null && protocol.intValue() == serverPing.getVersion().getProtocol()) {
                return;
            }
            ConfigurationProvider configurationProvider = Via.getPlatform().getConfigurationProvider();
            synchronized (configurationProvider) {
                servers.put(key, serverPing.getVersion().getProtocol());
            }
            Via.getPlatform().getConfigurationProvider().saveConfig();
        });
    }

    public static Map<String, Integer> getDetectedIds() {
        return new HashMap<String, Integer>(detectedProtocolIds);
    }

    public static ProtocolDetectorService getInstance() {
        return instance;
    }
}

