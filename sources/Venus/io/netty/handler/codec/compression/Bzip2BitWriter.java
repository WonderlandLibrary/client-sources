/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;

final class Bzip2BitWriter {
    private long bitBuffer;
    private int bitCount;

    Bzip2BitWriter() {
    }

    void writeBits(ByteBuf byteBuf, int n, long l) {
        if (n < 0 || n > 32) {
            throw new IllegalArgumentException("count: " + n + " (expected: 0-32)");
        }
        int n2 = this.bitCount;
        long l2 = this.bitBuffer | l << 64 - n >>> n2;
        if ((n2 += n) >= 32) {
            byteBuf.writeInt((int)(l2 >>> 32));
            l2 <<= 32;
            n2 -= 32;
        }
        this.bitBuffer = l2;
        this.bitCount = n2;
    }

    void writeBoolean(ByteBuf byteBuf, boolean bl) {
        int n = this.bitCount + 1;
        long l = this.bitBuffer | (bl ? 1L << 64 - n : 0L);
        if (n == 32) {
            byteBuf.writeInt((int)(l >>> 32));
            l = 0L;
            n = 0;
        }
        this.bitBuffer = l;
        this.bitCount = n;
    }

    void writeUnary(ByteBuf byteBuf, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("value: " + n + " (expected 0 or more)");
        }
        while (n-- > 0) {
            this.writeBoolean(byteBuf, false);
        }
        this.writeBoolean(byteBuf, true);
    }

    void writeInt(ByteBuf byteBuf, int n) {
        this.writeBits(byteBuf, 32, n);
    }

    void flush(ByteBuf byteBuf) {
        int n = this.bitCount;
        if (n > 0) {
            long l = this.bitBuffer;
            int n2 = 64 - n;
            if (n <= 8) {
                byteBuf.writeByte((int)(l >>> n2 << 8 - n));
            } else if (n <= 16) {
                byteBuf.writeShort((int)(l >>> n2 << 16 - n));
            } else if (n <= 24) {
                byteBuf.writeMedium((int)(l >>> n2 << 24 - n));
            } else {
                byteBuf.writeInt((int)(l >>> n2 << 32 - n));
            }
        }
    }
}

