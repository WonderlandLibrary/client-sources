/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
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
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.TriConsumer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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
        public void accept(String string, Object object, StringBuilder stringBuilder) {
            stringBuilder.append(GelfLayout.QU);
            JsonUtils.quoteAsString(string, stringBuilder);
            stringBuilder.append("\":\"");
            JsonUtils.quoteAsString(GelfLayout.access$200(String.valueOf(object)), stringBuilder);
            stringBuilder.append(GelfLayout.QC);
        }

        @Override
        public void accept(Object object, Object object2, Object object3) {
            this.accept((String)object, object2, (StringBuilder)object3);
        }
    };
    private static final ThreadLocal<StringBuilder> messageStringBuilder = new ThreadLocal();
    private static final ThreadLocal<StringBuilder> timestampStringBuilder = new ThreadLocal();

    @Deprecated
    public GelfLayout(String string, KeyValuePair[] keyValuePairArray, CompressionType compressionType, int n, boolean bl) {
        this(null, string, keyValuePairArray, compressionType, n, bl, true);
    }

    private GelfLayout(Configuration configuration, String string, KeyValuePair[] keyValuePairArray, CompressionType compressionType, int n, boolean bl, boolean bl2) {
        super(configuration, StandardCharsets.UTF_8, null, null);
        this.host = string != null ? string : NetUtils.getLocalHostname();
        KeyValuePair[] keyValuePairArray2 = this.additionalFields = keyValuePairArray != null ? keyValuePairArray : new KeyValuePair[]{};
        if (configuration == null) {
            for (KeyValuePair keyValuePair : this.additionalFields) {
                if (!GelfLayout.valueNeedsLookup(keyValuePair.getValue())) continue;
                throw new IllegalArgumentException("configuration needs to be set when there are additional fields with variables");
            }
        }
        this.compressionType = compressionType;
        this.compressionThreshold = n;
        this.includeStacktrace = bl;
        this.includeThreadContext = bl2;
    }

    @Deprecated
    public static GelfLayout createLayout(@PluginAttribute(value="host") String string, @PluginElement(value="AdditionalField") KeyValuePair[] keyValuePairArray, @PluginAttribute(value="compressionType", defaultString="GZIP") CompressionType compressionType, @PluginAttribute(value="compressionThreshold", defaultInt=1024) int n, @PluginAttribute(value="includeStacktrace", defaultBoolean=true) boolean bl) {
        return new GelfLayout(null, string, keyValuePairArray, compressionType, n, bl, true);
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
    public byte[] toByteArray(LogEvent logEvent) {
        StringBuilder stringBuilder = this.toText(logEvent, GelfLayout.getStringBuilder(), false);
        byte[] byArray = this.getBytes(stringBuilder.toString());
        return this.compressionType != CompressionType.OFF && byArray.length > this.compressionThreshold ? this.compress(byArray) : byArray;
    }

    @Override
    public void encode(LogEvent logEvent, ByteBufferDestination byteBufferDestination) {
        if (this.compressionType != CompressionType.OFF) {
            super.encode(logEvent, byteBufferDestination);
            return;
        }
        StringBuilder stringBuilder = this.toText(logEvent, GelfLayout.getStringBuilder(), true);
        Encoder<StringBuilder> encoder = this.getStringBuilderEncoder();
        encoder.encode(stringBuilder, byteBufferDestination);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private byte[] compress(byte[] byArray) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.compressionThreshold / 8);
            try (DeflaterOutputStream deflaterOutputStream = this.compressionType.createDeflaterOutputStream(byteArrayOutputStream);){
                if (deflaterOutputStream == null) {
                    byte[] byArray2 = byArray;
                    return byArray2;
                }
                deflaterOutputStream.write(byArray);
                deflaterOutputStream.finish();
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException iOException) {
            StatusLogger.getLogger().error(iOException);
            return byArray;
        }
    }

    @Override
    public String toSerializable(LogEvent logEvent) {
        StringBuilder stringBuilder = this.toText(logEvent, GelfLayout.getStringBuilder(), false);
        return stringBuilder.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private StringBuilder toText(LogEvent logEvent, StringBuilder stringBuilder, boolean bl) {
        Object object;
        stringBuilder.append('{');
        stringBuilder.append("\"version\":\"1.1\",");
        stringBuilder.append("\"host\":\"");
        JsonUtils.quoteAsString(GelfLayout.toNullSafeString(this.host), stringBuilder);
        stringBuilder.append(QC);
        stringBuilder.append("\"timestamp\":").append(GelfLayout.formatTimestamp(logEvent.getTimeMillis())).append(',');
        stringBuilder.append("\"level\":").append(this.formatLevel(logEvent.getLevel())).append(',');
        if (logEvent.getThreadName() != null) {
            stringBuilder.append("\"_thread\":\"");
            JsonUtils.quoteAsString(logEvent.getThreadName(), stringBuilder);
            stringBuilder.append(QC);
        }
        if (logEvent.getLoggerName() != null) {
            stringBuilder.append("\"_logger\":\"");
            JsonUtils.quoteAsString(logEvent.getLoggerName(), stringBuilder);
            stringBuilder.append(QC);
        }
        if (this.additionalFields.length > 0) {
            object = this.getConfiguration().getStrSubstitutor();
            for (KeyValuePair keyValuePair : this.additionalFields) {
                stringBuilder.append(QU);
                JsonUtils.quoteAsString(keyValuePair.getKey(), stringBuilder);
                stringBuilder.append("\":\"");
                String string = GelfLayout.valueNeedsLookup(keyValuePair.getValue()) ? ((StrSubstitutor)object).replace(logEvent, keyValuePair.getValue()) : keyValuePair.getValue();
                JsonUtils.quoteAsString(GelfLayout.toNullSafeString(string), stringBuilder);
                stringBuilder.append(QC);
            }
        }
        if (this.includeThreadContext) {
            logEvent.getContextData().forEach(WRITE_KEY_VALUES_INTO, stringBuilder);
        }
        if (logEvent.getThrown() != null) {
            stringBuilder.append("\"full_message\":\"");
            if (this.includeStacktrace) {
                JsonUtils.quoteAsString(GelfLayout.formatThrowable(logEvent.getThrown()), stringBuilder);
            } else {
                JsonUtils.quoteAsString(logEvent.getThrown().toString(), stringBuilder);
            }
            stringBuilder.append(QC);
        }
        stringBuilder.append("\"short_message\":\"");
        object = logEvent.getMessage();
        if (object instanceof CharSequence) {
            JsonUtils.quoteAsString((CharSequence)object, stringBuilder);
        } else if (bl && object instanceof StringBuilderFormattable) {
            StringBuilder stringBuilder2 = GelfLayout.getMessageStringBuilder();
            try {
                ((StringBuilderFormattable)object).formatTo(stringBuilder2);
                JsonUtils.quoteAsString(stringBuilder2, stringBuilder);
            } finally {
                GelfLayout.trimToMaxSize(stringBuilder2);
            }
        } else {
            JsonUtils.quoteAsString(GelfLayout.toNullSafeString(object.getFormattedMessage()), stringBuilder);
        }
        stringBuilder.append('\"');
        stringBuilder.append('}');
        return stringBuilder;
    }

    private static boolean valueNeedsLookup(String string) {
        return string != null && string.contains("${");
    }

    private static StringBuilder getMessageStringBuilder() {
        StringBuilder stringBuilder = messageStringBuilder.get();
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder(1024);
            messageStringBuilder.set(stringBuilder);
        }
        stringBuilder.setLength(0);
        return stringBuilder;
    }

    private static CharSequence toNullSafeString(CharSequence charSequence) {
        return charSequence == null ? "" : charSequence;
    }

    static CharSequence formatTimestamp(long l) {
        if (l < 1000L) {
            return "0";
        }
        StringBuilder stringBuilder = GelfLayout.getTimestampStringBuilder();
        stringBuilder.append(l);
        stringBuilder.insert(stringBuilder.length() - 3, '.');
        return stringBuilder;
    }

    private static StringBuilder getTimestampStringBuilder() {
        StringBuilder stringBuilder = timestampStringBuilder.get();
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder(20);
            timestampStringBuilder.set(stringBuilder);
        }
        stringBuilder.setLength(0);
        return stringBuilder;
    }

    private int formatLevel(Level level) {
        return Severity.getSeverity(level).getCode();
    }

    static CharSequence formatThrowable(Throwable throwable) {
        StringWriter stringWriter = new StringWriter(2048);
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.getBuffer();
    }

    @Override
    public Serializable toSerializable(LogEvent logEvent) {
        return this.toSerializable(logEvent);
    }

    @Override
    public void encode(Object object, ByteBufferDestination byteBufferDestination) {
        this.encode((LogEvent)object, byteBufferDestination);
    }

    GelfLayout(Configuration configuration, String string, KeyValuePair[] keyValuePairArray, CompressionType compressionType, int n, boolean bl, boolean bl2, 1 var8_8) {
        this(configuration, string, keyValuePairArray, compressionType, n, bl, bl2);
    }

    static CharSequence access$200(CharSequence charSequence) {
        return GelfLayout.toNullSafeString(charSequence);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
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
            return new GelfLayout(this.getConfiguration(), this.host, this.additionalFields, this.compressionType, this.compressionThreshold, this.includeStacktrace, this.includeThreadContext, null);
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

        public B setHost(String string) {
            this.host = string;
            return (B)((Builder)this.asBuilder());
        }

        public B setCompressionType(CompressionType compressionType) {
            this.compressionType = compressionType;
            return (B)((Builder)this.asBuilder());
        }

        public B setCompressionThreshold(int n) {
            this.compressionThreshold = n;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludeStacktrace(boolean bl) {
            this.includeStacktrace = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setIncludeThreadContext(boolean bl) {
            this.includeThreadContext = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B setAdditionalFields(KeyValuePair[] keyValuePairArray) {
            this.additionalFields = keyValuePairArray;
            return (B)((Builder)this.asBuilder());
        }

        @Override
        public Object build() {
            return this.build();
        }
    }

    public static enum CompressionType {
        GZIP{

            @Override
            public DeflaterOutputStream createDeflaterOutputStream(OutputStream outputStream) throws IOException {
                return new GZIPOutputStream(outputStream);
            }
        }
        ,
        ZLIB{

            @Override
            public DeflaterOutputStream createDeflaterOutputStream(OutputStream outputStream) throws IOException {
                return new DeflaterOutputStream(outputStream);
            }
        }
        ,
        OFF{

            @Override
            public DeflaterOutputStream createDeflaterOutputStream(OutputStream outputStream) throws IOException {
                return null;
            }
        };


        private CompressionType() {
        }

        public abstract DeflaterOutputStream createDeflaterOutputStream(OutputStream var1) throws IOException;

        CompressionType(1 var3_3) {
            this();
        }
    }
}

