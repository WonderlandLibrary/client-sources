/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractJacksonLayout;
import org.apache.logging.log4j.core.layout.JacksonFactory;

@Plugin(name="XmlLayout", category="Core", elementType="layout", printObject=true)
public final class XmlLayout
extends AbstractJacksonLayout {
    private static final String ROOT_TAG = "Events";

    protected XmlLayout(boolean bl, boolean bl2, boolean bl3, boolean bl4, Charset charset, boolean bl5) {
        super(null, new JacksonFactory.XML(bl5).newWriter(bl, bl2, bl4), charset, bl4, bl3, false, null, null);
    }

    @Override
    public byte[] getHeader() {
        if (!this.complete) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"");
        stringBuilder.append(this.getCharset().name());
        stringBuilder.append("\"?>");
        stringBuilder.append(this.eol);
        stringBuilder.append('<');
        stringBuilder.append(ROOT_TAG);
        stringBuilder.append(" xmlns=\"http://logging.apache.org/log4j/2.0/events\">");
        stringBuilder.append(this.eol);
        return stringBuilder.toString().getBytes(this.getCharset());
    }

    @Override
    public byte[] getFooter() {
        if (!this.complete) {
            return null;
        }
        return this.getBytes("</Events>" + this.eol);
    }

    @Override
    public Map<String, String> getContentFormat() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("xsd", "log4j-events.xsd");
        hashMap.put("version", "2.0");
        return hashMap;
    }

    @Override
    public String getContentType() {
        return "text/xml; charset=" + this.getCharset();
    }

    @PluginFactory
    public static XmlLayout createLayout(@PluginAttribute(value="locationInfo") boolean bl, @PluginAttribute(value="properties") boolean bl2, @PluginAttribute(value="complete") boolean bl3, @PluginAttribute(value="compact") boolean bl4, @PluginAttribute(value="charset", defaultString="UTF-8") Charset charset, @PluginAttribute(value="includeStacktrace", defaultBoolean=true) boolean bl5) {
        return new XmlLayout(bl, bl2, bl3, bl4, charset, bl5);
    }

    public static XmlLayout createDefaultLayout() {
        return new XmlLayout(false, false, false, false, StandardCharsets.UTF_8, true);
    }

    @Override
    public void toSerializable(LogEvent logEvent, Writer writer) throws JsonGenerationException, JsonMappingException, IOException {
        super.toSerializable(logEvent, writer);
    }

    @Override
    public String toSerializable(LogEvent logEvent) {
        return super.toSerializable(logEvent);
    }
}

