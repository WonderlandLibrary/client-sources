package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class NetherStalkRoom extends Piece
{
    public NetherStalkRoom() {
    }
    
    public NetherStalkRoom(final int p_i45612_1_, final Random p_i45612_2_, final StructureBoundingBox p_i45612_3_, final EnumFacing p_i45612_4_) {
        super(p_i45612_1_);
        this.coordBaseMode = p_i45612_4_;
        this.boundingBox = p_i45612_3_;
    }
    
    public static NetherStalkRoom func_175875_a(final List p_175875_0_, final Random p_175875_1_, final int p_175875_2_, final int p_175875_3_, final int p_175875_4_, final EnumFacing p_175875_5_, final int p_175875_6_) {
        final StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175875_2_, p_175875_3_, p_175875_4_, -5, -3, 0, 13, 14, 13, p_175875_5_);
        return (Piece.isAboveGround(var7) && StructureComponent.findIntersecting(p_175875_0_, var7) == null) ? new NetherStalkRoom(p_175875_6_, p_175875_1_, var7, p_175875_5_) : null;
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
        this.getNextComponentNormal((Start)p_74861_1_, p_74861_2_, p_74861_3_, 5, 11, true);
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
        int var4 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 3);
        for (int var5 = 0; var5 <= 6; ++var5) {
            final int var6 = var5 + 4;
            for (int var7 = 5; var7 <= 7; ++var7) {
                this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), var7, 5 + var5, var6, p_74875_3_);
            }
            if (var6 >= 5 && var6 <= 8) {
                this.func_175804_a(worldIn, p_74875_3_, 5, 5, var6, 7, var5 + 4, var6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            }
            else if (var6 >= 9 && var6 <= 10) {
                this.func_175804_a(worldIn, p_74875_3_, 5, 8, var6, 7, var5 + 4, var6, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
            }
            if (var5 >= 1) {
                this.func_175804_a(worldIn, p_74875_3_, 5, 6 + var5, var6, 7, 9 + var5, var6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
        }
        for (int var5 = 5; var5 <= 7; ++var5) {
            this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var4), var5, 12, 11, p_74875_3_);
        }
        this.func_175804_a(worldIn, p_74875_3_, 5, 6, 7, 5, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 6, 7, 7, 7, 7, Blocks.nether_brick_fence.getDefaultState(), Blocks.nether_brick_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 13, 12, 7, 13, 12, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 5, 2, 3, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 5, 9, 3, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 5, 4, 2, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 5, 2, 10, 5, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 5, 9, 10, 5, 10, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 10, 5, 4, 10, 5, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        int var5 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 0);
        final int var6 = this.getMetadataWithOffset(Blocks.nether_brick_stairs, 1);
        this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var6), 4, 5, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.nether_brick_stairs.getStateFromMeta(var5), 8, 5, 10, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 3, 4, 4, 4, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 4, 4, 9, 4, 8, Blocks.soul_sand.getDefaultState(), Blocks.soul_sand.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 5, 4, 4, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 5, 4, 9, 5, 8, Blocks.nether_wart.getDefaultState(), Blocks.nether_wart.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 2, 0, 8, 2, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 4, 12, 2, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 0, 0, 8, 1, 3, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 0, 9, 8, 1, 12, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 4, 3, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 9, 0, 4, 12, 1, 8, Blocks.nether_brick.getDefaultState(), Blocks.nether_brick.getDefaultState(), false);
        for (int var7 = 4; var7 <= 8; ++var7) {
            for (int var8 = 0; var8 <= 2; ++var8) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, var8, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, 12 - var8, p_74875_3_);
            }
        }
        for (int var7 = 0; var7 <= 2; ++var7) {
            for (int var8 = 4; var8 <= 8; ++var8) {
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), var7, -1, var8, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.nether_brick.getDefaultState(), 12 - var7, -1, var8, p_74875_3_);
            }
        }
        return true;
    }
}
