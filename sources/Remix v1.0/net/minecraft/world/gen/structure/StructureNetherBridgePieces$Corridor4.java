package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Corridor4 extends Piece
{
    public Corridor4() {
    }
    
    public Corridor4(final int p_i45618_1_, final Random p_i45618_2_, final StructureBoundingBox p_i45618_3_, final EnumFacing p_i45618_4_) {
        super(p_i45618_1_);
        this.coordBaseMode = p_i45618_4_;
        this.boundingBox = p_i45618_3_;
    }
    
    public static Corridor4 func_175880_a(final List p_175880_0_, final Random p_175880_1_, final int p_175880_2_, final int p_175880_3_, final int p_175880_4_, final EnumFacing p_175880_5_, final int p_175880_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175880_2_, p_175880_3_, p_175880_4_, -3, 0, 0, 9, 7, 9, p_175880_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175880_0_, var7) == null) ? new Corridor4(p_175880_6_, p_175880_1_, var7, p_175880_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        byte var4 = 1;
        if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
            var4 = 5;
        }
        this.getNextComponentX((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, var4, p_74861_3_.nextInt(8) > 0);
        this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 0, var4, p_74861_3_.nextInt(8) > 0);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
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
        for (int var4 = 0; var4 <= 5; ++var4) {
            for (int var5 = 0; var5 <= 8; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var5, -1, var4, p_74875_3_);
            }
        }
        return true;
    }
}
