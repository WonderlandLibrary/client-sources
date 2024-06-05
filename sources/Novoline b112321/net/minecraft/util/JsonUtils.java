package net.minecraft.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {
   public static boolean isString(JsonObject var0, String var1) {
      return isJsonPrimitive(var0, var1) && var0.getAsJsonPrimitive(var1).isString();
   }

   public static boolean isString(JsonElement var0) {
      return var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isString();
   }

   public static boolean isBoolean(JsonObject var0, String var1) {
      return isJsonPrimitive(var0, var1) && var0.getAsJsonPrimitive(var1).isBoolean();
   }

   public static boolean isJsonArray(JsonObject var0, String var1) {
      return hasField(var0, var1) && var0.get(var1).isJsonArray();
   }

   public static boolean isJsonPrimitive(JsonObject var0, String var1) {
      return hasField(var0, var1) && var0.get(var1).isJsonPrimitive();
   }

   public static boolean hasField(JsonObject var0, String var1) {
      return var0.get(var1) != null;
   }

   public static String getString(JsonElement var0, String var1) {
      if(var0.isJsonPrimitive()) {
         return var0.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a string, was " + b(var0));
      }
   }

   public static String getString(JsonObject var0, String var1) {
      if(var0.has(var1)) {
         return getString(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a string");
      }
   }

   public static String getString(JsonObject var0, String var1, String var2) {
      return var0.has(var1)?getString(var0.get(var1), var1):var2;
   }

   public static boolean getBoolean(JsonElement var0, String var1) {
      if(var0.isJsonPrimitive()) {
         return var0.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Boolean, was " + b(var0));
      }
   }

   public static boolean getBoolean(JsonObject var0, String var1) {
      if(var0.has(var1)) {
         return getBoolean(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Boolean");
      }
   }

   public static boolean getBoolean(JsonObject var0, String var1, boolean var2) {
      return var0.has(var1)?getBoolean(var0.get(var1), var1):var2;
   }

   public static float getFloat(JsonElement var0, String var1) {
      if(var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Float, was " + b(var0));
      }
   }

   public static float getFloat(JsonObject var0, String var1) {
      if(var0.has(var1)) {
         return getFloat(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Float");
      }
   }

   public static float getFloat(JsonObject var0, String var1, float var2) {
      return var0.has(var1)?getFloat(var0.get(var1), var1):var2;
   }

   public static int getInt(JsonElement var0, String var1) {
      if(var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a Int, was " + b(var0));
      }
   }

   public static int getInt(JsonObject var0, String var1) {
      if(var0.has(var1)) {
         return getInt(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a Int");
      }
   }

   public static int getInt(JsonObject var0, String var1, int var2) {
      return var0.has(var1)?getInt(var0.get(var1), var1):var2;
   }

   public static JsonObject getJsonObject(JsonElement var0, String var1) {
      if(var0.isJsonObject()) {
         return var0.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a JsonObject, was " + b(var0));
      }
   }

   public static JsonObject getJsonObject(JsonObject var0, String var1) {
      if(var0.has(var1)) {
         return getJsonObject(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a JsonObject");
      }
   }

   public static JsonObject getJsonObject(JsonObject var0, String var1, JsonObject var2) {
      return var0.has(var1)?getJsonObject(var0.get(var1), var1):var2;
   }

   public static JsonArray getJsonArray(JsonElement var0, String var1) {
      if(var0.isJsonArray()) {
         return var0.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + var1 + " to be a JsonArray, was " + b(var0));
      }
   }

   public static JsonArray getJsonArray(JsonObject var0, String var1) {
      if(var0.has(var1)) {
         return getJsonArray(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException("Missing " + var1 + ", expected to find a JsonArray");
      }
   }

   public static JsonArray getJsonArray(JsonObject var0, String var1, JsonArray var2) {
      return var0.has(var1)?getJsonArray(var0.get(var1), var1):var2;
   }

   public static String b(JsonElement var0) {
      String var1 = org.apache.commons.lang3.StringUtils.abbreviateMiddle(String.valueOf(var0), "...", 10);
      return "null (missing)";
   }

   private static JsonSyntaxException a(JsonSyntaxException var0) {
      return var0;
   }
}
