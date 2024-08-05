package net.minecraft.util;

public class Vector3d {
    public double x;
    public double y;
    public double z;

    public Vector3d() {
        this.x = this.y = this.z = 0.0D;
    }
    public Vector3d(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
