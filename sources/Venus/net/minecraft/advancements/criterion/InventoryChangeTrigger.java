/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class InventoryChangeTrigger
extends AbstractCriterionTrigger<Instance> {
    private static final ResourceLocation ID = new ResourceLocation("inventory_changed");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public Instance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "slots", new JsonObject());
        MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject2.get("occupied"));
        MinMaxBounds.IntBound intBound2 = MinMaxBounds.IntBound.fromJson(jsonObject2.get("full"));
        MinMaxBounds.IntBound intBound3 = MinMaxBounds.IntBound.fromJson(jsonObject2.get("empty"));
        ItemPredicate[] itemPredicateArray = ItemPredicate.deserializeArray(jsonObject.get("items"));
        return new Instance(andPredicate, intBound, intBound2, intBound3, itemPredicateArray);
    }

    public void test(ServerPlayerEntity serverPlayerEntity, PlayerInventory playerInventory, ItemStack itemStack) {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < playerInventory.getSizeInventory(); ++i) {
            ItemStack itemStack2 = playerInventory.getStackInSlot(i);
            if (itemStack2.isEmpty()) {
                ++n2;
                continue;
            }
            ++n3;
            if (itemStack2.getCount() < itemStack2.getMaxStackSize()) continue;
            ++n;
        }
        this.trigger(serverPlayerEntity, playerInventory, itemStack, n, n2, n3);
    }

    private void trigger(ServerPlayerEntity serverPlayerEntity, PlayerInventory playerInventory, ItemStack itemStack, int n, int n2, int n3) {
        this.triggerListeners(serverPlayerEntity, arg_0 -> InventoryChangeTrigger.lambda$trigger$0(playerInventory, itemStack, n, n2, n3, arg_0));
    }

    @Override
    public CriterionInstance deserializeTrigger(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        return this.deserializeTrigger(jsonObject, andPredicate, conditionArrayParser);
    }

    private static boolean lambda$trigger$0(PlayerInventory playerInventory, ItemStack itemStack, int n, int n2, int n3, Instance instance) {
        return instance.test(playerInventory, itemStack, n, n2, n3);
    }

    public static class Instance
    extends CriterionInstance {
        private final MinMaxBounds.IntBound occupied;
        private final MinMaxBounds.IntBound full;
        private final MinMaxBounds.IntBound empty;
        private final ItemPredicate[] items;

        public Instance(EntityPredicate.AndPredicate andPredicate, MinMaxBounds.IntBound intBound, MinMaxBounds.IntBound intBound2, MinMaxBounds.IntBound intBound3, ItemPredicate[] itemPredicateArray) {
            super(ID, andPredicate);
            this.occupied = intBound;
            this.full = intBound2;
            this.empty = intBound3;
            this.items = itemPredicateArray;
        }

        public static Instance forItems(ItemPredicate ... itemPredicateArray) {
            return new Instance(EntityPredicate.AndPredicate.ANY_AND, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, itemPredicateArray);
        }

        public static Instance forItems(IItemProvider ... iItemProviderArray) {
            ItemPredicate[] itemPredicateArray = new ItemPredicate[iItemProviderArray.length];
            for (int i = 0; i < iItemProviderArray.length; ++i) {
                itemPredicateArray[i] = new ItemPredicate(null, iItemProviderArray[i].asItem(), MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, EnchantmentPredicate.enchantments, EnchantmentPredicate.enchantments, null, NBTPredicate.ANY);
            }
            return Instance.forItems(itemPredicateArray);
        }

        @Override
        public JsonObject serialize(ConditionArraySerializer conditionArraySerializer) {
            JsonElement jsonElement;
            JsonObject jsonObject = super.serialize(conditionArraySerializer);
            if (!(this.occupied.isUnbounded() && this.full.isUnbounded() && this.empty.isUnbounded())) {
                jsonElement = new JsonObject();
                ((JsonObject)jsonElement).add("occupied", this.occupied.serialize());
                ((JsonObject)jsonElement).add("full", this.full.serialize());
                ((JsonObject)jsonElement).add("empty", this.empty.serialize());
                jsonObject.add("slots", jsonElement);
            }
            if (this.items.length > 0) {
                jsonElement = new JsonArray();
                for (ItemPredicate itemPredicate : this.items) {
                    ((JsonArray)jsonElement).add(itemPredicate.serialize());
                }
                jsonObject.add("items", jsonElement);
            }
            return jsonObject;
        }

        public boolean test(PlayerInventory playerInventory, ItemStack itemStack, int n, int n2, int n3) {
            if (!this.full.test(n)) {
                return true;
            }
            if (!this.empty.test(n2)) {
                return true;
            }
            if (!this.occupied.test(n3)) {
                return true;
            }
            int n4 = this.items.length;
            if (n4 == 0) {
                return false;
            }
            if (n4 != 1) {
                ObjectArrayList<ItemPredicate> objectArrayList = new ObjectArrayList<ItemPredicate>(this.items);
                int n5 = playerInventory.getSizeInventory();
                for (int i = 0; i < n5; ++i) {
                    if (objectArrayList.isEmpty()) {
                        return false;
                    }
                    ItemStack itemStack2 = playerInventory.getStackInSlot(i);
                    if (itemStack2.isEmpty()) continue;
                    objectArrayList.removeIf(arg_0 -> Instance.lambda$test$0(itemStack2, arg_0));
                }
                return objectArrayList.isEmpty();
            }
            return !itemStack.isEmpty() && this.items[0].test(itemStack);
        }

        private static boolean lambda$test$0(ItemStack itemStack, ItemPredicate itemPredicate) {
            return itemPredicate.test(itemStack);
        }
    }
}

