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
        StringBuilder stringBuilder = threadLocal.get();
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder(1024);
            threadLocal.set(stringBuilder);
        }
        AbstractStringLayout.trimToMaxSize(stringBuilder);
        stringBuilder.setLength(0);
        return stringBuilder;
    }

    private static boolean isPreJava8() {
        String string = System.getProperty("java.version");
        String[] stringArray = string.split("\\.");
        try {
            int n = Integer.parseInt(stringArray[0]);
            return n < 8;
        } catch (Exception exception) {
            return false;
        }
    }

    private static int size(String string, int n) {
        return PropertiesUtil.getProperties().getIntegerProperty(string, n);
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

    protected AbstractStringLayout(Charset charset, byte[] byArray, byte[] byArray2) {
        super(null, byArray, byArray2);
        this.headerSerializer = null;
        this.footerSerializer = null;
        this.charset = charset == null ? StandardCharsets.UTF_8 : charset;
        this.charsetName = this.charset.name();
        this.useCustomEncoding = AbstractStringLayout.isPreJava8() && (StandardCharsets.ISO_8859_1.equals(charset) || StandardCharsets.US_ASCII.equals(charset));
        this.textEncoder = Constants.ENABLE_DIRECT_ENCODERS ? new StringBuilderEncoder(this.charset) : null;
    }

    protected AbstractStringLayout(Configuration configuration, Charset charset, Serializer serializer, Serializer serializer2) {
        super(configuration, null, null);
        this.headerSerializer = serializer;
        this.footerSerializer = serializer2;
        this.charset = charset == null ? StandardCharsets.UTF_8 : charset;
        this.charsetName = this.charset.name();
        this.useCustomEncoding = AbstractStringLayout.isPreJava8() && (StandardCharsets.ISO_8859_1.equals(charset) || StandardCharsets.US_ASCII.equals(charset));
        this.textEncoder = Constants.ENABLE_DIRECT_ENCODERS ? new StringBuilderEncoder(this.charset) : null;
    }

    protected byte[] getBytes(String string) {
        if (this.useCustomEncoding) {
            return StringEncoder.encodeSingleByteChars(string);
        }
        try {
            return string.getBytes(this.charsetName);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            return string.getBytes(this.charset);
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

    protected byte[] serializeToBytes(Serializer serializer, byte[] byArray) {
        String string = this.serializeToString(serializer);
        if (serializer == null) {
            return byArray;
        }
        return StringEncoder.toBytes(string, this.getCharset());
    }

    protected String serializeToString(Serializer serializer) {
        if (serializer == null) {
            return null;
        }
        LoggerConfig loggerConfig = this.getConfiguration().getRootLogger();
        LogEvent logEvent = this.getLogEventFactory().createEvent(loggerConfig.getName(), null, "", loggerConfig.getLevel(), null, null, null);
        return serializer.toSerializable(logEvent);
    }

    @Override
    public byte[] toByteArray(LogEvent logEvent) {
        return this.getBytes((String)this.toSerializable(logEvent));
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

        public B setFooterSerializer(Serializer serializer) {
            this.footerSerializer = serializer;
            return (B)((Builder)this.asBuilder());
        }

        public B setHeaderSerializer(Serializer serializer) {
            this.headerSerializer = serializer;
            return (B)((Builder)this.asBuilder());
        }
    }
}

