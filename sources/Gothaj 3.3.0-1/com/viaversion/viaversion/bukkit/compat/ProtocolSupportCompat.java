package com.viaversion.viaversion.bukkit.compat;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class ProtocolSupportCompat {
   public static void registerPSConnectListener(ViaVersionPlugin plugin) {
      Via.getPlatform().getLogger().info("Registering ProtocolSupport compat connection listener");

      try {
         Class<? extends Event> connectionOpenEvent = (Class<? extends Event>)Class.forName("protocolsupport.api.events.ConnectionOpenEvent");
         Bukkit.getPluginManager().registerEvent(connectionOpenEvent, new Listener() {
         }, EventPriority.HIGH, (listener, event) -> {
            try {
               Object connection = event.getClass().getMethod("getConnection").invoke(event);
               ProtocolSupportConnectionListener connectListener = new ProtocolSupportConnectionListener(connection);
               ProtocolSupportConnectionListener.ADD_PACKET_LISTENER_METHOD.invoke(connection, connectListener);
            } catch (ReflectiveOperationException var4) {
               Via.getPlatform().getLogger().log(Level.WARNING, "Error when handling ProtocolSupport event", (Throwable)var4);
            }
         }, plugin);
      } catch (ClassNotFoundException var2) {
         Via.getPlatform().getLogger().log(Level.WARNING, "Unable to register ProtocolSupport listener", (Throwable)var2);
      }
   }

   public static boolean isMultiplatformPS() {
      try {
         Class.forName("protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketEncoder");
         return true;
      } catch (ClassNotFoundException var1) {
         return false;
      }
   }

   static ProtocolSupportCompat.HandshakeProtocolType handshakeVersionMethod() {
      Class<?> clazz = null;

      try {
         clazz = NMSUtil.nms("PacketHandshakingInSetProtocol", "net.minecraft.network.protocol.handshake.PacketHandshakingInSetProtocol");
         clazz.getMethod("getProtocolVersion");
         return ProtocolSupportCompat.HandshakeProtocolType.MAPPED;
      } catch (ClassNotFoundException var3) {
         throw new RuntimeException(var3);
      } catch (NoSuchMethodException var4) {
         try {
            if (clazz.getMethod("b").getReturnType() == int.class) {
               return ProtocolSupportCompat.HandshakeProtocolType.OBFUSCATED_B;
            } else if (clazz.getMethod("c").getReturnType() == int.class) {
               return ProtocolSupportCompat.HandshakeProtocolType.OBFUSCATED_C;
            } else {
               throw new UnsupportedOperationException("Protocol version method not found in " + clazz.getSimpleName());
            }
         } catch (ReflectiveOperationException var2) {
            throw new RuntimeException(var2);
         }
      }
   }

   static enum HandshakeProtocolType {
      MAPPED("getProtocolVersion"),
      OBFUSCATED_B("b"),
      OBFUSCATED_C("c");

      private final String methodName;

      private HandshakeProtocolType(String methodName) {
         this.methodName = methodName;
      }

      public String methodName() {
         return this.methodName;
      }
   }
}
