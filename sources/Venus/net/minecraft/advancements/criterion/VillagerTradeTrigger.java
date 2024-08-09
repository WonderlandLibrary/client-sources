/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class VillagerTradeTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("villager_trade");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        EntityPredicate.AndPredicate andPredicate2 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "villager", conditionArrayParser);
        ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("item"));
        return new Instance(andPredicate, andPredicate2, itemPredicate);
    }

    public void test(ServerPlayerEntity serverPlayerEntity, AbstractVillagerEntity abstractVillagerEntity, ItemStack itemStack) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, abstractVillagerEntity);
        this.triggerListeners(serverPlayerEntity, arg_0 -> VillagerTradeTrigger.lambda$test$0(lootContext, itemStack, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$test$0(LootContext lootContext, ItemStack itemStack, Instance instance) {
        return instance.test(lootContext, itemStack);
    }

    public static class Instance
    extends CriterionInstance {
        private final EntityPredicate.AndPredicate villager;
        private final ItemPredicate item;

        public Instance(EntityPredicate.AndPredicate andPredicate, EntityPredicate.AndPredicate andPredicate2, ItemPredicate itemPredicate) {
            super(ID, andPredicate);
            this.villager = andPredicate2;
            this.item = itemPredicate;
        }

        public static Instance any() {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, EntityPredicate.AndPredicate.ANY_AND, ItemPredicate.ANY);
        }

        public boolean test(LootContext lootContext, ItemStack itemStack) {
            if (!this.villager.testContext(lootContext)) {
                return true;
            }
            return this.item.test(itemStack);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("item", this.item.serialize());
            jsonObject.add("villager", this.villager.serializeConditions(conditionArraySerializer));
            return jsonObject;
        }
    }
}

