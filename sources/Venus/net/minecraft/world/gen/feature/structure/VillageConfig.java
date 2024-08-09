/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;

public class VillageConfig
implements IFeatureConfig {
    public static final Codec<VillageConfig> field_236533_a_ = RecordCodecBuilder.create(VillageConfig::lambda$static$0);
    private final Supplier<JigsawPattern> startPool;
    private final int size;

    public VillageConfig(Supplier<JigsawPattern> supplier, int n) {
        this.startPool = supplier;
        this.size = n;
    }

    public int func_236534_a_() {
        return this.size;
    }

    public Supplier<JigsawPattern> func_242810_c() {
        return this.startPool;
    }

    private static App lambda$static$0(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)JigsawPattern.field_244392_b_.fieldOf("start_pool")).forGetter(VillageConfig::func_242810_c), ((MapCodec)Codec.intRange(0, 7).fieldOf("size")).forGetter(VillageConfig::func_236534_a_)).apply(instance, VillageConfig::new);
    }
}

