/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CuredZombieVillagerTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("cured_zombie_villager");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        EntityPredicate.AndPredicate andPredicate2 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "zombie", conditionArrayParser);
        EntityPredicate.AndPredicate andPredicate3 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "villager", conditionArrayParser);
        return new Instance(andPredicate, andPredicate2, andPredicate3);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, ZombieEntity zombieEntity, VillagerEntity villagerEntity) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, zombieEntity);
        LootContext lootContext2 = EntityPredicate.getLootContext(serverPlayerEntity, villagerEntity);
        this.triggerListeners(serverPlayerEntity, arg_0 -> CuredZombieVillagerTrigger.lambda$trigger$0(lootContext, lootContext2, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(LootContext lootContext, LootContext lootContext2, Instance instance) {
        return instance.test(lootContext, lootContext2);
    }

    public static class Instance
    extends CriterionInstance {
        private final EntityPredicate.AndPredicate zombie;
        private final EntityPredicate.AndPredicate villager;

        public Instance(EntityPredicate.AndPredicate andPredicate, EntityPredicate.AndPredicate andPredicate2, EntityPredicate.AndPredicate andPredicate3) {
            super(ID, andPredicate);
            this.zombie = andPredicate2;
            this.villager = andPredicate3;
        }

        public static Instance any() {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND);
        }

        public boolean test(LootContext lootContext, LootContext lootContext2) {
            if (!this.zombie.testContext(lootContext)) {
                return true;
            }
            return this.villager.testContext(lootContext2);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("zombie", this.zombie.serializeConditions(conditionArraySerializer));
            jsonObject.add("villager", this.villager.serializeConditions(conditionArraySerializer));
            return jsonObject;
        }
    }
}

