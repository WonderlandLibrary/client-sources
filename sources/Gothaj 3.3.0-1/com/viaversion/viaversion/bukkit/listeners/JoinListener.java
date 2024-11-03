package com.viaversion.viaversion.bukkit.listeners;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.handlers.BukkitEncodeHandler;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import io.netty.channel.Channel;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Nullable;

public class JoinListener implements Listener {
   private static final Method GET_HANDLE;
   private static final Field CONNECTION;
   private static final Field NETWORK_MANAGER;
   private static final Field CHANNEL;

   private static Field findField(boolean checkSuperClass, Class<?> clazz, String... types) throws NoSuchFieldException {
      for (Field field : clazz.getDeclaredFields()) {
         String fieldTypeName = field.getType().getSimpleName();

         for (String type : types) {
            if (fieldTypeName.equals(type)) {
               if (!Modifier.isPublic(field.getModifiers())) {
                  field.setAccessible(true);
               }

               return field;
            }
         }
      }

      if (checkSuperClass && clazz != Object.class && clazz.getSuperclass() != null) {
         return findField(true, clazz.getSuperclass(), types);
      } else {
         throw new NoSuchFieldException(types[0]);
      }
   }

   private static Field findField(Class<?> clazz, Class<?> fieldType) throws NoSuchFieldException {
      for (Field field : clazz.getDeclaredFields()) {
         if (field.getType() == fieldType) {
            if (!Modifier.isPublic(field.getModifiers())) {
               field.setAccessible(true);
            }

            return field;
         }
      }

      throw new NoSuchFieldException(fieldType.getSimpleName());
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onJoin(PlayerJoinEvent e) {
      if (CHANNEL != null) {
         Player player = e.getPlayer();

         Channel channel;
         try {
            channel = this.getChannel(player);
         } catch (Exception var6) {
            Via.getPlatform().getLogger().log(Level.WARNING, var6, () -> "Could not find Channel for logging-in player " + player.getUniqueId());
            return;
         }

         if (channel.isOpen()) {
            UserConnection user = this.getUserConnection(channel);
            if (user == null) {
               Via.getPlatform().getLogger().log(Level.WARNING, "Could not find UserConnection for logging-in player {0}", player.getUniqueId());
            } else {
               ProtocolInfo info = user.getProtocolInfo();
               info.setUuid(player.getUniqueId());
               info.setUsername(player.getName());
               Via.getManager().getConnectionManager().onLoginSuccess(user);
            }
         }
      }
   }

   @Nullable
   private UserConnection getUserConnection(Channel channel) {
      BukkitEncodeHandler encoder = (BukkitEncodeHandler)channel.pipeline().get(BukkitEncodeHandler.class);
      return encoder != null ? encoder.connection() : null;
   }

   private Channel getChannel(Player player) throws Exception {
      Object entityPlayer = GET_HANDLE.invoke(player);
      Object pc = CONNECTION.get(entityPlayer);
      Object nm = NETWORK_MANAGER.get(pc);
      return (Channel)CHANNEL.get(nm);
   }

   static {
      Method getHandleMethod = null;
      Field gamePacketListenerField = null;
      Field connectionField = null;
      Field channelField = null;

      try {
         getHandleMethod = NMSUtil.obc("entity.CraftPlayer").getDeclaredMethod("getHandle");
         gamePacketListenerField = findField(false, getHandleMethod.getReturnType(), "PlayerConnection", "ServerGamePacketListenerImpl");
         connectionField = findField(true, gamePacketListenerField.getType(), "NetworkManager", "Connection");
         channelField = findField(connectionField.getType(), Class.forName("io.netty.channel.Channel"));
      } catch (NoSuchFieldException | ClassNotFoundException | NoSuchMethodException var5) {
         Via.getPlatform()
            .getLogger()
            .log(
               Level.WARNING,
               "Couldn't find reflection methods/fields to access Channel from player.\nLogin race condition fixer will be disabled.\n Some plugins that use ViaAPI on join event may work incorrectly.",
               (Throwable)var5
            );
      }

      GET_HANDLE = getHandleMethod;
      CONNECTION = gamePacketListenerField;
      NETWORK_MANAGER = connectionField;
      CHANNEL = channelField;
   }
}
