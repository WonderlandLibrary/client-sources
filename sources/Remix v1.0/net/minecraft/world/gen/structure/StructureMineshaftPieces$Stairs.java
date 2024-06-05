package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Stairs extends StructureComponent
{
    public Stairs() {
    }
    
    public Stairs(final int p_i45623_1_, final Random p_i45623_2_, final StructureBoundingBox p_i45623_3_, final EnumFacing p_i45623_4_) {
        super(p_i45623_1_);
        this.coordBaseMode = p_i45623_4_;
        this.boundingBox = p_i45623_3_;
    }
    
    public static StructureBoundingBox func_175812_a(final List p_175812_0_, final Random p_175812_1_, final int p_175812_2_, final int p_175812_3_, final int p_175812_4_, final EnumFacing p_175812_5_) {
        final StructureBoundingBox var6 = new StructureBoundingBox(p_175812_2_, p_175812_3_ - 5, p_175812_4_, p_175812_2_, p_175812_3_ + 2, p_175812_4_);
        switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[p_175812_5_.ordinal()]) {
            case 1: {
                var6.maxX = p_175812_2_ + 2;
                var6.minZ = p_175812_4_ - 8;
                break;
            }
            case 2: {
                var6.maxX = p_175812_2_ + 2;
                var6.maxZ = p_175812_4_ + 8;
                break;
            }
            case 3: {
                var6.minX = p_175812_2_ - 8;
                var6.maxZ = p_175812_4_ + 2;
                break;
            }
            case 4: {
                var6.maxX = p_175812_2_ + 8;
                var6.maxZ = p_175812_4_ + 2;
                break;
            }
        }
        return (StructureComponent.findIntersecting(p_175812_0_, var6) != null) ? null : var6;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
    }
    
    @Override
    public void buildComponent(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
        final int var4 = this.getComponentType();
        if (this.coordBaseMode != null) {
            switch (StructureMineshaftPieces.SwitchEnumFacing.field_175894_a[this.coordBaseMode.ordinal()]) {
                case 1: {
                    StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var4);
                    break;
                }
                case 2: {
                    StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var4);
                    break;
                }
                case 3: {
                    StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, var4);
                    break;
                }
                case 4: {
                    StructureMineshaftPieces.access$000(p_74861_1_, p_74861_2_, p_74861_3_, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, var4);
                    break;
                }
            }
        }
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        this.func_175804_a(worldIn, p_74875_3_, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        this.func_175804_a(worldIn, p_74875_3_, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        for (int var4 = 0; var4 < 5; ++var4) {
            this.func_175804_a(worldIn, p_74875_3_, 0, 5 - var4 - ((var4 < 4) ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
        }
        return true;
    }
}
