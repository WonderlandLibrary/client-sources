package net.minecraft.world.gen.structure;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import java.util.*;
import com.google.common.collect.*;

public static class DesertPyramid extends Feature
{
    private static final List itemsToGenerateInTemple;
    private boolean[] field_74940_h;
    
    public DesertPyramid() {
        this.field_74940_h = new boolean[4];
    }
    
    public DesertPyramid(final Random p_i2062_1_, final int p_i2062_2_, final int p_i2062_3_) {
        super(p_i2062_1_, p_i2062_2_, 64, p_i2062_3_, 21, 15, 21);
        this.field_74940_h = new boolean[4];
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("hasPlacedChest0", this.field_74940_h[0]);
        p_143012_1_.setBoolean("hasPlacedChest1", this.field_74940_h[1]);
        p_143012_1_.setBoolean("hasPlacedChest2", this.field_74940_h[2]);
        p_143012_1_.setBoolean("hasPlacedChest3", this.field_74940_h[3]);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.field_74940_h[0] = p_143011_1_.getBoolean("hasPlacedChest0");
        this.field_74940_h[1] = p_143011_1_.getBoolean("hasPlacedChest1");
        this.field_74940_h[2] = p_143011_1_.getBoolean("hasPlacedChest2");
        this.field_74940_h[3] = p_143011_1_.getBoolean("hasPlacedChest3");
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.func_175804_a(worldIn, p_74875_3_, 0, -4, 0, this.scatteredFeatureSizeX - 1, 0, this.scatteredFeatureSizeZ - 1, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        for (int var4 = 1; var4 <= 9; ++var4) {
            this.func_175804_a(worldIn, p_74875_3_, var4, var4, var4, this.scatteredFeatureSizeX - 1 - var4, var4, this.scatteredFeatureSizeZ - 1 - var4, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, var4 + 1, var4, var4 + 1, this.scatteredFeatureSizeX - 2 - var4, var4, this.scatteredFeatureSizeZ - 2 - var4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        for (int var4 = 0; var4 < this.scatteredFeatureSizeX; ++var4) {
            for (int var5 = 0; var5 < this.scatteredFeatureSizeZ; ++var5) {
                final byte var6 = -5;
                this.func_175808_b(worldIn, Blocks.sandstone.getDefaultState(), var4, var6, var5, p_74875_3_);
            }
        }
        int var4 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 3);
        int var5 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 2);
        final int var7 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 0);
        final int var8 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 1);
        final int var9 = ~EnumDyeColor.ORANGE.getDyeColorDamage() & 0xF;
        final int var10 = ~EnumDyeColor.BLUE.getDyeColorDamage() & 0xF;
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 9, 4, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 10, 1, 3, 10, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), 2, 10, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var5), 2, 10, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var7), 0, 10, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var8), 4, 10, 2, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 5, 0, 0, this.scatteredFeatureSizeX - 1, 9, 4, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 4, 10, 1, this.scatteredFeatureSizeX - 2, 10, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), this.scatteredFeatureSizeX - 3, 10, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var5), this.scatteredFeatureSizeX - 3, 10, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var7), this.scatteredFeatureSizeX - 5, 10, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var8), this.scatteredFeatureSizeX - 1, 10, 2, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 8, 0, 0, 12, 4, 4, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 1, 0, 11, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9, 1, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 9, 3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 10, 3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 11, 3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 11, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 11, 1, 1, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 8, 3, 3, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 1, 2, 8, 2, 2, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 12, 1, 1, 16, 3, 3, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 12, 1, 2, 16, 2, 2, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 4, 5, this.scatteredFeatureSizeX - 6, 4, this.scatteredFeatureSizeZ - 6, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 4, 9, 11, 4, 11, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 1, 8, 8, 3, 8, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 12, 1, 8, 12, 3, 8, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 1, 12, 8, 3, 12, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 12, 1, 12, 12, 3, 12, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 5, 4, 4, 11, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 5, 1, 5, this.scatteredFeatureSizeX - 2, 4, 11, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 7, 9, 6, 7, 11, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 7, 7, 9, this.scatteredFeatureSizeX - 7, 7, 11, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 5, 9, 5, 7, 11, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 6, 5, 9, this.scatteredFeatureSizeX - 6, 7, 11, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 5, 5, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 5, 6, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 6, 6, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - 6, 5, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - 6, 6, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - 7, 6, 10, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 2, 4, 4, 2, 6, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 3, 4, 4, this.scatteredFeatureSizeX - 3, 6, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), 2, 4, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), 2, 3, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), this.scatteredFeatureSizeX - 3, 4, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var4), this.scatteredFeatureSizeX - 3, 3, 4, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 3, 2, 2, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 3, 1, 3, this.scatteredFeatureSizeX - 2, 2, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getDefaultState(), 1, 1, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getDefaultState(), this.scatteredFeatureSizeX - 2, 1, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SAND.func_176624_a()), 1, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SAND.func_176624_a()), this.scatteredFeatureSizeX - 2, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var8), 2, 1, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone_stairs.getStateFromMeta(var7), this.scatteredFeatureSizeX - 3, 1, 2, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 4, 3, 5, 4, 3, 18, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 5, 3, 5, this.scatteredFeatureSizeX - 5, 3, 17, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 5, 4, 2, 16, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.scatteredFeatureSizeX - 6, 1, 5, this.scatteredFeatureSizeX - 5, 2, 16, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        for (int var11 = 5; var11 <= 17; var11 += 2) {
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 4, 1, var11, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), 4, 2, var11, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), this.scatteredFeatureSizeX - 5, 1, var11, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), this.scatteredFeatureSizeX - 5, 2, var11, p_74875_3_);
        }
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 10, 0, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 10, 0, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 9, 0, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 11, 0, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 8, 0, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 12, 0, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 7, 0, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 13, 0, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 9, 0, 11, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 11, 0, 11, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 10, 0, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 10, 0, 13, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var10), 10, 0, 10, p_74875_3_);
        for (int var11 = 0; var11 <= this.scatteredFeatureSizeX - 1; var11 += this.scatteredFeatureSizeX - 1) {
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 2, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 2, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 2, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 3, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 3, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 3, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 4, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), var11, 4, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 4, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 5, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 5, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 5, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 6, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), var11, 6, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 6, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 7, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 7, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 7, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 8, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 8, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 8, 3, p_74875_3_);
        }
        for (int var11 = 2; var11 <= this.scatteredFeatureSizeX - 3; var11 += this.scatteredFeatureSizeX - 3 - 2) {
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11 - 1, 2, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 2, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11 + 1, 2, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11 - 1, 3, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 3, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11 + 1, 3, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11 - 1, 4, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), var11, 4, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11 + 1, 4, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11 - 1, 5, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 5, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11 + 1, 5, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11 - 1, 6, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), var11, 6, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11 + 1, 6, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11 - 1, 7, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11, 7, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), var11 + 1, 7, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11 - 1, 8, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11, 8, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), var11 + 1, 8, 0, p_74875_3_);
        }
        this.func_175804_a(worldIn, p_74875_3_, 8, 4, 0, 12, 6, 0, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 8, 6, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 12, 6, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 9, 5, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), 10, 5, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stained_hardened_clay.getStateFromMeta(var9), 11, 5, 0, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 8, -14, 8, 12, -11, 12, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, -10, 8, 12, -10, 12, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, -9, 8, 12, -9, 12, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, -8, 8, 12, -1, 12, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, -11, 9, 11, -1, 11, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.stone_pressure_plate.getDefaultState(), 10, -11, 10, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 9, -13, 9, 11, -13, 11, Blocks.tnt.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 8, -11, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 8, -10, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), 7, -10, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 7, -11, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 12, -11, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 12, -10, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), 13, -10, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 13, -11, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, -11, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, -10, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), 10, -10, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 10, -11, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, -11, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 10, -10, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.func_176675_a()), 10, -10, 13, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.func_176675_a()), 10, -11, 13, p_74875_3_);
        for (final EnumFacing var13 : EnumFacing.Plane.HORIZONTAL) {
            if (!this.field_74940_h[var13.getHorizontalIndex()]) {
                final int var14 = var13.getFrontOffsetX() * 2;
                final int var15 = var13.getFrontOffsetZ() * 2;
                this.field_74940_h[var13.getHorizontalIndex()] = this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 10 + var14, -11, 10 + var15, WeightedRandomChestContent.func_177629_a(DesertPyramid.itemsToGenerateInTemple, Items.enchanted_book.getRandomEnchantedBook(p_74875_2_)), 2 + p_74875_2_.nextInt(5));
            }
        }
        return true;
    }
    
    static {
        itemsToGenerateInTemple = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15), new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2), new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1) });
    }
}
