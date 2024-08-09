/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.pattern.AnsiConverter;
import org.apache.logging.log4j.core.pattern.AnsiEscape;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.core.util.Patterns;
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="style", category="Converter")
@ConverterKeys(value={"style"})
@PerformanceSensitive(value={"allocation"})
public final class StyleConverter
extends LogEventPatternConverter
implements AnsiConverter {
    private final List<PatternFormatter> patternFormatters;
    private final boolean noAnsi;
    private final String style;
    private final String defaultStyle;

    private StyleConverter(List<PatternFormatter> list, String string, boolean bl) {
        super("style", "style");
        this.patternFormatters = list;
        this.style = string;
        this.defaultStyle = AnsiEscape.getDefaultStyle();
        this.noAnsi = bl;
    }

    public static StyleConverter newInstance(Configuration configuration, String[] stringArray) {
        if (stringArray.length < 1) {
            LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + stringArray.length);
            return null;
        }
        if (stringArray[0] == null) {
            LOGGER.error("No pattern supplied on style");
            return null;
        }
        if (stringArray[5] == null) {
            LOGGER.error("No style attributes provided");
            return null;
        }
        PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        List<PatternFormatter> list = patternParser.parse(stringArray[0]);
        String string = AnsiEscape.createSequence(stringArray[5].split(Patterns.COMMA_SEPARATOR));
        boolean bl = Arrays.toString(stringArray).contains("disableAnsi=true");
        boolean bl2 = Arrays.toString(stringArray).contains("noConsoleNoAnsi=true");
        boolean bl3 = bl || bl2 && System.console() == null;
        return new StyleConverter(list, string, bl3);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        int n = 0;
        int n2 = 0;
        if (!this.noAnsi) {
            n = stringBuilder.length();
            stringBuilder.append(this.style);
            n2 = stringBuilder.length();
        }
        int n3 = this.patternFormatters.size();
        for (int i = 0; i < n3; ++i) {
            this.patternFormatters.get(i).format(logEvent, stringBuilder);
        }
        if (!this.noAnsi) {
            if (stringBuilder.length() == n2) {
                stringBuilder.setLength(n);
            } else {
                stringBuilder.append(this.defaultStyle);
            }
        }
    }

    @Override
    public boolean handlesThrowable() {
        for (PatternFormatter patternFormatter : this.patternFormatters) {
            if (!patternFormatter.handlesThrowable()) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[style=");
        stringBuilder.append(this.style);
        stringBuilder.append(", defaultStyle=");
        stringBuilder.append(this.defaultStyle);
        stringBuilder.append(", patternFormatters=");
        stringBuilder.append(this.patternFormatters);
        stringBuilder.append(", noAnsi=");
        stringBuilder.append(this.noAnsi);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

