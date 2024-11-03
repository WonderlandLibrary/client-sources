package com.viaversion.viaversion.bungee.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeStorage implements StorableObject {
   private static Field bossField;
   private final ProxiedPlayer player;
   private String currentServer;
   private Set<UUID> bossbar;

   public BungeeStorage(ProxiedPlayer player) {
      this.player = player;
      this.currentServer = "";
      if (bossField != null) {
         try {
            this.bossbar = (Set<UUID>)bossField.get(player);
         } catch (IllegalAccessException var3) {
            var3.printStackTrace();
         }
      }
   }

   public ProxiedPlayer getPlayer() {
      return this.player;
   }

   public String getCurrentServer() {
      return this.currentServer;
   }

   public void setCurrentServer(String currentServer) {
      this.currentServer = currentServer;
   }

   public Set<UUID> getBossbar() {
      return this.bossbar;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         BungeeStorage that = (BungeeStorage)o;
         if (!Objects.equals(this.player, that.player)) {
            return false;
         } else {
            return !Objects.equals(this.currentServer, that.currentServer) ? false : Objects.equals(this.bossbar, that.bossbar);
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.player != null ? this.player.hashCode() : 0;
      result = 31 * result + (this.currentServer != null ? this.currentServer.hashCode() : 0);
      return 31 * result + (this.bossbar != null ? this.bossbar.hashCode() : 0);
   }

   static {
      try {
         Class<?> user = Class.forName("net.md_5.bungee.UserConnection");
         bossField = user.getDeclaredField("sentBossBars");
         bossField.setAccessible(true);
      } catch (ClassNotFoundException var1) {
      } catch (NoSuchFieldException var2) {
      }
   }
}
