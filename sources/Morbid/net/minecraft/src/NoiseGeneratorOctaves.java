package net.minecraft.src;

import java.util.*;

public class NoiseGeneratorOctaves extends NoiseGenerator
{
    private NoiseGeneratorPerlin[] generatorCollection;
    private int octaves;
    
    public NoiseGeneratorOctaves(final Random par1Random, final int par2) {
        this.octaves = par2;
        this.generatorCollection = new NoiseGeneratorPerlin[par2];
        for (int var3 = 0; var3 < par2; ++var3) {
            this.generatorCollection[var3] = new NoiseGeneratorPerlin(par1Random);
        }
    }
    
    public double[] generateNoiseOctaves(double[] par1ArrayOfDouble, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7, final double par8, final double par10, final double par12) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }
        else {
            for (int var14 = 0; var14 < par1ArrayOfDouble.length; ++var14) {
                par1ArrayOfDouble[var14] = 0.0;
            }
        }
        double var15 = 1.0;
        for (int var16 = 0; var16 < this.octaves; ++var16) {
            double var17 = par2 * var15 * par8;
            final double var18 = par3 * var15 * par10;
            double var19 = par4 * var15 * par12;
            long var20 = MathHelper.floor_double_long(var17);
            long var21 = MathHelper.floor_double_long(var19);
            var17 -= var20;
            var19 -= var21;
            var20 %= 16777216L;
            var21 %= 16777216L;
            var17 += var20;
            var19 += var21;
            this.generatorCollection[var16].populateNoiseArray(par1ArrayOfDouble, var17, var18, var19, par5, par6, par7, par8 * var15, par10 * var15, par12 * var15, var15);
            var15 /= 2.0;
        }
        return par1ArrayOfDouble;
    }
    
    public double[] generateNoiseOctaves(final double[] par1ArrayOfDouble, final int par2, final int par3, final int par4, final int par5, final double par6, final double par8, final double par10) {
        return this.generateNoiseOctaves(par1ArrayOfDouble, par2, 10, par3, par4, 1, par5, par6, 1.0, par8);
    }
}
