/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.ChestMinecartEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class MineshaftPieces {
    private static Piece createRandomShaftPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, @Nullable Direction direction, int n4, MineshaftStructure.Type type) {
        int n5 = random2.nextInt(100);
        if (n5 >= 80) {
            MutableBoundingBox mutableBoundingBox = Cross.findCrossing(list, random2, n, n2, n3, direction);
            if (mutableBoundingBox != null) {
                return new Cross(n4, mutableBoundingBox, direction, type);
            }
        } else if (n5 >= 70) {
            MutableBoundingBox mutableBoundingBox = Stairs.findStairs(list, random2, n, n2, n3, direction);
            if (mutableBoundingBox != null) {
                return new Stairs(n4, mutableBoundingBox, direction, type);
            }
        } else {
            MutableBoundingBox mutableBoundingBox = Corridor.findCorridorSize(list, random2, n, n2, n3, direction);
            if (mutableBoundingBox != null) {
                return new Corridor(n4, random2, mutableBoundingBox, direction, type);
            }
        }
        return null;
    }

    private static Piece generateAndAddPiece(StructurePiece structurePiece, List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
        if (n4 > 8) {
            return null;
        }
        if (Math.abs(n - structurePiece.getBoundingBox().minX) <= 80 && Math.abs(n3 - structurePiece.getBoundingBox().minZ) <= 80) {
            MineshaftStructure.Type type = ((Piece)structurePiece).mineShaftType;
            Piece piece = MineshaftPieces.createRandomShaftPiece(list, random2, n, n2, n3, direction, n4 + 1, type);
            if (piece != null) {
                list.add(piece);
                piece.buildComponent(structurePiece, list, random2);
            }
            return piece;
        }
        return null;
    }

    public static class Cross
    extends Piece {
        private final Direction corridorDirection;
        private final boolean isMultipleFloors;

        public Cross(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.MSCROSSING, compoundNBT);
            this.isMultipleFloors = compoundNBT.getBoolean("tf");
            this.corridorDirection = Direction.byHorizontalIndex(compoundNBT.getInt("D"));
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("tf", this.isMultipleFloors);
            compoundNBT.putInt("D", this.corridorDirection.getHorizontalIndex());
        }

        public Cross(int n, MutableBoundingBox mutableBoundingBox, @Nullable Direction direction, MineshaftStructure.Type type) {
            super(IStructurePieceType.MSCROSSING, n, type);
            this.corridorDirection = direction;
            this.boundingBox = mutableBoundingBox;
            this.isMultipleFloors = mutableBoundingBox.getYSize() > 3;
        }

        public static MutableBoundingBox findCrossing(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction) {
            MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(n, n2, n3, n, n2 + 3 - 1, n3);
            if (random2.nextInt(4) == 0) {
                mutableBoundingBox.maxY += 4;
            }
            switch (direction) {
                default: {
                    mutableBoundingBox.minX = n - 1;
                    mutableBoundingBox.maxX = n + 3;
                    mutableBoundingBox.minZ = n3 - 4;
                    break;
                }
                case SOUTH: {
                    mutableBoundingBox.minX = n - 1;
                    mutableBoundingBox.maxX = n + 3;
                    mutableBoundingBox.maxZ = n3 + 3 + 1;
                    break;
                }
                case WEST: {
                    mutableBoundingBox.minX = n - 4;
                    mutableBoundingBox.minZ = n3 - 1;
                    mutableBoundingBox.maxZ = n3 + 3;
                    break;
                }
                case EAST: {
                    mutableBoundingBox.maxX = n + 3 + 1;
                    mutableBoundingBox.minZ = n3 - 1;
                    mutableBoundingBox.maxZ = n3 + 3;
                }
            }
            return StructurePiece.findIntersecting(list, mutableBoundingBox) != null ? null : mutableBoundingBox;
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            int n = this.getComponentType();
            switch (this.corridorDirection) {
                default: {
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n);
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, n);
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, n);
                    break;
                }
                case SOUTH: {
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, n);
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, n);
                    break;
                }
                case WEST: {
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n);
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.WEST, n);
                    break;
                }
                case EAST: {
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n);
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, Direction.EAST, n);
                }
            }
            if (this.isMultipleFloors) {
                if (random2.nextBoolean()) {
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, Direction.NORTH, n);
                }
                if (random2.nextBoolean()) {
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, Direction.WEST, n);
                }
                if (random2.nextBoolean()) {
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, Direction.EAST, n);
                }
                if (random2.nextBoolean()) {
                    MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                }
            }
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.isLiquidInStructureBoundingBox(iSeedReader, mutableBoundingBox)) {
                return true;
            }
            BlockState blockState = this.getPlanksBlock();
            if (this.isMultipleFloors) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, CAVE_AIR, CAVE_AIR, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, CAVE_AIR, CAVE_AIR, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, CAVE_AIR, CAVE_AIR, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, CAVE_AIR, CAVE_AIR, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, CAVE_AIR, CAVE_AIR, true);
            } else {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, CAVE_AIR, CAVE_AIR, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, CAVE_AIR, CAVE_AIR, true);
            }
            this.placeSupportPillar(iSeedReader, mutableBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
            this.placeSupportPillar(iSeedReader, mutableBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
            this.placeSupportPillar(iSeedReader, mutableBoundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
            this.placeSupportPillar(iSeedReader, mutableBoundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
            for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i) {
                for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j) {
                    if (!this.getBlockStateFromPos(iSeedReader, i, this.boundingBox.minY - 1, j, mutableBoundingBox).isAir() || !this.getSkyBrightness(iSeedReader, i, this.boundingBox.minY - 1, j, mutableBoundingBox)) continue;
                    this.setBlockState(iSeedReader, blockState, i, this.boundingBox.minY - 1, j, mutableBoundingBox);
                }
            }
            return false;
        }

        private void placeSupportPillar(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4) {
            if (!this.getBlockStateFromPos(iSeedReader, n, n4 + 1, n3, mutableBoundingBox).isAir()) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n2, n3, n, n4, n3, this.getPlanksBlock(), CAVE_AIR, true);
            }
        }
    }

    public static class Stairs
    extends Piece {
        public Stairs(int n, MutableBoundingBox mutableBoundingBox, Direction direction, MineshaftStructure.Type type) {
            super(IStructurePieceType.MSSTAIRS, n, type);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Stairs(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.MSSTAIRS, compoundNBT);
        }

        public static MutableBoundingBox findStairs(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction) {
            MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(n, n2 - 5, n3, n, n2 + 3 - 1, n3);
            switch (direction) {
                default: {
                    mutableBoundingBox.maxX = n + 3 - 1;
                    mutableBoundingBox.minZ = n3 - 8;
                    break;
                }
                case SOUTH: {
                    mutableBoundingBox.maxX = n + 3 - 1;
                    mutableBoundingBox.maxZ = n3 + 8;
                    break;
                }
                case WEST: {
                    mutableBoundingBox.minX = n - 8;
                    mutableBoundingBox.maxZ = n3 + 3 - 1;
                    break;
                }
                case EAST: {
                    mutableBoundingBox.maxX = n + 8;
                    mutableBoundingBox.maxZ = n3 + 3 - 1;
                }
            }
            return StructurePiece.findIntersecting(list, mutableBoundingBox) != null ? null : mutableBoundingBox;
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            int n = this.getComponentType();
            Direction direction = this.getCoordBaseMode();
            if (direction != null) {
                switch (direction) {
                    default: {
                        MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n);
                        break;
                    }
                    case SOUTH: {
                        MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                        break;
                    }
                    case WEST: {
                        MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.WEST, n);
                        break;
                    }
                    case EAST: {
                        MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, Direction.EAST, n);
                    }
                }
            }
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.isLiquidInStructureBoundingBox(iSeedReader, mutableBoundingBox)) {
                return true;
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 0, 2, 7, 1, CAVE_AIR, CAVE_AIR, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 7, 2, 2, 8, CAVE_AIR, CAVE_AIR, true);
            for (int i = 0; i < 5; ++i) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, CAVE_AIR, CAVE_AIR, true);
            }
            return false;
        }
    }

    public static class Corridor
    extends Piece {
        private final boolean hasRails;
        private final boolean hasSpiders;
        private boolean spawnerPlaced;
        private final int sectionCount;

        public Corridor(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.MSCORRIDOR, compoundNBT);
            this.hasRails = compoundNBT.getBoolean("hr");
            this.hasSpiders = compoundNBT.getBoolean("sc");
            this.spawnerPlaced = compoundNBT.getBoolean("hps");
            this.sectionCount = compoundNBT.getInt("Num");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("hr", this.hasRails);
            compoundNBT.putBoolean("sc", this.hasSpiders);
            compoundNBT.putBoolean("hps", this.spawnerPlaced);
            compoundNBT.putInt("Num", this.sectionCount);
        }

        public Corridor(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction, MineshaftStructure.Type type) {
            super(IStructurePieceType.MSCORRIDOR, n, type);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
            this.hasRails = random2.nextInt(3) == 0;
            this.hasSpiders = !this.hasRails && random2.nextInt(23) == 0;
            this.sectionCount = this.getCoordBaseMode().getAxis() == Direction.Axis.Z ? mutableBoundingBox.getZSize() / 5 : mutableBoundingBox.getXSize() / 5;
        }

        public static MutableBoundingBox findCorridorSize(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction) {
            int n4;
            MutableBoundingBox mutableBoundingBox = new MutableBoundingBox(n, n2, n3, n, n2 + 3 - 1, n3);
            for (n4 = random2.nextInt(3) + 2; n4 > 0; --n4) {
                int n5 = n4 * 5;
                switch (direction) {
                    default: {
                        mutableBoundingBox.maxX = n + 3 - 1;
                        mutableBoundingBox.minZ = n3 - (n5 - 1);
                        break;
                    }
                    case SOUTH: {
                        mutableBoundingBox.maxX = n + 3 - 1;
                        mutableBoundingBox.maxZ = n3 + n5 - 1;
                        break;
                    }
                    case WEST: {
                        mutableBoundingBox.minX = n - (n5 - 1);
                        mutableBoundingBox.maxZ = n3 + 3 - 1;
                        break;
                    }
                    case EAST: {
                        mutableBoundingBox.maxX = n + n5 - 1;
                        mutableBoundingBox.maxZ = n3 + 3 - 1;
                    }
                }
                if (StructurePiece.findIntersecting(list, mutableBoundingBox) == null) break;
            }
            return n4 > 0 ? mutableBoundingBox : null;
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            block24: {
                int n = this.getComponentType();
                int n2 = random2.nextInt(4);
                Direction direction = this.getCoordBaseMode();
                if (direction != null) {
                    switch (direction) {
                        default: {
                            if (n2 <= 1) {
                                MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.minZ - 1, direction, n);
                                break;
                            }
                            if (n2 == 2) {
                                MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.minZ, Direction.WEST, n);
                                break;
                            }
                            MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.minZ, Direction.EAST, n);
                            break;
                        }
                        case SOUTH: {
                            if (n2 <= 1) {
                                MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.maxZ + 1, direction, n);
                                break;
                            }
                            if (n2 == 2) {
                                MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.maxZ - 3, Direction.WEST, n);
                                break;
                            }
                            MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.maxZ - 3, Direction.EAST, n);
                            break;
                        }
                        case WEST: {
                            if (n2 <= 1) {
                                MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.minZ, direction, n);
                                break;
                            }
                            if (n2 == 2) {
                                MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.minZ - 1, Direction.NORTH, n);
                                break;
                            }
                            MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                            break;
                        }
                        case EAST: {
                            if (n2 <= 1) {
                                MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.minZ, direction, n);
                                break;
                            }
                            if (n2 == 2) {
                                MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.minZ - 1, Direction.NORTH, n);
                                break;
                            }
                            MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + random2.nextInt(3), this.boundingBox.maxZ + 1, Direction.SOUTH, n);
                        }
                    }
                }
                if (n >= 8) break block24;
                if (direction != Direction.NORTH && direction != Direction.SOUTH) {
                    int n3 = this.boundingBox.minX + 3;
                    while (n3 + 3 <= this.boundingBox.maxX) {
                        int n4 = random2.nextInt(5);
                        if (n4 == 0) {
                            MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, n3, this.boundingBox.minY, this.boundingBox.minZ - 1, Direction.NORTH, n + 1);
                        } else if (n4 == 1) {
                            MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, n3, this.boundingBox.minY, this.boundingBox.maxZ + 1, Direction.SOUTH, n + 1);
                        }
                        n3 += 5;
                    }
                } else {
                    int n5 = this.boundingBox.minZ + 3;
                    while (n5 + 3 <= this.boundingBox.maxZ) {
                        int n6 = random2.nextInt(5);
                        if (n6 == 0) {
                            MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY, n5, Direction.WEST, n + 1);
                        } else if (n6 == 1) {
                            MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY, n5, Direction.EAST, n + 1);
                        }
                        n5 += 5;
                    }
                }
            }
        }

        @Override
        protected boolean generateChest(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, Random random2, int n, int n2, int n3, ResourceLocation resourceLocation) {
            BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
            if (mutableBoundingBox.isVecInside(blockPos) && iSeedReader.getBlockState(blockPos).isAir() && !iSeedReader.getBlockState(blockPos.down()).isAir()) {
                BlockState blockState = (BlockState)Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, random2.nextBoolean() ? RailShape.NORTH_SOUTH : RailShape.EAST_WEST);
                this.setBlockState(iSeedReader, blockState, n, n2, n3, mutableBoundingBox);
                ChestMinecartEntity chestMinecartEntity = new ChestMinecartEntity(iSeedReader.getWorld(), (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
                chestMinecartEntity.setLootTable(resourceLocation, random2.nextLong());
                iSeedReader.addEntity(chestMinecartEntity);
                return false;
            }
            return true;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2;
            int n3;
            int n4;
            if (this.isLiquidInStructureBoundingBox(iSeedReader, mutableBoundingBox)) {
                return true;
            }
            boolean bl = false;
            int n5 = 2;
            boolean bl2 = false;
            int n6 = 2;
            int n7 = this.sectionCount * 5 - 1;
            BlockState blockState = this.getPlanksBlock();
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 2, 1, n7, CAVE_AIR, CAVE_AIR, true);
            this.generateMaybeBox(iSeedReader, mutableBoundingBox, random2, 0.8f, 0, 2, 0, 2, 2, n7, CAVE_AIR, CAVE_AIR, false, true);
            if (this.hasSpiders) {
                this.generateMaybeBox(iSeedReader, mutableBoundingBox, random2, 0.6f, 0, 0, 0, 2, 1, n7, Blocks.COBWEB.getDefaultState(), CAVE_AIR, false, false);
            }
            for (n4 = 0; n4 < this.sectionCount; ++n4) {
                int n8;
                n3 = 2 + n4 * 5;
                this.placeSupport(iSeedReader, mutableBoundingBox, 0, 0, n3, 2, 2, random2);
                this.placeCobWeb(iSeedReader, mutableBoundingBox, random2, 0.1f, 0, 2, n3 - 1);
                this.placeCobWeb(iSeedReader, mutableBoundingBox, random2, 0.1f, 2, 2, n3 - 1);
                this.placeCobWeb(iSeedReader, mutableBoundingBox, random2, 0.1f, 0, 2, n3 + 1);
                this.placeCobWeb(iSeedReader, mutableBoundingBox, random2, 0.1f, 2, 2, n3 + 1);
                this.placeCobWeb(iSeedReader, mutableBoundingBox, random2, 0.05f, 0, 2, n3 - 2);
                this.placeCobWeb(iSeedReader, mutableBoundingBox, random2, 0.05f, 2, 2, n3 - 2);
                this.placeCobWeb(iSeedReader, mutableBoundingBox, random2, 0.05f, 0, 2, n3 + 2);
                this.placeCobWeb(iSeedReader, mutableBoundingBox, random2, 0.05f, 2, 2, n3 + 2);
                if (random2.nextInt(100) == 0) {
                    this.generateChest(iSeedReader, mutableBoundingBox, random2, 2, 0, n3 - 1, LootTables.CHESTS_ABANDONED_MINESHAFT);
                }
                if (random2.nextInt(100) == 0) {
                    this.generateChest(iSeedReader, mutableBoundingBox, random2, 0, 0, n3 + 1, LootTables.CHESTS_ABANDONED_MINESHAFT);
                }
                if (!this.hasSpiders || this.spawnerPlaced) continue;
                n2 = this.getYWithOffset(0);
                int n9 = n3 - 1 + random2.nextInt(3);
                n = this.getXWithOffset(1, n9);
                BlockPos blockPos2 = new BlockPos(n, n2, n8 = this.getZWithOffset(1, n9));
                if (!mutableBoundingBox.isVecInside(blockPos2) || !this.getSkyBrightness(iSeedReader, 1, 0, n9, mutableBoundingBox)) continue;
                this.spawnerPlaced = true;
                iSeedReader.setBlockState(blockPos2, Blocks.SPAWNER.getDefaultState(), 2);
                TileEntity tileEntity = iSeedReader.getTileEntity(blockPos2);
                if (!(tileEntity instanceof MobSpawnerTileEntity)) continue;
                ((MobSpawnerTileEntity)tileEntity).getSpawnerBaseLogic().setEntityType(EntityType.CAVE_SPIDER);
            }
            for (n4 = 0; n4 <= 2; ++n4) {
                for (n3 = 0; n3 <= n7; ++n3) {
                    n2 = -1;
                    BlockState blockState2 = this.getBlockStateFromPos(iSeedReader, n4, -1, n3, mutableBoundingBox);
                    if (!blockState2.isAir() || !this.getSkyBrightness(iSeedReader, n4, -1, n3, mutableBoundingBox)) continue;
                    n = -1;
                    this.setBlockState(iSeedReader, blockState, n4, -1, n3, mutableBoundingBox);
                }
            }
            if (this.hasRails) {
                BlockState blockState3 = (BlockState)Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.NORTH_SOUTH);
                for (n3 = 0; n3 <= n7; ++n3) {
                    BlockState blockState4 = this.getBlockStateFromPos(iSeedReader, 1, -1, n3, mutableBoundingBox);
                    if (blockState4.isAir() || !blockState4.isOpaqueCube(iSeedReader, new BlockPos(this.getXWithOffset(1, n3), this.getYWithOffset(-1), this.getZWithOffset(1, n3)))) continue;
                    float f = this.getSkyBrightness(iSeedReader, 1, 0, n3, mutableBoundingBox) ? 0.7f : 0.9f;
                    this.randomlyPlaceBlock(iSeedReader, mutableBoundingBox, random2, f, 1, 0, n3, blockState3);
                }
            }
            return false;
        }

        private void placeSupport(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4, int n5, Random random2) {
            if (this.isSupportingBox(iSeedReader, mutableBoundingBox, n, n5, n4, n3)) {
                BlockState blockState = this.getPlanksBlock();
                BlockState blockState2 = this.getFenceBlock();
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n2, n3, n, n4 - 1, n3, (BlockState)blockState2.with(FenceBlock.WEST, true), CAVE_AIR, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n5, n2, n3, n5, n4 - 1, n3, (BlockState)blockState2.with(FenceBlock.EAST, true), CAVE_AIR, true);
                if (random2.nextInt(4) == 0) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n4, n3, n, n4, n3, blockState, CAVE_AIR, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n5, n4, n3, n5, n4, n3, blockState, CAVE_AIR, true);
                } else {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n4, n3, n5, n4, n3, blockState, CAVE_AIR, true);
                    this.randomlyPlaceBlock(iSeedReader, mutableBoundingBox, random2, 0.05f, n + 1, n4, n3 - 1, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.NORTH));
                    this.randomlyPlaceBlock(iSeedReader, mutableBoundingBox, random2, 0.05f, n + 1, n4, n3 + 1, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.SOUTH));
                }
            }
        }

        private void placeCobWeb(ISeedReader iSeedReader, MutableBoundingBox mutableBoundingBox, Random random2, float f, int n, int n2, int n3) {
            if (this.getSkyBrightness(iSeedReader, n, n2, n3, mutableBoundingBox)) {
                this.randomlyPlaceBlock(iSeedReader, mutableBoundingBox, random2, f, n, n2, n3, Blocks.COBWEB.getDefaultState());
            }
        }
    }

    static abstract class Piece
    extends StructurePiece {
        protected MineshaftStructure.Type mineShaftType;

        public Piece(IStructurePieceType iStructurePieceType, int n, MineshaftStructure.Type type) {
            super(iStructurePieceType, n);
            this.mineShaftType = type;
        }

        public Piece(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
            super(iStructurePieceType, compoundNBT);
            this.mineShaftType = MineshaftStructure.Type.byId(compoundNBT.getInt("MST"));
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            compoundNBT.putInt("MST", this.mineShaftType.ordinal());
        }

        protected BlockState getPlanksBlock() {
            switch (this.mineShaftType) {
                default: {
                    return Blocks.OAK_PLANKS.getDefaultState();
                }
                case MESA: 
            }
            return Blocks.DARK_OAK_PLANKS.getDefaultState();
        }

        protected BlockState getFenceBlock() {
            switch (this.mineShaftType) {
                default: {
                    return Blocks.OAK_FENCE.getDefaultState();
                }
                case MESA: 
            }
            return Blocks.DARK_OAK_FENCE.getDefaultState();
        }

        protected boolean isSupportingBox(IBlockReader iBlockReader, MutableBoundingBox mutableBoundingBox, int n, int n2, int n3, int n4) {
            for (int i = n; i <= n2; ++i) {
                if (!this.getBlockStateFromPos(iBlockReader, i, n3 + 1, n4, mutableBoundingBox).isAir()) continue;
                return true;
            }
            return false;
        }
    }

    public static class Room
    extends Piece {
        private final List<MutableBoundingBox> connectedRooms = Lists.newLinkedList();

        public Room(int n, Random random2, int n2, int n3, MineshaftStructure.Type type) {
            super(IStructurePieceType.MSROOM, n, type);
            this.mineShaftType = type;
            this.boundingBox = new MutableBoundingBox(n2, 50, n3, n2 + 7 + random2.nextInt(6), 54 + random2.nextInt(6), n3 + 7 + random2.nextInt(6));
        }

        public Room(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.MSROOM, compoundNBT);
            ListNBT listNBT = compoundNBT.getList("Entrances", 11);
            for (int i = 0; i < listNBT.size(); ++i) {
                this.connectedRooms.add(new MutableBoundingBox(listNBT.getIntArray(i)));
            }
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            MutableBoundingBox mutableBoundingBox;
            Piece piece;
            int n;
            int n2 = this.getComponentType();
            int n3 = this.boundingBox.getYSize() - 3 - 1;
            if (n3 <= 0) {
                n3 = 1;
            }
            for (n = 0; n < this.boundingBox.getXSize() && (n += random2.nextInt(this.boundingBox.getXSize())) + 3 <= this.boundingBox.getXSize(); n += 4) {
                piece = MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + n, this.boundingBox.minY + random2.nextInt(n3) + 1, this.boundingBox.minZ - 1, Direction.NORTH, n2);
                if (piece == null) continue;
                mutableBoundingBox = piece.getBoundingBox();
                this.connectedRooms.add(new MutableBoundingBox(mutableBoundingBox.minX, mutableBoundingBox.minY, this.boundingBox.minZ, mutableBoundingBox.maxX, mutableBoundingBox.maxY, this.boundingBox.minZ + 1));
            }
            for (n = 0; n < this.boundingBox.getXSize() && (n += random2.nextInt(this.boundingBox.getXSize())) + 3 <= this.boundingBox.getXSize(); n += 4) {
                piece = MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX + n, this.boundingBox.minY + random2.nextInt(n3) + 1, this.boundingBox.maxZ + 1, Direction.SOUTH, n2);
                if (piece == null) continue;
                mutableBoundingBox = piece.getBoundingBox();
                this.connectedRooms.add(new MutableBoundingBox(mutableBoundingBox.minX, mutableBoundingBox.minY, this.boundingBox.maxZ - 1, mutableBoundingBox.maxX, mutableBoundingBox.maxY, this.boundingBox.maxZ));
            }
            for (n = 0; n < this.boundingBox.getZSize() && (n += random2.nextInt(this.boundingBox.getZSize())) + 3 <= this.boundingBox.getZSize(); n += 4) {
                piece = MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY + random2.nextInt(n3) + 1, this.boundingBox.minZ + n, Direction.WEST, n2);
                if (piece == null) continue;
                mutableBoundingBox = piece.getBoundingBox();
                this.connectedRooms.add(new MutableBoundingBox(this.boundingBox.minX, mutableBoundingBox.minY, mutableBoundingBox.minZ, this.boundingBox.minX + 1, mutableBoundingBox.maxY, mutableBoundingBox.maxZ));
            }
            for (n = 0; n < this.boundingBox.getZSize() && (n += random2.nextInt(this.boundingBox.getZSize())) + 3 <= this.boundingBox.getZSize(); n += 4) {
                piece = MineshaftPieces.generateAndAddPiece(structurePiece, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY + random2.nextInt(n3) + 1, this.boundingBox.minZ + n, Direction.EAST, n2);
                if (piece == null) continue;
                mutableBoundingBox = piece.getBoundingBox();
                this.connectedRooms.add(new MutableBoundingBox(this.boundingBox.maxX - 1, mutableBoundingBox.minY, mutableBoundingBox.minZ, this.boundingBox.maxX, mutableBoundingBox.maxY, mutableBoundingBox.maxZ));
            }
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            if (this.isLiquidInStructureBoundingBox(iSeedReader, mutableBoundingBox)) {
                return true;
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.DIRT.getDefaultState(), CAVE_AIR, false);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, CAVE_AIR, CAVE_AIR, true);
            for (MutableBoundingBox mutableBoundingBox2 : this.connectedRooms) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, mutableBoundingBox2.minX, mutableBoundingBox2.maxY - 2, mutableBoundingBox2.minZ, mutableBoundingBox2.maxX, mutableBoundingBox2.maxY, mutableBoundingBox2.maxZ, CAVE_AIR, CAVE_AIR, true);
            }
            this.randomlyRareFillWithBlocks(iSeedReader, mutableBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, CAVE_AIR, true);
            return false;
        }

        @Override
        public void offset(int n, int n2, int n3) {
            super.offset(n, n2, n3);
            for (MutableBoundingBox mutableBoundingBox : this.connectedRooms) {
                mutableBoundingBox.offset(n, n2, n3);
            }
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            ListNBT listNBT = new ListNBT();
            for (MutableBoundingBox mutableBoundingBox : this.connectedRooms) {
                listNBT.add(mutableBoundingBox.toNBTTagIntArray());
            }
            compoundNBT.put("Entrances", listNBT);
        }
    }
}

