/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.spi.StandardLevel;

public enum Severity {
    EMERG(0),
    ALERT(1),
    CRITICAL(2),
    ERROR(3),
    WARNING(4),
    NOTICE(5),
    INFO(6),
    DEBUG(7);

    private final int code;

    private Severity(int n2) {
        this.code = n2;
    }

    public int getCode() {
        return this.code;
    }

    public boolean isEqual(String string) {
        return this.name().equalsIgnoreCase(string);
    }

    public static Severity getSeverity(Level level) {
        switch (1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[level.getStandardLevel().ordinal()]) {
            case 1: {
                return DEBUG;
            }
            case 2: {
                return DEBUG;
            }
            case 3: {
                return DEBUG;
            }
            case 4: {
                return INFO;
            }
            case 5: {
                return WARNING;
            }
            case 6: {
                return ERROR;
            }
            case 7: {
                return ALERT;
            }
            case 8: {
                return EMERG;
            }
        }
        return DEBUG;
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$logging$log4j$spi$StandardLevel = new int[StandardLevel.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[StandardLevel.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[StandardLevel.TRACE.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[StandardLevel.DEBUG.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[StandardLevel.INFO.ordinal()] = 4;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[StandardLevel.WARN.ordinal()] = 5;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[StandardLevel.ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[StandardLevel.FATAL.ordinal()] = 7;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$logging$log4j$spi$StandardLevel[StandardLevel.OFF.ordinal()] = 8;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

