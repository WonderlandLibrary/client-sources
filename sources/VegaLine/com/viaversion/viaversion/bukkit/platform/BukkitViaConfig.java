/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.bukkit.plugin.Plugin;

public class BukkitViaConfig
extends AbstractViaConfig {
    private static final List<String> UNSUPPORTED = Arrays.asList("bungee-ping-interval", "bungee-ping-save", "bungee-servers", "velocity-ping-interval", "velocity-ping-save", "velocity-servers");
    private boolean quickMoveActionFix;
    private boolean hitboxFix1_9;
    private boolean hitboxFix1_14;
    private String blockConnectionMethod;
    private boolean armorToggleFix;
    private boolean registerUserConnectionOnJoin;

    public BukkitViaConfig() {
        super(new File(((Plugin)Via.getPlatform()).getDataFolder(), "config.yml"));
        this.reloadConfig();
    }

    @Override
    protected void loadFields() {
        super.loadFields();
        this.registerUserConnectionOnJoin = this.getBoolean("register-userconnections-on-join", true);
        this.quickMoveActionFix = this.getBoolean("quick-move-action-fix", false);
        this.hitboxFix1_9 = this.getBoolean("change-1_9-hitbox", false);
        this.hitboxFix1_14 = this.getBoolean("change-1_14-hitbox", false);
        this.blockConnectionMethod = this.getString("blockconnection-method", "packet");
        this.armorToggleFix = this.getBoolean("armor-toggle-fix", true);
    }

    @Override
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override
    public boolean shouldRegisterUserConnectionOnJoin() {
        return this.registerUserConnectionOnJoin;
    }

    @Override
    public boolean is1_12QuickMoveActionFix() {
        return this.quickMoveActionFix;
    }

    @Override
    public boolean is1_9HitboxFix() {
        return this.hitboxFix1_9;
    }

    @Override
    public boolean is1_14HitboxFix() {
        return this.hitboxFix1_14;
    }

    @Override
    public String getBlockConnectionMethod() {
        return this.blockConnectionMethod;
    }

    @Override
    public boolean isArmorToggleFix() {
        return this.armorToggleFix;
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return UNSUPPORTED;
    }
}

