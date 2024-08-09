/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");

    public static void detail(String string) {
        if (logDetail) {
            LOGGER.info("[OptiFine] " + string);
        }
    }

    public static void dbg(String string) {
        LOGGER.info("[OptiFine] " + string);
    }

    public static void warn(String string) {
        LOGGER.warn("[OptiFine] " + string);
    }

    public static void warn(String string, Throwable throwable) {
        LOGGER.warn("[OptiFine] " + string, throwable);
    }

    public static void error(String string) {
        LOGGER.error("[OptiFine] " + string);
    }

    public static void error(String string, Throwable throwable) {
        LOGGER.error("[OptiFine] " + string, throwable);
    }

    public static void log(String string) {
        Log.dbg(string);
    }
}

