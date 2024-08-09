/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.ExperimentalTime;
import kotlin.time.TimeMark;
import kotlin.time.TimeSource;
import kotlin.time.TimedValue;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a/\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u0005\u001a3\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u00f8\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a3\u0010\u0000\u001a\u00020\u0001*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\n\u001a3\u0010\u0000\u001a\u00020\u0001*\u00020\u000b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\f\u001a7\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u00f8\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a7\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\u000b2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u0002H\b0\u0003H\u0087\b\u00f8\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u0082\u0002\u000b\n\u0005\b\u009920\u0001\n\u0002\b\u0019\u00a8\u0006\r"}, d2={"measureTime", "Lkotlin/time/Duration;", "block", "Lkotlin/Function0;", "", "(Lkotlin/jvm/functions/Function0;)J", "measureTimedValue", "Lkotlin/time/TimedValue;", "T", "Lkotlin/time/TimeSource;", "(Lkotlin/time/TimeSource;Lkotlin/jvm/functions/Function0;)J", "Lkotlin/time/TimeSource$Monotonic;", "(Lkotlin/time/TimeSource$Monotonic;Lkotlin/jvm/functions/Function0;)J", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nmeasureTime.kt\nKotlin\n*S Kotlin\n*F\n+ 1 measureTime.kt\nkotlin/time/MeasureTimeKt\n*L\n1#1,121:1\n50#1,7:122\n113#1,7:129\n*S KotlinDebug\n*F\n+ 1 measureTime.kt\nkotlin/time/MeasureTimeKt\n*L\n21#1:122,7\n83#1:129,7\n*E\n"})
public final class MeasureTimeKt {
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalTime.class})
    public static final long measureTime(@NotNull Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "block");
        boolean bl = false;
        TimeSource.Monotonic monotonic = TimeSource.Monotonic.INSTANCE;
        boolean bl2 = false;
        long l = monotonic.markNow-z9LOYto();
        function0.invoke();
        return TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(l);
    }

    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalTime.class})
    public static final long measureTime(@NotNull TimeSource timeSource, @NotNull Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(timeSource, "<this>");
        Intrinsics.checkNotNullParameter(function0, "block");
        boolean bl = false;
        TimeMark timeMark = timeSource.markNow();
        function0.invoke();
        return timeMark.elapsedNow-UwyO8pc();
    }

    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalTime.class})
    public static final long measureTime(@NotNull TimeSource.Monotonic monotonic, @NotNull Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(monotonic, "<this>");
        Intrinsics.checkNotNullParameter(function0, "block");
        boolean bl = false;
        long l = monotonic.markNow-z9LOYto();
        function0.invoke();
        return TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(l);
    }

    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @NotNull
    public static final <T> TimedValue<T> measureTimedValue(@NotNull Function0<? extends T> function0) {
        Intrinsics.checkNotNullParameter(function0, "block");
        boolean bl = false;
        TimeSource.Monotonic monotonic = TimeSource.Monotonic.INSTANCE;
        boolean bl2 = false;
        long l = monotonic.markNow-z9LOYto();
        T t = function0.invoke();
        return new TimedValue(t, TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(l), null);
    }

    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @NotNull
    public static final <T> TimedValue<T> measureTimedValue(@NotNull TimeSource timeSource, @NotNull Function0<? extends T> function0) {
        Intrinsics.checkNotNullParameter(timeSource, "<this>");
        Intrinsics.checkNotNullParameter(function0, "block");
        boolean bl = false;
        TimeMark timeMark = timeSource.markNow();
        T t = function0.invoke();
        return new TimedValue(t, timeMark.elapsedNow-UwyO8pc(), null);
    }

    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @NotNull
    public static final <T> TimedValue<T> measureTimedValue(@NotNull TimeSource.Monotonic monotonic, @NotNull Function0<? extends T> function0) {
        Intrinsics.checkNotNullParameter(monotonic, "<this>");
        Intrinsics.checkNotNullParameter(function0, "block");
        boolean bl = false;
        long l = monotonic.markNow-z9LOYto();
        T t = function0.invoke();
        return new TimedValue(t, TimeSource.Monotonic.ValueTimeMark.elapsedNow-UwyO8pc(l), null);
    }
}

