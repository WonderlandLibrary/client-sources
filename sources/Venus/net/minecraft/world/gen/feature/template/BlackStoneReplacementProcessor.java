/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.template;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class BlackStoneReplacementProcessor
extends StructureProcessor {
    public static final Codec<BlackStoneReplacementProcessor> field_237057_a_;
    public static final BlackStoneReplacementProcessor field_237058_b_;
    private final Map<Block, Block> field_237059_c_ = Util.make(Maps.newHashMap(), BlackStoneReplacementProcessor::lambda$new$0);

    private BlackStoneReplacementProcessor() {
    }

    @Override
    public Template.BlockInfo func_230386_a_(IWorldReader iWorldReader, BlockPos blockPos, BlockPos blockPos2, Template.BlockInfo blockInfo, Template.BlockInfo blockInfo2, PlacementSettings placementSettings) {
        Block block = this.field_237059_c_.get(blockInfo2.state.getBlock());
        if (block == null) {
            return blockInfo2;
        }
        BlockState blockState = blockInfo2.state;
        BlockState blockState2 = block.getDefaultState();
        if (blockState.hasProperty(StairsBlock.FACING)) {
            blockState2 = (BlockState)blockState2.with(StairsBlock.FACING, blockState.get(StairsBlock.FACING));
        }
        if (blockState.hasProperty(StairsBlock.HALF)) {
            blockState2 = (BlockState)blockState2.with(StairsBlock.HALF, blockState.get(StairsBlock.HALF));
        }
        if (blockState.hasProperty(SlabBlock.TYPE)) {
            blockState2 = (BlockState)blockState2.with(SlabBlock.TYPE, blockState.get(SlabBlock.TYPE));
        }
        return new Template.BlockInfo(blockInfo2.pos, blockState2, blockInfo2.nbt);
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return IStructureProcessorType.field_237136_h_;
    }

    private static BlackStoneReplacementProcessor lambda$static$1() {
        return field_237058_b_;
    }

    private static void lambda$new$0(HashMap hashMap) {
        hashMap.put(Blocks.COBBLESTONE, Blocks.BLACKSTONE);
        hashMap.put(Blocks.MOSSY_COBBLESTONE, Blocks.BLACKSTONE);
        hashMap.put(Blocks.STONE, Blocks.POLISHED_BLACKSTONE);
        hashMap.put(Blocks.STONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
        hashMap.put(Blocks.MOSSY_STONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
        hashMap.put(Blocks.COBBLESTONE_STAIRS, Blocks.BLACKSTONE_STAIRS);
        hashMap.put(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.BLACKSTONE_STAIRS);
        hashMap.put(Blocks.STONE_STAIRS, Blocks.POLISHED_BLACKSTONE_STAIRS);
        hashMap.put(Blocks.STONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
        hashMap.put(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
        hashMap.put(Blocks.COBBLESTONE_SLAB, Blocks.BLACKSTONE_SLAB);
        hashMap.put(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.BLACKSTONE_SLAB);
        hashMap.put(Blocks.SMOOTH_STONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB);
        hashMap.put(Blocks.STONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB);
        hashMap.put(Blocks.STONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
        hashMap.put(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
        hashMap.put(Blocks.STONE_BRICK_WALL, Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
        hashMap.put(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
        hashMap.put(Blocks.COBBLESTONE_WALL, Blocks.BLACKSTONE_WALL);
        hashMap.put(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.BLACKSTONE_WALL);
        hashMap.put(Blocks.CHISELED_STONE_BRICKS, Blocks.CHISELED_POLISHED_BLACKSTONE);
        hashMap.put(Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        hashMap.put(Blocks.IRON_BARS, Blocks.CHAIN);
    }

    static {
        field_237058_b_ = new BlackStoneReplacementProcessor();
        field_237057_a_ = Codec.unit(BlackStoneReplacementProcessor::lambda$static$1);
    }
}

