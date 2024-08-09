/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.DamagePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class EntityHurtPlayerTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("entity_hurt_player");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        DamagePredicate damagePredicate = DamagePredicate.deserialize(jsonObject.get("damage"));
        return new Instance(andPredicate, damagePredicate);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, DamageSource damageSource, float f, float f2, boolean bl) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> EntityHurtPlayerTrigger.lambda$trigger$0(serverPlayerEntity, damageSource, f, f2, bl, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ServerPlayerEntity serverPlayerEntity, DamageSource damageSource, float f, float f2, boolean bl, Instance instance) {
        return instance.test(serverPlayerEntity, damageSource, f, f2, bl);
    }

    public static class Instance
    extends CriterionInstance {
        private final DamagePredicate damage;

        public Instance(EntityPredicate.AndPredicate andPredicate, DamagePredicate damagePredicate) {
            super(ID, andPredicate);
            this.damage = damagePredicate;
        }

        public static Instance forDamage(DamagePredicate.Builder builder) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, builder.build());
        }

        public boolean test(ServerPlayerEntity serverPlayerEntity, DamageSource damageSource, float f, float f2, boolean bl) {
            return this.damage.test(serverPlayerEntity, damageSource, f, f2, bl);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("damage", this.damage.serialize());
            return jsonObject;
        }
    }
}

