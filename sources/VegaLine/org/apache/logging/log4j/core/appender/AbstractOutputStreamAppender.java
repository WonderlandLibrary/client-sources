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

    protected AbstractOutputStreamAppender(String name, Layout<? extends Serializable> layout, Filter filter, boolean ignoreExceptions, boolean immediateFlush, M manager) {
        super(name, filter, layout, ignoreExceptions);
        this.manager = manager;
        this.immediateFlush = immediateFlush;
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
    public boolean stop(long timeout, TimeUnit timeUnit) {
        return this.stop(timeout, timeUnit, true);
    }

    @Override
    protected boolean stop(long timeout, TimeUnit timeUnit, boolean changeLifeCycleState) {
        boolean stopped = super.stop(timeout, timeUnit, changeLifeCycleState);
        stopped &= ((AbstractManager)this.manager).stop(timeout, timeUnit);
        if (changeLifeCycleState) {
            this.setStopped();
        }
        LOGGER.debug("Appender {} stopped with status {}", (Object)this.getName(), (Object)stopped);
        return stopped;
    }

    @Override
    public void append(LogEvent event) {
        try {
            this.tryAppend(event);
        } catch (AppenderLoggingException ex) {
            this.error("Unable to write to stream " + ((AbstractManager)this.manager).getName() + " for appender " + this.getName() + ": " + ex);
            throw ex;
        }
    }

    private void tryAppend(LogEvent event) {
        if (Constants.ENABLE_DIRECT_ENCODERS) {
            this.directEncodeEvent(event);
        } else {
            this.writeByteArrayToManager(event);
        }
    }

    protected void directEncodeEvent(LogEvent event) {
        this.getLayout().encode(event, (ByteBufferDestination)this.manager);
        if (this.immediateFlush || event.isEndOfBatch()) {
            ((OutputStreamManager)this.manager).flush();
        }
    }

    protected void writeByteArrayToManager(LogEvent event) {
        byte[] bytes = this.getLayout().toByteArray(event);
        if (bytes != null && bytes.length > 0) {
            ((OutputStreamManager)this.manager).write(bytes, this.immediateFlush || event.isEndOfBatch());
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

        public B withImmediateFlush(boolean immediateFlush) {
            this.immediateFlush = immediateFlush;
            return (B)((Builder)this.asBuilder());
        }

        public B withBufferedIo(boolean bufferedIo) {
            this.bufferedIo = bufferedIo;
            return (B)((Builder)this.asBuilder());
        }

        public B withBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return (B)((Builder)this.asBuilder());
        }
    }
}

