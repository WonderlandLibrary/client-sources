/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemPredicate {
    public static final ItemPredicate ANY = new ItemPredicate();
    @Nullable
    private final ITag<Item> tag;
    @Nullable
    private final Item item;
    private final MinMaxBounds.IntBound count;
    private final MinMaxBounds.IntBound durability;
    private final EnchantmentPredicate[] enchantments;
    private final EnchantmentPredicate[] bookEnchantments;
    @Nullable
    private final Potion potion;
    private final NBTPredicate nbt;

    public ItemPredicate() {
        this.tag = null;
        this.item = null;
        this.potion = null;
        this.count = MinMaxBounds.IntBound.UNBOUNDED;
        this.durability = MinMaxBounds.IntBound.UNBOUNDED;
        this.enchantments = EnchantmentPredicate.enchantments;
        this.bookEnchantments = EnchantmentPredicate.enchantments;
        this.nbt = NBTPredicate.ANY;
    }

    public ItemPredicate(@Nullable ITag<Item> iTag, @Nullable Item item, MinMaxBounds.IntBound intBound, MinMaxBounds.IntBound intBound2, EnchantmentPredicate[] enchantmentPredicateArray, EnchantmentPredicate[] enchantmentPredicateArray2, @Nullable Potion potion, NBTPredicate nBTPredicate) {
        this.tag = iTag;
        this.item = item;
        this.count = intBound;
        this.durability = intBound2;
        this.enchantments = enchantmentPredicateArray;
        this.bookEnchantments = enchantmentPredicateArray2;
        this.potion = potion;
        this.nbt = nBTPredicate;
    }

    public boolean test(ItemStack itemStack) {
        Map<Enchantment, Integer> map;
        if (this == ANY) {
            return false;
        }
        if (this.tag != null && !this.tag.contains(itemStack.getItem())) {
            return true;
        }
        if (this.item != null && itemStack.getItem() != this.item) {
            return true;
        }
        if (!this.count.test(itemStack.getCount())) {
            return true;
        }
        if (!this.durability.isUnbounded() && !itemStack.isDamageable()) {
            return true;
        }
        if (!this.durability.test(itemStack.getMaxDamage() - itemStack.getDamage())) {
            return true;
        }
        if (!this.nbt.test(itemStack)) {
            return true;
        }
        if (this.enchantments.length > 0) {
            map = EnchantmentHelper.deserializeEnchantments(itemStack.getEnchantmentTagList());
            for (EnchantmentPredicate enchantmentPredicate : this.enchantments) {
                if (enchantmentPredicate.test(map)) continue;
                return true;
            }
        }
        if (this.bookEnchantments.length > 0) {
            map = EnchantmentHelper.deserializeEnchantments(EnchantedBookItem.getEnchantments(itemStack));
            for (EnchantmentPredicate enchantmentPredicate : this.bookEnchantments) {
                if (enchantmentPredicate.test(map)) continue;
                return true;
            }
        }
        map = PotionUtils.getPotionFromItem(itemStack);
        return this.potion == null || this.potion == map;
    }

    public static ItemPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            EnchantmentPredicate[] enchantmentPredicateArray;
            Object object;
            ITag<Item> iTag;
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "item");
            MinMaxBounds.IntBound intBound = MinMaxBounds.IntBound.fromJson(jsonObject.get("count"));
            MinMaxBounds.IntBound intBound2 = MinMaxBounds.IntBound.fromJson(jsonObject.get("durability"));
            if (jsonObject.has("data")) {
                throw new JsonParseException("Disallowed data tag found");
            }
            NBTPredicate nBTPredicate = NBTPredicate.deserialize(jsonObject.get("nbt"));
            Item item = null;
            if (jsonObject.has("item")) {
                iTag = new ResourceLocation(JSONUtils.getString(jsonObject, "item"));
                item = Registry.ITEM.getOptional((ResourceLocation)((Object)iTag)).orElseThrow(() -> ItemPredicate.lambda$deserialize$0((ResourceLocation)((Object)iTag)));
            }
            iTag = null;
            if (jsonObject.has("tag")) {
                object = new ResourceLocation(JSONUtils.getString(jsonObject, "tag"));
                iTag = TagCollectionManager.getManager().getItemTags().get((ResourceLocation)object);
                if (iTag == null) {
                    throw new JsonSyntaxException("Unknown item tag '" + (ResourceLocation)object + "'");
                }
            }
            object = null;
            if (jsonObject.has("potion")) {
                enchantmentPredicateArray = new ResourceLocation(JSONUtils.getString(jsonObject, "potion"));
                object = Registry.POTION.getOptional((ResourceLocation)enchantmentPredicateArray).orElseThrow(() -> ItemPredicate.lambda$deserialize$1((ResourceLocation)enchantmentPredicateArray));
            }
            enchantmentPredicateArray = EnchantmentPredicate.deserializeArray(jsonObject.get("enchantments"));
            EnchantmentPredicate[] enchantmentPredicateArray2 = EnchantmentPredicate.deserializeArray(jsonObject.get("stored_enchantments"));
            return new ItemPredicate(iTag, item, intBound, intBound2, enchantmentPredicateArray, enchantmentPredicateArray2, (Potion)object, nBTPredicate);
        }
        return ANY;
    }

    public JsonElement serialize() {
        JsonArray jsonArray;
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (this.item != null) {
            jsonObject.addProperty("item", Registry.ITEM.getKey(this.item).toString());
        }
        if (this.tag != null) {
            jsonObject.addProperty("tag", TagCollectionManager.getManager().getItemTags().getValidatedIdFromTag(this.tag).toString());
        }
        jsonObject.add("count", this.count.serialize());
        jsonObject.add("durability", this.durability.serialize());
        jsonObject.add("nbt", this.nbt.serialize());
        if (this.enchantments.length > 0) {
            jsonArray = new JsonArray();
            for (EnchantmentPredicate enchantmentPredicate : this.enchantments) {
                jsonArray.add(enchantmentPredicate.serialize());
            }
            jsonObject.add("enchantments", jsonArray);
        }
        if (this.bookEnchantments.length > 0) {
            jsonArray = new JsonArray();
            for (EnchantmentPredicate enchantmentPredicate : this.bookEnchantments) {
                jsonArray.add(enchantmentPredicate.serialize());
            }
            jsonObject.add("stored_enchantments", jsonArray);
        }
        if (this.potion != null) {
            jsonObject.addProperty("potion", Registry.POTION.getKey(this.potion).toString());
        }
        return jsonObject;
    }

    public static ItemPredicate[] deserializeArray(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonElement, "items");
            ItemPredicate[] itemPredicateArray = new ItemPredicate[jsonArray.size()];
            for (int i = 0; i < itemPredicateArray.length; ++i) {
                itemPredicateArray[i] = ItemPredicate.deserialize(jsonArray.get(i));
            }
            return itemPredicateArray;
        }
        return new ItemPredicate[0];
    }

    private static JsonSyntaxException lambda$deserialize$1(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown potion '" + resourceLocation + "'");
    }

    private static JsonSyntaxException lambda$deserialize$0(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown item id '" + resourceLocation + "'");
    }

    public static class Builder {
        private final List<EnchantmentPredicate> enchantments = Lists.newArrayList();
        private final List<EnchantmentPredicate> bookEnchantments = Lists.newArrayList();
        @Nullable
        private Item item;
        @Nullable
        private ITag<Item> tag;
        private MinMaxBounds.IntBound count = MinMaxBounds.IntBound.UNBOUNDED;
        private MinMaxBounds.IntBound durability = MinMaxBounds.IntBound.UNBOUNDED;
        @Nullable
        private Potion potion;
        private NBTPredicate nbt = NBTPredicate.ANY;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder item(IItemProvider iItemProvider) {
            this.item = iItemProvider.asItem();
            return this;
        }

        public Builder tag(ITag<Item> iTag) {
            this.tag = iTag;
            return this;
        }

        public Builder nbt(CompoundNBT compoundNBT) {
            this.nbt = new NBTPredicate(compoundNBT);
            return this;
        }

        public Builder enchantment(EnchantmentPredicate enchantmentPredicate) {
            this.enchantments.add(enchantmentPredicate);
            return this;
        }

        public ItemPredicate build() {
            return new ItemPredicate(this.tag, this.item, this.count, this.durability, this.enchantments.toArray(EnchantmentPredicate.enchantments), this.bookEnchantments.toArray(EnchantmentPredicate.enchantments), this.potion, this.nbt);
        }
    }
}

