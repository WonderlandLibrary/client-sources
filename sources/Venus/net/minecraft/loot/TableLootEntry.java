/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class TableLootEntry
extends StandaloneLootEntry {
    private final ResourceLocation table;

    private TableLootEntry(ResourceLocation resourceLocation, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        super(n, n2, iLootConditionArray, iLootFunctionArray);
        this.table = resourceLocation;
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return LootEntryManager.LOOT_TABLE;
    }

    @Override
    public void func_216154_a(Consumer<ItemStack> consumer, LootContext lootContext) {
        LootTable lootTable = lootContext.getLootTable(this.table);
        lootTable.recursiveGenerate(lootContext, consumer);
    }

    @Override
    public void func_225579_a_(ValidationTracker validationTracker) {
        if (validationTracker.func_227532_a_(this.table)) {
            validationTracker.addProblem("Table " + this.table + " is recursively called");
        } else {
            super.func_225579_a_(validationTracker);
            LootTable lootTable = validationTracker.func_227539_c_(this.table);
            if (lootTable == null) {
                validationTracker.addProblem("Unknown loot table called " + this.table);
            } else {
                lootTable.validate(validationTracker.func_227531_a_("->{" + this.table + "}", this.table));
            }
        }
    }

    public static StandaloneLootEntry.Builder<?> builder(ResourceLocation resourceLocation) {
        return TableLootEntry.builder((arg_0, arg_1, arg_2, arg_3) -> TableLootEntry.lambda$builder$0(resourceLocation, arg_0, arg_1, arg_2, arg_3));
    }

    private static StandaloneLootEntry lambda$builder$0(ResourceLocation resourceLocation, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        return new TableLootEntry(resourceLocation, n, n2, iLootConditionArray, iLootFunctionArray);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends StandaloneLootEntry.Serializer<TableLootEntry> {
        @Override
        public void doSerialize(JsonObject jsonObject, TableLootEntry tableLootEntry, JsonSerializationContext jsonSerializationContext) {
            super.doSerialize(jsonObject, tableLootEntry, jsonSerializationContext);
            jsonObject.addProperty("name", tableLootEntry.table.toString());
        }

        @Override
        protected TableLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "name"));
            return new TableLootEntry(resourceLocation, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        protected StandaloneLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, StandaloneLootEntry standaloneLootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (TableLootEntry)standaloneLootEntry, jsonSerializationContext);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, LootEntry lootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (TableLootEntry)lootEntry, jsonSerializationContext);
        }
    }
}

