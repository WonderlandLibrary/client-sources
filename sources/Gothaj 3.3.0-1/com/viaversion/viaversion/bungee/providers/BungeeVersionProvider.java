package com.viaversion.viaversion.bungee.providers;

import com.google.common.collect.Lists;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.util.ReflectionUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.protocol.ProtocolConstants;

public class BungeeVersionProvider extends BaseVersionProvider {
   @Override
   public int getClosestServerProtocol(UserConnection user) throws Exception {
      List<Integer> list = ReflectionUtil.getStatic(ProtocolConstants.class, "SUPPORTED_VERSION_IDS", List.class);
      List<Integer> sorted = new ArrayList<>(list);
      Collections.sort(sorted);
      ProtocolInfo info = user.getProtocolInfo();
      if (sorted.contains(info.getProtocolVersion())) {
         return info.getProtocolVersion();
      } else if (info.getProtocolVersion() < sorted.get(0)) {
         return getLowestSupportedVersion();
      } else {
         for (Integer protocol : Lists.reverse(sorted)) {
            if (info.getProtocolVersion() > protocol && ProtocolVersion.isRegistered(protocol)) {
               return protocol;
            }
         }

         Via.getPlatform().getLogger().severe("Panic, no protocol id found for " + info.getProtocolVersion());
         return info.getProtocolVersion();
      }
   }

   public static int getLowestSupportedVersion() {
      try {
         List<Integer> list = ReflectionUtil.getStatic(ProtocolConstants.class, "SUPPORTED_VERSION_IDS", List.class);
         return list.get(0);
      } catch (IllegalAccessException | NoSuchFieldException var2) {
         var2.printStackTrace();
         return ProxyServer.getInstance().getProtocolVersion();
      }
   }
}
