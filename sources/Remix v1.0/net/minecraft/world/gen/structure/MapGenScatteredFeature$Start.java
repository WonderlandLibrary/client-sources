package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;

public static class Start extends StructureStart
{
    public Start() {
    }
    
    public Start(final World worldIn, final Random p_i2060_2_, final int p_i2060_3_, final int p_i2060_4_) {
        super(p_i2060_3_, p_i2060_4_);
        final BiomeGenBase var5 = worldIn.getBiomeGenForCoords(new BlockPos(p_i2060_3_ * 16 + 8, 0, p_i2060_4_ * 16 + 8));
        if (var5 != BiomeGenBase.jungle && var5 != BiomeGenBase.jungleHills) {
            if (var5 == BiomeGenBase.swampland) {
                final ComponentScatteredFeaturePieces.SwampHut var6 = new ComponentScatteredFeaturePieces.SwampHut(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
                this.components.add(var6);
            }
            else if (var5 == BiomeGenBase.desert || var5 == BiomeGenBase.desertHills) {
                final ComponentScatteredFeaturePieces.DesertPyramid var7 = new ComponentScatteredFeaturePieces.DesertPyramid(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
                this.components.add(var7);
            }
        }
        else {
            final ComponentScatteredFeaturePieces.JunglePyramid var8 = new ComponentScatteredFeaturePieces.JunglePyramid(p_i2060_2_, p_i2060_3_ * 16, p_i2060_4_ * 16);
            this.components.add(var8);
        }
        this.updateBoundingBox();
    }
}
