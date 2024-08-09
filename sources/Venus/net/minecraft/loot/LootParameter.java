/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import net.minecraft.util.ResourceLocation;

public class LootParameter<T> {
    private final ResourceLocation id;

    public LootParameter(ResourceLocation resourceLocation) {
        this.id = resourceLocation;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public String toString() {
        return "<parameter " + this.id + ">";
    }
}

