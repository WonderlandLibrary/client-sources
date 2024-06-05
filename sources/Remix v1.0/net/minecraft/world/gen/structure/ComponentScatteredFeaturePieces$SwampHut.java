package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public static class SwampHut extends Feature
{
    private boolean hasWitch;
    
    public SwampHut() {
    }
    
    public SwampHut(final Random p_i2066_1_, final int p_i2066_2_, final int p_i2066_3_) {
        super(p_i2066_1_, p_i2066_2_, 64, p_i2066_3_, 7, 5, 9);
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setBoolean("Witch", this.hasWitch);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.hasWitch = p_143011_1_.getBoolean("Witch");
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (!this.func_74935_a(worldIn, p_74875_3_, 0)) {
            return false;
        }
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 5, 1, 7, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 4, 2, 5, 4, 7, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 1, 0, 4, 1, 0, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 2, 2, 3, 3, 2, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 2, 3, 1, 3, 6, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 2, 3, 5, 3, 6, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 2, 2, 7, 4, 3, 7, Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.func_176839_a()), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 2, 1, 3, 2, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 0, 2, 5, 3, 2, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 7, 1, 3, 7, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 5, 0, 7, 5, 3, 7, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 2, 3, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 3, 3, 7, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 3, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 5, 3, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 5, 3, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.flower_pot.getDefaultState().withProperty(BlockFlowerPot.field_176443_b, BlockFlowerPot.EnumFlowerType.MUSHROOM_RED), 1, 3, 5, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.crafting_table.getDefaultState(), 3, 2, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.cauldron.getDefaultState(), 4, 2, 6, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 2, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 5, 2, 1, p_74875_3_);
        final int var4 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        final int var5 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
        final int var6 = this.getMetadataWithOffset(Blocks.oak_stairs, 0);
        final int var7 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 1, 6, 4, 1, Blocks.spruce_stairs.getStateFromMeta(var4), Blocks.spruce_stairs.getStateFromMeta(var4), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 2, 0, 4, 7, Blocks.spruce_stairs.getStateFromMeta(var6), Blocks.spruce_stairs.getStateFromMeta(var6), false);
        this.func_175804_a(worldIn, p_74875_3_, 6, 4, 2, 6, 4, 7, Blocks.spruce_stairs.getStateFromMeta(var5), Blocks.spruce_stairs.getStateFromMeta(var5), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 4, 8, 6, 4, 8, Blocks.spruce_stairs.getStateFromMeta(var7), Blocks.spruce_stairs.getStateFromMeta(var7), false);
        for (int var8 = 2; var8 <= 7; var8 += 5) {
            for (int var9 = 1; var9 <= 5; var9 += 4) {
                this.func_175808_b(worldIn, Blocks.log.getDefaultState(), var9, -1, var8, p_74875_3_);
            }
        }
        if (!this.hasWitch) {
            final int var8 = this.getXWithOffset(2, 5);
            final int var9 = this.getYWithOffset(2);
            final int var10 = this.getZWithOffset(2, 5);
            if (p_74875_3_.func_175898_b(new BlockPos(var8, var9, var10))) {
                this.hasWitch = true;
                final EntityWitch var11 = new EntityWitch(worldIn);
                var11.setLocationAndAngles(var8 + 0.5, var9, var10 + 0.5, 0.0f, 0.0f);
                var11.func_180482_a(worldIn.getDifficultyForLocation(new BlockPos(var8, var9, var10)), null);
                worldIn.spawnEntityInWorld(var11);
            }
        }
        return true;
    }
}
