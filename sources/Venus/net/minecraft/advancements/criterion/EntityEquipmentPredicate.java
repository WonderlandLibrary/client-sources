/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.util.JSONUtils;
import net.minecraft.world.raid.Raid;

public class EntityEquipmentPredicate {
    public static final EntityEquipmentPredicate ANY = new EntityEquipmentPredicate(ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY);
    public static final EntityEquipmentPredicate WEARING_ILLAGER_BANNER = new EntityEquipmentPredicate(ItemPredicate.Builder.create().item(Items.WHITE_BANNER).nbt(Raid.createIllagerBanner().getTag()).build(), ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY);
    private final ItemPredicate head;
    private final ItemPredicate chest;
    private final ItemPredicate legs;
    private final ItemPredicate feet;
    private final ItemPredicate mainHand;
    private final ItemPredicate offHand;

    public EntityEquipmentPredicate(ItemPredicate itemPredicate, ItemPredicate itemPredicate2, ItemPredicate itemPredicate3, ItemPredicate itemPredicate4, ItemPredicate itemPredicate5, ItemPredicate itemPredicate6) {
        this.head = itemPredicate;
        this.chest = itemPredicate2;
        this.legs = itemPredicate3;
        this.feet = itemPredicate4;
        this.mainHand = itemPredicate5;
        this.offHand = itemPredicate6;
    }

    public boolean test(@Nullable Entity entity2) {
        if (this == ANY) {
            return false;
        }
        if (!(entity2 instanceof LivingEntity)) {
            return true;
        }
        LivingEntity livingEntity = (LivingEntity)entity2;
        if (!this.head.test(livingEntity.getItemStackFromSlot(EquipmentSlotType.HEAD))) {
            return true;
        }
        if (!this.chest.test(livingEntity.getItemStackFromSlot(EquipmentSlotType.CHEST))) {
            return true;
        }
        if (!this.legs.test(livingEntity.getItemStackFromSlot(EquipmentSlotType.LEGS))) {
            return true;
        }
        if (!this.feet.test(livingEntity.getItemStackFromSlot(EquipmentSlotType.FEET))) {
            return true;
        }
        if (!this.mainHand.test(livingEntity.getItemStackFromSlot(EquipmentSlotType.MAINHAND))) {
            return true;
        }
        return this.offHand.test(livingEntity.getItemStackFromSlot(EquipmentSlotType.OFFHAND));
    }

    public static EntityEquipmentPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "equipment");
            ItemPredicate itemPredicate = ItemPredicate.deserialize(jsonObject.get("head"));
            ItemPredicate itemPredicate2 = ItemPredicate.deserialize(jsonObject.get("chest"));
            ItemPredicate itemPredicate3 = ItemPredicate.deserialize(jsonObject.get("legs"));
            ItemPredicate itemPredicate4 = ItemPredicate.deserialize(jsonObject.get("feet"));
            ItemPredicate itemPredicate5 = ItemPredicate.deserialize(jsonObject.get("mainhand"));
            ItemPredicate itemPredicate6 = ItemPredicate.deserialize(jsonObject.get("offhand"));
            return new EntityEquipmentPredicate(itemPredicate, itemPredicate2, itemPredicate3, itemPredicate4, itemPredicate5, itemPredicate6);
        }
        return ANY;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("head", this.head.serialize());
        jsonObject.add("chest", this.chest.serialize());
        jsonObject.add("legs", this.legs.serialize());
        jsonObject.add("feet", this.feet.serialize());
        jsonObject.add("mainhand", this.mainHand.serialize());
        jsonObject.add("offhand", this.offHand.serialize());
        return jsonObject;
    }

    public static class Builder {
        private ItemPredicate head = ItemPredicate.ANY;
        private ItemPredicate chest = ItemPredicate.ANY;
        private ItemPredicate legs = ItemPredicate.ANY;
        private ItemPredicate feet = ItemPredicate.ANY;
        private ItemPredicate mainHand = ItemPredicate.ANY;
        private ItemPredicate offHand = ItemPredicate.ANY;

        public static Builder createBuilder() {
            return new Builder();
        }

        public Builder setHeadCondition(ItemPredicate itemPredicate) {
            this.head = itemPredicate;
            return this;
        }

        public Builder setChestCondition(ItemPredicate itemPredicate) {
            this.chest = itemPredicate;
            return this;
        }

        public Builder setLegsCondition(ItemPredicate itemPredicate) {
            this.legs = itemPredicate;
            return this;
        }

        public Builder setFeetCondition(ItemPredicate itemPredicate) {
            this.feet = itemPredicate;
            return this;
        }

        public EntityEquipmentPredicate build() {
            return new EntityEquipmentPredicate(this.head, this.chest, this.legs, this.feet, this.mainHand, this.offHand);
        }
    }
}

