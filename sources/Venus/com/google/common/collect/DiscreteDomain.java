/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.NoSuchElementException;

@GwtCompatible
public abstract class DiscreteDomain<C extends Comparable> {
    public static DiscreteDomain<Integer> integers() {
        return IntegerDomain.access$000();
    }

    public static DiscreteDomain<Long> longs() {
        return LongDomain.access$100();
    }

    public static DiscreteDomain<BigInteger> bigIntegers() {
        return BigIntegerDomain.access$200();
    }

    protected DiscreteDomain() {
    }

    public abstract C next(C var1);

    public abstract C previous(C var1);

    public abstract long distance(C var1, C var2);

    @CanIgnoreReturnValue
    public C minValue() {
        throw new NoSuchElementException();
    }

    @CanIgnoreReturnValue
    public C maxValue() {
        throw new NoSuchElementException();
    }

    private static final class BigIntegerDomain
    extends DiscreteDomain<BigInteger>
    implements Serializable {
        private static final BigIntegerDomain INSTANCE = new BigIntegerDomain();
        private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
        private static final long serialVersionUID = 0L;

        private BigIntegerDomain() {
        }

        @Override
        public BigInteger next(BigInteger bigInteger) {
            return bigInteger.add(BigInteger.ONE);
        }

        @Override
        public BigInteger previous(BigInteger bigInteger) {
            return bigInteger.subtract(BigInteger.ONE);
        }

        @Override
        public long distance(BigInteger bigInteger, BigInteger bigInteger2) {
            return bigInteger2.subtract(bigInteger).max(MIN_LONG).min(MAX_LONG).longValue();
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }

        @Override
        public long distance(Comparable comparable, Comparable comparable2) {
            return this.distance((BigInteger)comparable, (BigInteger)comparable2);
        }

        @Override
        public Comparable previous(Comparable comparable) {
            return this.previous((BigInteger)comparable);
        }

        @Override
        public Comparable next(Comparable comparable) {
            return this.next((BigInteger)comparable);
        }

        static BigIntegerDomain access$200() {
            return INSTANCE;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class LongDomain
    extends DiscreteDomain<Long>
    implements Serializable {
        private static final LongDomain INSTANCE = new LongDomain();
        private static final long serialVersionUID = 0L;

        private LongDomain() {
        }

        @Override
        public Long next(Long l) {
            long l2 = l;
            return l2 == Long.MAX_VALUE ? null : Long.valueOf(l2 + 1L);
        }

        @Override
        public Long previous(Long l) {
            long l2 = l;
            return l2 == Long.MIN_VALUE ? null : Long.valueOf(l2 - 1L);
        }

        @Override
        public long distance(Long l, Long l2) {
            long l3 = l2 - l;
            if (l2 > l && l3 < 0L) {
                return Long.MAX_VALUE;
            }
            if (l2 < l && l3 > 0L) {
                return Long.MIN_VALUE;
            }
            return l3;
        }

        @Override
        public Long minValue() {
            return Long.MIN_VALUE;
        }

        @Override
        public Long maxValue() {
            return Long.MAX_VALUE;
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.longs()";
        }

        @Override
        public Comparable maxValue() {
            return this.maxValue();
        }

        @Override
        public Comparable minValue() {
            return this.minValue();
        }

        @Override
        public long distance(Comparable comparable, Comparable comparable2) {
            return this.distance((Long)comparable, (Long)comparable2);
        }

        @Override
        public Comparable previous(Comparable comparable) {
            return this.previous((Long)comparable);
        }

        @Override
        public Comparable next(Comparable comparable) {
            return this.next((Long)comparable);
        }

        static LongDomain access$100() {
            return INSTANCE;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class IntegerDomain
    extends DiscreteDomain<Integer>
    implements Serializable {
        private static final IntegerDomain INSTANCE = new IntegerDomain();
        private static final long serialVersionUID = 0L;

        private IntegerDomain() {
        }

        @Override
        public Integer next(Integer n) {
            int n2 = n;
            return n2 == Integer.MAX_VALUE ? null : Integer.valueOf(n2 + 1);
        }

        @Override
        public Integer previous(Integer n) {
            int n2 = n;
            return n2 == Integer.MIN_VALUE ? null : Integer.valueOf(n2 - 1);
        }

        @Override
        public long distance(Integer n, Integer n2) {
            return (long)n2.intValue() - (long)n.intValue();
        }

        @Override
        public Integer minValue() {
            return Integer.MIN_VALUE;
        }

        @Override
        public Integer maxValue() {
            return Integer.MAX_VALUE;
        }

        private Object readResolve() {
            return INSTANCE;
        }

        public String toString() {
            return "DiscreteDomain.integers()";
        }

        @Override
        public Comparable maxValue() {
            return this.maxValue();
        }

        @Override
        public Comparable minValue() {
            return this.minValue();
        }

        @Override
        public long distance(Comparable comparable, Comparable comparable2) {
            return this.distance((Integer)comparable, (Integer)comparable2);
        }

        @Override
        public Comparable previous(Comparable comparable) {
            return this.previous((Integer)comparable);
        }

        @Override
        public Comparable next(Comparable comparable) {
            return this.next((Integer)comparable);
        }

        static IntegerDomain access$000() {
            return INSTANCE;
        }
    }
}

