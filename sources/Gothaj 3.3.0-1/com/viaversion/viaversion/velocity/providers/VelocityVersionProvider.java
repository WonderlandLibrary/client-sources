package com.viaversion.viaversion.velocity.providers;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.ServerConnection;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.velocity.platform.VelocityViaInjector;
import io.netty.channel.ChannelHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.IntStream;
import org.jetbrains.annotations.Nullable;

public class VelocityVersionProvider extends BaseVersionProvider {
   private static final Method GET_ASSOCIATION = getAssociationMethod();

   @Nullable
   private static Method getAssociationMethod() {
      try {
         return Class.forName("com.velocitypowered.proxy.connection.MinecraftConnection").getMethod("getAssociation");
      } catch (ClassNotFoundException | NoSuchMethodException var1) {
         var1.printStackTrace();
         return null;
      }
   }

   @Override
   public int getClosestServerProtocol(UserConnection user) throws Exception {
      return user.isClientSide() ? this.getBackProtocol(user) : this.getFrontProtocol(user);
   }

   private int getBackProtocol(UserConnection user) throws Exception {
      ChannelHandler mcHandler = user.getChannel().pipeline().get("handler");
      ServerConnection serverConnection = (ServerConnection)GET_ASSOCIATION.invoke(mcHandler);
      return Via.proxyPlatform().protocolDetectorService().serverProtocolVersion(serverConnection.getServerInfo().getName());
   }

   private int getFrontProtocol(UserConnection user) throws Exception {
      int playerVersion = user.getProtocolInfo().getProtocolVersion();
      IntStream versions = ProtocolVersion.SUPPORTED_VERSIONS.stream().mapToInt(ProtocolVersion::getProtocol);
      if (VelocityViaInjector.GET_PLAYER_INFO_FORWARDING_MODE != null
         && ((Enum)VelocityViaInjector.GET_PLAYER_INFO_FORWARDING_MODE.invoke(VelocityPlugin.PROXY.getConfiguration())).name().equals("MODERN")) {
         versions = versions.filter(ver -> ver >= com.viaversion.viaversion.api.protocol.version.ProtocolVersion.v1_13.getVersion());
      }

      int[] compatibleProtocols = versions.toArray();
      if (Arrays.binarySearch(compatibleProtocols, playerVersion) >= 0) {
         return playerVersion;
      } else if (playerVersion < compatibleProtocols[0]) {
         return compatibleProtocols[0];
      } else {
         for (int i = compatibleProtocols.length - 1; i >= 0; i--) {
            int protocol = compatibleProtocols[i];
            if (playerVersion > protocol && com.viaversion.viaversion.api.protocol.version.ProtocolVersion.isRegistered(protocol)) {
               return protocol;
            }
         }

         Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + playerVersion);
         return playerVersion;
      }
   }
}
