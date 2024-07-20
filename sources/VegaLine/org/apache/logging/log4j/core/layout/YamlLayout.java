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
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractJacksonLayout;
import org.apache.logging.log4j.core.layout.JacksonFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name="YamlLayout", category="Core", elementType="layout", printObject=true)
public final class YamlLayout
extends AbstractJacksonLayout {
    private static final String DEFAULT_FOOTER = "";
    private static final String DEFAULT_HEADER = "";
    static final String CONTENT_TYPE = "application/yaml";

    protected YamlLayout(Configuration config, boolean locationInfo, boolean properties, boolean complete, boolean compact, boolean eventEol, String headerPattern, String footerPattern, Charset charset, boolean includeStacktrace) {
        super(config, new JacksonFactory.YAML(includeStacktrace).newWriter(locationInfo, properties, compact), charset, compact, complete, eventEol, PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(headerPattern).setDefaultPattern("").build(), PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(footerPattern).setDefaultPattern("").build());
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
        return "application/yaml; charset=" + this.getCharset();
    }

    @PluginFactory
    public static AbstractJacksonLayout createLayout(@PluginConfiguration Configuration config, @PluginAttribute(value="locationInfo") boolean locationInfo, @PluginAttribute(value="properties") boolean properties, @PluginAttribute(value="header", defaultString="") String headerPattern, @PluginAttribute(value="footer", defaultString="") String footerPattern, @PluginAttribute(value="charset", defaultString="UTF-8") Charset charset, @PluginAttribute(value="includeStacktrace", defaultBoolean=true) boolean includeStacktrace) {
        return new YamlLayout(config, locationInfo, properties, false, false, true, headerPattern, footerPattern, charset, includeStacktrace);
    }

    public static AbstractJacksonLayout createDefaultLayout() {
        return new YamlLayout(new DefaultConfiguration(), false, false, false, false, false, "", "", StandardCharsets.UTF_8, true);
    }

    @Override
    public void toSerializable(LogEvent event, Writer writer) throws IOException {
        if (this.complete && this.eventCount > 0L) {
            writer.append(", ");
        }
        super.toSerializable(event, writer);
    }
}

