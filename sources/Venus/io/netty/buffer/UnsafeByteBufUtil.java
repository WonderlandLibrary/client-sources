/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledUnsafeDirectByteBuf;
import io.netty.buffer.UnpooledUnsafeNoCleanerDirectByteBuf;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

final class UnsafeByteBufUtil {
    private static final boolean UNALIGNED;
    private static final byte ZERO = 0;
    static final boolean $assertionsDisabled;

    static byte getByte(long l) {
        return PlatformDependent.getByte(l);
    }

    static short getShort(long l) {
        if (UNALIGNED) {
            short s = PlatformDependent.getShort(l);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? s : Short.reverseBytes(s);
        }
        return (short)(PlatformDependent.getByte(l) << 8 | PlatformDependent.getByte(l + 1L) & 0xFF);
    }

    static short getShortLE(long l) {
        if (UNALIGNED) {
            short s = PlatformDependent.getShort(l);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(s) : s;
        }
        return (short)(PlatformDependent.getByte(l) & 0xFF | PlatformDependent.getByte(l + 1L) << 8);
    }

    static int getUnsignedMedium(long l) {
        if (UNALIGNED) {
            return (PlatformDependent.getByte(l) & 0xFF) << 16 | (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? PlatformDependent.getShort(l + 1L) : Short.reverseBytes(PlatformDependent.getShort(l + 1L))) & 0xFFFF;
        }
        return (PlatformDependent.getByte(l) & 0xFF) << 16 | (PlatformDependent.getByte(l + 1L) & 0xFF) << 8 | PlatformDependent.getByte(l + 2L) & 0xFF;
    }

    static int getUnsignedMediumLE(long l) {
        if (UNALIGNED) {
            return PlatformDependent.getByte(l) & 0xFF | ((PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(PlatformDependent.getShort(l + 1L)) : PlatformDependent.getShort(l + 1L)) & 0xFFFF) << 8;
        }
        return PlatformDependent.getByte(l) & 0xFF | (PlatformDependent.getByte(l + 1L) & 0xFF) << 8 | (PlatformDependent.getByte(l + 2L) & 0xFF) << 16;
    }

    static int getInt(long l) {
        if (UNALIGNED) {
            int n = PlatformDependent.getInt(l);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? n : Integer.reverseBytes(n);
        }
        return PlatformDependent.getByte(l) << 24 | (PlatformDependent.getByte(l + 1L) & 0xFF) << 16 | (PlatformDependent.getByte(l + 2L) & 0xFF) << 8 | PlatformDependent.getByte(l + 3L) & 0xFF;
    }

    static int getIntLE(long l) {
        if (UNALIGNED) {
            int n = PlatformDependent.getInt(l);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(n) : n;
        }
        return PlatformDependent.getByte(l) & 0xFF | (PlatformDependent.getByte(l + 1L) & 0xFF) << 8 | (PlatformDependent.getByte(l + 2L) & 0xFF) << 16 | PlatformDependent.getByte(l + 3L) << 24;
    }

    static long getLong(long l) {
        if (UNALIGNED) {
            long l2 = PlatformDependent.getLong(l);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? l2 : Long.reverseBytes(l2);
        }
        return (long)PlatformDependent.getByte(l) << 56 | ((long)PlatformDependent.getByte(l + 1L) & 0xFFL) << 48 | ((long)PlatformDependent.getByte(l + 2L) & 0xFFL) << 40 | ((long)PlatformDependent.getByte(l + 3L) & 0xFFL) << 32 | ((long)PlatformDependent.getByte(l + 4L) & 0xFFL) << 24 | ((long)PlatformDependent.getByte(l + 5L) & 0xFFL) << 16 | ((long)PlatformDependent.getByte(l + 6L) & 0xFFL) << 8 | (long)PlatformDependent.getByte(l + 7L) & 0xFFL;
    }

    static long getLongLE(long l) {
        if (UNALIGNED) {
            long l2 = PlatformDependent.getLong(l);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(l2) : l2;
        }
        return (long)PlatformDependent.getByte(l) & 0xFFL | ((long)PlatformDependent.getByte(l + 1L) & 0xFFL) << 8 | ((long)PlatformDependent.getByte(l + 2L) & 0xFFL) << 16 | ((long)PlatformDependent.getByte(l + 3L) & 0xFFL) << 24 | ((long)PlatformDependent.getByte(l + 4L) & 0xFFL) << 32 | ((long)PlatformDependent.getByte(l + 5L) & 0xFFL) << 40 | ((long)PlatformDependent.getByte(l + 6L) & 0xFFL) << 48 | (long)PlatformDependent.getByte(l + 7L) << 56;
    }

    static void setByte(long l, int n) {
        PlatformDependent.putByte(l, (byte)n);
    }

    static void setShort(long l, int n) {
        if (UNALIGNED) {
            PlatformDependent.putShort(l, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)n : Short.reverseBytes((short)n));
        } else {
            PlatformDependent.putByte(l, (byte)(n >>> 8));
            PlatformDependent.putByte(l + 1L, (byte)n);
        }
    }

    static void setShortLE(long l, int n) {
        if (UNALIGNED) {
            PlatformDependent.putShort(l, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)n) : (short)n);
        } else {
            PlatformDependent.putByte(l, (byte)n);
            PlatformDependent.putByte(l + 1L, (byte)(n >>> 8));
        }
    }

    static void setMedium(long l, int n) {
        PlatformDependent.putByte(l, (byte)(n >>> 16));
        if (UNALIGNED) {
            PlatformDependent.putShort(l + 1L, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)n : Short.reverseBytes((short)n));
        } else {
            PlatformDependent.putByte(l + 1L, (byte)(n >>> 8));
            PlatformDependent.putByte(l + 2L, (byte)n);
        }
    }

    static void setMediumLE(long l, int n) {
        PlatformDependent.putByte(l, (byte)n);
        if (UNALIGNED) {
            PlatformDependent.putShort(l + 1L, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)(n >>> 8)) : (short)(n >>> 8));
        } else {
            PlatformDependent.putByte(l + 1L, (byte)(n >>> 8));
            PlatformDependent.putByte(l + 2L, (byte)(n >>> 16));
        }
    }

    static void setInt(long l, int n) {
        if (UNALIGNED) {
            PlatformDependent.putInt(l, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? n : Integer.reverseBytes(n));
        } else {
            PlatformDependent.putByte(l, (byte)(n >>> 24));
            PlatformDependent.putByte(l + 1L, (byte)(n >>> 16));
            PlatformDependent.putByte(l + 2L, (byte)(n >>> 8));
            PlatformDependent.putByte(l + 3L, (byte)n);
        }
    }

    static void setIntLE(long l, int n) {
        if (UNALIGNED) {
            PlatformDependent.putInt(l, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(n) : n);
        } else {
            PlatformDependent.putByte(l, (byte)n);
            PlatformDependent.putByte(l + 1L, (byte)(n >>> 8));
            PlatformDependent.putByte(l + 2L, (byte)(n >>> 16));
            PlatformDependent.putByte(l + 3L, (byte)(n >>> 24));
        }
    }

    static void setLong(long l, long l2) {
        if (UNALIGNED) {
            PlatformDependent.putLong(l, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? l2 : Long.reverseBytes(l2));
        } else {
            PlatformDependent.putByte(l, (byte)(l2 >>> 56));
            PlatformDependent.putByte(l + 1L, (byte)(l2 >>> 48));
            PlatformDependent.putByte(l + 2L, (byte)(l2 >>> 40));
            PlatformDependent.putByte(l + 3L, (byte)(l2 >>> 32));
            PlatformDependent.putByte(l + 4L, (byte)(l2 >>> 24));
            PlatformDependent.putByte(l + 5L, (byte)(l2 >>> 16));
            PlatformDependent.putByte(l + 6L, (byte)(l2 >>> 8));
            PlatformDependent.putByte(l + 7L, (byte)l2);
        }
    }

    static void setLongLE(long l, long l2) {
        if (UNALIGNED) {
            PlatformDependent.putLong(l, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(l2) : l2);
        } else {
            PlatformDependent.putByte(l, (byte)l2);
            PlatformDependent.putByte(l + 1L, (byte)(l2 >>> 8));
            PlatformDependent.putByte(l + 2L, (byte)(l2 >>> 16));
            PlatformDependent.putByte(l + 3L, (byte)(l2 >>> 24));
            PlatformDependent.putByte(l + 4L, (byte)(l2 >>> 32));
            PlatformDependent.putByte(l + 5L, (byte)(l2 >>> 40));
            PlatformDependent.putByte(l + 6L, (byte)(l2 >>> 48));
            PlatformDependent.putByte(l + 7L, (byte)(l2 >>> 56));
        }
    }

    static byte getByte(byte[] byArray, int n) {
        return PlatformDependent.getByte(byArray, n);
    }

    static short getShort(byte[] byArray, int n) {
        if (UNALIGNED) {
            short s = PlatformDependent.getShort(byArray, n);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? s : Short.reverseBytes(s);
        }
        return (short)(PlatformDependent.getByte(byArray, n) << 8 | PlatformDependent.getByte(byArray, n + 1) & 0xFF);
    }

    static short getShortLE(byte[] byArray, int n) {
        if (UNALIGNED) {
            short s = PlatformDependent.getShort(byArray, n);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(s) : s;
        }
        return (short)(PlatformDependent.getByte(byArray, n) & 0xFF | PlatformDependent.getByte(byArray, n + 1) << 8);
    }

    static int getUnsignedMedium(byte[] byArray, int n) {
        if (UNALIGNED) {
            return (PlatformDependent.getByte(byArray, n) & 0xFF) << 16 | (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? PlatformDependent.getShort(byArray, n + 1) : Short.reverseBytes(PlatformDependent.getShort(byArray, n + 1))) & 0xFFFF;
        }
        return (PlatformDependent.getByte(byArray, n) & 0xFF) << 16 | (PlatformDependent.getByte(byArray, n + 1) & 0xFF) << 8 | PlatformDependent.getByte(byArray, n + 2) & 0xFF;
    }

    static int getUnsignedMediumLE(byte[] byArray, int n) {
        if (UNALIGNED) {
            return PlatformDependent.getByte(byArray, n) & 0xFF | ((PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(PlatformDependent.getShort(byArray, n + 1)) : PlatformDependent.getShort(byArray, n + 1)) & 0xFFFF) << 8;
        }
        return PlatformDependent.getByte(byArray, n) & 0xFF | (PlatformDependent.getByte(byArray, n + 1) & 0xFF) << 8 | (PlatformDependent.getByte(byArray, n + 2) & 0xFF) << 16;
    }

    static int getInt(byte[] byArray, int n) {
        if (UNALIGNED) {
            int n2 = PlatformDependent.getInt(byArray, n);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? n2 : Integer.reverseBytes(n2);
        }
        return PlatformDependent.getByte(byArray, n) << 24 | (PlatformDependent.getByte(byArray, n + 1) & 0xFF) << 16 | (PlatformDependent.getByte(byArray, n + 2) & 0xFF) << 8 | PlatformDependent.getByte(byArray, n + 3) & 0xFF;
    }

    static int getIntLE(byte[] byArray, int n) {
        if (UNALIGNED) {
            int n2 = PlatformDependent.getInt(byArray, n);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(n2) : n2;
        }
        return PlatformDependent.getByte(byArray, n) & 0xFF | (PlatformDependent.getByte(byArray, n + 1) & 0xFF) << 8 | (PlatformDependent.getByte(byArray, n + 2) & 0xFF) << 16 | PlatformDependent.getByte(byArray, n + 3) << 24;
    }

    static long getLong(byte[] byArray, int n) {
        if (UNALIGNED) {
            long l = PlatformDependent.getLong(byArray, n);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? l : Long.reverseBytes(l);
        }
        return (long)PlatformDependent.getByte(byArray, n) << 56 | ((long)PlatformDependent.getByte(byArray, n + 1) & 0xFFL) << 48 | ((long)PlatformDependent.getByte(byArray, n + 2) & 0xFFL) << 40 | ((long)PlatformDependent.getByte(byArray, n + 3) & 0xFFL) << 32 | ((long)PlatformDependent.getByte(byArray, n + 4) & 0xFFL) << 24 | ((long)PlatformDependent.getByte(byArray, n + 5) & 0xFFL) << 16 | ((long)PlatformDependent.getByte(byArray, n + 6) & 0xFFL) << 8 | (long)PlatformDependent.getByte(byArray, n + 7) & 0xFFL;
    }

    static long getLongLE(byte[] byArray, int n) {
        if (UNALIGNED) {
            long l = PlatformDependent.getLong(byArray, n);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(l) : l;
        }
        return (long)PlatformDependent.getByte(byArray, n) & 0xFFL | ((long)PlatformDependent.getByte(byArray, n + 1) & 0xFFL) << 8 | ((long)PlatformDependent.getByte(byArray, n + 2) & 0xFFL) << 16 | ((long)PlatformDependent.getByte(byArray, n + 3) & 0xFFL) << 24 | ((long)PlatformDependent.getByte(byArray, n + 4) & 0xFFL) << 32 | ((long)PlatformDependent.getByte(byArray, n + 5) & 0xFFL) << 40 | ((long)PlatformDependent.getByte(byArray, n + 6) & 0xFFL) << 48 | (long)PlatformDependent.getByte(byArray, n + 7) << 56;
    }

    static void setByte(byte[] byArray, int n, int n2) {
        PlatformDependent.putByte(byArray, n, (byte)n2);
    }

    static void setShort(byte[] byArray, int n, int n2) {
        if (UNALIGNED) {
            PlatformDependent.putShort(byArray, n, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)n2 : Short.reverseBytes((short)n2));
        } else {
            PlatformDependent.putByte(byArray, n, (byte)(n2 >>> 8));
            PlatformDependent.putByte(byArray, n + 1, (byte)n2);
        }
    }

    static void setShortLE(byte[] byArray, int n, int n2) {
        if (UNALIGNED) {
            PlatformDependent.putShort(byArray, n, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)n2) : (short)n2);
        } else {
            PlatformDependent.putByte(byArray, n, (byte)n2);
            PlatformDependent.putByte(byArray, n + 1, (byte)(n2 >>> 8));
        }
    }

    static void setMedium(byte[] byArray, int n, int n2) {
        PlatformDependent.putByte(byArray, n, (byte)(n2 >>> 16));
        if (UNALIGNED) {
            PlatformDependent.putShort(byArray, n + 1, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)n2 : Short.reverseBytes((short)n2));
        } else {
            PlatformDependent.putByte(byArray, n + 1, (byte)(n2 >>> 8));
            PlatformDependent.putByte(byArray, n + 2, (byte)n2);
        }
    }

    static void setMediumLE(byte[] byArray, int n, int n2) {
        PlatformDependent.putByte(byArray, n, (byte)n2);
        if (UNALIGNED) {
            PlatformDependent.putShort(byArray, n + 1, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)(n2 >>> 8)) : (short)(n2 >>> 8));
        } else {
            PlatformDependent.putByte(byArray, n + 1, (byte)(n2 >>> 8));
            PlatformDependent.putByte(byArray, n + 2, (byte)(n2 >>> 16));
        }
    }

    static void setInt(byte[] byArray, int n, int n2) {
        if (UNALIGNED) {
            PlatformDependent.putInt(byArray, n, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? n2 : Integer.reverseBytes(n2));
        } else {
            PlatformDependent.putByte(byArray, n, (byte)(n2 >>> 24));
            PlatformDependent.putByte(byArray, n + 1, (byte)(n2 >>> 16));
            PlatformDependent.putByte(byArray, n + 2, (byte)(n2 >>> 8));
            PlatformDependent.putByte(byArray, n + 3, (byte)n2);
        }
    }

    static void setIntLE(byte[] byArray, int n, int n2) {
        if (UNALIGNED) {
            PlatformDependent.putInt(byArray, n, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(n2) : n2);
        } else {
            PlatformDependent.putByte(byArray, n, (byte)n2);
            PlatformDependent.putByte(byArray, n + 1, (byte)(n2 >>> 8));
            PlatformDependent.putByte(byArray, n + 2, (byte)(n2 >>> 16));
            PlatformDependent.putByte(byArray, n + 3, (byte)(n2 >>> 24));
        }
    }

    static void setLong(byte[] byArray, int n, long l) {
        if (UNALIGNED) {
            PlatformDependent.putLong(byArray, n, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? l : Long.reverseBytes(l));
        } else {
            PlatformDependent.putByte(byArray, n, (byte)(l >>> 56));
            PlatformDependent.putByte(byArray, n + 1, (byte)(l >>> 48));
            PlatformDependent.putByte(byArray, n + 2, (byte)(l >>> 40));
            PlatformDependent.putByte(byArray, n + 3, (byte)(l >>> 32));
            PlatformDependent.putByte(byArray, n + 4, (byte)(l >>> 24));
            PlatformDependent.putByte(byArray, n + 5, (byte)(l >>> 16));
            PlatformDependent.putByte(byArray, n + 6, (byte)(l >>> 8));
            PlatformDependent.putByte(byArray, n + 7, (byte)l);
        }
    }

    static void setLongLE(byte[] byArray, int n, long l) {
        if (UNALIGNED) {
            PlatformDependent.putLong(byArray, n, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(l) : l);
        } else {
            PlatformDependent.putByte(byArray, n, (byte)l);
            PlatformDependent.putByte(byArray, n + 1, (byte)(l >>> 8));
            PlatformDependent.putByte(byArray, n + 2, (byte)(l >>> 16));
            PlatformDependent.putByte(byArray, n + 3, (byte)(l >>> 24));
            PlatformDependent.putByte(byArray, n + 4, (byte)(l >>> 32));
            PlatformDependent.putByte(byArray, n + 5, (byte)(l >>> 40));
            PlatformDependent.putByte(byArray, n + 6, (byte)(l >>> 48));
            PlatformDependent.putByte(byArray, n + 7, (byte)(l >>> 56));
        }
    }

    static void setZero(byte[] byArray, int n, int n2) {
        if (n2 == 0) {
            return;
        }
        PlatformDependent.setMemory(byArray, n, n2, (byte)0);
    }

    static ByteBuf copy(AbstractByteBuf abstractByteBuf, long l, int n, int n2) {
        abstractByteBuf.checkIndex(n, n2);
        ByteBuf byteBuf = abstractByteBuf.alloc().directBuffer(n2, abstractByteBuf.maxCapacity());
        if (n2 != 0) {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(l, byteBuf.memoryAddress(), n2);
                byteBuf.setIndex(0, n2);
            } else {
                byteBuf.writeBytes(abstractByteBuf, n, n2);
            }
        }
        return byteBuf;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static int setBytes(AbstractByteBuf abstractByteBuf, long l, int n, InputStream inputStream, int n2) throws IOException {
        abstractByteBuf.checkIndex(n, n2);
        ByteBuf byteBuf = abstractByteBuf.alloc().heapBuffer(n2);
        try {
            byte[] byArray = byteBuf.array();
            int n3 = byteBuf.arrayOffset();
            int n4 = inputStream.read(byArray, n3, n2);
            if (n4 > 0) {
                PlatformDependent.copyMemory(byArray, n3, l, (long)n4);
            }
            int n5 = n4;
            return n5;
        } finally {
            byteBuf.release();
        }
    }

    static void getBytes(AbstractByteBuf abstractByteBuf, long l, int n, ByteBuf byteBuf, int n2, int n3) {
        abstractByteBuf.checkIndex(n, n3);
        ObjectUtil.checkNotNull(byteBuf, "dst");
        if (MathUtil.isOutOfBounds(n2, n3, byteBuf.capacity())) {
            throw new IndexOutOfBoundsException("dstIndex: " + n2);
        }
        if (byteBuf.hasMemoryAddress()) {
            PlatformDependent.copyMemory(l, byteBuf.memoryAddress() + (long)n2, n3);
        } else if (byteBuf.hasArray()) {
            PlatformDependent.copyMemory(l, byteBuf.array(), byteBuf.arrayOffset() + n2, (long)n3);
        } else {
            byteBuf.setBytes(n2, abstractByteBuf, n, n3);
        }
    }

    static void getBytes(AbstractByteBuf abstractByteBuf, long l, int n, byte[] byArray, int n2, int n3) {
        abstractByteBuf.checkIndex(n, n3);
        ObjectUtil.checkNotNull(byArray, "dst");
        if (MathUtil.isOutOfBounds(n2, n3, byArray.length)) {
            throw new IndexOutOfBoundsException("dstIndex: " + n2);
        }
        if (n3 != 0) {
            PlatformDependent.copyMemory(l, byArray, n2, (long)n3);
        }
    }

    static void getBytes(AbstractByteBuf abstractByteBuf, long l, int n, ByteBuffer byteBuffer) {
        abstractByteBuf.checkIndex(n, byteBuffer.remaining());
        if (byteBuffer.remaining() == 0) {
            return;
        }
        if (byteBuffer.isDirect()) {
            if (byteBuffer.isReadOnly()) {
                throw new ReadOnlyBufferException();
            }
            long l2 = PlatformDependent.directBufferAddress(byteBuffer);
            PlatformDependent.copyMemory(l, l2 + (long)byteBuffer.position(), byteBuffer.remaining());
            byteBuffer.position(byteBuffer.position() + byteBuffer.remaining());
        } else if (byteBuffer.hasArray()) {
            PlatformDependent.copyMemory(l, byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), (long)byteBuffer.remaining());
            byteBuffer.position(byteBuffer.position() + byteBuffer.remaining());
        } else {
            byteBuffer.put(abstractByteBuf.nioBuffer());
        }
    }

    static void setBytes(AbstractByteBuf abstractByteBuf, long l, int n, ByteBuf byteBuf, int n2, int n3) {
        abstractByteBuf.checkIndex(n, n3);
        ObjectUtil.checkNotNull(byteBuf, "src");
        if (MathUtil.isOutOfBounds(n2, n3, byteBuf.capacity())) {
            throw new IndexOutOfBoundsException("srcIndex: " + n2);
        }
        if (n3 != 0) {
            if (byteBuf.hasMemoryAddress()) {
                PlatformDependent.copyMemory(byteBuf.memoryAddress() + (long)n2, l, n3);
            } else if (byteBuf.hasArray()) {
                PlatformDependent.copyMemory(byteBuf.array(), byteBuf.arrayOffset() + n2, l, (long)n3);
            } else {
                byteBuf.getBytes(n2, abstractByteBuf, n, n3);
            }
        }
    }

    static void setBytes(AbstractByteBuf abstractByteBuf, long l, int n, byte[] byArray, int n2, int n3) {
        abstractByteBuf.checkIndex(n, n3);
        if (n3 != 0) {
            PlatformDependent.copyMemory(byArray, n2, l, (long)n3);
        }
    }

    static void setBytes(AbstractByteBuf abstractByteBuf, long l, int n, ByteBuffer byteBuffer) {
        int n2 = byteBuffer.remaining();
        if (n2 == 0) {
            return;
        }
        if (byteBuffer.isDirect()) {
            abstractByteBuf.checkIndex(n, n2);
            long l2 = PlatformDependent.directBufferAddress(byteBuffer);
            PlatformDependent.copyMemory(l2 + (long)byteBuffer.position(), l, n2);
            byteBuffer.position(byteBuffer.position() + n2);
        } else if (byteBuffer.hasArray()) {
            abstractByteBuf.checkIndex(n, n2);
            PlatformDependent.copyMemory(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), l, (long)n2);
            byteBuffer.position(byteBuffer.position() + n2);
        } else if (n2 < 8) {
            UnsafeByteBufUtil.setSingleBytes(abstractByteBuf, l, n, byteBuffer, n2);
        } else {
            if (!$assertionsDisabled && abstractByteBuf.nioBufferCount() != 1) {
                throw new AssertionError();
            }
            ByteBuffer byteBuffer2 = abstractByteBuf.internalNioBuffer(n, n2);
            byteBuffer2.put(byteBuffer);
        }
    }

    private static void setSingleBytes(AbstractByteBuf abstractByteBuf, long l, int n, ByteBuffer byteBuffer, int n2) {
        abstractByteBuf.checkIndex(n, n2);
        int n3 = byteBuffer.position();
        int n4 = byteBuffer.limit();
        long l2 = l;
        for (int i = n3; i < n4; ++i) {
            byte by = byteBuffer.get(i);
            PlatformDependent.putByte(l2, by);
            ++l2;
        }
        byteBuffer.position(n4);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void getBytes(AbstractByteBuf abstractByteBuf, long l, int n, OutputStream outputStream, int n2) throws IOException {
        abstractByteBuf.checkIndex(n, n2);
        if (n2 != 0) {
            int n3 = Math.min(n2, 8192);
            if (abstractByteBuf.alloc().isDirectBufferPooled()) {
                ByteBuf byteBuf = abstractByteBuf.alloc().heapBuffer(n3);
                try {
                    byte[] byArray = byteBuf.array();
                    int n4 = byteBuf.arrayOffset();
                    UnsafeByteBufUtil.getBytes(l, byArray, n4, n3, outputStream, n2);
                } finally {
                    byteBuf.release();
                }
            } else {
                UnsafeByteBufUtil.getBytes(l, new byte[n3], 0, n3, outputStream, n2);
            }
        }
    }

    private static void getBytes(long l, byte[] byArray, int n, int n2, OutputStream outputStream, int n3) throws IOException {
        int n4;
        do {
            n4 = Math.min(n2, n3);
            PlatformDependent.copyMemory(l, byArray, n, (long)n4);
            outputStream.write(byArray, n, n4);
            l += (long)n4;
        } while ((n3 -= n4) > 0);
    }

    static void setZero(long l, int n) {
        if (n == 0) {
            return;
        }
        PlatformDependent.setMemory(l, n, (byte)0);
    }

    static UnpooledUnsafeDirectByteBuf newUnsafeDirectByteBuf(ByteBufAllocator byteBufAllocator, int n, int n2) {
        if (PlatformDependent.useDirectBufferNoCleaner()) {
            return new UnpooledUnsafeNoCleanerDirectByteBuf(byteBufAllocator, n, n2);
        }
        return new UnpooledUnsafeDirectByteBuf(byteBufAllocator, n, n2);
    }

    private UnsafeByteBufUtil() {
    }

    static {
        $assertionsDisabled = !UnsafeByteBufUtil.class.desiredAssertionStatus();
        UNALIGNED = PlatformDependent.isUnaligned();
    }
}

