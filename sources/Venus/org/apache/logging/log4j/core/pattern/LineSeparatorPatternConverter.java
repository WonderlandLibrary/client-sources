/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="LineSeparatorPatternConverter", category="Converter")
@ConverterKeys(value={"n"})
@PerformanceSensitive(value={"allocation"})
public final class LineSeparatorPatternConverter
extends LogEventPatternConverter {
    private static final LineSeparatorPatternConverter INSTANCE = new LineSeparatorPatternConverter();
    private final String lineSep = Strings.LINE_SEPARATOR;

    private LineSeparatorPatternConverter() {
        super("Line Sep", "lineSep");
    }

    public static LineSeparatorPatternConverter newInstance(String[] stringArray) {
        return INSTANCE;
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        stringBuilder.append(this.lineSep);
    }
}

