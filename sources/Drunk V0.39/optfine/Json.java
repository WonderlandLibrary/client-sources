/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class Json {
    public static float getFloat(JsonObject p_getFloat_0_, String p_getFloat_1_, float p_getFloat_2_) {
        float f;
        JsonElement jsonelement = p_getFloat_0_.get(p_getFloat_1_);
        if (jsonelement == null) {
            f = p_getFloat_2_;
            return f;
        }
        f = jsonelement.getAsFloat();
        return f;
    }

    public static boolean getBoolean(JsonObject p_getBoolean_0_, String p_getBoolean_1_, boolean p_getBoolean_2_) {
        boolean bl;
        JsonElement jsonelement = p_getBoolean_0_.get(p_getBoolean_1_);
        if (jsonelement == null) {
            bl = p_getBoolean_2_;
            return bl;
        }
        bl = jsonelement.getAsBoolean();
        return bl;
    }

    public static String getString(JsonObject p_getString_0_, String p_getString_1_) {
        return Json.getString(p_getString_0_, p_getString_1_, null);
    }

    public static String getString(JsonObject p_getString_0_, String p_getString_1_, String p_getString_2_) {
        String string;
        JsonElement jsonelement = p_getString_0_.get(p_getString_1_);
        if (jsonelement == null) {
            string = p_getString_2_;
            return string;
        }
        string = jsonelement.getAsString();
        return string;
    }

    public static float[] parseFloatArray(JsonElement p_parseFloatArray_0_, int p_parseFloatArray_1_) {
        return Json.parseFloatArray(p_parseFloatArray_0_, p_parseFloatArray_1_, null);
    }

    public static float[] parseFloatArray(JsonElement p_parseFloatArray_0_, int p_parseFloatArray_1_, float[] p_parseFloatArray_2_) {
        if (p_parseFloatArray_0_ == null) {
            return p_parseFloatArray_2_;
        }
        JsonArray jsonarray = p_parseFloatArray_0_.getAsJsonArray();
        if (jsonarray.size() != p_parseFloatArray_1_) {
            throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + p_parseFloatArray_1_ + ", array: " + jsonarray);
        }
        float[] afloat = new float[jsonarray.size()];
        int i = 0;
        while (i < afloat.length) {
            afloat[i] = jsonarray.get(i).getAsFloat();
            ++i;
        }
        return afloat;
    }

    public static int[] parseIntArray(JsonElement p_parseIntArray_0_, int p_parseIntArray_1_) {
        return Json.parseIntArray(p_parseIntArray_0_, p_parseIntArray_1_, null);
    }

    public static int[] parseIntArray(JsonElement p_parseIntArray_0_, int p_parseIntArray_1_, int[] p_parseIntArray_2_) {
        if (p_parseIntArray_0_ == null) {
            return p_parseIntArray_2_;
        }
        JsonArray jsonarray = p_parseIntArray_0_.getAsJsonArray();
        if (jsonarray.size() != p_parseIntArray_1_) {
            throw new JsonParseException("Wrong array length: " + jsonarray.size() + ", should be: " + p_parseIntArray_1_ + ", array: " + jsonarray);
        }
        int[] aint = new int[jsonarray.size()];
        int i = 0;
        while (i < aint.length) {
            aint[i] = jsonarray.get(i).getAsInt();
            ++i;
        }
        return aint;
    }
}

