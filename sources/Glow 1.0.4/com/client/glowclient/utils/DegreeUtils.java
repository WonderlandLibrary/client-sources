package com.client.glowclient.utils;

import net.minecraft.util.math.*;

public final class DegreeUtils
{
    public static double M(final double n) {
        return MathHelper.wrapDegrees(n);
    }
    
    public DegreeUtils() {
        super();
    }
    
    public static double M(final double n, final double n2, final double n3) {
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }
    
    public static float M(final float n, final float n2, final float n3) {
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }
    
    public static int M(final int n, final int n2, final int n3) {
        if (n < n2) {
            return n2;
        }
        if (n > n3) {
            return n3;
        }
        return n;
    }
    
    public static float A(final float n) {
        return MathHelper.wrapDegrees(n);
    }
    
    public static float D(final float n) {
        return MathHelper.sin(n);
    }
    
    public static float M(final float n) {
        return MathHelper.cos(n);
    }
}
