/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.advancements;

public enum AdvancementState {
    OBTAINED(0),
    UNOBTAINED(1);

    private final int id;

    private AdvancementState(int n2) {
        this.id = n2;
    }

    public int getId() {
        return this.id;
    }
}

