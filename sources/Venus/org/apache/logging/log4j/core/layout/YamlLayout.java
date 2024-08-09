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

    protected YamlLayout(Configuration configuration, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, String string, String string2, Charset charset, boolean bl6) {
        super(configuration, new JacksonFactory.YAML(bl6).newWriter(bl, bl2, bl4), charset, bl4, bl3, bl5, PatternLayout.newSerializerBuilder().setConfiguration(configuration).setPattern(string).setDefaultPattern("").build(), PatternLayout.newSerializerBuilder().setConfiguration(configuration).setPattern(string2).setDefaultPattern("").build());
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
        return "application/yaml; charset=" + this.getCharset();
    }

    @PluginFactory
    public static AbstractJacksonLayout createLayout(@PluginConfiguration Configuration configuration, @PluginAttribute(value="locationInfo") boolean bl, @PluginAttribute(value="properties") boolean bl2, @PluginAttribute(value="header", defaultString="") String string, @PluginAttribute(value="footer", defaultString="") String string2, @PluginAttribute(value="charset", defaultString="UTF-8") Charset charset, @PluginAttribute(value="includeStacktrace", defaultBoolean=true) boolean bl3) {
        return new YamlLayout(configuration, bl, bl2, false, false, true, string, string2, charset, bl3);
    }

    public static AbstractJacksonLayout createDefaultLayout() {
        return new YamlLayout(new DefaultConfiguration(), false, false, false, false, false, "", "", StandardCharsets.UTF_8, true);
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
}

