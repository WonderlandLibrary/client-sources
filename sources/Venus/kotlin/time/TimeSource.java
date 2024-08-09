/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.ComparableTimeMark;
import kotlin.time.Duration;
import kotlin.time.ExperimentalTime;
import kotlin.time.MonotonicTimeSource;
import kotlin.time.TimeMark;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u0000 \u00042\u00020\u0001:\u0003\u0004\u0005\u0006J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0007"}, d2={"Lkotlin/time/TimeSource;", "", "markNow", "Lkotlin/time/TimeMark;", "Companion", "Monotonic", "WithComparableMarks", "kotlin-stdlib"})
@SinceKotlin(version="1.9")
@WasExperimental(markerClass={ExperimentalTime.class})
public interface TimeSource {
    @NotNull
    public static final Companion Companion = kotlin.time.TimeSource$Companion.$$INSTANCE;

    @NotNull
    public TimeMark markNow();

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2={"Lkotlin/time/TimeSource$Companion;", "", "()V", "kotlin-stdlib"})
    public static final class Companion {
        static final Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\tB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u0004H\u0016\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0005\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016\u0082\u0002\b\n\u0002\b!\n\u0002\b\u0019\u00a8\u0006\n"}, d2={"Lkotlin/time/TimeSource$Monotonic;", "Lkotlin/time/TimeSource$WithComparableMarks;", "()V", "markNow", "Lkotlin/time/TimeSource$Monotonic$ValueTimeMark;", "markNow-z9LOYto", "()J", "toString", "", "ValueTimeMark", "kotlin-stdlib"})
    public static final class Monotonic
    implements WithComparableMarks {
        @NotNull
        public static final Monotonic INSTANCE = new Monotonic();

        private Monotonic() {
        }

        public long markNow-z9LOYto() {
            return MonotonicTimeSource.INSTANCE.markNow-z9LOYto();
        }

        @NotNull
        public String toString() {
            return MonotonicTimeSource.INSTANCE.toString();
        }

        @Override
        public ComparableTimeMark markNow() {
            return ValueTimeMark.box-impl(this.markNow-z9LOYto());
        }

        @Override
        public TimeMark markNow() {
            return ValueTimeMark.box-impl(this.markNow-z9LOYto());
        }

        /*
         * Illegal identifiers - consider using --renameillegalidents true
         * Duplicate member names - consider using --renamedupmembers true
         */
        @JvmInline
        @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0014\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0087@\u0018\u00002\u00020\u0001B\u0018\b\u0000\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001b\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\f\u001a\u00020\rH\u0016\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000e\u0010\u0006J\u001a\u0010\u000f\u001a\u00020\u00102\b\u0010\t\u001a\u0004\u0018\u00010\u0011H\u00d6\u0003\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0014\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0016J\u0010\u0010\u0019\u001a\u00020\bH\u00d6\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u001e\u0010\u001c\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0001H\u0096\u0002\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001b\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\rH\u0096\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b \u0010!J\u001b\u0010\u001c\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\"\u0010!J\u001b\u0010#\u001a\u00020\u00002\u0006\u0010\u001f\u001a\u00020\rH\u0096\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b$\u0010!J\u0010\u0010%\u001a\u00020&H\u00d6\u0001\u00a2\u0006\u0004\b'\u0010(R\u0012\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X\u0080\u0004\u00a2\u0006\u0002\n\u0000\u0088\u0001\u0002\u0092\u0001\u00060\u0003j\u0002`\u0004\u00f8\u0001\u0000\u0082\u0002\b\n\u0002\b\u0019\n\u0002\b!\u00a8\u0006)"}, d2={"Lkotlin/time/TimeSource$Monotonic$ValueTimeMark;", "Lkotlin/time/ComparableTimeMark;", "reading", "", "Lkotlin/time/ValueTimeMarkReading;", "constructor-impl", "(J)J", "compareTo", "", "other", "compareTo-6eNON_k", "(JJ)I", "elapsedNow", "Lkotlin/time/Duration;", "elapsedNow-UwyO8pc", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "hasNotPassedNow", "hasNotPassedNow-impl", "(J)Z", "hasPassedNow", "hasPassedNow-impl", "hashCode", "hashCode-impl", "(J)I", "minus", "minus-UwyO8pc", "(JLkotlin/time/ComparableTimeMark;)J", "duration", "minus-LRDsOJo", "(JJ)J", "minus-6eNON_k", "plus", "plus-LRDsOJo", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "kotlin-stdlib"})
        @SinceKotlin(version="1.9")
        @WasExperimental(markerClass={ExperimentalTime.class})
        public static final class ValueTimeMark
        implements ComparableTimeMark {
            private final long reading;

            public static long elapsedNow-UwyO8pc(long l) {
                return MonotonicTimeSource.INSTANCE.elapsedFrom-6eNON_k(l);
            }

            @Override
            public long elapsedNow-UwyO8pc() {
                return ValueTimeMark.elapsedNow-UwyO8pc(this.reading);
            }

            public static long plus-LRDsOJo(long l, long l2) {
                return MonotonicTimeSource.INSTANCE.adjustReading-6QKq23U(l, l2);
            }

            public long plus-LRDsOJo(long l) {
                return ValueTimeMark.plus-LRDsOJo(this.reading, l);
            }

            public static long minus-LRDsOJo(long l, long l2) {
                return MonotonicTimeSource.INSTANCE.adjustReading-6QKq23U(l, Duration.unaryMinus-UwyO8pc(l2));
            }

            public long minus-LRDsOJo(long l) {
                return ValueTimeMark.minus-LRDsOJo(this.reading, l);
            }

            public static boolean hasPassedNow-impl(long l) {
                return !Duration.isNegative-impl(ValueTimeMark.elapsedNow-UwyO8pc(l));
            }

            @Override
            public boolean hasPassedNow() {
                return ValueTimeMark.hasPassedNow-impl(this.reading);
            }

            public static boolean hasNotPassedNow-impl(long l) {
                return Duration.isNegative-impl(ValueTimeMark.elapsedNow-UwyO8pc(l));
            }

            @Override
            public boolean hasNotPassedNow() {
                return ValueTimeMark.hasNotPassedNow-impl(this.reading);
            }

            public static long minus-UwyO8pc(long l, @NotNull ComparableTimeMark comparableTimeMark) {
                Intrinsics.checkNotNullParameter(comparableTimeMark, "other");
                if (!(comparableTimeMark instanceof ValueTimeMark)) {
                    throw new IllegalArgumentException("Subtracting or comparing time marks from different time sources is not possible: " + ValueTimeMark.toString-impl(l) + " and " + comparableTimeMark);
                }
                return ValueTimeMark.minus-6eNON_k(l, ((ValueTimeMark)comparableTimeMark).unbox-impl());
            }

            @Override
            public long minus-UwyO8pc(@NotNull ComparableTimeMark comparableTimeMark) {
                Intrinsics.checkNotNullParameter(comparableTimeMark, "other");
                return ValueTimeMark.minus-UwyO8pc(this.reading, comparableTimeMark);
            }

            public static final long minus-6eNON_k(long l, long l2) {
                return MonotonicTimeSource.INSTANCE.differenceBetween-fRLX17w(l, l2);
            }

            public static final int compareTo-6eNON_k(long l, long l2) {
                return Duration.compareTo-LRDsOJo(ValueTimeMark.minus-6eNON_k(l, l2), Duration.Companion.getZERO-UwyO8pc());
            }

            public static int compareTo-impl(long l, @NotNull ComparableTimeMark comparableTimeMark) {
                Intrinsics.checkNotNullParameter(comparableTimeMark, "other");
                return ValueTimeMark.box-impl(l).compareTo(comparableTimeMark);
            }

            @Override
            public int compareTo(@NotNull ComparableTimeMark comparableTimeMark) {
                return ComparableTimeMark.DefaultImpls.compareTo(this, comparableTimeMark);
            }

            public static String toString-impl(long l) {
                return "ValueTimeMark(reading=" + l + ')';
            }

            public String toString() {
                return ValueTimeMark.toString-impl(this.reading);
            }

            public static int hashCode-impl(long l) {
                return Long.hashCode(l);
            }

            @Override
            public int hashCode() {
                return ValueTimeMark.hashCode-impl(this.reading);
            }

            public static boolean equals-impl(long l, Object object) {
                if (!(object instanceof ValueTimeMark)) {
                    return true;
                }
                long l2 = ((ValueTimeMark)object).unbox-impl();
                return l != l2;
            }

            @Override
            public boolean equals(Object object) {
                return ValueTimeMark.equals-impl(this.reading, object);
            }

            private ValueTimeMark(long l) {
                this.reading = l;
            }

            public static long constructor-impl(long l) {
                return l;
            }

            public static final ValueTimeMark box-impl(long l) {
                return new ValueTimeMark(l);
            }

            public final long unbox-impl() {
                return this.reading;
            }

            public static final boolean equals-impl0(long l, long l2) {
                return l == l2;
            }

            @Override
            public ComparableTimeMark plus-LRDsOJo(long l) {
                return ValueTimeMark.box-impl(this.plus-LRDsOJo(l));
            }

            @Override
            public TimeMark plus-LRDsOJo(long l) {
                return ValueTimeMark.box-impl(this.plus-LRDsOJo(l));
            }

            @Override
            public ComparableTimeMark minus-LRDsOJo(long l) {
                return ValueTimeMark.box-impl(this.minus-LRDsOJo(l));
            }

            @Override
            public TimeMark minus-LRDsOJo(long l) {
                return ValueTimeMark.box-impl(this.minus-LRDsOJo(l));
            }

            @Override
            public int compareTo(Object object) {
                return this.compareTo((ComparableTimeMark)object);
            }
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0004"}, d2={"Lkotlin/time/TimeSource$WithComparableMarks;", "Lkotlin/time/TimeSource;", "markNow", "Lkotlin/time/ComparableTimeMark;", "kotlin-stdlib"})
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalTime.class})
    public static interface WithComparableMarks
    extends TimeSource {
        @Override
        @NotNull
        public ComparableTimeMark markNow();
    }
}

