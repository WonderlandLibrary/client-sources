/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

final class HeapByteBufUtil {
    static byte getByte(byte[] memory, int index) {
        return memory[index];
    }

    static short getShort(byte[] memory, int index) {
        return (short)(memory[index] << 8 | memory[index + 1] & 0xFF);
    }

    static short getShortLE(byte[] memory, int index) {
        return (short)(memory[index] & 0xFF | memory[index + 1] << 8);
    }

    static int getUnsignedMedium(byte[] memory, int index) {
        return (memory[index] & 0xFF) << 16 | (memory[index + 1] & 0xFF) << 8 | memory[index + 2] & 0xFF;
    }

    static int getUnsignedMediumLE(byte[] memory, int index) {
        return memory[index] & 0xFF | (memory[index + 1] & 0xFF) << 8 | (memory[index + 2] & 0xFF) << 16;
    }

    static int getInt(byte[] memory, int index) {
        return (memory[index] & 0xFF) << 24 | (memory[index + 1] & 0xFF) << 16 | (memory[index + 2] & 0xFF) << 8 | memory[index + 3] & 0xFF;
    }

    static int getIntLE(byte[] memory, int index) {
        return memory[index] & 0xFF | (memory[index + 1] & 0xFF) << 8 | (memory[index + 2] & 0xFF) << 16 | (memory[index + 3] & 0xFF) << 24;
    }

    static long getLong(byte[] memory, int index) {
        return ((long)memory[index] & 0xFFL) << 56 | ((long)memory[index + 1] & 0xFFL) << 48 | ((long)memory[index + 2] & 0xFFL) << 40 | ((long)memory[index + 3] & 0xFFL) << 32 | ((long)memory[index + 4] & 0xFFL) << 24 | ((long)memory[index + 5] & 0xFFL) << 16 | ((long)memory[index + 6] & 0xFFL) << 8 | (long)memory[index + 7] & 0xFFL;
    }

    static long getLongLE(byte[] memory, int index) {
        return (long)memory[index] & 0xFFL | ((long)memory[index + 1] & 0xFFL) << 8 | ((long)memory[index + 2] & 0xFFL) << 16 | ((long)memory[index + 3] & 0xFFL) << 24 | ((long)memory[index + 4] & 0xFFL) << 32 | ((long)memory[index + 5] & 0xFFL) << 40 | ((long)memory[index + 6] & 0xFFL) << 48 | ((long)memory[index + 7] & 0xFFL) << 56;
    }

    static void setByte(byte[] memory, int index, int value) {
        memory[index] = (byte)value;
    }

    static void setShort(byte[] memory, int index, int value) {
        memory[index] = (byte)(value >>> 8);
        memory[index + 1] = (byte)value;
    }

    static void setShortLE(byte[] memory, int index, int value) {
        memory[index] = (byte)value;
        memory[index + 1] = (byte)(value >>> 8);
    }

    static void setMedium(byte[] memory, int index, int value) {
        memory[index] = (byte)(value >>> 16);
        memory[index + 1] = (byte)(value >>> 8);
        memory[index + 2] = (byte)value;
    }

    static void setMediumLE(byte[] memory, int index, int value) {
        memory[index] = (byte)value;
        memory[index + 1] = (byte)(value >>> 8);
        memory[index + 2] = (byte)(value >>> 16);
    }

    static void setInt(byte[] memory, int index, int value) {
        memory[index] = (byte)(value >>> 24);
        memory[index + 1] = (byte)(value >>> 16);
        memory[index + 2] = (byte)(value >>> 8);
        memory[index + 3] = (byte)value;
    }

    static void setIntLE(byte[] memory, int index, int value) {
        memory[index] = (byte)value;
        memory[index + 1] = (byte)(value >>> 8);
        memory[index + 2] = (byte)(value >>> 16);
        memory[index + 3] = (byte)(value >>> 24);
    }

    static void setLong(byte[] memory, int index, long value) {
        memory[index] = (byte)(value >>> 56);
        memory[index + 1] = (byte)(value >>> 48);
        memory[index + 2] = (byte)(value >>> 40);
        memory[index + 3] = (byte)(value >>> 32);
        memory[index + 4] = (byte)(value >>> 24);
        memory[index + 5] = (byte)(value >>> 16);
        memory[index + 6] = (byte)(value >>> 8);
        memory[index + 7] = (byte)value;
    }

    static void setLongLE(byte[] memory, int index, long value) {
        memory[index] = (byte)value;
        memory[index + 1] = (byte)(value >>> 8);
        memory[index + 2] = (byte)(value >>> 16);
        memory[index + 3] = (byte)(value >>> 24);
        memory[index + 4] = (byte)(value >>> 32);
        memory[index + 5] = (byte)(value >>> 40);
        memory[index + 6] = (byte)(value >>> 48);
        memory[index + 7] = (byte)(value >>> 56);
    }

    private HeapByteBufUtil() {
    }
}

