package com.viaversion.viaversion.util;

public final class Key {
   public static String stripNamespace(String identifier) {
      int index = identifier.indexOf(58);
      return index == -1 ? identifier : identifier.substring(index + 1);
   }

   public static String stripMinecraftNamespace(String identifier) {
      if (identifier.startsWith("minecraft:")) {
         return identifier.substring(10);
      } else {
         return identifier.startsWith(":") ? identifier.substring(1) : identifier;
      }
   }

   public static String namespaced(String identifier) {
      int index = identifier.indexOf(58);
      if (index == -1) {
         return "minecraft:" + identifier;
      } else {
         return index == 0 ? "minecraft" + identifier : identifier;
      }
   }

   public static boolean isValid(String identifier) {
      return identifier.matches("([0-9a-z_.-]*:)?[0-9a-z_/.-]*");
   }
}
