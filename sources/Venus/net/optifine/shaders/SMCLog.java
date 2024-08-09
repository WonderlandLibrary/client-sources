/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PREFIX = "[Shaders] ";

    public static void severe(String string) {
        LOGGER.error(PREFIX + string);
    }

    public static void warning(String string) {
        LOGGER.warn(PREFIX + string);
    }

    public static void info(String string) {
        LOGGER.info(PREFIX + string);
    }

    public static void fine(String string) {
        LOGGER.debug(PREFIX + string);
    }

    public static void severe(String string, Object ... objectArray) {
        String string2 = String.format(string, objectArray);
        LOGGER.error(PREFIX + string2);
    }

    public static void warning(String string, Object ... objectArray) {
        String string2 = String.format(string, objectArray);
        LOGGER.warn(PREFIX + string2);
    }

    public static void info(String string, Object ... objectArray) {
        String string2 = String.format(string, objectArray);
        LOGGER.info(PREFIX + string2);
    }

    public static void fine(String string, Object ... objectArray) {
        String string2 = String.format(string, objectArray);
        LOGGER.debug(PREFIX + string2);
    }
}

