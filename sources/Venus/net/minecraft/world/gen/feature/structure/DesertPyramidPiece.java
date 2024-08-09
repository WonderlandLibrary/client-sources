/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class DesertPyramidPiece
extends ScatteredStructurePiece {
    private final boolean[] hasPlacedChest = new boolean[4];

    public DesertPyramidPiece(Random random2, int n, int n2) {
        super(IStructurePieceType.TEDP, random2, n, 64, n2, 21, 15, 21);
    }

    public DesertPyramidPiece(TemplateManager templateManager, CompoundNBT compoundNBT) {
        super(IStructurePieceType.TEDP, compoundNBT);
        this.hasPlacedChest[0] = compoundNBT.getBoolean("hasPlacedChest0");
        this.hasPlacedChest[1] = compoundNBT.getBoolean("hasPlacedChest1");
        this.hasPlacedChest[2] = compoundNBT.getBoolean("hasPlacedChest2");
        this.hasPlacedChest[3] = compoundNBT.getBoolean("hasPlacedChest3");
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        compoundNBT.putBoolean("hasPlacedChest0", this.hasPlacedChest[0]);
        compoundNBT.putBoolean("hasPlacedChest1", this.hasPlacedChest[1]);
        compoundNBT.putBoolean("hasPlacedChest2", this.hasPlacedChest[2]);
        compoundNBT.putBoolean("hasPlacedChest3", this.hasPlacedChest[3]);
    }

    @Override
    public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        int n;
        int n2;
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, -4, 0, this.width - 1, 0, this.depth - 1, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        for (n2 = 1; n2 <= 9; ++n2) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, n2, n2, this.width - 1 - n2, n2, this.depth - 1 - n2, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2 + 1, n2, n2 + 1, this.width - 2 - n2, n2, this.depth - 2 - n2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        }
        for (n2 = 0; n2 < this.width; ++n2) {
            for (int i = 0; i < this.depth; ++i) {
                int n3 = -5;
                this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.SANDSTONE.getDefaultState(), n2, -5, i, mutableBoundingBox);
            }
        }
        BlockState blockState = (BlockState)Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
        BlockState blockState2 = (BlockState)Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
        BlockState blockState3 = (BlockState)Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
        BlockState blockState4 = (BlockState)Blocks.SANDSTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 9, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 10, 1, 3, 10, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.setBlockState(iSeedReader, blockState, 2, 10, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState2, 2, 10, 4, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState3, 0, 10, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 4, 10, 2, mutableBoundingBox);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 5, 0, 0, this.width - 1, 9, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 4, 10, 1, this.width - 2, 10, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.setBlockState(iSeedReader, blockState, this.width - 3, 10, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState2, this.width - 3, 10, 4, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState3, this.width - 5, 10, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, this.width - 1, 10, 2, mutableBoundingBox);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 0, 0, 12, 4, 4, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 1, 0, 11, 3, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 9, 1, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 9, 2, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 9, 3, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 10, 3, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 11, 3, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 11, 2, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 11, 1, 1, mutableBoundingBox);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 1, 8, 3, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 2, 8, 2, 2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 1, 1, 16, 3, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 1, 2, 16, 2, 2, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 4, 5, this.width - 6, 4, this.depth - 6, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 4, 9, 11, 4, 11, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 1, 8, 8, 3, 8, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 1, 8, 12, 3, 8, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 1, 12, 8, 3, 12, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 1, 12, 12, 3, 12, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 5, 4, 4, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 5, 1, 5, this.width - 2, 4, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 7, 9, 6, 7, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 7, 7, 9, this.width - 7, 7, 11, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 5, 9, 5, 7, 11, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 6, 5, 9, this.width - 6, 7, 11, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 5, 5, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 5, 6, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 6, 6, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), this.width - 6, 5, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), this.width - 6, 6, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), this.width - 7, 6, 10, mutableBoundingBox);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 4, 4, 2, 6, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 3, 4, 4, this.width - 3, 6, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.setBlockState(iSeedReader, blockState, 2, 4, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState, 2, 3, 4, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState, this.width - 3, 4, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState, this.width - 3, 3, 4, mutableBoundingBox);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 3, 2, 2, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 3, 1, 3, this.width - 2, 2, 3, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.setBlockState(iSeedReader, Blocks.SANDSTONE.getDefaultState(), 1, 1, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.SANDSTONE.getDefaultState(), this.width - 2, 1, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.SANDSTONE_SLAB.getDefaultState(), 1, 2, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.SANDSTONE_SLAB.getDefaultState(), this.width - 2, 2, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 2, 1, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState3, this.width - 3, 1, 2, mutableBoundingBox);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 3, 5, 4, 3, 17, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 5, 3, 5, this.width - 5, 3, 17, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 5, 4, 2, 16, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.width - 6, 1, 5, this.width - 5, 2, 16, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        for (n = 5; n <= 17; n += 2) {
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 4, 1, n, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), 4, 2, n, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), this.width - 5, 1, n, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), this.width - 5, 2, n, mutableBoundingBox);
        }
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 10, 0, 7, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 10, 0, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 9, 0, 9, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 11, 0, 9, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 8, 0, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 12, 0, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 7, 0, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 13, 0, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 9, 0, 11, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 11, 0, 11, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 10, 0, 12, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 10, 0, 13, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.BLUE_TERRACOTTA.getDefaultState(), 10, 0, 10, mutableBoundingBox);
        for (n = 0; n <= this.width - 1; n += this.width - 1) {
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 2, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 2, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 2, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 3, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 3, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 3, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 4, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), n, 4, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 4, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 5, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 5, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 5, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 6, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), n, 6, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 6, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 7, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 7, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 7, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 8, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 8, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 8, 3, mutableBoundingBox);
        }
        for (n = 2; n <= this.width - 3; n += this.width - 3 - 2) {
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n - 1, 2, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 2, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n + 1, 2, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n - 1, 3, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 3, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n + 1, 3, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n - 1, 4, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), n, 4, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n + 1, 4, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n - 1, 5, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 5, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n + 1, 5, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n - 1, 6, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), n, 6, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n + 1, 6, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n - 1, 7, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n, 7, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), n + 1, 7, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n - 1, 8, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n, 8, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), n + 1, 8, 0, mutableBoundingBox);
        }
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 4, 0, 12, 6, 0, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 8, 6, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 12, 6, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 9, 5, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), 10, 5, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.ORANGE_TERRACOTTA.getDefaultState(), 11, 5, 0, mutableBoundingBox);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, -14, 8, 12, -11, 12, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, -10, 8, 12, -10, 12, Blocks.CHISELED_SANDSTONE.getDefaultState(), Blocks.CHISELED_SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, -9, 8, 12, -9, 12, Blocks.CUT_SANDSTONE.getDefaultState(), Blocks.CUT_SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, -8, 8, 12, -1, 12, Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), true);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, -11, 9, 11, -1, 11, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.setBlockState(iSeedReader, Blocks.STONE_PRESSURE_PLATE.getDefaultState(), 10, -11, 10, mutableBoundingBox);
        this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, -13, 9, 11, -13, 11, Blocks.TNT.getDefaultState(), Blocks.AIR.getDefaultState(), true);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 8, -11, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 8, -10, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), 7, -10, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 7, -11, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 12, -11, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 12, -10, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), 13, -10, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 13, -11, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 10, -11, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 10, -10, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), 10, -10, 7, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 10, -11, 7, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 10, -11, 12, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 10, -10, 12, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CHISELED_SANDSTONE.getDefaultState(), 10, -10, 13, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CUT_SANDSTONE.getDefaultState(), 10, -11, 13, mutableBoundingBox);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (this.hasPlacedChest[direction.getHorizontalIndex()]) continue;
            int n4 = direction.getXOffset() * 2;
            int n5 = direction.getZOffset() * 2;
            this.hasPlacedChest[direction.getHorizontalIndex()] = this.generateChest(iSeedReader, mutableBoundingBox, random2, 10 + n4, -11, 10 + n5, LootTables.CHESTS_DESERT_PYRAMID);
        }
        return false;
    }
}

