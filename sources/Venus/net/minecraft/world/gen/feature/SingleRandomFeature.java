/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class SingleRandomFeature
implements IFeatureConfig {
    public static final Codec<SingleRandomFeature> field_236642_a_ = ((MapCodec)ConfiguredFeature.field_242764_c.fieldOf("features")).xmap(SingleRandomFeature::new, SingleRandomFeature::lambda$static$0).codec();
    public final List<Supplier<ConfiguredFeature<?, ?>>> features;

    public SingleRandomFeature(List<Supplier<ConfiguredFeature<?, ?>>> list) {
        this.features = list;
    }

    @Override
    public Stream<ConfiguredFeature<?, ?>> func_241856_an_() {
        return this.features.stream().flatMap(SingleRandomFeature::lambda$func_241856_an_$1);
    }

    private static Stream lambda$func_241856_an_$1(Supplier supplier) {
        return ((ConfiguredFeature)supplier.get()).func_242768_d();
    }

    private static List lambda$static$0(SingleRandomFeature singleRandomFeature) {
        return singleRandomFeature.features;
    }
}

