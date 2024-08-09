/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class JigsawReplacementStructureProcessor
extends StructureProcessor {
    public static final Codec<JigsawReplacementStructureProcessor> field_237085_a_;
    public static final JigsawReplacementStructureProcessor INSTANCE;

    private JigsawReplacementStructureProcessor() {
    }

    @Override
    @Nullable
    public Template.BlockInfo func_230386_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
        BlockState blockState = blockInfo2.state;
        if (blockState.isIn(Blocks.JIGSAW)) {
            String string = blockInfo2.nbt.getString("final_state");
            BlockStateParser blockStateParser = new BlockStateParser(new StringReader(string), false);
            try {
                blockStateParser.parse(false);
            } catch (CommandSyntaxException commandSyntaxException) {
                throw new RuntimeException(commandSyntaxException);
            }
            return blockStateParser.getState().isIn(Blocks.STRUCTURE_VOID) ? null : new Template.BlockInfo(blockInfo2.pos, blockStateParser.getState(), null);
        }
        return blockInfo2;
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.JIGSAW_REPLACEMENT;
    }

    private static JigsawReplacementStructureProcessor lambda$static$0() {
        return INSTANCE;
    }

    static {
        INSTANCE = new JigsawReplacementStructureProcessor();
        field_237085_a_ = Codec.unit(JigsawReplacementStructureProcessor::lambda$static$0);
    }
}

