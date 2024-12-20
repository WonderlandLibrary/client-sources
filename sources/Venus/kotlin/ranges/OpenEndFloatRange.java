/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.ranges;

import kotlin.Metadata;
import kotlin.ranges.OpenEndRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0096\u0002J\u0013\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\fH\u0016J\u0018\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0002H\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\t\u00a8\u0006\u0019"}, d2={"Lkotlin/ranges/OpenEndFloatRange;", "Lkotlin/ranges/OpenEndRange;", "", "start", "endExclusive", "(FF)V", "_endExclusive", "_start", "getEndExclusive", "()Ljava/lang/Float;", "getStart", "contains", "", "value", "equals", "other", "", "hashCode", "", "isEmpty", "lessThanOrEquals", "a", "b", "toString", "", "kotlin-stdlib"})
final class OpenEndFloatRange
implements OpenEndRange<Float> {
    private final float _start;
    private final float _endExclusive;

    public OpenEndFloatRange(float f, float f2) {
        this._start = f;
        this._endExclusive = f2;
    }

    @Override
    @NotNull
    public Float getStart() {
        return Float.valueOf(this._start);
    }

    @Override
    @NotNull
    public Float getEndExclusive() {
        return Float.valueOf(this._endExclusive);
    }

    private final boolean lessThanOrEquals(float f, float f2) {
        return f <= f2;
    }

    @Override
    public boolean contains(float f) {
        return f >= this._start && f < this._endExclusive;
    }

    @Override
    public boolean isEmpty() {
        return !(this._start < this._endExclusive);
    }

    public boolean equals(@Nullable Object object) {
        return object instanceof OpenEndFloatRange && (this.isEmpty() && ((OpenEndFloatRange)object).isEmpty() || this._start == ((OpenEndFloatRange)object)._start && this._endExclusive == ((OpenEndFloatRange)object)._endExclusive);
    }

    public int hashCode() {
        return this.isEmpty() ? -1 : 31 * Float.hashCode(this._start) + Float.hashCode(this._endExclusive);
    }

    @NotNull
    public String toString() {
        return this._start + "..<" + this._endExclusive;
    }

    @Override
    public Comparable getStart() {
        return this.getStart();
    }

    @Override
    public Comparable getEndExclusive() {
        return this.getEndExclusive();
    }

    @Override
    public boolean contains(Comparable comparable) {
        return this.contains(((Number)((Object)comparable)).floatValue());
    }
}

