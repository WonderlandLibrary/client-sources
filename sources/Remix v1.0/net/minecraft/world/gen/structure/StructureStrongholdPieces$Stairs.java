package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public static class Stairs extends Stronghold
{
    private boolean field_75024_a;
    
    public Stairs() {
    }
    
    public Stairs(final int p_i2081_1_, final Random p_i2081_2_, final int p_i2081_3_, final int p_i2081_4_) {
        super(p_i2081_1_);
        this.field_75024_a = true;
        this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2081_2_);
        this.field_143013_d = Door.OPENING;
        switch (StructureStrongholdPieces.SwitchEnumFacing.field_175951_b[this.coordBaseMode.ordinal()]) {
            case 1:
            case 2: {
                this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
                break;
            }
            default: {
                this.boundingBox = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
                break;
            }
        }
    }
    
    public Stairs(final int p_i45574_1_, final Random p_i45574_2_, final StructureBoundingBox p_i45574_3_, final EnumFacing p_i45574_4_) {
        super(p_i45574_1_);
        this.field_75024_a = false;
        this.coordBaseMode = p_i45574_4_;
        this.field_143013_d = this.getRandomDoor(p_i45574_2_);
        this.boundingBox = p_i45574_3_;
    }
    
    public static Stairs func_175863_a(final List p_175863_0_, final Random p_175863_1_, final int p_175863_2_, final int p_175863_3_, final int p_175863_4_, final EnumFacing p_175863_5_, final int p_175863_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175863_0_, var7) == null) ? new Stairs(p_175863_6_, p_175863_1_, var7, p_175863_5_) : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Source", this.field_75024_a);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.field_75024_a = p_143011_1_.getBoolean("Source");
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        if (this.field_75024_a) {
            StructureStrongholdPieces.access$102(Crossing.class);
        }
        this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 4, 10, 4, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 1, 7, 0);
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, Door.OPENING, 1, 1, 4);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 6, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 6, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 5, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 5, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 3, 4, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 3, 3, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 1, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.func_176624_a()), 1, 1, 3, p_74875_3_);
        return true;
    }
}
