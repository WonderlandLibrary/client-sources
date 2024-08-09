/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.velocity.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class VelocityViaConfig
extends AbstractViaConfig {
    private static final List<String> UNSUPPORTED = Arrays.asList("nms-player-ticking", "item-cache", "quick-move-action-fix", "bungee-ping-interval", "bungee-ping-save", "bungee-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    private int velocityPingInterval;
    private boolean velocityPingSave;
    private Map<String, Integer> velocityServerProtocols;

    public VelocityViaConfig(File file) {
        super(new File(file, "config.yml"));
        this.reloadConfig();
    }

    @Override
    protected void loadFields() {
        super.loadFields();
        this.velocityPingInterval = this.getInt("velocity-ping-interval", 60);
        this.velocityPingSave = this.getBoolean("velocity-ping-save", false);
        this.velocityServerProtocols = this.get("velocity-servers", Map.class, new HashMap());
    }

    @Override
    protected void handleConfig(Map<String, Object> map) {
        Map map2 = !(map.get("velocity-servers") instanceof Map) ? new HashMap() : (Map)map.get("velocity-servers");
        for (Map.Entry entry : new HashSet(map2.entrySet())) {
            if (entry.getValue() instanceof Integer) continue;
            if (entry.getValue() instanceof String) {
                ProtocolVersion protocolVersion = ProtocolVersion.getClosest((String)entry.getValue());
                if (protocolVersion != null) {
                    map2.put(entry.getKey(), protocolVersion.getVersion());
                    continue;
                }
                map2.remove(entry.getKey());
                continue;
            }
            map2.remove(entry.getKey());
        }
        if (!map2.containsKey("default")) {
            try {
                map2.put("default", VelocityViaInjector.getLowestSupportedProtocolVersion());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        map.put("velocity-servers", map2);
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return UNSUPPORTED;
    }

    @Override
    public boolean isItemCache() {
        return true;
    }

    @Override
    public boolean isNMSPlayerTicking() {
        return true;
    }

    public int getVelocityPingInterval() {
        return this.velocityPingInterval;
    }

    public boolean isVelocityPingSave() {
        return this.velocityPingSave;
    }

    public Map<String, Integer> getVelocityServerProtocols() {
        return this.velocityServerProtocols;
    }
}

