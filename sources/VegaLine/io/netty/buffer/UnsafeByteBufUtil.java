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
    private static final boolean UNALIGNED = PlatformDependent.isUnaligned();
    private static final byte ZERO = 0;

    static byte getByte(long address) {
        return PlatformDependent.getByte(address);
    }

    static short getShort(long address) {
        if (UNALIGNED) {
            short v = PlatformDependent.getShort(address);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
        }
        return (short)(PlatformDependent.getByte(address) << 8 | PlatformDependent.getByte(address + 1L) & 0xFF);
    }

    static short getShortLE(long address) {
        if (UNALIGNED) {
            short v = PlatformDependent.getShort(address);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(v) : v;
        }
        return (short)(PlatformDependent.getByte(address) & 0xFF | PlatformDependent.getByte(address + 1L) << 8);
    }

    static int getUnsignedMedium(long address) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                return PlatformDependent.getByte(address) & 0xFF | (PlatformDependent.getShort(address + 1L) & 0xFFFF) << 8;
            }
            return (Short.reverseBytes(PlatformDependent.getShort(address)) & 0xFFFF) << 8 | PlatformDependent.getByte(address + 2L) & 0xFF;
        }
        return (PlatformDependent.getByte(address) & 0xFF) << 16 | (PlatformDependent.getByte(address + 1L) & 0xFF) << 8 | PlatformDependent.getByte(address + 2L) & 0xFF;
    }

    static int getUnsignedMediumLE(long address) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                return (Short.reverseBytes(PlatformDependent.getShort(address)) & 0xFFFF) << 8 | PlatformDependent.getByte(address + 2L) & 0xFF;
            }
            return PlatformDependent.getByte(address) & 0xFF | (PlatformDependent.getShort(address + 1L) & 0xFFFF) << 8;
        }
        return PlatformDependent.getByte(address) & 0xFF | (PlatformDependent.getByte(address + 1L) & 0xFF) << 8 | (PlatformDependent.getByte(address + 1L) & 0xFF) << 16;
    }

    static int getInt(long address) {
        if (UNALIGNED) {
            int v = PlatformDependent.getInt(address);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
        }
        return PlatformDependent.getByte(address) << 24 | (PlatformDependent.getByte(address + 1L) & 0xFF) << 16 | (PlatformDependent.getByte(address + 2L) & 0xFF) << 8 | PlatformDependent.getByte(address + 3L) & 0xFF;
    }

    static int getIntLE(long address) {
        if (UNALIGNED) {
            int v = PlatformDependent.getInt(address);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(v) : v;
        }
        return PlatformDependent.getByte(address) & 0xFF | (PlatformDependent.getByte(address + 1L) & 0xFF) << 8 | (PlatformDependent.getByte(address + 2L) & 0xFF) << 16 | PlatformDependent.getByte(address + 3L) << 24;
    }

    static long getLong(long address) {
        if (UNALIGNED) {
            long v = PlatformDependent.getLong(address);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
        }
        return (long)PlatformDependent.getByte(address) << 56 | ((long)PlatformDependent.getByte(address + 1L) & 0xFFL) << 48 | ((long)PlatformDependent.getByte(address + 2L) & 0xFFL) << 40 | ((long)PlatformDependent.getByte(address + 3L) & 0xFFL) << 32 | ((long)PlatformDependent.getByte(address + 4L) & 0xFFL) << 24 | ((long)PlatformDependent.getByte(address + 5L) & 0xFFL) << 16 | ((long)PlatformDependent.getByte(address + 6L) & 0xFFL) << 8 | (long)PlatformDependent.getByte(address + 7L) & 0xFFL;
    }

    static long getLongLE(long address) {
        if (UNALIGNED) {
            long v = PlatformDependent.getLong(address);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(v) : v;
        }
        return (long)PlatformDependent.getByte(address) & 0xFFL | ((long)PlatformDependent.getByte(address + 1L) & 0xFFL) << 8 | ((long)PlatformDependent.getByte(address + 2L) & 0xFFL) << 16 | ((long)PlatformDependent.getByte(address + 3L) & 0xFFL) << 24 | ((long)PlatformDependent.getByte(address + 4L) & 0xFFL) << 32 | ((long)PlatformDependent.getByte(address + 5L) & 0xFFL) << 40 | ((long)PlatformDependent.getByte(address + 6L) & 0xFFL) << 48 | (long)PlatformDependent.getByte(address + 7L) << 56;
    }

    static void setByte(long address, int value) {
        PlatformDependent.putByte(address, (byte)value);
    }

    static void setShort(long address, int value) {
        if (UNALIGNED) {
            PlatformDependent.putShort(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)value : Short.reverseBytes((short)value));
        } else {
            PlatformDependent.putByte(address, (byte)(value >>> 8));
            PlatformDependent.putByte(address + 1L, (byte)value);
        }
    }

    static void setShortLE(long address, int value) {
        if (UNALIGNED) {
            PlatformDependent.putShort(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)value) : (short)value);
        } else {
            PlatformDependent.putByte(address, (byte)value);
            PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
        }
    }

    static void setMedium(long address, int value) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                PlatformDependent.putByte(address, (byte)value);
                PlatformDependent.putShort(address + 1L, (short)(value >>> 8));
            } else {
                PlatformDependent.putShort(address, Short.reverseBytes((short)(value >>> 8)));
                PlatformDependent.putByte(address + 2L, (byte)value);
            }
        } else {
            PlatformDependent.putByte(address, (byte)(value >>> 16));
            PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
            PlatformDependent.putByte(address + 2L, (byte)value);
        }
    }

    static void setMediumLE(long address, int value) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                PlatformDependent.putShort(address, Short.reverseBytes((short)(value >>> 8)));
                PlatformDependent.putByte(address + 2L, (byte)value);
            } else {
                PlatformDependent.putByte(address, (byte)value);
                PlatformDependent.putShort(address + 1L, (short)(value >>> 8));
            }
        } else {
            PlatformDependent.putByte(address, (byte)value);
            PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
            PlatformDependent.putByte(address + 2L, (byte)(value >>> 16));
        }
    }

    static void setInt(long address, int value) {
        if (UNALIGNED) {
            PlatformDependent.putInt(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
        } else {
            PlatformDependent.putByte(address, (byte)(value >>> 24));
            PlatformDependent.putByte(address + 1L, (byte)(value >>> 16));
            PlatformDependent.putByte(address + 2L, (byte)(value >>> 8));
            PlatformDependent.putByte(address + 3L, (byte)value);
        }
    }

    static void setIntLE(long address, int value) {
        if (UNALIGNED) {
            PlatformDependent.putInt(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(value) : value);
        } else {
            PlatformDependent.putByte(address, (byte)value);
            PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
            PlatformDependent.putByte(address + 2L, (byte)(value >>> 16));
            PlatformDependent.putByte(address + 3L, (byte)(value >>> 24));
        }
    }

    static void setLong(long address, long value) {
        if (UNALIGNED) {
            PlatformDependent.putLong(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
        } else {
            PlatformDependent.putByte(address, (byte)(value >>> 56));
            PlatformDependent.putByte(address + 1L, (byte)(value >>> 48));
            PlatformDependent.putByte(address + 2L, (byte)(value >>> 40));
            PlatformDependent.putByte(address + 3L, (byte)(value >>> 32));
            PlatformDependent.putByte(address + 4L, (byte)(value >>> 24));
            PlatformDependent.putByte(address + 5L, (byte)(value >>> 16));
            PlatformDependent.putByte(address + 6L, (byte)(value >>> 8));
            PlatformDependent.putByte(address + 7L, (byte)value);
        }
    }

    static void setLongLE(long address, long value) {
        if (UNALIGNED) {
            PlatformDependent.putLong(address, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(value) : value);
        } else {
            PlatformDependent.putByte(address, (byte)value);
            PlatformDependent.putByte(address + 1L, (byte)(value >>> 8));
            PlatformDependent.putByte(address + 2L, (byte)(value >>> 16));
            PlatformDependent.putByte(address + 3L, (byte)(value >>> 24));
            PlatformDependent.putByte(address + 4L, (byte)(value >>> 32));
            PlatformDependent.putByte(address + 5L, (byte)(value >>> 40));
            PlatformDependent.putByte(address + 6L, (byte)(value >>> 48));
            PlatformDependent.putByte(address + 7L, (byte)(value >>> 56));
        }
    }

    static byte getByte(byte[] array, int index) {
        return PlatformDependent.getByte(array, index);
    }

    static short getShort(byte[] array, int index) {
        if (UNALIGNED) {
            short v = PlatformDependent.getShort(array, index);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Short.reverseBytes(v);
        }
        return (short)(PlatformDependent.getByte(array, index) << 8 | PlatformDependent.getByte(array, index + 1) & 0xFF);
    }

    static short getShortLE(byte[] array, int index) {
        if (UNALIGNED) {
            short v = PlatformDependent.getShort(array, index);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes(v) : v;
        }
        return (short)(PlatformDependent.getByte(index) & 0xFF | PlatformDependent.getByte(index + 1) << 8);
    }

    static int getUnsignedMedium(byte[] array, int index) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                return PlatformDependent.getByte(array, index) & 0xFF | (PlatformDependent.getShort(array, index + 1) & 0xFFFF) << 8;
            }
            return (Short.reverseBytes(PlatformDependent.getShort(array, index)) & 0xFFFF) << 8 | PlatformDependent.getByte(array, index + 2) & 0xFF;
        }
        return (PlatformDependent.getByte(array, index) & 0xFF) << 16 | (PlatformDependent.getByte(array, index + 1) & 0xFF) << 8 | PlatformDependent.getByte(array, index + 2) & 0xFF;
    }

    static int getUnsignedMediumLE(byte[] array, int index) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                return (Short.reverseBytes(PlatformDependent.getShort(array, index)) & 0xFFFF) << 8 | PlatformDependent.getByte(array, index + 2) & 0xFF;
            }
            return PlatformDependent.getByte(array, index) & 0xFF | (PlatformDependent.getShort(array, index + 1) & 0xFFFF) << 8;
        }
        return PlatformDependent.getByte(array, index) & 0xFF | (PlatformDependent.getByte(array, index + 1) & 0xFF) << 8 | (PlatformDependent.getByte(array, index + 2) & 0xFF) << 16;
    }

    static int getInt(byte[] array, int index) {
        if (UNALIGNED) {
            int v = PlatformDependent.getInt(array, index);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Integer.reverseBytes(v);
        }
        return PlatformDependent.getByte(array, index) << 24 | (PlatformDependent.getByte(array, index + 1) & 0xFF) << 16 | (PlatformDependent.getByte(array, index + 2) & 0xFF) << 8 | PlatformDependent.getByte(array, index + 3) & 0xFF;
    }

    static int getIntLE(byte[] array, int index) {
        if (UNALIGNED) {
            int v = PlatformDependent.getInt(array, index);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(v) : v;
        }
        return PlatformDependent.getByte(array, index) & 0xFF | (PlatformDependent.getByte(array, index + 1) & 0xFF) << 8 | (PlatformDependent.getByte(array, index + 2) & 0xFF) << 16 | PlatformDependent.getByte(array, index + 3) << 24;
    }

    static long getLong(byte[] array, int index) {
        if (UNALIGNED) {
            long v = PlatformDependent.getLong(array, index);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? v : Long.reverseBytes(v);
        }
        return (long)PlatformDependent.getByte(array, index) << 56 | ((long)PlatformDependent.getByte(array, index + 1) & 0xFFL) << 48 | ((long)PlatformDependent.getByte(array, index + 2) & 0xFFL) << 40 | ((long)PlatformDependent.getByte(array, index + 3) & 0xFFL) << 32 | ((long)PlatformDependent.getByte(array, index + 4) & 0xFFL) << 24 | ((long)PlatformDependent.getByte(array, index + 5) & 0xFFL) << 16 | ((long)PlatformDependent.getByte(array, index + 6) & 0xFFL) << 8 | (long)PlatformDependent.getByte(array, index + 7) & 0xFFL;
    }

    static long getLongLE(byte[] array, int index) {
        if (UNALIGNED) {
            long v = PlatformDependent.getLong(array, index);
            return PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(v) : v;
        }
        return (long)PlatformDependent.getByte(array, index) & 0xFFL | ((long)PlatformDependent.getByte(array, index + 1) & 0xFFL) << 8 | ((long)PlatformDependent.getByte(array, index + 2) & 0xFFL) << 16 | ((long)PlatformDependent.getByte(array, index + 3) & 0xFFL) << 24 | ((long)PlatformDependent.getByte(array, index + 4) & 0xFFL) << 32 | ((long)PlatformDependent.getByte(array, index + 5) & 0xFFL) << 40 | ((long)PlatformDependent.getByte(array, index + 6) & 0xFFL) << 48 | (long)PlatformDependent.getByte(array, index + 7) << 56;
    }

    static void setByte(byte[] array, int index, int value) {
        PlatformDependent.putByte(array, index, (byte)value);
    }

    static void setShort(byte[] array, int index, int value) {
        if (UNALIGNED) {
            PlatformDependent.putShort(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? (short)value : Short.reverseBytes((short)value));
        } else {
            PlatformDependent.putByte(array, index, (byte)(value >>> 8));
            PlatformDependent.putByte(array, index + 1, (byte)value);
        }
    }

    static void setShortLE(byte[] array, int index, int value) {
        if (UNALIGNED) {
            PlatformDependent.putShort(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Short.reverseBytes((short)value) : (short)value);
        } else {
            PlatformDependent.putByte(array, index, (byte)value);
            PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
        }
    }

    static void setMedium(byte[] array, int index, int value) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                PlatformDependent.putByte(array, index, (byte)value);
                PlatformDependent.putShort(array, index + 1, (short)(value >>> 8));
            } else {
                PlatformDependent.putShort(array, index, Short.reverseBytes((short)(value >>> 8)));
                PlatformDependent.putByte(array, index + 2, (byte)value);
            }
        } else {
            PlatformDependent.putByte(array, index, (byte)(value >>> 16));
            PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
            PlatformDependent.putByte(array, index + 2, (byte)value);
        }
    }

    static void setMediumLE(byte[] array, int index, int value) {
        if (UNALIGNED) {
            if (PlatformDependent.BIG_ENDIAN_NATIVE_ORDER) {
                PlatformDependent.putShort(array, index, Short.reverseBytes((short)(value >>> 8)));
                PlatformDependent.putByte(array, index + 2, (byte)value);
            } else {
                PlatformDependent.putByte(array, index, (byte)value);
                PlatformDependent.putShort(array, index + 1, (short)(value >>> 8));
            }
        } else {
            PlatformDependent.putByte(array, index, (byte)value);
            PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
            PlatformDependent.putByte(array, index + 2, (byte)(value >>> 16));
        }
    }

    static void setInt(byte[] array, int index, int value) {
        if (UNALIGNED) {
            PlatformDependent.putInt(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Integer.reverseBytes(value));
        } else {
            PlatformDependent.putByte(array, index, (byte)(value >>> 24));
            PlatformDependent.putByte(array, index + 1, (byte)(value >>> 16));
            PlatformDependent.putByte(array, index + 2, (byte)(value >>> 8));
            PlatformDependent.putByte(array, index + 3, (byte)value);
        }
    }

    static void setIntLE(byte[] array, int index, int value) {
        if (UNALIGNED) {
            PlatformDependent.putInt(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Integer.reverseBytes(value) : value);
        } else {
            PlatformDependent.putByte(array, index, (byte)value);
            PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
            PlatformDependent.putByte(array, index + 2, (byte)(value >>> 16));
            PlatformDependent.putByte(array, index + 3, (byte)(value >>> 24));
        }
    }

    static void setLong(byte[] array, int index, long value) {
        if (UNALIGNED) {
            PlatformDependent.putLong(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? value : Long.reverseBytes(value));
        } else {
            PlatformDependent.putByte(array, index, (byte)(value >>> 56));
            PlatformDependent.putByte(array, index + 1, (byte)(value >>> 48));
            PlatformDependent.putByte(array, index + 2, (byte)(value >>> 40));
            PlatformDependent.putByte(array, index + 3, (byte)(value >>> 32));
            PlatformDependent.putByte(array, index + 4, (byte)(value >>> 24));
            PlatformDependent.putByte(array, index + 5, (byte)(value >>> 16));
            PlatformDependent.putByte(array, index + 6, (byte)(value >>> 8));
            PlatformDependent.putByte(array, index + 7, (byte)value);
        }
    }

    static void setLongLE(byte[] array, int index, long value) {
        if (UNALIGNED) {
            PlatformDependent.putLong(array, index, PlatformDependent.BIG_ENDIAN_NATIVE_ORDER ? Long.reverseBytes(value) : value);
        } else {
            PlatformDependent.putByte(array, index, (byte)value);
            PlatformDependent.putByte(array, index + 1, (byte)(value >>> 8));
            PlatformDependent.putByte(array, index + 2, (byte)(value >>> 16));
            PlatformDependent.putByte(array, index + 3, (byte)(value >>> 24));
            PlatformDependent.putByte(array, index + 4, (byte)(value >>> 32));
            PlatformDependent.putByte(array, index + 5, (byte)(value >>> 40));
            PlatformDependent.putByte(array, index + 6, (byte)(value >>> 48));
            PlatformDependent.putByte(array, index + 7, (byte)(value >>> 56));
        }
    }

    static void setZero(byte[] array, int index, int length) {
        if (length == 0) {
            return;
        }
        PlatformDependent.setMemory(array, index, length, (byte)0);
    }

    static ByteBuf copy(AbstractByteBuf buf, long addr, int index, int length) {
        buf.checkIndex(index, length);
        ByteBuf copy = buf.alloc().directBuffer(length, buf.maxCapacity());
        if (length != 0) {
            if (copy.hasMemoryAddress()) {
                PlatformDependent.copyMemory(addr, copy.memoryAddress(), length);
                copy.setIndex(0, length);
            } else {
                copy.writeBytes(buf, index, length);
            }
        }
        return copy;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static int setBytes(AbstractByteBuf buf, long addr, int index, InputStream in, int length) throws IOException {
        buf.checkIndex(index, length);
        ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
        try {
            byte[] tmp = tmpBuf.array();
            int offset = tmpBuf.arrayOffset();
            int readBytes = in.read(tmp, offset, length);
            if (readBytes > 0) {
                PlatformDependent.copyMemory(tmp, offset, addr, (long)readBytes);
            }
            int n = readBytes;
            return n;
        } finally {
            tmpBuf.release();
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, ByteBuf dst, int dstIndex, int length) {
        buf.checkIndex(index, length);
        ObjectUtil.checkNotNull(dst, "dst");
        if (MathUtil.isOutOfBounds(dstIndex, length, dst.capacity())) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        }
        if (dst.hasMemoryAddress()) {
            PlatformDependent.copyMemory(addr, dst.memoryAddress() + (long)dstIndex, length);
        } else if (dst.hasArray()) {
            PlatformDependent.copyMemory(addr, dst.array(), dst.arrayOffset() + dstIndex, (long)length);
        } else {
            dst.setBytes(dstIndex, buf, index, length);
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, byte[] dst, int dstIndex, int length) {
        buf.checkIndex(index, length);
        ObjectUtil.checkNotNull(dst, "dst");
        if (MathUtil.isOutOfBounds(dstIndex, length, dst.length)) {
            throw new IndexOutOfBoundsException("dstIndex: " + dstIndex);
        }
        if (length != 0) {
            PlatformDependent.copyMemory(addr, dst, dstIndex, (long)length);
        }
    }

    static void getBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer dst) {
        buf.checkIndex(index, dst.remaining());
        if (dst.remaining() == 0) {
            return;
        }
        if (dst.isDirect()) {
            if (dst.isReadOnly()) {
                throw new ReadOnlyBufferException();
            }
            long dstAddress = PlatformDependent.directBufferAddress(dst);
            PlatformDependent.copyMemory(addr, dstAddress + (long)dst.position(), dst.remaining());
            dst.position(dst.position() + dst.remaining());
        } else if (dst.hasArray()) {
            PlatformDependent.copyMemory(addr, dst.array(), dst.arrayOffset() + dst.position(), (long)dst.remaining());
            dst.position(dst.position() + dst.remaining());
        } else {
            dst.put(buf.nioBuffer());
        }
    }

    static void setBytes(AbstractByteBuf buf, long addr, int index, ByteBuf src, int srcIndex, int length) {
        buf.checkIndex(index, length);
        ObjectUtil.checkNotNull(src, "src");
        if (MathUtil.isOutOfBounds(srcIndex, length, src.capacity())) {
            throw new IndexOutOfBoundsException("srcIndex: " + srcIndex);
        }
        if (length != 0) {
            if (src.hasMemoryAddress()) {
                PlatformDependent.copyMemory(src.memoryAddress() + (long)srcIndex, addr, length);
            } else if (src.hasArray()) {
                PlatformDependent.copyMemory(src.array(), src.arrayOffset() + srcIndex, addr, (long)length);
            } else {
                src.getBytes(srcIndex, buf, index, length);
            }
        }
    }

    static void setBytes(AbstractByteBuf buf, long addr, int index, byte[] src, int srcIndex, int length) {
        buf.checkIndex(index, length);
        if (length != 0) {
            PlatformDependent.copyMemory(src, srcIndex, addr, (long)length);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void setBytes(AbstractByteBuf buf, long addr, int index, ByteBuffer src) {
        buf.checkIndex(index, src.remaining());
        int length = src.remaining();
        if (length == 0) {
            return;
        }
        if (src.isDirect()) {
            long srcAddress = PlatformDependent.directBufferAddress(src);
            PlatformDependent.copyMemory(srcAddress + (long)src.position(), addr, src.remaining());
            src.position(src.position() + length);
        } else if (src.hasArray()) {
            PlatformDependent.copyMemory(src.array(), src.arrayOffset() + src.position(), addr, (long)length);
            src.position(src.position() + length);
        } else {
            ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
            try {
                byte[] tmp = tmpBuf.array();
                src.get(tmp, tmpBuf.arrayOffset(), length);
                PlatformDependent.copyMemory(tmp, tmpBuf.arrayOffset(), addr, (long)length);
            } finally {
                tmpBuf.release();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void getBytes(AbstractByteBuf buf, long addr, int index, OutputStream out, int length) throws IOException {
        buf.checkIndex(index, length);
        if (length != 0) {
            ByteBuf tmpBuf = buf.alloc().heapBuffer(length);
            try {
                byte[] tmp = tmpBuf.array();
                int offset = tmpBuf.arrayOffset();
                PlatformDependent.copyMemory(addr, tmp, offset, (long)length);
                out.write(tmp, offset, length);
            } finally {
                tmpBuf.release();
            }
        }
    }

    static void setZero(AbstractByteBuf buf, long addr, int index, int length) {
        if (length == 0) {
            return;
        }
        buf.checkIndex(index, length);
        PlatformDependent.setMemory(addr, length, (byte)0);
    }

    static UnpooledUnsafeDirectByteBuf newUnsafeDirectByteBuf(ByteBufAllocator alloc, int initialCapacity, int maxCapacity) {
        if (PlatformDependent.useDirectBufferNoCleaner()) {
            return new UnpooledUnsafeNoCleanerDirectByteBuf(alloc, initialCapacity, maxCapacity);
        }
        return new UnpooledUnsafeDirectByteBuf(alloc, initialCapacity, maxCapacity);
    }

    private UnsafeByteBufUtil() {
    }
}

