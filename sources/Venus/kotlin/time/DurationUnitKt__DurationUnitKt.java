/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationUnit;
import kotlin.time.DurationUnitKt__DurationUnitJvmKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=5, xi=49, d1={"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0001\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0001\u00a8\u0006\t"}, d2={"durationUnitByIsoChar", "Lkotlin/time/DurationUnit;", "isoChar", "", "isTimeComponent", "", "durationUnitByShortName", "shortName", "", "kotlin-stdlib"}, xs="kotlin/time/DurationUnitKt")
class DurationUnitKt__DurationUnitKt
extends DurationUnitKt__DurationUnitJvmKt {
    @SinceKotlin(version="1.3")
    @NotNull
    public static final String shortName(@NotNull DurationUnit durationUnit) {
        String string;
        Intrinsics.checkNotNullParameter((Object)durationUnit, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$0[durationUnit.ordinal()]) {
            case 1: {
                string = "ns";
                break;
            }
            case 2: {
                string = "us";
                break;
            }
            case 3: {
                string = "ms";
                break;
            }
            case 4: {
                string = "s";
                break;
            }
            case 5: {
                string = "m";
                break;
            }
            case 6: {
                string = "h";
                break;
            }
            case 7: {
                string = "d";
                break;
            }
            default: {
                throw new IllegalStateException(("Unknown unit: " + (Object)((Object)durationUnit)).toString());
            }
        }
        return string;
    }

    @SinceKotlin(version="1.5")
    @NotNull
    public static final DurationUnit durationUnitByShortName(@NotNull String string) {
        DurationUnit durationUnit;
        Intrinsics.checkNotNullParameter(string, "shortName");
        switch (string) {
            case "ns": {
                durationUnit = DurationUnit.NANOSECONDS;
                break;
            }
            case "us": {
                durationUnit = DurationUnit.MICROSECONDS;
                break;
            }
            case "ms": {
                durationUnit = DurationUnit.MILLISECONDS;
                break;
            }
            case "s": {
                durationUnit = DurationUnit.SECONDS;
                break;
            }
            case "m": {
                durationUnit = DurationUnit.MINUTES;
                break;
            }
            case "h": {
                durationUnit = DurationUnit.HOURS;
                break;
            }
            case "d": {
                durationUnit = DurationUnit.DAYS;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown duration unit short name: " + string);
            }
        }
        return durationUnit;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SinceKotlin(version="1.5")
    @NotNull
    public static final DurationUnit durationUnitByIsoChar(char c, boolean bl) {
        DurationUnit durationUnit;
        if (!bl) {
            if (c != 'D') throw new IllegalArgumentException("Invalid or unsupported duration ISO non-time unit: " + c);
            durationUnit = DurationUnit.DAYS;
            return durationUnit;
        } else {
            char c2 = c;
            if (c2 == 'H') {
                durationUnit = DurationUnit.HOURS;
                return durationUnit;
            } else if (c2 == 'M') {
                durationUnit = DurationUnit.MINUTES;
                return durationUnit;
            } else {
                if (c2 != 'S') throw new IllegalArgumentException("Invalid duration ISO time unit: " + c);
                durationUnit = DurationUnit.SECONDS;
            }
        }
        return durationUnit;
    }

    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[DurationUnit.values().length];
            try {
                nArray[DurationUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[DurationUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[DurationUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[DurationUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[DurationUnit.MINUTES.ordinal()] = 5;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[DurationUnit.HOURS.ordinal()] = 6;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[DurationUnit.DAYS.ordinal()] = 7;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

