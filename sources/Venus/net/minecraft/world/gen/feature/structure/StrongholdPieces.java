/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
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

public class StrongholdPieces {
    private static final PieceWeight[] PIECE_WEIGHTS = new PieceWeight[]{new PieceWeight(Straight.class, 40, 0), new PieceWeight(Prison.class, 5, 5), new PieceWeight(LeftTurn.class, 20, 0), new PieceWeight(RightTurn.class, 20, 0), new PieceWeight(RoomCrossing.class, 10, 6), new PieceWeight(StairsStraight.class, 5, 5), new PieceWeight(Stairs.class, 5, 5), new PieceWeight(Crossing.class, 5, 4), new PieceWeight(ChestCorridor.class, 5, 4), new PieceWeight(Library.class, 10, 2){

        @Override
        public boolean canSpawnMoreStructuresOfType(int n) {
            return super.canSpawnMoreStructuresOfType(n) && n > 4;
        }
    }, new PieceWeight(PortalRoom.class, 20, 1){

        @Override
        public boolean canSpawnMoreStructuresOfType(int n) {
            return super.canSpawnMoreStructuresOfType(n) && n > 5;
        }
    }};
    private static List<PieceWeight> structurePieceList;
    private static Class<? extends Stronghold> strongComponentType;
    private static int totalWeight;
    private static final Stones STRONGHOLD_STONES;

    public static void prepareStructurePieces() {
        structurePieceList = Lists.newArrayList();
        for (PieceWeight pieceWeight : PIECE_WEIGHTS) {
            pieceWeight.instancesSpawned = 0;
            structurePieceList.add(pieceWeight);
        }
        strongComponentType = null;
    }

    private static boolean canAddStructurePieces() {
        boolean bl = false;
        totalWeight = 0;
        for (PieceWeight pieceWeight : structurePieceList) {
            if (pieceWeight.instancesLimit > 0 && pieceWeight.instancesSpawned < pieceWeight.instancesLimit) {
                bl = true;
            }
            totalWeight += pieceWeight.pieceWeight;
        }
        return bl;
    }

    private static Stronghold findAndCreatePieceFactory(Class<? extends Stronghold> clazz, List<StructurePiece> list, Random random2, int n, int n2, int n3, @Nullable Direction direction, int n4) {
        Stronghold stronghold = null;
        if (clazz == Straight.class) {
            stronghold = Straight.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == Prison.class) {
            stronghold = Prison.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == LeftTurn.class) {
            stronghold = LeftTurn.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == RightTurn.class) {
            stronghold = RightTurn.func_214824_a(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == RoomCrossing.class) {
            stronghold = RoomCrossing.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == StairsStraight.class) {
            stronghold = StairsStraight.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == Stairs.class) {
            stronghold = Stairs.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == Crossing.class) {
            stronghold = Crossing.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == ChestCorridor.class) {
            stronghold = ChestCorridor.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == Library.class) {
            stronghold = Library.createPiece(list, random2, n, n2, n3, direction, n4);
        } else if (clazz == PortalRoom.class) {
            stronghold = PortalRoom.createPiece(list, n, n2, n3, direction, n4);
        }
        return stronghold;
    }

    private static Stronghold generatePieceFromSmallDoor(Stairs2 stairs2, List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
        if (!StrongholdPieces.canAddStructurePieces()) {
            return null;
        }
        if (strongComponentType != null) {
            Stronghold stronghold = StrongholdPieces.findAndCreatePieceFactory(strongComponentType, list, random2, n, n2, n3, direction, n4);
            strongComponentType = null;
            if (stronghold != null) {
                return stronghold;
            }
        }
        int n5 = 0;
        block0: while (n5 < 5) {
            ++n5;
            int n6 = random2.nextInt(totalWeight);
            for (PieceWeight pieceWeight : structurePieceList) {
                if ((n6 -= pieceWeight.pieceWeight) >= 0) continue;
                if (!pieceWeight.canSpawnMoreStructuresOfType(n4) || pieceWeight == stairs2.lastPlaced) continue block0;
                Stronghold stronghold = StrongholdPieces.findAndCreatePieceFactory(pieceWeight.pieceClass, list, random2, n, n2, n3, direction, n4);
                if (stronghold == null) continue;
                ++pieceWeight.instancesSpawned;
                stairs2.lastPlaced = pieceWeight;
                if (!pieceWeight.canSpawnMoreStructures()) {
                    structurePieceList.remove(pieceWeight);
                }
                return stronghold;
            }
        }
        MutableBoundingBox mutableBoundingBox = Corridor.findPieceBox(list, random2, n, n2, n3, direction);
        return mutableBoundingBox != null && mutableBoundingBox.minY > 1 ? new Corridor(n4, mutableBoundingBox, direction) : null;
    }

    private static StructurePiece generateAndAddPiece(Stairs2 stairs2, List<StructurePiece> list, Random random2, int n, int n2, int n3, @Nullable Direction direction, int n4) {
        if (n4 > 50) {
            return null;
        }
        if (Math.abs(n - stairs2.getBoundingBox().minX) <= 112 && Math.abs(n3 - stairs2.getBoundingBox().minZ) <= 112) {
            Stronghold stronghold = StrongholdPieces.generatePieceFromSmallDoor(stairs2, list, random2, n, n2, n3, direction, n4 + 1);
            if (stronghold != null) {
                list.add(stronghold);
                stairs2.pendingChildren.add(stronghold);
            }
            return stronghold;
        }
        return null;
    }

    static {
        STRONGHOLD_STONES = new Stones();
    }

    static class PieceWeight {
        public final Class<? extends Stronghold> pieceClass;
        public final int pieceWeight;
        public int instancesSpawned;
        public final int instancesLimit;

        public PieceWeight(Class<? extends Stronghold> clazz, int n, int n2) {
            this.pieceClass = clazz;
            this.pieceWeight = n;
            this.instancesLimit = n2;
        }

        public boolean canSpawnMoreStructuresOfType(int n) {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }

        public boolean canSpawnMoreStructures() {
            return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
        }
    }

    public static class Straight
    extends Stronghold {
        private final boolean expandsX;
        private final boolean expandsZ;

        public Straight(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHS, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
            this.expandsX = random2.nextInt(2) == 0;
            this.expandsZ = random2.nextInt(2) == 0;
        }

        public Straight(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHS, compoundNBT);
            this.expandsX = compoundNBT.getBoolean("Left");
            this.expandsZ = compoundNBT.getBoolean("Right");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("Left", this.expandsX);
            compoundNBT.putBoolean("Right", this.expandsZ);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Stairs2)structurePiece, list, random2, 1, 1);
            if (this.expandsX) {
                this.getNextComponentX((Stairs2)structurePiece, list, random2, 1, 2);
            }
            if (this.expandsZ) {
                this.getNextComponentZ((Stairs2)structurePiece, list, random2, 1, 2);
            }
        }

        public static Straight createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 7, direction);
            return Straight.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Straight(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 4, 6, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 1, 1, 0);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, Stronghold.Door.OPENING, 1, 1, 6);
            BlockState blockState = (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST);
            BlockState blockState2 = (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST);
            this.randomlyPlaceBlock(iSeedReader, mutableBoundingBox, random2, 0.1f, 1, 2, 1, blockState);
            this.randomlyPlaceBlock(iSeedReader, mutableBoundingBox, random2, 0.1f, 3, 2, 1, blockState2);
            this.randomlyPlaceBlock(iSeedReader, mutableBoundingBox, random2, 0.1f, 1, 2, 5, blockState);
            this.randomlyPlaceBlock(iSeedReader, mutableBoundingBox, random2, 0.1f, 3, 2, 5, blockState2);
            if (this.expandsX) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 2, 0, 3, 4, CAVE_AIR, CAVE_AIR, true);
            }
            if (this.expandsZ) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 2, 4, 3, 4, CAVE_AIR, CAVE_AIR, true);
            }
            return false;
        }
    }

    public static class Prison
    extends Stronghold {
        public Prison(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHPH, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
        }

        public Prison(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHPH, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Stairs2)structurePiece, list, random2, 1, 1);
        }

        public static Prison createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 9, 5, 11, direction);
            return Prison.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Prison(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 8, 4, 10, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 1, 1, 0);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 10, 3, 3, 10, CAVE_AIR, CAVE_AIR, true);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 1, 1, 4, 3, 1, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 1, 3, 4, 3, 3, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 1, 7, 4, 3, 7, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 1, 9, 4, 3, 9, false, random2, STRONGHOLD_STONES);
            for (int i = 1; i <= 3; ++i) {
                this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, i, 4, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)((BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true)).with(PaneBlock.EAST, true), 4, i, 5, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, i, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 5, i, 5, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 6, i, 5, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true), 7, i, 5, mutableBoundingBox);
            }
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, 3, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true), 4, 3, 8, mutableBoundingBox);
            BlockState blockState = (BlockState)Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.FACING, Direction.WEST)).with(DoorBlock.HALF, DoubleBlockHalf.UPPER);
            this.setBlockState(iSeedReader, blockState, 4, 1, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState2, 4, 2, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState, 4, 1, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, blockState2, 4, 2, 8, mutableBoundingBox);
            return false;
        }
    }

    public static class LeftTurn
    extends Turn {
        public LeftTurn(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHLT, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
        }

        public LeftTurn(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHLT, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            Direction direction = this.getCoordBaseMode();
            if (direction != Direction.NORTH && direction != Direction.EAST) {
                this.getNextComponentZ((Stairs2)structurePiece, list, random2, 1, 1);
            } else {
                this.getNextComponentX((Stairs2)structurePiece, list, random2, 1, 1);
            }
        }

        public static LeftTurn createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 5, direction);
            return LeftTurn.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new LeftTurn(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 4, 4, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 1, 1, 0);
            Direction direction = this.getCoordBaseMode();
            if (direction != Direction.NORTH && direction != Direction.EAST) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 1, 4, 3, 3, CAVE_AIR, CAVE_AIR, true);
            } else {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 1, 0, 3, 3, CAVE_AIR, CAVE_AIR, true);
            }
            return false;
        }
    }

    public static class RightTurn
    extends Turn {
        public RightTurn(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHRT, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
        }

        public RightTurn(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHRT, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            Direction direction = this.getCoordBaseMode();
            if (direction != Direction.NORTH && direction != Direction.EAST) {
                this.getNextComponentX((Stairs2)structurePiece, list, random2, 1, 1);
            } else {
                this.getNextComponentZ((Stairs2)structurePiece, list, random2, 1, 1);
            }
        }

        public static RightTurn func_214824_a(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 5, direction);
            return RightTurn.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new RightTurn(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 4, 4, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 1, 1, 0);
            Direction direction = this.getCoordBaseMode();
            if (direction != Direction.NORTH && direction != Direction.EAST) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 1, 0, 3, 3, CAVE_AIR, CAVE_AIR, true);
            } else {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 1, 4, 3, 3, CAVE_AIR, CAVE_AIR, true);
            }
            return false;
        }
    }

    public static class RoomCrossing
    extends Stronghold {
        protected final int roomType;

        public RoomCrossing(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHRC, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
            this.roomType = random2.nextInt(5);
        }

        public RoomCrossing(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHRC, compoundNBT);
            this.roomType = compoundNBT.getInt("Type");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putInt("Type", this.roomType);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Stairs2)structurePiece, list, random2, 4, 1);
            this.getNextComponentX((Stairs2)structurePiece, list, random2, 1, 4);
            this.getNextComponentZ((Stairs2)structurePiece, list, random2, 1, 4);
        }

        public static RoomCrossing createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -1, 0, 11, 7, 11, direction);
            return RoomCrossing.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new RoomCrossing(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 10, 6, 10, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 4, 1, 0);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 10, 6, 3, 10, CAVE_AIR, CAVE_AIR, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 1, 4, 0, 3, 6, CAVE_AIR, CAVE_AIR, true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 1, 4, 10, 3, 6, CAVE_AIR, CAVE_AIR, true);
            switch (this.roomType) {
                case 0: {
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 5, 1, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST), 4, 3, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST), 6, 3, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.SOUTH), 5, 3, 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.NORTH), 5, 3, 6, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 4, 1, 6, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 6, 1, 6, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 5, 1, 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 5, 1, 6, mutableBoundingBox);
                    break;
                }
                case 1: {
                    for (int i = 0; i < 5; ++i) {
                        this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3, 1, 3 + i, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 7, 1, 3 + i, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3 + i, 1, 3, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3 + i, 1, 7, mutableBoundingBox);
                    }
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 5, 1, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 5, 2, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 5, 3, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.WATER.getDefaultState(), 5, 4, 5, mutableBoundingBox);
                    break;
                }
                case 2: {
                    int n;
                    for (n = 1; n <= 9; ++n) {
                        this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 1, 3, n, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 9, 3, n, mutableBoundingBox);
                    }
                    for (n = 1; n <= 9; ++n) {
                        this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), n, 3, 1, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), n, 3, 9, mutableBoundingBox);
                    }
                    this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 5, 1, 6, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 4, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 5, 3, 6, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 4, 1, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 6, 1, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 4, 3, 5, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 6, 3, 5, mutableBoundingBox);
                    for (n = 1; n <= 3; ++n) {
                        this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 4, n, 4, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 6, n, 4, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 4, n, 6, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.COBBLESTONE.getDefaultState(), 6, n, 6, mutableBoundingBox);
                    }
                    this.setBlockState(iSeedReader, Blocks.TORCH.getDefaultState(), 5, 3, 5, mutableBoundingBox);
                    for (n = 2; n <= 8; ++n) {
                        this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 2, 3, n, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 3, 3, n, mutableBoundingBox);
                        if (n <= 3 || n >= 7) {
                            this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 4, 3, n, mutableBoundingBox);
                            this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 5, 3, n, mutableBoundingBox);
                            this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 6, 3, n, mutableBoundingBox);
                        }
                        this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 7, 3, n, mutableBoundingBox);
                        this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 8, 3, n, mutableBoundingBox);
                    }
                    BlockState blockState = (BlockState)Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.WEST);
                    this.setBlockState(iSeedReader, blockState, 9, 1, 3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, blockState, 9, 2, 3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, blockState, 9, 3, 3, mutableBoundingBox);
                    this.generateChest(iSeedReader, mutableBoundingBox, random2, 3, 4, 8, LootTables.CHESTS_STRONGHOLD_CROSSING);
                }
            }
            return false;
        }
    }

    public static class StairsStraight
    extends Stronghold {
        public StairsStraight(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHSSD, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
        }

        public StairsStraight(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHSSD, compoundNBT);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Stairs2)structurePiece, list, random2, 1, 1);
        }

        public static StairsStraight createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -7, 0, 5, 11, 8, direction);
            return StairsStraight.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new StairsStraight(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 10, 7, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 1, 7, 0);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, Stronghold.Door.OPENING, 1, 1, 7);
            BlockState blockState = (BlockState)Blocks.COBBLESTONE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
            for (int i = 0; i < 6; ++i) {
                this.setBlockState(iSeedReader, blockState, 1, 6 - i, 1 + i, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState, 2, 6 - i, 1 + i, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState, 3, 6 - i, 1 + i, mutableBoundingBox);
                if (i >= 5) continue;
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 1, 5 - i, 1 + i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 2, 5 - i, 1 + i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3, 5 - i, 1 + i, mutableBoundingBox);
            }
            return false;
        }
    }

    public static class Stairs
    extends Stronghold {
        private final boolean source;

        public Stairs(IStructurePieceType iStructurePieceType, int n, Random random2, int n2, int n3) {
            super(iStructurePieceType, n);
            this.source = true;
            this.setCoordBaseMode(Direction.Plane.HORIZONTAL.random(random2));
            this.entryDoor = Stronghold.Door.OPENING;
            this.boundingBox = this.getCoordBaseMode().getAxis() == Direction.Axis.Z ? new MutableBoundingBox(n2, 64, n3, n2 + 5 - 1, 74, n3 + 5 - 1) : new MutableBoundingBox(n2, 64, n3, n2 + 5 - 1, 74, n3 + 5 - 1);
        }

        public Stairs(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHSD, n);
            this.source = false;
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
        }

        public Stairs(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
            super(iStructurePieceType, compoundNBT);
            this.source = compoundNBT.getBoolean("Source");
        }

        public Stairs(TemplateManager templateManager, CompoundNBT compoundNBT) {
            this(IStructurePieceType.SHSD, compoundNBT);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("Source", this.source);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            if (this.source) {
                strongComponentType = Crossing.class;
            }
            this.getNextComponentNormal((Stairs2)structurePiece, list, random2, 1, 1);
        }

        public static Stairs createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -7, 0, 5, 11, 5, direction);
            return Stairs.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Stairs(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 10, 4, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 1, 7, 0);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, Stronghold.Door.OPENING, 1, 1, 4);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 2, 6, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 6, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 1, 5, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 5, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3, 4, 3, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3, 3, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3, 2, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 3, 3, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 2, 2, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 2, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 1, 1, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), 1, 1, 3, mutableBoundingBox);
            return false;
        }
    }

    public static class Crossing
    extends Stronghold {
        private final boolean leftLow;
        private final boolean leftHigh;
        private final boolean rightLow;
        private final boolean rightHigh;

        public Crossing(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SH5C, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
            this.leftLow = random2.nextBoolean();
            this.leftHigh = random2.nextBoolean();
            this.rightLow = random2.nextBoolean();
            this.rightHigh = random2.nextInt(3) > 0;
        }

        public Crossing(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SH5C, compoundNBT);
            this.leftLow = compoundNBT.getBoolean("leftLow");
            this.leftHigh = compoundNBT.getBoolean("leftHigh");
            this.rightLow = compoundNBT.getBoolean("rightLow");
            this.rightHigh = compoundNBT.getBoolean("rightHigh");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("leftLow", this.leftLow);
            compoundNBT.putBoolean("leftHigh", this.leftHigh);
            compoundNBT.putBoolean("rightLow", this.rightLow);
            compoundNBT.putBoolean("rightHigh", this.rightHigh);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            int n = 3;
            int n2 = 5;
            Direction direction = this.getCoordBaseMode();
            if (direction == Direction.WEST || direction == Direction.NORTH) {
                n = 8 - n;
                n2 = 8 - n2;
            }
            this.getNextComponentNormal((Stairs2)structurePiece, list, random2, 5, 1);
            if (this.leftLow) {
                this.getNextComponentX((Stairs2)structurePiece, list, random2, n, 1);
            }
            if (this.leftHigh) {
                this.getNextComponentX((Stairs2)structurePiece, list, random2, n2, 7);
            }
            if (this.rightLow) {
                this.getNextComponentZ((Stairs2)structurePiece, list, random2, n, 1);
            }
            if (this.rightHigh) {
                this.getNextComponentZ((Stairs2)structurePiece, list, random2, n2, 7);
            }
        }

        public static Crossing createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -3, 0, 10, 9, 11, direction);
            return Crossing.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new Crossing(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 9, 8, 10, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 4, 3, 0);
            if (this.leftLow) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, 1, 0, 5, 3, CAVE_AIR, CAVE_AIR, true);
            }
            if (this.rightLow) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 3, 1, 9, 5, 3, CAVE_AIR, CAVE_AIR, true);
            }
            if (this.leftHigh) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 5, 7, 0, 7, 9, CAVE_AIR, CAVE_AIR, true);
            }
            if (this.rightHigh) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 5, 7, 9, 7, 9, CAVE_AIR, CAVE_AIR, true);
            }
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 10, 7, 3, 10, CAVE_AIR, CAVE_AIR, true);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, 2, 1, 8, 2, 6, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 1, 5, 4, 4, 9, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 8, 1, 5, 8, 4, 9, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, 4, 7, 3, 4, 9, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, 3, 5, 3, 3, 6, false, random2, STRONGHOLD_STONES);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 3, 4, 3, 3, 4, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 4, 6, 3, 4, 6, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), true);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 5, 1, 7, 7, 1, 8, false, random2, STRONGHOLD_STONES);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 1, 9, 7, 1, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 2, 7, 7, 2, 7, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 5, 7, 4, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 8, 5, 7, 8, 5, 9, Blocks.SMOOTH_STONE_SLAB.getDefaultState(), Blocks.SMOOTH_STONE_SLAB.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 5, 5, 7, 7, 5, 9, (BlockState)Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE), (BlockState)Blocks.SMOOTH_STONE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.DOUBLE), true);
            this.setBlockState(iSeedReader, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.SOUTH), 6, 5, 6, mutableBoundingBox);
            return false;
        }
    }

    public static class ChestCorridor
    extends Stronghold {
        private boolean hasMadeChest;

        public ChestCorridor(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHCC, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
        }

        public ChestCorridor(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHCC, compoundNBT);
            this.hasMadeChest = compoundNBT.getBoolean("Chest");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("Chest", this.hasMadeChest);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            this.getNextComponentNormal((Stairs2)structurePiece, list, random2, 1, 1);
        }

        public static ChestCorridor createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 7, direction);
            return ChestCorridor.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new ChestCorridor(n4, random2, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 4, 4, 6, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 1, 1, 0);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, Stronghold.Door.OPENING, 1, 1, 6);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, 2, 3, 1, 4, Blocks.STONE_BRICKS.getDefaultState(), Blocks.STONE_BRICKS.getDefaultState(), true);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 1, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 1, 5, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 2, mutableBoundingBox);
            this.setBlockState(iSeedReader, Blocks.STONE_BRICK_SLAB.getDefaultState(), 3, 2, 4, mutableBoundingBox);
            for (int i = 2; i <= 4; ++i) {
                this.setBlockState(iSeedReader, Blocks.STONE_BRICK_SLAB.getDefaultState(), 2, 1, i, mutableBoundingBox);
            }
            if (!this.hasMadeChest && mutableBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))) {
                this.hasMadeChest = true;
                this.generateChest(iSeedReader, mutableBoundingBox, random2, 3, 2, 3, LootTables.CHESTS_STRONGHOLD_CORRIDOR);
            }
            return false;
        }
    }

    public static class Library
    extends Stronghold {
        private final boolean isLargeRoom;

        public Library(int n, Random random2, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHLI, n);
            this.setCoordBaseMode(direction);
            this.entryDoor = this.getRandomDoor(random2);
            this.boundingBox = mutableBoundingBox;
            this.isLargeRoom = mutableBoundingBox.getYSize() > 6;
        }

        public Library(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHLI, compoundNBT);
            this.isLargeRoom = compoundNBT.getBoolean("Tall");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("Tall", this.isLargeRoom);
        }

        public static Library createPiece(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -1, 0, 14, 11, 15, direction);
            if (!(Library.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null || Library.canStrongholdGoDeeper(mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -1, 0, 14, 6, 15, direction)) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null)) {
                return null;
            }
            return new Library(n4, random2, mutableBoundingBox, direction);
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            int n2 = 11;
            if (!this.isLargeRoom) {
                n2 = 6;
            }
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 13, n2 - 1, 14, true, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, this.entryDoor, 4, 1, 0);
            this.generateMaybeBox(iSeedReader, mutableBoundingBox, random2, 0.07f, 2, 1, 1, 11, 4, 13, Blocks.COBWEB.getDefaultState(), Blocks.COBWEB.getDefaultState(), false, true);
            boolean bl = true;
            int n3 = 12;
            for (n = 1; n <= 13; ++n) {
                if ((n - 1) % 4 == 0) {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, n, 1, 4, n, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 1, n, 12, 4, n, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), true);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.EAST), 2, 3, n, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, Direction.WEST), 11, 3, n, mutableBoundingBox);
                    if (!this.isLargeRoom) continue;
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 6, n, 1, 9, n, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), true);
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 6, n, 12, 9, n, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), true);
                    continue;
                }
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, n, 1, 4, n, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 1, n, 12, 4, n, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), true);
                if (!this.isLargeRoom) continue;
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 6, n, 1, 9, n, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 12, 6, n, 12, 9, n, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), true);
            }
            for (n = 3; n < 12; n += 2) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 1, n, 4, 3, n, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 6, 1, n, 7, 3, n, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 1, n, 10, 3, n, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), true);
            }
            if (this.isLargeRoom) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 5, 1, 3, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 5, 1, 12, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 5, 1, 9, 5, 2, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 5, 12, 9, 5, 13, Blocks.OAK_PLANKS.getDefaultState(), Blocks.OAK_PLANKS.getDefaultState(), true);
                this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 11, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 8, 5, 11, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.OAK_PLANKS.getDefaultState(), 9, 5, 10, mutableBoundingBox);
                BlockState blockState = (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
                BlockState blockState2 = (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.SOUTH, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 3, 6, 3, 3, 6, 11, blockState2, blockState2, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 6, 3, 10, 6, 9, blockState2, blockState2, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 6, 2, 9, 6, 2, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 6, 12, 7, 6, 12, blockState, blockState, true);
                this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 3, 6, 2, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.EAST, true), 3, 6, 12, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.WEST, true), 10, 6, 2, mutableBoundingBox);
                for (int i = 0; i <= 2; ++i) {
                    this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.SOUTH, true)).with(FenceBlock.WEST, true), 8 + i, 6, 12 - i, mutableBoundingBox);
                    if (i == 2) continue;
                    this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.NORTH, true)).with(FenceBlock.EAST, true), 8 + i, 6, 11 - i, mutableBoundingBox);
                }
                BlockState blockState3 = (BlockState)Blocks.LADDER.getDefaultState().with(LadderBlock.FACING, Direction.SOUTH);
                this.setBlockState(iSeedReader, blockState3, 10, 1, 13, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState3, 10, 2, 13, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState3, 10, 3, 13, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState3, 10, 4, 13, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState3, 10, 5, 13, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState3, 10, 6, 13, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState3, 10, 7, 13, mutableBoundingBox);
                int n4 = 7;
                int n5 = 7;
                BlockState blockState4 = (BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.EAST, true);
                this.setBlockState(iSeedReader, blockState4, 6, 9, 7, mutableBoundingBox);
                BlockState blockState5 = (BlockState)Blocks.OAK_FENCE.getDefaultState().with(FenceBlock.WEST, true);
                this.setBlockState(iSeedReader, blockState5, 7, 9, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState4, 6, 8, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState5, 7, 8, 7, mutableBoundingBox);
                BlockState blockState6 = (BlockState)((BlockState)blockState2.with(FenceBlock.WEST, true)).with(FenceBlock.EAST, true);
                this.setBlockState(iSeedReader, blockState6, 6, 7, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState6, 7, 7, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState4, 5, 7, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState5, 8, 7, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)blockState4.with(FenceBlock.NORTH, true), 6, 7, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)blockState4.with(FenceBlock.SOUTH, true), 6, 7, 8, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)blockState5.with(FenceBlock.NORTH, true), 7, 7, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, (BlockState)blockState5.with(FenceBlock.SOUTH, true), 7, 7, 8, mutableBoundingBox);
                BlockState blockState7 = Blocks.TORCH.getDefaultState();
                this.setBlockState(iSeedReader, blockState7, 5, 8, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState7, 8, 8, 7, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState7, 6, 8, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState7, 6, 8, 8, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState7, 7, 8, 6, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState7, 7, 8, 8, mutableBoundingBox);
            }
            this.generateChest(iSeedReader, mutableBoundingBox, random2, 3, 3, 5, LootTables.CHESTS_STRONGHOLD_LIBRARY);
            if (this.isLargeRoom) {
                this.setBlockState(iSeedReader, CAVE_AIR, 12, 9, 1, mutableBoundingBox);
                this.generateChest(iSeedReader, mutableBoundingBox, random2, 12, 8, 1, LootTables.CHESTS_STRONGHOLD_LIBRARY);
            }
            return false;
        }
    }

    public static class PortalRoom
    extends Stronghold {
        private boolean hasSpawner;

        public PortalRoom(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHPR, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
        }

        public PortalRoom(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHPR, compoundNBT);
            this.hasSpawner = compoundNBT.getBoolean("Mob");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putBoolean("Mob", this.hasSpawner);
        }

        @Override
        public void buildComponent(StructurePiece structurePiece, List<StructurePiece> list, Random random2) {
            if (structurePiece != null) {
                ((Stairs2)structurePiece).strongholdPortalRoom = this;
            }
        }

        public static PortalRoom createPiece(List<StructurePiece> list, int n, int n2, int n3, Direction direction, int n4) {
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -4, -1, 0, 11, 8, 16, direction);
            return PortalRoom.canStrongholdGoDeeper(mutableBoundingBox) && StructurePiece.findIntersecting(list, mutableBoundingBox) == null ? new PortalRoom(n4, mutableBoundingBox, direction) : null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            int n;
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 0, 0, 0, 10, 7, 15, false, random2, STRONGHOLD_STONES);
            this.func_242917_a(iSeedReader, random2, mutableBoundingBox, Stronghold.Door.GRATES, 4, 1, 0);
            int n2 = 6;
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, n2, 1, 1, n2, 14, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 9, n2, 1, 9, n2, 14, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, n2, 1, 8, n2, 2, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 2, n2, 14, 8, n2, 14, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 1, 1, 1, 2, 1, 4, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 8, 1, 1, 9, 1, 4, false, random2, STRONGHOLD_STONES);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 1, 1, 1, 1, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), true);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 9, 1, 1, 9, 1, 3, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), true);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 3, 1, 8, 7, 1, 12, false, random2, STRONGHOLD_STONES);
            this.fillWithBlocks(iSeedReader, mutableBoundingBox, 4, 1, 9, 6, 1, 11, Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState(), true);
            BlockState blockState = (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true);
            BlockState blockState2 = (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true)).with(PaneBlock.EAST, true);
            for (n = 3; n < 14; n += 2) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 0, 3, n, 0, 4, n, blockState, blockState, true);
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, 10, 3, n, 10, 4, n, blockState, blockState, true);
            }
            for (n = 2; n < 9; n += 2) {
                this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, 3, 15, n, 4, 15, blockState2, blockState2, true);
            }
            BlockState blockState3 = (BlockState)Blocks.STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 1, 5, 6, 1, 7, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 2, 6, 6, 2, 7, false, random2, STRONGHOLD_STONES);
            this.fillWithRandomizedBlocks(iSeedReader, mutableBoundingBox, 4, 3, 7, 6, 3, 7, false, random2, STRONGHOLD_STONES);
            for (int i = 4; i <= 6; ++i) {
                this.setBlockState(iSeedReader, blockState3, i, 1, 4, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState3, i, 2, 5, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState3, i, 3, 6, mutableBoundingBox);
            }
            BlockState blockState4 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.NORTH);
            BlockState blockState5 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.SOUTH);
            BlockState blockState6 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.EAST);
            BlockState blockState7 = (BlockState)Blocks.END_PORTAL_FRAME.getDefaultState().with(EndPortalFrameBlock.FACING, Direction.WEST);
            boolean bl = true;
            boolean[] blArray = new boolean[12];
            for (int i = 0; i < blArray.length; ++i) {
                blArray[i] = random2.nextFloat() > 0.9f;
                bl &= blArray[i];
            }
            this.setBlockState(iSeedReader, (BlockState)blockState4.with(EndPortalFrameBlock.EYE, blArray[0]), 4, 3, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState4.with(EndPortalFrameBlock.EYE, blArray[1]), 5, 3, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState4.with(EndPortalFrameBlock.EYE, blArray[2]), 6, 3, 8, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState5.with(EndPortalFrameBlock.EYE, blArray[3]), 4, 3, 12, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState5.with(EndPortalFrameBlock.EYE, blArray[4]), 5, 3, 12, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState5.with(EndPortalFrameBlock.EYE, blArray[5]), 6, 3, 12, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState6.with(EndPortalFrameBlock.EYE, blArray[6]), 3, 3, 9, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState6.with(EndPortalFrameBlock.EYE, blArray[7]), 3, 3, 10, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState6.with(EndPortalFrameBlock.EYE, blArray[8]), 3, 3, 11, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState7.with(EndPortalFrameBlock.EYE, blArray[9]), 7, 3, 9, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState7.with(EndPortalFrameBlock.EYE, blArray[10]), 7, 3, 10, mutableBoundingBox);
            this.setBlockState(iSeedReader, (BlockState)blockState7.with(EndPortalFrameBlock.EYE, blArray[11]), 7, 3, 11, mutableBoundingBox);
            if (bl) {
                BlockState blockState8 = Blocks.END_PORTAL.getDefaultState();
                this.setBlockState(iSeedReader, blockState8, 4, 3, 9, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState8, 5, 3, 9, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState8, 6, 3, 9, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState8, 4, 3, 10, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState8, 5, 3, 10, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState8, 6, 3, 10, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState8, 4, 3, 11, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState8, 5, 3, 11, mutableBoundingBox);
                this.setBlockState(iSeedReader, blockState8, 6, 3, 11, mutableBoundingBox);
            }
            if (!this.hasSpawner) {
                n2 = this.getYWithOffset(3);
                BlockPos blockPos2 = new BlockPos(this.getXWithOffset(5, 6), n2, this.getZWithOffset(5, 6));
                if (mutableBoundingBox.isVecInside(blockPos2)) {
                    this.hasSpawner = true;
                    iSeedReader.setBlockState(blockPos2, Blocks.SPAWNER.getDefaultState(), 2);
                    TileEntity tileEntity = iSeedReader.getTileEntity(blockPos2);
                    if (tileEntity instanceof MobSpawnerTileEntity) {
                        ((MobSpawnerTileEntity)tileEntity).getSpawnerBaseLogic().setEntityType(EntityType.SILVERFISH);
                    }
                }
            }
            return false;
        }
    }

    static abstract class Stronghold
    extends StructurePiece {
        protected Door entryDoor = Door.OPENING;

        protected Stronghold(IStructurePieceType iStructurePieceType, int n) {
            super(iStructurePieceType, n);
        }

        public Stronghold(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
            super(iStructurePieceType, compoundNBT);
            this.entryDoor = Door.valueOf(compoundNBT.getString("EntryDoor"));
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            compoundNBT.putString("EntryDoor", this.entryDoor.name());
        }

        protected void func_242917_a(ISeedReader iSeedReader, Random random2, MutableBoundingBox mutableBoundingBox, Door door, int n, int n2, int n3) {
            switch (door) {
                case OPENING: {
                    this.fillWithBlocks(iSeedReader, mutableBoundingBox, n, n2, n3, n + 3 - 1, n2 + 3 - 1, n3, CAVE_AIR, CAVE_AIR, true);
                    break;
                }
                case WOOD_DOOR: {
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n, n2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n, n2 + 1, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n + 1, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n + 2, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n + 2, n2 + 1, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n + 2, n2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.OAK_DOOR.getDefaultState(), n + 1, n2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.OAK_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), n + 1, n2 + 1, n3, mutableBoundingBox);
                    break;
                }
                case GRATES: {
                    this.setBlockState(iSeedReader, Blocks.CAVE_AIR.getDefaultState(), n + 1, n2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.CAVE_AIR.getDefaultState(), n + 1, n2 + 1, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true), n, n2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.WEST, true), n, n2 + 1, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), n, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), n + 1, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)((BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true), n + 2, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true), n + 2, n2 + 1, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.IRON_BARS.getDefaultState().with(PaneBlock.EAST, true), n + 2, n2, n3, mutableBoundingBox);
                    break;
                }
                case IRON_DOOR: {
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n, n2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n, n2 + 1, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n + 1, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n + 2, n2 + 2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n + 2, n2 + 1, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), n + 2, n2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.IRON_DOOR.getDefaultState(), n + 1, n2, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.IRON_DOOR.getDefaultState().with(DoorBlock.HALF, DoubleBlockHalf.UPPER), n + 1, n2 + 1, n3, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.STONE_BUTTON.getDefaultState().with(AbstractButtonBlock.HORIZONTAL_FACING, Direction.NORTH), n + 2, n2 + 1, n3 + 1, mutableBoundingBox);
                    this.setBlockState(iSeedReader, (BlockState)Blocks.STONE_BUTTON.getDefaultState().with(AbstractButtonBlock.HORIZONTAL_FACING, Direction.SOUTH), n + 2, n2 + 1, n3 - 1, mutableBoundingBox);
                }
            }
        }

        protected Door getRandomDoor(Random random2) {
            int n = random2.nextInt(5);
            switch (n) {
                default: {
                    return Door.OPENING;
                }
                case 2: {
                    return Door.WOOD_DOOR;
                }
                case 3: {
                    return Door.GRATES;
                }
                case 4: 
            }
            return Door.IRON_DOOR;
        }

        @Nullable
        protected StructurePiece getNextComponentNormal(Stairs2 stairs2, List<StructurePiece> list, Random random2, int n, int n2) {
            Direction direction = this.getCoordBaseMode();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.minZ - 1, direction, this.getComponentType());
                    }
                    case SOUTH: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX + n, this.boundingBox.minY + n2, this.boundingBox.maxZ + 1, direction, this.getComponentType());
                    }
                    case WEST: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY + n2, this.boundingBox.minZ + n, direction, this.getComponentType());
                    }
                    case EAST: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY + n2, this.boundingBox.minZ + n, direction, this.getComponentType());
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece getNextComponentX(Stairs2 stairs2, List<StructurePiece> list, Random random2, int n, int n2) {
            Direction direction = this.getCoordBaseMode();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, Direction.WEST, this.getComponentType());
                    }
                    case SOUTH: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX - 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, Direction.WEST, this.getComponentType());
                    }
                    case WEST: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
                    }
                    case EAST: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.minZ - 1, Direction.NORTH, this.getComponentType());
                    }
                }
            }
            return null;
        }

        @Nullable
        protected StructurePiece getNextComponentZ(Stairs2 stairs2, List<StructurePiece> list, Random random2, int n, int n2) {
            Direction direction = this.getCoordBaseMode();
            if (direction != null) {
                switch (direction) {
                    case NORTH: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, Direction.EAST, this.getComponentType());
                    }
                    case SOUTH: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.maxX + 1, this.boundingBox.minY + n, this.boundingBox.minZ + n2, Direction.EAST, this.getComponentType());
                    }
                    case WEST: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
                    }
                    case EAST: {
                        return StrongholdPieces.generateAndAddPiece(stairs2, list, random2, this.boundingBox.minX + n2, this.boundingBox.minY + n, this.boundingBox.maxZ + 1, Direction.SOUTH, this.getComponentType());
                    }
                }
            }
            return null;
        }

        protected static boolean canStrongholdGoDeeper(MutableBoundingBox mutableBoundingBox) {
            return mutableBoundingBox != null && mutableBoundingBox.minY > 10;
        }

        public static enum Door {
            OPENING,
            WOOD_DOOR,
            GRATES,
            IRON_DOOR;

        }
    }

    public static class Stairs2
    extends Stairs {
        public PieceWeight lastPlaced;
        @Nullable
        public PortalRoom strongholdPortalRoom;
        public final List<StructurePiece> pendingChildren = Lists.newArrayList();

        public Stairs2(Random random2, int n, int n2) {
            super(IStructurePieceType.SHSTART, 0, random2, n, n2);
        }

        public Stairs2(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHSTART, compoundNBT);
        }
    }

    public static class Corridor
    extends Stronghold {
        private final int steps;

        public Corridor(int n, MutableBoundingBox mutableBoundingBox, Direction direction) {
            super(IStructurePieceType.SHFC, n);
            this.setCoordBaseMode(direction);
            this.boundingBox = mutableBoundingBox;
            this.steps = direction != Direction.NORTH && direction != Direction.SOUTH ? mutableBoundingBox.getXSize() : mutableBoundingBox.getZSize();
        }

        public Corridor(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.SHFC, compoundNBT);
            this.steps = compoundNBT.getInt("Steps");
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putInt("Steps", this.steps);
        }

        public static MutableBoundingBox findPieceBox(List<StructurePiece> list, Random random2, int n, int n2, int n3, Direction direction) {
            int n4 = 3;
            MutableBoundingBox mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, 4, direction);
            StructurePiece structurePiece = StructurePiece.findIntersecting(list, mutableBoundingBox);
            if (structurePiece == null) {
                return null;
            }
            if (structurePiece.getBoundingBox().minY == mutableBoundingBox.minY) {
                for (int i = 3; i >= 1; --i) {
                    mutableBoundingBox = MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, i - 1, direction);
                    if (structurePiece.getBoundingBox().intersectsWith(mutableBoundingBox)) continue;
                    return MutableBoundingBox.getComponentToAddBoundingBox(n, n2, n3, -1, -1, 0, 5, 5, i, direction);
                }
            }
            return null;
        }

        @Override
        public boolean func_230383_a_(ISeedReader iSeedReader, StructureManager structureManager, ChunkGenerator chunkGenerator, Random random2, MutableBoundingBox mutableBoundingBox, ChunkPos chunkPos, BlockPos blockPos) {
            for (int i = 0; i < this.steps; ++i) {
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 0, 0, i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 1, 0, i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 2, 0, i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3, 0, i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 4, 0, i, mutableBoundingBox);
                for (int j = 1; j <= 3; ++j) {
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 0, j, i, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.CAVE_AIR.getDefaultState(), 1, j, i, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.CAVE_AIR.getDefaultState(), 2, j, i, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.CAVE_AIR.getDefaultState(), 3, j, i, mutableBoundingBox);
                    this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 4, j, i, mutableBoundingBox);
                }
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 0, 4, i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 1, 4, i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 2, 4, i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 3, 4, i, mutableBoundingBox);
                this.setBlockState(iSeedReader, Blocks.STONE_BRICKS.getDefaultState(), 4, 4, i, mutableBoundingBox);
            }
            return false;
        }
    }

    static class Stones
    extends StructurePiece.BlockSelector {
        private Stones() {
        }

        @Override
        public void selectBlocks(Random random2, int n, int n2, int n3, boolean bl) {
            float f;
            this.blockstate = bl ? ((f = random2.nextFloat()) < 0.2f ? Blocks.CRACKED_STONE_BRICKS.getDefaultState() : (f < 0.5f ? Blocks.MOSSY_STONE_BRICKS.getDefaultState() : (f < 0.55f ? Blocks.INFESTED_STONE_BRICKS.getDefaultState() : Blocks.STONE_BRICKS.getDefaultState()))) : Blocks.CAVE_AIR.getDefaultState();
        }
    }

    public static abstract class Turn
    extends Stronghold {
        protected Turn(IStructurePieceType iStructurePieceType, int n) {
            super(iStructurePieceType, n);
        }

        public Turn(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
            super(iStructurePieceType, compoundNBT);
        }
    }
}

