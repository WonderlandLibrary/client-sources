package net.minecraft.util;

public class Vector3d
{
    public double field_181059_a;
    public double field_181060_b;
    public double field_181061_c;

    public Vector3d()
    {
        this.field_181059_a = this.field_181060_b = this.field_181061_c = 0.0D;
    }

    public Vector3d(double x, double y, double z) {
        this.field_181059_a = x;
        this.field_181060_b = y;
        this.field_181061_c = z;
    }

    public double getX() {
        return field_181059_a;
    }

    public double getY() {
        return field_181060_b;
    }

    public double getZ() {
        return field_181061_c;
    }
}
