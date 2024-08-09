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

    private static Map<Level, String> createLevelStyleMap(String[] stringArray) {
        if (stringArray.length < 2) {
            return DEFAULT_STYLES;
        }
        String string = stringArray[0].replaceAll("disableAnsi=(true|false)", "").replaceAll("noConsoleNoAnsi=(true|false)", "");
        Map<String, String> map = AnsiEscape.createMap(string, new String[]{STYLE_KEY});
        HashMap<Level, String> hashMap = new HashMap<Level, String>(DEFAULT_STYLES);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Object object;
            String string2 = entry.getKey().toUpperCase(Locale.ENGLISH);
            String string3 = entry.getValue();
            if (STYLE_KEY.equalsIgnoreCase(string2)) {
                object = STYLES.get(string3.toUpperCase(Locale.ENGLISH));
                if (object == null) {
                    LOGGER.error("Unknown level style: " + string3 + ". Use one of " + Arrays.toString(STYLES.keySet().toArray()));
                    continue;
                }
                hashMap.putAll((Map<Level, String>)object);
                continue;
            }
            object = Level.toLevel(string2);
            if (object == null) {
                LOGGER.error("Unknown level name: " + string2 + ". Use one of " + Arrays.toString(DEFAULT_STYLES.keySet().toArray()));
                continue;
            }
            hashMap.put((Level)object, string3);
        }
        return hashMap;
    }

    public static HighlightConverter newInstance(Configuration configuration, String[] stringArray) {
        if (stringArray.length < 1) {
            LOGGER.error("Incorrect number of options on style. Expected at least 1, received " + stringArray.length);
            return null;
        }
        if (stringArray[0] == null) {
            LOGGER.error("No pattern supplied on style");
            return null;
        }
        PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        List<PatternFormatter> list = patternParser.parse(stringArray[0]);
        boolean bl = Arrays.toString(stringArray).contains("disableAnsi=true");
        boolean bl2 = Arrays.toString(stringArray).contains("noConsoleNoAnsi=true");
        boolean bl3 = bl || bl2 && System.console() == null;
        return new HighlightConverter(list, HighlightConverter.createLevelStyleMap(stringArray), bl3);
    }

    private HighlightConverter(List<PatternFormatter> list, Map<Level, String> map, boolean bl) {
        super("style", "style");
        this.patternFormatters = list;
        this.levelStyles = map;
        this.defaultStyle = AnsiEscape.getDefaultStyle();
        this.noAnsi = bl;
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        int n;
        int n2 = 0;
        int n3 = 0;
        if (!this.noAnsi) {
            n2 = stringBuilder.length();
            stringBuilder.append(this.levelStyles.get(logEvent.getLevel()));
            n3 = stringBuilder.length();
        }
        int n4 = this.patternFormatters.size();
        for (n = 0; n < n4; ++n) {
            this.patternFormatters.get(n).format(logEvent, stringBuilder);
        }
        int n5 = n = stringBuilder.length() == n3 ? 1 : 0;
        if (!this.noAnsi) {
            if (n != 0) {
                stringBuilder.setLength(n2);
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

