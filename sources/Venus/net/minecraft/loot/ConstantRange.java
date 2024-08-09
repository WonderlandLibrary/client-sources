/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.loot.IRandomRange;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public final class ConstantRange
implements IRandomRange {
    private final int value;

    public ConstantRange(int n) {
        this.value = n;
    }

    @Override
    public int generateInt(Random random2) {
        return this.value;
    }

    @Override
    public ResourceLocation getType() {
        return CONSTANT;
    }

    public static ConstantRange of(int n) {
        return new ConstantRange(n);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<ConstantRange>,
    JsonSerializer<ConstantRange> {
        @Override
        public ConstantRange deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new ConstantRange(JSONUtils.getInt(jsonElement, "value"));
        }

        @Override
        public JsonElement serialize(ConstantRange constantRange, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(constantRange.value);
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ConstantRange)object, type, jsonSerializationContext);
        }
    }
}

