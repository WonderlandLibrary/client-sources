/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.StringBuilders;

@Plugin(name="MarkerPatternConverter", category="Converter")
@ConverterKeys(value={"marker"})
@PerformanceSensitive(value={"allocation"})
public final class MarkerPatternConverter
extends LogEventPatternConverter {
    private MarkerPatternConverter(String[] stringArray) {
        super("Marker", "marker");
    }

    public static MarkerPatternConverter newInstance(String[] stringArray) {
        return new MarkerPatternConverter(stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        Marker marker = logEvent.getMarker();
        if (marker != null) {
            StringBuilders.appendValue(stringBuilder, marker);
        }
    }
}

