/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.core.util.Constants;

public abstract class AbstractOutputStreamAppender<M extends OutputStreamManager>
extends AbstractAppender {
    private final boolean immediateFlush;
    private final M manager;

    protected AbstractOutputStreamAppender(String string, Layout<? extends Serializable> layout, Filter filter, boolean bl, boolean bl2, M m) {
        super(string, filter, layout, bl);
        this.manager = m;
        this.immediateFlush = bl2;
    }

    public boolean getImmediateFlush() {
        return this.immediateFlush;
    }

    public M getManager() {
        return this.manager;
    }

    @Override
    public void start() {
        if (this.getLayout() == null) {
            LOGGER.error("No layout set for the appender named [" + this.getName() + "].");
        }
        if (this.manager == null) {
            LOGGER.error("No OutputStreamManager set for the appender named [" + this.getName() + "].");
        }
        super.start();
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        return this.stop(l, timeUnit, false);
    }

    @Override
    protected boolean stop(long l, TimeUnit timeUnit, boolean bl) {
        boolean bl2 = super.stop(l, timeUnit, bl);
        bl2 &= ((AbstractManager)this.manager).stop(l, timeUnit);
        if (bl) {
            this.setStopped();
        }
        LOGGER.debug("Appender {} stopped with status {}", (Object)this.getName(), (Object)bl2);
        return bl2;
    }

    @Override
    public void append(LogEvent logEvent) {
        try {
            this.tryAppend(logEvent);
        } catch (AppenderLoggingException appenderLoggingException) {
            this.error("Unable to write to stream " + ((AbstractManager)this.manager).getName() + " for appender " + this.getName() + ": " + appenderLoggingException);
            throw appenderLoggingException;
        }
    }

    private void tryAppend(LogEvent logEvent) {
        if (Constants.ENABLE_DIRECT_ENCODERS) {
            this.directEncodeEvent(logEvent);
        } else {
            this.writeByteArrayToManager(logEvent);
        }
    }

    protected void directEncodeEvent(LogEvent logEvent) {
        this.getLayout().encode(logEvent, (ByteBufferDestination)this.manager);
        if (this.immediateFlush || logEvent.isEndOfBatch()) {
            ((OutputStreamManager)this.manager).flush();
        }
    }

    protected void writeByteArrayToManager(LogEvent logEvent) {
        byte[] byArray = this.getLayout().toByteArray(logEvent);
        if (byArray != null && byArray.length > 0) {
            ((OutputStreamManager)this.manager).write(byArray, this.immediateFlush || logEvent.isEndOfBatch());
        }
    }

    public static abstract class Builder<B extends Builder<B>>
    extends AbstractAppender.Builder<B> {
        @PluginBuilderAttribute
        private boolean bufferedIo = true;
        @PluginBuilderAttribute
        private int bufferSize = Constants.ENCODER_BYTE_BUFFER_SIZE;
        @PluginBuilderAttribute
        private boolean immediateFlush = true;

        public int getBufferSize() {
            return this.bufferSize;
        }

        public boolean isBufferedIo() {
            return this.bufferedIo;
        }

        public boolean isImmediateFlush() {
            return this.immediateFlush;
        }

        public B withImmediateFlush(boolean bl) {
            this.immediateFlush = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B withBufferedIo(boolean bl) {
            this.bufferedIo = bl;
            return (B)((Builder)this.asBuilder());
        }

        public B withBufferSize(int n) {
            this.bufferSize = n;
            return (B)((Builder)this.asBuilder());
        }
    }
}

