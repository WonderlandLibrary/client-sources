/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TripWireBlock;
import net.minecraft.block.TripWireHookBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class JunglePyramidPiece
extends ScatteredStructurePiece {
    private boolean placedMainChest;
    private boolean placedHiddenChest;
    private boolean placedTrap1;
    private boolean placedTrap2;
    private static final Selector MOSS_STONE_SELECTOR = new Selector();

    public JunglePyramidPiece(Random random2, int n, int n2) {
        super(IStructurePieceType.TEJP, random2, n, 64, n2, 12, 10, 15);
    }

    public JunglePyramidPiece(TemplateManager templateManager, CompoundNBT compoundNBT) {
        super(IStructurePieceType.TEJP, compoundNBT);
        this.placedMainChest = compoundNBT.getBoolean("placedMainChest");
        this.placedHiddenChest = compoundNBT.getBoolean("placedHiddenChest");
        this.placedTrap1 = compoundNBT.getBoolean("placedTrap1");
        this.placedTrap2 = compoundNBT.getBoolean("placedTrap2");
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        compoundNBT.putBoolean("placedMainChest", this.placedMainChest);
        compoundNBT.putBoolean("placedHiddenChest", this.placedHiddenChest);
        compoundNBT.putBoolean("placedTrap1", this.placedTrap1);
        compoundNBT.putBoolean("placedTrap2", this.placedTrap2);
    }

    @Override
    public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        int n;
        int n2;
        if (!this.isInsideBounds(iSeedReader, mutableBoundingBox, 1)) {
            return true;
        }
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, -4, 0, this.width - 1, 0, this.depth - 1, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, 1, 2, 9, 2, 2, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, 1, 12, 9, 2, 12, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, 1, 3, 2, 2, 11, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 9, 1, 3, 9, 2, 11, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, 3, 1, 10, 6, 1, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, 3, 13, 10, 6, 13, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, 3, 2, 1, 6, 12, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 10, 3, 2, 10, 6, 12, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, 3, 2, 9, 3, 12, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, 6, 2, 9, 6, 12, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 3, 7, 3, 8, 7, 11, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 8, 4, 7, 8, 10, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 3, 1, 3, 8, 2, 11);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 4, 3, 6, 7, 3, 9);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 2, 4, 2, 9, 5, 12);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 4, 6, 5, 7, 6, 9);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 5, 7, 6, 6, 7, 8);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 5, 1, 2, 6, 2, 2);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 5, 2, 12, 6, 2, 12);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 5, 5, 1, 6, 5, 1);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 5, 5, 13, 6, 5, 13);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 1, 5, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 10, 5, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 1, 5, 9, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), 10, 5, 9, mutableBoundingBox);
        for (n2 = 0; n2 <= 14; n2 += 14) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, 4, n2, 2, 5, n2, false, random2, MOSS_STONE_SELECTOR);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 4, n2, 4, 5, n2, false, random2, MOSS_STONE_SELECTOR);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 7, 4, n2, 7, 5, n2, false, random2, MOSS_STONE_SELECTOR);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 9, 4, n2, 9, 5, n2, false, random2, MOSS_STONE_SELECTOR);
        }
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 5, 6, 0, 6, 6, 0, false, random2, MOSS_STONE_SELECTOR);
        for (n2 = 0; n2 <= 11; n2 += 11) {
            for (int i = 2; i <= 12; i += 2) {
                this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, n2, 4, i, n2, 5, i, false, random2, MOSS_STONE_SELECTOR);
            }
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, n2, 6, 5, n2, 6, 5, false, random2, MOSS_STONE_SELECTOR);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, n2, 6, 9, n2, 6, 9, false, random2, MOSS_STONE_SELECTOR);
        }
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, 7, 2, 2, 9, 2, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 9, 7, 2, 9, 9, 2, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, 7, 12, 2, 9, 12, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 9, 7, 12, 9, 9, 12, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 9, 4, 4, 9, 4, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 7, 9, 4, 7, 9, 4, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 9, 10, 4, 9, 10, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 7, 9, 10, 7, 9, 10, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 5, 9, 7, 6, 9, 7, false, random2, MOSS_STONE_SELECTOR);
        BlockState blockState = (BlockState)Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
        BlockState blockState2 = (BlockState)Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
        BlockState blockState3 = (BlockState)Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
        BlockState blockState4 = (BlockState)Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
        this.setBlockState(iSeedReader, blockState4, 5, 9, 6, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 6, 9, 6, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState3, 5, 9, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState3, 6, 9, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 4, 0, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 5, 0, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 6, 0, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 7, 0, 0, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 4, 1, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 4, 2, 9, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 4, 3, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 7, 1, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 7, 2, 9, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState4, 7, 3, 10, mutableBoundingBox);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 1, 9, 4, 1, 9, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 7, 1, 9, 7, 1, 9, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 1, 10, 7, 2, 10, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 5, 4, 5, 6, 4, 5, false, random2, MOSS_STONE_SELECTOR);
        this.setBlockState(iSeedReader, blockState, 4, 4, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState2, 7, 4, 5, mutableBoundingBox);
        for (n = 0; n < 4; ++n) {
            this.setBlockState(iSeedReader, blockState3, 5, 0 - n, 6 + n, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState3, 6, 0 - n, 6 + n, mutableBoundingBox);
            this.fillWithAir(iSeedReader, mutableBoundingBox, 5, 0 - n, 7 + n, 6, 0 - n, 9 + n);
        }
        this.fillWithAir(iSeedReader, mutableBoundingBox, 1, -3, 12, 10, -1, 13);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 1, -3, 1, 3, -1, 13);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 1, -3, 1, 9, -1, 5);
        for (n = 1; n <= 13; n += 2) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, -3, n, 1, -2, n, false, random2, MOSS_STONE_SELECTOR);
        }
        for (n = 2; n <= 12; n += 2) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, -1, n, 3, -1, n, false, random2, MOSS_STONE_SELECTOR);
        }
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, -2, 1, 5, -2, 1, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 7, -2, 1, 9, -2, 1, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 6, -3, 1, 6, -3, 1, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 6, -1, 1, 6, -1, 1, false, random2, MOSS_STONE_SELECTOR);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.EAST)).with(TripWireHookBlock.ATTACHED, true), 1, -3, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.WEST)).with(TripWireHookBlock.ATTACHED, true), 4, -3, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.EAST, true)).with(TripWireBlock.WEST, true)).with(TripWireBlock.ATTACHED, true), 2, -3, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.EAST, true)).with(TripWireBlock.WEST, true)).with(TripWireBlock.ATTACHED, true), 3, -3, 8, mutableBoundingBox);
        BlockState blockState5 = (BlockState)((BlockState)Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE)).with(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE);
        this.setBlockState(iSeedReader, blockState5, 5, -3, 7, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState5, 5, -3, 6, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState5, 5, -3, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState5, 5, -3, 4, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState5, 5, -3, 3, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState5, 5, -3, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE)).with(RedstoneWireBlock.WEST, RedstoneSide.SIDE), 5, -3, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.EAST, RedstoneSide.SIDE)).with(RedstoneWireBlock.WEST, RedstoneSide.SIDE), 4, -3, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 3, -3, 1, mutableBoundingBox);
        if (!this.placedTrap1) {
            this.placedTrap1 = this.createDispenser(iSeedReader, mutableBoundingBox, random2, 3, -2, 1, Direction.NORTH, LootTables.CHESTS_JUNGLE_TEMPLE_DISPENSER);
        }
        this.setBlockState(iSeedReader, (BlockState)Blocks.VINE.getDefaultState().with(VineBlock.SOUTH, true), 3, -2, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.NORTH)).with(TripWireHookBlock.ATTACHED, true), 7, -3, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.TRIPWIRE_HOOK.getDefaultState().with(TripWireHookBlock.FACING, Direction.SOUTH)).with(TripWireHookBlock.ATTACHED, true), 7, -3, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.NORTH, true)).with(TripWireBlock.SOUTH, true)).with(TripWireBlock.ATTACHED, true), 7, -3, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.NORTH, true)).with(TripWireBlock.SOUTH, true)).with(TripWireBlock.ATTACHED, true), 7, -3, 3, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)((BlockState)Blocks.TRIPWIRE.getDefaultState().with(TripWireBlock.NORTH, true)).with(TripWireBlock.SOUTH, true)).with(TripWireBlock.ATTACHED, true), 7, -3, 4, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.EAST, RedstoneSide.SIDE)).with(RedstoneWireBlock.WEST, RedstoneSide.SIDE), 8, -3, 6, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.WEST, RedstoneSide.SIDE)).with(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE), 9, -3, 6, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE)).with(RedstoneWireBlock.SOUTH, RedstoneSide.UP), 9, -3, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 9, -3, 4, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState5, 9, -2, 4, mutableBoundingBox);
        if (!this.placedTrap2) {
            this.placedTrap2 = this.createDispenser(iSeedReader, mutableBoundingBox, random2, 9, -2, 3, Direction.WEST, LootTables.CHESTS_JUNGLE_TEMPLE_DISPENSER);
        }
        this.setBlockState(iSeedReader, (BlockState)Blocks.VINE.getDefaultState().with(VineBlock.EAST, true), 8, -1, 3, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)Blocks.VINE.getDefaultState().with(VineBlock.EAST, true), 8, -2, 3, mutableBoundingBox);
        if (!this.placedMainChest) {
            this.placedMainChest = this.generateChest(iSeedReader, mutableBoundingBox, random2, 8, -3, 3, LootTables.CHESTS_JUNGLE_TEMPLE);
        }
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 9, -3, 2, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 8, -3, 1, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 4, -3, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5, -2, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 5, -1, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 6, -3, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 7, -2, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 7, -1, 5, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 8, -3, 5, mutableBoundingBox);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 9, -1, 1, 9, -1, 5, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithAir(iSeedReader, mutableBoundingBox, 8, -3, 8, 10, -1, 10);
        this.setBlockState(iSeedReader, Blocks.CHISELED_STONE_BRICKS.getDefaultState(), 8, -2, 11, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CHISELED_STONE_BRICKS.getDefaultState(), 9, -2, 11, mutableBoundingBox);
        this.setBlockState(iSeedReader, Blocks.CHISELED_STONE_BRICKS.getDefaultState(), 10, -2, 11, mutableBoundingBox);
        BlockState blockState6 = (BlockState)((BlockState)Blocks.LEVER.getDefaultState().with(LeverBlock.HORIZONTAL_FACING, Direction.NORTH)).with(LeverBlock.FACE, AttachFace.WALL);
        this.setBlockState(iSeedReader, blockState6, 8, -2, 12, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState6, 9, -2, 12, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState6, 10, -2, 12, mutableBoundingBox);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 8, -3, 8, 8, -3, 10, false, random2, MOSS_STONE_SELECTOR);
        this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 10, -3, 8, 10, -3, 10, false, random2, MOSS_STONE_SELECTOR);
        this.setBlockState(iSeedReader, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 10, -2, 9, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState5, 8, -2, 9, mutableBoundingBox);
        this.setBlockState(iSeedReader, blockState5, 8, -2, 10, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)((BlockState)((BlockState)((BlockState)Blocks.REDSTONE_WIRE.getDefaultState().with(RedstoneWireBlock.NORTH, RedstoneSide.SIDE)).with(RedstoneWireBlock.SOUTH, RedstoneSide.SIDE)).with(RedstoneWireBlock.EAST, RedstoneSide.SIDE)).with(RedstoneWireBlock.WEST, RedstoneSide.SIDE), 10, -1, 9, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)Blocks.STICKY_PISTON.getDefaultState().with(PistonBlock.FACING, Direction.UP), 9, -2, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)Blocks.STICKY_PISTON.getDefaultState().with(PistonBlock.FACING, Direction.WEST), 10, -2, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)Blocks.STICKY_PISTON.getDefaultState().with(PistonBlock.FACING, Direction.WEST), 10, -1, 8, mutableBoundingBox);
        this.setBlockState(iSeedReader, (BlockState)Blocks.REPEATER.getDefaultState().with(RepeaterBlock.HORIZONTAL_FACING, Direction.NORTH), 10, -2, 10, mutableBoundingBox);
        if (!this.placedHiddenChest) {
            this.placedHiddenChest = this.generateChest(iSeedReader, mutableBoundingBox, random2, 9, -3, 10, LootTables.CHESTS_JUNGLE_TEMPLE);
        }
        return false;
    }

    static class Selector
    extends StructurePiece.BlockSelector {
        private Selector() {
        }

        @Override
        public void selectBlocks(Random random2, int n, int n2, int n3, boolean bl) {
            this.blockstate = random2.nextFloat() < 0.4f ? Blocks.COBBLESTONE.getDefaultState() : Blocks.MOSSY_COBBLESTONE.getDefaultState();
        }
    }
}

