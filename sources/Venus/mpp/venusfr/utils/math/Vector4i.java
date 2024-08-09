/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.math;

public class Vector4i {
    public int x;
    public int y;
    public int z;
    public int w;

    public Vector4i(int n, int n2, int n3, int n4) {
        this.x = n;
        this.y = n2;
        this.z = n3;
        this.w = n4;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public int getW() {
        return this.w;
    }

    public void setX(int n) {
        this.x = n;
    }

    public void setY(int n) {
        this.y = n;
    }

    public void setZ(int n) {
        this.z = n;
    }

    public void setW(int n) {
        this.w = n;
    }
}

