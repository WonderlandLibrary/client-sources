/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.DistancePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LevitationTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("levitation");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        DistancePredicate distancePredicate = DistancePredicate.deserialize(jsonObject.get("distance"));
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("duration"));
        return new Instance(andPredicate, distancePredicate, intBound);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, Vector3d vector3d, int n) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> LevitationTrigger.lambda$trigger$0(serverPlayerEntity, vector3d, n, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ServerPlayerEntity serverPlayerEntity, Vector3d vector3d, int n, Instance instance) {
        return instance.test(serverPlayerEntity, vector3d, n);
    }

    public static class Instance
    extends CriterionInstance {
        private final DistancePredicate distance;
        private final MinMaxBounds.IntBound duration;

        public Instance(EntityPredicate.AndPredicate andPredicate, DistancePredicate distancePredicate, MinMaxBounds.IntBound intBound) {
            super(ID, andPredicate);
            this.distance = distancePredicate;
            this.duration = intBound;
        }

        public static Instance forDistance(DistancePredicate distancePredicate) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, distancePredicate, MinMaxBounds.IntBound.UNBOUNDED);
        }

        public boolean test(ServerPlayerEntity serverPlayerEntity, Vector3d vector3d, int n) {
            if (!this.distance.test(vector3d.x, vector3d.y, vector3d.z, serverPlayerEntity.getPosX(), serverPlayerEntity.getPosY(), serverPlayerEntity.getPosZ())) {
                return true;
            }
            return this.duration.test(n);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("distance", this.distance.serialize());
            jsonObject.add("duration", this.duration.serialize());
            return jsonObject;
        }
    }
}

