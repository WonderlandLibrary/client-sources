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
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
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

public class FortressPieces {
    private static final PieceWeight[] PRIMARY_COMPONENTS = new PieceWeight[]{new PieceWeight(Straight.class, 30, 0, true), new PieceWeight(Crossing3.class, 10, 4), new PieceWeight(Crossing.class, 10, 4), new PieceWeight(Stairs.class, 10, 3), new PieceWeight(Throne.class, 5, 2), new PieceWeight(Entrance.class, 5, 1)};
    private static final PieceWeight[] SECONDARY_COMPONENTS = new PieceWeight[]{new PieceWeight(Corridor5.class, 25, 0, true), new PieceWeight(Crossing2.class, 15, 5), new PieceWeight(Corridor2.class, 5, 10), new PieceWeight(Corridor.class, 5, 10), new PieceWeight(Corridor3.class, 10, 3, true), new PieceWeight(Corridor4.class, 7, 2), new PieceWeight(NetherStalkRoom.class, 5, 2)};

    private static Piece findAndCreateBridgePieceFactory(PieceWeight pieceWeight, List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
        Class<? extends Piece> clazz = pieceWeight.weightClass;
        Piece piece = null;
        if (clazz == Straight.class) {
            piece = Straight.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == Crossing3.class) {
            piece = Crossing3.createPiece(list, n, n2, n3, direction, n4);
        } else if (clazz == Crossing.class) {
            piece = Crossing.createPiece(list, n, n2, n3, direction, n4);
        } else if (clazz == Stairs.class) {
            piece = Stairs.createPiece(list, n, n2, n3, n4, direction);
        } else if (clazz == Throne.class) {
            piece = Throne.createPiece(list, n, n2, n3, n4, direction);
        } else if (clazz == Entrance.class) {
            piece = Entrance.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == Corridor5.class) {
            piece = Corridor5.createPiece(list, n, n2, n3, direction, n4);
        } else if (clazz == Corridor2.class) {
            piece = Corridor2.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == Corridor.class) {
            piece = Corridor.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == Corridor3.class) {
            piece = Corridor3.createPiece(list, n, n2, n3, direction, n4);
        } else if (clazz == Corridor4.class) {
            piece = Corridor4.func_214814_a(list, n, n2, n3, direction, n4);
        } else if (clazz == Crossing2.class) {
            piece = Crossing2.createPiece(list, n, n2, n3, direction, n4);
        } else if (clazz == NetherStalkRoom.class) {
            piece = NetherStalkRoom.createPiece(list, n, n2, n3, direction, n4);
        }
        return piece;
    }

    static class PieceWeight {
        public final Class<? extends Piece> weightClass;
        public final int weight;
        public int placeCount;
        public final int maxPlaceCount;
        public final boolean allowInRow;

        public PieceWeight(Class<? extends Piece> clazz, int n, int n2, boolean bl) {
            this.weightClass = clazz;
            this.weight = n;
            this.maxPlaceCount = n2;
            this.allowInRow = bl;
        }

        public PieceWeight(Class<? extends Piece> clazz, int n, int n2) {
            this(clazz, n, n2, false);
        }

        public boolean doPlace(int n) {
            return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
        }

        public boolean isValid() {
            return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
        }
    }

    public static class Straight
    extends Piece {
        public Straight(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NEBS, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Straight(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NEBS, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Start)structurePiece, list, random2, 1, 3, true);
        }

        public static Straight createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -3, 0, 5, 10, 19, direction);
            return Straight.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Straight(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 4, 4, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 5, 0, 3, 7, 18, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 0, 0, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 5, 0, 4, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 4, 2, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 13, 4, 2, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 15, 4, 1, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (int i = 0; i <= 4; ++i) {
                for (int j = 0; j <= 2; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, j, mutableBoundingBox);
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, 18 - j, mutableBoundingBox);
                }
            }
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            BlockState blockState2 = (BlockState)blockState.with(FenceBlock.EAST, true);
            BlockState blockState3 = (BlockState)blockState.with(FenceBlock.WEST, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 1, 0, 4, 1, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 4, 0, 4, 4, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 14, 0, 4, 14, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 17, 0, 4, 17, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 1, 4, 4, 1, blockState3, blockState3, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 3, 4, 4, 4, 4, blockState3, blockState3, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 3, 14, 4, 4, 14, blockState3, blockState3, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 17, 4, 4, 17, blockState3, blockState3, true);
            return false;
        }
    }

    public static class Crossing3
    extends Piece {
        public Crossing3(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NEBCR, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        protected Crossing3(Random random2, int n, int n2) {
            super(IStructurePieceType.NEBCR, 0);
            this.setCoordBaseMode(Direction.Plane.HORIZONTAL.random(random2));
            this.boundingBox = this.getCoordBaseMode().getAxis() == Direction.Axis.Z ? new MutableBoundingBox(n, 64, n2, n + 19 - 1, 73, n2 + 19 - 1) : new MutableBoundingBox(n, 64, n2, n + 19 - 1, 73, n2 + 19 - 1);
        }

        protected Crossing3(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
            super(iStructurePieceType, compoundNBT);
        }

        public Crossing3(TemplateManager templateManager, CompoundNBT compoundNBT) {
            this(IStructurePieceType.NEBCR, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Start)structurePiece, list, random2, 8, 3, true);
            this.getNextComponentX((Start)structurePiece, list, random2, 3, 8, true);
            this.getNextComponentZ((Start)structurePiece, list, random2, 3, 8, true);
        }

        public static Crossing3 createPiece(List<StructurePiece> list, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -8, -3, 0, 19, 10, 19, direction);
            return Crossing3.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Crossing3(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2;
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 3, 0, 11, 4, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 7, 18, 4, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 5, 0, 10, 7, 18, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 8, 18, 7, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 5, 0, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 5, 11, 7, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 5, 0, 11, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 5, 11, 11, 5, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 7, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 5, 7, 18, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 11, 7, 5, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 5, 11, 18, 5, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 0, 11, 2, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 2, 13, 11, 2, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 0, 0, 11, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 0, 15, 11, 1, 18, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (n2 = 7; n2 <= 11; ++n2) {
                for (n = 0; n <= 2; ++n) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, n, mutableBoundingBox);
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, 18 - n, mutableBoundingBox);
                }
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 7, 5, 2, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 13, 2, 7, 18, 2, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 7, 3, 1, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 15, 0, 7, 18, 1, 11, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (n2 = 0; n2 <= 2; ++n2) {
                for (n = 7; n <= 11; ++n) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, n, mutableBoundingBox);
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 18 - n2, -1, n, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Crossing
    extends Piece {
        public Crossing(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NERC, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Crossing(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NERC, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Start)structurePiece, list, random2, 2, 0, true);
            this.getNextComponentX((Start)structurePiece, list, random2, 0, 2, true);
            this.getNextComponentZ((Start)structurePiece, list, random2, 0, 2, true);
        }

        public static Crossing createPiece(List<StructurePiece> list, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -2, 0, 0, 7, 9, 7, direction);
            return Crossing.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Crossing(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 6, 7, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 1, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 6, 1, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 0, 6, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 6, 6, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 6, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 5, 0, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 2, 0, 6, 6, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 2, 5, 6, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 6, 0, 4, 6, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 0, 4, 5, 0, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 6, 6, 4, 6, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 6, 4, 5, 6, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 6, 2, 0, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 2, 0, 5, 4, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 6, 2, 6, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 5, 2, 6, 5, 4, blockState2, blockState2, true);
            for (int i = 0; i <= 6; ++i) {
                for (int j = 0; j <= 6; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, j, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Stairs
    extends Piece {
        public Stairs(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NESR, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Stairs(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NESR, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentZ((Start)structurePiece, list, random2, 6, 2, true);
        }

        public static Stairs createPiece(List<StructurePiece> list, int n, int n2, int n3, int n4, Direction direction) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -2, 0, 0, 7, 11, 7, direction);
            return Stairs.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Stairs(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 6, 1, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 6, 10, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 1, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 0, 6, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 1, 0, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 2, 1, 6, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 6, 5, 8, 6, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 2, 0, 5, 4, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 3, 2, 6, 5, 2, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 3, 4, 6, 5, 4, blockState2, blockState2, true);
            this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 5, 2, 5, mutableBoundingBox);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 2, 5, 4, 3, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 2, 5, 3, 4, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 2, 5, 2, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 5, 1, 6, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 7, 1, 5, 7, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 8, 2, 6, 8, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 6, 0, 4, 8, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 0, 4, 5, 0, blockState, blockState, true);
            for (int i = 0; i <= 6; ++i) {
                for (int j = 0; j <= 6; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, j, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Throne
    extends Piece {
        private boolean hasSpawner;

        public Throne(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NEMT, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Throne(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NEMT, compoundNBT);
            this.hasSpawner = compoundNBT.getBoolean("Mob");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("Mob", this.hasSpawner);
        }

        public static Throne createPiece(List<StructurePiece> list, int n, int n2, int n3, int n4, Direction direction) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -2, 0, 0, 7, 8, 9, direction);
            return Throne.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Throne(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockPos blockPos2;
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 6, 7, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 0, 0, 5, 1, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 1, 5, 2, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 2, 5, 3, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 3, 5, 4, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 0, 1, 4, 2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 0, 5, 4, 2, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 5, 2, 1, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 5, 2, 5, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 3, 0, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 5, 3, 6, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 5, 8, 5, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.setBlockState(iSeedReader, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true), 1, 6, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true), 5, 6, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true)).with(FenceBlock.NORTH, true), 0, 6, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.NORTH, true), 6, 6, 3, mutableBoundingBox);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 6, 4, 0, 6, 7, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 6, 4, 6, 6, 7, blockState2, blockState2, true);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true)).with(FenceBlock.SOUTH, true), 0, 6, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.SOUTH, true), 6, 6, 8, mutableBoundingBox);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 6, 8, 5, 6, 8, blockState, blockState, true);
            this.setBlockState(iSeedReader, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true), 1, 7, 8, mutableBoundingBox);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 7, 8, 4, 7, 8, blockState, blockState, true);
            this.setBlockState(iSeedReader, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true), 5, 7, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true), 2, 8, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState, 3, 8, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true), 4, 8, 8, mutableBoundingBox);
            if (!this.hasSpawner && mutableBoundingBox.isVecInside(blockPos2 = new BlockPos(this.getXWithOffset(3, 5), this.getYWithOffset(5), this.getZWithOffset(3, 5)))) {
                this.hasSpawner = true;
                iSeedReader.setBlockState(blockPos2, Blocks.SPAWNER.getDefaultState(), 2);
                TileEntity tileEntity = iSeedReader.getTileEntity(blockPos2);
                if (tileEntity instanceof MobSpawnerTileEntity) {
                    ((MobSpawnerTileEntity)tileEntity).getSpawnerBaseLogic().setEntityType(EntityType.BLAZE);
                }
            }
            for (int i = 0; i <= 6; ++i) {
                for (int j = 0; j <= 6; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, j, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Entrance
    extends Piece {
        public Entrance(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NECE, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Entrance(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NECE, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Start)structurePiece, list, random2, 5, 3, false);
        }

        public static Entrance createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -5, -3, 0, 13, 14, 13, direction);
            return Entrance.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Entrance(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2;
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 8, 0, 7, 8, 0, Blocks.NETHER_BRICK_FENCE.getDefaultState(), Blocks.NETHER_BRICK_FENCE.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            for (n2 = 1; n2 <= 11; n2 += 2) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, 10, 0, n2, 11, 0, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, 10, 12, n2, 11, 12, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 10, n2, 0, 11, n2, blockState2, blockState2, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 10, n2, 12, 11, n2, blockState2, blockState2, true);
                this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, 13, 0, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, 13, 12, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 0, 13, n2, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 12, 13, n2, mutableBoundingBox);
                if (n2 == 11) continue;
                this.setBlockState(iSeedReader, blockState, n2 + 1, 13, 0, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState, n2 + 1, 13, 12, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState2, 0, 13, n2 + 1, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState2, 12, 13, n2 + 1, mutableBoundingBox);
            }
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 0, 13, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.EAST, true), 0, 13, 12, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.WEST, true), 12, 13, 12, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.WEST, true), 12, 13, 0, mutableBoundingBox);
            for (n2 = 3; n2 <= 9; n2 += 2) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 7, n2, 1, 8, n2, (BlockState)blockState2.with(FenceBlock.WEST, true), (BlockState)blockState2.with(FenceBlock.WEST, true), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 7, n2, 11, 8, n2, (BlockState)blockState2.with(FenceBlock.EAST, true), (BlockState)blockState2.with(FenceBlock.EAST, true), true);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (n2 = 4; n2 <= 8; ++n2) {
                for (n = 0; n <= 2; ++n) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, n, mutableBoundingBox);
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, 12 - n, mutableBoundingBox);
                }
            }
            for (n2 = 0; n2 <= 2; ++n2) {
                for (n = 4; n <= 8; ++n) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, n, mutableBoundingBox);
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 12 - n2, -1, n, mutableBoundingBox);
                }
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 5, 5, 7, 5, 7, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 6, 6, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 6, 0, 6, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.LAVA.getDefaultState(), 6, 5, 6, mutableBoundingBox);
            BlockPos blockPos2 = new BlockPos(this.getXWithOffset(6, 6), this.getYWithOffset(5), this.getZWithOffset(6, 6));
            if (mutableBoundingBox.isVecInside(blockPos2)) {
                iSeedReader.getPendingFluidTicks().scheduleTick(blockPos2, Fluids.LAVA, 0);
            }
            return false;
        }
    }

    public static class Corridor5
    extends Piece {
        public Corridor5(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NESC, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Corridor5(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NESC, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Start)structurePiece, list, random2, 1, 0, false);
        }

        public static Corridor5 createPiece(List<StructurePiece> list, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, 0, 0, 5, 7, 5, direction);
            return Corridor5.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Corridor5(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 1, 0, 4, 1, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 3, 0, 4, 3, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 3, 1, 4, 4, 1, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 3, 3, 4, 4, 3, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (int i = 0; i <= 4; ++i) {
                for (int j = 0; j <= 4; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, j, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Corridor2
    extends Piece {
        private boolean chest;

        public Corridor2(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NESCRT, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
            this.chest = random2.nextInt(3) == 0;
        }

        public Corridor2(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NESCRT, compoundNBT);
            this.chest = compoundNBT.getBoolean("Chest");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("Chest", this.chest);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentZ((Start)structurePiece, list, random2, 0, 1, false);
        }

        public static Corridor2 createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, 0, 0, 5, 7, 5, direction);
            return Corridor2.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Corridor2(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 1, 0, 4, 1, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 3, 0, 4, 3, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 4, 1, 4, 4, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 3, 4, 3, 4, 4, blockState, blockState, true);
            if (this.chest && mutableBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(1, 3), this.getYWithOffset(2), this.getZWithOffset(1, 3)))) {
                this.chest = false;
                this.generateChest(iSeedReader, mutableBoundingBox, random2, 1, 2, 3, LootTables.CHESTS_NETHER_BRIDGE);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (int i = 0; i <= 4; ++i) {
                for (int j = 0; j <= 4; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, j, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Corridor
    extends Piece {
        private boolean chest;

        public Corridor(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NESCLT, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
            this.chest = random2.nextInt(3) == 0;
        }

        public Corridor(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NESCLT, compoundNBT);
            this.chest = compoundNBT.getBoolean("Chest");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("Chest", this.chest);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentX((Start)structurePiece, list, random2, 0, 1, false);
        }

        public static Corridor createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, 0, 0, 5, 7, 5, direction);
            return Corridor.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Corridor(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 2, 0, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 3, 1, 4, 4, 1, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 3, 3, 4, 4, 3, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 4, 3, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 4, 1, 4, 4, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 3, 4, 3, 4, 4, blockState, blockState, true);
            if (this.chest && mutableBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))) {
                this.chest = false;
                this.generateChest(iSeedReader, mutableBoundingBox, random2, 3, 2, 3, LootTables.CHESTS_NETHER_BRIDGE);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (int i = 0; i <= 4; ++i) {
                for (int j = 0; j <= 4; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, j, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Corridor3
    extends Piece {
        public Corridor3(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NECCS, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Corridor3(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NECCS, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Start)structurePiece, list, random2, 1, 0, false);
        }

        public static Corridor3 createPiece(List<StructurePiece> list, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -7, 0, 5, 14, 10, direction);
            return Corridor3.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Corridor3(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockState blockState = (BlockState)Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            for (int i = 0; i <= 9; ++i) {
                int n = Math.max(1, 7 - i);
                int n2 = Math.min(Math.max(n + 5, 14 - i), 13);
                int n3 = i;
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, i, 4, n, i, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, n + 1, i, 3, n2 - 1, i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
                if (i <= 6) {
                    this.setBlockState(iSeedReader, blockState, 1, n + 1, i, mutableBoundingBox);
                    this.setBlockState(iSeedReader, blockState, 2, n + 1, i, mutableBoundingBox);
                    this.setBlockState(iSeedReader, blockState, 3, n + 1, i, mutableBoundingBox);
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, n2, i, 4, n2, i, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, n + 1, i, 0, n2 - 1, i, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, n + 1, i, 4, n2 - 1, i, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
                if ((i & 1) == 0) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, n + 2, i, 0, n + 3, i, blockState2, blockState2, true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, n + 2, i, 4, n + 3, i, blockState2, blockState2, true);
                }
                for (int j = 0; j <= 4; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), j, -1, n3, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Corridor4
    extends Piece {
        public Corridor4(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NECTB, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Corridor4(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NECTB, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            int n = 1;
            Direction direction = this.getCoordBaseMode();
            if (direction == Direction.WEST || direction == Direction.NORTH) {
                n = 5;
            }
            this.getNextComponentX((Start)structurePiece, list, random2, 0, n, random2.nextInt(8) > 0);
            this.getNextComponentZ((Start)structurePiece, list, random2, 0, n, random2.nextInt(8) > 0);
        }

        public static Corridor4 func_214814_a(List<StructurePiece> list, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -3, 0, 0, 9, 7, 9, direction);
            return Corridor4.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Corridor4(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 8, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 8, 5, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 6, 0, 8, 6, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 2, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 2, 0, 8, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 0, 1, 4, 0, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 3, 0, 7, 4, 0, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 4, 8, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 4, 2, 2, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, 4, 7, 2, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 8, 7, 3, 8, blockState2, blockState2, true);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.EAST, true)).with(FenceBlock.SOUTH, true), 0, 3, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.SOUTH, true), 8, 3, 8, mutableBoundingBox);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 6, 0, 3, 7, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 3, 6, 8, 3, 7, blockState, blockState, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 4, 0, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 3, 4, 8, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 5, 2, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 3, 5, 7, 5, 5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 5, 1, 5, 5, blockState2, blockState2, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 4, 5, 7, 5, 5, blockState2, blockState2, true);
            for (int i = 0; i <= 5; ++i) {
                for (int j = 0; j <= 8; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), j, -1, i, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Crossing2
    extends Piece {
        public Crossing2(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NESCSC, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public Crossing2(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NESCSC, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Start)structurePiece, list, random2, 1, 0, false);
            this.getNextComponentX((Start)structurePiece, list, random2, 0, 1, false);
            this.getNextComponentZ((Start)structurePiece, list, random2, 0, 1, false);
        }

        public static Crossing2 createPiece(List<StructurePiece> list, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, 0, 0, 5, 7, 5, direction);
            return Crossing2.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Crossing2(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 1, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 4, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 0, 0, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 2, 0, 4, 5, 0, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 4, 0, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 2, 4, 4, 5, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 6, 0, 4, 6, 4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (int i = 0; i <= 4; ++i) {
                for (int j = 0; j <= 4; ++j) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), i, -1, j, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class NetherStalkRoom
    extends Piece {
        public NetherStalkRoom(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NECSR, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public NetherStalkRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NECSR, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Start)structurePiece, list, random2, 5, 3, false);
            this.getNextComponentNormal((Start)structurePiece, list, random2, 5, 11, false);
        }

        public static NetherStalkRoom createPiece(List<StructurePiece> list, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -5, -3, 0, 13, 14, 13, direction);
            return NetherStalkRoom.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new NetherStalkRoom(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2;
            int n3;
            int n4;
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 0, 12, 4, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 0, 12, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 0, 1, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 5, 0, 12, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 11, 4, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 5, 11, 10, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 9, 11, 7, 12, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 0, 4, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 5, 0, 10, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 9, 0, 7, 12, 1, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 11, 2, 10, 12, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
            BlockState blockState3 = (BlockState)blockState2.with(FenceBlock.WEST, true);
            BlockState blockState4 = (BlockState)blockState2.with(FenceBlock.EAST, true);
            for (n4 = 1; n4 <= 11; n4 += 2) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n4, 10, 0, n4, 11, 0, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n4, 10, 12, n4, 11, 12, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 10, n4, 0, 11, n4, blockState2, blockState2, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 10, n4, 12, 11, n4, blockState2, blockState2, true);
                this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n4, 13, 0, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n4, 13, 12, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 0, 13, n4, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 12, 13, n4, mutableBoundingBox);
                if (n4 == 11) continue;
                this.setBlockState(iSeedReader, blockState, n4 + 1, 13, 0, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState, n4 + 1, 13, 12, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState2, 0, 13, n4 + 1, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState2, 12, 13, n4 + 1, mutableBoundingBox);
            }
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 0, 13, 0, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.EAST, true), 0, 13, 12, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.WEST, true), 12, 13, 12, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.NETHER_BRICK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.WEST, true), 12, 13, 0, mutableBoundingBox);
            for (n4 = 3; n4 <= 9; n4 += 2) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 7, n4, 1, 8, n4, blockState3, blockState3, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 11, 7, n4, 11, 8, n4, blockState4, blockState4, true);
            }
            BlockState blockState5 = (BlockState)Blocks.NETHER_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
            for (n3 = 0; n3 <= 6; ++n3) {
                int n5 = n3 + 4;
                for (n2 = 5; n2 <= 7; ++n2) {
                    this.setBlockState(iSeedReader, blockState5, n2, 5 + n3, n5, mutableBoundingBox);
                }
                if (n5 >= 5 && n5 <= 8) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 5, n5, 7, n3 + 4, n5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
                } else if (n5 >= 9 && n5 <= 10) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 8, n5, 7, n3 + 4, n5, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
                }
                if (n3 < 1) continue;
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 6 + n3, n5, 7, 9 + n3, n5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            }
            for (n3 = 5; n3 <= 7; ++n3) {
                this.setBlockState(iSeedReader, blockState5, n3, 12, 11, mutableBoundingBox);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 6, 7, 5, 7, 7, blockState4, blockState4, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 7, 6, 7, 7, 7, 7, blockState3, blockState3, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 13, 12, 7, 13, 12, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 2, 3, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 9, 3, 5, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 2, 5, 4, 2, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 5, 2, 10, 5, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 5, 9, 10, 5, 10, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 5, 4, 10, 5, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            BlockState blockState6 = (BlockState)blockState5.with(StairsBlock.FACING, Direction.EAST);
            BlockState blockState7 = (BlockState)blockState5.with(StairsBlock.FACING, Direction.WEST);
            this.setBlockState(iSeedReader, blockState7, 4, 5, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState7, 4, 5, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState7, 4, 5, 9, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState7, 4, 5, 10, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState6, 8, 5, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState6, 8, 5, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState6, 8, 5, 9, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState6, 8, 5, 10, mutableBoundingBox);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SAND.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.getDefaultState(), Blocks.SOUL_SAND.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.getDefaultState(), Blocks.NETHER_WART.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.getDefaultState(), Blocks.NETHER_WART.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 2, 0, 8, 2, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 2, 4, 12, 2, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 0, 0, 8, 1, 3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 0, 9, 8, 1, 12, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 0, 4, 3, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 0, 4, 12, 1, 8, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (n2 = 4; n2 <= 8; ++n2) {
                for (n = 0; n <= 2; ++n) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, n, mutableBoundingBox);
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, 12 - n, mutableBoundingBox);
                }
            }
            for (n2 = 0; n2 <= 2; ++n2) {
                for (n = 4; n <= 8; ++n) {
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), n2, -1, n, mutableBoundingBox);
                    this.replaceAirAndLiquidDownwards(iSeedReader, Blocks.NETHER_BRICKS.getDefaultState(), 12 - n2, -1, n, mutableBoundingBox);
                }
            }
            return false;
        }
    }

    public static class Start
    extends Crossing3 {
        public PieceWeight lastPlaced;
        public List<PieceWeight> primaryWeights;
        public List<PieceWeight> secondaryWeights;
        public final List<StructurePiece> pendingChildren = Lists.newArrayList();

        public Start(Random random2, int n, int n2) {
            super(random2, n, n2);
            this.primaryWeights = Lists.newArrayList();
            for (PieceWeight pieceWeight : PRIMARY_COMPONENTS) {
                pieceWeight.placeCount = 0;
                this.primaryWeights.add(pieceWeight);
            }
            this.secondaryWeights = Lists.newArrayList();
            for (PieceWeight pieceWeight : SECONDARY_COMPONENTS) {
                pieceWeight.placeCount = 0;
                this.secondaryWeights.add(pieceWeight);
            }
        }

        public Start(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NESTART, compoundNBT);
        }
    }

    static abstract class Piece
    extends StructurePiece {
        protected Piece(IStructurePieceType iStructurePieceType, int n) {
            super(iStructurePieceType, n);
        }

        public Piece(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
            super(iStructurePieceType, compoundNBT);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
        }

        private int getTotalWeight(List<PieceWeight> list) {
            boolean bl = false;
            int n = 0;
            for (PieceWeight pieceWeight : list) {
                if (pieceWeight.maxPlaceCount > 0 && pieceWeight.placeCount < pieceWeight.maxPlaceCount) {
                    bl = true;
                }
                n += pieceWeight.weight;
            }
            return bl ? n : -1;
        }

        private Piece generatePiece(Start start, List<PieceWeight> list, List<StructurePiece> list2, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            int n5 = this.getTotalWeight(list);
            boolean bl = n5 > 0 && n4 <= 30;
            int n6 = 0;
            block0: while (n6 < 5 && bl) {
                ++n6;
                int n7 = random2.nextInt(n5);
                for (PieceWeight pieceWeight : list) {
                    if ((n7 -= pieceWeight.weight) >= 0) continue;
                    if (!pieceWeight.doPlace(n4) || pieceWeight == start.lastPlaced && !pieceWeight.allowInRow) continue block0;
                    Piece piece = FortressPieces.findAndCreateBridgePieceFactory(pieceWeight, list2, random2, n, n2, n3, direction, n4);
                    if (piece == null) continue;
                    ++pieceWeight.placeCount;
                    start.lastPlaced = pieceWeight;
                    if (!pieceWeight.isValid()) {
                        list.remove(pieceWeight);
                    }
                    return piece;
                }
            }
            return End.createPiece(list2, random2, n, n2, n3, direction, n4);
        }

        private StructurePiece generateAndAddPiece(Start start, List<StructurePiece> list, Random random2, int n, int n2, int n3, @Nullable Direction direction, int n4, boolean bl) {
            if (Math.abs(n - start.getBoundingBox().minX) <= 112 && Math.abs(n3 - start.getBoundingBox().minZ) <= 112) {
                Piece piece;
                List<PieceWeight> list2 = start.primaryWeights;
                if (bl) {
                    list2 = start.secondaryWeights;
                }
                if ((piece = this.generatePiece(start, list2, list, random2, n, n2, n3, direction, n4 + 1)) != null) {
                    list.add(piece);
                    start.pendingChildren.add(piece);
                }
                return piece;
            }
            return End.createPiece(list, random2, n, n2, n3, direction, n4);
        }

        @Nullable
        protected StructurePiece getNextComponentNormal(Start start, List<StructurePiece> list, Random random2, int n, int n2, boolean bl) {
            Direction direction = this.getCoordBaseMode();
            if (direction != null) {
                switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
                    case 1: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.minZ - 1, direction, this.getComponentType(), bl);
                    }
                    case 2: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.maxZ + 1, direction, this.getComponentType(), bl);
                    }
                    case 3: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY + n2, this.boundingBox.minZ + n, direction, this.getComponentType(), bl);
                    }
                    case 4: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY + n2, this.boundingBox.minZ + n, direction, this.getComponentType(), bl);
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece getNextComponentX(Start start, List<StructurePiece> list, Random random2, int n, int n2, boolean bl) {
            Direction direction = this.getCoordBaseMode();
            if (direction != null) {
                switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
                    case 1: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, Direction.WEST, this.getComponentType(), bl);
                    }
                    case 2: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, Direction.WEST, this.getComponentType(), bl);
                    }
                    case 3: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType(), bl);
                    }
                    case 4: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType(), bl);
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece getNextComponentZ(Start start, List<StructurePiece> list, Random random2, int n, int n2, boolean bl) {
            Direction direction = this.getCoordBaseMode();
            if (direction != null) {
                switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
                    case 1: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, Direction.EAST, this.getComponentType(), bl);
                    }
                    case 2: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, Direction.EAST, this.getComponentType(), bl);
                    }
                    case 3: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType(), bl);
                    }
                    case 4: {
                        return this.generateAndAddPiece(start, list, random2, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType(), bl);
                    }
                }
            }
            return null;
        }

        protected static boolean isAboveGround(MutableBoundingBox mutableBoundingBox) {
            return mutableBoundingBox != null && mutableBoundingBox.minY > 10;
        }
    }

    public static class End
    extends Piece {
        private final int fillSeed;

        public End(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.NEBEF, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
            this.fillSeed = random2.nextInt();
        }

        public End(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.NEBEF, compoundNBT);
            this.fillSeed = compoundNBT.getInt("Seed");
        }

        public static End createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -3, 0, 5, 10, 8, direction);
            return End.isAboveGround(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new End(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putInt("Seed", this.fillSeed);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2;
            int n3;
            Random random3 = new Random(this.fillSeed);
            for (n3 = 0; n3 <= 4; ++n3) {
                for (n2 = 3; n2 <= 4; ++n2) {
                    n = random3.nextInt(8);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n3, n2, 0, n3, n2, n, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
                }
            }
            n3 = random3.nextInt(8);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 0, 0, 5, n3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            n3 = random3.nextInt(8);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 5, 0, 4, 5, n3, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            for (n2 = 0; n2 <= 4; ++n2) {
                n = random3.nextInt(5);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, 2, 0, n2, 2, n, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
            }
            for (n2 = 0; n2 <= 4; ++n2) {
                for (n = 0; n <= 1; ++n) {
                    int n4 = random3.nextInt(3);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n2, n, 0, n2, n, n4, Blocks.NETHER_BRICKS.getDefaultState(), Blocks.NETHER_BRICKS.getDefaultState(), true);
                }
            }
            return false;
        }
    }
}

