/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.entities.storage;

public abstract class EntityPositionStorage {
    private double x;
    private double y;
    private double z;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setCoordinates(double d, double d2, double d3, boolean bl) {
        if (bl) {
            this.x += d;
            this.y += d2;
            this.z += d3;
        } else {
            this.x = d;
            this.y = d2;
            this.z = d3;
        }
    }
}

