package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public static class Entrance extends Piece
{
    public Entrance() {
    }
    
    public Entrance(final int p_i45617_1_, final Random p_i45617_2_, final StructureBoundingBox p_i45617_3_, final EnumFacing p_i45617_4_) {
        super(p_i45617_1_);
        this.coordBaseMode = p_i45617_4_;
        this.boundingBox = p_i45617_3_;
    }
    
    public static Entrance func_175881_a(final List p_175881_0_, final Random p_175881_1_, final int p_175881_2_, final int p_175881_3_, final int p_175881_4_, final EnumFacing p_175881_5_, final int p_175881_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175881_2_, p_175881_3_, p_175881_4_, -5, -3, 0, 13, 14, 13, p_175881_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175881_0_, var7) == null) ? new Entrance(p_175881_6_, p_175881_1_, var7, p_175881_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
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
        for (int var4 = 1; var4 <= 11; var4 += 2) {
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
        }
        this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 12, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 0, 13, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_fence.getDefaultState(), 12, 13, 0, p_74875_3_);
        for (int var4 = 3; var4 <= 9; var4 += 2) {
            this.func_175804_a(worldIn, p_74875_3_, 1, 7, var4, 1, 8, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 11, 7, var4, 11, 8, var4, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
        }
        this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        for (int var4 = 4; var4 <= 8; ++var4) {
            for (int var5 = 0; var5 <= 2; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, 12 - var5, p_74875_3_);
            }
        }
        for (int var4 = 0; var4 <= 2; ++var4) {
            for (int var5 = 4; var5 <= 8; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 12 - var4, -1, var5, p_74875_3_);
            }
        }
        this.func_175804_a(worldIn, p_74875_3_, 5, 5, 5, 7, 5, 7, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 1, 6, 6, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.nether_brick.getDefaultState(), 6, 0, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.flowing_lava.getDefaultState(), 6, 5, 6, p_74875_3_);
        final BlockPos var6 = new BlockPos(this.getXWithOffset(6, 6), this.getYWithOffset(5), this.getZWithOffset(6, 6));
        if (p_74875_3_.func_175898_b(var6)) {
            worldIn.func_175637_a(Blocks.flowing_lava, var6, p_74875_2_);
        }
        return true;
    }
}
