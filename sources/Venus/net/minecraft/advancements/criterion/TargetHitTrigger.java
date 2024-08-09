/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TargetHitTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("target_hit");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("signal_strength"));
        EntityPredicate.AndPredicate andPredicate2 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "projectile", conditionArrayParser);
        return new Instance(andPredicate, intBound, andPredicate2);
    }

    public void test(ServerPlayerEntity serverPlayerEntity, Entity entity2, Vector3d vector3d, int n) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, entity2);
        this.triggerListeners(serverPlayerEntity, arg_0 -> TargetHitTrigger.lambda$test$0(lootContext, vector3d, n, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$test$0(LootContext lootContext, Vector3d vector3d, int n, Instance instance) {
        return instance.test(lootContext, vector3d, n);
    }

    public static class Instance
    extends CriterionInstance {
        private final MinMaxBounds.IntBound signalStrength;
        private final EntityPredicate.AndPredicate projectile;

        public Instance(EntityPredicate.AndPredicate andPredicate, MinMaxBounds.IntBound intBound, EntityPredicate.AndPredicate andPredicate2) {
            super(ID, andPredicate);
            this.signalStrength = intBound;
            this.projectile = andPredicate2;
        }

        public static Instance create(MinMaxBounds.IntBound intBound, EntityPredicate.AndPredicate andPredicate) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, intBound, andPredicate);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("signal_strength", this.signalStrength.serialize());
            jsonObject.add("projectile", this.projectile.serializeConditions(conditionArraySerializer));
            return jsonObject;
        }

        public boolean test(LootContext lootContext, Vector3d vector3d, int n) {
            if (!this.signalStrength.test(n)) {
                return true;
            }
            return this.projectile.testContext(lootContext);
        }
    }
}

