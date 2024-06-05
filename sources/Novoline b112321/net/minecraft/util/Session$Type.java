package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Map;

public enum Session$Type {
   LEGACY("legacy"),
   MOJANG("mojang");

   private static final Map SESSION_TYPES = Maps.newHashMap();
   private final String sessionType;
   private static final Session$Type[] $VALUES = new Session$Type[]{LEGACY, MOJANG};

   private Session$Type(String var3) {
      this.sessionType = var3;
   }

   public static Session$Type setSessionType(String var0) {
      return (Session$Type)SESSION_TYPES.get(var0.toLowerCase());
   }

   static {
      for(Session$Type var11 : values()) {
         SESSION_TYPES.put(var11.sessionType, var11);
      }

   }
}
