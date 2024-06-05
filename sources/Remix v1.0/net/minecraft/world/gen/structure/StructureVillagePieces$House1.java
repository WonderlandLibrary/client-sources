package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public static class House1 extends Village
{
    public House1() {
    }
    
    public House1(final Start p_i45571_1_, final int p_i45571_2_, final Random p_i45571_3_, final StructureBoundingBox p_i45571_4_, final EnumFacing p_i45571_5_) {
        super(p_i45571_1_, p_i45571_2_);
        this.coordBaseMode = p_i45571_5_;
        this.boundingBox = p_i45571_4_;
    }
    
    public static House1 func_175850_a(final Start p_175850_0_, final List p_175850_1_, final Random p_175850_2_, final int p_175850_3_, final int p_175850_4_, final int p_175850_5_, final EnumFacing p_175850_6_, final int p_175850_7_) {
        final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, p_175850_6_);
        return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175850_1_, var8) == null) ? new House1(p_175850_0_, p_175850_7_, p_175850_2_, var8, p_175850_6_) : null;
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, p_74875_3_);
            if (this.field_143015_k < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
        }
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 7, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 8, 0, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 8, 5, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 6, 1, 8, 6, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 7, 2, 8, 7, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        final int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        final int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
        for (int var6 = -1; var6 <= 2; ++var6) {
            for (int var7 = 0; var7 <= 8; ++var7) {
                this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var4), var7, 6 + var6, var6, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var5), var7, 6 + var6, 5 - var6, p_74875_3_);
            }
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 0, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 5, 8, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 1, 0, 8, 1, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 0, 0, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 5, 0, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 2, 5, 8, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 2, 0, 8, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 2, 1, 0, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 5, 7, 4, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 2, 1, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 7, 4, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 5, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 6, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 3, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 6, 2, 5, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 1, 7, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 4, 7, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 3, 4, 7, 3, 4, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 7, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 0)), 7, 1, 3, p_74875_3_);
        int var6 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), 6, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), 5, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), 4, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), 3, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 6, 1, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 6, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 1, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), 4, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.crafting_table.getDefaultState(), 7, 1, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, p_74875_3_);
        this.func_175810_a(worldIn, p_74875_3_, p_74875_2_, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        if (this.func_175807_a(worldIn, 1, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air && this.func_175807_a(worldIn, 1, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air) {
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, p_74875_3_);
        }
        for (int var7 = 0; var7 < 6; ++var7) {
            for (int var8 = 0; var8 < 9; ++var8) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var8, 9, var7, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.cobblestone.getDefaultState(), var8, -1, var7, p_74875_3_);
            }
        }
        this.spawnVillagers(worldIn, p_74875_3_, 2, 1, 2, 1);
        return true;
    }
    
    @Override
    protected int func_180779_c(final int p_180779_1_, final int p_180779_2_) {
        return 1;
    }
}
