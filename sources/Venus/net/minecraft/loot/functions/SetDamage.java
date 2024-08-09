/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetDamage
extends LootFunction {
    private static final Logger LOGGER = LogManager.getLogger();
    private final RandomValueRange damageRange;

    private SetDamage(ILootCondition[] iLootConditionArray, RandomValueRange randomValueRange) {
        super(iLootConditionArray);
        this.damageRange = randomValueRange;
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_DAMAGE;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        if (itemStack.isDamageable()) {
            float f = 1.0f - this.damageRange.generateFloat(lootContext.getRandom());
            itemStack.setDamage(MathHelper.floor(f * (float)itemStack.getMaxDamage()));
        } else {
            LOGGER.warn("Couldn't set damage of loot item {}", (Object)itemStack);
        }
        return itemStack;
    }

    public static LootFunction.Builder<?> func_215931_a(RandomValueRange randomValueRange) {
        return SetDamage.builder(arg_0 -> SetDamage.lambda$func_215931_a$0(randomValueRange, arg_0));
    }

    private static ILootFunction lambda$func_215931_a$0(RandomValueRange randomValueRange, ILootCondition[] iLootConditionArray) {
        return new SetDamage(iLootConditionArray, randomValueRange);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetDamage> {
        @Override
        public void serialize(JsonObject jsonObject, SetDamage setDamage, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setDamage, jsonSerializationContext);
            jsonObject.add("damage", jsonSerializationContext.serialize(setDamage.damageRange));
        }

        @Override
        public SetDamage deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return new SetDamage(iLootConditionArray, JSONUtils.deserializeClass(jsonObject, "damage", jsonDeserializationContext, RandomValueRange.class));
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetDamage)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetDamage)object, jsonSerializationContext);
        }
    }
}

