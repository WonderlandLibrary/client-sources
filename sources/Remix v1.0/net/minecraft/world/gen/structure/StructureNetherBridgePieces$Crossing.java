package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Crossing extends Piece
{
    public Crossing() {
    }
    
    public Crossing(final int p_i45610_1_, final Random p_i45610_2_, final StructureBoundingBox p_i45610_3_, final EnumFacing p_i45610_4_) {
        super(p_i45610_1_);
        this.coordBaseMode = p_i45610_4_;
        this.boundingBox = p_i45610_3_;
    }
    
    public static Crossing func_175873_a(final List p_175873_0_, final Random p_175873_1_, final int p_175873_2_, final int p_175873_3_, final int p_175873_4_, final EnumFacing p_175873_5_, final int p_175873_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175873_2_, p_175873_3_, p_175873_4_, -2, 0, 0, 7, 9, 7, p_175873_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175873_0_, var7) == null) ? new Crossing(p_175873_6_, p_175873_1_, var7, p_175873_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 2, 0, false);
        this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
        this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
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
        for (int var4 = 0; var4 <= 6; ++var4) {
            for (int var5 = 0; var5 <= 6; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
            }
        }
        return true;
    }
}
