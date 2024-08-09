/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import java.util.List;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootGenerator;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;

public class SetContents
extends LootFunction {
    private final List<LootEntry> field_215924_a;

    private SetContents(ILootCondition[] iLootConditionArray, List<LootEntry> list) {
        super(iLootConditionArray);
        this.field_215924_a = ImmutableList.copyOf(list);
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_CONTENTS;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        if (itemStack.isEmpty()) {
            return itemStack;
        }
        NonNullList<ItemStack> nonNullList = NonNullList.create();
        this.field_215924_a.forEach(arg_0 -> SetContents.lambda$doApply$1(lootContext, nonNullList, arg_0));
        CompoundNBT compoundNBT = new CompoundNBT();
        ItemStackHelper.saveAllItems(compoundNBT, nonNullList);
        CompoundNBT compoundNBT2 = itemStack.getOrCreateTag();
        compoundNBT2.put("BlockEntityTag", compoundNBT.merge(compoundNBT2.getCompound("BlockEntityTag")));
        return itemStack;
    }

    @Override
    public void func_225580_a_(ValidationTracker validationTracker) {
        super.func_225580_a_(validationTracker);
        for (int i = 0; i < this.field_215924_a.size(); ++i) {
            this.field_215924_a.get(i).func_225579_a_(validationTracker.func_227534_b_(".entry[" + i + "]"));
        }
    }

    public static Builder builderIn() {
        return new Builder();
    }

    private static void lambda$doApply$1(LootContext lootContext, NonNullList nonNullList, LootEntry lootEntry) {
        lootEntry.expand(lootContext, arg_0 -> SetContents.lambda$doApply$0(nonNullList, lootContext, arg_0));
    }

    private static void lambda$doApply$0(NonNullList nonNullList, LootContext lootContext, ILootGenerator iLootGenerator) {
        iLootGenerator.func_216188_a(LootTable.capStackSizes(nonNullList::add), lootContext);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootFunction.Builder<Builder> {
        private final List<LootEntry> lootEntries = Lists.newArrayList();

        @Override
        protected Builder doCast() {
            return this;
        }

        public Builder addLootEntry(LootEntry.Builder<?> builder) {
            this.lootEntries.add(builder.build());
            return this;
        }

        @Override
        public ILootFunction build() {
            return new SetContents(this.getConditions(), this.lootEntries);
        }

        @Override
        protected LootFunction.Builder doCast() {
            return this.doCast();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetContents> {
        @Override
        public void serialize(JsonObject jsonObject, SetContents setContents, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setContents, jsonSerializationContext);
            jsonObject.add("entries", jsonSerializationContext.serialize(setContents.field_215924_a));
        }

        @Override
        public SetContents deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            LootEntry[] lootEntryArray = JSONUtils.deserializeClass(jsonObject, "entries", jsonDeserializationContext, LootEntry[].class);
            return new SetContents(iLootConditionArray, Arrays.asList(lootEntryArray));
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetContents)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetContents)object, jsonSerializationContext);
        }
    }
}

