package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Crossing extends Stronghold
{
    private boolean field_74996_b;
    private boolean field_74997_c;
    private boolean field_74995_d;
    private boolean field_74999_h;
    
    public Crossing() {
    }
    
    public Crossing(final int p_i45580_1_, final Random p_i45580_2_, final StructureBoundingBox p_i45580_3_, final EnumFacing p_i45580_4_) {
        super(p_i45580_1_);
        this.coordBaseMode = p_i45580_4_;
        this.field_143013_d = this.getRandomDoor(p_i45580_2_);
        this.boundingBox = p_i45580_3_;
        this.field_74996_b = p_i45580_2_.nextBoolean();
        this.field_74997_c = p_i45580_2_.nextBoolean();
        this.field_74995_d = p_i45580_2_.nextBoolean();
        this.field_74999_h = (p_i45580_2_.nextInt(3) > 0);
    }
    
    public static Crossing func_175866_a(final List p_175866_0_, final Random p_175866_1_, final int p_175866_2_, final int p_175866_3_, final int p_175866_4_, final EnumFacing p_175866_5_, final int p_175866_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
        return (Stronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_175866_0_, var7) == null) ? new Crossing(p_175866_6_, p_175866_1_, var7, p_175866_5_) : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("leftLow", this.field_74996_b);
        p_143012_1_.setBoolean("leftHigh", this.field_74997_c);
        p_143012_1_.setBoolean("rightLow", this.field_74995_d);
        p_143012_1_.setBoolean("rightHigh", this.field_74999_h);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.field_74996_b = p_143011_1_.getBoolean("leftLow");
        this.field_74997_c = p_143011_1_.getBoolean("leftHigh");
        this.field_74995_d = p_143011_1_.getBoolean("rightLow");
        this.field_74999_h = p_143011_1_.getBoolean("rightHigh");
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        int var4 = 3;
        int var5 = 5;
        if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
            var4 = 8 - var4;
            var5 = 8 - var5;
        }
        this.getNextComponentNormal((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, 5, 1);
        if (this.field_74996_b) {
            this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
        }
        if (this.field_74997_c) {
            this.getNextComponentX((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
        }
        if (this.field_74995_d) {
            this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
        }
        if (this.field_74999_h) {
            this.getNextComponentZ((Stairs2)p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 0, 0, 0, 9, 8, 10, true, p_74875_2_, StructureStrongholdPieces.access$000());
        this.placeDoor(worldIn, p_74875_2_, p_74875_3_, this.field_143013_d, 4, 3, 0);
        if (this.field_74996_b) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 3, 1, 0, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        if (this.field_74995_d) {
            this.func_175804_a(worldIn, p_74875_3_, 9, 3, 1, 9, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        if (this.field_74997_c) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 5, 7, 0, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        if (this.field_74999_h) {
            this.func_175804_a(worldIn, p_74875_3_, 9, 5, 7, 9, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 10, 7, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 2, 1, 8, 2, 6, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 4, 1, 5, 4, 4, 9, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 8, 1, 5, 8, 4, 9, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 4, 7, 3, 4, 9, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 1, 3, 5, 3, 3, 6, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 4, 3, 3, 4, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 6, 3, 4, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
        this.fillWithRandomizedBlocks(worldIn, p_74875_3_, 5, 1, 7, 7, 1, 8, false, p_74875_2_, StructureStrongholdPieces.access$000());
        this.func_175804_a(worldIn, p_74875_3_, 5, 1, 9, 7, 1, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 2, 7, 7, 2, 7, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 5, 7, 4, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 5, 7, 8, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState(), 6, 5, 6, p_74875_3_);
        return true;
    }
}
