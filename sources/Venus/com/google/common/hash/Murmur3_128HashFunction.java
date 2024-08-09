/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.primitives.UnsignedBytes;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.annotation.Nullable;

final class Murmur3_128HashFunction
extends AbstractStreamingHashFunction
implements Serializable {
    private final int seed;
    private static final long serialVersionUID = 0L;

    Murmur3_128HashFunction(int n) {
        this.seed = n;
    }

    @Override
    public int bits() {
        return 1;
    }

    @Override
    public Hasher newHasher() {
        return new Murmur3_128Hasher(this.seed);
    }

    public String toString() {
        return "Hashing.murmur3_128(" + this.seed + ")";
    }

    public boolean equals(@Nullable Object object) {
        if (object instanceof Murmur3_128HashFunction) {
            Murmur3_128HashFunction murmur3_128HashFunction = (Murmur3_128HashFunction)object;
            return this.seed == murmur3_128HashFunction.seed;
        }
        return true;
    }

    public int hashCode() {
        return this.getClass().hashCode() ^ this.seed;
    }

    private static final class Murmur3_128Hasher
    extends AbstractStreamingHashFunction.AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 16;
        private static final long C1 = -8663945395140668459L;
        private static final long C2 = 5545529020109919103L;
        private long h1;
        private long h2;
        private int length;

        Murmur3_128Hasher(int n) {
            super(16);
            this.h1 = n;
            this.h2 = n;
            this.length = 0;
        }

        @Override
        protected void process(ByteBuffer byteBuffer) {
            long l = byteBuffer.getLong();
            long l2 = byteBuffer.getLong();
            this.bmix64(l, l2);
            this.length += 16;
        }

        private void bmix64(long l, long l2) {
            this.h1 ^= Murmur3_128Hasher.mixK1(l);
            this.h1 = Long.rotateLeft(this.h1, 27);
            this.h1 += this.h2;
            this.h1 = this.h1 * 5L + 1390208809L;
            this.h2 ^= Murmur3_128Hasher.mixK2(l2);
            this.h2 = Long.rotateLeft(this.h2, 31);
            this.h2 += this.h1;
            this.h2 = this.h2 * 5L + 944331445L;
        }

        @Override
        protected void processRemaining(ByteBuffer byteBuffer) {
            long l = 0L;
            long l2 = 0L;
            this.length += byteBuffer.remaining();
            switch (byteBuffer.remaining()) {
                case 15: {
                    l2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(14)) << 48;
                }
                case 14: {
                    l2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(13)) << 40;
                }
                case 13: {
                    l2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(12)) << 32;
                }
                case 12: {
                    l2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(11)) << 24;
                }
                case 11: {
                    l2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(10)) << 16;
                }
                case 10: {
                    l2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(9)) << 8;
                }
                case 9: {
                    l2 ^= (long)UnsignedBytes.toInt(byteBuffer.get(8));
                }
                case 8: {
                    l ^= byteBuffer.getLong();
                    break;
                }
                case 7: {
                    l ^= (long)UnsignedBytes.toInt(byteBuffer.get(6)) << 48;
                }
                case 6: {
                    l ^= (long)UnsignedBytes.toInt(byteBuffer.get(5)) << 40;
                }
                case 5: {
                    l ^= (long)UnsignedBytes.toInt(byteBuffer.get(4)) << 32;
                }
                case 4: {
                    l ^= (long)UnsignedBytes.toInt(byteBuffer.get(3)) << 24;
                }
                case 3: {
                    l ^= (long)UnsignedBytes.toInt(byteBuffer.get(2)) << 16;
                }
                case 2: {
                    l ^= (long)UnsignedBytes.toInt(byteBuffer.get(1)) << 8;
                }
                case 1: {
                    l ^= (long)UnsignedBytes.toInt(byteBuffer.get(0));
                    break;
                }
                default: {
                    throw new AssertionError((Object)"Should never get here.");
                }
            }
            this.h1 ^= Murmur3_128Hasher.mixK1(l);
            this.h2 ^= Murmur3_128Hasher.mixK2(l2);
        }

        @Override
        public HashCode makeHash() {
            this.h1 ^= (long)this.length;
            this.h2 ^= (long)this.length;
            this.h1 += this.h2;
            this.h2 += this.h1;
            this.h1 = Murmur3_128Hasher.fmix64(this.h1);
            this.h2 = Murmur3_128Hasher.fmix64(this.h2);
            this.h1 += this.h2;
            this.h2 += this.h1;
            return HashCode.fromBytesNoCopy(ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.h1).putLong(this.h2).array());
        }

        private static long fmix64(long l) {
            l ^= l >>> 33;
            l *= -49064778989728563L;
            l ^= l >>> 33;
            l *= -4265267296055464877L;
            l ^= l >>> 33;
            return l;
        }

        private static long mixK1(long l) {
            l *= -8663945395140668459L;
            l = Long.rotateLeft(l, 31);
            return l *= 5545529020109919103L;
        }

        private static long mixK2(long l) {
            l *= 5545529020109919103L;
            l = Long.rotateLeft(l, 33);
            return l *= -8663945395140668459L;
        }
    }
}

