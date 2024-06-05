package net.minecraft.src;

import java.util.*;

public class MathHelper
{
    private static float[] SIN_TABLE;
    
    static {
        MathHelper.SIN_TABLE = new float[65536];
        for (int var0 = 0; var0 < 65536; ++var0) {
            MathHelper.SIN_TABLE[var0] = (float)Math.sin(var0 * 3.141592653589793 * 2.0 / 65536.0);
        }
    }
    
    public static final float sin(final float par0) {
        return MathHelper.SIN_TABLE[(int)(par0 * 10430.378f) & 0xFFFF];
    }
    
    public static final float cos(final float par0) {
        return MathHelper.SIN_TABLE[(int)(par0 * 10430.378f + 16384.0f) & 0xFFFF];
    }
    
    public static final float sqrt_float(final float par0) {
        return (float)Math.sqrt(par0);
    }
    
    public static final float sqrt_double(final double par0) {
        return (float)Math.sqrt(par0);
    }
    
    public static int floor_float(final float par0) {
        final int var1 = (int)par0;
        return (par0 < var1) ? (var1 - 1) : var1;
    }
    
    public static int truncateDoubleToInt(final double par0) {
        return (int)(par0 + 1024.0) - 1024;
    }
    
    public static int floor_double(final double par0) {
        final int var2 = (int)par0;
        return (par0 < var2) ? (var2 - 1) : var2;
    }
    
    public static long floor_double_long(final double par0) {
        final long var2 = (long)par0;
        return (par0 < var2) ? (var2 - 1L) : var2;
    }
    
    public static float abs(final float par0) {
        return (par0 >= 0.0f) ? par0 : (-par0);
    }
    
    public static int abs_int(final int par0) {
        return (par0 >= 0) ? par0 : (-par0);
    }
    
    public static int ceiling_float_int(final float par0) {
        final int var1 = (int)par0;
        return (par0 > var1) ? (var1 + 1) : var1;
    }
    
    public static int ceiling_double_int(final double par0) {
        final int var2 = (int)par0;
        return (par0 > var2) ? (var2 + 1) : var2;
    }
    
    public static int clamp_int(final int par0, final int par1, final int par2) {
        return (par0 < par1) ? par1 : ((par0 > par2) ? par2 : par0);
    }
    
    public static float clamp_float(final float par0, final float par1, final float par2) {
        return (par0 < par1) ? par1 : ((par0 > par2) ? par2 : par0);
    }
    
    public static double abs_max(double par0, double par2) {
        if (par0 < 0.0) {
            par0 = -par0;
        }
        if (par2 < 0.0) {
            par2 = -par2;
        }
        return (par0 > par2) ? par0 : par2;
    }
    
    public static int bucketInt(final int par0, final int par1) {
        return (par0 < 0) ? (-((-par0 - 1) / par1) - 1) : (par0 / par1);
    }
    
    public static boolean stringNullOrLengthZero(final String par0Str) {
        return par0Str == null || par0Str.length() == 0;
    }
    
    public static int getRandomIntegerInRange(final Random par0Random, final int par1, final int par2) {
        return (par1 >= par2) ? par1 : (par0Random.nextInt(par2 - par1 + 1) + par1);
    }
    
    public static double getRandomDoubleInRange(final Random par0Random, final double par1, final double par3) {
        return (par1 >= par3) ? par1 : (par0Random.nextDouble() * (par3 - par1) + par1);
    }
    
    public static double average(final long[] par0ArrayOfLong) {
        long var1 = 0L;
        for (final long var4 : par0ArrayOfLong) {
            var1 += var4;
        }
        return var1 / par0ArrayOfLong.length;
    }
    
    public static float wrapAngleTo180_float(float par0) {
        par0 %= 360.0f;
        if (par0 >= 180.0f) {
            par0 -= 360.0f;
        }
        if (par0 < -180.0f) {
            par0 += 360.0f;
        }
        return par0;
    }
    
    public static double wrapAngleTo180_double(double par0) {
        par0 %= 360.0;
        if (par0 >= 180.0) {
            par0 -= 360.0;
        }
        if (par0 < -180.0) {
            par0 += 360.0;
        }
        return par0;
    }
    
    public static int parseIntWithDefault(final String par0Str, final int par1) {
        int var2 = par1;
        try {
            var2 = Integer.parseInt(par0Str);
        }
        catch (Throwable t) {}
        return var2;
    }
    
    public static int parseIntWithDefaultAndMax(final String par0Str, final int par1, final int par2) {
        int var3 = par1;
        try {
            var3 = Integer.parseInt(par0Str);
        }
        catch (Throwable t) {}
        if (var3 < par2) {
            var3 = par2;
        }
        return var3;
    }
    
    public static double parseDoubleWithDefault(final String par0Str, final double par1) {
        double var3 = par1;
        try {
            var3 = Double.parseDouble(par0Str);
        }
        catch (Throwable t) {}
        return var3;
    }
    
    public static double func_82713_a(final String par0Str, final double par1, final double par3) {
        double var5 = par1;
        try {
            var5 = Double.parseDouble(par0Str);
        }
        catch (Throwable t) {}
        if (var5 < par3) {
            var5 = par3;
        }
        return var5;
    }
}
