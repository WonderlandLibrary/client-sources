/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.ranges;

import kotlin.Deprecated;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.ClosedRange;
import kotlin.ranges.LongProgression;
import kotlin.ranges.OpenEndRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u001a2\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u00020\u00030\u0004:\u0001\u001aB\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\u0011\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0096\u0002J\u0013\u0010\u0012\u001a\u00020\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0010H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u001a\u0010\b\u001a\u00020\u00038VX\u0097\u0004\u00a2\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0014\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\f\u00a8\u0006\u001b"}, d2={"Lkotlin/ranges/LongRange;", "Lkotlin/ranges/LongProgression;", "Lkotlin/ranges/ClosedRange;", "", "Lkotlin/ranges/OpenEndRange;", "start", "endInclusive", "(JJ)V", "endExclusive", "getEndExclusive$annotations", "()V", "getEndExclusive", "()Ljava/lang/Long;", "getEndInclusive", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"})
public final class LongRange
extends LongProgression
implements ClosedRange<Long>,
OpenEndRange<Long> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final LongRange EMPTY = new LongRange(1L, 0L);

    public LongRange(long l, long l2) {
        super(l, l2, 1L);
    }

    @Override
    @NotNull
    public Long getStart() {
        return this.getFirst();
    }

    @Override
    @NotNull
    public Long getEndInclusive() {
        return this.getLast();
    }

    @Override
    @NotNull
    public Long getEndExclusive() {
        if (this.getLast() == Long.MAX_VALUE) {
            throw new IllegalStateException("Cannot return the exclusive upper bound of a range that includes MAX_VALUE.".toString());
        }
        return this.getLast() + 1L;
    }

    @Deprecated(message="Can throw an exception when it's impossible to represent the value with Long type, for example, when the range includes MAX_VALUE. It's recommended to use 'endInclusive' property that doesn't throw.")
    @SinceKotlin(version="1.9")
    @WasExperimental(markerClass={ExperimentalStdlibApi.class})
    public static void getEndExclusive$annotations() {
    }

    @Override
    public boolean contains(long l) {
        return this.getFirst() <= l && l <= this.getLast();
    }

    @Override
    public boolean isEmpty() {
        return this.getFirst() > this.getLast();
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object instanceof LongRange && (this.isEmpty() && ((LongRange)object).isEmpty() || this.getFirst() == ((LongRange)object).getFirst() && this.getLast() == ((LongRange)object).getLast());
    }

    @Override
    public int hashCode() {
        return this.isEmpty() ? -1 : (int)((long)31 * (this.getFirst() ^ this.getFirst() >>> 32) + (this.getLast() ^ this.getLast() >>> 32));
    }

    @Override
    @NotNull
    public String toString() {
        return this.getFirst() + ".." + this.getLast();
    }

    @Override
    public Comparable getStart() {
        return this.getStart();
    }

    @Override
    public Comparable getEndInclusive() {
        return this.getEndInclusive();
    }

    @Override
    public Comparable getEndExclusive() {
        return this.getEndExclusive();
    }

    @Override
    public boolean contains(Comparable comparable) {
        return this.contains(((Number)((Object)comparable)).longValue());
    }

    public static final LongRange access$getEMPTY$cp() {
        return EMPTY;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lkotlin/ranges/LongRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/LongRange;", "getEMPTY", "()Lkotlin/ranges/LongRange;", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final LongRange getEMPTY() {
            return LongRange.access$getEMPTY$cp();
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

