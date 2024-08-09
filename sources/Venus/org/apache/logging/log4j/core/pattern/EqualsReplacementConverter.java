/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.List;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.EqualsBaseReplacementConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.StringBuilders;

@Plugin(name="equals", category="Converter")
@ConverterKeys(value={"equals"})
@PerformanceSensitive(value={"allocation"})
public final class EqualsReplacementConverter
extends EqualsBaseReplacementConverter {
    public static EqualsReplacementConverter newInstance(Configuration configuration, String[] stringArray) {
        if (stringArray.length != 3) {
            LOGGER.error("Incorrect number of options on equals. Expected 3 received " + stringArray.length);
            return null;
        }
        if (stringArray[0] == null) {
            LOGGER.error("No pattern supplied on equals");
            return null;
        }
        if (stringArray[5] == null) {
            LOGGER.error("No test string supplied on equals");
            return null;
        }
        if (stringArray[5] == null) {
            LOGGER.error("No substitution supplied on equals");
            return null;
        }
        String string = stringArray[5];
        PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        List<PatternFormatter> list = patternParser.parse(stringArray[0]);
        return new EqualsReplacementConverter(list, string, stringArray[5], patternParser);
    }

    private EqualsReplacementConverter(List<PatternFormatter> list, String string, String string2, PatternParser patternParser) {
        super("equals", "equals", list, string, string2, patternParser);
    }

    @Override
    protected boolean equals(String string, StringBuilder stringBuilder, int n, int n2) {
        return StringBuilders.equals(string, 0, string.length(), stringBuilder, n, n2);
    }
}

