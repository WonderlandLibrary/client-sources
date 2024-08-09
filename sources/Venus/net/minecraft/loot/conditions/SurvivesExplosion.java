/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;

public class SurvivesExplosion
implements ILootCondition {
    private static final SurvivesExplosion INSTANCE = new SurvivesExplosion();

    private SurvivesExplosion() {
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.SURVIVES_EXPLOSION;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.EXPLOSION_RADIUS);
    }

    @Override
    public boolean test(LootContext lootContext) {
        Float f = lootContext.get(LootParameters.EXPLOSION_RADIUS);
        if (f != null) {
            Random random2 = lootContext.getRandom();
            float f2 = 1.0f / f.floatValue();
            return random2.nextFloat() <= f2;
        }
        return false;
    }

    public static ILootCondition.IBuilder builder() {
        return SurvivesExplosion::lambda$builder$0;
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$builder$0() {
        return INSTANCE;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<SurvivesExplosion> {
        @Override
        public void serialize(JsonObject jsonObject, SurvivesExplosion survivesExplosion, JsonSerializationContext jsonSerializationContext) {
        }

        @Override
        public SurvivesExplosion deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return INSTANCE;
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SurvivesExplosion)object, jsonSerializationContext);
        }
    }
}

