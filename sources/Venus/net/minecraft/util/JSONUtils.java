/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;

public class JSONUtils {
    private static final Gson GSON = new GsonBuilder().create();

    public static boolean isString(JsonObject jsonObject, String string) {
        return !JSONUtils.isJsonPrimitive(jsonObject, string) ? false : jsonObject.getAsJsonPrimitive(string).isString();
    }

    public static boolean isString(JsonElement jsonElement) {
        return !jsonElement.isJsonPrimitive() ? false : jsonElement.getAsJsonPrimitive().isString();
    }

    public static boolean isNumber(JsonElement jsonElement) {
        return !jsonElement.isJsonPrimitive() ? false : jsonElement.getAsJsonPrimitive().isNumber();
    }

    public static boolean isBoolean(JsonObject jsonObject, String string) {
        return !JSONUtils.isJsonPrimitive(jsonObject, string) ? false : jsonObject.getAsJsonPrimitive(string).isBoolean();
    }

    public static boolean isJsonArray(JsonObject jsonObject, String string) {
        return !JSONUtils.hasField(jsonObject, string) ? false : jsonObject.get(string).isJsonArray();
    }

    public static boolean isJsonPrimitive(JsonObject jsonObject, String string) {
        return !JSONUtils.hasField(jsonObject, string) ? false : jsonObject.get(string).isJsonPrimitive();
    }

    public static boolean hasField(JsonObject jsonObject, String string) {
        if (jsonObject == null) {
            return true;
        }
        return jsonObject.get(string) != null;
    }

    public static String getString(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsString();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a string, was " + JSONUtils.toString(jsonElement));
    }

    public static String getString(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return JSONUtils.getString(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a string");
    }

    public static String getString(JsonObject jsonObject, String string, String string2) {
        return jsonObject.has(string) ? JSONUtils.getString(jsonObject.get(string), string) : string2;
    }

    public static Item getItem(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive()) {
            String string2 = jsonElement.getAsString();
            return Registry.ITEM.getOptional(new ResourceLocation(string2)).orElseThrow(() -> JSONUtils.lambda$getItem$0(string, string2));
        }
        throw new JsonSyntaxException("Expected " + string + " to be an item, was " + JSONUtils.toString(jsonElement));
    }

    public static Item getItem(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return JSONUtils.getItem(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find an item");
    }

    public static boolean getBoolean(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive()) {
            return jsonElement.getAsBoolean();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Boolean, was " + JSONUtils.toString(jsonElement));
    }

    public static boolean getBoolean(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return JSONUtils.getBoolean(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a Boolean");
    }

    public static boolean getBoolean(JsonObject jsonObject, String string, boolean bl) {
        return jsonObject.has(string) ? JSONUtils.getBoolean(jsonObject.get(string), string) : bl;
    }

    public static float getFloat(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsFloat();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Float, was " + JSONUtils.toString(jsonElement));
    }

    public static float getFloat(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return JSONUtils.getFloat(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a Float");
    }

    public static float getFloat(JsonObject jsonObject, String string, float f) {
        return jsonObject.has(string) ? JSONUtils.getFloat(jsonObject.get(string), string) : f;
    }

    public static long getLong(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsLong();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Long, was " + JSONUtils.toString(jsonElement));
    }

    public static long getLong(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return JSONUtils.getLong(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a Long");
    }

    public static long getLong(JsonObject jsonObject, String string, long l) {
        return jsonObject.has(string) ? JSONUtils.getLong(jsonObject.get(string), string) : l;
    }

    public static int getInt(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsInt();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Int, was " + JSONUtils.toString(jsonElement));
    }

    public static int getInt(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return JSONUtils.getInt(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a Int");
    }

    public static int getInt(JsonObject jsonObject, String string, int n) {
        return jsonObject.has(string) ? JSONUtils.getInt(jsonObject.get(string), string) : n;
    }

    public static byte getByte(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isNumber()) {
            return jsonElement.getAsByte();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a Byte, was " + JSONUtils.toString(jsonElement));
    }

    public static byte getByte(JsonObject jsonObject, String string, byte by) {
        return jsonObject.has(string) ? JSONUtils.getByte(jsonObject.get(string), string) : by;
    }

    public static JsonObject getJsonObject(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonObject()) {
            return jsonElement.getAsJsonObject();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a JsonObject, was " + JSONUtils.toString(jsonElement));
    }

    public static JsonObject getJsonObject(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return JSONUtils.getJsonObject(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a JsonObject");
    }

    public static JsonObject getJsonObject(JsonObject jsonObject, String string, JsonObject jsonObject2) {
        return jsonObject.has(string) ? JSONUtils.getJsonObject(jsonObject.get(string), string) : jsonObject2;
    }

    public static JsonArray getJsonArray(JsonElement jsonElement, String string) {
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        }
        throw new JsonSyntaxException("Expected " + string + " to be a JsonArray, was " + JSONUtils.toString(jsonElement));
    }

    public static JsonArray getJsonArray(JsonObject jsonObject, String string) {
        if (jsonObject.has(string)) {
            return JSONUtils.getJsonArray(jsonObject.get(string), string);
        }
        throw new JsonSyntaxException("Missing " + string + ", expected to find a JsonArray");
    }

    @Nullable
    public static JsonArray getJsonArray(JsonObject jsonObject, String string, @Nullable JsonArray jsonArray) {
        return jsonObject.has(string) ? JSONUtils.getJsonArray(jsonObject.get(string), string) : jsonArray;
    }

    public static <T> T deserializeClass(@Nullable JsonElement jsonElement, String string, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> clazz) {
        if (jsonElement != null) {
            return jsonDeserializationContext.deserialize(jsonElement, clazz);
        }
        throw new JsonSyntaxException("Missing " + string);
    }

    public static <T> T deserializeClass(JsonObject jsonObject, String string, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> clazz) {
        if (jsonObject.has(string)) {
            return JSONUtils.deserializeClass(jsonObject.get(string), string, jsonDeserializationContext, clazz);
        }
        throw new JsonSyntaxException("Missing " + string);
    }

    public static <T> T deserializeClass(JsonObject jsonObject, String string, T t, JsonDeserializationContext jsonDeserializationContext, Class<? extends T> clazz) {
        return jsonObject.has(string) ? JSONUtils.deserializeClass(jsonObject.get(string), string, jsonDeserializationContext, clazz) : t;
    }

    public static String toString(JsonElement jsonElement) {
        String string = StringUtils.abbreviateMiddle(String.valueOf(jsonElement), "...", 10);
        if (jsonElement == null) {
            return "null (missing)";
        }
        if (jsonElement.isJsonNull()) {
            return "null (json)";
        }
        if (jsonElement.isJsonArray()) {
            return "an array (" + string + ")";
        }
        if (jsonElement.isJsonObject()) {
            return "an object (" + string + ")";
        }
        if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber()) {
                return "a number (" + string + ")";
            }
            if (jsonPrimitive.isBoolean()) {
                return "a boolean (" + string + ")";
            }
        }
        return string;
    }

    @Nullable
    public static <T> T fromJson(Gson gson, Reader reader, Class<T> clazz, boolean bl) {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(bl);
            return gson.getAdapter(clazz).read(jsonReader);
        } catch (IOException iOException) {
            throw new JsonParseException(iOException);
        }
    }

    @Nullable
    public static <T> T fromJSON(Gson gson, Reader reader, TypeToken<T> typeToken, boolean bl) {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(bl);
            return gson.getAdapter(typeToken).read(jsonReader);
        } catch (IOException iOException) {
            throw new JsonParseException(iOException);
        }
    }

    @Nullable
    public static <T> T fromJSON(Gson gson, String string, TypeToken<T> typeToken, boolean bl) {
        return JSONUtils.fromJSON(gson, new StringReader(string), typeToken, bl);
    }

    @Nullable
    public static <T> T fromJson(Gson gson, String string, Class<T> clazz, boolean bl) {
        return JSONUtils.fromJson(gson, new StringReader(string), clazz, bl);
    }

    @Nullable
    public static <T> T fromJSONUnlenient(Gson gson, Reader reader, TypeToken<T> typeToken) {
        return JSONUtils.fromJSON(gson, reader, typeToken, false);
    }

    @Nullable
    public static <T> T fromJSONUnlenient(Gson gson, String string, TypeToken<T> typeToken) {
        return JSONUtils.fromJSON(gson, string, typeToken, false);
    }

    @Nullable
    public static <T> T fromJson(Gson gson, Reader reader, Class<T> clazz) {
        return JSONUtils.fromJson(gson, reader, clazz, false);
    }

    @Nullable
    public static <T> T fromJson(Gson gson, String string, Class<T> clazz) {
        return JSONUtils.fromJson(gson, string, clazz, false);
    }

    public static JsonObject fromJson(String string, boolean bl) {
        return JSONUtils.fromJson(new StringReader(string), bl);
    }

    public static JsonObject fromJson(Reader reader, boolean bl) {
        return JSONUtils.fromJson(GSON, reader, JsonObject.class, bl);
    }

    public static JsonObject fromJson(String string) {
        return JSONUtils.fromJson(string, false);
    }

    public static JsonObject fromJson(Reader reader) {
        return JSONUtils.fromJson(reader, false);
    }

    private static JsonSyntaxException lambda$getItem$0(String string, String string2) {
        return new JsonSyntaxException("Expected " + string + " to be an item, was unknown string '" + string2 + "'");
    }
}

