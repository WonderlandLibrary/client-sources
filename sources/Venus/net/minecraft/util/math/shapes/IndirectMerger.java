/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.util.math.shapes.IDoubleListMerger;

public final class IndirectMerger
implements IDoubleListMerger {
    private final DoubleArrayList field_197856_a;
    private final IntArrayList list1;
    private final IntArrayList list2;

    protected IndirectMerger(DoubleList doubleList, DoubleList doubleList2, boolean bl, boolean bl2) {
        int n = 0;
        int n2 = 0;
        double d = Double.NaN;
        int n3 = doubleList.size();
        int n4 = doubleList2.size();
        int n5 = n3 + n4;
        this.field_197856_a = new DoubleArrayList(n5);
        this.list1 = new IntArrayList(n5);
        this.list2 = new IntArrayList(n5);
        while (true) {
            double d2;
            boolean bl3;
            boolean bl4 = n < n3;
            boolean bl5 = bl3 = n2 < n4;
            if (!bl4 && !bl3) {
                if (this.field_197856_a.isEmpty()) {
                    this.field_197856_a.add(Math.min(doubleList.getDouble(n3 - 1), doubleList2.getDouble(n4 - 1)));
                }
                return;
            }
            boolean bl6 = bl4 && (!bl3 || doubleList.getDouble(n) < doubleList2.getDouble(n2) + 1.0E-7);
            double d3 = d2 = bl6 ? doubleList.getDouble(n++) : doubleList2.getDouble(n2++);
            if ((n == 0 || !bl4) && !bl6 && !bl2 || (n2 == 0 || !bl3) && bl6 && !bl) continue;
            if (!(d >= d2 - 1.0E-7)) {
                this.list1.add(n - 1);
                this.list2.add(n2 - 1);
                this.field_197856_a.add(d2);
                d = d2;
                continue;
            }
            if (this.field_197856_a.isEmpty()) continue;
            this.list1.set(this.list1.size() - 1, n - 1);
            this.list2.set(this.list2.size() - 1, n2 - 1);
        }
    }

    @Override
    public boolean forMergedIndexes(IDoubleListMerger.IConsumer iConsumer) {
        for (int i = 0; i < this.field_197856_a.size() - 1; ++i) {
            if (iConsumer.merge(this.list1.getInt(i), this.list2.getInt(i), i)) continue;
            return true;
        }
        return false;
    }

    @Override
    public DoubleList func_212435_a() {
        return this.field_197856_a;
    }
}

