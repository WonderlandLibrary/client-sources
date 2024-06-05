package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;

public static class Church extends Village
{
    public Church() {
    }
    
    public Church(final Start p_i45564_1_, final int p_i45564_2_, final Random p_i45564_3_, final StructureBoundingBox p_i45564_4_, final EnumFacing p_i45564_5_) {
        super(p_i45564_1_, p_i45564_2_);
        this.coordBaseMode = p_i45564_5_;
        this.boundingBox = p_i45564_4_;
    }
    
    public static Church func_175854_a(final Start p_175854_0_, final List p_175854_1_, final Random p_175854_2_, final int p_175854_3_, final int p_175854_4_, final int p_175854_5_, final EnumFacing p_175854_6_, final int p_175854_7_) {
        final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, p_175854_6_);
        return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175854_1_, var8) == null) ? new Church(p_175854_0_, p_175854_7_, p_175854_2_, var8, p_175854_6_) : null;
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, p_74875_3_);
            if (this.field_143015_k < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
        }
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 3, 3, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 5, 1, 3, 9, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 0, 3, 0, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 3, 10, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 4, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 4, 0, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 0, 4, 4, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 8, 3, 4, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 5, 4, 3, 10, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 5, 5, 3, 5, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 9, 0, 4, 9, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 0, 4, 4, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 0, 11, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 11, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 2, 11, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 1, 1, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 2, 1, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 3, 1, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 1, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 1, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 1)), 1, 2, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 0)), 3, 2, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 6, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 7, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 6, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 7, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 6, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 7, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 3, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 3, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 3, 8, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.getOpposite()), 2, 4, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.rotateY()), 1, 4, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.rotateYCCW()), 3, 4, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode), 2, 4, 5, p_74875_3_);
        final int var4 = this.getMetadataWithOffset(Blocks.ladder, 4);
        for (int var5 = 1; var5 <= 9; ++var5) {
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, var5, 3, p_74875_3_);
        }
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, 2, 0, p_74875_3_);
        this.func_175810_a(worldIn, p_74875_3_, p_74875_2_, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        if (this.func_175807_a(worldIn, 2, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air && this.func_175807_a(worldIn, 2, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air) {
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, p_74875_3_);
        }
        for (int var5 = 0; var5 < 9; ++var5) {
            for (int var6 = 0; var6 < 5; ++var6) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var6, 12, var5, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.cobblestone.getDefaultState(), var6, -1, var5, p_74875_3_);
            }
        }
        this.spawnVillagers(worldIn, p_74875_3_, 2, 1, 2, 1);
        return true;
    }
    
    @Override
    protected int func_180779_c(final int p_180779_1_, final int p_180779_2_) {
        return 2;
    }
}
