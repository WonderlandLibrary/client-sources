/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.fabricmc.loader.api.FabricLoader
 *  org.apache.logging.log4j.LogManager
 */
package nl.matsv.viabackwards;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import nl.matsv.viabackwards.api.ViaBackwardsPlatform;
import nl.matsv.viabackwards.fabric.util.LoggerWrapper;
import org.apache.logging.log4j.LogManager;

public class ViaFabricAddon
implements ViaBackwardsPlatform,
Runnable {
    private final Logger logger = new LoggerWrapper(LogManager.getLogger((String)"ViaBackwards"));
    private File configDir;

    @Override
    public void run() {
        Path configDirPath = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("ViaBackwards");
        this.configDir = configDirPath.toFile();
        this.init(configDirPath.resolve("config.yml").toFile());
    }

    @Override
    public void disable() {
    }

    @Override
    public File getDataFolder() {
        return this.configDir;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}

