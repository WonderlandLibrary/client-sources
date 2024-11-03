package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class HandItemCache extends BukkitRunnable {
   private final Map<UUID, Item> handCache = new ConcurrentHashMap<>();

   public void run() {
      List<UUID> players = new ArrayList<>(this.handCache.keySet());

      for (Player p : Bukkit.getOnlinePlayers()) {
         this.handCache.put(p.getUniqueId(), convert(p.getItemInHand()));
         players.remove(p.getUniqueId());
      }

      for (UUID uuid : players) {
         this.handCache.remove(uuid);
      }
   }

   public Item getHandItem(UUID player) {
      return this.handCache.get(player);
   }

   public static Item convert(ItemStack itemInHand) {
      return itemInHand == null
         ? new DataItem(0, (byte)0, (short)0, null)
         : new DataItem(itemInHand.getTypeId(), (byte)itemInHand.getAmount(), itemInHand.getDurability(), null);
   }
}
