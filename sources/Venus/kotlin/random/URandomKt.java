/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.random;

import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.WasExperimental;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.random.RandomKt;
import kotlin.ranges.UIntRange;
import kotlin.ranges.ULongRange;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a\"\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0000\u00f8\u0001\u0000\u00a2\u0006\u0004\b\t\u0010\n\u001a\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u001a\u001e\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a2\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a\u0014\u0010\u0018\u001a\u00020\u0003*\u00020\rH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019\u001a\u001e\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a&\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a\u001c\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 \u001a\u0014\u0010!\u001a\u00020\b*\u00020\rH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"\u001a\u001e\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0004\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b#\u0010$\u001a&\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b%\u0010&\u001a\u001c\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u001e\u001a\u00020'H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006)"}, d2={"checkUIntRangeBounds", "", "from", "Lkotlin/UInt;", "until", "checkUIntRangeBounds-J1ME1BU", "(II)V", "checkULongRangeBounds", "Lkotlin/ULong;", "checkULongRangeBounds-eb3DHEI", "(JJ)V", "nextUBytes", "Lkotlin/UByteArray;", "Lkotlin/random/Random;", "size", "", "(Lkotlin/random/Random;I)[B", "array", "nextUBytes-EVgfTAA", "(Lkotlin/random/Random;[B)[B", "fromIndex", "toIndex", "nextUBytes-Wvrt4B4", "(Lkotlin/random/Random;[BII)[B", "nextUInt", "(Lkotlin/random/Random;)I", "nextUInt-qCasIEU", "(Lkotlin/random/Random;I)I", "nextUInt-a8DCA5k", "(Lkotlin/random/Random;II)I", "range", "Lkotlin/ranges/UIntRange;", "(Lkotlin/random/Random;Lkotlin/ranges/UIntRange;)I", "nextULong", "(Lkotlin/random/Random;)J", "nextULong-V1Xi4fY", "(Lkotlin/random/Random;J)J", "nextULong-jmpaW-c", "(Lkotlin/random/Random;JJ)J", "Lkotlin/ranges/ULongRange;", "(Lkotlin/random/Random;Lkotlin/ranges/ULongRange;)J", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nURandom.kt\nKotlin\n*S Kotlin\n*F\n+ 1 URandom.kt\nkotlin/random/URandomKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,155:1\n1#2:156\n*E\n"})
public final class URandomKt {
    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
    public static final int nextUInt(@NotNull Random random2) {
        Intrinsics.checkNotNullParameter(random2, "<this>");
        return UInt.constructor-impl(random2.nextInt());
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
    public static final int nextUInt-qCasIEU(@NotNull Random random2, int n) {
        Intrinsics.checkNotNullParameter(random2, "$this$nextUInt");
        return URandomKt.nextUInt-a8DCA5k(random2, 0, n);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
    public static final int nextUInt-a8DCA5k(@NotNull Random random2, int n, int n2) {
        Intrinsics.checkNotNullParameter(random2, "$this$nextUInt");
        URandomKt.checkUIntRangeBounds-J1ME1BU(n, n2);
        int n3 = n ^ Integer.MIN_VALUE;
        int n4 = n2 ^ Integer.MIN_VALUE;
        int n5 = random2.nextInt(n3, n4) ^ Integer.MIN_VALUE;
        return UInt.constructor-impl(n5);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
    public static final int nextUInt(@NotNull Random random2, @NotNull UIntRange uIntRange) {
        Intrinsics.checkNotNullParameter(random2, "<this>");
        Intrinsics.checkNotNullParameter(uIntRange, "range");
        if (uIntRange.isEmpty()) {
            throw new IllegalArgumentException("Cannot get random in empty range: " + uIntRange);
        }
        return Integer.compareUnsigned(uIntRange.getLast-pVg5ArA(), -1) < 0 ? URandomKt.nextUInt-a8DCA5k(random2, uIntRange.getFirst-pVg5ArA(), UInt.constructor-impl(uIntRange.getLast-pVg5ArA() + 1)) : (Integer.compareUnsigned(uIntRange.getFirst-pVg5ArA(), 0) > 0 ? UInt.constructor-impl(URandomKt.nextUInt-a8DCA5k(random2, UInt.constructor-impl(uIntRange.getFirst-pVg5ArA() - 1), uIntRange.getLast-pVg5ArA()) + 1) : URandomKt.nextUInt(random2));
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
    public static final long nextULong(@NotNull Random random2) {
        Intrinsics.checkNotNullParameter(random2, "<this>");
        return ULong.constructor-impl(random2.nextLong());
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
    public static final long nextULong-V1Xi4fY(@NotNull Random random2, long l) {
        Intrinsics.checkNotNullParameter(random2, "$this$nextULong");
        return URandomKt.nextULong-jmpaW-c(random2, 0L, l);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
    public static final long nextULong-jmpaW-c(@NotNull Random random2, long l, long l2) {
        Intrinsics.checkNotNullParameter(random2, "$this$nextULong");
        URandomKt.checkULongRangeBounds-eb3DHEI(l, l2);
        long l3 = l ^ Long.MIN_VALUE;
        long l4 = l2 ^ Long.MIN_VALUE;
        long l5 = random2.nextLong(l3, l4) ^ Long.MIN_VALUE;
        return ULong.constructor-impl(l5);
    }

    @SinceKotlin(version="1.5")
    @WasExperimental(markerClass={ExperimentalUnsignedTypes.class})
    public static final long nextULong(@NotNull Random random2, @NotNull ULongRange uLongRange) {
        long l;
        Intrinsics.checkNotNullParameter(random2, "<this>");
        Intrinsics.checkNotNullParameter(uLongRange, "range");
        if (uLongRange.isEmpty()) {
            throw new IllegalArgumentException("Cannot get random in empty range: " + uLongRange);
        }
        if (Long.compareUnsigned(uLongRange.getLast-s-VKNKU(), -1L) < 0) {
            long l2 = uLongRange.getLast-s-VKNKU();
            int n = 1;
            l = URandomKt.nextULong-jmpaW-c(random2, uLongRange.getFirst-s-VKNKU(), ULong.constructor-impl(l2 + ULong.constructor-impl((long)n & 0xFFFFFFFFL)));
        } else if (Long.compareUnsigned(uLongRange.getFirst-s-VKNKU(), 0L) > 0) {
            long l3 = uLongRange.getFirst-s-VKNKU();
            int n = 1;
            l3 = URandomKt.nextULong-jmpaW-c(random2, ULong.constructor-impl(l3 - ULong.constructor-impl((long)n & 0xFFFFFFFFL)), uLongRange.getLast-s-VKNKU());
            n = 1;
            l = ULong.constructor-impl(l3 + ULong.constructor-impl((long)n & 0xFFFFFFFFL));
        } else {
            l = URandomKt.nextULong(random2);
        }
        return l;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final byte[] nextUBytes-EVgfTAA(@NotNull Random random2, @NotNull byte[] byArray) {
        Intrinsics.checkNotNullParameter(random2, "$this$nextUBytes");
        Intrinsics.checkNotNullParameter(byArray, "array");
        random2.nextBytes(byArray);
        return byArray;
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final byte[] nextUBytes(@NotNull Random random2, int n) {
        Intrinsics.checkNotNullParameter(random2, "<this>");
        return UByteArray.constructor-impl(random2.nextBytes(n));
    }

    @SinceKotlin(version="1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final byte[] nextUBytes-Wvrt4B4(@NotNull Random random2, @NotNull byte[] byArray, int n, int n2) {
        Intrinsics.checkNotNullParameter(random2, "$this$nextUBytes");
        Intrinsics.checkNotNullParameter(byArray, "array");
        random2.nextBytes(byArray, n, n2);
        return byArray;
    }

    public static byte[] nextUBytes-Wvrt4B4$default(Random random2, byte[] byArray, int n, int n2, int n3, Object object) {
        if ((n3 & 2) != 0) {
            n = 0;
        }
        if ((n3 & 4) != 0) {
            n2 = UByteArray.getSize-impl(byArray);
        }
        return URandomKt.nextUBytes-Wvrt4B4(random2, byArray, n, n2);
    }

    public static final void checkUIntRangeBounds-J1ME1BU(int n, int n2) {
        boolean bl;
        boolean bl2 = bl = Integer.compareUnsigned(n2, n) > 0;
        if (!bl) {
            boolean bl3 = false;
            String string = RandomKt.boundsErrorMessage(UInt.box-impl(n), UInt.box-impl(n2));
            throw new IllegalArgumentException(string.toString());
        }
    }

    public static final void checkULongRangeBounds-eb3DHEI(long l, long l2) {
        boolean bl;
        boolean bl2 = bl = Long.compareUnsigned(l2, l) > 0;
        if (!bl) {
            boolean bl3 = false;
            String string = RandomKt.boundsErrorMessage(ULong.box-impl(l), ULong.box-impl(l2));
            throw new IllegalArgumentException(string.toString());
        }
    }
}

