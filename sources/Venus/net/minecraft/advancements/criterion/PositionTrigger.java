/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PositionTrigger
extends AbstractCriterionTrigger<Instance> {
    private final ResourceLocation id;

    public PositionTrigger(ResourceLocation resourceLocation) {
        this.id = resourceLocation;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "location", jsonObject);
        LocationPredicate locationPredicate = LocationPredicate.deserialize(jsonObject2);
        return new Instance(this.id, andPredicate, locationPredicate);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> PositionTrigger.lambda$trigger$0(serverPlayerEntity, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ServerPlayerEntity serverPlayerEntity, Instance instance) {
        return instance.test(serverPlayerEntity.getServerWorld(), serverPlayerEntity.getPosX(), serverPlayerEntity.getPosY(), serverPlayerEntity.getPosZ());
    }

    public static class Instance
    extends CriterionInstance {
        private final LocationPredicate location;

        public Instance(ResourceLocation resourceLocation, EntityPredicate.AndPredicate andPredicate, LocationPredicate locationPredicate) {
            super(resourceLocation, andPredicate);
            this.location = locationPredicate;
        }

        public static Instance forLocation(LocationPredicate locationPredicate) {
            return new Instance(CriteriaTriggers.LOCATION.id, EntityPredicate.AndPredicate.ANY_AND, locationPredicate);
        }

        public static Instance sleptInBed() {
            return new Instance(CriteriaTriggers.SLEPT_IN_BED.id, EntityPredicate.AndPredicate.ANY_AND, LocationPredicate.ANY);
        }

        public static Instance villageHero() {
            return new Instance(CriteriaTriggers.HERO_OF_THE_VILLAGE.id, EntityPredicate.AndPredicate.ANY_AND, LocationPredicate.ANY);
        }

        public boolean test(ServerWorld serverWorld, double d, double d2, double d3) {
            return this.location.test(serverWorld, d, d2, d3);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("location", this.location.serialize());
            return jsonObject;
        }
    }
}

