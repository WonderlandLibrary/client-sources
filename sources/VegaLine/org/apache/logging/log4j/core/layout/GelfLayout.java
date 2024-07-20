/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.core.layout.Encoder;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.net.Severity;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.core.util.NetUtils;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.TriConsumer;

@Plugin(name="GelfLayout", category="Core", elementType="layout", printObject=true)
public final class GelfLayout
extends AbstractStringLayout {
    private static final char C = ',';
    private static final int COMPRESSION_THRESHOLD = 1024;
    private static final char Q = '\"';
    private static final String QC = "\",";
    private static final String QU = "\"_";
    private final KeyValuePair[] additionalFields;
    private final int compressionThreshold;
    private final CompressionType compressionType;
    private final String host;
    private final boolean includeStacktrace;
    private final boolean includeThreadContext;
    private static final TriConsumer<String, Object, StringBuilder> WRITE_KEY_VALUES_INTO = new TriConsumer<String, Object, StringBuilder>(){

        @Override
        public void accept(String key, Object value, StringBuilder stringBuilder) {
            stringBuilder.append(GelfLayout.QU);
            JsonUtils.quoteAsString(key, stringBuilder);
            stringBuilder.append("\":\"");
            JsonUtils.quoteAsString(GelfLayout.toNullSafeString(String.valueOf(value)), stringBuilder);
            stringBuilder.append(GelfLayout.QC);
        }
    };
    private static final ThreadLocal<StringBuilder> messageStringBuilder = new ThreadLocal();
    private static final ThreadLocal<StringBuilder> timestampStringBuilder = new ThreadLocal();

    @Deprecated
    public GelfLayout(String host, KeyValuePair[] additionalFields, CompressionType compressionType, int compressionThreshold, boolean includeStacktrace) {
        this(null, host, additionalFields, compressionType, compressionThreshold, includeStacktrace, true);
    }

    private GelfLayout(Configuration config, String host, KeyValuePair[] additionalFields, CompressionType compressionType, int compressionThreshold, boolean includeStacktrace, boolean includeThreadContext) {
        super(config, StandardCharsets.UTF_8, null, null);
        this.host = host != null ? host : NetUtils.getLocalHostname();
        KeyValuePair[] keyValuePairArray = this.additionalFields = additionalFields != null ? additionalFields : new KeyValuePair[]{};
        if (config == null) {
            for (KeyValuePair additionalField : this.additionalFields) {
                if (!GelfLayout.valueNeedsLookup(additionalField.getValue())) continue;
                throw new IllegalArgumentException("configuration needs to be set when there are additional fields with variables");
            }
        }
        this.compressionType = compressionType;
        this.compressionThreshold = compressionThreshold;
        this.includeStacktrace = includeStacktrace;
        this.includeThreadContext = includeThreadContext;
    }

    @Deprecated
    public static GelfLayout createLayout(@PluginAttribute(value="host") String host, @PluginElement(value="AdditionalField") KeyValuePair[] additionalFields, @PluginAttribute(value="compressionType", defaultString="GZIP") CompressionType compressionType, @PluginAttribute(value="compressionThreshold", defaultInt=1024) int compressionThreshold, @PluginAttribute(value="includeStacktrace", defaultBoolean=true) boolean includeStacktrace) {
        return new GelfLayout(null, host, additionalFields, compressionType, compressionThreshold, includeStacktrace, true);
    }

    @PluginBuilderFactory
    public static <B extends Builder<B>> B newBuilder() {
        return (B)((Builder)new Builder().asBuilder());
    }

    @Override
    public Map<String, String> getContentFormat() {
        return Collections.emptyMap();
    }

    @Override
    public String getContentType() {
        return "application/json; charset=" + this.getCharset();
    }

    @Override
    public byte[] toByteArray(LogEvent event) {
        StringBuilder text = this.toText(event, GelfLayout.getStringBuilder(), false);
        byte[] bytes = this.getBytes(text.toString());
        return this.compressionType != CompressionType.OFF && bytes.length > this.compressionThreshold ? this.compress(bytes) : bytes;
    }

    @Override
    public void encode(LogEvent event, ByteBufferDestination destination) {
        if (this.compressionType != CompressionType.OFF) {
            super.encode(event, destination);
            return;
        }
        StringBuilder text = this.toText(event, GelfLayout.getStringBuilder(), true);
        Encoder<StringBuilder> helper = this.getStringBuilderEncoder();
        helper.encode(text, destination);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private byte[] compress(byte[] bytes) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(this.compressionThreshold / 8);
            try (DeflaterOutputStream stream = this.compressionType.createDeflaterOutputStream(baos);){
                if (stream == null) {
                    byte[] byArray = bytes;
                    return byArray;
                }
                stream.write(bytes);
                stream.finish();
                return baos.toByteArray();
            }
        } catch (IOException e) {
            StatusLogger.getLogger().error(e);
            return bytes;
        }
    }

    @Override
    public String toSerializable(LogEvent event) {
        StringBuilder text = this.toText(event, GelfLayout.getStringBuilder(), false);
        return text.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private StringBuilder toText(LogEvent event, StringBuilder builder, boolean gcFree) {
        builder.append('{');
        builder.append("\"version\":\"1.1\",");
        builder.append("\"host\":\"");
        JsonUtils.quoteAsString(GelfLayout.toNullSafeString(this.host), builder);
        builder.append(QC);
        builder.append("\"timestamp\":").append(GelfLayout.formatTimestamp(event.getTimeMillis())).append(',');
        builder.append("\"level\":").append(this.formatLevel(event.getLevel())).append(',');
        if (event.getThreadName() != null) {
            builder.append("\"_thread\":\"");
            JsonUtils.quoteAsString(event.getThreadName(), builder);
            builder.append(QC);
        }
        if (event.getLoggerName() != null) {
            builder.append("\"_logger\":\"");
            JsonUtils.quoteAsString(event.getLoggerName(), builder);
            builder.append(QC);
        }
        if (this.additionalFields.length > 0) {
            StrSubstitutor strSubstitutor = this.getConfiguration().getStrSubstitutor();
            for (KeyValuePair additionalField : this.additionalFields) {
                builder.append(QU);
                JsonUtils.quoteAsString(additionalField.getKey(), builder);
                builder.append("\":\"");
                String value = GelfLayout.valueNeedsLookup(additionalField.getValue()) ? strSubstitutor.replace(event, additionalField.getValue()) : additionalField.getValue();
                JsonUtils.quoteAsString(GelfLayout.toNullSafeString(value), builder);
                builder.append(QC);
            }
        }
        if (this.includeThreadContext) {
            event.getContextData().forEach(WRITE_KEY_VALUES_INTO, builder);
        }
        if (event.getThrown() != null) {
            builder.append("\"full_message\":\"");
            if (this.includeStacktrace) {
                JsonUtils.quoteAsString(GelfLayout.formatThrowable(event.getThrown()), builder);
            } else {
                JsonUtils.quoteAsString(event.getThrown().toString(), builder);
            }
            builder.append(QC);
        }
        builder.append("\"short_message\":\"");
        Message message = event.getMessage();
        if (message instanceof CharSequence) {
            JsonUtils.quoteAsString((CharSequence)((Object)message), builder);
        } else if (gcFree && message instanceof StringBuilderFormattable) {
            StringBuilder messageBuffer = GelfLayout.getMessageStringBuilder();
            try {
                ((StringBuilderFormattable)((Object)message)).formatTo(messageBuffer);
                JsonUtils.quoteAsString(messageBuffer, builder);
            } finally {
                GelfLayout.trimToMaxSize(messageBuffer);
            }
        } else {
            JsonUtils.quoteAsString(GelfLayout.toNullSafeString(message.getFormattedMessage()), builder);
        }
        builder.append('\"');
        builder.append('}');
        return builder;
    }

    private static boolean valueNeedsLookup(String value) {
        return value != null && value.contains("${");
    }

    private static StringBuilder getMessageStringBuilder() {
        StringBuilder result = messageStringBuilder.get();
        if (result == null) {
            result = new StringBuilder(1024);
            messageStringBuilder.set(result);
        }
        result.setLength(0);
        return result;
    }

    private static CharSequence toNullSafeString(CharSequence s) {
        return s == null ? "" : s;
    }

    static CharSequence formatTimestamp(long timeMillis) {
        if (timeMillis < 1000L) {
            return "0";
        }
        StringBuilder builder = GelfLayout.getTimestampStringBuilder();
        builder.append(timeMillis);
        builder.insert(builder.length() - 3, '.');
        return builder;
    }

    private static StringBuilder getTimestampStringBuilder() {
        StringBuilder result = timestampStringBuilder.get();
        if (result == null) {
            result = new StringBuilder(20);
            timestampStringBuilder.set(result);
        }
        result.setLength(0);
        return result;
    }

    private int formatLevel(Level level) {
        return Severity.getSeverity(level).getCode();
    }

    static CharSequence formatThrowable(Throwable throwable) {
        StringWriter sw = new StringWriter(2048);
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.flush();
        return sw.getBuffer();
    }

    public static class Builder<B extends Builder<B>>
    extends AbstractStringLayout.Builder<B>
    implements org.apache.logging.log4j.core.util.Builder<GelfLayout> {
        @PluginBuilderAttribute
        private String host;
        @PluginElement(value="AdditionalField")
        private KeyValuePair[] additionalFields;
        @PluginBuilderAttribute
        private CompressionType compressionType = CompressionType.GZIP;
        @PluginBuilderAttribute
        private int compressionThreshold = 1024;
        @PluginBuilderAttribute
        private boolean includeStacktrace = true;
        @PluginBuilderAttribute
        private boolean includeThreadContext = true;

        public Builder() {
            this.setCharset(StandardCharsets.UTF_8);
        }

        @Override
        public GelfLayout build() {
            return new GelfLayout(this.getConfiguration(), this.host, this.additionalFields, this.compressionType, this.compressionThreshold, this.includeStacktrace, this.includeThreadContext);
        }

        public String getHost() {
            return this.host;
        }

        public CompressionType getCompressionType() {
            return this.compressionType;
        }

        public int getCompressionThreshold() {
            return this.compressionThreshold;
        }

        public boolean isIncludeStacktrace() {
            return this.includeStacktrace;
        }

        public boolean isIncludeThreadContext() {
            return this.includeThreadContext;
        }

        public KeyValuePair[] getAdditionalFields() {
            return this.additionalFields;
        }

        public B setHost(String host) {
            this.host = host;
            return (B)((Builder)this.asBuilder());
        }

        public B setCompressionType(CompressionType compressionType) {
            this.compressionType = compressionType;
            return (B)((Builder)this.asBuilder());
        }

        public B setCompressionThreshold(int compressionThreshold) {
            this.compressionThreshold = compressionThreshold;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludeStacktrace(boolean includeStacktrace) {
            this.includeStacktrace = includeStacktrace;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludeThreadContext(boolean includeThreadContext) {
            this.includeThreadContext = includeThreadContext;
            return (B)((Builder)this.asBuilder());
        }

        public B setAdditionalFields(KeyValuePair[] additionalFields) {
            this.additionalFields = additionalFields;
            return (B)((Builder)this.asBuilder());
        }
    }

    public static enum CompressionType {
        GZIP{

            @Override
            public DeflaterOutputStream createDeflaterOutputStream(OutputStream os) throws IOException {
                return new GZIPOutputStream(os);
            }
        }
        ,
        ZLIB{

            @Override
            public DeflaterOutputStream createDeflaterOutputStream(OutputStream os) throws IOException {
                return new DeflaterOutputStream(os);
            }
        }
        ,
        OFF{

            @Override
            public DeflaterOutputStream createDeflaterOutputStream(OutputStream os) throws IOException {
                return null;
            }
        };


        public abstract DeflaterOutputStream createDeflaterOutputStream(OutputStream var1) throws IOException;
    }
}

