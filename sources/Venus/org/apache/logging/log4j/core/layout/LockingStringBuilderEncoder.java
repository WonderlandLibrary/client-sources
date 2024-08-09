/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

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

public class LockingStringBuilderEncoder
implements Encoder<StringBuilder> {
    private final Charset charset;
    private final CharsetEncoder charsetEncoder;
    private final CharBuffer cachedCharBuffer;

    public LockingStringBuilderEncoder(Charset charset) {
        this(charset, Constants.ENCODER_CHAR_BUFFER_SIZE);
    }

    public LockingStringBuilderEncoder(Charset charset, int n) {
        this.charset = Objects.requireNonNull(charset, "charset");
        this.charsetEncoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        this.cachedCharBuffer = CharBuffer.wrap(new char[n]);
    }

    private CharBuffer getCharBuffer() {
        return this.cachedCharBuffer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void encode(StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        ByteBufferDestination byteBufferDestination2 = byteBufferDestination;
        synchronized (byteBufferDestination2) {
            try {
                TextEncoderHelper.encodeText(this.charsetEncoder, this.cachedCharBuffer, byteBufferDestination.getByteBuffer(), stringBuilder, byteBufferDestination);
            } catch (Exception exception) {
                this.logEncodeTextException(exception, stringBuilder, byteBufferDestination);
                TextEncoderHelper.encodeTextFallBack(this.charset, stringBuilder, byteBufferDestination);
            }
        }
    }

    private void logEncodeTextException(Exception exception, StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        StatusLogger.getLogger().error("Recovering from LockingStringBuilderEncoder.encode('{}') error", (Object)stringBuilder, (Object)exception);
    }

    @Override
    public void encode(Object object, ByteBufferDestination byteBufferDestination) {
        this.encode((StringBuilder)object, byteBufferDestination);
    }
}

