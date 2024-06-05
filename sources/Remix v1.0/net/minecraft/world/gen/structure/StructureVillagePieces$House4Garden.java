package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public static class House4Garden extends Village
{
    private boolean isRoofAccessible;
    
    public House4Garden() {
    }
    
    public House4Garden(final Start p_i45566_1_, final int p_i45566_2_, final Random p_i45566_3_, final StructureBoundingBox p_i45566_4_, final EnumFacing p_i45566_5_) {
        super(p_i45566_1_, p_i45566_2_);
        this.coordBaseMode = p_i45566_5_;
        this.boundingBox = p_i45566_4_;
        this.isRoofAccessible = p_i45566_3_.nextBoolean();
    }
    
    public static House4Garden func_175858_a(final Start p_175858_0_, final List p_175858_1_, final Random p_175858_2_, final int p_175858_3_, final int p_175858_4_, final int p_175858_5_, final EnumFacing p_175858_6_, final int p_175858_7_) {
        final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, p_175858_6_);
        return (StructureComponent.findIntersecting(p_175858_1_, var8) != null) ? null : new House4Garden(p_175858_0_, p_175858_7_, p_175858_2_, var8, p_175858_6_);
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Terrace", this.isRoofAccessible);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.isRoofAccessible = p_143011_1_.getBoolean("Terrace");
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, p_74875_3_);
            if (this.field_143015_k < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 4, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 0, 4, 4, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 1, 3, 4, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 0, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 0, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 0, 3, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 1, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cobblestone.getDefaultState(), 4, 3, 4, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 4, 3, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 2, 2, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 4, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 1, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 1, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 1, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 2, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 3, 3, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 3, 2, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), 3, 1, 0, p_74875_3_);
        if (this.func_175807_a(worldIn, 2, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air && this.func_175807_a(worldIn, 2, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air) {
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, p_74875_3_);
        }
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 3, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        if (this.isRoofAccessible) {
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 0, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 5, 4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 2, 5, 4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 3, 5, 4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 5, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 1, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 2, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 0, 5, 3, p_74875_3_);
        }
        if (this.isRoofAccessible) {
            final int var4 = this.getMetadataWithOffset(Blocks.ladder, 3);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, 1, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, 2, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, 3, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.ladder.getStateFromMeta(var4), 3, 4, 3, p_74875_3_);
        }
        this.func_175811_a(worldIn, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, this.coordBaseMode), 2, 3, 1, p_74875_3_);
        for (int var4 = 0; var4 < 5; ++var4) {
            for (int var5 = 0; var5 < 5; ++var5) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var5, 6, var4, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.cobblestone.getDefaultState(), var5, -1, var4, p_74875_3_);
            }
        }
        this.spawnVillagers(worldIn, p_74875_3_, 1, 1, 2, 1);
        return true;
    }
}
