/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.internal;

public class Logger {
    private final java.util.logging.Logger logger;

    private Logger(String string) {
        this.logger = java.util.logging.Logger.getLogger(string);
    }

    public static Logger getLogger(String string) {
        return new Logger(string);
    }

    public boolean isLoggable(Level level) {
        return this.logger.isLoggable(Level.access$000(level));
    }

    public void warn(String string) {
        this.logger.log(Level.access$000(Level.WARNING), string);
    }

    public static enum Level {
        WARNING(java.util.logging.Level.FINE);

        private final java.util.logging.Level level;

        private Level(java.util.logging.Level level) {
            this.level = level;
        }

        static java.util.logging.Level access$000(Level level) {
            return level.level;
        }
    }
}

