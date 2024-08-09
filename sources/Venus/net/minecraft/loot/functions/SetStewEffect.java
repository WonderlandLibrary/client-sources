/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.loot.functions.LootFunctionManager;
import net.minecraft.potion.Effect;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SetStewEffect
extends LootFunction {
    private final Map<Effect, RandomValueRange> field_215950_a;

    private SetStewEffect(ILootCondition[] iLootConditionArray, Map<Effect, RandomValueRange> map) {
        super(iLootConditionArray);
        this.field_215950_a = ImmutableMap.copyOf(map);
    }

    @Override
    public LootFunctionType getFunctionType() {
        return LootFunctionManager.SET_STEW_EFFECT;
    }

    @Override
    public ItemStack doApply(ItemStack itemStack, LootContext lootContext) {
        if (itemStack.getItem() == Items.SUSPICIOUS_STEW && !this.field_215950_a.isEmpty()) {
            Random random2 = lootContext.getRandom();
            int n = random2.nextInt(this.field_215950_a.size());
            Map.Entry<Effect, RandomValueRange> entry = Iterables.get(this.field_215950_a.entrySet(), n);
            Effect effect = entry.getKey();
            int n2 = entry.getValue().generateInt(random2);
            if (!effect.isInstant()) {
                n2 *= 20;
            }
            SuspiciousStewItem.addEffect(itemStack, effect, n2);
            return itemStack;
        }
        return itemStack;
    }

    public static Builder func_215948_b() {
        return new Builder();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Builder
    extends LootFunction.Builder<Builder> {
        private final Map<Effect, RandomValueRange> field_216078_a = Maps.newHashMap();

        @Override
        protected Builder doCast() {
            return this;
        }

        public Builder func_216077_a(Effect effect, RandomValueRange randomValueRange) {
            this.field_216078_a.put(effect, randomValueRange);
            return this;
        }

        @Override
        public ILootFunction build() {
            return new SetStewEffect(this.getConditions(), this.field_216078_a);
        }

        @Override
        protected LootFunction.Builder doCast() {
            return this.doCast();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    extends LootFunction.Serializer<SetStewEffect> {
        @Override
        public void serialize(JsonObject jsonObject, SetStewEffect setStewEffect, JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setStewEffect, jsonSerializationContext);
            if (!setStewEffect.field_215950_a.isEmpty()) {
                JsonArray jsonArray = new JsonArray();
                for (Effect effect : setStewEffect.field_215950_a.keySet()) {
                    JsonObject jsonObject2 = new JsonObject();
                    ResourceLocation resourceLocation = Registry.EFFECTS.getKey(effect);
                    if (resourceLocation == null) {
                        throw new IllegalArgumentException("Don't know how to serialize mob effect " + effect);
                    }
                    jsonObject2.add("type", new JsonPrimitive(resourceLocation.toString()));
                    jsonObject2.add("duration", jsonSerializationContext.serialize(setStewEffect.field_215950_a.get(effect)));
                    jsonArray.add(jsonObject2);
                }
                jsonObject.add("effects", jsonArray);
            }
        }

        @Override
        public SetStewEffect deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            HashMap<Effect, RandomValueRange> hashMap = Maps.newHashMap();
            if (jsonObject.has("effects")) {
                for (JsonElement jsonElement : JSONUtils.getJsonArray(jsonObject, "effects")) {
                    String string = JSONUtils.getString(jsonElement.getAsJsonObject(), "type");
                    Effect effect = Registry.EFFECTS.getOptional(new ResourceLocation(string)).orElseThrow(() -> Serializer.lambda$deserialize$0(string));
                    RandomValueRange randomValueRange = JSONUtils.deserializeClass(jsonElement.getAsJsonObject(), "duration", jsonDeserializationContext, RandomValueRange.class);
                    hashMap.put(effect, randomValueRange);
                }
            }
            return new SetStewEffect(iLootConditionArray, hashMap);
        }

        @Override
        public LootFunction deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditionArray) {
            return this.deserialize(jsonObject, jsonDeserializationContext, iLootConditionArray);
        }

        @Override
        public void serialize(JsonObject jsonObject, LootFunction lootFunction, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetStewEffect)lootFunction, jsonSerializationContext);
        }

        @Override
        public void serialize(JsonObject jsonObject, Object object, JsonSerializationContext jsonSerializationContext) {
            this.serialize(jsonObject, (SetStewEffect)object, jsonSerializationContext);
        }

        private static JsonSyntaxException lambda$deserialize$0(String string) {
            return new JsonSyntaxException("Unknown mob effect '" + string + "'");
        }
    }
}

