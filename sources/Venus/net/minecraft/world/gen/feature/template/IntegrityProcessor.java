/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class IntegrityProcessor
extends StructureProcessor {
    public static final Codec<IntegrityProcessor> field_237077_a_ = ((MapCodec)Codec.FLOAT.fieldOf("integrity")).orElse(Float.valueOf(1.0f)).xmap(IntegrityProcessor::new, IntegrityProcessor::lambda$static$0).codec();
    private final float integrity;

    public IntegrityProcessor(float f) {
        this.integrity = f;
    }

    @Override
    @Nullable
    public Template.BlockInfo func_230386_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
        Random random2 = placementSettings.getRandom(blockInfo2.pos);
        return !(this.integrity >= 1.0f) && !(random2.nextFloat() <= this.integrity) ? null : blockInfo2;
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.BLOCK_ROT;
    }

    private static Float lambda$static$0(IntegrityProcessor integrityProcessor) {
        return Float.valueOf(integrityProcessor.integrity);
    }
}

