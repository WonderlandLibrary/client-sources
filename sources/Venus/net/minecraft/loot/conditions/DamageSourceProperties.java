/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

public class DamageSourceProperties
implements ILootCondition {
    private final DamageSourcePredicate predicate;

    private DamageSourceProperties(DamageSourcePredicate damageSourcePredicate) {
        this.predicate = damageSourcePredicate;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.DAMAGE_SOURCE_PROPERTIES;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.field_237457_g_, LootParameters.DAMAGE_SOURCE);
    }

    @Override
    public boolean test(LootContext lootContext) {
        DamageSource damageSource = lootContext.get(LootParameters.DAMAGE_SOURCE);
        Vector3d vector3d = lootContext.get(LootParameters.field_237457_g_);
        return vector3d != null && damageSource != null && this.predicate.test(lootContext.getWorld(), vector3d, damageSource);
    }

    public static ILootCondition.IBuilder builder(DamageSourcePredicate.Builder builder) {
        return () -> DamageSourceProperties.lambda$builder$0(builder);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$builder$0(DamageSourcePredicate.Builder builder) {
        return new DamageSourceProperties(builder.build());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<DamageSourceProperties> {
        @Override
        public void serialize(JsonObject jsonObject, DamageSourceProperties damageSourceProperties, JsonSerializationContext jsonSerializationContext) {
            jsonObject.add("predicate", damageSourceProperties.predicate.serialize());
        }

        @Override
        public DamageSourceProperties deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            DamageSourcePredicate damageSourcePredicate = DamageSourcePredicate.deserialize(jsonObject.get("predicate"));
            return new DamageSourceProperties(damageSourcePredicate);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (DamageSourceProperties)object, jsonSerializationContext);
        }
    }
}

