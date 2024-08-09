/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MobEffectsPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EffectsChangedTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("effects_changed");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        MobEffectsPredicate mobEffectsPredicate = MobEffectsPredicate.deserialize(jsonObject.get("effects"));
        return new Instance(andPredicate, mobEffectsPredicate);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> EffectsChangedTrigger.lambda$trigger$0(serverPlayerEntity, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ServerPlayerEntity serverPlayerEntity, Instance instance) {
        return instance.test(serverPlayerEntity);
    }

    public static class Instance
    extends CriterionInstance {
        private final MobEffectsPredicate effects;

        public Instance(EntityPredicate.AndPredicate andPredicate, MobEffectsPredicate mobEffectsPredicate) {
            super(ID, andPredicate);
            this.effects = mobEffectsPredicate;
        }

        public static Instance forEffect(MobEffectsPredicate mobEffectsPredicate) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, mobEffectsPredicate);
        }

        public boolean test(ServerPlayerEntity serverPlayerEntity) {
            return this.effects.test(serverPlayerEntity);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("effects", this.effects.serialize());
            return jsonObject;
        }
    }
}

