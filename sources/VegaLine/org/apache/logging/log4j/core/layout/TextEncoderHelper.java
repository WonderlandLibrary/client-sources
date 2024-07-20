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
    static void encodeTextFallBack(Charset charset, StringBuilder text, ByteBufferDestination destination) {
        byte[] bytes = text.toString().getBytes(charset);
        ByteBufferDestination byteBufferDestination = destination;
        synchronized (byteBufferDestination) {
            ByteBuffer buffer = destination.getByteBuffer();
            int offset = 0;
            do {
                int length = Math.min(bytes.length - offset, buffer.remaining());
                buffer.put(bytes, offset, length);
                if ((offset += length) >= bytes.length) continue;
                buffer = destination.drain(buffer);
            } while (offset < bytes.length);
        }
    }

    static void encodeTextWithCopy(CharsetEncoder charsetEncoder, CharBuffer charBuf, ByteBuffer temp, StringBuilder text, ByteBufferDestination destination) {
        TextEncoderHelper.encodeText(charsetEncoder, charBuf, temp, text, destination);
        TextEncoderHelper.copyDataToDestination(temp, destination);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void copyDataToDestination(ByteBuffer temp, ByteBufferDestination destination) {
        ByteBufferDestination byteBufferDestination = destination;
        synchronized (byteBufferDestination) {
            ByteBuffer destinationBuffer = destination.getByteBuffer();
            if (destinationBuffer != temp) {
                temp.flip();
                if (temp.remaining() > destinationBuffer.remaining()) {
                    destinationBuffer = destination.drain(destinationBuffer);
                }
                destinationBuffer.put(temp);
                temp.clear();
            }
        }
    }

    static void encodeText(CharsetEncoder charsetEncoder, CharBuffer charBuf, ByteBuffer byteBuf, StringBuilder text, ByteBufferDestination destination) {
        charsetEncoder.reset();
        ByteBuffer temp = byteBuf;
        int start = 0;
        int todoChars = text.length();
        boolean endOfInput = true;
        do {
            charBuf.clear();
            int copied = TextEncoderHelper.copy(text, start, charBuf);
            start += copied;
            endOfInput = (todoChars -= copied) <= 0;
            charBuf.flip();
            temp = TextEncoderHelper.encode(charsetEncoder, charBuf, endOfInput, destination, temp);
        } while (!endOfInput);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public static void encodeText(CharsetEncoder charsetEncoder, CharBuffer charBuf, ByteBufferDestination destination) {
        ByteBufferDestination byteBufferDestination = destination;
        synchronized (byteBufferDestination) {
            charsetEncoder.reset();
            ByteBuffer byteBuf = destination.getByteBuffer();
            TextEncoderHelper.encode(charsetEncoder, charBuf, true, destination, byteBuf);
        }
    }

    private static ByteBuffer encode(CharsetEncoder charsetEncoder, CharBuffer charBuf, boolean endOfInput, ByteBufferDestination destination, ByteBuffer byteBuf) {
        try {
            byteBuf = TextEncoderHelper.encodeAsMuchAsPossible(charsetEncoder, charBuf, endOfInput, destination, byteBuf);
            if (endOfInput) {
                byteBuf = TextEncoderHelper.flushRemainingBytes(charsetEncoder, destination, byteBuf);
            }
        } catch (CharacterCodingException ex) {
            throw new IllegalStateException(ex);
        }
        return byteBuf;
    }

    private static ByteBuffer encodeAsMuchAsPossible(CharsetEncoder charsetEncoder, CharBuffer charBuf, boolean endOfInput, ByteBufferDestination destination, ByteBuffer temp) throws CharacterCodingException {
        CoderResult result;
        do {
            result = charsetEncoder.encode(charBuf, temp, endOfInput);
            temp = TextEncoderHelper.drainIfByteBufferFull(destination, temp, result);
        } while (result.isOverflow());
        if (!result.isUnderflow()) {
            result.throwException();
        }
        return temp;
    }

    private static ByteBuffer drainIfByteBufferFull(ByteBufferDestination destination, ByteBuffer temp, CoderResult result) {
        if (result.isOverflow()) {
            ByteBuffer destinationBuffer = destination.getByteBuffer();
            if (destinationBuffer != temp) {
                temp.flip();
                destinationBuffer.put(temp);
                temp.clear();
            }
            temp = destinationBuffer = destination.drain(destinationBuffer);
        }
        return temp;
    }

    private static ByteBuffer flushRemainingBytes(CharsetEncoder charsetEncoder, ByteBufferDestination destination, ByteBuffer temp) throws CharacterCodingException {
        CoderResult result;
        do {
            result = charsetEncoder.flush(temp);
            temp = TextEncoderHelper.drainIfByteBufferFull(destination, temp, result);
        } while (result.isOverflow());
        if (!result.isUnderflow()) {
            result.throwException();
        }
        return temp;
    }

    static int copy(StringBuilder source, int offset, CharBuffer destination) {
        int length = Math.min(source.length() - offset, destination.remaining());
        char[] array = destination.array();
        int start = destination.position();
        source.getChars(offset, offset + length, array, start);
        destination.position(start + length);
        return length;
    }
}

