/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.vector.Vector3d;

public class EntityHasProperty
implements ILootCondition {
    private final EntityPredicate predicate;
    private final LootContext.EntityTarget target;

    private EntityHasProperty(EntityPredicate entityPredicate, LootContext.EntityTarget entityTarget) {
        this.predicate = entityPredicate;
        this.target = entityTarget;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.ENTITY_PROPERTIES;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.field_237457_g_, this.target.getParameter());
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity2 = lootContext.get(this.target.getParameter());
        Vector3d vector3d = lootContext.get(LootParameters.field_237457_g_);
        return this.predicate.test(lootContext.getWorld(), vector3d, entity2);
    }

    public static ILootCondition.IBuilder builder(LootContext.EntityTarget entityTarget) {
        return EntityHasProperty.builder(entityTarget, EntityPredicate.Builder.create());
    }

    public static ILootCondition.IBuilder builder(LootContext.EntityTarget entityTarget, EntityPredicate.Builder builder) {
        return () -> EntityHasProperty.lambda$builder$0(builder, entityTarget);
    }

    public static ILootCondition.IBuilder func_237477_a_(LootContext.EntityTarget entityTarget, EntityPredicate entityPredicate) {
        return () -> EntityHasProperty.lambda$func_237477_a_$1(entityPredicate, entityTarget);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$func_237477_a_$1(EntityPredicate entityPredicate, LootContext.EntityTarget entityTarget) {
        return new EntityHasProperty(entityPredicate, entityTarget);
    }

    private static ILootCondition lambda$builder$0(EntityPredicate.Builder builder, LootContext.EntityTarget entityTarget) {
        return new EntityHasProperty(builder.build(), entityTarget);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<EntityHasProperty> {
        @Override
        public void serialize(JsonObject jsonObject, EntityHasProperty entityHasProperty, JsonSerializationContext jsonSerializationContext) {
            jsonObject.add("predicate", entityHasProperty.predicate.serialize());
            jsonObject.add("entity", jsonSerializationContext.serialize((Object)entityHasProperty.target));
        }

        @Override
        public EntityHasProperty deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            EntityPredicate entityPredicate = EntityPredicate.deserialize(jsonObject.get("predicate"));
            return new EntityHasProperty(entityPredicate, JSONUtils.deserializeClass(jsonObject, "entity", jsonDeserializationContext, LootContext.EntityTarget.class));
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (EntityHasProperty)object, jsonSerializationContext);
        }
    }
}

