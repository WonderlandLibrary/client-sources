package net.minecraft.client.renderer;

import com.google.common.primitives.Floats;

import java.util.Comparator;

class WorldRenderer$1 implements Comparator<Integer> {
    private final float[] field_181659_a;
    private final WorldRenderer field_181660_b;

    WorldRenderer$1(WorldRenderer p_i46500_1_, float[] p_i46500_2_) {
        this.field_181660_b = p_i46500_1_;
        this.field_181659_a = p_i46500_2_;
    }

    @Override
    public int compare(Integer p_compare_1_, Integer p_compare_2_) {
        return Floats.compare(this.field_181659_a[p_compare_2_], this.field_181659_a[p_compare_1_]);
    }
}
