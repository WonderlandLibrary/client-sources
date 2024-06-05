package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Well extends Village
{
    public Well() {
    }
    
    public Well(final Start p_i2109_1_, final int p_i2109_2_, final Random p_i2109_3_, final int p_i2109_4_, final int p_i2109_5_) {
        super(p_i2109_1_, p_i2109_2_);
        this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2109_3_);
        switch (StructureVillagePieces.SwitchEnumFacing.field_176064_a[this.coordBaseMode.ordinal()]) {
            case 1:
            case 2: {
                this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                break;
            }
            default: {
                this.boundingBox = new StructureBoundingBox(p_i2109_4_, 64, p_i2109_5_, p_i2109_4_ + 6 - 1, 78, p_i2109_5_ + 6 - 1);
                break;
            }
        }
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, this.getComponentType());
        StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, this.getComponentType());
        StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
        StructureVillagePieces.access$000((Start)p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(worldIn, p_74875_3_);
            if (this.field_143015_k < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
        }
        this.func_175804_a(worldIn, p_74875_3_, 1, 0, 1, 4, 12, 4, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), false);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, 12, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 3, 12, 2, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, 12, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 3, 12, 3, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 1, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 13, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 1, 14, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 13, 4, p_74875_3_);
        this.func_175811_a(worldIn, Blocks.oak_fence.getDefaultState(), 4, 14, 4, p_74875_3_);
        this.func_175804_a(worldIn, p_74875_3_, 1, 15, 1, 4, 15, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
        for (int var4 = 0; var4 <= 5; ++var4) {
            for (int var5 = 0; var5 <= 5; ++var5) {
                if (var5 == 0 || var5 == 5 || var4 == 0 || var4 == 5) {
                    this.func_175811_a(worldIn, Blocks.gravel.getDefaultState(), var5, 11, var4, p_74875_3_);
                    this.clearCurrentPositionBlocksUpwards(worldIn, var5, 12, var4, p_74875_3_);
                }
            }
        }
        return true;
    }
}
