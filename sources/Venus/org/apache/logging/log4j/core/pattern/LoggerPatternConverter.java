/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.NamePatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="LoggerPatternConverter", category="Converter")
@ConverterKeys(value={"c", "logger"})
@PerformanceSensitive(value={"allocation"})
public final class LoggerPatternConverter
extends NamePatternConverter {
    private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter(null);

    private LoggerPatternConverter(String[] stringArray) {
        super("Logger", "logger", stringArray);
    }

    public static LoggerPatternConverter newInstance(String[] stringArray) {
        if (stringArray == null || stringArray.length == 0) {
            return INSTANCE;
        }
        return new LoggerPatternConverter(stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        this.abbreviate(logEvent.getLoggerName(), stringBuilder);
    }
}

