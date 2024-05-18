// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

public class Vec3d
{
    public double x;
    public double y;
    public double z;
    
    public Vec3d() {
    }
    
    public Vec3d(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vec3d(final Vec3d v) {
        this.set(v);
    }
    
    public Vec3d(final Vec3f v) {
        this.set(v);
    }
    
    public void set(final Vec3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    
    public void set(final Vec3d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    
    public void set(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void mul(final double scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
    }
    
    public void sub(final Vec3f t1, final Vec3f t2) {
        this.x = t1.x - t2.x;
        this.y = t1.y - t2.y;
        this.z = t1.z - t2.z;
    }
    
    public void sub(final Vec3d t1, final Vec3d t2) {
        this.x = t1.x - t2.x;
        this.y = t1.y - t2.y;
        this.z = t1.z - t2.z;
    }
    
    public void sub(final Vec3d t1) {
        this.x -= t1.x;
        this.y -= t1.y;
        this.z -= t1.z;
    }
    
    public void add(final Vec3d t1, final Vec3d t2) {
        this.x = t1.x + t2.x;
        this.y = t1.y + t2.y;
        this.z = t1.z + t2.z;
    }
    
    public void add(final Vec3d t1) {
        this.x += t1.x;
        this.y += t1.y;
        this.z += t1.z;
    }
    
    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public void normalize() {
        final double norm = 1.0 / this.length();
        this.x *= norm;
        this.y *= norm;
        this.z *= norm;
    }
    
    public void cross(final Vec3d v1, final Vec3d v2) {
        final double tmpX = v1.y * v2.z - v1.z * v2.y;
        final double tmpY = v2.x * v1.z - v2.z * v1.x;
        this.z = v1.x * v2.y - v1.y * v2.x;
        this.x = tmpX;
        this.y = tmpY;
    }
    
    public double dot(final Vec3d v1) {
        return this.x * v1.x + this.y * v1.y + this.z * v1.z;
    }
    
    @Override
    public int hashCode() {
        long bits = 7L;
        bits = 31L * bits + Double.doubleToLongBits(this.x);
        bits = 31L * bits + Double.doubleToLongBits(this.y);
        bits = 31L * bits + Double.doubleToLongBits(this.z);
        return (int)(bits ^ bits >> 32);
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vec3d) {
            final Vec3d v = (Vec3d)obj;
            return this.x == v.x && this.y == v.y && this.z == v.z;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Vec3d[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
}
