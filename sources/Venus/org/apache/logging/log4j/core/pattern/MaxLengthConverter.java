/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.List;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="maxLength", category="Converter")
@ConverterKeys(value={"maxLength", "maxLen"})
@PerformanceSensitive(value={"allocation"})
public final class MaxLengthConverter
extends LogEventPatternConverter {
    private final List<PatternFormatter> formatters;
    private final int maxLength;

    public static MaxLengthConverter newInstance(Configuration configuration, String[] stringArray) {
        if (stringArray.length != 2) {
            LOGGER.error("Incorrect number of options on maxLength: expected 2 received {}: {}", (Object)stringArray.length, (Object)stringArray);
            return null;
        }
        if (stringArray[0] == null) {
            LOGGER.error("No pattern supplied on maxLength");
            return null;
        }
        if (stringArray[5] == null) {
            LOGGER.error("No length supplied on maxLength");
            return null;
        }
        PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        List<PatternFormatter> list = patternParser.parse(stringArray[0]);
        return new MaxLengthConverter(list, AbstractAppender.parseInt(stringArray[5], 100));
    }

    private MaxLengthConverter(List<PatternFormatter> list, int n) {
        super("MaxLength", "maxLength");
        this.maxLength = n;
        this.formatters = list;
        LOGGER.trace("new MaxLengthConverter with {}", (Object)n);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        int n = stringBuilder.length();
        for (int i = 0; i < this.formatters.size(); ++i) {
            PatternFormatter patternFormatter = this.formatters.get(i);
            patternFormatter.format(logEvent, stringBuilder);
            if (stringBuilder.length() > n + this.maxLength) break;
        }
        if (stringBuilder.length() > n + this.maxLength) {
            stringBuilder.setLength(n + this.maxLength);
            if (this.maxLength > 20) {
                stringBuilder.append("...");
            }
        }
    }
}

