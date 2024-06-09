/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Json {
    public static float getFloat(JsonObject obj, String field, float def) {
        JsonElement elem = obj.get(field);
        return elem == null ? def : elem.getAsFloat();
    }

    public static boolean getBoolean(JsonObject obj, String field, boolean def) {
        JsonElement elem = obj.get(field);
        return elem == null ? def : elem.getAsBoolean();
    }

    public static String getString(JsonObject jsonObj, String field) {
        return Json.getString(jsonObj, field, null);
    }

    public static String getString(JsonObject jsonObj, String field, String def) {
        JsonElement jsonElement = jsonObj.get(field);
        return jsonElement == null ? def : jsonElement.getAsString();
    }

    public static float[] parseFloatArray(JsonElement jsonElement, int len) {
        return Json.parseFloatArray(jsonElement, len, null);
    }

    public static float[] parseFloatArray(JsonElement jsonElement, int len, float[] def) {
        if (jsonElement == null) {
            return def;
        }
        JsonArray arr2 = jsonElement.getAsJsonArray();
        if (arr2.size() != len) {
            throw new JsonParseException("Wrong array length: " + arr2.size() + ", should be: " + len + ", array: " + arr2);
        }
        float[] floatArr = new float[arr2.size()];
        for (int i2 = 0; i2 < floatArr.length; ++i2) {
            floatArr[i2] = arr2.get(i2).getAsFloat();
        }
        return floatArr;
    }

    public static int[] parseIntArray(JsonElement jsonElement, int len) {
        return Json.parseIntArray(jsonElement, len, null);
    }

    public static int[] parseIntArray(JsonElement jsonElement, int len, int[] def) {
        if (jsonElement == null) {
            return def;
        }
        JsonArray arr2 = jsonElement.getAsJsonArray();
        if (arr2.size() != len) {
            throw new JsonParseException("Wrong array length: " + arr2.size() + ", should be: " + len + ", array: " + arr2);
        }
        int[] intArr = new int[arr2.size()];
        for (int i2 = 0; i2 < intArr.length; ++i2) {
            intArr[i2] = arr2.get(i2).getAsInt();
        }
        return intArr;
    }
}

