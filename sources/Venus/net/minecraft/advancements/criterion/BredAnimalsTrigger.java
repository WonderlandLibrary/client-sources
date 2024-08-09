/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BredAnimalsTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("bred_animals");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        EntityPredicate.AndPredicate andPredicate2 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "parent", conditionArrayParser);
        EntityPredicate.AndPredicate andPredicate3 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "partner", conditionArrayParser);
        EntityPredicate.AndPredicate andPredicate4 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "child", conditionArrayParser);
        return new Instance(andPredicate, andPredicate2, andPredicate3, andPredicate4);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, AnimalEntity animalEntity, AnimalEntity animalEntity2, @Nullable AgeableEntity ageableEntity) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, animalEntity);
        LootContext lootContext2 = EntityPredicate.getLootContext(serverPlayerEntity, animalEntity2);
        LootContext lootContext3 = ageableEntity != null ? EntityPredicate.getLootContext(serverPlayerEntity, ageableEntity) : null;
        this.triggerListeners(serverPlayerEntity, arg_0 -> BredAnimalsTrigger.lambda$trigger$0(lootContext, lootContext2, lootContext3, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(LootContext lootContext, LootContext lootContext2, LootContext lootContext3, Instance instance) {
        return instance.test(lootContext, lootContext2, lootContext3);
    }

    public static class Instance
    extends CriterionInstance {
        private final EntityPredicate.AndPredicate parent;
        private final EntityPredicate.AndPredicate partner;
        private final EntityPredicate.AndPredicate child;

        public Instance(EntityPredicate.AndPredicate andPredicate, EntityPredicate.AndPredicate andPredicate2, EntityPredicate.AndPredicate andPredicate3, EntityPredicate.AndPredicate andPredicate4) {
            super(ID, andPredicate);
            this.parent = andPredicate2;
            this.partner = andPredicate3;
            this.child = andPredicate4;
        }

        public static Instance any() {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND);
        }

        public static Instance forParent(EntityPredicate.Builder builder) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.createAndFromEntityCondition(builder.build()));
        }

        public static Instance forAll(EntityPredicate entityPredicate, EntityPredicate entityPredicate2, EntityPredicate entityPredicate3) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.createAndFromEntityCondition(entityPredicate), EntityPredicate.AndPredicate.createAndFromEntityCondition(entityPredicate2), EntityPredicate.AndPredicate.createAndFromEntityCondition(entityPredicate3));
        }

        public boolean test(LootContext lootContext, LootContext lootContext2, @Nullable LootContext lootContext3) {
            if (this.child == EntityPredicate.AndPredicate.ANY_AND || lootContext3 != null && this.child.testContext(lootContext3)) {
                return this.parent.testContext(lootContext) && this.partner.testContext(lootContext2) || this.parent.testContext(lootContext2) && this.partner.testContext(lootContext);
            }
            return true;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("parent", this.parent.serializeConditions(conditionArraySerializer));
            jsonObject.add("partner", this.partner.serializeConditions(conditionArraySerializer));
            jsonObject.add("child", this.child.serializeConditions(conditionArraySerializer));
            return jsonObject;
        }
    }
}

