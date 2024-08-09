/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;

class Bzip2BitReader {
    private static final int MAX_COUNT_OF_READABLE_BYTES = 0xFFFFFFF;
    private ByteBuf in;
    private long bitBuffer;
    private int bitCount;

    Bzip2BitReader() {
    }

    void setByteBuf(ByteBuf byteBuf) {
        this.in = byteBuf;
    }

    int readBits(int n) {
        if (n < 0 || n > 32) {
            throw new IllegalArgumentException("count: " + n + " (expected: 0-32 )");
        }
        int n2 = this.bitCount;
        long l = this.bitBuffer;
        if (n2 < n) {
            int n3;
            long l2;
            switch (this.in.readableBytes()) {
                case 1: {
                    l2 = this.in.readUnsignedByte();
                    n3 = 8;
                    break;
                }
                case 2: {
                    l2 = this.in.readUnsignedShort();
                    n3 = 16;
                    break;
                }
                case 3: {
                    l2 = this.in.readUnsignedMedium();
                    n3 = 24;
                    break;
                }
                default: {
                    l2 = this.in.readUnsignedInt();
                    n3 = 32;
                }
            }
            l = l << n3 | l2;
            n2 += n3;
            this.bitBuffer = l;
        }
        this.bitCount = n2 -= n;
        return (int)(l >>> n2 & (n != 32 ? (long)((1 << n) - 1) : 0xFFFFFFFFL));
    }

    boolean readBoolean() {
        return this.readBits(1) != 0;
    }

    int readInt() {
        return this.readBits(32);
    }

    void refill() {
        short s = this.in.readUnsignedByte();
        this.bitBuffer = this.bitBuffer << 8 | (long)s;
        this.bitCount += 8;
    }

    boolean isReadable() {
        return this.bitCount > 0 || this.in.isReadable();
    }

    boolean hasReadableBits(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("count: " + n + " (expected value greater than 0)");
        }
        return this.bitCount >= n || (this.in.readableBytes() << 3 & Integer.MAX_VALUE) >= n - this.bitCount;
    }

    boolean hasReadableBytes(int n) {
        if (n < 0 || n > 0xFFFFFFF) {
            throw new IllegalArgumentException("count: " + n + " (expected: 0-" + 0xFFFFFFF + ')');
        }
        return this.hasReadableBits(n << 3);
    }
}

