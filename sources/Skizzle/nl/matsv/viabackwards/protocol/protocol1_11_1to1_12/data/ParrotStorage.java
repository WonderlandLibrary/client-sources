/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_11_1to1_12.data;

import nl.matsv.viabackwards.api.entities.storage.EntityStorage;

public class ParrotStorage
implements EntityStorage {
    private boolean tamed = true;
    private boolean sitting = true;

    public boolean isTamed() {
        return this.tamed;
    }

    public void setTamed(boolean tamed) {
        this.tamed = tamed;
    }

    public boolean isSitting() {
        return this.sitting;
    }

    public void setSitting(boolean sitting) {
        this.sitting = sitting;
    }
}

