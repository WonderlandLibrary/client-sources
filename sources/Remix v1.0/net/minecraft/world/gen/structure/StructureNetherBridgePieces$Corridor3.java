package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Corridor3 extends Piece
{
    public Corridor3() {
    }
    
    public Corridor3(final int p_i45619_1_, final Random p_i45619_2_, final StructureBoundingBox p_i45619_3_, final EnumFacing p_i45619_4_) {
        super(p_i45619_1_);
        this.coordBaseMode = p_i45619_4_;
        this.boundingBox = p_i45619_3_;
    }
    
    public static Corridor3 func_175883_a(final List p_175883_0_, final Random p_175883_1_, final int p_175883_2_, final int p_175883_3_, final int p_175883_4_, final EnumFacing p_175883_5_, final int p_175883_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175883_2_, p_175883_3_, p_175883_4_, -1, -7, 0, 5, 14, 10, p_175883_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175883_0_, var7) == null) ? new Corridor3(p_175883_6_, p_175883_1_, var7, p_175883_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        final int var4 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 2);
        for (int var5 = 0; var5 <= 9; ++var5) {
            final int var6 = Math.max(1, 7 - var5);
            final int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
            final int var8 = var5;
            this.func_175804_a(worldIn, p_74875_3_, 0, 0, var5, 4, var6, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 1, var6 + 1, var5, 3, var7 - 1, var5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            if (var5 <= 6) {
                this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 1, var6 + 1, var5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 2, var6 + 1, var5, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), 3, var6 + 1, var5, p_74875_3_);
            }
            this.func_175804_a(worldIn, p_74875_3_, 0, var7, var5, 4, var7, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 0, var6 + 1, var5, 0, var7 - 1, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, 4, var6 + 1, var5, 4, var7 - 1, var5, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            if ((var5 & 0x1) == 0x0) {
                this.func_175804_a(worldIn, p_74875_3_, 0, var6 + 2, var5, 0, var6 + 3, var5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
                this.func_175804_a(worldIn, p_74875_3_, 4, var6 + 2, var5, 4, var6 + 3, var5, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
            }
            for (int var9 = 0; var9 <= 4; ++var9) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var9, -1, var8, p_74875_3_);
            }
        }
        return true;
    }
}
