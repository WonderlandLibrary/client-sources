/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PrimitiveSpreadBuilder;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\u0002J\f\u0010\f\u001a\u00020\u0004*\u00020\u0002H\u0014R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lkotlin/jvm/internal/DoubleSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", "value", "", "toArray", "getSize", "kotlin-stdlib"})
public final class DoubleSpreadBuilder
extends PrimitiveSpreadBuilder<double[]> {
    @NotNull
    private final double[] values;

    public DoubleSpreadBuilder(int n) {
        super(n);
        this.values = new double[n];
    }

    @Override
    protected int getSize(@NotNull double[] dArray) {
        Intrinsics.checkNotNullParameter(dArray, "<this>");
        return dArray.length;
    }

    public final void add(double d) {
        int n = this.getPosition();
        this.setPosition(n + 1);
        this.values[n] = d;
    }

    @NotNull
    public final double[] toArray() {
        return this.toArray(this.values, new double[this.size()]);
    }

    @Override
    public int getSize(Object object) {
        return this.getSize((double[])object);
    }
}

