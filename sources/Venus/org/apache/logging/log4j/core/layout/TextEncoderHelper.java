/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;

public class TextEncoderHelper {
    private TextEncoderHelper() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void encodeTextFallBack(Charset charset, StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        byte[] byArray = stringBuilder.toString().getBytes(charset);
        ByteBufferDestination byteBufferDestination2 = byteBufferDestination;
        synchronized (byteBufferDestination2) {
            ByteBuffer byteBuffer = byteBufferDestination.getByteBuffer();
            int n = 0;
            do {
                int n2 = Math.min(byArray.length - n, byteBuffer.remaining());
                byteBuffer.put(byArray, n, n2);
                if ((n += n2) >= byArray.length) continue;
                byteBuffer = byteBufferDestination.drain(byteBuffer);
            } while (n < byArray.length);
        }
    }

    static void encodeTextWithCopy(CharsetEncoder charsetEncoder, CharBuffer charBuffer, ByteBuffer byteBuffer, StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        TextEncoderHelper.encodeText(charsetEncoder, charBuffer, byteBuffer, stringBuilder, byteBufferDestination);
        TextEncoderHelper.copyDataToDestination(byteBuffer, byteBufferDestination);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void copyDataToDestination(ByteBuffer byteBuffer, ByteBufferDestination byteBufferDestination) {
        ByteBufferDestination byteBufferDestination2 = byteBufferDestination;
        synchronized (byteBufferDestination2) {
            ByteBuffer byteBuffer2 = byteBufferDestination.getByteBuffer();
            if (byteBuffer2 != byteBuffer) {
                byteBuffer.flip();
                if (byteBuffer.remaining() > byteBuffer2.remaining()) {
                    byteBuffer2 = byteBufferDestination.drain(byteBuffer2);
                }
                byteBuffer2.put(byteBuffer);
                byteBuffer.clear();
            }
        }
    }

    static void encodeText(CharsetEncoder charsetEncoder, CharBuffer charBuffer, ByteBuffer byteBuffer, StringBuilder stringBuilder, ByteBufferDestination byteBufferDestination) {
        charsetEncoder.reset();
        ByteBuffer byteBuffer2 = byteBuffer;
        int n = 0;
        int n2 = stringBuilder.length();
        boolean bl = true;
        do {
            charBuffer.clear();
            int n3 = TextEncoderHelper.copy(stringBuilder, n, charBuffer);
            n += n3;
            bl = (n2 -= n3) <= 0;
            charBuffer.flip();
            byteBuffer2 = TextEncoderHelper.encode(charsetEncoder, charBuffer, bl, byteBufferDestination, byteBuffer2);
        } while (!bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public static void encodeText(CharsetEncoder charsetEncoder, CharBuffer charBuffer, ByteBufferDestination byteBufferDestination) {
        ByteBufferDestination byteBufferDestination2 = byteBufferDestination;
        synchronized (byteBufferDestination2) {
            charsetEncoder.reset();
            ByteBuffer byteBuffer = byteBufferDestination.getByteBuffer();
            TextEncoderHelper.encode(charsetEncoder, charBuffer, true, byteBufferDestination, byteBuffer);
        }
    }

    private static ByteBuffer encode(CharsetEncoder charsetEncoder, CharBuffer charBuffer, boolean bl, ByteBufferDestination byteBufferDestination, ByteBuffer byteBuffer) {
        try {
            byteBuffer = TextEncoderHelper.encodeAsMuchAsPossible(charsetEncoder, charBuffer, bl, byteBufferDestination, byteBuffer);
            if (bl) {
                byteBuffer = TextEncoderHelper.flushRemainingBytes(charsetEncoder, byteBufferDestination, byteBuffer);
            }
        } catch (CharacterCodingException characterCodingException) {
            throw new IllegalStateException(characterCodingException);
        }
        return byteBuffer;
    }

    private static ByteBuffer encodeAsMuchAsPossible(CharsetEncoder charsetEncoder, CharBuffer charBuffer, boolean bl, ByteBufferDestination byteBufferDestination, ByteBuffer byteBuffer) throws CharacterCodingException {
        CoderResult coderResult;
        do {
            coderResult = charsetEncoder.encode(charBuffer, byteBuffer, bl);
            byteBuffer = TextEncoderHelper.drainIfByteBufferFull(byteBufferDestination, byteBuffer, coderResult);
        } while (coderResult.isOverflow());
        if (!coderResult.isUnderflow()) {
            coderResult.throwException();
        }
        return byteBuffer;
    }

    private static ByteBuffer drainIfByteBufferFull(ByteBufferDestination byteBufferDestination, ByteBuffer byteBuffer, CoderResult coderResult) {
        if (coderResult.isOverflow()) {
            ByteBuffer byteBuffer2 = byteBufferDestination.getByteBuffer();
            if (byteBuffer2 != byteBuffer) {
                byteBuffer.flip();
                byteBuffer2.put(byteBuffer);
                byteBuffer.clear();
            }
            byteBuffer = byteBuffer2 = byteBufferDestination.drain(byteBuffer2);
        }
        return byteBuffer;
    }

    private static ByteBuffer flushRemainingBytes(CharsetEncoder charsetEncoder, ByteBufferDestination byteBufferDestination, ByteBuffer byteBuffer) throws CharacterCodingException {
        CoderResult coderResult;
        do {
            coderResult = charsetEncoder.flush(byteBuffer);
            byteBuffer = TextEncoderHelper.drainIfByteBufferFull(byteBufferDestination, byteBuffer, coderResult);
        } while (coderResult.isOverflow());
        if (!coderResult.isUnderflow()) {
            coderResult.throwException();
        }
        return byteBuffer;
    }

    static int copy(StringBuilder stringBuilder, int n, CharBuffer charBuffer) {
        int n2 = Math.min(stringBuilder.length() - n, charBuffer.remaining());
        char[] cArray = charBuffer.array();
        int n3 = charBuffer.position();
        stringBuilder.getChars(n, n + n2, cArray, n3);
        charBuffer.position(n3 + n2);
        return n2;
    }
}

