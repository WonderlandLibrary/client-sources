package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MainHandProvider;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeMainHandProvider extends MainHandProvider {
   private static Method getSettings;
   private static Method setMainHand;

   @Override
   public void setMainHand(UserConnection user, int hand) {
      ProtocolInfo info = user.getProtocolInfo();
      if (info != null && info.getUuid() != null) {
         ProxiedPlayer player = ProxyServer.getInstance().getPlayer(info.getUuid());
         if (player != null) {
            try {
               Object settings = getSettings.invoke(player);
               if (settings != null) {
                  setMainHand.invoke(settings, hand);
               }
            } catch (InvocationTargetException | IllegalAccessException var6) {
               var6.printStackTrace();
            }
         }
      }
   }

   static {
      try {
         getSettings = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getSettings");
         setMainHand = Class.forName("net.md_5.bungee.protocol.packet.ClientSettings").getDeclaredMethod("setMainHand", int.class);
      } catch (Exception var1) {
      }
   }
}
