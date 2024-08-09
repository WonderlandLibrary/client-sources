/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.io;

import it.unimi.dsi.fastutil.io.BinIO;
import it.unimi.dsi.fastutil.io.MeasurableInputStream;
import it.unimi.dsi.fastutil.io.RepositionableStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class FastMultiByteArrayInputStream
extends MeasurableInputStream
implements RepositionableStream {
    public static final int SLICE_BITS = 10;
    public static final int SLICE_SIZE = 1024;
    public static final int SLICE_MASK = 1023;
    public byte[][] array;
    public byte[] current;
    public long length;
    private long position;

    public FastMultiByteArrayInputStream(MeasurableInputStream measurableInputStream) throws IOException {
        this(measurableInputStream, measurableInputStream.length());
    }

    public FastMultiByteArrayInputStream(InputStream inputStream, long l) throws IOException {
        this.length = l;
        this.array = new byte[(int)((l + 1024L - 1L) / 1024L) + 1][];
        for (int i = 0; i < this.array.length - 1; ++i) {
            this.array[i] = new byte[l >= 1024L ? 1024 : (int)l];
            if (BinIO.loadBytes(inputStream, this.array[i]) != this.array[i].length) {
                throw new EOFException();
            }
            l -= (long)this.array[i].length;
        }
        this.current = this.array[0];
    }

    public FastMultiByteArrayInputStream(FastMultiByteArrayInputStream fastMultiByteArrayInputStream) {
        this.array = fastMultiByteArrayInputStream.array;
        this.length = fastMultiByteArrayInputStream.length;
        this.current = this.array[0];
    }

    public FastMultiByteArrayInputStream(byte[] byArray) {
        if (byArray.length == 0) {
            this.array = new byte[1][];
        } else {
            this.array = new byte[2][];
            this.array[0] = byArray;
            this.length = byArray.length;
            this.current = byArray;
        }
    }

    @Override
    public int available() {
        return (int)Math.min(Integer.MAX_VALUE, this.length - this.position);
    }

    @Override
    public long skip(long l) {
        if (l > this.length - this.position) {
            l = this.length - this.position;
        }
        this.position += l;
        this.updateCurrent();
        return l;
    }

    @Override
    public int read() {
        int n;
        if (this.length == this.position) {
            return 1;
        }
        if ((n = (int)(this.position++ & 0x3FFL)) == 0) {
            this.updateCurrent();
        }
        return this.current[n] & 0xFF;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) {
        int n3;
        long l = this.length - this.position;
        if (l == 0L) {
            return n2 == 0 ? 0 : -1;
        }
        int n4 = n3 = (int)Math.min((long)n2, l);
        while (true) {
            int n5;
            if ((n5 = (int)(this.position & 0x3FFL)) == 0) {
                this.updateCurrent();
            }
            int n6 = Math.min(n3, this.current.length - n5);
            System.arraycopy(this.current, n5, byArray, n, n6);
            this.position += (long)n6;
            if ((n3 -= n6) == 0) {
                return n4;
            }
            n += n6;
        }
    }

    private void updateCurrent() {
        this.current = this.array[(int)(this.position >>> 10)];
    }

    @Override
    public long position() {
        return this.position;
    }

    @Override
    public void position(long l) {
        this.position = Math.min(l, this.length);
        this.updateCurrent();
    }

    @Override
    public long length() throws IOException {
        return this.length;
    }

    @Override
    public void close() {
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }
}

