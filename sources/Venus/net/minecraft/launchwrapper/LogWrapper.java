/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.launchwrapper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogWrapper {
    public static LogWrapper log = new LogWrapper();
    private Logger myLog;
    private static boolean configured;

    private static void configureLogging() {
        LogWrapper.log.myLog = LogManager.getLogger("LaunchWrapper");
        configured = true;
    }

    public static void retarget(Logger logger) {
        LogWrapper.log.myLog = logger;
    }

    public static void log(String string, Level level, String string2, Object ... objectArray) {
        LogWrapper.makeLog(string);
        LogManager.getLogger(string).log(level, String.format(string2, objectArray));
    }

    public static void log(Level level, String string, Object ... objectArray) {
        if (!configured) {
            LogWrapper.configureLogging();
        }
        LogWrapper.log.myLog.log(level, String.format(string, objectArray));
    }

    public static void log(String string, Level level, Throwable throwable, String string2, Object ... objectArray) {
        LogWrapper.makeLog(string);
        LogManager.getLogger(string).log(level, String.format(string2, objectArray), throwable);
    }

    public static void log(Level level, Throwable throwable, String string, Object ... objectArray) {
        if (!configured) {
            LogWrapper.configureLogging();
        }
        LogWrapper.log.myLog.log(level, String.format(string, objectArray), throwable);
    }

    public static void severe(String string, Object ... objectArray) {
        LogWrapper.log(Level.ERROR, string, objectArray);
    }

    public static void warning(String string, Object ... objectArray) {
        LogWrapper.log(Level.WARN, string, objectArray);
    }

    public static void info(String string, Object ... objectArray) {
        LogWrapper.log(Level.INFO, string, objectArray);
    }

    public static void fine(String string, Object ... objectArray) {
        LogWrapper.log(Level.DEBUG, string, objectArray);
    }

    public static void finer(String string, Object ... objectArray) {
        LogWrapper.log(Level.TRACE, string, objectArray);
    }

    public static void finest(String string, Object ... objectArray) {
        LogWrapper.log(Level.TRACE, string, objectArray);
    }

    public static void makeLog(String string) {
        LogManager.getLogger(string);
    }
}

