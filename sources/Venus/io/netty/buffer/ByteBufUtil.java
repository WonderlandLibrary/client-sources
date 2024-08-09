/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AbstractByteBufAllocator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import io.netty.buffer.WrappedByteBuf;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.Recycler;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Locale;

public final class ByteBufUtil {
    private static final InternalLogger logger;
    private static final FastThreadLocal<CharBuffer> CHAR_BUFFERS;
    private static final byte WRITE_UTF_UNKNOWN = 63;
    private static final int MAX_CHAR_BUFFER_SIZE;
    private static final int THREAD_LOCAL_BUFFER_SIZE;
    private static final int MAX_BYTES_PER_CHAR_UTF8;
    static final int WRITE_CHUNK_SIZE = 8192;
    static final ByteBufAllocator DEFAULT_ALLOCATOR;
    private static final ByteProcessor FIND_NON_ASCII;

    public static String hexDump(ByteBuf byteBuf) {
        return ByteBufUtil.hexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static String hexDump(ByteBuf byteBuf, int n, int n2) {
        return HexUtil.access$000(byteBuf, n, n2);
    }

    public static String hexDump(byte[] byArray) {
        return ByteBufUtil.hexDump(byArray, 0, byArray.length);
    }

    public static String hexDump(byte[] byArray, int n, int n2) {
        return HexUtil.access$100(byArray, n, n2);
    }

    public static byte decodeHexByte(CharSequence charSequence, int n) {
        return StringUtil.decodeHexByte(charSequence, n);
    }

    public static byte[] decodeHexDump(CharSequence charSequence) {
        return StringUtil.decodeHexDump(charSequence, 0, charSequence.length());
    }

    public static byte[] decodeHexDump(CharSequence charSequence, int n, int n2) {
        return StringUtil.decodeHexDump(charSequence, n, n2);
    }

    public static boolean ensureWritableSuccess(int n) {
        return n == 0 || n == 2;
    }

    public static int hashCode(ByteBuf byteBuf) {
        int n;
        int n2 = byteBuf.readableBytes();
        int n3 = n2 >>> 2;
        int n4 = n2 & 3;
        int n5 = 1;
        int n6 = byteBuf.readerIndex();
        if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
            for (n = n3; n > 0; --n) {
                n5 = 31 * n5 + byteBuf.getInt(n6);
                n6 += 4;
            }
        } else {
            for (n = n3; n > 0; --n) {
                n5 = 31 * n5 + ByteBufUtil.swapInt(byteBuf.getInt(n6));
                n6 += 4;
            }
        }
        for (n = n4; n > 0; --n) {
            n5 = 31 * n5 + byteBuf.getByte(n6++);
        }
        if (n5 == 0) {
            n5 = 1;
        }
        return n5;
    }

    public static int indexOf(ByteBuf byteBuf, ByteBuf byteBuf2) {
        int n = byteBuf2.readableBytes() - byteBuf.readableBytes() + 1;
        for (int i = 0; i < n; ++i) {
            if (!ByteBufUtil.equals(byteBuf, byteBuf.readerIndex(), byteBuf2, byteBuf2.readerIndex() + i, byteBuf.readableBytes())) continue;
            return byteBuf2.readerIndex() + i;
        }
        return 1;
    }

    public static boolean equals(ByteBuf byteBuf, int n, ByteBuf byteBuf2, int n2, int n3) {
        int n4;
        if (n < 0 || n2 < 0 || n3 < 0) {
            throw new IllegalArgumentException("All indexes and lengths must be non-negative");
        }
        if (byteBuf.writerIndex() - n3 < n || byteBuf2.writerIndex() - n3 < n2) {
            return true;
        }
        int n5 = n3 >>> 3;
        int n6 = n3 & 7;
        if (byteBuf.order() == byteBuf2.order()) {
            for (n4 = n5; n4 > 0; --n4) {
                if (byteBuf.getLong(n) != byteBuf2.getLong(n2)) {
                    return true;
                }
                n += 8;
                n2 += 8;
            }
        } else {
            for (n4 = n5; n4 > 0; --n4) {
                if (byteBuf.getLong(n) != ByteBufUtil.swapLong(byteBuf2.getLong(n2))) {
                    return true;
                }
                n += 8;
                n2 += 8;
            }
        }
        for (n4 = n6; n4 > 0; --n4) {
            if (byteBuf.getByte(n) != byteBuf2.getByte(n2)) {
                return true;
            }
            ++n;
            ++n2;
        }
        return false;
    }

    public static boolean equals(ByteBuf byteBuf, ByteBuf byteBuf2) {
        int n = byteBuf.readableBytes();
        if (n != byteBuf2.readableBytes()) {
            return true;
        }
        return ByteBufUtil.equals(byteBuf, byteBuf.readerIndex(), byteBuf2, byteBuf2.readerIndex(), n);
    }

    public static int compare(ByteBuf byteBuf, ByteBuf byteBuf2) {
        int n;
        int n2 = byteBuf.readableBytes();
        int n3 = byteBuf2.readableBytes();
        int n4 = Math.min(n2, n3);
        int n5 = n4 >>> 2;
        int n6 = n4 & 3;
        int n7 = byteBuf.readerIndex();
        int n8 = byteBuf2.readerIndex();
        if (n5 > 0) {
            long l;
            n = byteBuf.order() == ByteOrder.BIG_ENDIAN ? 1 : 0;
            int n9 = n5 << 2;
            if (byteBuf.order() == byteBuf2.order()) {
                l = n != 0 ? ByteBufUtil.compareUintBigEndian(byteBuf, byteBuf2, n7, n8, n9) : ByteBufUtil.compareUintLittleEndian(byteBuf, byteBuf2, n7, n8, n9);
            } else {
                long l2 = l = n != 0 ? ByteBufUtil.compareUintBigEndianA(byteBuf, byteBuf2, n7, n8, n9) : ByteBufUtil.compareUintBigEndianB(byteBuf, byteBuf2, n7, n8, n9);
            }
            if (l != 0L) {
                return (int)Math.min(Integer.MAX_VALUE, Math.max(Integer.MIN_VALUE, l));
            }
            n7 += n9;
            n8 += n9;
        }
        n = n7 + n6;
        while (n7 < n) {
            int n10 = byteBuf.getUnsignedByte(n7) - byteBuf2.getUnsignedByte(n8);
            if (n10 != 0) {
                return n10;
            }
            ++n7;
            ++n8;
        }
        return n2 - n3;
    }

    private static long compareUintBigEndian(ByteBuf byteBuf, ByteBuf byteBuf2, int n, int n2, int n3) {
        int n4 = n + n3;
        while (n < n4) {
            long l = byteBuf.getUnsignedInt(n) - byteBuf2.getUnsignedInt(n2);
            if (l != 0L) {
                return l;
            }
            n += 4;
            n2 += 4;
        }
        return 0L;
    }

    private static long compareUintLittleEndian(ByteBuf byteBuf, ByteBuf byteBuf2, int n, int n2, int n3) {
        int n4 = n + n3;
        while (n < n4) {
            long l = byteBuf.getUnsignedIntLE(n) - byteBuf2.getUnsignedIntLE(n2);
            if (l != 0L) {
                return l;
            }
            n += 4;
            n2 += 4;
        }
        return 0L;
    }

    private static long compareUintBigEndianA(ByteBuf byteBuf, ByteBuf byteBuf2, int n, int n2, int n3) {
        int n4 = n + n3;
        while (n < n4) {
            long l = byteBuf.getUnsignedInt(n) - byteBuf2.getUnsignedIntLE(n2);
            if (l != 0L) {
                return l;
            }
            n += 4;
            n2 += 4;
        }
        return 0L;
    }

    private static long compareUintBigEndianB(ByteBuf byteBuf, ByteBuf byteBuf2, int n, int n2, int n3) {
        int n4 = n + n3;
        while (n < n4) {
            long l = byteBuf.getUnsignedIntLE(n) - byteBuf2.getUnsignedInt(n2);
            if (l != 0L) {
                return l;
            }
            n += 4;
            n2 += 4;
        }
        return 0L;
    }

    public static int indexOf(ByteBuf byteBuf, int n, int n2, byte by) {
        if (n <= n2) {
            return ByteBufUtil.firstIndexOf(byteBuf, n, n2, by);
        }
        return ByteBufUtil.lastIndexOf(byteBuf, n, n2, by);
    }

    public static short swapShort(short s) {
        return Short.reverseBytes(s);
    }

    public static int swapMedium(int n) {
        int n2 = n << 16 & 0xFF0000 | n & 0xFF00 | n >>> 16 & 0xFF;
        if ((n2 & 0x800000) != 0) {
            n2 |= 0xFF000000;
        }
        return n2;
    }

    public static int swapInt(int n) {
        return Integer.reverseBytes(n);
    }

    public static long swapLong(long l) {
        return Long.reverseBytes(l);
    }

    public static ByteBuf writeShortBE(ByteBuf byteBuf, int n) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.writeShort(n) : byteBuf.writeShortLE(n);
    }

    public static ByteBuf setShortBE(ByteBuf byteBuf, int n, int n2) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.setShort(n, n2) : byteBuf.setShortLE(n, n2);
    }

    public static ByteBuf writeMediumBE(ByteBuf byteBuf, int n) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.writeMedium(n) : byteBuf.writeMediumLE(n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ByteBuf readBytes(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, int n) {
        boolean bl = true;
        ByteBuf byteBuf2 = byteBufAllocator.buffer(n);
        try {
            byteBuf.readBytes(byteBuf2);
            bl = false;
            ByteBuf byteBuf3 = byteBuf2;
            return byteBuf3;
        } finally {
            if (bl) {
                byteBuf2.release();
            }
        }
    }

    private static int firstIndexOf(ByteBuf byteBuf, int n, int n2, byte by) {
        if ((n = Math.max(n, 0)) >= n2 || byteBuf.capacity() == 0) {
            return 1;
        }
        return byteBuf.forEachByte(n, n2 - n, new ByteProcessor.IndexOfProcessor(by));
    }

    private static int lastIndexOf(ByteBuf byteBuf, int n, int n2, byte by) {
        if ((n = Math.min(n, byteBuf.capacity())) < 0 || byteBuf.capacity() == 0) {
            return 1;
        }
        return byteBuf.forEachByteDesc(n2, n - n2, new ByteProcessor.IndexOfProcessor(by));
    }

    public static ByteBuf writeUtf8(ByteBufAllocator byteBufAllocator, CharSequence charSequence) {
        ByteBuf byteBuf = byteBufAllocator.buffer(ByteBufUtil.utf8MaxBytes(charSequence));
        ByteBufUtil.writeUtf8(byteBuf, charSequence);
        return byteBuf;
    }

    public static int writeUtf8(ByteBuf byteBuf, CharSequence charSequence) {
        return ByteBufUtil.reserveAndWriteUtf8(byteBuf, charSequence, ByteBufUtil.utf8MaxBytes(charSequence));
    }

    public static int reserveAndWriteUtf8(ByteBuf byteBuf, CharSequence charSequence, int n) {
        while (true) {
            if (byteBuf instanceof AbstractByteBuf) {
                AbstractByteBuf abstractByteBuf = (AbstractByteBuf)byteBuf;
                abstractByteBuf.ensureWritable0(n);
                int n2 = ByteBufUtil.writeUtf8(abstractByteBuf, abstractByteBuf.writerIndex, charSequence, charSequence.length());
                abstractByteBuf.writerIndex += n2;
                return n2;
            }
            if (!(byteBuf instanceof WrappedByteBuf)) break;
            byteBuf = byteBuf.unwrap();
        }
        byte[] byArray = charSequence.toString().getBytes(CharsetUtil.UTF_8);
        byteBuf.writeBytes(byArray);
        return byArray.length;
    }

    static int writeUtf8(AbstractByteBuf abstractByteBuf, int n, CharSequence charSequence, int n2) {
        int n3 = n;
        for (int i = 0; i < n2; ++i) {
            char c = charSequence.charAt(i);
            if (c < '\u0080') {
                abstractByteBuf._setByte(n++, (byte)c);
                continue;
            }
            if (c < '\u0800') {
                abstractByteBuf._setByte(n++, (byte)(0xC0 | c >> 6));
                abstractByteBuf._setByte(n++, (byte)(0x80 | c & 0x3F));
                continue;
            }
            if (StringUtil.isSurrogate(c)) {
                char c2;
                if (!Character.isHighSurrogate(c)) {
                    abstractByteBuf._setByte(n++, 63);
                    continue;
                }
                try {
                    c2 = charSequence.charAt(++i);
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    abstractByteBuf._setByte(n++, 63);
                    break;
                }
                if (!Character.isLowSurrogate(c2)) {
                    abstractByteBuf._setByte(n++, 63);
                    abstractByteBuf._setByte(n++, Character.isHighSurrogate(c2) ? 63 : (int)c2);
                    continue;
                }
                int n4 = Character.toCodePoint(c, c2);
                abstractByteBuf._setByte(n++, (byte)(0xF0 | n4 >> 18));
                abstractByteBuf._setByte(n++, (byte)(0x80 | n4 >> 12 & 0x3F));
                abstractByteBuf._setByte(n++, (byte)(0x80 | n4 >> 6 & 0x3F));
                abstractByteBuf._setByte(n++, (byte)(0x80 | n4 & 0x3F));
                continue;
            }
            abstractByteBuf._setByte(n++, (byte)(0xE0 | c >> 12));
            abstractByteBuf._setByte(n++, (byte)(0x80 | c >> 6 & 0x3F));
            abstractByteBuf._setByte(n++, (byte)(0x80 | c & 0x3F));
        }
        return n - n3;
    }

    public static int utf8MaxBytes(int n) {
        return n * MAX_BYTES_PER_CHAR_UTF8;
    }

    public static int utf8MaxBytes(CharSequence charSequence) {
        return ByteBufUtil.utf8MaxBytes(charSequence.length());
    }

    public static int utf8Bytes(CharSequence charSequence) {
        int n;
        if (charSequence instanceof AsciiString) {
            return charSequence.length();
        }
        int n2 = charSequence.length();
        for (n = 0; n < n2 && charSequence.charAt(n) < '\u0080'; ++n) {
        }
        return n < n2 ? n + ByteBufUtil.utf8Bytes(charSequence, n, n2) : n;
    }

    private static int utf8Bytes(CharSequence charSequence, int n, int n2) {
        int n3 = 0;
        for (int i = n; i < n2; ++i) {
            char c = charSequence.charAt(i);
            if (c < '\u0800') {
                n3 += (127 - c >>> 31) + 1;
                continue;
            }
            if (StringUtil.isSurrogate(c)) {
                char c2;
                if (!Character.isHighSurrogate(c)) {
                    ++n3;
                    continue;
                }
                try {
                    c2 = charSequence.charAt(++i);
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    ++n3;
                    break;
                }
                if (!Character.isLowSurrogate(c2)) {
                    n3 += 2;
                    continue;
                }
                n3 += 4;
                continue;
            }
            n3 += 3;
        }
        return n3;
    }

    public static ByteBuf writeAscii(ByteBufAllocator byteBufAllocator, CharSequence charSequence) {
        ByteBuf byteBuf = byteBufAllocator.buffer(charSequence.length());
        ByteBufUtil.writeAscii(byteBuf, charSequence);
        return byteBuf;
    }

    public static int writeAscii(ByteBuf byteBuf, CharSequence charSequence) {
        int n = charSequence.length();
        if (!(charSequence instanceof AsciiString)) {
            while (true) {
                if (byteBuf instanceof AbstractByteBuf) {
                    AbstractByteBuf abstractByteBuf = (AbstractByteBuf)byteBuf;
                    abstractByteBuf.ensureWritable0(n);
                    int n2 = ByteBufUtil.writeAscii(abstractByteBuf, abstractByteBuf.writerIndex, charSequence, n);
                    abstractByteBuf.writerIndex += n2;
                    return n2;
                }
                if (!(byteBuf instanceof WrappedByteBuf)) break;
                byteBuf = byteBuf.unwrap();
            }
            byte[] byArray = charSequence.toString().getBytes(CharsetUtil.US_ASCII);
            byteBuf.writeBytes(byArray);
            return byArray.length;
        }
        AsciiString asciiString = (AsciiString)charSequence;
        byteBuf.writeBytes(asciiString.array(), asciiString.arrayOffset(), n);
        return n;
    }

    static int writeAscii(AbstractByteBuf abstractByteBuf, int n, CharSequence charSequence, int n2) {
        for (int i = 0; i < n2; ++i) {
            abstractByteBuf._setByte(n++, AsciiString.c2b(charSequence.charAt(i)));
        }
        return n2;
    }

    public static ByteBuf encodeString(ByteBufAllocator byteBufAllocator, CharBuffer charBuffer, Charset charset) {
        return ByteBufUtil.encodeString0(byteBufAllocator, false, charBuffer, charset, 0);
    }

    public static ByteBuf encodeString(ByteBufAllocator byteBufAllocator, CharBuffer charBuffer, Charset charset, int n) {
        return ByteBufUtil.encodeString0(byteBufAllocator, false, charBuffer, charset, n);
    }

    static ByteBuf encodeString0(ByteBufAllocator byteBufAllocator, boolean bl, CharBuffer charBuffer, Charset charset, int n) {
        CharsetEncoder charsetEncoder = CharsetUtil.encoder(charset);
        int n2 = (int)((double)charBuffer.remaining() * (double)charsetEncoder.maxBytesPerChar()) + n;
        boolean bl2 = true;
        ByteBuf byteBuf = bl ? byteBufAllocator.heapBuffer(n2) : byteBufAllocator.buffer(n2);
        try {
            ByteBuffer byteBuffer = byteBuf.internalNioBuffer(byteBuf.readerIndex(), n2);
            int n3 = byteBuffer.position();
            CoderResult coderResult = charsetEncoder.encode(charBuffer, byteBuffer, false);
            if (!coderResult.isUnderflow()) {
                coderResult.throwException();
            }
            if (!(coderResult = charsetEncoder.flush(byteBuffer)).isUnderflow()) {
                coderResult.throwException();
            }
            byteBuf.writerIndex(byteBuf.writerIndex() + byteBuffer.position() - n3);
            bl2 = false;
            ByteBuf byteBuf2 = byteBuf;
            return byteBuf2;
        } catch (CharacterCodingException characterCodingException) {
            throw new IllegalStateException(characterCodingException);
        } finally {
            if (bl2) {
                byteBuf.release();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static String decodeString(ByteBuf byteBuf, int n, int n2, Charset charset) {
        if (n2 == 0) {
            return "";
        }
        CharsetDecoder charsetDecoder = CharsetUtil.decoder(charset);
        int n3 = (int)((double)n2 * (double)charsetDecoder.maxCharsPerByte());
        CharBuffer charBuffer = CHAR_BUFFERS.get();
        if (charBuffer.length() < n3) {
            charBuffer = CharBuffer.allocate(n3);
            if (n3 <= MAX_CHAR_BUFFER_SIZE) {
                CHAR_BUFFERS.set(charBuffer);
            }
        } else {
            charBuffer.clear();
        }
        if (byteBuf.nioBufferCount() == 1) {
            ByteBufUtil.decodeString(charsetDecoder, byteBuf.nioBuffer(n, n2), charBuffer);
        } else {
            ByteBuf byteBuf2 = byteBuf.alloc().heapBuffer(n2);
            try {
                byteBuf2.writeBytes(byteBuf, n, n2);
                ByteBufUtil.decodeString(charsetDecoder, byteBuf2.internalNioBuffer(byteBuf2.readerIndex(), n2), charBuffer);
            } finally {
                byteBuf2.release();
            }
        }
        return charBuffer.flip().toString();
    }

    private static void decodeString(CharsetDecoder charsetDecoder, ByteBuffer byteBuffer, CharBuffer charBuffer) {
        try {
            CoderResult coderResult = charsetDecoder.decode(byteBuffer, charBuffer, false);
            if (!coderResult.isUnderflow()) {
                coderResult.throwException();
            }
            if (!(coderResult = charsetDecoder.flush(charBuffer)).isUnderflow()) {
                coderResult.throwException();
            }
        } catch (CharacterCodingException characterCodingException) {
            throw new IllegalStateException(characterCodingException);
        }
    }

    public static ByteBuf threadLocalDirectBuffer() {
        if (THREAD_LOCAL_BUFFER_SIZE <= 0) {
            return null;
        }
        if (PlatformDependent.hasUnsafe()) {
            return ThreadLocalUnsafeDirectByteBuf.newInstance();
        }
        return ThreadLocalDirectByteBuf.newInstance();
    }

    public static byte[] getBytes(ByteBuf byteBuf) {
        return ByteBufUtil.getBytes(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static byte[] getBytes(ByteBuf byteBuf, int n, int n2) {
        return ByteBufUtil.getBytes(byteBuf, n, n2, true);
    }

    public static byte[] getBytes(ByteBuf byteBuf, int n, int n2, boolean bl) {
        if (MathUtil.isOutOfBounds(n, n2, byteBuf.capacity())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + n + ") <= start + length(" + n2 + ") <= buf.capacity(" + byteBuf.capacity() + ')');
        }
        if (byteBuf.hasArray()) {
            if (bl || n != 0 || n2 != byteBuf.capacity()) {
                int n3 = byteBuf.arrayOffset() + n;
                return Arrays.copyOfRange(byteBuf.array(), n3, n3 + n2);
            }
            return byteBuf.array();
        }
        byte[] byArray = new byte[n2];
        byteBuf.getBytes(n, byArray);
        return byArray;
    }

    public static void copy(AsciiString asciiString, ByteBuf byteBuf) {
        ByteBufUtil.copy(asciiString, 0, byteBuf, asciiString.length());
    }

    public static void copy(AsciiString asciiString, int n, ByteBuf byteBuf, int n2, int n3) {
        if (MathUtil.isOutOfBounds(n, n3, asciiString.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + n + ") <= srcIdx + length(" + n3 + ") <= srcLen(" + asciiString.length() + ')');
        }
        ObjectUtil.checkNotNull(byteBuf, "dst").setBytes(n2, asciiString.array(), n + asciiString.arrayOffset(), n3);
    }

    public static void copy(AsciiString asciiString, int n, ByteBuf byteBuf, int n2) {
        if (MathUtil.isOutOfBounds(n, n2, asciiString.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + n + ") <= srcIdx + length(" + n2 + ") <= srcLen(" + asciiString.length() + ')');
        }
        ObjectUtil.checkNotNull(byteBuf, "dst").writeBytes(asciiString.array(), n + asciiString.arrayOffset(), n2);
    }

    public static String prettyHexDump(ByteBuf byteBuf) {
        return ByteBufUtil.prettyHexDump(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static String prettyHexDump(ByteBuf byteBuf, int n, int n2) {
        return HexUtil.access$200(byteBuf, n, n2);
    }

    public static void appendPrettyHexDump(StringBuilder stringBuilder, ByteBuf byteBuf) {
        ByteBufUtil.appendPrettyHexDump(stringBuilder, byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
    }

    public static void appendPrettyHexDump(StringBuilder stringBuilder, ByteBuf byteBuf, int n, int n2) {
        HexUtil.access$300(stringBuilder, byteBuf, n, n2);
    }

    public static boolean isText(ByteBuf byteBuf, Charset charset) {
        return ByteBufUtil.isText(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), charset);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isText(ByteBuf byteBuf, int n, int n2, Charset charset) {
        ObjectUtil.checkNotNull(byteBuf, "buf");
        ObjectUtil.checkNotNull(charset, "charset");
        int n3 = byteBuf.readerIndex() + byteBuf.readableBytes();
        if (n < 0 || n2 < 0 || n > n3 - n2) {
            throw new IndexOutOfBoundsException("index: " + n + " length: " + n2);
        }
        if (charset.equals(CharsetUtil.UTF_8)) {
            return ByteBufUtil.isUtf8(byteBuf, n, n2);
        }
        if (charset.equals(CharsetUtil.US_ASCII)) {
            return ByteBufUtil.isAscii(byteBuf, n, n2);
        }
        CharsetDecoder charsetDecoder = CharsetUtil.decoder(charset, CodingErrorAction.REPORT, CodingErrorAction.REPORT);
        try {
            if (byteBuf.nioBufferCount() == 1) {
                charsetDecoder.decode(byteBuf.nioBuffer(n, n2));
            } else {
                ByteBuf byteBuf2 = byteBuf.alloc().heapBuffer(n2);
                try {
                    byteBuf2.writeBytes(byteBuf, n, n2);
                    charsetDecoder.decode(byteBuf2.internalNioBuffer(byteBuf2.readerIndex(), n2));
                } finally {
                    byteBuf2.release();
                }
            }
            return true;
        } catch (CharacterCodingException characterCodingException) {
            return true;
        }
    }

    private static boolean isAscii(ByteBuf byteBuf, int n, int n2) {
        return byteBuf.forEachByte(n, n2, FIND_NON_ASCII) == -1;
    }

    private static boolean isUtf8(ByteBuf byteBuf, int n, int n2) {
        int n3 = n + n2;
        while (n < n3) {
            byte by;
            byte by2;
            byte by3;
            if (((by3 = byteBuf.getByte(n++)) & 0x80) == 0) continue;
            if ((by3 & 0xE0) == 192) {
                if (n >= n3) {
                    return true;
                }
                if (((by2 = byteBuf.getByte(n++)) & 0xC0) != 128) {
                    return true;
                }
                if ((by3 & 0xFF) >= 194) continue;
                return true;
            }
            if ((by3 & 0xF0) == 224) {
                if (n > n3 - 2) {
                    return true;
                }
                by2 = byteBuf.getByte(n++);
                by = byteBuf.getByte(n++);
                if ((by2 & 0xC0) != 128 || (by & 0xC0) != 128) {
                    return true;
                }
                if ((by3 & 0xF) == 0 && (by2 & 0xFF) < 160) {
                    return true;
                }
                if ((by3 & 0xF) != 13 || (by2 & 0xFF) <= 159) continue;
                return true;
            }
            if ((by3 & 0xF8) == 240) {
                if (n > n3 - 3) {
                    return true;
                }
                by2 = byteBuf.getByte(n++);
                by = byteBuf.getByte(n++);
                byte by4 = byteBuf.getByte(n++);
                if ((by2 & 0xC0) != 128 || (by & 0xC0) != 128 || (by4 & 0xC0) != 128) {
                    return true;
                }
                if ((by3 & 0xFF) <= 244 && ((by3 & 0xFF) != 240 || (by2 & 0xFF) >= 144) && ((by3 & 0xFF) != 244 || (by2 & 0xFF) <= 143)) continue;
                return true;
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void readBytes(ByteBufAllocator byteBufAllocator, ByteBuffer byteBuffer, int n, int n2, OutputStream outputStream) throws IOException {
        if (byteBuffer.hasArray()) {
            outputStream.write(byteBuffer.array(), n + byteBuffer.arrayOffset(), n2);
        } else {
            int n3 = Math.min(n2, 8192);
            byteBuffer.clear().position(n);
            if (byteBufAllocator.isDirectBufferPooled()) {
                ByteBuf byteBuf = byteBufAllocator.heapBuffer(n3);
                try {
                    byte[] byArray = byteBuf.array();
                    int n4 = byteBuf.arrayOffset();
                    ByteBufUtil.getBytes(byteBuffer, byArray, n4, n3, outputStream, n2);
                } finally {
                    byteBuf.release();
                }
            } else {
                ByteBufUtil.getBytes(byteBuffer, new byte[n3], 0, n3, outputStream, n2);
            }
        }
    }

    private static void getBytes(ByteBuffer byteBuffer, byte[] byArray, int n, int n2, OutputStream outputStream, int n3) throws IOException {
        int n4;
        do {
            n4 = Math.min(n2, n3);
            byteBuffer.get(byArray, n, n4);
            outputStream.write(byArray, n, n4);
        } while ((n3 -= n4) > 0);
    }

    private ByteBufUtil() {
    }

    static int access$500() {
        return THREAD_LOCAL_BUFFER_SIZE;
    }

    static {
        AbstractByteBufAllocator abstractByteBufAllocator;
        logger = InternalLoggerFactory.getInstance(ByteBufUtil.class);
        CHAR_BUFFERS = new FastThreadLocal<CharBuffer>(){

            @Override
            protected CharBuffer initialValue() throws Exception {
                return CharBuffer.allocate(1024);
            }

            @Override
            protected Object initialValue() throws Exception {
                return this.initialValue();
            }
        };
        MAX_BYTES_PER_CHAR_UTF8 = (int)CharsetUtil.encoder(CharsetUtil.UTF_8).maxBytesPerChar();
        String string = SystemPropertyUtil.get("io.netty.allocator.type", PlatformDependent.isAndroid() ? "unpooled" : "pooled");
        if ("unpooled".equals(string = string.toLowerCase(Locale.US).trim())) {
            abstractByteBufAllocator = UnpooledByteBufAllocator.DEFAULT;
            logger.debug("-Dio.netty.allocator.type: {}", (Object)string);
        } else if ("pooled".equals(string)) {
            abstractByteBufAllocator = PooledByteBufAllocator.DEFAULT;
            logger.debug("-Dio.netty.allocator.type: {}", (Object)string);
        } else {
            abstractByteBufAllocator = PooledByteBufAllocator.DEFAULT;
            logger.debug("-Dio.netty.allocator.type: pooled (unknown: {})", (Object)string);
        }
        DEFAULT_ALLOCATOR = abstractByteBufAllocator;
        THREAD_LOCAL_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.threadLocalDirectBufferSize", 0);
        logger.debug("-Dio.netty.threadLocalDirectBufferSize: {}", (Object)THREAD_LOCAL_BUFFER_SIZE);
        MAX_CHAR_BUFFER_SIZE = SystemPropertyUtil.getInt("io.netty.maxThreadLocalCharBufferSize", 16384);
        logger.debug("-Dio.netty.maxThreadLocalCharBufferSize: {}", (Object)MAX_CHAR_BUFFER_SIZE);
        FIND_NON_ASCII = new ByteProcessor(){

            @Override
            public boolean process(byte by) {
                return by >= 0;
            }
        };
    }

    static final class ThreadLocalDirectByteBuf
    extends UnpooledDirectByteBuf {
        private static final Recycler<ThreadLocalDirectByteBuf> RECYCLER = new Recycler<ThreadLocalDirectByteBuf>(){

            @Override
            protected ThreadLocalDirectByteBuf newObject(Recycler.Handle<ThreadLocalDirectByteBuf> handle) {
                return new ThreadLocalDirectByteBuf(handle, null);
            }

            @Override
            protected Object newObject(Recycler.Handle handle) {
                return this.newObject(handle);
            }
        };
        private final Recycler.Handle<ThreadLocalDirectByteBuf> handle;

        static ThreadLocalDirectByteBuf newInstance() {
            ThreadLocalDirectByteBuf threadLocalDirectByteBuf = RECYCLER.get();
            threadLocalDirectByteBuf.setRefCnt(1);
            return threadLocalDirectByteBuf;
        }

        private ThreadLocalDirectByteBuf(Recycler.Handle<ThreadLocalDirectByteBuf> handle) {
            super((ByteBufAllocator)UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle;
        }

        @Override
        protected void deallocate() {
            if (this.capacity() > ByteBufUtil.access$500()) {
                super.deallocate();
            } else {
                this.clear();
                this.handle.recycle(this);
            }
        }

        ThreadLocalDirectByteBuf(Recycler.Handle handle, 1 var2_2) {
            this(handle);
        }
    }

    static final class ThreadLocalUnsafeDirectByteBuf
    extends UnpooledUnsafeDirectByteBuf {
        private static final Recycler<ThreadLocalUnsafeDirectByteBuf> RECYCLER = new Recycler<ThreadLocalUnsafeDirectByteBuf>(){

            @Override
            protected ThreadLocalUnsafeDirectByteBuf newObject(Recycler.Handle<ThreadLocalUnsafeDirectByteBuf> handle) {
                return new ThreadLocalUnsafeDirectByteBuf(handle, null);
            }

            @Override
            protected Object newObject(Recycler.Handle handle) {
                return this.newObject(handle);
            }
        };
        private final Recycler.Handle<ThreadLocalUnsafeDirectByteBuf> handle;

        static ThreadLocalUnsafeDirectByteBuf newInstance() {
            ThreadLocalUnsafeDirectByteBuf threadLocalUnsafeDirectByteBuf = RECYCLER.get();
            threadLocalUnsafeDirectByteBuf.setRefCnt(1);
            return threadLocalUnsafeDirectByteBuf;
        }

        private ThreadLocalUnsafeDirectByteBuf(Recycler.Handle<ThreadLocalUnsafeDirectByteBuf> handle) {
            super((ByteBufAllocator)UnpooledByteBufAllocator.DEFAULT, 256, Integer.MAX_VALUE);
            this.handle = handle;
        }

        @Override
        protected void deallocate() {
            if (this.capacity() > ByteBufUtil.access$500()) {
                super.deallocate();
            } else {
                this.clear();
                this.handle.recycle(this);
            }
        }

        ThreadLocalUnsafeDirectByteBuf(Recycler.Handle handle, 1 var2_2) {
            this(handle);
        }
    }

    private static final class HexUtil {
        private static final char[] BYTE2CHAR;
        private static final char[] HEXDUMP_TABLE;
        private static final String[] HEXPADDING;
        private static final String[] HEXDUMP_ROWPREFIXES;
        private static final String[] BYTE2HEX;
        private static final String[] BYTEPADDING;

        private HexUtil() {
        }

        private static String hexDump(ByteBuf byteBuf, int n, int n2) {
            if (n2 < 0) {
                throw new IllegalArgumentException("length: " + n2);
            }
            if (n2 == 0) {
                return "";
            }
            int n3 = n + n2;
            char[] cArray = new char[n2 << 1];
            int n4 = n;
            int n5 = 0;
            while (n4 < n3) {
                System.arraycopy(HEXDUMP_TABLE, byteBuf.getUnsignedByte(n4) << 1, cArray, n5, 2);
                ++n4;
                n5 += 2;
            }
            return new String(cArray);
        }

        private static String hexDump(byte[] byArray, int n, int n2) {
            if (n2 < 0) {
                throw new IllegalArgumentException("length: " + n2);
            }
            if (n2 == 0) {
                return "";
            }
            int n3 = n + n2;
            char[] cArray = new char[n2 << 1];
            int n4 = n;
            int n5 = 0;
            while (n4 < n3) {
                System.arraycopy(HEXDUMP_TABLE, (byArray[n4] & 0xFF) << 1, cArray, n5, 2);
                ++n4;
                n5 += 2;
            }
            return new String(cArray);
        }

        private static String prettyHexDump(ByteBuf byteBuf, int n, int n2) {
            if (n2 == 0) {
                return "";
            }
            int n3 = n2 / 16 + (n2 % 15 == 0 ? 0 : 1) + 4;
            StringBuilder stringBuilder = new StringBuilder(n3 * 80);
            HexUtil.appendPrettyHexDump(stringBuilder, byteBuf, n, n2);
            return stringBuilder.toString();
        }

        private static void appendPrettyHexDump(StringBuilder stringBuilder, ByteBuf byteBuf, int n, int n2) {
            int n3;
            int n4;
            int n5;
            if (MathUtil.isOutOfBounds(n, n2, byteBuf.capacity())) {
                throw new IndexOutOfBoundsException("expected: 0 <= offset(" + n + ") <= offset + length(" + n2 + ") <= buf.capacity(" + byteBuf.capacity() + ')');
            }
            if (n2 == 0) {
                return;
            }
            stringBuilder.append("         +-------------------------------------------------+" + StringUtil.NEWLINE + "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |" + StringUtil.NEWLINE + "+--------+-------------------------------------------------+----------------+");
            int n6 = n;
            int n7 = n2 >>> 4;
            int n8 = n2 & 0xF;
            for (n5 = 0; n5 < n7; ++n5) {
                int n9;
                n4 = (n5 << 4) + n6;
                HexUtil.appendHexDumpRowPrefix(stringBuilder, n5, n4);
                n3 = n4 + 16;
                for (n9 = n4; n9 < n3; ++n9) {
                    stringBuilder.append(BYTE2HEX[byteBuf.getUnsignedByte(n9)]);
                }
                stringBuilder.append(" |");
                for (n9 = n4; n9 < n3; ++n9) {
                    stringBuilder.append(BYTE2CHAR[byteBuf.getUnsignedByte(n9)]);
                }
                stringBuilder.append('|');
            }
            if (n8 != 0) {
                n5 = (n7 << 4) + n6;
                HexUtil.appendHexDumpRowPrefix(stringBuilder, n7, n5);
                n4 = n5 + n8;
                for (n3 = n5; n3 < n4; ++n3) {
                    stringBuilder.append(BYTE2HEX[byteBuf.getUnsignedByte(n3)]);
                }
                stringBuilder.append(HEXPADDING[n8]);
                stringBuilder.append(" |");
                for (n3 = n5; n3 < n4; ++n3) {
                    stringBuilder.append(BYTE2CHAR[byteBuf.getUnsignedByte(n3)]);
                }
                stringBuilder.append(BYTEPADDING[n8]);
                stringBuilder.append('|');
            }
            stringBuilder.append(StringUtil.NEWLINE + "+--------+-------------------------------------------------+----------------+");
        }

        private static void appendHexDumpRowPrefix(StringBuilder stringBuilder, int n, int n2) {
            if (n < HEXDUMP_ROWPREFIXES.length) {
                stringBuilder.append(HEXDUMP_ROWPREFIXES[n]);
            } else {
                stringBuilder.append(StringUtil.NEWLINE);
                stringBuilder.append(Long.toHexString((long)n2 & 0xFFFFFFFFL | 0x100000000L));
                stringBuilder.setCharAt(stringBuilder.length() - 9, '|');
                stringBuilder.append('|');
            }
        }

        static String access$000(ByteBuf byteBuf, int n, int n2) {
            return HexUtil.hexDump(byteBuf, n, n2);
        }

        static String access$100(byte[] byArray, int n, int n2) {
            return HexUtil.hexDump(byArray, n, n2);
        }

        static String access$200(ByteBuf byteBuf, int n, int n2) {
            return HexUtil.prettyHexDump(byteBuf, n, n2);
        }

        static void access$300(StringBuilder stringBuilder, ByteBuf byteBuf, int n, int n2) {
            HexUtil.appendPrettyHexDump(stringBuilder, byteBuf, n, n2);
        }

        static {
            int n;
            StringBuilder stringBuilder;
            int n2;
            BYTE2CHAR = new char[256];
            HEXDUMP_TABLE = new char[1024];
            HEXPADDING = new String[16];
            HEXDUMP_ROWPREFIXES = new String[4096];
            BYTE2HEX = new String[256];
            BYTEPADDING = new String[16];
            char[] cArray = "0123456789abcdef".toCharArray();
            for (n2 = 0; n2 < 256; ++n2) {
                HexUtil.HEXDUMP_TABLE[n2 << 1] = cArray[n2 >>> 4 & 0xF];
                HexUtil.HEXDUMP_TABLE[(n2 << 1) + 1] = cArray[n2 & 0xF];
            }
            for (n2 = 0; n2 < HEXPADDING.length; ++n2) {
                int n3 = HEXPADDING.length - n2;
                stringBuilder = new StringBuilder(n3 * 3);
                for (n = 0; n < n3; ++n) {
                    stringBuilder.append("   ");
                }
                HexUtil.HEXPADDING[n2] = stringBuilder.toString();
            }
            for (n2 = 0; n2 < HEXDUMP_ROWPREFIXES.length; ++n2) {
                StringBuilder stringBuilder2 = new StringBuilder(12);
                stringBuilder2.append(StringUtil.NEWLINE);
                stringBuilder2.append(Long.toHexString((long)(n2 << 4) & 0xFFFFFFFFL | 0x100000000L));
                stringBuilder2.setCharAt(stringBuilder2.length() - 9, '|');
                stringBuilder2.append('|');
                HexUtil.HEXDUMP_ROWPREFIXES[n2] = stringBuilder2.toString();
            }
            for (n2 = 0; n2 < BYTE2HEX.length; ++n2) {
                HexUtil.BYTE2HEX[n2] = ' ' + StringUtil.byteToHexStringPadded(n2);
            }
            for (n2 = 0; n2 < BYTEPADDING.length; ++n2) {
                int n4 = BYTEPADDING.length - n2;
                stringBuilder = new StringBuilder(n4);
                for (n = 0; n < n4; ++n) {
                    stringBuilder.append(' ');
                }
                HexUtil.BYTEPADDING[n2] = stringBuilder.toString();
            }
            for (n2 = 0; n2 < BYTE2CHAR.length; ++n2) {
                HexUtil.BYTE2CHAR[n2] = n2 <= 31 || n2 >= 127 ? 46 : (char)n2;
            }
        }
    }
}

