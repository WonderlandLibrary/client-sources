/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Objects;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;
import org.apache.logging.log4j.core.layout.Encoder;
import org.apache.logging.log4j.core.layout.TextEncoderHelper;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.status.StatusLogger;

public class StringBuilderEncoder
implements Encoder<StringBuilder> {
    private static final int DEFAULT_BYTE_BUFFER_SIZE = 8192;
    private final ThreadLocal<CharBuffer> charBufferThreadLocal = new ThreadLocal();
    private final ThreadLocal<ByteBuffer> byteBufferThreadLocal = new ThreadLocal();
    private final ThreadLocal<CharsetEncoder> charsetEncoderThreadLocal = new ThreadLocal();
    private final Charset charset;
    private final int charBufferSize;
    private final int byteBufferSize;

    public StringBuilderEncoder(Charset charset) {
        this(charset, Constants.ENCODER_CHAR_BUFFER_SIZE, 8192);
    }

    public StringBuilderEncoder(Charset charset, int n, int n2) {
        this.charBufferSize = n;
        this.byteBufferSize = n2;
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    @Override
    public void encode(StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        ByteBuffer byteBuffer = this.getByteBuffer();
        byteBuffer.clear();
        byteBuffer.limit(Math.min(byteBuffer.capacity(), byteBufferDestination.getByteBuffer().capacity()));
        CharsetEncoder charsetEncoder = this.getCharsetEncoder();
        int n = StringBuilderEncoder.estimateBytes(stringBuilder.length(), charsetEncoder.maxBytesPerChar());
        if (byteBuffer.remaining() < n) {
            this.encodeSynchronized(this.getCharsetEncoder(), this.getCharBuffer(), stringBuilder, byteBufferDestination);
        } else {
            this.encodeWithThreadLocals(charsetEncoder, this.getCharBuffer(), byteBuffer, stringBuilder, byteBufferDestination);
        }
    }

    private void encodeWithThreadLocals(CharsetEncoder charsetEncoder, CharBuffer charBuffer, ByteBuffer byteBuffer, StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        try {
            TextEncoderHelper.encodeTextWithCopy(charsetEncoder, charBuffer, byteBuffer, stringBuilder, byteBufferDestination);
        } catch (Exception exception) {
            this.logEncodeTextException(exception, stringBuilder, byteBufferDestination);
            TextEncoderHelper.encodeTextFallBack(this.charset, stringBuilder, byteBufferDestination);
        }
    }

    private static int estimateBytes(int n, float f) {
        return (int)((double)n * (double)f);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void encodeSynchronized(CharsetEncoder charsetEncoder, CharBuffer charBuffer, StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        ByteBufferDestination byteBufferDestination2 = byteBufferDestination;
        synchronized (byteBufferDestination2) {
            try {
                TextEncoderHelper.encodeText(charsetEncoder, charBuffer, byteBufferDestination.getByteBuffer(), stringBuilder, byteBufferDestination);
            } catch (Exception exception) {
                this.logEncodeTextException(exception, stringBuilder, byteBufferDestination);
                TextEncoderHelper.encodeTextFallBack(this.charset, stringBuilder, byteBufferDestination);
            }
        }
    }

    private CharsetEncoder getCharsetEncoder() {
        CharsetEncoder charsetEncoder = this.charsetEncoderThreadLocal.get();
        if (charsetEncoder == null) {
            charsetEncoder = this.charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            this.charsetEncoderThreadLocal.set(charsetEncoder);
        }
        return charsetEncoder;
    }

    private CharBuffer getCharBuffer() {
        CharBuffer charBuffer = this.charBufferThreadLocal.get();
        if (charBuffer == null) {
            charBuffer = CharBuffer.wrap(new char[this.charBufferSize]);
            this.charBufferThreadLocal.set(charBuffer);
        }
        return charBuffer;
    }

    private ByteBuffer getByteBuffer() {
        ByteBuffer byteBuffer = this.byteBufferThreadLocal.get();
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.wrap(new byte[this.byteBufferSize]);
            this.byteBufferThreadLocal.set(byteBuffer);
        }
        return byteBuffer;
    }

    private void logEncodeTextException(Exception exception, StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        StatusLogger.getLogger().error("Recovering from StringBuilderEncoder.encode('{}') error: {}", (Object)stringBuilder, (Object)exception, (Object)exception);
    }

    @Override
    public void encode(Object object, ByteBufferDestination byteBufferDestination) {
        this.encode((StringBuilder)object, byteBufferDestination);
    }
}

