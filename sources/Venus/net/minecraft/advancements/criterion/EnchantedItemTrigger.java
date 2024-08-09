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
public class EnchantedItemTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("enchanted_item");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("item"));
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("levels"));
        return new Instance(andPredicate, itemPredicate, intBound);
    }

    public void trigger(ServerPlayerEntity serverPlayerEntity, ItemStack itemStack, int n) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> EnchantedItemTrigger.lambda$trigger$0(itemStack, n, arg_0));
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
        private final MinMaxBounds.IntBound levels;

        public Instance(EntityPredicate.AndPredicate andPredicate, ItemPredicate itemPredicate, MinMaxBounds.IntBound intBound) {
            super(ID, andPredicate);
            this.item = itemPredicate;
            this.levels = intBound;
        }

        public static Instance any() {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, ItemPredicate.ANY, MinMaxBounds.IntBound.UNBOUNDED);
        }

        public boolean test(ItemStack itemStack, int n) {
            if (!this.item.test(itemStack)) {
                return true;
            }
            return this.levels.test(n);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            jsonObject.add("item", this.item.serialize());
            jsonObject.add("levels", this.levels.serialize());
            return jsonObject;
        }
    }
}

