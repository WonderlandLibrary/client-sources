/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data;

public class ParrotStorage {
    private boolean tamed = true;
    private boolean sitting = true;

    public boolean isTamed() {
        return this.tamed;
    }

    public void setTamed(boolean bl) {
        this.tamed = bl;
    }

    public boolean isSitting() {
        return this.sitting;
    }

    public void setSitting(boolean bl) {
        this.sitting = bl;
    }
}

