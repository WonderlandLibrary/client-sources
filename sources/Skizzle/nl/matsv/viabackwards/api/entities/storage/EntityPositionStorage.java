/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.entities.storage;

import nl.matsv.viabackwards.api.entities.storage.EntityStorage;

public abstract class EntityPositionStorage
implements EntityStorage {
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

    public void setCoordinates(double x, double y, double z, boolean relative) {
        if (relative) {
            this.x += x;
            this.y += y;
            this.z += z;
        } else {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

