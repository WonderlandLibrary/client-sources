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

public class GroupLootEntry
extends ParentedLootEntry {
    GroupLootEntry(LootEntry[] lootEntryArray, ILootCondition[] iLootConditionArray) {
        super(lootEntryArray, iLootConditionArray);
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return LootEntryManager.GROUP;
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
                ILootEntry iLootEntry = iLootEntryArray[0];
                ILootEntry iLootEntry2 = iLootEntryArray[5];
                return (arg_0, arg_1) -> GroupLootEntry.lambda$combineChildren$0(iLootEntry, iLootEntry2, arg_0, arg_1);
            }
        }
        return (arg_0, arg_1) -> GroupLootEntry.lambda$combineChildren$1(iLootEntryArray, arg_0, arg_1);
    }

    private static boolean lambda$combineChildren$1(ILootEntry[] iLootEntryArray, LootContext lootContext, Consumer consumer) {
        for (ILootEntry iLootEntry : iLootEntryArray) {
            iLootEntry.expand(lootContext, consumer);
        }
        return false;
    }

    private static boolean lambda$combineChildren$0(ILootEntry iLootEntry, ILootEntry iLootEntry2, LootContext lootContext, Consumer consumer) {
        iLootEntry.expand(lootContext, consumer);
        iLootEntry2.expand(lootContext, consumer);
        return false;
    }
}

