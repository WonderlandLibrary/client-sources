/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.IntClamper;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;

public class LimitCount
extends LootFunction {
    private final IntClamper field_215914_a;

    private LimitCount(ILootCondition[] iLootConditionArray, IntClamper intClamper) {
        super(iLootConditionArray);
        this.field_215914_a = intClamper;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.LIMIT_COUNT;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        int n = this.field_215914_a.applyAsInt(itemStack.getCount());
        itemStack.setCount(n);
        return itemStack;
    }

    public static LootFunction.Builder<?> func_215911_a(IntClamper intClamper) {
        return LimitCount.builder(arg_0 -> LimitCount.lambda$func_215911_a$0(intClamper, arg_0));
    }

    private static ILootFunction lambda$func_215911_a$0(IntClamper intClamper, ILootCondition[] iLootConditionArray) {
        return new LimitCount(iLootConditionArray, intClamper);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<LimitCount> {
        @Override
        public void serialize(JsonObject jsonObject, LimitCount limitCount, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, limitCount, jsonSerializationContext);
            jsonObject.add("limit", jsonSerializationContext.serialize(limitCount.field_215914_a));
        }

        @Override
        public LimitCount deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            IntClamper intClamper = JSONUtils.deserializeClass(jsonObject, "limit", jsonDeserializationContext, IntClamper.class);
            return new LimitCount(iLootConditionArray, intClamper);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (LimitCount)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (LimitCount)object, jsonSerializationContext);
        }
    }
}

