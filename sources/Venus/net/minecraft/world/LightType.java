/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

public enum LightType {
    SKY(15),
    BLOCK(0);

    public final int defaultLightValue;

    private LightType(int n2) {
        this.defaultLightValue = n2;
    }
}

