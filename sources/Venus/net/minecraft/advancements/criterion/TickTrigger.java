/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TickTrigger
extends AbstractCriterionTrigger<Instance> {
    public static final ResourceLocation ID = new ResourceLocation("tick");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return new Instance(andPredicate);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity) {
        this.triggerListeners(serverPlayerEntity, TickTrigger::lambda$trigger$0);
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(Instance instance) {
        return false;
    }

    public static class Instance
    extends CriterionInstance {
        public Instance(EntityPredicate.AndPredicate andPredicate) {
            super(ID, andPredicate);
        }
    }
}

