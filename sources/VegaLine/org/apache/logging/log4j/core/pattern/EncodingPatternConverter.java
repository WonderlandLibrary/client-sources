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

    private EncodingPatternConverter(List<PatternFormatter> formatters) {
        super("encode", "encode");
        this.formatters = formatters;
    }

    public static EncodingPatternConverter newInstance(Configuration config, String[] options) {
        if (options.length != 1) {
            LOGGER.error("Incorrect number of options on escape. Expected 1, received " + options.length);
            return null;
        }
        if (options[0] == null) {
            LOGGER.error("No pattern supplied on escape");
            return null;
        }
        PatternParser parser = PatternLayout.createPatternParser(config);
        List<PatternFormatter> formatters = parser.parse(options[0]);
        return new EncodingPatternConverter(formatters);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        int i;
        int start = toAppendTo.length();
        for (i = 0; i < this.formatters.size(); ++i) {
            this.formatters.get(i).format(event, toAppendTo);
        }
        block11: for (i = toAppendTo.length() - 1; i >= start; --i) {
            char c = toAppendTo.charAt(i);
            switch (c) {
                case '\r': {
                    toAppendTo.setCharAt(i, '\\');
                    toAppendTo.insert(i + 1, 'r');
                    continue block11;
                }
                case '\n': {
                    toAppendTo.setCharAt(i, '\\');
                    toAppendTo.insert(i + 1, 'n');
                    continue block11;
                }
                case '&': {
                    toAppendTo.setCharAt(i, '&');
                    toAppendTo.insert(i + 1, "amp;");
                    continue block11;
                }
                case '<': {
                    toAppendTo.setCharAt(i, '&');
                    toAppendTo.insert(i + 1, "lt;");
                    continue block11;
                }
                case '>': {
                    toAppendTo.setCharAt(i, '&');
                    toAppendTo.insert(i + 1, "gt;");
                    continue block11;
                }
                case '\"': {
                    toAppendTo.setCharAt(i, '&');
                    toAppendTo.insert(i + 1, "quot;");
                    continue block11;
                }
                case '\'': {
                    toAppendTo.setCharAt(i, '&');
                    toAppendTo.insert(i + 1, "apos;");
                    continue block11;
                }
                case '/': {
                    toAppendTo.setCharAt(i, '&');
                    toAppendTo.insert(i + 1, "#x2F;");
                }
            }
        }
    }
}

