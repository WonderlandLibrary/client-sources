package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public static class Cross extends StructureComponent
{
    private EnumFacing corridorDirection;
    private boolean isMultipleFloors;
    
    public Cross() {
    }
    
    public Cross(final int p_i45624_1_, final Random p_i45624_2_, final StructureBoundingBox p_i45624_3_, final EnumFacing p_i45624_4_) {
        super(p_i45624_1_);
        this.corridorDirection = p_i45624_4_;
        this.boundingBox = p_i45624_3_;
        this.isMultipleFloors = (p_i45624_3_.getYSize() > 3);
    }
    
    public static StructureBoundingBox func_175813_a(final List p_175813_0_, final Random p_175813_1_, final int p_175813_2_, final int p_175813_3_, final int p_175813_4_, final EnumFacing p_175813_5_) {
        final StructureBoundingBox var6 = new StructureBoundingBox(p_175813_2_, p_175813_3_, p_175813_4_, p_175813_2_, p_175813_3_ + 2, p_175813_4_);
        if (p_175813_1_.nextInt(4) == 0) {
            final StructureBoundingBox structureBoundingBox = var6;
            structureBoundingBox.maxY += 4;
        }
        switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[p_175813_5_.ordinal()]) {
            case 1: {
                var6.minX = p_175813_2_ - 1;
                var6.maxX = p_175813_2_ + 3;
                var6.minZ = p_175813_4_ - 4;
                break;
            }
            case 2: {
                var6.minX = p_175813_2_ - 1;
                var6.maxX = p_175813_2_ + 3;
                var6.maxZ = p_175813_4_ + 4;
                break;
            }
            case 3: {
                var6.minX = p_175813_2_ - 4;
                var6.minZ = p_175813_4_ - 1;
                var6.maxZ = p_175813_4_ + 3;
                break;
            }
            case 4: {
                var6.maxX = p_175813_2_ + 4;
                var6.minZ = p_175813_4_ - 1;
                var6.maxZ = p_175813_4_ + 3;
                break;
            }
        }
        return (StructureComponent.findIntersecting(p_175813_0_, var6) != null) ? null : var6;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        p_143012_1_.setBoolean("tf", this.isMultipleFloors);
        p_143012_1_.setInteger("D", this.corridorDirection.getHorizontalIndex());
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        this.isMultipleFloors = p_143011_1_.getBoolean("tf");
        this.corridorDirection = EnumFacing.getHorizontal(p_143011_1_.getInteger("D"));
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        final int var4 = this.getComponentType();
        switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[this.corridorDirection.ordinal()]) {
            case 1: {
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var4);
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var4);
                break;
            }
            case 2: {
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var4);
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var4);
                break;
            }
            case 3: {
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var4);
                break;
            }
            case 4: {
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var4);
                break;
            }
        }
        if (this.isMultipleFloors) {
            if (p_74861_3_.nextBoolean()) {
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
            }
            if (p_74861_3_.nextBoolean()) {
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.WEST, var4);
            }
            if (p_74861_3_.nextBoolean()) {
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.EAST, var4);
            }
            if (p_74861_3_.nextBoolean()) {
                StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
            }
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        if (this.isMultipleFloors) {
            this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        else {
            this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
        for (int var4 = this.boundingBox.minX; var4 <= this.boundingBox.maxX; ++var4) {
            for (int var5 = this.boundingBox.minZ; var5 <= this.boundingBox.maxZ; ++var5) {
                if (this.func_175807_a(worldIn, var4, this.boundingBox.minY - 1, var5, p_74875_3_).getBlock().getMaterial() == Material.air) {
                    this.func_175811_a(worldIn, Blocks.planks.getDefaultState(), var4, this.boundingBox.minY - 1, var5, p_74875_3_);
                }
            }
        }
        return true;
    }
}
