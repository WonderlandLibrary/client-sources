/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.viaversion.viarewind;

import com.viaversion.viarewind.ViaRewindConfig;
import com.viaversion.viarewind.api.ViaRewindPlatform;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin
extends JavaPlugin
implements ViaRewindPlatform {
    public void onEnable() {
        ViaRewindConfig conf = new ViaRewindConfig(new File(this.getDataFolder(), "config.yml"));
        conf.reloadConfig();
        this.init(conf);
    }
}

