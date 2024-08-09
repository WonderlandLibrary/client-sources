/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

@GwtIncompatible
final class ReaderInputStream
extends InputStream {
    private final Reader reader;
    private final CharsetEncoder encoder;
    private final byte[] singleByte = new byte[1];
    private CharBuffer charBuffer;
    private ByteBuffer byteBuffer;
    private boolean endOfInput;
    private boolean draining;
    private boolean doneFlushing;

    ReaderInputStream(Reader reader, Charset charset, int n) {
        this(reader, charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE), n);
    }

    ReaderInputStream(Reader reader, CharsetEncoder charsetEncoder, int n) {
        this.reader = Preconditions.checkNotNull(reader);
        this.encoder = Preconditions.checkNotNull(charsetEncoder);
        Preconditions.checkArgument(n > 0, "bufferSize must be positive: %s", n);
        charsetEncoder.reset();
        this.charBuffer = CharBuffer.allocate(n);
        this.charBuffer.flip();
        this.byteBuffer = ByteBuffer.allocate(n);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public int read() throws IOException {
        return this.read(this.singleByte) == 1 ? UnsignedBytes.toInt(this.singleByte[0]) : -1;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        CoderResult coderResult;
        Preconditions.checkPositionIndexes(n, n + n2, byArray.length);
        if (n2 == 0) {
            return 1;
        }
        int n3 = 0;
        boolean bl = this.endOfInput;
        block0: while (true) {
            if (this.draining) {
                if ((n3 += this.drain(byArray, n + n3, n2 - n3)) == n2 || this.doneFlushing) {
                    return n3 > 0 ? n3 : -1;
                }
                this.draining = false;
                this.byteBuffer.clear();
            }
            while (true) {
                if ((coderResult = this.doneFlushing ? CoderResult.UNDERFLOW : (bl ? this.encoder.flush(this.byteBuffer) : this.encoder.encode(this.charBuffer, this.byteBuffer, this.endOfInput))).isOverflow()) {
                    this.startDraining(true);
                    continue block0;
                }
                if (coderResult.isUnderflow()) {
                    if (bl) {
                        this.doneFlushing = true;
                        this.startDraining(false);
                        continue block0;
                    }
                    if (this.endOfInput) {
                        bl = true;
                        continue;
                    }
                    this.readMoreChars();
                    continue;
                }
                if (coderResult.isError()) break block0;
            }
            break;
        }
        coderResult.throwException();
        return 1;
    }

    private static CharBuffer grow(CharBuffer charBuffer) {
        char[] cArray = Arrays.copyOf(charBuffer.array(), charBuffer.capacity() * 2);
        CharBuffer charBuffer2 = CharBuffer.wrap(cArray);
        charBuffer2.position(charBuffer.position());
        charBuffer2.limit(charBuffer.limit());
        return charBuffer2;
    }

    private void readMoreChars() throws IOException {
        if (ReaderInputStream.availableCapacity(this.charBuffer) == 0) {
            if (this.charBuffer.position() > 0) {
                this.charBuffer.compact().flip();
            } else {
                this.charBuffer = ReaderInputStream.grow(this.charBuffer);
            }
        }
        int n = this.charBuffer.limit();
        int n2 = this.reader.read(this.charBuffer.array(), n, ReaderInputStream.availableCapacity(this.charBuffer));
        if (n2 == -1) {
            this.endOfInput = true;
        } else {
            this.charBuffer.limit(n + n2);
        }
    }

    private static int availableCapacity(Buffer buffer) {
        return buffer.capacity() - buffer.limit();
    }

    private void startDraining(boolean bl) {
        this.byteBuffer.flip();
        if (bl && this.byteBuffer.remaining() == 0) {
            this.byteBuffer = ByteBuffer.allocate(this.byteBuffer.capacity() * 2);
        } else {
            this.draining = true;
        }
    }

    private int drain(byte[] byArray, int n, int n2) {
        int n3 = Math.min(n2, this.byteBuffer.remaining());
        this.byteBuffer.get(byArray, n, n3);
        return n3;
    }
}

