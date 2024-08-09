package dev.luvbeeq.via.platform.viaversion;

import com.viaversion.viaversion.configuration.AbstractViaConfig;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ViaConfig extends AbstractViaConfig {

    private final static List<String> UNSUPPORTED = Arrays.asList("anti-xray-patch", "bungee-ping-interval",
            "bungee-ping-save", "bungee-servers", "quick-move-action-fix", "nms-player-ticking",
            "velocity-ping-interval", "velocity-ping-save", "velocity-servers",
            "blockconnection-method", "change-1_9-hitbox", "change-1_14-hitbox",
            "show-shield-when-sword-in-hand", "left-handed-handling");


    public ViaConfig(File configFile) {
        super(configFile);

        this.reloadConfig();
    }

    @Override
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return UNSUPPORTED;
    }
}
