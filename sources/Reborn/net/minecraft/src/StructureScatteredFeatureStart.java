package net.minecraft.src;

import java.util.*;

public class StructureScatteredFeatureStart extends StructureStart
{
    public StructureScatteredFeatureStart(final World par1World, final Random par2Random, final int par3, final int par4) {
        final BiomeGenBase var5 = par1World.getBiomeGenForCoords(par3 * 16 + 8, par4 * 16 + 8);
        if (var5 != BiomeGenBase.jungle && var5 != BiomeGenBase.jungleHills) {
            if (var5 == BiomeGenBase.swampland) {
                final ComponentScatteredFeatureSwampHut var6 = new ComponentScatteredFeatureSwampHut(par2Random, par3 * 16, par4 * 16);
                this.components.add(var6);
            }
            else {
                final ComponentScatteredFeatureDesertPyramid var7 = new ComponentScatteredFeatureDesertPyramid(par2Random, par3 * 16, par4 * 16);
                this.components.add(var7);
            }
        }
        else {
            final ComponentScatteredFeatureJunglePyramid var8 = new ComponentScatteredFeatureJunglePyramid(par2Random, par3 * 16, par4 * 16);
            this.components.add(var8);
        }
        this.updateBoundingBox();
    }
}
