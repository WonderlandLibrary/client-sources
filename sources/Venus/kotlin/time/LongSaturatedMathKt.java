/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlin.time.DurationUnitKt;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\u0002\u001a\u00020\u0001H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\t\u001a*\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u00f8\u0001\u0000\u00a2\u0006\u0004\b\r\u0010\u000e\u001a*\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0010\u0010\u000e\u001a(\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0000\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u001a(\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u001a(\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0000\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0001H\u0080\b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2={"checkInfiniteSumDefined", "", "value", "duration", "Lkotlin/time/Duration;", "durationInUnit", "checkInfiniteSumDefined-PjuGub4", "(JJJ)J", "infinityOfSign", "(J)J", "saturatingAdd", "unit", "Lkotlin/time/DurationUnit;", "saturatingAdd-NuflL3o", "(JLkotlin/time/DurationUnit;J)J", "saturatingAddInHalves", "saturatingAddInHalves-NuflL3o", "saturatingDiff", "valueNs", "origin", "(JJLkotlin/time/DurationUnit;)J", "saturatingFiniteDiff", "value1", "value2", "saturatingOriginsDiff", "origin1", "origin2", "isSaturated", "", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nlongSaturatedMath.kt\nKotlin\n*S Kotlin\n*F\n+ 1 longSaturatedMath.kt\nkotlin/time/LongSaturatedMathKt\n*L\n1#1,81:1\n80#1:82\n80#1:83\n80#1:84\n80#1:85\n80#1:86\n80#1:87\n*S KotlinDebug\n*F\n+ 1 longSaturatedMath.kt\nkotlin/time/LongSaturatedMathKt\n*L\n14#1:82\n17#1:83\n36#1:84\n46#1:85\n53#1:86\n57#1:87\n*E\n"})
public final class LongSaturatedMathKt {
    public static final long saturatingAdd-NuflL3o(long l, @NotNull DurationUnit durationUnit, long l2) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        long l3 = Duration.toLong-impl(l2, durationUnit);
        long l4 = l;
        boolean bl = false;
        if ((l4 - 1L | 1L) == Long.MAX_VALUE) {
            return LongSaturatedMathKt.checkInfiniteSumDefined-PjuGub4(l, l2, l3);
        }
        l4 = l3;
        bl = false;
        if ((l4 - 1L | 1L) == Long.MAX_VALUE) {
            return LongSaturatedMathKt.saturatingAddInHalves-NuflL3o(l, durationUnit, l2);
        }
        l4 = l + l3;
        if (((l ^ l4) & (l3 ^ l4)) < 0L) {
            return l < 0L ? Long.MIN_VALUE : Long.MAX_VALUE;
        }
        return l4;
    }

    private static final long checkInfiniteSumDefined-PjuGub4(long l, long l2, long l3) {
        if (Duration.isInfinite-impl(l2) && (l ^ l3) < 0L) {
            throw new IllegalArgumentException("Summing infinities of different signs");
        }
        return l;
    }

    private static final long saturatingAddInHalves-NuflL3o(long l, DurationUnit durationUnit, long l2) {
        long l3;
        long l4 = Duration.div-UwyO8pc(l2, 2);
        long l5 = l3 = Duration.toLong-impl(l4, durationUnit);
        boolean bl = false;
        if ((l5 - 1L | 1L) == Long.MAX_VALUE) {
            return l3;
        }
        return LongSaturatedMathKt.saturatingAdd-NuflL3o(LongSaturatedMathKt.saturatingAdd-NuflL3o(l, durationUnit, l4), durationUnit, Duration.minus-LRDsOJo(l2, l4));
    }

    private static final long infinityOfSign(long l) {
        return l < 0L ? Duration.Companion.getNEG_INFINITE-UwyO8pc$kotlin_stdlib() : Duration.Companion.getINFINITE-UwyO8pc();
    }

    public static final long saturatingDiff(long l, long l2, @NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        long l3 = l2;
        boolean bl = false;
        if ((l3 - 1L | 1L) == Long.MAX_VALUE) {
            return Duration.unaryMinus-UwyO8pc(LongSaturatedMathKt.infinityOfSign(l2));
        }
        return LongSaturatedMathKt.saturatingFiniteDiff(l, l2, durationUnit);
    }

    public static final long saturatingOriginsDiff(long l, long l2, @NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        long l3 = l2;
        boolean bl = false;
        if ((l3 - 1L | 1L) == Long.MAX_VALUE) {
            if (l == l2) {
                return Duration.Companion.getZERO-UwyO8pc();
            }
            return Duration.unaryMinus-UwyO8pc(LongSaturatedMathKt.infinityOfSign(l2));
        }
        l3 = l;
        bl = false;
        if ((l3 - 1L | 1L) == Long.MAX_VALUE) {
            return LongSaturatedMathKt.infinityOfSign(l);
        }
        return LongSaturatedMathKt.saturatingFiniteDiff(l, l2, durationUnit);
    }

    private static final long saturatingFiniteDiff(long l, long l2, DurationUnit durationUnit) {
        long l3 = l - l2;
        if (((l3 ^ l) & (l3 ^ l2 ^ 0xFFFFFFFFFFFFFFFFL)) < 0L) {
            if (durationUnit.compareTo((Enum)DurationUnit.MILLISECONDS) < 0) {
                long l4 = DurationUnitKt.convertDurationUnit(1L, DurationUnit.MILLISECONDS, durationUnit);
                long l5 = l / l4 - l2 / l4;
                long l6 = l % l4 - l2 % l4;
                return Duration.plus-LRDsOJo(DurationKt.toDuration(l5, DurationUnit.MILLISECONDS), DurationKt.toDuration(l6, durationUnit));
            }
            return Duration.unaryMinus-UwyO8pc(LongSaturatedMathKt.infinityOfSign(l3));
        }
        return DurationKt.toDuration(l3, durationUnit);
    }

    public static final boolean isSaturated(long l) {
        boolean bl = false;
        return (l - 1L | 1L) == Long.MAX_VALUE;
    }
}

