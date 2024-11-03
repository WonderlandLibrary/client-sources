package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.network.ProtocolVersion;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.util.ReflectionUtil;
import com.viaversion.viaversion.velocity.handlers.VelocityChannelInitializer;
import io.netty.channel.ChannelInitializer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.jetbrains.annotations.Nullable;

public class VelocityViaInjector implements ViaInjector {
   public static final Method GET_PLAYER_INFO_FORWARDING_MODE = getPlayerInfoForwardingModeMethod();

   @Nullable
   private static Method getPlayerInfoForwardingModeMethod() {
      try {
         return Class.forName("com.velocitypowered.proxy.config.VelocityConfiguration").getMethod("getPlayerInfoForwardingMode");
      } catch (ClassNotFoundException | NoSuchMethodException var1) {
         var1.printStackTrace();
         return null;
      }
   }

   private ChannelInitializer getInitializer() throws Exception {
      Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
      Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getServerChannelInitializer");
      return (ChannelInitializer)ReflectionUtil.invoke(channelInitializerHolder, "get");
   }

   private ChannelInitializer getBackendInitializer() throws Exception {
      Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
      Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getBackendChannelInitializer");
      return (ChannelInitializer)ReflectionUtil.invoke(channelInitializerHolder, "get");
   }

   @Override
   public void inject() throws Exception {
      Via.getPlatform().getLogger().info("Replacing channel initializers; you can safely ignore the following two warnings.");
      Object connectionManager = ReflectionUtil.get(VelocityPlugin.PROXY, "cm", Object.class);
      Object channelInitializerHolder = ReflectionUtil.invoke(connectionManager, "getServerChannelInitializer");
      ChannelInitializer originalInitializer = this.getInitializer();
      channelInitializerHolder.getClass()
         .getMethod("set", ChannelInitializer.class)
         .invoke(channelInitializerHolder, new VelocityChannelInitializer(originalInitializer, false));
      Object backendInitializerHolder = ReflectionUtil.invoke(connectionManager, "getBackendChannelInitializer");
      ChannelInitializer backendInitializer = this.getBackendInitializer();
      backendInitializerHolder.getClass()
         .getMethod("set", ChannelInitializer.class)
         .invoke(backendInitializerHolder, new VelocityChannelInitializer(backendInitializer, true));
   }

   @Override
   public void uninject() {
      Via.getPlatform().getLogger().severe("ViaVersion cannot remove itself from Velocity without a reboot!");
   }

   @Override
   public int getServerProtocolVersion() throws Exception {
      return getLowestSupportedProtocolVersion();
   }

   @Override
   public IntSortedSet getServerProtocolVersions() throws Exception {
      int lowestSupportedProtocolVersion = getLowestSupportedProtocolVersion();
      IntSortedSet set = new IntLinkedOpenHashSet();

      for (ProtocolVersion version : ProtocolVersion.SUPPORTED_VERSIONS) {
         if (version.getProtocol() >= lowestSupportedProtocolVersion) {
            set.add(version.getProtocol());
         }
      }

      return set;
   }

   public static int getLowestSupportedProtocolVersion() {
      try {
         if (GET_PLAYER_INFO_FORWARDING_MODE != null
            && ((Enum)GET_PLAYER_INFO_FORWARDING_MODE.invoke(VelocityPlugin.PROXY.getConfiguration())).name().equals("MODERN")) {
            return com.viaversion.viaversion.api.protocol.version.ProtocolVersion.v1_13.getVersion();
         }
      } catch (InvocationTargetException | IllegalAccessException var1) {
      }

      return ProtocolVersion.MINIMUM_VERSION.getProtocol();
   }

   @Override
   public JsonObject getDump() {
      JsonObject data = new JsonObject();

      try {
         data.addProperty("currentInitializer", this.getInitializer().getClass().getName());
      } catch (Exception var3) {
      }

      return data;
   }
}
