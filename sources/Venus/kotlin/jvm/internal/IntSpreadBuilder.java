/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PrimitiveSpreadBuilder;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u0006\u0010\n\u001a\u00020\u0002J\f\u0010\u000b\u001a\u00020\u0004*\u00020\u0002H\u0014R\u000e\u0010\u0006\u001a\u00020\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2={"Lkotlin/jvm/internal/IntSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", "value", "toArray", "getSize", "kotlin-stdlib"})
public final class IntSpreadBuilder
extends PrimitiveSpreadBuilder<int[]> {
    @NotNull
    private final int[] values;

    public IntSpreadBuilder(int n) {
        super(n);
        this.values = new int[n];
    }

    @Override
    protected int getSize(@NotNull int[] nArray) {
        Intrinsics.checkNotNullParameter(nArray, "<this>");
        return nArray.length;
    }

    public final void add(int n) {
        int n2 = this.getPosition();
        this.setPosition(n2 + 1);
        this.values[n2] = n;
    }

    @NotNull
    public final int[] toArray() {
        return this.toArray(this.values, new int[this.size()]);
    }

    @Override
    public int getSize(Object object) {
        return this.getSize((int[])object);
    }
}

