/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.ranges.CharRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlin.time.Duration;
import kotlin.time.DurationUnit;
import kotlin.time.DurationUnitKt;
import kotlin.time.ExperimentalTime;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000>\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b*\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010#\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u0005H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010&\u001a\u0018\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u0001H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u001a\u0018\u0010)\u001a\u00020\u00072\u0006\u0010*\u001a\u00020\u0001H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u001a\u0018\u0010+\u001a\u00020\u00072\u0006\u0010,\u001a\u00020\u0001H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u001a\u0018\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u0001H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u001a\u0010\u0010/\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0001H\u0002\u001a\u0010\u00100\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u0001H\u0002\u001a \u00101\u001a\u00020\u00072\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u000205H\u0002\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106\u001a\u0010\u00107\u001a\u00020\u00012\u0006\u00102\u001a\u000203H\u0002\u001a)\u00108\u001a\u00020\u0005*\u0002032\u0006\u00109\u001a\u00020\u00052\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u0002050;H\u0082\b\u001a)\u0010=\u001a\u000203*\u0002032\u0006\u00109\u001a\u00020\u00052\u0012\u0010:\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u0002050;H\u0082\b\u001a\u001f\u0010>\u001a\u00020\u0007*\u00020\b2\u0006\u0010?\u001a\u00020\u0007H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\b@\u0010A\u001a\u001f\u0010>\u001a\u00020\u0007*\u00020\u00052\u0006\u0010?\u001a\u00020\u0007H\u0087\n\u00f8\u0001\u0000\u00a2\u0006\u0004\bB\u0010C\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\b2\u0006\u0010E\u001a\u00020FH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010G\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\u00052\u0006\u0010E\u001a\u00020FH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010H\u001a\u001c\u0010D\u001a\u00020\u0007*\u00020\u00012\u0006\u0010E\u001a\u00020FH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010I\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0080T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0080T\u00a2\u0006\u0002\n\u0000\"!\u0010\u0006\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\t\u0010\r\u001a\u0004\b\u000b\u0010\u000e\"!\u0010\u0006\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\t\u0010\u000f\u001a\u0004\b\u000b\u0010\u0010\"!\u0010\u0011\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0012\u0010\n\u001a\u0004\b\u0013\u0010\f\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000e\"!\u0010\u0011\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0010\"!\u0010\u0014\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0015\u0010\n\u001a\u0004\b\u0016\u0010\f\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0015\u0010\r\u001a\u0004\b\u0016\u0010\u000e\"!\u0010\u0014\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0015\u0010\u000f\u001a\u0004\b\u0016\u0010\u0010\"!\u0010\u0017\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0018\u0010\n\u001a\u0004\b\u0019\u0010\f\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0018\u0010\r\u001a\u0004\b\u0019\u0010\u000e\"!\u0010\u0017\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u0018\u0010\u000f\u001a\u0004\b\u0019\u0010\u0010\"!\u0010\u001a\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001b\u0010\n\u001a\u0004\b\u001c\u0010\f\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001b\u0010\r\u001a\u0004\b\u001c\u0010\u000e\"!\u0010\u001a\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001b\u0010\u000f\u001a\u0004\b\u001c\u0010\u0010\"!\u0010\u001d\u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001e\u0010\n\u001a\u0004\b\u001f\u0010\f\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001e\u0010\r\u001a\u0004\b\u001f\u0010\u000e\"!\u0010\u001d\u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b\u001e\u0010\u000f\u001a\u0004\b\u001f\u0010\u0010\"!\u0010 \u001a\u00020\u0007*\u00020\b8FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b!\u0010\n\u001a\u0004\b\"\u0010\f\"!\u0010 \u001a\u00020\u0007*\u00020\u00058FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b!\u0010\r\u001a\u0004\b\"\u0010\u000e\"!\u0010 \u001a\u00020\u0007*\u00020\u00018FX\u0087\u0004\u00f8\u0001\u0000\u00a2\u0006\f\u0012\u0004\b!\u0010\u000f\u001a\u0004\b\"\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006J"}, d2={"MAX_MILLIS", "", "MAX_NANOS", "MAX_NANOS_IN_MILLIS", "NANOS_IN_MILLIS", "", "days", "Lkotlin/time/Duration;", "", "getDays$annotations", "(D)V", "getDays", "(D)J", "(I)V", "(I)J", "(J)V", "(J)J", "hours", "getHours$annotations", "getHours", "microseconds", "getMicroseconds$annotations", "getMicroseconds", "milliseconds", "getMilliseconds$annotations", "getMilliseconds", "minutes", "getMinutes$annotations", "getMinutes", "nanoseconds", "getNanoseconds$annotations", "getNanoseconds", "seconds", "getSeconds$annotations", "getSeconds", "durationOf", "normalValue", "unitDiscriminator", "(JI)J", "durationOfMillis", "normalMillis", "durationOfMillisNormalized", "millis", "durationOfNanos", "normalNanos", "durationOfNanosNormalized", "nanos", "millisToNanos", "nanosToMillis", "parseDuration", "value", "", "strictIso", "", "(Ljava/lang/String;Z)J", "parseOverLongIsoComponent", "skipWhile", "startIndex", "predicate", "Lkotlin/Function1;", "", "substringWhile", "times", "duration", "times-kIfJnKk", "(DJ)J", "times-mvk6XK0", "(IJ)J", "toDuration", "unit", "Lkotlin/time/DurationUnit;", "(DLkotlin/time/DurationUnit;)J", "(ILkotlin/time/DurationUnit;)J", "(JLkotlin/time/DurationUnit;)J", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nDuration.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Duration.kt\nkotlin/time/DurationKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1495:1\n1447#1,6:1497\n1450#1,3:1503\n1447#1,6:1506\n1447#1,6:1512\n1450#1,3:1521\n1#2:1496\n1726#3,3:1518\n*S KotlinDebug\n*F\n+ 1 Duration.kt\nkotlin/time/DurationKt\n*L\n1371#1:1497,6\n1405#1:1503,3\n1408#1:1506,6\n1411#1:1512,6\n1447#1:1521,3\n1436#1:1518,3\n*E\n"})
public final class DurationKt {
    public static final int NANOS_IN_MILLIS = 1000000;
    public static final long MAX_NANOS = 4611686018426999999L;
    public static final long MAX_MILLIS = 0x3FFFFFFFFFFFFFFFL;
    private static final long MAX_NANOS_IN_MILLIS = 4611686018426L;

    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    public static final long toDuration(int n, @NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        return durationUnit.compareTo((Enum)DurationUnit.SECONDS) <= 0 ? DurationKt.durationOfNanos(DurationUnitKt.convertDurationUnitOverflow(n, durationUnit, DurationUnit.NANOSECONDS)) : DurationKt.toDuration((long)n, durationUnit);
    }

    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    public static final long toDuration(long l, @NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        long l2 = DurationUnitKt.convertDurationUnitOverflow(4611686018426999999L, DurationUnit.NANOSECONDS, durationUnit);
        if (new LongRange(-l2, l2).contains(l)) {
            return DurationKt.durationOfNanos(DurationUnitKt.convertDurationUnitOverflow(l, durationUnit, DurationUnit.NANOSECONDS));
        }
        long l3 = DurationUnitKt.convertDurationUnit(l, durationUnit, DurationUnit.MILLISECONDS);
        return DurationKt.durationOfMillis(RangesKt.coerceIn(l3, -4611686018427387903L, 0x3FFFFFFFFFFFFFFFL));
    }

    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    public static final long toDuration(double d, @NotNull DurationUnit durationUnit) {
        long l;
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        double d2 = DurationUnitKt.convertDurationUnit(d, durationUnit, DurationUnit.NANOSECONDS);
        boolean bl2 = bl = !Double.isNaN(d2);
        if (!bl) {
            boolean bl3 = false;
            String string = "Duration value cannot be NaN.";
            throw new IllegalArgumentException(string.toString());
        }
        long l2 = MathKt.roundToLong(d2);
        if (new LongRange(-4611686018426999999L, 4611686018426999999L).contains(l2)) {
            l = DurationKt.durationOfNanos(l2);
        } else {
            long l3 = MathKt.roundToLong(DurationUnitKt.convertDurationUnit(d, durationUnit, DurationUnit.MILLISECONDS));
            l = DurationKt.durationOfMillisNormalized(l3);
        }
        return l;
    }

    public static final long getNanoseconds(int n) {
        return DurationKt.toDuration(n, DurationUnit.NANOSECONDS);
    }

    @Deprecated(message="Use 'Int.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getNanoseconds$annotations(int n) {
    }

    public static final long getNanoseconds(long l) {
        return DurationKt.toDuration(l, DurationUnit.NANOSECONDS);
    }

    @Deprecated(message="Use 'Long.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getNanoseconds$annotations(long l) {
    }

    public static final long getNanoseconds(double d) {
        return DurationKt.toDuration(d, DurationUnit.NANOSECONDS);
    }

    @Deprecated(message="Use 'Double.nanoseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.nanoseconds", imports={"kotlin.time.Duration.Companion.nanoseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getNanoseconds$annotations(double d) {
    }

    public static final long getMicroseconds(int n) {
        return DurationKt.toDuration(n, DurationUnit.MICROSECONDS);
    }

    @Deprecated(message="Use 'Int.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMicroseconds$annotations(int n) {
    }

    public static final long getMicroseconds(long l) {
        return DurationKt.toDuration(l, DurationUnit.MICROSECONDS);
    }

    @Deprecated(message="Use 'Long.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMicroseconds$annotations(long l) {
    }

    public static final long getMicroseconds(double d) {
        return DurationKt.toDuration(d, DurationUnit.MICROSECONDS);
    }

    @Deprecated(message="Use 'Double.microseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.microseconds", imports={"kotlin.time.Duration.Companion.microseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMicroseconds$annotations(double d) {
    }

    public static final long getMilliseconds(int n) {
        return DurationKt.toDuration(n, DurationUnit.MILLISECONDS);
    }

    @Deprecated(message="Use 'Int.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMilliseconds$annotations(int n) {
    }

    public static final long getMilliseconds(long l) {
        return DurationKt.toDuration(l, DurationUnit.MILLISECONDS);
    }

    @Deprecated(message="Use 'Long.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMilliseconds$annotations(long l) {
    }

    public static final long getMilliseconds(double d) {
        return DurationKt.toDuration(d, DurationUnit.MILLISECONDS);
    }

    @Deprecated(message="Use 'Double.milliseconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.milliseconds", imports={"kotlin.time.Duration.Companion.milliseconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMilliseconds$annotations(double d) {
    }

    public static final long getSeconds(int n) {
        return DurationKt.toDuration(n, DurationUnit.SECONDS);
    }

    @Deprecated(message="Use 'Int.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getSeconds$annotations(int n) {
    }

    public static final long getSeconds(long l) {
        return DurationKt.toDuration(l, DurationUnit.SECONDS);
    }

    @Deprecated(message="Use 'Long.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getSeconds$annotations(long l) {
    }

    public static final long getSeconds(double d) {
        return DurationKt.toDuration(d, DurationUnit.SECONDS);
    }

    @Deprecated(message="Use 'Double.seconds' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.seconds", imports={"kotlin.time.Duration.Companion.seconds"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getSeconds$annotations(double d) {
    }

    public static final long getMinutes(int n) {
        return DurationKt.toDuration(n, DurationUnit.MINUTES);
    }

    @Deprecated(message="Use 'Int.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMinutes$annotations(int n) {
    }

    public static final long getMinutes(long l) {
        return DurationKt.toDuration(l, DurationUnit.MINUTES);
    }

    @Deprecated(message="Use 'Long.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMinutes$annotations(long l) {
    }

    public static final long getMinutes(double d) {
        return DurationKt.toDuration(d, DurationUnit.MINUTES);
    }

    @Deprecated(message="Use 'Double.minutes' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.minutes", imports={"kotlin.time.Duration.Companion.minutes"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getMinutes$annotations(double d) {
    }

    public static final long getHours(int n) {
        return DurationKt.toDuration(n, DurationUnit.HOURS);
    }

    @Deprecated(message="Use 'Int.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.hours", imports={"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getHours$annotations(int n) {
    }

    public static final long getHours(long l) {
        return DurationKt.toDuration(l, DurationUnit.HOURS);
    }

    @Deprecated(message="Use 'Long.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.hours", imports={"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getHours$annotations(long l) {
    }

    public static final long getHours(double d) {
        return DurationKt.toDuration(d, DurationUnit.HOURS);
    }

    @Deprecated(message="Use 'Double.hours' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.hours", imports={"kotlin.time.Duration.Companion.hours"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getHours$annotations(double d) {
    }

    public static final long getDays(int n) {
        return DurationKt.toDuration(n, DurationUnit.DAYS);
    }

    @Deprecated(message="Use 'Int.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.days", imports={"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getDays$annotations(int n) {
    }

    public static final long getDays(long l) {
        return DurationKt.toDuration(l, DurationUnit.DAYS);
    }

    @Deprecated(message="Use 'Long.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.days", imports={"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getDays$annotations(long l) {
    }

    public static final long getDays(double d) {
        return DurationKt.toDuration(d, DurationUnit.DAYS);
    }

    @Deprecated(message="Use 'Double.days' extension property from Duration.Companion instead.", replaceWith=@ReplaceWith(expression="this.days", imports={"kotlin.time.Duration.Companion.days"}))
    @DeprecatedSinceKotlin(warningSince="1.5", errorSince="1.8", hiddenSince="1.9")
    @SinceKotlin(version="1.3")
    @ExperimentalTime
    public static void getDays$annotations(double d) {
    }

    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @InlineOnly
    private static final long times-mvk6XK0(int n, long l) {
        return Duration.times-UwyO8pc(l, n);
    }

    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @InlineOnly
    private static final long times-kIfJnKk(double d, long l) {
        return Duration.times-UwyO8pc(l, d);
    }

    private static final long parseDuration(String string, boolean bl) {
        int n = string.length();
        if (n == 0) {
            throw new IllegalArgumentException("The string is empty");
        }
        int n2 = 0;
        long l = Duration.Companion.getZERO-UwyO8pc();
        String string2 = "Infinity";
        char c = string.charAt(n2);
        if (c == '+' ? true : c == '-') {
            ++n2;
        }
        c = n2 > 0 ? (char)'\u0001' : '\u0000';
        boolean bl2 = c != '\u0000' && StringsKt.startsWith$default((CharSequence)string, '-', false, 2, null);
        if (n <= n2) {
            throw new IllegalArgumentException("No components");
        }
        if (string.charAt(n2) == 'P') {
            if (++n2 == n) {
                throw new IllegalArgumentException();
            }
            String string3 = "+-.";
            boolean bl3 = false;
            Object object = null;
            while (n2 < n) {
                String string4;
                int n3;
                if (string.charAt(n2) == 'T') {
                    if (bl3 || ++n2 == n) {
                        throw new IllegalArgumentException();
                    }
                    bl3 = true;
                    continue;
                }
                String string5 = string;
                boolean bl4 = false;
                String string6 = string5;
                String string7 = string5;
                boolean bl5 = false;
                for (n3 = n2; n3 < string7.length(); ++n3) {
                    char c2 = string7.charAt(n3);
                    boolean bl6 = false;
                    if (!(new CharRange('0', '9').contains(c2) || StringsKt.contains$default((CharSequence)string3, c2, false, 2, null))) break;
                }
                int n4 = n3;
                Intrinsics.checkNotNull(string6, "null cannot be cast to non-null type java.lang.String");
                Intrinsics.checkNotNullExpressionValue(string6.substring(n2, n4), "this as java.lang.String\u2026ing(startIndex, endIndex)");
                if (((CharSequence)string4).length() == 0) {
                    throw new IllegalArgumentException();
                }
                Object object2 = string;
                if ((n2 += string4.length()) < 0 || n2 > StringsKt.getLastIndex((CharSequence)object2)) {
                    int n5 = n2;
                    n4 = 0;
                    throw new IllegalArgumentException("Missing unit for value " + string4);
                }
                char c3 = object2.charAt(n2);
                ++n2;
                object2 = DurationUnitKt.durationUnitByIsoChar(c3, bl3);
                if (object != null && ((Enum)object).compareTo((Enum)object2) <= 0) {
                    throw new IllegalArgumentException("Unexpected order of duration components");
                }
                object = object2;
                int n6 = StringsKt.indexOf$default((CharSequence)string4, '.', 0, false, 6, null);
                if (object2 == DurationUnit.SECONDS && n6 > 0) {
                    String string8;
                    String string9 = string4;
                    n3 = 0;
                    Intrinsics.checkNotNull(string9, "null cannot be cast to non-null type java.lang.String");
                    Intrinsics.checkNotNullExpressionValue(string9.substring(n3, n6), "this as java.lang.String\u2026ing(startIndex, endIndex)");
                    l = Duration.plus-LRDsOJo(l, DurationKt.toDuration(DurationKt.parseOverLongIsoComponent(string8), (DurationUnit)((Object)object2)));
                    String string10 = string4;
                    Intrinsics.checkNotNull(string10, "null cannot be cast to non-null type java.lang.String");
                    String string11 = string10.substring(n6);
                    Intrinsics.checkNotNullExpressionValue(string11, "this as java.lang.String).substring(startIndex)");
                    l = Duration.plus-LRDsOJo(l, DurationKt.toDuration(Double.parseDouble(string11), (DurationUnit)((Object)object2)));
                    continue;
                }
                l = Duration.plus-LRDsOJo(l, DurationKt.toDuration(DurationKt.parseOverLongIsoComponent(string4), (DurationUnit)((Object)object2)));
            }
        } else {
            if (bl) {
                throw new IllegalArgumentException();
            }
            if (StringsKt.regionMatches(string, n2, string2, 0, Math.max(n - n2, string2.length()), true)) {
                l = Duration.Companion.getINFINITE-UwyO8pc();
            } else {
                boolean bl7;
                Object object = null;
                boolean bl8 = false;
                boolean bl9 = bl7 = c == '\u0000';
                if (c != '\u0000' && string.charAt(n2) == '(' && StringsKt.last(string) == ')') {
                    bl7 = true;
                    if (++n2 == --n) {
                        throw new IllegalArgumentException("No components");
                    }
                }
                while (n2 < n) {
                    char c4;
                    int n7;
                    int n8;
                    int n9;
                    String string12;
                    if (bl8 && bl7) {
                        int n10;
                        string12 = string;
                        boolean bl10 = false;
                        for (n10 = n2; n10 < string12.length(); ++n10) {
                            n9 = string12.charAt(n10);
                            boolean bl11 = false;
                            if (!(n9 == 32)) break;
                        }
                        n2 = n10;
                    }
                    bl8 = true;
                    String string13 = string;
                    boolean bl12 = false;
                    String string14 = string13;
                    String string15 = string13;
                    boolean bl13 = false;
                    for (n8 = n2; n8 < string15.length(); ++n8) {
                        n7 = string15.charAt(n8);
                        c4 = '\u0000';
                        if (!(new CharRange('0', '9').contains((char)n7) || n7 == 46)) break;
                    }
                    int n11 = n8;
                    Intrinsics.checkNotNull(string14, "null cannot be cast to non-null type java.lang.String");
                    Intrinsics.checkNotNullExpressionValue(string14.substring(n2, n11), "this as java.lang.String\u2026ing(startIndex, endIndex)");
                    if (((CharSequence)string12).length() == 0) {
                        throw new IllegalArgumentException();
                    }
                    n2 += string12.length();
                    Object object3 = string;
                    n9 = 0;
                    String string16 = object3;
                    String string17 = object3;
                    n8 = 0;
                    for (n7 = n2; n7 < string17.length(); ++n7) {
                        c4 = string17.charAt(n7);
                        boolean bl14 = false;
                        if (!new CharRange('a', 'z').contains(c4)) break;
                    }
                    int n12 = n7;
                    Intrinsics.checkNotNull(string16, "null cannot be cast to non-null type java.lang.String");
                    String string18 = string16.substring(n2, n12);
                    Intrinsics.checkNotNullExpressionValue(string18, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                    string13 = string18;
                    n2 += string13.length();
                    object3 = DurationUnitKt.durationUnitByShortName(string13);
                    if (object != null && ((Enum)object).compareTo((Enum)object3) <= 0) {
                        throw new IllegalArgumentException("Unexpected order of duration components");
                    }
                    object = object3;
                    n9 = StringsKt.indexOf$default((CharSequence)string12, '.', 0, false, 6, null);
                    if (n9 > 0) {
                        String string19 = string12;
                        n8 = 0;
                        Intrinsics.checkNotNull(string19, "null cannot be cast to non-null type java.lang.String");
                        String string20 = string19.substring(n8, n9);
                        Intrinsics.checkNotNullExpressionValue(string20, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                        string16 = string20;
                        l = Duration.plus-LRDsOJo(l, DurationKt.toDuration(Long.parseLong(string16), (DurationUnit)((Object)object3)));
                        String string21 = string12;
                        Intrinsics.checkNotNull(string21, "null cannot be cast to non-null type java.lang.String");
                        String string22 = string21.substring(n9);
                        Intrinsics.checkNotNullExpressionValue(string22, "this as java.lang.String).substring(startIndex)");
                        l = Duration.plus-LRDsOJo(l, DurationKt.toDuration(Double.parseDouble(string22), (DurationUnit)((Object)object3)));
                        if (n2 >= n) continue;
                        throw new IllegalArgumentException("Fractional component must be last");
                    }
                    l = Duration.plus-LRDsOJo(l, DurationKt.toDuration(Long.parseLong(string12), (DurationUnit)((Object)object3)));
                }
            }
        }
        return bl2 ? Duration.unaryMinus-UwyO8pc(l) : l;
    }

    private static final long parseOverLongIsoComponent(String string) {
        int n = string.length();
        int n2 = 0;
        if (n > 0 && StringsKt.contains$default((CharSequence)"+-", string.charAt(0), false, 2, null)) {
            ++n2;
        }
        if (n - n2 > 16) {
            boolean bl;
            block6: {
                Iterable iterable = new IntRange(n2, StringsKt.getLastIndex(string));
                boolean bl2 = false;
                if (iterable instanceof Collection && ((Collection)iterable).isEmpty()) {
                    bl = true;
                } else {
                    Iterator iterator2 = iterable.iterator();
                    while (iterator2.hasNext()) {
                        int n3;
                        int n4 = n3 = ((IntIterator)iterator2).nextInt();
                        boolean bl3 = false;
                        if (new CharRange('0', '9').contains(string.charAt(n4))) continue;
                        bl = false;
                        break block6;
                    }
                    bl = true;
                }
            }
            if (bl) {
                return string.charAt(0) == '-' ? Long.MIN_VALUE : Long.MAX_VALUE;
            }
        }
        return StringsKt.startsWith$default(string, "+", false, 2, null) ? Long.parseLong(StringsKt.drop(string, 1)) : Long.parseLong(string);
    }

    private static final String substringWhile(String string, int n, Function1<? super Character, Boolean> function1) {
        int n2;
        boolean bl = false;
        String string2 = string;
        String string3 = string;
        boolean bl2 = false;
        for (n2 = n; n2 < string3.length() && function1.invoke(Character.valueOf(string3.charAt(n2))).booleanValue(); ++n2) {
        }
        int n3 = n2;
        Intrinsics.checkNotNull(string2, "null cannot be cast to non-null type java.lang.String");
        String string4 = string2.substring(n, n3);
        Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String\u2026ing(startIndex, endIndex)");
        return string4;
    }

    private static final int skipWhile(String string, int n, Function1<? super Character, Boolean> function1) {
        int n2;
        boolean bl = false;
        for (n2 = n; n2 < string.length() && function1.invoke(Character.valueOf(string.charAt(n2))).booleanValue(); ++n2) {
        }
        return n2;
    }

    private static final long nanosToMillis(long l) {
        return l / (long)1000000;
    }

    private static final long millisToNanos(long l) {
        return l * (long)1000000;
    }

    private static final long durationOfNanos(long l) {
        return Duration.constructor-impl(l << 1);
    }

    private static final long durationOfMillis(long l) {
        return Duration.constructor-impl((l << 1) + 1L);
    }

    private static final long durationOf(long l, int n) {
        return Duration.constructor-impl((l << 1) + (long)n);
    }

    private static final long durationOfNanosNormalized(long l) {
        return new LongRange(-4611686018426999999L, 4611686018426999999L).contains(l) ? DurationKt.durationOfNanos(l) : DurationKt.durationOfMillis(DurationKt.nanosToMillis(l));
    }

    private static final long durationOfMillisNormalized(long l) {
        return new LongRange(-4611686018426L, 4611686018426L).contains(l) ? DurationKt.durationOfNanos(DurationKt.millisToNanos(l)) : DurationKt.durationOfMillis(RangesKt.coerceIn(l, -4611686018427387903L, 0x3FFFFFFFFFFFFFFFL));
    }

    public static final long access$parseDuration(String string, boolean bl) {
        return DurationKt.parseDuration(string, bl);
    }

    public static final long access$durationOf(long l, int n) {
        return DurationKt.durationOf(l, n);
    }

    public static final long access$durationOfNanosNormalized(long l) {
        return DurationKt.durationOfNanosNormalized(l);
    }

    public static final long access$durationOfMillisNormalized(long l) {
        return DurationKt.durationOfMillisNormalized(l);
    }

    public static final long access$nanosToMillis(long l) {
        return DurationKt.nanosToMillis(l);
    }

    public static final long access$millisToNanos(long l) {
        return DurationKt.millisToNanos(l);
    }

    public static final long access$durationOfNanos(long l) {
        return DurationKt.durationOfNanos(l);
    }

    public static final long access$durationOfMillis(long l) {
        return DurationKt.durationOfMillis(l);
    }
}

