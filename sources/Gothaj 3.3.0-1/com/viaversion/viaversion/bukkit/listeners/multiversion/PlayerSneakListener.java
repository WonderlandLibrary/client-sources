package com.viaversion.viaversion.bukkit.listeners.multiversion;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerSneakListener extends ViaBukkitListener {
   private static final float STANDING_HEIGHT = 1.8F;
   private static final float HEIGHT_1_14 = 1.5F;
   private static final float HEIGHT_1_9 = 1.6F;
   private static final float DEFAULT_WIDTH = 0.6F;
   private final boolean is1_9Fix;
   private final boolean is1_14Fix;
   private Map<Player, Boolean> sneaking;
   private Set<UUID> sneakingUuids;
   private final Method getHandle;
   private Method setSize;
   private boolean useCache;

   public PlayerSneakListener(ViaVersionPlugin plugin, boolean is1_9Fix, boolean is1_14Fix) throws ReflectiveOperationException {
      super(plugin, null);
      this.is1_9Fix = is1_9Fix;
      this.is1_14Fix = is1_14Fix;
      String packageName = plugin.getServer().getClass().getPackage().getName();
      this.getHandle = Class.forName(packageName + ".entity.CraftPlayer").getMethod("getHandle");
      Class<?> entityPlayerClass = Class.forName(packageName.replace("org.bukkit.craftbukkit", "net.minecraft.server") + ".EntityPlayer");

      try {
         this.setSize = entityPlayerClass.getMethod("setSize", float.class, float.class);
      } catch (NoSuchMethodException var7) {
         this.setSize = entityPlayerClass.getMethod("a", float.class, float.class);
      }

      if (Via.getAPI().getServerVersion().lowestSupportedVersion() >= ProtocolVersion.v1_9.getVersion()) {
         this.sneaking = new WeakHashMap<>();
         this.useCache = true;
         plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            for (Entry<Player, Boolean> entry : this.sneaking.entrySet()) {
               this.setHeight(entry.getKey(), entry.getValue() ? 1.5F : 1.6F);
            }
         }, 1L, 1L);
      }

      if (is1_14Fix) {
         this.sneakingUuids = new HashSet<>();
      }
   }

   @EventHandler(
      ignoreCancelled = true
   )
   public void playerToggleSneak(PlayerToggleSneakEvent event) {
      Player player = event.getPlayer();
      UserConnection userConnection = this.getUserConnection(player);
      if (userConnection != null) {
         ProtocolInfo info = userConnection.getProtocolInfo();
         if (info != null) {
            int protocolVersion = info.getProtocolVersion();
            if (this.is1_14Fix && protocolVersion >= ProtocolVersion.v1_14.getVersion()) {
               this.setHeight(player, event.isSneaking() ? 1.5F : 1.8F);
               if (event.isSneaking()) {
                  this.sneakingUuids.add(player.getUniqueId());
               } else {
                  this.sneakingUuids.remove(player.getUniqueId());
               }

               if (!this.useCache) {
                  return;
               }

               if (event.isSneaking()) {
                  this.sneaking.put(player, true);
               } else {
                  this.sneaking.remove(player);
               }
            } else if (this.is1_9Fix && protocolVersion >= ProtocolVersion.v1_9.getVersion()) {
               this.setHeight(player, event.isSneaking() ? 1.6F : 1.8F);
               if (!this.useCache) {
                  return;
               }

               if (event.isSneaking()) {
                  this.sneaking.put(player, false);
               } else {
                  this.sneaking.remove(player);
               }
            }
         }
      }
   }

   @EventHandler(
      ignoreCancelled = true
   )
   public void playerDamage(EntityDamageEvent event) {
      if (this.is1_14Fix) {
         if (event.getCause() == DamageCause.SUFFOCATION) {
            if (event.getEntityType() == EntityType.PLAYER) {
               Player player = (Player)event.getEntity();
               if (this.sneakingUuids.contains(player.getUniqueId())) {
                  double y = player.getEyeLocation().getY() + 0.045;
                  y -= (double)((int)y);
                  if (y < 0.09) {
                     event.setCancelled(true);
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void playerQuit(PlayerQuitEvent event) {
      if (this.sneaking != null) {
         this.sneaking.remove(event.getPlayer());
      }

      if (this.sneakingUuids != null) {
         this.sneakingUuids.remove(event.getPlayer().getUniqueId());
      }
   }

   private void setHeight(Player player, float height) {
      try {
         this.setSize.invoke(this.getHandle.invoke(player), 0.6F, height);
      } catch (InvocationTargetException | IllegalAccessException var4) {
         var4.printStackTrace();
      }
   }
}
