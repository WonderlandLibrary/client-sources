/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.loot.ILootEntry;
import net.minecraft.loot.ILootGenerator;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;

public abstract class ParentedLootEntry
extends LootEntry {
    protected final LootEntry[] entries;
    private final ILootEntry children;

    protected ParentedLootEntry(LootEntry[] lootEntryArray, ILootCondition[] iLootConditionArray) {
        super(iLootConditionArray);
        this.entries = lootEntryArray;
        this.children = this.combineChildren(lootEntryArray);
    }

    @Override
    public void func_225579_a_(ValidationTracker validationTracker) {
        super.func_225579_a_(validationTracker);
        if (this.entries.length == 0) {
            validationTracker.addProblem("Empty children list");
        }
        for (int i = 0; i < this.entries.length; ++i) {
            this.entries[i].func_225579_a_(validationTracker.func_227534_b_(".entry[" + i + "]"));
        }
    }

    protected abstract ILootEntry combineChildren(ILootEntry[] var1);

    @Override
    public final boolean expand(LootContext lootContext, Consumer<ILootGenerator> consumer) {
        return !this.test(lootContext) ? false : this.children.expand(lootContext, consumer);
    }

    public static <T extends ParentedLootEntry> LootEntry.Serializer<T> getSerializer(IFactory<T> iFactory) {
        return new LootEntry.Serializer<T>(iFactory){
            final IFactory val$factory;
            {
                this.val$factory = iFactory;
            }

            @Override
            public void doSerialize(JsonObject jsonObject, T t, JsonSerializationContext jsonSerializationContext) {
                jsonObject.add("children", jsonSerializationContext.serialize(((ParentedLootEntry)t).entries));
            }

            @Override
            public final T deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
                LootEntry[] lootEntryArray = JSONUtils.deserializeClass(jsonObject, "children", jsonDeserializationContext, LootEntry[].class);
                return this.val$factory.create(lootEntryArray, iLootConditionArray);
            }

            @Override
            public LootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
                return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
            }

            @Override
            public void doSerialize(JsonObject jsonObject, LootEntry lootEntry, JsonSerializationContext jsonSerializationContext) {
                this.doSerialize(jsonObject, (Object)((ParentedLootEntry)lootEntry), jsonSerializationContext);
            }
        };
    }

    @FunctionalInterface
    public static interface IFactory<T extends ParentedLootEntry> {
        public T create(LootEntry[] var1, ILootCondition[] var2);
    }
}

