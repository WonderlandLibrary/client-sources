package com.viaversion.viaversion.bukkit.util;

import org.bukkit.Bukkit;

public final class NMSUtil {
   private static final String BASE = Bukkit.getServer().getClass().getPackage().getName();
   private static final String NMS = BASE.replace("org.bukkit.craftbukkit", "net.minecraft.server");
   private static final boolean DEBUG_PROPERTY = loadDebugProperty();

   private static boolean loadDebugProperty() {
      try {
         Class<?> serverClass = nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
         Object server = serverClass.getDeclaredMethod("getServer").invoke(null);
         return (Boolean)serverClass.getMethod("isDebugging").invoke(server);
      } catch (ReflectiveOperationException var2) {
         return false;
      }
   }

   public static Class<?> nms(String className) throws ClassNotFoundException {
      return Class.forName(NMS + "." + className);
   }

   public static Class<?> nms(String className, String fallbackFullClassName) throws ClassNotFoundException {
      try {
         return Class.forName(NMS + "." + className);
      } catch (ClassNotFoundException var3) {
         return Class.forName(fallbackFullClassName);
      }
   }

   public static Class<?> obc(String className) throws ClassNotFoundException {
      return Class.forName(BASE + "." + className);
   }

   public static boolean isDebugPropertySet() {
      return DEBUG_PROPERTY;
   }
}
