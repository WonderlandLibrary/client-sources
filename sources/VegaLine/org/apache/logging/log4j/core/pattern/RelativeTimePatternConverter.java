/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.lang.management.ManagementFactory;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="RelativeTimePatternConverter", category="Converter")
@ConverterKeys(value={"r", "relative"})
@PerformanceSensitive(value={"allocation"})
public class RelativeTimePatternConverter
extends LogEventPatternConverter {
    private final long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();

    public RelativeTimePatternConverter() {
        super("Time", "time");
    }

    public static RelativeTimePatternConverter newInstance(String[] options) {
        return new RelativeTimePatternConverter();
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        long timestamp = event.getTimeMillis();
        toAppendTo.append(timestamp - this.startTime);
    }
}

