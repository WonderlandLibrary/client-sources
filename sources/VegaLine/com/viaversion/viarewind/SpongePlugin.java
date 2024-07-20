/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.config.ConfigDir
 *  org.spongepowered.api.event.Listener
 *  org.spongepowered.api.event.Order
 *  org.spongepowered.api.event.lifecycle.ConstructPluginEvent
 *  org.spongepowered.api.event.lifecycle.RefreshGameEvent
 *  org.spongepowered.plugin.builtin.jvm.Plugin
 */
package com.viaversion.viarewind;

import com.google.inject.Inject;
import com.viaversion.viarewind.ViaRewindConfig;
import com.viaversion.viarewind.api.ViaRewindPlatform;
import com.viaversion.viaversion.sponge.util.LoggerWrapper;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RefreshGameEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin(value="viarewind")
public class SpongePlugin
implements ViaRewindPlatform {
    private Logger logger;
    @Inject
    private org.apache.logging.log4j.Logger loggerSlf4j;
    @Inject
    @ConfigDir(sharedRoot=false)
    private Path configDir;
    private ViaRewindConfig conf;

    @Listener(order=Order.LATE)
    public void loadPlugin(ConstructPluginEvent e) {
        this.logger = new LoggerWrapper(this.loggerSlf4j);
        this.conf = new ViaRewindConfig(this.configDir.resolve("config.yml").toFile());
        this.conf.reloadConfig();
        this.init(this.conf);
    }

    @Listener
    public void reload(RefreshGameEvent e) {
        this.conf.reloadConfig();
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}

