package com.viaversion.viaversion.bukkit.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.Player;

public final class ProtocolSupportUtil {
   private static final Method PROTOCOL_VERSION_METHOD;
   private static final Method GET_ID_METHOD;

   public static int getProtocolVersion(Player player) {
      if (PROTOCOL_VERSION_METHOD == null) {
         return -1;
      } else {
         try {
            Object version = PROTOCOL_VERSION_METHOD.invoke(null, player);
            return (Integer)GET_ID_METHOD.invoke(version);
         } catch (InvocationTargetException | IllegalAccessException var2) {
            var2.printStackTrace();
            return -1;
         }
      }
   }

   static {
      Method protocolVersionMethod = null;
      Method getIdMethod = null;

      try {
         protocolVersionMethod = Class.forName("protocolsupport.api.ProtocolSupportAPI").getMethod("getProtocolVersion", Player.class);
         getIdMethod = Class.forName("protocolsupport.api.ProtocolVersion").getMethod("getId");
      } catch (ReflectiveOperationException var3) {
      }

      PROTOCOL_VERSION_METHOD = protocolVersionMethod;
      GET_ID_METHOD = getIdMethod;
   }
}
