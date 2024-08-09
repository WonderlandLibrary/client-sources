/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.LootConditionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class TableBonus
implements ILootCondition {
    private final Enchantment enchantment;
    private final float[] chances;

    private TableBonus(Enchantment enchantment, float[] fArray) {
        this.enchantment = enchantment;
        this.chances = fArray;
    }

    @Override
    public LootConditionType func_230419_b_() {
        return LootConditionManager.TABLE_BONUS;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.TOOL);
    }

    @Override
    public boolean test(LootContext lootContext) {
        ItemStack itemStack = lootContext.get(LootParameters.TOOL);
        int n = itemStack != null ? EnchantmentHelper.getEnchantmentLevel(this.enchantment, itemStack) : 0;
        float f = this.chances[Math.min(n, this.chances.length - 1)];
        return lootContext.getRandom().nextFloat() < f;
    }

    public static ILootCondition.IBuilder builder(Enchantment enchantment, float ... fArray) {
        return () -> TableBonus.lambda$builder$0(enchantment, fArray);
    }

    @Override
    public boolean test(Object object) {
        return this.test((LootContext)object);
    }

    private static ILootCondition lambda$builder$0(Enchantment enchantment, float[] fArray) {
        return new TableBonus(enchantment, fArray);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements ILootSerializer<TableBonus> {
        @Override
        public void serialize(JsonObject jsonObject, TableBonus tableBonus, JsonSerializationContext jsonSerializationContext) {
            jsonObject.addProperty("enchantment", Registry.ENCHANTMENT.getKey(tableBonus.enchantment).toString());
            jsonObject.add("chances", jsonSerializationContext.serialize(tableBonus.chances));
        }

        @Override
        public TableBonus deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "enchantment"));
            Enchantment enchantment = Registry.ENCHANTMENT.getOptional(resourceLocation).orElseThrow(() -> Serializer.lambda$deserialize$0(resourceLocation));
            float[] fArray = JSONUtils.deserializeClass(jsonObject, "chances", jsonDeserializationContext, float[].class);
            return new TableBonus(enchantment, fArray);
        }

        @Override
        public Object deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
            return this.deserialize(jsonObject, jsonDeserializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (TableBonus)object, jsonSerializationContext);
        }

        private static JsonParseException lambda$deserialize$0(ResourceLocation resourceLocation) {
            return new JsonParseException("Invalid enchantment id: " + resourceLocation);
        }
    }
}

