/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class LavaSubmergingProcessor
extends StructureProcessor {
    public static final Codec<LavaSubmergingProcessor> field_241531_a_;
    public static final LavaSubmergingProcessor field_241532_b_;

    @Override
    @Nullable
    public Template.BlockInfo func_230386_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
        BlockPos blockPos3 = blockInfo2.pos;
        boolean bl = iWorldReader.getBlockState(blockPos3).isIn(Blocks.LAVA);
        return bl && !Block.isOpaque(blockInfo2.state.getShape(iWorldReader, blockPos3)) ? new Template.BlockInfo(blockPos3, Blocks.LAVA.getDefaultState(), blockInfo2.nbt) : blockInfo2;
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.field_241534_i_;
    }

    private static LavaSubmergingProcessor lambda$static$0() {
        return field_241532_b_;
    }

    static {
        field_241532_b_ = new LavaSubmergingProcessor();
        field_241531_a_ = Codec.unit(LavaSubmergingProcessor::lambda$static$0);
    }
}

