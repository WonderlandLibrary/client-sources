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

    public StringBuilderEncoder(Charset charset, int charBufferSize, int byteBufferSize) {
        this.charBufferSize = charBufferSize;
        this.byteBufferSize = byteBufferSize;
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    @Override
    public void encode(StringBuilder source, ByteBufferDestination destination) {
        ByteBuffer temp = this.getByteBuffer();
        temp.clear();
        temp.limit(Math.min(temp.capacity(), destination.getByteBuffer().capacity()));
        CharsetEncoder charsetEncoder = this.getCharsetEncoder();
        int estimatedBytes = StringBuilderEncoder.estimateBytes(source.length(), charsetEncoder.maxBytesPerChar());
        if (temp.remaining() < estimatedBytes) {
            this.encodeSynchronized(this.getCharsetEncoder(), this.getCharBuffer(), source, destination);
        } else {
            this.encodeWithThreadLocals(charsetEncoder, this.getCharBuffer(), temp, source, destination);
        }
    }

    private void encodeWithThreadLocals(CharsetEncoder charsetEncoder, CharBuffer charBuffer, ByteBuffer temp, StringBuilder source, ByteBufferDestination destination) {
        try {
            TextEncoderHelper.encodeTextWithCopy(charsetEncoder, charBuffer, temp, source, destination);
        } catch (Exception ex) {
            this.logEncodeTextException(ex, source, destination);
            TextEncoderHelper.encodeTextFallBack(this.charset, source, destination);
        }
    }

    private static int estimateBytes(int charCount, float maxBytesPerChar) {
        return (int)((double)charCount * (double)maxBytesPerChar);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void encodeSynchronized(CharsetEncoder charsetEncoder, CharBuffer charBuffer, StringBuilder source, ByteBufferDestination destination) {
        ByteBufferDestination byteBufferDestination = destination;
        synchronized (byteBufferDestination) {
            try {
                TextEncoderHelper.encodeText(charsetEncoder, charBuffer, destination.getByteBuffer(), source, destination);
            } catch (Exception ex) {
                this.logEncodeTextException(ex, source, destination);
                TextEncoderHelper.encodeTextFallBack(this.charset, source, destination);
            }
        }
    }

    private CharsetEncoder getCharsetEncoder() {
        CharsetEncoder result = this.charsetEncoderThreadLocal.get();
        if (result == null) {
            result = this.charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            this.charsetEncoderThreadLocal.set(result);
        }
        return result;
    }

    private CharBuffer getCharBuffer() {
        CharBuffer result = this.charBufferThreadLocal.get();
        if (result == null) {
            result = CharBuffer.wrap(new char[this.charBufferSize]);
            this.charBufferThreadLocal.set(result);
        }
        return result;
    }

    private ByteBuffer getByteBuffer() {
        ByteBuffer result = this.byteBufferThreadLocal.get();
        if (result == null) {
            result = ByteBuffer.wrap(new byte[this.byteBufferSize]);
            this.byteBufferThreadLocal.set(result);
        }
        return result;
    }

    private void logEncodeTextException(Exception ex, StringBuilder text, ByteBufferDestination destination) {
        StatusLogger.getLogger().error("Recovering from StringBuilderEncoder.encode('{}') error: {}", (Object)text, (Object)ex, (Object)ex);
    }
}

