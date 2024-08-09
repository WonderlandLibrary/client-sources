/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.List;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="encode", category="Converter")
@ConverterKeys(value={"enc", "encode"})
@PerformanceSensitive(value={"allocation"})
public final class EncodingPatternConverter
extends LogEventPatternConverter {
    private final List<PatternFormatter> formatters;

    private EncodingPatternConverter(List<PatternFormatter> list) {
        super("encode", "encode");
        this.formatters = list;
    }

    public static EncodingPatternConverter newInstance(Configuration configuration, String[] stringArray) {
        if (stringArray.length != 1) {
            LOGGER.error("Incorrect number of options on escape. Expected 1, received " + stringArray.length);
            return null;
        }
        if (stringArray[0] == null) {
            LOGGER.error("No pattern supplied on escape");
            return null;
        }
        PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        List<PatternFormatter> list = patternParser.parse(stringArray[0]);
        return new EncodingPatternConverter(list);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        int n;
        int n2 = stringBuilder.length();
        for (n = 0; n < this.formatters.size(); ++n) {
            this.formatters.get(n).format(logEvent, stringBuilder);
        }
        block11: for (n = stringBuilder.length() - 1; n >= n2; --n) {
            char c = stringBuilder.charAt(n);
            switch (c) {
                case '\r': {
                    stringBuilder.setCharAt(n, '\\');
                    stringBuilder.insert(n + 1, 'r');
                    continue block11;
                }
                case '\n': {
                    stringBuilder.setCharAt(n, '\\');
                    stringBuilder.insert(n + 1, 'n');
                    continue block11;
                }
                case '&': {
                    stringBuilder.setCharAt(n, '&');
                    stringBuilder.insert(n + 1, "amp;");
                    continue block11;
                }
                case '<': {
                    stringBuilder.setCharAt(n, '&');
                    stringBuilder.insert(n + 1, "lt;");
                    continue block11;
                }
                case '>': {
                    stringBuilder.setCharAt(n, '&');
                    stringBuilder.insert(n + 1, "gt;");
                    continue block11;
                }
                case '\"': {
                    stringBuilder.setCharAt(n, '&');
                    stringBuilder.insert(n + 1, "quot;");
                    continue block11;
                }
                case '\'': {
                    stringBuilder.setCharAt(n, '&');
                    stringBuilder.insert(n + 1, "apos;");
                    continue block11;
                }
                case '/': {
                    stringBuilder.setCharAt(n, '&');
                    stringBuilder.insert(n + 1, "#x2F;");
                }
            }
        }
    }
}

