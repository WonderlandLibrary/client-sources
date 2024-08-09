/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.random;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.random.XorWowRandom;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0004H\u0007\u001a\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\fH\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003H\u0000\u001a\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004H\u0000\u001a\u0010\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0003H\u0000\u001a\u0014\u0010\u000f\u001a\u00020\u0003*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0011H\u0007\u001a\u0014\u0010\u0012\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0013H\u0007\u001a\u0014\u0010\u0014\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0003H\u0000\u00a8\u0006\u0016"}, d2={"Random", "Lkotlin/random/Random;", "seed", "", "", "boundsErrorMessage", "", "from", "", "until", "checkRangeBounds", "", "", "fastLog2", "value", "nextInt", "range", "Lkotlin/ranges/IntRange;", "nextLong", "Lkotlin/ranges/LongRange;", "takeUpperBits", "bitCount", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nRandom.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Random.kt\nkotlin/random/RandomKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,383:1\n1#2:384\n*E\n"})
public final class RandomKt {
    @SinceKotlin(version="1.3")
    @NotNull
    public static final Random Random(int n) {
        return new XorWowRandom(n, n >> 31);
    }

    @SinceKotlin(version="1.3")
    @NotNull
    public static final Random Random(long l) {
        return new XorWowRandom((int)l, (int)(l >> 32));
    }

    @SinceKotlin(version="1.3")
    public static final int nextInt(@NotNull Random random2, @NotNull IntRange intRange) {
        Intrinsics.checkNotNullParameter(random2, "<this>");
        Intrinsics.checkNotNullParameter(intRange, "range");
        if (intRange.isEmpty()) {
            throw new IllegalArgumentException("Cannot get random in empty range: " + intRange);
        }
        return intRange.getLast() < Integer.MAX_VALUE ? random2.nextInt(intRange.getFirst(), intRange.getLast() + 1) : (intRange.getFirst() > Integer.MIN_VALUE ? random2.nextInt(intRange.getFirst() - 1, intRange.getLast()) + 1 : random2.nextInt());
    }

    @SinceKotlin(version="1.3")
    public static final long nextLong(@NotNull Random random2, @NotNull LongRange longRange) {
        Intrinsics.checkNotNullParameter(random2, "<this>");
        Intrinsics.checkNotNullParameter(longRange, "range");
        if (longRange.isEmpty()) {
            throw new IllegalArgumentException("Cannot get random in empty range: " + longRange);
        }
        return longRange.getLast() < Long.MAX_VALUE ? random2.nextLong(longRange.getFirst(), longRange.getLast() + 1L) : (longRange.getFirst() > Long.MIN_VALUE ? random2.nextLong(longRange.getFirst() - 1L, longRange.getLast()) + 1L : random2.nextLong());
    }

    public static final int fastLog2(int n) {
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    public static final int takeUpperBits(int n, int n2) {
        return n >>> 32 - n2 & -n2 >> 31;
    }

    public static final void checkRangeBounds(int n, int n2) {
        boolean bl;
        boolean bl2 = bl = n2 > n;
        if (!bl) {
            boolean bl3 = false;
            String string = RandomKt.boundsErrorMessage(n, n2);
            throw new IllegalArgumentException(string.toString());
        }
    }

    public static final void checkRangeBounds(long l, long l2) {
        boolean bl;
        boolean bl2 = bl = l2 > l;
        if (!bl) {
            boolean bl3 = false;
            String string = RandomKt.boundsErrorMessage(l, l2);
            throw new IllegalArgumentException(string.toString());
        }
    }

    public static final void checkRangeBounds(double d, double d2) {
        boolean bl;
        boolean bl2 = bl = d2 > d;
        if (!bl) {
            boolean bl3 = false;
            String string = RandomKt.boundsErrorMessage(d, d2);
            throw new IllegalArgumentException(string.toString());
        }
    }

    @NotNull
    public static final String boundsErrorMessage(@NotNull Object object, @NotNull Object object2) {
        Intrinsics.checkNotNullParameter(object, "from");
        Intrinsics.checkNotNullParameter(object2, "until");
        return "Random range is empty: [" + object + ", " + object2 + ").";
    }
}

