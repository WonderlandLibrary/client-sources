/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.hash.BloomFilterStrategies;
import com.google.common.hash.Funnel;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.annotation.Nullable;

@Beta
public final class BloomFilter<T>
implements Predicate<T>,
Serializable {
    private final BloomFilterStrategies.BitArray bits;
    private final int numHashFunctions;
    private final Funnel<? super T> funnel;
    private final Strategy strategy;

    private BloomFilter(BloomFilterStrategies.BitArray bitArray, int n, Funnel<? super T> funnel, Strategy strategy) {
        Preconditions.checkArgument(n > 0, "numHashFunctions (%s) must be > 0", n);
        Preconditions.checkArgument(n <= 255, "numHashFunctions (%s) must be <= 255", n);
        this.bits = Preconditions.checkNotNull(bitArray);
        this.numHashFunctions = n;
        this.funnel = Preconditions.checkNotNull(funnel);
        this.strategy = Preconditions.checkNotNull(strategy);
    }

    public BloomFilter<T> copy() {
        return new BloomFilter<T>(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
    }

    public boolean mightContain(T t) {
        return this.strategy.mightContain(t, this.funnel, this.numHashFunctions, this.bits);
    }

    @Override
    @Deprecated
    public boolean apply(T t) {
        return this.mightContain(t);
    }

    @CanIgnoreReturnValue
    public boolean put(T t) {
        return this.strategy.put(t, this.funnel, this.numHashFunctions, this.bits);
    }

    public double expectedFpp() {
        return Math.pow((double)this.bits.bitCount() / (double)this.bitSize(), this.numHashFunctions);
    }

    @VisibleForTesting
    long bitSize() {
        return this.bits.bitSize();
    }

    public boolean isCompatible(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        return this != bloomFilter && this.numHashFunctions == bloomFilter.numHashFunctions && this.bitSize() == bloomFilter.bitSize() && this.strategy.equals(bloomFilter.strategy) && this.funnel.equals(bloomFilter.funnel);
    }

    public void putAll(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        Preconditions.checkArgument(this != bloomFilter, "Cannot combine a BloomFilter with itself.");
        Preconditions.checkArgument(this.numHashFunctions == bloomFilter.numHashFunctions, "BloomFilters must have the same number of hash functions (%s != %s)", this.numHashFunctions, bloomFilter.numHashFunctions);
        Preconditions.checkArgument(this.bitSize() == bloomFilter.bitSize(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", this.bitSize(), bloomFilter.bitSize());
        Preconditions.checkArgument(this.strategy.equals(bloomFilter.strategy), "BloomFilters must have equal strategies (%s != %s)", (Object)this.strategy, (Object)bloomFilter.strategy);
        Preconditions.checkArgument(this.funnel.equals(bloomFilter.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, bloomFilter.funnel);
        this.bits.putAll(bloomFilter.bits);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof BloomFilter) {
            BloomFilter bloomFilter = (BloomFilter)object;
            return this.numHashFunctions == bloomFilter.numHashFunctions && this.funnel.equals(bloomFilter.funnel) && this.bits.equals(bloomFilter.bits) && this.strategy.equals(bloomFilter.strategy);
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.numHashFunctions, this.funnel, this.strategy, this.bits);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int n, double d) {
        return BloomFilter.create(funnel, (long)n, d);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long l, double d) {
        return BloomFilter.create(funnel, l, d, BloomFilterStrategies.MURMUR128_MITZ_64);
    }

    @VisibleForTesting
    static <T> BloomFilter<T> create(Funnel<? super T> funnel, long l, double d, Strategy strategy) {
        Preconditions.checkNotNull(funnel);
        Preconditions.checkArgument(l >= 0L, "Expected insertions (%s) must be >= 0", l);
        Preconditions.checkArgument(d > 0.0, "False positive probability (%s) must be > 0.0", (Object)d);
        Preconditions.checkArgument(d < 1.0, "False positive probability (%s) must be < 1.0", (Object)d);
        Preconditions.checkNotNull(strategy);
        if (l == 0L) {
            l = 1L;
        }
        long l2 = BloomFilter.optimalNumOfBits(l, d);
        int n = BloomFilter.optimalNumOfHashFunctions(l, l2);
        try {
            return new BloomFilter<T>(new BloomFilterStrategies.BitArray(l2), n, funnel, strategy);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Could not create BloomFilter of " + l2 + " bits", illegalArgumentException);
        }
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int n) {
        return BloomFilter.create(funnel, (long)n);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long l) {
        return BloomFilter.create(funnel, l, 0.03);
    }

    @VisibleForTesting
    static int optimalNumOfHashFunctions(long l, long l2) {
        return Math.max(1, (int)Math.round((double)l2 / (double)l * Math.log(2.0)));
    }

    @VisibleForTesting
    static long optimalNumOfBits(long l, double d) {
        if (d == 0.0) {
            d = Double.MIN_VALUE;
        }
        return (long)((double)(-l) * Math.log(d) / (Math.log(2.0) * Math.log(2.0)));
    }

    private Object writeReplace() {
        return new SerialForm(this);
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeByte(SignedBytes.checkedCast(this.strategy.ordinal()));
        dataOutputStream.writeByte(UnsignedBytes.checkedCast(this.numHashFunctions));
        dataOutputStream.writeInt(this.bits.data.length);
        for (long l : this.bits.data) {
            dataOutputStream.writeLong(l);
        }
    }

    public static <T> BloomFilter<T> readFrom(InputStream inputStream, Funnel<T> funnel) throws IOException {
        Preconditions.checkNotNull(inputStream, "InputStream");
        Preconditions.checkNotNull(funnel, "Funnel");
        int n = -1;
        int n2 = -1;
        int n3 = -1;
        try {
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            n = dataInputStream.readByte();
            n2 = UnsignedBytes.toInt(dataInputStream.readByte());
            n3 = dataInputStream.readInt();
            BloomFilterStrategies bloomFilterStrategies = BloomFilterStrategies.values()[n];
            long[] lArray = new long[n3];
            for (int i = 0; i < lArray.length; ++i) {
                lArray[i] = dataInputStream.readLong();
            }
            return new BloomFilter<T>(new BloomFilterStrategies.BitArray(lArray), n2, funnel, bloomFilterStrategies);
        } catch (RuntimeException runtimeException) {
            String string = "Unable to deserialize BloomFilter from InputStream. strategyOrdinal: " + n + " numHashFunctions: " + n2 + " dataLength: " + n3;
            throw new IOException(string, runtimeException);
        }
    }

    static BloomFilterStrategies.BitArray access$000(BloomFilter bloomFilter) {
        return bloomFilter.bits;
    }

    static int access$100(BloomFilter bloomFilter) {
        return bloomFilter.numHashFunctions;
    }

    static Funnel access$200(BloomFilter bloomFilter) {
        return bloomFilter.funnel;
    }

    static Strategy access$300(BloomFilter bloomFilter) {
        return bloomFilter.strategy;
    }

    BloomFilter(BloomFilterStrategies.BitArray bitArray, int n, Funnel funnel, Strategy strategy, 1 var5_5) {
        this(bitArray, n, funnel, strategy);
    }

    private static class SerialForm<T>
    implements Serializable {
        final long[] data;
        final int numHashFunctions;
        final Funnel<? super T> funnel;
        final Strategy strategy;
        private static final long serialVersionUID = 1L;

        SerialForm(BloomFilter<T> bloomFilter) {
            this.data = BloomFilter.access$000(bloomFilter).data;
            this.numHashFunctions = BloomFilter.access$100(bloomFilter);
            this.funnel = BloomFilter.access$200(bloomFilter);
            this.strategy = BloomFilter.access$300(bloomFilter);
        }

        Object readResolve() {
            return new BloomFilter(new BloomFilterStrategies.BitArray(this.data), this.numHashFunctions, this.funnel, this.strategy, null);
        }
    }

    static interface Strategy
    extends Serializable {
        public <T> boolean put(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.BitArray var4);

        public <T> boolean mightContain(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.BitArray var4);

        public int ordinal();
    }
}

