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

@Plugin(name="MarkerNamePatternConverter", category="Converter")
@ConverterKeys(value={"markerSimpleName"})
@PerformanceSensitive(value={"allocation"})
public final class MarkerSimpleNamePatternConverter
extends LogEventPatternConverter {
    private MarkerSimpleNamePatternConverter(String[] stringArray) {
        super("MarkerSimpleName", "markerSimpleName");
    }

    public static MarkerSimpleNamePatternConverter newInstance(String[] stringArray) {
        return new MarkerSimpleNamePatternConverter(stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        Marker marker = logEvent.getMarker();
        if (marker != null) {
            stringBuilder.append(marker.getName());
        }
    }
}

