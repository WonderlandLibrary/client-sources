/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.core.util.Constants;

public class OutputStreamManager
extends AbstractManager
implements ByteBufferDestination {
    protected final Layout<?> layout;
    protected ByteBuffer byteBuffer;
    private volatile OutputStream os;
    private boolean skipFooter;

    protected OutputStreamManager(OutputStream outputStream, String string, Layout<?> layout, boolean bl) {
        this(outputStream, string, layout, bl, Constants.ENCODER_BYTE_BUFFER_SIZE);
    }

    protected OutputStreamManager(OutputStream outputStream, String string, Layout<?> layout, boolean bl, int n) {
        this(outputStream, string, layout, bl, ByteBuffer.wrap(new byte[n]));
    }

    @Deprecated
    protected OutputStreamManager(OutputStream outputStream, String string, Layout<?> layout, boolean bl, ByteBuffer byteBuffer) {
        super(null, string);
        byte[] byArray;
        this.os = outputStream;
        this.layout = layout;
        if (bl && layout != null && (byArray = layout.getHeader()) != null) {
            try {
                this.getOutputStream().write(byArray, 0, byArray.length);
            } catch (IOException iOException) {
                this.logError("Unable to write header", iOException);
            }
        }
        this.byteBuffer = Objects.requireNonNull(byteBuffer, "byteBuffer");
    }

    protected OutputStreamManager(LoggerContext loggerContext, OutputStream outputStream, String string, boolean bl, Layout<? extends Serializable> layout, boolean bl2, ByteBuffer byteBuffer) {
        super(loggerContext, string);
        byte[] byArray;
        if (bl && outputStream != null) {
            LOGGER.error("Invalid OutputStreamManager configuration for '{}': You cannot both set the OutputStream and request on-demand.", (Object)string);
        }
        this.layout = layout;
        this.byteBuffer = Objects.requireNonNull(byteBuffer, "byteBuffer");
        this.os = outputStream;
        if (bl2 && layout != null && (byArray = layout.getHeader()) != null) {
            try {
                this.getOutputStream().write(byArray, 0, byArray.length);
            } catch (IOException iOException) {
                this.logError("Unable to write header for " + string, iOException);
            }
        }
    }

    public static <T> OutputStreamManager getManager(String string, T t, ManagerFactory<? extends OutputStreamManager, T> managerFactory) {
        return AbstractManager.getManager(string, managerFactory, t);
    }

    protected OutputStream createOutputStream() throws IOException {
        throw new IllegalStateException(this.getClass().getCanonicalName() + " must implement createOutputStream()");
    }

    public void skipFooter(boolean bl) {
        this.skipFooter = bl;
    }

    @Override
    public boolean releaseSub(long l, TimeUnit timeUnit) {
        this.writeFooter();
        return this.closeOutputStream();
    }

    protected void writeFooter() {
        if (this.layout == null || this.skipFooter) {
            return;
        }
        byte[] byArray = this.layout.getFooter();
        if (byArray != null) {
            this.write(byArray);
        }
    }

    public boolean isOpen() {
        return this.getCount() > 0;
    }

    public boolean hasOutputStream() {
        return this.os != null;
    }

    protected OutputStream getOutputStream() throws IOException {
        if (this.os == null) {
            this.os = this.createOutputStream();
        }
        return this.os;
    }

    protected void setOutputStream(OutputStream outputStream) {
        byte[] byArray = this.layout.getHeader();
        if (byArray != null) {
            try {
                outputStream.write(byArray, 0, byArray.length);
                this.os = outputStream;
            } catch (IOException iOException) {
                this.logError("Unable to write header", iOException);
            }
        } else {
            this.os = outputStream;
        }
    }

    protected void write(byte[] byArray) {
        this.write(byArray, 0, byArray.length, true);
    }

    protected void write(byte[] byArray, boolean bl) {
        this.write(byArray, 0, byArray.length, bl);
    }

    protected void write(byte[] byArray, int n, int n2) {
        this.write(byArray, n, n2, true);
    }

    protected synchronized void write(byte[] byArray, int n, int n2, boolean bl) {
        if (bl && this.byteBuffer.position() == 0) {
            this.writeToDestination(byArray, n, n2);
            this.flushDestination();
            return;
        }
        if (n2 >= this.byteBuffer.capacity()) {
            this.flush();
            this.writeToDestination(byArray, n, n2);
        } else {
            if (n2 > this.byteBuffer.remaining()) {
                this.flush();
            }
            this.byteBuffer.put(byArray, n, n2);
        }
        if (bl) {
            this.flush();
        }
    }

    protected synchronized void writeToDestination(byte[] byArray, int n, int n2) {
        try {
            this.getOutputStream().write(byArray, n, n2);
        } catch (IOException iOException) {
            throw new AppenderLoggingException("Error writing to stream " + this.getName(), iOException);
        }
    }

    protected synchronized void flushDestination() {
        OutputStream outputStream = this.os;
        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException iOException) {
                throw new AppenderLoggingException("Error flushing stream " + this.getName(), iOException);
            }
        }
    }

    protected synchronized void flushBuffer(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        if (byteBuffer.limit() > 0) {
            this.writeToDestination(byteBuffer.array(), 0, byteBuffer.limit());
        }
        byteBuffer.clear();
    }

    public synchronized void flush() {
        this.flushBuffer(this.byteBuffer);
        this.flushDestination();
    }

    protected synchronized boolean closeOutputStream() {
        this.flush();
        OutputStream outputStream = this.os;
        if (outputStream == null || outputStream == System.out || outputStream == System.err) {
            return false;
        }
        try {
            outputStream.close();
        } catch (IOException iOException) {
            this.logError("Unable to close stream", iOException);
            return true;
        }
        return false;
    }

    @Override
    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    @Override
    public ByteBuffer drain(ByteBuffer byteBuffer) {
        this.flushBuffer(byteBuffer);
        return byteBuffer;
    }
}

