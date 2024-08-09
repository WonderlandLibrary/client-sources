/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import org.apache.http.Consts;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.params.HttpParams;
import org.apache.http.util.Args;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
public abstract class AbstractSessionOutputBuffer
implements SessionOutputBuffer,
BufferInfo {
    private static final byte[] CRLF = new byte[]{13, 10};
    private OutputStream outStream;
    private ByteArrayBuffer buffer;
    private Charset charset;
    private boolean ascii;
    private int minChunkLimit;
    private HttpTransportMetricsImpl metrics;
    private CodingErrorAction onMalformedCharAction;
    private CodingErrorAction onUnmappableCharAction;
    private CharsetEncoder encoder;
    private ByteBuffer bbuf;

    protected AbstractSessionOutputBuffer(OutputStream outputStream, int n, Charset charset, int n2, CodingErrorAction codingErrorAction, CodingErrorAction codingErrorAction2) {
        Args.notNull(outputStream, "Input stream");
        Args.notNegative(n, "Buffer size");
        this.outStream = outputStream;
        this.buffer = new ByteArrayBuffer(n);
        this.charset = charset != null ? charset : Consts.ASCII;
        this.ascii = this.charset.equals(Consts.ASCII);
        this.encoder = null;
        this.minChunkLimit = n2 >= 0 ? n2 : 512;
        this.metrics = this.createTransportMetrics();
        this.onMalformedCharAction = codingErrorAction != null ? codingErrorAction : CodingErrorAction.REPORT;
        this.onUnmappableCharAction = codingErrorAction2 != null ? codingErrorAction2 : CodingErrorAction.REPORT;
    }

    public AbstractSessionOutputBuffer() {
    }

    protected void init(OutputStream outputStream, int n, HttpParams httpParams) {
        Args.notNull(outputStream, "Input stream");
        Args.notNegative(n, "Buffer size");
        Args.notNull(httpParams, "HTTP parameters");
        this.outStream = outputStream;
        this.buffer = new ByteArrayBuffer(n);
        String string = (String)httpParams.getParameter("http.protocol.element-charset");
        this.charset = string != null ? Charset.forName(string) : Consts.ASCII;
        this.ascii = this.charset.equals(Consts.ASCII);
        this.encoder = null;
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
        return this.buffer.capacity();
    }

    @Override
    public int length() {
        return this.buffer.length();
    }

    @Override
    public int available() {
        return this.capacity() - this.length();
    }

    protected void flushBuffer() throws IOException {
        int n = this.buffer.length();
        if (n > 0) {
            this.outStream.write(this.buffer.buffer(), 0, n);
            this.buffer.clear();
            this.metrics.incrementBytesTransferred(n);
        }
    }

    @Override
    public void flush() throws IOException {
        this.flushBuffer();
        this.outStream.flush();
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (byArray == null) {
            return;
        }
        if (n2 > this.minChunkLimit || n2 > this.buffer.capacity()) {
            this.flushBuffer();
            this.outStream.write(byArray, n, n2);
            this.metrics.incrementBytesTransferred(n2);
        } else {
            int n3 = this.buffer.capacity() - this.buffer.length();
            if (n2 > n3) {
                this.flushBuffer();
            }
            this.buffer.append(byArray, n, n2);
        }
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        if (byArray == null) {
            return;
        }
        this.write(byArray, 0, byArray.length);
    }

    @Override
    public void write(int n) throws IOException {
        if (this.buffer.isFull()) {
            this.flushBuffer();
        }
        this.buffer.append(n);
    }

    @Override
    public void writeLine(String string) throws IOException {
        if (string == null) {
            return;
        }
        if (string.length() > 0) {
            if (this.ascii) {
                for (int i = 0; i < string.length(); ++i) {
                    this.write(string.charAt(i));
                }
            } else {
                CharBuffer charBuffer = CharBuffer.wrap(string);
                this.writeEncoded(charBuffer);
            }
        }
        this.write(CRLF);
    }

    @Override
    public void writeLine(CharArrayBuffer charArrayBuffer) throws IOException {
        if (charArrayBuffer == null) {
            return;
        }
        if (this.ascii) {
            int n;
            int n2 = 0;
            for (int i = charArrayBuffer.length(); i > 0; i -= n) {
                n = this.buffer.capacity() - this.buffer.length();
                if ((n = Math.min(n, i)) > 0) {
                    this.buffer.append(charArrayBuffer, n2, n);
                }
                if (this.buffer.isFull()) {
                    this.flushBuffer();
                }
                n2 += n;
            }
        } else {
            CharBuffer charBuffer = CharBuffer.wrap(charArrayBuffer.buffer(), 0, charArrayBuffer.length());
            this.writeEncoded(charBuffer);
        }
        this.write(CRLF);
    }

    private void writeEncoded(CharBuffer charBuffer) throws IOException {
        CoderResult coderResult;
        if (!charBuffer.hasRemaining()) {
            return;
        }
        if (this.encoder == null) {
            this.encoder = this.charset.newEncoder();
            this.encoder.onMalformedInput(this.onMalformedCharAction);
            this.encoder.onUnmappableCharacter(this.onUnmappableCharAction);
        }
        if (this.bbuf == null) {
            this.bbuf = ByteBuffer.allocate(1024);
        }
        this.encoder.reset();
        while (charBuffer.hasRemaining()) {
            coderResult = this.encoder.encode(charBuffer, this.bbuf, false);
            this.handleEncodingResult(coderResult);
        }
        coderResult = this.encoder.flush(this.bbuf);
        this.handleEncodingResult(coderResult);
        this.bbuf.clear();
    }

    private void handleEncodingResult(CoderResult coderResult) throws IOException {
        if (coderResult.isError()) {
            coderResult.throwException();
        }
        this.bbuf.flip();
        while (this.bbuf.hasRemaining()) {
            this.write(this.bbuf.get());
        }
        this.bbuf.compact();
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.metrics;
    }
}

