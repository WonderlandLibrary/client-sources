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

    public static LevelPatternConverter newInstance(String[] options) {
        if (options == null || options.length == 0) {
            return INSTANCE;
        }
        HashMap<Level, String> levelMap = new HashMap<Level, String>();
        int length = Integer.MAX_VALUE;
        boolean lowerCase = false;
        String[] definitions = options[0].split(Patterns.COMMA_SEPARATOR);
        for (String def : definitions) {
            String[] pair = def.split("=");
            if (pair == null || pair.length != 2) {
                LOGGER.error("Invalid option {}", (Object)def);
                continue;
            }
            String key = pair[0].trim();
            String value = pair[1].trim();
            if (OPTION_LENGTH.equalsIgnoreCase(key)) {
                length = Integer.parseInt(value);
                continue;
            }
            if (OPTION_LOWER.equalsIgnoreCase(key)) {
                lowerCase = Boolean.parseBoolean(value);
                continue;
            }
            Level level = Level.toLevel(key, null);
            if (level == null) {
                LOGGER.error("Invalid Level {}", (Object)key);
                continue;
            }
            levelMap.put(level, value);
        }
        if (levelMap.isEmpty() && length == Integer.MAX_VALUE && !lowerCase) {
            return INSTANCE;
        }
        for (Level level : Level.values()) {
            if (levelMap.containsKey(level)) continue;
            String left = LevelPatternConverter.left(level, length);
            levelMap.put(level, lowerCase ? left.toLowerCase(Locale.US) : left);
        }
        return new LevelPatternConverter(levelMap);
    }

    private static String left(Level level, int length) {
        String string = level.toString();
        if (length >= string.length()) {
            return string;
        }
        return string.substring(0, length);
    }

    @Override
    public void format(LogEvent event, StringBuilder output) {
        output.append(this.levelMap == null ? event.getLevel().toString() : this.levelMap.get(event.getLevel()));
    }

    @Override
    public String getStyleClass(Object e) {
        if (e instanceof LogEvent) {
            return "level " + ((LogEvent)e).getLevel().name().toLowerCase(Locale.ENGLISH);
        }
        return "level";
    }
}

