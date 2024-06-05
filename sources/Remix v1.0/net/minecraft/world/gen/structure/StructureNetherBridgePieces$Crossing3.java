package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Crossing3 extends Piece
{
    public Crossing3() {
    }
    
    public Crossing3(final int p_i45622_1_, final Random p_i45622_2_, final StructureBoundingBox p_i45622_3_, final EnumFacing p_i45622_4_) {
        super(p_i45622_1_);
        this.coordBaseMode = p_i45622_4_;
        this.boundingBox = p_i45622_3_;
    }
    
    protected Crossing3(final Random p_i2042_1_, final int p_i2042_2_, final int p_i2042_3_) {
        super(0);
        this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2042_1_);
        switch (StructureNetherBridgePieces.SwitchEnumFacing.field_175888_a[this.coordBaseMode.ordinal()]) {
            case 1:
            case 2: {
                this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
                break;
            }
            default: {
                this.boundingBox = new StructureBoundingBox(p_i2042_2_, 64, p_i2042_3_, p_i2042_2_ + 19 - 1, 73, p_i2042_3_ + 19 - 1);
                break;
            }
        }
    }
    
    public static Crossing3 func_175885_a(final List p_175885_0_, final Random p_175885_1_, final int p_175885_2_, final int p_175885_3_, final int p_175885_4_, final EnumFacing p_175885_5_, final int p_175885_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175885_2_, p_175885_3_, p_175885_4_, -8, -3, 0, 19, 10, 19, p_175885_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175885_0_, var7) == null) ? new Crossing3(p_175885_6_, p_175885_1_, var7, p_175885_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 8, 3, false);
        this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 3, 8, false);
        this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 3, 8, false);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
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
        for (int var4 = 7; var4 <= 11; ++var4) {
            for (int var5 = 0; var5 <= 2; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 18 - var5, p_74875_3_);
            }
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 7, 5, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 13, 2, 7, 18, 2, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 7, 3, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 15, 0, 7, 18, 1, 11, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        for (int var4 = 0; var4 <= 2; ++var4) {
            for (int var5 = 7; var5 <= 11; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 18 - var4, -1, var5, p_74875_3_);
            }
        }
        return true;
    }
}
