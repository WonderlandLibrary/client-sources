/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class BuriedTreasure {

    public static class Piece
    extends StructurePiece {
        public Piece(BlockPos blockPos) {
            super(IStructurePieceType.BTP, 0);
            this.boundingBox = new MutableBoundingBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }

        public Piece(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.BTP, compoundNBT);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n = iSeedReader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, this.boundingBox.minX, this.boundingBox.minZ);
            BlockPos.Mutable mutable = new BlockPos.Mutable(this.boundingBox.minX, n, this.boundingBox.minZ);
            while (mutable.getY() > 0) {
                BlockState blockState = iSeedReader.getBlockState(mutable);
                BlockState blockState2 = iSeedReader.getBlockState((BlockPos)mutable.down());
                if (blockState2 == Blocks.SANDSTONE.getDefaultState() || blockState2 == Blocks.STONE.getDefaultState() || blockState2 == Blocks.ANDESITE.getDefaultState() || blockState2 == Blocks.GRANITE.getDefaultState() || blockState2 == Blocks.DIORITE.getDefaultState()) {
                    BlockState blockState3 = !blockState.isAir() && !this.func_204295_a(blockState) ? blockState : Blocks.SAND.getDefaultState();
                    for (Direction direction : Direction.values()) {
                        BlockPos blockPos2 = mutable.offset(direction);
                        BlockState blockState4 = iSeedReader.getBlockState(blockPos2);
                        if (!blockState4.isAir() && !this.func_204295_a(blockState4)) continue;
                        BlockPos blockPos3 = blockPos2.down();
                        BlockState blockState5 = iSeedReader.getBlockState(blockPos3);
                        if ((blockState5.isAir() || this.func_204295_a(blockState5)) && direction != Direction.UP) {
                            iSeedReader.setBlockState(blockPos2, blockState2, 3);
                            continue;
                        }
                        iSeedReader.setBlockState(blockPos2, blockState3, 3);
                    }
                    this.boundingBox = new MutableBoundingBox(mutable.getX(), mutable.getY(), mutable.getZ(), mutable.getX(), mutable.getY(), mutable.getZ());
                    return this.generateChest(iSeedReader, mutableBoundingBox, random2, mutable, LootTables.CHESTS_BURIED_TREASURE, null);
                }
                mutable.move(0, -1, 0);
            }
            return true;
        }

        private boolean func_204295_a(BlockState blockState) {
            return blockState == Blocks.WATER.getDefaultState() || blockState == Blocks.LAVA.getDefaultState();
        }
    }
}

