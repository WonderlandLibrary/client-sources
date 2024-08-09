/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.time.ExperimentalTime;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0087\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2={"Lkotlin/time/DurationUnit;", "", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "(Ljava/lang/String;ILjava/util/concurrent/TimeUnit;)V", "getTimeUnit$kotlin_stdlib", "()Ljava/util/concurrent/TimeUnit;", "NANOSECONDS", "MICROSECONDS", "MILLISECONDS", "SECONDS", "MINUTES", "HOURS", "DAYS", "kotlin-stdlib"})
@SinceKotlin(version="1.6")
@WasExperimental(markerClass={ExperimentalTime.class})
public final class DurationUnit
extends Enum<DurationUnit> {
    @NotNull
    private final TimeUnit timeUnit;
    public static final /* enum */ DurationUnit NANOSECONDS = new DurationUnit(TimeUnit.NANOSECONDS);
    public static final /* enum */ DurationUnit MICROSECONDS = new DurationUnit(TimeUnit.MICROSECONDS);
    public static final /* enum */ DurationUnit MILLISECONDS = new DurationUnit(TimeUnit.MILLISECONDS);
    public static final /* enum */ DurationUnit SECONDS = new DurationUnit(TimeUnit.SECONDS);
    public static final /* enum */ DurationUnit MINUTES = new DurationUnit(TimeUnit.MINUTES);
    public static final /* enum */ DurationUnit HOURS = new DurationUnit(TimeUnit.HOURS);
    public static final /* enum */ DurationUnit DAYS = new DurationUnit(TimeUnit.DAYS);
    private static final DurationUnit[] $VALUES = DurationUnit.$values();
    private static final EnumEntries $ENTRIES = EnumEntriesKt.enumEntries((Enum[])$VALUES);

    private DurationUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    @NotNull
    public final TimeUnit getTimeUnit$kotlin_stdlib() {
        return this.timeUnit;
    }

    public static DurationUnit[] values() {
        return (DurationUnit[])$VALUES.clone();
    }

    public static DurationUnit valueOf(String string) {
        return Enum.valueOf(DurationUnit.class, string);
    }

    @NotNull
    public static EnumEntries<DurationUnit> getEntries() {
        return $ENTRIES;
    }

    private static final DurationUnit[] $values() {
        DurationUnit[] durationUnitArray = new DurationUnit[]{NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS};
        return durationUnitArray;
    }
}

