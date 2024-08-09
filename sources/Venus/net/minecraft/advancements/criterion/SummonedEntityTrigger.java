/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SummonedEntityTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("summoned_entity");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        EntityPredicate.AndPredicate andPredicate2 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "entity", conditionArrayParser);
        return new Instance(andPredicate, andPredicate2);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, Entity entity2) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, entity2);
        this.triggerListeners(serverPlayerEntity, arg_0 -> SummonedEntityTrigger.lambda$trigger$0(lootContext, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(LootContext lootContext, Instance instance) {
        return instance.test(lootContext);
    }

    public static class Instance
    extends CriterionInstance {
        private final EntityPredicate.AndPredicate entity;

        public Instance(EntityPredicate.AndPredicate andPredicate, EntityPredicate.AndPredicate andPredicate2) {
            super(ID, andPredicate);
            this.entity = andPredicate2;
        }

        public static Instance summonedEntity(EntityPredicate.Builder builder) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.createAndFromEntityCondition(builder.build()));
        }

        public boolean test(LootContext lootContext) {
            return this.entity.testContext(lootContext);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("entity", this.entity.serializeConditions(conditionArraySerializer));
            return jsonObject;
        }
    }
}

