/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j;

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.logging.log4j.spi.StandardLevel;
import org.apache.logging.log4j.util.Strings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Level
implements Comparable<Level>,
Serializable {
    public static final Level OFF;
    public static final Level FATAL;
    public static final Level ERROR;
    public static final Level WARN;
    public static final Level INFO;
    public static final Level DEBUG;
    public static final Level TRACE;
    public static final Level ALL;
    public static final String CATEGORY = "Level";
    private static final ConcurrentMap<String, Level> LEVELS;
    private static final long serialVersionUID = 1581082L;
    private final String name;
    private final int intLevel;
    private final StandardLevel standardLevel;

    private Level(String string, int n) {
        if (Strings.isEmpty(string)) {
            throw new IllegalArgumentException("Illegal null or empty Level name.");
        }
        if (n < 0) {
            throw new IllegalArgumentException("Illegal Level int less than zero.");
        }
        this.name = string;
        this.intLevel = n;
        this.standardLevel = StandardLevel.getStandardLevel(n);
        if (LEVELS.putIfAbsent(string, this) != null) {
            throw new IllegalStateException("Level " + string + " has already been defined.");
        }
    }

    public int intLevel() {
        return this.intLevel;
    }

    public StandardLevel getStandardLevel() {
        return this.standardLevel;
    }

    public boolean isInRange(Level level, Level level2) {
        return this.intLevel >= level.intLevel && this.intLevel <= level2.intLevel;
    }

    public boolean isLessSpecificThan(Level level) {
        return this.intLevel >= level.intLevel;
    }

    public boolean isMoreSpecificThan(Level level) {
        return this.intLevel <= level.intLevel;
    }

    public Level clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public int compareTo(Level level) {
        return this.intLevel < level.intLevel ? -1 : (this.intLevel > level.intLevel ? 1 : 0);
    }

    public boolean equals(Object object) {
        return object instanceof Level && object == this;
    }

    public Class<Level> getDeclaringClass() {
        return Level.class;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String name() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public static Level forName(String string, int n) {
        Level level = (Level)LEVELS.get(string);
        if (level != null) {
            return level;
        }
        try {
            return new Level(string, n);
        } catch (IllegalStateException illegalStateException) {
            return (Level)LEVELS.get(string);
        }
    }

    public static Level getLevel(String string) {
        return (Level)LEVELS.get(string);
    }

    public static Level toLevel(String string) {
        return Level.toLevel(string, DEBUG);
    }

    public static Level toLevel(String string, Level level) {
        if (string == null) {
            return level;
        }
        Level level2 = (Level)LEVELS.get(string.toUpperCase(Locale.ENGLISH));
        return level2 == null ? level : level2;
    }

    public static Level[] values() {
        Collection collection = LEVELS.values();
        return collection.toArray(new Level[collection.size()]);
    }

    public static Level valueOf(String string) {
        Objects.requireNonNull(string, "No level name given.");
        String string2 = string.toUpperCase(Locale.ENGLISH);
        Level level = (Level)LEVELS.get(string2);
        if (level != null) {
            return level;
        }
        throw new IllegalArgumentException("Unknown level constant [" + string2 + "].");
    }

    public static <T extends Enum<T>> T valueOf(Class<T> clazz, String string) {
        return Enum.valueOf(clazz, string);
    }

    protected Object readResolve() {
        return Level.valueOf(this.name);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Level)object);
    }

    static {
        LEVELS = new ConcurrentHashMap<String, Level>();
        OFF = new Level("OFF", StandardLevel.OFF.intLevel());
        FATAL = new Level("FATAL", StandardLevel.FATAL.intLevel());
        ERROR = new Level("ERROR", StandardLevel.ERROR.intLevel());
        WARN = new Level("WARN", StandardLevel.WARN.intLevel());
        INFO = new Level("INFO", StandardLevel.INFO.intLevel());
        DEBUG = new Level("DEBUG", StandardLevel.DEBUG.intLevel());
        TRACE = new Level("TRACE", StandardLevel.TRACE.intLevel());
        ALL = new Level("ALL", StandardLevel.ALL.intLevel());
    }
}

