/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class CaveEdgeConfig
implements IPlacementConfig {
    public static final Codec<CaveEdgeConfig> field_236946_a_ = RecordCodecBuilder.create(CaveEdgeConfig::lambda$static$2);
    protected final GenerationStage.Carving step;
    protected final float probability;

    public CaveEdgeConfig(GenerationStage.Carving carving, float f) {
        this.step = carving;
        this.probability = f;
    }

    private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)GenerationStage.Carving.field_236074_c_.fieldOf("step")).forGetter(CaveEdgeConfig::lambda$static$0), ((MapCodec)Codec.FLOAT.fieldOf("probability")).forGetter(CaveEdgeConfig::lambda$static$1)).apply(instance, CaveEdgeConfig::new);
    }

    private static Float lambda$static$1(CaveEdgeConfig caveEdgeConfig) {
        return Float.valueOf(caveEdgeConfig.probability);
    }

    private static GenerationStage.Carving lambda$static$0(CaveEdgeConfig caveEdgeConfig) {
        return caveEdgeConfig.step;
    }
}

