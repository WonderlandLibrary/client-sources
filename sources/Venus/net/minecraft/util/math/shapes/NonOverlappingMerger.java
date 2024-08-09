/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.math.shapes.IDoubleListMerger;

public class NonOverlappingMerger
extends AbstractDoubleList
implements IDoubleListMerger {
    private final DoubleList list1;
    private final DoubleList list2;
    private final boolean field_199640_c;

    public NonOverlappingMerger(DoubleList doubleList, DoubleList doubleList2, boolean bl) {
        this.list1 = doubleList;
        this.list2 = doubleList2;
        this.field_199640_c = bl;
    }

    @Override
    public int size() {
        return this.list1.size() + this.list2.size();
    }

    @Override
    public boolean forMergedIndexes(IDoubleListMerger.IConsumer iConsumer) {
        return this.field_199640_c ? this.func_199637_b((arg_0, arg_1, arg_2) -> NonOverlappingMerger.lambda$forMergedIndexes$0(iConsumer, arg_0, arg_1, arg_2)) : this.func_199637_b(iConsumer);
    }

    private boolean func_199637_b(IDoubleListMerger.IConsumer iConsumer) {
        int n;
        int n2 = this.list1.size() - 1;
        for (n = 0; n < n2; ++n) {
            if (iConsumer.merge(n, -1, n)) continue;
            return true;
        }
        if (!iConsumer.merge(n2, -1, n2)) {
            return true;
        }
        for (n = 0; n < this.list2.size(); ++n) {
            if (iConsumer.merge(n2, n, n2 + 1 + n)) continue;
            return true;
        }
        return false;
    }

    @Override
    public double getDouble(int n) {
        return n < this.list1.size() ? this.list1.getDouble(n) : this.list2.getDouble(n - this.list1.size());
    }

    @Override
    public DoubleList func_212435_a() {
        return this;
    }

    private static boolean lambda$forMergedIndexes$0(IDoubleListMerger.IConsumer iConsumer, int n, int n2, int n3) {
        return iConsumer.merge(n2, n, n3);
    }
}

