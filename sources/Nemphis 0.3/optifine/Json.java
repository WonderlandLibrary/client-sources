/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
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
        JsonArray arr = jsonElement.getAsJsonArray();
        if (arr.size() != len) {
            throw new JsonParseException("Wrong array length: " + arr.size() + ", should be: " + len + ", array: " + (Object)arr);
        }
        float[] floatArr = new float[arr.size()];
        int i = 0;
        while (i < floatArr.length) {
            floatArr[i] = arr.get(i).getAsFloat();
            ++i;
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
        JsonArray arr = jsonElement.getAsJsonArray();
        if (arr.size() != len) {
            throw new JsonParseException("Wrong array length: " + arr.size() + ", should be: " + len + ", array: " + (Object)arr);
        }
        int[] intArr = new int[arr.size()];
        int i = 0;
        while (i < intArr.length) {
            intArr[i] = arr.get(i).getAsInt();
            ++i;
        }
        return intArr;
    }
}

