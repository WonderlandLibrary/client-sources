/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ItemOverride {
    private final ResourceLocation location;
    private final Map<ResourceLocation, Float> mapResourceValues;

    public ItemOverride(ResourceLocation resourceLocation, Map<ResourceLocation, Float> map) {
        this.location = resourceLocation;
        this.mapResourceValues = map;
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    boolean matchesOverride(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity) {
        Item item = itemStack.getItem();
        for (Map.Entry<ResourceLocation, Float> entry : this.mapResourceValues.entrySet()) {
            IItemPropertyGetter iItemPropertyGetter = ItemModelsProperties.func_239417_a_(item, entry.getKey());
            if (iItemPropertyGetter != null && !(iItemPropertyGetter.call(itemStack, clientWorld, livingEntity) < entry.getValue().floatValue())) continue;
            return true;
        }
        return false;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Deserializer
    implements JsonDeserializer<ItemOverride> {
        protected Deserializer() {
        }

        @Override
        public ItemOverride deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "model"));
            Map<ResourceLocation, Float> map = this.makeMapResourceValues(jsonObject);
            return new ItemOverride(resourceLocation, map);
        }

        protected Map<ResourceLocation, Float> makeMapResourceValues(JsonObject jsonObject) {
            LinkedHashMap<ResourceLocation, Float> linkedHashMap = Maps.newLinkedHashMap();
            JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "predicate");
            for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                linkedHashMap.put(new ResourceLocation(entry.getKey()), Float.valueOf(JSONUtils.getFloat(entry.getValue(), entry.getKey())));
            }
            return linkedHashMap;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

