package net.minecraft.world.gen.structure;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;

public static class Corridor extends Stronghold
{
    private int field_74993_a;
    
    public Corridor() {
    }
    
    public Corridor(final int p_i45581_1_, final Random p_i45581_2_, final StructureBoundingBox p_i45581_3_, final EnumFacing p_i45581_4_) {
        super(p_i45581_1_);
        this.coordBaseMode = p_i45581_4_;
        this.boundingBox = p_i45581_3_;
        this.field_74993_a = ((p_i45581_4_ != EnumFacing.NORTH && p_i45581_4_ != EnumFacing.SOUTH) ? p_i45581_3_.getXSize() : p_i45581_3_.getZSize());
    }
    
    public static StructureBoundingBox func_175869_a(final List p_175869_0_, final Random p_175869_1_, final int p_175869_2_, final int p_175869_3_, final int p_175869_4_, final EnumFacing p_175869_5_) {
        final boolean var6 = true;
        StructureBoundingBox var7 = StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
        final StructureComponent var8 = StructureComponent.findIntersecting(p_175869_0_, var7);
        if (var8 == null) {
            return null;
        }
        if (var8.getBoundingBox().minY == var7.minY) {
            for (int var9 = 3; var9 >= 1; --var9) {
                var7 = StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, var9 - 1, p_175869_5_);
                if (!var8.getBoundingBox().intersectsWith(var7)) {
                    return StructureBoundingBox.func_175897_a(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, var9, p_175869_5_);
                }
            }
        }
        return null;
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        super.writeStructureToNBT(p_143012_1_);
        p_143012_1_.setInteger("Steps", this.field_74993_a);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        super.readStructureFromNBT(p_143011_1_);
        this.field_74993_a = p_143011_1_.getInteger("Steps");
    }
    
    @Override
    public boolean addComponentParts(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
        if (this.isLiquidInStructureBoundingBox(worldIn, p_74875_3_)) {
            return false;
        }
        for (int var4 = 0; var4 < this.field_74993_a; ++var4) {
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 0, 0, var4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 0, var4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 0, var4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 0, var4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 4, 0, var4, p_74875_3_);
            for (int var5 = 1; var5 <= 3; ++var5) {
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 0, var5, var4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 1, var5, var4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 2, var5, var4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.air.getDefaultState(), 3, var5, var4, p_74875_3_);
                this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 4, var5, var4, p_74875_3_);
            }
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 0, 4, var4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 1, 4, var4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 2, 4, var4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 3, 4, var4, p_74875_3_);
            this.func_175811_a(worldIn, Blocks.stonebrick.getDefaultState(), 4, 4, var4, p_74875_3_);
        }
        return true;
    }
}
