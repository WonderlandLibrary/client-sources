/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.DamageSourcePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class KilledTrigger
extends AbstractCriterionTrigger<Instance> {
    private final ResourceLocation id;

    public KilledTrigger(ResourceLocation resourceLocation) {
        this.id = resourceLocation;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return new Instance(this.id, andPredicate, EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "entity", conditionArrayParser), DamageSourcePredicate.deserialize(jsonObject.get("killing_blow")));
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, Entity entity2, DamageSource damageSource) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, entity2);
        this.triggerListeners(serverPlayerEntity, arg_0 -> KilledTrigger.lambda$trigger$0(serverPlayerEntity, lootContext, damageSource, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ServerPlayerEntity serverPlayerEntity, LootContext lootContext, DamageSource damageSource, Instance instance) {
        return instance.test(serverPlayerEntity, lootContext, damageSource);
    }

    public static class Instance
    extends CriterionInstance {
        private final EntityPredicate.AndPredicate entity;
        private final DamageSourcePredicate killingBlow;

        public Instance(ResourceLocation resourceLocation, EntityPredicate.AndPredicate andPredicate, EntityPredicate.AndPredicate andPredicate2, DamageSourcePredicate damageSourcePredicate) {
            super(resourceLocation, andPredicate);
            this.entity = andPredicate2;
            this.killingBlow = damageSourcePredicate;
        }

        public static Instance playerKilledEntity(EntityPredicate.Builder builder) {
            return new Instance(CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.createAndFromEntityCondition(builder.build()), DamageSourcePredicate.ANY);
        }

        public static Instance playerKilledEntity() {
            return new Instance(CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND, DamageSourcePredicate.ANY);
        }

        public static Instance playerKilledEntity(EntityPredicate.Builder builder, DamageSourcePredicate.Builder builder2) {
            return new Instance(CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.createAndFromEntityCondition(builder.build()), builder2.build());
        }

        public static Instance entityKilledPlayer() {
            return new Instance(CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND, DamageSourcePredicate.ANY);
        }

        public boolean test(ServerPlayerEntity serverPlayerEntity, LootContext lootContext, DamageSource damageSource) {
            return !this.killingBlow.test(serverPlayerEntity, damageSource) ? false : this.entity.testContext(lootContext);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("entity", this.entity.serializeConditions(conditionArraySerializer));
            jsonObject.add("killing_blow", this.killingBlow.serialize());
            return jsonObject;
        }
    }
}

