/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationUnit;
import kotlin.time.ExperimentalTime;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000 \n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0001\u001a \u0010\u0000\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0001\u001a \u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0001\u001a\f\u0010\b\u001a\u00020\u0004*\u00020\tH\u0007\u001a\f\u0010\n\u001a\u00020\t*\u00020\u0004H\u0007\u00a8\u0006\u000b"}, d2={"convertDurationUnit", "", "value", "sourceUnit", "Lkotlin/time/DurationUnit;", "targetUnit", "", "convertDurationUnitOverflow", "toDurationUnit", "Ljava/util/concurrent/TimeUnit;", "toTimeUnit", "kotlin-stdlib"}, xs="kotlin/time/DurationUnitKt")
class DurationUnitKt__DurationUnitJvmKt {
    @SinceKotlin(version="1.8")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @NotNull
    public static final TimeUnit toTimeUnit(@NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "<this>");
        return durationUnit.getTimeUnit$kotlin_stdlib();
    }

    @SinceKotlin(version="1.8")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @NotNull
    public static final DurationUnit toDurationUnit(@NotNull TimeUnit timeUnit) {
        DurationUnit durationUnit;
        Intrinsics.checkNotNullParameter((Object)timeUnit, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$0[timeUnit.ordinal()]) {
            case 1: {
                durationUnit = DurationUnit.NANOSECONDS;
                break;
            }
            case 2: {
                durationUnit = DurationUnit.MICROSECONDS;
                break;
            }
            case 3: {
                durationUnit = DurationUnit.MILLISECONDS;
                break;
            }
            case 4: {
                durationUnit = DurationUnit.SECONDS;
                break;
            }
            case 5: {
                durationUnit = DurationUnit.MINUTES;
                break;
            }
            case 6: {
                durationUnit = DurationUnit.HOURS;
                break;
            }
            case 7: {
                durationUnit = DurationUnit.DAYS;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return durationUnit;
    }

    @SinceKotlin(version="1.3")
    public static final double convertDurationUnit(double d, @NotNull DurationUnit durationUnit, @NotNull DurationUnit durationUnit2) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "sourceUnit");
        Intrinsics.checkNotNullParameter((Object)durationUnit2, "targetUnit");
        long l = durationUnit2.getTimeUnit$kotlin_stdlib().convert(1L, durationUnit.getTimeUnit$kotlin_stdlib());
        if (l > 0L) {
            return d * (double)l;
        }
        long l2 = durationUnit.getTimeUnit$kotlin_stdlib().convert(1L, durationUnit2.getTimeUnit$kotlin_stdlib());
        return d / (double)l2;
    }

    @SinceKotlin(version="1.5")
    public static final long convertDurationUnitOverflow(long l, @NotNull DurationUnit durationUnit, @NotNull DurationUnit durationUnit2) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "sourceUnit");
        Intrinsics.checkNotNullParameter((Object)durationUnit2, "targetUnit");
        return durationUnit2.getTimeUnit$kotlin_stdlib().convert(l, durationUnit.getTimeUnit$kotlin_stdlib());
    }

    @SinceKotlin(version="1.5")
    public static final long convertDurationUnit(long l, @NotNull DurationUnit durationUnit, @NotNull DurationUnit durationUnit2) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "sourceUnit");
        Intrinsics.checkNotNullParameter((Object)durationUnit2, "targetUnit");
        return durationUnit2.getTimeUnit$kotlin_stdlib().convert(l, durationUnit.getTimeUnit$kotlin_stdlib());
    }

    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[TimeUnit.values().length];
            try {
                nArray[TimeUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[TimeUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[TimeUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[TimeUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[TimeUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[TimeUnit.DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

