package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Straight extends Piece
{
    public Straight() {
    }
    
    public Straight(final int p_i45620_1_, final Random p_i45620_2_, final StructureBoundingBox p_i45620_3_, final EnumFacing p_i45620_4_) {
        super(p_i45620_1_);
        this.coordBaseMode = p_i45620_4_;
        this.boundingBox = p_i45620_3_;
    }
    
    public static Straight func_175882_a(final List p_175882_0_, final Random p_175882_1_, final int p_175882_2_, final int p_175882_3_, final int p_175882_4_, final EnumFacing p_175882_5_, final int p_175882_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175882_2_, p_175882_3_, p_175882_4_, -1, -3, 0, 5, 10, 19, p_175882_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175882_0_, var7) == null) ? new Straight(p_175882_6_, p_175882_1_, var7, p_175882_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 3, false);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        this.func_175804_a(worldIn, p_74875_3_, 0, 3, 0, 4, 4, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 5, 0, 3, 7, 18, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 0, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 5, 0, 4, 5, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 4, 2, 5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 13, 4, 2, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 15, 4, 1, 18, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        for (int var4 = 0; var4 <= 4; ++var4) {
            for (int var5 = 0; var5 <= 2; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 18 - var5, p_74875_3_);
            }
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
