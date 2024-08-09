/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.LootFunctionManager;

public class ExplosionDecay
extends LootFunction {
    private ExplosionDecay(ILootCondition[] iLootConditionArray) {
        super(iLootConditionArray);
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.EXPLOSION_DECAY;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        Float f = lootContext.get(LootParameters.EXPLOSION_RADIUS);
        if (f != null) {
            Random random2 = lootContext.getRandom();
            float f2 = 1.0f / f.floatValue();
            int n = itemStack.getCount();
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                if (!(random2.nextFloat() <= f2)) continue;
                ++n2;
            }
            itemStack.setCount(n2);
        }
        return itemStack;
    }

    public static LootFunction.Builder<?> builder() {
        return ExplosionDecay.builder(ExplosionDecay::new);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<ExplosionDecay> {
        @Override
        public ExplosionDecay deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return new ExplosionDecay(iLootConditionArray);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }
    }
}

