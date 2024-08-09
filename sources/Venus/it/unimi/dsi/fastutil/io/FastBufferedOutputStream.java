/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.io;

import it.unimi.dsi.fastutil.io.MeasurableOutputStream;
import it.unimi.dsi.fastutil.io.MeasurableStream;
import it.unimi.dsi.fastutil.io.RepositionableStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;

public class FastBufferedOutputStream
extends MeasurableOutputStream
implements RepositionableStream {
    private static final boolean ASSERTS = false;
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    protected byte[] buffer;
    protected int pos;
    protected int avail;
    protected OutputStream os;
    private FileChannel fileChannel;
    private RepositionableStream repositionableStream;
    private MeasurableStream measurableStream;

    private static int ensureBufferSize(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal buffer size: " + n);
        }
        return n;
    }

    public FastBufferedOutputStream(OutputStream outputStream, byte[] byArray) {
        this.os = outputStream;
        FastBufferedOutputStream.ensureBufferSize(byArray.length);
        this.buffer = byArray;
        this.avail = byArray.length;
        if (outputStream instanceof RepositionableStream) {
            this.repositionableStream = (RepositionableStream)((Object)outputStream);
        }
        if (outputStream instanceof MeasurableStream) {
            this.measurableStream = (MeasurableStream)((Object)outputStream);
        }
        if (this.repositionableStream == null) {
            try {
                this.fileChannel = (FileChannel)outputStream.getClass().getMethod("getChannel", new Class[0]).invoke(outputStream, new Object[0]);
            } catch (IllegalAccessException illegalAccessException) {
            } catch (IllegalArgumentException illegalArgumentException) {
            } catch (NoSuchMethodException noSuchMethodException) {
            } catch (InvocationTargetException invocationTargetException) {
            } catch (ClassCastException classCastException) {
                // empty catch block
            }
        }
    }

    public FastBufferedOutputStream(OutputStream outputStream, int n) {
        this(outputStream, new byte[FastBufferedOutputStream.ensureBufferSize(n)]);
    }

    public FastBufferedOutputStream(OutputStream outputStream) {
        this(outputStream, 8192);
    }

    private void dumpBuffer(boolean bl) throws IOException {
        if (!bl || this.avail == 0) {
            this.os.write(this.buffer, 0, this.pos);
            this.pos = 0;
            this.avail = this.buffer.length;
        }
    }

    @Override
    public void write(int n) throws IOException {
        --this.avail;
        this.buffer[this.pos++] = (byte)n;
        this.dumpBuffer(true);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (n2 >= this.buffer.length) {
            this.dumpBuffer(false);
            this.os.write(byArray, n, n2);
            return;
        }
        if (n2 <= this.avail) {
            System.arraycopy(byArray, n, this.buffer, this.pos, n2);
            this.pos += n2;
            this.avail -= n2;
            this.dumpBuffer(true);
            return;
        }
        this.dumpBuffer(false);
        System.arraycopy(byArray, n, this.buffer, 0, n2);
        this.pos = n2;
        this.avail -= n2;
    }

    @Override
    public void flush() throws IOException {
        this.dumpBuffer(false);
        this.os.flush();
    }

    @Override
    public void close() throws IOException {
        if (this.os == null) {
            return;
        }
        this.flush();
        if (this.os != System.out) {
            this.os.close();
        }
        this.os = null;
        this.buffer = null;
    }

    @Override
    public long position() throws IOException {
        if (this.repositionableStream != null) {
            return this.repositionableStream.position() + (long)this.pos;
        }
        if (this.measurableStream != null) {
            return this.measurableStream.position() + (long)this.pos;
        }
        if (this.fileChannel != null) {
            return this.fileChannel.position() + (long)this.pos;
        }
        throw new UnsupportedOperationException("position() can only be called if the underlying byte stream implements the MeasurableStream or RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel");
    }

    @Override
    public void position(long l) throws IOException {
        this.flush();
        if (this.repositionableStream != null) {
            this.repositionableStream.position(l);
        } else if (this.fileChannel != null) {
            this.fileChannel.position(l);
        } else {
            throw new UnsupportedOperationException("position() can only be called if the underlying byte stream implements the RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel");
        }
    }

    @Override
    public long length() throws IOException {
        this.flush();
        if (this.measurableStream != null) {
            return this.measurableStream.length();
        }
        if (this.fileChannel != null) {
            return this.fileChannel.size();
        }
        throw new UnsupportedOperationException();
    }
}

