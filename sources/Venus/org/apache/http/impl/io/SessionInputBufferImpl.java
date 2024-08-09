/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import org.apache.http.MessageConstraintException;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

public class SessionInputBufferImpl
implements SessionInputBuffer,
BufferInfo {
    private final HttpTransportMetricsImpl metrics;
    private final byte[] buffer;
    private final ByteArrayBuffer lineBuffer;
    private final int minChunkLimit;
    private final MessageConstraints constraints;
    private final CharsetDecoder decoder;
    private InputStream inStream;
    private int bufferPos;
    private int bufferLen;
    private CharBuffer cbuf;

    public SessionInputBufferImpl(HttpTransportMetricsImpl httpTransportMetricsImpl, int n, int n2, MessageConstraints messageConstraints, CharsetDecoder charsetDecoder) {
        Args.notNull(httpTransportMetricsImpl, "HTTP transport metrcis");
        Args.positive(n, "Buffer size");
        this.metrics = httpTransportMetricsImpl;
        this.buffer = new byte[n];
        this.bufferPos = 0;
        this.bufferLen = 0;
        this.minChunkLimit = n2 >= 0 ? n2 : 512;
        this.constraints = messageConstraints != null ? messageConstraints : MessageConstraints.DEFAULT;
        this.lineBuffer = new ByteArrayBuffer(n);
        this.decoder = charsetDecoder;
    }

    public SessionInputBufferImpl(HttpTransportMetricsImpl httpTransportMetricsImpl, int n) {
        this(httpTransportMetricsImpl, n, n, null, null);
    }

    public void bind(InputStream inputStream) {
        this.inStream = inputStream;
    }

    public boolean isBound() {
        return this.inStream != null;
    }

    @Override
    public int capacity() {
        return this.buffer.length;
    }

    @Override
    public int length() {
        return this.bufferLen - this.bufferPos;
    }

    @Override
    public int available() {
        return this.capacity() - this.length();
    }

    private int streamRead(byte[] byArray, int n, int n2) throws IOException {
        Asserts.notNull(this.inStream, "Input stream");
        return this.inStream.read(byArray, n, n2);
    }

    public int fillBuffer() throws IOException {
        int n;
        int n2;
        int n3;
        if (this.bufferPos > 0) {
            n3 = this.bufferLen - this.bufferPos;
            if (n3 > 0) {
                System.arraycopy(this.buffer, this.bufferPos, this.buffer, 0, n3);
            }
            this.bufferPos = 0;
            this.bufferLen = n3;
        }
        if ((n3 = this.streamRead(this.buffer, n2 = this.bufferLen, n = this.buffer.length - n2)) == -1) {
            return 1;
        }
        this.bufferLen = n2 + n3;
        this.metrics.incrementBytesTransferred(n3);
        return n3;
    }

    public boolean hasBufferedData() {
        return this.bufferPos < this.bufferLen;
    }

    public void clear() {
        this.bufferPos = 0;
        this.bufferLen = 0;
    }

    @Override
    public int read() throws IOException {
        while (!this.hasBufferedData()) {
            int n = this.fillBuffer();
            if (n != -1) continue;
            return 1;
        }
        return this.buffer[this.bufferPos++] & 0xFF;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (byArray == null) {
            return 1;
        }
        if (this.hasBufferedData()) {
            int n4 = Math.min(n2, this.bufferLen - this.bufferPos);
            System.arraycopy(this.buffer, this.bufferPos, byArray, n, n4);
            this.bufferPos += n4;
            return n4;
        }
        if (n2 > this.minChunkLimit) {
            int n5 = this.streamRead(byArray, n, n2);
            if (n5 > 0) {
                this.metrics.incrementBytesTransferred(n5);
            }
            return n5;
        }
        while (!this.hasBufferedData()) {
            n3 = this.fillBuffer();
            if (n3 != -1) continue;
            return 1;
        }
        n3 = Math.min(n2, this.bufferLen - this.bufferPos);
        System.arraycopy(this.buffer, this.bufferPos, byArray, n, n3);
        this.bufferPos += n3;
        return n3;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        if (byArray == null) {
            return 1;
        }
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        int n = this.constraints.getMaxLineLength();
        int n2 = 0;
        boolean bl = true;
        while (bl) {
            int n3;
            int n4 = -1;
            for (n3 = this.bufferPos; n3 < this.bufferLen; ++n3) {
                if (this.buffer[n3] != 10) continue;
                n4 = n3;
                break;
            }
            if (n > 0 && (n3 = this.lineBuffer.length() + (n4 >= 0 ? n4 : this.bufferLen) - this.bufferPos) >= n) {
                throw new MessageConstraintException("Maximum line length limit exceeded");
            }
            if (n4 != -1) {
                if (this.lineBuffer.isEmpty()) {
                    return this.lineFromReadBuffer(charArrayBuffer, n4);
                }
                bl = false;
                n3 = n4 + 1 - this.bufferPos;
                this.lineBuffer.append(this.buffer, this.bufferPos, n3);
                this.bufferPos = n4 + 1;
                continue;
            }
            if (this.hasBufferedData()) {
                n3 = this.bufferLen - this.bufferPos;
                this.lineBuffer.append(this.buffer, this.bufferPos, n3);
                this.bufferPos = this.bufferLen;
            }
            if ((n2 = this.fillBuffer()) != -1) continue;
            bl = false;
        }
        if (n2 == -1 && this.lineBuffer.isEmpty()) {
            return 1;
        }
        return this.lineFromLineBuffer(charArrayBuffer);
    }

    private int lineFromLineBuffer(CharArrayBuffer charArrayBuffer) throws IOException {
        int n = this.lineBuffer.length();
        if (n > 0) {
            if (this.lineBuffer.byteAt(n - 1) == 10) {
                --n;
            }
            if (n > 0 && this.lineBuffer.byteAt(n - 1) == 13) {
                --n;
            }
        }
        if (this.decoder == null) {
            charArrayBuffer.append(this.lineBuffer, 0, n);
        } else {
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.lineBuffer.buffer(), 0, n);
            n = this.appendDecoded(charArrayBuffer, byteBuffer);
        }
        this.lineBuffer.clear();
        return n;
    }

    private int lineFromReadBuffer(CharArrayBuffer charArrayBuffer, int n) throws IOException {
        int n2 = n;
        int n3 = this.bufferPos;
        this.bufferPos = n2 + 1;
        if (n2 > n3 && this.buffer[n2 - 1] == 13) {
            --n2;
        }
        int n4 = n2 - n3;
        if (this.decoder == null) {
            charArrayBuffer.append(this.buffer, n3, n4);
        } else {
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.buffer, n3, n4);
            n4 = this.appendDecoded(charArrayBuffer, byteBuffer);
        }
        return n4;
    }

    private int appendDecoded(CharArrayBuffer charArrayBuffer, ByteBuffer byteBuffer) throws IOException {
        CoderResult coderResult;
        if (!byteBuffer.hasRemaining()) {
            return 1;
        }
        if (this.cbuf == null) {
            this.cbuf = CharBuffer.allocate(1024);
        }
        this.decoder.reset();
        int n = 0;
        while (byteBuffer.hasRemaining()) {
            coderResult = this.decoder.decode(byteBuffer, this.cbuf, false);
            n += this.handleDecodingResult(coderResult, charArrayBuffer, byteBuffer);
        }
        coderResult = this.decoder.flush(this.cbuf);
        this.cbuf.clear();
        return n += this.handleDecodingResult(coderResult, charArrayBuffer, byteBuffer);
    }

    private int handleDecodingResult(CoderResult coderResult, CharArrayBuffer charArrayBuffer, ByteBuffer byteBuffer) throws IOException {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.cbuf.flip();
        int n = this.cbuf.remaining();
        while (this.cbuf.hasRemaining()) {
            charArrayBuffer.append(this.cbuf.get());
        }
        this.cbuf.compact();
        return n;
    }

    @Override
    public String readLine() throws IOException {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(64);
        int n = this.readLine(charArrayBuffer);
        return n != -1 ? charArrayBuffer.toString() : null;
    }

    @Override
    public boolean isDataAvailable(int n) throws IOException {
        return this.hasBufferedData();
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }
}

