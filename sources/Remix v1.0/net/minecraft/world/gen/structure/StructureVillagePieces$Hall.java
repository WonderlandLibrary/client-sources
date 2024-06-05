package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;

public static class Hall extends Village
{
    public Hall() {
    }
    
    public Hall(final Start p_i45567_1_, final int p_i45567_2_, final Random p_i45567_3_, final StructureBoundingBox p_i45567_4_, final EnumFacing p_i45567_5_) {
        super(p_i45567_1_, p_i45567_2_);
        this.coordBaseMode = p_i45567_5_;
        this.boundingBox = p_i45567_4_;
    }
    
    public static Hall func_175857_a(final Start p_175857_0_, final List p_175857_1_, final Random p_175857_2_, final int p_175857_3_, final int p_175857_4_, final int p_175857_5_, final EnumFacing p_175857_6_, final int p_175857_7_) {
        final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, p_175857_6_);
        return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175857_1_, var8) == null) ? new Hall(p_175857_0_, p_175857_7_, p_175857_2_, var8, p_175857_6_) : null;
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, p_74875_3_);
            if (this.field_143015_k < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
        }
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 0, 6, 8, 0, 10, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 6, 0, 6, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 6, 2, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 1, 6, 8, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 10, 7, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 0, 0, 8, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 5, 7, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 5, 7, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 4, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, p_74875_3_);
        final int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        final int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
        for (int var6 = -1; var6 <= 2; ++var6) {
            for (int var7 = 0; var7 <= 8; ++var7) {
                this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var4), var7, 4 + var6, var6, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var5), var7, 4 + var6, 5 - var6, p_74875_3_);
            }
        }
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 2, 1, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 1, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 3, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.double_stone_slab.getDefaultState(), 6, 1, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode), 2, 3, 1, p_74875_3_);
        this.func_175810_a(worldIn, p_74875_3_, p_74875_2_, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        if (this.func_175807_a(worldIn, 2, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air && this.func_175807_a(worldIn, 2, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air) {
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, p_74875_3_);
        }
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 6, 1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 6, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.getOpposite()), 6, 3, 4, p_74875_3_);
        this.func_175810_a(worldIn, p_74875_3_, p_74875_2_, 6, 1, 5, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        for (int var6 = 0; var6 < 5; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var7, 7, var6, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.cobblestone.getDefaultState(), var7, -1, var6, p_74875_3_);
            }
        }
        this.spawnVillagers(worldIn, p_74875_3_, 4, 1, 2, 2);
        return true;
    }
    
    @Override
    protected int func_180779_c(final int p_180779_1_, final int p_180779_2_) {
        return (p_180779_1_ == 0) ? 4 : super.func_180779_c(p_180779_1_, p_180779_2_);
    }
}
