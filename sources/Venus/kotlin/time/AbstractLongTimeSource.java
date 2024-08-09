/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.time.ComparableTimeMark;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlin.time.DurationUnitKt;
import kotlin.time.ExperimentalTime;
import kotlin.time.LongSaturatedMathKt;
import kotlin.time.TimeMark;
import kotlin.time.TimeSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b'\u0018\u00002\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\bH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\bH$R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0012"}, d2={"Lkotlin/time/AbstractLongTimeSource;", "Lkotlin/time/TimeSource$WithComparableMarks;", "unit", "Lkotlin/time/DurationUnit;", "(Lkotlin/time/DurationUnit;)V", "getUnit", "()Lkotlin/time/DurationUnit;", "zero", "", "getZero", "()J", "zero$delegate", "Lkotlin/Lazy;", "adjustedRead", "markNow", "Lkotlin/time/ComparableTimeMark;", "read", "LongTimeMark", "kotlin-stdlib"})
@SinceKotlin(version="1.9")
@WasExperimental(markerClass={ExperimentalTime.class})
public abstract class AbstractLongTimeSource
implements TimeSource.WithComparableMarks {
    @NotNull
    private final DurationUnit unit;
    @NotNull
    private final Lazy zero$delegate;

    public AbstractLongTimeSource(@NotNull DurationUnit durationUnit) {
        Intrinsics.checkNotNullParameter((Object)durationUnit, "unit");
        this.unit = durationUnit;
        this.zero$delegate = LazyKt.lazy((Function0)new Function0<Long>(this){
            final AbstractLongTimeSource this$0;
            {
                this.this$0 = abstractLongTimeSource;
                super(0);
            }

            @NotNull
            public final Long invoke() {
                return this.this$0.read();
            }

            public Object invoke() {
                return this.invoke();
            }
        });
    }

    @NotNull
    protected final DurationUnit getUnit() {
        return this.unit;
    }

    protected abstract long read();

    private final long getZero() {
        Lazy lazy = this.zero$delegate;
        return ((Number)lazy.getValue()).longValue();
    }

    private final long adjustedRead() {
        return this.read() - this.getZero();
    }

    @Override
    @NotNull
    public ComparableTimeMark markNow() {
        return new LongTimeMark(this.adjustedRead(), this, Duration.Companion.getZERO-UwyO8pc(), null);
    }

    @Override
    public TimeMark markNow() {
        return this.markNow();
    }

    public static final long access$adjustedRead(AbstractLongTimeSource abstractLongTimeSource) {
        return abstractLongTimeSource.adjustedRead();
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B \u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0015\u0010\n\u001a\u00020\u0007H\u0016\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0001H\u0096\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0007H\u0096\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0018\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0016\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!\u00a8\u0006\u001c"}, d2={"Lkotlin/time/AbstractLongTimeSource$LongTimeMark;", "Lkotlin/time/ComparableTimeMark;", "startedAt", "", "timeSource", "Lkotlin/time/AbstractLongTimeSource;", "offset", "Lkotlin/time/Duration;", "(JLkotlin/time/AbstractLongTimeSource;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "J", "elapsedNow", "elapsedNow-UwyO8pc", "()J", "equals", "", "other", "", "hashCode", "", "minus", "minus-UwyO8pc", "(Lkotlin/time/ComparableTimeMark;)J", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/ComparableTimeMark;", "toString", "", "kotlin-stdlib"})
    @SourceDebugExtension(value={"SMAP\nTimeSources.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TimeSources.kt\nkotlin/time/AbstractLongTimeSource$LongTimeMark\n+ 2 longSaturatedMath.kt\nkotlin/time/LongSaturatedMathKt\n*L\n1#1,199:1\n80#2:200\n*S KotlinDebug\n*F\n+ 1 TimeSources.kt\nkotlin/time/AbstractLongTimeSource$LongTimeMark\n*L\n67#1:200\n*E\n"})
    private static final class LongTimeMark
    implements ComparableTimeMark {
        private final long startedAt;
        @NotNull
        private final AbstractLongTimeSource timeSource;
        private final long offset;

        private LongTimeMark(long l, AbstractLongTimeSource abstractLongTimeSource, long l2) {
            Intrinsics.checkNotNullParameter(abstractLongTimeSource, "timeSource");
            this.startedAt = l;
            this.timeSource = abstractLongTimeSource;
            this.offset = l2;
        }

        @Override
        public long elapsedNow-UwyO8pc() {
            return Duration.minus-LRDsOJo(LongSaturatedMathKt.saturatingOriginsDiff(AbstractLongTimeSource.access$adjustedRead(this.timeSource), this.startedAt, this.timeSource.getUnit()), this.offset);
        }

        @Override
        @NotNull
        public ComparableTimeMark plus-LRDsOJo(long l) {
            long l2;
            DurationUnit durationUnit = this.timeSource.getUnit();
            if (Duration.isInfinite-impl(l)) {
                long l3 = LongSaturatedMathKt.saturatingAdd-NuflL3o(this.startedAt, durationUnit, l);
                return new LongTimeMark(l3, this.timeSource, Duration.Companion.getZERO-UwyO8pc(), null);
            }
            long l4 = Duration.truncateTo-UwyO8pc$kotlin_stdlib(l, durationUnit);
            long l5 = Duration.plus-LRDsOJo(Duration.minus-LRDsOJo(l, l4), this.offset);
            long l6 = LongSaturatedMathKt.saturatingAdd-NuflL3o(this.startedAt, durationUnit, l4);
            long l7 = Duration.truncateTo-UwyO8pc$kotlin_stdlib(l5, durationUnit);
            l6 = LongSaturatedMathKt.saturatingAdd-NuflL3o(l6, durationUnit, l7);
            long l8 = Duration.minus-LRDsOJo(l5, l7);
            long l9 = Duration.getInWholeNanoseconds-impl(l8);
            if (l6 != 0L && l9 != 0L && (l6 ^ l9) < 0L) {
                l2 = DurationKt.toDuration(MathKt.getSign(l9), durationUnit);
                l6 = LongSaturatedMathKt.saturatingAdd-NuflL3o(l6, durationUnit, l2);
                l8 = Duration.minus-LRDsOJo(l8, l2);
            }
            long l10 = l2 = l6;
            boolean bl = false;
            long l11 = (l10 - 1L | 1L) == Long.MAX_VALUE ? Duration.Companion.getZERO-UwyO8pc() : l8;
            return new LongTimeMark(l2, this.timeSource, l11, null);
        }

        @Override
        public long minus-UwyO8pc(@NotNull ComparableTimeMark comparableTimeMark) {
            Intrinsics.checkNotNullParameter(comparableTimeMark, "other");
            if (!(comparableTimeMark instanceof LongTimeMark) || !Intrinsics.areEqual(this.timeSource, ((LongTimeMark)comparableTimeMark).timeSource)) {
                throw new IllegalArgumentException("Subtracting or comparing time marks from different time sources is not possible: " + this + " and " + comparableTimeMark);
            }
            long l = LongSaturatedMathKt.saturatingOriginsDiff(this.startedAt, ((LongTimeMark)comparableTimeMark).startedAt, this.timeSource.getUnit());
            return Duration.plus-LRDsOJo(l, Duration.minus-LRDsOJo(this.offset, ((LongTimeMark)comparableTimeMark).offset));
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return object instanceof LongTimeMark && Intrinsics.areEqual(this.timeSource, ((LongTimeMark)object).timeSource) && Duration.equals-impl0(this.minus-UwyO8pc((ComparableTimeMark)object), Duration.Companion.getZERO-UwyO8pc());
        }

        @Override
        public int hashCode() {
            return Duration.hashCode-impl(this.offset) * 37 + Long.hashCode(this.startedAt);
        }

        @NotNull
        public String toString() {
            return "LongTimeMark(" + this.startedAt + DurationUnitKt.shortName(this.timeSource.getUnit()) + " + " + Duration.toString-impl(this.offset) + ", " + this.timeSource + ')';
        }

        @Override
        @NotNull
        public ComparableTimeMark minus-LRDsOJo(long l) {
            return ComparableTimeMark.DefaultImpls.minus-LRDsOJo(this, l);
        }

        @Override
        public int compareTo(@NotNull ComparableTimeMark comparableTimeMark) {
            return ComparableTimeMark.DefaultImpls.compareTo(this, comparableTimeMark);
        }

        @Override
        public boolean hasPassedNow() {
            return ComparableTimeMark.DefaultImpls.hasPassedNow(this);
        }

        @Override
        public boolean hasNotPassedNow() {
            return ComparableTimeMark.DefaultImpls.hasNotPassedNow(this);
        }

        @Override
        public TimeMark plus-LRDsOJo(long l) {
            return this.plus-LRDsOJo(l);
        }

        @Override
        public TimeMark minus-LRDsOJo(long l) {
            return this.minus-LRDsOJo(l);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((ComparableTimeMark)object);
        }

        public LongTimeMark(long l, AbstractLongTimeSource abstractLongTimeSource, long l2, DefaultConstructorMarker defaultConstructorMarker) {
            this(l, abstractLongTimeSource, l2);
        }
    }
}

