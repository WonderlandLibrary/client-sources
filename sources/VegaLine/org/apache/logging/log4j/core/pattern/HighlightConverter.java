/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.Level;
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
import org.apache.logging.log4j.util.PerformanceSensitive;

@Plugin(name="highlight", category="Converter")
@ConverterKeys(value={"highlight"})
@PerformanceSensitive(value={"allocation"})
public final class HighlightConverter
extends LogEventPatternConverter
implements AnsiConverter {
    private static final Map<Level, String> DEFAULT_STYLES = new HashMap<Level, String>();
    private static final Map<Level, String> LOGBACK_STYLES = new HashMap<Level, String>();
    private static final String STYLE_KEY = "STYLE";
    private static final String STYLE_KEY_DEFAULT = "DEFAULT";
    private static final String STYLE_KEY_LOGBACK = "LOGBACK";
    private static final Map<String, Map<Level, String>> STYLES = new HashMap<String, Map<Level, String>>();
    private final Map<Level, String> levelStyles;
    private final List<PatternFormatter> patternFormatters;
    private final boolean noAnsi;
    private final String defaultStyle;

    private static Map<Level, String> createLevelStyleMap(String[] options) {
        if (options.length < 2) {
            return DEFAULT_STYLES;
        }
        String string = options[1].replaceAll("disableAnsi=(true|false)", "").replaceAll("noConsoleNoAnsi=(true|false)", "");
        Map<String, String> styles = AnsiEscape.createMap(string, new String[]{STYLE_KEY});
        HashMap<Level, String> levelStyles = new HashMap<Level, String>(DEFAULT_STYLES);
        for (Map.Entry<String, String> entry : styles.entrySet()) {
            String key = entry.getKey().toUpperCase(Locale.ENGLISH);
            String value = entry.getValue();
            if (STYLE_KEY.equalsIgnoreCase(key)) {
                Map<Level, String> enumMap = STYLES.get(value.toUpperCase(Locale.ENGLISH));
                if (enumMap == null) {
                    LOGGER.error("Unknown level style: " + value + ". Use one of " + Arrays.toString(STYLES.keySet().toArray()));
                    continue;
                }
                levelStyles.putAll(enumMap);
                continue;
            }
            Level level = Level.toLevel(key);
            if (level == null) {
                LOGGER.error("Unknown level name: " + key + ". Use one of " + Arrays.toString(DEFAULT_STYLES.keySet().toArray()));
                continue;
            }
            levelStyles.put(level, value);
        }
        return levelStyles;
    }

    public static HighlightConverter newInstance(Configuration config, String[] options) {
        if (options.length < 1) {
            LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + options.length);
            return null;
        }
        if (options[0] == null) {
            LOGGER.error("No pattern supplied on style");
            return null;
        }
        PatternParser parser = PatternLayout.createPatternParser(config);
        List<PatternFormatter> formatters = parser.parse(options[0]);
        boolean disableAnsi = Arrays.toString(options).contains("disableAnsi=true");
        boolean noConsoleNoAnsi = Arrays.toString(options).contains("noConsoleNoAnsi=true");
        boolean hideAnsi = disableAnsi || noConsoleNoAnsi && System.console() == null;
        return new HighlightConverter(formatters, HighlightConverter.createLevelStyleMap(options), hideAnsi);
    }

    private HighlightConverter(List<PatternFormatter> patternFormatters, Map<Level, String> levelStyles, boolean noAnsi) {
        super("style", "style");
        this.patternFormatters = patternFormatters;
        this.levelStyles = levelStyles;
        this.defaultStyle = AnsiEscape.getDefaultStyle();
        this.noAnsi = noAnsi;
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        boolean empty;
        int start = 0;
        int end = 0;
        if (!this.noAnsi) {
            start = toAppendTo.length();
            toAppendTo.append(this.levelStyles.get(event.getLevel()));
            end = toAppendTo.length();
        }
        int size = this.patternFormatters.size();
        for (int i = 0; i < size; ++i) {
            this.patternFormatters.get(i).format(event, toAppendTo);
        }
        boolean bl = empty = toAppendTo.length() == end;
        if (!this.noAnsi) {
            if (empty) {
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

    static {
        DEFAULT_STYLES.put(Level.FATAL, AnsiEscape.createSequence("BRIGHT", "RED"));
        DEFAULT_STYLES.put(Level.ERROR, AnsiEscape.createSequence("BRIGHT", "RED"));
        DEFAULT_STYLES.put(Level.WARN, AnsiEscape.createSequence("YELLOW"));
        DEFAULT_STYLES.put(Level.INFO, AnsiEscape.createSequence("GREEN"));
        DEFAULT_STYLES.put(Level.DEBUG, AnsiEscape.createSequence("CYAN"));
        DEFAULT_STYLES.put(Level.TRACE, AnsiEscape.createSequence("BLACK"));
        LOGBACK_STYLES.put(Level.FATAL, AnsiEscape.createSequence("BLINK", "BRIGHT", "RED"));
        LOGBACK_STYLES.put(Level.ERROR, AnsiEscape.createSequence("BRIGHT", "RED"));
        LOGBACK_STYLES.put(Level.WARN, AnsiEscape.createSequence("RED"));
        LOGBACK_STYLES.put(Level.INFO, AnsiEscape.createSequence("BLUE"));
        LOGBACK_STYLES.put(Level.DEBUG, AnsiEscape.createSequence(null));
        LOGBACK_STYLES.put(Level.TRACE, AnsiEscape.createSequence(null));
        STYLES.put(STYLE_KEY_DEFAULT, DEFAULT_STYLES);
        STYLES.put(STYLE_KEY_LOGBACK, LOGBACK_STYLES);
    }
}

