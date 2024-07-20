/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.internal;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class Logger {
    private final java.util.logging.Logger logger;

    private Logger(String name) {
        this.logger = java.util.logging.Logger.getLogger(name);
    }

    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    public boolean isLoggable(Level level) {
        return this.logger.isLoggable(level.level);
    }

    public void warn(String msg) {
        this.logger.log(Level.WARNING.level, msg);
    }

    /*
     * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
     */
    public static enum Level {
        WARNING(java.util.logging.Level.FINE);

        private final java.util.logging.Level level;

        private Level(java.util.logging.Level level) {
            this.level = level;
        }
    }
}

