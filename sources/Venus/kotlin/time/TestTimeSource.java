/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.time;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.time.AbstractLongTimeSource;
import kotlin.time.Duration;
import kotlin.time.DurationUnit;
import kotlin.time.DurationUnitKt;
import kotlin.time.ExperimentalTime;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002\u00f8\u0001\u0000\u00a2\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000e"}, d2={"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(J)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"})
@SinceKotlin(version="1.9")
@WasExperimental(markerClass={ExperimentalTime.class})
@SourceDebugExtension(value={"SMAP\nTimeSources.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TimeSources.kt\nkotlin/time/TestTimeSource\n+ 2 longSaturatedMath.kt\nkotlin/time/LongSaturatedMathKt\n*L\n1#1,199:1\n80#2:200\n80#2:201\n*S KotlinDebug\n*F\n+ 1 TimeSources.kt\nkotlin/time/TestTimeSource\n*L\n173#1:200\n180#1:201\n*E\n"})
public final class TestTimeSource
extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnit.NANOSECONDS);
        this.markNow();
    }

    @Override
    protected long read() {
        return this.reading;
    }

    public final void plusAssign-LRDsOJo(long l) {
        long l2;
        long l3 = l2 = Duration.toLong-impl(l, this.getUnit());
        boolean bl = false;
        if (!((l3 - 1L | 1L) == Long.MAX_VALUE)) {
            l3 = this.reading + l2;
            if ((this.reading ^ l2) >= 0L && (this.reading ^ l3) < 0L) {
                this.overflow-LRDsOJo(l);
            }
            this.reading = l3;
        } else {
            l3 = Duration.div-UwyO8pc(l, 2);
            long l4 = Duration.toLong-impl(l3, this.getUnit());
            boolean bl2 = false;
            if (!((l4 - 1L | 1L) == Long.MAX_VALUE)) {
                l4 = this.reading;
                try {
                    this.plusAssign-LRDsOJo(l3);
                    this.plusAssign-LRDsOJo(Duration.minus-LRDsOJo(l, l3));
                } catch (IllegalStateException illegalStateException) {
                    this.reading = l4;
                    throw illegalStateException;
                }
            } else {
                this.overflow-LRDsOJo(l);
            }
        }
    }

    private final void overflow-LRDsOJo(long l) {
        throw new IllegalStateException("TestTimeSource will overflow if its reading " + this.reading + DurationUnitKt.shortName(this.getUnit()) + " is advanced by " + Duration.toString-impl(l) + '.');
    }
}

