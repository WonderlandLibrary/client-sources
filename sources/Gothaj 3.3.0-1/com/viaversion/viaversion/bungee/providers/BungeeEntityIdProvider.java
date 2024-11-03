package com.viaversion.viaversion.bungee.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bungee.storage.BungeeStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.EntityIdProvider;
import java.lang.reflect.Method;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeEntityIdProvider extends EntityIdProvider {
   private static final Method GET_CLIENT_ENTITY_ID;

   @Override
   public int getEntityId(UserConnection user) throws Exception {
      BungeeStorage storage = user.get(BungeeStorage.class);
      ProxiedPlayer player = storage.getPlayer();
      return (Integer)GET_CLIENT_ENTITY_ID.invoke(player);
   }

   static {
      try {
         GET_CLIENT_ENTITY_ID = Class.forName("net.md_5.bungee.UserConnection").getDeclaredMethod("getClientEntityId");
      } catch (ClassNotFoundException | NoSuchMethodException var1) {
         throw new RuntimeException(var1);
      }
   }
}
