/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public class StructureNetherBridgePieces {
    private static final PieceWeight[] primaryComponents = new PieceWeight[]{new PieceWeight(Straight.class, 30, 0, true), new PieceWeight(Crossing3.class, 10, 4), new PieceWeight(Crossing.class, 10, 4), new PieceWeight(Stairs.class, 10, 3), new PieceWeight(Throne.class, 5, 2), new PieceWeight(Entrance.class, 5, 1)};
    private static final PieceWeight[] secondaryComponents = new PieceWeight[]{new PieceWeight(Corridor5.class, 25, 0, true), new PieceWeight(Crossing2.class, 15, 5), new PieceWeight(Corridor2.class, 5, 10), new PieceWeight(Corridor.class, 5, 10), new PieceWeight(Corridor3.class, 10, 3, true), new PieceWeight(Corridor4.class, 7, 2), new PieceWeight(NetherStalkRoom.class, 5, 2)};
    private static final String __OBFID = "CL_00000453";

    public static void registerNetherFortressPieces() {
        MapGenStructureIO.registerStructureComponent(Crossing3.class, "NeBCr");
        MapGenStructureIO.registerStructureComponent(End.class, "NeBEF");
        MapGenStructureIO.registerStructureComponent(Straight.class, "NeBS");
        MapGenStructureIO.registerStructureComponent(Corridor3.class, "NeCCS");
        MapGenStructureIO.registerStructureComponent(Corridor4.class, "NeCTB");
        MapGenStructureIO.registerStructureComponent(Entrance.class, "NeCE");
        MapGenStructureIO.registerStructureComponent(Crossing2.class, "NeSCSC");
        MapGenStructureIO.registerStructureComponent(Corridor.class, "NeSCLT");
        MapGenStructureIO.registerStructureComponent(Corridor5.class, "NeSC");
        MapGenStructureIO.registerStructureComponent(Corridor2.class, "NeSCRT");
        MapGenStructureIO.registerStructureComponent(NetherStalkRoom.class, "NeCSR");
        MapGenStructureIO.registerStructureComponent(Throne.class, "NeMT");
        MapGenStructureIO.registerStructureComponent(Crossing.class, "NeRC");
        MapGenStructureIO.registerStructureComponent(Stairs.class, "NeSR");
        MapGenStructureIO.registerStructureComponent(Start.class, "NeStart");
    }

    private static Piece func_175887_b(PieceWeight p_175887_0_, List p_175887_1_, Random p_175887_2_, int p_175887_3_, int p_175887_4_, int p_175887_5_, EnumFacing p_175887_6_, int p_175887_7_) {
        Class var8 = p_175887_0_.weightClass;
        Piece var9 = null;
        if (var8 == Straight.class) {
            var9 = Straight.func_175882_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Crossing3.class) {
            var9 = Crossing3.func_175885_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Crossing.class) {
            var9 = Crossing.func_175873_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Stairs.class) {
            var9 = Stairs.func_175872_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
        } else if (var8 == Throne.class) {
            var9 = Throne.func_175874_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_7_, p_175887_6_);
        } else if (var8 == Entrance.class) {
            var9 = Entrance.func_175881_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Corridor5.class) {
            var9 = Corridor5.func_175877_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Corridor2.class) {
            var9 = Corridor2.func_175876_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Corridor.class) {
            var9 = Corridor.func_175879_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Corridor3.class) {
            var9 = Corridor3.func_175883_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Corridor4.class) {
            var9 = Corridor4.func_175880_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == Crossing2.class) {
            var9 = Crossing2.func_175878_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        } else if (var8 == NetherStalkRoom.class) {
            var9 = NetherStalkRoom.func_175875_a(p_175887_1_, p_175887_2_, p_175887_3_, p_175887_4_, p_175887_5_, p_175887_6_, p_175887_7_);
        }
        return var9;
    }

    public static class Corridor
    extends Piece {
        private boolean field_111021_b;
        private static final String __OBFID = "CL_00000461";

        public Corridor() {
        }

        public Corridor(int p_i45615_1_, Random p_i45615_2_, StructureBoundingBox p_i45615_3_, EnumFacing p_i45615_4_) {
            super(p_i45615_1_);
            this.coordBaseMode = p_i45615_4_;
            this.boundingBox = p_i45615_3_;
            this.field_111021_b = p_i45615_2_.nextInt(3) == 0;
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound p_143011_1_) {
            super.readStructureFromNBT(p_143011_1_);
            this.field_111021_b = p_143011_1_.getBoolean("Chest");
        }

        @Override
        protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {
            super.writeStructureToNBT(p_143012_1_);
            p_143012_1_.setBoolean("Chest", this.field_111021_b);
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
        }

        public static Corridor func_175879_a(List p_175879_0_, Random p_175879_1_, int p_175879_2_, int p_175879_3_, int p_175879_4_, EnumFacing p_175879_5_, int p_175879_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175879_2_, p_175879_3_, p_175879_4_, -1, 0, 0, 5, 7, 5, p_175879_5_);
            return Corridor.isAboveGround(var7) && StructureComponent.findIntersecting(p_175879_0_, var7) == null ? new Corridor(p_175879_6_, p_175879_1_, var7, p_175879_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 3, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            if (this.field_111021_b && p_74875_3_.func_175898_b(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))) {
                this.field_111021_b = false;
                this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 3, 2, 3, field_111019_a, 2 + p_74875_2_.nextInt(4));
            }
            this.func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            int var4 = 0;
            while (var4 <= 4) {
                int var5 = 0;
                while (var5 <= 4) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Corridor2
    extends Piece {
        private boolean field_111020_b;
        private static final String __OBFID = "CL_00000463";

        public Corridor2() {
        }

        public Corridor2(int p_i45613_1_, Random p_i45613_2_, StructureBoundingBox p_i45613_3_, EnumFacing p_i45613_4_) {
            super(p_i45613_1_);
            this.coordBaseMode = p_i45613_4_;
            this.boundingBox = p_i45613_3_;
            this.field_111020_b = p_i45613_2_.nextInt(3) == 0;
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound p_143011_1_) {
            super.readStructureFromNBT(p_143011_1_);
            this.field_111020_b = p_143011_1_.getBoolean("Chest");
        }

        @Override
        protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {
            super.writeStructureToNBT(p_143012_1_);
            p_143012_1_.setBoolean("Chest", this.field_111020_b);
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
        }

        public static Corridor2 func_175876_a(List p_175876_0_, Random p_175876_1_, int p_175876_2_, int p_175876_3_, int p_175876_4_, EnumFacing p_175876_5_, int p_175876_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175876_2_, p_175876_3_, p_175876_4_, -1, 0, 0, 5, 7, 5, p_175876_5_);
            return Corridor2.isAboveGround(var7) && StructureComponent.findIntersecting(p_175876_0_, var7) == null ? new Corridor2(p_175876_6_, p_175876_1_, var7, p_175876_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 3, 4, 1, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 3, 3, 4, 3, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            if (this.field_111020_b && p_74875_3_.func_175898_b(new BlockPos(this.getXWithOffset(1, 3), this.getYWithOffset(2), this.getZWithOffset(1, 3)))) {
                this.field_111020_b = false;
                this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 1, 2, 3, field_111019_a, 2 + p_74875_2_.nextInt(4));
            }
            this.func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            int var4 = 0;
            while (var4 <= 4) {
                int var5 = 0;
                while (var5 <= 4) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Corridor3
    extends Piece {
        private static final String __OBFID = "CL_00000457";

        public Corridor3() {
        }

        public Corridor3(int p_i45619_1_, Random p_i45619_2_, StructureBoundingBox p_i45619_3_, EnumFacing p_i45619_4_) {
            super(p_i45619_1_);
            this.coordBaseMode = p_i45619_4_;
            this.boundingBox = p_i45619_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
        }

        public static Corridor3 func_175883_a(List p_175883_0_, Random p_175883_1_, int p_175883_2_, int p_175883_3_, int p_175883_4_, EnumFacing p_175883_5_, int p_175883_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175883_2_, p_175883_3_, p_175883_4_, -1, -7, 0, 5, 14, 10, p_175883_5_);
            return Corridor3.isAboveGround(var7) && StructureComponent.findIntersecting(p_175883_0_, var7) == null ? new Corridor3(p_175883_6_, p_175883_1_, var7, p_175883_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            int var4 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 2);
            int var5 = 0;
            while (var5 <= 9) {
                int var6 = Math.max(1, 7 - var5);
                int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
                int var8 = var5;
                this.func_175804_a(worldIn, p_74875_3_, 0, 0, var5, 4, var6, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 1, var6 + 1, var5, 3, var7 - 1, var5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                if (var5 <= 6) {
                    this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 1, var6 + 1, var5, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 2, var6 + 1, var5, p_74875_3_);
                    this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 3, var6 + 1, var5, p_74875_3_);
                }
                this.func_175804_a(worldIn, p_74875_3_, 0, var7, var5, 4, var7, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 0, var6 + 1, var5, 0, var7 - 1, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 4, var6 + 1, var5, 4, var7 - 1, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                if ((var5 & 1) == 0) {
                    this.func_175804_a(worldIn, p_74875_3_, 0, var6 + 2, var5, 0, var6 + 3, var5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                    this.func_175804_a(worldIn, p_74875_3_, 4, var6 + 2, var5, 4, var6 + 3, var5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                }
                int var9 = 0;
                while (var9 <= 4) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var9, -1, var8, p_74875_3_);
                    ++var9;
                }
                ++var5;
            }
            return true;
        }
    }

    public static class Corridor4
    extends Piece {
        private static final String __OBFID = "CL_00000458";

        public Corridor4() {
        }

        public Corridor4(int p_i45618_1_, Random p_i45618_2_, StructureBoundingBox p_i45618_3_, EnumFacing p_i45618_4_) {
            super(p_i45618_1_);
            this.coordBaseMode = p_i45618_4_;
            this.boundingBox = p_i45618_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            int var4 = 1;
            if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
                var4 = 5;
            }
            this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, var4, p_74861_3_.nextInt(8) > 0);
            this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, var4, p_74861_3_.nextInt(8) > 0);
        }

        public static Corridor4 func_175880_a(List p_175880_0_, Random p_175880_1_, int p_175880_2_, int p_175880_3_, int p_175880_4_, EnumFacing p_175880_5_, int p_175880_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175880_2_, p_175880_3_, p_175880_4_, -3, 0, 0, 9, 7, 9, p_175880_5_);
            return Corridor4.isAboveGround(var7) && StructureComponent.findIntersecting(p_175880_0_, var7) == null ? new Corridor4(p_175880_6_, p_175880_1_, var7, p_175880_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 8, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 8, 5, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 8, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 2, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 2, 0, 8, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 3, 0, 1, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 3, 0, 7, 4, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 8, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 1, 4, 2, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 4, 7, 2, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 8, 8, 3, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 6, 0, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 3, 6, 8, 3, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 4, 0, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 3, 4, 8, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 3, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 3, 5, 7, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 4, 5, 1, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 4, 5, 7, 5, 5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            int var4 = 0;
            while (var4 <= 5) {
                int var5 = 0;
                while (var5 <= 8) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var5, -1, var4, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Corridor5
    extends Piece {
        private static final String __OBFID = "CL_00000462";

        public Corridor5() {
        }

        public Corridor5(int p_i45614_1_, Random p_i45614_2_, StructureBoundingBox p_i45614_3_, EnumFacing p_i45614_4_) {
            super(p_i45614_1_);
            this.coordBaseMode = p_i45614_4_;
            this.boundingBox = p_i45614_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
        }

        public static Corridor5 func_175877_a(List p_175877_0_, Random p_175877_1_, int p_175877_2_, int p_175877_3_, int p_175877_4_, EnumFacing p_175877_5_, int p_175877_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175877_2_, p_175877_3_, p_175877_4_, -1, 0, 0, 5, 7, 5, p_175877_5_);
            return Corridor5.isAboveGround(var7) && StructureComponent.findIntersecting(p_175877_0_, var7) == null ? new Corridor5(p_175877_6_, p_175877_1_, var7, p_175877_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 3, 0, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 3, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 3, 3, 4, 4, 3, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            int var4 = 0;
            while (var4 <= 4) {
                int var5 = 0;
                while (var5 <= 4) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Crossing
    extends Piece {
        private static final String __OBFID = "CL_00000468";

        public Crossing() {
        }

        public Crossing(int p_i45610_1_, Random p_i45610_2_, StructureBoundingBox p_i45610_3_, EnumFacing p_i45610_4_) {
            super(p_i45610_1_);
            this.coordBaseMode = p_i45610_4_;
            this.boundingBox = p_i45610_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 2, 0, false);
            this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
            this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
        }

        public static Crossing func_175873_a(List p_175873_0_, Random p_175873_1_, int p_175873_2_, int p_175873_3_, int p_175873_4_, EnumFacing p_175873_5_, int p_175873_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175873_2_, p_175873_3_, p_175873_4_, -2, 0, 0, 7, 9, 7, p_175873_5_);
            return Crossing.isAboveGround(var7) && StructureComponent.findIntersecting(p_175873_0_, var7) == null ? new Crossing(p_175873_6_, p_175873_1_, var7, p_175873_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 6, 7, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 1, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 6, 1, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 2, 0, 6, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 2, 6, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 5, 0, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 2, 0, 6, 6, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 2, 5, 6, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 6, 0, 4, 6, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 6, 6, 4, 6, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 6, 4, 5, 6, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 6, 2, 0, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 6, 2, 6, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 5, 2, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            int var4 = 0;
            while (var4 <= 6) {
                int var5 = 0;
                while (var5 <= 6) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Crossing2
    extends Piece {
        private static final String __OBFID = "CL_00000460";

        public Crossing2() {
        }

        public Crossing2(int p_i45616_1_, Random p_i45616_2_, StructureBoundingBox p_i45616_3_, EnumFacing p_i45616_4_) {
            super(p_i45616_1_);
            this.coordBaseMode = p_i45616_4_;
            this.boundingBox = p_i45616_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
            this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
            this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
        }

        public static Crossing2 func_175878_a(List p_175878_0_, Random p_175878_1_, int p_175878_2_, int p_175878_3_, int p_175878_4_, EnumFacing p_175878_5_, int p_175878_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175878_2_, p_175878_3_, p_175878_4_, -1, 0, 0, 5, 7, 5, p_175878_5_);
            return Crossing2.isAboveGround(var7) && StructureComponent.findIntersecting(p_175878_0_, var7) == null ? new Crossing2(p_175878_6_, p_175878_1_, var7, p_175878_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 4, 5, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 0, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 2, 4, 4, 5, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            int var4 = 0;
            while (var4 <= 4) {
                int var5 = 0;
                while (var5 <= 4) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Crossing3
    extends Piece {
        private static final String __OBFID = "CL_00000454";

        public Crossing3() {
        }

        public Crossing3(int p_i45622_1_, Random p_i45622_2_, StructureBoundingBox p_i45622_3_, EnumFacing p_i45622_4_) {
            super(p_i45622_1_);
            this.coordBaseMode = p_i45622_4_;
            this.boundingBox = p_i45622_3_;
        }

        protected Crossing3(Random p_i2042_1_, int p_i2042_2_, int p_i2042_3_) {
            super(0);
            this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2042_1_);
            switch (this.coordBaseMode) {
                case NORTH: 
                case SOUTH: {
                    this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
                    break;
                }
                default: {
                    this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
                }
            }
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 8, 3, false);
            this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 3, 8, false);
            this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 3, 8, false);
        }

        public static Crossing3 func_175885_a(List p_175885_0_, Random p_175885_1_, int p_175885_2_, int p_175885_3_, int p_175885_4_, EnumFacing p_175885_5_, int p_175885_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175885_2_, p_175885_3_, p_175885_4_, -8, -3, 0, 19, 10, 19, p_175885_5_);
            return Crossing3.isAboveGround(var7) && StructureComponent.findIntersecting(p_175885_0_, var7) == null ? new Crossing3(p_175885_6_, p_175885_1_, var7, p_175885_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            int var5;
            this.func_175804_a(worldIn, p_74875_3_, 7, 3, 0, 11, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 7, 18, 4, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 5, 0, 10, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 8, 18, 7, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 5, 0, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 5, 11, 7, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 11, 5, 0, 11, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 11, 5, 11, 11, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 7, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 11, 5, 7, 18, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 11, 7, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 11, 5, 11, 18, 5, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 2, 0, 11, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 2, 13, 11, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 0, 0, 11, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 0, 15, 11, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            int var4 = 7;
            while (var4 <= 11) {
                var5 = 0;
                while (var5 <= 2) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 18 - var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 7, 5, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 13, 2, 7, 18, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 7, 3, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 15, 0, 7, 18, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            var4 = 0;
            while (var4 <= 2) {
                var5 = 7;
                while (var5 <= 11) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 18 - var4, -1, var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class End
    extends Piece {
        private int fillSeed;
        private static final String __OBFID = "CL_00000455";

        public End() {
        }

        public End(int p_i45621_1_, Random p_i45621_2_, StructureBoundingBox p_i45621_3_, EnumFacing p_i45621_4_) {
            super(p_i45621_1_);
            this.coordBaseMode = p_i45621_4_;
            this.boundingBox = p_i45621_3_;
            this.fillSeed = p_i45621_2_.nextInt();
        }

        public static End func_175884_a(List p_175884_0_, Random p_175884_1_, int p_175884_2_, int p_175884_3_, int p_175884_4_, EnumFacing p_175884_5_, int p_175884_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175884_2_, p_175884_3_, p_175884_4_, -1, -3, 0, 5, 10, 8, p_175884_5_);
            return End.isAboveGround(var7) && StructureComponent.findIntersecting(p_175884_0_, var7) == null ? new End(p_175884_6_, p_175884_1_, var7, p_175884_5_) : null;
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound p_143011_1_) {
            super.readStructureFromNBT(p_143011_1_);
            this.fillSeed = p_143011_1_.getInteger("Seed");
        }

        @Override
        protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {
            super.writeStructureToNBT(p_143012_1_);
            p_143012_1_.setInteger("Seed", this.fillSeed);
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            int var6;
            int var7;
            Random var4 = new Random(this.fillSeed);
            int var5 = 0;
            while (var5 <= 4) {
                var6 = 3;
                while (var6 <= 4) {
                    var7 = var4.nextInt(8);
                    this.func_175804_a(worldIn, p_74875_3_, var5, var6, 0, var5, var6, var7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                    ++var6;
                }
                ++var5;
            }
            var5 = var4.nextInt(8);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 0, 5, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            var5 = var4.nextInt(8);
            this.func_175804_a(worldIn, p_74875_3_, 4, 5, 0, 4, 5, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            var5 = 0;
            while (var5 <= 4) {
                var6 = var4.nextInt(5);
                this.func_175804_a(worldIn, p_74875_3_, var5, 2, 0, var5, 2, var6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                ++var5;
            }
            var5 = 0;
            while (var5 <= 4) {
                var6 = 0;
                while (var6 <= 1) {
                    var7 = var4.nextInt(3);
                    this.func_175804_a(worldIn, p_74875_3_, var5, var6, 0, var5, var6, var7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                    ++var6;
                }
                ++var5;
            }
            return true;
        }
    }

    public static class Entrance
    extends Piece {
        private static final String __OBFID = "CL_00000459";

        public Entrance() {
        }

        public Entrance(int p_i45617_1_, Random p_i45617_2_, StructureBoundingBox p_i45617_3_, EnumFacing p_i45617_4_) {
            super(p_i45617_1_);
            this.coordBaseMode = p_i45617_4_;
            this.boundingBox = p_i45617_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
        }

        public static Entrance func_175881_a(List p_175881_0_, Random p_175881_1_, int p_175881_2_, int p_175881_3_, int p_175881_4_, EnumFacing p_175881_5_, int p_175881_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175881_2_, p_175881_3_, p_175881_4_, -5, -3, 0, 13, 14, 13, p_175881_5_);
            return Entrance.isAboveGround(var7) && StructureComponent.findIntersecting(p_175881_0_, var7) == null ? new Entrance(p_175881_6_, p_175881_1_, var7, p_175881_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            int var5;
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 8, 0, 7, 8, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            int var4 = 1;
            while (var4 <= 11) {
                this.func_175804_a(worldIn, p_74875_3_, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), var4, 13, 0, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), var4, 13, 12, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, var4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, var4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 0, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 12, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, var4 + 1, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, var4 + 1, p_74875_3_);
                var4 += 2;
            }
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, p_74875_3_);
            var4 = 3;
            while (var4 <= 9) {
                this.func_175804_a(worldIn, p_74875_3_, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                var4 += 2;
            }
            this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            var4 = 4;
            while (var4 <= 8) {
                var5 = 0;
                while (var5 <= 2) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 12 - var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            var4 = 0;
            while (var4 <= 2) {
                var5 = 4;
                while (var5 <= 8) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 12 - var4, -1, var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            this.func_175804_a(worldIn, p_74875_3_, 5, 5, 5, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 1, 6, 6, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 6, 0, 6, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.flowing_lava.getDefaultState(), 6, 5, 6, p_74875_3_);
            BlockPos var6 = new BlockPos(this.getXWithOffset(6, 6), this.getYWithOffset(5), this.getZWithOffset(6, 6));
            if (p_74875_3_.func_175898_b(var6)) {
                worldIn.func_175637_a(Blocks.flowing_lava, var6, p_74875_2_);
            }
            return true;
        }
    }

    public static class NetherStalkRoom
    extends Piece {
        private static final String __OBFID = "CL_00000464";

        public NetherStalkRoom() {
        }

        public NetherStalkRoom(int p_i45612_1_, Random p_i45612_2_, StructureBoundingBox p_i45612_3_, EnumFacing p_i45612_4_) {
            super(p_i45612_1_);
            this.coordBaseMode = p_i45612_4_;
            this.boundingBox = p_i45612_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 11, true);
        }

        public static NetherStalkRoom func_175875_a(List p_175875_0_, Random p_175875_1_, int p_175875_2_, int p_175875_3_, int p_175875_4_, EnumFacing p_175875_5_, int p_175875_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175875_2_, p_175875_3_, p_175875_4_, -5, -3, 0, 13, 14, 13, p_175875_5_);
            return NetherStalkRoom.isAboveGround(var7) && StructureComponent.findIntersecting(p_175875_0_, var7) == null ? new NetherStalkRoom(p_175875_6_, p_175875_1_, var7, p_175875_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            int var6;
            int var7;
            int var8;
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 12, 4, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 12, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 1, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 11, 5, 0, 12, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 11, 4, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 5, 11, 10, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 9, 11, 7, 12, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 0, 4, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 5, 0, 10, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 9, 0, 7, 12, 1, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 11, 2, 10, 12, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            int var4 = 1;
            while (var4 <= 11) {
                this.func_175804_a(worldIn, p_74875_3_, var4, 10, 0, var4, 11, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, var4, 10, 12, var4, 11, 12, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 0, 10, var4, 0, 11, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 12, 10, var4, 12, 11, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), var4, 13, 0, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), var4, 13, 12, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 0, 13, var4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 12, 13, var4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 0, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), var4 + 1, 13, 12, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, var4 + 1, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, var4 + 1, p_74875_3_);
                var4 += 2;
            }
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, p_74875_3_);
            var4 = 3;
            while (var4 <= 9) {
                this.func_175804_a(worldIn, p_74875_3_, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                var4 += 2;
            }
            var4 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 3);
            int var5 = 0;
            while (var5 <= 6) {
                var6 = var5 + 4;
                var7 = 5;
                while (var7 <= 7) {
                    this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), var7, 5 + var5, var6, p_74875_3_);
                    ++var7;
                }
                if (var6 >= 5 && var6 <= 8) {
                    this.func_175804_a(worldIn, p_74875_3_, 5, 5, var6, 7, var5 + 4, var6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                } else if (var6 >= 9 && var6 <= 10) {
                    this.func_175804_a(worldIn, p_74875_3_, 5, 8, var6, 7, var5 + 4, var6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
                }
                if (var5 >= 1) {
                    this.func_175804_a(worldIn, p_74875_3_, 5, 6 + var5, var6, 7, 9 + var5, var6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                }
                ++var5;
            }
            var5 = 5;
            while (var5 <= 7) {
                this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), var5, 12, 11, p_74875_3_);
                ++var5;
            }
            this.func_175804_a(worldIn, p_74875_3_, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 13, 12, 7, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 2, 3, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 9, 3, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 4, 2, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 9, 5, 2, 10, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 9, 5, 9, 10, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 10, 5, 4, 10, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            var5 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 0);
            var6 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 1);
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 10, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 9, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 10, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 3, 4, 4, 4, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 4, 4, 9, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 3, 5, 4, 4, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 8, 5, 4, 9, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            var7 = 4;
            while (var7 <= 8) {
                var8 = 0;
                while (var8 <= 2) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, var8, p_74875_3_);
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, 12 - var8, p_74875_3_);
                    ++var8;
                }
                ++var7;
            }
            var7 = 0;
            while (var7 <= 2) {
                var8 = 4;
                while (var8 <= 8) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, var8, p_74875_3_);
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 12 - var7, -1, var8, p_74875_3_);
                    ++var8;
                }
                ++var7;
            }
            return true;
        }
    }

    static abstract class Piece
    extends StructureComponent {
        protected static final List field_111019_a = Lists.newArrayList((Object[])new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 5), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.nether_wart, 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 8), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 2, 4, 2)});
        private static final String __OBFID = "CL_00000466";

        public Piece() {
        }

        protected Piece(int p_i2054_1_) {
            super(p_i2054_1_);
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound p_143011_1_) {
        }

        @Override
        protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {
        }

        private int getTotalWeight(List p_74960_1_) {
            boolean var2 = false;
            int var3 = 0;
            for (PieceWeight var5 : p_74960_1_) {
                if (var5.field_78824_d > 0 && var5.field_78827_c < var5.field_78824_d) {
                    var2 = true;
                }
                var3 += var5.field_78826_b;
            }
            return var2 ? var3 : -1;
        }

        private Piece func_175871_a(Start p_175871_1_, List p_175871_2_, List p_175871_3_, Random p_175871_4_, int p_175871_5_, int p_175871_6_, int p_175871_7_, EnumFacing p_175871_8_, int p_175871_9_) {
            int var10 = this.getTotalWeight(p_175871_2_);
            boolean var11 = var10 > 0 && p_175871_9_ <= 30;
            int var12 = 0;
            block0 : while (var12 < 5 && var11) {
                ++var12;
                int var13 = p_175871_4_.nextInt(var10);
                for (PieceWeight var15 : p_175871_2_) {
                    if ((var13 -= var15.field_78826_b) >= 0) continue;
                    if (!var15.func_78822_a(p_175871_9_) || var15 == p_175871_1_.theNetherBridgePieceWeight && !var15.field_78825_e) continue block0;
                    Piece var16 = StructureNetherBridgePieces.func_175887_b(var15, p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
                    if (var16 == null) continue;
                    ++var15.field_78827_c;
                    p_175871_1_.theNetherBridgePieceWeight = var15;
                    if (!var15.func_78823_a()) {
                        p_175871_2_.remove(var15);
                    }
                    return var16;
                }
            }
            return End.func_175884_a(p_175871_3_, p_175871_4_, p_175871_5_, p_175871_6_, p_175871_7_, p_175871_8_, p_175871_9_);
        }

        private StructureComponent func_175870_a(Start p_175870_1_, List p_175870_2_, Random p_175870_3_, int p_175870_4_, int p_175870_5_, int p_175870_6_, EnumFacing p_175870_7_, int p_175870_8_, boolean p_175870_9_) {
            if (Math.abs(p_175870_4_ - p_175870_1_.getBoundingBox().minX) <= 112 && Math.abs(p_175870_6_ - p_175870_1_.getBoundingBox().minZ) <= 112) {
                Piece var11;
                List var10 = p_175870_1_.primaryWeights;
                if (p_175870_9_) {
                    var10 = p_175870_1_.secondaryWeights;
                }
                if ((var11 = this.func_175871_a(p_175870_1_, var10, p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_ + 1)) != null) {
                    p_175870_2_.add(var11);
                    p_175870_1_.field_74967_d.add(var11);
                }
                return var11;
            }
            return End.func_175884_a(p_175870_2_, p_175870_3_, p_175870_4_, p_175870_5_, p_175870_6_, p_175870_7_, p_175870_8_);
        }

        protected StructureComponent getNextComponentNormal(Start p_74963_1_, List p_74963_2_, Random p_74963_3_, int p_74963_4_, int p_74963_5_, boolean p_74963_6_) {
            if (this.coordBaseMode != null) {
                switch (this.coordBaseMode) {
                    case NORTH: {
                        return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType(), p_74963_6_);
                    }
                    case SOUTH: {
                        return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX + p_74963_4_, this.boundingBox.minY + p_74963_5_, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType(), p_74963_6_);
                    }
                    case WEST: {
                        return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, this.coordBaseMode, this.getComponentType(), p_74963_6_);
                    }
                    case EAST: {
                        return this.func_175870_a(p_74963_1_, p_74963_2_, p_74963_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74963_5_, this.boundingBox.minZ + p_74963_4_, this.coordBaseMode, this.getComponentType(), p_74963_6_);
                    }
                }
            }
            return null;
        }

        protected StructureComponent getNextComponentX(Start p_74961_1_, List p_74961_2_, Random p_74961_3_, int p_74961_4_, int p_74961_5_, boolean p_74961_6_) {
            if (this.coordBaseMode != null) {
                switch (this.coordBaseMode) {
                    case NORTH: {
                        return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, this.getComponentType(), p_74961_6_);
                    }
                    case SOUTH: {
                        return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX - 1, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ + p_74961_5_, EnumFacing.WEST, this.getComponentType(), p_74961_6_);
                    }
                    case WEST: {
                        return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), p_74961_6_);
                    }
                    case EAST: {
                        return this.func_175870_a(p_74961_1_, p_74961_2_, p_74961_3_, this.boundingBox.minX + p_74961_5_, this.boundingBox.minY + p_74961_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType(), p_74961_6_);
                    }
                }
            }
            return null;
        }

        protected StructureComponent getNextComponentZ(Start p_74965_1_, List p_74965_2_, Random p_74965_3_, int p_74965_4_, int p_74965_5_, boolean p_74965_6_) {
            if (this.coordBaseMode != null) {
                switch (this.coordBaseMode) {
                    case NORTH: {
                        return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, this.getComponentType(), p_74965_6_);
                    }
                    case SOUTH: {
                        return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74965_4_, this.boundingBox.minZ + p_74965_5_, EnumFacing.EAST, this.getComponentType(), p_74965_6_);
                    }
                    case WEST: {
                        return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), p_74965_6_);
                    }
                    case EAST: {
                        return this.func_175870_a(p_74965_1_, p_74965_2_, p_74965_3_, this.boundingBox.minX + p_74965_5_, this.boundingBox.minY + p_74965_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType(), p_74965_6_);
                    }
                }
            }
            return null;
        }

        protected static boolean isAboveGround(StructureBoundingBox p_74964_0_) {
            if (p_74964_0_ != null && p_74964_0_.minY > 10) {
                return true;
            }
            return false;
        }
    }

    static class PieceWeight {
        public Class weightClass;
        public final int field_78826_b;
        public int field_78827_c;
        public int field_78824_d;
        public boolean field_78825_e;
        private static final String __OBFID = "CL_00000467";

        public PieceWeight(Class p_i2055_1_, int p_i2055_2_, int p_i2055_3_, boolean p_i2055_4_) {
            this.weightClass = p_i2055_1_;
            this.field_78826_b = p_i2055_2_;
            this.field_78824_d = p_i2055_3_;
            this.field_78825_e = p_i2055_4_;
        }

        public PieceWeight(Class p_i2056_1_, int p_i2056_2_, int p_i2056_3_) {
            this(p_i2056_1_, p_i2056_2_, p_i2056_3_, false);
        }

        public boolean func_78822_a(int p_78822_1_) {
            if (this.field_78824_d != 0 && this.field_78827_c >= this.field_78824_d) {
                return false;
            }
            return true;
        }

        public boolean func_78823_a() {
            if (this.field_78824_d != 0 && this.field_78827_c >= this.field_78824_d) {
                return false;
            }
            return true;
        }
    }

    public static class Stairs
    extends Piece {
        private static final String __OBFID = "CL_00000469";

        public Stairs() {
        }

        public Stairs(int p_i45609_1_, Random p_i45609_2_, StructureBoundingBox p_i45609_3_, EnumFacing p_i45609_4_) {
            super(p_i45609_1_);
            this.coordBaseMode = p_i45609_4_;
            this.boundingBox = p_i45609_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 6, 2, false);
        }

        public static Stairs func_175872_a(List p_175872_0_, Random p_175872_1_, int p_175872_2_, int p_175872_3_, int p_175872_4_, int p_175872_5_, EnumFacing p_175872_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175872_2_, p_175872_3_, p_175872_4_, -2, 0, 0, 7, 11, 7, p_175872_6_);
            return Stairs.isAboveGround(var7) && StructureComponent.findIntersecting(p_175872_0_, var7) == null ? new Stairs(p_175872_5_, p_175872_1_, var7, p_175872_6_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 6, 1, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 6, 10, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 1, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 2, 0, 6, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 1, 0, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 2, 1, 6, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 6, 5, 8, 6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 2, 0, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 3, 2, 6, 5, 2, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 3, 4, 6, 5, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 5, 2, 5, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 4, 2, 5, 4, 3, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 3, 2, 5, 3, 4, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 2, 5, 2, 5, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 5, 1, 6, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 7, 1, 5, 7, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 8, 2, 6, 8, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 6, 0, 4, 8, 0, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 5, 0, 4, 5, 0, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            int var4 = 0;
            while (var4 <= 6) {
                int var5 = 0;
                while (var5 <= 6) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            return true;
        }
    }

    public static class Start
    extends Crossing3 {
        public PieceWeight theNetherBridgePieceWeight;
        public List primaryWeights;
        public List secondaryWeights;
        public List field_74967_d = Lists.newArrayList();
        private static final String __OBFID = "CL_00000470";

        public Start() {
        }

        public Start(Random p_i2059_1_, int p_i2059_2_, int p_i2059_3_) {
            PieceWeight var7;
            super(p_i2059_1_, p_i2059_2_, p_i2059_3_);
            this.primaryWeights = Lists.newArrayList();
            PieceWeight[] var4 = primaryComponents;
            int var5 = var4.length;
            int var6 = 0;
            while (var6 < var5) {
                var7 = var4[var6];
                var7.field_78827_c = 0;
                this.primaryWeights.add(var7);
                ++var6;
            }
            this.secondaryWeights = Lists.newArrayList();
            var4 = secondaryComponents;
            var5 = var4.length;
            var6 = 0;
            while (var6 < var5) {
                var7 = var4[var6];
                var7.field_78827_c = 0;
                this.secondaryWeights.add(var7);
                ++var6;
            }
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound p_143011_1_) {
            super.readStructureFromNBT(p_143011_1_);
        }

        @Override
        protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {
            super.writeStructureToNBT(p_143012_1_);
        }
    }

    public static class Straight
    extends Piece {
        private static final String __OBFID = "CL_00000456";

        public Straight() {
        }

        public Straight(int p_i45620_1_, Random p_i45620_2_, StructureBoundingBox p_i45620_3_, EnumFacing p_i45620_4_) {
            super(p_i45620_1_);
            this.coordBaseMode = p_i45620_4_;
            this.boundingBox = p_i45620_3_;
        }

        @Override
        public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_) {
            this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 3, false);
        }

        public static Straight func_175882_a(List p_175882_0_, Random p_175882_1_, int p_175882_2_, int p_175882_3_, int p_175882_4_, EnumFacing p_175882_5_, int p_175882_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175882_2_, p_175882_3_, p_175882_4_, -1, -3, 0, 5, 10, 19, p_175882_5_);
            return Straight.isAboveGround(var7) && StructureComponent.findIntersecting(p_175882_0_, var7) == null ? new Straight(p_175882_6_, p_175882_1_, var7, p_175882_5_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 4, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 5, 0, 3, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 0, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 5, 0, 4, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 13, 4, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, 15, 4, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            int var4 = 0;
            while (var4 <= 4) {
                int var5 = 0;
                while (var5 <= 2) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 18 - var5, p_74875_3_);
                    ++var5;
                }
                ++var4;
            }
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 4, 0, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 14, 0, 4, 14, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 1, 17, 0, 4, 17, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 4, 4, 1, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 3, 4, 4, 4, 4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 3, 14, 4, 4, 14, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, 1, 17, 4, 4, 17, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            return true;
        }
    }

    public static class Throne
    extends Piece {
        private boolean hasSpawner;
        private static final String __OBFID = "CL_00000465";

        public Throne() {
        }

        public Throne(int p_i45611_1_, Random p_i45611_2_, StructureBoundingBox p_i45611_3_, EnumFacing p_i45611_4_) {
            super(p_i45611_1_);
            this.coordBaseMode = p_i45611_4_;
            this.boundingBox = p_i45611_3_;
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound p_143011_1_) {
            super.readStructureFromNBT(p_143011_1_);
            this.hasSpawner = p_143011_1_.getBoolean("Mob");
        }

        @Override
        protected void writeStructureToNBT(NBTTagCompound p_143012_1_) {
            super.writeStructureToNBT(p_143012_1_);
            p_143012_1_.setBoolean("Mob", this.hasSpawner);
        }

        public static Throne func_175874_a(List p_175874_0_, Random p_175874_1_, int p_175874_2_, int p_175874_3_, int p_175874_4_, int p_175874_5_, EnumFacing p_175874_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175874_2_, p_175874_3_, p_175874_4_, -2, 0, 0, 7, 8, 9, p_175874_6_);
            return Throne.isAboveGround(var7) && StructureComponent.findIntersecting(p_175874_0_, var7) == null ? new Throne(p_175874_5_, p_175874_1_, var7, p_175874_6_) : null;
        }

        @Override
        public boolean addComponentParts(World worldIn, Random p_74875_2_, StructureBoundingBox p_74875_3_) {
            BlockPos var4;
            this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 6, 7, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 0, 0, 5, 1, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 1, 5, 2, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 3, 2, 5, 3, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 4, 3, 5, 4, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 1, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 2, 0, 5, 4, 2, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 5, 2, 1, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 5, 5, 2, 5, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 3, 0, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 5, 3, 6, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 5, 8, 5, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 1, 6, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 5, 6, 3, p_74875_3_);
            this.func_175804_a(worldIn, p_74875_3_, 0, 6, 3, 0, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 6, 6, 3, 6, 6, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, 6, 8, 5, 7, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 2, 8, 8, 4, 8, 8, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            if (!this.hasSpawner && p_74875_3_.func_175898_b(var4 = new BlockPos(this.getXWithOffset(3, 5), this.getYWithOffset(5), this.getZWithOffset(3, 5)))) {
                this.hasSpawner = true;
                worldIn.setBlockState(var4, Blocks.mob_spawner.getDefaultState(), 2);
                TileEntity var5 = worldIn.getTileEntity(var4);
                if (var5 instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner)var5).getSpawnerBaseLogic().setEntityName("Blaze");
                }
            }
            int var6 = 0;
            while (var6 <= 6) {
                int var7 = 0;
                while (var7 <= 6) {
                    this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var6, -1, var7, p_74875_3_);
                    ++var7;
                }
                ++var6;
            }
            return true;
        }
    }

}

