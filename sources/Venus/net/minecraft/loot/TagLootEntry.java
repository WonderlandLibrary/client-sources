/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootGenerator;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class TagLootEntry
extends StandaloneLootEntry {
    private final ITag<Item> tag;
    private final boolean expand;

    private TagLootEntry(ITag<Item> iTag, boolean bl, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        super(n, n2, iLootConditionArray, iLootFunctionArray);
        this.tag = iTag;
        this.expand = bl;
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return LootEntryManager.TAG;
    }

    @Override
    public void func_216154_a(Consumer<ItemStack> consumer, LootContext lootContext) {
        this.tag.getAllElements().forEach(arg_0 -> TagLootEntry.lambda$func_216154_a$0(consumer, arg_0));
    }

    private boolean generateLoot(LootContext lootContext, Consumer<ILootGenerator> consumer) {
        if (!this.test(lootContext)) {
            return true;
        }
        for (Item item : this.tag.getAllElements()) {
            consumer.accept(new StandaloneLootEntry.Generator(this, item){
                final Item val$item;
                final TagLootEntry this$0;
                {
                    this.this$0 = tagLootEntry;
                    this.val$item = item;
                    super(tagLootEntry);
                }

                @Override
                public void func_216188_a(Consumer<ItemStack> consumer, LootContext lootContext) {
                    consumer.accept(new ItemStack(this.val$item));
                }
            });
        }
        return false;
    }

    @Override
    public boolean expand(LootContext lootContext, Consumer<ILootGenerator> consumer) {
        return this.expand ? this.generateLoot(lootContext, consumer) : super.expand(lootContext, consumer);
    }

    public static StandaloneLootEntry.Builder<?> getBuilder(ITag<Item> iTag) {
        return TagLootEntry.builder((arg_0, arg_1, arg_2, arg_3) -> TagLootEntry.lambda$getBuilder$1(iTag, arg_0, arg_1, arg_2, arg_3));
    }

    private static StandaloneLootEntry lambda$getBuilder$1(ITag iTag, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
        return new TagLootEntry(iTag, true, n, n2, iLootConditionArray, iLootFunctionArray);
    }

    private static void lambda$func_216154_a$0(Consumer consumer, Item item) {
        consumer.accept(new ItemStack(item));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends StandaloneLootEntry.Serializer<TagLootEntry> {
        @Override
        public void doSerialize(JsonObject jsonObject, TagLootEntry tagLootEntry, JsonSerializationContext jsonSerializationContext) {
            super.doSerialize(jsonObject, tagLootEntry, jsonSerializationContext);
            jsonObject.addProperty("name", TagCollectionManager.getManager().getItemTags().getValidatedIdFromTag(tagLootEntry.tag).toString());
            jsonObject.addProperty("expand", tagLootEntry.expand);
        }

        @Override
        protected TagLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "name"));
            ITag<Item> iTag = TagCollectionManager.getManager().getItemTags().get(resourceLocation);
            if (iTag == null) {
                throw new JsonParseException("Can't find tag: " + resourceLocation);
            }
            boolean bl = JSONUtils.getBoolean(jsonObject, "expand");
            return new TagLootEntry(iTag, bl, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        protected StandaloneLootEntry deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int n, int n2, ILootCondition[] iLootConditionArray, ILootFunction[] iLootFunctionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, n, n2, iLootConditionArray, iLootFunctionArray);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, StandaloneLootEntry standaloneLootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (TagLootEntry)standaloneLootEntry, jsonSerializationContext);
        }

        @Override
        public void doSerialize(JsonObject jsonObject, LootEntry lootEntry, JsonSerializationContext jsonSerializationContext) {
            this.doSerialize(jsonObject, (TagLootEntry)lootEntry, jsonSerializationContext);
        }
    }
}

