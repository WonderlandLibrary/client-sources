/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootType;

public class LootPoolEntryType
extends LootType<LootEntry> {
    public LootPoolEntryType(ILootSerializer<? extends LootEntry> iLootSerializer) {
        super(iLootSerializer);
    }
}

