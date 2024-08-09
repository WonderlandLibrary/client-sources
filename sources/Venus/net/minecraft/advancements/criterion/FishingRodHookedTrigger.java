/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FishingRodHookedTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("fishing_rod_hooked");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("rod"));
        EntityPredicate.AndPredicate andPredicate2 = EntityPredicate.AndPredicate.deserializeJSONObject(jsonObject, "entity", conditionArrayParser);
        ItemPredicate itemPredicate2 = ItemPredicate.deserialize(jsonObject.get("item"));
        return new Instance(andPredicate, itemPredicate, andPredicate2, itemPredicate2);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, ItemStack itemStack, FishingBobberEntity fishingBobberEntity, Collection<ItemStack> collection) {
        LootContext lootContext = EntityPredicate.getLootContext(serverPlayerEntity, fishingBobberEntity.func_234607_k_() != null ? fishingBobberEntity.func_234607_k_() : fishingBobberEntity);
        this.triggerListeners(serverPlayerEntity, arg_0 -> FishingRodHookedTrigger.lambda$trigger$0(itemStack, lootContext, collection, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ItemStack itemStack, LootContext lootContext, Collection collection, Instance instance) {
        return instance.test(itemStack, lootContext, collection);
    }

    public static class Instance
    extends CriterionInstance {
        private final ItemPredicate rod;
        private final EntityPredicate.AndPredicate entity;
        private final ItemPredicate item;

        public Instance(EntityPredicate.AndPredicate andPredicate, ItemPredicate itemPredicate, EntityPredicate.AndPredicate andPredicate2, ItemPredicate itemPredicate2) {
            super(ID, andPredicate);
            this.rod = itemPredicate;
            this.entity = andPredicate2;
            this.item = itemPredicate2;
        }

        public static Instance create(ItemPredicate itemPredicate, EntityPredicate entityPredicate, ItemPredicate itemPredicate2) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, itemPredicate, EntityPredicate.AndPredicate.createAndFromEntityCondition(entityPredicate), itemPredicate2);
        }

        public boolean test(ItemStack itemStack, LootContext lootContext, Collection<ItemStack> collection) {
            if (!this.rod.test(itemStack)) {
                return true;
            }
            if (!this.entity.testContext(lootContext)) {
                return true;
            }
            if (this.item != ItemPredicate.ANY) {
                Object object;
                boolean bl = false;
                Entity entity2 = lootContext.get(LootParameters.THIS_ENTITY);
                if (entity2 instanceof ItemEntity && this.item.test(((ItemEntity)(object = (ItemEntity)entity2)).getItem())) {
                    bl = true;
                }
                for (ItemStack itemStack2 : collection) {
                    if (!this.item.test(itemStack2)) continue;
                    bl = true;
                    break;
                }
                if (!bl) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("rod", this.rod.serialize());
            jsonObject.add("entity", this.entity.serializeConditions(conditionArraySerializer));
            jsonObject.add("item", this.item.serialize());
            return jsonObject;
        }
    }
}

