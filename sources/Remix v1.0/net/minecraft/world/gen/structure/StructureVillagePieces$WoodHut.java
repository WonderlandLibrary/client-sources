package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public static class WoodHut extends Village
{
    private boolean isTallHouse;
    private int tablePosition;
    
    public WoodHut() {
    }
    
    public WoodHut(final Start p_i45565_1_, final int p_i45565_2_, final Random p_i45565_3_, final StructureBoundingBox p_i45565_4_, final EnumFacing p_i45565_5_) {
        super(p_i45565_1_, p_i45565_2_);
        this.coordBaseMode = p_i45565_5_;
        this.boundingBox = p_i45565_4_;
        this.isTallHouse = p_i45565_3_.nextBoolean();
        this.tablePosition = p_i45565_3_.nextInt(3);
    }
    
    public static WoodHut func_175853_a(final Start p_175853_0_, final List p_175853_1_, final Random p_175853_2_, final int p_175853_3_, final int p_175853_4_, final int p_175853_5_, final EnumFacing p_175853_6_, final int p_175853_7_) {
        final StructureBoundingBox var8 = StructureBoundingBox.func_175897_a(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, p_175853_6_);
        return (Village.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_175853_1_, var8) == null) ? new WoodHut(p_175853_0_, p_175853_7_, p_175853_2_, var8, p_175853_6_) : null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setInteger("T", this.tablePosition);
        p_143012_1_.setBoolean("C", this.isTallHouse);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.tablePosition = p_143011_1_.getInteger("T");
        this.isTallHouse = p_143011_1_.getBoolean("C");
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
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
        if (this.isTallHouse) {
            this.func_175804_a(worldIn, p_74875_3_, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        }
        else {
            this.func_175804_a(worldIn, p_74875_3_, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        }
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 1, 4, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 2, 4, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 1, 4, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 2, 4, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 0, 4, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 0, 4, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 0, 4, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 3, 4, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 3, 4, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.log.getDefaultState(), 3, 4, 3, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 0, 2, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.glass_pane.getDefaultState(), 3, 2, 2, p_74875_3_);
        if (this.tablePosition > 0) {
            this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, p_74875_3_);
        }
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 1, 0, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, 2, 0, p_74875_3_);
        this.func_175810_a(worldIn, p_74875_3_, p_74875_2_, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        if (this.func_175807_a(worldIn, 1, 0, -1, p_74875_3_).getBlock().getMaterial() == Material.air && this.func_175807_a(worldIn, 1, -1, -1, p_74875_3_).getBlock().getMaterial() != Material.air) {
            this.func_175811_a(worldIn, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, p_74875_3_);
        }
        for (int var4 = 0; var4 < 5; ++var4) {
            for (int var5 = 0; var5 < 4; ++var5) {
                this.clearCurrentPositionBlocksUpwards(worldIn, var5, 6, var4, p_74875_3_);
                this.func_175808_b(worldIn, Blocks.cobblestone.getDefaultState(), var5, -1, var4, p_74875_3_);
            }
        }
        this.spawnVillagers(worldIn, p_74875_3_, 1, 1, 2, 1);
        return true;
    }
}
