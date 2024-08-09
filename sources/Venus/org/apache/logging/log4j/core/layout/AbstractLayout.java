/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.status.StatusLogger;

public abstract class AbstractLayout<T extends Serializable>
implements Layout<T> {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    protected final Configuration configuration;
    protected long eventCount;
    protected final byte[] footer;
    protected final byte[] header;

    @Deprecated
    public AbstractLayout(byte[] byArray, byte[] byArray2) {
        this(null, byArray, byArray2);
    }

    public AbstractLayout(Configuration configuration, byte[] byArray, byte[] byArray2) {
        this.configuration = configuration;
        this.header = byArray;
        this.footer = byArray2;
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public Map<String, String> getContentFormat() {
        return new HashMap<String, String>();
    }

    @Override
    public byte[] getFooter() {
        return this.footer;
    }

    @Override
    public byte[] getHeader() {
        return this.header;
    }

    protected void markEvent() {
        ++this.eventCount;
    }

    @Override
    public void encode(LogEvent logEvent, ByteBufferDestination byteBufferDestination) {
        byte[] byArray = this.toByteArray(logEvent);
        AbstractLayout.writeTo(byArray, 0, byArray.length, byteBufferDestination);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeTo(byte[] byArray, int n, int n2, ByteBufferDestination byteBufferDestination) {
        int n3 = 0;
        ByteBufferDestination byteBufferDestination2 = byteBufferDestination;
        synchronized (byteBufferDestination2) {
            ByteBuffer byteBuffer = byteBufferDestination.getByteBuffer();
            do {
                if (n2 > byteBuffer.remaining()) {
                    byteBuffer = byteBufferDestination.drain(byteBuffer);
                }
                n3 = Math.min(n2, byteBuffer.remaining());
                byteBuffer.put(byArray, n, n3);
                n += n3;
            } while ((n2 -= n3) > 0);
        }
    }

    @Override
    public void encode(Object object, ByteBufferDestination byteBufferDestination) {
        this.encode((LogEvent)object, byteBufferDestination);
    }

    public static abstract class Builder<B extends Builder<B>> {
        @PluginConfiguration
        private Configuration configuration;
        @PluginBuilderAttribute
        private byte[] footer;
        @PluginBuilderAttribute
        private byte[] header;

        public B asBuilder() {
            return (B)this;
        }

        public Configuration getConfiguration() {
            return this.configuration;
        }

        public byte[] getFooter() {
            return this.footer;
        }

        public byte[] getHeader() {
            return this.header;
        }

        public B setConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this.asBuilder();
        }

        public B setFooter(byte[] byArray) {
            this.footer = byArray;
            return this.asBuilder();
        }

        public B setHeader(byte[] byArray) {
            this.header = byArray;
            return this.asBuilder();
        }
    }
}

