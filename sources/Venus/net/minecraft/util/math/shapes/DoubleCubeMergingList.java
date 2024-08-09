/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.util.math.shapes.DoubleRangeList;
import net.minecraft.util.math.shapes.IDoubleListMerger;
import net.minecraft.util.math.shapes.VoxelShapes;

public final class DoubleCubeMergingList
implements IDoubleListMerger {
    private final DoubleRangeList field_212436_a;
    private final int firstSize;
    private final int secondSize;
    private final int gcd;

    DoubleCubeMergingList(int n, int n2) {
        this.field_212436_a = new DoubleRangeList((int)VoxelShapes.lcm(n, n2));
        this.firstSize = n;
        this.secondSize = n2;
        this.gcd = IntMath.gcd(n, n2);
    }

    @Override
    public boolean forMergedIndexes(IDoubleListMerger.IConsumer iConsumer) {
        int n = this.firstSize / this.gcd;
        int n2 = this.secondSize / this.gcd;
        for (int i = 0; i <= this.field_212436_a.size(); ++i) {
            if (iConsumer.merge(i / n2, i / n, i)) continue;
            return true;
        }
        return false;
    }

    @Override
    public DoubleList func_212435_a() {
        return this.field_212436_a;
    }
}

