package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Stairs extends Piece
{
    public Stairs() {
    }
    
    public Stairs(final int p_i45609_1_, final Random p_i45609_2_, final StructureBoundingBox p_i45609_3_, final EnumFacing p_i45609_4_) {
        super(p_i45609_1_);
        this.coordBaseMode = p_i45609_4_;
        this.boundingBox = p_i45609_3_;
    }
    
    public static Stairs func_175872_a(final List p_175872_0_, final Random p_175872_1_, final int p_175872_2_, final int p_175872_3_, final int p_175872_4_, final int p_175872_5_, final EnumFacing p_175872_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175872_2_, p_175872_3_, p_175872_4_, -2, 0, 0, 7, 11, 7, p_175872_6_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175872_0_, var7) == null) ? new Stairs(p_175872_5_, p_175872_1_, var7, p_175872_6_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentZ((Start)p_74861_1_, p_74861_2_, p_74861_3_, 6, 2, false);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
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
        for (int var4 = 0; var4 <= 6; ++var4) {
            for (int var5 = 0; var5 <= 6; ++var5) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var4, -1, var5, p_74875_3_);
            }
        }
        return true;
    }
}
