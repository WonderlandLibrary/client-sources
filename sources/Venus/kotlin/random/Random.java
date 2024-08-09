/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.random;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.PlatformRandomKt;
import kotlin.random.RandomKt;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b'\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J$\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016\u00a8\u0006\u0018"}, d2={"Lkotlin/random/Random;", "", "()V", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "Default", "kotlin-stdlib"})
@SinceKotlin(version="1.3")
@SourceDebugExtension(value={"SMAP\nRandom.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Random.kt\nkotlin/random/Random\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,383:1\n1#2:384\n*E\n"})
public abstract class Random {
    @NotNull
    public static final Default Default = new Default(null);
    @NotNull
    private static final Random defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();

    public abstract int nextBits(int var1);

    public int nextInt() {
        return this.nextBits(32);
    }

    public int nextInt(int n) {
        return this.nextInt(0, n);
    }

    public int nextInt(int n, int n2) {
        int n3;
        boolean bl;
        RandomKt.checkRangeBounds(n, n2);
        int n4 = n2 - n;
        if (n4 > 0 || n4 == Integer.MIN_VALUE) {
            int n5;
            int n6;
            if ((n4 & -n4) == n4) {
                n6 = RandomKt.fastLog2(n4);
                n5 = this.nextBits(n6);
            } else {
                int n7;
                n6 = 0;
                while ((n7 = this.nextInt() >>> 1) - (n6 = n7 % n4) + (n4 - 1) < 0) {
                }
                n5 = n6;
            }
            int n8 = n5;
            return n + n8;
        }
        do {
            if (n <= (n3 = this.nextInt())) {
                if (n3 < n2) {
                    bl = true;
                    continue;
                }
                bl = false;
                continue;
            }
            bl = false;
        } while (!bl);
        return n3;
    }

    public long nextLong() {
        return ((long)this.nextInt() << 32) + (long)this.nextInt();
    }

    public long nextLong(long l) {
        return this.nextLong(0L, l);
    }

    public long nextLong(long l, long l2) {
        long l3;
        boolean bl;
        RandomKt.checkRangeBounds(l, l2);
        long l4 = l2 - l;
        if (l4 > 0L) {
            long l5 = 0L;
            if ((l4 & -l4) == l4) {
                long l6;
                int n;
                int n2 = (int)l4;
                int n3 = (int)(l4 >>> 32);
                if (n2 != 0) {
                    n = RandomKt.fastLog2(n2);
                    l6 = (long)this.nextBits(n) & 0xFFFFFFFFL;
                } else if (n3 == 1) {
                    l6 = (long)this.nextInt() & 0xFFFFFFFFL;
                } else {
                    n = RandomKt.fastLog2(n3);
                    l6 = ((long)this.nextBits(n) << 32) + ((long)this.nextInt() & 0xFFFFFFFFL);
                }
                l5 = l6;
            } else {
                long l7;
                long l8 = 0L;
                while ((l7 = this.nextLong() >>> 1) - (l8 = l7 % l4) + (l4 - 1L) < 0L) {
                }
                l5 = l8;
            }
            return l + l5;
        }
        do {
            if (l <= (l3 = this.nextLong())) {
                if (l3 < l2) {
                    bl = true;
                    continue;
                }
                bl = false;
                continue;
            }
            bl = false;
        } while (!bl);
        return l3;
    }

    public boolean nextBoolean() {
        return this.nextBits(1) != 0;
    }

    public double nextDouble() {
        return PlatformRandomKt.doubleFromParts(this.nextBits(26), this.nextBits(27));
    }

    public double nextDouble(double d) {
        return this.nextDouble(0.0, d);
    }

    public double nextDouble(double d, double d2) {
        double d3;
        double d4;
        RandomKt.checkRangeBounds(d, d2);
        double d5 = d2 - d;
        if (Double.isInfinite(d5) && !Double.isInfinite(d4 = d) && !Double.isNaN(d4) && !Double.isInfinite(d4 = d2) && !Double.isNaN(d4)) {
            d4 = this.nextDouble() * (d2 / (double)2 - d / (double)2);
            d3 = d + d4 + d4;
        } else {
            d3 = d + this.nextDouble() * d5;
        }
        double d6 = d3;
        return d6 >= d2 ? Math.nextAfter(d2, Double.NEGATIVE_INFINITY) : d6;
    }

    public float nextFloat() {
        return (float)this.nextBits(24) / 1.6777216E7f;
    }

    @NotNull
    public byte[] nextBytes(@NotNull byte[] byArray, int n, int n2) {
        int n3;
        int n4;
        int n5;
        Intrinsics.checkNotNullParameter(byArray, "array");
        int n6 = n5 = new IntRange(0, byArray.length).contains(n) && new IntRange(0, byArray.length).contains(n2) ? 1 : 0;
        if (n5 == 0) {
            boolean bl = false;
            String string = "fromIndex (" + n + ") or toIndex (" + n2 + ") are out of range: 0.." + byArray.length + '.';
            throw new IllegalArgumentException(string.toString());
        }
        int n7 = n5 = n <= n2 ? 1 : 0;
        if (n5 == 0) {
            boolean bl = false;
            String string = "fromIndex (" + n + ") must be not greater than toIndex (" + n2 + ").";
            throw new IllegalArgumentException(string.toString());
        }
        n5 = (n2 - n) / 4;
        int n8 = 0;
        n8 = n;
        int n9 = 0;
        while (n9 < n5) {
            n4 = n9++;
            n3 = 0;
            int n10 = this.nextInt();
            byArray[n8] = (byte)n10;
            byArray[n8 + 1] = (byte)(n10 >>> 8);
            byArray[n8 + 2] = (byte)(n10 >>> 16);
            byArray[n8 + 3] = (byte)(n10 >>> 24);
            n8 += 4;
        }
        n9 = n2 - n8;
        n4 = this.nextBits(n9 * 8);
        for (n3 = 0; n3 < n9; ++n3) {
            byArray[n8 + n3] = (byte)(n4 >>> n3 * 8);
        }
        return byArray;
    }

    public static byte[] nextBytes$default(Random random2, byte[] byArray, int n, int n2, int n3, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nextBytes");
        }
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = byArray.length;
        }
        return random2.nextBytes(byArray, n, n2);
    }

    @NotNull
    public byte[] nextBytes(@NotNull byte[] byArray) {
        Intrinsics.checkNotNullParameter(byArray, "array");
        return this.nextBytes(byArray, 0, byArray.length);
    }

    @NotNull
    public byte[] nextBytes(int n) {
        return this.nextBytes(new byte[n]);
    }

    public static final Random access$getDefaultRandom$cp() {
        return defaultRandom;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003:\u0001\u001cB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0007H\u0016J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016J\u0018\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u0019H\u0016J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0014\u001a\u00020\u00192\u0006\u0010\u0013\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0002R\u000e\u0010\u0005\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lkotlin/random/Random$Default;", "Lkotlin/random/Random;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "defaultRandom", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "writeReplace", "", "Serialized", "kotlin-stdlib"})
    public static final class Default
    extends Random
    implements Serializable {
        private Default() {
        }

        private final Object writeReplace() {
            return Serialized.INSTANCE;
        }

        @Override
        public int nextBits(int n) {
            return Random.access$getDefaultRandom$cp().nextBits(n);
        }

        @Override
        public int nextInt() {
            return Random.access$getDefaultRandom$cp().nextInt();
        }

        @Override
        public int nextInt(int n) {
            return Random.access$getDefaultRandom$cp().nextInt(n);
        }

        @Override
        public int nextInt(int n, int n2) {
            return Random.access$getDefaultRandom$cp().nextInt(n, n2);
        }

        @Override
        public long nextLong() {
            return Random.access$getDefaultRandom$cp().nextLong();
        }

        @Override
        public long nextLong(long l) {
            return Random.access$getDefaultRandom$cp().nextLong(l);
        }

        @Override
        public long nextLong(long l, long l2) {
            return Random.access$getDefaultRandom$cp().nextLong(l, l2);
        }

        @Override
        public boolean nextBoolean() {
            return Random.access$getDefaultRandom$cp().nextBoolean();
        }

        @Override
        public double nextDouble() {
            return Random.access$getDefaultRandom$cp().nextDouble();
        }

        @Override
        public double nextDouble(double d) {
            return Random.access$getDefaultRandom$cp().nextDouble(d);
        }

        @Override
        public double nextDouble(double d, double d2) {
            return Random.access$getDefaultRandom$cp().nextDouble(d, d2);
        }

        @Override
        public float nextFloat() {
            return Random.access$getDefaultRandom$cp().nextFloat();
        }

        @Override
        @NotNull
        public byte[] nextBytes(@NotNull byte[] byArray) {
            Intrinsics.checkNotNullParameter(byArray, "array");
            return Random.access$getDefaultRandom$cp().nextBytes(byArray);
        }

        @Override
        @NotNull
        public byte[] nextBytes(int n) {
            return Random.access$getDefaultRandom$cp().nextBytes(n);
        }

        @Override
        @NotNull
        public byte[] nextBytes(@NotNull byte[] byArray, int n, int n2) {
            Intrinsics.checkNotNullParameter(byArray, "array");
            return Random.access$getDefaultRandom$cp().nextBytes(byArray, n, n2);
        }

        public Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0000\b\u00c2\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2={"Lkotlin/random/Random$Default$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "serialVersionUID", "", "readResolve", "", "kotlin-stdlib"})
        private static final class Serialized
        implements Serializable {
            @NotNull
            public static final Serialized INSTANCE = new Serialized();
            private static final long serialVersionUID = 0L;

            private Serialized() {
            }

            private final Object readResolve() {
                return Default;
            }
        }
    }
}

