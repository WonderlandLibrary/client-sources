/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.api;

import de.gerrygames.viarewind.api.ViaRewindConfig;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import us.myles.ViaVersion.util.Config;

public class ViaRewindConfigImpl
extends Config
implements ViaRewindConfig {
    public ViaRewindConfigImpl(File configFile) {
        super(configFile);
        this.reloadConfig();
    }

    @Override
    public ViaRewindConfig.CooldownIndicator getCooldownIndicator() {
        return ViaRewindConfig.CooldownIndicator.valueOf(this.getString("cooldown-indicator", "TITLE").toUpperCase());
    }

    @Override
    public boolean isReplaceAdventureMode() {
        return this.getBoolean("replace-adventure", false);
    }

    @Override
    public boolean isReplaceParticles() {
        return this.getBoolean("replace-particles", false);
    }

    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viarewind/config.yml");
    }

    @Override
    protected void handleConfig(Map<String, Object> map) {
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}

