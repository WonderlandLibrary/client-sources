/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;

public class RandomChance
implements ILootCondition {
    private final float chance;

    private RandomChance(float f) {
        this.chance = f;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.RANDOM_CHANCE;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return lootContext.getRandom().nextFloat() < this.chance;
    }

    public static ILootCondition.IBuilder builder(float f) {
        return () -> RandomChance.lambda$builder$0(f);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$builder$0(float f) {
        return new RandomChance(f);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<RandomChance> {
        @Override
        public void serialize(JsonObject jsonObject, RandomChance randomChance, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("chance", Float.valueOf(randomChance.chance));
        }

        @Override
        public RandomChance deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return new RandomChance(JSONUtils.getFloat(jsonObject, "chance"));
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (RandomChance)object, jsonSerializationContext);
        }
    }
}

