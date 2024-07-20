/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.plugin.Plugin
 */
package com.viaversion.viarewind;

import com.viaversion.viarewind.ViaRewindConfig;
import com.viaversion.viarewind.api.ViaRewindPlatform;
import java.io.File;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeePlugin
extends Plugin
implements ViaRewindPlatform {
    public void onEnable() {
        ViaRewindConfig conf = new ViaRewindConfig(new File(this.getDataFolder(), "config.yml"));
        conf.reloadConfig();
        this.init(conf);
    }
}

