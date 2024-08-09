/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

final class HeapByteBufUtil {
    static byte getByte(byte[] byArray, int n) {
        return byArray[n];
    }

    static short getShort(byte[] byArray, int n) {
        return (short)(byArray[n] << 8 | byArray[n + 1] & 0xFF);
    }

    static short getShortLE(byte[] byArray, int n) {
        return (short)(byArray[n] & 0xFF | byArray[n + 1] << 8);
    }

    static int getUnsignedMedium(byte[] byArray, int n) {
        return (byArray[n] & 0xFF) << 16 | (byArray[n + 1] & 0xFF) << 8 | byArray[n + 2] & 0xFF;
    }

    static int getUnsignedMediumLE(byte[] byArray, int n) {
        return byArray[n] & 0xFF | (byArray[n + 1] & 0xFF) << 8 | (byArray[n + 2] & 0xFF) << 16;
    }

    static int getInt(byte[] byArray, int n) {
        return (byArray[n] & 0xFF) << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
    }

    static int getIntLE(byte[] byArray, int n) {
        return byArray[n] & 0xFF | (byArray[n + 1] & 0xFF) << 8 | (byArray[n + 2] & 0xFF) << 16 | (byArray[n + 3] & 0xFF) << 24;
    }

    static long getLong(byte[] byArray, int n) {
        return ((long)byArray[n] & 0xFFL) << 56 | ((long)byArray[n + 1] & 0xFFL) << 48 | ((long)byArray[n + 2] & 0xFFL) << 40 | ((long)byArray[n + 3] & 0xFFL) << 32 | ((long)byArray[n + 4] & 0xFFL) << 24 | ((long)byArray[n + 5] & 0xFFL) << 16 | ((long)byArray[n + 6] & 0xFFL) << 8 | (long)byArray[n + 7] & 0xFFL;
    }

    static long getLongLE(byte[] byArray, int n) {
        return (long)byArray[n] & 0xFFL | ((long)byArray[n + 1] & 0xFFL) << 8 | ((long)byArray[n + 2] & 0xFFL) << 16 | ((long)byArray[n + 3] & 0xFFL) << 24 | ((long)byArray[n + 4] & 0xFFL) << 32 | ((long)byArray[n + 5] & 0xFFL) << 40 | ((long)byArray[n + 6] & 0xFFL) << 48 | ((long)byArray[n + 7] & 0xFFL) << 56;
    }

    static void setByte(byte[] byArray, int n, int n2) {
        byArray[n] = (byte)n2;
    }

    static void setShort(byte[] byArray, int n, int n2) {
        byArray[n] = (byte)(n2 >>> 8);
        byArray[n + 1] = (byte)n2;
    }

    static void setShortLE(byte[] byArray, int n, int n2) {
        byArray[n] = (byte)n2;
        byArray[n + 1] = (byte)(n2 >>> 8);
    }

    static void setMedium(byte[] byArray, int n, int n2) {
        byArray[n] = (byte)(n2 >>> 16);
        byArray[n + 1] = (byte)(n2 >>> 8);
        byArray[n + 2] = (byte)n2;
    }

    static void setMediumLE(byte[] byArray, int n, int n2) {
        byArray[n] = (byte)n2;
        byArray[n + 1] = (byte)(n2 >>> 8);
        byArray[n + 2] = (byte)(n2 >>> 16);
    }

    static void setInt(byte[] byArray, int n, int n2) {
        byArray[n] = (byte)(n2 >>> 24);
        byArray[n + 1] = (byte)(n2 >>> 16);
        byArray[n + 2] = (byte)(n2 >>> 8);
        byArray[n + 3] = (byte)n2;
    }

    static void setIntLE(byte[] byArray, int n, int n2) {
        byArray[n] = (byte)n2;
        byArray[n + 1] = (byte)(n2 >>> 8);
        byArray[n + 2] = (byte)(n2 >>> 16);
        byArray[n + 3] = (byte)(n2 >>> 24);
    }

    static void setLong(byte[] byArray, int n, long l) {
        byArray[n] = (byte)(l >>> 56);
        byArray[n + 1] = (byte)(l >>> 48);
        byArray[n + 2] = (byte)(l >>> 40);
        byArray[n + 3] = (byte)(l >>> 32);
        byArray[n + 4] = (byte)(l >>> 24);
        byArray[n + 5] = (byte)(l >>> 16);
        byArray[n + 6] = (byte)(l >>> 8);
        byArray[n + 7] = (byte)l;
    }

    static void setLongLE(byte[] byArray, int n, long l) {
        byArray[n] = (byte)l;
        byArray[n + 1] = (byte)(l >>> 8);
        byArray[n + 2] = (byte)(l >>> 16);
        byArray[n + 3] = (byte)(l >>> 24);
        byArray[n + 4] = (byte)(l >>> 32);
        byArray[n + 5] = (byte)(l >>> 40);
        byArray[n + 6] = (byte)(l >>> 48);
        byArray[n + 7] = (byte)(l >>> 56);
    }

    private HeapByteBufUtil() {
    }
}

