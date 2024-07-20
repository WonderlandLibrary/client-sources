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

    private LoggerPatternConverter(String[] options) {
        super("Logger", "logger", options);
    }

    public static LoggerPatternConverter newInstance(String[] options) {
        if (options == null || options.length == 0) {
            return INSTANCE;
        }
        return new LoggerPatternConverter(options);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        this.abbreviate(event.getLoggerName(), toAppendTo);
    }
}

