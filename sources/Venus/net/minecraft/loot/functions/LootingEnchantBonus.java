/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;

public class LootingEnchantBonus
extends LootFunction {
    private final RandomValueRange count;
    private final int limit;

    private LootingEnchantBonus(ILootCondition[] iLootConditionArray, RandomValueRange randomValueRange, int n) {
        super(iLootConditionArray);
        this.count = randomValueRange;
        this.limit = n;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.LOOTING_ENCHANT;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.KILLER_ENTITY);
    }

    private boolean func_215917_b() {
        return this.limit > 0;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        Entity entity2 = lootContext.get(LootParameters.KILLER_ENTITY);
        if (entity2 instanceof LivingEntity) {
            int n = EnchantmentHelper.getLootingModifier((LivingEntity)entity2);
            if (n == 0) {
                return itemStack;
            }
            float f = (float)n * this.count.generateFloat(lootContext.getRandom());
            itemStack.grow(Math.round(f));
            if (this.func_215917_b() && itemStack.getCount() > this.limit) {
                itemStack.setCount(this.limit);
            }
        }
        return itemStack;
    }

    public static Builder builder(RandomValueRange randomValueRange) {
        return new Builder(randomValueRange);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootFunction.Builder<Builder> {
        private final RandomValueRange field_216073_a;
        private int field_216074_b = 0;

        public Builder(RandomValueRange randomValueRange) {
            this.field_216073_a = randomValueRange;
        }

        @Override
        protected Builder doCast() {
            return this;
        }

        public Builder func_216072_a(int n) {
            this.field_216074_b = n;
            return this;
        }

        @Override
        public ILootFunction build() {
            return new LootingEnchantBonus(this.getConditions(), this.field_216073_a, this.field_216074_b);
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
    extends LootFunction.Serializer<LootingEnchantBonus> {
        @Override
        public void serialize(JsonObject jsonObject, LootingEnchantBonus lootingEnchantBonus, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, lootingEnchantBonus, jsonSerializationContext);
            jsonObject.add("count", jsonSerializationContext.serialize(lootingEnchantBonus.count));
            if (lootingEnchantBonus.func_215917_b()) {
                jsonObject.add("limit", jsonSerializationContext.serialize(lootingEnchantBonus.limit));
            }
        }

        @Override
        public LootingEnchantBonus deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            int n = JSONUtils.getInt(jsonObject, "limit", 0);
            return new LootingEnchantBonus(iLootConditionArray, JSONUtils.deserializeClass(jsonObject, "count", jsonDeserializationContext, RandomValueRange.class), n);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (LootingEnchantBonus)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (LootingEnchantBonus)object, jsonSerializationContext);
        }
    }
}

