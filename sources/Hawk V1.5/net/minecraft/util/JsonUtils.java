package net.minecraft.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {
   private static final String __OBFID = "CL_00001484";

   public static boolean getJsonObjectBooleanFieldValue(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return getJsonElementBooleanValue(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Missing ")).append(var1).append(", expected to find a Boolean")));
      }
   }

   public static boolean getJsonObjectBooleanFieldValueOrDefault(JsonObject var0, String var1, boolean var2) {
      return var0.has(var1) ? getJsonElementBooleanValue(var0.get(var1), var1) : var2;
   }

   public static boolean jsonObjectHasNamedField(JsonObject var0, String var1) {
      return var0 == null ? false : var0.get(var1) != null;
   }

   public static String getJsonObjectStringFieldValueOrDefault(JsonObject var0, String var1, String var2) {
      return var0.has(var1) ? getJsonElementStringValue(var0.get(var1), var1) : var2;
   }

   public static String getJsonObjectStringFieldValue(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return getJsonElementStringValue(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Missing ")).append(var1).append(", expected to find a string")));
      }
   }

   public static boolean jsonObjectFieldTypeIsPrimitive(JsonObject var0, String var1) {
      return !jsonObjectHasNamedField(var0, var1) ? false : var0.get(var1).isJsonPrimitive();
   }

   public static String getJsonElementTypeDescription(JsonElement var0) {
      String var1 = org.apache.commons.lang3.StringUtils.abbreviateMiddle(String.valueOf(var0), "...", 10);
      if (var0 == null) {
         return "null (missing)";
      } else if (var0.isJsonNull()) {
         return "null (json)";
      } else if (var0.isJsonArray()) {
         return String.valueOf((new StringBuilder("an array (")).append(var1).append(")"));
      } else if (var0.isJsonObject()) {
         return String.valueOf((new StringBuilder("an object (")).append(var1).append(")"));
      } else {
         if (var0.isJsonPrimitive()) {
            JsonPrimitive var2 = var0.getAsJsonPrimitive();
            if (var2.isNumber()) {
               return String.valueOf((new StringBuilder("a number (")).append(var1).append(")"));
            }

            if (var2.isBoolean()) {
               return String.valueOf((new StringBuilder("a boolean (")).append(var1).append(")"));
            }
         }

         return var1;
      }
   }

   public static int getJsonObjectIntegerFieldValueOrDefault(JsonObject var0, String var1, int var2) {
      return var0.has(var1) ? getJsonElementIntegerValue(var0.get(var1), var1) : var2;
   }

   public static JsonObject getJsonObjectFieldOrDefault(JsonObject var0, String var1, JsonObject var2) {
      return var0.has(var1) ? getElementAsJsonObject(var0.get(var1), var1) : var2;
   }

   public static String getJsonElementStringValue(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive()) {
         return var0.getAsString();
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Expected ")).append(var1).append(" to be a string, was ").append(getJsonElementTypeDescription(var0))));
      }
   }

   public static JsonObject getJsonObject(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return getElementAsJsonObject(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Missing ")).append(var1).append(", expected to find a JsonObject")));
      }
   }

   public static float getJsonElementFloatValue(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsFloat();
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Expected ")).append(var1).append(" to be a Float, was ").append(getJsonElementTypeDescription(var0))));
      }
   }

   public static boolean getJsonElementBooleanValue(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive()) {
         return var0.getAsBoolean();
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Expected ")).append(var1).append(" to be a Boolean, was ").append(getJsonElementTypeDescription(var0))));
      }
   }

   public static JsonArray getJsonElementAsJsonArray(JsonElement var0, String var1) {
      if (var0.isJsonArray()) {
         return var0.getAsJsonArray();
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Expected ")).append(var1).append(" to be a JsonArray, was ").append(getJsonElementTypeDescription(var0))));
      }
   }

   public static int getJsonElementIntegerValue(JsonElement var0, String var1) {
      if (var0.isJsonPrimitive() && var0.getAsJsonPrimitive().isNumber()) {
         return var0.getAsInt();
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Expected ")).append(var1).append(" to be a Int, was ").append(getJsonElementTypeDescription(var0))));
      }
   }

   public static boolean func_180199_c(JsonObject var0, String var1) {
      return !jsonObjectFieldTypeIsPrimitive(var0, var1) ? false : var0.getAsJsonPrimitive(var1).isBoolean();
   }

   public static boolean jsonElementTypeIsString(JsonElement var0) {
      return !var0.isJsonPrimitive() ? false : var0.getAsJsonPrimitive().isString();
   }

   public static float getJsonObjectFloatFieldValue(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return getJsonElementFloatValue(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Missing ")).append(var1).append(", expected to find a Float")));
      }
   }

   public static JsonArray getJsonObjectJsonArrayFieldOrDefault(JsonObject var0, String var1, JsonArray var2) {
      return var0.has(var1) ? getJsonElementAsJsonArray(var0.get(var1), var1) : var2;
   }

   public static JsonObject getElementAsJsonObject(JsonElement var0, String var1) {
      if (var0.isJsonObject()) {
         return var0.getAsJsonObject();
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Expected ")).append(var1).append(" to be a JsonObject, was ").append(getJsonElementTypeDescription(var0))));
      }
   }

   public static int getJsonObjectIntegerFieldValue(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return getJsonElementIntegerValue(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Missing ")).append(var1).append(", expected to find a Int")));
      }
   }

   public static boolean jsonObjectFieldTypeIsArray(JsonObject var0, String var1) {
      return !jsonObjectHasNamedField(var0, var1) ? false : var0.get(var1).isJsonArray();
   }

   public static boolean jsonObjectFieldTypeIsString(JsonObject var0, String var1) {
      return !jsonObjectFieldTypeIsPrimitive(var0, var1) ? false : var0.getAsJsonPrimitive(var1).isString();
   }

   public static JsonArray getJsonObjectJsonArrayField(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         return getJsonElementAsJsonArray(var0.get(var1), var1);
      } else {
         throw new JsonSyntaxException(String.valueOf((new StringBuilder("Missing ")).append(var1).append(", expected to find a JsonArray")));
      }
   }

   public static float getJsonObjectFloatFieldValueOrDefault(JsonObject var0, String var1, float var2) {
      return var0.has(var1) ? getJsonElementFloatValue(var0.get(var1), var1) : var2;
   }
}
