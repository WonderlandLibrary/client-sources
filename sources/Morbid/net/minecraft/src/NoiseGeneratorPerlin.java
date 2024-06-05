package net.minecraft.src;

import java.util.*;

public class NoiseGeneratorPerlin extends NoiseGenerator
{
    private int[] permutations;
    public double xCoord;
    public double yCoord;
    public double zCoord;
    
    public NoiseGeneratorPerlin() {
        this(new Random());
    }
    
    public NoiseGeneratorPerlin(final Random par1Random) {
        this.permutations = new int[512];
        this.xCoord = par1Random.nextDouble() * 256.0;
        this.yCoord = par1Random.nextDouble() * 256.0;
        this.zCoord = par1Random.nextDouble() * 256.0;
        for (int var2 = 0; var2 < 256; this.permutations[var2] = var2++) {}
        for (int var2 = 0; var2 < 256; ++var2) {
            final int var3 = par1Random.nextInt(256 - var2) + var2;
            final int var4 = this.permutations[var2];
            this.permutations[var2] = this.permutations[var3];
            this.permutations[var3] = var4;
            this.permutations[var2 + 256] = this.permutations[var2];
        }
    }
    
    public final double lerp(final double par1, final double par3, final double par5) {
        return par3 + par1 * (par5 - par3);
    }
    
    public final double func_76309_a(final int par1, final double par2, final double par4) {
        final int var6 = par1 & 0xF;
        final double var7 = (1 - ((var6 & 0x8) >> 3)) * par2;
        final double var8 = (var6 < 4) ? 0.0 : ((var6 != 12 && var6 != 14) ? par4 : par2);
        return (((var6 & 0x1) == 0x0) ? var7 : (-var7)) + (((var6 & 0x2) == 0x0) ? var8 : (-var8));
    }
    
    public final double grad(final int par1, final double par2, final double par4, final double par6) {
        final int var8 = par1 & 0xF;
        final double var9 = (var8 < 8) ? par2 : par4;
        final double var10 = (var8 < 4) ? par4 : ((var8 != 12 && var8 != 14) ? par6 : par2);
        return (((var8 & 0x1) == 0x0) ? var9 : (-var9)) + (((var8 & 0x2) == 0x0) ? var10 : (-var10));
    }
    
    public void populateNoiseArray(final double[] par1ArrayOfDouble, final double par2, final double par4, final double par6, final int par8, final int par9, final int par10, final double par11, final double par13, final double par15, final double par17) {
        if (par9 == 1) {
            final boolean var64 = false;
            final boolean var65 = false;
            final boolean var66 = false;
            final boolean var67 = false;
            double var68 = 0.0;
            double var69 = 0.0;
            int var70 = 0;
            final double var71 = 1.0 / par17;
            for (int var72 = 0; var72 < par8; ++var72) {
                double var73 = par2 + var72 * par11 + this.xCoord;
                int var74 = (int)var73;
                if (var73 < var74) {
                    --var74;
                }
                final int var75 = var74 & 0xFF;
                var73 -= var74;
                final double var76 = var73 * var73 * var73 * (var73 * (var73 * 6.0 - 15.0) + 10.0);
                for (int var77 = 0; var77 < par10; ++var77) {
                    double var78 = par6 + var77 * par15 + this.zCoord;
                    int var79 = (int)var78;
                    if (var78 < var79) {
                        --var79;
                    }
                    final int var80 = var79 & 0xFF;
                    var78 -= var79;
                    final double var81 = var78 * var78 * var78 * (var78 * (var78 * 6.0 - 15.0) + 10.0);
                    final int var82 = this.permutations[var75] + 0;
                    final int var83 = this.permutations[var82] + var80;
                    final int var84 = this.permutations[var75 + 1] + 0;
                    final int var85 = this.permutations[var84] + var80;
                    var68 = this.lerp(var76, this.func_76309_a(this.permutations[var83], var73, var78), this.grad(this.permutations[var85], var73 - 1.0, 0.0, var78));
                    var69 = this.lerp(var76, this.grad(this.permutations[var83 + 1], var73, 0.0, var78 - 1.0), this.grad(this.permutations[var85 + 1], var73 - 1.0, 0.0, var78 - 1.0));
                    final double var86 = this.lerp(var81, var68, var69);
                    final int n;
                    final int var87 = n = var70++;
                    par1ArrayOfDouble[n] += var86 * var71;
                }
            }
        }
        else {
            int var82 = 0;
            final double var88 = 1.0 / par17;
            int var85 = -1;
            final boolean var89 = false;
            final boolean var90 = false;
            final boolean var91 = false;
            final boolean var92 = false;
            final boolean var93 = false;
            final boolean var94 = false;
            double var95 = 0.0;
            double var73 = 0.0;
            double var96 = 0.0;
            double var76 = 0.0;
            for (int var77 = 0; var77 < par8; ++var77) {
                double var78 = par2 + var77 * par11 + this.xCoord;
                int var79 = (int)var78;
                if (var78 < var79) {
                    --var79;
                }
                final int var80 = var79 & 0xFF;
                var78 -= var79;
                final double var81 = var78 * var78 * var78 * (var78 * (var78 * 6.0 - 15.0) + 10.0);
                for (int var97 = 0; var97 < par10; ++var97) {
                    double var98 = par6 + var97 * par15 + this.zCoord;
                    int var99 = (int)var98;
                    if (var98 < var99) {
                        --var99;
                    }
                    final int var100 = var99 & 0xFF;
                    var98 -= var99;
                    final double var101 = var98 * var98 * var98 * (var98 * (var98 * 6.0 - 15.0) + 10.0);
                    for (int var102 = 0; var102 < par9; ++var102) {
                        double var103 = par4 + var102 * par13 + this.yCoord;
                        int var104 = (int)var103;
                        if (var103 < var104) {
                            --var104;
                        }
                        final int var105 = var104 & 0xFF;
                        var103 -= var104;
                        final double var106 = var103 * var103 * var103 * (var103 * (var103 * 6.0 - 15.0) + 10.0);
                        if (var102 == 0 || var105 != var85) {
                            var85 = var105;
                            final int var107 = this.permutations[var80] + var105;
                            final int var108 = this.permutations[var107] + var100;
                            final int var109 = this.permutations[var107 + 1] + var100;
                            final int var110 = this.permutations[var80 + 1] + var105;
                            final int var70 = this.permutations[var110] + var100;
                            final int var111 = this.permutations[var110 + 1] + var100;
                            var95 = this.lerp(var81, this.grad(this.permutations[var108], var78, var103, var98), this.grad(this.permutations[var70], var78 - 1.0, var103, var98));
                            var73 = this.lerp(var81, this.grad(this.permutations[var109], var78, var103 - 1.0, var98), this.grad(this.permutations[var111], var78 - 1.0, var103 - 1.0, var98));
                            var96 = this.lerp(var81, this.grad(this.permutations[var108 + 1], var78, var103, var98 - 1.0), this.grad(this.permutations[var70 + 1], var78 - 1.0, var103, var98 - 1.0));
                            var76 = this.lerp(var81, this.grad(this.permutations[var109 + 1], var78, var103 - 1.0, var98 - 1.0), this.grad(this.permutations[var111 + 1], var78 - 1.0, var103 - 1.0, var98 - 1.0));
                        }
                        final double var112 = this.lerp(var106, var95, var73);
                        final double var113 = this.lerp(var106, var96, var76);
                        final double var114 = this.lerp(var101, var112, var113);
                        final int n2;
                        final int var87 = n2 = var82++;
                        par1ArrayOfDouble[n2] += var114 * var88;
                    }
                }
            }
        }
    }
}
