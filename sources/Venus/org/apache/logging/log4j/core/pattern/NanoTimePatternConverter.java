/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="NanoTimePatternConverter", category="Converter")
@ConverterKeys(value={"N", "nano"})
@PerformanceSensitive(value={"allocation"})
public final class NanoTimePatternConverter
extends LogEventPatternConverter {
    private NanoTimePatternConverter(String[] stringArray) {
        super("Nanotime", "nanotime");
    }

    public static NanoTimePatternConverter newInstance(String[] stringArray) {
        return new NanoTimePatternConverter(stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        stringBuilder.append(logEvent.getNanoTime());
    }
}

