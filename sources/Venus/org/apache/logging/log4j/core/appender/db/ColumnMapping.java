/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.db;

import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.spi.ThreadContextStack;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.ReadOnlyStringMap;

@Plugin(name="ColumnMapping", category="Core", printObject=true)
public class ColumnMapping {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final String name;
    private final StringLayout layout;
    private final String literalValue;
    private final Class<?> type;

    private ColumnMapping(String string, StringLayout stringLayout, String string2, Class<?> clazz) {
        this.name = string;
        this.layout = stringLayout;
        this.literalValue = string2;
        this.type = clazz;
    }

    public String getName() {
        return this.name;
    }

    public StringLayout getLayout() {
        return this.layout;
    }

    public String getLiteralValue() {
        return this.literalValue;
    }

    public Class<?> getType() {
        return this.type;
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    static Logger access$000() {
        return LOGGER;
    }

    ColumnMapping(String string, StringLayout stringLayout, String string2, Class clazz, 1 var5_5) {
        this(string, stringLayout, string2, clazz);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<ColumnMapping> {
        @PluginBuilderAttribute
        @Required(message="No column name provided")
        private String name;
        @PluginElement(value="Layout")
        private StringLayout layout;
        @PluginBuilderAttribute
        private String pattern;
        @PluginBuilderAttribute
        private String literal;
        @PluginBuilderAttribute
        @Required(message="No conversion type provided")
        private Class<?> type = String.class;
        @PluginConfiguration
        private Configuration configuration;

        public Builder setName(String string) {
            this.name = string;
            return this;
        }

        public Builder setLayout(StringLayout stringLayout) {
            this.layout = stringLayout;
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

        public Builder setType(Class<?> clazz) {
            this.type = clazz;
            return this;
        }

        public Builder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        @Override
        public ColumnMapping build() {
            if (this.pattern != null) {
                this.layout = PatternLayout.newBuilder().withPattern(this.pattern).withConfiguration(this.configuration).build();
            }
            if (!(this.layout != null || this.literal != null || Date.class.isAssignableFrom(this.type) || ReadOnlyStringMap.class.isAssignableFrom(this.type) || ThreadContextMap.class.isAssignableFrom(this.type) || ThreadContextStack.class.isAssignableFrom(this.type))) {
                ColumnMapping.access$000().error("No layout or literal value specified and type ({}) is not compatible with ThreadContextMap, ThreadContextStack, or java.util.Date", (Object)this.type);
                return null;
            }
            return new ColumnMapping(this.name, this.layout, this.literal, this.type, null);
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

