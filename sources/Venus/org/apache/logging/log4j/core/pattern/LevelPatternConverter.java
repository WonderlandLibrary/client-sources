/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="LevelPatternConverter", category="Converter")
@ConverterKeys(value={"p", "level"})
@PerformanceSensitive(value={"allocation"})
public final class LevelPatternConverter
extends LogEventPatternConverter {
    private static final String OPTION_LENGTH = "length";
    private static final String OPTION_LOWER = "lowerCase";
    private static final LevelPatternConverter INSTANCE = new LevelPatternConverter(null);
    private final Map<Level, String> levelMap;

    private LevelPatternConverter(Map<Level, String> map) {
        super("Level", "level");
        this.levelMap = map;
    }

    public static LevelPatternConverter newInstance(String[] stringArray) {
        Object object;
        if (stringArray == null || stringArray.length == 0) {
            return INSTANCE;
        }
        HashMap<Level, String> hashMap = new HashMap<Level, String>();
        int n = Integer.MAX_VALUE;
        boolean bl = false;
        String[] stringArray2 = stringArray[0].split(Patterns.COMMA_SEPARATOR);
        for (String object2 : stringArray2) {
            object = object2.split("=");
            if (object == null || ((String[])object).length != 2) {
                LOGGER.error("Invalid option {}", (Object)object2);
                continue;
            }
            String string = object[0].trim();
            String string2 = object[5].trim();
            if (OPTION_LENGTH.equalsIgnoreCase(string)) {
                n = Integer.parseInt(string2);
                continue;
            }
            if (OPTION_LOWER.equalsIgnoreCase(string)) {
                bl = Boolean.parseBoolean(string2);
                continue;
            }
            Level level = Level.toLevel(string, null);
            if (level == null) {
                LOGGER.error("Invalid Level {}", (Object)string);
                continue;
            }
            hashMap.put(level, string2);
        }
        if (hashMap.isEmpty() && n == Integer.MAX_VALUE && !bl) {
            return INSTANCE;
        }
        for (Level level : Level.values()) {
            if (hashMap.containsKey(level)) continue;
            object = LevelPatternConverter.left(level, n);
            hashMap.put(level, (String)(bl ? ((String)object).toLowerCase(Locale.US) : object));
        }
        return new LevelPatternConverter(hashMap);
    }

    private static String left(Level level, int n) {
        String string = level.toString();
        if (n >= string.length()) {
            return string;
        }
        return string.substring(0, n);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        stringBuilder.append(this.levelMap == null ? logEvent.getLevel().toString() : this.levelMap.get(logEvent.getLevel()));
    }

    @Override
    public String getStyleClass(Object object) {
        if (object instanceof LogEvent) {
            return "level " + ((LogEvent)object).getLevel().name().toLowerCase(Locale.ENGLISH);
        }
        return "level";
    }
}

