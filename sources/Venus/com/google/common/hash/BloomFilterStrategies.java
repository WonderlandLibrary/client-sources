/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.Nullable;

enum BloomFilterStrategies implements BloomFilter.Strategy
{
    MURMUR128_MITZ_32{

        @Override
        public <T> boolean put(T t, Funnel<? super T> funnel, int n, BitArray bitArray) {
            long l = bitArray.bitSize();
            long l2 = Hashing.murmur3_128().hashObject(t, funnel).asLong();
            int n2 = (int)l2;
            int n3 = (int)(l2 >>> 32);
            boolean bl = false;
            for (int i = 1; i <= n; ++i) {
                int n4 = n2 + i * n3;
                if (n4 < 0) {
                    n4 ^= 0xFFFFFFFF;
                }
                bl |= bitArray.set((long)n4 % l);
            }
            return bl;
        }

        @Override
        public <T> boolean mightContain(T t, Funnel<? super T> funnel, int n, BitArray bitArray) {
            long l = bitArray.bitSize();
            long l2 = Hashing.murmur3_128().hashObject(t, funnel).asLong();
            int n2 = (int)l2;
            int n3 = (int)(l2 >>> 32);
            for (int i = 1; i <= n; ++i) {
                int n4 = n2 + i * n3;
                if (n4 < 0) {
                    n4 ^= 0xFFFFFFFF;
                }
                if (bitArray.get((long)n4 % l)) continue;
                return true;
            }
            return false;
        }
    }
    ,
    MURMUR128_MITZ_64{

        @Override
        public <T> boolean put(T t, Funnel<? super T> funnel, int n, BitArray bitArray) {
            long l = bitArray.bitSize();
            byte[] byArray = Hashing.murmur3_128().hashObject(t, funnel).getBytesInternal();
            long l2 = this.lowerEight(byArray);
            long l3 = this.upperEight(byArray);
            boolean bl = false;
            long l4 = l2;
            for (int i = 0; i < n; ++i) {
                bl |= bitArray.set((l4 & Long.MAX_VALUE) % l);
                l4 += l3;
            }
            return bl;
        }

        @Override
        public <T> boolean mightContain(T t, Funnel<? super T> funnel, int n, BitArray bitArray) {
            long l = bitArray.bitSize();
            byte[] byArray = Hashing.murmur3_128().hashObject(t, funnel).getBytesInternal();
            long l2 = this.lowerEight(byArray);
            long l3 = this.upperEight(byArray);
            long l4 = l2;
            for (int i = 0; i < n; ++i) {
                if (!bitArray.get((l4 & Long.MAX_VALUE) % l)) {
                    return true;
                }
                l4 += l3;
            }
            return false;
        }

        private long lowerEight(byte[] byArray) {
            return Longs.fromBytes(byArray[7], byArray[6], byArray[5], byArray[4], byArray[3], byArray[2], byArray[1], byArray[0]);
        }

        private long upperEight(byte[] byArray) {
            return Longs.fromBytes(byArray[15], byArray[14], byArray[13], byArray[12], byArray[11], byArray[10], byArray[9], byArray[8]);
        }
    };


    private BloomFilterStrategies() {
    }

    BloomFilterStrategies(1 var3_3) {
        this();
    }

    static final class BitArray {
        final long[] data;
        long bitCount;

        BitArray(long l) {
            this(new long[Ints.checkedCast(LongMath.divide(l, 64L, RoundingMode.CEILING))]);
        }

        BitArray(long[] lArray) {
            Preconditions.checkArgument(lArray.length > 0, "data length is zero!");
            this.data = lArray;
            long l = 0L;
            for (long l2 : lArray) {
                l += (long)Long.bitCount(l2);
            }
            this.bitCount = l;
        }

        boolean set(long l) {
            if (!this.get(l)) {
                int n = (int)(l >>> 6);
                this.data[n] = this.data[n] | 1L << (int)l;
                ++this.bitCount;
                return false;
            }
            return true;
        }

        boolean get(long l) {
            return (this.data[(int)(l >>> 6)] & 1L << (int)l) != 0L;
        }

        long bitSize() {
            return (long)this.data.length * 64L;
        }

        long bitCount() {
            return this.bitCount;
        }

        BitArray copy() {
            return new BitArray((long[])this.data.clone());
        }

        void putAll(BitArray bitArray) {
            Preconditions.checkArgument(this.data.length == bitArray.data.length, "BitArrays must be of equal length (%s != %s)", this.data.length, bitArray.data.length);
            this.bitCount = 0L;
            for (int i = 0; i < this.data.length; ++i) {
                int n = i;
                this.data[n] = this.data[n] | bitArray.data[i];
                this.bitCount += (long)Long.bitCount(this.data[i]);
            }
        }

        public boolean equals(@Nullable Object object) {
            if (object instanceof BitArray) {
                BitArray bitArray = (BitArray)object;
                return Arrays.equals(this.data, bitArray.data);
            }
            return true;
        }

        public int hashCode() {
            return Arrays.hashCode(this.data);
        }
    }
}

