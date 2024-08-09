/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;

public class RandomChanceWithLooting
implements ILootCondition {
    private final float chance;
    private final float lootingMultiplier;

    private RandomChanceWithLooting(float f, float f2) {
        this.chance = f;
        this.lootingMultiplier = f2;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.RANDOM_CHANCE_WITH_LOOTING;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.KILLER_ENTITY);
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity2 = lootContext.get(LootParameters.KILLER_ENTITY);
        int n = 0;
        if (entity2 instanceof LivingEntity) {
            n = EnchantmentHelper.getLootingModifier((LivingEntity)entity2);
        }
        return lootContext.getRandom().nextFloat() < this.chance + (float)n * this.lootingMultiplier;
    }

    public static ILootCondition.IBuilder builder(float f, float f2) {
        return () -> RandomChanceWithLooting.lambda$builder$0(f, f2);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$builder$0(float f, float f2) {
        return new RandomChanceWithLooting(f, f2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<RandomChanceWithLooting> {
        @Override
        public void serialize(JsonObject jsonObject, RandomChanceWithLooting randomChanceWithLooting, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("chance", Float.valueOf(randomChanceWithLooting.chance));
            jsonObject.addProperty("looting_multiplier", Float.valueOf(randomChanceWithLooting.lootingMultiplier));
        }

        @Override
        public RandomChanceWithLooting deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return new RandomChanceWithLooting(JSONUtils.getFloat(jsonObject, "chance"), JSONUtils.getFloat(jsonObject, "looting_multiplier"));
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (RandomChanceWithLooting)object, jsonSerializationContext);
        }
    }
}

