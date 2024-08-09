/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.RandomRanges;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;

public class EnchantWithLevels
extends LootFunction {
    private final IRandomRange randomLevel;
    private final boolean isTreasure;

    private EnchantWithLevels(ILootCondition[] iLootConditionArray, IRandomRange iRandomRange, boolean bl) {
        super(iLootConditionArray);
        this.randomLevel = iRandomRange;
        this.isTreasure = bl;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.ENCHANT_WITH_LEVELS;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        Random random2 = lootContext.getRandom();
        return EnchantmentHelper.addRandomEnchantment(random2, itemStack, this.randomLevel.generateInt(random2), this.isTreasure);
    }

    public static Builder func_215895_a(IRandomRange iRandomRange) {
        return new Builder(iRandomRange);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootFunction.Builder<Builder> {
        private final IRandomRange field_216060_a;
        private boolean field_216061_b;

        public Builder(IRandomRange iRandomRange) {
            this.field_216060_a = iRandomRange;
        }

        @Override
        protected Builder doCast() {
            return this;
        }

        public Builder func_216059_e() {
            this.field_216061_b = true;
            return this;
        }

        @Override
        public ILootFunction build() {
            return new EnchantWithLevels(this.getConditions(), this.field_216060_a, this.field_216061_b);
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
    extends LootFunction.Serializer<EnchantWithLevels> {
        @Override
        public void serialize(JsonObject jsonObject, EnchantWithLevels enchantWithLevels, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, enchantWithLevels, jsonSerializationContext);
            jsonObject.add("levels", RandomRanges.serialize(enchantWithLevels.randomLevel, jsonSerializationContext));
            jsonObject.addProperty("treasure", enchantWithLevels.isTreasure);
        }

        @Override
        public EnchantWithLevels deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            IRandomRange iRandomRange = RandomRanges.deserialize(jsonObject.get("levels"), jsonDeserializationContext);
            boolean bl = JSONUtils.getBoolean(jsonObject, "treasure", false);
            return new EnchantWithLevels(iLootConditionArray, iRandomRange, bl);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (EnchantWithLevels)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (EnchantWithLevels)object, jsonSerializationContext);
        }
    }
}

