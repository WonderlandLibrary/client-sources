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

    private ColumnMapping(String name, StringLayout layout, String literalValue, Class<?> type2) {
        this.name = name;
        this.layout = layout;
        this.literalValue = literalValue;
        this.type = type2;
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

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setLayout(StringLayout layout) {
            this.layout = layout;
            return this;
        }

        public Builder setPattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder setLiteral(String literal) {
            this.literal = literal;
            return this;
        }

        public Builder setType(Class<?> type2) {
            this.type = type2;
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
                LOGGER.error("No layout or literal value specified and type ({}) is not compatible with ThreadContextMap, ThreadContextStack, or java.util.Date", (Object)this.type);
                return null;
            }
            return new ColumnMapping(this.name, this.layout, this.literal, this.type);
        }
    }
}

