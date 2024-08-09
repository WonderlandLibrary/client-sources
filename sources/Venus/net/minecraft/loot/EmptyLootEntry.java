/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;

public class EmptyLootEntry
extends StandaloneLootEntry {
    private EmptyLootEntry(int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        super(n, n2, iLootConditionArray, iLootFunctionArray);
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return LootEntryManager.EMPTY;
    }

    @Override
    public void func_216154_a(Consumer<ItemStack> consumer, LootContext lootContext) {
    }

    public static StandaloneLootEntry.Builder<?> func_216167_a() {
        return EmptyLootEntry.builder(EmptyLootEntry::new);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends StandaloneLootEntry.Serializer<EmptyLootEntry> {
        @Override
        public EmptyLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            return new EmptyLootEntry(n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        public StandaloneLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, n, n2, iLootConditionArray, iLootFunctionArray);
        }
    }
}

