/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.input.ClosedInputStream;

public class ByteArrayOutputStream
extends OutputStream {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private final List<byte[]> buffers = new ArrayList<byte[]>();
    private int currentBufferIndex;
    private int filledBufferSum;
    private byte[] currentBuffer;
    private int count;
    private boolean reuseBuffers = true;

    public ByteArrayOutputStream() {
        this(1024);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ByteArrayOutputStream(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative initial size: " + n);
        }
        ByteArrayOutputStream byteArrayOutputStream = this;
        synchronized (byteArrayOutputStream) {
            this.needNewBuffer(n);
        }
    }

    private void needNewBuffer(int n) {
        if (this.currentBufferIndex < this.buffers.size() - 1) {
            this.filledBufferSum += this.currentBuffer.length;
            ++this.currentBufferIndex;
            this.currentBuffer = this.buffers.get(this.currentBufferIndex);
        } else {
            int n2;
            if (this.currentBuffer == null) {
                n2 = n;
                this.filledBufferSum = 0;
            } else {
                n2 = Math.max(this.currentBuffer.length << 1, n - this.filledBufferSum);
                this.filledBufferSum += this.currentBuffer.length;
            }
            ++this.currentBufferIndex;
            this.currentBuffer = new byte[n2];
            this.buffers.add(this.currentBuffer);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(byte[] byArray, int n, int n2) {
        if (n < 0 || n > byArray.length || n2 < 0 || n + n2 > byArray.length || n + n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (n2 == 0) {
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = this;
        synchronized (byteArrayOutputStream) {
            int n3 = this.count + n2;
            int n4 = n2;
            int n5 = this.count - this.filledBufferSum;
            while (n4 > 0) {
                int n6 = Math.min(n4, this.currentBuffer.length - n5);
                System.arraycopy(byArray, n + n2 - n4, this.currentBuffer, n5, n6);
                if ((n4 -= n6) <= 0) continue;
                this.needNewBuffer(n3);
                n5 = 0;
            }
            this.count = n3;
        }
    }

    @Override
    public synchronized void write(int n) {
        int n2 = this.count - this.filledBufferSum;
        if (n2 == this.currentBuffer.length) {
            this.needNewBuffer(this.count + 1);
            n2 = 0;
        }
        this.currentBuffer[n2] = (byte)n;
        ++this.count;
    }

    public synchronized int write(InputStream inputStream) throws IOException {
        int n = 0;
        int n2 = this.count - this.filledBufferSum;
        int n3 = inputStream.read(this.currentBuffer, n2, this.currentBuffer.length - n2);
        while (n3 != -1) {
            n += n3;
            this.count += n3;
            if ((n2 += n3) == this.currentBuffer.length) {
                this.needNewBuffer(this.currentBuffer.length);
                n2 = 0;
            }
            n3 = inputStream.read(this.currentBuffer, n2, this.currentBuffer.length - n2);
        }
        return n;
    }

    public synchronized int size() {
        return this.count;
    }

    @Override
    public void close() throws IOException {
    }

    public synchronized void reset() {
        this.count = 0;
        this.filledBufferSum = 0;
        this.currentBufferIndex = 0;
        if (this.reuseBuffers) {
            this.currentBuffer = this.buffers.get(this.currentBufferIndex);
        } else {
            this.currentBuffer = null;
            int n = this.buffers.get(0).length;
            this.buffers.clear();
            this.needNewBuffer(n);
            this.reuseBuffers = true;
        }
    }

    public synchronized void writeTo(OutputStream outputStream) throws IOException {
        int n = this.count;
        for (byte[] byArray : this.buffers) {
            int n2 = Math.min(byArray.length, n);
            outputStream.write(byArray, 0, n2);
            if ((n -= n2) != 0) continue;
            break;
        }
    }

    public static InputStream toBufferedInputStream(InputStream inputStream) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream, 1024);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream, int n) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(n);
        byteArrayOutputStream.write(inputStream);
        return byteArrayOutputStream.toInputStream();
    }

    public synchronized InputStream toInputStream() {
        int n = this.count;
        if (n == 0) {
            return new ClosedInputStream();
        }
        ArrayList<ByteArrayInputStream> arrayList = new ArrayList<ByteArrayInputStream>(this.buffers.size());
        for (byte[] byArray : this.buffers) {
            int n2 = Math.min(byArray.length, n);
            arrayList.add(new ByteArrayInputStream(byArray, 0, n2));
            if ((n -= n2) != 0) continue;
            break;
        }
        this.reuseBuffers = false;
        return new SequenceInputStream(Collections.enumeration(arrayList));
    }

    public synchronized byte[] toByteArray() {
        int n = this.count;
        if (n == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] byArray = new byte[n];
        int n2 = 0;
        for (byte[] byArray2 : this.buffers) {
            int n3 = Math.min(byArray2.length, n);
            System.arraycopy(byArray2, 0, byArray, n2, n3);
            n2 += n3;
            if ((n -= n3) != 0) continue;
            break;
        }
        return byArray;
    }

    @Deprecated
    public String toString() {
        return new String(this.toByteArray(), Charset.defaultCharset());
    }

    public String toString(String string) throws UnsupportedEncodingException {
        return new String(this.toByteArray(), string);
    }

    public String toString(Charset charset) {
        return new String(this.toByteArray(), charset);
    }
}

