/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.event;

public enum Level {
    ERROR(40, "ERROR"),
    WARN(30, "WARN"),
    INFO(20, "INFO"),
    DEBUG(10, "DEBUG"),
    TRACE(0, "TRACE");

    private final int levelInt;
    private final String levelStr;

    private Level(int n2, String string2) {
        this.levelInt = n2;
        this.levelStr = string2;
    }

    public int toInt() {
        return this.levelInt;
    }

    public static Level intToLevel(int n) {
        switch (n) {
            case 0: {
                return TRACE;
            }
            case 10: {
                return DEBUG;
            }
            case 20: {
                return INFO;
            }
            case 30: {
                return WARN;
            }
            case 40: {
                return ERROR;
            }
        }
        throw new IllegalArgumentException("Level integer [" + n + "] not recognized.");
    }

    public String toString() {
        return this.levelStr;
    }
}

