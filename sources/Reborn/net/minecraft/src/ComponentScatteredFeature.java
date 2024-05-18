package net.minecraft.src;

import java.util.*;

abstract class ComponentScatteredFeature extends StructureComponent
{
    protected final int scatteredFeatureSizeX;
    protected final int scatteredFeatureSizeY;
    protected final int scatteredFeatureSizeZ;
    protected int field_74936_d;
    
    protected ComponentScatteredFeature(final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7) {
        super(0);
        this.field_74936_d = -1;
        this.scatteredFeatureSizeX = par5;
        this.scatteredFeatureSizeY = par6;
        this.scatteredFeatureSizeZ = par7;
        switch (this.coordBaseMode = par1Random.nextInt(4)) {
            case 0:
            case 2: {
                this.boundingBox = new StructureBoundingBox(par2, par3, par4, par2 + par5 - 1, par3 + par6 - 1, par4 + par7 - 1);
                break;
            }
            default: {
                this.boundingBox = new StructureBoundingBox(par2, par3, par4, par2 + par7 - 1, par3 + par6 - 1, par4 + par5 - 1);
                break;
            }
        }
    }
    
    protected boolean func_74935_a(final World par1World, final StructureBoundingBox par2StructureBoundingBox, final int par3) {
        if (this.field_74936_d >= 0) {
            return true;
        }
        int var4 = 0;
        int var5 = 0;
        for (int var6 = this.boundingBox.minZ; var6 <= this.boundingBox.maxZ; ++var6) {
            for (int var7 = this.boundingBox.minX; var7 <= this.boundingBox.maxX; ++var7) {
                if (par2StructureBoundingBox.isVecInside(var7, 64, var6)) {
                    var4 += Math.max(par1World.getTopSolidOrLiquidBlock(var7, var6), par1World.provider.getAverageGroundLevel());
                    ++var5;
                }
            }
        }
        if (var5 == 0) {
            return false;
        }
        this.field_74936_d = var4 / var5;
        this.boundingBox.offset(0, this.field_74936_d - this.boundingBox.minY + par3, 0);
        return true;
    }
}
