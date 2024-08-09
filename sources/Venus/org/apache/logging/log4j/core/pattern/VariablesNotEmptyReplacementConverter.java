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

@Plugin(name="notEmpty", category="Converter")
@ConverterKeys(value={"notEmpty", "varsNotEmpty", "variablesNotEmpty"})
@PerformanceSensitive(value={"allocation"})
public final class VariablesNotEmptyReplacementConverter
extends LogEventPatternConverter {
    private final List<PatternFormatter> formatters;

    private VariablesNotEmptyReplacementConverter(List<PatternFormatter> list) {
        super("notEmpty", "notEmpty");
        this.formatters = list;
    }

    public static VariablesNotEmptyReplacementConverter newInstance(Configuration configuration, String[] stringArray) {
        if (stringArray.length != 1) {
            LOGGER.error("Incorrect number of options on varsNotEmpty. Expected 1 received " + stringArray.length);
            return null;
        }
        if (stringArray[0] == null) {
            LOGGER.error("No pattern supplied on varsNotEmpty");
            return null;
        }
        PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        List<PatternFormatter> list = patternParser.parse(stringArray[0]);
        return new VariablesNotEmptyReplacementConverter(list);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        int n = stringBuilder.length();
        boolean bl = true;
        boolean bl2 = false;
        for (int i = 0; i < this.formatters.size(); ++i) {
            PatternFormatter patternFormatter = this.formatters.get(i);
            int n2 = stringBuilder.length();
            patternFormatter.format(logEvent, stringBuilder);
            if (!patternFormatter.getConverter().isVariable()) continue;
            bl2 = true;
            bl = bl && stringBuilder.length() == n2;
        }
        if (!bl2 || bl) {
            stringBuilder.setLength(n);
        }
    }
}

