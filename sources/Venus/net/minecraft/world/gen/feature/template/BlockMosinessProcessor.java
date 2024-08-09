/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.state.properties.Half;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class BlockMosinessProcessor
extends StructureProcessor {
    public static final Codec<BlockMosinessProcessor> field_237062_a_ = ((MapCodec)Codec.FLOAT.fieldOf("mossiness")).xmap(BlockMosinessProcessor::new, BlockMosinessProcessor::lambda$static$0).codec();
    private final float field_237063_b_;

    public BlockMosinessProcessor(float f) {
        this.field_237063_b_ = f;
    }

    @Override
    @Nullable
    public Template.BlockInfo func_230386_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
        Random random2 = placementSettings.getRandom(blockInfo2.pos);
        BlockState blockState = blockInfo2.state;
        BlockPos blockPos3 = blockInfo2.pos;
        BlockState blockState2 = null;
        if (!(blockState.isIn(Blocks.STONE_BRICKS) || blockState.isIn(Blocks.STONE) || blockState.isIn(Blocks.CHISELED_STONE_BRICKS))) {
            if (blockState.isIn(BlockTags.STAIRS)) {
                blockState2 = this.func_237067_a_(random2, blockInfo2.state);
            } else if (blockState.isIn(BlockTags.SLABS)) {
                blockState2 = this.func_237070_b_(random2);
            } else if (blockState.isIn(BlockTags.WALLS)) {
                blockState2 = this.func_237071_c_(random2);
            } else if (blockState.isIn(Blocks.OBSIDIAN)) {
                blockState2 = this.func_237072_d_(random2);
            }
        } else {
            blockState2 = this.func_237065_a_(random2);
        }
        return blockState2 != null ? new Template.BlockInfo(blockPos3, blockState2, blockInfo2.nbt) : blockInfo2;
    }

    @Nullable
    private BlockState func_237065_a_(Random random2) {
        if (random2.nextFloat() >= 0.5f) {
            return null;
        }
        BlockState[] blockStateArray = new BlockState[]{Blocks.CRACKED_STONE_BRICKS.getDefaultState(), BlockMosinessProcessor.func_237066_a_(random2, Blocks.STONE_BRICK_STAIRS)};
        BlockState[] blockStateArray2 = new BlockState[]{Blocks.MOSSY_STONE_BRICKS.getDefaultState(), BlockMosinessProcessor.func_237066_a_(random2, Blocks.MOSSY_STONE_BRICK_STAIRS)};
        return this.func_237069_a_(random2, blockStateArray, blockStateArray2);
    }

    @Nullable
    private BlockState func_237067_a_(Random random2, BlockState blockState) {
        Direction direction = blockState.get(StairsBlock.FACING);
        Half half = blockState.get(StairsBlock.HALF);
        if (random2.nextFloat() >= 0.5f) {
            return null;
        }
        BlockState[] blockStateArray = new BlockState[]{Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_BRICK_SLAB.getDefaultState()};
        BlockState[] blockStateArray2 = new BlockState[]{(BlockState)((BlockState)Blocks.MOSSY_STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, direction)).with(StairsBlock.HALF, half), Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState()};
        return this.func_237069_a_(random2, blockStateArray, blockStateArray2);
    }

    @Nullable
    private BlockState func_237070_b_(Random random2) {
        return random2.nextFloat() < this.field_237063_b_ ? Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState() : null;
    }

    @Nullable
    private BlockState func_237071_c_(Random random2) {
        return random2.nextFloat() < this.field_237063_b_ ? Blocks.MOSSY_STONE_BRICK_WALL.getDefaultState() : null;
    }

    @Nullable
    private BlockState func_237072_d_(Random random2) {
        return random2.nextFloat() < 0.15f ? Blocks.CRYING_OBSIDIAN.getDefaultState() : null;
    }

    private static BlockState func_237066_a_(Random random2, Block block) {
        return (BlockState)((BlockState)block.getDefaultState().with(StairsBlock.FACING, Direction.Plane.HORIZONTAL.random(random2))).with(StairsBlock.HALF, Half.values()[random2.nextInt(Half.values().length)]);
    }

    private BlockState func_237069_a_(Random random2, BlockState[] blockStateArray, BlockState[] blockStateArray2) {
        return random2.nextFloat() < this.field_237063_b_ ? BlockMosinessProcessor.func_237068_a_(random2, blockStateArray2) : BlockMosinessProcessor.func_237068_a_(random2, blockStateArray);
    }

    private static BlockState func_237068_a_(Random random2, BlockState[] blockStateArray) {
        return blockStateArray[random2.nextInt(blockStateArray.length)];
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.field_237135_g_;
    }

    private static Float lambda$static$0(BlockMosinessProcessor blockMosinessProcessor) {
        return Float.valueOf(blockMosinessProcessor.field_237063_b_);
    }
}

