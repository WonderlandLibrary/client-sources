/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  net.fabricmc.loader.api.FabricLoader
 */
package com.viaversion.viarewind;

import com.viaversion.viarewind.ViaRewindConfig;
import com.viaversion.viarewind.api.ViaRewindPlatform;
import com.viaversion.viarewind.fabric.util.LoggerWrapper;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

public class ViaFabricAddon
implements ViaRewindPlatform,
Runnable {
    private final Logger logger = new LoggerWrapper(LogManager.getLogger("ViaRewind"));

    @Override
    public void run() {
        ViaRewindConfig conf = new ViaRewindConfig(FabricLoader.getInstance().getConfigDirectory().toPath().resolve("ViaRewind").resolve("config.yml").toFile());
        conf.reloadConfig();
        this.init(conf);
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}

