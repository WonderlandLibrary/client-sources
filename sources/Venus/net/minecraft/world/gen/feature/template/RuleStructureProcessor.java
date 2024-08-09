/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.RuleEntry;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class RuleStructureProcessor
extends StructureProcessor {
    public static final Codec<RuleStructureProcessor> field_237125_a_ = ((MapCodec)RuleEntry.field_237108_a_.listOf().fieldOf("rules")).xmap(RuleStructureProcessor::new, RuleStructureProcessor::lambda$static$0).codec();
    private final ImmutableList<RuleEntry> rules;

    public RuleStructureProcessor(List<? extends RuleEntry> list) {
        this.rules = ImmutableList.copyOf(list);
    }

    @Override
    @Nullable
    public Template.BlockInfo func_230386_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
        Random random2 = new Random(MathHelper.getPositionRandom(blockInfo2.pos));
        BlockState blockState = iWorldReader.getBlockState(blockInfo2.pos);
        for (RuleEntry ruleEntry : this.rules) {
            if (!ruleEntry.func_237110_a_(blockInfo2.state, blockState, blockInfo.pos, blockInfo2.pos, blockPos2, random2)) continue;
            return new Template.BlockInfo(blockInfo2.pos, ruleEntry.getOutputState(), ruleEntry.getOutputNbt());
        }
        return blockInfo2;
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.RULE;
    }

    private static List lambda$static$0(RuleStructureProcessor ruleStructureProcessor) {
        return ruleStructureProcessor.rules;
    }
}

