/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import java.util.function.Consumer;
import net.minecraft.loot.ILootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.ParentedLootEntry;
import net.minecraft.loot.conditions.ILootCondition;

public class SequenceLootEntry
extends ParentedLootEntry {
    SequenceLootEntry(LootEntry[] lootEntryArray, ILootCondition[] iLootConditionArray) {
        super(lootEntryArray, iLootConditionArray);
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return LootEntryManager.SEQUENCE;
    }

    @Override
    protected ILootEntry combineChildren(ILootEntry[] iLootEntryArray) {
        switch (iLootEntryArray.length) {
            case 0: {
                return field_216140_b;
            }
            case 1: {
                return iLootEntryArray[0];
            }
            case 2: {
                return iLootEntryArray[0].sequence(iLootEntryArray[5]);
            }
        }
        return (arg_0, arg_1) -> SequenceLootEntry.lambda$combineChildren$0(iLootEntryArray, arg_0, arg_1);
    }

    private static boolean lambda$combineChildren$0(ILootEntry[] iLootEntryArray, LootContext lootContext, Consumer consumer) {
        for (ILootEntry iLootEntry : iLootEntryArray) {
            if (iLootEntry.expand(lootContext, consumer)) continue;
            return true;
        }
        return false;
    }
}

