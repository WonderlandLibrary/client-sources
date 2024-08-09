/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public class WriterOutputStream
extends OutputStream {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private final Writer writer;
    private final CharsetDecoder decoder;
    private final boolean writeImmediately;
    private final ByteBuffer decoderIn = ByteBuffer.allocate(128);
    private final CharBuffer decoderOut;

    public WriterOutputStream(Writer writer, CharsetDecoder charsetDecoder) {
        this(writer, charsetDecoder, 1024, false);
    }

    public WriterOutputStream(Writer writer, CharsetDecoder charsetDecoder, int n, boolean bl) {
        WriterOutputStream.checkIbmJdkWithBrokenUTF16(charsetDecoder.charset());
        this.writer = writer;
        this.decoder = charsetDecoder;
        this.writeImmediately = bl;
        this.decoderOut = CharBuffer.allocate(n);
    }

    public WriterOutputStream(Writer writer, Charset charset, int n, boolean bl) {
        this(writer, charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE).replaceWith("?"), n, bl);
    }

    public WriterOutputStream(Writer writer, Charset charset) {
        this(writer, charset, 1024, false);
    }

    public WriterOutputStream(Writer writer, String string, int n, boolean bl) {
        this(writer, Charset.forName(string), n, bl);
    }

    public WriterOutputStream(Writer writer, String string) {
        this(writer, string, 1024, false);
    }

    @Deprecated
    public WriterOutputStream(Writer writer) {
        this(writer, Charset.defaultCharset(), 1024, false);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        while (n2 > 0) {
            int n3 = Math.min(n2, this.decoderIn.remaining());
            this.decoderIn.put(byArray, n, n3);
            this.processInput(false);
            n2 -= n3;
            n += n3;
        }
        if (this.writeImmediately) {
            this.flushOutput();
        }
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        this.write(byArray, 0, byArray.length);
    }

    @Override
    public void write(int n) throws IOException {
        this.write(new byte[]{(byte)n}, 0, 1);
    }

    @Override
    public void flush() throws IOException {
        this.flushOutput();
        this.writer.flush();
    }

    @Override
    public void close() throws IOException {
        this.processInput(true);
        this.flushOutput();
        this.writer.close();
    }

    private void processInput(boolean bl) throws IOException {
        CoderResult coderResult;
        this.decoderIn.flip();
        while ((coderResult = this.decoder.decode(this.decoderIn, this.decoderOut, bl)).isOverflow()) {
            this.flushOutput();
        }
        if (!coderResult.isUnderflow()) {
            throw new IOException("Unexpected coder result");
        }
        this.decoderIn.compact();
    }

    private void flushOutput() throws IOException {
        if (this.decoderOut.position() > 0) {
            this.writer.write(this.decoderOut.array(), 0, this.decoderOut.position());
            this.decoderOut.rewind();
        }
    }

    private static void checkIbmJdkWithBrokenUTF16(Charset charset) {
        if (!"UTF-16".equals(charset.name())) {
            return;
        }
        String string = "v\u00e9s";
        byte[] byArray = "v\u00e9s".getBytes(charset);
        CharsetDecoder charsetDecoder = charset.newDecoder();
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        CharBuffer charBuffer = CharBuffer.allocate(3);
        int n = byArray.length;
        for (int i = 0; i < n; ++i) {
            byteBuffer.put(byArray[i]);
            byteBuffer.flip();
            try {
                charsetDecoder.decode(byteBuffer, charBuffer, i == n - 1);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new UnsupportedOperationException("UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream");
            }
            byteBuffer.compact();
        }
        charBuffer.rewind();
        if (!"v\u00e9s".equals(charBuffer.toString())) {
            throw new UnsupportedOperationException("UTF-16 requested when runninng on an IBM JDK with broken UTF-16 support. Please find a JDK that supports UTF-16 if you intend to use UF-16 with WriterOutputStream");
        }
    }
}

