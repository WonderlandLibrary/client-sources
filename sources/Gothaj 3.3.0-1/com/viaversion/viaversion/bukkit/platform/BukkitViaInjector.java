package com.viaversion.viaversion.bukkit.platform;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.bukkit.handlers.BukkitChannelInitializer;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.platform.LegacyViaInjector;
import com.viaversion.viaversion.platform.WrappedChannelInitializer;
import com.viaversion.viaversion.util.ReflectionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BukkitViaInjector extends LegacyViaInjector {
   private static final boolean HAS_WORLD_VERSION_PROTOCOL_VERSION = PaperViaInjector.hasClass("net.minecraft.SharedConstants")
      && PaperViaInjector.hasClass("net.minecraft.WorldVersion")
      && !PaperViaInjector.hasClass("com.mojang.bridge.game.GameVersion");

   @Override
   public void inject() throws ReflectiveOperationException {
      if (PaperViaInjector.PAPER_INJECTION_METHOD) {
         PaperViaInjector.setPaperChannelInitializeListener();
      } else {
         super.inject();
      }
   }

   @Override
   public void uninject() throws ReflectiveOperationException {
      if (PaperViaInjector.PAPER_INJECTION_METHOD) {
         PaperViaInjector.removePaperChannelInitializeListener();
      } else {
         super.uninject();
      }
   }

   @Override
   public int getServerProtocolVersion() throws ReflectiveOperationException {
      if (PaperViaInjector.PAPER_PROTOCOL_METHOD) {
         return Bukkit.getUnsafe().getProtocolVersion();
      } else {
         return HAS_WORLD_VERSION_PROTOCOL_VERSION ? this.cursedProtocolDetection() : this.veryCursedProtocolDetection();
      }
   }

   private int cursedProtocolDetection() throws ReflectiveOperationException {
      Class<?> sharedConstantsClass = Class.forName("net.minecraft.SharedConstants");
      Class<?> worldVersionClass = Class.forName("net.minecraft.WorldVersion");
      Method getWorldVersionMethod = null;

      for (Method method : sharedConstantsClass.getDeclaredMethods()) {
         if (method.getReturnType() == worldVersionClass && method.getParameterTypes().length == 0) {
            getWorldVersionMethod = method;
            break;
         }
      }

      Preconditions.checkNotNull(getWorldVersionMethod, "Failed to get world version method");
      Object worldVersion = getWorldVersionMethod.invoke(null);

      for (Method methodx : worldVersionClass.getDeclaredMethods()) {
         if (methodx.getReturnType() == int.class && methodx.getParameterTypes().length == 0) {
            return (Integer)methodx.invoke(worldVersion);
         }
      }

      throw new IllegalAccessException("Failed to find protocol version method in WorldVersion");
   }

   private int veryCursedProtocolDetection() throws ReflectiveOperationException {
      Class<?> serverClazz = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
      Object server = ReflectionUtil.invokeStatic(serverClazz, "getServer");
      Preconditions.checkNotNull(server, "Failed to get server instance");
      Class<?> pingClazz = NMSUtil.nms("ServerPing", "net.minecraft.network.protocol.status.ServerPing");
      Object ping = null;

      for (Field field : serverClazz.getDeclaredFields()) {
         if (field.getType() == pingClazz) {
            field.setAccessible(true);
            ping = field.get(server);
            break;
         }
      }

      Preconditions.checkNotNull(ping, "Failed to get server ping");
      Class<?> serverDataClass = NMSUtil.nms("ServerPing$ServerData", "net.minecraft.network.protocol.status.ServerPing$ServerData");
      Object serverData = null;

      for (Field fieldx : pingClazz.getDeclaredFields()) {
         if (fieldx.getType() == serverDataClass) {
            fieldx.setAccessible(true);
            serverData = fieldx.get(ping);
            break;
         }
      }

      Preconditions.checkNotNull(serverData, "Failed to get server data");

      for (Field fieldxx : serverDataClass.getDeclaredFields()) {
         if (fieldxx.getType() == int.class) {
            fieldxx.setAccessible(true);
            int protocolVersion = (Integer)fieldxx.get(serverData);
            if (protocolVersion != -1) {
               return protocolVersion;
            }
         }
      }

      throw new RuntimeException("Failed to get server");
   }

   @Nullable
   @Override
   protected Object getServerConnection() throws ReflectiveOperationException {
      Class<?> serverClass = NMSUtil.nms("MinecraftServer", "net.minecraft.server.MinecraftServer");
      Class<?> connectionClass = NMSUtil.nms("ServerConnection", "net.minecraft.server.network.ServerConnection");
      Object server = ReflectionUtil.invokeStatic(serverClass, "getServer");

      for (Method method : serverClass.getDeclaredMethods()) {
         if (method.getReturnType() == connectionClass && method.getParameterTypes().length == 0) {
            Object connection = method.invoke(server);
            if (connection != null) {
               return connection;
            }
         }
      }

      return null;
   }

   @Override
   protected WrappedChannelInitializer createChannelInitializer(ChannelInitializer<Channel> oldInitializer) {
      return new BukkitChannelInitializer(oldInitializer);
   }

   @Override
   protected void blame(ChannelHandler bootstrapAcceptor) throws ReflectiveOperationException {
      ClassLoader classLoader = bootstrapAcceptor.getClass().getClassLoader();
      if (classLoader.getClass().getName().equals("org.bukkit.plugin.java.PluginClassLoader")) {
         PluginDescriptionFile description = ReflectionUtil.get(classLoader, "description", PluginDescriptionFile.class);
         throw new RuntimeException(
            "Unable to inject, due to " + bootstrapAcceptor.getClass().getName() + ", try without the plugin " + description.getName() + "?"
         );
      } else {
         throw new RuntimeException("Unable to find core component 'childHandler', please check your plugins. issue: " + bootstrapAcceptor.getClass().getName());
      }
   }

   @Override
   public boolean lateProtocolVersionSetting() {
      return !PaperViaInjector.PAPER_PROTOCOL_METHOD && !HAS_WORLD_VERSION_PROTOCOL_VERSION;
   }

   public boolean isBinded() {
      if (PaperViaInjector.PAPER_INJECTION_METHOD) {
         return true;
      } else {
         try {
            Object connection = this.getServerConnection();
            if (connection == null) {
               return false;
            }

            for (Field field : connection.getClass().getDeclaredFields()) {
               if (List.class.isAssignableFrom(field.getType())) {
                  field.setAccessible(true);
                  List<?> value = (List<?>)field.get(connection);
                  synchronized (value) {
                     if (!value.isEmpty() && value.get(0) instanceof ChannelFuture) {
                        return true;
                     }
                  }
               }
            }
         } catch (ReflectiveOperationException var10) {
            var10.printStackTrace();
         }

         return false;
      }
   }
}
