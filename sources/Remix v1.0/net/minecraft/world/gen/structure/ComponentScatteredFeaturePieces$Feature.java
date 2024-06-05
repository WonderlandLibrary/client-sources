package net.minecraft.world.gen.structure;

import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

abstract static class Feature extends StructureComponent
{
    protected int scatteredFeatureSizeX;
    protected int scatteredFeatureSizeY;
    protected int scatteredFeatureSizeZ;
    protected int field_74936_d;
    
    public Feature() {
        this.field_74936_d = -1;
    }
    
    protected Feature(final Random p_i2065_1_, final int p_i2065_2_, final int p_i2065_3_, final int p_i2065_4_, final int p_i2065_5_, final int p_i2065_6_, final int p_i2065_7_) {
        super(0);
        this.field_74936_d = -1;
        this.scatteredFeatureSizeX = p_i2065_5_;
        this.scatteredFeatureSizeY = p_i2065_6_;
        this.scatteredFeatureSizeZ = p_i2065_7_;
        this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(p_i2065_1_);
        switch (ComponentScatteredFeaturePieces.SwitchEnumFacing.field_175956_a[this.coordBaseMode.ordinal()]) {
            case 1:
            case 2: {
                this.boundingBox = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_, p_i2065_2_ + p_i2065_5_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_7_ - 1);
                break;
            }
            default: {
                this.boundingBox = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_, p_i2065_2_ + p_i2065_7_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_5_ - 1);
                break;
            }
        }
    }
    
    @Override
    protected void writeStructureToNBT(final NBTTagCompound p_143012_1_) {
        p_143012_1_.setInteger("Width", this.scatteredFeatureSizeX);
        p_143012_1_.setInteger("Height", this.scatteredFeatureSizeY);
        p_143012_1_.setInteger("Depth", this.scatteredFeatureSizeZ);
        p_143012_1_.setInteger("HPos", this.field_74936_d);
    }
    
    @Override
    protected void readStructureFromNBT(final NBTTagCompound p_143011_1_) {
        this.scatteredFeatureSizeX = p_143011_1_.getInteger("Width");
        this.scatteredFeatureSizeY = p_143011_1_.getInteger("Height");
        this.scatteredFeatureSizeZ = p_143011_1_.getInteger("Depth");
        this.field_74936_d = p_143011_1_.getInteger("HPos");
    }
    
    protected boolean func_74935_a(final World worldIn, final StructureBoundingBox p_74935_2_, final int p_74935_3_) {
        if (this.field_74936_d >= 0) {
            return true;
        }
        int var4 = 0;
        int var5 = 0;
        for (int var6 = this.boundingBox.minZ; var6 <= this.boundingBox.maxZ; ++var6) {
            for (int var7 = this.boundingBox.minX; var7 <= this.boundingBox.maxX; ++var7) {
                final BlockPos var8 = new BlockPos(var7, 64, var6);
                if (p_74935_2_.func_175898_b(var8)) {
                    var4 += Math.max(worldIn.func_175672_r(var8).getY(), worldIn.provider.getAverageGroundLevel());
                    ++var5;
                }
            }
        }
        if (var5 == 0) {
            return false;
        }
        this.field_74936_d = var4 / var5;
        this.boundingBox.offset(0, this.field_74936_d - this.boundingBox.minY + p_74935_3_, 0);
        return true;
    }
}
