/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PlayerEntityInteractionTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("player_interacted_with_entity");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("item"));
        EntityPredicate.AndPredicate andPredicate2 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "entity", conditionArrayParser);
        return new Instance(andPredicate, itemPredicate, andPredicate2);
    }

    public void test(ServerPlayerEntity serverPlayerEntity, ItemStack itemStack, Entity entity2) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, entity2);
        this.triggerListeners(serverPlayerEntity, arg_0 -> PlayerEntityInteractionTrigger.lambda$test$0(itemStack, lootContext, arg_0));
    }

    @Override
    protected CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$test$0(ItemStack itemStack, LootContext lootContext, Instance instance) {
        return instance.test(itemStack, lootContext);
    }

    public static class Instance
    extends CriterionInstance {
        private final ItemPredicate stack;
        private final EntityPredicate.AndPredicate entity;

        public Instance(EntityPredicate.AndPredicate andPredicate, ItemPredicate itemPredicate, EntityPredicate.AndPredicate andPredicate2) {
            super(ID, andPredicate);
            this.stack = itemPredicate;
            this.entity = andPredicate2;
        }

        public static Instance create(EntityPredicate.AndPredicate andPredicate, ItemPredicate.Builder builder, EntityPredicate.AndPredicate andPredicate2) {
            return new Instance(andPredicate, builder.build(), andPredicate2);
        }

        public boolean test(ItemStack itemStack, LootContext lootContext) {
            return !this.stack.test(itemStack) ? false : this.entity.testContext(lootContext);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("item", this.stack.serialize());
            jsonObject.add("entity", this.entity.serializeConditions(conditionArraySerializer));
            return jsonObject;
        }
    }
}

