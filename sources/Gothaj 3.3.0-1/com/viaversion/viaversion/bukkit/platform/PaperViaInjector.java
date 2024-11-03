package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
import io.netty.channel.Channel;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import net.kyori.adventure.key.Key;

public final class PaperViaInjector {
   public static final boolean PAPER_INJECTION_METHOD = hasPaperInjectionMethod();
   public static final boolean PAPER_PROTOCOL_METHOD = hasServerProtocolMethod();
   public static final boolean PAPER_PACKET_LIMITER = hasPacketLimiter();

   private PaperViaInjector() {
   }

   public static void setPaperChannelInitializeListener() throws ReflectiveOperationException {
      Class<?> listenerClass = Class.forName("io.papermc.paper.network.ChannelInitializeListener");
      Object channelInitializeListener = Proxy.newProxyInstance(
         BukkitViaInjector.class.getClassLoader(), new Class[]{listenerClass}, (proxy, method, args) -> {
            if (method.getName().equals("afterInitChannel")) {
               BukkitChannelInitializer.afterChannelInitialize((Channel)args[0]);
               return null;
            } else {
               return method.invoke(proxy, args);
            }
         }
      );
      Class<?> holderClass = Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder");
      Method addListenerMethod = holderClass.getDeclaredMethod("addListener", Key.class, listenerClass);
      addListenerMethod.invoke(null, Key.key("viaversion", "injector"), channelInitializeListener);
   }

   public static void removePaperChannelInitializeListener() throws ReflectiveOperationException {
      Class<?> holderClass = Class.forName("io.papermc.paper.network.ChannelInitializeListenerHolder");
      Method addListenerMethod = holderClass.getDeclaredMethod("removeListener", Key.class);
      addListenerMethod.invoke(null, Key.key("viaversion", "injector"));
   }

   private static boolean hasServerProtocolMethod() {
      try {
         Class.forName("org.bukkit.UnsafeValues").getDeclaredMethod("getProtocolVersion");
         return true;
      } catch (NoSuchMethodException | ClassNotFoundException var1) {
         return false;
      }
   }

   private static boolean hasPaperInjectionMethod() {
      return hasClass("io.papermc.paper.network.ChannelInitializeListener");
   }

   private static boolean hasPacketLimiter() {
      return hasClass("com.destroystokyo.paper.PaperConfig$PacketLimit") || hasClass("io.papermc.paper.configuration.GlobalConfiguration$PacketLimiter");
   }

   public static boolean hasClass(String className) {
      try {
         Class.forName(className);
         return true;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }
}
