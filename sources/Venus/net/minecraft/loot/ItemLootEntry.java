/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemLootEntry
extends StandaloneLootEntry {
    private final Item item;

    private ItemLootEntry(Item item, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        super(n, n2, iLootConditionArray, iLootFunctionArray);
        this.item = item;
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return LootEntryManager.ITEM;
    }

    @Override
    public void func_216154_a(Consumer<ItemStack> consumer, LootContext lootContext) {
        consumer.accept(new ItemStack(this.item));
    }

    public static StandaloneLootEntry.Builder<?> builder(IItemProvider iItemProvider) {
        return ItemLootEntry.builder((arg_0, arg_1, arg_2, arg_3) -> ItemLootEntry.lambda$builder$0(iItemProvider, arg_0, arg_1, arg_2, arg_3));
    }

    private static StandaloneLootEntry lambda$builder$0(IItemProvider iItemProvider, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        return new ItemLootEntry(iItemProvider.asItem(), n, n2, iLootConditionArray, iLootFunctionArray);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends StandaloneLootEntry.Serializer<ItemLootEntry> {
        @Override
        public void doSerialize(JsonObject jsonObject, ItemLootEntry itemLootEntry, JsonSerializationContext jsonSerializationContext) {
            super.doSerialize(jsonObject, itemLootEntry, jsonSerializationContext);
            ResourceLocation resourceLocation = Registry.ITEM.getKey(itemLootEntry.item);
            if (resourceLocation == null) {
                throw new IllegalArgumentException("Can't serialize unknown item " + itemLootEntry.item);
            }
            jsonObject.addProperty("name", resourceLocation.toString());
        }

        @Override
        protected ItemLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            Item item = JSONUtils.getItem(jsonObject, "name");
            return new ItemLootEntry(item, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        protected StandaloneLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, StandaloneLootEntry standaloneLootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (ItemLootEntry)standaloneLootEntry, jsonSerializationContext);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, LootEntry lootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (ItemLootEntry)lootEntry, jsonSerializationContext);
        }
    }
}

