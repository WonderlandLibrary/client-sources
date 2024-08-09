/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.layout.AbstractJacksonLayout;
import org.apache.logging.log4j.core.layout.JacksonFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name="JsonLayout", category="Core", elementType="layout", printObject=true)
public final class JsonLayout
extends AbstractJacksonLayout {
    private static final String DEFAULT_FOOTER = "]";
    private static final String DEFAULT_HEADER = "[";
    static final String CONTENT_TYPE = "application/json";

    protected JsonLayout(Configuration configuration, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, String string, String string2, Charset charset, boolean bl7) {
        super(configuration, new JacksonFactory.JSON(bl3, bl7).newWriter(bl, bl2, bl5), charset, bl5, bl4, bl6, PatternLayout.newSerializerBuilder().setConfiguration(configuration).setPattern(string).setDefaultPattern(DEFAULT_HEADER).build(), PatternLayout.newSerializerBuilder().setConfiguration(configuration).setPattern(string2).setDefaultPattern(DEFAULT_FOOTER).build());
    }

    @Override
    public byte[] getHeader() {
        if (!this.complete) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.serializeToString(this.getHeaderSerializer());
        if (string != null) {
            stringBuilder.append(string);
        }
        stringBuilder.append(this.eol);
        return this.getBytes(stringBuilder.toString());
    }

    @Override
    public byte[] getFooter() {
        if (!this.complete) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.eol);
        String string = this.serializeToString(this.getFooterSerializer());
        if (string != null) {
            stringBuilder.append(string);
        }
        stringBuilder.append(this.eol);
        return this.getBytes(stringBuilder.toString());
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("version", "2.0");
        return hashMap;
    }

    @Override
    public String getContentType() {
        return "application/json; charset=" + this.getCharset();
    }

    @Deprecated
    public static JsonLayout createLayout(@PluginConfiguration Configuration configuration, @PluginAttribute(value="locationInfo") boolean bl, @PluginAttribute(value="properties") boolean bl2, @PluginAttribute(value="propertiesAsList") boolean bl3, @PluginAttribute(value="complete") boolean bl4, @PluginAttribute(value="compact") boolean bl5, @PluginAttribute(value="eventEol") boolean bl6, @PluginAttribute(value="header", defaultString="[") String string, @PluginAttribute(value="footer", defaultString="]") String string2, @PluginAttribute(value="charset", defaultString="UTF-8") Charset charset, @PluginAttribute(value="includeStacktrace", defaultBoolean=true) boolean bl7) {
        boolean bl8 = bl2 && bl3;
        return new JsonLayout(configuration, bl, bl2, bl8, bl4, bl5, bl6, string, string2, charset, bl7);
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    public static JsonLayout createDefaultLayout() {
        return new JsonLayout(new DefaultConfiguration(), false, false, false, false, false, false, DEFAULT_HEADER, DEFAULT_FOOTER, StandardCharsets.UTF_8, true);
    }

    @Override
    public void toSerializable(LogEvent logEvent, Writer writer) throws IOException {
        if (this.complete && this.eventCount > 0L) {
            writer.append(", ");
        }
        super.toSerializable(logEvent, writer);
    }

    @Override
    public String toSerializable(LogEvent logEvent) {
        return super.toSerializable(logEvent);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder<B extends Builder<B>>
    extends AbstractJacksonLayout.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<JsonLayout> {
        @PluginBuilderAttribute
        private boolean locationInfo;
        @PluginBuilderAttribute
        private boolean properties;
        @PluginBuilderAttribute
        private boolean propertiesAsList;
        @PluginBuilderAttribute
        private boolean includeStacktrace = true;

        public Builder() {
            this.setCharset(StandardCharsets.UTF_8);
        }

        @Override
        public JsonLayout build() {
            boolean bl = this.properties && this.propertiesAsList;
            String string = this.toStringOrNull(this.getHeader());
            String string2 = this.toStringOrNull(this.getFooter());
            return new JsonLayout(this.getConfiguration(), this.locationInfo, this.properties, bl, this.isComplete(), this.isCompact(), this.getEventEol(), string, string2, this.getCharset(), this.includeStacktrace);
        }

        private String toStringOrNull(byte[] byArray) {
            return byArray == null ? null : new String(byArray, Charset.defaultCharset());
        }

        public boolean isLocationInfo() {
            return this.locationInfo;
        }

        public boolean isProperties() {
            return this.properties;
        }

        public boolean isPropertiesAsList() {
            return this.propertiesAsList;
        }

        public boolean isIncludeStacktrace() {
            return this.includeStacktrace;
        }

        public B setLocationInfo(boolean bl) {
            this.locationInfo = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setProperties(boolean bl) {
            this.properties = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setPropertiesAsList(boolean bl) {
            this.propertiesAsList = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludeStacktrace(boolean bl) {
            this.includeStacktrace = bl;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public Object build() {
            return this.build();
        }
    }
}

