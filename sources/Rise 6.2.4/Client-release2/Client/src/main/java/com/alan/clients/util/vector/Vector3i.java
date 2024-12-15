package com.alan.clients.util.vector;

import lombok.Setter;
@Setter
public class Vector3i {

    public int x;
    public int y;
    public int z;

    public Vector3i(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i add(final int x, final int y, final int z) {
        return new Vector3i(this.x + x, this.y + y, this.z + z);
    }

    public Vector3i add(final Vector3i vector) {
        return add(vector.x, vector.y, vector.z);
    }

    public Vector3i subtract(final int x, final int y, final int z) {
        return add(-x, -y, -z);
    }

    public Vector3i subtract(final Vector3i vector) {
        return add(-vector.x, -vector.y, -vector.z);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Vector3d multiply(final double v) {
        return new Vector3d(x * v, y * v, z * v);
    }

    public double distance(Vector3i vector3d) {
        return Math.sqrt(Math.pow(vector3d.x - x, 2) + Math.pow(vector3d.y - y, 2) + Math.pow(vector3d.z - z, 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector3d)) return false;

        Vector3d vector = (Vector3d) obj;
        return (((double) x == Math.floor(vector.x)) && (double) y == Math.floor(vector.y) && (double) z == Math.floor(vector.z));
    }
}