/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

public class PlayerVehicleTracker
extends StoredObject {
    private int vehicleId = -1;

    public PlayerVehicleTracker(UserConnection user) {
        super(user);
    }

    public int getVehicleId() {
        return this.vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}

