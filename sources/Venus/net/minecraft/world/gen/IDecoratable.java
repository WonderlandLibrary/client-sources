/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.FeatureSpreadConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

public interface IDecoratable<R> {
    public R withPlacement(ConfiguredPlacement<?> var1);

    default public R func_242729_a(int n) {
        return this.withPlacement(Placement.field_242898_b.configure(new ChanceConfig(n)));
    }

    default public R func_242730_a(FeatureSpread featureSpread) {
        return this.withPlacement(Placement.field_242899_c.configure(new FeatureSpreadConfig(featureSpread)));
    }

    default public R func_242731_b(int n) {
        return this.func_242730_a(FeatureSpread.func_242252_a(n));
    }

    default public R func_242732_c(int n) {
        return this.func_242730_a(FeatureSpread.func_242253_a(0, n));
    }

    default public R func_242733_d(int n) {
        return this.withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(0, 0, n)));
    }

    default public R func_242728_a() {
        return this.withPlacement(Placement.field_242903_g.configure(NoPlacementConfig.field_236556_b_));
    }
}

