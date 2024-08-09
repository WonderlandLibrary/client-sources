/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth.util.logging;

import net.raphimc.mcauth.util.logging.ILogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleLogger
implements ILogger {
    public static final Logger LOGGER = LoggerFactory.getLogger("MinecraftAuth");

    @Override
    public void info(String string) {
        LOGGER.info(string);
    }

    @Override
    public void warn(String string) {
        LOGGER.warn(string);
    }

    @Override
    public void error(String string) {
        LOGGER.error(string);
    }
}

