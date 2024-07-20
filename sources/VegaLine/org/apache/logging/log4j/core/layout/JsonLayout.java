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

    protected JsonLayout(Configuration config, boolean locationInfo, boolean properties, boolean encodeThreadContextAsList, boolean complete, boolean compact, boolean eventEol, String headerPattern, String footerPattern, Charset charset, boolean includeStacktrace) {
        super(config, new JacksonFactory.JSON(encodeThreadContextAsList, includeStacktrace).newWriter(locationInfo, properties, compact), charset, compact, complete, eventEol, PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(headerPattern).setDefaultPattern(DEFAULT_HEADER).build(), PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(footerPattern).setDefaultPattern(DEFAULT_FOOTER).build());
    }

    @Override
    public byte[] getHeader() {
        if (!this.complete) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        String str = this.serializeToString(this.getHeaderSerializer());
        if (str != null) {
            buf.append(str);
        }
        buf.append(this.eol);
        return this.getBytes(buf.toString());
    }

    @Override
    public byte[] getFooter() {
        if (!this.complete) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        buf.append(this.eol);
        String str = this.serializeToString(this.getFooterSerializer());
        if (str != null) {
            buf.append(str);
        }
        buf.append(this.eol);
        return this.getBytes(buf.toString());
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("version", "2.0");
        return result;
    }

    @Override
    public String getContentType() {
        return "application/json; charset=" + this.getCharset();
    }

    @Deprecated
    public static JsonLayout createLayout(@PluginConfiguration Configuration config, @PluginAttribute(value="locationInfo") boolean locationInfo, @PluginAttribute(value="properties") boolean properties, @PluginAttribute(value="propertiesAsList") boolean propertiesAsList, @PluginAttribute(value="complete") boolean complete, @PluginAttribute(value="compact") boolean compact, @PluginAttribute(value="eventEol") boolean eventEol, @PluginAttribute(value="header", defaultString="[") String headerPattern, @PluginAttribute(value="footer", defaultString="]") String footerPattern, @PluginAttribute(value="charset", defaultString="UTF-8") Charset charset, @PluginAttribute(value="includeStacktrace", defaultBoolean=true) boolean includeStacktrace) {
        boolean encodeThreadContextAsList = properties && propertiesAsList;
        return new JsonLayout(config, locationInfo, properties, encodeThreadContextAsList, complete, compact, eventEol, headerPattern, footerPattern, charset, includeStacktrace);
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    public static JsonLayout createDefaultLayout() {
        return new JsonLayout(new DefaultConfiguration(), false, false, false, false, false, false, DEFAULT_HEADER, DEFAULT_FOOTER, StandardCharsets.UTF_8, true);
    }

    @Override
    public void toSerializable(LogEvent event, Writer writer) throws IOException {
        if (this.complete && this.eventCount > 0L) {
            writer.append(", ");
        }
        super.toSerializable(event, writer);
    }

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
            boolean encodeThreadContextAsList = this.properties && this.propertiesAsList;
            String headerPattern = this.toStringOrNull(this.getHeader());
            String footerPattern = this.toStringOrNull(this.getFooter());
            return new JsonLayout(this.getConfiguration(), this.locationInfo, this.properties, encodeThreadContextAsList, this.isComplete(), this.isCompact(), this.getEventEol(), headerPattern, footerPattern, this.getCharset(), this.includeStacktrace);
        }

        private String toStringOrNull(byte[] header) {
            return header == null ? null : new String(header, Charset.defaultCharset());
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

        public B setLocationInfo(boolean locationInfo) {
            this.locationInfo = locationInfo;
            return (B)((Builder)this.asBuilder());
        }

        public B setProperties(boolean properties) {
            this.properties = properties;
            return (B)((Builder)this.asBuilder());
        }

        public B setPropertiesAsList(boolean propertiesAsList) {
            this.propertiesAsList = propertiesAsList;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludeStacktrace(boolean includeStacktrace) {
            this.includeStacktrace = includeStacktrace;
            return (B)((Builder)this.asBuilder());
        }
    }
}

