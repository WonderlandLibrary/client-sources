/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.loot.IRandomRange;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public final class BinomialRange
implements IRandomRange {
    private final int n;
    private final float p;

    public BinomialRange(int n, float f) {
        this.n = n;
        this.p = f;
    }

    @Override
    public int generateInt(Random random2) {
        int n = 0;
        for (int i = 0; i < this.n; ++i) {
            if (!(random2.nextFloat() < this.p)) continue;
            ++n;
        }
        return n;
    }

    public static BinomialRange of(int n, float f) {
        return new BinomialRange(n, f);
    }

    @Override
    public ResourceLocation getType() {
        return BINOMIAL;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<BinomialRange>,
    JsonSerializer<BinomialRange> {
        @Override
        public BinomialRange deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, "value");
            int n = JSONUtils.getInt(jsonObject, "n");
            float f = JSONUtils.getFloat(jsonObject, "p");
            return new BinomialRange(n, f);
        }

        @Override
        public JsonElement serialize(BinomialRange binomialRange, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("n", binomialRange.n);
            jsonObject.addProperty("p", Float.valueOf(binomialRange.p));
            return jsonObject;
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((BinomialRange)object, type, jsonSerializationContext);
        }
    }
}

