/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.db.jdbc;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.util.Booleans;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Strings;

@Plugin(name="Column", category="Core", printObject=true)
public final class ColumnConfig {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final String columnName;
    private final PatternLayout layout;
    private final String literalValue;
    private final boolean eventTimestamp;
    private final boolean unicode;
    private final boolean clob;

    private ColumnConfig(String string, PatternLayout patternLayout, String string2, boolean bl, boolean bl2, boolean bl3) {
        this.columnName = string;
        this.layout = patternLayout;
        this.literalValue = string2;
        this.eventTimestamp = bl;
        this.unicode = bl2;
        this.clob = bl3;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public PatternLayout getLayout() {
        return this.layout;
    }

    public String getLiteralValue() {
        return this.literalValue;
    }

    public boolean isEventTimestamp() {
        return this.eventTimestamp;
    }

    public boolean isUnicode() {
        return this.unicode;
    }

    public boolean isClob() {
        return this.clob;
    }

    public String toString() {
        return "{ name=" + this.columnName + ", layout=" + this.layout + ", literal=" + this.literalValue + ", timestamp=" + this.eventTimestamp + " }";
    }

    @Deprecated
    public static ColumnConfig createColumnConfig(Configuration configuration, String string, String string2, String string3, String string4, String string5, String string6) {
        if (Strings.isEmpty(string)) {
            LOGGER.error("The column config is not valid because it does not contain a column name.");
            return null;
        }
        boolean bl = Boolean.parseBoolean(string4);
        boolean bl2 = Booleans.parseBoolean(string5, true);
        boolean bl3 = Boolean.parseBoolean(string6);
        return ColumnConfig.newBuilder().setConfiguration(configuration).setName(string).setPattern(string2).setLiteral(string3).setEventTimestamp(bl).setUnicode(bl2).setClob(bl3).build();
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    static Logger access$000() {
        return LOGGER;
    }

    ColumnConfig(String string, PatternLayout patternLayout, String string2, boolean bl, boolean bl2, boolean bl3, 1 var7_7) {
        this(string, patternLayout, string2, bl, bl2, bl3);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<ColumnConfig> {
        @PluginConfiguration
        private Configuration configuration;
        @PluginBuilderAttribute
        @Required(message="No name provided")
        private String name;
        @PluginBuilderAttribute
        private String pattern;
        @PluginBuilderAttribute
        private String literal;
        @PluginBuilderAttribute
        private boolean isEventTimestamp;
        @PluginBuilderAttribute
        private boolean isUnicode = true;
        @PluginBuilderAttribute
        private boolean isClob;

        public Builder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder setName(String string) {
            this.name = string;
            return this;
        }

        public Builder setPattern(String string) {
            this.pattern = string;
            return this;
        }

        public Builder setLiteral(String string) {
            this.literal = string;
            return this;
        }

        public Builder setEventTimestamp(boolean bl) {
            this.isEventTimestamp = bl;
            return this;
        }

        public Builder setUnicode(boolean bl) {
            this.isUnicode = bl;
            return this;
        }

        public Builder setClob(boolean bl) {
            this.isClob = bl;
            return this;
        }

        @Override
        public ColumnConfig build() {
            if (Strings.isEmpty(this.name)) {
                ColumnConfig.access$000().error("The column config is not valid because it does not contain a column name.");
                return null;
            }
            boolean bl = Strings.isNotEmpty(this.pattern);
            boolean bl2 = Strings.isNotEmpty(this.literal);
            if (bl && bl2 || bl && this.isEventTimestamp || bl2 && this.isEventTimestamp) {
                ColumnConfig.access$000().error("The pattern, literal, and isEventTimestamp attributes are mutually exclusive.");
                return null;
            }
            if (this.isEventTimestamp) {
                return new ColumnConfig(this.name, null, null, true, false, false, null);
            }
            if (bl2) {
                return new ColumnConfig(this.name, null, this.literal, false, false, false, null);
            }
            if (bl) {
                PatternLayout patternLayout = PatternLayout.newBuilder().withPattern(this.pattern).withConfiguration(this.configuration).withAlwaysWriteExceptions(true).build();
                return new ColumnConfig(this.name, patternLayout, null, false, this.isUnicode, this.isClob, null);
            }
            ColumnConfig.access$000().error("To configure a column you must specify a pattern or literal or set isEventDate to true.");
            return null;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

