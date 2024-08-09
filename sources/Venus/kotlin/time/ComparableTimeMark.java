/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.ExperimentalTime;
import kotlin.time.TimeMark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\bg\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00000\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u0006\u001a\u00020\u00072\b\u0010\u0005\u001a\u0004\u0018\u00010\bH\u00a6\u0002J\b\u0010\t\u001a\u00020\u0004H&J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0000H\u00a6\u0002\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\f\u0010\rJ\u001b\u0010\n\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000bH\u0096\u0002\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000bH\u00a6\u0002\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0010\u0082\u0002\b\n\u0002\b!\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2={"Lkotlin/time/ComparableTimeMark;", "Lkotlin/time/TimeMark;", "", "compareTo", "", "other", "equals", "", "", "hashCode", "minus", "Lkotlin/time/Duration;", "minus-UwyO8pc", "(Lkotlin/time/ComparableTimeMark;)J", "duration", "minus-LRDsOJo", "(J)Lkotlin/time/ComparableTimeMark;", "plus", "plus-LRDsOJo", "kotlin-stdlib"})
@SinceKotlin(version="1.9")
@WasExperimental(markerClass={ExperimentalTime.class})
public interface ComparableTimeMark
extends TimeMark,
Comparable<ComparableTimeMark> {
    @Override
    @NotNull
    public ComparableTimeMark plus-LRDsOJo(long var1);

    @Override
    @NotNull
    public ComparableTimeMark minus-LRDsOJo(long var1);

    public long minus-UwyO8pc(@NotNull ComparableTimeMark var1);

    @Override
    public int compareTo(@NotNull ComparableTimeMark var1);

    public boolean equals(@Nullable Object var1);

    public int hashCode();

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static ComparableTimeMark minus-LRDsOJo(@NotNull ComparableTimeMark comparableTimeMark, long l) {
            return comparableTimeMark.plus-LRDsOJo(Duration.unaryMinus-UwyO8pc(l));
        }

        public static int compareTo(@NotNull ComparableTimeMark comparableTimeMark, @NotNull ComparableTimeMark comparableTimeMark2) {
            Intrinsics.checkNotNullParameter(comparableTimeMark2, "other");
            return Duration.compareTo-LRDsOJo(comparableTimeMark.minus-UwyO8pc(comparableTimeMark2), Duration.Companion.getZERO-UwyO8pc());
        }

        public static boolean hasPassedNow(@NotNull ComparableTimeMark comparableTimeMark) {
            return TimeMark.DefaultImpls.hasPassedNow(comparableTimeMark);
        }

        public static boolean hasNotPassedNow(@NotNull ComparableTimeMark comparableTimeMark) {
            return TimeMark.DefaultImpls.hasNotPassedNow(comparableTimeMark);
        }
    }
}

