/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.layout.PatternMatch;
import org.apache.logging.log4j.core.layout.PatternSelector;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="MarkerPatternSelector", category="Core", elementType="patternSelector", printObject=true)
public class MarkerPatternSelector
implements PatternSelector {
    private final Map<String, PatternFormatter[]> formatterMap = new HashMap<String, PatternFormatter[]>();
    private final Map<String, String> patternMap = new HashMap<String, String>();
    private final PatternFormatter[] defaultFormatters;
    private final String defaultPattern;
    private static Logger LOGGER = StatusLogger.getLogger();

    @Deprecated
    public MarkerPatternSelector(PatternMatch[] patternMatchArray, String string, boolean bl, boolean bl2, Configuration configuration) {
        this(patternMatchArray, string, bl, false, bl2, configuration);
    }

    private MarkerPatternSelector(PatternMatch[] patternMatchArray, String string, boolean bl, boolean bl2, boolean bl3, Configuration configuration) {
        PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        for (PatternMatch patternMatch : patternMatchArray) {
            try {
                List<PatternFormatter> list = patternParser.parse(patternMatch.getPattern(), bl, bl2, bl3);
                this.formatterMap.put(patternMatch.getKey(), list.toArray(new PatternFormatter[list.size()]));
                this.patternMap.put(patternMatch.getKey(), patternMatch.getPattern());
            } catch (RuntimeException runtimeException) {
                throw new IllegalArgumentException("Cannot parse pattern '" + patternMatch.getPattern() + "'", runtimeException);
            }
        }
        try {
            List<PatternFormatter> list = patternParser.parse(string, bl, bl2, bl3);
            this.defaultFormatters = list.toArray(new PatternFormatter[list.size()]);
            this.defaultPattern = string;
        } catch (RuntimeException runtimeException) {
            throw new IllegalArgumentException("Cannot parse pattern '" + string + "'", runtimeException);
        }
    }

    @Override
    public PatternFormatter[] getFormatters(LogEvent logEvent) {
        Marker marker = logEvent.getMarker();
        if (marker == null) {
            return this.defaultFormatters;
        }
        for (String string : this.formatterMap.keySet()) {
            if (!marker.isInstanceOf(string)) continue;
            return this.formatterMap.get(string);
        }
        return this.defaultFormatters;
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    @Deprecated
    public static MarkerPatternSelector createSelector(PatternMatch[] patternMatchArray, String string, boolean bl, boolean bl2, Configuration configuration) {
        Builder builder = MarkerPatternSelector.newBuilder();
        builder.setProperties(patternMatchArray);
        builder.setDefaultPattern(string);
        builder.setAlwaysWriteExceptions(bl);
        builder.setNoConsoleNoAnsi(bl2);
        builder.setConfiguration(configuration);
        return builder.build();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = true;
        for (Map.Entry<String, String> entry : this.patternMap.entrySet()) {
            if (!bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("key=\"").append(entry.getKey()).append("\", pattern=\"").append(entry.getValue()).append("\"");
            bl = false;
        }
        if (!bl) {
            stringBuilder.append(", ");
        }
        stringBuilder.append("default=\"").append(this.defaultPattern).append("\"");
        return stringBuilder.toString();
    }

    static Logger access$000() {
        return LOGGER;
    }

    MarkerPatternSelector(PatternMatch[] patternMatchArray, String string, boolean bl, boolean bl2, boolean bl3, Configuration configuration, 1 var7_7) {
        this(patternMatchArray, string, bl, bl2, bl3, configuration);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<MarkerPatternSelector> {
        @PluginElement(value="PatternMatch")
        private PatternMatch[] properties;
        @PluginBuilderAttribute(value="defaultPattern")
        private String defaultPattern;
        @PluginBuilderAttribute(value="alwaysWriteExceptions")
        private boolean alwaysWriteExceptions = true;
        @PluginBuilderAttribute(value="disableAnsi")
        private boolean disableAnsi;
        @PluginBuilderAttribute(value="noConsoleNoAnsi")
        private boolean noConsoleNoAnsi;
        @PluginConfiguration
        private Configuration configuration;

        @Override
        public MarkerPatternSelector build() {
            if (this.defaultPattern == null) {
                this.defaultPattern = "%m%n";
            }
            if (this.properties == null || this.properties.length == 0) {
                MarkerPatternSelector.access$000().warn("No marker patterns were provided with PatternMatch");
                return null;
            }
            return new MarkerPatternSelector(this.properties, this.defaultPattern, this.alwaysWriteExceptions, this.disableAnsi, this.noConsoleNoAnsi, this.configuration, null);
        }

        public Builder setProperties(PatternMatch[] patternMatchArray) {
            this.properties = patternMatchArray;
            return this;
        }

        public Builder setDefaultPattern(String string) {
            this.defaultPattern = string;
            return this;
        }

        public Builder setAlwaysWriteExceptions(boolean bl) {
            this.alwaysWriteExceptions = bl;
            return this;
        }

        public Builder setDisableAnsi(boolean bl) {
            this.disableAnsi = bl;
            return this;
        }

        public Builder setNoConsoleNoAnsi(boolean bl) {
            this.noConsoleNoAnsi = bl;
            return this;
        }

        public Builder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

