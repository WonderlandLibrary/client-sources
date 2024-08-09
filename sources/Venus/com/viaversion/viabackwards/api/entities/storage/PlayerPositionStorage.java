/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;

public abstract class PlayerPositionStorage
implements StorableObject {
    private double x;
    private double y;
    private double z;

    protected PlayerPositionStorage() {
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(double d) {
        this.x = d;
    }

    public void setY(double d) {
        this.y = d;
    }

    public void setZ(double d) {
        this.z = d;
    }

    public void setCoordinates(PacketWrapper packetWrapper, boolean bl) throws Exception {
        this.setCoordinates(packetWrapper.get(Type.DOUBLE, 0), packetWrapper.get(Type.DOUBLE, 1), packetWrapper.get(Type.DOUBLE, 2), bl);
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

