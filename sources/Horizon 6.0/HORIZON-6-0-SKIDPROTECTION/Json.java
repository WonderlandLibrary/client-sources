package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Json
{
    public static float HorizonCode_Horizon_È(final JsonObject obj, final String field, final float def) {
        final JsonElement elem = obj.get(field);
        return (elem == null) ? def : elem.getAsFloat();
    }
    
    public static boolean HorizonCode_Horizon_È(final JsonObject obj, final String field, final boolean def) {
        final JsonElement elem = obj.get(field);
        return (elem == null) ? def : elem.getAsBoolean();
    }
    
    public static String HorizonCode_Horizon_È(final JsonObject jsonObj, final String field) {
        return HorizonCode_Horizon_È(jsonObj, field, null);
    }
    
    public static String HorizonCode_Horizon_È(final JsonObject jsonObj, final String field, final String def) {
        final JsonElement jsonElement = jsonObj.get(field);
        return (jsonElement == null) ? def : jsonElement.getAsString();
    }
    
    public static float[] HorizonCode_Horizon_È(final JsonElement jsonElement, final int len) {
        return HorizonCode_Horizon_È(jsonElement, len, (float[])null);
    }
    
    public static float[] HorizonCode_Horizon_È(final JsonElement jsonElement, final int len, final float[] def) {
        if (jsonElement == null) {
            return def;
        }
        final JsonArray arr = jsonElement.getAsJsonArray();
        if (arr.size() != len) {
            throw new JsonParseException("Wrong array length: " + arr.size() + ", should be: " + len + ", array: " + arr);
        }
        final float[] floatArr = new float[arr.size()];
        for (int i = 0; i < floatArr.length; ++i) {
            floatArr[i] = arr.get(i).getAsFloat();
        }
        return floatArr;
    }
    
    public static int[] Â(final JsonElement jsonElement, final int len) {
        return HorizonCode_Horizon_È(jsonElement, len, (int[])null);
    }
    
    public static int[] HorizonCode_Horizon_È(final JsonElement jsonElement, final int len, final int[] def) {
        if (jsonElement == null) {
            return def;
        }
        final JsonArray arr = jsonElement.getAsJsonArray();
        if (arr.size() != len) {
            throw new JsonParseException("Wrong array length: " + arr.size() + ", should be: " + len + ", array: " + arr);
        }
        final int[] intArr = new int[arr.size()];
        for (int i = 0; i < intArr.length; ++i) {
            intArr[i] = arr.get(i).getAsInt();
        }
        return intArr;
    }
}
