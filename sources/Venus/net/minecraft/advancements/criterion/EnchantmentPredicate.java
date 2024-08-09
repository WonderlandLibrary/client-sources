/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class EnchantmentPredicate {
    public static final EnchantmentPredicate ANY = new EnchantmentPredicate();
    public static final EnchantmentPredicate[] enchantments = new EnchantmentPredicate[0];
    private final Enchantment enchantment;
    private final MinMaxBounds.IntBound levels;

    public EnchantmentPredicate() {
        this.enchantment = null;
        this.levels = MinMaxBounds.IntBound.UNBOUNDED;
    }

    public EnchantmentPredicate(@Nullable Enchantment enchantment, MinMaxBounds.IntBound intBound) {
        this.enchantment = enchantment;
        this.levels = intBound;
    }

    public boolean test(Map<Enchantment, Integer> map) {
        if (this.enchantment != null) {
            if (!map.containsKey(this.enchantment)) {
                return true;
            }
            int n = map.get(this.enchantment);
            if (this.levels != null && !this.levels.test(n)) {
                return true;
            }
        } else if (this.levels != null) {
            for (Integer n : map.values()) {
                if (!this.levels.test(n)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public JsonElement serialize() {
        if (this == ANY) {
            return JsonNull.INSTANCE;
        }
        JsonObject jsonObject = new JsonObject();
        if (this.enchantment != null) {
            jsonObject.addProperty("enchantment", Registry.ENCHANTMENT.getKey(this.enchantment).toString());
        }
        jsonObject.add("levels", this.levels.serialize());
        return jsonObject;
    }

    public static EnchantmentPredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            Object object;
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "enchantment");
            Enchantment enchantment = null;
            if (jsonObject.has("enchantment")) {
                object = new ResourceLocation(JSONUtils.getString(jsonObject, "enchantment"));
                enchantment = Registry.ENCHANTMENT.getOptional((ResourceLocation)object).orElseThrow(() -> EnchantmentPredicate.lambda$deserialize$0((ResourceLocation)object));
            }
            object = MinMaxBounds.IntBound.fromJson(jsonObject.get("levels"));
            return new EnchantmentPredicate(enchantment, (MinMaxBounds.IntBound)object);
        }
        return ANY;
    }

    public static EnchantmentPredicate[] deserializeArray(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonElement, "enchantments");
            EnchantmentPredicate[] enchantmentPredicateArray = new EnchantmentPredicate[jsonArray.size()];
            for (int i = 0; i < enchantmentPredicateArray.length; ++i) {
                enchantmentPredicateArray[i] = EnchantmentPredicate.deserialize(jsonArray.get(i));
            }
            return enchantmentPredicateArray;
        }
        return enchantments;
    }

    private static JsonSyntaxException lambda$deserialize$0(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown enchantment '" + resourceLocation + "'");
    }
}

