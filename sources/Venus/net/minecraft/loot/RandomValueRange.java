/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.loot.IRandomRange;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RandomValueRange
implements IRandomRange {
    private final float min;
    private final float max;

    public RandomValueRange(float f, float f2) {
        this.min = f;
        this.max = f2;
    }

    public RandomValueRange(float f) {
        this.min = f;
        this.max = f;
    }

    public static RandomValueRange of(float f, float f2) {
        return new RandomValueRange(f, f2);
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    @Override
    public int generateInt(Random random2) {
        return MathHelper.nextInt(random2, MathHelper.floor(this.min), MathHelper.floor(this.max));
    }

    public float generateFloat(Random random2) {
        return MathHelper.nextFloat(random2, this.min, this.max);
    }

    public boolean isInRange(int n) {
        return (float)n <= this.max && (float)n >= this.min;
    }

    @Override
    public ResourceLocation getType() {
        return UNIFORM;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<RandomValueRange>,
    JsonSerializer<RandomValueRange> {
        @Override
        public RandomValueRange deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (JSONUtils.isNumber(jsonElement)) {
                return new RandomValueRange(JSONUtils.getFloat(jsonElement, "value"));
            }
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "value");
            float f = JSONUtils.getFloat(jsonObject, "min");
            float f2 = JSONUtils.getFloat(jsonObject, "max");
            return new RandomValueRange(f, f2);
        }

        @Override
        public JsonElement serialize(RandomValueRange randomValueRange, Type type, JsonSerializationContext jsonSerializationContext) {
            if (randomValueRange.min == randomValueRange.max) {
                return new JsonPrimitive(Float.valueOf(randomValueRange.min));
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("min", Float.valueOf(randomValueRange.min));
            jsonObject.addProperty("max", Float.valueOf(randomValueRange.max));
            return jsonObject;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((RandomValueRange)object, type, jsonSerializationContext);
        }
    }
}

