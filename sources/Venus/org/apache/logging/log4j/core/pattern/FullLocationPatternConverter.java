/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

@Plugin(name="FullLocationPatternConverter", category="Converter")
@ConverterKeys(value={"l", "location"})
public final class FullLocationPatternConverter
extends LogEventPatternConverter {
    private static final FullLocationPatternConverter INSTANCE = new FullLocationPatternConverter();

    private FullLocationPatternConverter() {
        super("Full Location", "fullLocation");
    }

    public static FullLocationPatternConverter newInstance(String[] stringArray) {
        return INSTANCE;
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        StackTraceElement stackTraceElement = logEvent.getSource();
        if (stackTraceElement != null) {
            stringBuilder.append(stackTraceElement.toString());
        }
    }
}

