/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class WoodlandMansionPieces {
    public static void generateMansion(TemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<MansionTemplate> list, Random random2) {
        Grid grid = new Grid(random2);
        Placer placer = new Placer(templateManager, random2);
        placer.createMansion(blockPos, rotation, list, grid);
    }

    static class Grid {
        private final Random random;
        private final SimpleGrid baseGrid;
        private final SimpleGrid thirdFloorGrid;
        private final SimpleGrid[] floorRooms;
        private final int entranceX;
        private final int entranceY;

        public Grid(Random random2) {
            this.random = random2;
            int n = 11;
            this.entranceX = 7;
            this.entranceY = 4;
            this.baseGrid = new SimpleGrid(11, 11, 5);
            this.baseGrid.set(this.entranceX, this.entranceY, this.entranceX + 1, this.entranceY + 1, 3);
            this.baseGrid.set(this.entranceX - 1, this.entranceY, this.entranceX - 1, this.entranceY + 1, 2);
            this.baseGrid.set(this.entranceX + 2, this.entranceY - 2, this.entranceX + 3, this.entranceY + 3, 5);
            this.baseGrid.set(this.entranceX + 1, this.entranceY - 2, this.entranceX + 1, this.entranceY - 1, 1);
            this.baseGrid.set(this.entranceX + 1, this.entranceY + 2, this.entranceX + 1, this.entranceY + 3, 1);
            this.baseGrid.set(this.entranceX - 1, this.entranceY - 1, 1);
            this.baseGrid.set(this.entranceX - 1, this.entranceY + 2, 1);
            this.baseGrid.set(0, 0, 11, 1, 5);
            this.baseGrid.set(0, 9, 11, 11, 5);
            this.recursiveCorridor(this.baseGrid, this.entranceX, this.entranceY - 2, Direction.WEST, 6);
            this.recursiveCorridor(this.baseGrid, this.entranceX, this.entranceY + 3, Direction.WEST, 6);
            this.recursiveCorridor(this.baseGrid, this.entranceX - 2, this.entranceY - 1, Direction.WEST, 3);
            this.recursiveCorridor(this.baseGrid, this.entranceX - 2, this.entranceY + 2, Direction.WEST, 3);
            while (this.cleanEdges(this.baseGrid)) {
            }
            this.floorRooms = new SimpleGrid[3];
            this.floorRooms[0] = new SimpleGrid(11, 11, 5);
            this.floorRooms[1] = new SimpleGrid(11, 11, 5);
            this.floorRooms[2] = new SimpleGrid(11, 11, 5);
            this.identifyRooms(this.baseGrid, this.floorRooms[0]);
            this.identifyRooms(this.baseGrid, this.floorRooms[1]);
            this.floorRooms[0].set(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 0x800000);
            this.floorRooms[1].set(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 0x800000);
            this.thirdFloorGrid = new SimpleGrid(this.baseGrid.width, this.baseGrid.height, 5);
            this.setupThirdFloor();
            this.identifyRooms(this.thirdFloorGrid, this.floorRooms[2]);
        }

        public static boolean isHouse(SimpleGrid simpleGrid, int n, int n2) {
            int n3 = simpleGrid.get(n, n2);
            return n3 == 1 || n3 == 2 || n3 == 3 || n3 == 4;
        }

        public boolean isRoomId(SimpleGrid simpleGrid, int n, int n2, int n3, int n4) {
            return (this.floorRooms[n3].get(n, n2) & 0xFFFF) == n4;
        }

        @Nullable
        public Direction get1x2RoomDirection(SimpleGrid simpleGrid, int n, int n2, int n3, int n4) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (!this.isRoomId(simpleGrid, n + direction.getXOffset(), n2 + direction.getZOffset(), n3, n4)) continue;
                return direction;
            }
            return null;
        }

        private void recursiveCorridor(SimpleGrid simpleGrid, int n, int n2, Direction direction, int n3) {
            if (n3 > 0) {
                Direction direction2;
                simpleGrid.set(n, n2, 1);
                simpleGrid.setIf(n + direction.getXOffset(), n2 + direction.getZOffset(), 0, 1);
                for (int i = 0; i < 8; ++i) {
                    direction2 = Direction.byHorizontalIndex(this.random.nextInt(4));
                    if (direction2 == direction.getOpposite() || direction2 == Direction.EAST && this.random.nextBoolean()) continue;
                    int n4 = n + direction.getXOffset();
                    int n5 = n2 + direction.getZOffset();
                    if (simpleGrid.get(n4 + direction2.getXOffset(), n5 + direction2.getZOffset()) != 0 || simpleGrid.get(n4 + direction2.getXOffset() * 2, n5 + direction2.getZOffset() * 2) != 0) continue;
                    this.recursiveCorridor(simpleGrid, n + direction.getXOffset() + direction2.getXOffset(), n2 + direction.getZOffset() + direction2.getZOffset(), direction2, n3 - 1);
                    break;
                }
                Direction direction3 = direction.rotateY();
                direction2 = direction.rotateYCCW();
                simpleGrid.setIf(n + direction3.getXOffset(), n2 + direction3.getZOffset(), 0, 2);
                simpleGrid.setIf(n + direction2.getXOffset(), n2 + direction2.getZOffset(), 0, 2);
                simpleGrid.setIf(n + direction.getXOffset() + direction3.getXOffset(), n2 + direction.getZOffset() + direction3.getZOffset(), 0, 2);
                simpleGrid.setIf(n + direction.getXOffset() + direction2.getXOffset(), n2 + direction.getZOffset() + direction2.getZOffset(), 0, 2);
                simpleGrid.setIf(n + direction.getXOffset() * 2, n2 + direction.getZOffset() * 2, 0, 2);
                simpleGrid.setIf(n + direction3.getXOffset() * 2, n2 + direction3.getZOffset() * 2, 0, 2);
                simpleGrid.setIf(n + direction2.getXOffset() * 2, n2 + direction2.getZOffset() * 2, 0, 2);
            }
        }

        private boolean cleanEdges(SimpleGrid simpleGrid) {
            boolean bl = false;
            for (int i = 0; i < simpleGrid.height; ++i) {
                for (int j = 0; j < simpleGrid.width; ++j) {
                    if (simpleGrid.get(j, i) != 0) continue;
                    int n = 0;
                    n += Grid.isHouse(simpleGrid, j + 1, i) ? 1 : 0;
                    n += Grid.isHouse(simpleGrid, j - 1, i) ? 1 : 0;
                    n += Grid.isHouse(simpleGrid, j, i + 1) ? 1 : 0;
                    if ((n += Grid.isHouse(simpleGrid, j, i - 1) ? 1 : 0) >= 3) {
                        simpleGrid.set(j, i, 2);
                        bl = true;
                        continue;
                    }
                    if (n != 2) continue;
                    int n2 = 0;
                    n2 += Grid.isHouse(simpleGrid, j + 1, i + 1) ? 1 : 0;
                    n2 += Grid.isHouse(simpleGrid, j - 1, i + 1) ? 1 : 0;
                    n2 += Grid.isHouse(simpleGrid, j + 1, i - 1) ? 1 : 0;
                    if ((n2 += Grid.isHouse(simpleGrid, j - 1, i - 1) ? 1 : 0) > 1) continue;
                    simpleGrid.set(j, i, 2);
                    bl = true;
                }
            }
            return bl;
        }

        private void setupThirdFloor() {
            int n;
            int n2;
            ArrayList<Tuple<Integer, Integer>> arrayList = Lists.newArrayList();
            SimpleGrid simpleGrid = this.floorRooms[1];
            for (int i = 0; i < this.thirdFloorGrid.height; ++i) {
                for (n2 = 0; n2 < this.thirdFloorGrid.width; ++n2) {
                    int n3 = simpleGrid.get(n2, i);
                    n = n3 & 0xF0000;
                    if (n != 131072 || (n3 & 0x200000) != 0x200000) continue;
                    arrayList.add(new Tuple<Integer, Integer>(n2, i));
                }
            }
            if (arrayList.isEmpty()) {
                this.thirdFloorGrid.set(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
            } else {
                Tuple tuple = (Tuple)arrayList.get(this.random.nextInt(arrayList.size()));
                n2 = simpleGrid.get((Integer)tuple.getA(), (Integer)tuple.getB());
                simpleGrid.set((Integer)tuple.getA(), (Integer)tuple.getB(), n2 | 0x400000);
                Direction direction = this.get1x2RoomDirection(this.baseGrid, (Integer)tuple.getA(), (Integer)tuple.getB(), 1, n2 & 0xFFFF);
                n = (Integer)tuple.getA() + direction.getXOffset();
                int n4 = (Integer)tuple.getB() + direction.getZOffset();
                for (int i = 0; i < this.thirdFloorGrid.height; ++i) {
                    for (int j = 0; j < this.thirdFloorGrid.width; ++j) {
                        if (!Grid.isHouse(this.baseGrid, j, i)) {
                            this.thirdFloorGrid.set(j, i, 5);
                            continue;
                        }
                        if (j == (Integer)tuple.getA() && i == (Integer)tuple.getB()) {
                            this.thirdFloorGrid.set(j, i, 3);
                            continue;
                        }
                        if (j != n || i != n4) continue;
                        this.thirdFloorGrid.set(j, i, 3);
                        this.floorRooms[2].set(j, i, 0x800000);
                    }
                }
                ArrayList<Direction> arrayList2 = Lists.newArrayList();
                for (Direction direction2 : Direction.Plane.HORIZONTAL) {
                    if (this.thirdFloorGrid.get(n + direction2.getXOffset(), n4 + direction2.getZOffset()) != 0) continue;
                    arrayList2.add(direction2);
                }
                if (arrayList2.isEmpty()) {
                    this.thirdFloorGrid.set(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
                    simpleGrid.set((Integer)tuple.getA(), (Integer)tuple.getB(), n2);
                } else {
                    Direction direction3 = (Direction)arrayList2.get(this.random.nextInt(arrayList2.size()));
                    this.recursiveCorridor(this.thirdFloorGrid, n + direction3.getXOffset(), n4 + direction3.getZOffset(), direction3, 4);
                    while (this.cleanEdges(this.thirdFloorGrid)) {
                    }
                }
            }
        }

        private void identifyRooms(SimpleGrid simpleGrid, SimpleGrid simpleGrid2) {
            int n;
            ArrayList<Tuple<Integer, Integer>> arrayList = Lists.newArrayList();
            for (n = 0; n < simpleGrid.height; ++n) {
                for (int i = 0; i < simpleGrid.width; ++i) {
                    if (simpleGrid.get(i, n) != 2) continue;
                    arrayList.add(new Tuple<Integer, Integer>(i, n));
                }
            }
            Collections.shuffle(arrayList, this.random);
            n = 10;
            for (Tuple tuple : arrayList) {
                int n2;
                int n3 = (Integer)tuple.getA();
                if (simpleGrid2.get(n3, n2 = ((Integer)tuple.getB()).intValue()) != 0) continue;
                int n4 = n3;
                int n5 = n3;
                int n6 = n2;
                int n7 = n2;
                int n8 = 65536;
                if (simpleGrid2.get(n3 + 1, n2) == 0 && simpleGrid2.get(n3, n2 + 1) == 0 && simpleGrid2.get(n3 + 1, n2 + 1) == 0 && simpleGrid.get(n3 + 1, n2) == 2 && simpleGrid.get(n3, n2 + 1) == 2 && simpleGrid.get(n3 + 1, n2 + 1) == 2) {
                    n5 = n3 + 1;
                    n7 = n2 + 1;
                    n8 = 262144;
                } else if (simpleGrid2.get(n3 - 1, n2) == 0 && simpleGrid2.get(n3, n2 + 1) == 0 && simpleGrid2.get(n3 - 1, n2 + 1) == 0 && simpleGrid.get(n3 - 1, n2) == 2 && simpleGrid.get(n3, n2 + 1) == 2 && simpleGrid.get(n3 - 1, n2 + 1) == 2) {
                    n4 = n3 - 1;
                    n7 = n2 + 1;
                    n8 = 262144;
                } else if (simpleGrid2.get(n3 - 1, n2) == 0 && simpleGrid2.get(n3, n2 - 1) == 0 && simpleGrid2.get(n3 - 1, n2 - 1) == 0 && simpleGrid.get(n3 - 1, n2) == 2 && simpleGrid.get(n3, n2 - 1) == 2 && simpleGrid.get(n3 - 1, n2 - 1) == 2) {
                    n4 = n3 - 1;
                    n6 = n2 - 1;
                    n8 = 262144;
                } else if (simpleGrid2.get(n3 + 1, n2) == 0 && simpleGrid.get(n3 + 1, n2) == 2) {
                    n5 = n3 + 1;
                    n8 = 131072;
                } else if (simpleGrid2.get(n3, n2 + 1) == 0 && simpleGrid.get(n3, n2 + 1) == 2) {
                    n7 = n2 + 1;
                    n8 = 131072;
                } else if (simpleGrid2.get(n3 - 1, n2) == 0 && simpleGrid.get(n3 - 1, n2) == 2) {
                    n4 = n3 - 1;
                    n8 = 131072;
                } else if (simpleGrid2.get(n3, n2 - 1) == 0 && simpleGrid.get(n3, n2 - 1) == 2) {
                    n6 = n2 - 1;
                    n8 = 131072;
                }
                int n9 = this.random.nextBoolean() ? n4 : n5;
                int n10 = this.random.nextBoolean() ? n6 : n7;
                int n11 = 0x200000;
                if (!simpleGrid.edgesTo(n9, n10, 0)) {
                    n9 = n9 == n4 ? n5 : n4;
                    int n12 = n10 = n10 == n6 ? n7 : n6;
                    if (!simpleGrid.edgesTo(n9, n10, 0)) {
                        int n13 = n10 = n10 == n6 ? n7 : n6;
                        if (!simpleGrid.edgesTo(n9, n10, 0)) {
                            n9 = n9 == n4 ? n5 : n4;
                            int n14 = n10 = n10 == n6 ? n7 : n6;
                            if (!simpleGrid.edgesTo(n9, n10, 0)) {
                                n11 = 0;
                                n9 = n4;
                                n10 = n6;
                            }
                        }
                    }
                }
                for (int i = n6; i <= n7; ++i) {
                    for (int j = n4; j <= n5; ++j) {
                        if (j == n9 && i == n10) {
                            simpleGrid2.set(j, i, 0x100000 | n11 | n8 | n);
                            continue;
                        }
                        simpleGrid2.set(j, i, n8 | n);
                    }
                }
                ++n;
            }
        }
    }

    static class Placer {
        private final TemplateManager templateManager;
        private final Random random;
        private int startX;
        private int startY;

        public Placer(TemplateManager templateManager, Random random2) {
            this.templateManager = templateManager;
            this.random = random2;
        }

        public void createMansion(BlockPos blockPos, Rotation rotation, List<MansionTemplate> list, Grid grid) {
            int n;
            PlacementData placementData = new PlacementData();
            placementData.position = blockPos;
            placementData.rotation = rotation;
            placementData.wallType = "wall_flat";
            PlacementData placementData2 = new PlacementData();
            this.entrance(list, placementData);
            placementData2.position = placementData.position.up(8);
            placementData2.rotation = placementData.rotation;
            placementData2.wallType = "wall_window";
            if (!list.isEmpty()) {
                // empty if block
            }
            SimpleGrid simpleGrid = grid.baseGrid;
            SimpleGrid simpleGrid2 = grid.thirdFloorGrid;
            this.startX = grid.entranceX + 1;
            this.startY = grid.entranceY + 1;
            int n2 = grid.entranceX + 1;
            int n3 = grid.entranceY;
            this.traverseOuterWalls(list, placementData, simpleGrid, Direction.SOUTH, this.startX, this.startY, n2, n3);
            this.traverseOuterWalls(list, placementData2, simpleGrid, Direction.SOUTH, this.startX, this.startY, n2, n3);
            PlacementData placementData3 = new PlacementData();
            placementData3.position = placementData.position.up(19);
            placementData3.rotation = placementData.rotation;
            placementData3.wallType = "wall_window";
            boolean bl = false;
            for (int i = 0; i < simpleGrid2.height && !bl; ++i) {
                for (n = simpleGrid2.width - 1; n >= 0 && !bl; --n) {
                    if (!Grid.isHouse(simpleGrid2, n, i)) continue;
                    placementData3.position = placementData3.position.offset(rotation.rotate(Direction.SOUTH), 8 + (i - this.startY) * 8);
                    placementData3.position = placementData3.position.offset(rotation.rotate(Direction.EAST), (n - this.startX) * 8);
                    this.traverseWallPiece(list, placementData3);
                    this.traverseOuterWalls(list, placementData3, simpleGrid2, Direction.SOUTH, n, i, n, i);
                    bl = true;
                }
            }
            this.createRoof(list, blockPos.up(16), rotation, simpleGrid, simpleGrid2);
            this.createRoof(list, blockPos.up(27), rotation, simpleGrid2, null);
            if (!list.isEmpty()) {
                // empty if block
            }
            RoomCollection[] roomCollectionArray = new RoomCollection[]{new FirstFloor(), new SecondFloor(), new ThirdFloor()};
            for (n = 0; n < 3; ++n) {
                Object object;
                BlockPos blockPos2 = blockPos.up(8 * n + (n == 2 ? 3 : 0));
                SimpleGrid simpleGrid3 = grid.floorRooms[n];
                SimpleGrid simpleGrid4 = n == 2 ? simpleGrid2 : simpleGrid;
                String string = n == 0 ? "carpet_south_1" : "carpet_south_2";
                String string2 = n == 0 ? "carpet_west_1" : "carpet_west_2";
                for (int i = 0; i < simpleGrid4.height; ++i) {
                    for (int j = 0; j < simpleGrid4.width; ++j) {
                        if (simpleGrid4.get(j, i) != 1) continue;
                        object = blockPos2.offset(rotation.rotate(Direction.SOUTH), 8 + (i - this.startY) * 8);
                        object = ((BlockPos)object).offset(rotation.rotate(Direction.EAST), (j - this.startX) * 8);
                        list.add(new MansionTemplate(this.templateManager, "corridor_floor", (BlockPos)object, rotation));
                        if (simpleGrid4.get(j, i - 1) == 1 || (simpleGrid3.get(j, i - 1) & 0x800000) == 0x800000) {
                            list.add(new MansionTemplate(this.templateManager, "carpet_north", ((BlockPos)object).offset(rotation.rotate(Direction.EAST), 1).up(), rotation));
                        }
                        if (simpleGrid4.get(j + 1, i) == 1 || (simpleGrid3.get(j + 1, i) & 0x800000) == 0x800000) {
                            list.add(new MansionTemplate(this.templateManager, "carpet_east", ((BlockPos)object).offset(rotation.rotate(Direction.SOUTH), 1).offset(rotation.rotate(Direction.EAST), 5).up(), rotation));
                        }
                        if (simpleGrid4.get(j, i + 1) == 1 || (simpleGrid3.get(j, i + 1) & 0x800000) == 0x800000) {
                            list.add(new MansionTemplate(this.templateManager, string, ((BlockPos)object).offset(rotation.rotate(Direction.SOUTH), 5).offset(rotation.rotate(Direction.WEST), 1), rotation));
                        }
                        if (simpleGrid4.get(j - 1, i) != 1 && (simpleGrid3.get(j - 1, i) & 0x800000) != 0x800000) continue;
                        list.add(new MansionTemplate(this.templateManager, string2, ((BlockPos)object).offset(rotation.rotate(Direction.WEST), 1).offset(rotation.rotate(Direction.NORTH), 1), rotation));
                    }
                }
                String string3 = n == 0 ? "indoors_wall_1" : "indoors_wall_2";
                String string4 = n == 0 ? "indoors_door_1" : "indoors_door_2";
                object = Lists.newArrayList();
                for (int i = 0; i < simpleGrid4.height; ++i) {
                    for (int j = 0; j < simpleGrid4.width; ++j) {
                        Object object2;
                        Object object32;
                        boolean bl2;
                        boolean bl3 = bl2 = n == 2 && simpleGrid4.get(j, i) == 3;
                        if (simpleGrid4.get(j, i) != 2 && !bl2) continue;
                        int n4 = simpleGrid3.get(j, i);
                        int n5 = n4 & 0xF0000;
                        int n6 = n4 & 0xFFFF;
                        bl2 = bl2 && (n4 & 0x800000) == 0x800000;
                        object.clear();
                        if ((n4 & 0x200000) == 0x200000) {
                            for (Object object32 : Direction.Plane.HORIZONTAL) {
                                if (simpleGrid4.get(j + ((Direction)object32).getXOffset(), i + ((Direction)object32).getZOffset()) != 1) continue;
                                object.add(object32);
                            }
                        }
                        Object object4 = null;
                        if (!object.isEmpty()) {
                            object4 = (Direction)object.get(this.random.nextInt(object.size()));
                        } else if ((n4 & 0x100000) == 0x100000) {
                            object4 = Direction.UP;
                        }
                        object32 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 8 + (i - this.startY) * 8);
                        object32 = ((BlockPos)object32).offset(rotation.rotate(Direction.EAST), -1 + (j - this.startX) * 8);
                        if (Grid.isHouse(simpleGrid4, j - 1, i) && !grid.isRoomId(simpleGrid4, j - 1, i, n, n6)) {
                            list.add(new MansionTemplate(this.templateManager, object4 == Direction.WEST ? string4 : string3, (BlockPos)object32, rotation));
                        }
                        if (simpleGrid4.get(j + 1, i) == 1 && !bl2) {
                            object2 = ((BlockPos)object32).offset(rotation.rotate(Direction.EAST), 8);
                            list.add(new MansionTemplate(this.templateManager, object4 == Direction.EAST ? string4 : string3, (BlockPos)object2, rotation));
                        }
                        if (Grid.isHouse(simpleGrid4, j, i + 1) && !grid.isRoomId(simpleGrid4, j, i + 1, n, n6)) {
                            object2 = ((BlockPos)object32).offset(rotation.rotate(Direction.SOUTH), 7);
                            object2 = ((BlockPos)object2).offset(rotation.rotate(Direction.EAST), 7);
                            list.add(new MansionTemplate(this.templateManager, object4 == Direction.SOUTH ? string4 : string3, (BlockPos)object2, rotation.add(Rotation.CLOCKWISE_90)));
                        }
                        if (simpleGrid4.get(j, i - 1) == 1 && !bl2) {
                            object2 = ((BlockPos)object32).offset(rotation.rotate(Direction.NORTH), 1);
                            object2 = ((BlockPos)object2).offset(rotation.rotate(Direction.EAST), 7);
                            list.add(new MansionTemplate(this.templateManager, object4 == Direction.NORTH ? string4 : string3, (BlockPos)object2, rotation.add(Rotation.CLOCKWISE_90)));
                        }
                        if (n5 == 65536) {
                            this.addRoom1x1(list, (BlockPos)object32, rotation, (Direction)object4, roomCollectionArray[n]);
                            continue;
                        }
                        if (n5 == 131072 && object4 != null) {
                            object2 = grid.get1x2RoomDirection(simpleGrid4, j, i, n, n6);
                            boolean bl4 = (n4 & 0x400000) == 0x400000;
                            this.addRoom1x2(list, (BlockPos)object32, rotation, (Direction)object2, (Direction)object4, roomCollectionArray[n], bl4);
                            continue;
                        }
                        if (n5 == 262144 && object4 != null && object4 != Direction.UP) {
                            object2 = ((Direction)object4).rotateY();
                            if (!grid.isRoomId(simpleGrid4, j + ((Direction)object2).getXOffset(), i + ((Direction)object2).getZOffset(), n, n6)) {
                                object2 = ((Direction)object2).getOpposite();
                            }
                            this.addRoom2x2(list, (BlockPos)object32, rotation, (Direction)object2, (Direction)object4, roomCollectionArray[n]);
                            continue;
                        }
                        if (n5 != 262144 || object4 != Direction.UP) continue;
                        this.addRoom2x2Secret(list, (BlockPos)object32, rotation, roomCollectionArray[n]);
                    }
                }
            }
        }

        private void traverseOuterWalls(List<MansionTemplate> list, PlacementData placementData, SimpleGrid simpleGrid, Direction direction, int n, int n2, int n3, int n4) {
            int n5 = n;
            int n6 = n2;
            Direction direction2 = direction;
            do {
                if (!Grid.isHouse(simpleGrid, n5 + direction.getXOffset(), n6 + direction.getZOffset())) {
                    this.traverseTurn(list, placementData);
                    direction = direction.rotateY();
                    if (n5 == n3 && n6 == n4 && direction2 == direction) continue;
                    this.traverseWallPiece(list, placementData);
                    continue;
                }
                if (Grid.isHouse(simpleGrid, n5 + direction.getXOffset(), n6 + direction.getZOffset()) && Grid.isHouse(simpleGrid, n5 + direction.getXOffset() + direction.rotateYCCW().getXOffset(), n6 + direction.getZOffset() + direction.rotateYCCW().getZOffset())) {
                    this.traverseInnerTurn(list, placementData);
                    n5 += direction.getXOffset();
                    n6 += direction.getZOffset();
                    direction = direction.rotateYCCW();
                    continue;
                }
                if ((n5 += direction.getXOffset()) == n3 && (n6 += direction.getZOffset()) == n4 && direction2 == direction) continue;
                this.traverseWallPiece(list, placementData);
            } while (n5 != n3 || n6 != n4 || direction2 != direction);
        }

        private void createRoof(List<MansionTemplate> list, BlockPos blockPos, Rotation rotation, SimpleGrid simpleGrid, @Nullable SimpleGrid simpleGrid2) {
            BlockPos blockPos2;
            boolean bl;
            BlockPos blockPos3;
            int n;
            int n2;
            for (n2 = 0; n2 < simpleGrid.height; ++n2) {
                for (n = 0; n < simpleGrid.width; ++n) {
                    blockPos3 = blockPos.offset(rotation.rotate(Direction.SOUTH), 8 + (n2 - this.startY) * 8);
                    blockPos3 = blockPos3.offset(rotation.rotate(Direction.EAST), (n - this.startX) * 8);
                    boolean bl2 = bl = simpleGrid2 != null && Grid.isHouse(simpleGrid2, n, n2);
                    if (!Grid.isHouse(simpleGrid, n, n2) || bl) continue;
                    list.add(new MansionTemplate(this.templateManager, "roof", blockPos3.up(3), rotation));
                    if (!Grid.isHouse(simpleGrid, n + 1, n2)) {
                        blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 6);
                        list.add(new MansionTemplate(this.templateManager, "roof_front", blockPos2, rotation));
                    }
                    if (!Grid.isHouse(simpleGrid, n - 1, n2)) {
                        blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 0);
                        blockPos2 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 7);
                        list.add(new MansionTemplate(this.templateManager, "roof_front", blockPos2, rotation.add(Rotation.CLOCKWISE_180)));
                    }
                    if (!Grid.isHouse(simpleGrid, n, n2 - 1)) {
                        blockPos2 = blockPos3.offset(rotation.rotate(Direction.WEST), 1);
                        list.add(new MansionTemplate(this.templateManager, "roof_front", blockPos2, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
                    }
                    if (Grid.isHouse(simpleGrid, n, n2 + 1)) continue;
                    blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 6);
                    blockPos2 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 6);
                    list.add(new MansionTemplate(this.templateManager, "roof_front", blockPos2, rotation.add(Rotation.CLOCKWISE_90)));
                }
            }
            if (simpleGrid2 != null) {
                for (n2 = 0; n2 < simpleGrid.height; ++n2) {
                    for (n = 0; n < simpleGrid.width; ++n) {
                        blockPos3 = blockPos.offset(rotation.rotate(Direction.SOUTH), 8 + (n2 - this.startY) * 8);
                        blockPos3 = blockPos3.offset(rotation.rotate(Direction.EAST), (n - this.startX) * 8);
                        bl = Grid.isHouse(simpleGrid2, n, n2);
                        if (!Grid.isHouse(simpleGrid, n, n2) || !bl) continue;
                        if (!Grid.isHouse(simpleGrid, n + 1, n2)) {
                            blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 7);
                            list.add(new MansionTemplate(this.templateManager, "small_wall", blockPos2, rotation));
                        }
                        if (!Grid.isHouse(simpleGrid, n - 1, n2)) {
                            blockPos2 = blockPos3.offset(rotation.rotate(Direction.WEST), 1);
                            blockPos2 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 6);
                            list.add(new MansionTemplate(this.templateManager, "small_wall", blockPos2, rotation.add(Rotation.CLOCKWISE_180)));
                        }
                        if (!Grid.isHouse(simpleGrid, n, n2 - 1)) {
                            blockPos2 = blockPos3.offset(rotation.rotate(Direction.WEST), 0);
                            blockPos2 = blockPos2.offset(rotation.rotate(Direction.NORTH), 1);
                            list.add(new MansionTemplate(this.templateManager, "small_wall", blockPos2, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
                        }
                        if (!Grid.isHouse(simpleGrid, n, n2 + 1)) {
                            blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 6);
                            blockPos2 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 7);
                            list.add(new MansionTemplate(this.templateManager, "small_wall", blockPos2, rotation.add(Rotation.CLOCKWISE_90)));
                        }
                        if (!Grid.isHouse(simpleGrid, n + 1, n2)) {
                            if (!Grid.isHouse(simpleGrid, n, n2 - 1)) {
                                blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 7);
                                blockPos2 = blockPos2.offset(rotation.rotate(Direction.NORTH), 2);
                                list.add(new MansionTemplate(this.templateManager, "small_wall_corner", blockPos2, rotation));
                            }
                            if (!Grid.isHouse(simpleGrid, n, n2 + 1)) {
                                blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 8);
                                blockPos2 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 7);
                                list.add(new MansionTemplate(this.templateManager, "small_wall_corner", blockPos2, rotation.add(Rotation.CLOCKWISE_90)));
                            }
                        }
                        if (Grid.isHouse(simpleGrid, n - 1, n2)) continue;
                        if (!Grid.isHouse(simpleGrid, n, n2 - 1)) {
                            blockPos2 = blockPos3.offset(rotation.rotate(Direction.WEST), 2);
                            blockPos2 = blockPos2.offset(rotation.rotate(Direction.NORTH), 1);
                            list.add(new MansionTemplate(this.templateManager, "small_wall_corner", blockPos2, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
                        }
                        if (Grid.isHouse(simpleGrid, n, n2 + 1)) continue;
                        blockPos2 = blockPos3.offset(rotation.rotate(Direction.WEST), 1);
                        blockPos2 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 8);
                        list.add(new MansionTemplate(this.templateManager, "small_wall_corner", blockPos2, rotation.add(Rotation.CLOCKWISE_180)));
                    }
                }
            }
            for (n2 = 0; n2 < simpleGrid.height; ++n2) {
                for (n = 0; n < simpleGrid.width; ++n) {
                    BlockPos blockPos4;
                    blockPos3 = blockPos.offset(rotation.rotate(Direction.SOUTH), 8 + (n2 - this.startY) * 8);
                    blockPos3 = blockPos3.offset(rotation.rotate(Direction.EAST), (n - this.startX) * 8);
                    boolean bl3 = bl = simpleGrid2 != null && Grid.isHouse(simpleGrid2, n, n2);
                    if (!Grid.isHouse(simpleGrid, n, n2) || bl) continue;
                    if (!Grid.isHouse(simpleGrid, n + 1, n2)) {
                        blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 6);
                        if (!Grid.isHouse(simpleGrid, n, n2 + 1)) {
                            blockPos4 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 6);
                            list.add(new MansionTemplate(this.templateManager, "roof_corner", blockPos4, rotation));
                        } else if (Grid.isHouse(simpleGrid, n + 1, n2 + 1)) {
                            blockPos4 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 5);
                            list.add(new MansionTemplate(this.templateManager, "roof_inner_corner", blockPos4, rotation));
                        }
                        if (!Grid.isHouse(simpleGrid, n, n2 - 1)) {
                            list.add(new MansionTemplate(this.templateManager, "roof_corner", blockPos2, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
                        } else if (Grid.isHouse(simpleGrid, n + 1, n2 - 1)) {
                            blockPos4 = blockPos3.offset(rotation.rotate(Direction.EAST), 9);
                            blockPos4 = blockPos4.offset(rotation.rotate(Direction.NORTH), 2);
                            list.add(new MansionTemplate(this.templateManager, "roof_inner_corner", blockPos4, rotation.add(Rotation.CLOCKWISE_90)));
                        }
                    }
                    if (Grid.isHouse(simpleGrid, n - 1, n2)) continue;
                    blockPos2 = blockPos3.offset(rotation.rotate(Direction.EAST), 0);
                    blockPos2 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 0);
                    if (!Grid.isHouse(simpleGrid, n, n2 + 1)) {
                        blockPos4 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 6);
                        list.add(new MansionTemplate(this.templateManager, "roof_corner", blockPos4, rotation.add(Rotation.CLOCKWISE_90)));
                    } else if (Grid.isHouse(simpleGrid, n - 1, n2 + 1)) {
                        blockPos4 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 8);
                        blockPos4 = blockPos4.offset(rotation.rotate(Direction.WEST), 3);
                        list.add(new MansionTemplate(this.templateManager, "roof_inner_corner", blockPos4, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
                    }
                    if (!Grid.isHouse(simpleGrid, n, n2 - 1)) {
                        list.add(new MansionTemplate(this.templateManager, "roof_corner", blockPos2, rotation.add(Rotation.CLOCKWISE_180)));
                        continue;
                    }
                    if (!Grid.isHouse(simpleGrid, n - 1, n2 - 1)) continue;
                    blockPos4 = blockPos2.offset(rotation.rotate(Direction.SOUTH), 1);
                    list.add(new MansionTemplate(this.templateManager, "roof_inner_corner", blockPos4, rotation.add(Rotation.CLOCKWISE_180)));
                }
            }
        }

        private void entrance(List<MansionTemplate> list, PlacementData placementData) {
            Direction direction = placementData.rotation.rotate(Direction.WEST);
            list.add(new MansionTemplate(this.templateManager, "entrance", placementData.position.offset(direction, 9), placementData.rotation));
            placementData.position = placementData.position.offset(placementData.rotation.rotate(Direction.SOUTH), 16);
        }

        private void traverseWallPiece(List<MansionTemplate> list, PlacementData placementData) {
            list.add(new MansionTemplate(this.templateManager, placementData.wallType, placementData.position.offset(placementData.rotation.rotate(Direction.EAST), 7), placementData.rotation));
            placementData.position = placementData.position.offset(placementData.rotation.rotate(Direction.SOUTH), 8);
        }

        private void traverseTurn(List<MansionTemplate> list, PlacementData placementData) {
            placementData.position = placementData.position.offset(placementData.rotation.rotate(Direction.SOUTH), -1);
            list.add(new MansionTemplate(this.templateManager, "wall_corner", placementData.position, placementData.rotation));
            placementData.position = placementData.position.offset(placementData.rotation.rotate(Direction.SOUTH), -7);
            placementData.position = placementData.position.offset(placementData.rotation.rotate(Direction.WEST), -6);
            placementData.rotation = placementData.rotation.add(Rotation.CLOCKWISE_90);
        }

        private void traverseInnerTurn(List<MansionTemplate> list, PlacementData placementData) {
            placementData.position = placementData.position.offset(placementData.rotation.rotate(Direction.SOUTH), 6);
            placementData.position = placementData.position.offset(placementData.rotation.rotate(Direction.EAST), 8);
            placementData.rotation = placementData.rotation.add(Rotation.COUNTERCLOCKWISE_90);
        }

        private void addRoom1x1(List<MansionTemplate> list, BlockPos blockPos, Rotation rotation, Direction direction, RoomCollection roomCollection) {
            Rotation rotation2 = Rotation.NONE;
            String string = roomCollection.get1x1(this.random);
            if (direction != Direction.EAST) {
                if (direction == Direction.NORTH) {
                    rotation2 = rotation2.add(Rotation.COUNTERCLOCKWISE_90);
                } else if (direction == Direction.WEST) {
                    rotation2 = rotation2.add(Rotation.CLOCKWISE_180);
                } else if (direction == Direction.SOUTH) {
                    rotation2 = rotation2.add(Rotation.CLOCKWISE_90);
                } else {
                    string = roomCollection.get1x1Secret(this.random);
                }
            }
            BlockPos blockPos2 = Template.getZeroPositionWithTransform(new BlockPos(1, 0, 0), Mirror.NONE, rotation2, 7, 7);
            rotation2 = rotation2.add(rotation);
            blockPos2 = blockPos2.rotate(rotation);
            BlockPos blockPos3 = blockPos.add(blockPos2.getX(), 0, blockPos2.getZ());
            list.add(new MansionTemplate(this.templateManager, string, blockPos3, rotation2));
        }

        private void addRoom1x2(List<MansionTemplate> list, BlockPos blockPos, Rotation rotation, Direction direction, Direction direction2, RoomCollection roomCollection, boolean bl) {
            if (direction2 == Direction.EAST && direction == Direction.SOUTH) {
                BlockPos blockPos2 = blockPos.offset(rotation.rotate(Direction.EAST), 1);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2SideEntrance(this.random, bl), blockPos2, rotation));
            } else if (direction2 == Direction.EAST && direction == Direction.NORTH) {
                BlockPos blockPos3 = blockPos.offset(rotation.rotate(Direction.EAST), 1);
                blockPos3 = blockPos3.offset(rotation.rotate(Direction.SOUTH), 6);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2SideEntrance(this.random, bl), blockPos3, rotation, Mirror.LEFT_RIGHT));
            } else if (direction2 == Direction.WEST && direction == Direction.NORTH) {
                BlockPos blockPos4 = blockPos.offset(rotation.rotate(Direction.EAST), 7);
                blockPos4 = blockPos4.offset(rotation.rotate(Direction.SOUTH), 6);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2SideEntrance(this.random, bl), blockPos4, rotation.add(Rotation.CLOCKWISE_180)));
            } else if (direction2 == Direction.WEST && direction == Direction.SOUTH) {
                BlockPos blockPos5 = blockPos.offset(rotation.rotate(Direction.EAST), 7);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2SideEntrance(this.random, bl), blockPos5, rotation, Mirror.FRONT_BACK));
            } else if (direction2 == Direction.SOUTH && direction == Direction.EAST) {
                BlockPos blockPos6 = blockPos.offset(rotation.rotate(Direction.EAST), 1);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2SideEntrance(this.random, bl), blockPos6, rotation.add(Rotation.CLOCKWISE_90), Mirror.LEFT_RIGHT));
            } else if (direction2 == Direction.SOUTH && direction == Direction.WEST) {
                BlockPos blockPos7 = blockPos.offset(rotation.rotate(Direction.EAST), 7);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2SideEntrance(this.random, bl), blockPos7, rotation.add(Rotation.CLOCKWISE_90)));
            } else if (direction2 == Direction.NORTH && direction == Direction.WEST) {
                BlockPos blockPos8 = blockPos.offset(rotation.rotate(Direction.EAST), 7);
                blockPos8 = blockPos8.offset(rotation.rotate(Direction.SOUTH), 6);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2SideEntrance(this.random, bl), blockPos8, rotation.add(Rotation.CLOCKWISE_90), Mirror.FRONT_BACK));
            } else if (direction2 == Direction.NORTH && direction == Direction.EAST) {
                BlockPos blockPos9 = blockPos.offset(rotation.rotate(Direction.EAST), 1);
                blockPos9 = blockPos9.offset(rotation.rotate(Direction.SOUTH), 6);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2SideEntrance(this.random, bl), blockPos9, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
            } else if (direction2 == Direction.SOUTH && direction == Direction.NORTH) {
                BlockPos blockPos10 = blockPos.offset(rotation.rotate(Direction.EAST), 1);
                blockPos10 = blockPos10.offset(rotation.rotate(Direction.NORTH), 8);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2FrontEntrance(this.random, bl), blockPos10, rotation));
            } else if (direction2 == Direction.NORTH && direction == Direction.SOUTH) {
                BlockPos blockPos11 = blockPos.offset(rotation.rotate(Direction.EAST), 7);
                blockPos11 = blockPos11.offset(rotation.rotate(Direction.SOUTH), 14);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2FrontEntrance(this.random, bl), blockPos11, rotation.add(Rotation.CLOCKWISE_180)));
            } else if (direction2 == Direction.WEST && direction == Direction.EAST) {
                BlockPos blockPos12 = blockPos.offset(rotation.rotate(Direction.EAST), 15);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2FrontEntrance(this.random, bl), blockPos12, rotation.add(Rotation.CLOCKWISE_90)));
            } else if (direction2 == Direction.EAST && direction == Direction.WEST) {
                BlockPos blockPos13 = blockPos.offset(rotation.rotate(Direction.WEST), 7);
                blockPos13 = blockPos13.offset(rotation.rotate(Direction.SOUTH), 6);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2FrontEntrance(this.random, bl), blockPos13, rotation.add(Rotation.COUNTERCLOCKWISE_90)));
            } else if (direction2 == Direction.UP && direction == Direction.EAST) {
                BlockPos blockPos14 = blockPos.offset(rotation.rotate(Direction.EAST), 15);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2Secret(this.random), blockPos14, rotation.add(Rotation.CLOCKWISE_90)));
            } else if (direction2 == Direction.UP && direction == Direction.SOUTH) {
                BlockPos blockPos15 = blockPos.offset(rotation.rotate(Direction.EAST), 1);
                blockPos15 = blockPos15.offset(rotation.rotate(Direction.NORTH), 0);
                list.add(new MansionTemplate(this.templateManager, roomCollection.get1x2Secret(this.random), blockPos15, rotation));
            }
        }

        private void addRoom2x2(List<MansionTemplate> list, BlockPos blockPos, Rotation rotation, Direction direction, Direction direction2, RoomCollection roomCollection) {
            int n = 0;
            int n2 = 0;
            Rotation rotation2 = rotation;
            Mirror mirror = Mirror.NONE;
            if (direction2 == Direction.EAST && direction == Direction.SOUTH) {
                n = -7;
            } else if (direction2 == Direction.EAST && direction == Direction.NORTH) {
                n = -7;
                n2 = 6;
                mirror = Mirror.LEFT_RIGHT;
            } else if (direction2 == Direction.NORTH && direction == Direction.EAST) {
                n = 1;
                n2 = 14;
                rotation2 = rotation.add(Rotation.COUNTERCLOCKWISE_90);
            } else if (direction2 == Direction.NORTH && direction == Direction.WEST) {
                n = 7;
                n2 = 14;
                rotation2 = rotation.add(Rotation.COUNTERCLOCKWISE_90);
                mirror = Mirror.LEFT_RIGHT;
            } else if (direction2 == Direction.SOUTH && direction == Direction.WEST) {
                n = 7;
                n2 = -8;
                rotation2 = rotation.add(Rotation.CLOCKWISE_90);
            } else if (direction2 == Direction.SOUTH && direction == Direction.EAST) {
                n = 1;
                n2 = -8;
                rotation2 = rotation.add(Rotation.CLOCKWISE_90);
                mirror = Mirror.LEFT_RIGHT;
            } else if (direction2 == Direction.WEST && direction == Direction.NORTH) {
                n = 15;
                n2 = 6;
                rotation2 = rotation.add(Rotation.CLOCKWISE_180);
            } else if (direction2 == Direction.WEST && direction == Direction.SOUTH) {
                n = 15;
                mirror = Mirror.FRONT_BACK;
            }
            BlockPos blockPos2 = blockPos.offset(rotation.rotate(Direction.EAST), n);
            blockPos2 = blockPos2.offset(rotation.rotate(Direction.SOUTH), n2);
            list.add(new MansionTemplate(this.templateManager, roomCollection.get2x2(this.random), blockPos2, rotation2, mirror));
        }

        private void addRoom2x2Secret(List<MansionTemplate> list, BlockPos blockPos, Rotation rotation, RoomCollection roomCollection) {
            BlockPos blockPos2 = blockPos.offset(rotation.rotate(Direction.EAST), 1);
            list.add(new MansionTemplate(this.templateManager, roomCollection.get2x2Secret(this.random), blockPos2, rotation, Mirror.NONE));
        }
    }

    static class ThirdFloor
    extends SecondFloor {
        private ThirdFloor() {
        }
    }

    static class SimpleGrid {
        private final int[][] grid;
        private final int width;
        private final int height;
        private final int valueIfOutside;

        public SimpleGrid(int n, int n2, int n3) {
            this.width = n;
            this.height = n2;
            this.valueIfOutside = n3;
            this.grid = new int[n][n2];
        }

        public void set(int n, int n2, int n3) {
            if (n >= 0 && n < this.width && n2 >= 0 && n2 < this.height) {
                this.grid[n][n2] = n3;
            }
        }

        public void set(int n, int n2, int n3, int n4, int n5) {
            for (int i = n2; i <= n4; ++i) {
                for (int j = n; j <= n3; ++j) {
                    this.set(j, i, n5);
                }
            }
        }

        public int get(int n, int n2) {
            return n >= 0 && n < this.width && n2 >= 0 && n2 < this.height ? this.grid[n][n2] : this.valueIfOutside;
        }

        public void setIf(int n, int n2, int n3, int n4) {
            if (this.get(n, n2) == n3) {
                this.set(n, n2, n4);
            }
        }

        public boolean edgesTo(int n, int n2, int n3) {
            return this.get(n - 1, n2) == n3 || this.get(n + 1, n2) == n3 || this.get(n, n2 + 1) == n3 || this.get(n, n2 - 1) == n3;
        }
    }

    static class SecondFloor
    extends RoomCollection {
        private SecondFloor() {
        }

        @Override
        public String get1x1(Random random2) {
            return "1x1_b" + (random2.nextInt(4) + 1);
        }

        @Override
        public String get1x1Secret(Random random2) {
            return "1x1_as" + (random2.nextInt(4) + 1);
        }

        @Override
        public String get1x2SideEntrance(Random random2, boolean bl) {
            return bl ? "1x2_c_stairs" : "1x2_c" + (random2.nextInt(4) + 1);
        }

        @Override
        public String get1x2FrontEntrance(Random random2, boolean bl) {
            return bl ? "1x2_d_stairs" : "1x2_d" + (random2.nextInt(5) + 1);
        }

        @Override
        public String get1x2Secret(Random random2) {
            return "1x2_se" + (random2.nextInt(1) + 1);
        }

        @Override
        public String get2x2(Random random2) {
            return "2x2_b" + (random2.nextInt(5) + 1);
        }

        @Override
        public String get2x2Secret(Random random2) {
            return "2x2_s1";
        }
    }

    static abstract class RoomCollection {
        private RoomCollection() {
        }

        public abstract String get1x1(Random var1);

        public abstract String get1x1Secret(Random var1);

        public abstract String get1x2SideEntrance(Random var1, boolean var2);

        public abstract String get1x2FrontEntrance(Random var1, boolean var2);

        public abstract String get1x2Secret(Random var1);

        public abstract String get2x2(Random var1);

        public abstract String get2x2Secret(Random var1);
    }

    static class PlacementData {
        public Rotation rotation;
        public BlockPos position;
        public String wallType;

        private PlacementData() {
        }
    }

    public static class MansionTemplate
    extends TemplateStructurePiece {
        private final String templateName;
        private final Rotation rotation;
        private final Mirror mirror;

        public MansionTemplate(TemplateManager templateManager, String string, BlockPos blockPos, Rotation rotation) {
            this(templateManager, string, blockPos, rotation, Mirror.NONE);
        }

        public MansionTemplate(TemplateManager templateManager, String string, BlockPos blockPos, Rotation rotation, Mirror mirror) {
            super(IStructurePieceType.WMP, 0);
            this.templateName = string;
            this.templatePosition = blockPos;
            this.rotation = rotation;
            this.mirror = mirror;
            this.loadTemplate(templateManager);
        }

        public MansionTemplate(TemplateManager templateManager, CompoundNBT compoundNBT) {
            super(IStructurePieceType.WMP, compoundNBT);
            this.templateName = compoundNBT.getString("Template");
            this.rotation = Rotation.valueOf(compoundNBT.getString("Rot"));
            this.mirror = Mirror.valueOf(compoundNBT.getString("Mi"));
            this.loadTemplate(templateManager);
        }

        private void loadTemplate(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(new ResourceLocation("woodland_mansion/" + this.templateName));
            PlacementSettings placementSettings = new PlacementSettings().setIgnoreEntities(false).setRotation(this.rotation).setMirror(this.mirror).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementSettings);
        }

        @Override
        protected void readAdditional(CompoundNBT compoundNBT) {
            super.readAdditional(compoundNBT);
            compoundNBT.putString("Template", this.templateName);
            compoundNBT.putString("Rot", this.placeSettings.getRotation().name());
            compoundNBT.putString("Mi", this.placeSettings.getMirror().name());
        }

        @Override
        protected void handleDataMarker(String string, BlockPos blockPos, IServerWorld iServerWorld, Random random2, MutableBoundingBox mutableBoundingBox) {
            if (string.startsWith("Chest")) {
                Rotation rotation = this.placeSettings.getRotation();
                BlockState blockState = Blocks.CHEST.getDefaultState();
                if ("ChestWest".equals(string)) {
                    blockState = (BlockState)blockState.with(ChestBlock.FACING, rotation.rotate(Direction.WEST));
                } else if ("ChestEast".equals(string)) {
                    blockState = (BlockState)blockState.with(ChestBlock.FACING, rotation.rotate(Direction.EAST));
                } else if ("ChestSouth".equals(string)) {
                    blockState = (BlockState)blockState.with(ChestBlock.FACING, rotation.rotate(Direction.SOUTH));
                } else if ("ChestNorth".equals(string)) {
                    blockState = (BlockState)blockState.with(ChestBlock.FACING, rotation.rotate(Direction.NORTH));
                }
                this.generateChest(iServerWorld, mutableBoundingBox, random2, blockPos, LootTables.CHESTS_WOODLAND_MANSION, blockState);
            } else {
                AbstractIllagerEntity abstractIllagerEntity;
                int n = -1;
                switch (string.hashCode()) {
                    case -1505748702: {
                        if (!string.equals("Warrior")) break;
                        n = 1;
                        break;
                    }
                    case 2390418: {
                        if (!string.equals("Mage")) break;
                        n = 0;
                    }
                }
                switch (n) {
                    case 0: {
                        abstractIllagerEntity = EntityType.EVOKER.create(iServerWorld.getWorld());
                        break;
                    }
                    case 1: {
                        abstractIllagerEntity = EntityType.VINDICATOR.create(iServerWorld.getWorld());
                        break;
                    }
                    default: {
                        return;
                    }
                }
                abstractIllagerEntity.enablePersistence();
                abstractIllagerEntity.moveToBlockPosAndAngles(blockPos, 0.0f, 0.0f);
                abstractIllagerEntity.onInitialSpawn(iServerWorld, iServerWorld.getDifficultyForLocation(abstractIllagerEntity.getPosition()), SpawnReason.STRUCTURE, null, null);
                iServerWorld.func_242417_l(abstractIllagerEntity);
                iServerWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
            }
        }
    }

    static class FirstFloor
    extends RoomCollection {
        private FirstFloor() {
        }

        @Override
        public String get1x1(Random random2) {
            return "1x1_a" + (random2.nextInt(5) + 1);
        }

        @Override
        public String get1x1Secret(Random random2) {
            return "1x1_as" + (random2.nextInt(4) + 1);
        }

        @Override
        public String get1x2SideEntrance(Random random2, boolean bl) {
            return "1x2_a" + (random2.nextInt(9) + 1);
        }

        @Override
        public String get1x2FrontEntrance(Random random2, boolean bl) {
            return "1x2_b" + (random2.nextInt(5) + 1);
        }

        @Override
        public String get1x2Secret(Random random2) {
            return "1x2_s" + (random2.nextInt(2) + 1);
        }

        @Override
        public String get2x2(Random random2) {
            return "2x2_a" + (random2.nextInt(4) + 1);
        }

        @Override
        public String get2x2Secret(Random random2) {
            return "2x2_s1";
        }
    }
}

