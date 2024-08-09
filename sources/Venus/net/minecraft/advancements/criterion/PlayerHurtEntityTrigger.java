/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.DamagePredicate;
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
public class PlayerHurtEntityTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("player_hurt_entity");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        DamagePredicate damagePredicate = DamagePredicate.deserialize(jsonObject.get("damage"));
        EntityPredicate.AndPredicate andPredicate2 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "entity", conditionArrayParser);
        return new Instance(andPredicate, damagePredicate, andPredicate2);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, Entity entity2, DamageSource damageSource, float f, float f2, boolean bl) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, entity2);
        this.triggerListeners(serverPlayerEntity, arg_0 -> PlayerHurtEntityTrigger.lambda$trigger$0(serverPlayerEntity, lootContext, damageSource, f, f2, bl, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ServerPlayerEntity serverPlayerEntity, LootContext lootContext, DamageSource damageSource, float f, float f2, boolean bl, Instance instance) {
        return instance.test(serverPlayerEntity, lootContext, damageSource, f, f2, bl);
    }

    public static class Instance
    extends CriterionInstance {
        private final DamagePredicate damage;
        private final EntityPredicate.AndPredicate entity;

        public Instance(EntityPredicate.AndPredicate andPredicate, DamagePredicate damagePredicate, EntityPredicate.AndPredicate andPredicate2) {
            super(ID, andPredicate);
            this.damage = damagePredicate;
            this.entity = andPredicate2;
        }

        public static Instance forDamage(DamagePredicate.Builder builder) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, builder.build(), EntityPredicate.AndPredicate.ANY_AND);
        }

        public boolean test(ServerPlayerEntity serverPlayerEntity, LootContext lootContext, DamageSource damageSource, float f, float f2, boolean bl) {
            if (!this.damage.test(serverPlayerEntity, damageSource, f, f2, bl)) {
                return true;
            }
            return this.entity.testContext(lootContext);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("damage", this.damage.serialize());
            jsonObject.add("entity", this.entity.serializeConditions(conditionArraySerializer));
            return jsonObject;
        }
    }
}

