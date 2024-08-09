/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import net.minecraft.loot.ILootSerializer;

public class LootType<T> {
    private final ILootSerializer<? extends T> serializer;

    public LootType(ILootSerializer<? extends T> iLootSerializer) {
        this.serializer = iLootSerializer;
    }

    public ILootSerializer<? extends T> getSerializer() {
        return this.serializer;
    }
}

