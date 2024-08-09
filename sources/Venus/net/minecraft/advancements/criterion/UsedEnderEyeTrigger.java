/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class UsedEnderEyeTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("used_ender_eye");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        MinMaxBounds.FloatBound floatBound = MinMaxBounds.FloatBound.fromJson(jsonObject.get("distance"));
        return new Instance(andPredicate, floatBound);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, BlockPos blockPos) {
        double d = serverPlayerEntity.getPosX() - (double)blockPos.getX();
        double d2 = serverPlayerEntity.getPosZ() - (double)blockPos.getZ();
        double d3 = d * d + d2 * d2;
        this.triggerListeners(serverPlayerEntity, arg_0 -> UsedEnderEyeTrigger.lambda$trigger$0(d3, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(double d, Instance instance) {
        return instance.test(d);
    }

    public static class Instance
    extends CriterionInstance {
        private final MinMaxBounds.FloatBound distance;

        public Instance(EntityPredicate.AndPredicate andPredicate, MinMaxBounds.FloatBound floatBound) {
            super(ID, andPredicate);
            this.distance = floatBound;
        }

        public boolean test(double d) {
            return this.distance.testSquared(d);
        }
    }
}

