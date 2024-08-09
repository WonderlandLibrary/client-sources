/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ItemDurabilityTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("item_durability_changed");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("item"));
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("durability"));
        MinMaxBounds.IntBound intBound2 = MinMaxBounds.IntBound.fromJson(jsonObject.get("delta"));
        return new Instance(andPredicate, itemPredicate, intBound, intBound2);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, ItemStack itemStack, int n) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> ItemDurabilityTrigger.lambda$trigger$0(itemStack, n, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(ItemStack itemStack, int n, Instance instance) {
        return instance.test(itemStack, n);
    }

    public static class Instance
    extends CriterionInstance {
        private final ItemPredicate item;
        private final MinMaxBounds.IntBound durability;
        private final MinMaxBounds.IntBound delta;

        public Instance(EntityPredicate.AndPredicate andPredicate, ItemPredicate itemPredicate, MinMaxBounds.IntBound intBound, MinMaxBounds.IntBound intBound2) {
            super(ID, andPredicate);
            this.item = itemPredicate;
            this.durability = intBound;
            this.delta = intBound2;
        }

        public static Instance create(EntityPredicate.AndPredicate andPredicate, ItemPredicate itemPredicate, MinMaxBounds.IntBound intBound) {
            return new Instance(andPredicate, itemPredicate, intBound, MinMaxBounds.IntBound.UNBOUNDED);
        }

        public boolean test(ItemStack itemStack, int n) {
            if (!this.item.test(itemStack)) {
                return true;
            }
            if (!this.durability.test(itemStack.getMaxDamage() - n)) {
                return true;
            }
            return this.delta.test(itemStack.getDamage() - n);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("item", this.item.serialize());
            jsonObject.add("durability", this.durability.serialize());
            jsonObject.add("delta", this.delta.serialize());
            return jsonObject;
        }
    }
}

