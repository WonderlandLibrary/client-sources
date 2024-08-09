/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.RandomRanges;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;

public class SetCount
extends LootFunction {
    private final IRandomRange countRange;

    private SetCount(ILootCondition[] iLootConditionArray, IRandomRange iRandomRange) {
        super(iLootConditionArray);
        this.countRange = iRandomRange;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_COUNT;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        itemStack.setCount(this.countRange.generateInt(lootContext.getRandom()));
        return itemStack;
    }

    public static LootFunction.Builder<?> builder(IRandomRange iRandomRange) {
        return SetCount.builder(arg_0 -> SetCount.lambda$builder$0(iRandomRange, arg_0));
    }

    private static ILootFunction lambda$builder$0(IRandomRange iRandomRange, ILootCondition[] iLootConditionArray) {
        return new SetCount(iLootConditionArray, iRandomRange);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetCount> {
        @Override
        public void serialize(JsonObject jsonObject, SetCount setCount, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setCount, jsonSerializationContext);
            jsonObject.add("count", RandomRanges.serialize(setCount.countRange, jsonSerializationContext));
        }

        @Override
        public SetCount deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            IRandomRange iRandomRange = RandomRanges.deserialize(jsonObject.get("count"), jsonDeserializationContext);
            return new SetCount(iLootConditionArray, iRandomRange);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetCount)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetCount)object, jsonSerializationContext);
        }
    }
}

