/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class NetherTravelTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("nether_travel");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        LocationPredicate locationPredicate = LocationPredicate.deserialize(jsonObject.get("entered"));
        LocationPredicate locationPredicate2 = LocationPredicate.deserialize(jsonObject.get("exited"));
        DistancePredicate distancePredicate = DistancePredicate.deserialize(jsonObject.get("distance"));
        return new Instance(andPredicate, locationPredicate, locationPredicate2, distancePredicate);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, Vector3d vector3d) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> NetherTravelTrigger.lambda$trigger$0(serverPlayerEntity, vector3d, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ServerPlayerEntity serverPlayerEntity, Vector3d vector3d, Instance instance) {
        return instance.test(serverPlayerEntity.getServerWorld(), vector3d, serverPlayerEntity.getPosX(), serverPlayerEntity.getPosY(), serverPlayerEntity.getPosZ());
    }

    public static class Instance
    extends CriterionInstance {
        private final LocationPredicate entered;
        private final LocationPredicate exited;
        private final DistancePredicate distance;

        public Instance(EntityPredicate.AndPredicate andPredicate, LocationPredicate locationPredicate, LocationPredicate locationPredicate2, DistancePredicate distancePredicate) {
            super(ID, andPredicate);
            this.entered = locationPredicate;
            this.exited = locationPredicate2;
            this.distance = distancePredicate;
        }

        public static Instance forDistance(DistancePredicate distancePredicate) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, LocationPredicate.ANY, LocationPredicate.ANY, distancePredicate);
        }

        public boolean test(ServerWorld serverWorld, Vector3d vector3d, double d, double d2, double d3) {
            if (!this.entered.test(serverWorld, vector3d.x, vector3d.y, vector3d.z)) {
                return true;
            }
            if (!this.exited.test(serverWorld, d, d2, d3)) {
                return true;
            }
            return this.distance.test(vector3d.x, vector3d.y, vector3d.z, d, d2, d3);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("entered", this.entered.serialize());
            jsonObject.add("exited", this.exited.serialize());
            jsonObject.add("distance", this.distance.serialize());
            return jsonObject;
        }
    }
}

