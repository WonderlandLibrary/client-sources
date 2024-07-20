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

    private StyleConverter(List<PatternFormatter> patternFormatters, String style, boolean noAnsi) {
        super("style", "style");
        this.patternFormatters = patternFormatters;
        this.style = style;
        this.defaultStyle = AnsiEscape.getDefaultStyle();
        this.noAnsi = noAnsi;
    }

    public static StyleConverter newInstance(Configuration config, String[] options) {
        if (options.length < 1) {
            LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + options.length);
            return null;
        }
        if (options[0] == null) {
            LOGGER.error("No pattern supplied on style");
            return null;
        }
        if (options[1] == null) {
            LOGGER.error("No style attributes provided");
            return null;
        }
        PatternParser parser = PatternLayout.createPatternParser(config);
        List<PatternFormatter> formatters = parser.parse(options[0]);
        String style = AnsiEscape.createSequence(options[1].split(Patterns.COMMA_SEPARATOR));
        boolean disableAnsi = Arrays.toString(options).contains("disableAnsi=true");
        boolean noConsoleNoAnsi = Arrays.toString(options).contains("noConsoleNoAnsi=true");
        boolean hideAnsi = disableAnsi || noConsoleNoAnsi && System.console() == null;
        return new StyleConverter(formatters, style, hideAnsi);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        int start = 0;
        int end = 0;
        if (!this.noAnsi) {
            start = toAppendTo.length();
            toAppendTo.append(this.style);
            end = toAppendTo.length();
        }
        int size = this.patternFormatters.size();
        for (int i = 0; i < size; ++i) {
            this.patternFormatters.get(i).format(event, toAppendTo);
        }
        if (!this.noAnsi) {
            if (toAppendTo.length() == end) {
                toAppendTo.setLength(start);
            } else {
                toAppendTo.append(this.defaultStyle);
            }
        }
    }

    @Override
    public boolean handlesThrowable() {
        for (PatternFormatter formatter : this.patternFormatters) {
            if (!formatter.handlesThrowable()) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[style=");
        sb.append(this.style);
        sb.append(", defaultStyle=");
        sb.append(this.defaultStyle);
        sb.append(", patternFormatters=");
        sb.append(this.patternFormatters);
        sb.append(", noAnsi=");
        sb.append(this.noAnsi);
        sb.append(']');
        return sb.toString();
    }
}

