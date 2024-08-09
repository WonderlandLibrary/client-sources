/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.io;

import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.io.MeasurableInputStream;
import it.unimi.dsi.fastutil.io.RepositionableStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class InspectableFileCachedInputStream
extends MeasurableInputStream
implements RepositionableStream,
WritableByteChannel {
    public static final boolean DEBUG = false;
    public static final int DEFAULT_BUFFER_SIZE = 65536;
    public final byte[] buffer;
    public int inspectable;
    private final File overflowFile;
    private final RandomAccessFile randomAccessFile;
    private final FileChannel fileChannel;
    private long position;
    private long mark;
    private long writePosition;

    public InspectableFileCachedInputStream(int n, File file) throws IOException {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal buffer size " + n);
        }
        if (file != null) {
            this.overflowFile = file;
        } else {
            this.overflowFile = File.createTempFile(this.getClass().getSimpleName(), "overflow");
            this.overflowFile.deleteOnExit();
        }
        this.buffer = new byte[n];
        this.randomAccessFile = new RandomAccessFile(this.overflowFile, "rw");
        this.fileChannel = this.randomAccessFile.getChannel();
        this.mark = -1L;
    }

    public InspectableFileCachedInputStream(int n) throws IOException {
        this(n, null);
    }

    public InspectableFileCachedInputStream() throws IOException {
        this(65536);
    }

    private void ensureOpen() throws IOException {
        if (this.position == -1L) {
            throw new IOException("This " + this.getClass().getSimpleName() + " is closed");
        }
    }

    public void clear() throws IOException {
        if (!this.fileChannel.isOpen()) {
            throw new IOException("This " + this.getClass().getSimpleName() + " is closed");
        }
        this.inspectable = 0;
        this.writePosition = this.position = (long)0;
        this.mark = -1L;
    }

    @Override
    public int write(ByteBuffer byteBuffer) throws IOException {
        this.ensureOpen();
        int n = byteBuffer.remaining();
        if (this.inspectable < this.buffer.length) {
            int n2 = Math.min(this.buffer.length - this.inspectable, n);
            byteBuffer.get(this.buffer, this.inspectable, n2);
            this.inspectable += n2;
        }
        if (byteBuffer.hasRemaining()) {
            this.fileChannel.position(this.writePosition);
            this.writePosition += (long)this.fileChannel.write(byteBuffer);
        }
        return n;
    }

    public void truncate(long l) throws FileNotFoundException, IOException {
        this.fileChannel.truncate(Math.max(l, this.writePosition));
    }

    @Override
    public void close() {
        this.position = -1L;
    }

    public void reopen() throws IOException {
        if (!this.fileChannel.isOpen()) {
            throw new IOException("This " + this.getClass().getSimpleName() + " is closed");
        }
        this.position = 0L;
    }

    public void dispose() throws IOException {
        this.position = -1L;
        this.randomAccessFile.close();
        this.overflowFile.delete();
    }

    protected void finalize() throws Throwable {
        try {
            this.dispose();
        } finally {
            super.finalize();
        }
    }

    @Override
    public int available() throws IOException {
        this.ensureOpen();
        return (int)Math.min(Integer.MAX_VALUE, this.length() - this.position);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        this.ensureOpen();
        if (n2 == 0) {
            return 1;
        }
        if (this.position == this.length()) {
            return 1;
        }
        ByteArrays.ensureOffsetLength(byArray, n, n2);
        int n4 = 0;
        if (this.position < (long)this.inspectable) {
            n3 = Math.min(this.inspectable - (int)this.position, n2);
            System.arraycopy(this.buffer, (int)this.position, byArray, n, n3);
            n2 -= n3;
            n += n3;
            this.position += (long)n3;
            n4 = n3;
        }
        if (n2 > 0) {
            if (this.position == this.length()) {
                return n4 != 0 ? n4 : -1;
            }
            this.fileChannel.position(this.position - (long)this.inspectable);
            n3 = (int)Math.min(this.length() - this.position, (long)n2);
            int n5 = this.randomAccessFile.read(byArray, n, n3);
            this.position += (long)n5;
            n4 += n5;
        }
        return n4;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public long skip(long l) throws IOException {
        this.ensureOpen();
        long l2 = Math.min(l, this.length() - this.position);
        this.position += l2;
        return l2;
    }

    @Override
    public int read() throws IOException {
        this.ensureOpen();
        if (this.position == this.length()) {
            return 1;
        }
        if (this.position < (long)this.inspectable) {
            return this.buffer[(int)this.position++] & 0xFF;
        }
        this.fileChannel.position(this.position - (long)this.inspectable);
        ++this.position;
        return this.randomAccessFile.read();
    }

    @Override
    public long length() throws IOException {
        this.ensureOpen();
        return (long)this.inspectable + this.writePosition;
    }

    @Override
    public long position() throws IOException {
        this.ensureOpen();
        return this.position;
    }

    @Override
    public void position(long l) throws IOException {
        this.position = Math.min(l, this.length());
    }

    @Override
    public boolean isOpen() {
        return this.position != -1L;
    }

    @Override
    public void mark(int n) {
        this.mark = this.position;
    }

    @Override
    public void reset() throws IOException {
        this.ensureOpen();
        if (this.mark == -1L) {
            throw new IOException("Mark has not been set");
        }
        this.position(this.mark);
    }

    @Override
    public boolean markSupported() {
        return false;
    }
}

