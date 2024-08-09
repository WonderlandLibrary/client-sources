/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.core.layout.Encoder;
import org.apache.logging.log4j.core.layout.PatternSelector;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.core.pattern.RegexReplacement;
import org.apache.logging.log4j.util.Strings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Plugin(name="PatternLayout", category="Core", elementType="layout", printObject=true)
public final class PatternLayout
extends AbstractStringLayout {
    public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
    public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %notEmpty{%x }- %m%n";
    public static final String SIMPLE_CONVERSION_PATTERN = "%d [%t] %p %c - %m%n";
    public static final String KEY = "Converter";
    private final String conversionPattern;
    private final PatternSelector patternSelector;
    private final AbstractStringLayout.Serializer eventSerializer;

    private PatternLayout(Configuration configuration, RegexReplacement regexReplacement, String string, PatternSelector patternSelector, Charset charset, boolean bl, boolean bl2, boolean bl3, String string2, String string3) {
        super(configuration, charset, PatternLayout.newSerializerBuilder().setConfiguration(configuration).setReplace(regexReplacement).setPatternSelector(patternSelector).setAlwaysWriteExceptions(bl).setDisableAnsi(bl2).setNoConsoleNoAnsi(bl3).setPattern(string2).build(), PatternLayout.newSerializerBuilder().setConfiguration(configuration).setReplace(regexReplacement).setPatternSelector(patternSelector).setAlwaysWriteExceptions(bl).setDisableAnsi(bl2).setNoConsoleNoAnsi(bl3).setPattern(string3).build());
        this.conversionPattern = string;
        this.patternSelector = patternSelector;
        this.eventSerializer = PatternLayout.newSerializerBuilder().setConfiguration(configuration).setReplace(regexReplacement).setPatternSelector(patternSelector).setAlwaysWriteExceptions(bl).setDisableAnsi(bl2).setNoConsoleNoAnsi(bl3).setPattern(string).setDefaultPattern(DEFAULT_CONVERSION_PATTERN).build();
    }

    public static SerializerBuilder newSerializerBuilder() {
        return new SerializerBuilder();
    }

    @Deprecated
    public static AbstractStringLayout.Serializer createSerializer(Configuration configuration, RegexReplacement regexReplacement, String string, String string2, PatternSelector patternSelector, boolean bl, boolean bl2) {
        SerializerBuilder serializerBuilder = PatternLayout.newSerializerBuilder();
        serializerBuilder.setAlwaysWriteExceptions(bl);
        serializerBuilder.setConfiguration(configuration);
        serializerBuilder.setDefaultPattern(string2);
        serializerBuilder.setNoConsoleNoAnsi(bl2);
        serializerBuilder.setPattern(string);
        serializerBuilder.setPatternSelector(patternSelector);
        serializerBuilder.setReplace(regexReplacement);
        return serializerBuilder.build();
    }

    public String getConversionPattern() {
        return this.conversionPattern;
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("structured", "false");
        hashMap.put("formatType", "conversion");
        hashMap.put("format", this.conversionPattern);
        return hashMap;
    }

    @Override
    public String toSerializable(LogEvent logEvent) {
        return this.eventSerializer.toSerializable(logEvent);
    }

    @Override
    public void encode(LogEvent logEvent, ByteBufferDestination byteBufferDestination) {
        if (!(this.eventSerializer instanceof AbstractStringLayout.Serializer2)) {
            super.encode(logEvent, byteBufferDestination);
            return;
        }
        StringBuilder stringBuilder = this.toText((AbstractStringLayout.Serializer2)((Object)this.eventSerializer), logEvent, PatternLayout.getStringBuilder());
        Encoder<StringBuilder> encoder = this.getStringBuilderEncoder();
        encoder.encode(stringBuilder, byteBufferDestination);
        PatternLayout.trimToMaxSize(stringBuilder);
    }

    private StringBuilder toText(AbstractStringLayout.Serializer2 serializer2, LogEvent logEvent, StringBuilder stringBuilder) {
        return serializer2.toSerializable(logEvent, stringBuilder);
    }

    public static PatternParser createPatternParser(Configuration configuration) {
        if (configuration == null) {
            return new PatternParser(configuration, KEY, LogEventPatternConverter.class);
        }
        PatternParser patternParser = (PatternParser)configuration.getComponent(KEY);
        if (patternParser == null) {
            patternParser = new PatternParser(configuration, KEY, LogEventPatternConverter.class);
            configuration.addComponent(KEY, patternParser);
            patternParser = (PatternParser)configuration.getComponent(KEY);
        }
        return patternParser;
    }

    public String toString() {
        return this.patternSelector == null ? this.conversionPattern : this.patternSelector.toString();
    }

    @PluginFactory
    @Deprecated
    public static PatternLayout createLayout(@PluginAttribute(value="pattern", defaultString="%m%n") String string, @PluginElement(value="PatternSelector") PatternSelector patternSelector, @PluginConfiguration Configuration configuration, @PluginElement(value="Replace") RegexReplacement regexReplacement, @PluginAttribute(value="charset") Charset charset, @PluginAttribute(value="alwaysWriteExceptions", defaultBoolean=true) boolean bl, @PluginAttribute(value="noConsoleNoAnsi") boolean bl2, @PluginAttribute(value="header") String string2, @PluginAttribute(value="footer") String string3) {
        return PatternLayout.newBuilder().withPattern(string).withPatternSelector(patternSelector).withConfiguration(configuration).withRegexReplacement(regexReplacement).withCharset(charset).withAlwaysWriteExceptions(bl).withNoConsoleNoAnsi(bl2).withHeader(string2).withFooter(string3).build();
    }

    public static PatternLayout createDefaultLayout() {
        return PatternLayout.newBuilder().build();
    }

    public static PatternLayout createDefaultLayout(Configuration configuration) {
        return PatternLayout.newBuilder().withConfiguration(configuration).build();
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder(null);
    }

    @Override
    public Serializable toSerializable(LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }

    @Override
    public void encode(Object object, ByteBufferDestination byteBufferDestination) {
        this.encode((LogEvent)object, byteBufferDestination);
    }

    PatternLayout(Configuration configuration, RegexReplacement regexReplacement, String string, PatternSelector patternSelector, Charset charset, boolean bl, boolean bl2, boolean bl3, String string2, String string3, 1 var11_11) {
        this(configuration, regexReplacement, string, patternSelector, charset, bl, bl2, bl3, string2, string3);
    }

    static class 1 {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    implements org.apache.logging.log4j.core.util.Builder<PatternLayout> {
        @PluginBuilderAttribute
        private String pattern = "%m%n";
        @PluginElement(value="PatternSelector")
        private PatternSelector patternSelector;
        @PluginConfiguration
        private Configuration configuration;
        @PluginElement(value="Replace")
        private RegexReplacement regexReplacement;
        @PluginBuilderAttribute
        private Charset charset = Charset.defaultCharset();
        @PluginBuilderAttribute
        private boolean alwaysWriteExceptions = true;
        @PluginBuilderAttribute
        private boolean disableAnsi;
        @PluginBuilderAttribute
        private boolean noConsoleNoAnsi;
        @PluginBuilderAttribute
        private String header;
        @PluginBuilderAttribute
        private String footer;

        private Builder() {
        }

        public Builder withPattern(String string) {
            this.pattern = string;
            return this;
        }

        public Builder withPatternSelector(PatternSelector patternSelector) {
            this.patternSelector = patternSelector;
            return this;
        }

        public Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public Builder withRegexReplacement(RegexReplacement regexReplacement) {
            this.regexReplacement = regexReplacement;
            return this;
        }

        public Builder withCharset(Charset charset) {
            if (charset != null) {
                this.charset = charset;
            }
            return this;
        }

        public Builder withAlwaysWriteExceptions(boolean bl) {
            this.alwaysWriteExceptions = bl;
            return this;
        }

        public Builder withDisableAnsi(boolean bl) {
            this.disableAnsi = bl;
            return this;
        }

        public Builder withNoConsoleNoAnsi(boolean bl) {
            this.noConsoleNoAnsi = bl;
            return this;
        }

        public Builder withHeader(String string) {
            this.header = string;
            return this;
        }

        public Builder withFooter(String string) {
            this.footer = string;
            return this;
        }

        @Override
        public PatternLayout build() {
            if (this.configuration == null) {
                this.configuration = new DefaultConfiguration();
            }
            return new PatternLayout(this.configuration, this.regexReplacement, this.pattern, this.patternSelector, this.charset, this.alwaysWriteExceptions, this.disableAnsi, this.noConsoleNoAnsi, this.header, this.footer, null);
        }

        @Override
        public Object build() {
            return this.build();
        }

        Builder(1 var1_1) {
            this();
        }
    }

    private static class PatternSelectorSerializer
    implements AbstractStringLayout.Serializer,
    AbstractStringLayout.Serializer2 {
        private final PatternSelector patternSelector;
        private final RegexReplacement replace;

        private PatternSelectorSerializer(PatternSelector patternSelector, RegexReplacement regexReplacement) {
            this.patternSelector = patternSelector;
            this.replace = regexReplacement;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String toSerializable(LogEvent logEvent) {
            StringBuilder stringBuilder = AbstractStringLayout.getStringBuilder();
            try {
                String string = this.toSerializable(logEvent, stringBuilder).toString();
                return string;
            } finally {
                AbstractStringLayout.trimToMaxSize(stringBuilder);
            }
        }

        @Override
        public StringBuilder toSerializable(LogEvent logEvent, StringBuilder stringBuilder) {
            PatternFormatter[] patternFormatterArray = this.patternSelector.getFormatters(logEvent);
            int n = patternFormatterArray.length;
            for (int i = 0; i < n; ++i) {
                patternFormatterArray[i].format(logEvent, stringBuilder);
            }
            if (this.replace != null) {
                String string = stringBuilder.toString();
                string = this.replace.format(string);
                stringBuilder.setLength(0);
                stringBuilder.append(string);
            }
            return stringBuilder;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append("[patternSelector=");
            stringBuilder.append(this.patternSelector);
            stringBuilder.append(", replace=");
            stringBuilder.append(this.replace);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        PatternSelectorSerializer(PatternSelector patternSelector, RegexReplacement regexReplacement, 1 var3_3) {
            this(patternSelector, regexReplacement);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class SerializerBuilder
    implements org.apache.logging.log4j.core.util.Builder<AbstractStringLayout.Serializer> {
        private Configuration configuration;
        private RegexReplacement replace;
        private String pattern;
        private String defaultPattern;
        private PatternSelector patternSelector;
        private boolean alwaysWriteExceptions;
        private boolean disableAnsi;
        private boolean noConsoleNoAnsi;

        @Override
        public AbstractStringLayout.Serializer build() {
            if (Strings.isEmpty(this.pattern) && Strings.isEmpty(this.defaultPattern)) {
                return null;
            }
            if (this.patternSelector == null) {
                try {
                    PatternParser patternParser = PatternLayout.createPatternParser(this.configuration);
                    List<PatternFormatter> list = patternParser.parse(this.pattern == null ? this.defaultPattern : this.pattern, this.alwaysWriteExceptions, this.disableAnsi, this.noConsoleNoAnsi);
                    PatternFormatter[] patternFormatterArray = list.toArray(new PatternFormatter[0]);
                    return new PatternSerializer(patternFormatterArray, this.replace, null);
                } catch (RuntimeException runtimeException) {
                    throw new IllegalArgumentException("Cannot parse pattern '" + this.pattern + "'", runtimeException);
                }
            }
            return new PatternSelectorSerializer(this.patternSelector, this.replace, null);
        }

        public SerializerBuilder setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public SerializerBuilder setReplace(RegexReplacement regexReplacement) {
            this.replace = regexReplacement;
            return this;
        }

        public SerializerBuilder setPattern(String string) {
            this.pattern = string;
            return this;
        }

        public SerializerBuilder setDefaultPattern(String string) {
            this.defaultPattern = string;
            return this;
        }

        public SerializerBuilder setPatternSelector(PatternSelector patternSelector) {
            this.patternSelector = patternSelector;
            return this;
        }

        public SerializerBuilder setAlwaysWriteExceptions(boolean bl) {
            this.alwaysWriteExceptions = bl;
            return this;
        }

        public SerializerBuilder setDisableAnsi(boolean bl) {
            this.disableAnsi = bl;
            return this;
        }

        public SerializerBuilder setNoConsoleNoAnsi(boolean bl) {
            this.noConsoleNoAnsi = bl;
            return this;
        }

        @Override
        public Object build() {
            return this.build();
        }
    }

    private static class PatternSerializer
    implements AbstractStringLayout.Serializer,
    AbstractStringLayout.Serializer2 {
        private final PatternFormatter[] formatters;
        private final RegexReplacement replace;

        private PatternSerializer(PatternFormatter[] patternFormatterArray, RegexReplacement regexReplacement) {
            this.formatters = patternFormatterArray;
            this.replace = regexReplacement;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String toSerializable(LogEvent logEvent) {
            StringBuilder stringBuilder = AbstractStringLayout.getStringBuilder();
            try {
                String string = this.toSerializable(logEvent, stringBuilder).toString();
                return string;
            } finally {
                AbstractStringLayout.trimToMaxSize(stringBuilder);
            }
        }

        @Override
        public StringBuilder toSerializable(LogEvent logEvent, StringBuilder stringBuilder) {
            int n = this.formatters.length;
            for (int i = 0; i < n; ++i) {
                this.formatters[i].format(logEvent, stringBuilder);
            }
            if (this.replace != null) {
                String string = stringBuilder.toString();
                string = this.replace.format(string);
                stringBuilder.setLength(0);
                stringBuilder.append(string);
            }
            return stringBuilder;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append("[formatters=");
            stringBuilder.append(Arrays.toString(this.formatters));
            stringBuilder.append(", replace=");
            stringBuilder.append(this.replace);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        PatternSerializer(PatternFormatter[] patternFormatterArray, RegexReplacement regexReplacement, 1 var3_3) {
            this(patternFormatterArray, regexReplacement);
        }
    }
}

