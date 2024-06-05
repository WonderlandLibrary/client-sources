package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;

public static class House3 extends Village
{
    public House3() {
    }
    
    public House3(final Start p_i45561_1_, final int p_i45561_2_, final Random p_i45561_3_, final StructureBoundingBox p_i45561_4_, final EnumFacing p_i45561_5_) {
        super(p_i45561_1_, p_i45561_2_);
        this.coordBaseMode = p_i45561_5_;
        this.boundingBox = p_i45561_4_;
    }
    
    public static House3 func_175849_a(final Start p_175849_0_, final List p_175849_1_, final Random p_175849_2_, final int p_175849_3_, final int p_175849_4_, final int p_175849_5_, final EnumFacing p_175849_6_, final int p_175849_7_) {
        final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, p_175849_6_);
        return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175849_1_, var8) == null) ? new House3(p_175849_0_, p_175849_7_, p_175849_2_, var8, p_175849_6_) : null;
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
        this.func_175804_a(worldIn, p_74875_3_, 2, 0, 5, 8, 0, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 8, 0, 0, 8, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 0, 7, 2, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 5, 2, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 0, 6, 2, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 0, 10, 7, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 5, 2, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 4, 3, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 0, 4, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 0, 4, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 4, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 4, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 4, 4, p_74875_3_);
        final int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        final int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
        for (int var6 = -1; var6 <= 2; ++var6) {
            for (int var7 = 0; var7 <= 8; ++var7) {
                this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var4), var7, 4 + var6, var6, p_74875_3_);
                if ((var6 > -1 || var7 <= 1) && (var6 > 0 || var7 <= 3) && (var6 > 1 || var7 <= 4 || var7 >= 6)) {
                    this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var5), var7, 4 + var6, 5 - var6, p_74875_3_);
                }
            }
        }
        this.func_175804_a(worldIn, p_74875_3_, 3, 4, 5, 3, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 7, 4, 2, 7, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 5, 4, 4, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 5, 4, 6, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 6, 3, 5, 6, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        int var6 = this.getMetadataWithOffset(Blocks.oak_stairs, 0);
        for (int var7 = 4; var7 >= 1; --var7) {
            this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), var7, 2 + var7, 7 - var7, p_74875_3_);
            for (int var8 = 8 - var7; var8 <= 10; ++var8) {
                this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var6), var7, 2 + var7, var8, p_74875_3_);
            }
        }
        int var7 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 6, 6, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 7, 5, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var7), 6, 6, 4, p_74875_3_);
        for (int var8 = 6; var8 <= 8; ++var8) {
            for (int var9 = 5; var9 <= 10; ++var9) {
                this.func_175811_a(worldIn, Blocks.oak_stairs.getStateFromMeta(var7), var8, 12 - var8, var9, p_74875_3_);
            }
        }
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 0, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 0, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 4, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 5, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 6, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 8, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 8, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 8, 2, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 8, 2, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 8, 2, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 8, 2, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 2, 2, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 2, 2, 9, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 4, 4, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 5, 4, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 6, 4, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 5, 5, 10, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode), 2, 3, 1, p_74875_3_);
        this.func_175810_a(worldIn, p_74875_3_, p_74875_2_, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, -1, 3, 2, -1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        if (this.func_175807_a(worldIn, 2, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air && this.func_175807_a(worldIn, 2, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air) {
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, p_74875_3_);
        }
        for (int var8 = 0; var8 < 5; ++var8) {
            for (int var9 = 0; var9 < 9; ++var9) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var9, 7, var8, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.cobblestone.getDefaultState(), var9, -1, var8, p_74875_3_);
            }
        }
        for (int var8 = 5; var8 < 11; ++var8) {
            for (int var9 = 2; var9 < 9; ++var9) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var9, 7, var8, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.cobblestone.getDefaultState(), var9, -1, var8, p_74875_3_);
            }
        }
        this.spawnVillagers(worldIn, p_74875_3_, 4, 1, 2, 2);
        return true;
    }
}
