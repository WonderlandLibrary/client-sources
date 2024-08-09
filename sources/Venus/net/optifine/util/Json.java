/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Json {
    public static float getFloat(JsonObject jsonObject, String string, float f) {
        JsonElement jsonElement = jsonObject.get(string);
        return jsonElement == null ? f : jsonElement.getAsFloat();
    }

    public static boolean getBoolean(JsonObject jsonObject, String string, boolean bl) {
        JsonElement jsonElement = jsonObject.get(string);
        return jsonElement == null ? bl : jsonElement.getAsBoolean();
    }

    public static String getString(JsonObject jsonObject, String string) {
        return Json.getString(jsonObject, string, null);
    }

    public static String getString(JsonObject jsonObject, String string, String string2) {
        JsonElement jsonElement = jsonObject.get(string);
        return jsonElement == null ? string2 : jsonElement.getAsString();
    }

    public static float[] parseFloatArray(JsonElement jsonElement, int n) {
        return Json.parseFloatArray(jsonElement, n, null);
    }

    public static float[] parseFloatArray(JsonElement jsonElement, int n, float[] fArray) {
        if (jsonElement == null) {
            return fArray;
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if (jsonArray.size() != n) {
            throw new JsonParseException("Wrong array length: " + jsonArray.size() + ", should be: " + n + ", array: " + jsonArray);
        }
        float[] fArray2 = new float[jsonArray.size()];
        for (int i = 0; i < fArray2.length; ++i) {
            fArray2[i] = jsonArray.get(i).getAsFloat();
        }
        return fArray2;
    }

    public static int[] parseIntArray(JsonElement jsonElement, int n) {
        return Json.parseIntArray(jsonElement, n, null);
    }

    public static int[] parseIntArray(JsonElement jsonElement, int n, int[] nArray) {
        if (jsonElement == null) {
            return nArray;
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        if (jsonArray.size() != n) {
            throw new JsonParseException("Wrong array length: " + jsonArray.size() + ", should be: " + n + ", array: " + jsonArray);
        }
        int[] nArray2 = new int[jsonArray.size()];
        for (int i = 0; i < nArray2.length; ++i) {
            nArray2[i] = jsonArray.get(i).getAsInt();
        }
        return nArray2;
    }
}

