/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.impl.DefaultLogEventFactory;
import org.apache.logging.log4j.core.layout.AbstractLayout;
import org.apache.logging.log4j.core.layout.Encoder;
import org.apache.logging.log4j.core.layout.StringBuilderEncoder;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.StringEncoder;
import org.apache.logging.log4j.util.PropertiesUtil;

public abstract class AbstractStringLayout
extends AbstractLayout<String>
implements StringLayout {
    protected static final int DEFAULT_STRING_BUILDER_SIZE = 1024;
    protected static final int MAX_STRING_BUILDER_SIZE = Math.max(1024, AbstractStringLayout.size("log4j.layoutStringBuilder.maxSize", 2048));
    private static final ThreadLocal<StringBuilder> threadLocal = new ThreadLocal();
    private Encoder<StringBuilder> textEncoder;
    private transient Charset charset;
    private final String charsetName;
    private final Serializer footerSerializer;
    private final Serializer headerSerializer;
    private final boolean useCustomEncoding;

    protected static StringBuilder getStringBuilder() {
        StringBuilder result = threadLocal.get();
        if (result == null) {
            result = new StringBuilder(1024);
            threadLocal.set(result);
        }
        AbstractStringLayout.trimToMaxSize(result);
        result.setLength(0);
        return result;
    }

    private static boolean isPreJava8() {
        String version = System.getProperty("java.version");
        String[] parts = version.split("\\.");
        try {
            int major = Integer.parseInt(parts[1]);
            return major < 8;
        } catch (Exception ex) {
            return true;
        }
    }

    private static int size(String property, int defaultValue) {
        return PropertiesUtil.getProperties().getIntegerProperty(property, defaultValue);
    }

    protected static void trimToMaxSize(StringBuilder stringBuilder) {
        if (stringBuilder.length() > MAX_STRING_BUILDER_SIZE) {
            stringBuilder.setLength(MAX_STRING_BUILDER_SIZE);
            stringBuilder.trimToSize();
        }
    }

    protected AbstractStringLayout(Charset charset) {
        this(charset, (byte[])null, (byte[])null);
    }

    protected AbstractStringLayout(Charset aCharset, byte[] header, byte[] footer) {
        super(null, header, footer);
        this.headerSerializer = null;
        this.footerSerializer = null;
        this.charset = aCharset == null ? StandardCharsets.UTF_8 : aCharset;
        this.charsetName = this.charset.name();
        this.useCustomEncoding = AbstractStringLayout.isPreJava8() && (StandardCharsets.ISO_8859_1.equals(aCharset) || StandardCharsets.US_ASCII.equals(aCharset));
        this.textEncoder = Constants.ENABLE_DIRECT_ENCODERS ? new StringBuilderEncoder(this.charset) : null;
    }

    protected AbstractStringLayout(Configuration config, Charset aCharset, Serializer headerSerializer, Serializer footerSerializer) {
        super(config, null, null);
        this.headerSerializer = headerSerializer;
        this.footerSerializer = footerSerializer;
        this.charset = aCharset == null ? StandardCharsets.UTF_8 : aCharset;
        this.charsetName = this.charset.name();
        this.useCustomEncoding = AbstractStringLayout.isPreJava8() && (StandardCharsets.ISO_8859_1.equals(aCharset) || StandardCharsets.US_ASCII.equals(aCharset));
        this.textEncoder = Constants.ENABLE_DIRECT_ENCODERS ? new StringBuilderEncoder(this.charset) : null;
    }

    protected byte[] getBytes(String s) {
        if (this.useCustomEncoding) {
            return StringEncoder.encodeSingleByteChars(s);
        }
        try {
            return s.getBytes(this.charsetName);
        } catch (UnsupportedEncodingException e) {
            return s.getBytes(this.charset);
        }
    }

    @Override
    public Charset getCharset() {
        return this.charset;
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public byte[] getFooter() {
        return this.serializeToBytes(this.footerSerializer, super.getFooter());
    }

    public Serializer getFooterSerializer() {
        return this.footerSerializer;
    }

    @Override
    public byte[] getHeader() {
        return this.serializeToBytes(this.headerSerializer, super.getHeader());
    }

    public Serializer getHeaderSerializer() {
        return this.headerSerializer;
    }

    private DefaultLogEventFactory getLogEventFactory() {
        return DefaultLogEventFactory.getInstance();
    }

    protected Encoder<StringBuilder> getStringBuilderEncoder() {
        if (this.textEncoder == null) {
            this.textEncoder = new StringBuilderEncoder(this.getCharset());
        }
        return this.textEncoder;
    }

    protected byte[] serializeToBytes(Serializer serializer, byte[] defaultValue) {
        String serializable = this.serializeToString(serializer);
        if (serializer == null) {
            return defaultValue;
        }
        return StringEncoder.toBytes(serializable, this.getCharset());
    }

    protected String serializeToString(Serializer serializer) {
        if (serializer == null) {
            return null;
        }
        LoggerConfig rootLogger = this.getConfiguration().getRootLogger();
        LogEvent logEvent = this.getLogEventFactory().createEvent(rootLogger.getName(), null, "", rootLogger.getLevel(), null, null, null);
        return serializer.toSerializable(logEvent);
    }

    @Override
    public byte[] toByteArray(LogEvent event) {
        return this.getBytes((String)this.toSerializable(event));
    }

    public static interface Serializer2 {
        public StringBuilder toSerializable(LogEvent var1, StringBuilder var2);
    }

    public static interface Serializer {
        public String toSerializable(LogEvent var1);
    }

    public static abstract class Builder<B extends Builder<B>>
    extends AbstractLayout.Builder<B> {
        @PluginBuilderAttribute(value="charset")
        private Charset charset;
        @PluginElement(value="footerSerializer")
        private Serializer footerSerializer;
        @PluginElement(value="headerSerializer")
        private Serializer headerSerializer;

        public Charset getCharset() {
            return this.charset;
        }

        public Serializer getFooterSerializer() {
            return this.footerSerializer;
        }

        public Serializer getHeaderSerializer() {
            return this.headerSerializer;
        }

        public B setCharset(Charset charset) {
            this.charset = charset;
            return (B)((Builder)this.asBuilder());
        }

        public B setFooterSerializer(Serializer footerSerializer) {
            this.footerSerializer = footerSerializer;
            return (B)((Builder)this.asBuilder());
        }

        public B setHeaderSerializer(Serializer headerSerializer) {
            this.headerSerializer = headerSerializer;
            return (B)((Builder)this.asBuilder());
        }
    }
}

