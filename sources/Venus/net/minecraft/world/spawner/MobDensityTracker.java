/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.spawner;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.util.math.BlockPos;

public class MobDensityTracker {
    private final List<DensityEntry> field_234997_a_ = Lists.newArrayList();

    public void func_234998_a_(BlockPos blockPos, double d) {
        if (d != 0.0) {
            this.field_234997_a_.add(new DensityEntry(blockPos, d));
        }
    }

    public double func_234999_b_(BlockPos blockPos, double d) {
        if (d == 0.0) {
            return 0.0;
        }
        double d2 = 0.0;
        for (DensityEntry densityEntry : this.field_234997_a_) {
            d2 += densityEntry.func_235002_a_(blockPos);
        }
        return d2 * d;
    }

    static class DensityEntry {
        private final BlockPos field_235000_a_;
        private final double field_235001_b_;

        public DensityEntry(BlockPos blockPos, double d) {
            this.field_235000_a_ = blockPos;
            this.field_235001_b_ = d;
        }

        public double func_235002_a_(BlockPos blockPos) {
            double d = this.field_235000_a_.distanceSq(blockPos);
            return d == 0.0 ? Double.POSITIVE_INFINITY : this.field_235001_b_ / Math.sqrt(d);
        }
    }
}

