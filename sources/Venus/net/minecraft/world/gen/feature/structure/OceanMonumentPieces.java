/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.ElderGuardianEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanMonumentPieces {

    static class ZDoubleRoomFitHelper
    implements IMonumentRoomFitHelper {
        private ZDoubleRoomFitHelper() {
        }

        @Override
        public boolean fits(RoomDefinition roomDefinition) {
            return roomDefinition.hasOpening[Direction.NORTH.getIndex()] && !roomDefinition.connections[Direction.NORTH.getIndex()].claimed;
        }

        @Override
        public Piece create(Direction direction, RoomDefinition roomDefinition, Random random2) {
            RoomDefinition roomDefinition2 = roomDefinition;
            if (!roomDefinition.hasOpening[Direction.NORTH.getIndex()] || roomDefinition.connections[Direction.NORTH.getIndex()].claimed) {
                roomDefinition2 = roomDefinition.connections[Direction.SOUTH.getIndex()];
            }
            roomDefinition2.claimed = true;
            roomDefinition2.connections[Direction.NORTH.getIndex()].claimed = true;
            return new DoubleZRoom(direction, roomDefinition2);
        }
    }

    static class YZDoubleRoomFitHelper
    implements IMonumentRoomFitHelper {
        private YZDoubleRoomFitHelper() {
        }

        @Override
        public boolean fits(RoomDefinition roomDefinition) {
            if (roomDefinition.hasOpening[Direction.NORTH.getIndex()] && !roomDefinition.connections[Direction.NORTH.getIndex()].claimed && roomDefinition.hasOpening[Direction.UP.getIndex()] && !roomDefinition.connections[Direction.UP.getIndex()].claimed) {
                RoomDefinition roomDefinition2 = roomDefinition.connections[Direction.NORTH.getIndex()];
                return roomDefinition2.hasOpening[Direction.UP.getIndex()] && !roomDefinition2.connections[Direction.UP.getIndex()].claimed;
            }
            return true;
        }

        @Override
        public Piece create(Direction direction, RoomDefinition roomDefinition, Random random2) {
            roomDefinition.claimed = true;
            roomDefinition.connections[Direction.NORTH.getIndex()].claimed = true;
            roomDefinition.connections[Direction.UP.getIndex()].claimed = true;
            roomDefinition.connections[Direction.NORTH.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
            return new DoubleYZRoom(direction, roomDefinition);
        }
    }

    static class YDoubleRoomFitHelper
    implements IMonumentRoomFitHelper {
        private YDoubleRoomFitHelper() {
        }

        @Override
        public boolean fits(RoomDefinition roomDefinition) {
            return roomDefinition.hasOpening[Direction.UP.getIndex()] && !roomDefinition.connections[Direction.UP.getIndex()].claimed;
        }

        @Override
        public Piece create(Direction direction, RoomDefinition roomDefinition, Random random2) {
            roomDefinition.claimed = true;
            roomDefinition.connections[Direction.UP.getIndex()].claimed = true;
            return new DoubleYRoom(direction, roomDefinition);
        }
    }

    static class XYDoubleRoomFitHelper
    implements IMonumentRoomFitHelper {
        private XYDoubleRoomFitHelper() {
        }

        @Override
        public boolean fits(RoomDefinition roomDefinition) {
            if (roomDefinition.hasOpening[Direction.EAST.getIndex()] && !roomDefinition.connections[Direction.EAST.getIndex()].claimed && roomDefinition.hasOpening[Direction.UP.getIndex()] && !roomDefinition.connections[Direction.UP.getIndex()].claimed) {
                RoomDefinition roomDefinition2 = roomDefinition.connections[Direction.EAST.getIndex()];
                return roomDefinition2.hasOpening[Direction.UP.getIndex()] && !roomDefinition2.connections[Direction.UP.getIndex()].claimed;
            }
            return true;
        }

        @Override
        public Piece create(Direction direction, RoomDefinition roomDefinition, Random random2) {
            roomDefinition.claimed = true;
            roomDefinition.connections[Direction.EAST.getIndex()].claimed = true;
            roomDefinition.connections[Direction.UP.getIndex()].claimed = true;
            roomDefinition.connections[Direction.EAST.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
            return new DoubleXYRoom(direction, roomDefinition);
        }
    }

    static class XDoubleRoomFitHelper
    implements IMonumentRoomFitHelper {
        private XDoubleRoomFitHelper() {
        }

        @Override
        public boolean fits(RoomDefinition roomDefinition) {
            return roomDefinition.hasOpening[Direction.EAST.getIndex()] && !roomDefinition.connections[Direction.EAST.getIndex()].claimed;
        }

        @Override
        public Piece create(Direction direction, RoomDefinition roomDefinition, Random random2) {
            roomDefinition.claimed = true;
            roomDefinition.connections[Direction.EAST.getIndex()].claimed = true;
            return new DoubleXRoom(direction, roomDefinition);
        }
    }

    public static class WingRoom
    extends Piece {
        private int mainDesign;

        public WingRoom(Direction direction, MutableBoundingBox mutableBoundingBox, int n) {
            super(IStructurePieceType.OMWR, direction, mutableBoundingBox);
            this.mainDesign = n & 1;
        }

        public WingRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMWR, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.mainDesign == 0) {
                int n;
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10 - n, 3 - n, 20 - n, 12 + n, 3 - n, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 0, 6, 15, 0, 16, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 0, 6, 6, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 16, 0, 6, 16, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 7, 7, 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 1, 7, 15, 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 6, 9, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 13, 1, 6, 15, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 1, 7, 9, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 13, 1, 7, 14, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 0, 5, 13, 0, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 0, 7, 12, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 0, 10, 8, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 14, 0, 10, 14, 0, 12, DARK_PRISMARINE, DARK_PRISMARINE, true);
                for (n = 18; n >= 7; n -= 3) {
                    this.setBlockState(iSeedReader, SEA_LANTERN, 6, 3, n, mutableBoundingBox);
                    this.setBlockState(iSeedReader, SEA_LANTERN, 16, 3, n, mutableBoundingBox);
                }
                this.setBlockState(iSeedReader, SEA_LANTERN, 10, 0, 10, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 12, 0, 10, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 10, 0, 12, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 12, 0, 12, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 8, 3, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 14, 3, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 4, 2, 4, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 4, 1, 4, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 4, 0, 4, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 18, 2, 4, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 18, 1, 4, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 18, 0, 4, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 4, 2, 18, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 4, 1, 18, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 4, 0, 18, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 18, 2, 18, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 18, 1, 18, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 18, 0, 18, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 9, 7, 20, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 13, 7, 20, mutableBoundingBox);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 0, 21, 7, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 0, 21, 16, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.spawnElder(iSeedReader, mutableBoundingBox, 11, 2, 1);
            } else if (this.mainDesign == 1) {
                int n;
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 3, 18, 13, 3, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 0, 18, 9, 2, 18, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 13, 0, 18, 13, 2, 18, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                int n2 = 9;
                int n3 = 20;
                int n4 = 5;
                for (n = 0; n < 2; ++n) {
                    this.setBlockState(iSeedReader, BRICKS_PRISMARINE, n2, 6, 20, mutableBoundingBox);
                    this.setBlockState(iSeedReader, SEA_LANTERN, n2, 5, 20, mutableBoundingBox);
                    this.setBlockState(iSeedReader, BRICKS_PRISMARINE, n2, 4, 20, mutableBoundingBox);
                    n2 = 13;
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 3, 7, 15, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                n2 = 10;
                for (n = 0; n < 2; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, 0, 10, n2, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, 0, 12, n2, 6, 12, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.setBlockState(iSeedReader, SEA_LANTERN, n2, 0, 10, mutableBoundingBox);
                    this.setBlockState(iSeedReader, SEA_LANTERN, n2, 0, 12, mutableBoundingBox);
                    this.setBlockState(iSeedReader, SEA_LANTERN, n2, 4, 10, mutableBoundingBox);
                    this.setBlockState(iSeedReader, SEA_LANTERN, n2, 4, 12, mutableBoundingBox);
                    n2 = 12;
                }
                n2 = 8;
                for (n = 0; n < 2; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, 0, 7, n2, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, 0, 14, n2, 2, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    n2 = 14;
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 3, 8, 8, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 14, 3, 8, 14, 3, 13, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.spawnElder(iSeedReader, mutableBoundingBox, 11, 5, 0);
            }
            return false;
        }
    }

    public static class SimpleTopRoom
    extends Piece {
        public SimpleTopRoom(Direction direction, RoomDefinition roomDefinition) {
            super(IStructurePieceType.OMSIMPLET, 1, direction, roomDefinition, 1, 1, 1);
        }

        public SimpleTopRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMSIMPLET, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.roomDefinition.index / 25 > 0) {
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.getIndex()]);
            }
            if (this.roomDefinition.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 4, 1, 6, 4, 6, ROUGH_PRISMARINE);
            }
            for (int i = 1; i <= 6; ++i) {
                for (int j = 1; j <= 6; ++j) {
                    if (random2.nextInt(3) == 0) continue;
                    int n = 2 + (random2.nextInt(4) == 0 ? 0 : 1);
                    BlockState blockState = Blocks.WET_SPONGE.getDefaultState();
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, i, n, j, i, 3, j, blockState, blockState, true);
                }
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
            if (this.roomDefinition.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 2, 0);
            }
            return false;
        }
    }

    public static class SimpleRoom
    extends Piece {
        private int mainDesign;

        public SimpleRoom(Direction direction, RoomDefinition roomDefinition, Random random2) {
            super(IStructurePieceType.OMSIMPLE, 1, direction, roomDefinition, 1, 1, 1);
            this.mainDesign = random2.nextInt(3);
        }

        public SimpleRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMSIMPLE, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            boolean bl;
            if (this.roomDefinition.index / 25 > 0) {
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.getIndex()]);
            }
            if (this.roomDefinition.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 4, 1, 6, 4, 6, ROUGH_PRISMARINE);
            }
            boolean bl2 = bl = this.mainDesign != 0 && random2.nextBoolean() && !this.roomDefinition.hasOpening[Direction.DOWN.getIndex()] && !this.roomDefinition.hasOpening[Direction.UP.getIndex()] && this.roomDefinition.countOpenings() > 1;
            if (this.mainDesign == 0) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 0, 2, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 2, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 2, 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 0, 2, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.setBlockState(iSeedReader, SEA_LANTERN, 1, 2, 1, mutableBoundingBox);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 0, 7, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 3, 0, 7, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 0, 7, 2, 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 0, 6, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.setBlockState(iSeedReader, SEA_LANTERN, 6, 2, 1, mutableBoundingBox);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 5, 2, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 5, 2, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 5, 0, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 7, 2, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.setBlockState(iSeedReader, SEA_LANTERN, 1, 2, 6, mutableBoundingBox);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 5, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 3, 5, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 5, 7, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 7, 6, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.setBlockState(iSeedReader, SEA_LANTERN, 6, 2, 6, mutableBoundingBox);
                if (this.roomDefinition.hasOpening[Direction.SOUTH.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 3, 0, 4, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 3, 0, 4, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 2, 0, 4, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 1, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                if (this.roomDefinition.hasOpening[Direction.NORTH.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 3, 7, 4, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 3, 6, 4, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 2, 7, 4, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 6, 4, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                if (this.roomDefinition.hasOpening[Direction.WEST.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 3, 0, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 3, 1, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 3, 0, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 3, 1, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                if (this.roomDefinition.hasOpening[Direction.EAST.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 3, 3, 7, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 3, 3, 7, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 3, 7, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 3, 7, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
            } else if (this.mainDesign == 1) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 1, 2, 2, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 1, 5, 2, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 5, 5, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 2, 5, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.setBlockState(iSeedReader, SEA_LANTERN, 2, 2, 2, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 2, 2, 5, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 5, 2, 5, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 5, 2, 2, mutableBoundingBox);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 0, 1, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 1, 0, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 7, 1, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 6, 0, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 7, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 6, 7, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 0, 7, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 1, 7, 3, 1, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.setBlockState(iSeedReader, ROUGH_PRISMARINE, 1, 2, 0, mutableBoundingBox);
                this.setBlockState(iSeedReader, ROUGH_PRISMARINE, 0, 2, 1, mutableBoundingBox);
                this.setBlockState(iSeedReader, ROUGH_PRISMARINE, 1, 2, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, ROUGH_PRISMARINE, 0, 2, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, ROUGH_PRISMARINE, 6, 2, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, ROUGH_PRISMARINE, 7, 2, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, ROUGH_PRISMARINE, 6, 2, 0, mutableBoundingBox);
                this.setBlockState(iSeedReader, ROUGH_PRISMARINE, 7, 2, 1, mutableBoundingBox);
                if (!this.roomDefinition.hasOpening[Direction.SOUTH.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 0, 6, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                if (!this.roomDefinition.hasOpening[Direction.NORTH.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 7, 6, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                if (!this.roomDefinition.hasOpening[Direction.WEST.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 1, 0, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 1, 0, 2, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 1, 0, 1, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                if (!this.roomDefinition.hasOpening[Direction.EAST.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 3, 1, 7, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 1, 7, 2, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 1, 7, 1, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
            } else if (this.mainDesign == 2) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 0, 6, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 7, 6, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 0, 7, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 0, 6, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 7, 6, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 7, 6, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 3, 0, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 3, 7, 2, 4, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 2, 0, DARK_PRISMARINE, DARK_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 7, 4, 2, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
                if (this.roomDefinition.hasOpening[Direction.SOUTH.getIndex()]) {
                    this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 2, 0);
                }
                if (this.roomDefinition.hasOpening[Direction.NORTH.getIndex()]) {
                    this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 7, 4, 2, 7);
                }
                if (this.roomDefinition.hasOpening[Direction.WEST.getIndex()]) {
                    this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 3, 0, 2, 4);
                }
                if (this.roomDefinition.hasOpening[Direction.EAST.getIndex()]) {
                    this.makeOpening(iSeedReader, mutableBoundingBox, 7, 1, 3, 7, 2, 4);
                }
            }
            if (bl) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 3, 4, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 2, 3, 4, 2, 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 3, 3, 4, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            }
            return false;
        }
    }

    static class RoomDefinition {
        private final int index;
        private final RoomDefinition[] connections = new RoomDefinition[6];
        private final boolean[] hasOpening = new boolean[6];
        private boolean claimed;
        private boolean isSource;
        private int scanIndex;

        public RoomDefinition(int n) {
            this.index = n;
        }

        public void setConnection(Direction direction, RoomDefinition roomDefinition) {
            this.connections[direction.getIndex()] = roomDefinition;
            roomDefinition.connections[direction.getOpposite().getIndex()] = this;
        }

        public void updateOpenings() {
            for (int i = 0; i < 6; ++i) {
                this.hasOpening[i] = this.connections[i] != null;
            }
        }

        public boolean findSource(int n) {
            if (this.isSource) {
                return false;
            }
            this.scanIndex = n;
            for (int i = 0; i < 6; ++i) {
                if (this.connections[i] == null || !this.hasOpening[i] || this.connections[i].scanIndex == n || !this.connections[i].findSource(n)) continue;
                return false;
            }
            return true;
        }

        public boolean isSpecial() {
            return this.index >= 75;
        }

        public int countOpenings() {
            int n = 0;
            for (int i = 0; i < 6; ++i) {
                if (!this.hasOpening[i]) continue;
                ++n;
            }
            return n;
        }
    }

    public static abstract class Piece
    extends StructurePiece {
        protected static final BlockState ROUGH_PRISMARINE = Blocks.PRISMARINE.getDefaultState();
        protected static final BlockState BRICKS_PRISMARINE = Blocks.PRISMARINE_BRICKS.getDefaultState();
        protected static final BlockState DARK_PRISMARINE = Blocks.DARK_PRISMARINE.getDefaultState();
        protected static final BlockState DOT_DECO_DATA = BRICKS_PRISMARINE;
        protected static final BlockState SEA_LANTERN = Blocks.SEA_LANTERN.getDefaultState();
        protected static final BlockState WATER = Blocks.WATER.getDefaultState();
        protected static final Set<Block> field_212180_g = ((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().add(Blocks.ICE)).add(Blocks.PACKED_ICE)).add(Blocks.BLUE_ICE)).add(WATER.getBlock())).build();
        protected static final int GRIDROOM_SOURCE_INDEX = Piece.getRoomIndex(2, 0, 0);
        protected static final int GRIDROOM_TOP_CONNECT_INDEX = Piece.getRoomIndex(2, 2, 0);
        protected static final int GRIDROOM_LEFTWING_CONNECT_INDEX = Piece.getRoomIndex(0, 1, 0);
        protected static final int GRIDROOM_RIGHTWING_CONNECT_INDEX = Piece.getRoomIndex(4, 1, 0);
        protected RoomDefinition roomDefinition;

        protected static final int getRoomIndex(int n, int n2, int n3) {
            return n2 * 25 + n3 * 5 + n;
        }

        public Piece(IStructurePieceType iStructurePieceType, int n) {
            super(iStructurePieceType, n);
        }

        public Piece(IStructurePieceType iStructurePieceType, Direction direction, MutableBoundingBox mutableBoundingBox) {
            super(iStructurePieceType, 1);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        protected Piece(IStructurePieceType iStructurePieceType, int n, Direction direction, RoomDefinition roomDefinition, int n2, int n3, int n4) {
            super(iStructurePieceType, n);
            this.setCoordBaseMode(direction);
            this.roomDefinition = roomDefinition;
            int n5 = roomDefinition.index;
            int n6 = n5 % 5;
            int n7 = n5 / 5 % 5;
            int n8 = n5 / 25;
            this.boundingBox = direction != Direction.NORTH && direction != Direction.SOUTH ? new MutableBoundingBox(0, 0, 0, n4 * 8 - 1, n3 * 4 - 1, n2 * 8 - 1) : new MutableBoundingBox(0, 0, 0, n2 * 8 - 1, n3 * 4 - 1, n4 * 8 - 1);
            switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
                case 1: {
                    this.boundingBox.offset(n6 * 8, n8 * 4, -(n7 + n4) * 8 + 1);
                    break;
                }
                case 2: {
                    this.boundingBox.offset(n6 * 8, n8 * 4, n7 * 8);
                    break;
                }
                case 3: {
                    this.boundingBox.offset(-(n7 + n4) * 8 + 1, n8 * 4, n6 * 8);
                    break;
                }
                default: {
                    this.boundingBox.offset(n7 * 8, n8 * 4, n6 * 8);
                }
            }
        }

        public Piece(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
            super(iStructurePieceType, compoundNBT);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
        }

        protected void makeOpening(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4, int n5, int n6) {
            for (int i = n2; i <= n5; ++i) {
                for (int j = n; j <= n4; ++j) {
                    for (int k = n3; k <= n6; ++k) {
                        BlockState blockState = this.getBlockStateFromPos(iSeedReader, j, i, k, mutableBoundingBox);
                        if (field_212180_g.contains(blockState.getBlock())) continue;
                        if (this.getYWithOffset(i) >= iSeedReader.getSeaLevel() && blockState != WATER) {
                            this.setBlockState(iSeedReader, Blocks.AIR.getDefaultState(), j, i, k, mutableBoundingBox);
                            continue;
                        }
                        this.setBlockState(iSeedReader, WATER, j, i, k, mutableBoundingBox);
                    }
                }
            }
        }

        protected void generateDefaultFloor(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, boolean bl) {
            if (bl) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 0, 0, n2 + 0, n + 2, 0, n2 + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 5, 0, n2 + 0, n + 8 - 1, 0, n2 + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 3, 0, n2 + 0, n + 4, 0, n2 + 2, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 3, 0, n2 + 5, n + 4, 0, n2 + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 3, 0, n2 + 2, n + 4, 0, n2 + 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 3, 0, n2 + 5, n + 4, 0, n2 + 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 2, 0, n2 + 3, n + 2, 0, n2 + 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 5, 0, n2 + 3, n + 5, 0, n2 + 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            } else {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 0, 0, n2 + 0, n + 8 - 1, 0, n2 + 8 - 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            }
        }

        protected void generateBoxOnFillOnly(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4, int n5, int n6, BlockState blockState) {
            for (int i = n2; i <= n5; ++i) {
                for (int j = n; j <= n4; ++j) {
                    for (int k = n3; k <= n6; ++k) {
                        if (this.getBlockStateFromPos(iSeedReader, j, i, k, mutableBoundingBox) != WATER) continue;
                        this.setBlockState(iSeedReader, blockState, j, i, k, mutableBoundingBox);
                    }
                }
            }
        }

        protected boolean doesChunkIntersect(MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4) {
            int n5 = this.getXWithOffset(n, n2);
            int n6 = this.getZWithOffset(n, n2);
            int n7 = this.getXWithOffset(n3, n4);
            int n8 = this.getZWithOffset(n3, n4);
            return mutableBoundingBox.intersectsWith(Math.min(n5, n7), Math.min(n6, n8), Math.max(n5, n7), Math.max(n6, n8));
        }

        protected boolean spawnElder(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3) {
            int n4;
            int n5;
            int n6 = this.getXWithOffset(n, n3);
            if (mutableBoundingBox.isVecInside(new BlockPos(n6, n5 = this.getYWithOffset(n2), n4 = this.getZWithOffset(n, n3)))) {
                ElderGuardianEntity elderGuardianEntity = EntityType.ELDER_GUARDIAN.create(iSeedReader.getWorld());
                elderGuardianEntity.heal(elderGuardianEntity.getMaxHealth());
                elderGuardianEntity.setLocationAndAngles((double)n6 + 0.5, n5, (double)n4 + 0.5, 0.0f, 0.0f);
                elderGuardianEntity.onInitialSpawn(iSeedReader, iSeedReader.getDifficultyForLocation(elderGuardianEntity.getPosition()), SpawnReason.STRUCTURE, null, null);
                iSeedReader.func_242417_l(elderGuardianEntity);
                return false;
            }
            return true;
        }
    }

    public static class Penthouse
    extends Piece {
        public Penthouse(Direction direction, MutableBoundingBox mutableBoundingBox) {
            super(IStructurePieceType.OMPENTHOUSE, direction, mutableBoundingBox);
        }

        public Penthouse(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMPENTHOUSE, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, -1, 2, 11, -1, 11, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, -1, 0, 1, -1, 11, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, -1, 0, 13, -1, 11, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, -1, 0, 11, -1, 1, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, -1, 12, 11, -1, 13, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 0, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 13, 0, 0, 13, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 0, 0, 12, 0, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 0, 13, 12, 0, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            for (n = 2; n <= 11; n += 3) {
                this.setBlockState(iSeedReader, SEA_LANTERN, 0, 0, n, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 13, 0, n, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, n, 0, 0, mutableBoundingBox);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 0, 3, 4, 0, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 0, 3, 11, 0, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 0, 9, 9, 0, 11, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 5, 0, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 8, 0, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 10, 0, 10, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 3, 0, 10, mutableBoundingBox);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 0, 3, 3, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 0, 3, 10, 0, 7, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 0, 10, 7, 0, 10, DARK_PRISMARINE, DARK_PRISMARINE, true);
            n = 3;
            for (int i = 0; i < 2; ++i) {
                for (int j = 2; j <= 8; j += 3) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, 0, j, n, 2, j, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                n = 10;
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 0, 10, 5, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 0, 10, 8, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, -1, 7, 7, -1, 8, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.makeOpening(iSeedReader, mutableBoundingBox, 6, -1, 3, 7, -1, 4);
            this.spawnElder(iSeedReader, mutableBoundingBox, 6, 1, 1);
            return false;
        }
    }

    public static class MonumentCoreRoom
    extends Piece {
        public MonumentCoreRoom(Direction direction, RoomDefinition roomDefinition) {
            super(IStructurePieceType.OMCR, 1, direction, roomDefinition, 2, 2, 2);
        }

        public MonumentCoreRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMCR, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2;
            this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 8, 0, 14, 8, 14, ROUGH_PRISMARINE);
            int n3 = 7;
            BlockState blockState = BRICKS_PRISMARINE;
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 7, 0, 0, 7, 15, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 7, 0, 15, 7, 15, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 7, 0, 15, 7, 0, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 7, 15, 14, 7, 15, blockState, blockState, true);
            for (n2 = 1; n2 <= 6; ++n2) {
                blockState = BRICKS_PRISMARINE;
                if (n2 == 2 || n2 == 6) {
                    blockState = ROUGH_PRISMARINE;
                }
                for (n = 0; n <= 15; n += 15) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n2, 0, n, n2, 1, blockState, blockState, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n2, 6, n, n2, 9, blockState, blockState, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n2, 14, n, n2, 15, blockState, blockState, true);
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, n2, 0, 1, n2, 0, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, n2, 0, 9, n2, 0, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 14, n2, 0, 14, n2, 0, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, n2, 15, 14, n2, 15, blockState, blockState, true);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 3, 6, 9, 6, 9, DARK_PRISMARINE, DARK_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 4, 7, 8, 5, 8, Blocks.GOLD_BLOCK.getDefaultState(), Blocks.GOLD_BLOCK.getDefaultState(), true);
            for (n2 = 3; n2 <= 6; n2 += 3) {
                for (n = 6; n <= 9; n += 3) {
                    this.setBlockState(iSeedReader, SEA_LANTERN, n, n2, 6, mutableBoundingBox);
                    this.setBlockState(iSeedReader, SEA_LANTERN, n, n2, 9, mutableBoundingBox);
                }
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 6, 5, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 9, 5, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 1, 6, 10, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 1, 9, 10, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 5, 6, 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 1, 5, 9, 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 10, 6, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 1, 10, 9, 2, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 5, 5, 6, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 10, 5, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 2, 5, 10, 6, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 2, 10, 10, 6, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 7, 1, 5, 7, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 7, 1, 10, 7, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 7, 9, 5, 7, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 7, 9, 10, 7, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 7, 5, 6, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 7, 10, 6, 7, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 7, 5, 14, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 7, 10, 14, 7, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 1, 2, 2, 1, 3, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 2, 3, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 13, 1, 2, 13, 1, 3, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 1, 2, 12, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 1, 12, 2, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 13, 3, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 13, 1, 12, 13, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 1, 13, 12, 1, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            return false;
        }
    }

    public static class MonumentBuilding
    extends Piece {
        private RoomDefinition sourceRoom;
        private RoomDefinition coreRoom;
        private final List<Piece> childPieces = Lists.newArrayList();

        public MonumentBuilding(Random random2, int n, int n2, Direction direction) {
            super(IStructurePieceType.OMB, 0);
            this.setCoordBaseMode(direction);
            Direction direction2 = this.getCoordBaseMode();
            this.boundingBox = direction2.getAxis() == Direction.Axis.Z ? new MutableBoundingBox(n, 39, n2, n + 58 - 1, 61, n2 + 58 - 1) : new MutableBoundingBox(n, 39, n2, n + 58 - 1, 61, n2 + 58 - 1);
            List<RoomDefinition> list = this.generateRoomGraph(random2);
            this.sourceRoom.claimed = true;
            this.childPieces.add(new EntryRoom(direction2, this.sourceRoom));
            this.childPieces.add(new MonumentCoreRoom(direction2, this.coreRoom));
            ArrayList<IMonumentRoomFitHelper> arrayList = Lists.newArrayList();
            arrayList.add(new XYDoubleRoomFitHelper());
            arrayList.add(new YZDoubleRoomFitHelper());
            arrayList.add(new ZDoubleRoomFitHelper());
            arrayList.add(new XDoubleRoomFitHelper());
            arrayList.add(new YDoubleRoomFitHelper());
            arrayList.add(new FitSimpleRoomTopHelper());
            arrayList.add(new FitSimpleRoomHelper());
            block0: for (RoomDefinition roomDefinition : list) {
                if (roomDefinition.claimed || roomDefinition.isSpecial()) continue;
                for (IMonumentRoomFitHelper object22 : arrayList) {
                    if (!object22.fits(roomDefinition)) continue;
                    this.childPieces.add(object22.create(direction2, roomDefinition, random2));
                    continue block0;
                }
            }
            int n3 = this.boundingBox.minY;
            int n4 = this.getXWithOffset(9, 22);
            int n5 = this.getZWithOffset(9, 22);
            for (Piece piece : this.childPieces) {
                piece.getBoundingBox().offset(n4, n3, n5);
            }
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.createProper(this.getXWithOffset(1, 1), this.getYWithOffset(1), this.getZWithOffset(1, 1), this.getXWithOffset(23, 21), this.getYWithOffset(8), this.getZWithOffset(23, 21));
            MutableBoundingBox mutableBoundingBox2 = MutableBoundingBox.createProper(this.getXWithOffset(34, 1), this.getYWithOffset(1), this.getZWithOffset(34, 1), this.getXWithOffset(56, 21), this.getYWithOffset(8), this.getZWithOffset(56, 21));
            MutableBoundingBox mutableBoundingBox3 = MutableBoundingBox.createProper(this.getXWithOffset(22, 22), this.getYWithOffset(13), this.getZWithOffset(22, 22), this.getXWithOffset(35, 35), this.getYWithOffset(17), this.getZWithOffset(35, 35));
            int n6 = random2.nextInt();
            this.childPieces.add(new WingRoom(direction2, mutableBoundingBox, n6++));
            this.childPieces.add(new WingRoom(direction2, mutableBoundingBox2, n6++));
            this.childPieces.add(new Penthouse(direction2, mutableBoundingBox3));
        }

        public MonumentBuilding(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMB, compoundNBT);
        }

        private List<RoomDefinition> generateRoomGraph(Random random2) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            RoomDefinition[] roomDefinitionArray = new RoomDefinition[75];
            for (n7 = 0; n7 < 5; ++n7) {
                for (n6 = 0; n6 < 4; ++n6) {
                    n5 = 0;
                    n4 = MonumentBuilding.getRoomIndex(n7, 0, n6);
                    roomDefinitionArray[n4] = new RoomDefinition(n4);
                }
            }
            for (n7 = 0; n7 < 5; ++n7) {
                for (n6 = 0; n6 < 4; ++n6) {
                    n5 = 1;
                    n4 = MonumentBuilding.getRoomIndex(n7, 1, n6);
                    roomDefinitionArray[n4] = new RoomDefinition(n4);
                }
            }
            for (n7 = 1; n7 < 4; ++n7) {
                for (n6 = 0; n6 < 2; ++n6) {
                    n5 = 2;
                    n4 = MonumentBuilding.getRoomIndex(n7, 2, n6);
                    roomDefinitionArray[n4] = new RoomDefinition(n4);
                }
            }
            this.sourceRoom = roomDefinitionArray[GRIDROOM_SOURCE_INDEX];
            for (n7 = 0; n7 < 5; ++n7) {
                for (n6 = 0; n6 < 5; ++n6) {
                    for (n5 = 0; n5 < 3; ++n5) {
                        n4 = MonumentBuilding.getRoomIndex(n7, n5, n6);
                        if (roomDefinitionArray[n4] == null) continue;
                        for (Direction object : Direction.values()) {
                            int n8;
                            n3 = n7 + object.getXOffset();
                            n2 = n5 + object.getYOffset();
                            n = n6 + object.getZOffset();
                            if (n3 < 0 || n3 >= 5 || n < 0 || n >= 5 || n2 < 0 || n2 >= 3 || roomDefinitionArray[n8 = MonumentBuilding.getRoomIndex(n3, n2, n)] == null) continue;
                            if (n == n6) {
                                roomDefinitionArray[n4].setConnection(object, roomDefinitionArray[n8]);
                                continue;
                            }
                            roomDefinitionArray[n4].setConnection(object.getOpposite(), roomDefinitionArray[n8]);
                        }
                    }
                }
            }
            RoomDefinition roomDefinition = new RoomDefinition(1003);
            RoomDefinition roomDefinition2 = new RoomDefinition(1001);
            RoomDefinition roomDefinition3 = new RoomDefinition(1002);
            roomDefinitionArray[GRIDROOM_TOP_CONNECT_INDEX].setConnection(Direction.UP, roomDefinition);
            roomDefinitionArray[GRIDROOM_LEFTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, roomDefinition2);
            roomDefinitionArray[GRIDROOM_RIGHTWING_CONNECT_INDEX].setConnection(Direction.SOUTH, roomDefinition3);
            roomDefinition.claimed = true;
            roomDefinition2.claimed = true;
            roomDefinition3.claimed = true;
            this.sourceRoom.isSource = true;
            this.coreRoom = roomDefinitionArray[MonumentBuilding.getRoomIndex(random2.nextInt(4), 0, 2)];
            this.coreRoom.claimed = true;
            this.coreRoom.connections[Direction.EAST.getIndex()].claimed = true;
            this.coreRoom.connections[Direction.NORTH.getIndex()].claimed = true;
            this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.NORTH.getIndex()].claimed = true;
            this.coreRoom.connections[Direction.UP.getIndex()].claimed = true;
            this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
            this.coreRoom.connections[Direction.NORTH.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
            this.coreRoom.connections[Direction.EAST.getIndex()].connections[Direction.NORTH.getIndex()].connections[Direction.UP.getIndex()].claimed = true;
            ArrayList<RoomDefinition> arrayList = Lists.newArrayList();
            for (RoomDefinition roomDefinition4 : roomDefinitionArray) {
                if (roomDefinition4 == null) continue;
                roomDefinition4.updateOpenings();
                arrayList.add(roomDefinition4);
            }
            roomDefinition.updateOpenings();
            Collections.shuffle(arrayList, random2);
            int n9 = 1;
            for (RoomDefinition roomDefinition4 : arrayList) {
                int n10 = 0;
                for (n3 = 0; n10 < 2 && n3 < 5; ++n3) {
                    n2 = random2.nextInt(6);
                    if (!roomDefinition4.hasOpening[n2]) continue;
                    n = Direction.byIndex(n2).getOpposite().getIndex();
                    roomDefinition4.hasOpening[n2] = false;
                    roomDefinition4.connections[n2].hasOpening[n] = false;
                    if (roomDefinition4.findSource(n9++) && roomDefinition4.connections[n2].findSource(n9++)) {
                        ++n10;
                        continue;
                    }
                    roomDefinition4.hasOpening[n2] = true;
                    roomDefinition4.connections[n2].hasOpening[n] = true;
                }
            }
            arrayList.add(roomDefinition);
            arrayList.add(roomDefinition2);
            arrayList.add(roomDefinition3);
            return arrayList;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2 = Math.max(iSeedReader.getSeaLevel(), 64) - this.boundingBox.minY;
            this.makeOpening(iSeedReader, mutableBoundingBox, 0, 0, 0, 58, n2, 58);
            this.generateWing(false, 0, iSeedReader, random2, mutableBoundingBox);
            this.generateWing(true, 33, iSeedReader, random2, mutableBoundingBox);
            this.generateEntranceArchs(iSeedReader, random2, mutableBoundingBox);
            this.generateEntranceWall(iSeedReader, random2, mutableBoundingBox);
            this.generateRoofPiece(iSeedReader, random2, mutableBoundingBox);
            this.generateLowerWall(iSeedReader, random2, mutableBoundingBox);
            this.generateMiddleWall(iSeedReader, random2, mutableBoundingBox);
            this.generateUpperWall(iSeedReader, random2, mutableBoundingBox);
            for (n = 0; n < 7; ++n) {
                int n3 = 0;
                while (n3 < 7) {
                    if (n3 == 0 && n == 3) {
                        n3 = 6;
                    }
                    int n4 = n * 9;
                    int n5 = n3 * 9;
                    for (int i = 0; i < 4; ++i) {
                        for (int j = 0; j < 4; ++j) {
                            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, n4 + i, 0, n5 + j, mutableBoundingBox);
                            this.replaceAirAndLiquidDownwards(iSeedReader, BRICKS_PRISMARINE, n4 + i, -1, n5 + j, mutableBoundingBox);
                        }
                    }
                    if (n != 0 && n != 6) {
                        n3 += 6;
                        continue;
                    }
                    ++n3;
                }
            }
            for (n = 0; n < 5; ++n) {
                this.makeOpening(iSeedReader, mutableBoundingBox, -1 - n, 0 + n * 2, -1 - n, -1 - n, 23, 58 + n);
                this.makeOpening(iSeedReader, mutableBoundingBox, 58 + n, 0 + n * 2, -1 - n, 58 + n, 23, 58 + n);
                this.makeOpening(iSeedReader, mutableBoundingBox, 0 - n, 0 + n * 2, -1 - n, 57 + n, 23, -1 - n);
                this.makeOpening(iSeedReader, mutableBoundingBox, 0 - n, 0 + n * 2, 58 + n, 57 + n, 23, 58 + n);
            }
            for (Piece piece : this.childPieces) {
                if (!piece.getBoundingBox().intersectsWith(mutableBoundingBox)) continue;
                piece.func_230383_a_(iSeedReader, structureManager, chunkGenerator, random2, mutableBoundingBox, chunkPos, blockPos);
            }
            return false;
        }

        private void generateWing(boolean bl, int n, ISeedReader iSeedReader, Random random2, MutableBoundingBox mutableBoundingBox) {
            int n2 = 24;
            if (this.doesChunkIntersect(mutableBoundingBox, n, 0, n + 23, 1)) {
                int n3;
                int n4;
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 0, 0, 0, n + 24, 0, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, n + 0, 1, 0, n + 24, 10, 20);
                for (n4 = 0; n4 < 4; ++n4) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + n4, n4 + 1, n4, n + n4, n4 + 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + n4 + 7, n4 + 5, n4 + 7, n + n4 + 7, n4 + 5, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 17 - n4, n4 + 5, n4 + 7, n + 17 - n4, n4 + 5, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 24 - n4, n4 + 1, n4, n + 24 - n4, n4 + 1, 20, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + n4 + 1, n4 + 1, n4, n + 23 - n4, n4 + 1, n4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + n4 + 8, n4 + 5, n4 + 7, n + 16 - n4, n4 + 5, n4 + 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 4, 4, 4, n + 6, 4, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 7, 4, 4, n + 17, 4, 6, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 18, 4, 4, n + 20, 4, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 11, 8, 11, n + 13, 8, 20, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.setBlockState(iSeedReader, DOT_DECO_DATA, n + 12, 9, 12, mutableBoundingBox);
                this.setBlockState(iSeedReader, DOT_DECO_DATA, n + 12, 9, 15, mutableBoundingBox);
                this.setBlockState(iSeedReader, DOT_DECO_DATA, n + 12, 9, 18, mutableBoundingBox);
                n4 = n + (bl ? 19 : 5);
                int n5 = n + (bl ? 5 : 19);
                for (n3 = 20; n3 >= 5; n3 -= 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n4, 5, n3, mutableBoundingBox);
                }
                for (n3 = 19; n3 >= 7; n3 -= 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n5, 5, n3, mutableBoundingBox);
                }
                for (n3 = 0; n3 < 4; ++n3) {
                    int n6 = bl ? n + 24 - (17 - n3 * 3) : n + 17 - n3 * 3;
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n6, 5, 5, mutableBoundingBox);
                }
                this.setBlockState(iSeedReader, DOT_DECO_DATA, n5, 5, 5, mutableBoundingBox);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 11, 1, 12, n + 13, 7, 12, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 12, 1, 11, n + 12, 7, 13, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            }
        }

        private void generateEntranceArchs(ISeedReader iSeedReader, Random random2, MutableBoundingBox mutableBoundingBox) {
            if (this.doesChunkIntersect(mutableBoundingBox, 22, 5, 35, 0)) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 25, 0, 0, 32, 8, 20);
                for (int i = 0; i < 4; ++i) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 24, 2, 5 + i * 4, 24, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 22, 4, 5 + i * 4, 23, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 25, 5, 5 + i * 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 26, 6, 5 + i * 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, SEA_LANTERN, 26, 5, 5 + i * 4, mutableBoundingBox);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 33, 2, 5 + i * 4, 33, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 34, 4, 5 + i * 4, 35, 4, 5 + i * 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 32, 5, 5 + i * 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 31, 6, 5 + i * 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, SEA_LANTERN, 31, 5, 5 + i * 4, mutableBoundingBox);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 27, 6, 5 + i * 4, 30, 6, 5 + i * 4, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                }
            }
        }

        private void generateEntranceWall(ISeedReader iSeedReader, Random random2, MutableBoundingBox mutableBoundingBox) {
            if (this.doesChunkIntersect(mutableBoundingBox, 15, 20, 42, 0)) {
                int n;
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 0, 21, 42, 0, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 26, 1, 21, 31, 3, 21);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 21, 12, 21, 36, 12, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 17, 11, 21, 40, 11, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 16, 10, 21, 41, 10, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 7, 21, 42, 9, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 16, 6, 21, 41, 6, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 17, 5, 21, 40, 5, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 21, 4, 21, 36, 4, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 22, 3, 21, 26, 3, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 31, 3, 21, 35, 3, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 23, 2, 21, 25, 2, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 32, 2, 21, 34, 2, 21, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 28, 4, 20, 29, 4, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 27, 3, 21, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 30, 3, 21, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 26, 2, 21, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 31, 2, 21, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 25, 1, 21, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 32, 1, 21, mutableBoundingBox);
                for (n = 0; n < 7; ++n) {
                    this.setBlockState(iSeedReader, DARK_PRISMARINE, 28 - n, 6 + n, 21, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DARK_PRISMARINE, 29 + n, 6 + n, 21, mutableBoundingBox);
                }
                for (n = 0; n < 4; ++n) {
                    this.setBlockState(iSeedReader, DARK_PRISMARINE, 28 - n, 9 + n, 21, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DARK_PRISMARINE, 29 + n, 9 + n, 21, mutableBoundingBox);
                }
                this.setBlockState(iSeedReader, DARK_PRISMARINE, 28, 12, 21, mutableBoundingBox);
                this.setBlockState(iSeedReader, DARK_PRISMARINE, 29, 12, 21, mutableBoundingBox);
                for (n = 0; n < 3; ++n) {
                    this.setBlockState(iSeedReader, DARK_PRISMARINE, 22 - n * 2, 8, 21, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DARK_PRISMARINE, 22 - n * 2, 9, 21, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DARK_PRISMARINE, 35 + n * 2, 8, 21, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DARK_PRISMARINE, 35 + n * 2, 9, 21, mutableBoundingBox);
                }
                this.makeOpening(iSeedReader, mutableBoundingBox, 15, 13, 21, 42, 15, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 15, 1, 21, 15, 6, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 16, 1, 21, 16, 5, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 17, 1, 21, 20, 4, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 21, 1, 21, 21, 3, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 22, 1, 21, 22, 2, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 23, 1, 21, 24, 1, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 42, 1, 21, 42, 6, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 41, 1, 21, 41, 5, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 37, 1, 21, 40, 4, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 36, 1, 21, 36, 3, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 33, 1, 21, 34, 1, 21);
                this.makeOpening(iSeedReader, mutableBoundingBox, 35, 1, 21, 35, 2, 21);
            }
        }

        private void generateRoofPiece(ISeedReader iSeedReader, Random random2, MutableBoundingBox mutableBoundingBox) {
            if (this.doesChunkIntersect(mutableBoundingBox, 21, 21, 36, 1)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 21, 0, 22, 36, 0, 36, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 21, 1, 22, 36, 23, 36);
                for (int i = 0; i < 4; ++i) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 21 + i, 13 + i, 21 + i, 36 - i, 13 + i, 21 + i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 21 + i, 13 + i, 36 - i, 36 - i, 13 + i, 36 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 21 + i, 13 + i, 22 + i, 21 + i, 13 + i, 35 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 36 - i, 13 + i, 22 + i, 36 - i, 13 + i, 35 - i, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 25, 16, 25, 32, 16, 32, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 25, 17, 25, 25, 19, 25, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 32, 17, 25, 32, 19, 25, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 25, 17, 32, 25, 19, 32, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 32, 17, 32, 32, 19, 32, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 26, 20, 26, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 27, 21, 27, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 27, 20, 27, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 26, 20, 31, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 27, 21, 30, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 27, 20, 30, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 31, 20, 31, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 30, 21, 30, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 30, 20, 30, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 31, 20, 26, mutableBoundingBox);
                this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 30, 21, 27, mutableBoundingBox);
                this.setBlockState(iSeedReader, SEA_LANTERN, 30, 20, 27, mutableBoundingBox);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 28, 21, 27, 29, 21, 27, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 27, 21, 28, 27, 21, 29, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 28, 21, 30, 29, 21, 30, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 30, 21, 28, 30, 21, 29, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            }
        }

        private void generateLowerWall(ISeedReader iSeedReader, Random random2, MutableBoundingBox mutableBoundingBox) {
            int n;
            if (this.doesChunkIntersect(mutableBoundingBox, 0, 21, 6, 1)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 21, 6, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 21, 6, 7, 57);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 4, 21, 6, 4, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n + 1, 21, n, n + 1, 57 - n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                for (n = 23; n < 53; n += 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, 5, 5, n, mutableBoundingBox);
                }
                this.setBlockState(iSeedReader, DOT_DECO_DATA, 5, 5, 52, mutableBoundingBox);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n + 1, 21, n, n + 1, 57 - n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 52, 6, 3, 52, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 51, 5, 3, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            }
            if (this.doesChunkIntersect(mutableBoundingBox, 51, 21, 58, 1)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 51, 0, 21, 57, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 51, 1, 21, 57, 7, 57);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 51, 4, 21, 53, 4, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 57 - n, n + 1, 21, 57 - n, n + 1, 57 - n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                for (n = 23; n < 53; n += 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, 52, 5, n, mutableBoundingBox);
                }
                this.setBlockState(iSeedReader, DOT_DECO_DATA, 52, 5, 52, mutableBoundingBox);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 51, 1, 52, 53, 3, 52, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 52, 1, 51, 52, 3, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            }
            if (this.doesChunkIntersect(mutableBoundingBox, 0, 51, 57, 0)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 0, 51, 50, 0, 57, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 7, 1, 51, 50, 10, 57);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 1, n + 1, 57 - n, 56 - n, n + 1, 57 - n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
            }
        }

        private void generateMiddleWall(ISeedReader iSeedReader, Random random2, MutableBoundingBox mutableBoundingBox) {
            int n;
            if (this.doesChunkIntersect(mutableBoundingBox, 7, 21, 13, 1)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 0, 21, 13, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 7, 1, 21, 13, 10, 50);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 8, 21, 13, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 7, n + 5, 21, n + 7, n + 5, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                for (n = 21; n <= 45; n += 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, 12, 9, n, mutableBoundingBox);
                }
            }
            if (this.doesChunkIntersect(mutableBoundingBox, 44, 21, 50, 1)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 44, 0, 21, 50, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 44, 1, 21, 50, 10, 50);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 44, 8, 21, 46, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 50 - n, n + 5, 21, 50 - n, n + 5, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                for (n = 21; n <= 45; n += 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, 45, 9, n, mutableBoundingBox);
                }
            }
            if (this.doesChunkIntersect(mutableBoundingBox, 8, 44, 49, 1)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 14, 0, 44, 43, 0, 50, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 14, 1, 44, 43, 10, 50);
                for (n = 12; n <= 45; n += 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 9, 45, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 9, 52, mutableBoundingBox);
                    if (n != 12 && n != 18 && n != 24 && n != 33 && n != 39 && n != 45) continue;
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 9, 47, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 9, 50, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 10, 45, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 10, 46, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 10, 51, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 10, 52, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 11, 47, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 11, 50, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 12, 48, mutableBoundingBox);
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 12, 49, mutableBoundingBox);
                }
                for (n = 0; n < 3; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8 + n, 5 + n, 54, 49 - n, 5 + n, 54, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 8, 54, 46, 8, 54, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 14, 8, 44, 43, 8, 53, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            }
        }

        private void generateUpperWall(ISeedReader iSeedReader, Random random2, MutableBoundingBox mutableBoundingBox) {
            int n;
            if (this.doesChunkIntersect(mutableBoundingBox, 14, 21, 20, 0)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 14, 0, 21, 20, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 14, 1, 22, 20, 14, 43);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 18, 12, 22, 20, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 18, 12, 21, 20, 12, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n + 14, n + 9, 21, n + 14, n + 9, 43 - n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                for (n = 23; n <= 39; n += 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, 19, 13, n, mutableBoundingBox);
                }
            }
            if (this.doesChunkIntersect(mutableBoundingBox, 37, 21, 43, 0)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 37, 0, 21, 43, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 37, 1, 22, 43, 14, 43);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 37, 12, 22, 39, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 37, 12, 21, 39, 12, 21, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 43 - n, n + 9, 21, 43 - n, n + 9, 43 - n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                for (n = 23; n <= 39; n += 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, 38, 13, n, mutableBoundingBox);
                }
            }
            if (this.doesChunkIntersect(mutableBoundingBox, 15, 37, 42, 0)) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 21, 0, 37, 36, 0, 43, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                this.makeOpening(iSeedReader, mutableBoundingBox, 21, 1, 37, 36, 14, 43);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 21, 12, 37, 36, 12, 39, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                for (n = 0; n < 4; ++n) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15 + n, n + 9, 43 - n, 42 - n, n + 9, 43 - n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                }
                for (n = 21; n <= 36; n += 3) {
                    this.setBlockState(iSeedReader, DOT_DECO_DATA, n, 13, 38, mutableBoundingBox);
                }
            }
        }
    }

    static interface IMonumentRoomFitHelper {
        public boolean fits(RoomDefinition var1);

        public Piece create(Direction var1, RoomDefinition var2, Random var3);
    }

    static class FitSimpleRoomTopHelper
    implements IMonumentRoomFitHelper {
        private FitSimpleRoomTopHelper() {
        }

        @Override
        public boolean fits(RoomDefinition roomDefinition) {
            return !roomDefinition.hasOpening[Direction.WEST.getIndex()] && !roomDefinition.hasOpening[Direction.EAST.getIndex()] && !roomDefinition.hasOpening[Direction.NORTH.getIndex()] && !roomDefinition.hasOpening[Direction.SOUTH.getIndex()] && !roomDefinition.hasOpening[Direction.UP.getIndex()];
        }

        @Override
        public Piece create(Direction direction, RoomDefinition roomDefinition, Random random2) {
            roomDefinition.claimed = true;
            return new SimpleTopRoom(direction, roomDefinition);
        }
    }

    static class FitSimpleRoomHelper
    implements IMonumentRoomFitHelper {
        private FitSimpleRoomHelper() {
        }

        @Override
        public boolean fits(RoomDefinition roomDefinition) {
            return false;
        }

        @Override
        public Piece create(Direction direction, RoomDefinition roomDefinition, Random random2) {
            roomDefinition.claimed = true;
            return new SimpleRoom(direction, roomDefinition, random2);
        }
    }

    public static class EntryRoom
    extends Piece {
        public EntryRoom(Direction direction, RoomDefinition roomDefinition) {
            super(IStructurePieceType.OMENTRY, 1, direction, roomDefinition, 1, 1, 1);
        }

        public EntryRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMENTRY, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 2, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 3, 0, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 1, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 2, 0, 7, 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 0, 7, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 7, 7, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 0, 2, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 0, 6, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            if (this.roomDefinition.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 7, 4, 2, 7);
            }
            if (this.roomDefinition.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 3, 1, 2, 4);
            }
            if (this.roomDefinition.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 6, 1, 3, 7, 2, 4);
            }
            return false;
        }
    }

    public static class DoubleZRoom
    extends Piece {
        public DoubleZRoom(Direction direction, RoomDefinition roomDefinition) {
            super(IStructurePieceType.OMDZR, 1, direction, roomDefinition, 1, 1, 2);
        }

        public DoubleZRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMDZR, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            RoomDefinition roomDefinition = this.roomDefinition.connections[Direction.NORTH.getIndex()];
            RoomDefinition roomDefinition2 = this.roomDefinition;
            if (this.roomDefinition.index / 25 > 0) {
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 8, roomDefinition.hasOpening[Direction.DOWN.getIndex()]);
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 0, roomDefinition2.hasOpening[Direction.DOWN.getIndex()]);
            }
            if (roomDefinition2.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 4, 1, 6, 4, 7, ROUGH_PRISMARINE);
            }
            if (roomDefinition.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 4, 8, 6, 4, 14, ROUGH_PRISMARINE);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 0, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 3, 0, 7, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 0, 7, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 15, 6, 3, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 0, 7, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 0, 7, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 15, 6, 2, 15, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 0, 0, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 1, 0, 7, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 0, 7, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 15, 6, 1, 15, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 1, 1, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 1, 6, 1, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 1, 1, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 3, 1, 6, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 13, 1, 1, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 13, 6, 1, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 13, 1, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 3, 13, 6, 3, 14, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 1, 6, 2, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 6, 5, 3, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 1, 9, 2, 3, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 9, 5, 3, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 2, 6, 4, 2, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 2, 9, 4, 2, 9, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 2, 7, 2, 2, 8, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 7, 5, 2, 8, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.setBlockState(iSeedReader, SEA_LANTERN, 2, 2, 5, mutableBoundingBox);
            this.setBlockState(iSeedReader, SEA_LANTERN, 5, 2, 5, mutableBoundingBox);
            this.setBlockState(iSeedReader, SEA_LANTERN, 2, 2, 10, mutableBoundingBox);
            this.setBlockState(iSeedReader, SEA_LANTERN, 5, 2, 10, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 2, 3, 5, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 5, 3, 5, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 2, 3, 10, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 5, 3, 10, mutableBoundingBox);
            if (roomDefinition2.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 2, 0);
            }
            if (roomDefinition2.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 7, 1, 3, 7, 2, 4);
            }
            if (roomDefinition2.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 3, 0, 2, 4);
            }
            if (roomDefinition.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 15, 4, 2, 15);
            }
            if (roomDefinition.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 11, 0, 2, 12);
            }
            if (roomDefinition.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 7, 1, 11, 7, 2, 12);
            }
            return false;
        }
    }

    public static class DoubleYZRoom
    extends Piece {
        public DoubleYZRoom(Direction direction, RoomDefinition roomDefinition) {
            super(IStructurePieceType.OMDYZR, 1, direction, roomDefinition, 1, 2, 2);
        }

        public DoubleYZRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMDYZR, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockState blockState;
            int n;
            RoomDefinition roomDefinition = this.roomDefinition.connections[Direction.NORTH.getIndex()];
            RoomDefinition roomDefinition2 = this.roomDefinition;
            RoomDefinition roomDefinition3 = roomDefinition.connections[Direction.UP.getIndex()];
            RoomDefinition roomDefinition4 = roomDefinition2.connections[Direction.UP.getIndex()];
            if (this.roomDefinition.index / 25 > 0) {
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 8, roomDefinition.hasOpening[Direction.DOWN.getIndex()]);
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 0, roomDefinition2.hasOpening[Direction.DOWN.getIndex()]);
            }
            if (roomDefinition4.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 8, 1, 6, 8, 7, ROUGH_PRISMARINE);
            }
            if (roomDefinition3.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 8, 8, 6, 8, 14, ROUGH_PRISMARINE);
            }
            for (n = 1; n <= 7; ++n) {
                blockState = BRICKS_PRISMARINE;
                if (n == 2 || n == 6) {
                    blockState = ROUGH_PRISMARINE;
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, n, 0, 0, n, 15, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, n, 0, 7, n, 15, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, n, 0, 6, n, 0, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, n, 15, 6, n, 15, blockState, blockState, true);
            }
            for (n = 1; n <= 7; ++n) {
                blockState = DARK_PRISMARINE;
                if (n == 2 || n == 6) {
                    blockState = SEA_LANTERN;
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, n, 7, 4, n, 8, blockState, blockState, true);
            }
            if (roomDefinition2.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 2, 0);
            }
            if (roomDefinition2.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 7, 1, 3, 7, 2, 4);
            }
            if (roomDefinition2.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 3, 0, 2, 4);
            }
            if (roomDefinition.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 15, 4, 2, 15);
            }
            if (roomDefinition.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 11, 0, 2, 12);
            }
            if (roomDefinition.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 7, 1, 11, 7, 2, 12);
            }
            if (roomDefinition4.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 5, 0, 4, 6, 0);
            }
            if (roomDefinition4.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 7, 5, 3, 7, 6, 4);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 4, 2, 6, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 2, 6, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 5, 6, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            }
            if (roomDefinition4.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 5, 3, 0, 6, 4);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 2, 2, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 2, 1, 3, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 5, 1, 3, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            }
            if (roomDefinition3.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 5, 15, 4, 6, 15);
            }
            if (roomDefinition3.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 5, 11, 0, 6, 12);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 10, 2, 4, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 10, 1, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 13, 1, 3, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            }
            if (roomDefinition3.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 7, 5, 11, 7, 6, 12);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 4, 10, 6, 4, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 10, 6, 3, 10, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 13, 6, 3, 13, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            }
            return false;
        }
    }

    public static class DoubleYRoom
    extends Piece {
        public DoubleYRoom(Direction direction, RoomDefinition roomDefinition) {
            super(IStructurePieceType.OMDYR, 1, direction, roomDefinition, 1, 2, 1);
        }

        public DoubleYRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMDYR, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.roomDefinition.index / 25 > 0) {
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 0, this.roomDefinition.hasOpening[Direction.DOWN.getIndex()]);
            }
            RoomDefinition roomDefinition = this.roomDefinition.connections[Direction.UP.getIndex()];
            if (roomDefinition.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 8, 1, 6, 8, 6, ROUGH_PRISMARINE);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 4, 0, 0, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 4, 0, 7, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 0, 6, 4, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 7, 6, 4, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 4, 1, 2, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 2, 1, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 4, 1, 5, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 4, 2, 6, 4, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 4, 5, 2, 4, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 5, 1, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 4, 5, 5, 4, 6, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 4, 5, 6, 4, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            RoomDefinition roomDefinition2 = this.roomDefinition;
            for (int i = 1; i <= 5; i += 4) {
                int n = 0;
                if (roomDefinition2.hasOpening[Direction.SOUTH.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, i, n, 2, i + 2, n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, i, n, 5, i + 2, n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, i + 2, n, 4, i + 2, n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, i, n, 7, i + 2, n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, i + 1, n, 7, i + 1, n, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                }
                n = 7;
                if (roomDefinition2.hasOpening[Direction.NORTH.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, i, n, 2, i + 2, n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, i, n, 5, i + 2, n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, i + 2, n, 4, i + 2, n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, i, n, 7, i + 2, n, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, i + 1, n, 7, i + 1, n, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                }
                int n2 = 0;
                if (roomDefinition2.hasOpening[Direction.WEST.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i, 2, n2, i + 2, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i, 5, n2, i + 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i + 2, 3, n2, i + 2, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i, 0, n2, i + 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i + 1, 0, n2, i + 1, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                }
                n2 = 7;
                if (roomDefinition2.hasOpening[Direction.EAST.getIndex()]) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i, 2, n2, i + 2, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i, 5, n2, i + 2, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i + 2, 3, n2, i + 2, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i, 0, n2, i + 2, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, i + 1, 0, n2, i + 1, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
                }
                roomDefinition2 = roomDefinition;
            }
            return false;
        }
    }

    public static class DoubleXYRoom
    extends Piece {
        public DoubleXYRoom(Direction direction, RoomDefinition roomDefinition) {
            super(IStructurePieceType.OMDXYR, 1, direction, roomDefinition, 2, 2, 1);
        }

        public DoubleXYRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMDXYR, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            RoomDefinition roomDefinition = this.roomDefinition.connections[Direction.EAST.getIndex()];
            RoomDefinition roomDefinition2 = this.roomDefinition;
            RoomDefinition roomDefinition3 = roomDefinition2.connections[Direction.UP.getIndex()];
            RoomDefinition roomDefinition4 = roomDefinition.connections[Direction.UP.getIndex()];
            if (this.roomDefinition.index / 25 > 0) {
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 8, 0, roomDefinition.hasOpening[Direction.DOWN.getIndex()]);
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 0, roomDefinition2.hasOpening[Direction.DOWN.getIndex()]);
            }
            if (roomDefinition3.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 8, 1, 7, 8, 6, ROUGH_PRISMARINE);
            }
            if (roomDefinition4.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 8, 8, 1, 14, 8, 6, ROUGH_PRISMARINE);
            }
            for (int i = 1; i <= 7; ++i) {
                BlockState blockState = BRICKS_PRISMARINE;
                if (i == 2 || i == 6) {
                    blockState = ROUGH_PRISMARINE;
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, i, 0, 0, i, 7, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, i, 0, 15, i, 7, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, i, 0, 15, i, 0, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, i, 7, 14, i, 7, blockState, blockState, true);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 1, 3, 2, 7, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 2, 4, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 5, 4, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 13, 1, 3, 13, 7, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 1, 2, 12, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 1, 5, 12, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 3, 5, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 1, 3, 10, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 7, 2, 10, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 5, 2, 5, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 5, 2, 10, 7, 2, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 5, 5, 5, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 5, 5, 10, 7, 5, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 6, 6, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 9, 6, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 6, 6, 5, mutableBoundingBox);
            this.setBlockState(iSeedReader, BRICKS_PRISMARINE, 9, 6, 5, mutableBoundingBox);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 4, 3, 6, 4, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 4, 3, 10, 4, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.setBlockState(iSeedReader, SEA_LANTERN, 5, 4, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, SEA_LANTERN, 5, 4, 5, mutableBoundingBox);
            this.setBlockState(iSeedReader, SEA_LANTERN, 10, 4, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, SEA_LANTERN, 10, 4, 5, mutableBoundingBox);
            if (roomDefinition2.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 2, 0);
            }
            if (roomDefinition2.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 7, 4, 2, 7);
            }
            if (roomDefinition2.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 3, 0, 2, 4);
            }
            if (roomDefinition.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 11, 1, 0, 12, 2, 0);
            }
            if (roomDefinition.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 11, 1, 7, 12, 2, 7);
            }
            if (roomDefinition.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 15, 1, 3, 15, 2, 4);
            }
            if (roomDefinition3.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 5, 0, 4, 6, 0);
            }
            if (roomDefinition3.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 5, 7, 4, 6, 7);
            }
            if (roomDefinition3.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 5, 3, 0, 6, 4);
            }
            if (roomDefinition4.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 11, 5, 0, 12, 6, 0);
            }
            if (roomDefinition4.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 11, 5, 7, 12, 6, 7);
            }
            if (roomDefinition4.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 15, 5, 3, 15, 6, 4);
            }
            return false;
        }
    }

    public static class DoubleXRoom
    extends Piece {
        public DoubleXRoom(Direction direction, RoomDefinition roomDefinition) {
            super(IStructurePieceType.OMDXR, 1, direction, roomDefinition, 2, 1, 1);
        }

        public DoubleXRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.OMDXR, compoundNBT);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            RoomDefinition roomDefinition = this.roomDefinition.connections[Direction.EAST.getIndex()];
            RoomDefinition roomDefinition2 = this.roomDefinition;
            if (this.roomDefinition.index / 25 > 0) {
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 8, 0, roomDefinition.hasOpening[Direction.DOWN.getIndex()]);
                this.generateDefaultFloor(iSeedReader, mutableBoundingBox, 0, 0, roomDefinition2.hasOpening[Direction.DOWN.getIndex()]);
            }
            if (roomDefinition2.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 1, 4, 1, 7, 4, 6, ROUGH_PRISMARINE);
            }
            if (roomDefinition.connections[Direction.UP.getIndex()] == null) {
                this.generateBoxOnFillOnly(iSeedReader, mutableBoundingBox, 8, 4, 1, 14, 4, 6, ROUGH_PRISMARINE);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 0, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 3, 0, 15, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 0, 15, 3, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 7, 14, 3, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 2, 0, 15, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 0, 15, 2, 0, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 7, 14, 2, 7, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 0, 0, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 1, 0, 15, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 0, 15, 1, 0, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 7, 14, 1, 7, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 0, 10, 1, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 2, 0, 9, 2, 3, ROUGH_PRISMARINE, ROUGH_PRISMARINE, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 3, 0, 10, 3, 4, BRICKS_PRISMARINE, BRICKS_PRISMARINE, true);
            this.setBlockState(iSeedReader, SEA_LANTERN, 6, 2, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, SEA_LANTERN, 9, 2, 3, mutableBoundingBox);
            if (roomDefinition2.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 0, 4, 2, 0);
            }
            if (roomDefinition2.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 3, 1, 7, 4, 2, 7);
            }
            if (roomDefinition2.hasOpening[Direction.WEST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 0, 1, 3, 0, 2, 4);
            }
            if (roomDefinition.hasOpening[Direction.SOUTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 11, 1, 0, 12, 2, 0);
            }
            if (roomDefinition.hasOpening[Direction.NORTH.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 11, 1, 7, 12, 2, 7);
            }
            if (roomDefinition.hasOpening[Direction.EAST.getIndex()]) {
                this.makeOpening(iSeedReader, mutableBoundingBox, 15, 1, 3, 15, 2, 4);
            }
            return false;
        }
    }
}

