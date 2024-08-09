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
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class DynamicLootEntry
extends StandaloneLootEntry {
    private final ResourceLocation name;

    private DynamicLootEntry(ResourceLocation resourceLocation, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        super(n, n2, iLootConditionArray, iLootFunctionArray);
        this.name = resourceLocation;
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return LootEntryManager.DYNAMIC;
    }

    @Override
    public void func_216154_a(Consumer<ItemStack> consumer, LootContext lootContext) {
        lootContext.generateDynamicDrop(this.name, consumer);
    }

    public static StandaloneLootEntry.Builder<?> func_216162_a(ResourceLocation resourceLocation) {
        return DynamicLootEntry.builder((arg_0, arg_1, arg_2, arg_3) -> DynamicLootEntry.lambda$func_216162_a$0(resourceLocation, arg_0, arg_1, arg_2, arg_3));
    }

    private static StandaloneLootEntry lambda$func_216162_a$0(ResourceLocation resourceLocation, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        return new DynamicLootEntry(resourceLocation, n, n2, iLootConditionArray, iLootFunctionArray);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends StandaloneLootEntry.Serializer<DynamicLootEntry> {
        @Override
        public void doSerialize(JsonObject jsonObject, DynamicLootEntry dynamicLootEntry, JsonSerializationContext jsonSerializationContext) {
            super.doSerialize(jsonObject, dynamicLootEntry, jsonSerializationContext);
            jsonObject.addProperty("name", dynamicLootEntry.name.toString());
        }

        @Override
        protected DynamicLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "name"));
            return new DynamicLootEntry(resourceLocation, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        protected StandaloneLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, StandaloneLootEntry standaloneLootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (DynamicLootEntry)standaloneLootEntry, jsonSerializationContext);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, LootEntry lootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (DynamicLootEntry)lootEntry, jsonSerializationContext);
        }
    }
}

