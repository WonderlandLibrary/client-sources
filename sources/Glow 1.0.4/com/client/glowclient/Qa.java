package com.client.glowclient;

import net.minecraft.util.math.*;

public class qA
{
    public static final double d = 0.7853981633974483;
    public static final double L = 1.0E-9;
    public static final double A = 1.5707963267948966;
    public static final double B = 6.283185307179586;
    public static final long b = 1000000000L;
    
    public static qb D(final Vec3d vec3d) {
        double n;
        double n2 = 0.0;
        if (vec3d.x == 0.0 && vec3d.z == 0.0) {
            n = 0.0;
            n2 = 1.5707963267948966;
        }
        else {
            n = Math.atan2(vec3d.z, vec3d.x) - 1.5707963267948966;
            final double n3 = -Math.atan2(vec3d.y, Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z));
        }
        return qb.M(n2, n);
    }
    
    public static double A(double n) {
        double n2 = n;
        while (n2 > 3.141592653589793) {
            n2 = (n -= 6.283185307179586);
        }
        double n3 = n;
        while (n3 < -3.141592653589793) {
            n3 = (n += 6.283185307179586);
        }
        return n;
    }
    
    public static double M(final double n, final long n2) {
        return (double)(Math.round(n * n2) / n2);
    }
    
    public qA() {
        super();
    }
    
    public static boolean M(final double n, final double n2) {
        return M(n, n2, 1.0E-9);
    }
    
    public static qb M(final Vec3d vec3d) {
        return D(vec3d).D();
    }
    
    public static double D(double n) {
        double n2 = n;
        while (n2 <= -180.0) {
            n2 = (n += 360.0);
        }
        double n3 = n;
        while (n3 > 180.0) {
            n3 = (n -= 360.0);
        }
        return n;
    }
    
    public static boolean M(final double n, final double n2, final double n3) {
        return Double.compare(n, n2) == 0 || Math.abs(n - n2) < n3;
    }
    
    public static double M(final double n) {
        return M(n, 1000000000L);
    }
}
