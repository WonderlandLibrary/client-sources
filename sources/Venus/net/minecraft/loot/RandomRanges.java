/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.Map;
import net.minecraft.loot.BinomialRange;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IRandomRange;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class RandomRanges {
    private static final Map<ResourceLocation, Class<? extends IRandomRange>> GENERATOR_MAP = Maps.newHashMap();

    public static IRandomRange deserialize(JsonElement jsonElement, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.isJsonPrimitive()) {
            return (IRandomRange)jsonDeserializationContext.deserialize(jsonElement, (Type)((Object)ConstantRange.class));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String string = JSONUtils.getString(jsonObject, "type", IRandomRange.UNIFORM.toString());
        Class<? extends IRandomRange> clazz = GENERATOR_MAP.get(new ResourceLocation(string));
        if (clazz == null) {
            throw new JsonParseException("Unknown generator: " + string);
        }
        return (IRandomRange)jsonDeserializationContext.deserialize(jsonObject, clazz);
    }

    public static JsonElement serialize(IRandomRange iRandomRange, JsonSerializationContext jsonSerializationContext) {
        JsonElement jsonElement = jsonSerializationContext.serialize(iRandomRange);
        if (jsonElement.isJsonObject()) {
            jsonElement.getAsJsonObject().addProperty("type", iRandomRange.getType().toString());
        }
        return jsonElement;
    }

    static {
        GENERATOR_MAP.put(IRandomRange.UNIFORM, RandomValueRange.class);
        GENERATOR_MAP.put(IRandomRange.BINOMIAL, BinomialRange.class);
        GENERATOR_MAP.put(IRandomRange.CONSTANT, ConstantRange.class);
    }
}

