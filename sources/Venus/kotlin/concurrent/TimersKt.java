/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.concurrent;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001aM\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001aO\u0010\u0000\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001a\u001a\u0010\u0010\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001aM\u0010\u0010\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001aO\u0010\u0010\u001a\u00020\u00012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001a'\u0010\u0011\u001a\u00020\f2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001a3\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001a;\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001a3\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001a;\u0010\u0012\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001a;\u0010\u0015\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u001a;\u0010\u0015\u001a\u00020\f*\u00020\u00012\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0019\b\u0004\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u00a2\u0006\u0002\b\u000eH\u0087\b\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001\u00a8\u0006\u0016"}, d2={"fixedRateTimer", "Ljava/util/Timer;", "name", "", "daemon", "", "startAt", "Ljava/util/Date;", "period", "", "action", "Lkotlin/Function1;", "Ljava/util/TimerTask;", "", "Lkotlin/ExtensionFunctionType;", "initialDelay", "timer", "timerTask", "schedule", "time", "delay", "scheduleAtFixedRate", "kotlin-stdlib"})
@JvmName(name="TimersKt")
public final class TimersKt {
    @InlineOnly
    private static final TimerTask schedule(Timer timer, long l, Function1<? super TimerTask, Unit> function1) {
        Intrinsics.checkNotNullParameter(timer, "<this>");
        Intrinsics.checkNotNullParameter(function1, "action");
        TimerTask timerTask2 = new TimerTask(function1){
            final Function1<TimerTask, Unit> $action;
            {
                this.$action = function1;
            }

            public void run() {
                this.$action.invoke(this);
            }
        };
        timer.schedule(timerTask2, l);
        return timerTask2;
    }

    @InlineOnly
    private static final TimerTask schedule(Timer timer, Date date, Function1<? super TimerTask, Unit> function1) {
        Intrinsics.checkNotNullParameter(timer, "<this>");
        Intrinsics.checkNotNullParameter(date, "time");
        Intrinsics.checkNotNullParameter(function1, "action");
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer.schedule(timerTask2, date);
        return timerTask2;
    }

    @InlineOnly
    private static final TimerTask schedule(Timer timer, long l, long l2, Function1<? super TimerTask, Unit> function1) {
        Intrinsics.checkNotNullParameter(timer, "<this>");
        Intrinsics.checkNotNullParameter(function1, "action");
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer.schedule(timerTask2, l, l2);
        return timerTask2;
    }

    @InlineOnly
    private static final TimerTask schedule(Timer timer, Date date, long l, Function1<? super TimerTask, Unit> function1) {
        Intrinsics.checkNotNullParameter(timer, "<this>");
        Intrinsics.checkNotNullParameter(date, "time");
        Intrinsics.checkNotNullParameter(function1, "action");
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer.schedule(timerTask2, date, l);
        return timerTask2;
    }

    @InlineOnly
    private static final TimerTask scheduleAtFixedRate(Timer timer, long l, long l2, Function1<? super TimerTask, Unit> function1) {
        Intrinsics.checkNotNullParameter(timer, "<this>");
        Intrinsics.checkNotNullParameter(function1, "action");
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer.scheduleAtFixedRate(timerTask2, l, l2);
        return timerTask2;
    }

    @InlineOnly
    private static final TimerTask scheduleAtFixedRate(Timer timer, Date date, long l, Function1<? super TimerTask, Unit> function1) {
        Intrinsics.checkNotNullParameter(timer, "<this>");
        Intrinsics.checkNotNullParameter(date, "time");
        Intrinsics.checkNotNullParameter(function1, "action");
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer.scheduleAtFixedRate(timerTask2, date, l);
        return timerTask2;
    }

    @PublishedApi
    @NotNull
    public static final Timer timer(@Nullable String string, boolean bl) {
        return string == null ? new Timer(bl) : new Timer(string, bl);
    }

    @InlineOnly
    private static final Timer timer(String string, boolean bl, long l, long l2, Function1<? super TimerTask, Unit> function1) {
        Timer timer;
        Intrinsics.checkNotNullParameter(function1, "action");
        Timer timer2 = timer = TimersKt.timer(string, bl);
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer2.schedule(timerTask2, l, l2);
        return timer;
    }

    static Timer timer$default(String string, boolean bl, long l, long l2, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            bl = false;
        }
        if ((n & 4) != 0) {
            l = 0L;
        }
        Intrinsics.checkNotNullParameter(function1, "action");
        Timer timer = TimersKt.timer(string, bl);
        object = timer;
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        ((Timer)object).schedule(timerTask2, l, l2);
        return timer;
    }

    @InlineOnly
    private static final Timer timer(String string, boolean bl, Date date, long l, Function1<? super TimerTask, Unit> function1) {
        Timer timer;
        Intrinsics.checkNotNullParameter(date, "startAt");
        Intrinsics.checkNotNullParameter(function1, "action");
        Timer timer2 = timer = TimersKt.timer(string, bl);
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer2.schedule(timerTask2, date, l);
        return timer;
    }

    static Timer timer$default(String string, boolean bl, Date date, long l, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            bl = false;
        }
        Intrinsics.checkNotNullParameter(date, "startAt");
        Intrinsics.checkNotNullParameter(function1, "action");
        Timer timer = TimersKt.timer(string, bl);
        object = timer;
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        ((Timer)object).schedule(timerTask2, date, l);
        return timer;
    }

    @InlineOnly
    private static final Timer fixedRateTimer(String string, boolean bl, long l, long l2, Function1<? super TimerTask, Unit> function1) {
        Timer timer;
        Intrinsics.checkNotNullParameter(function1, "action");
        Timer timer2 = timer = TimersKt.timer(string, bl);
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer2.scheduleAtFixedRate(timerTask2, l, l2);
        return timer;
    }

    static Timer fixedRateTimer$default(String string, boolean bl, long l, long l2, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            bl = false;
        }
        if ((n & 4) != 0) {
            l = 0L;
        }
        Intrinsics.checkNotNullParameter(function1, "action");
        Timer timer = TimersKt.timer(string, bl);
        object = timer;
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        ((Timer)object).scheduleAtFixedRate(timerTask2, l, l2);
        return timer;
    }

    @InlineOnly
    private static final Timer fixedRateTimer(String string, boolean bl, Date date, long l, Function1<? super TimerTask, Unit> function1) {
        Timer timer;
        Intrinsics.checkNotNullParameter(date, "startAt");
        Intrinsics.checkNotNullParameter(function1, "action");
        Timer timer2 = timer = TimersKt.timer(string, bl);
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        timer2.scheduleAtFixedRate(timerTask2, date, l);
        return timer;
    }

    static Timer fixedRateTimer$default(String string, boolean bl, Date date, long l, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        if ((n & 2) != 0) {
            bl = false;
        }
        Intrinsics.checkNotNullParameter(date, "startAt");
        Intrinsics.checkNotNullParameter(function1, "action");
        Timer timer = TimersKt.timer(string, bl);
        object = timer;
        TimerTask timerTask2 = new /* invalid duplicate definition of identical inner class */;
        ((Timer)object).scheduleAtFixedRate(timerTask2, date, l);
        return timer;
    }

    @InlineOnly
    private static final TimerTask timerTask(Function1<? super TimerTask, Unit> function1) {
        Intrinsics.checkNotNullParameter(function1, "action");
        return new /* invalid duplicate definition of identical inner class */;
    }
}

