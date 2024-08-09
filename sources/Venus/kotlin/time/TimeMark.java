/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.time.AdjustedTimeMark;
import kotlin.time.Duration;
import kotlin.time.ExperimentalTime;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0015\u0010\u0002\u001a\u00020\u0003H&\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0007H\u0016J\u001b\u0010\t\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0003H\u0096\u0002\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001b\u0010\r\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0003H\u0096\u0002\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\f\u0082\u0002\b\n\u0002\b!\n\u0002\b\u0019\u00a8\u0006\u000f"}, d2={"Lkotlin/time/TimeMark;", "", "elapsedNow", "Lkotlin/time/Duration;", "elapsedNow-UwyO8pc", "()J", "hasNotPassedNow", "", "hasPassedNow", "minus", "duration", "minus-LRDsOJo", "(J)Lkotlin/time/TimeMark;", "plus", "plus-LRDsOJo", "kotlin-stdlib"})
@SinceKotlin(version="1.9")
@WasExperimental(markerClass={ExperimentalTime.class})
public interface TimeMark {
    public long elapsedNow-UwyO8pc();

    @NotNull
    public TimeMark plus-LRDsOJo(long var1);

    @NotNull
    public TimeMark minus-LRDsOJo(long var1);

    public boolean hasPassedNow();

    public boolean hasNotPassedNow();

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(mv={1, 9, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        @NotNull
        public static TimeMark plus-LRDsOJo(@NotNull TimeMark timeMark, long l) {
            return new AdjustedTimeMark(timeMark, l, null);
        }

        @NotNull
        public static TimeMark minus-LRDsOJo(@NotNull TimeMark timeMark, long l) {
            return timeMark.plus-LRDsOJo(Duration.unaryMinus-UwyO8pc(l));
        }

        public static boolean hasPassedNow(@NotNull TimeMark timeMark) {
            return !Duration.isNegative-impl(timeMark.elapsedNow-UwyO8pc());
        }

        public static boolean hasNotPassedNow(@NotNull TimeMark timeMark) {
            return Duration.isNegative-impl(timeMark.elapsedNow-UwyO8pc());
        }
    }
}

