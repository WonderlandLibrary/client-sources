/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class NopProcessor
extends StructureProcessor {
    public static final Codec<NopProcessor> field_237097_a_;
    public static final NopProcessor INSTANCE;

    private NopProcessor() {
    }

    @Override
    @Nullable
    public Template.BlockInfo func_230386_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
        return blockInfo2;
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.NOP;
    }

    private static NopProcessor lambda$static$0() {
        return INSTANCE;
    }

    static {
        INSTANCE = new NopProcessor();
        field_237097_a_ = Codec.unit(NopProcessor::lambda$static$0);
    }
}

