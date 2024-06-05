package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public static class Corridor2 extends Piece
{
    private boolean field_111020_b;
    
    public Corridor2() {
    }
    
    public Corridor2(final int p_i45613_1_, final Random p_i45613_2_, final StructureBoundingBox p_i45613_3_, final EnumFacing p_i45613_4_) {
        super(p_i45613_1_);
        this.coordBaseMode = p_i45613_4_;
        this.boundingBox = p_i45613_3_;
        this.field_111020_b = (p_i45613_2_.nextInt(3) == 0);
    }
    
    public static Corridor2 func_175876_a(final List p_175876_0_, final Random p_175876_1_, final int p_175876_2_, final int p_175876_3_, final int p_175876_4_, final EnumFacing p_175876_5_, final int p_175876_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175876_2_, p_175876_3_, p_175876_4_, -1, 0, 0, 5, 7, 5, p_175876_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175876_0_, var7) == null) ? new Corridor2(p_175876_6_, p_175876_1_, var7, p_175876_5_) : null;
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.field_111020_b = p_143011_1_.getBoolean("Chest");
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Chest", this.field_111020_b);
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
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
            this.func_180778_a(worldIn, p_74875_3_, p_74875_2_, 1, 2, 3, Corridor2.field_111019_a, 2 + p_74875_2_.nextInt(4));
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 6, 0, 4, 6, 4, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        for (int var4 = 0; var4 <= 4; ++var4) {
            for (int var5 = 0; var5 <= 4; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
            }
        }
        return true;
    }
}
