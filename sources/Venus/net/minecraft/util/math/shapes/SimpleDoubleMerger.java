/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.math.shapes.IDoubleListMerger;

public class SimpleDoubleMerger
implements IDoubleListMerger {
    private final DoubleList list;

    public SimpleDoubleMerger(DoubleList doubleList) {
        this.list = doubleList;
    }

    @Override
    public boolean forMergedIndexes(IDoubleListMerger.IConsumer iConsumer) {
        for (int i = 0; i <= this.list.size(); ++i) {
            if (iConsumer.merge(i, i, i)) continue;
            return true;
        }
        return false;
    }

    @Override
    public DoubleList func_212435_a() {
        return this.list;
    }
}

