/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.StairsShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SwampHutPiece
extends ScatteredStructurePiece {
    private boolean witch;
    private boolean field_214822_f;

    public SwampHutPiece(Random random2, int n, int n2) {
        super(IStructurePieceType.TESH, random2, n, 64, n2, 7, 7, 9);
    }

    public SwampHutPiece(TemplateManager templateManager, CompoundNBT compoundNBT) {
        super(IStructurePieceType.TESH, compoundNBT);
        this.witch = compoundNBT.getBoolean("Witch");
        this.field_214822_f = compoundNBT.getBoolean("Cat");
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        compoundNBT.putBoolean("Witch", this.witch);
        compoundNBT.putBoolean("Cat", this.field_214822_f);
    }

    @Override
    public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        int n;
        int n2;
        int n3;
        if (!this.isInsideBounds(iSeedReader, mutableBoundingBox, 1)) {
            return true;
        }
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 1, 5, 1, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 2, 5, 4, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 1, 0, 4, 1, 0, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 2, 2, 3, 3, 2, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 3, 1, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 3, 5, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 2, 7, 4, 3, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 0, 2, 1, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 0, 2, 5, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 0, 7, 1, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 0, 7, 5, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), true);
        this.setBlockState(iSeedReader, Blocks.OAK_FENCE.getDefaultState(), 2, 3, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.OAK_FENCE.getDefaultState(), 3, 3, 7, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 1, 3, 4, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 5, 3, 4, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 5, 3, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.POTTED_RED_MUSHROOM.getDefaultState(), 1, 3, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 2, 6, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CAULDRON.getDefaultState(), 4, 2, 6, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.OAK_FENCE.getDefaultState(), 1, 2, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.OAK_FENCE.getDefaultState(), 5, 2, 1, mutableBoundingBox);
        BlockState blockState = (BlockState)Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
        BlockState blockState2 = (BlockState)Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
        BlockState blockState3 = (BlockState)Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
        BlockState blockState4 = (BlockState)Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 4, 1, 6, 4, 1, blockState, blockState, true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 4, 2, 0, 4, 7, blockState2, blockState2, true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 4, 2, 6, 4, 7, blockState3, blockState3, true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 4, 8, 6, 4, 8, blockState4, blockState4, true);
        this.setBlockState(iSeedReader, (BlockState)blockState.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT), 0, 4, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)blockState.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT), 6, 4, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)blockState4.with(StairsBlock.SHAPE, StairsShape.OUTER_LEFT), 0, 4, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)blockState4.with(StairsBlock.SHAPE, StairsShape.OUTER_RIGHT), 6, 4, 8, mutableBoundingBox);
        for (n3 = 2; n3 <= 7; n3 += 5) {
            for (n2 = 1; n2 <= 5; n2 += 4) {
                this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.OAK_LOG.getDefaultState(), n2, -1, n3, mutableBoundingBox);
            }
        }
        if (!this.witch && mutableBoundingBox.isVecInside(new BlockPos(n3 = this.getXWithOffset(2, 5), n2 = this.getYWithOffset(2), n = this.getZWithOffset(2, 5)))) {
            this.witch = true;
            WitchEntity witchEntity = EntityType.WITCH.create(iSeedReader.getWorld());
            witchEntity.enablePersistence();
            witchEntity.setLocationAndAngles((double)n3 + 0.5, n2, (double)n + 0.5, 0.0f, 0.0f);
            witchEntity.onInitialSpawn(iSeedReader, iSeedReader.getDifficultyForLocation(new BlockPos(n3, n2, n)), SpawnReason.STRUCTURE, null, null);
            iSeedReader.func_242417_l(witchEntity);
        }
        this.func_214821_a(iSeedReader, mutableBoundingBox);
        return false;
    }

    private void func_214821_a(IServerWorld iServerWorld, MutableBoundingBox mutableBoundingBox) {
        int n;
        int n2;
        int n3;
        if (!this.field_214822_f && mutableBoundingBox.isVecInside(new BlockPos(n3 = this.getXWithOffset(2, 5), n2 = this.getYWithOffset(2), n = this.getZWithOffset(2, 5)))) {
            this.field_214822_f = true;
            CatEntity catEntity = EntityType.CAT.create(iServerWorld.getWorld());
            catEntity.enablePersistence();
            catEntity.setLocationAndAngles((double)n3 + 0.5, n2, (double)n + 0.5, 0.0f, 0.0f);
            catEntity.onInitialSpawn(iServerWorld, iServerWorld.getDifficultyForLocation(new BlockPos(n3, n2, n)), SpawnReason.STRUCTURE, null, null);
            iServerWorld.func_242417_l(catEntity);
        }
    }
}

