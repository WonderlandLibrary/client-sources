/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import org.apache.http.Consts;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
public abstract class AbstractSessionInputBuffer
implements SessionInputBuffer,
BufferInfo {
    private InputStream inStream;
    private byte[] buffer;
    private ByteArrayBuffer lineBuffer;
    private Charset charset;
    private boolean ascii;
    private int maxLineLen;
    private int minChunkLimit;
    private HttpTransportMetricsImpl metrics;
    private CodingErrorAction onMalformedCharAction;
    private CodingErrorAction onUnmappableCharAction;
    private int bufferPos;
    private int bufferLen;
    private CharsetDecoder decoder;
    private CharBuffer cbuf;

    protected void init(InputStream inputStream, int n, HttpParams httpParams) {
        Args.notNull(inputStream, "Input stream");
        Args.notNegative(n, "Buffer size");
        Args.notNull(httpParams, "HTTP parameters");
        this.inStream = inputStream;
        this.buffer = new byte[n];
        this.bufferPos = 0;
        this.bufferLen = 0;
        this.lineBuffer = new ByteArrayBuffer(n);
        String string = (String)httpParams.getParameter("http.protocol.element-charset");
        this.charset = string != null ? Charset.forName(string) : Consts.ASCII;
        this.ascii = this.charset.equals(Consts.ASCII);
        this.decoder = null;
        this.maxLineLen = httpParams.getIntParameter("http.connection.max-line-length", -1);
        this.minChunkLimit = httpParams.getIntParameter("http.connection.min-chunk-limit", 512);
        this.metrics = this.createTransportMetrics();
        CodingErrorAction codingErrorAction = (CodingErrorAction)httpParams.getParameter("http.malformed.input.action");
        this.onMalformedCharAction = codingErrorAction != null ? codingErrorAction : CodingErrorAction.REPORT;
        CodingErrorAction codingErrorAction2 = (CodingErrorAction)httpParams.getParameter("http.unmappable.input.action");
        this.onUnmappableCharAction = codingErrorAction2 != null ? codingErrorAction2 : CodingErrorAction.REPORT;
    }

    protected HttpTransportMetricsImpl createTransportMetrics() {
        return new HttpTransportMetricsImpl();
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

    protected int fillBuffer() throws IOException {
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
        if ((n3 = this.inStream.read(this.buffer, n2 = this.bufferLen, n = this.buffer.length - n2)) == -1) {
            return 1;
        }
        this.bufferLen = n2 + n3;
        this.metrics.incrementBytesTransferred(n3);
        return n3;
    }

    protected boolean hasBufferedData() {
        return this.bufferPos < this.bufferLen;
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
            int n5 = this.inStream.read(byArray, n, n2);
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

    private int locateLF() {
        for (int i = this.bufferPos; i < this.bufferLen; ++i) {
            if (this.buffer[i] != 10) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        Args.notNull(charArrayBuffer, "Char array buffer");
        int n = 0;
        boolean bl = true;
        while (bl) {
            int n2;
            int n3 = this.locateLF();
            if (n3 != -1) {
                if (this.lineBuffer.isEmpty()) {
                    return this.lineFromReadBuffer(charArrayBuffer, n3);
                }
                bl = false;
                n2 = n3 + 1 - this.bufferPos;
                this.lineBuffer.append(this.buffer, this.bufferPos, n2);
                this.bufferPos = n3 + 1;
            } else {
                if (this.hasBufferedData()) {
                    n2 = this.bufferLen - this.bufferPos;
                    this.lineBuffer.append(this.buffer, this.bufferPos, n2);
                    this.bufferPos = this.bufferLen;
                }
                if ((n = this.fillBuffer()) == -1) {
                    bl = false;
                }
            }
            if (this.maxLineLen <= 0 || this.lineBuffer.length() < this.maxLineLen) continue;
            throw new IOException("Maximum line length limit exceeded");
        }
        if (n == -1 && this.lineBuffer.isEmpty()) {
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
        if (this.ascii) {
            charArrayBuffer.append(this.lineBuffer, 0, n);
        } else {
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.lineBuffer.buffer(), 0, n);
            n = this.appendDecoded(charArrayBuffer, byteBuffer);
        }
        this.lineBuffer.clear();
        return n;
    }

    private int lineFromReadBuffer(CharArrayBuffer charArrayBuffer, int n) throws IOException {
        int n2 = this.bufferPos;
        int n3 = n;
        this.bufferPos = n3 + 1;
        if (n3 > n2 && this.buffer[n3 - 1] == 13) {
            --n3;
        }
        int n4 = n3 - n2;
        if (this.ascii) {
            charArrayBuffer.append(this.buffer, n2, n4);
        } else {
            ByteBuffer byteBuffer = ByteBuffer.wrap(this.buffer, n2, n4);
            n4 = this.appendDecoded(charArrayBuffer, byteBuffer);
        }
        return n4;
    }

    private int appendDecoded(CharArrayBuffer charArrayBuffer, ByteBuffer byteBuffer) throws IOException {
        CoderResult coderResult;
        if (!byteBuffer.hasRemaining()) {
            return 1;
        }
        if (this.decoder == null) {
            this.decoder = this.charset.newDecoder();
            this.decoder.onMalformedInput(this.onMalformedCharAction);
            this.decoder.onUnmappableCharacter(this.onUnmappableCharAction);
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
        if (n != -1) {
            return charArrayBuffer.toString();
        }
        return null;
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }
}

