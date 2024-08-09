/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bungee.providers.BungeeVersionProvider;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class BungeeViaConfig
extends AbstractViaConfig {
    private static final List<String> UNSUPPORTED = Arrays.asList("nms-player-ticking", "item-cache", "quick-move-action-fix", "velocity-ping-interval", "velocity-ping-save", "velocity-servers", "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox");
    private int bungeePingInterval;
    private boolean bungeePingSave;
    private Map<String, Integer> bungeeServerProtocols;

    public BungeeViaConfig(File file) {
        super(new File(file, "config.yml"));
        this.reloadConfig();
    }

    @Override
    protected void loadFields() {
        super.loadFields();
        this.bungeePingInterval = this.getInt("bungee-ping-interval", 60);
        this.bungeePingSave = this.getBoolean("bungee-ping-save", false);
        this.bungeeServerProtocols = this.get("bungee-servers", Map.class, new HashMap());
    }

    @Override
    protected void handleConfig(Map<String, Object> map) {
        Map map2 = !(map.get("bungee-servers") instanceof Map) ? new HashMap() : (Map)map.get("bungee-servers");
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
            map2.put("default", BungeeVersionProvider.getLowestSupportedVersion());
        }
        map.put("bungee-servers", map2);
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

    public int getBungeePingInterval() {
        return this.bungeePingInterval;
    }

    public boolean isBungeePingSave() {
        return this.bungeePingSave;
    }

    public Map<String, Integer> getBungeeServerProtocols() {
        return this.bungeeServerProtocols;
    }
}

