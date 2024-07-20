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
    public AbstractLayout(byte[] header, byte[] footer) {
        this(null, header, footer);
    }

    public AbstractLayout(Configuration configuration, byte[] header, byte[] footer) {
        this.configuration = configuration;
        this.header = header;
        this.footer = footer;
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
    public void encode(LogEvent event, ByteBufferDestination destination) {
        byte[] data = this.toByteArray(event);
        AbstractLayout.writeTo(data, 0, data.length, destination);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeTo(byte[] data, int offset, int length, ByteBufferDestination destination) {
        int chunk = 0;
        ByteBufferDestination byteBufferDestination = destination;
        synchronized (byteBufferDestination) {
            ByteBuffer buffer = destination.getByteBuffer();
            do {
                if (length > buffer.remaining()) {
                    buffer = destination.drain(buffer);
                }
                chunk = Math.min(length, buffer.remaining());
                buffer.put(data, offset, chunk);
                offset += chunk;
            } while ((length -= chunk) > 0);
        }
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

        public B setFooter(byte[] footer) {
            this.footer = footer;
            return this.asBuilder();
        }

        public B setHeader(byte[] header) {
            this.header = header;
            return this.asBuilder();
        }
    }
}

