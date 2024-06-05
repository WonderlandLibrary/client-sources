package net.optifine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.acE;
import net.optifine.MatchBlock;

public class Json {
   public static float getFloat(JsonObject var0, String var1, float var2) {
      MatchBlock.b();
      JsonElement var4 = var0.get(var1);
      return var4 == null?var2:var4.getAsFloat();
   }

   public static boolean getBoolean(JsonObject var0, String var1, boolean var2) {
      MatchBlock.b();
      JsonElement var4 = var0.get(var1);
      return var4 == null?var2:var4.getAsBoolean();
   }

   public static String getString(JsonObject var0, String var1) {
      return getString(var0, var1, (String)null);
   }

   public static String getString(JsonObject var0, String var1, String var2) {
      MatchBlock.b();
      JsonElement var4 = var0.get(var1);
      return var4 == null?var2:var4.getAsString();
   }

   public static float[] parseFloatArray(JsonElement var0, int var1) {
      return parseFloatArray(var0, var1, (float[])null);
   }

   public static float[] parseFloatArray(JsonElement var0, int var1, float[] var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 == null) {
         return var2;
      } else {
         JsonArray var4 = var0.getAsJsonArray();
         if(var4.size() != var1) {
            throw new JsonParseException("Wrong array length: " + var4.size() + ", should be: " + var1 + ", array: " + var4);
         } else {
            float[] var5 = new float[var4.size()];
            int var6 = 0;
            if(var6 < var5.length) {
               var5[var6] = var4.get(var6).getAsFloat();
               ++var6;
            }

            return var5;
         }
      }
   }

   public static int[] parseIntArray(JsonElement var0, int var1) {
      return parseIntArray(var0, var1, (int[])null);
   }

   public static int[] parseIntArray(JsonElement var0, int var1, int[] var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 == null) {
         return var2;
      } else {
         JsonArray var4 = var0.getAsJsonArray();
         if(var4.size() != var1) {
            throw new JsonParseException("Wrong array length: " + var4.size() + ", should be: " + var1 + ", array: " + var4);
         } else {
            int[] var5 = new int[var4.size()];
            int var6 = 0;
            if(var6 < var5.length) {
               var5[var6] = var4.get(var6).getAsInt();
               ++var6;
            }

            return var5;
         }
      }
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
