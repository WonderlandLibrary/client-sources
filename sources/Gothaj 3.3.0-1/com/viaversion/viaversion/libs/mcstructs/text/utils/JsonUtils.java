package com.viaversion.viaversion.libs.mcstructs.text.utils;

import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JsonUtils {
   public static boolean getBoolean(JsonElement element, String key) {
      if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean()) {
         return element.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + key + " to be a boolean, was " + element);
      }
   }

   public static boolean getBoolean(JsonObject object, String key) {
      if (object.has(key)) {
         return getBoolean(object.get(key), key);
      } else {
         throw new JsonSyntaxException("Missing " + key + ", expected to find a boolean");
      }
   }

   public static boolean getBoolean(JsonObject object, String key, boolean fallback) {
      return object.has(key) ? getBoolean(object, key) : fallback;
   }

   public static int getInt(JsonElement element, String key) {
      if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
         return element.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + key + " to be a boolean, was " + element);
      }
   }

   public static int getInt(JsonObject object, String key) {
      if (object.has(key)) {
         return getInt(object.get(key), key);
      } else {
         throw new JsonSyntaxException("Missing " + key + ", expected to find a boolean");
      }
   }

   public static int getInt(JsonObject object, String key, int fallback) {
      return object.has(key) ? getInt(object, key) : fallback;
   }

   public static String getString(JsonElement element, String key) {
      if (element.isJsonPrimitive()) {
         return element.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + key + " to be a string, was " + element);
      }
   }

   public static String getString(JsonObject object, String key) {
      if (object.has(key)) {
         return getString(object.get(key), key);
      } else {
         throw new JsonSyntaxException("Missing " + key + ", expected to find a string");
      }
   }

   public static String getString(JsonObject object, String key, String fallback) {
      return object.has(key) ? getString(object, key) : fallback;
   }

   public static JsonObject getJsonObject(JsonElement element, String key) {
      if (element.isJsonObject()) {
         return element.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + key + " to be a JsonObject, was " + element);
      }
   }

   public static JsonObject getJsonObject(JsonObject object, String key) {
      if (object.has(key)) {
         return getJsonObject(object.get(key), key);
      } else {
         throw new JsonSyntaxException("Missing " + key + ", expected to find a JsonObject");
      }
   }

   public static String toSortedString(@Nullable JsonElement element, @Nullable Comparator<String> comparator) {
      if (element == null) {
         return null;
      } else {
         return comparator != null ? sort(element, comparator).toString() : sort(element, Comparator.naturalOrder()).toString();
      }
   }

   public static JsonElement sort(@Nullable JsonElement element, @Nonnull Comparator<String> comparator) {
      if (element == null) {
         return null;
      } else if (element.isJsonArray()) {
         JsonArray array = element.getAsJsonArray();

         for (int i = 0; i < array.size(); i++) {
            array.set(i, sort(array.get(i), comparator));
         }

         return array;
      } else if (!element.isJsonObject()) {
         return element;
      } else {
         JsonObject object = element.getAsJsonObject();
         JsonObject sorted = new JsonObject();
         List<String> keys = new ArrayList<>(object.keySet());
         keys.sort(comparator);

         for (String key : keys) {
            sorted.add(key, sort(object.get(key), comparator));
         }

         return sorted;
      }
   }
}
