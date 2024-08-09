/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class ReaderInputStream
extends InputStream {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final Reader reader;
    private final CharsetEncoder encoder;
    private final CharBuffer encoderIn;
    private final ByteBuffer encoderOut;
    private CoderResult lastCoderResult;
    private boolean endOfInput;

    public ReaderInputStream(Reader reader, CharsetEncoder charsetEncoder) {
        this(reader, charsetEncoder, 1024);
    }

    public ReaderInputStream(Reader reader, CharsetEncoder charsetEncoder, int n) {
        this.reader = reader;
        this.encoder = charsetEncoder;
        this.encoderIn = CharBuffer.allocate(n);
        this.encoderIn.flip();
        this.encoderOut = ByteBuffer.allocate(128);
        this.encoderOut.flip();
    }

    public ReaderInputStream(Reader reader, Charset charset, int n) {
        this(reader, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), n);
    }

    public ReaderInputStream(Reader reader, Charset charset) {
        this(reader, charset, 1024);
    }

    public ReaderInputStream(Reader reader, String string, int n) {
        this(reader, Charset.forName(string), n);
    }

    public ReaderInputStream(Reader reader, String string) {
        this(reader, string, 1024);
    }

    @Deprecated
    public ReaderInputStream(Reader reader) {
        this(reader, Charset.defaultCharset());
    }

    private void fillBuffer() throws IOException {
        if (!this.endOfInput && (this.lastCoderResult == null || this.lastCoderResult.isUnderflow())) {
            this.encoderIn.compact();
            int n = this.encoderIn.position();
            int n2 = this.reader.read(this.encoderIn.array(), n, this.encoderIn.remaining());
            if (n2 == -1) {
                this.endOfInput = true;
            } else {
                this.encoderIn.position(n + n2);
            }
            this.encoderIn.flip();
        }
        this.encoderOut.compact();
        this.lastCoderResult = this.encoder.encode(this.encoderIn, this.encoderOut, this.endOfInput);
        this.encoderOut.flip();
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (byArray == null) {
            throw new NullPointerException("Byte array must not be null");
        }
        if (n2 < 0 || n < 0 || n + n2 > byArray.length) {
            throw new IndexOutOfBoundsException("Array Size=" + byArray.length + ", offset=" + n + ", length=" + n2);
        }
        int n3 = 0;
        if (n2 == 0) {
            return 1;
        }
        while (n2 > 0) {
            if (this.encoderOut.hasRemaining()) {
                int n4 = Math.min(this.encoderOut.remaining(), n2);
                this.encoderOut.get(byArray, n, n4);
                n += n4;
                n2 -= n4;
                n3 += n4;
                continue;
            }
            this.fillBuffer();
            if (!this.endOfInput || this.encoderOut.hasRemaining()) continue;
        }
        return n3 == 0 && this.endOfInput ? -1 : n3;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public int read() throws IOException {
        do {
            if (this.encoderOut.hasRemaining()) {
                return this.encoderOut.get() & 0xFF;
            }
            this.fillBuffer();
        } while (!this.endOfInput || this.encoderOut.hasRemaining());
        return 1;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}

